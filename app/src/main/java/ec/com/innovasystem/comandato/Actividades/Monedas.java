package ec.com.innovasystem.comandato.Actividades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ec.com.innovasystem.comandato.Adaptadores.MonedasAdapter;
import ec.com.innovasystem.comandato.Entidades.ObjectHash;
import ec.com.innovasystem.comandato.Entidades.TotalPuntos;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.http.BaseActivity;
import ec.com.innovasystem.comandato.http.RequestApp;

public class Monedas extends BaseActivity implements View.OnClickListener{
    public RecyclerView listaMonedas;
    MonedasAdapter adapter;
    private CircleImageView btnFoto;
    private ImageView imgCerrarSesion;
    private TextView textNombrePerfil, textCorreo, textFechaNacimiento;
    public RecyclerView.LayoutManager lManager;
    private ArrayList<TotalPuntos> lstPuntos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.activity_monedas);
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
        textCorreo=(TextView)findViewById(R.id.textCorreo);
        textCorreo.setText(obtenerDatosUsuario().getCorreo());
        textFechaNacimiento=(TextView)findViewById(R.id.textFechaNacimiento);
        textFechaNacimiento.setText("C.I. "+obtenerDatosUsuario().getCedula());
        imgCerrarSesion=(ImageView)findViewById(R.id.imgCerrarSesion);
        imgCerrarSesion.setOnClickListener(this);
        btnFoto=(CircleImageView)findViewById(R.id.btnFoto);
        verFoto();
        textNombrePerfil=(TextView)findViewById(R.id.textNombrePerfil);
        textNombrePerfil.setText(obtenerDatosUsuario().getNombre().toUpperCase()+" "+obtenerDatosUsuario().getApellido().toUpperCase());
        lstPuntos= new ArrayList<>();
        listaMonedas=(RecyclerView)findViewById(R.id.rv_listas);
        lManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager          // HACERLO EN BLOQUE TIPO GALERIA 3 COLUMNAS
                = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        adapter= new MonedasAdapter(this);
        listaMonedas.setLayoutManager(gridLayoutManager);
        listaMonedas.setAdapter(adapter);
        traerMonedas();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    public void verFoto()
    {
        if(obtenerDatosUsuario().getFoto()!=null && obtenerDatosUsuario().getFoto().length()>0) {
            Picasso.with(Monedas.this)
                    .load(obtenerDatosUsuario().getFoto())
                    .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                    .into(btnFoto, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            byte[] imageByteArray = Base64.decode(obtenerDatosUsuario().getFoto(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                            btnFoto.setImageBitmap(decodedByte);
                        }
                    });
        }
        else {
            Picasso.with(Monedas.this)
                    .load(R.drawable.com_facebook_profile_picture_blank_square)
                    .into(btnFoto);
        }
    }

    public void traerMonedas()
    {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("usuario_id", String.valueOf(obtenerDatosUsuario().getId())));
            jsonpost = crearRequestGeneral("obtenerTotalPuntosUsuario", lstHash);
            Log.i("param2","param "+jsonpost.toString());
            String rutaURL = Constantes.TIENDAS_COMANDATO;
            RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    boolean guardar=analizarRespuestaJson(response, Monedas.this,2);
                    if (guardar) {
                        adapter.items.clear();
                        JSONArray lista= response.optJSONArray("lista");
                        Log.i("mondas","monedas "+response.toString());
                        if (lista != null && lista.length() > 0) {
                            for(int i=0;i<lista.length();i++)
                            {
                                TotalPuntos monedas= new TotalPuntos(Parcel.obtain());
                                try {
                                    monedas.setNombre(lista.getJSONObject(i).getString("nombre"));
                                    monedas.setTotal(lista.getJSONObject(i).getInt("total"));
                                    monedas.setImage(lista.getJSONObject(i).getString("url_imagen"));
                                    //imagen
                                    adapter.items.add(monedas);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else Toast.makeText(Monedas.this,"No hay datos",Toast.LENGTH_SHORT).show();
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgCerrarSesion:
                cerrarSesion();
                break;
        }
    }
}
