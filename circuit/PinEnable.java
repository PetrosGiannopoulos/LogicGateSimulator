package circuit;

public class PinEnable {

	private boolean active;
	
	public PinEnable(boolean active){
		this.active=active;
	}
	
	public void setActive(boolean active){
		this.active=active;
	}
	
	public boolean isActive(){
		return this.active;
	}
}
