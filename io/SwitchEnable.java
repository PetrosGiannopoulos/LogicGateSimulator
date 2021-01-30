package io;

public class SwitchEnable {

	private boolean active;
	
	public SwitchEnable(boolean active){
		this.active=active;
	}
	
	public void setActive(boolean active){
		this.active=active;
	}
	
	public boolean isActive(){
		return this.active;
	}
}
