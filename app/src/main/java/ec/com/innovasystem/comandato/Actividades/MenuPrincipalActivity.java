package ec.com.innovasystem.comandato.Actividades;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonArray;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tooltip.Tooltip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import ec.com.innovasystem.comandato.Entidades.Nivel;
import ec.com.innovasystem.comandato.Entidades.ObjectHash;
import ec.com.innovasystem.comandato.Entidades.SectoresRuleta;
import ec.com.innovasystem.comandato.Entidades.TotalPuntos;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.Ruleta.RuletaActivity;
import ec.com.innovasystem.comandato.Utils.DownloadFile;
import ec.com.innovasystem.comandato.http.BaseActivity;
import ec.com.innovasystem.comandato.http.InputStreamVolleyRequest;
import ec.com.innovasystem.comandato.http.RequestApp;
import ec.com.innovasystem.comandato.http.RequestImage;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MenuPrincipalActivity extends BaseActivity implements View.OnClickListener {

    public static Context activity;
    ArrayList lstPuntosMaximo ;
    private Button participar;
    private ImageButton puntos_canje ;
    private RelativeLayout descuentos ;
    private RelativeLayout premios ;
    private Button ly_Monedas;
    private TextView textConsulta, textPuntos,textBonificaciones, textMonto, textCircle, textIntent, tetxNivel2;
    private ImageButton ly_reclamar;
    private Button ly_premiosobtenidos;
    private Button perfil ;
    private List<Nivel> lstNivel;
    private Button btnSubir;

    ArrayList<Integer> lstColores;

    private ImageView btnCompartir;
    private ImageView imgCerrarSesion;
    private ImageView btnInstrucciones;
    private ImageButton consultas ;
    private boolean compartiendo=false;
    private CircleImageView btnFoto;
    private TextView textNombre, textCorreo, textNivel, textIntentos;
    private Tooltip.Builder builder;
    private boolean jsonItems=false, jsonPuntosAnteriores=false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.activity_menu_principal);
        parametros.putInt("verbtnatras", 1);
        parametros.putInt("vertoolbar", 1);
        super.onCreate(parametros);
        Bundle extras = getIntent().getExtras();
        FirebaseMessaging.getInstance().subscribeToTopic("notificacion_general"); // NOS SUSCRIBIMOS EN UN TOPIC POR PARTE DEL SERVIDOR
        //FirebaseMessaging.getInstance().unsubscribeFromTopic("notificacion_general_ios"); // NOS SUSCRIBIMOS EN UN TOPIC POR PARTE DEL SERVIDOR
        //FirebaseMessaging.getInstance().unsubscribeFromTopic("barcelona"); // NOS SUSCRIBIMOS EN UN TOPIC POR PARTE DEL SERVIDOR
        Typeface tf=Typeface.createFromAsset(getAssets(), "tipografias/DaxWide-Bold.otf");
        Typeface tf2=Typeface.createFromAsset(getAssets(), "tipografias/DaxWide-Light.otf");
        btnInstrucciones=(ImageView)findViewById(R.id.btnInstrucciones);
        btnInstrucciones.setOnClickListener(this);
        participar =(Button) findViewById(R.id.ly_participar);
        participar.setTypeface(tf);
        puntos_canje=(ImageButton) findViewById(R.id.ly_puntocanje);
        consultas=(ImageButton) findViewById(R.id.ly_consultas);
        premios=(RelativeLayout) findViewById(R.id.ly_premios_content);
        perfil=(Button) findViewById(R.id.ly_perfil);
        //perfil.setTextSize(5*getResources().getDisplayMetrics().density);
        perfil.setTypeface(tf);
        descuentos=(RelativeLayout) findViewById(R.id.ly_desc_content);
        ly_reclamar=(ImageButton)findViewById(R.id.ly_reclamar);
        ly_reclamar.setOnClickListener(this);
        ly_premiosobtenidos=(Button)findViewById(R.id.ly_premiosobtenidos);
        ly_premiosobtenidos.setTypeface(tf);
        ly_Monedas=(Button)findViewById(R.id.ly_Monedas);
        ly_Monedas.setTypeface(tf);
        textCircle=(TextView)findViewById(R.id.textCircle);
        textCircle.setTypeface(tf);
        textMonto=(TextView)findViewById(R.id.textMonto);
        textMonto.setTypeface(tf2);
        textConsulta=(TextView)findViewById(R.id.textConsulta);
        textConsulta.setTypeface(tf);
        textPuntos=(TextView)findViewById(R.id.textPuntos);
        textPuntos.setTypeface(tf);
        textIntent=(TextView)findViewById(R.id.textIntent);
        textIntent.setTypeface(tf2);
        textNombre=(TextView)findViewById(R.id.textNombre);
        textNombre.setTypeface(tf);
        tetxNivel2=(TextView)findViewById(R.id.tetxNivel2);
        tetxNivel2.setTypeface(tf2);
        textCorreo=(TextView)findViewById(R.id.textCorreo);
        textCorreo.setTypeface(tf2);
        textNivel=(TextView)findViewById(R.id.textNivel);
        textBonificaciones=(TextView)findViewById(R.id.textBonificaciones);
        textBonificaciones.setTypeface(tf);
        imgCerrarSesion=(ImageView)findViewById(R.id.imgCerrarSesion);
        textIntentos=(TextView)findViewById(R.id.textIntentos);
        imgCerrarSesion.setOnClickListener(this);
        btnSubir=(Button) findViewById(R.id.btnSubir);
        //btnSubir.setTextSize(5*getResources().getDisplayMetrics().density);
        btnSubir.setTypeface(tf);
        btnSubir.setOnClickListener(this);
        btnCompartir=(ImageView)findViewById(R.id.btnCompartir);
        btnCompartir.setOnClickListener(this);
        btnFoto=(CircleImageView)findViewById(R.id.btnFoto);
        lstNivel= new ArrayList<>();
        participar.setOnClickListener(this);
        puntos_canje.setOnClickListener(this);
        consultas.setOnClickListener(this);
        perfil.setOnClickListener(this);
        descuentos.setOnClickListener(this);
        premios.setOnClickListener(this);
        ly_Monedas.setOnClickListener(this);
        ly_premiosobtenidos.setOnClickListener(this);
        verFoto();
        verDatos();
        traerNivel();
        traerIntentos();
        traerEventosUsuario();
        //revisarDatosACargar();
    }

    public void traerColores(){
        JSONObject object=leerArchivoUso();
        //CONSULTA LOS COLORES QUE HAY QUE TRAER DE A BASE
            lstColores= new ArrayList<>();
            lstColores.add(Color.parseColor("#30B950"));
            lstColores.add(Color.parseColor("#1129DC"));
            lstColores.add(Color.parseColor("#30B950"));
            lstColores.add(Color.parseColor("#1129DC"));
            lstColores.add(Color.parseColor("#30B950"));
            lstColores.add(Color.parseColor("#1129DC"));
            lstColores.add(Color.parseColor("#30B950"));
            lstColores.add(Color.parseColor("#1129DC"));
        /*if(object.isNull("coloresRuleta")){
        } else{
            try {
                String colores= new String(object.getString("coloresRuleta"));
                int opaqueRed = Color.parseColor("0xff0000ff");
                for(String color:colores.split(";")){

                    lstColores.add(Color.parseColor(color.trim()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void revisarDatosACargar(){
        crearArchivo(MenuPrincipalActivity.this,"DatosACargar",Constantes.datosIniciales);
    }

    public void compararDatos(String id){
        //lee llos datos del servicio y recibe el string que debe contener un json
        //String datosNuevos="{\"id\":\"2\"},{\"foto1\":\"ubicacionPrimera\"},{\"foto2\":\"ubicacionSegunda\"},{\"foto3\":\"ubicacionTercera\"},{\"foto4\":\"ubicacionCuarta\"},{\"foto5\":\"ubicacionQuinta\"},{\"color1\":\"color\"},{\"color2\":\"color\"},{\"color3\":\"color\"},{\"color4\":\"color\"},{\"color5\":\"color\"},{\"color6\":\"color\"},{\"color7\":\"color\"},{\"color8\":\"color\"},{\"musica\":\"ubicacionMusica\"}";
        //actualizarDatos(datosNuevos);
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("id", id));
            jsonpost = crearRequestGeneral("obtenerPlantilla", lstHash);
            Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;

        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean guardar=analizarRespuestaJson(response, MenuPrincipalActivity.this, 2);
                if (guardar) {
                    JSONArray lista=response.optJSONArray("lista");
                    try {
                        if(!lista.getJSONObject(0).isNull("disenioApp")){
                            actualizarDatos(MenuPrincipalActivity.this,lista.getJSONObject(0).getString("disenioApp"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("todos","todos "+response.toString());
                }
                onConnectionFinished();
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
                Log.e("State", objVolleyError.toString());
                dialogo(getString(R.string.tituloInstrucciones).toUpperCase(),getString(R.string.textoInstrucciones).toUpperCase());
                onConnectionFailed(objVolleyError);
            }
        });

        agregarPeticionHttp(request);
    }



    public void verDatos()
    {
        textNombre.setText(obtenerDatosUsuario().getNombre().toUpperCase());
        textMonto.setText("Cupo: "+obtenerDatosUsuario().getMonto());
        textCorreo.setText(obtenerDatosUsuario().getCorreo());
    }

    public void verFoto()
    {
        //Log.i("foto","foto "+getSesion().obtenerUserData().getFoto());
        if(obtenerDatosUsuario().getFoto()!=null && obtenerDatosUsuario().getFoto().length()>0) {
            Picasso.with(MenuPrincipalActivity.this)
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
            Picasso.with(MenuPrincipalActivity.this)
                    .load(R.drawable.com_facebook_profile_picture_blank_square)
                    .into(btnFoto);
        }
       /* Log.i("foto face","foto fce "+obtenerDatosUsuario().getFoto());
        Glide.with(MenuPrincipalActivity.this)
                .load(obtenerDatosUsuario().getFoto())
                .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                .diskCacheStrategy( DiskCacheStrategy.SOURCE     )
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        byte[] imageByteArray = Base64.decode(obtenerDatosUsuario().getFoto(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(imageByteArray, 0,imageByteArray.length);
                        btnFoto.setImageBitmap(decodedByte);
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(btnFoto);*/

    }


    @Override
    protected void onResume() {
        super.onResume();
        verFoto();
        verDatos();
        traerNivel();
        traerIntentos();
        traerEventosUsuario();
        participar.setEnabled(true);
        ly_reclamar.setEnabled(true);
        consultas.setEnabled(true);
        perfil.setEnabled(true);
        puntos_canje.setEnabled(true);
        premios.setEnabled(true);
        ly_Monedas.setEnabled(true);
        if (compartiendo==true) {
            dialogoGeneral("Excelente acabas de compartir la aplicación", MenuPrincipalActivity.this, 2);
            compartiendo=false;
        }
    }

    public void imagenes(final String url, final String nombre){
            ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    Log.i("response","response "+response);
                    guardarImagen(response,nombre);
                    onConnectionFinished();
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError objVolleyError) {
                            Log.i("error","error "+objVolleyError.getMessage());
                            onConnectionFailed(objVolleyError);
                        }
                    });
            agregarPeticionHttp(request);
    }

    public void descargarArchivo(String url){
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, url, new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] response) {
                guardarArchivo(response,"tonoRuleta.min");
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
                Log.i("error","error "+objVolleyError.getMessage());
                onConnectionFailed(objVolleyError);
            }
        }, new HashMap<String,String>());
        agregarPeticionHttp(request);
    }

    public void todosPuntos()
    {
        JSONObject jsonpost = null;
        try {
            jsonpost = crearRequestGeneral("todosPuntos");
            Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;

        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("resutado","resultado  subir de nivel "+response.toString());
                boolean guardar=analizarRespuestaJson(response, MenuPrincipalActivity.this, 2);
                if (guardar) {
                    lstPuntosMaximo= new ArrayList();
                    JSONArray lista= response.optJSONArray("lista");
                    for (int i=0; i<lista.length();i++){
                        TotalPuntos puntos= new TotalPuntos(Parcel.obtain());
                        try {
                            puntos.setId(lista.getJSONObject(i).getInt("punto_id"));
                            if(lista.getJSONObject(i).isNull("cantidad_padre")){
                                puntos.setPadre(0);
                            }else{
                                puntos.setPadre(lista.getJSONObject(i).getInt("cantidad_padre"));
                            }

                            lstPuntosMaximo.add(puntos);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.i("todos","todos "+response.toString());
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
                Log.e("State", objVolleyError.toString());
                onConnectionFailed(objVolleyError);
            }
        });

        agregarPeticionHttp(request);
    }

    public void traerSubir()
    {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("usuario_id", String.valueOf(obtenerDatosUsuario().getId())));
            jsonpost = crearRequestGeneral("subirNivel", lstHash);
            Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;

        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("resutado","resultado  subir de nivel "+response.toString());
                boolean guardar=analizarRespuestaJson(response, MenuPrincipalActivity.this, 2);
                if (guardar) {
                    String texto= response.optString("texto");
                    JSONArray lista= response.optJSONArray("lista");
                    if (lista != null && lista.length() > 0) {
                        for (int i = 0; i < lista.length(); i++) {
                            try {
                                textNivel.setText(lista.getJSONObject(i).getString("nivel_id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    dialogoGeneral(texto, MenuPrincipalActivity.this, 2);
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
                Log.e("State", objVolleyError.toString());
                    onConnectionFailed(objVolleyError);
            }
        });

        agregarPeticionHttp(request);
    }


    public void traerNivel()
    {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("usuario_id", String.valueOf(obtenerDatosUsuario().getId())));
            jsonpost =  crearRequestGeneral("obtenerNivelUsuario", lstHash);
            Log.i("param2","param "+jsonpost.toString());
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
                boolean guardar=analizarRespuestaJson(response, MenuPrincipalActivity.this);
                if (guardar) {
                    JSONArray lista= response.optJSONArray("lista");
                    if (lista != null && lista.length() > 0) {
                        lstNivel.clear();
                        for(int i=0;i<lista.length();i++)
                        {
                            Nivel nivel= new Nivel();
                            try {
                                nivel.setId(lista.getJSONObject(i).getInt("nivel_usuario_id"));
                                nivel.setFecha(lista.getJSONObject(i).getString("fecha_creacion"));
                                nivel.setNivel(lista.getJSONObject(i).getInt("nivel_id"));
                                textNivel.setText(String.valueOf(lista.getJSONObject(i).getInt("nivel_id")));
                                lstNivel.add(nivel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else Toast.makeText(MenuPrincipalActivity.this,"No hay datos",Toast.LENGTH_SHORT).show();
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
                  onConnectionFailed(objVolleyError);
            }
        });
        agregarPeticionHttp(request);
    }

    public void traerIntentos()
    {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("usuario_id", String.valueOf(obtenerDatosUsuario().getId())));
            jsonpost = crearRequestGeneral("obtenerIntentosUsuario", lstHash);
            Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            //Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;

        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean guardar=analizarRespuestaJson(response, MenuPrincipalActivity.this);
                if (guardar) {
                    JSONArray lista= response.optJSONArray("lista");
                    Log.i("object","object intentos "+response.toString());
                    for(int i=0;i<lista.length();i++) {
                        try {
                            textIntentos.setText(lista.getJSONObject(i).getString("intentos"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    public void sectoresRuleta()
    {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("usuario_id", String.valueOf(obtenerDatosUsuario().getId())));
            jsonpost = crearRequestGeneral("obtenerItemsRuletaUsuario", lstHash);
            Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            //Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;
        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Log.i("sector","sector response "+response.toString());
                boolean guardar=analizarRespuestaJson(response, MenuPrincipalActivity.this);
                try {
                    if (guardar) {
                        JSONObject lista = response.optJSONObject("lista");
                        if (lista.optJSONArray("itemsRuleta") == null) {
                            Log.i("valores", "valores " + jsonItems + " anteriores " + jsonPuntosAnteriores);
                        }
                        JSONArray puntosAnteriores = lista.optJSONArray("totalPuntosAnterior");
                        JSONArray itemsRuleta = lista.optJSONArray("itemsRuleta");
                        if (itemsRuleta != null && itemsRuleta.length() > 0 && puntosAnteriores != null && puntosAnteriores.length() > 0) {
                            participar.setEnabled(false);
                            ArrayList lstRuleta = new ArrayList<>();
                            ArrayList lstPuntosA = new ArrayList<>();
                            for (int i = 0; i < itemsRuleta.length(); i++) {
                                SectoresRuleta sector = new SectoresRuleta(Parcel.obtain());
                                try {
                                    sector.setPos(i);
                                    sector.setItem_id(itemsRuleta.getJSONObject(i).getInt("item_id"));
                                    sector.setPuntos(itemsRuleta.getJSONObject(i).getString("puntos"));
                                    sector.setCantidad(itemsRuleta.getJSONObject(i).getInt("cantidad"));
                                    lstRuleta.add(sector);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            for (int i = 0; i < puntosAnteriores.length(); i++) {
                                TotalPuntos monedas = new TotalPuntos(Parcel.obtain());
                                try {
                                    monedas.setNombre(puntosAnteriores.getJSONObject(i).getString("nombre"));
                                    monedas.setTotal(puntosAnteriores.getJSONObject(i).getInt("total"));
                                    monedas.setImage(puntosAnteriores.getJSONObject(i).getString("url_imagen"));
                                    monedas.setId(puntosAnteriores.getJSONObject(i).getInt("punto_id"));
                                    lstPuntosA.add(monedas);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Log.i("sectorRuleta","sector ruleta numero de opciones menu principal "+lstRuleta.size());
                            Intent intent = new Intent(MenuPrincipalActivity.this, RuletaActivity.class);
                            intent.putParcelableArrayListExtra("listaRuleta", (ArrayList<? extends Parcelable>) lstRuleta);
                            intent.putParcelableArrayListExtra("listaPuntoAnt", (ArrayList<? extends Parcelable>) lstPuntosA);
                            intent.putIntegerArrayListExtra("listaColores",  lstColores);
                            intent.putParcelableArrayListExtra("listaPuntosMaximos",lstPuntosMaximo);
                            startActivity(intent);
                        } else
                            Toast.makeText(MenuPrincipalActivity.this, "No hay datos intente mas tarde", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Log.i("error",e.getMessage());
                    Toast.makeText(MenuPrincipalActivity.this,"Error intente mas tarde",Toast.LENGTH_LONG).show();
                    finish();
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
                Log.e("State", objVolleyError.toString());
                onConnectionFailed(objVolleyError);
            }
        });
        agregarPeticionHttp(request);
    }

    public void guardarCompartir(String nombreApp)
    {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("usuario_id", String.valueOf(obtenerDatosUsuario().getId())));
            lstHash.add(new ObjectHash("referencia", nombreApp));
            jsonpost = crearRequestGeneral("eventoCompartir", lstHash);
            Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;
        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("sector","sector response "+response.toString());
                String resultado = response.optString("resultado").trim();
                try {
                    if (resultado.equalsIgnoreCase("ok")) {
                       // String texto=response.optString("texto").toString();
                        compartiendo=true;
                    }
                    else {
                        String error = response.optString("texto").trim();
                        dialogoGeneral(error, MenuPrincipalActivity.this,2);
                    }
                }
                catch (Exception e)
                {
                    Log.i("error",e.getMessage());
                    Toast.makeText(MenuPrincipalActivity.this,"Error intente mas tarde",Toast.LENGTH_LONG).show();
                    finish();
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
                Log.e("State", objVolleyError.getMessage());
                onConnectionFailed(objVolleyError);
            }
        });
        agregarPeticionHttp(request);
    }

    public void traerInstrucciones(){
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("nombre", "PARTICIPARRULETA"));
            jsonpost = crearRequestGeneral("obtenerInstrucciones", lstHash);
            Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;

        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean guardar=analizarRespuestaJson(response, MenuPrincipalActivity.this, 2);
                if (guardar) {
                    JSONArray lista = response.optJSONArray("lista");
                    try {
                        dialogo(getString(R.string.tituloInstrucciones).toUpperCase(),lista.getJSONObject(0).getString("valor").toUpperCase());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("todos","todos "+response.toString());
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
                Log.e("State", objVolleyError.toString());
                dialogo(getString(R.string.tituloInstrucciones).toUpperCase(),getString(R.string.textoInstrucciones).toUpperCase());
                onConnectionFailed(objVolleyError);
            }
        });
        agregarPeticionHttp(request);
    }

    public void traerEventosUsuario()
    {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("usuario_id", String.valueOf(obtenerDatosUsuario().getId())));
            jsonpost = crearRequestGeneral("obtenerEventoUsuario", lstHash);
            Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;
        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("resutado","resultado traer  eventos "+response.toString());
                String resultado = response.optString("resultado").trim();
                if(resultado.equalsIgnoreCase("ok")) {
                    JSONArray lista = response.optJSONArray("lista");
                    if (lista != null && lista.length() > 0) {
                        textCircle.setVisibility(View.VISIBLE);
                        textCircle.setText(String.valueOf(lista.length()));
                    } else {
                        textCircle.setVisibility(View.GONE);
                    }
                }else {
                    textCircle.setVisibility(View.GONE);
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
                Log.e("State", objVolleyError.toString());
                onConnectionFailed(objVolleyError);
            }
        });
        agregarPeticionHttp(request);
    }

    public void onClick(View view) {
        switch(view.getId()) {

            case R.id.ly_participar:
                int intento=Integer.parseInt(textIntentos.getText().toString());
                if(intento>0) {
                    todosPuntos();
                    traerColores();
                    sectoresRuleta();
                }
                else {
                    dialogoGeneral("No tienes intentos para participar", MenuPrincipalActivity.this, 2);
                }
                break;
            case R.id.ly_reclamar:
                ly_reclamar.setEnabled(false);
                Intent intents= new Intent(getApplicationContext(), ReclamarEventousuario.class);
                startActivity(intents);
                break;
            case R.id.ly_consultas:
                consultas.setEnabled(false);
                Intent intent= new Intent(getApplicationContext(), Comentario.class);
                startActivity(intent);
                //MediaPlayer.create(getBaseContext(), R.raw.voicefile).start();
                break;
            case R.id.ly_perfil:
                perfil.setEnabled(false);
                Intent intentPerfil= new Intent(getApplicationContext(), PerfilActivity.class);
                startActivity(intentPerfil);
                //MediaPlayer.create(getBaseContext(), R.raw.voicefile).start();
                break;
            case R.id.ly_puntocanje:
                puntos_canje.setEnabled(false);
                Intent intentPuntoCanje= new Intent(getApplicationContext(), PuntosCanjeActivity.class);
                startActivity(intentPuntoCanje);
                break;
            case R.id.ly_desc_content:
                builder = new Tooltip.Builder(view, R.style.Tooltip2)
                        .setCancelable(true)
                        .setDismissOnClick(false)
                        .setCornerRadius(20f)
                        .setGravity(Gravity.BOTTOM)
                        .setText("Nivel Actual");
                builder.show();
                //MediaPlayer.create(getBaseContext(), R.raw.voicefile).start();
                break;
            case R.id.ly_premiosobtenidos:
                premios.setEnabled(false);
                Intent intentPRemios= new Intent(getApplicationContext(), Premios.class);
                startActivity(intentPRemios);
                //MediaPlayer.create(getBaseContext(), R.raw.voicefile).start();
                break;
            case R.id.ly_premios_content:
                builder = new Tooltip.Builder(view, R.style.Tooltip2)
                        .setCancelable(true)
                        .setDismissOnClick(false)
                        .setCornerRadius(20f)
                        .setGravity(Gravity.BOTTOM)
                        .setText("Tienes este número de intentos \ndisponibles para participar");
                builder.show();
                break;
            case R.id.ly_Monedas:
                ly_Monedas.setEnabled(false);
                Intent intentMonedas= new Intent(this, Monedas.class);
                startActivity(intentMonedas);
                break;
            case R.id.btnSubir:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Subir de Nivel")
                        .setMessage("¿Está seguro que desea subir de nivel?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                traerSubir();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
            case R.id.btnCompartir:
                String url="\"Bájate la nueva aplicación móvil de Comandato, para que te diviertas ganando muchos premios\"\nhttps://play.google.com/store/apps/details?id=ec.com.innovasystem.comandato&hl=es";
                final Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");
                i.putExtra(Intent.EXTRA_TEXT, url);

                final List<ResolveInfo> activities = getPackageManager().queryIntentActivities (i, 0);

                List<String> appNames = new ArrayList<String>();
                for (ResolveInfo info : activities) {
                    appNames.add(info.loadLabel(getPackageManager()).toString());
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Compartir con:");
                builder.setItems(appNames.toArray(new CharSequence[appNames.size()]), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        ResolveInfo info = activities.get(item);
                        ApplicationInfo  ai;
                        try {
                            ai = getPackageManager().getApplicationInfo(info.activityInfo.packageName, 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            ai = null;
                        }
                        String nombreAplicacion = (String) (ai != null ? getPackageManager().getApplicationLabel(ai) : "(Desconocido)");
                        Log.i("nombre","nombre "+nombreAplicacion);
                        guardarCompartir(nombreAplicacion);
                        // start the selected activity
                        i.setPackage(info.activityInfo.packageName);
                        startActivity(i);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;

            case R.id.btnInstrucciones:
                //llamar servicio
                traerInstrucciones();
                //dialogo(getString(R.string.tituloInstrucciones).toUpperCase(),getString(R.string.textoInstrucciones).toUpperCase());
                break;
            case R.id.imgCerrarSesion:
                cerrarSesion();
                break;
        }
    }
}
