package gates;

public class OR extends Gate{

	public OR(GatePosition pos) {
		super("OR", 2,pos);
		
	}
	
	public OR(){
		super("OR",2);
	}
	
	public void operate(){
		if(this.input[0]==0 && this.input[1]==0){this.output=0;return;}
		this.output=1;
	}

}
