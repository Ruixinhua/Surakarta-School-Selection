package UserInterface;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import Board.BoardAlgorithm;
import Board.BoardBase;
import Type.Move;
import Type.Pix;

public class Input implements MouseListener,MouseWheelListener,KeyListener {

	private ActionRecorder actions;
	
	public Input(ActionRecorder actionsResponsor) {
		actions=actionsResponsor;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {}

	
	public void manipulate(String command) {
		System.out.println(command);
		
		if(command.contains("#")) {
			//user click a chess
			int x=command.charAt(1)-'0';
			int y=command.charAt(2)-'0';
			Pix p=actions.getClick();
			if(p==null) {
				//move from current position
				actions.addClick(x, y);
			}else {
				//move to current position
				actions.addMove(new Move(p,new Pix(x,y)));
				actions.clickFinish();
			}
		}
		if(command.contains("Undo")) {
				actions.board.unmove();
		}
		if(command.contains("Change"))
			actions.board.changeColor();
		if(command.contains("Restart"))
			actions.board=new BoardAlgorithm();
		if(command.contains("B-Move")) {
			actions.addMove(
			actions.board.doAlphaBetaMulti(BoardBase.BLACK_CHESS, actions.calculateDepth));
		}
		if(command.contains("P-Move")) {
			actions.addMove(
			actions.board.doAlphaBetaMulti(BoardBase.PINK_CHESS, actions.calculateDepth));
		}
		if(command.contains("Depth")) {
			actions.calculateDepth++;
			actions.calculateDepth%=8;
			if(actions.calculateDepth<4)
				actions.calculateDepth=4;
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Button button=actions.detectButton(e.getX(), e.getY());
		actions.mouseDown=true;
		if(button!=null)
			manipulate(button.title);
		}

	@Override
	public void mouseReleased(MouseEvent e) {
		actions.mouseDown=false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	

	@Override
	public void mouseExited(MouseEvent e) {}
}
