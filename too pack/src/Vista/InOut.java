
package Vista;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;

public class InOut extends JFrame {
    public int PedirEnteros (String m){
        int a=Integer.parseInt(JOptionPane.showInputDialog(m));
        return a;
    }
    public String PedirString (String m){
        String a=JOptionPane.showInputDialog(m);
        return a;
    }
    
    public void Mostrar(String m){
        JOptionPane.showMessageDialog(null,m);
    }
    
    public void MostrarDatos (String m,boolean v){
        Timer time=new Timer();
        TimerTask tarea=new TimerTask(){
            @Override
            public void run() {
                System.out.println(m);
                try{
                    new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
                }catch(Exception E){
                    
                }
            }
        };
        time.schedule(tarea, 0, 20000);
        if(v==true){
            time.cancel();
        }
    }
    
    public int ValidarNum(String m,String n, int min){//Validar que sea positiva la entrada o este en el rango dado
        int a=0;
        boolean v=false;
        do{
            try{
                v=false;
                a=PedirEnteros(m);
                if (a<min){
                    a=PedirEnteros(n);
                    v=true;
                }
            }catch (NumberFormatException excepcion) {
                    Mostrar("Ingrese una opcion valida");
                    v=true;
            }
        }while(v==true);
        return a;        
    }
    
    
}
