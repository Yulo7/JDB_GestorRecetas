package org.example;


public class Receta {

    private int id;
    private String nombre;
    private String categoria;
    private int tiempoPreparacion;
    private String dificultad;
    private int raciones;

    // Constructor completo
    public Receta(int id, String nombre, String categoria,
                  int tiempoPreparacion, String dificultad, int raciones) {

        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.tiempoPreparacion = tiempoPreparacion;
        this.dificultad = dificultad;
        this.raciones = raciones;
    }

    // Constructor para añadir una receta nueva
    public Receta(String nombre, String categoria,
                  int tiempoPreparacion, String dificultad, int raciones) {

        this.nombre = nombre;
        this.categoria = categoria;
        this.tiempoPreparacion = tiempoPreparacion;
        this.dificultad = dificultad;
        this.raciones = raciones;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public String getDificultad() {
        return dificultad;
    }

    public int getRaciones() {
        return raciones;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public void setRaciones(int raciones) {
        this.raciones = raciones;
    }

    @Override
    public String toString() {
        return "Receta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", tiempoPreparacion=" + tiempoPreparacion +
                ", dificultad='" + dificultad + '\'' +
                ", raciones=" + raciones +
                '}';
    }
}
