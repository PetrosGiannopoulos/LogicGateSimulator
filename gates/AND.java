package gates;

public class AND extends Gate{

	public AND(GatePosition pos) {
		super("AND", 2,pos);
		
	}
	
	public AND() {
		super("AND",2);
	}

	@Override
	public void operate(){
		if(this.input[0] == 0 || this.input[1] == 0){this.output=0;return;}
		this.output=1;
		
	}

}
