/*
 * Representa el juego del dominó, con sus reglas.
 * Se recomienda una implementación modular.
 */
package IU;

import core.*;

/**
 * @author Rubén Solveira Sorga - solveira.ruben@gmail.com
 */

public class Domino {

	private static int NUM_JUGADORES; // valor de 2,3 o 4
	private static Jugador[] jugador;
	private static int identificadorJugador;  // turno para el jugador[0], turno para el jugador[1]...

	/**
	 * Función pública. Es la que se ejecuta en el main. Pide el número de jugadores y sus nombres.
	 * Ejecuta la función principal iniciarPartida() en bucle.
	 */
	public static void iniciarJuego() {
		boolean seguir = true;
		System.out.println("IMPORTANTE. Si las fichas se ven mal, tienes que "
			+ "cambiar la fuente de letra de la consola. \n"
			+ "Vete a Tools → Options → Miscellaneous → Output → Font y aqui \n"
			+ "cambiar a una fuente como 'Consolas' o otra monoespaciada. \n"
			+ "Quitar la que viene por defecto, se llama 'Monoespaced'. \n");

		pedirNumJugadores();
		insertarJugador();

		// Ejecuta iniciarPartida() mientras devuelva true. Se pregunta si se
		// quiere seguir jugando, si es SI, iniciarPartida() devuelve true y
		// se vuelve ejecutar, si es NO, se devuelve false y se acaba todo.
		while (seguir == true) {
			// iniciarPartida() se ejecuta mientras devuelva true.
			seguir = iniciarPartida();
		}
		System.out.println("Adiós.");
	}

	
	/**
	 * Función principal. Es la que hace casi todo. Llama a las demás funciones.
	 *
	 * @return booleano, se pregunta si se quiere seguir jugando, devuelve true si SI, false si NO.
	 */
	private static boolean iniciarPartida() {
		identificadorJugador = 0; // turno para el jugador[0], turno para el jugador[1]... 
		int opcion = 0;
		String jugadas[];
		boolean manoVacia;
		int acabarPartidaPorFaltaDeOpciones = 0; // Si todos los jugadores pasan turno

		Monton monton = new Monton();
		Tablero tablero = new Tablero();
		repartirFichas(monton);
		ES.limpiarPantalla();

		do {
			tablero.mostrarTablero();
			jugador[identificadorJugador].mostrarMano();

			jugadas = posiblesJugadas(tablero, jugador[identificadorJugador], monton);
			opcion = accionarJugada(tablero, jugador[identificadorJugador], monton, jugadas);

			// Si el jugador vacía la mano, se acaba la partida. Bucle while de abajo.
			manoVacia = jugador[identificadorJugador].getLongitudFichasMano() == 0 ? true : false;
			// Si no se tiene ficha para poner, hay que coger del monton, opcion
			// 55, y no se pasa turno hasta que se pueda colocar o no queden 
			// fichas en el monton.
			if (opcion == 55) {
				// Coge del monton y no pasa turno
			} else if (opcion == 22) {
				// Se pasa turno y se incrementa el contador de pasar turno,
				// por si no hay jugadas posibles y se tiene que acabar la partida.
				// Pasar turno al siguiente jugador
				// Si el contador acabarPartida es igual al número de jugadores
				// significa que todos han pasado turno, lo cual no tienen jugadas
				// posibles y se acaba la partida.
				identificadorJugador++;
				acabarPartidaPorFaltaDeOpciones++;
			} else {
				// Si es otro número (del 0 al rango de opciones de fichas)
				// se pasa turno al siguiente jugador, y se resetea el contador
				// de acabar la partida.
				identificadorJugador++;
				acabarPartidaPorFaltaDeOpciones = 0;

			}

			//Volver a empezar turno por el primer jugador
			if (identificadorJugador == NUM_JUGADORES) {
				identificadorJugador = 0;
			}
			ES.limpiarPantalla();

			// Repetir mientras no se introduzca 'Finalizar partida' y la mano
			// del jugador no está vacia (Se gana cuando la mano está vacia), y
			// sigue habiendo jugadas disponibles.
		} while (opcion != 99 && manoVacia == false
			&& acabarPartidaPorFaltaDeOpciones != NUM_JUGADORES);

		if (acabarPartidaPorFaltaDeOpciones == NUM_JUGADORES) {
			System.out.println("La partida se acabó por falta de jugadas posibles");
		} else if (manoVacia == true) {
			System.out.println("Se acabó por que alguien ya puso la última ficha");
		} else if (opcion == 99) {
			System.out.println("Se acabó por que alguien quiso finalizar partida");
		}

		ES.limpiarPantalla();
		System.out.println("             PARTIDA FINALIZADA");
		System.out.println("-------------------------------------------------");
		calcularPuntuacion();
		// Ejecuta preguntarSeguirJugando() y si se introduce 'S', retorna true.
		if (preguntarSeguirJugando()) {
			return true;
		} else {
			return false;
		}
	}
	

	private static void pedirNumJugadores() {
		int num;

		do {
			num = ES.pideNumero("¿Cuántos jugadores van a jugar? [2,3,4]: ");
		} while (num <= 1 || num >= 5);

		// Reservar espacio
		jugador = new Jugador[num];
		NUM_JUGADORES = num;
	}
	

	/**
	 * Función para insertar los jugadores en el array. También se comprueba si los nombres estan
	 * repetidos y se obliga a introducir uno que no estea repetido.
	 */
	private static void insertarJugador() {
		int num = 0;
		String nombre = "";
		String[] todosNombres = new String[NUM_JUGADORES]; // Evitar nombres repetidos

		do {
			nombre = ES.pideCadena("Nombre del jugador " + (num + 1) + ": ").toUpperCase();
			todosNombres[num] = nombre;

			// Comprobar nombres repetidos.
			for (int n = 0; n < num; n++) {
				while (nombre.equals(todosNombres[n])) {
					nombre = ES.pideCadena("Nombre, repetido. Introduce otro nombre:").toUpperCase();
					n = 0;
				}
			}
			todosNombres[num] = nombre;

			// Instanciar jugador
			jugador[num] = new Jugador(nombre);
			num++;
		} while (num < NUM_JUGADORES);
	}
	

	/**
	 * Funcion para repartir 7 fichas a los jugadores al principio de la partida
	 *
	 * @param monton el monton de donde se cogen las fichas
	 */
	private static void repartirFichas(Monton monton) {
		for (int j = 0; j < NUM_JUGADORES; j++) {
			// Vaciar primero, repartir después.
			// Prevenir, por si se juega una segunda partida y quedan fichas en la mano.
			jugador[j].vaciarMano();
			for (int i = 0; i < 7; i++) {
				jugador[j].anadirFichaMano(monton.cogerFicha());
			}
		}
	}

	/**
	 * Calcula y muestra la puntuación de la partida actual y de todas las partidas. Gana el que
	 * menor puntuación tenga. En caso de empate en la puntuación, gana el que puso la primera
	 * ficha.
	 */
	private static void calcularPuntuacion() {
		int minimaPuntuacion = Integer.MAX_VALUE;
		String ganadorActual = ""; // nombre con la puntuación más baja.
		int puntuacionGanadorActual = 0;
		String jugadorLlevaMano = ""; // nombre del jugador que puso la primera ficha.
		int puntuacionJugadorLlevaMano = 0;
		int longitudNombreMasLargo = 0; // para centrar la tabla de puntuación.

		for (int j = 0; j < jugador.length; j++) {
			// Coger el nombre y puntuación del jugador con la puntuación mínima actual.
			if (minimaPuntuacion >= jugador[j].calcularPuntuacionPartida()) {
				minimaPuntuacion = jugador[j].getPuntuacionFichasMano();
				ganadorActual = jugador[j].getNombre();
				puntuacionGanadorActual = jugador[j].getPuntuacionFichasMano();
			}

			// Coger la longitud del nombre más largo. Para centrar bien la tabla de puntuaciones.
			if (jugador[j].getNombre().length() > longitudNombreMasLargo) {
				longitudNombreMasLargo = jugador[j].getNombre().length();
			}

			// Coger el nombre y puntuación del jugador que lleva la mano.
			if (jugador[j].getLlevarMano() == true) {
				jugadorLlevaMano = jugador[j].getNombre();
				puntuacionJugadorLlevaMano = jugador[j].getPuntuacionFichasMano();
			}
		}

		// En caso de empate en la puntuacion, gana el que lleva la mano (puso
		// la primera ficha).
		if (puntuacionJugadorLlevaMano == puntuacionGanadorActual) {
			ganadorActual = jugadorLlevaMano;
		}

		System.out.println("El ganador de esta partida es: " + ganadorActual
			+ " (" + minimaPuntuacion + " puntos).");
		System.out.println("Jugador que llevó la mano (puso la primera ficha) fue: "
			+ jugadorLlevaMano);

		System.out.println("\nTabla de puntuaciones: ");
		System.out.print("Nombre");
		for (int n = "Nombre".length(); n < longitudNombreMasLargo; n++) {
			System.out.print(" "); // Pintar espacio para que quede centrado.
		}
		System.out.print("  Puntuación actual      Puntuación total \n");

		for (int j = 0; j < jugador.length; j++) {
			System.out.print(jugador[j].getNombre() + "  ");
			// Pintar espacio en nombre, para que quede centrado.
			for (int n = jugador[j].getNombre().length(); n < longitudNombreMasLargo; n++) {
				System.out.print(" ");
			}
			System.out.print(jugador[j].getPuntuacionFichasMano()
				+ "                         " + jugador[j].getPuntuacionTotal() + "\n");
		}
	}
	

	private static boolean preguntarSeguirJugando() {
		String opcion = "";
		do {
			opcion = ES.pideCadena("\n¿Quereis seguir jugando? (S/N): ");
			opcion = opcion.trim().toLowerCase();
		} while (!opcion.equals("n") && !opcion.equals("s"));

		if (opcion.equals("s")) {
			return true;
		} else {
			return false;
		}
	}
	

	/**
	 * Función que contempla, calcula y muestra la opcion de todas las posibles jugadas. La función
	 * creará un 'paquete' String[] que contendrá 2 campos separados por '#', se hará un split(). El
	 * indice del 'paquete' String[] será la opción de la jugada a elegir. El 'paquete' con las
	 * posibles jugadas se pasará a otra función que leerá y ejecutará la opción. - Si se puede
	 * añadir una ficha al tablero: 1º: Indica el indice de la ficha que está en la mano y se puede
	 * colocar en el tablero. 2º: Indica: si se pone a la derecha o a la izquierda. - Si no se
	 * pueden añadir fichas al tablero: 1º: El valor de este campo será siempre 22,55 o 99. 2º:
	 * Indica: pasar turno, ó coger ficha del montón, ó finalizar partida.
	 *
	 * @param tablero
	 * @param jugador
	 * @param monton
	 * @return un String que pasará un 'paquete' de información a la función accionarJugada(String)
	 */
	private static String[] posiblesJugadas(Tablero tablero, Jugador jugador, Monton monton) {

		// Todos los posibles valores del 'paquete'
		// [opcion]: identificador de opción. Irrepetible, se incrementa +1.
		// [id]* == indice Ficha que está en la mano.
		//      [INDICE]	:              VALOR
		/* String [opcion]: |    Campo[1]     |    Campo[2]   |
		   paquete:			|---------------------------------|	
							|  [id]     	  #	  COLOCAR	  | -> Indica la primera ficha vertical que se coloca en el tablero.
							|  [id]     	  #   DERECHA     | -> Indica la ficha a colocar en la derecha del tablero.
							|  [id]           #	  IZQUIERDA	  | -> Indica la ficha a colocar en la izquierda del tablero.
							|   22        	  #	  PASARTURNO  | -> Pasa turno
							|   55	          #	  COGERMONTON | -> Coge nueva ficha del montón.
							|   99	          #	  FINALIZAR	  | -> Finaliza la partida.
		 */
		int opcion = 0; // Indice del array paquetes.
		String[] paquetes = new String[15]; // Mejor pasarse...

		// Muestra distintas opciones'[numeros]' con sus 'acciones' (Pasar turno, colocar...).
		String MostrarOpcionFila1 = "        "; // mostrar opción poner a izquierda
		String MostrarOpcionFila2 = "        "; // mostrar opción poner a derecha

		//  Si el tablero está vacío, añadir primera ficha o pasar turno si no
		//  se tiene una ficha vertical (numeros iguales).
		if (tablero.getNumFichasTablero() == 0) {

			boolean vertical = false;
			for (int indice = 0; indice < jugador.getLongitudFichasMano(); indice++) {
				// Comprobar si tiene ficha vertical, para poder empezar a colocar.
				if (jugador.getFichaMano(indice).getNumIguales()) {
					vertical = true;
					// Se crea el paquete con el campo1 'indice ficha' y el
					// Campo2 'acción'. Y se incrementa el indice del paquete, 
					// para que haiga más paquetes.
					paquetes[opcion] = indice + "#COLOCAR";
					MostrarOpcionFila1 += "[" + opcion + "] Colocar     ";
					opcion++;
				} else {
					MostrarOpcionFila1 += "                  ";
				}
			}

			//Componer paquete:
			// Si hay vertical: el paquete ya contiene toda la información de la vertical.
			if (vertical) {
				//
			} else {
				// Si no hay vertical:
				// Se coge activa la opcion de coger del monton
				if (monton.estaVacio() == false) {
					paquetes[opcion] = "55#COGERMONTON";
					opcion++;
					MostrarOpcionFila1 += "[55] Coger monton";
				} else {
					// Si no quedan fichas en el monton, se pasa turno
					paquetes[opcion] = "22#PASARTURNO";
					opcion++;
					MostrarOpcionFila1 += "[22] Pasar turno";
				}
			}

		} else {
			// Si ya hay fichas en el tablero, comprobar si la mano contiene
			// fichas que se puedan colocar al inicio o al final.
			boolean colocarInicio = false;
			boolean colocarFin = false;

			for (int indice = 0; indice < jugador.getLongitudFichasMano(); indice++) {

				// Comprobar si los numeros izquierdo y derecho de las fichas que tenemos en la mano
				// coinciden con el número izquierdo de la primera ficha del tablero. Si hay, se coloca al inicio.
				if ((tablero.getPrimeraFicha().getNumIzq() == jugador.getFichaMano(indice).getNumDer())
					|| (tablero.getPrimeraFicha().getNumIzq() == jugador.getFichaMano(indice).getNumIzq())) {
					colocarInicio = true;
					paquetes[opcion] = indice + "#IZQUIERDA";
					MostrarOpcionFila1 += "[" + opcion + "] Izquierda     ";
					opcion++;
				} else {
					if (jugador.getFichaMano(indice).getNumIguales()) {
						MostrarOpcionFila1 += "                ";
					} else {
						MostrarOpcionFila1 += "                  ";
					}
				}

				// Comprobar si los numeros izquierdo y derecho de las fichas que tenemos en la mano
				// coinciden con el número derecho de la ultima ficha del tablero. Si hay, se coloca al final.
				if ((tablero.getUltimaFicha().getNumDer() == jugador.getFichaMano(indice).getNumDer())
					|| (tablero.getUltimaFicha().getNumDer() == jugador.getFichaMano(indice).getNumIzq())) {
					colocarFin = true;
					paquetes[opcion] = indice + "#DERECHA";
					MostrarOpcionFila2 += "[" + opcion + "] Derecha       ";
					opcion++;
				} else {
					if (jugador.getFichaMano(indice).getNumIguales()) {
						MostrarOpcionFila2 += "                ";
					} else {
						MostrarOpcionFila2 += "                  ";
					}
				}
			}

			if (!colocarInicio && !colocarFin && monton.estaVacio()) {
				paquetes[opcion] = "22#PASARTURNO";
				opcion++;
				MostrarOpcionFila1 += "[22] Pasar turno";
			}
			if (!colocarInicio && !colocarFin && !monton.estaVacio()) {
				paquetes[opcion] = "55#COGERMONTON";
				opcion++;
				MostrarOpcionFila1 += "[55] Coger monton";
			}
		}

		//  Siempre mostrar opcion de salir de la partida
		paquetes[opcion] = "99#FINALIZAR";
		opcion++;
		MostrarOpcionFila1 += "\t[99] Finalizar";

		System.out.println(MostrarOpcionFila1);
		System.out.println(MostrarOpcionFila2);

//		System.out.println("\n\nPaquetes al acabar en la funcion posiblesJugadas()");
//		for (int x=0; x < paquetes.length; x++) {
//			System.out.println( x + " -> " + paquetes[x]);
//		}
		return paquetes;
	}

	private static int accionarJugada(Tablero tablero, Jugador jugador, Monton monton, String[] paquetes) {

		// Indica el rango de opcion que se puede introducir.
		// Solo contará los paquetes con las fichas, no los otros (FINALIZAR,...).
		// Los otros tendrán valores fijos, FINALIZAR=99,PASARTURNO=22,COGERMONTON=55.
		int rangoOpcionFichas = 0;
		int opcion;
		boolean pasarTurno = false;
		boolean cogerMonton = false;

		// Al parecer hay problemas con el contenido en null... en fín...
		// Poner un valor -1 en los indices que contienen null.
		for (int i = 0; i < paquetes.length; i++) {
			if (paquetes[i] == null) {
				paquetes[i] = "-1";
			}
		}

		System.out.print("\nPosibles opciones: ");

		for (int j = 0; j < paquetes.length; j++) {

			if (paquetes[j].equals("99#FINALIZAR")
				|| paquetes[j].equals("22#PASARTURNO")
				|| paquetes[j].equals("55#COGERMONTON")
				|| paquetes[j].equals("-1")) {
				//No pongo los indices normales de los valores por defecto.
				// ya que por ejemplo la opción FINALIZAR puede tener el indice '4'
				// en un momento dado, pero su opción es 99.
			} else {
				// Si son fichas, indicamos el número y aumentamos el rango de opcion.
				System.out.print("[" + j + "]");
				rangoOpcionFichas++;
			}

			// Pongo aqui los valores por defecto con la opción correcta.
			if (paquetes[j].equals("22#PASARTURNO")) {
				pasarTurno = true;
				System.out.print("  [22]  ");
			}
			if (paquetes[j].equals("55#COGERMONTON")) {
				cogerMonton = true;
				System.out.print("  [55]  ");
			}
			if (paquetes[j].equals("99#FINALIZAR")) {
				System.out.print("  [99]  ");
			}
		}

		do {
			opcion = ES.pideNumero("\nIntroduce la opción: ");
			// Pasa turno si se introduce 22 y la jugada PASARTURNO esta disponible.
			if (pasarTurno == true && opcion == 22) {
				return 22;
			}

			// Coge del monton si se introduce 55 y la jugada COGERMONTON está disponible.
			if (cogerMonton == true && opcion == 55) {
				jugador.anadirFichaMano(monton.cogerFicha());
				return 55;
			}
		} while (opcion < 0 || opcion >= rangoOpcionFichas && opcion != 99);

		// Si la opcion introducida es 99, se retorna ese valor y se acaba la partida.
		if (opcion == 99) {
			return opcion;

		} else {
			// Separar el paquete en dos, al encontrar '#'.
			// El campos[0] indica la ficha, el campos[1] indica si derecha o izquierda.
			String[] campos = paquetes[opcion].split("#");

			if (campos[1].equals("COLOCAR")) {
				// Da igual Fin que Inicio, es la primera ficha.
				// Añadir la ficha de la mano del jugador al tablero
				tablero.insertarFichaFin(jugador.getFichaMano(Integer.parseInt(campos[0])));
				// Quitar ficha de la mano.
				jugador.quitarFichaMano(Integer.parseInt(campos[0]));
				// Coger otra ficha del monton, si hay.
//				if ( !monton.estaVacio() ) {
//					jugador.anadirFichaMano( monton.cogerFicha() );
//				}
				// Como es la primera ficha que se coloca, este jugador lleva la mano.
				jugador.setLlevarMano();
			}

			if (campos[1].equals("DERECHA")) {
				tablero.insertarFichaFin(jugador.getFichaMano(Integer.parseInt(campos[0])));
				jugador.quitarFichaMano(Integer.parseInt(campos[0]));
//				if ( !monton.estaVacio() ) {
//					jugador.anadirFichaMano( monton.cogerFicha() );
//				}
			}

			if (campos[1].equals("IZQUIERDA")) {
				tablero.insertarFichaInicio(jugador.getFichaMano(Integer.parseInt(campos[0])));
				jugador.quitarFichaMano(Integer.parseInt(campos[0]));
//				if ( !monton.estaVacio() ) {
//					jugador.anadirFichaMano( monton.cogerFicha() );
//				}
			}

			return opcion;
		}
	} // Fin accionarJugada().

	
	//(EXTRA). Ya no se utiliza.
	/**
	 * Función que calcula todas las opciones de todos los jugadores en un turno. Si no se pueden
	 * colocar fichas es decir, si no hay fichas en el montón y TODOS los jugadores deben pasar
	 * turno, entonces la partida se acaba por falta de opciones y se calcula la puntuación. Para
	 * esto se cojen los 'paquetes' de los jugadores y se mira si todos tienen PASARTURNO.
	 *
	 * @param jugador
	 */
	private static boolean acabarPartidaPorFaltaDeOpciones(Tablero tablero, Jugador[] jugador, Monton monton) {
		// Contador que se incrementa cuando hay un paquete con 22#PASARTURNO
		// Si este contador es igual al numero de jugadores, significa que TODOS
		// tienen que pasar turno, no hay jugadas disponibles, entonces se acaba partida.
		int vecesPasarTurno = 0;
		String[] paquete = new String[10];

		// Recorre todos los jugadores
		for (int j = 0; j < jugador.length; j++) {
			paquete = posiblesJugadas(tablero, jugador[j], monton);
//			System.out.println("*** Numero de paquetes: " + paquete.length + "**");

			// Busca en todos los paquetes si está PASARTURNO
			for (int i = 0; i < paquete.length; i++) {
				// Buscar solo en los paquetes no null, si se hace un equals a 
				// un null, da error.
				if (paquete[i] != null) {
					if (paquete[i].equals("22#PASARTURNO")) {
						// Si el paquete contiene 22#PASARTURNO se incrementa contador.
						vecesPasarTurno++;
					}
				}
			}
		}
		// Si el contador es igual al número de jugadores, se acaba partida.
		if (vecesPasarTurno == jugador.length) {
			System.out.println("La partida se acabó por FALTA DE JUGADAS POSIBLES.");
			return true;
		} else {
			return false;
		}
	}
	
}
