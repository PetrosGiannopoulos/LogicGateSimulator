package io;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

public class Switch {

	private int x,y,value;
	private Image img;
	private static Clip clip;
	private static AudioInputStream audioInputStream;
	private boolean isSelected;
	
	private static boolean isLoaded = false;
	static{
		
		loadSound();
	}
	
	public Switch(int value,int x,int y){
		this.value=value;
		this.x=x;
		this.y=y;
		this.img = loadImage("SwitchButtonH");
		this.isSelected = false;
		//loadSound();
	}
	
	public void setValue(int value){
		this.value=value;
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public void setY(int y){
		this.y=y;
	}
	
	public void setSelected(boolean isSelected){
		this.isSelected = isSelected;
	}
	
	public static void loadSound(){
		isLoaded = true;
		String path = (System.getProperty("user.dir")+"/snds/switchSnd.WAV").replace("\\", "/");
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(path));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isLoaded = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isLoaded = false;
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isLoaded = false;
		}
	}
	
	public void playSound(){
		if(isLoaded){
			if (clip.isRunning())clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public int getValue(){
		return this.value;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getWidth(){
		return this.getImage().getWidth(null);
	}
	
	public int getHeight(){
		return this.getImage().getHeight(null);
	}
	
	public Image getImage(){
		return this.img;
	}
	
	public boolean isSelected(){
		return this.isSelected;
	}
	
	public Image loadImage(String name){
		String path = (System.getProperty("user.dir")+"/imgs/gates/"+name+".gif").replace("\\", "/");
		
		Image res_img = null;
		
		try {
			res_img = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res_img;
	}
}
