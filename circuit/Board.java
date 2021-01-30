package circuit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import gates.*;
import graphics.GateImage;
import graphics.Gui;

public class Board {

	private Circuit circuit;
	private Gui gui;
	
	public Board(Gui gui){
		this.gui=gui;
		circuit = new Circuit(4,10);
		//build Circuit
		//String inputPath = (System.getProperty("user.dir")+"/input.txt").replace("\\", "/");
		
		//System.out.println(inputPath);
		//loadCircuit(inputPath);
		/*circuit.setValue(0, 0, 1);
		circuit.setValue(0, 1, 1);
		circuit.setValue(0, 2, 1);
		circuit.setValue(0, 3, 1);
		circuit.addGate(new AND(new GatePosition(0,0,1,0)));
		circuit.addGate(new AND(new GatePosition(0,2,1,1)));
		circuit.addGate(new XNOR(new GatePosition(1,0,2,1)));*/
		
		//circuit.run();
		//circuit.printPins();
	}
	
	public void loadCircuit(String filepath){
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filepath));
		
			String line;
			while ((line = br.readLine()) != null) {
				String[] words = line.split(",");
				switch(words[0]){
					case "BOARD":
						circuit = new Circuit(Integer.parseInt(words[1]),Integer.parseInt(words[2]));
						break;
					case "AND":
						circuit.addGate(new AND(new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]),Integer.parseInt(words[5]))));
						gui.getCircuitPanel().getGateImage().add(new GateImage(getCircuit().getPinLoc()[Integer.parseInt(words[1])][Integer.parseInt(words[2])].getX()
								,getCircuit().getPinLoc()[Integer.parseInt(words[4])][Integer.parseInt(words[5])].getY(),
								"AND",new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]),Integer.parseInt(words[5])),2));
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[2]), true);
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[3]), true);
						circuit.setEnable(Integer.parseInt(words[4]), Integer.parseInt(words[5]), true);
						break;
					case "NAND":
						circuit.addGate(new NAND(new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]),Integer.parseInt(words[5]))));
						gui.getCircuitPanel().getGateImage().add(new GateImage(getCircuit().getPinLoc()[Integer.parseInt(words[1])][Integer.parseInt(words[2])].getX()
								,getCircuit().getPinLoc()[Integer.parseInt(words[4])][Integer.parseInt(words[5])].getY(),
								"NAND",new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]),Integer.parseInt(words[5])),2));
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[2]), true);
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[3]), true);
						circuit.setEnable(Integer.parseInt(words[4]), Integer.parseInt(words[5]), true);
						break;
					case "OR":
						circuit.addGate(new OR(new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]),Integer.parseInt(words[5]))));
						gui.getCircuitPanel().getGateImage().add(new GateImage(getCircuit().getPinLoc()[Integer.parseInt(words[1])][Integer.parseInt(words[2])].getX()
								,getCircuit().getPinLoc()[Integer.parseInt(words[4])][Integer.parseInt(words[5])].getY(),
								"OR",new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]),Integer.parseInt(words[5])),2));
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[2]), true);
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[3]), true);
						circuit.setEnable(Integer.parseInt(words[4]), Integer.parseInt(words[5]), true);
						break;
					case "NOR":
						circuit.addGate(new NOR(new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]),Integer.parseInt(words[5]))));
						gui.getCircuitPanel().getGateImage().add(new GateImage(getCircuit().getPinLoc()[Integer.parseInt(words[1])][Integer.parseInt(words[2])].getX()
								,getCircuit().getPinLoc()[Integer.parseInt(words[4])][Integer.parseInt(words[5])].getY(),
								"NOR",new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]),Integer.parseInt(words[5])),2));
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[2]), true);
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[3]), true);
						circuit.setEnable(Integer.parseInt(words[4]), Integer.parseInt(words[5]), true);
						break;
					case "XOR":
						circuit.addGate(new XOR(new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]),Integer.parseInt(words[5]))));
						gui.getCircuitPanel().getGateImage().add(new GateImage(getCircuit().getPinLoc()[Integer.parseInt(words[1])][Integer.parseInt(words[2])].getX()
								,getCircuit().getPinLoc()[Integer.parseInt(words[4])][Integer.parseInt(words[5])].getY(),
								"XOR",new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]),Integer.parseInt(words[5])),2));
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[2]), true);
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[3]), true);
						circuit.setEnable(Integer.parseInt(words[4]), Integer.parseInt(words[5]), true);
						break;
					case "XNOR":
						circuit.addGate(new XNOR(new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]),Integer.parseInt(words[5]))));
						gui.getCircuitPanel().getGateImage().add(new GateImage(getCircuit().getPinLoc()[Integer.parseInt(words[1])][Integer.parseInt(words[2])].getX()
								,getCircuit().getPinLoc()[Integer.parseInt(words[4])][Integer.parseInt(words[5])].getY(),
								"XNOR",new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]),Integer.parseInt(words[5])),2));
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[2]), true);
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[3]), true);
						circuit.setEnable(Integer.parseInt(words[4]), Integer.parseInt(words[5]), true);
						break;
					case "NOT":
						circuit.addGate(new NOT(new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4]))));
						gui.getCircuitPanel().getGateImage().add(new GateImage(getCircuit().getPinLoc()[Integer.parseInt(words[1])][Integer.parseInt(words[2])].getX()
								,getCircuit().getPinLoc()[Integer.parseInt(words[3])][Integer.parseInt(words[4])].getY(),
								"NOT",new GatePosition(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[2]),Integer.parseInt(words[3]),Integer.parseInt(words[4])),1));
						circuit.setEnable(Integer.parseInt(words[1]), Integer.parseInt(words[2]), true);
						circuit.setEnable(Integer.parseInt(words[3]), Integer.parseInt(words[4]), true);
						break;
					case "SWITCH":
						circuit.addSwitch(Integer.parseInt(words[1]));
						//circuit.setValue(0, Integer.parseInt(words[2]), Integer.parseInt(words[3]));
						break;
					case "LED":
						circuit.addLed(Integer.parseInt(words[1]));
						break;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveCircuit(String filepath){
		System.out.println(filepath);
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(filepath);
			printWriter.println("BOARD,"+circuit.getColumns()+","+circuit.getRows());
			//Save gates
			for(int i=0;i<circuit.getGates().size();i++){
				Gate gate = circuit.getGates().get(i);
				if(gate.getType().equals("NOT")){
					printWriter.println(gate.getType()+","+gate.getPos().getInColumn()+","+gate.getPos().getInRow()+","+gate.getPos().getOutColumn()+","+gate.getPos().getOutRow());
				}
				else{
					printWriter.println(gate.getType()+","+gate.getPos().getInColumn()+","+gate.getPos().getInRow()+","+gate.getPos().getInRow2()+","+gate.getPos().getOutColumn()+","+gate.getPos().getOutRow());
				}
			}
			//Save Switches
			for(int i=0;i<circuit.getSwitches().length;i++){
				if(circuit.isSwitchEnable(i)){
					printWriter.println("SWITCH,"+i);
				}
			}
			//Save leds
			for(int i=0;i<circuit.getLeds().length;i++){
				if(circuit.isLedEnable(i)){
					printWriter.println("LED,"+i);
				}
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			printWriter.close();
		}
		
	}
	
	public void runCircuit(){
		circuit.run();
		circuit.printPins();
	}
	
	public void newCircuit(int columns,int rows){
		circuit = new Circuit(columns,rows);
	}
	
	public void resetCircuit(){
		circuit = new Circuit(4,10);
		gui.getCircuitPanel().getGateImage().clear();
	}
	
	public void deleteSwitch(){
		for(int i=0;i<circuit.getSwitches().length;i++){
			if(circuit.getSwitches()[i].isSelected()){
				circuit.getSwitches()[i].setSelected(false);
				circuit.setSwitchEnable(i, false);
			}
		}
	}
	
	public void deleteLed(){
		for(int i=0;i<circuit.getLeds().length;i++){
			if(circuit.getLeds()[i].isSelected()){
				circuit.getLeds()[i].setSelected(false);
				circuit.setLedEnable(i,false);
			}
		}
	}
	
	public Circuit getCircuit(){
		return this.circuit;
	}
	
	
	public Gui getGui(){
		return this.gui;
	}
	
}
