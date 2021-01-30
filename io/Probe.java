package io;

public class Probe {

	boolean value;
	
	public Probe(boolean value){
		this.value=value;
	}
	
	public void setValue(boolean value){
		this.value=value;
	}
	
	public boolean getValue(){
		return this.value;
	}
}
