package Type;

public class Pix {
	public int x;
	public int y;
	public Pix(int X,int Y) {
		x=X;
		y=Y;
	}
	public boolean equals(Pix compare) {
		return x==compare.x&&y==compare.y;
	}
}
