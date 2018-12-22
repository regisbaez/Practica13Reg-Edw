package com.tarea.practica13.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BDService {

    private static BDService instancia;

    public static BDService getInstancia(){

        if (instancia == null)
            instancia = new BDService();
        return instancia;
    }

    public Connection connection(){
        Connection connection = null;

        try {
            String url = "jdbc:h2:tcp://localhost/~/jms";
            connection = DriverManager.getConnection(url, "", "");
        }catch (Exception e){
            Logger.getLogger(BDService.class.getName()).log(Level.SEVERE, null, e);
        }

        return connection;
    }
}
