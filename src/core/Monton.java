/*
 * Representa el conjunto de fichas, en total 28, desordenadas.
 * Estructura: Las fichas se guardarán en un array estático.
 * Funcionalidad: Crear el montón, desordenar, quitar una ficha, etc.
 */

package core;

import java.util.LinkedList;
import java.util.Collections;

/**
 * @author Rubén Solveira Sorga - solveira.ruben@gmail.com
 */
public class Monton {

	private static LinkedList<Ficha> fichasMonton;
	private static int numFichasEnMonton;

	/**
	 * Constructor de la clase Monton. Crear 28 fichas, asignándole dos números y un identificador
	 * para saber si es vertical (numIguales).
	 */
	public Monton() {
		fichasMonton = new LinkedList<Ficha>();

		for (int izq = 0; izq <= 6; izq++) {
			for (int der = izq; der <= 6; der++) {

				if (izq == der) {
					// Crear ficha números iguales (true), será ficha vertical
					fichasMonton.add(new Ficha(izq, der, true));
				} else {
					// Crear ficha números distintos (false), será ficha horizontal
					fichasMonton.add(new Ficha(izq, der, false));
				}
			}
		}
		mezclarFichas();
	}
	

	/**
	 * Función para mezclar las fichas del Monton, desordenarlas aleatoriamente
	 */
	private void mezclarFichas() {
		Collections.shuffle(fichasMonton);
	}

	
	/**
	 * Muestra gráficamente todas las fichas que están en el Monton
	 */
	public void mostrarMonton() {

		//String con 5 filas, contiene las filas de UNA sola ficha.
		String[] filasUnaFicha = new String[5];

		//String con 5 filas, concatena, añade, TODAS las filas de fichas individuales
		String filasTodasFichas[] = {"", "", "", "", ""};
		System.out.println("------------- MONTON -----------------");

		boolean horizontal;
		for (Ficha f : fichasMonton) {
			filasUnaFicha = f.representarFicha();

			horizontal = f.getNumIguales() == false ? true : false;

			// Añadir la representacion de una ficha al array de todas las fichas.
			for (int j = 0; j < 5; j++) {
				// Pintar espacios solo en las filas 1 y 5, si la ficha es horizontal
				if (horizontal == true && (j == 0 || j == 4)) {
					filasTodasFichas[j] += "       ";
				}
				filasTodasFichas[j] += filasUnaFicha[j];
			}
		}

		// Pintar todas las fichas
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
		return fichasMonton.isEmpty();
	}
	

	/**
	 * Función para coger la primera ficha del montón
	 *
	 * @return La primera ficha del montón
	 */
	public Ficha cogerFicha() {
		return fichasMonton.remove();
	}

}
