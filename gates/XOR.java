package gates;

public class XOR extends Gate{

	public XOR(GatePosition pos) {
		super("XOR", 2,pos);
		
	}
	
	public XOR(){
		super("XOR",2);
	}
	
	public void operate(){
		if(this.input[0]==this.input[1]){this.output=0;return;}
		this.output=1;
	}

}
