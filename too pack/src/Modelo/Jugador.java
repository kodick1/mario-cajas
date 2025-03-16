
package Modelo;

public class Jugador extends Persona {
    private int puntuacion;
    
	//constructor vacio
	public Jugador() {
		
	}	
        
    public Jugador(int puntuacion, String nombres, double id) {
        super(nombres, id);
        this.puntuacion = puntuacion;
    }	
	//getters y setters
	public int getPuntuacion() {
		return puntuacion;
	}	
	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	
    
}
