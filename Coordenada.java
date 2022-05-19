package flota;

public class Coordenada {
	private int x;
	private int y;
	
	public Coordenada(int x, int y) {
		this.setX(x);
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean equals(Coordenada coordenada) {
		if(this.getX() == coordenada.getX() && this.y == coordenada.getY())
			return true;
		else
			return false;
	}

	public int setX(int x) {
		return x;
	}
}