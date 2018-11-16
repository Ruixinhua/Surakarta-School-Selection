package UserInterface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;

public class Painter extends JComponent{
	
	private static final long serialVersionUID = 1L;
	public static int unitWidth=60;
	public static int chessRadius=15;
	
	private ActionRecorder actions;
	
	public Painter(ActionRecorder currentActions) {
		actions=currentActions;
	}
	
	public void refreshStaticVariable(int newUnitWidth) {
		unitWidth=newUnitWidth;
		chessRadius=unitWidth/4;
		actions.initButtons();
	}	
	
	public void paint(Graphics g) {
		Point mouse=super.getMousePosition();
		if(mouse!=null) {
			actions.setMouse(mouse.x, mouse.y);
		}
		
		paintBoardBackground(g);
		
		actions.paint(g);

		
	}
	
	public void paintBoardBackground(Graphics g) {
		int a=unitWidth;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3.0f));
		g.setColor(Color.DARK_GRAY);
		g.setColor(new Color(0,130,0));
		g.drawArc(a, a, 4 * a, 4 * a, 0, 270);
		g.setColor(new Color(22,211,254));
		g.drawArc(a  + a, a  + a, 2 * a, 2 * a, 0, 270);

		g.setColor(new Color(0,130,0));
		g.drawArc(5 * a  + a, a, 4 * a, 4 * a, -90, 270);
		g.setColor(new Color(22,211,254));
		g.drawArc(6 * a  + a, a  + a, 2 * a, 2 * a, -90, 270);

		g.setColor(new Color(0,130,0));
		g.drawArc(a, 5 * a  + a, 4 * a, 4 * a, 90, 270);
		g.setColor(new Color(22,211,254));
		g.drawArc(a  + a, 6 * a  + a, 2 * a, 2 * a, 90, 270);

		g.setColor(new Color(0,130,0));
		g.drawArc(5 * a  + a, 5 * a  + a, 4 * a, 4 * a, 180, 270);
		g.setColor(new Color(22,211,254));
		g.drawArc(6 * a  + a, 6 * a  + a, 2 * a, 2 * a, 180, 270);
		
		Color[] lineColor=new Color[3];
		lineColor[0]=new Color(230,230,0);
		lineColor[1]=new Color(22,211,254);
		lineColor[2]=new Color(0,130,0);

		for (int i = 0; i < 6; i++) {
			g.setColor(i>2?lineColor[5-i]:lineColor[i]);
			g.drawLine(2 * a + i * a  + a, 2 * a  + a, 2 * a + i * a  + a, 7 * a  + a);
			g.drawLine(2 * a  + a, 2 * a + i * a  + a, 7 * a  + a, 2 * a + i * a  + a);
		}
		g2.setStroke(new BasicStroke(1.0f));
	}
	

}
