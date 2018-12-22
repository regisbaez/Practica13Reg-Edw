package com.tarea.practica13.main;

import com.tarea.practica13.servicios.BootstrapService;
import com.tarea.practica13.servicios.Productor;
import org.apache.activemq.broker.BrokerService;

import javax.jms.JMSException;
import java.sql.SQLException;

public class EnviarMensajes {

    public static void main(String[] args) {

        String cola = " notificaciones.cola";

        try {
            BootstrapService.startDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BrokerService brokerService = new BrokerService();
        try {
            brokerService.addConnector("tcp://localhost:61616");
            brokerService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            new Productor().enviarMensaje(cola);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
