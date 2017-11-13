package ec.com.innovasystem.comandato.web;

/**
 * Creado por Hermosa Programación.
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Creado por Hermosa Programación.
 * Clase que representa un cliente HTTP Volley
 */

public final class VolleySingleton {
    private  static VolleySingleton objcolaPeticionesVolley = null;
    private RequestQueue colaVolley = null;

    /*constructor privado crea la cola de peticioneshttp*/
    private VolleySingleton(Context ctx){
        colaVolley =  Volley.newRequestQueue(ctx);
    }

    public static VolleySingleton getInstance(Context ctx){
        if(objcolaPeticionesVolley == null){
            objcolaPeticionesVolley = new VolleySingleton(ctx);
        }
        return objcolaPeticionesVolley;
    }

    public RequestQueue getColaVolley() {
        return colaVolley;
    }

    public void addToRequestQueue(Request req) {
        this.getColaVolley().add(req);
    }
}