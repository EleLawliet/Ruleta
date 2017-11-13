package ec.com.innovasystem.comandato.Entidades;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by InnovaCaicedo on 7/12/2016.
 */

public class Usuario {

    private int id;
    private String facwbookId;
    private String nombre;
    private String apellido;
    private String cedula;
    private String fecha;
    private String correo;
    private String direccionCasa;
    private String direccionTrabajo;
    private String telefono;
    private String celular;
    private String foto;
    private String monto;
    private boolean sesion;
    public Usuario(JSONObject pojoDatosPersonales, JSONObject usuario, JSONObject datosComandato){
        try {
            this.id = pojoDatosPersonales.getInt("usuario_id");
            this.nombre = pojoDatosPersonales.getString("nombre");
            this.apellido = pojoDatosPersonales.getString("apellido");
            this.cedula = pojoDatosPersonales.getString("cedula");
            this.fecha = pojoDatosPersonales.getString("fecha_nacimiento");
            this.correo = pojoDatosPersonales.getString("correo_personal");
            this.direccionCasa = pojoDatosPersonales.getString("direccion_casa");
            this.direccionTrabajo = pojoDatosPersonales.getString("direccion_trabajo");
            this.telefono = pojoDatosPersonales.getString("telefono");
            this.celular = pojoDatosPersonales.getString("celular");
            this.foto = pojoDatosPersonales.getString("foto");
            String face=usuario.getString("facebook_id");
            if (face==null || face.equalsIgnoreCase("null"))
                this.facwbookId = "facebook";
            else
                this.facwbookId= face;
            String mon=datosComandato.getString("cupo");
            if (mon==null || mon.equalsIgnoreCase("null"))
                this.monto="0";
            else
                this.monto=datosComandato.getString("cupo");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Usuario(){}
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccionCasa() {
        return direccionCasa;
    }

    public void setDireccionCasa(String direccionCasa) {
        this.direccionCasa = direccionCasa;
    }

    public String getDireccionTrabajo() {
        return direccionTrabajo;
    }

    public void setDireccionTrabajo(String direccionTrabajo) {
        this.direccionTrabajo = direccionTrabajo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public boolean isSesion() {
        return sesion;
    }

    public void setSesion(boolean sesion) {
        this.sesion = sesion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFacwbookId() {
        return facwbookId;
    }

    public void setFacwbookId(String facwbookId) {
        this.facwbookId = facwbookId;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
