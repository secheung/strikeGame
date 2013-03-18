package gameDev;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class AnimData{
	long frameTimer;
	int FPS = 30;
	
	int numOfFrames;
	int currentFrame;
	int currentRow;
	int currentColumn;
	
	int frameWidth;
	int frameHeight;
	
	int sheetWidth;
	int sheetHeight;
	
	BufferedImage spriteSheet;
	String filePath;
	String animKey;
	
	boolean playedOnce;
	
	int rows;
	int columns;
	
	public AnimData(String filePath, int rows, int columns){
		this.frameTimer = 0;
		this.currentFrame = 1;
		
		this.filePath = filePath;
		this.rows = rows;
		this.columns = columns;
		
		this.FPS = 1000/FPS;
		this.numOfFrames = rows*columns;
		playedOnce = false;
		
	}
	
	public AnimData(AnimData copy){
		this.frameTimer = 0;
		this.currentFrame = 1;
		
		this.filePath = copy.getFilePath();
		this.rows = copy.getRows();
		this.columns = copy.getColumns();
		
		this.FPS = 1000/FPS;
		this.numOfFrames = rows*columns;
		playedOnce = false;
		
		this.loadImageData(copy.getSheet(), copy.getSheetWidth(), copy.getSheetHeight());
	}
	
	public int getDrawX(){
		return currentColumn*frameWidth;
	}
	
	public int getDrawY(){
		return currentRow*frameHeight;
	}
	
	public int getFrameWidth(){
		return frameWidth;
	}

	public int getFrameHeight(){
		return frameHeight;
	}
	
	public int getSheetWidth() {
		return sheetWidth;
	}
	
	public int getSheetHeight() {
		return sheetHeight;
	}
	
	public BufferedImage getSheet(){
		return spriteSheet;
	}
	
	public String getAnimKey(){
		return animKey;
	}

	public String getFilePath(){
		return filePath;
	}
	
	public int getCurrentFrame(){
		return currentFrame;
	}
	
	public int getNumOfFrames(){
		return numOfFrames;
	}
	
	public boolean getIfPlayedOnce(){
		return playedOnce;
	}
	
	public int getRows(){
		return rows;
	}
	
	public int getColumns(){
		return columns;
	}
	
	public void loadImageData(BufferedImage spriteSheet, int sheetWidth, int sheetHeight){
	//public void setSheetData(int sheetWidth, int sheetHeight){
		this.spriteSheet = spriteSheet;
		this.sheetWidth = sheetWidth;
		this.sheetHeight = sheetHeight;
		
		this.frameWidth = sheetWidth/columns;
		this.frameHeight = sheetHeight/rows;
	}
	
	public void resetFrameCount(){
		currentFrame = 1;
		frameTimer = System.currentTimeMillis();
		playedOnce = false;
	}
	
	public void updateFrame(long gameTime){
		
		if(gameTime > frameTimer + FPS){
			frameTimer = gameTime;
			currentFrame = (int) (currentFrame + 1);
			
			if(currentFrame >= numOfFrames){
				playedOnce = true;
				currentFrame = 1;
			}
			
			currentRow = ((int)currentFrame / columns);
			currentColumn = ((int)currentFrame % columns);	
		}
	}
}
