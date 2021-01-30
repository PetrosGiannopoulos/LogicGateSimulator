package gates;

public class NOR extends Gate{

	
	public NOR(GatePosition pos) {
		super("NOR", 2,pos);
		
	}
	
	public NOR(){
		super("NOR",2);
	}
	
	public void operate(){
		if(this.input[0] == 1 || this.input[1] == 1){this.output=0;return;}
		this.output=1;
	}

}
