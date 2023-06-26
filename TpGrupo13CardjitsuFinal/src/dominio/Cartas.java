package dominio;

public class Cartas {

	private int nivel;
	private TiposDeCarta elemento;
	/**
	 * @param nivel
	 * @param elemento
	 */
	public Cartas(int nivel, TiposDeCarta elemento) {
		this.nivel = nivel;
		this.elemento = elemento;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public TiposDeCarta getElemento() {
		return elemento;
	}
	public void setElemento(TiposDeCarta elemento) {
		this.elemento = elemento;
	}
	@Override
	public String toString() {
		return "" + elemento + "(" + nivel + ")";
	}
	
	
	
}