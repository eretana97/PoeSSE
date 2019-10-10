/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utiles;

import javax.swing.JOptionPane;

/**
 *
 * @author edgar
 */
public class Alertas {

    public boolean confirmacion(String msj){
        int res = JOptionPane.showConfirmDialog(null, msj);
        return res == 0;
    }
    
    public void alertaError(String msj){
        JOptionPane.showMessageDialog(null, msj, "Error Inesperado", JOptionPane.ERROR_MESSAGE);
    }
    
    public void alertaInfo(String msj){
        JOptionPane.showMessageDialog(null, msj, "Error Inesperado", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
