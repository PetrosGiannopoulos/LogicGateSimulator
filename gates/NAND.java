package gates;

public class NAND extends Gate{

	public NAND(GatePosition pos) {
		super("NAND", 2,pos);
		
	}
	
	public NAND(){
		super("NAND",2);
	}
	
	public void operate(){
		if(this.input[0] == 1 && this.input[1] == 1){this.output=0;return;}
		this.output=1;
	}

}
