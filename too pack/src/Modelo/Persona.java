
package Modelo;

public class Persona {
    private double id;
    String nombres;
    
    public Persona (){
        
    }
    public Persona(String nombres,double id) {
        this.nombres=nombres;
        this.id = id;
    }
    public double getId(){
        return id;
    }
    public String getNombres(){
    return nombres;
    }
}