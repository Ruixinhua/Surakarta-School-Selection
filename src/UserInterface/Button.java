package UserInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Button {
	public int x;
	public int y;
	public int width;
	public int height;
	public String title;
	public int statue;
	public Color color;
	public Button(int buttonX,int buttonY,int buttonWidth,int buttonheight,String buttonTitle,Color buttonColor) {
		x=buttonX;
		y=buttonY;
		width=buttonWidth;
		height=buttonheight;
		title=buttonTitle;
		color=buttonColor;
	}
	public Button(int buttonX,int buttonY,int buttonWidth,int buttonheight) {
		x=buttonX;
		y=buttonY;
		width=buttonWidth;
		height=buttonheight;
	}
	public boolean contains(int pixX,int pixY) {
		if(pixX>=x&&pixX<=x+width)
			if(pixY>=y&&pixY<=y+height) {
				return true;
			}
		return false;
	}

	public void paintChess(Graphics g) {
		switch (statue) {
			case 0:return;
			case 1:g.setColor(new Color(255,89,89));break;
			case 2:g.setColor(Color.GRAY);break;
		}
		g.fillOval(x, y, width, height);
		g.setColor(Color.white);
		g.fillOval(x+width/2, y+height/4, width/4, height/4);
	}
	public void paintButton(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		g.setFont(new Font("Verdana", 10, 10));
		g.setColor(Color.WHITE);
		g.drawString(title, x + Painter.unitWidth/8, y + Painter.unitWidth/3);
	}
	public void paintMouse(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawOval(x-Painter.chessRadius, y-Painter.chessRadius, width+2*Painter.chessRadius, height+2*Painter.chessRadius);
	}
	public void paintRect(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect(x, y, width, height);
	}
	public void paintOnClick(Graphics g) {
		if(color==null)
			return;
		Color initialColor=color;
		color=color.darker();
		paintButton(g);
		color=initialColor;		
	}
}
