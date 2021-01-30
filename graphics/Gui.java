package graphics;

import gates.AND;
import gates.Gate;
import gates.GatePosition;
import gates.NAND;
import gates.NOR;
import gates.NOT;
import gates.OR;
import gates.XNOR;
import gates.XOR;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import circuit.Board;

public class Gui{

	private JFrame frame;
	private CircuitPanel circuitPanel;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private boolean isPainting = true;
	private Board board;
	private Image img;
	
	private CustomButton andButton,nandButton,orButton,norButton,xorButton,xnorButton,notButton;
	private CustomButton switchButton,ledButton,probeButton,pinButton;
	private Gate gate;
	
	private JLabel gateLabel,inRowLabel,inRow2Label,outRowLabel;
	private JComboBox gateComboBox,inRowComboBox,inRow2ComboBox,outRowComboBox;
	private CustomDialog infoDialog;
	
	//enumaration
	private Mode mode;
	public Gui(){
		
		frame = new JFrame();
		frame.addWindowListener(new CloseWindowAndExit());
		frame.setSize(new Dimension(800,625));
		frame.setTitle("Logic Gate Simulator");
		frame.setResizable(false);
		frame.setVisible(true);	
		
		
		//add ContentPane
		frame.setContentPane(contentPane = new JPanel());
		contentPane.setBackground(Color.white);
		contentPane.setLayout(null);
		
		//JFileMenuBar
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		menuBar.setVisible(true);
		//fileMenu
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem newBoard = new JMenuItem("New Board");
		newBoard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				CustomDialog newBoardDialog = new CustomDialog("New Board",200,170);
				
				newBoardDialog.setLayout(null);
				
				JLabel columnLabel = new JLabel("Columns: ");
				columnLabel.reshape(10, 20, 50, 20);
				newBoardDialog.add(columnLabel);
				
				JComboBox columnComboBox = new JComboBox();
				columnComboBox.reshape(80, 20, 100, 20);
				for(int i=2;i<5;i++){
					columnComboBox.addItem(""+i);
				}
				newBoardDialog.add(columnComboBox);
				
				JLabel rowLabel = new JLabel("Rows: ");
				rowLabel.reshape(10, 50, 50, 20);
				newBoardDialog.add(rowLabel);
				
				JComboBox rowComboBox = new JComboBox();
				rowComboBox.reshape(80, 50, 100, 20);
				for(int i=2;i<11;i++){
					rowComboBox.addItem(""+i);
				}
				newBoardDialog.add(rowComboBox);
				
				newBoardDialog.setVisible(true);
				
				JButton okButton = new JButton("OK");
				okButton.reshape(50, 80, 50, 30);
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						board.newCircuit(Integer.parseInt(columnComboBox.getSelectedItem().toString()), Integer.parseInt(rowComboBox.getSelectedItem().toString()));
						newBoardDialog.dispose();
					}
				});
				newBoardDialog.add(okButton);
				
				
				JButton cancelButton = new JButton("Cancel");
				cancelButton.reshape(100, 80, 80, 30);
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						newBoardDialog.dispose();
					}
				});
				newBoardDialog.add(cancelButton);
				
				//board.newCircuit(columns, rows);
			}
		});
		fileMenu.add(newBoard);
		JMenuItem resetBoard = new JMenuItem("Reset Board");
		resetBoard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				board.resetCircuit();
				
			}
		});
		fileMenu.add(resetBoard);
		JMenuItem loadCircuit = new JMenuItem("Load Circuit");
		loadCircuit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FileDialog dialog = new FileDialog(frame,"Load a circuit...",FileDialog.LOAD);
				dialog.setVisible(true);
				//board.loadCircuit(dialog.getFile());
				board.loadCircuit(dialog.getFiles()[0].getAbsolutePath());
			}
		});
		fileMenu.add(loadCircuit);
		JMenuItem saveCircuit = new JMenuItem("Save Circuit");
		fileMenu.add(saveCircuit);
		saveCircuit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FileDialog dialog = new FileDialog(frame,"Save circuit...",FileDialog.SAVE);
				dialog.setVisible(true);
				board.saveCircuit(dialog.getFiles()[0].getAbsolutePath());
			}
		});
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		fileMenu.add(exit);
		
		JMenu runMenu = new JMenu("Run");
		menuBar.add(runMenu);
		JMenuItem runCircuit = new JMenuItem("Run Circuit");
		runCircuit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				board.getCircuit().run();
				board.getCircuit().printPins();
			}
		});
		runMenu.add(runCircuit);
		
		JMenu gateMenu = new JMenu("Gate");
		menuBar.add(gateMenu);
		JMenuItem editGate = new JMenuItem("Edit Gate");
		editGate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CustomDialog editGateDialog = new CustomDialog("Edit Selected Gate",230,220);
				
				editGateDialog.setLayout(null);
				
				gateLabel = new JLabel("Choose Gate: ");
				gateLabel.reshape(10, 20, 100, 20);
				editGateDialog.add(gateLabel);
				
				gateComboBox = new JComboBox();
				gateComboBox.reshape(110, 20, 100, 20);
				gateComboBox.addItem("AND");
				gateComboBox.addItem("NAND");
				gateComboBox.addItem("OR");
				gateComboBox.addItem("NOR");
				gateComboBox.addItem("XOR");
				gateComboBox.addItem("XNOR");
				gateComboBox.addItem("NOT");
				gateComboBox.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						if(e.getItem().equals("NOT")){
							inRow2ComboBox.setVisible(false);
							inRow2Label.setVisible(false);
							inRow2ComboBox.setSelectedItem(inRowComboBox.getSelectedItem());
						}
						else{
							inRow2ComboBox.setVisible(true);
							inRow2Label.setVisible(true);
						}
					}
				});
				
				editGateDialog.add(gateComboBox);
				
				inRowLabel = new JLabel("Choose inputRow: ");
				inRowLabel.reshape(10, 50, 100, 20);
				editGateDialog.add(inRowLabel);
				
				inRowComboBox = new JComboBox();
				inRowComboBox.reshape(110, 50, 100, 20);
				for(int i=0;i<board.getCircuit().getRows();i++){
					inRowComboBox.addItem(""+i);
				}
				editGateDialog.add(inRowComboBox);
				
				inRow2Label = new JLabel("Choose inputRow2: ");
				inRow2Label.reshape(10,80,100,20);
				editGateDialog.add(inRow2Label);
				
				inRow2ComboBox = new JComboBox();
				inRow2ComboBox.reshape(110, 80, 100, 20);
				for(int i=0;i<board.getCircuit().getRows();i++){
					inRow2ComboBox.addItem(""+i);
				}
				editGateDialog.add(inRow2ComboBox);
				
				outRowLabel = new JLabel("Choose outputRow: ");
				outRowLabel.reshape(10,110,100,20);
				editGateDialog.add(outRowLabel);
				
				outRowComboBox = new JComboBox();
				outRowComboBox.reshape(110, 110, 100, 20);
				for(int i=0;i<board.getCircuit().getRows();i++){
					outRowComboBox.addItem(""+i);
				}
				editGateDialog.add(outRowComboBox);
				
				JButton okButton = new JButton("OK");
				okButton.reshape(50, 140, 50, 30);
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						Gate gate = getCircuitPanel().getSelectedGate();
						Gate newGate = null;
						int inRowItem = Integer.parseInt(inRowComboBox.getSelectedItem().toString());
						int inRow2Item = Integer.parseInt(inRow2ComboBox.getSelectedItem().toString());
						int outRowItem = Integer.parseInt(outRowComboBox.getSelectedItem().toString());
						switch(gateComboBox.getSelectedItem().toString()){
							case "AND":newGate = new AND(new GatePosition(gate.getPos().getInColumn(),inRowItem,inRow2Item,gate.getPos().getOutColumn(),outRowItem));break;
							case "NAND":newGate = new NAND(new GatePosition(gate.getPos().getInColumn(),inRowItem,inRow2Item,gate.getPos().getOutColumn(),outRowItem));break;
							case "OR":newGate = new OR(new GatePosition(gate.getPos().getInColumn(),inRowItem,inRow2Item,gate.getPos().getOutColumn(),outRowItem));break;
							case "NOR":newGate = new NOR(new GatePosition(gate.getPos().getInColumn(),inRowItem,inRow2Item,gate.getPos().getOutColumn(),outRowItem));break;
							case "XOR":newGate = new XOR(new GatePosition(gate.getPos().getInColumn(),inRowItem,inRow2Item,gate.getPos().getOutColumn(),outRowItem));break;
							case "XNOR":newGate = new XNOR(new GatePosition(gate.getPos().getInColumn(),inRowItem,inRow2Item,gate.getPos().getOutColumn(),outRowItem));break;
							case "NOT":newGate = new NOT(new GatePosition(gate.getPos().getInColumn(),inRowItem,inRow2Item,gate.getPos().getOutColumn(),outRowItem));break;
							
						}
						
						//change pins
						board.getCircuit().setEnable(gate.getPos().getInColumn(), gate.getPos().getInRow(), false);
						board.getCircuit().setEnable(gate.getPos().getInColumn(), gate.getPos().getInRow2(), false);
						board.getCircuit().setEnable(gate.getPos().getOutColumn(), gate.getPos().getOutRow(), false);
						
						board.getCircuit().setEnable(newGate.getPos().getInColumn(), newGate.getPos().getInRow(), true);
						board.getCircuit().setEnable(newGate.getPos().getInColumn(), newGate.getPos().getInRow2(), true);
						board.getCircuit().setEnable(newGate.getPos().getOutColumn(), newGate.getPos().getOutRow(), true);
						//change gate
						getCircuitPanel().getGates().set(getCircuitPanel().getSelectedGateIndex(), newGate);
						//change gate image
						GateImage gateSelectedImage = getCircuitPanel().getGateImage().get(getCircuitPanel().getSelectedGateImageIndex());
						int gX = board.getCircuit().getPinLoc()[newGate.getPos().getInColumn()][inRowItem].getX();
						int gY = board.getCircuit().getPinLoc()[newGate.getPos().getOutColumn()][outRowItem].getY();
						getCircuitPanel().getGateImage().set(getCircuitPanel().getSelectedGateImageIndex(), new GateImage(gX,gY,newGate.getType(),new GatePosition(newGate.getPos().getInColumn(),inRowItem,inRow2Item,newGate.getPos().getOutColumn(),outRowItem),newGate.getInputNum()));
						editGateDialog.dispose();
					}
				});
				editGateDialog.add(okButton);
				
				JButton cancelButton = new JButton("Cancel");
				cancelButton.reshape(100, 140, 80, 30);
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						editGateDialog.dispose();
					}
				});
				editGateDialog.add(cancelButton);
				
				editGateDialog.setLocationRelativeTo(frame);
				editGateDialog.setVisible(true);
			}
		});
		
		gateMenu.add(editGate);
		JMenuItem deleteGate = new JMenuItem("Delete Gate");
		deleteGate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Gate g = getCircuitPanel().getSelectedGate();
				GateImage g_img = getCircuitPanel().getGateImage(g);
				
				//g_img.setSelected(false);
				if(g_img != null && g_img.isSelected()){
					g_img.setSelected(false);
					board.getCircuit().setEnable(g.getPos().getInColumn(), g.getPos().getInRow(), false);
					board.getCircuit().setEnable(g.getPos().getInColumn(), g.getPos().getInRow2(), false);
					board.getCircuit().setEnable(g.getPos().getOutColumn(), g.getPos().getOutRow(), false);
					getCircuitPanel().deleteGateImage(g.getPos());
					board.getCircuit().deleteGate(g.getPos());
				}
			}
		});
		gateMenu.add(deleteGate);
		JMenuItem deleteSwitch = new JMenuItem("Delete Switch");
		deleteSwitch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				board.deleteSwitch();
			}
		});
		gateMenu.add(deleteSwitch);
		JMenuItem deleteLed = new JMenuItem("Delete Led");
		deleteLed.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				board.deleteLed();
			}
		});
		gateMenu.add(deleteLed);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		JMenuItem mainInfo = new JMenuItem("Main Info");
		mainInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				infoDialog = new CustomDialog("Main Info/Instructions",400,310){
					@Override
					public void paint(Graphics g){
						g.setColor(Color.black);
						g.fillRect(0, 0, infoDialog.getWidth(), infoDialog.getHeight());
						g.setColor(Color.green);
						//draw Gate Info
						g.drawString("----------------------------------------------------------------------------------------------", 10, 40);
						g.drawString("Gate Info", 165, 50);
						g.drawString("----------------------------------------------------------------------------------------------", 10, 60);
						g.drawString("- adding a gate to circuit is very simple just press the corresponding ",15,70);
						g.drawString("  buttons in the left area and then select the desirable input and", 15, 82);
						g.drawString("  output pins", 15, 94);
						g.drawString("- in order to edit or delete a gate simply select its icon in the circuit",15,106);
						g.drawString("  and then press the corresponding button in the Gate menu",15,118);
						//draw Switch Info 
						g.drawString("----------------------------------------------------------------------------------------------", 10, 130);
						g.drawString("Switch Info",165,140);
						g.drawString("----------------------------------------------------------------------------------------------", 10, 150);
						g.drawString("- adding a switch to circuit is a matter of selecting a pin in the first", 15, 170);
						g.drawString("  column. Be aware of having at least one gate connected to that pin", 15, 182);
						g.drawString("  before trying to add a switch", 15, 194);
						g.drawString("- deleting a switch is the exact same procedure as explained", 15, 206);
						g.drawString("  with the gates", 15, 218);
						//draw Led Info
						g.drawString("----------------------------------------------------------------------------------------------", 10, 230);
						g.drawString("Led Info",165,240);
						g.drawString("----------------------------------------------------------------------------------------------", 10, 250);
						g.drawString("- adding a led to circuit is a matter of selecting a pin in the last", 15, 260);
						g.drawString("  column. Be aware of having at least one gate connected to that pin", 15, 272);
						g.drawString("- deleting a led is the exact same procedure as explained", 15, 284);
						g.drawString("  with the gates", 15, 296);
					}
				};
				
				infoDialog.setResizable(false);
				infoDialog.setAlwaysOnTop(true);
				infoDialog.setVisible(true);
				
			}
		});
		helpMenu.add(mainInfo);
		
		//Tool Icon Buttons
		andButton = new CustomButton("ANDGate32.gif");
		andButton.reshape(10, 25, 50, 40);
		andButton.addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						img = loadImage("ANDGate.gif");
						mode = Mode.Gate;
						gate = new AND();
					}
				}
		);
		contentPane.add(andButton);
		
	    nandButton = new CustomButton("NANDGate32.gif");
		nandButton.reshape(10, 25+1*42, 50, 40);
		nandButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				img = loadImage("NANDGate.gif");
				mode = Mode.Gate;
				gate = new NAND();
			}
		});
		contentPane.add(nandButton);
		
		orButton = new CustomButton("ORGate32.gif");
		orButton.reshape(10, 25+2*42, 50, 40);
		orButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				img = loadImage("ORGate.gif");
				mode = Mode.Gate;
				gate = new OR();
			}
		});
		contentPane.add(orButton);
		
		norButton = new CustomButton("NORGate32.gif");
		norButton.reshape(10, 25+3*42, 50, 40);
		norButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				img = loadImage("NORGate.gif");
				mode = Mode.Gate;
				gate = new NOR();
			}
		});
		contentPane.add(norButton);
		
		xorButton = new CustomButton("XORGate32.gif");
		xorButton.reshape(10, 25+4*42, 50, 40);
		xorButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				img = loadImage("XORGate.gif");
				mode = Mode.Gate;
				gate = new XOR();
			}
		});
		contentPane.add(xorButton);
		
		xnorButton = new CustomButton("XNORGate32.gif");
		xnorButton.reshape(10, 25+5*42, 50, 40);
		xnorButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				img = loadImage("XNORGate.gif");
				mode = Mode.Gate;
				gate = new XNOR();
			}
		});
		contentPane.add(xnorButton);
		
		notButton = new CustomButton("NOTGate32.gif");
		notButton.reshape(10, 25+6*42, 50, 40);
		notButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				img = loadImage("NOTGate.gif");
				mode = Mode.NOTGate;
				gate = new NOT();
			}
		});
		contentPane.add(notButton);
		
		switchButton = new CustomButton("Switch32.gif");
		switchButton.reshape(10,25+7*42,50,40);
		switchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				img = loadImage("SwitchButtonH.gif");
				mode = Mode.Switch;
			}
		});
		contentPane.add(switchButton);
		
		ledButton = new CustomButton("LedOff32.gif");
		ledButton.reshape(10,25+8*42,50,40);
		ledButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				img = loadImage("LedOff.gif");
				mode = Mode.Led;
			}
		});
		contentPane.add(ledButton);
		
		probeButton = new CustomButton("Probe32.gif");
		probeButton.reshape(10, 25+9*42, 50, 40);
		probeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mode = Mode.Probe;
			}
		});
		contentPane.add(probeButton);
		
		pinButton = new CustomButton("Pin32.png");
		pinButton.reshape(10, 25+10*42, 50, 40);
		pinButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mode = Mode.Pin;
			}
		});
		contentPane.add(pinButton);
		
		//passive rendering @20ms
		render();
	}
	
	private class CloseWindowAndExit extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			System.exit(0);
		}
	}
	
	
	public void render(){
		SwingWorker worker = new SwingWorker(){

			@Override
			protected Object doInBackground() throws Exception {
				// TODO Auto-generated method stub
				
				while(isPainting){
					Thread.sleep(100);
					frame.repaint();
					frame.revalidate();
					
				}
				/*Thread.sleep(100);
				frame.repaint();
				frame.revalidate();*/
				
				return null;
			}
			
		};
		worker.execute();
		
	}
	
	public void setBoard(Board board){
		this.board=board;
		//Circuit Panel
		circuitPanel = new CircuitPanel(board);
		circuitPanel.reshape(100, 25, 670, 525);
		circuitPanel.setVisible(true);
		contentPane.add(circuitPanel);
	}
	
	public Image loadImage(String name){
		String path = (System.getProperty("user.dir")+"/imgs/gates/"+name).replace("\\", "/");
		//System.out.println(path);
		Image res_img = null;
		try {
			res_img = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res_img;
	}
	
	public Image getImage(){
		return this.img;
	}
	
	public Gate getGate(){
		return this.gate;
	}
	
	public CircuitPanel getCircuitPanel(){
		return this.circuitPanel;
	}
	
	public Mode getMode(){
		return this.mode;
	}
	
	public static enum Mode{
		Gate,NOTGate,Switch,Led,Probe,Pin
	}
	
	
}
