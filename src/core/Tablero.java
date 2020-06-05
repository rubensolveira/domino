/*
 * Representa el tablero de juego, fichas que se están jugando actualmente,
 * donde los jugadores colocan las fichas en cada turno.
 * Estructura: se utilizará una doble cola (Deque).
 * Funcionalidad: insertar la ficha en su lugar correcto automáticamente,
 * visualizar, etc.
 */

package core;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Rubén Solveira Sorga
 */

public class Tablero {

	private static Deque<Ficha> fichasEnTablero;  // Lista enlazada de fichas
	private static int numFichasTablero; // Total de fichas en Tablero

	/**
	 * Constructor de la clase Tablero.
	 */
	public Tablero() {
		fichasEnTablero = new ArrayDeque<>();
		numFichasTablero = 0;
	}

	
	/**
	 * Método para insertar una ficha al principio del Tablero (lista enlazada). Si los numeros
	 * derecha e izquierda de las 2 fichas no coinciden hay que girarla para que los numeros iguales
	 * esteen conjuntos.
	 *
	 * @param ficha Nueva ficha a insertar al principio
	 */
	public void insertarFichaInicio(Ficha ficha) {
		// comprobar si hay fichas, si no da error al comprobar si hay que girar
		if (numFichasTablero > 0) {
			if (fichasEnTablero.getFirst().getNumIzq() != ficha.getNumDer()) {
				ficha.girarFicha();
			}
		}
		fichasEnTablero.addFirst(ficha);
		numFichasTablero++;
	}

	
	/**
	 * Método para insertar una ficha al final del Tablero (lista enlazada). Si los numeros derecha
	 * e izquierda de las 2 fichas no coinciden hay que girarla para que los numeros iguales esteen
	 * conjuntos.
	 *
	 * @param ficha Nueva ficha a insertar al final
	 */
	public void insertarFichaFin(Ficha ficha) {
		// Comprobar si hay fichas, si no da error al comprobar si hay que girar
		if (numFichasTablero > 0) {
			// Girar la ficha pasada por parametro, si corresponde.
			if (fichasEnTablero.getLast().getNumDer() != ficha.getNumIzq()) {
				ficha.girarFicha();
			}
		}
		fichasEnTablero.addLast(ficha);
		numFichasTablero++;
	}

	
	/**
	 * Mostrar tablero recorriendo la lista con un for normal. Muestra graficamente todas las fichas
	 * que están en el Tablero
	 */
//	public void mostrarTablero(){
//		Ficha[] fichas = new Ficha[ fichasEnTablero.size() ];
//		// Hay que usar esta funcion para pasar la lista enlazada a un array primitivo
//		fichas = fichasEnTablero.toArray(fichas);
//		
//		//String con 5 filas, contiene las filas de UNA sola ficha.
//		String[] filasUnaFicha = new String[5];
//
//
//		//String con 5 filas, concatena, añade, TODAS filas de fichas individuales
//		String filasTodasFichas[] = {"","","","",""};
//		System.out.println("-------------TABLERO-----------------");
//		
//		boolean horizontal;
//		for ( int i = 0; i < fichasEnTablero.size(); i++ ) {
//			filasUnaFicha = fichas[i].representarFicha();
//
//			horizontal = fichas[i].getNumIguales() == false ?  true : false;
//
//			for ( int j = 0; j < 5; j++ ){
//				// Pintar espacios solo en las filas 1 y 5, si la ficha es horizontal
//				if( horizontal == true  && ( j == 0 || j == 4) ){
//						filasTodasFichas[j] += "       ";
//				}
//				filasTodasFichas[j] += filasUnaFicha[j];
//			}
//		}
//		
//		for (int i = 0; i < 5; i++) {
//			System.out.println(filasTodasFichas[i]);
//		}
//
//	}
	
	
	/**
	 * Mostrar tablero recorriendo la lista con un for mejorado. Muestra gráficamente todas las
	 * fichas que están en el Tablero
	 */
	public void mostrarTablero() {

		//String con 5 filas, contiene las filas de UNA sola ficha.
		String[] filasUnaFicha = new String[5];

		//String con 5 filas, concatena, añade, TODAS filas de fichas individuales
		String[] filasTodasFichas = {"", "", "", "", ""};

		boolean horizontal;

		System.out.println("--------------- TABLERO ---------------");

		if (numFichasTablero == 0) {
			System.out.println("No hay fichas en el tablero, aún.\n");

		} else {
			// For mejorado: recorre todos los objetos Ficha (f) del ArrayDeque 'fichasEnTablero'.
			for (Ficha f : fichasEnTablero) {
				// 'filasUnaFicha' contiene un array de Strings de las filas de 'f'
				filasUnaFicha = f.representarFicha();

				// Comprobar si la Ficha f actual es horizontal o no
				horizontal = f.getNumIguales() == false ? true : false;

				// Si es horizontal, hacer un espacio en las filas 0 y 4.
				// Y se concatenan (suman) las filas.
				for (int i = 0; i < 5; i++) {
					filasTodasFichas[i] += filasUnaFicha[i];
				}
			}

			// Mostrar todas las filas concatenadas.
			for (int i = 0; i < 5; i++) {
				System.out.println(filasTodasFichas[i]);
			}
		}

	}

	public Deque<Ficha> getDequeFichasTablero() {
		return fichasEnTablero;
	}

	public int getNumFichasTablero() {
		return numFichasTablero;
	}

}
