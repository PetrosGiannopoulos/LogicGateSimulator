package graphics;

import gates.GatePosition;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GateImage{

	private int x,y;
	private int translateX = 50,translateY = 5;
	private int inputNum;
	private Image img;
	private GatePosition pos;
	private boolean isSelected;
	
	public GateImage(int x,int y,Image img,GatePosition pos,int inputNum){
		this.x=x+translateX;
		this.y=y+translateY;
		this.img=img;
		this.pos=pos;
		this.inputNum=inputNum;
		this.isSelected = false;
	}
	
	public GateImage(int x,int y,String type,GatePosition pos,int inputNum){
		this.x=x+translateX;
		this.y=y+translateY;
		this.pos=pos;
		this.inputNum=inputNum;
		this.img = loadImage(type);
		this.isSelected = false;
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public void setY(int y){
		this.y=y;
	}
	
	public void setPos(GatePosition pos){
		this.pos=pos;
	}
	
	public void setSelected(boolean isSelected){
		this.isSelected = isSelected;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getCenterX(){
		return this.x+getWidth()/2;
	}
	
	public int getCenterY(){
		return this.y+getHeight()/2;
	}
	
	public int getWidth(){
		return this.img.getWidth(null);
	}
	
	public int getHeight(){
		return this.img.getHeight(null);
	}
	
	public GatePosition getPos(){
		return this.pos;
	}
	
	public int getInputNum(){
		return this.inputNum;
	}
	
	public Image getImage(){
		return this.img;
	}
	
	public boolean isSelected(){
		return this.isSelected;
	}
	
	public Image loadImage(String type){
		
		String path = (System.getProperty("user.dir")+"/imgs/gates/"+type).replace("\\", "/");
		//System.out.println(path+"Gate.gif");
		
		Image res_img = null;
		
		try {
			res_img = ImageIO.read(new File(path+"Gate.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res_img;
	}
}
