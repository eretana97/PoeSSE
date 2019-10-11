package com.utiles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Raisa Ramírez
 */
public class BotonesTabla extends DefaultTableCellRenderer{
    
    //Metodo para crear botones
    public JButton crearBoton(String toolTipText, String name, String iconPath) {
        
        /*        
        toolTipText: Texto de información
        name: Nombre del botón
        iconPath: Ruta del icono        
        */
        
        JButton button = new JButton(); 
        button.setBackground(new Color(100, 178, 205));
        Border line = new LineBorder(new Color(204,204,204));        
        Border margin = new EmptyBorder(0, 0, 0,0);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        button.setName(name);
        button.setToolTipText(toolTipText);  
        try {
            Image img = ImageIO.read(getClass().getResource(iconPath));            
            button.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return button;
    }
    
    //Función que permite crear componentes en celdas de jTable
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //Label dentro de celda
        if (value instanceof JLabel) {
           JLabel lbl = (JLabel)value;
           return lbl;
        }
        //Botón dentro de celda
        if (value instanceof JButton) {
           JButton btn = (JButton)value;
           return btn;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
