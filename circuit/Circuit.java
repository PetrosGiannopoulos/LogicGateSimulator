package circuit;

import gates.*;
import io.*;

import java.util.Comparator;
import java.util.LinkedList;


public class Circuit {

	private Pin[][] pins;
	private PinLoc[][] pinLocs;
	private PinEnable[][] pinEnable;
	private Switch[] swiches;
	private SwitchEnable[] swichEnable;
	private Led[] leds;
	private LedEnable[] ledEnable;
	private Probe[][] probes;
	private int columns,rows;
	private LinkedList<Gate> gates = new LinkedList<>();
	
	public Circuit(int columns,int rows){
		this.columns=columns;
		this.rows=rows;
		this.pins = new Pin[columns][rows];
		this.pinLocs = new PinLoc[columns][rows];
		this.pinEnable = new PinEnable[columns][rows];
		this.swiches = new Switch[rows];
		this.swichEnable = new SwitchEnable[rows];
		this.leds = new Led[rows];
		this.ledEnable = new LedEnable[rows];
		this.probes = new Probe[columns][rows];
		initPins(0);
		initPinLocs();
		initPinEnable();
		initSwitches();
		initLeds();
		initProbes();
	}
	
	public void addGate(Gate gate){
		//gates.add(new Gate(gate.getType(), gate.getInputNum(), gate.getPos()));
		//Make new instance and add it to circuit then sort gates by input column for left to right calculation
		switch(gate.getType()){
			case "AND": gates.add(new AND(gate.getPos()));break;
			case "NAND": gates.add(new NAND(gate.getPos()));break;
			case "OR": gates.add(new OR(gate.getPos()));break;
			case "NOR": gates.add(new NOR(gate.getPos()));break;
			case "XOR": gates.add(new XOR(gate.getPos()));break;
			case "XNOR": gates.add(new XNOR(gate.getPos()));break;
			case "NOT": gates.add(new NOT(gate.getPos()));break;
		}
		
		Comparator<Gate> comp = new Comparator<Gate>() {

			@Override
			public int compare(Gate g1, Gate g2) {
				// TODO Auto-generated method stub
				if(g1.getPos().getInColumn()<g2.getPos().getInColumn()){return -1;}
				else if(g1.getPos().getInColumn()>g2.getPos().getInColumn()){return 1;}
				else{return 0;}
			}
		};
		gates.sort(comp);
		/*for(int i=0;i<gates.size();i++){
			System.out.println("inCol: "+gates.get(i).getPos().getInColumn()+"|inRow: "+gates.get(i).getPos().getInRow()+"|inRow2: "+gates.get(i).getPos().getInRow2()+"|type: "+gates.get(i).getType());
		}
		System.out.println("-----------");*/
	}
	
	public void deleteGate(GatePosition pos){
		for(Gate g : gates){
			if(g.getPos().getInColumn() == pos.getInColumn() && g.getPos().getInRow()==pos.getInRow() && g.getPos().getInRow2()==pos.getInRow2() && g.getPos().getOutColumn() == pos.getOutColumn() && g.getPos().getOutRow()==pos.getOutRow()){
				gates.remove(g);
			}
		}
	}
	
	public void addSwitch(int row){
		if(getPinEnable()[0][row].isActive()){
			swichEnable[row].setActive(true);
			swiches[row].setSelected(false);
		}
	}
	
	public void addLed(int row){
		if(getPinEnable()[columns-1][row].isActive()){
			ledEnable[row].setActive(true);
			leds[row].setSelected(false);
		}
	}
	
	public void run(){
		
		for(Gate g : gates){
			
			g.setInput(0, pins[g.getPos().getInColumn()][g.getPos().getInRow()].getValue());
			//System.out.print("input0: "+g.getInput(0)+"|");
			if(g.getInput().length==2){
				g.setInput(1, pins[g.getPos().getInColumn()][g.getPos().getInRow2()].getValue());
				//System.out.print("input1: "+g.getInput(1)+"|");
			}
			g.operate();//"execute" each gate at circuit
			//System.out.println("output: "+g.getOutput());
			//pins[g.getPos().getOutColumn()][g.getPos().getOutRow()].setValue(g.getOutput());
			this.setValue(g.getPos().getOutColumn(), g.getPos().getOutRow(), g.getOutput());
		}
		
		for(int i=0;i<rows;i++){
			if(getPinEnable()[columns-1][i].isActive()){
				leds[i].setState(pins[columns-1][i].getValue());
			}
		}
	}
	
	public void initPins(int value){
		for(int i=0;i<columns;i++){
			for(int j=0;j<rows;j++){
				pins[i][j] = new Pin(value);
			}
		}
	}
	
	public void initPinLocs(){
		for(int i=0;i<columns;i++){
			for(int j=0;j<rows;j++){
				pinLocs[i][j] = new PinLoc(i*150,j*50);
			}
		}
	}
	
	public void initPinEnable(){
		for(int i=0;i<columns;i++){
			for(int j=0;j<rows;j++){
				pinEnable[i][j] = new PinEnable(false);
			}
		}
	}
	
	public void initSwitches(){
		for(int i=0;i<rows;i++){
			swiches[i] = new Switch(0, 20, 22+i*50);
			swichEnable[i] = new SwitchEnable(false);
		}
	}
	
	public void initLeds(){
		for(int i=0;i<rows;i++){
			leds[i] = new Led(pinLocs[columns-1][i].getX(),22+i*50,0);
			ledEnable[i] = new LedEnable(false);
		}
	}
	
	public void initProbes(){
		for(int i=0;i<columns;i++){
			for(int j=0;j<rows;j++){
				probes[i][j] = new Probe(false);
			}
		}
	}
	
	public void printPins(){
		for(int i=0;i<columns;i++){
			System.out.println("Column:"+i);
			for(int j=0;j<rows;j++){
				System.out.println("Row: "+j+"| val:"+pins[i][j].getValue());
			}
		}
	}
	
	public void setValue(int column,int row,int value){
		this.pins[column][row].setValue(value);
	}
	
	public void setEnable(int column,int row,boolean active){
		this.pinEnable[column][row].setActive(active);
	}
	
	public void setSwitchEnable(int row,boolean active){
		this.swichEnable[row].setActive(active);
	}
	
	public void setLedEnable(int row,boolean active){
		this.ledEnable[row].setActive(active);
	}
	
	public void setProbeValue(int column,int row,boolean value){
		this.probes[column][row].setValue(value);
	}
	
	public void turnSwitchOn(int row){
		swiches[row].setValue(1);
	}
	
	public void turnSwitchOff(int row){
		swiches[row].setValue(0);
	}
	
	public int getValue(int column,int row){
		return this.pins[column][row].getValue();
	}
	
	public int getSwitchValue(int row){
		return this.swiches[row].getValue();
	}
	
	public boolean isSwitchEnable(int row){
		return this.swichEnable[row].isActive();
	}
	
	public int getLedValue(int row){
		return this.leds[row].getState();
	}
	
	public boolean isLedEnable(int row){
		return this.ledEnable[row].isActive();
	}
	
	public boolean isProbeAttached(int column,int row){
		return this.probes[column][row].getValue();
	}
	
	public int getColumns(){
		return this.columns;
	}
	
	public int getRows(){
		return this.rows;
	}
	
	public Pin[][] getPins(){
		return this.pins;
	}
	
	public PinLoc[][] getPinLoc(){
		return this.pinLocs;
	}
	
	public PinEnable[][] getPinEnable(){
		return this.pinEnable;
	}
	
	public LinkedList<Gate> getGates(){
		return this.gates;
	}
	
	public Switch[] getSwitches(){
		return this.swiches;
	}
	
	public Led[] getLeds(){
		return this.leds;
	}
	
}
