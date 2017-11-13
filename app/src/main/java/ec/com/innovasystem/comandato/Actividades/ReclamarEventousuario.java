package ec.com.innovasystem.comandato.Actividades;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ec.com.innovasystem.comandato.Adaptadores.ReclamarEventosUsuarioAdapter;
import ec.com.innovasystem.comandato.Entidades.EventoUsuarioObj;
import ec.com.innovasystem.comandato.Entidades.ObjectHash;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.http.BaseActivity;
import ec.com.innovasystem.comandato.http.RequestApp;

public class ReclamarEventousuario extends BaseActivity implements View.OnClickListener{
    private RecyclerView lista;
    private RecyclerView.LayoutManager lManager;
    private ReclamarEventosUsuarioAdapter adapter;
    private ImageView imgCerrarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.activity_reclamar_eventousuario);
        parametros.putInt("verbtnatras", 1);
        parametros.putInt("vertoolbar", 1);
        super.onCreate(parametros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_backspace_white_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.BLANCO), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        imgCerrarSesion=(ImageView)findViewById(R.id.imgCerrarSesion);
        imgCerrarSesion.setOnClickListener(this);
        lista=(RecyclerView)findViewById(R.id.rv_listas);
        lManager = new LinearLayoutManager(this);
        lista.setLayoutManager(lManager);
        adapter= new ReclamarEventosUsuarioAdapter(ReclamarEventousuario.this);
        lista.setAdapter(adapter);
        traerEventosUsuario();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void traerEventosUsuario()
    {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("usuario_id", String.valueOf(obtenerDatosUsuario().getId())));
            jsonpost = crearRequestGeneral("obtenerEventoUsuario", lstHash);
            //Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            //Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;
        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.i("resutado","resultado "+response.toString());
                boolean guardar=analizarRespuestaJson(response, ReclamarEventousuario.this,2);
                if (guardar) {
                    adapter.items.clear();
                    String exito = response.optString("texto").trim();
                    JSONArray lista = response.optJSONArray("lista");
                    if(lista!=null && lista.length()>0) {
                        for(int i=0; i<lista.length();i++)
                        {
                            EventoUsuarioObj evento= new EventoUsuarioObj();
                            try {
                                evento.setId(lista.getJSONObject(i).getInt("evento_usuario_id"));
                                evento.setDescripcion(lista.getJSONObject(i).getString("nombreEventoComandato"));
                                evento.setNombre(lista.getJSONObject(i).getString("nombreEvento"));
                                evento.setFecha(lista.getJSONObject(i).getString("fecha_fin"));
                                evento.setCosto(lista.getJSONObject(i).getInt("cantidad"));
                                evento.setImage(lista.getJSONObject(i).getString("url_imagen"));
                                adapter.items.add(evento);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(ReclamarEventousuario.this,"No hay valores en este momento",Toast.LENGTH_SHORT).show();
                    }
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
                //Log.e("State", objVolleyError.toString());
               onConnectionFailed(objVolleyError);
            }
        });
        agregarPeticionHttp(request);
    }

    public void reclamarEventoUsuario(EventoUsuarioObj evento, final int pos, TextView textCanjear)
    {
        textCanjear.setEnabled(false);
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("evento_usuario_id", String.valueOf(evento.getId())));
            jsonpost = crearRequestGeneral("reclamarEventoUsuario", lstHash);
            //Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            //Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;
        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("resutado","resultado "+response.toString());
                String resultado = response.optString("resultado").trim();
                if (resultado.equalsIgnoreCase("ok")) {
                    String texto=response.optString("texto").trim();
                    adapter.items.remove(pos);
                    adapter.notifyDataSetChanged();
                    dialogoGeneral(texto, ReclamarEventousuario.this, 2);
                }
                else
                {
                    String error=response.optString("texto").trim();
                    dialogoGeneral(error, ReclamarEventousuario.this, 2);
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
                // Log.e("State", objVolleyError.toString());
                if (objVolleyError.networkResponse == null) {
                    //    Log.i(getString(R.string.name_by_log_definition), "JSON RESPUESTA: 500- Error Inesperado");
                    onConnectionFailed();

                } else {
                    onConnectionFailed();
                    //    Log.i(getString(R.string.name_by_log_definition), "JSON RESPUESTA: " + objVolleyError.networkResponse.statusCode + "-" + new String(objVolleyError.networkResponse.data));
                    //    objUtils.getProccessMessageObjectExceptionOAUTH(objVolleyError.networkResponse, getView(), objFather);
                }
            }
        });

        agregarPeticionHttp(request);
        textCanjear.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgCerrarSesion:
                cerrarSesion();
                break;
        }
    }
}
