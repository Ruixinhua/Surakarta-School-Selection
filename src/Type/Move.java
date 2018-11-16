package Type;

public class Move {
	public Pix from;
	public Pix to;
	public Move(int fromx,int fromy,int tox,int toy) {
		from=new Pix(fromx,fromy);
		to=new Pix(tox,toy);
	}
	public Move(Pix from,Pix to) {
		this.from=from;
		this.to=to;
	}
	public boolean equals(Move compare) {
		return from.equals(compare.from)&&to.equals(compare.to);
	}
}
