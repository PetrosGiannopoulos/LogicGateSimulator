package circuit;

public class Pin {

	int value;
	public Pin(int value){
		this.value=value;
	}
	
	public void setValue(int value){
		this.value=value;
	}
	
	public int getValue(){
		return this.value;
	}
	
}
