import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import Board.BoardAlgorithm;
import UserInterface.ActionRecorder;
import UserInterface.Input;
import UserInterface.Painter;

public class Main {
	public static void main(String[] args) {

		BoardAlgorithm board = new BoardAlgorithm();
		ActionRecorder actions = new ActionRecorder(board);
		Input input = new Input(actions);
		Painter pic = new Painter(actions);
		

		JFrame window = new JFrame("Surakarta 2018.8.5");
		window.setSize(11 * Painter.unitWidth, 13 * Painter.unitWidth);
		window.setBackground(Color.white);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.getContentPane().setBackground(Color.white);
		window.add(pic);

		pic.addMouseListener(input);
		pic.addKeyListener(input);
		pic.addMouseWheelListener(input);
		pic.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) { 
                pic.refreshStaticVariable(Math.min(pic.getHeight()/13,pic.getWidth()/11));
            }
		});
		
		window.setVisible(true);

		new Timer().scheduleAtFixedRate(new TimerTask() {
			public void run() {
				pic.repaint();
			}
		}, 500, 50);
	}
}
