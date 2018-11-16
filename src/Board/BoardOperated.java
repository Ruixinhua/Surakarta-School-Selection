package Board;
import java.util.LinkedList;
import java.util.Stack;

import Type.Move;
import Type.Step;

public class BoardOperated extends BoardBase{
	//OperatedBoard implements the functions which let two people compete each other. 
	private Stack<Step> history;
	
	public BoardOperated() {
		super();
		history=new Stack<Step>();
	}
	
	public void unmove() {
		if(history.isEmpty())
			return;
		
		Step nowStep = history.pop();
		byte chess = nowStep.type;
		if(chess == PINK_CHESS)
			cntPink++;
		if(chess == BLACK_CHESS)
			cntBlack++;
		
		set(nowStep.from.x, nowStep.from.y, get(nowStep.to.x, nowStep.to.y));
		set(nowStep.to.x, nowStep.to.y, chess);
	}
	
	public Stack<Step> history(){
		return history;
	}
	public void changeColor() {
		for(int i = 0;i<6;i++)
			for(int j = 0; j < 3; j++) {
				byte temp = get(i, j);
				set(i, j, get(5-i, 5-j));
				set(5-i, 5-j, temp);
			}
	}
	
	public void move(Move m) {
		byte chess = get(m.to.x, m.to.y);
		Step nowStep = new Step(m, chess);
		if (chess == BLACK_CHESS) 
			cntBlack--;
		if(chess == PINK_CHESS)
			cntPink--;
		history.push(nowStep);

		set(m.to.x, m.to.y, get(m.from.x, m.from.y));
		set(m.from.x, m.from.y, NO_CHESS);
	}
	
	private byte[][][] p = new byte[4][6][3];
	private int[] lineNum=new int[4];
	public LinkedList<Move> getEatList(byte chessType){
		LinkedList<Move> e = new LinkedList<Move>();
		Stack<byte[][]> l=new Stack<byte[][]>();
		Stack<Integer> n=new Stack<Integer>();
		
		for(int k = 1;k<3;k++) {
			for(int i = 0;i<6;i++) {
				if(get(k,i)!=NO_CHESS) {
					//p[0][lineNum[0]]=new byte[3];
					p[0][lineNum[0]][0]=(byte)k;
					p[0][lineNum[0]][1]=(byte)i;
					p[0][lineNum[0]][2]=get(k,i);
					lineNum[0]++;
				}
				if(get(i,5-k)!=NO_CHESS) {
					//p[1][lineNum[1]]=new byte[3];
					p[1][lineNum[1]][0]=(byte)i;
					p[1][lineNum[1]][1]=(byte)(5-k);
					p[1][lineNum[1]][2]=get(i,5-k);
					lineNum[1]++;
				}
				if(get(5-k,5-i)!=NO_CHESS) {
					//p[2][lineNum[2]]=new byte[3];
					p[2][lineNum[2]][0]=(byte)(5-k);
					p[2][lineNum[2]][1]=(byte)(5-i);
					p[2][lineNum[2]][2]=get(5-k,5-i);
					lineNum[2]++;
				}
				if(get(5-i,k)!=NO_CHESS) {
					//p[3][lineNum[3]]=new byte[3];
					p[3][lineNum[3]][0]=(byte)(5-i);
					p[3][lineNum[3]][1]=(byte)k;
					p[3][lineNum[3]][2]=get(5-i,k);
					lineNum[3]++;
				}
			}
		for(int i=0;i<lineNum.length;i++)
			if(lineNum[i]>0) {
				l.push(p[i]);
				n.push(lineNum[i]);
				lineNum[i]=0;
			}
		if(l.isEmpty())continue;
		byte[][] firstLine=l.peek();
		int firstLineNum=n.peek();
		while(l.size()>1) {
			byte[][] thisLine=l.pop();
			int thisLineNum=n.pop();
			byte[][] nextLine=l.peek();
			int nextLineNum=n.peek();
			checkEatLine(thisLine,thisLineNum,nextLine,nextLineNum,e,chessType);
		}
		checkEatLine(l.pop(),n.pop(),firstLine,firstLineNum,e,chessType);
		}
		return e;
	}
	
	private byte[] aPoint,bPoint,mPoint;
	private void checkEatLine(byte[][] thisLine,int thisLineNum,byte[][] nextLine,int nextLineNum, LinkedList<Move> e,byte chessType) {
		aPoint=thisLine[0];bPoint=nextLine[nextLineNum-1];
		
		if(aPoint[2]!=bPoint[2]) {
				if(bPoint[2]==chessType)
				e.add(new Move(bPoint[0],bPoint[1],aPoint[0],aPoint[1]));
				if(aPoint[2]==chessType)
				e.add(new Move(aPoint[0],aPoint[1],bPoint[0],bPoint[1]));
				return;
			}
		
		if(aPoint[0]==bPoint[0]&&aPoint[1]==bPoint[1]) {
			mPoint=aPoint;
			if(thisLineNum>1)
				aPoint=thisLine[1];
			if(nextLineNum>1)
				bPoint=nextLine[nextLineNum-2];
			
			if(mPoint[2]!=bPoint[2]) {
				if(mPoint[2]==chessType)
					e.add(new Move(mPoint[0],mPoint[1],bPoint[0],bPoint[1]));
			}
			if(mPoint[2]!=aPoint[2]) {
				if(mPoint[2]==chessType)
				e.add(new Move(mPoint[0],mPoint[1],aPoint[0],aPoint[1]));
			}
		}	
	}
	
	public LinkedList<Move> getMoveList(byte chessType) {
		LinkedList<Move> e = new LinkedList<Move>();
		
		for(int x = 0;x<6;x++) {
			for(int y = 0;y<6;y++) {
				if(get(x, y) == chessType) {
					if(x>0) { 
						if(get(x-1, y) == NO_CHESS)e.add(new Move(x, y, x-1, y));
						if(y>0) if(get(x-1, y-1) == NO_CHESS)e.add(new Move(x, y, x-1, y-1));
						if(y<5) if(get(x-1, y + 1) == NO_CHESS)e.add(new Move(x, y, x-1, y + 1));
					}
					if(x<5) {
						if(get(x + 1, y) == NO_CHESS)e.add(new Move(x, y, x + 1, y));
						if(y>0) if(get(x + 1, y-1) == NO_CHESS)e.add(new Move(x, y, x + 1, y-1));
						if(y<5) if(get(x + 1, y + 1) == NO_CHESS)e.add(new Move(x, y, x + 1, y + 1));
					}
					if(y>0) if(get(x, y-1) == NO_CHESS)e.add(new Move(x, y, x, y-1));
					if(y<5) if(get(x, y + 1) == NO_CHESS)e.add(new Move(x, y, x, y + 1));
				}
			}
		}
		return e;
	}
	
	public LinkedList<Move> getStepList(byte chessType) {
		LinkedList<Move> e = getMoveList(chessType);
		LinkedList<Move> a = getEatList(chessType);
		a.addAll(e);
		return a;
	}
}
