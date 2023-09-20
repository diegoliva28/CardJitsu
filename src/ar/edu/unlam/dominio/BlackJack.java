package ar.edu.unlam.dominio;

import java.util.Random;

public class BlackJack {
	private static Palo palosPosibles[] = Palo.values();
	private static Figura figurasPosibles[] = Figura.values();
	private static final int CANTIDAD_MAXIMA_CARTAS = 52;
	private static Carta cartasDisponibles[] = new Carta[CANTIDAD_MAXIMA_CARTAS];

	private Carta mazo[];
	private Carta cartasJugador[];
	private Carta cartasCrupier[];

	public static void inicializarCartasDisponibles() {
		/*
		 * Se debe completar el array cartasDisponibles con cada una de las cartas que
		 * se puede tener. Para esto se recomienda por cada palo (palosPosibles) ir
		 * generando las (figurasPosibles) que puede tener cada uno. Tener presente que
		 * luego, las cartas se van a tener que ordenar por alg�n criterio, para eso se
		 * puede utilizar el atributo codigo de los objetos de tipo Carta.
		 */

//		int palo=0;
//		int figura=0;
		int indice = 0;

		for (int i = 0; i < palosPosibles.length; i++) {
			for (int j = 0; j < figurasPosibles.length; j++) {
//				Carta nueva = new Carta(indice, palosPosibles[i], figurasPosibles[i]);
//				cartasDisponibles[indice] = nueva;
				cartasDisponibles[indice] = new Carta(indice, palosPosibles[i], figurasPosibles[j]);
				indice++;
			}
		}
	}

	public BlackJack() {
		/*
		 * Debe generar las condiciones para el correcto funcionamiento del juego.
		 * 
		 */
		this.cartasCrupier = new Carta[5];
		this.cartasJugador = new Carta[5];
		this.mazo = cartasDisponibles;
	}

	public void ordenar() {
		/*
		 * Se debe ordenar el mazo de cartas de manera ascendente.
		 */
		Carta auxiliar = null;
		for (int i = 1; i < mazo.length; i++) {
			for (int j = 0; j < mazo.length - 1; j++) {
				if (mazo[j] != null && mazo[j + 1] != null) {
					if (mazo[j].getCodigo() < mazo[j + 1].getCodigo()) {
						auxiliar = mazo[j];
						mazo[j] = mazo[j + 1];
						mazo[j + 1] = auxiliar;
					}
				}
			}
		}

	}

	public void mezclar() {
		/*
		 * Se debe alterar el orden natural del mazo de manera que NO sea posible saber
		 * de antemano qu� carta sigue a una carta determinada. Si as� lo deseara, el
		 * programador puede regenerar los objetos del mazo nuevamente (Vaciar las
		 * cartas que pudiera tener el mazo y luego ir completando en cada posici�n cada
		 * carta de manera aleatoria).
		 */
		Random r = new Random();

		for (int i = 0; i < this.mazo.length; i++) {
			int numeroX = r.nextInt(this.mazo.length);
			Carta aux = this.mazo[i];
			this.mazo[i] = this.mazo[numeroX];
			this.mazo[numeroX] = aux;
		}

//		for(int i = 0; i < cartasDisponibles.length; i++) {
//			int posicion;
//			Carta nueva = cartasDisponibles[i];
//			if(this.seRepiteLaCarta(nueva) == false) {
//				do {
//					posicion = (int)(Math.random()*52);	
//				} while(mazo[posicion] != null);
//				mazo[posicion] = cartasDisponibles[i];
//			}
//		}

	}

//	public boolean seRepiteLaCarta(Carta nueva) {
//		boolean seRepite = false;
//		for (int i = 0; i < mazo.length; i++) {
//			if (mazo[i] != null && mazo[i].getCodigo() == nueva.getCodigo()) {
//				seRepite = true;
//				break;
//			}
//		}
//		return seRepite;
//	}

	public Carta siguiente() {
		/*
		 * Determina la siguiente carta que corresponde al jugador y la devuelve a t�tuo
		 * informativo
		 */

		Carta primera = null;
		boolean seEntregoCarta = false;
		int i = 0;

		while (!seEntregoCarta && i < this.cartasJugador.length) {
			if (this.cartasJugador[i] == null) {
				primera = repartirCarta();
				this.cartasJugador[i] = primera;
				seEntregoCarta = true;
			}
			i++;
		}
		return primera;
	}

	private Carta repartirCarta() {
		Carta naipe = null;
		int i = 0;
		boolean cartaEncontrada = false;

		while (!cartaEncontrada && i < this.mazo.length) {
			if (this.mazo[i] != null) {
				naipe = this.mazo[i];
				this.mazo[i] = null;
				cartaEncontrada = true;
			}
			i++;
		}
		return naipe;
	}

	public boolean gano() {
		/*
		 * Eval�a las cartas obtenidas por el jugador contra las cartas del crupier y
		 * devuelve true si el jugador result� ganador.
		 */
		boolean ganoJugador = false;
		int puntosJugador = 0;
//		if (!perdio()) {
		for (int i = 0; i < cartasJugador.length; i++) {
			if (cartasJugador[i] != null) {
				puntosJugador += cartasJugador[i].getValor();
			}
		}

		int puntosGrupier = 0;
		for (int i = 0; i < cartasCrupier.length; i++) {
			if (cartasCrupier[i] != null) {
				puntosGrupier += cartasCrupier[i].getValor();
			}
		}

		if (!this.perdio() && puntosGrupier<=21) {
			if ((21 - puntosJugador) < (21 - puntosGrupier)) {
				ganoJugador = true;
			} else {
				ganoJugador = false;
			}
		}else if(!this.perdio() && puntosGrupier>21) {
			ganoJugador=true;
		}
		return ganoJugador;
	}

	public boolean perdio() {
		/*
		 * Devuelve true si el puntaje del jugador supera los 21 puntos.
		 */
		boolean perdio = false;
		int puntos = 0;
		for (int i = 0; i < cartasJugador.length; i++) {
			if (cartasJugador[i] != null) {
				puntos += cartasJugador[i].getValor();
			}
		}

		if (puntos > 21) {
			perdio = true;
		}
		/*
		 * if(puntos >= 17 && puntos <= 21) { perdio = false; } else if(puntos > 21) {
		 * perdio = true; } else { perdio = false; }
		 */

		return perdio;
	}

	public void jugarGrupier() {
		/*
		 * El juego del grupier consiste en ir sacando y guardando sus cartas mientras
		 * su puntaje sea menor a 17. Cuando el puntaje del grupier es 17 o m�s finaliza
		 * su juego.
		 */

		int acumulador = 0;
		int i = 0;

		while (acumulador<=17 && i < this.cartasCrupier.length) {
			Carta x=this.repartirCarta();
			acumulador+=x.getValor();
			this.cartasCrupier[i]=x;
			i++;
		}
//		do {
//			if(acumulador < 17 && acumulador > 21) {
//				this.cartasCrupier[i]=siguiente();
//				acumulador+=this.cartasCrupier[i].getValor();
//				i++;
//			}

//		} while (acumulador < 17 && acumulador > 21);

//		HACER
//		Carta grupier = null;
//		int acumulado = 0;
//		do {
//			grupier = mazo[indiceMazo++];
//			acumulado += grupier.getValor();
//			for(int i = 0; i < cartasCrupier.length; i++) {
//				if(cartasCrupier[i] == null) {
//					cartasCrupier[i] = grupier;
//					break;
//				}
//			}
//		}while(acumulado <= 21);
//		
//		
	}

	public static Palo[] getPalosPosibles() {
		return palosPosibles;
	}

	public static Figura[] getFigurasPosibles() {
		return figurasPosibles;
	}

	public static int getCantidadMaximaCartas() {
		return CANTIDAD_MAXIMA_CARTAS;
	}

	public static Carta[] getCartasDisponibles() {
		return cartasDisponibles;
	}

	public Carta[] getMazo() {
		return mazo;
	}

	public Carta[] getCartasJugador() {
		return cartasJugador;
	}

	public Carta[] getCartasCrupier() {
		return cartasCrupier;
	}

	public static void setPalosPosibles(Palo[] palosPosibles) {
		BlackJack.palosPosibles = palosPosibles;
	}

	public static void setFigurasPosibles(Figura[] figurasPosibles) {
		BlackJack.figurasPosibles = figurasPosibles;
	}

	public static void setCartasDisponibles(Carta[] cartasDisponibles) {
		BlackJack.cartasDisponibles = cartasDisponibles;
	}

	public void setMazo(Carta[] mazo) {
		this.mazo = mazo;
	}

	public void setCartasJugador(Carta[] cartasJugador) {
		this.cartasJugador = cartasJugador;
	}

	public void setCartasCrupier(Carta[] cartasCrupier) {
		this.cartasCrupier = cartasCrupier;
	}

	public String toString() {
		/*
		 * Devuelve un String con las cartas del jugador y las cartas del crupier
		 */
		String jugador = "";
		for (int i = 0; i < cartasJugador.length; i++) {
			if (cartasJugador[i] != null) {
				jugador += "[" + (i + 1) + "] " + cartasJugador[i] + "\n";
			}
		}

		String grupier = "";
		for (int i = 0; i < cartasCrupier.length; i++) {
			if (cartasCrupier[i] != null) {
				grupier += "[" + (i + 1) + "] " + cartasCrupier[i] + "\n";
			}
		}

		return "Cartas Jugador: \n" + jugador + "\nCartas Grupier: \n" + grupier;
	}
}
