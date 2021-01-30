package io;

public class LedEnable {

	private boolean active;
	
	public LedEnable(boolean active){
		this.active=active;
	}
	
	public void setActive(boolean active){
		this.active=active;
	}
	
	public boolean isActive(){
		return this.active;
	}
}
