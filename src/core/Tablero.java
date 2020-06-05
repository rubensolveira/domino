/*
 * Representa la mesa de juego, donde los jugadores colocan las fichas en cada turno.
 * Estructura: se utilizará una doble cola (Deque).
 * Funcionalidad: insertar la ficha en su lugar correcto automáticamente, visualizar, etc.
 */

package core;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Rubén Solveira Sorga - solveira.ruben@gmail.com
 */
public class Tablero {

	private static Deque<Ficha> fichasEnTablero; // Lista enlazada de fichas
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
	 * Muestra gráficamente todas las fichas que están en el Tablero.
	 */
	public void mostrarTablero() {
		//String con 5 filas, contiene las filas de UNA sola ficha.
		String[] filasUnaFicha = new String[5];
		//String con 5 filas, concatena, añade, TODAS filas de fichas individuales
		String[] filasTodasFichas = {"", "", "", "", ""};

		System.out.println("--------------- TABLERO ---------------");

		if (fichasEnTablero.isEmpty()) {
			System.out.println("\nNo hay fichas en el tablero, aún.\n\n");
		} else {
			// For mejorado: recorre todos los objetos Ficha (f) del ArrayDeque 'fichasEnTablero'.
			for (Ficha f : fichasEnTablero) {
				// 'filasUnaFicha' contiene un array de Strings de las filas de 'f'
				filasUnaFicha = f.representarFicha();

				// Se concatenan (suman) las filas.
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

	
	public Ficha getPrimeraFicha() {
		return fichasEnTablero.getFirst();
	}

	public Ficha getUltimaFicha() {
		return fichasEnTablero.getLast();
	}

	public Deque<Ficha> getDequeFichasTablero() {
		return fichasEnTablero;
	}

	public int getNumFichasTablero() {
		return numFichasTablero;
	}

}
