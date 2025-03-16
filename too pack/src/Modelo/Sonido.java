/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

/**
 *
 * @author Lenovo
 */
public class Sonido {
    
public  void sonido(String m) {    
    try{
   //AudioClip clip; //"../Sonido/saltoMario.wav"
   File Url=new File(m);
 
   AudioClip clip= Applet.newAudioClip(Url.toURI().toURL());
      clip.play();
      
      
 
     }catch(MalformedURLException ex){
         
     System.err.println(ex+" error");
     
     }
    
    
    
}    
}
