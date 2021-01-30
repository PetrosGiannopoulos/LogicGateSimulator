import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import graphics.Gui;
import circuit.Board;


public class Main {

	
	private Gui gui;
	private Board board;
	public static void main(String[] args){
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Main();
	}
	
	public Main(){
		//for later expansion
		gui = new Gui();
		
		board = new Board(gui);
		gui.setBoard(board);
		
	}
}
