/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vistas;

/**
 *
 * @author edgar
 */
public class FormAdminSedesItca extends javax.swing.JInternalFrame {

    /** Creates new form FormAdminSedesItca */
    public FormAdminSedesItca() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_sede = new javax.swing.JTextField();
        btn_agregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_sedes = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Sedes Itca Fepade");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Nueva Sede:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        txt_sede.setName(""); // NOI18N
        getContentPane().add(txt_sede, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 290, -1));

        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/add.png"))); // NOI18N
        btn_agregar.setText("Agregar");
        btn_agregar.setIconTextGap(10);
        getContentPane().add(btn_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, -1, -1));

        tabla_sedes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Sede", "Accion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabla_sedes);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 450, 180));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/bg8.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 290));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_sedes;
    private javax.swing.JTextField txt_sede;
    // End of variables declaration//GEN-END:variables

}