package gates;

public class NOT extends Gate{

	public NOT(GatePosition pos) {
		super("NOT", 1,pos);
		
	}
	
	public NOT(){
		super("NOT",1);
	}
	
	public void operate(){
		this.output = 1-this.input[0];
	}

	
}
