
package Modelo;

import Vista.*;
import java.applet.Applet;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;

public class JuegoM {
    
    InOut o=new InOut();
    Sonido s= new Sonido();    
    String Matriz [][]=new String[7][12];
    int f=0;//variable global para el proceso de movimiento de las cajas
    // llenamos la matriz con caracteres que indiquen "espacios vacios"
    public void LlenarMatriz(){
        for(int f=0;f<7;f++){//recorremos filas
            for(int c=0;c<12;c++){//recorremos columnas
                Matriz[f][c]="≡";//igualamos
            }
        }
    }
    
    public String Mostrar(){
       String m="";//variable que acumula la informacion de la matriz
        for(int f=0;f<7;f++){//recorremos
            for(int c=0;c<12;c++){
                m+=Matriz[f][c]+" ";//acumulamos
            }
            m+="\n";
        }
        return m;
    }
    
    public int Juego(){
        LlenarMatriz();
        int puntuacion=0;// iniciamos la puntuacion en 0
        Random Al=new Random();// creamos una variable para la insercion de "cajas" aleatorias
        // v==true quiere decir que el jugador ha perdido
        // i=true quiere dedir que se debe insertar una nueva caja
        boolean v=false, i=false;
        int num=0;//contador de cajas iniciales
        int x=0;// determina la posicion en la que se insertara una nueva caja
        do{//genera las primeras cajas aleatoriamente
            generar();
            num++;
            
        }while(num!=12); 
        generarPersonaje();//genera aleatoriamente la posicion inicial del personaje
        //Proceso de movimiento y suma de puntaje
        do{
            if(i==true){
                x=Al.nextInt(12);//genera un numero aleatorio 
            }
            i=Cajas(x);// Se hace el proceso de insercion y movimiento de la caja
            v=Verificar();// verifica si existe una columna "llena"
            // se realiza la eliminacion de la fila en caso de que este "llena" y se aumenta la puntuacion dej jugador
            puntuacion+=EliminarFila();
            if(v==false){
                GenerarMovimientos(puntuacion);
            }else{
                o.Mostrar(Mostrar());
            } 
            if("☺".equals(Matriz[f][x])){
                v=true;// verifica si una caja cae en la misma posicion del personaje
            }
            
        }while(v==false);// se realiza el proceso hasta que el jugador "pierda"
        s.sonido("../Sonido/GameOver.wav");
        o.Mostrar("fin del juego");
        return puntuacion;
    }   
    public void generar(){//genera las 12 cajas iniciales
        Random Al=new Random();
        int x=Al.nextInt(12);
        int cont=0;
        while(cont<7&&Matriz[cont][x]!="■"){// hace que la caja llegue hasta la base de la matriz
                Matriz[cont][x]="■";//genera la caja
                if(cont>0){
                    Matriz[cont-1][x]="≡";
                }
             cont++;
        } 
    }
    
    public boolean Cajas(int x){
        boolean v=false;//boolean que determina el fin del proceso de desplazamiento de la caja
        //es decir, que la caja ha llegado a la base de la matriz
        //verifica que la posicion en la que se realizara la insercion este "vacia"
        if(Matriz[f][x]!="■"){
            Matriz[f][x]="■";//inserta en la posicion
            if(f>0){
                Matriz[f-1][x]="≡";// hace la posicion anterior vacia
            }
            if(f+1<7){
                // verifica si la  siguente posicion es una caja
                if(Matriz[f+1][x]=="■"){
                    f=7; //iguala filas a 7
                }
            }
            f++; 
            if(f>=7){// si filas es mayor o igual a 7, el proceso de desplazamiento ha terminado
                v=true;
                f=0;//reinicia f en 0 para la siguiente caja
            }
        }  
        return v;
    }
    
     public boolean Verificar (){// verifica si hay toda una columna llena de cajas
        int p;
        for(int c=0;c<12;c++){
            p=0;// contador para numero de cajas
            for(int f=0;f<7;f++){
                if(Matriz[f][c]=="■"){
                    p++;
                }
                if(p==7){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void generarPersonaje(){
        Random Al=new Random();
        int x=Al.nextInt(12);
        int cont=0;
        //Genera aleatoriamente la posicion en la que estara el personaje y realiza su proceso de desplazamiento
            while(cont<7&&!"■".equals(Matriz[cont][x])){
                Matriz[cont][x]="☺";
                if(cont>0){
                    Matriz[cont-1][x]="≡";
                }
                cont++;
            }
    }
    
     public void GenerarMovimientos(int punt){// Muestra el menu al usuario y le permite empezar a jugar
        String res=o.PedirString("Puntuacion: "+punt+"\n"+Mostrar()+"A. Izquierda \nD. Derecha \nQ. Diagonal izquierda \nE. Diagonal derecha");
        res=res.toLowerCase();
        switch (res){
                case "e":MovDiagonalDerecha();break;
                case "q":MovDiagonalizquierda();break;
                case "d":MovDerecha(); break;
                case "a":MovIzquierda();break;
                default:o.Mostrar("Opción no valida"); 
        }
    }
     
    public void MovDerecha(){
        int pf=0;//filas
        int pc=0;//columnas
        for(int ii=0;ii<7;ii++){//busca la posicion del personaje
            for(int c=0;c<12;c++){
                if("☺".equals(Matriz[ii][c])){
                    pf=ii;
                    pc=c;
                }
            }   
        }
        
        if(pc+1<12){// verifica que el personaje no se salga de la matriz al realizar el movimiento
            //Vacia la posicion actual y se ubica al personaje en la columna siguiente
            if("≡".equals(Matriz[pf][pc+1])){
               Matriz[pf][pc]="≡";
               Matriz[pf][pc+1]="☺";
               pc++; 
              
            while(pf+1<7){
            // si el personaje bajo en un "hueco" bajara hasta encontrar la base de la matriz o una caja
                if("≡".equals(Matriz[pf+1][pc])){
                    Matriz[pf][pc]="≡";
                    Matriz[pf+1][pc]="☺";
                }
                pf++;
            }
            }else{// si en frente del personaje hay una caja
                if(pc+2<12){// verifica que la caja no salga de la matriz
                    if("■".equals(Matriz[pf][pc+1])&&"≡".equals(Matriz[pf][pc+2])){
                        //Actualiza las posiciones de caja y personaje
                        if("≡".equals(Matriz[pf-1][pc+1])){
                            Matriz[pf][pc]="≡";
                            Matriz[pf][pc+1]="☺";
                            Matriz[pf][pc+2]="■";
                            Matriz[pf][pc+2]="■"; s.sonido("../Sonido/caida.wav");
                            pc++;
                        }else{
                            o.Mostrar("El movimiento no puede realizarse");
                        }
                        // si existe un hueco envia la caja hasta la base o hasta encotrar una caja distinta
                        while(pf+1<7){
                            if("≡".equals(Matriz[pf+1][pc+1])){
                                Matriz[pf][pc+1]="≡";
                                Matriz[pf+1][pc+1]="■";
                            }
                            pf++;
                        }
                    }else{
                        o.Mostrar("El movimiento no puede realizarse");
                    }
                }else{
                    o.Mostrar("El movimiento no puede realizarse");
                }
            }
        }else{
            o.Mostrar("El movimiento no puede realizarse");
        }
    }
     public void MovIzquierda (){
        int pf=0;
        int pc=0;
        for(int i=0;i<7;i++){
            for(int c=0;c<12;c++){
                if("☺".equals(Matriz[i][c])){
                    pf=i;
                    pc=c;
                }
            }   
        }
        if(pc-1>-1){
            if("≡".equals(Matriz[pf][pc-1])){
               Matriz[pf][pc]="≡";
               Matriz[pf][pc-1]="☺";
               pc--; 
              
            while(pf+1<7){
                if("≡".equals(Matriz[pf+1][pc])){
                    Matriz[pf][pc]="≡";
                    Matriz[pf+1][pc]="☺";
                }
                pf++;
            }
            }else{
                if(pc-2>=0){
                    if("■".equals(Matriz[pf][pc-1])&&"≡".equals(Matriz[pf][pc-2])){
                        if("≡".equals(Matriz[pf-1][pc-1])){
                            Matriz[pf][pc]="≡";
                            Matriz[pf][pc-1]="☺";
                            Matriz[pf][pc-2]="■";
                            s.sonido("../Sonido/caida.wav");
                            pc--;
                        }else{
                            o.Mostrar("El movimiento no puede realizarse");
                        }
                        
                        while(pf+1<7){
                            if("≡".equals(Matriz[pf+1][pc-1])){
                                Matriz[pf][pc-1]="≡";
                                Matriz[pf+1][pc-1]="■";
                            }
                            pf++;
                        }
                    }else{
                        o.Mostrar("El movimiento no puede realizarse");
                    }
                }else{
                    o.Mostrar("El movimiento no puede realizarse");
                }
                
            }
        
        }else{
                o.Mostrar("El movimiento no puede realizarse");
            }
    }
    
    public void MovDiagonalizquierda(){
        int pf=0;
        int pc=0;
        for(int i=0;i<7;i++){
            for(int c=0;c<12;c++){
                if("☺".equals(Matriz[i][c])){
                    pf=i;
                    pc=c;
                }
            }   
        }
        if(pc-1>=0 && pf-1>=0){
            //Si el personaje no tiene diagonalmente una caja se realia el proceso de mover a la izquierda
            if("≡".equals(Matriz[pf][pc-1])){
                MovIzquierda(); 
            }if("■".equals(Matriz[pf][pc-1])){// verifica si diagonalmente tiene una cara
                s.sonido("../Sonido/saltoMario.wav");
                if("≡".equals(Matriz[pf-1][pc-1])){//realiza el reemplazo
                     Matriz[pf][pc]="≡";
                     Matriz[pf-1][pc-1]="☺";
                }if(pc-2>=0){// verifica que no salga de la matriz
                    // realiza el movimiento en caso de que diagonalmente exista una caja
                        if("■".equals(Matriz[pf-1][pc-1]) && "≡".equals(Matriz[pf-1][pc-2]) && "≡".equals(Matriz[pf-2][pc-1])){
                             Matriz[pf][pc]="≡";
                             Matriz[pf-1][pc-1]="☺";
                             Matriz[pf-1][pc-2]="■"; 
                             s.sonido("../Sonido/caida.wav");
                             //Si la caja queda en medio de un "vacio" se realiza el desplacamiento hasta la base
                             while(pf<7){
                                    if("≡".equals(Matriz[pf][pc-2])){
                                        Matriz[pf-1][pc-2]="≡";
                                        Matriz[pf][pc-2]="■";
                                        Mostrar();
                                    }
                                    pf++;
                                }
                        }
                    }else{ //o.Mostrar("El movimiento en diagonal no puede realizarse");
                }
            }    
        }else { o.Mostrar("El movimiento no puede realizarse"); }    
    } // fin metodo MovDiagonalizquierda
    
     public void MovDiagonalDerecha(){
        int pf=0;
        int pc=0;
        for(int i=0;i<7;i++){
            for(int c=0;c<12;c++){
                if("☺".equals(Matriz[i][c])){
                    pf=i;
                    pc=c;
                }
            }   
        }
        if(pc+1<12 && pf-1>=0){
            if("≡".equals(Matriz[pf][pc+1])){
                MovDerecha();
                
            }if("■".equals(Matriz[pf][pc+1])){
                s.sonido("../Sonido/saltoMario.wav");
                if("≡".equals(Matriz[pf-1][pc+1])){
                     Matriz[pf][pc]="≡";
                     Matriz[pf-1][pc+1]="☺";
                    
                    
                }if(pc+2<12){
                        if("■".equals(Matriz[pf-1][pc+1]) && "≡".equals(Matriz[pf-1][pc+2]) && "≡".equals(Matriz[pf-2][pc+1])){
                             Matriz[pf][pc]="≡";
                             Matriz[pf-1][pc+1]="☺";                     
                             Matriz[pf-1][pc+2]="■";
                             s.sonido("../Sonido/caida.wav");

                             while( pf<7){

                                 if("≡".equals(Matriz[pf][pc+2])){
                                        Matriz[pf-1][pc+2]="≡";
                                        Matriz[pf][pc+2]="■";
                                        Mostrar();
                                    }
                                    pf++; 
                                }
                        } 
                }else{ //o.Mostrar("El movimiento en diagonal no puede realizarse");
                }
            }
        }else { o.Mostrar("El movimiento no puede realizarse"); }  
    }   

    public int EliminarFila (){// elimina la fila en caso de que este completa de cajas
        int p=0;// contador de cajas
        boolean n=false;
        int punt=0;
        for(int c=0;c<12;c++){//Recorre la ultima fila 
            if(Matriz[6][c]=="■"){
                p++;
            }
            if(p==12){
                n= true;
            }
        }
        if(n==true){
            for(int c=0;c<12;c++){// recorre nuevamente la ultimma fila y reemplaza las cajas por espacios vacios
                if(Matriz[6][c]=="■"){
                    Matriz[6][c]="≡";
                }
            }
            s.sonido("../Sonido/Fila.wav");o.Mostrar(Mostrar());
            actualizarMatrizPorPuntaje();
            punt+=10;//suma puntuacion
        }
        return punt;
    }
    
    //Intercambia las posiciones de la ultima fila con la anterior para su eliminacion 
    public void actualizarMatrizPorPuntaje(){
        for(int f=6;f>0;f--){// realiza el proceso hasta la segunda fila
            for(int c=11;c>=0;c--){
                Matriz[f][c]=Matriz[f-1][c];
            }
        }
        for(int c=0; c<12;c++){
        Matriz[0][c]="≡";//llena la primera fila de la matriz con espacios vacios
        }
        f++;
        if(f>=7){
            f=0;
        }
    }
}
