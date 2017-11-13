package ec.com.innovasystem.comandato.Ruleta;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.tooltip.Tooltip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ec.com.innovasystem.comandato.Actividades.Constantes;
import ec.com.innovasystem.comandato.Actividades.MainActivity;
import ec.com.innovasystem.comandato.Adaptadores.Monedas_Ruletas_Adapter;
import ec.com.innovasystem.comandato.Entidades.ObjectHash;
import ec.com.innovasystem.comandato.Entidades.SectoresRuleta;
import ec.com.innovasystem.comandato.Entidades.TotalPuntos;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.http.BaseActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RuletaActivity extends BaseActivity implements AccionesRuleta, View.OnClickListener,  Animation.AnimationListener{

    private Animation animation;
    private int itemId=0;
    private TextView textGanado;
    public RecyclerView listaMonedas;
    Monedas_Ruletas_Adapter adapter;
    private RouletteView mRouletteView;
    private RelativeLayout rlyMonedas;
    private ImageView imgCerrarSesion;
    private int opcion=0;
    private ArrayList lstPuntos;
    private ArrayList<TotalPuntos> lstPuntosAnteriores= new ArrayList<>();
    private ArrayList<TotalPuntos> lstPuntosMaximos;
    private Button btnMover ;
    private Animation slideUp;
    private Animation slideDown;
    private MediaPlayer mediaPlayer;
    private String nota="";
    Bundle extras;
    String ubicacionArchivo="";
    public List<Integer> lstColores;
    private Tooltip.Builder builder;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.activity_main_ruleta);
        parametros.putInt("verbtnatras", 1);
        parametros.putInt("vertoolbar", 1);
        super.onCreate(parametros);
        handler = new Handler();
        lstColores= new ArrayList<>();
        Typeface tf = Typeface.createFromAsset(getAssets(),"tipografias/Ancient Medium.ttf");
        List<SectoresRuleta> lstRuleta= new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_backspace_white_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.BLANCO), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //File root = new File(Environment.getExternalStorageDirectory(), "RuletaComandato");
        //File file = new File(root,"tonoRuleta.mid");
        //ubicacionArchivo= file.getAbsolutePath();
        mediaPlayer = MediaPlayer.create(RuletaActivity.this, R.raw.balongames);
        //mediaPlayer= new MediaPlayer();
        /*try {
            mediaPlayer.setDataSource(ubicacionArchivo);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //mediaPlayer = MediaPlayer.create(RuletaActivity.this, R.raw.balongames);
        mediaPlayer.setVolume(0.2f,0.2f);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animation.setAnimationListener(this);
        extras = getIntent().getExtras();
        textGanado=(TextView)findViewById(R.id.textGanadores);
        textGanado.setVisibility(View.GONE);
        textGanado.setTypeface(tf);
        imgCerrarSesion=(ImageView)findViewById(R.id.imgCerrarSesion);
        imgCerrarSesion.setOnClickListener(this);
        slideUp= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slideUp.setAnimationListener(this);
        listaMonedas=(RecyclerView)findViewById(R.id.rv_listas);
        GridLayoutManager gridLayoutManager          // HACERLO EN BLOQUE TIPO GALERIA 3 COLUMNAS
                = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);
        adapter= new Monedas_Ruletas_Adapter(this);
        listaMonedas.setLayoutManager(gridLayoutManager);
        listaMonedas.setAdapter(adapter);
        btnMover=(Button)findViewById(R.id.btnMover);
        btnMover.setOnClickListener(this);
        rlyMonedas=(RelativeLayout)findViewById(R.id.rlyMonedas);
        if (extras != null) {
            lstRuleta.clear();
            lstColores.clear();
            lstRuleta = extras.getParcelableArrayList("listaRuleta");
            lstPuntos=extras.getParcelableArrayList("listaPuntoAnt");
            lstPuntosMaximos=extras.getParcelableArrayList("listaPuntosMaximos");
            Log.i("puntos","puntsmaximos "+lstPuntosMaximos.size());
            adapter.lstPuntosMaximos.addAll(lstPuntosMaximos);
            lstPuntosAnteriores.addAll(lstPuntos);
            if(lstPuntosAnteriores.size()>0){
                adapter.itemsAnteriores.addAll(lstPuntosAnteriores);
            }
            lstColores=  extras.getIntegerArrayList("listaColores");

            Log.i("sectorRuleta","sector ruleta numero de opciones ruleta activity "+lstRuleta.size());
            traerMonedas(lstPuntos, 0);
            if(lstRuleta==null || lstRuleta.size()==0 )
            {
                btnMover.setEnabled(false);
                Toast.makeText(RuletaActivity.this,"No existen valores intente mas tarde",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else
        {
            btnMover.setEnabled(false);
            Toast.makeText(RuletaActivity.this,"No existen valores intente mas tarde",Toast.LENGTH_SHORT).show();
            finish();
        }

            mRouletteView = (RouletteView) findViewById(R.id.rouletteView);

        //configuro textos de ruleta}
        mRouletteView.opciones.clear();
        mRouletteView.opciones.addAll(lstRuleta);
        mRouletteView.mSectorColors.clear();
        mRouletteView.mSectorColors.addAll(lstColores);
        lstRuleta.clear();
        //update sectors añadir mayor cantidad de sectores es decir superior a los 7,
        //bugs con respecto a la resolucion, no se detiene .... tal vez se pueda deshabilitar el touch y pues que el usuario no pueda mantener presionado la ruleta
        /*seteo para que esta clase maneje los eventos de la ruleta*/
        mRouletteView.setAccionesruleta(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            handler.removeCallbacks(mRunnable);
            handler.removeCallbacksAndMessages(null);
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRouletteView.updateSectors();
    }

    public void traerMonedas(ArrayList<TotalPuntos> lstPuntos, int pos) {
        adapter.pos=pos;
        adapter.items.clear();
        if (lstPuntos != null && lstPuntos.size() > 0) {
            adapter.items.addAll(lstPuntos);
            adapter.notifyDataSetChanged();
        } else
            Toast.makeText(RuletaActivity.this,"No hay datos",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRouletteView.updateSectors();
    }

    /*Se ejecuta cuando la ruleta ha selecionado una opción mediante la opcion manual*/
    @Override
    public void opcionSeleccionada(int idopcion, String opciontexto) {
        Toast toast = Toast.makeText(this, "Ha ganado la opcion ["+idopcion+"] "+opciontexto, Toast.LENGTH_LONG);
        toast.show();
    }


    Runnable mRunnable;
    @Override
    public void opcionSeleccionadaxboton(final int idopcion, final String opciontexto) {
        int cantidadGanada=0;
        try {
            cantidadGanada=Integer.parseInt(opciontexto.split(" ")[0].trim());
            adapter.monedasGanadas=cantidadGanada;
            traerMonedas(lstPuntos, itemId);
            mRunnable= new Runnable() {
                @Override
                public void run() {
                    textGanado.startAnimation(slideUp);
                    textGanado.setVisibility(View.VISIBLE);
                }
            };
            handler.postDelayed(mRunnable, 2000);
            textGanado.setText(opciontexto);
            Log.i("nota","nota "+nota);
            if (nota.trim().length() > 0) {

                mRunnable= new Runnable() {
                    public void run() {
                        textGanado.setVisibility(View.GONE);
                        if(getCurrentFocus()!=null) {
                            builder = new Tooltip.Builder(getCurrentFocus(), R.style.Tooltip3)
                                    .setCancelable(false)
                                    .setDismissOnClick(false)
                                    .setCornerRadius(20f)
                                    .setGravity(Gravity.TOP)
                                    .setText(nota);
                            builder.show();
                        }
                    }
                };
                handler.postDelayed(mRunnable, 4000);
            }
            mRunnable= new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            };
            handler.postDelayed(mRunnable, 9000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    final int CANTIDAD_MAXIMA_INTENTOS = 3;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void requestOkHttpPost(final String url, final String json, final int intentos) throws Exception {
        try {
            if (intentos <= CANTIDAD_MAXIMA_INTENTOS) {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

                Request request = new Request.Builder()
                        .header("Accept", "application/json")
                        .header("Content-type", "application/json")
                        .header("authorization", "Basic Y29tYW5kYXRvOnJ1bGV0NDIwMTY=")
                        .url(url)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        try {
                            Log.i("Caida", "Caída por conexion, esperando 2 segundos...");
                            Thread.sleep(3000);
                            int reintentos = intentos + 1;
                            Log.i("Caida", reintentos + " vez de reintento de solicitud del servicio");
                            requestOkHttpPost(url, json, reintentos);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        if (!response.isSuccessful()) throw new IOException(
                                "Unexpected code " + response);

                        String respuesta = response.body().string();
                        try {
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = new JSONObject(respuesta);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            Log.i("okhttpjson", "okhttpjson " + jsonObj.toString());
                            String resultado = jsonObj.optString("resultado").trim();
                            if (resultado.equalsIgnoreCase("ok")) {
                                JSONObject lista = jsonObj.optJSONObject("lista");
                                JSONObject itemGanador = lista.optJSONObject("itemGanador");
                                //nota=lista.getString("nota");
                                JSONArray lstTotalPuntosPos = lista.optJSONArray("totalPuntosPosterior");
                                Log.i("object premio", "object premio " + itemGanador);
                                Log.i("object total", "total puntos " + lstTotalPuntosPos);
                                int posicion = itemGanador.getInt("item_id");
                                itemId=itemGanador.getInt("punto_id");
                                lstPuntos.clear();
                                for (int i = 0; i < lstTotalPuntosPos.length(); i++) {
                                    TotalPuntos totalPuntos = new TotalPuntos(Parcel.obtain());
                                    totalPuntos.setId(lstTotalPuntosPos.getJSONObject(i).getInt("punto_id"));
                                    totalPuntos.setNombre(lstTotalPuntosPos.getJSONObject(i).getString("nombre"));
                                    totalPuntos.setTotal(lstTotalPuntosPos.getJSONObject(i).getInt("total"));
                                    totalPuntos.setImage(lstTotalPuntosPos.getJSONObject(i).getString("url_imagen"));
                                    lstPuntos.add(totalPuntos);
                                }
                                mRouletteView.setOpcionDetener(posicion);
                                Log.i("se detuvo", "se detuvo " + posicion);
                            } else {
                                String error = jsonObj.optString("texto").trim();
                                Toast.makeText(RuletaActivity.this, error, Toast.LENGTH_SHORT).show();
                                Log.i("error", "error WS " + error);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                final String error = "Problemas con tu conexión, inténtalo más tarde...";
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        Looper.prepare();
                        Toast.makeText(RuletaActivity.this, error, Toast.LENGTH_LONG).show();
                        try {
                            mRouletteView.detenerRuleta();// items eliminados problemas index
                            RuletaActivity.this.finish();
                        }catch (Exception e){
                            Log.i("error","error");
                        }
                        Looper.loop();
                    }
                }.start();
                throw new Exception(error);
            }
        }catch (Exception e){
            Log.i("error", "error WS " + e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void traerPremio2()
    {
        try {
            //Se empieza a dar vuelta la ruleta
            Thread thread = new Thread() {
                @Override
                public void run() {
                    synchronized (this) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("se mueve", "se mueve");
                                mRouletteView.moverhastaopcion(-1, 3, -1);
                            }
                        });
                    }
                }
            };
            thread.start();
            new AsyncTask<Void, Void, Void>(){


                @Override
                protected Void doInBackground(Void... params) {
                    // TODO Auto-generated method stub
                    try {
                        JSONObject jsonpost = null;
                        ArrayList<ObjectHash> lstHash=new ArrayList<>();
                        lstHash.add(new ObjectHash("usuario_id", String.valueOf(obtenerDatosUsuario().getId())));
                        try {
                            jsonpost = crearRequestGeneral("obtenerItemGanadorRuletaUsuario", lstHash);
                            Log.i("param2","param "+jsonpost.toString());
                            String rutaURL = Constantes.TIENDAS_COMANDATO;
                            requestOkHttpPost(rutaURL, jsonpost.toString(),0);
                    /*if(mRouletteView.getOpcionDetener()==-1) {
                        Toast.makeText(RuletaActivity.this,"Problemas de conexió intente mas tarde",Toast.LENGTH_LONG).show();
                    }*/
                        } catch (Exception e) {
                            Toast.makeText(RuletaActivity.this,"Problemas de conexión intente mas tarde",Toast.LENGTH_LONG).show();

                            Log.i("print","print error");
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    return null;
                }


            }.execute();
        }catch (Exception e){
            e.printStackTrace();
            Log.i("error","error");
        }
        //Se hace la petición al servicio
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) mediaPlayer .release();}

    @Override
    public void onResume() {
        super.onResume();
        if (mediaPlayer != null) mediaPlayer .start();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaPlayer != null) mediaPlayer .release();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnMover:
                btnMover.setEnabled(false);
                traerPremio2();
                break;
            case R.id.imgCerrarSesion:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Cerrar sesión")
                        .setMessage("¿Está seguro que desea cerrar sesión?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(FacebookSdk.isInitialized())
                                {
                                    Log.i("facemain", "main cerrar sesion face ");
                                    LoginManager.getInstance().logOut();
                                    logoutUser();
                                    Intent intent= new Intent(RuletaActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Log.i("facemain","logueado en face ");
                                    //  LoginManager.getInstance().logOut();
                                    logoutUser();
                                    Intent intent= new Intent(RuletaActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}