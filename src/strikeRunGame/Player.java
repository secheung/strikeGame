package strikeRunGame;

import gameDev.GameEnv;
import gameDev.GameObj;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import strikeRunGame.BasicEnemy.EnemyState;

public class Player extends GameObj{
	static enum PlayerState implements ObjState{
		STAND,
		ATTACK,
		ENDSTRIKE,
		MOVE,
		DODGE,
		DEAD
	} 
	
	private static final double MOVEMENTSPEED = 1.5;
	private static final double MOVEMENTDODGESPEED = 15;
	private static final int OBJWIDTH = 85;
	private static final int OBJHEIGHT = 240;
	private static final int ATTACKDISTANCE = 50;
	
	private static final int KEYRIGHT = KeyEvent.VK_RIGHT;
	private static final int KEYLEFT = KeyEvent.VK_LEFT;
	private static final int KEYDODGE = KeyEvent.VK_SHIFT;
	private static final int KEYATTACK = KeyEvent.VK_A;
	
	boolean keyRight = false;
	boolean keyLeft = false;
	boolean keyDodge = false;
	boolean keyAttack = false;
	
	private static final int maxDodgeTime = 60;
	private static final int decDodgeTime = 5;
	private static final int dodgeDelay = -20*decDodgeTime;
	int dodgeTimer = maxDodgeTime;
	boolean dodgeOK = true;
	
	private static final int incAttackFrame = 1;
	int endAttackFrame = 100;
	int attackFrame = endAttackFrame;
	boolean attackOK = true;
	int attackNum = 1;
	
	PlayerState playerState;
	
	public Player(String name, double x, double y, GameEnv gameEnv) {
		super(name, x, y,gameEnv);
		playerState = PlayerState.STAND;
		direction = Direction.RIGHT;
		//this.setAnim("playerStand");
		displayAnim = gameAssets.getAnimation("playerStand");
		
		this.width = OBJWIDTH;
		this.height = OBJHEIGHT;
	}

	@Override
	public PlayerState getState() {
		return playerState;
	}
	
	@Override
	public void setState(ObjState state) {
		this.playerState = (PlayerState) state;
	}
	
	public int getAttackNum(){
		return attackNum;
	}
	
	public void setAttackNum(int attackNum){
		this.attackNum = attackNum;
	}
	
	@Override
	public void updateState(){
		//System.out.println(playerState+" "+playerDirection);
		
		//moving
		if((keyLeft || keyRight) && ((playerState == PlayerState.STAND || playerState == PlayerState.MOVE) || (displayAnim.getIfPlayedOnce() && playerState == PlayerState.DODGE))){
			playerState = PlayerState.MOVE;
			//this.setAnim("playerRun");
			displayAnim = gameAssets.getAnimation("playerRun");
			setDirectionByInput();
		} 
		
		//executing dodge
		if(keyDodge && (keyLeft||keyRight) && dodgeOK && (playerState == PlayerState.MOVE || playerState == PlayerState.ATTACK || playerState == PlayerState.ENDSTRIKE)){
			playerState = PlayerState.DODGE;
			//this.setAnim("playerDodge");
			displayAnim = gameAssets.getAnimation("playerDodge");
			displayAnim.resetFrameCount();
			setDirectionByInput();
			dodgeOK = false;
		} 
		
		//coming out of dodge
		//if(displayAnim.getIfPlayedOnce() && playerState == PlayerState.DODGE){
		if(dodgeTimer < 0 && playerState == PlayerState.DODGE){
			playerState = PlayerState.STAND;
			//this.setAnim("playerStand");
			displayAnim = gameAssets.getAnimation("playerStand");
			if(direction == Direction.LEFT)
				direction = Direction.RIGHT;
			else if(direction == Direction.RIGHT)
				direction = Direction.LEFT;
		}
		
		//execute attack
		if(keyAttack && (playerState == PlayerState.STAND ||playerState == PlayerState.MOVE)){
			GameObj enemy = getObjToAttack();
			
			if(enemy != null){
				playerState = PlayerState.ATTACK;
				attackNum = 1;
				//this.setAnim("strike"+attackNum);
				displayAnim = gameAssets.getAnimation("strike"+attackNum);
				displayAnim.resetFrameCount();
				attackFrame = 0;
			}
		}
		
		//attacking
		if(playerState == PlayerState.ATTACK){
			if(displayAnim.getIfPlayedOnce()){
				playerState = PlayerState.ENDSTRIKE;
				//this.setAnim("playerstrike"+attackNum+"end");
				displayAnim = gameAssets.getAnimation("playerstrike"+attackNum+"end");
				displayAnim.resetFrameCount();
			}
		}
		
		if(playerState == PlayerState.ENDSTRIKE){
			if(displayAnim.getIfPlayedOnce()){
				playerState = PlayerState.STAND;
				displayAnim = gameAssets.getAnimation("playerStand");
				//this.setAnim("playerStand");
			}
		}
		
		//standing
		if(!keyLeft && !keyRight && playerState == PlayerState.MOVE){
			playerState = PlayerState.STAND;
			displayAnim = gameAssets.getAnimation("playerStand");
			//this.setAnim("playerStand");
		}
		
		updateDodgeTimer();
		//updateAttackFrame();
	}
	
	public void updateDodgeTimer(){
		
		if(!dodgeOK)
			dodgeTimer -= decDodgeTime;
		
		if(dodgeTimer <= dodgeDelay){
			dodgeTimer = maxDodgeTime;
			dodgeOK = true;
		}
	}
	
	public void updateAttackFrame(){
		attackFrame += incAttackFrame;
		
		if(attackFrame >= endAttackFrame){
			updateState();
			attackFrame = 0;
		}
	}
	
	public GameObj getObjToAttack(){
		Enumeration<String> objKeys = gameEnv.getGameObjKeys();
		String objKey;
		GameObj obj;
		while(objKeys.hasMoreElements()){
			objKey = objKeys.nextElement();
			obj = gameEnv.getGameObj(objKey);
			if(getDistanceFromCentre(obj) < ATTACKDISTANCE){
				if	(	(this.getX() - obj.getX() < 0 && direction == Direction.RIGHT) ||
						(this.getX() - obj.getX() > 0 && direction == Direction.LEFT)
					){
					obj.setState(EnemyState.STRUCK);
					return obj;
				}
			}
		}
		
		return null;
	}
	
	private void setDirectionByInput(){
		if(keyLeft)
			direction = Direction.LEFT;
		else if(keyRight)
			direction = Direction.RIGHT;
	}
	
	@Override
	public void update() {
		if(playerState == PlayerState.MOVE){
			if(direction == Direction.LEFT)
				this.x -= Player.MOVEMENTSPEED;
			else if(direction == Direction.RIGHT)
				this.x += Player.MOVEMENTSPEED;
		} else if(playerState == PlayerState.DODGE){
			if(direction == Direction.LEFT)
				this.x -= Player.MOVEMENTDODGESPEED;
			else if(direction == Direction.RIGHT)
				this.x += Player.MOVEMENTDODGESPEED;
		} else if(playerState == PlayerState.ATTACK){
			updateAttackFrame();
		}
		updateState();

	}

	@Override
	public void draw(Graphics graphics) {
		//graphics.drawImage(this.displayImage, (int)x, (int)y, (ImageObserver) this);

		if(displayAnim != null)
			displayAnim.updateFrame(System.currentTimeMillis());
		else
			return;
		
		
		if(direction == Direction.RIGHT)
			//graphics.drawImage(displayAnim.getSheet(), (int)x, (int)y, displayAnim.getSheetWidth(), displayAnim.getSheetHeight(), null);
			graphics.drawImage(	displayAnim.getSheet(),
					(int)x,(int)y,
					(int)x+displayAnim.getFrameWidth(),(int)y+displayAnim.getFrameHeight(),
					displayAnim.getDrawX(),displayAnim.getDrawY(),
					displayAnim.getDrawX()+displayAnim.getFrameWidth(),displayAnim.getDrawY()+displayAnim.getFrameHeight(),
					null);
		else if(direction == Direction.LEFT)
			//graphics.drawImage(displayAnim.getSheet(), (int)x, (int)y, -1*displayAnim.getSheetWidth(), displayAnim.getSheetHeight(), null);
			graphics.drawImage(	displayAnim.getSheet(),
					(int)x+displayAnim.getFrameWidth(),(int)y,
					(int)x,(int)y+displayAnim.getFrameHeight(),
					displayAnim.getDrawX(),displayAnim.getDrawY(),
					displayAnim.getDrawX()+displayAnim.getFrameWidth(),displayAnim.getDrawY()+displayAnim.getFrameHeight(),
					null);
			
			
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		switch(key.getKeyCode()){
			case Player.KEYLEFT:
				keyLeft=true;
				break;
			case Player.KEYRIGHT:
				keyRight=true;
				break;
			case Player.KEYDODGE:
				keyDodge=true;
				break;
			case Player.KEYATTACK:
				keyAttack=true;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		switch(key.getKeyCode()){
			case Player.KEYLEFT:
				keyLeft=false;
				break;
			case Player.KEYRIGHT:
				keyRight=false;
				break;
			case Player.KEYDODGE:
				keyDodge=false;
				break;
			case Player.KEYATTACK:
				keyAttack=false;
				break;
		}
	}

	@Override
	public void mousePressed() {}

	@Override
	public void mouseReleased() {}

	@Override
	public void mouseDragged() {}
}
