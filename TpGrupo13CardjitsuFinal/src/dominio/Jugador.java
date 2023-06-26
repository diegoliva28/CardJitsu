package dominio;

import java.util.Arrays;

public class Jugador {
	private String nombre;
	private Cartas[] mano;
	private int victorias;
	private int derrotas;

	/**
	 * @param nombre
	 */
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.mano = new Cartas[5];
		this.victorias=0;
		this.derrotas=0;
	}

	public void sumarVictoria() {
		int acumulador=1;
	this.victorias += acumulador;
		
	}

	public void sumarDerrota() {
		int acumulador=1;
		this.derrotas += acumulador;
		
	}
	// Recibe como parametro un array de tipo cartas.Cada carta del array de cartas
	// recibido se ira guardando en la mano del jugador.
	public void completarMano(Cartas[] cartasMano) {
		for (int i = 0; i < cartasMano.length; i++) {
			mano[i] = cartasMano[i];
		}

	}

	public String verMano() {
		String cartas = "";
		for (int i = 0; i < mano.length; i++) {
		cartas += " \nCarta "+(i+1) + " = " + mano[i].getElemento()+"("+mano[i].getNivel()+")";
		}
		return cartas;
	}

//Metodo que devuelve una carta de la mano del jugador segun la posicion que se le pase por parametro.
	public Cartas elegirCarta(int posicion) {
		Cartas aux;
		aux = mano[posicion];
		return aux;
	}

	// La carta elegida de la mano se vuelve null
	public void descartarCarta(int numero) {
		mano[numero] = null;

	}

	// Recibe como parametro una carta la cual se guardará si encuentra un espacio
	// null en la mano del jugador.

	public void agregarCartaMano(Cartas carta) {
		for (int i = 0; i < mano.length; i++) {
			if (mano[i] == null) {
				mano[i] = carta;
			}
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getVictorias() {
		return victorias;
	}

	public void setVictorias(int victorias) {
		this.victorias = victorias;
	}

	public int getDerrotas() {
		return derrotas;
	}

	public void setDerrotas(int derrotas) {
		this.derrotas = derrotas;
	}

	@Override
	public String toString() {
		return "Jugador [nombre=" + nombre + ", mano=" + Arrays.toString(mano) + ", victorias=" + victorias
				+ ", derrotas=" + derrotas + "]";
	}

}