/*
  Representa una ficha de dominó del monton, formada por dos numeros, [0..6].
 */
package core;

/**
 * @author Rubén Solveira Sorga - solveira.ruben@gmail.com
 */

public class Ficha {

	private int numIzquierdo;
	private int numDerecho;
	private boolean numIguales;

	/**
	 * Constructor de la clase Ficha
	 *
	 * @param numIzquierdo , numero izquierdo de la ficha
	 * @param numDerecho , numero derecho de la ficha
	 * @param numIguales , boolean para saber si la ficha tiene dos numeros iguales
	 */
	public Ficha(int numIzquierdo, int numDerecho, boolean numIguales) {
		this.numIzquierdo = numIzquierdo;
		this.numDerecho = numDerecho;
		this.numIguales = numIguales;
	}

	
	/**
	 * Funcion para representar graficamente una ficha Si numIzquierdo y numDerecho son distintos,
	 * la ficha se muestra en horizontal
	 *
	 * @return Array de String con 5 filas
	 */
	public String[] representarFicha() {
		String[] fila = new String[5];
		fila[0] = "";
		fila[1] = "";
		fila[2] = "";
		fila[3] = "";
		fila[4] = "";

		// Pintar en vertical
		if (this.numIguales) {
			fila[0] = "╔═══╗";  // ALT + 201 , ALT + 205 , ALT + 187
			fila[1] = "║ " + this.numIzquierdo + " ║";  // ALT + 186
			fila[2] = "║ • ║";  // ALT + 186 , ALT + 7 , ALT + 186
			fila[3] = "║ " + this.numDerecho + " ║";  // ALT + 186
			fila[4] = "╚═══╝";  // ALT + 200 , ALT + 205 , ALT + 188
		} else {
			//Pintar en horizontal
			fila[0] = "       ";
			fila[1] = "╔═════╗";
			fila[2] = "║" + this.numIzquierdo + " • " + this.numDerecho + "║";
			fila[3] = "╚═════╝";
			fila[4] = "       ";
		}

		return fila;
	}

	/**
	 * Gira una ficha horizontal, intercambiando sus numeros, importante cuando se ponen fichas en
	 * el Tablero y sus extremos no coinciden.
	 */
	public void girarFicha() {
		int temporal;
		temporal = numDerecho;
		numDerecho = numIzquierdo;
		numIzquierdo = temporal;
	}

	public boolean getNumIguales() {
		return numIguales;
	}

	public int getNumDer() {
		return numDerecho;
	}

	public int getNumIzq() {
		return numIzquierdo;
	}

}
