package com.tarea.practica13.servicios;

import com.tarea.practica13.modelo.Sensor;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

public class Productor {

    public Productor() {

    }

    /**
     * @param cola
     * @throws Exception
     */
    public void enviarMensaje(String cola) throws JMSException {

        //Creando el connection factory indicando el host y puerto, en la trama el failover indica que reconecta de manera
        // automatica
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        //Crea un nuevo hilo cuando hacemos a conexión, que no se detiene cuando
        // aplicamos el metodo stop(), para eso tenemos que cerrar la JVM o
        // realizar un close().
        Connection connection = factory.createConnection("admin", "admin");
        connection.start();

        // Creando una sesión no transaccional y automatica.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Creamos o nos connectamos a la una cola, por defecto ActiveMQ permite
        // la creación si no existe. Si la cola es del tipo Queue es acumula los mensajes, si es
        // del tipo topic es en el momento.

        Queue queue = session.createQueue(cola);
//        Topic topic = session.createTopic(cola);


        // Creando el objeto de referencia para enviar los mensajes.
        MessageProducer producer = session.createProducer(queue);


//        String mensaje = mensajeEnviar;


        // Creando un simple mensaje de texto y enviando.


        while (true) {
            try {
                TimeUnit.SECONDS.sleep(2);
                Sensor data = new Sensor();
                CRUD<Sensor> crud = new CRUD<>(Sensor.class);
                crud.crear(data);
                String mensajeData = Json.toJson(data);
                TextMessage message = session.createTextMessage(mensajeData);

                producer.send(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
