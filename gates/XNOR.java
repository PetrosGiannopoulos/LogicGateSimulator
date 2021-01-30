package gates;

public class XNOR extends Gate{

	public XNOR(GatePosition pos) {
		super("XNOR", 2, pos);
		
	}
	
	public XNOR(){
		super("XNOR",2);
	}
	
	public void operate(){
		if(this.input[0]!=this.input[1]){this.output=0;return;}
		this.output=1;
	}

}
