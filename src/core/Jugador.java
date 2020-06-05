/*
 * Representa a un jugador de la partida, identificado por el nombre,
 * las fichas de la mano, puntos en la partida y puntos acumulados.
 *
 * Funcionalidad: recibe 7 fichas, de entre las fichas posibles a colocar,
 * selecciona una, roba ficha del montón, consultar/modificar puntos, etc.
 */
package core;

import java.util.ArrayList;

/**
 * @author Rubén Solveira Sorga - solveira.ruben@gmail.com
 */
public class Jugador {

	private String nombre;
	private ArrayList<Ficha> fichasMano;
	private int longitudFichasMano;
	private int puntuacionTotal;
	private int puntuacionFichasMano;
	private boolean llevarMano; // flag en caso de empate

	/**
	 * Constructor de la clase jugador
	 *
	 * @param nombre Nombre del jugador como String
	 */
	public Jugador(String nombre) {
		this.nombre = nombre;
		fichasMano = new ArrayList<>();
		longitudFichasMano = 0;
		puntuacionTotal = 0;
		puntuacionFichasMano = 0;
		llevarMano = false;
	}
	

	public Ficha getFichaMano(int num) {
		return fichasMano.get(num);
	}

	public String getNombre() {
		return nombre;
	}

	public void setLlevarMano() {
		llevarMano = true;
	}

	public boolean getLlevarMano() {
		return llevarMano;
	}

	public int getLongitudFichasMano() {
		return longitudFichasMano;
	}

	public void anadirFichaMano(Ficha ficha) {
		fichasMano.add(ficha);
		longitudFichasMano++;
	}

	public void quitarFichaMano(int num) {
		fichasMano.remove(num);
		longitudFichasMano--;
	}

	public void vaciarMano() {
		fichasMano.clear();
		longitudFichasMano = 0;
	}

	
	public void mostrarMano() {
		//String con 5 filas, contiene las filas de UNA sola ficha.
		String[] filasUnaFicha = new String[5];
		//String con 5 filas, concatena, añade, TODAS filas de fichas individuales
		String filasTodasFichas[] = {"", "", "", "", ""};
		String mostrarIndice = "";
		System.out.println("\n------ Fichas en la mano de " + nombre + " ------");
		System.out.println("Número de fichas: " + fichasMano.size());

		for (int i = 0; i < longitudFichasMano; i++) {
			// 'filasUnaFicha' contiene un array de Strings de las filas de 'f'
			filasUnaFicha = fichasMano.get(i).representarFicha();

			// Se concatenan (suman) las filas.
			for (int j = 0; j < 5; j++) {
				filasTodasFichas[j] += "           " + filasUnaFicha[j];
			}
		}

		// Pintar las fichas.
		for (int i = 0; i < 5; i++) {
			System.out.println(filasTodasFichas[i]);
		}

		// Pintar los índices debajo de las fichas.
//		for (int i = 0; i < getLongitudFichasMano(); i++) {
//			if ( getFichaMano(i).getNumIguales() ) {
//				mostrarIndice += "            [" + i + "] ";
//			} else {
//				mostrarIndice += "             [" + i + "]  ";
//			}
//		}
//		System.out.println(indice);
//		System.out.println("\n");
	}
	

	public int calcularPuntuacionPartida() {
		puntuacionFichasMano = 0;
		for (Ficha f : fichasMano) {
			puntuacionFichasMano += f.getNumDer();
			puntuacionFichasMano += f.getNumIzq();
		}
		puntuacionTotal += puntuacionFichasMano;
		return puntuacionFichasMano;
	}

	public int getPuntuacionFichasMano() {
		return puntuacionFichasMano;
	}

	public int getPuntuacionTotal() {
		return puntuacionTotal;
	}

}
