package com.vistas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @version 1.0
 * @author Edgar Retana
 */
public class FormMDI extends javax.swing.JFrame {

    public static enum FORMULARIOS{
        //PANTALLAS ALUMNO Y COOR
        SOLICITUD_SSE,HORARIO_SSE,CONTROL_SSE,FINALIZACION_SSE,
        HORARIO_COOR,COOR_SOLICITUDES,NOTAS_ALUMNO,
        HORARIOS_CONSULTAS,
        //PANTALLAS ADMINISTRADOR
        SEDES_ITCA,ESCUELAS,CARRERAS
    };
    
    private static enum USER_TYPE{
        ADMIN,COOR,ALUMNO
    }
    
    private JPanel sidebar = null;
    private int screen_height,screen_width;
    
    public FormMDI() {
        initComponents();
        
        //Calculamos las medidas de la pantalla local
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        screen_height = env.getMaximumWindowBounds().height;
        screen_width = env.getMaximumWindowBounds().width;
        
        //Maximiza la ventana al iniciar
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Estable el menu dependiendo del tipo de usuario
        sidebarType(USER_TYPE.ADMIN);
    }

    /**
     * Metodo que permite cambiar de pantalla mostrada en MDI
     * @param f VALOR ENUM PARA SELECCIONAR EL FORMULARIO REQUERIDO
     */
    public void navegacion(FORMULARIOS f){
        jdesktop.removeAll();
        jdesktop.repaint();
        JInternalFrame jif = null;
        switch(f){
            case SOLICITUD_SSE:
                jif = new FormEstadoSolicitudSSE();
            break;
            case HORARIO_SSE:
                jif = new FormHorarioSSE();
            break;
            case CONTROL_SSE:
                jif = new FormControlSSE();
            break;
            case FINALIZACION_SSE:
                jif = new FormFinalizacionSSE();
            break;
            case HORARIO_COOR:
                jif = new FormHorarioCoor();
            break;
            case COOR_SOLICITUDES:
                jif = new FormCoorSolicitudesSSE();
            break;
            case NOTAS_ALUMNO:
                jif = new FormNotasAlumno();
            break;
            case HORARIOS_CONSULTAS:
                jif = new FormHorariosConsultas();
            break;
            case SEDES_ITCA:
                jif = new FormAdminSedesItca();
            break;
            case ESCUELAS:
                jif = new FormAdminEscuelas();
            break;
            case CARRERAS:
                jif = new FormAdminCarreras();
            break;
        }
        
        jdesktop.add(jif);
        
        try{
            //El formulario no se puede Maximizar
            jif.setMaximizable(false);
            //El formulario centrado
            Dimension sizeP = jdesktop.getSize();
            Dimension sizeH = jif.getSize();
            jif.setLocation((sizeP.width - sizeH.width)/2, (sizeP.height - sizeH.height)/2);
            jif.show();
            jif.setSelected(true);
            
        }catch(PropertyVetoException e){
            
        }
        
    }
    
    public void sidebarChanges(Component com){
        for(Component c : sidebar.getComponents()){
            c.setBackground(new Color(48,80,101));
        }
        com.setBackground(new Color(50,50,50));
    }
    
    
    public void sidebarType(USER_TYPE u){
        switch(u){
            case COOR:
                sidebar = new PanelCoordinador();
            break;
            case ALUMNO:
                sidebar = new PanelEstudiante();
            break;
            case ADMIN:
                sidebar = new PanelAdmin();
            break;
        }
        
        sidebar.setBounds(0,0,200,screen_height);
        for(Component c : sidebar.getComponents()){
            c.addMouseListener(new SidebarClickEvent());
        }
        
        jPanel1.add(sidebar);
    }
    
    protected class SidebarClickEvent extends MouseAdapter{

        @Override
        public void mouseClicked(MouseEvent e) {
            String name = e.getComponent().getName();
            sidebarChanges(e.getComponent());
            
            switch(name){
                //PANTALLAS COORDINADOR
                case "horarios_coordinador":
                    navegacion(FORMULARIOS.HORARIO_COOR);
                break;
                case "solicitudes_sse":
                    navegacion(FORMULARIOS.COOR_SOLICITUDES);
                break;
                //PANTALLAS ALUMNO
                case "solicitud_sse":
                    navegacion(FORMULARIOS.SOLICITUD_SSE);
                break;
                case "horario_sse":
                    navegacion(FORMULARIOS.HORARIO_SSE);
                break;
                case "control_sse":
                    navegacion(FORMULARIOS.CONTROL_SSE);
                break;
                case "finalizacion_sse":
                    navegacion(FORMULARIOS.FINALIZACION_SSE);
                break;
                case "notas_ciclo":
                    navegacion(FORMULARIOS.NOTAS_ALUMNO);
                break;
                case "horarios_consulta":
                    navegacion(FORMULARIOS.HORARIOS_CONSULTAS);
                break;
                //PANTALLAS ADMIN
                case "sede_itca":
                    navegacion(FORMULARIOS.SEDES_ITCA);
                break;
                case "escuelas":
                    navegacion(FORMULARIOS.ESCUELAS);
                break;
                case "carreras":
                    navegacion(FORMULARIOS.CARRERAS);
                break;
            }
            
        }
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem3 = new javax.swing.JMenuItem();
        jdesktop = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mitem_logout = new javax.swing.JMenuItem();
        mitem_exit = new javax.swing.JMenuItem();

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jdesktop.setName(""); // NOI18N

        javax.swing.GroupLayout jdesktopLayout = new javax.swing.GroupLayout(jdesktop);
        jdesktop.setLayout(jdesktopLayout);
        jdesktopLayout.setHorizontalGroup(
            jdesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );
        jdesktopLayout.setVerticalGroup(
            jdesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 479, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 479, Short.MAX_VALUE)
        );

        jMenu1.setText("Ventana");

        mitem_logout.setText("Cerrar sesión");
        mitem_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitem_logoutActionPerformed(evt);
            }
        });
        jMenu1.add(mitem_logout);

        mitem_exit.setText("Cerrar aplicación");
        mitem_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitem_exitActionPerformed(evt);
            }
        });
        jMenu1.add(mitem_exit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jdesktop))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdesktop)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mitem_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitem_exitActionPerformed
        int x = JOptionPane.showConfirmDialog(jdesktop, "Desea cerrar la aplicación?","Cerrar aplicación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(x == 0) System.exit(0);
    }//GEN-LAST:event_mitem_exitActionPerformed

    private void mitem_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitem_logoutActionPerformed
        int x = JOptionPane.showConfirmDialog(jdesktop, "Desea cerrar la sesión de su usuario?","Cerrar sesión",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(x == 0) {
            FormLogin login = new FormLogin();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_mitem_logoutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMDI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JDesktopPane jdesktop;
    private javax.swing.JMenuItem mitem_exit;
    private javax.swing.JMenuItem mitem_logout;
    // End of variables declaration//GEN-END:variables
}
