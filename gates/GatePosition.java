package gates;

public class GatePosition {

	private int inColumn,inRow,inRow2,outColumn,outRow;
	
	public GatePosition(){
		this.inColumn=0;
		this.inRow=0;
		this.inRow2=0;
		this.outColumn=0;
		this.outRow=0;
	}
	public GatePosition(int inColumn,int inRow,int inRow2,int outColumn,int outRow){
		this.inColumn=inColumn;
		this.inRow=inRow;
		this.inRow2=inRow2;
		this.outColumn=outColumn;
		this.outRow=outRow;
	}
	
	public void setInColumn(int inColumn){
		this.inColumn=inColumn;
	}
	
	public void setInRow(int inRow){
		this.inRow=inRow;
	}
	
	public void setInRow2(int inRow2){
		this.inRow2=inRow2;
	}
	
	public void setOutColumn(int outColumn){
		this.outColumn=outColumn;
	}
	
	public void setOutRow(int outRow){
		this.outRow=outRow;
	}
	
	
	public int getInColumn(){
		return this.inColumn;
	}
	
	public int getInRow(){
		return this.inRow;
	}
	
	public int getInRow2(){
		return this.inRow2;
	}
	
	public int getOutColumn(){
		return this.outColumn;
	}
	
	public int getOutRow(){
		return this.outRow;
	}
}
