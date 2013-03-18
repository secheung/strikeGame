package gameDev;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public abstract class GameObj {
	public static enum Direction{
		LEFT,
		RIGHT,
		UP,
		DOWN
	}
	
	public interface ObjState{};
	
	protected String name;
	protected double x;
	protected double y;
	protected double width;
	protected double height;
	
	protected Image displayImage;
	protected AnimData displayAnim = null;
	protected Direction direction;
	protected GameEnv gameEnv;
	protected GameAssets gameAssets;
	
	public GameObj(String name,double x, double y, GameEnv gameEnv){
		this.name = name;
		this.gameEnv = gameEnv;
		this.gameAssets = gameEnv.getGameAssets();
		this.x = x;
		this.y = y;
	}
	
	public double getX(){
		return x;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public double getY(){
		return y;
	}

	public void setY(double y){
		this.y = y;
	}
	
	public double getWidth(){
		return width;
	}
	
	public void setWidth(double width){
		this.width = width;
	}
	
	public double getHeight(){
		return height;
	}
	
	public void setHeight(double height){
		this.height = height;
	}
	
	public AnimData getDisplayAnim(){
		return displayAnim;
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public void setDirection(Direction direction){
		this.direction = direction;
	}
	
	protected double getDistanceFromCentre(GameObj obj){
		double centreX1 = (obj.getX()+obj.getWidth())/2;
		double centreY1 = (obj.getY()+obj.getHeight())/2;
		double centreX2 = (this.getX()+this.getWidth())/2;
		double centreY2 = (this.getY()+this.getHeight())/2;
		
		double distance = Math.sqrt(Math.pow(centreX1 - centreX2,2) + Math.pow(centreY1 - centreY2,2));
		
		return distance;
	}
	
	protected  boolean radiusColDetection(GameObj obj1,GameObj obj2){
		return true;
	}
	
	protected boolean boxDetection(GameObj obj){
		double left1, left2;
		double right1, right2;
		double top1, top2;
		double bottom1, bottom2;
	 
		left1 = obj.getX();
		left2 = this.getX();
		right1 = obj.getX() + obj.getWidth();
		right2 = this.getX() + this.getWidth();
		top1 = obj.getY();
		top2 = this.getY();
		bottom1 = obj.getY() + obj.getHeight();
		bottom2 = this.getY() + this.getHeight();
		
		if (bottom1 < top2) return false;
		if (top1 > bottom2) return false;
	 
		if (right1 < left2) return false;
		if (left1 > right2) return false;
		
		return true;
	}
	
	public abstract ObjState getState();
	public abstract void setState(ObjState state);
	protected abstract void updateState();
	protected abstract void update();
	protected abstract void draw(Graphics graphics);
	
	protected abstract void keyPressed(KeyEvent key);
	protected abstract void keyReleased(KeyEvent key);
	
	protected abstract void mousePressed();
	protected abstract void mouseReleased();
	protected abstract void mouseDragged();
}
