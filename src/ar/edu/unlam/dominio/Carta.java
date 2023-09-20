package ar.edu.unlam.dominio;

public class Carta {
	private int codigo;
	private Palo palo;
	private Figura figura;

	public Carta(int codigo, Palo palo, Figura figura) {
		this.codigo = codigo;
		this.palo = palo;
		this.figura = figura;
	}

	public int getValor() {
		/*
		 * Debe retornar el valor asociado a la figura
		 */
		int valor = 0;

		switch (this.figura) {
			case AS:
				valor=1;
				break;
			case DOS:
				valor=2;
				break;
			case TRES:
				valor=3;
				break;
			case CUATRO:
				valor=4;
				break;
			case CINCO:
				valor=5;
				break;
			case SEIS:
				valor=6;
				break;
			case SIETE:
				valor=7;
				break;
			case OCHO:
				valor=8;
				break;
			case NUEVE:
				valor=9;
				break;
			case DIEZ:
			case J:
			case K:
			case Q:
				valor=10;
				break;
		}
		return valor;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Palo getPalo() {
		return palo;
	}

	public void setPalo(Palo palo) {
		this.palo = palo;
	}

	public Figura getFigura() {
		return figura;
	}

	public void setFigura(Figura figura) {
		this.figura = figura;
	}

	@Override
	public String toString() {
		return "Carta [codigo=" + codigo + ", palo=" + palo + ", figura=" + figura + "]";
	}
	
	

}
