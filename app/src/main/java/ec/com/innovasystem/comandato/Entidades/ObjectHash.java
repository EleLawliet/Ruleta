package ec.com.innovasystem.comandato.Entidades;

/**
 * Created by InnovaCaicedo on 3/12/2016.
 */

public class ObjectHash {

    private String nombre;
    private String valor;
    public ObjectHash(String nombre, String valor){
        this.nombre = nombre;
        this.valor = valor;
    }

    public ObjectHash(){}
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
