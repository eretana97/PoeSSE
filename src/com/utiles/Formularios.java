/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utiles;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author edgar
 */
public class Formularios {
    
    public String get(JTextField obj){
        return obj.getText();
    }
    
    public String get(JTextArea obj){
        return obj.getText();
    }
    
    public int get(JSpinner obj){
        return Integer.parseInt(obj.getValue().toString());
    }
    
    public ComboItem get(JComboBox<ComboItem> obj){
        return obj.getItemAt(obj.getSelectedIndex());
    }
    
    public int getVal(JComboBox<ComboItem> obj){
        return obj.getItemAt(obj.getSelectedIndex()).getValue();
    }
    
    public String getTxt(JComboBox<ComboItem> obj){
        return obj.getItemAt(obj.getSelectedIndex()).getText();
    }
    
    public Date getDate(JSpinner obj){
        SimpleDateFormat format = new SimpleDateFormat(obj.getValue().toString());
        return format.getCalendar().getTime();
    }
    
}
