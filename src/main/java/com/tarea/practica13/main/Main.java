package com.tarea.practica13.main;

import com.tarea.practica13.servicios.Consumidor;
import com.tarea.practica13.servicios.Websocket;
import freemarker.template.Configuration;
import freemarker.template.Version;
import org.eclipse.jetty.websocket.api.Session;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class Main {

    public static List<Session> sessions = new ArrayList<>();

    public static void main(String[] args) {

        staticFiles.location("/static");

        Configuration configuration = new Configuration(new Version(2, 3, 3));
        configuration.setClassForTemplateLoading(Main.class, "/templates");

        String cola = "notificaciones.cola";
        webSocket("/nuevoMensaje", Websocket.class);

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);
        //check de path
        get("/", (request, response) -> new ModelAndView(null, "index.ftl"),freeMarkerEngine);

        Consumidor servicioConsumidor = new Consumidor(cola);
        try {
            servicioConsumidor.conectar();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void enviarMensaje(String text) {

        for (Session sesionConectada : sessions) {
            try {
                sesionConectada.getRemote().sendString(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
