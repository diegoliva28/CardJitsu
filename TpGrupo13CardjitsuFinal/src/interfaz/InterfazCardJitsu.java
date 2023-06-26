package interfaz;

import java.util.Random;
import java.util.Scanner;

import dominio.Cartas;
import dominio.Jugador;
import dominio.TiposDeCarta;
import dominio.CardJitsu;

public class InterfazCardJitsu {
	private static final int MOSTRAR_REGLAS = 1, REGISTRAR_JUGADORES = 2, INICIAR_JUEGO = 3, RANKING = 4, SALIR = 9;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		CardJitsu juego = new CardJitsu();
		Cartas[] barajaDelJuego = obtenerBaraja();
		int opcion = 0;

		do {
			opcion = mostrarMenu(sc);
			switch (opcion) {
			case MOSTRAR_REGLAS:
				String mostrarReglas = juego.mostrarReglas();
				System.out.println(mostrarReglas);
				break;

			case REGISTRAR_JUGADORES:
				registrarJugadoresAlJuego(juego, sc);

				break;
			case INICIAR_JUEGO:
				iniciarJuego(juego, sc, barajaDelJuego);
				break;

			case RANKING:
					mostrarRanking(juego,sc);
				break;

			case SALIR:
				System.out.println("\033[33mGracias por jugar.\u001B[0m");
				System.out.println("Saliendo...");
				sc.close();
				break;
			}

		} while (opcion != 9);

	}

	private static void mostrarRanking(CardJitsu juego, Scanner sc) {
		int opcion=0;
		do {
		System.out.println("\033[33mIngrese una opcion\u001B[0m");
		System.out.println("1-Ver Ranking de victorias");
		System.out.println("2-Ver Ranking de derrotas");
		opcion=sc.nextInt();
		}while(opcion <= 0 || opcion >2);
		
		if(opcion==1) {
			mostrarRankingVictorias(juego);
		}
		if(opcion==2) {
			mostrarRankingDerrotas(juego);
		}
	}

	private static void mostrarRankingDerrotas(CardJitsu juego) {
		juego.ordenarJugadoresPorDerrotas();
		Jugador[]jugadores=juego.getJugadores();
		System.out.println("************************");
		System.out.println("*******\033[33mTop Losses\u001B[0m*******");
		System.out.println("************************");
		for(int i=0;i<jugadores.length;i++) {
			if(jugadores[i] !=null) {
				System.out.println("Jugador [ "+jugadores[i].getNombre()+" ]"+" Derrotas [ "+jugadores[i].getDerrotas()+" ]");
			}
		}
	}
		
	

	private static void mostrarRankingVictorias(CardJitsu juego) {
		juego.ordenarJugadoresPorVictorias();
		Jugador[]jugadores= juego.getJugadores();
		System.out.println("************************");
		System.out.println("********\033[33mTop Wins\u001B[0m********");
		System.out.println("************************");
		for(int i=0;i<jugadores.length;i++) {
			if(jugadores[i] !=null) {
				System.out.println("Jugador [ "+jugadores[i].getNombre()+" ]"+" Victorias [ "+jugadores[i].getVictorias()+" ]");
			}
		}
	}

	private static void registrarJugadoresAlJuego(CardJitsu juego, Scanner sc) {
		String nombre;
		System.out.println("Ingrese el nombre del jugador a registrar");
		nombre = sc.next();

		Jugador nuevo = new Jugador(nombre);
		boolean sePudoAgregar = juego.agregarJugador(nuevo, nombre);

		if (sePudoAgregar == true) {
			System.out.println("Se pudo registrar el jugador " + nombre);
		} else {
			System.out.println("El nombre de jugador ya esta siendo usado.");
		}

	}

	private static void iniciarJuego(CardJitsu juego, Scanner sc, Cartas[] barajaJuego) {
		Random r = new Random();
		Cartas[] mano;

		Cartas[] cartasTiradasPorLosJugadores;

		boolean jugarDeNuevo = false;
		boolean primeraVez=true;
		
		int opcion;
		// Se inicia el do while y se repetira la ejecución del bloque de codigo
		// mientras se cumpla la condicion de que jugarDeNuevo sea true.
		// De lo contrario saldra del do while.
	
		Jugador[] jugadores = new Jugador[2];
		do {
			
			if(primeraVez) {
			
			int posicion = 0;
			int agregado = 1;

			while (agregado != 3) {
				Jugador encontrado = null;
				System.out.println("\nIngrese el nombre del jugador "+agregado);
				String nombre = sc.next();
				encontrado = juego.encontrarJugador(nombre);

				if (encontrado != null && jugadores[0] != encontrado) {//jugadores[0] != encontrado
					jugadores[posicion] = encontrado;
					posicion++;
					agregado++;
				} else if(jugadores[0]==encontrado && encontrado!=null) {
					System.out.println("\nEl jugador ya fue registrado");
				}
				
				if(encontrado==null){
					System.out.println("\nNo se encontro a este jugador en nuestro registro");
					registrarJugadoresAlJuego(juego, sc);
				}
			}
			primeraVez=false;
			}

			// array de los elementos ganados de los jugadores
			int[] jugador1Rondas = new int[3];
			int[] jugador2Rondas = new int[3];

			

			// array para guardar las dos cartas
			cartasTiradasPorLosJugadores = new Cartas[2];

			// cargar una mano al jugador
			mano = otorgarMano(barajaJuego);
			jugadores[0].completarMano(mano);
			mano = otorgarMano(barajaJuego);
			jugadores[1].completarMano(mano);

			// Rondas
			for (int rondas = 0; rondas < 100; rondas++) {
				System.out.println();
				System.out.println("\033[33mRonda\u001B[0m " + (rondas + 1));
				// Turnos
				for (int turnos = 0; turnos < 2; turnos++) {

					if (turnos == 0) {
						System.out.println("Turno del jugador " + jugadores[0].getNombre());
						System.out.println(jugadores[turnos].verMano());

						// elige del 1-5 y lo guarda en opcion
						opcion = seleccionarOpcionCartas(sc);

						// se guarda la carta elegida en el array
						cartasTiradasPorLosJugadores[turnos] = jugadores[turnos].elegirCarta(opcion - 1);

						// se descarta una carta de la mano del jugador debido a que fue tirada
						jugadores[turnos].descartarCarta(opcion - 1);

						// Obtener una carta random del mazo para cargar a la posicion vacia que quedo
						// en
						// el mazo

						Cartas cartaNuevaReponer = obtenerCartaRandomBaraja(barajaJuego);

						// Agregar carta al mazo del jugador para su proximo turno
						jugadores[turnos].agregarCartaMano(cartaNuevaReponer);
					}
					if (turnos == 1) {

						int numAleatorio = r.nextInt(5);
						// Se elige la carta de manera aleatoria del 0 hasta el 5 -->(sin incluirlo).
						cartasTiradasPorLosJugadores[turnos] = jugadores[turnos].elegirCarta(numAleatorio);
						jugadores[turnos].descartarCarta(numAleatorio);
						Cartas cartaNuevaReponer = obtenerCartaRandomBaraja(barajaJuego);
						jugadores[turnos].agregarCartaMano(cartaNuevaReponer);
					}
				}

				// fuera de la iteracion de turnos.
				mostrarCartasEvaluadas(cartasTiradasPorLosJugadores);

				juego.evaluarGanadorRonda(cartasTiradasPorLosJugadores, jugador1Rondas, jugador2Rondas);
				System.out.println("Jugador "+jugadores[0].getNombre());
				mostrarRondasGanadas(jugador1Rondas);

				System.out.println("Jugador "+ jugadores[1].getNombre());
				mostrarRondasGanadas(jugador2Rondas);

				// Se declara dos booleanos para verificar si el juego se sigue o no.
				// Se le pasa al metodo ""elJuegoSigue""las rondas de los jugadores asi verifica
				// si alguno cumple las condiciones para ganar.
				// Si devuelve true alguna variable ,se preguntara si quiere una revancha.
				// Si no quiere,cambiara el estado de jugarDeNuevo a false y saldra del bucle.

				boolean ganoJ1 = juego.seAcaboElJuego(jugador1Rondas);
				boolean ganoJ2 = juego.seAcaboElJuego(jugador2Rondas);

				if (ganoJ1 == true) {
					System.out.println("El juego se acabo. \033[32mFelicitaciones ganaste\u001B[0m " + jugadores[0].getNombre());
					jugarDeNuevo = preguntarVolverAJugar(sc);
					jugadores[0].sumarVictoria();
					jugadores[1].sumarDerrota();
					break;
				}
				if (ganoJ2 == true) {
					System.out.println("El juego se acabo. \033[31mHa ganado\u001B[0m " + jugadores[1].getNombre());
					jugarDeNuevo = preguntarVolverAJugar(sc);
					jugadores[1].sumarVictoria();
					jugadores[0].sumarDerrota();
					break;
				}
			}
		} while (jugarDeNuevo);
	}

	private static Cartas[] obtenerBaraja() {
		Cartas[] obtenerBaraja = new Cartas[45];
		int posicionArr = 0;

		for (int i = 1; i <= obtenerBaraja.length; i++) {
			if (i > 0 && i <= 15) {
				obtenerBaraja[posicionArr] = new Cartas(i, TiposDeCarta.FUEGO);
			} else if (i > 15 && i <= 30) {
				obtenerBaraja[posicionArr] = new Cartas(i - 15, TiposDeCarta.AGUA);
			} else if (i > 30 && i <= 45) {
				obtenerBaraja[posicionArr] = new Cartas(i - 30, TiposDeCarta.HIELO);
			}
			posicionArr++;
		}
		return obtenerBaraja;
	}

	

	private static boolean preguntarVolverAJugar(Scanner sc) {
		int opcion = 0;
		boolean volverAJugar;
		do {
			
			System.out.println("1-Revancha.");
			System.out.println("2-Salir de la partida.");
			opcion = sc.nextInt();
		} while (opcion <= 0 || opcion > 2);

		if (opcion == 1) {
			volverAJugar = true;
		} else {
			volverAJugar = false;
		}

		return volverAJugar;
	}

	private static int seleccionarOpcionCartas(Scanner sc) {
		int opcion;
		do {
			System.out.println("Seleccione que carta tirar (del 1 al 5.)");
			opcion = sc.nextInt();
		} while (opcion <= 0 || opcion > 5);
		return opcion;
	}

	private static void mostrarRondasGanadas(int[] jugadorRondas) {
		System.out.println("\033[33mMedallas\u001B[0m");
		
		for (int i = 0; i < jugadorRondas.length; i++) {

			if (i == 0) {
				  
				System.out.println("|\033[31mFuego\u001B[0m = " + jugadorRondas[i]+"|");
				
			} else if (i == 1) {
				  
				System.out.println("|\033[34mAgua\u001B[0m = " + jugadorRondas[i]+"|");
				 
			} else {
				
				System.out.println("|\033[36mHielo\u001B[0m = " + jugadorRondas[i]+"|");
				 
			}
			
		}

	}

	private static int mostrarMenu(Scanner sc) {
		int opcion;
		System.out.println();
		System.out.println("*************************");
		System.out.println("*\033[33mBienvenido a Card-Jitsu\u001B[0m*");
		System.out.println("*************************");
		System.out.println();
		System.out.println("\033[33mSeleccione una opcion\u001B[0m");
		System.out.println("1-Ver las reglas.");
		System.out.println("2-Registrar jugadores.");
		System.out.println("3-Iniciar juego.");
		System.out.println("4-Ver ranking");
		System.out.println("9-Salir.");
		return opcion = sc.nextInt();

	}

	// Metodo que devuelve un array de cartas de 5 posiciones.
	private static Cartas[] otorgarMano(Cartas[] barajaDelJuego) {
		Random r = new Random();
		Cartas[] manoRandom = new Cartas[5];
		for (int i = 0; i < manoRandom.length; i++) {
			int numRandom = r.nextInt(45);
			manoRandom[i] = barajaDelJuego[numRandom];
		}
		return manoRandom;
	}

	// Devuelve una carta random del mazo
	private static Cartas obtenerCartaRandomBaraja(Cartas[] barajaDelJuego) {
		Cartas carta;
		Random r = new Random();
		int numAleatorio = r.nextInt(45);
		carta = barajaDelJuego[numAleatorio];
		return carta;
	}

	// Metodo que muestra las cartas que tiraron ambos jugadores.Recibe como
	// parametro el array de cartas a evaluar.
	private static void mostrarCartasEvaluadas(Cartas[] evaluar) {
		for (int i = 0; i < evaluar.length; i++) {
			System.out.println("\nEl jugador " + (i + 1) + " ha tirado la carta = " + evaluar[i]);
		}
	}
}