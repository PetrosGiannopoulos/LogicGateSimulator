package gates;

public class Gate {

	String type;
	int[] input;
	int output;
	int inputNum;
	GatePosition pos;
	
	public Gate(String type,int inputNum,GatePosition pos){
		this.type=type;
		this.input = new int[inputNum];
		this.inputNum=inputNum;
		this.pos=pos;
		
	}
	
	public Gate(String type,int inputNum){
		this.type=type;
		this.input = new int[inputNum];
		this.inputNum=inputNum;
	}
	
	public void setInput(int[] input){
		this.input=input;
	}
	
	public void setInput(int index,int value){
		this.input[index]=value;
	}
	
	public void setOutput(int output){
		this.output=output;
	}
	
	public void setPos(GatePosition pos){
		this.pos=pos;
	}
	
	public String getType(){
		return this.type;
	}
	
	public int[] getInput(){
		return this.input;
	}
	
	public int getInput(int index){
		return this.input[index];
	}
	
	public int getInputNum(){
		return this.inputNum;
	}
	
	public int getOutput(){
		return this.output;
	}
	
	public GatePosition getPos(){
		return this.pos;
	}
	
	public void operate(){
		
	}
}
