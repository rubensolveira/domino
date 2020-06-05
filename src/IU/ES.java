package IU;

import java.util.Scanner;

/**
 * @author Rubén Solveira Sorga
 */

public class ES {

	//  ATRIBUTOS
	public static Scanner teclado = new Scanner(System.in);

	//  MÉTODOS
	/**
	 * Lee una cadena de texto del teclado
	 *
	 * @param mensaje El mensaje a visualizar antes de recibir la cadena de texto
	 * @return Una cadena de texto
	 */
	public static String pideCadena(String mensaje) {
		String toret;
		do {
			// Poner mensaje
			System.out.print(mensaje);
			// Recoger String
			toret = teclado.nextLine().trim();
			// Repetir si no se introduce nada
		} while (toret.equals(""));

		return toret;
	}

	
	/**
	 * Lee un número del teclado
	 *
	 * @param mensaje El mensaje a visualizar antes de recibir el número entero
	 * @return El número como entero
	 */
	public static int pideNumero(String mensaje) {
		boolean repite;
		int toret = 0;

		do {
			repite = false;
			// Poner mensaje
			System.out.print(mensaje);

			try {
				// Recoger entero
				toret = Integer.parseInt(teclado.nextLine().trim());
			} catch (NumberFormatException exc) {
				// Repetir si no se recibe un entero
				repite = true;
			}
		} while (repite);

		return toret;
	}

	
	/**
	 * Método para limpiar pantalla
	 */
	public static void limpiarPantalla() {
		for (int i = 0; i < 10; i++) {
			System.out.println();
		}
	}

} // Fin clase ES
