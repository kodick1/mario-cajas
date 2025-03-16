

package Control;

import Modelo.JuegoM;
import Modelo.Jugador;
import Modelo.Sonido;
import Modelo.operacionArchivo;
import Vista.InOut;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Scanner;
import javax.swing.JOptionPane;
public class Control {
    
    public static void main(String[] args) {
        int res; 
        do{
            res = menuInicioJuego();//recibimos la opcion elegida por el usuario
        switch(res){
                case 0:{  play(); 
                    break;}
                case 1:{  mostrarPuntajes();
                     break;}
                case 2:{  System.exit(0);}
            }
        }while(res!=2); 
    }
    
    public  static int menuInicioJuego(){
        int seleccion;
        String m=""; 
        Sonido s= new Sonido();//auxiliar para el sonido
        m=operacionArchivo.leerArchivoMatriz("../Archivo1.txt");//Traemos el archivo a una variable (personaje de Stack Attack)
        //Ventana principal
        seleccion = JOptionPane.showOptionDialog(null,m+"","STACK ATTACK",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[] { "❤Play☺►", "✍Puntajes", "✖Exit" },"❤Play▷");
         
        if(seleccion==2){//Opcion "Salir"
                        s.sonido("../Sonido/Salir.wav");
                        try {   JOptionPane.showMessageDialog(null," Gracias por Jugar STACK ATTACK");
                        //Ponemos a "Dormir" el programa durante los ms que queremos
                        Thread.sleep(15*1000);} catch (InterruptedException e) {
                        System.out.println(e);}
         }else{s.sonido("../Sonido/boton.wav");}
        return seleccion;
    }

    public static void play(){//primera opcion 
        ArrayList<Jugador> listaLeida = new ArrayList<Jugador>();//Lista de tipo jugador
        listaLeida = operacionArchivo.leerArchivo();//traemos el archivo a la lista, si dicha lista existe
        InOut o=new InOut();// objeto
        int p = 0;// variable que guardara la puntuacion 
        boolean x=false;
        //objeto de tipo jugador para poder realizar la insercion en la lista (en caso de que se necesite crear un nuevo jugador)
        Jugador jugador = new Jugador();
        String n = o.PedirString("Nombre del jugador ");//variable guarda nombre del jugador        
        JuegoM i=new JuegoM();//objeto tipo juego
        p=i.Juego();// proceso juego (devuelve la puntuacion obtenida)
        //Recorremos la lista y modificamos la puntuacion (en caso de que se necesite modificar la puntuacion de un jugador existente)
        for(int c=0;c<listaLeida.size();c++){
            if(listaLeida.get(c).getNombres().equalsIgnoreCase(n) ){//realizamos la comparacion entre nombres
                //realizamos la comparacion de los puntajes(solo se modifica si el nuevo puntaje es mayor al anterior)
                if(p>=listaLeida.get(c).getPuntuacion()){
                    listaLeida.get(c).setPuntuacion(p);}//modificamos el puntaje
                x=true;
            }
        }
        // x==false quiere decir que el jugador no fue encontrado. por lo tanto hablamos de un nuevo jugador
        // y debemos realizar el proceso de insercion a la lista
        if(x==false){
           jugador.setNombres(n); //guardamos nombre
           jugador.setPuntuacion(p);//guardamos puntuacion
           listaLeida.add(jugador);//agregamos nuevo jugador
        }
        operacionArchivo.crearArchivo(listaLeida);
        o.Mostrar(" El jugador "+ n +" tiene el puntaje de "+p+" .");//imprimimos la informacion del jugador
    }
    
    public static void mostrarPuntajes(){// Segunda opcion 
        String m="";//Variable que acumula los nombres y puntajes de los jugadores
        ArrayList<Jugador> listaLeida = new ArrayList<Jugador>();// creamos una lista
        listaLeida = operacionArchivo.leerArchivo();//traemos el archivo a la lista
		for (Jugador jugador1 : listaLeida) {//recorremos la lista y acumulamos la informacion
			m+=jugador1.getPuntuacion()+ " " + jugador1.getNombres()+"\n";
		}
                JOptionPane.showMessageDialog(null,m);//imprimimos la informacion
    }
}