package io;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Led {

	private int x,y;
	private int translateX = 35,translateY = 0;
	private int state;
	private Image imgOn,imgOff;
	private boolean isSelected;
	
	public Led(int x,int y,int state){
		this.x=x+translateX;
		this.y=y+translateY;
		this.state=state;
		this.imgOff = loadImage("LedOff");
		this.imgOn = loadImage("LedOn");
		this.isSelected = false;
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
	
	public void setState(int state){
		this.state=state;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getWidth(){
		//return this.getImage().getWidth(null);
		return 40;
	}
	
	public int getHeight(){
		//return this.getImage().getHeight(null);
		return 40;
	}
	
	public boolean isSelected(){
		return this.isSelected;
	}
	
	public int getState(){
		return this.state;
	}
	
	
	public Image getImage(){
		if(this.state==1)return this.imgOn;
		return this.imgOff;
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
