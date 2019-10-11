/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utiles;

import java.awt.Panel;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Raisa Ramírez
 */
public class SendMail {
    
    //Método para el envio de correos
    
    public void sendMail(String[] addresses,int proceso){
        
        /*Tipos de correo por proceso
        0 = Inicio SSE
        1 = Coordinador RECHAZA - AUTORIZA - HACE OBSERVACIONES
        2 = Estudiante llena formulario de finalización
        3 = Estado solvente
        */
        
        //Estructuras de mensaje por defecto
        String[] defaultAsunto = {"Solicitud enviada",
                                  "Solicitud pendiente",
                                  "Estado actualizado",
                                  "Formulario de finalización enviado",
                                  "Solvencia pendiente",
                                  "Solvencia otorgada"
                                };
        String[] defaultMensaje = {"Su solicitud para iniciar servicio social ha sido enviada",
                                   "Tiene una nueva solicitud",
                                   "El estado de su solicitud ha sido actualizado",
                                   "Ha completado el formulario de finalización",
                                   "Tiene solicitudes pendientes de solvencia",
                                   "Se ha otorgado solvencia en su servicio social"
                                };
        //Configuraciones de correo
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "25");
        propiedad.setProperty("mail.smtp.auth","true"); 
        
        
        //Datos de correo administrador del sistema - LLENAR
        /*-----------------------------------------------------------
         ----------------------------------------------------------- */
        Session sesion = Session.getDefaultInstance(propiedad);
        String correoEnvia = "";        
        String contrasena = "";   
        /*-----------------------------------------------------------
         ----------------------------------------------------------- */
        
        
        //Asunto y mensaje segun proceso de solicitud
        String[] asunto = new String[2];
        String[] mensaje = new String[2];
        switch (proceso) {
            case 0:
                asunto[0] = defaultAsunto[0];
                mensaje[0] = defaultMensaje[0];
                asunto[1] = defaultAsunto[1];
                mensaje[1] = defaultMensaje[1];
                break;
            case 1:
                asunto[0] = defaultAsunto[2];
                mensaje[0] = defaultMensaje[2];
                break;
            case 2:
                asunto[0] = defaultAsunto[3];
                mensaje[0] = defaultMensaje[3];
                asunto[1] = defaultAsunto[4];
                mensaje[1] = defaultMensaje[4];
                break;
            case 3:
                asunto[0] = defaultAsunto[5];
                mensaje[0] = defaultMensaje[5];
                break;            
        }        
        MimeMessage mail = new MimeMessage(sesion);
        try {
            mail.setFrom(new InternetAddress (correoEnvia));
            for (int i = 0; i < asunto.length; i++) { 
                mail.setRecipient(Message.RecipientType.TO, new InternetAddress(addresses[i]));                 
                mail.setSubject(asunto[i]);
                mail.setText(mensaje[i]);
                mail.setContent(mensaje[i], "text/html; charset=utf-8");
                
                Transport transportar = sesion.getTransport("smtp");
                transportar.connect(correoEnvia,contrasena);
                transportar.sendMessage(mail, mail.getAllRecipients());                 
                transportar.close();
                System.out.println("Correo enviado");
            }           
            
        } catch (AddressException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
