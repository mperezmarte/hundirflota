package flota;

import java.util.Arrays;

public class FlotaNueva {

	public static void main(String[] args) {
		char[][] matriz = new char [10][10];
		
		inicializarMalla(matriz);
		rellenarMatriz(matriz);
		
	

	}
	
	public static void rellenarMatriz(char[][] matriz) {
		System.out.print(" \n");
		for(int i=0; i< matriz.length; i++) {
			for(int j=0; j< matriz[i].length; j++) {
				matriz[i][j]= '*';
				matriz[5][3]='b';
				matriz[5][4]='b';
				matriz[5][5]='b';
				System.out.print(" ");
				System.out.print(matriz[i][j]);
				System.out.print(" ");
				
			}
			System.out.print("\n");
			
		
		}	
		
	}	
	
	
	

	public static void inicializarMalla(char[][] matriz) {
		System.out.print("\n"); 
		for(int i = 0; i<matriz.length; i++) {
			for(int j = 0; j<matriz[i].length; j++) {
				System.out.print(" ");
				System.out.print(matriz[i][j]='*');
				System.out.print(" ");
				}
			System.out.print("\n");
			
			}
		
		
	}
	
	
	
	
	
	


}
