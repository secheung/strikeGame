package gameDev;

import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.Hashtable;

public class GameEnv {
	Hashtable<String,GameObj> gameObjs;
	GameAssets assets;
	
	public GameEnv(GameAssets assets){
		this.assets = assets;
		gameObjs = new Hashtable<String,GameObj>();
	}
	
	public GameObj getGameObj(String gameObjKey){
		return gameObjs.get(gameObjKey);
	}
	
	public Enumeration<String> getGameObjKeys(){
		return gameObjs.keys();
	}
	
	public GameAssets getGameAssets(){
		return assets;
	}
	
	public void addGameObj(String objKey,GameObj gameObj){
		gameObjs.put(objKey,gameObj);
	}
	
	public void removeGameObj(String objKey){
		gameObjs.remove(objKey);
	}
	
	public void update(){
		Enumeration<String> gameObjKeys = gameObjs.keys();
		GameObj buffer;
		while(gameObjKeys.hasMoreElements()){
			buffer = gameObjs.get(gameObjKeys.nextElement());
			buffer.update();
		}
	}
	
	public void keyPressed(KeyEvent key){
		Enumeration<String> gameObjKeys = gameObjs.keys();
		GameObj gameObj;
		while(gameObjKeys.hasMoreElements()){
			gameObj = gameObjs.get(gameObjKeys.nextElement());
			gameObj.keyPressed(key);
		}
	}
	
	public void keyReleased(KeyEvent key){
		Enumeration<String> gameObjKeys = gameObjs.keys();
		GameObj gameObj;
		while(gameObjKeys.hasMoreElements()){
			gameObj = gameObjs.get(gameObjKeys.nextElement());
			gameObj.keyReleased(key);
		}
	}
}
