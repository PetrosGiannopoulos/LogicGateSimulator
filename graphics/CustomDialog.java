package graphics;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JDialog;

public class CustomDialog extends JDialog{

	public CustomDialog(String title,int width,int height){
		setTitle(title);
		setSize(new Dimension(width,height));
	}
	
	
}
