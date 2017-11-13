package ec.com.innovasystem.comandato.Entidades;

/**
 * Created by InnovaCaicedo on 23/12/2016.
 */

public class PremiosPromocionesObj {

    private int id;
    private String nombre;
    private int cantidad;
    private int nivel;
    private String image;
    private String image2;
    private int cantidadPromocion;
    private String caducidad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public int getCantidadPromocion() {
        return cantidadPromocion;
    }

    public void setCantidadPromocion(int cantidadPromocion) {
        this.cantidadPromocion = cantidadPromocion;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }
}
