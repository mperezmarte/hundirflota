package flota;
import java.util.Random;
import java.util.Scanner;

import mipktMetodos.Usuario;

public class Main {
	public static int[] tamanoBarcos = {4, 3, 3};
	public static Barco[] barcos = new Barco[tamanoBarcos.length];
	public static Coordenada coordenadasBarcos;
	public static Coordenada coordenadasBarcosUsuario;
	public static int ejeHorizontal = 10;
	public static int ejeVertical = 10;
	public static char[][] mallaUsuario = new char[10][10];
	public static int[][] mallaBarcos = new int[10][10];
	public static boolean gameLoop = true;
	public static int misilesIniciales = 15;
	public static Scanner scanner = new Scanner(System.in);	
	public static Random random = new Random();
	
	public static void inicializarMallaBarcos() {
		for(int i = 0; i<mallaBarcos.length; i++) {
			for(int j = 0; j<mallaBarcos[0].length; j++) {
				mallaBarcos[i][j] = '*';
			}
		}
		
		generarPosicionesBarcos();
		
	}
	

	

	public static void main(String[] args) {
		inicializarMallaBarcos();
	
		
		String input;
		char[] inputArray;
		
		int x = 0;
		int y = 0;
		boolean asignadoX = false;
		boolean asignadoY = false;
		
		int pos0, pos1 ;
		while(gameLoop) {
			if(misilesIniciales==0) {
				System.out.println("Te has quedado sin misiles.\nFin del juego");
				gameLoop = false;
			
			}
			
			System.out.println("Introduce la coordenada: (Ej. A3)");

			input = scanner.nextLine().toUpperCase();
			
			if(input.length() != 2) {
				System.out.println("El valor introducido no es valido");
				System.out.println("Error Longitud");
				continue;
			}
			
			inputArray = input.toCharArray();
			
			pos0 = (int)inputArray[0];
			pos1 = (int)inputArray[1];
			
			if(pos0 >= 65 && pos0 <= 74) {
				x = pos0 - 65;
				asignadoX = true;
			}
			
			else if (pos0 >= 48 && pos0 <= 57) {
				y = pos0 - 48;
				asignadoY = true;
			}
			
			else {
				System.out.println("El valor introducido no es valido");
				continue;
			}
			
			if(pos1>= 65 && pos1 <= 74) {
				if(asignadoX) {
					System.out.println("El valor introducido no es valido");		
					continue;
				}
				x = pos1 - 65;
			}
						
			else if (pos1 >= 48 && pos1 <= 57) {
				if(asignadoY) {
					System.out.println("El valor introducido no es valido");
					continue;
				}
				y = pos1 - 48;
			}
			
			else {
				System.out.println("El valor introducido no es valido");
				continue;
			}
			
			coordenadaBarcoUsuario(x,y);
			if(impactoBarco(x, y) )
			misilesIniciales--;
		
			
		}
	}
	
	private static void coordenadaBarcoUsuario(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	public static void generarPosicionesBarcos() {

		
		for(int i = 0; i<tamanoBarcos.length; i++) {
			boolean valido = false;
			int x = 0, y = 0;
			boolean horizontal = true;
			int tamano = tamanoBarcos[i];
			
			while(!valido) {
				x = generarNumeroAleatorio();
				y = generarNumeroAleatorio();
				horizontal = generarPosicion();
				if(estaDisponible(x, y, horizontal, tamano)) {
					valido = true;
				}
			}
			
			for(int j = 0; j<tamano; j++) {

				if(horizontal) {
				mallaBarcos[x+j][y] = '1';
					
				}
				else {
				mallaBarcos[x][y+j] = '1';
				}
			}
			Coordenada coordenada = new Coordenada(x, y);	
			System.out.println(coordenada);
			System.out.println("X: " + x + " Y: " + y);
		 
		
		}
		imprimirMalla(mallaBarcos);
		
		
		
		
	}


	
	private static Coordenada coordenada(Object x, Object y) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean estaDisponible(int x, int y, boolean horizontal, int tamano) {
		for(int i = 0; i<tamano; i++) {
			
			//Comrpobamos que se generan los barcos aleatoriamente y no se pisan en ninguna casilla.
			//System.out.println("x = " + x + " y = " + y); 
			if(horizontal) {
				if(x+i >= ejeHorizontal || mallaBarcos[x+i][y] == 'b')
					return false;
			}
			else {
				if(y+i >= ejeVertical || mallaBarcos[x][y+i] == 'b')
					return false;
			}
		}
		return true;
	}
	
	public static int generarNumeroAleatorio() {
		return random.nextInt(10);
	}
	
	public static boolean generarPosicion() {
		// Generamos un boolean que se considera horizontal
		// si devuelve true o vertical si devuelve false
		return random.nextBoolean();
	}
	
	public static void imprimirMalla(int[][] malla) {
		for(int i = 0; i<malla.length; i++) {
			for(int j = 0; j<malla[i].length; j++) {
				if(malla[i][j] == '1') {
					System.out.print(" 1 ");
				}
				else {
					System.out.print(" 0 ");
				}
			}
			System.out.print("\n");
		}
	}
	

	public static boolean impactoBarco(int x, int y ) {
		if(mallaBarcos[x][y] == 'b') {
			return true;
		}
		else {
			 return false;
		}
		
		
	}

	
	
	

}
