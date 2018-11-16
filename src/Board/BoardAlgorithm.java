package Board;

import java.util.LinkedList;

import Type.Move;

public class BoardAlgorithm extends BoardOperated{
	public static final int INF=9999;
	public byte enemy;
	public byte ally;
	
	public void setAlly(byte chessType) {
		ally = chessType;
		if(ally == BLACK_CHESS)
			enemy = PINK_CHESS;
		else
			enemy = BLACK_CHESS;
		
	}
	public void setEnemy(byte chessType) {
		enemy = chessType;
		if(enemy == BLACK_CHESS)
			ally = PINK_CHESS;
		else
			ally = BLACK_CHESS;			
	}

	public int evaluation(byte chessType) {

		int pinkMoveRange=getMoveList(PINK_CHESS).size();
		int blackMoveRange=getMoveList(BLACK_CHESS).size();
		
		int pinkScore=pinkMoveRange+cntPink*3;
		int blackScore=blackMoveRange+cntBlack*3;
		
		if(chessType==BLACK_CHESS)
			return blackScore-pinkScore;
		else
			return pinkScore-blackScore;
	}
	
	private int maxDepth;
	
	public Move doAlphaBetaMulti(byte chessType, int depth) {
		
		setAlly(chessType);
		maxDepth=depth-1;
	    
	    LinkedList<Move> l;
		    l=getStepList(chessType);
		 Move bestMove=null;
		 int maxValue=-INF;
		 
		 Move m=null;
		 for(int i=0;i<l.size();i++) {
			 m=l.pop();
			 move(m);
			 
			 int value=Minimax(maxDepth);
			 if(value>maxValue) {
				 maxValue=value;
				 bestMove=m;
			 }
			 
			 unmove();
		 }
		 return bestMove;
	}
	
	public int Minimax(int depth) {
	    int bestvalue, value;
	    
	    if(cntBlack == 0 || cntPink == 0 || depth <= 0)
	        return evaluation(ally);
	    
	    LinkedList<Move> l;
	    if(depth % 2 == 0) {
		    l = getStepList(ally);
	        bestvalue = -INF;
	    }
	    else {
	        bestvalue = INF;
		    l = getStepList(enemy);
	    	}
	    while(!l.isEmpty()) {
	    		Move m = l.remove();
	        move(m);
	        value = Minimax( depth-1);
	        unmove() ;
	        if(depth % 2 == 0) {
	        		if(value>bestvalue)
	        			bestvalue = value;
	        }
	        else {
	        		if(value<bestvalue)
	        			bestvalue = value;
	        }
	    	}
	    
	    return bestvalue;
	}
	
	public void run() {
		
	}
}
