package strikeRunGame;

import gameDev.GameEnv;
import gameDev.GameObj;
import gameDev.GameObj.Direction;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import strikeRunGame.Player.PlayerState;

public class BasicEnemy extends GameObj{
	static enum EnemyState implements ObjState{
		MOVE,
		ATTACK,
		DIZZY,
		STRUCK,
		ENDSTRIKE,
		DEAD
	}
	
	Player player;
	
	EnemyState enemyState;
	
	double MOVEMENTSPEED;
	private static final int OBJWIDTH = 80;
	private static final int OBJHEIGHT = 240;
	
	public BasicEnemy(String name, double x, double y, GameEnv gameEnv) {
		super(name, x, y, gameEnv);
		
		player = (Player) gameEnv.getGameObj("player");
		enemyState = EnemyState.MOVE;
		direction = Direction.LEFT;
		
		//this.setAnim("enemyRun");
		displayAnim = gameAssets.getAnimation("enemyRun");
		
		this.width = OBJWIDTH;
		this.height = OBJHEIGHT;
		
		MOVEMENTSPEED = 3.5*Math.random()+0.5;
	}

	@Override
	public ObjState getState() {
		return enemyState;
	}
	
	@Override
	public void setState(ObjState state) {
		this.enemyState = (EnemyState)state;
	}
	
	@Override
	protected void updateState() {
		if(enemyState == EnemyState.STRUCK){
			displayAnim = null;
			
			if(player.getState()==PlayerState.ENDSTRIKE){
				enemyState = EnemyState.ENDSTRIKE;
				//this.setAnim("enemystrike"+player.getAttackNum()+"end");
				displayAnim = gameAssets.getAnimation("enemystrike"+player.getAttackNum()+"end");
				displayAnim.resetFrameCount();
			} else if(player.getState()==PlayerState.DODGE){
				enemyState = EnemyState.DIZZY;
				//this.setAnim("enemyDizzy");
				//displayAnim = gameAssets.getAnimation("enemyDizzy");
				displayAnim = gameAssets.getCopyAnim("enemyDizzy");
				displayAnim.resetFrameCount();//<PROBLEM HERE RECREATE BY DODGING OUT OF TWO ENEMY STRIKES
			}
			
		}
		
		if(enemyState == EnemyState.ENDSTRIKE && displayAnim.getIfPlayedOnce()){
			enemyState = EnemyState.DEAD;
			displayAnim = null;
		}
		
		if(enemyState == EnemyState.DIZZY && displayAnim.getIfPlayedOnce()){
			enemyState = EnemyState.MOVE;
			//this.setAnim("enemyMove");
			displayAnim = gameAssets.getAnimation("enemyMove");
		}
		
		if(enemyState == EnemyState.MOVE && boxDetection(player) && player.getState() != PlayerState.DODGE){
			enemyState = EnemyState.ATTACK;
			//this.setAnim("enemyAttack");
			displayAnim = gameAssets.getAnimation("enemyAttack");
			displayAnim.resetFrameCount();
		}
		
		if(enemyState == EnemyState.ATTACK && displayAnim.getIfPlayedOnce()){
			enemyState = EnemyState.MOVE;
			//this.setAnim("enemyRun");
			displayAnim = gameAssets.getAnimation("enemyRun");
		}
		
		if(enemyState == EnemyState.MOVE){
			//this.setAnim("enemyRun");
			displayAnim = gameAssets.getAnimation("enemyRun");
			if(this.getX() - player.getX() < 0)
				direction = Direction.RIGHT;
			else if(this.getX() - player.getX() > 0)
				direction = Direction.LEFT;
				
		}
		
	}

	@Override
	protected void update() {
		if(enemyState == EnemyState.MOVE)
			if(direction == Direction.LEFT)
				this.x -= MOVEMENTSPEED;
			else if(direction == Direction.RIGHT)
				this.x += MOVEMENTSPEED;
		
		updateState();
			
	}

	@Override
	protected void draw(Graphics graphics) {
		//System.out.println(enemyState+" "+enemyDirection);
		
		if(displayAnim != null)
			displayAnim.updateFrame(System.currentTimeMillis());
		else
			return;
		
		
		if(direction == Direction.RIGHT)
			graphics.drawImage(	displayAnim.getSheet(),
					(int)x,(int)y,
					(int)x+displayAnim.getFrameWidth(),(int)y+displayAnim.getFrameHeight(),
					displayAnim.getDrawX(),displayAnim.getDrawY(),
					displayAnim.getDrawX()+displayAnim.getFrameWidth(),displayAnim.getDrawY()+displayAnim.getFrameHeight(),
					null);
		else if(direction == Direction.LEFT)
			graphics.drawImage(	displayAnim.getSheet(),
					(int)x+displayAnim.getFrameWidth(),(int)y,
					(int)x,(int)y+displayAnim.getFrameHeight(),
					displayAnim.getDrawX(),displayAnim.getDrawY(),
					displayAnim.getDrawX()+displayAnim.getFrameWidth(),displayAnim.getDrawY()+displayAnim.getFrameHeight(),
					null);
			
	}
	
	

	@Override
	protected void keyPressed(KeyEvent key) {}
	@Override
	protected void keyReleased(KeyEvent key) {}
	@Override
	protected void mousePressed() {}
	@Override
	protected void mouseReleased() {}
	@Override
	protected void mouseDragged() {}

}
