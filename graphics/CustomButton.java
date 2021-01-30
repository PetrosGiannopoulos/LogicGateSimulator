package graphics;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CustomButton extends JButton{

	ImageIcon icon;
	public CustomButton(String name){
		
		String path = (System.getProperty("user.dir")+"/imgs/buttons/").replace("\\", "/");
		//System.out.println(path+name);
		this.icon = new ImageIcon(path+name);
		setIcon(this.icon);
	}
	
	public ImageIcon getIcon(){
		return this.icon;
	}
	
}
