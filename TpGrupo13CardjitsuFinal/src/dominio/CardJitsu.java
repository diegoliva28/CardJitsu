package dominio;

public class CardJitsu {

	private Jugador[] jugadores;

	
	public CardJitsu() {
		this.jugadores = new Jugador[20];

	}

	public String mostrarReglas() {
		String mensaje = "";
		return mensaje = "\033[33mRegistrar jugadores =\u001B[0m Antes de comenzar debe registar 2 jugadores\r\n"
				+ "\033[33mObjetivo =\u001B[0m Gana batallas para convertirte en un maestro ninja\r\n"
				+ "\033[33mBatalla =\u001B[0m Cada jugador elige una carta y se comparan los elementos y valores\r\n"
				+ "\033[33mElementos =\u001B[0m Agua apaga fuego, Fuego derrite nieve y Nieve apaga fuego\r\n"
				+ "\033[33mValores =\u001B[0m El valor mas alto gana si las cartas son del mismo elemento\r\n"
				+ "\033[33mMedallas =\u001B[0m Para ganar consigue una medalla de cada elemento o tres del mismo elemento";

	}
	
	public Jugador[] getJugadores() {
		return jugadores;
	}

	public void ordenarJugadoresPorDerrotas() {
		Jugador aux;
		
		for(int i=0;i<jugadores.length;i++) {
			for(int j=0;j<jugadores.length-1;j++) {
				if(jugadores[j] !=null && jugadores[j+1] !=null) {
					if(jugadores[j].getDerrotas()  < jugadores[j+1].getDerrotas()){
						aux=jugadores[j];
						jugadores[j]=jugadores[j+1];
						jugadores[j+1]=aux;
					}
				}
			}
		}
	}
	
	public void ordenarJugadoresPorVictorias() {
		Jugador aux;
		
		for(int i=0;i<jugadores.length;i++) {
			for(int j=0;j<jugadores.length-1;j++) {
				if(jugadores[j] !=null && jugadores[j+1] !=null) {
					if(jugadores[j].getVictorias()  < jugadores[j+1].getVictorias()){
						aux=jugadores[j];
						jugadores[j]=jugadores[j+1];
						jugadores[j+1]=aux;
					}
				}
			}
		}
	}
	public Jugador encontrarJugador(String nombre) {
		Jugador encontrado=null;
		for(int i=0;i<jugadores.length;i++) {
			if(jugadores[i] !=null && jugadores[i].getNombre().equals(nombre)) {
				encontrado=jugadores[i];
			}
		}
		return encontrado;
	}
	
	public boolean agregarJugador(Jugador jugador,String nombre) {
		
		boolean sePudoAgregar=false;
		for (int i = 0; i < jugadores.length; i++) {
			if ( !existeJugador(nombre) && jugadores[i] == null) {
				jugadores[i] = jugador;
				i = jugadores.length;
				sePudoAgregar=true;

			}
		}
		return sePudoAgregar;

	}
	public void evaluarGanadorRonda(Cartas[] cartasTiradasPorLosJugadores, int[] jugador1RondasGanadas,
			int[] jugador2RondasGanadas) {

		Cartas carta1 = cartasTiradasPorLosJugadores[0];
		Cartas carta2 = cartasTiradasPorLosJugadores[1];
		int acumulador = 1;

		switch (carta1.getElemento()) {
		// [0]=FUEGO , [1]=AGUA [2]=HIELO
		case FUEGO:
			if (carta2.getElemento().equals(TiposDeCarta.FUEGO)) {
				if (carta1.getNivel() > carta2.getNivel()) {
					jugador1RondasGanadas[0] += acumulador;
				}
				if (carta1.getNivel() < carta2.getNivel()) {
					jugador2RondasGanadas[0] += acumulador;
				} else {
					break;
				}
			}
			if (carta2.getElemento().equals(TiposDeCarta.AGUA)) {
				jugador2RondasGanadas[1] += acumulador;
			}
			if (carta2.getElemento().equals(TiposDeCarta.HIELO)) {
				jugador1RondasGanadas[0] += acumulador;
			}
			break;

		case AGUA:
			// [0]=FUEGO , [1]=AGUA [2]=HIELO
			if (carta2.getElemento().equals(TiposDeCarta.FUEGO)) {
				jugador1RondasGanadas[1] += acumulador;
			}
			if (carta2.getElemento().equals(TiposDeCarta.AGUA)) {
				if (carta1.getNivel() > carta2.getNivel()) {
					jugador1RondasGanadas[1] += acumulador;
				}
				if (carta1.getNivel() < carta2.getNivel()) {
					jugador2RondasGanadas[1] += acumulador;
				} else {
					break;
				}
			}
			if (carta2.getElemento().equals(TiposDeCarta.HIELO)) {
				jugador2RondasGanadas[2] += acumulador;
			}
			break;

		case HIELO:
			// [0]=FUEGO , [1]=AGUA [2]=HIELO
			if (carta2.getElemento().equals(TiposDeCarta.FUEGO)) {
				jugador2RondasGanadas[0] += acumulador;
			}
			if (carta2.getElemento().equals(TiposDeCarta.AGUA)) {
				jugador1RondasGanadas[2] += acumulador;
			}
			if (carta2.getElemento().equals(TiposDeCarta.HIELO)) {
				if (carta1.getNivel() > carta2.getNivel()) {
					jugador1RondasGanadas[2] += acumulador;
				}
				if (carta1.getNivel() < carta2.getNivel()) {
					jugador2RondasGanadas[2] += acumulador;
				} else {
					break;
				}
			}
			break;
		}
	}

//recibe array int de los elementos ganados de un jugador.
	public boolean seAcaboElJuego(int[] arrayJugadorRondas) {
		boolean tieneAlmenosUnElemento = true;
		boolean tieneTresElementos = false;

		for (int i = 0; i < arrayJugadorRondas.length; i++) {
			if (arrayJugadorRondas[i] < 1) {
				tieneAlmenosUnElemento = false;
			}
			if (arrayJugadorRondas[i] == 3) {
				tieneTresElementos = true;
			}
		}

		if (tieneAlmenosUnElemento || tieneTresElementos) {
			return true;
		} else {
			return false;
		}
	}
	private boolean existeJugador(String nombre) {
		boolean existe = false;
		for (int i = 0; i < jugadores.length; i++) {
			
			if(jugadores[i] !=null && jugadores[i].getNombre().equals(nombre)) {
				existe=true;
			}
		}
		return existe;
	}
	

}






