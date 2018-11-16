package Type;

public class Step extends Move{
	public byte type;
	public Step(int fromx, int fromy, int tox, int toy,byte chessType) {
		super(fromx, fromy, tox, toy);
		type=chessType;
	}
	public Step(Pix fromPix,Pix toPix,byte chessType) {
		super(fromPix,toPix);
		type=chessType;
	}
	public Step(Move m,byte chessType) {
		super(m.from,m.to);
		type=chessType;
	}
}
