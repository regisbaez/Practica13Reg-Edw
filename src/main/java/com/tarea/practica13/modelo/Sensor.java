package com.tarea.practica13.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Sensor {

    @Id @GeneratedValue
    private int idBD;
    private int id;
    private Long temperatura;
    private Long humedad;
    private String fecha;

    public Sensor() {

        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Date date = new Date();
        //random generated numbers for data test
        setId((int) (Math.random() * 100) + 1);
        setFecha(format.format(date));
        setHumedad((long) (Math.random() * 100) + 1);
        setTemperatura((long) (Math.random() * 100) + 1);
    }

    public Sensor(int id, Long temperatura, Long humedad, String fecha) {
        this.id = id;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.fecha = fecha;
    }

    public int getIdBD() {
        return idBD;
    }

    public void setIdBD(int idBD) {
        this.idBD = idBD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Long temperatura) {
        this.temperatura = temperatura;
    }

    public Long getHumedad() {
        return humedad;
    }

    public void setHumedad(Long humedad) {
        this.humedad = humedad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
