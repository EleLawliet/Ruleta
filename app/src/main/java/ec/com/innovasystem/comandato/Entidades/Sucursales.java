package ec.com.innovasystem.comandato.Entidades;

import java.util.List;

/**
 * Created by Junior on 30/09/2016.
 */
public class Sucursales {

    private List<TiendasBean> tiendas;

    public List<TiendasBean> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<TiendasBean> tiendas) {
        this.tiendas = tiendas;
    }

    public static class TiendasBean {
        private int id;
        private String title;
        private String direccion;
        private String phone;
        private String horario;
        private String ciudad;
        private String lat;
        private String lng;
        private int zoom;
        public  TiendasBean(int id, String ciudad)
        {
            this.id=id;
            this.ciudad=ciudad;
        }
        public TiendasBean(int id, String title, String direccion, String phone, String horario, String lat, String lng, int zoom) {
            this.id = id;
            this.title = title;
            this.direccion = direccion;
            this.phone = phone;
            this.horario = horario;
            this.lat = lat;
            this.lng = lng;
            this.zoom = zoom;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHorario() {
            return horario;
        }

        public void setHorario(String horario) {
            this.horario = horario;
        }

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public int getZoom() {
            return zoom;
        }

        public void setZoom(int zoom) {
            this.zoom = zoom;
        }
    }

}
