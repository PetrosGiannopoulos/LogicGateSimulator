package graphics;

import gates.*;
import graphics.Gui.Mode;
import io.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JPanel;

import circuit.Board;


public class CircuitPanel extends JPanel{

	private Board board;
	private LinkedList<Gate> gates;
	private Gate selectedGate;
	private LinkedList<GateImage> gates_g = new LinkedList<>();
	private int selectedGateIndex,selectedGateImageIndex,gateCounter;
	public CircuitPanel(Board board){
		this.board=board;
		this.selectedGateIndex = -1;
		this.selectedGateImageIndex = -1;
		addMouseListener(new PressIt());
	
	}
	
	public void paintComponent(Graphics g){
		
		//draw BackGround
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth()-1, getHeight()-1);
		
		
		//draw Switches
		for(int i=0;i<board.getCircuit().getRows();i++){
			if(board.getCircuit().isSwitchEnable(i)){
				g.drawImage(board.getCircuit().getSwitches()[i].getImage(), 20, 22+i*50, null);
				if(board.getCircuit().getSwitches()[i].isSelected()){
					g.setColor(Color.red);
					g.drawRect(20, 22+i*50, board.getCircuit().getSwitches()[i].getWidth(), board.getCircuit().getSwitches()[i].getHeight());
					g.setColor(Color.black);
				}
			}
		}
		
		//draw Pins
		for(int i=0;i<board.getCircuit().getColumns();i++){
			g.setColor(Color.darkGray);
			g.fillRect(82+150*i, 0, 50, getHeight());
			g.setColor(Color.black);
			g.drawRect(82+150*i, 0, 50, getHeight());
			for(int j=0;j<board.getCircuit().getRows();j++){
				
				g.fillRect(board.getCircuit().getPinLoc()[i][j].getX(),board.getCircuit().getPinLoc()[i][j].getY(),15,15);
				//draw Enabled Pin Border
				if(board.getCircuit().getPinEnable()[i][j].isActive()){
					g.setColor(Color.red);
					g.drawRect(board.getCircuit().getPinLoc()[i][j].getX(),board.getCircuit().getPinLoc()[i][j].getY(),15,15);
					g.setColor(Color.black);
					if(board.getCircuit().isProbeAttached(i, j)){
						g.setColor(Color.white);
						g.drawString(""+board.getCircuit().getPins()[i][j].getValue(), board.getCircuit().getPinLoc()[i][j].getX()+7, board.getCircuit().getPinLoc()[i][j].getY()+13);
						g.setColor(Color.black);
					}
				}
			}
		}
		
		
		
		
		//draw Gates
		g.setColor(Color.black);
		gateCounter = 0;
		for(GateImage gate : gates_g){
			
			g.drawImage(gate.getImage(), gate.getX(), gate.getY(),60,60, null);
			//draw Pin-Gate Lines
			
			//from input(s) to gate
			if(gate.getInputNum()>1){
				g.drawLine(board.getCircuit().getPinLoc()[gate.getPos().getInColumn()][gate.getPos().getInRow()].getX()+7,board.getCircuit().getPinLoc()[gate.getPos().getInColumn()][gate.getPos().getInRow()].getY()+7,gate.getX(),gate.getCenterY()-18);
				g.drawLine(board.getCircuit().getPinLoc()[gate.getPos().getInColumn()][gate.getPos().getInRow2()].getX()+7,board.getCircuit().getPinLoc()[gate.getPos().getInColumn()][gate.getPos().getInRow2()].getY()+8,gate.getX(),gate.getCenterY()+12);
			}
			else{
				g.drawLine(board.getCircuit().getPinLoc()[gate.getPos().getInColumn()][gate.getPos().getInRow()].getX()+7,board.getCircuit().getPinLoc()[gate.getPos().getInColumn()][gate.getPos().getInRow()].getY()+7,gate.getX(),gate.getCenterY()-3);
			}
			//from gate to output
			g.drawLine(gate.getX()+gate.getWidth()-5,gate.getCenterY()-2,board.getCircuit().getPinLoc()[gate.getPos().getOutColumn()][gate.getPos().getOutRow()].getX(),board.getCircuit().getPinLoc()[gate.getPos().getOutColumn()][gate.getPos().getOutRow()].getY()+7);
			
			gateCounter++;
		}
		
		gateCounter = 0;
		for(GateImage gate : gates_g){
			
			//draw selected Gate border
			if(gate.isSelected()){
				g.setColor(Color.green);
				g.drawRect(gate.getX(), gate.getY(), 60, 60);
				g.setColor(Color.black);
				selectedGateImageIndex = gateCounter;
			}
			gateCounter++;
		}
		
		
		
		//draw Leds
		for(int i=0;i<board.getCircuit().getRows();i++){
			if(board.getCircuit().isLedEnable(i)){
				//g.drawImage(board.getCircuit().getLeds()[i].getImage(), board.getCircuit().getPinLoc()[board.getCircuit().getColumns()-1][i].getX(), 12+i*50,40,40, null);
				g.drawImage(board.getCircuit().getLeds()[i].getImage(), board.getCircuit().getLeds()[i].getX(), 12+i*50,40,40, null);
				if(board.getCircuit().getLeds()[i].isSelected()){
					g.setColor(Color.blue);
					//g.drawRect(board.getCircuit().getPinLoc()[board.getCircuit().getColumns()-1][i].getX(), 12+i*50,40,40);
					g.drawRect(board.getCircuit().getLeds()[i].getX(), 12+i*50,40,40);
					g.setColor(Color.black);
				}
			}
		}
		
		//draw BackGround Border
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		
	}

	private class PressIt extends MouseAdapter{
		int counter = 0;
		int inputCol,inputRow,inputRow2,outputCol,outputRow;
		int gateX,gateY;
		boolean hitRow,hitRow2,hitColumn;
		boolean error = false;
		public void mousePressed(MouseEvent e){
			
			//press switches
			if(e.getX()<board.getCircuit().getPinLoc()[0][0].getX()){
				for(int i=0;i<board.getCircuit().getRows();i++){
					int sX = board.getCircuit().getSwitches()[i].getX();
					int sY = board.getCircuit().getSwitches()[i].getY();
					int sW = board.getCircuit().getSwitches()[i].getWidth();
					int sH = board.getCircuit().getSwitches()[i].getHeight();
					if((e.getX()>sX && e.getX()<sX+sW)&&(e.getY()>sY && e.getY()<sY+sH)){
						if(board.getCircuit().isSwitchEnable(i)){
							board.getCircuit().getSwitches()[i].setValue(1-board.getCircuit().getSwitchValue(i));
							board.getCircuit().setValue(0, i, board.getCircuit().getSwitchValue(i));
							//play sound
							board.getCircuit().getSwitches()[i].playSound();
						}
					}
				}
			}
			//attach gate inputs and output to pins 
			if(board.getGui().getMode() == Mode.Gate){
				counter++;
				if(counter == 1){
					hitRow = false;
					for(int i=0;i<board.getCircuit().getColumns();i++){
						for(int j=0;j<board.getCircuit().getRows();j++){
							int gX = board.getCircuit().getPinLoc()[i][j].getX();
							int gY = board.getCircuit().getPinLoc()[i][j].getY();
							if((e.getX()>gX && e.getX()<(gX+15)) && (e.getY()>gY && e.getY()<(gY+15))){
								inputCol = i;
								inputRow = j;
								board.getCircuit().setEnable(inputCol, inputRow, true);
								gateX = gX;
								gateY = gY;
								hitRow=true;
							}
						}
					}
					if(hitRow==false){counter=0;}
					else{hitRow=false;}
				}
				else if(counter==2){
					hitRow2=false;
					exit0:
					for(int i=0;i<board.getCircuit().getColumns();i++){
						for(int j=0;j<board.getCircuit().getRows();j++){
							int gX = board.getCircuit().getPinLoc()[i][j].getX();
							int gY = board.getCircuit().getPinLoc()[i][j].getY();
							if((e.getX()>gX && e.getX()<(gX+15)) && (e.getY()>gY && e.getY()<(gY+15))){
								if(j!=inputRow && i==inputCol){
									
									
									inputRow2 = j;
									board.getCircuit().setEnable(inputCol, inputRow2, true);
								}
								else{
									error = true;
									break exit0;
								}
								hitRow2 = true;
							}
						}
					}
					if(hitRow2==false){counter=1;}
					else{hitRow2=false;}
					if(error){counter=1;error=false;}
				}
				else if(counter==3){
					hitColumn=false;
					exit1:
					for(int i=0;i<board.getCircuit().getColumns();i++){
						for(int j=0;j<board.getCircuit().getRows();j++){
							int gX = board.getCircuit().getPinLoc()[i][j].getX();
							int gY = board.getCircuit().getPinLoc()[i][j].getY();
							if((e.getX()>gX && e.getX()<(gX+15)) && (e.getY()>gY && e.getY()<(gY+15))){
								if(i>inputCol){
									//Enable Pins
									outputCol=i;
									outputRow=j;
									board.getCircuit().setEnable(outputCol, outputRow, true);
									
									//Set Gate Position
									board.getGui().getGate().setPos(new GatePosition(inputCol,inputRow,inputRow2,outputCol,outputRow));
									board.getCircuit().addGate(board.getGui().getGate());
									//System.out.println(board.getGui().getGate().getInputNum());
									gates_g.add(new GateImage(gateX,gY,board.getGui().getImage(),board.getGui().getGate().getPos(),board.getGui().getGate().getInputNum()));
								}
								else{
									error = true;
									break exit1;
								}
								hitColumn=true;
							}
						}
					}
					
					counter=0;
					if(hitColumn==false){counter=2;}
					else{hitColumn=false;}
					if(error){counter = 2;error = false;}
				}
			}
			else if(board.getGui().getMode() == Mode.NOTGate){
				counter++;
				if(counter == 1){
					hitRow = false;
					for(int i=0;i<board.getCircuit().getColumns();i++){
						for(int j=0;j<board.getCircuit().getRows();j++){
							int gX = board.getCircuit().getPinLoc()[i][j].getX();
							int gY = board.getCircuit().getPinLoc()[i][j].getY();
							if((e.getX()>gX && e.getX()<(gX+15)) && (e.getY()>gY && e.getY()<(gY+15))){
								inputCol = i;
								inputRow = j;
								board.getCircuit().setEnable(inputCol, inputRow, true);
								gateX = gX;
								gateY = gY;
								hitRow=true;
							}
						}
					}
					if(hitRow==false){counter=0;}
					else{hitRow=false;}
				}
				else if(counter==2){
					hitColumn=false;
					exit2:
					for(int i=0;i<board.getCircuit().getColumns();i++){
						for(int j=0;j<board.getCircuit().getRows();j++){
							int gX = board.getCircuit().getPinLoc()[i][j].getX();
							int gY = board.getCircuit().getPinLoc()[i][j].getY();
							if((e.getX()>gX && e.getX()<(gX+15)) && (e.getY()>gY && e.getY()<(gY+15))){
								if(i>inputCol){
									//Enable Pins
									outputCol=i;
									outputRow=j;
									board.getCircuit().setEnable(outputCol, outputRow, true);
									
									//Set Gate Position
									board.getGui().getGate().setPos(new GatePosition(inputCol,inputRow,inputRow,outputCol,outputRow));
									board.getCircuit().addGate(board.getGui().getGate());
									//System.out.println(board.getGui().getGate().getInputNum());
									gates_g.add(new GateImage(gateX,gY,board.getGui().getImage(),board.getGui().getGate().getPos(),board.getGui().getGate().getInputNum()));
								}
								else{
									error = true;
									break exit2;
								}
								hitColumn=true;
							}
						}
					}
					
					counter=0;
					if(hitColumn==false){counter=1;}
					else{hitColumn=false;}
					if(error){counter = 1;error = false;}
				}
				
			}
			else if(board.getGui().getMode() == Mode.Switch){
				//place switch to column-0 pins
				for(int j=0;j<board.getCircuit().getRows();j++){
					int gX = board.getCircuit().getPinLoc()[0][j].getX();
					int gY = board.getCircuit().getPinLoc()[0][j].getY();
					if((e.getX()>gX && e.getX()<(gX+15)) && (e.getY()>gY && e.getY()<(gY+15))){
						board.getCircuit().addSwitch(j);
					}
				}
			}
			else if(board.getGui().getMode() == Mode.Led){
				//place switch to column-MAX pins
				for(int j=0;j<board.getCircuit().getRows();j++){
					int gX = board.getCircuit().getPinLoc()[board.getCircuit().getColumns()-1][j].getX();
					int gY = board.getCircuit().getPinLoc()[board.getCircuit().getColumns()-1][j].getY();
					if((e.getX()>gX && e.getX()<(gX+15)) && (e.getY()>gY && e.getY()<(gY+15))){
						board.getCircuit().addLed(j);
					}
				}
			}
			else if(board.getGui().getMode() == Mode.Probe){
				//place probe to pins
				for(int i=0;i<board.getCircuit().getColumns();i++){
					for(int j=0;j<board.getCircuit().getRows();j++){
						int gX = board.getCircuit().getPinLoc()[i][j].getX();
						int gY = board.getCircuit().getPinLoc()[i][j].getY();
						if((e.getX()>gX && e.getX()<(gX+15)) && (e.getY()>gY && e.getY()<(gY+15))){
							board.getCircuit().setProbeValue(i, j, (board.getCircuit().isProbeAttached(i,j))?false:true);
							
						}
					}
				}
			}
			else if(board.getGui().getMode() == Mode.Pin){
				//Enable/Disable pins
				for(int i=0;i<board.getCircuit().getColumns();i++){
					for(int j=0;j<board.getCircuit().getRows();j++){
						int gX = board.getCircuit().getPinLoc()[i][j].getX();
						int gY = board.getCircuit().getPinLoc()[i][j].getY();
						if((e.getX()>gX && e.getX()<(gX+15)) && (e.getY()>gY && e.getY()<(gY+15))){
							board.getCircuit().setEnable(i, j, (board.getCircuit().getPinEnable()[i][j].isActive())?false:true);
						}
					}
				}
			}
			//Select Gate from GateImage list
			for(int i=0;i<gates_g.size();i++){
				//Deselect previous gates/In other words one instance a time
				gates_g.get(i).setSelected(false);
				//Change select status
				if((e.getX()>gates_g.get(i).getX() && e.getX()<gates_g.get(i).getX()+gates_g.get(i).getWidth()) && (e.getY()>gates_g.get(i).getY() && e.getY()<gates_g.get(i).getY()+gates_g.get(i).getHeight())){
					gates_g.get(i).setSelected(gates_g.get(i).isSelected()? false:true);
					if(gates_g.get(i).isSelected()){
						gates = board.getCircuit().getGates();
						
						for(int k=0;k<gates.size();k++){
							if((gates.get(k).getPos().getInColumn() == gates_g.get(i).getPos().getInColumn()) 
									&& (gates.get(k).getPos().getInRow() == gates_g.get(i).getPos().getInRow()) 
									&& (gates.get(k).getPos().getInRow2() == gates_g.get(i).getPos().getInRow2())){
								selectedGate = gates.get(k);
								selectedGateIndex = k;
							}
						}
					}
				}
			}
			//Select Switch
			for(int i=0;i<board.getCircuit().getSwitches().length;i++){
				Switch swich = board.getCircuit().getSwitches()[i];  
				swich.setSelected(false);
				if((e.getX()>swich.getX() && e.getX()<swich.getX()+swich.getWidth()) && (e.getY()>swich.getY() && e.getY()<swich.getY()+swich.getHeight())){
					swich.setSelected((swich.isSelected())?false:true);
					
				}
			}
			//Select Led
			for(int i=0;i<board.getCircuit().getLeds().length;i++){
				Led led = board.getCircuit().getLeds()[i];
				led.setSelected(false);
				if((e.getX()>led.getX() && e.getX()<led.getX()+led.getWidth()) && (e.getY()>led.getY() && e.getY()<led.getY()+led.getHeight())){
					led.setSelected((led.isSelected())?false:true);
				}
			}
			
		}
	}
	
	public void deleteGateImage(GatePosition pos){
		for(int i=0;i<gates_g.size();i++){
			GateImage g = gates_g.get(i);
			if(g.getPos().getInColumn() == pos.getInColumn() && g.getPos().getInRow() == pos.getInRow() && g.getPos().getInRow2() == pos.getInRow2() && g.getPos().getOutColumn() == pos.getOutColumn() && g.getPos().getOutRow() == pos.getOutRow()){
					gates_g.remove(i);
			}
			
		}
	}
	
	public GateImage getGateImage(Gate g){
		if(g == null)return null;
		for(GateImage gate : gates_g){
			if(gate.getPos().getInColumn() == g.getPos().getInColumn() && gate.getPos().getInRow() == g.getPos().getInRow() && gate.getPos().getInRow2() == g.getPos().getInRow2() && gate.getPos().getOutColumn() == g.getPos().getOutColumn() && gate.getPos().getOutRow() == g.getPos().getOutRow()){
				return gate;
			}
		}
		
		return null;
		
	}
	
	public LinkedList<GateImage> getGateImage(){
		return this.gates_g;
	}
	
	public LinkedList<Gate> getGates(){
		return this.gates;
	}
	
	public Gate getSelectedGate(){
		return this.selectedGate;
	}
	
	public int getSelectedGateIndex(){
		return this.selectedGateIndex;
	}
	
	public int getSelectedGateImageIndex(){
		return this.selectedGateImageIndex;
	}
}
