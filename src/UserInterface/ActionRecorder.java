package UserInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

import Board.BoardAlgorithm;
import Board.BoardBase;
import Type.Move;
import Type.Pix;

public class ActionRecorder {
	private Move moveAction;
	private final int maxMoveCount = 20;
	public int calculateDepth=4;
	private int moveCount = 0;
	private Pix click;
	private int countClick;
	private int maxCountClick=10;
	private int mousex, mousey;
	public BoardAlgorithm board;
	public boolean mouseDown=false;
	private Button[][] chess = new Button[6][6];

	private Button blackMove, pinkMove;
	private Button load, save;
	private Button undo;
	private Button changeColor, restart, alwaysShow, autoRun;
	private Button freeRun;
	private Button changeDepth;

	ArrayList<Button> buttons;

	public ActionRecorder(BoardAlgorithm currentBoard) {
		board = currentBoard;
		initButtons();
	}
	
	public void initButtons() {
		int a = Painter.unitWidth;
		int b = Painter.chessRadius;
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 6; y++) {
				chess[x][y] = new Button(2 * a + x * a - b + a, 2 * a + y * a - b + a, 2 * b, 2 * b);
				chess[x][y].title="#"+x+""+y;
			}
		}
		buttons = new ArrayList<Button>();

		// 计算棋子格子
		changeDepth = new Button(3 * a, 11 * a, a, a / 2, "Depth ", new Color(35, 200, 211));
		buttons.add(changeDepth);
		// 改变深度按钮
		freeRun = new Button(3 * a, 11 * a + a / 2, a, a / 2, "FreeRun", new Color(238, 201, 0));
		buttons.add(freeRun);
		// 自由移动按钮
		blackMove = new Button(4 * a, 11 * a, a, a, "B-Move", Color.DARK_GRAY);
		buttons.add(blackMove);
		// 黑方移动按钮
		pinkMove = new Button(5 * a, 11 * a, a, a, "P-Move", new Color(255, 106, 106));
		buttons.add(pinkMove);
		// 红方移动按钮
		load = new Button(6 * a, 11 * a, a, a / 2, "Load", Color.GRAY);
		buttons.add(load);
		// 加载按钮
		save = new Button(6 * a, 11 * a + a / 2, a, a / 2, "Save", Color.PINK);
		buttons.add(save);
		// 保存按钮
		undo = new Button((int) (7 * a), 11 * a, a, a, "Undo " + board.history().size(), new Color(171, 212, 22));
		buttons.add(undo);
		// 回到上一步按钮
		changeColor = new Button((int) (8 * a), 11 * a, a, a / 2, "Change", new Color(155, 140, 244));
		buttons.add(changeColor);
		// 旋转棋盘按钮
		restart = new Button((int) (8 * a), (int) (11.5 * a), a, a / 2, "Restart", new Color(67, 205, 128));
		buttons.add(restart);
		// 重启按钮
		alwaysShow = new Button((int) (9 * a), (int) (11.5 * a), a, a / 2, "Show", new Color(244, 167, 28));
		buttons.add(alwaysShow);
		// 显示走法按钮
		autoRun = new Button((int) (9 * a), (int) (11 * a), a, a / 2, "AutoRun", new Color(101, 122, 251));
		buttons.add(autoRun);

		buttons.trimToSize();		
	}

	public void paint(Graphics g) {
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 6; y++) {
				chess[x][y].statue = board.get(x, y);
				if(click!=null) {
					if(x==click.x&&y==click.y) {
						LinkedList<Move> l=board.getStepList(board.get(click.x,click.y));
						while(!l.isEmpty()) {
							Move m=l.pop();
							if(m.from.equals(click))
								chess[m.to.x][m.to.y].paintMouse(g);
						}
						
						chess[x][y].paintRect(g);
					countClick++;
					if(countClick<=maxCountClick)
						chess[x][y].statue=BoardBase.NO_CHESS;
					if(countClick>2*maxCountClick)
						countClick=0;
					}
				}
				
				if(moveAction!=null) {
					if(x==moveAction.from.x&&y==moveAction.from.y) {
						chess[x][y].statue=BoardBase.NO_CHESS;
					}else if(x==moveAction.to.x&&y==moveAction.to.y) {
						Pix p=this.getNextPosition();
						Button animation=new Button(p.x,p.y,2*Painter.chessRadius,2*Painter.chessRadius);
						animation.statue=chess[x][y].statue;
						chess[x][y].statue=BoardBase.NO_CHESS;
						animation.paintChess(g);
					}				
				}
				
				chess[x][y].paintChess(g);
			}
		}

		undo.title= ("Undo " + board.history().size());
		changeDepth.title="Depth "+calculateDepth;
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).paintButton(g);
		}

		Button detected=detectButton(mousex,mousey);
		if(detected!=null) {
			if(mouseDown)
				detected.paintOnClick(g);
			detected.paintMouse(g);
		}
	}
	
	public Button detectButton(int mouseX,int mouseY) {
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 6; y++) {
				if (chess[x][y].contains(mousex, mousey))
					return chess[x][y];
			}
		}
		int mousebuttonx = mouseX;
		int mousebuttony = mouseY;

		for (int i = 0; i < buttons.size(); i++) {
			if (buttons.get(i).contains(mousebuttonx, mousebuttony))
				return buttons.get(i);
		}
		return null;
	}

	private boolean check(Move move) {
		LinkedList<Move> a = board.getStepList(board.get(move.from.x, move.from.y));
		//生成所有走法
		for(Move b:a)if(b.equals(move))return true;
		//如果走法合理返回真
		return false;
	}
	
	public void addMove(Move move) {
		if(check(move)) {
		moveAction = move;
		board.move(move);
		}
		clickFinish();
	}

	public void addClick(int x, int y) {
		if(board.get(x, y)!=BoardBase.NO_CHESS)
		click = new Pix(x, y);
		countClick=0;
	}

	public void clickFinish() {
		click = null;
		countClick=0;
	}

	public Pix getClick() {
		return click;
	}

	public Pix getMoveStart() {
		if (moveAction == null)
			return null;
		return moveAction.from;
	}

	public void setMouse(int x, int y) {
		mousex = x;
		mousey = y;
	}

	public Pix getNextPosition() {
		moveCount++;
		
		int xS=chess[moveAction.from.x][moveAction.from.y].x;
		int yS=chess[moveAction.from.x][moveAction.from.y].y;
		int xE=chess[moveAction.to.x][moveAction.to.y].x;
		int yE=chess[moveAction.to.x][moveAction.to.y].y;
		
		Pix p = new Pix(xS+(xE-xS)*moveCount/maxMoveCount, yS+(yE-yS)*moveCount/maxMoveCount);

		if (moveCount == maxMoveCount) {
			moveCount = 0;
			moveAction = null;
		}
		
		return p;
	}
}
