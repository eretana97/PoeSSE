package com.utiles;

import java.awt.event.KeyEvent;

/**
 *
 * @author Raisa Ramírez
 */
public class KeyTyped {
    //Metodos para validaciones evento KeyTyped
    
    //221415
    public void validarEnteros(java.awt.event.KeyEvent evt){
        Character s = evt.getKeyChar();
        if (!Character.isDigit(s)) {
            evt.consume();
        }        
    }
    //Hola Mundo
    public void validarTexto(java.awt.event.KeyEvent evt){
        Character s = evt.getKeyChar();
        if (!Character.isLetter(s) && s!=KeyEvent.VK_SPACE) {
            evt.consume();
        }
    }
    //100.25
    public void validarReales(java.awt.event.KeyEvent evt, String valor){
        Character s= evt.getKeyChar();
        if (!Character.isDigit(s) && s!='.') {
            evt.consume();
        }
        if (s=='.' && valor.contains(".")) {
            evt.consume();
        }
    }
}
