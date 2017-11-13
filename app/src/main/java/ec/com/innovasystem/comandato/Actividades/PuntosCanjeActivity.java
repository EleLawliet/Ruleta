package ec.com.innovasystem.comandato.Actividades;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ec.com.innovasystem.comandato.Adaptadores.CustomAdapter;
import ec.com.innovasystem.comandato.Adaptadores.LugaresAdapter;
import ec.com.innovasystem.comandato.Entidades.ObjectHash;
import ec.com.innovasystem.comandato.Entidades.Sucursales;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.http.BaseActivity;
import ec.com.innovasystem.comandato.http.RequestApp;

public class PuntosCanjeActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    private Spinner spinner;
    CustomAdapter customAdapter;
    public List<Sucursales.TiendasBean> lista1;
    public RecyclerView listaLugares;
    private ImageView imgCerrarSesion;
    private LugaresAdapter adapter;
    public Gson gson = new Gson();
    public RecyclerView.LayoutManager lManager;
    public LinearLayout contentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.activity_puntos_canje);
        parametros.putInt("verbtnatras", 1);
        parametros.putInt("vertoolbar", 1);
        super.onCreate(parametros);
        imgCerrarSesion=(ImageView)findViewById(R.id.imgCerrarSesion);
        imgCerrarSesion.setOnClickListener(this);
        spinner=(Spinner)findViewById(R.id.spinner);
        lista1= new ArrayList<>();
        cargarAdaptador();
        customAdapter=new CustomAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, lista1);
        spinner.setAdapter(customAdapter);
        spinner.setOnItemSelectedListener(this);
        listaLugares = (RecyclerView)findViewById(R.id.lv_sucursales);
        lManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager          // HACERLO EN BLOQUE TIPO GALERIA 3 COLUMNAS
                = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);

        adapter= new LugaresAdapter(this);
        listaLugares.setLayoutManager(gridLayoutManager);
        listaLugares.setAdapter(adapter);
        contentView = (LinearLayout)findViewById(R.id.content_view);
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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void verGoogleMaps(Sucursales.TiendasBean tienda)
    {
        String uri = "http://maps.google.com/maps?q=loc:" + tienda.getLat() + "," + tienda.getLng() + " (" + tienda.getTitle() + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
        //Log.i("map","maps");
    }

    public void cargarAdaptador() {
        HashMap<String, String> objMapData = new HashMap<>();
        JSONObject jsonpost = null;
        try {
            objMapData.put("metodo", "todosCiudad");
            jsonpost = new JSONObject(objMapData);
            //Log.i("resutado","resultado "+jsonpost.toString());
            String rutaURL = Constantes.TIENDAS_COMANDATO;
            RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Log.i("resutado","resultado "+response.toString());
                    customAdapter.clear();
                    boolean guardar=analizarRespuestaJson(response, PuntosCanjeActivity.this);
                    if (guardar) {
                        customAdapter.clear();
                        JSONArray lista = response.optJSONArray("lista");
                        if(lista!=null && lista.length()>0) {
                            for (int i = 0; i < lista.length(); i++) {
                                try {
                                    Sucursales.TiendasBean tiendasBean = new Sucursales.TiendasBean(lista.getJSONObject(i).getInt("ciudad_id"), lista.getJSONObject(i).getString("nombre"));
                                    lista1.add(tiendasBean);
                                    // String ciudad=lista.getJSONObject(i).getString("nombre");
                                    //Log.i("ciudad"," ciudad "+ciudad);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            customAdapter.notifyDataSetChanged();
                        }
                        else
                            Toast.makeText(PuntosCanjeActivity.this,"No hay datos que mostrar", Toast.LENGTH_SHORT).show();
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
        } catch (Exception e) {
            //Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void cargarDatosCiudad(Sucursales.TiendasBean tienda) {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("ciudad_id", String.valueOf(tienda.getId())));
            jsonpost = crearRequestGeneral("tiendasXCiudad", lstHash);
            //Log.i("dats","datos "+jsonpost.toString());
            String rutaURL = Constantes.TIENDAS_COMANDATO;
            RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Log.i("resutado","resultado "+response);
                    boolean guardar=analizarRespuestaJson(response, PuntosCanjeActivity.this);
                    if (guardar) {
                        adapter.items.clear();
                        JSONArray lista = response.optJSONArray("lista");
                        if(lista!=null && lista.length()>0) {
                            for (int i = 0; i < lista.length(); i++) {
                                try {
                                    //Log.i("ciudad","ciudad "+lista.toString());
                                    int tienda_id = lista.getJSONObject(i).getInt("tienda_id");
                                    String title = lista.getJSONObject(i).getString("title");
                                    String direccion = lista.getJSONObject(i).getString("direccion");
                                    String phone = lista.getJSONObject(i).getString("telefono");
                                    String horario = lista.getJSONObject(i).getString("horario");
                                    String latitud = lista.getJSONObject(i).getString("lat");
                                    String longitud = lista.getJSONObject(i).getString("lng");
                                    int zoom = lista.getJSONObject(i).getInt("zoom");
                                    Sucursales.TiendasBean tiendasBean1 = new Sucursales.TiendasBean(tienda_id, title, direccion, phone, horario, latitud, longitud, zoom);
                                    adapter.items.add(tiendasBean1);
                                    //Log.i("ciudad", " title " + title);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else
                            Toast.makeText(PuntosCanjeActivity.this,"No hay datos que mostrar", Toast.LENGTH_SHORT).show();
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
        } catch (Exception e) {
            //Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId())
        {
            case R.id.spinner:
                Sucursales.TiendasBean tienda= lista1.get(position);
                cargarDatosCiudad(tienda);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgCerrarSesion:
                cerrarSesion();
                break;
        }
    }
}
