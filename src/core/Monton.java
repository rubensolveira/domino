/*
 * Representa el conjunto de fichas, en total 28, desordenadas.
 * Estructura: Las fichas se guardarán en un array estático.
 * Funcionalidad: Crear el montón, desordenar, quitar una ficha, etc.
 */

package core;

import java.util.Random;

/**
 * @author Rubén Solveira Sorga
 */

public class Monton {

	private static Ficha[] fichas; // Va a contener todas las fichas, 28.
	private static int numFichasEnMonton;

	/**
	 * Constructor de la clase Monton. Crear 28 fichas, asignándole dos números y un identificador
	 * para saber si es vertical (numIguales).
	 */
	public Monton() {
		fichas = new Ficha[28]; // Reservar espacio para 28 fichas
		numFichasEnMonton = 28; // Total de fichas en el Monton
		int numeroFicha = 0; // contador

		for (int izq = 0; izq <= 6; izq++) {
			for (int der = izq; der <= 6; der++) {

				if (izq == der) {
					// Crear ficha números iguales (true), será ficha vertical
					fichas[numeroFicha] = new Ficha(izq, der, true);
				} else {
					// Crear ficha números distintos (false), será ficha horizontal
					fichas[numeroFicha] = new Ficha(izq, der, false);
				}
				numeroFicha++;
			}
		}
		mezclarFichas();
	}

	
	/**
	 * Función para mezclar las fichas del Monton, desordenarlas aleatoriamente
	 */
	public void mezclarFichas() {
		// Crear array listaRandom, sus indices van a contener valores
		// de 0 a 27, desordenados.
		int[] listaRandom = new int[28];

		// Dar valor -2 a todos los elementos del array
		// porque al crearlos, se crean con valor 0, y mas adelante no interesa
		// que tengan ese valor, por que se comprueba si se repiten los valores 
		// desde 0 a 27, entonces se repiten y el bucle se hace infinito.
		for (int j = 0; j < listaRandom.length; j++) {
			listaRandom[j] = -2;
		}

		Random rand = new Random(System.currentTimeMillis());
		int numeroRandom;
		int numElementosLista = 0;
		boolean repetido;

		while (numElementosLista <= 27) {
			repetido = false;
			numeroRandom = rand.nextInt(28); // Genera de 0 a 27
			for (int x = 0; x <= numElementosLista; x++) {
				if (numeroRandom == listaRandom[x]) {
					repetido = true;
				}
			}
			if (repetido == false) {
				listaRandom[numElementosLista] = numeroRandom;
				numElementosLista++;
			}
		}

		//  Crear nuevo array donde se meteran las fichas ordenadas en las posiciones random
		Ficha[] fichasRandom = new Ficha[28];
		for (int k = 0; k <= 27; k++) {
			fichasRandom[k] = fichas[listaRandom[k]];
		}
		fichas = fichasRandom;
	}

	
	/**
	 * Muestra graficamente todas las fichas que están en el Monton.
	 */
	public void mostrarMonton() {

		//String con 5 filas, contiene las filas de UNA sola ficha.
		String[] filasUnaFicha = new String[5];

		//String con 5 filas, concatena, añade, TODAS las filas de fichas individuales
		String filasTodasFichas[] = {"", "", "", "", ""};
		System.out.println("------------- MONTON -----------------");

		boolean horizontal;
		for (int i = 0; i < numFichasEnMonton; i++) {
			filasUnaFicha = fichas[i].representarFicha();

			horizontal = fichas[i].getNumIguales() == false ? true : false;

			for (int j = 0; j < 5; j++) {
				// Pintar espacios solo en las filas 1 y 5, si la ficha es horizontal
				if (horizontal == true && (j == 0 || j == 4)) {
					filasTodasFichas[j] += "       ";
				}
				filasTodasFichas[j] += filasUnaFicha[j];
			}
		}

		for (int i = 0; i < 5; i++) {
			System.out.println(filasTodasFichas[i]);
		}
	}

	
	/**
	 * Función bool para saber si el montón está vacío Importante a la hora de coger nuevas fichas,
	 * si no quedan fichas, no se cogen
	 *
	 * @return true si el montón está vacío
	 */
	public boolean estaVacio() {
		return numFichasEnMonton == 0;
	}

	
	/**
	 * Función para coger la ultima ficha del montón
	 *
	 * @return La última ficha del montón
	 */
	public Ficha cogerFicha() {
		if (estaVacio() == false) {
			numFichasEnMonton--;
		}
		return fichas[numFichasEnMonton];
	}

	
	public int getNumFichasEnMonton() {
		return numFichasEnMonton;
	}

	
	public Ficha[] getFichas() {
		return fichas;
	}
}
