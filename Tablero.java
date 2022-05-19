package flota;

import java.util.Arrays;

public class Tablero {
	private Barco[][] tablero; //null es agua

	public Tablero() {
		
		crearTablero();
		int [] posicion = {2,3,4};
		
	}
	
	private void crearTablero() {
		this.tablero = new Barco[10][10];
		for(int i=0; i< tablero.length; i++) {
			Arrays.fill(tablero[i], '*');
			System.out.println(Arrays.toString(tablero[i]));
		}
	}
	
}