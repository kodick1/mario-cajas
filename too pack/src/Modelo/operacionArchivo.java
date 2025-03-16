/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class operacionArchivo {
    //crea el archivo en disco, recibe como parámetro la lista de estudiantes
	public static void crearArchivo(ArrayList<Jugador> lista) {
		FileWriter flwriter = null;
		try {
                    File fichero = new File("../puntuaciones.csv");
                    fichero.delete();// eliminamos el archivo anterior
			//crea el flujo para escribir en el archivo
			flwriter = new FileWriter("../puntuaciones.csv",true); // creamos uno nuevo con las modificaciones                   
                    try ( //crea un buffer o flujo intermedio antes de escribir directamente en el archivo
                            BufferedWriter bfwriter = new BufferedWriter(flwriter)
                    //bfwriter.write("PUNTUACION;JUGADOR\n");
                    ) {
                        for (Jugador jugador : lista) {
                            //escribe los datos en el archivo
                            bfwriter.write(+jugador.getPuntuacion() + ";" + jugador.getNombres() + "\n");
                        }
                        //cierra el buffer intermedio
                    }
			System.out.println("Archivo creado satisfactoriamente..");
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (flwriter != null) {
				try {//cierra el flujo principal
					flwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//crea el archivo en disco, retorna la lista de estudiantes
	public static ArrayList leerArchivo() {
		// crea el flujo para leer desde el archivo
		File file = new File("../puntuaciones.csv");
		ArrayList <Jugador>listaJugadores= new ArrayList<Jugador>();	
		Scanner scanner;
		try {
			//se pasa el flujo al objeto scanner
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				// el objeto scanner lee linea a linea desde el archivo
				String linea = scanner.nextLine();
				Scanner delimitar = new Scanner(linea);
				//se usa una expresión regular
				//que valida que antes o despues de una coma (,) exista cualquier cosa
				//parte la cadena recibida cada vez que encuentre una coma				
				
                                delimitar.useDelimiter("\\s*;\\s*");
				Jugador e= new Jugador();
				e.setPuntuacion(delimitar.nextInt());
				e.setNombres(delimitar.next());
				listaJugadores.add(e);
			}
			//se cierra el ojeto scanner
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return listaJugadores;
	}
        
        public static String leerArchivoMatriz(String mm){
            String linea;
            String arreglo[]; 
            String texto="";         
        try               
         { BufferedReader in = new BufferedReader ( new FileReader ( mm ) );         
          linea = in.readLine ();
                          
            
           while (linea != null)
           {
               String[]a =linea.split(" ");
               String Nombre= a[0]; 

               texto+=linea+"\n";
                         
               linea = in.readLine (); 
           }
           in.close ();           
           
         }         
        catch (IOException e)
         { System.out.println("Error al tratar de abrir el archivo.");
         } 
        return texto;
        }
    
}
