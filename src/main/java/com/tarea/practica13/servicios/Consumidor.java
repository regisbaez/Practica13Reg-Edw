package com.tarea.practica13.servicios;

import com.tarea.practica13.main.Main;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Consumidor {

    ActiveMQConnectionFactory factory;
    Connection connection;
    Session session;
    Queue queue;
    Topic topic;
    MessageConsumer consumer;
    String cola;


    /**
     *
     */
    public Consumidor(String cola) {
        this.cola = cola;
    }

    /**
     *
     * @throws JMSException
     */
    public void conectar() throws JMSException {

        //Creando el connection factory indicando el host y puerto, en la trama el failover indica que reconecta de manera
        // automatica
        factory = new ActiveMQConnectionFactory("admin", "admin", "failover:tcp://localhost:61616");


        //Crea un nuevo hilo cuando hacemos a conexión, que no se detiene cuando
        // aplicamos el metodo stop(), para eso tenemos que cerrar la JVM o
        // realizar un close().
        connection = factory.createConnection();


        // Arrancamos la conexión
        //Puede verlo en direccion por defecto de tu activemq local:
        //http://127.0.0.1:8161/admin/connections.jsp
        connection.start();

        // Creando una sesión no transaccional y automatica.
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Creamos o nos connectamos a la una cola, por defecto ActiveMQ permite
        // la creación si no existe. Si la cola es del tipo Queue es acumula los mensajes, si es
        // del tipo topic es en el momento.

        queue = session.createQueue(cola);
//        topic = session.createTopic(cola);


        consumer = session.createConsumer(queue);
        consumer.setMessageListener(message -> {

            try {
                TextMessage messageTexto = (TextMessage) message;
                Main.enviarMensaje(messageTexto.getText());
                System.out.println("El mensaje de texto recibido: " + messageTexto.getText()+" - "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }


    public void cerrarConexion() throws JMSException {
        connection.stop();
        connection.close();
    }
}
