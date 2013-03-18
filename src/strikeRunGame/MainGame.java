package strikeRunGame;
import gameDev.GameAssets;
import gameDev.GameEnv;
import gameDev.GameObj;
import gameDev.GameWindow;

import java.util.Enumeration;

import strikeRunGame.BasicEnemy.EnemyState;

public class MainGame{
	static int ENEMYLIMIT = 3;
	
	static Thread gameThread;
	static GameAssets gameAssets;
	static GameEnv gameEnv;
	static GameWindow gameWindow;
	
	static int numOfEnemies;
	
	public static void main(String[] args) {
		
		gameAssets = new GameAssets();
		gameAssets.addAnimData("playerStand","assets/player/stand.png", 10,5);
		gameAssets.addAnimData("playerRun","assets/player/run.png", 8,4);
		//gameAssets.addAnimData("playerDodge","assets/player/dodge.png", 3,6);
		gameAssets.addAnimData("playerDodge","assets/player/dodgefast.png", 1,5);
		
		gameAssets.addAnimData("enemyRun","assets/enemy/run.png", 3,5);
		gameAssets.addAnimData("enemyAttack","assets/enemy/attack.png", 6,5);
		gameAssets.addAnimData("enemyDizzy","assets/enemy/dizzy.png", 9,5);
		
		gameAssets.addAnimData("strike1","assets/strikes/strike1.png", 6,5);
		gameAssets.addAnimData("enemystrike1end","assets/enemy/strike1end.png", 2,5);
		gameAssets.addAnimData("playerstrike1end","assets/player/strike1end.png", 2,5);
		
		gameEnv = new GameEnv(gameAssets);
		gameWindow = new GameWindow(gameEnv);
		
		Player player = new Player("player",GameWindow.SCREENWIDTH/2,GameWindow.SCREENHEIGHT/2-50,gameEnv);
		gameEnv.addGameObj("player", player);
		
		gameThread = new Thread(gameWindow);
		gameThread.start();
		
		BasicEnemy enemy;
		int spawnPoint;
		
		numOfEnemies = 0;
		while(true){
			if(numOfEnemies < ENEMYLIMIT){
				spawnPoint = (Math.random()*2 > 1)?1:0;
				enemy = new BasicEnemy("enemy"+numOfEnemies,GameWindow.SCREENWIDTH*spawnPoint+Math.random()*50,GameWindow.SCREENHEIGHT/2-40,gameEnv);
				gameEnv.addGameObj("enemy"+numOfEnemies, enemy);
				
				numOfEnemies++;
			}
			
			removeDeadEnemies();
		}
		
	}
	
	public static void removeDeadEnemies(){
		Enumeration<String> objKeys = gameEnv.getGameObjKeys();
		GameObj gameObj;
		String key;
		while(objKeys.hasMoreElements()){
			key = objKeys.nextElement();
			gameObj = gameEnv.getGameObj(key);
			if(gameObj.getState() == EnemyState.DEAD){
				gameObj.setX(0);
				gameObj.setState(EnemyState.MOVE);
			}
		}
	}

}