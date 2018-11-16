package Board;

public class BoardBase extends Thread{
	//BaseBoard implements the functions operating chess directly. 
	private byte[] chess; 
	public final static byte NO_CHESS = 0;
	public final static byte PINK_CHESS = 1;
	public final static byte BLACK_CHESS = 2;
	
	public int cntPink;
	public int cntBlack;
	
	public BoardBase() {
		chess=new byte[36];
		for (int x = 0; x < 6; x++) {
			set(x, 0, BLACK_CHESS);
			set(x, 1, BLACK_CHESS);
			set(x, 2, NO_CHESS);
			set(x, 3, NO_CHESS);
			set(x, 4, PINK_CHESS);
			set(x, 5, PINK_CHESS);
		}
		cntPink = 12;
		cntBlack = 12;
	}
	public void set(int x,int y,byte v) {
		chess[6 * y + x] = v;
	}
	
	public byte get(int x,int y) {
		return chess[6 * y + x];
	}
}
