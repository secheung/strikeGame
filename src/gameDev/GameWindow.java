package gameDev;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.Enumeration;

public class GameWindow extends Frame implements Runnable, MouseListener, MouseMotionListener, KeyListener, WindowListener{

	public static int SCREENWIDTH = (int)(Toolkit.getDefaultToolkit().getScreenSize().width);
	public static int SCREENHEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.5);
	
	MediaTracker tracker;
	GameEnv gameEnv;
	
	public GameWindow(GameEnv gameEnv){
		super("strike run");
		
		tracker = new MediaTracker(this);
		gameEnv.getGameAssets().loadAnimData(tracker);
		tracker = null;
		this.gameEnv = gameEnv;
		Panel panel = new Panel();
		
		addMouseListener(this);
        addMouseMotionListener(this);
        addWindowListener(this);
		addKeyListener(this);
		
		setSize(GameWindow.SCREENWIDTH,GameWindow.SCREENHEIGHT);
		setVisible(true);
		setBackground(Color.GRAY);
	}
	
	@Override
	public void run() {
		long prevTime = System.currentTimeMillis();
		
		try{
			while(this != null){
				if(System.currentTimeMillis() - prevTime > 10){
					repaint();
					prevTime = System.currentTimeMillis();
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

	private void gameLoop(){
		
	}
	
	public void update(Graphics g){
		gameEnv.update();
		paint(g);
	}
	
	public void paint(Graphics g){
		//BufferedImage offscreen = new BufferedImage(GameWindow.SCREENWIDTH,GameWindow.SCREENHEIGHT,BufferedImage.TYPE_INT_ARGB);
		Image offscreen = createImage(GameWindow.SCREENWIDTH,GameWindow.SCREENHEIGHT);
		Graphics offscreengraphics = offscreen.getGraphics();
		
		Enumeration<String> iterator = gameEnv.getGameObjKeys();
		String key;
		while(iterator.hasMoreElements()){
			key = iterator.nextElement();
			gameEnv.getGameObj(key).draw(offscreengraphics);
		}
		
		g.drawImage(offscreen,0,0,this);
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent key) {
		gameEnv.keyPressed(key);
		
	}

	@Override
	public void keyReleased(KeyEvent key) {
		gameEnv.keyReleased(key);
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
