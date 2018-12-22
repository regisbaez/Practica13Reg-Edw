package com.tarea.practica13.servicios;

import com.tarea.practica13.main.Main;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class Websocket {

    @OnWebSocketConnect
    public void conectando(Session usuario) {
        System.out.println("Conectando Usuario: " + usuario.getLocalAddress().getAddress().toString());
        Main.sessions.add(usuario);
    }

    @OnWebSocketClose
    public void cerrandoConexion(Session usuario, int statusCode, String reason) {
        System.out.println("Desconectando el usuario: " + usuario.getLocalAddress().getAddress().toString());
        Main.sessions.remove(usuario);
    }
}
