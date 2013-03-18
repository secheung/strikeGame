package gameDev;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class GameAssets {
	Hashtable<String, Image> imageTable;
	Hashtable<String, AnimData> animTable;
	ArrayList<String> animKeys;
	MediaTracker tracker;
	
	public GameAssets(){
		imageTable = new Hashtable<String, Image>();
		animTable = new Hashtable<String, AnimData>();
		animKeys = new ArrayList<String>(); 
	}
	/*
	public void addImage(String imageKey, String fileName){
		Image set = Toolkit.getDefaultToolkit().getImage(fileName);
		imageTable.put(imageKey, set);
	}
	
	public Image getImage(String imageKey){
		return imageTable.get(imageKey);
	}
	*/
	
	public void addAnimData(String animKey,String filePath, int rows, int columns){
		AnimData set= new AnimData(filePath,rows,columns);
		animTable.put(animKey, set);
		animKeys.add(animKey);
	}
	
	
	public AnimData getAnimation(String animKey){
		return animTable.get(animKey);
	}
	
	
	public AnimData getCopyAnim(String animKey){
		AnimData buffer = animTable.get(animKey);
		AnimData copy = new AnimData(buffer);
		
		return copy;
	}
	
	public void loadAnimData(MediaTracker tracker){
		this.tracker = tracker;
		String keyBuffer;
		AnimData animBuffer;
		//Image imageBuffer;
		BufferedImage imageBuffer;
		
		int sheetWidth;
		int sheetHeight;
		
		Enumeration<String> iterator = animTable.keys();
		while(iterator.hasMoreElements()){
			keyBuffer = iterator.nextElement();
			animBuffer = animTable.get(keyBuffer);

			//imageBuffer = Toolkit.getDefaultToolkit().getImage(animBuffer.getFilePath());
			try {
			    imageBuffer = ImageIO.read(new File(animBuffer.getFilePath()));
				tracker.waitForID(0);
			 
				sheetWidth = imageBuffer.getWidth(null);
				sheetHeight = imageBuffer.getHeight(null);
				
				animTable.get(keyBuffer).loadImageData(imageBuffer,sheetWidth, sheetHeight);
				
				tracker.addImage(imageBuffer, 0);
			} catch (IOException e) {} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
