package ec.com.innovasystem.comandato.web;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Creado por Andres Cantos el 13/noviembre/2015
 */
public class RecursoVolley {
    private  static RecursoVolley objcolaPeticionesVolley = null;
    private RequestQueue colaVolley = null;

    /*constructor privado crea la cola de peticioneshttp*/
    private RecursoVolley(Context ctx){
        colaVolley =  Volley.newRequestQueue(ctx);
    }

    public static RecursoVolley getInstancia(Context ctx){
        if(objcolaPeticionesVolley == null){
            objcolaPeticionesVolley = new RecursoVolley(ctx);
        }
        return objcolaPeticionesVolley;
    }

    public RequestQueue getColaVolley() {
        return colaVolley;
    }
}
