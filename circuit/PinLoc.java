package circuit;

public class PinLoc {

	int x,y;
	int translateX = 100,translateY = 25;
	
	
	public PinLoc(int x,int y){
		this.x=x+translateX;
		this.y=y+translateY;
	}
	
	public void setX(int x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
}
