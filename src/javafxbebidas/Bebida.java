package javafxbebidas;

import java.io.Serializable;

/**
 * Clase que representa un objeto de la clase Bebida.
 * 
 * Contiene los siguientes atributos:
 * 
 * - Nombre: nombre de la bebida
 * - Color: color de la bebida
 * - Unidades: unidades de la bebida
 * @author Adrián Romero Ramírez
 */
public class Bebida implements Serializable{
    
    protected String nombre; // Nombre de la bebida
    protected String color; // Color de la bebida
    protected int unidades; // Unidades de la bebida
    
    private static final long serialVersionUID = 42L;

    /**
     * Costructor de objetos Bebida con su nombre, color y unidades
     * @param nombre Nombre de la bebida
     * @param color Color de la bebida
     * @param unidades unidades de la bebida
     */
    public Bebida(String nombre, String color, int unidades) {
        this.nombre = nombre;
        this.color = color;
        this.unidades = unidades;
    }

    /**
     * Método para obtener el nombre de la bebida
     * @return nombre de la bebida
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para cambiar el nombre de la bebida
     * @param nombre nombre que se le quiere poner
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método para obtener el color de la bebida
     * @return color de la bebida
     */
    public String getColor() {
        return color;
    }

    /**
     * Método para cambiar el color de la bebida
     * @param color color que se le quiere poner
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Método para obtener las unidades de la bebida
     * @return unidades de la bebida
     */
    public int getUnidades() {
        return unidades;
    }

    /**
     * Método para cambiar las unidades de la bebida
     * @param unidades unidades que se le quiere poner
     */
    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }
    
    /**
     * Método que crea un String especifico para Bebida
     * @return String especifico de Bebida
     */
    @Override
    public String toString(){
        StringBuilder cad = new StringBuilder();
        cad.append(nombre);
        cad.append(" ");
        cad.append(color);
        cad.append(" ");
        cad.append(unidades);
        return cad.toString();
    }
    
}
