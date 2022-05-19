package flota;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ClaseHundirFlota {
	
	private static final int VALOR_AGUA = 5;
	private static final int NUM_BARCOS = 1;

	/********************************************************************************************************
	 * M�todo principal: 
	 * 	- es el primer m�todo al que llama la m�quina virtual para ejecutar nuestro programa
	 * 	- programamos en el el funcionamiento general del programa.  Intentamos en la medida de lo posible
	 * 		aislar todas las funciones que podamos identificar como principales
	 * @param args
	 *******************************************************************************************************/
	
	public static void main(String[] args) {
		/*
		 * Estructura de los datos:
		 * 	Por cada jugador mantengo una matriz donde almaceno tanto sus barcos como los disparos que el otro
		 * 	le ha realizado.  La declaro como matriz de enteros porque necesito considerar varios valores 
		 * 	distintos, por lo que no me puede servir un dato boolean.
		 * 
		 * 	La tabla de barcos de cada jugador contiene un contador por cada uno de los barcos.  Cuando un tiro
		 * 	da sobre un barco en particular, decremento su valor.  Cuando el valor est� a 0, entonces asumo que 
		 * 	el barco ha sido hundido.
		 */
			
		int[][] matrizUsuario = inicializaMatrizDisparos();
		int[] barcosUsuario = inicializaTablaBarcos();
		
		int[][] matrizMaquina = inicializaMatrizDisparos();
		int[] barcosMaquina = inicializaTablaBarcos();
		
		
		/*
		 * Variables auxiliares que me sirven para llevar adelante la ejecuci�n del m�todo
		 */
		// Variable que utilizo para llevar el control del programa.  Cuando se ponga a cierto tendr� que terminar
		boolean haFinalizado = false;
		// Variable que utilizo para comprobar si un tiro de alg�n jugador ha sido correcto antes de actualizar
		// la informaci�n
		boolean tiroCorrecto = false;
		// Variable donde recojo el disparo de cada jugador.  Es una tabla de dos posiciones
		int[] tiro = new int[2];
		
		/*
		 ************************************************************
		 * Inicializaci�n del juego: Poner barcos (usuario y m�quina)
		 ************************************************************
		 */
		inicializacion(matrizUsuario, matrizMaquina, barcosMaquina);
		
		/*
		 ************************************************************
		 * Bucle principal del programa: se repite el juego hasta que
		 * un jugador gana.  La variable haFinalizado recoge un valor
		 * l�gico que me indica cu�ndo ha llegado ese punto.
		 ************************************************************
		 */
		while(!haFinalizado){
		
			/*
			 ************************************************************
			 * Al principio de cada turno, tengo que mostrarle al usuario
			 * c�mo se encuentra el juego: Dibujo los paneles
			 ************************************************************
			 */
			dibujarPaneles(matrizUsuario, barcosUsuario, matrizMaquina, barcosMaquina);
			/*
			 * Mientras que no de me un disparo correcto, 
			 * le solicito un disparo correcto.
			 */
			tiroCorrecto = false;
			while(!tiroCorrecto)
			{
				/*
				 * Solicito al usuario el disparo.
				 */
				tiro = pedirCasilla("Dispare:");
			
				/*
				 * Evaluar Tiro usuario.
				 */
				tiroCorrecto = evaluarTiro(tiro, matrizUsuario);
			}
			
			/*
			 * Actualizo los datos del disparo.  Si llego aqu� ya se
			 * que ha sido un disparo correcto
			 */
			actualizaMatriz(matrizUsuario, barcosUsuario, tiro);
			/*
			 * Compruebo si ha terminado el juego el usuario
			 */
			haFinalizado = compruebaFin(barcosUsuario);
			
			/*
			 * Si el usuario ha ganado, 
			 * entonces directamente terminamos.
			 */
			if(!haFinalizado){
				/*
				 * Mientras que no de me un disparo correcto, 
				 * le solicito un disparo correcto.
				 */
				tiroCorrecto = false;
				while(!tiroCorrecto)
				{
					tiro = generaDisparoAleatorio();
					
					/*
					 * Evaluar tiro de la m�quina
					 */
					tiroCorrecto = evaluarTiro(tiro, matrizMaquina);
				}
				actualizaMatriz(matrizMaquina,barcosMaquina,tiro);
				
				haFinalizado = compruebaFin(barcosMaquina);
			}
		}
		System.out.println("Fin del juego!!!!!!");
	}

	/**********************************************************************************************
	 * Funciones principales llamadas desde el cuerpo del programa
	 **********************************************************************************************/
	
	/**
	 * Funci�n destinada a dibujar los paneles del usuario.
	 */
	private static void dibujarPaneles(int[][] matrizUsuario,
			int[] barcosUsuario, int[][] matrizMaquina, int[] barcosMaquina) {
		//TODO hay que crearlo.
	}

	/**
	 * Funci�n que una vez hemos comprobado que el tiro ha sido correcto, actualiza la informaci�n en la matriz
	 */
	private static void actualizaMatriz(
						int[][] matriz, 
						int[] barcos, 
						int[] tiro) 
	{
		int valorCasilla = matriz[tiro[0]][tiro[1]];	
		
		if(valorCasilla<VALOR_AGUA)
		{
			barcos[valorCasilla]--;			
		}
		matriz[tiro[0]][tiro[1]] = matriz[tiro[0]][tiro[1]] + NUM_BARCOS + 1;
	}

	/**
	 * Funci�n que realiza la inicializaci�n del juego.  
	 * Su funci�n es colocar los barcos del usuario y de la m�quina
	 */

	private static void inicializacion(int[][] matrizUsuario,
									int[][] matrizMaquina,
									int[] barcos) {
		
		ponerBarcosUsuario(matrizUsuario, barcos);
		ponerBarcosMaquina(matrizMaquina, barcos);
	
	}

	/**
	 * Funci�n que se encarga de situar los barcos de la m�quina
	 */
	
	private static void ponerBarcosMaquina(int[][] matrizMaquina, int[] barcos) {
		boolean esBarcoValido = false;
		int[] valoresUsuario = new int[3];
		for(int i=0;i<=1;i++) // contar 5 barcos
		{
			while(!esBarcoValido)
			{
				valoresUsuario = generaBarcoAleatorio();
				esBarcoValido = comprobarBarco(valoresUsuario[0], 
												valoresUsuario[1], 
												valoresUsuario[2]==1, 
												barcos[i], // Tama�o del barco
												matrizMaquina); // Matriz de los barcos de la m�quina
				if(esBarcoValido)
				{
					almacenaEnTabla(matrizMaquina,
									valoresUsuario[0], 
									valoresUsuario[1], 
									valoresUsuario[2]==1, 
									barcos[i], // Tama�o del barco
									i); // Valor que le pongo en sus casillas
				}
			}
			esBarcoValido = false;
		}
		
	}
	
	/**
	 * Funci�n que se encarga de situar los barcos del usuario
	 */

	private static void ponerBarcosUsuario(int[][] matrizUsuario, int[] barcos) {
		boolean esBarcoValido = false;
		int[] valoresUsuario = new int[3];
		for(int i=0;i<=1;i++) // contar 5 barcos
		{
			while(!esBarcoValido)
			{
				valoresUsuario = pedirBarcoUsuario(String.valueOf(barcos[i]) + " (barco " + (i+1) + "�)");
				esBarcoValido = comprobarBarco(valoresUsuario[0], 
												valoresUsuario[1], 
												valoresUsuario[2]==1, 
												barcos[i], 
												matrizUsuario);
				if(esBarcoValido)
				{
					almacenaEnTabla(matrizUsuario,
									valoresUsuario[0], 
									valoresUsuario[1], 
									valoresUsuario[2]==1, 
									barcos[i], // tama�o del barco
									i); //valor que le pongo en sus casillas
				}
				else
					System.out.println("Posici�n err�nea");
			}
			esBarcoValido = false;

		}
		
	}
	
	/**
	 * Funci�n que se encarga de pedir un barco al usuario.
	 */
	
	private static int[] pedirBarcoUsuario(String tamanhoBarco) {
		int[] valoresBarco = new int[3];
		int[] casilla = new int[2];
		char[] posicion = new char[1];
		
		casilla = pedirCasilla("Introduzca la primera casilla del barco de tama�o " + tamanhoBarco + " (Ej: A4):");
		valoresBarco[0] = casilla[0];
		valoresBarco[1] = casilla[1];
		
		posicion[0] = 'a';
		while(Character.toUpperCase(posicion[0])!='V' && Character.toUpperCase(posicion[0])!='H')
		{
			// pedir posicion
			System.out.println("�En qu� posici�n quiere poner su barco? (V/H):");
			posicion = leeCaracteresTeclado(1);
		
			if(Character.toUpperCase(posicion[0]) == 'H')
			{
				valoresBarco[2] = 1;
			}
			else
			{
				if(Character.toUpperCase(posicion[0]) == 'V')
					valoresBarco[2] = 0;
			}
 		}
		
		return valoresBarco;
	}

	/**
	 * Funci�n que se encarga de pedir una casilla al usuario.
	 * Se utiliza bien para pedir un disparo o bien para pedir la primera posici�n del barco.
	 */
	
	private static int[] pedirCasilla(String cadenaMensaje){
		int[] valores = new int[2];
		boolean encontrada = false;
		char[] casilla = new char [2];
		char[] letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
		
		System.out.println(cadenaMensaje);
		casilla = leeCaracteresTeclado(2);
		
		//transformar la fila
		for(int i=0;i<8 && !encontrada;i++)
		{
			if(Character.toUpperCase(casilla[0])==letras[i]){
				encontrada = true;
				valores[0] = i;
			}
		}
		if(!encontrada)
			valores[0]=-1;
		/***************************************************************
		 * La transformaci�n anterior se puede hacer de esta otra manera
		 * utilizando el caracter como valor num�rico.
		 * 
		 * valores[0] = Character.toUpperCase(casilla[0]) - 'A';
		 ***************************************************************/
		// Columna transformada
		valores[1] = Character.getNumericValue(casilla[1]);
		
		return valores;
	}
	
	/**
	 * Funci�n que se encarga de actualizar la matriz una vez 
	 * que el tiro ha sido evaluado como v�lido 
	 */
	
	private static void almacenaEnTabla(int[][] matriz, 
										int fila, 
										int columna, 
										boolean posicion, 
										int tamanho,
										int valorBarco)
	{
		if(posicion) // si el barco est� en horizontal
		{
			for(int i=columna;i<=columna+tamanho-1;i++)
			{
				matriz[fila][i] = valorBarco;
			}
		}
		else // (si no) ==> si el barco est� en vertical
		{
			for(int i=fila;i<=fila+tamanho-1;i++)
			{
				matriz[i][columna] = valorBarco;
					
			}
		}
	}
	
	/********************************************************************************************************
	 * Funciones para que la m�quina genere valores aleatorios
	 ********************************************************************************************************/
	/**
	 * Funci�n que genera un disparo aleatorio.  Devuelve un array de dos posiciones de entero.  
	 * La primera casilla es la fila.
	 * La segunda casilla es la columna.
	 */
	private static int[] generaDisparoAleatorio(){
		int[] valores = new int[2];
		
		valores[0] = (int)Math.round(7*Math.random());
		valores[1] = (int)Math.round(7*Math.random());
				
		return valores;
	}
	/**
	 * Funci�n que genera un barco aleatorio.  Devuelve un array de tres posiciones de entero.  
	 * La primera casilla es la fila.
	 * La segunda casilla es la columna.
	 * La tercera casilla es una valor 0 � 1 que indica (vertical/horizontal).
	 */
	private static int[] generaBarcoAleatorio() {
		int[] valores = new int[3];
		
		valores[0] = (int)Math.round(7*Math.random());
		valores[1] = (int)Math.round(7*Math.random());
		valores[2] = (int)Math.round(Math.random());
				
		return valores;
	}
	
	/********************************************************************************************************
	 * Funciones que comprueban restricciones
	 ********************************************************************************************************/
	
	/**
	 *  Funci�n que comprueba si hemos finalizado el juego
	 * 	Se reduce a una b�squeda en un array.  Busco el primer valor distinto de 0
	 */
	private static boolean compruebaFin(int[] barcos)
	{
		for(int i=0;i<NUM_BARCOS;i++)
		{
			if(barcos[i]!=0)
				return false;
		}
		return true;
	}
	
	/**
	 * Funci�n que comprueba si una casilla, dadas por su fila y su columna, est� dentro del tablero.
	 */
	
	private static boolean compruebaCasillaErronea(int fila, int columna){
		return ((fila < 0 || fila > 7 || columna < 0 || columna > 7));
		
	}
	
	/**
	 * Funci�n que comprueba si un barco que un jugador desea situar en alg�n punto del tablero es correcto.
	 */
	
	private static boolean comprobarBarco(int fila, 
											int columna, 
											boolean posicion, // True-H; False-V
											int tamanho,
											int[][] matriz)
	{
		// Se comprueba si la casilla inicial est� dentro del tablero
		if(compruebaCasillaErronea(fila, columna))
		{
			return false;
		}
		// Si el barco est� en horizontal, compruebo que no se sale por la derecha del tablero
		if(posicion && (columna+tamanho-1)>7)
		{
			return false;
		}
		// Si el barco est� en vertical, compruebo que no se sale por debajo del tablero
		if(!posicion && (fila+tamanho-1)>7)
		{
			return false;
		}
		
		// Si llego a este punto, es que el barco cabe dentro del tablero
		// Me queda por comprobar que el barco no se pisa con otros que ya est�n puestos
		
		if(posicion) // si el barco est� en horizontal
		{
			for(int i=columna;i<=columna+tamanho-1;i++)
			{
				if(matriz[fila][i]!=VALOR_AGUA)
				{
					return false;
				}
			}
		}
		else // (si no) ==> si el barco est� en vertical
		{
			for(int i=fila;i<=fila+tamanho-1;i++)
			{
				if(matriz[i][columna]!=VALOR_AGUA)
				{
					return false;
				}
			}
		}
		// Si llego a este punto es que todas las comprobaciones se han cumplido.  Devuelvo cierto (true)
		return true;
	}
	
	/**
	 * Funci�n que evalua si un tiro de alg�n jugador es correcto, es decir, es una casilla dentro del tablero
	 * y no ha sido disparada previamente por ese mismo jugador
	 */
	
	private static boolean evaluarTiro(int[] valores, int[][] matriz) {
		// Casilla dentro del tablero
		
		// Casilla repetida??
		return (
				(!compruebaCasillaErronea(valores[0], valores[1]))
				&&
				(matriz[valores[0]][valores[1]]<=VALOR_AGUA)
			   );
	}
	
	/*********************************************************************************************************
	 * Funciones inicializaci�n
	 *********************************************************************************************************/
	/**
	 * Funci�n que sirve para crear un panel vac�o.  Inicialmente, rellena todas las casillas con el valor
	 * de "agua no disparada" (VALOR_AGUA)
	 */
	private static int[][] inicializaMatrizDisparos(){
		int[][] matriz = new int[8][8];
		for(int i=0;i<=7;i++)
		{
			for(int j=0;j<=7;j++)
			{
				matriz[i][j] = VALOR_AGUA;
			}
		}
		return matriz;
	}
	
	/**
	 * Funci�n que sirve para crear la tabla de barcos de cada jugador.  Inicializa la tabla con tantas posiciones
	 * como barcos hay, y en cada una de ellas escribe el tama�o del barco correspondiente.
	 */
	private static int[] inicializaTablaBarcos(){
		int[] tablaBarcos = {5,4,3,3,2};
		return tablaBarcos;
	}
	
	/*********************************************************************************************************
	 * Funciones auxiliares
	 *********************************************************************************************************/
	/**
	 * Lee un n�mero de caracteres escritos desde el teclado
	 * El par�metro numCaracteres es el n�mero de caracteres que queremos leer
	 * El resultado se devuelve en un array de ese mismo n�mero de posiciones
	 */
	public static char[] leeCaracteresTeclado(int numCaracteres){
		InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader (isr);
		String cadena="";
		char[] resultado = new char[numCaracteres];
		try {
			while(cadena.length()<numCaracteres){
				cadena = br.readLine();
				if(cadena.length()<numCaracteres)
					System.out.println("Se espera un texto de " + 
										numCaracteres);
				for(int i=0;i<numCaracteres 
								&& cadena.length()>=numCaracteres;i++)
					resultado[i]=cadena.charAt(i);
			}
		} catch (IOException e) {
			System.out.println("Error en la lectura del teclado.");
		} 
		
		return resultado;
	}
}
