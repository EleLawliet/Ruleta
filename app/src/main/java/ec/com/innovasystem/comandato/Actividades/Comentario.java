package ec.com.innovasystem.comandato.Actividades;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ec.com.innovasystem.comandato.Adaptadores.ComentariosSpinnerAdapter;
import ec.com.innovasystem.comandato.Entidades.Comentarios;
import ec.com.innovasystem.comandato.Entidades.ObjectHash;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.http.BaseActivity;
import ec.com.innovasystem.comandato.http.RequestApp;

public class Comentario extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Spinner spinner;
    ComentariosSpinnerAdapter customAdapter;
    public List<Comentarios> lista1;
    private int idSpinner=0;
    private Context context;
    private TextView textNombre;
    private TextView textApellidos;
    private TextView textCorreo;
    private TextView textTelefono;
    private TextView textMensaje;
    private ImageView imgCerrarSesion;
    private Button btnEnviar;
    private int idTipoComentario=0;
    private final static int permiso = 0;
    public boolean nombre,email, telefono, mensaje=false;
    private String imei="" ;
    private OnFragmentInteractionListener mListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.fragment_comentario);
        parametros.putInt("verbtnatras", 1);
        parametros.putInt("vertoolbar", 1);
        super.onCreate(parametros);
        Bundle extras = getIntent().getExtras();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);// no se active ningun edit text
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_PHONE_STATE)) {

                new AlertDialog.Builder(this)
                        .setTitle("Permission Request")
                        .setMessage("No ha otorgado un permiso necesario para el funcionamiento de esta opción")
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //re-request
                                ActivityCompat.requestPermissions(Comentario.this,
                                        new String[]{android.Manifest.permission.READ_PHONE_STATE},
                                        permiso);
                            }
                        })
                        .setIcon(R.drawable.ic_launcher)
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_PHONE_STATE},
                        permiso);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            imei =  obtenerImei();
        }
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
        spinner=(Spinner)findViewById(R.id.spinner);
        lista1= new ArrayList<>();
        traerTipoComentarios();
        customAdapter= new ComentariosSpinnerAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, lista1);
        spinner.setAdapter(customAdapter);
        spinner.setOnItemSelectedListener(this);
        textNombre=(TextView)findViewById(R.id.textNombre);
        textNombre.setText(obtenerDatosUsuario().getNombre());
        textApellidos=(TextView)findViewById(R.id.textApellidos);
        textApellidos.setText(obtenerDatosUsuario().getApellido());
        textCorreo=(TextView)findViewById(R.id.textCorreo);
        textCorreo.setText(obtenerDatosUsuario().getCorreo());
        textTelefono=(TextView)findViewById(R.id.textTelefono);
        textTelefono.setText(obtenerDatosUsuario().getTelefono());
        textMensaje=(EditText)findViewById(R.id.textMensaje);
        // se puede enviar los items directamente en vez de crear un arreglo
        btnEnviar=(Button)findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(this);
        //tipoLetraTextView(Comentario.this, "tipografias/MyriadPro-BoldCondIt_2.otf", textApellidos,textCorreo, textTelefono, textMensaje, btnEnviar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case permiso: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imei = obtenerImei();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public String obtenerImei(){
        TelephonyManager mngr = (TelephonyManager) getApplication().getApplicationContext().getSystemService(TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        return imei;
    }

    public void guardarComentario(String mensaje)
    {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("tipo_mensaje_id", String.valueOf(idSpinner)));
            lstHash.add(new ObjectHash("usuario_id", String.valueOf(String.valueOf(obtenerDatosUsuario().getId()))));
            lstHash.add(new ObjectHash("imei", imei));
            lstHash.add(new ObjectHash("mensaje", mensaje));
            jsonpost = crearRequestGeneral("insertarMensaje", lstHash);
            Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;

        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean guardar=analizarRespuestaJson(response);
                if (guardar) {
                    String exito = response.optString("texto").trim();
                    dialogoGeneral(exito, Comentario.this, 1);
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
               // Log.e("State", objVolleyError.toString());
                onConnectionFailed(objVolleyError);
            }
        });

        agregarPeticionHttp(request);
    }

    public void traerTipoComentarios()
    {  HashMap<String, Object> objMapData = new HashMap<>();
        JSONObject jsonpost = null;
        try {
            objMapData.put("metodo", "todosTipoMensaje");
            jsonpost = crearRequestGeneral("todosTipoMensaje");
           // Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            //Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;

        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               // Log.i("resutado","resultado "+response.toString());
                boolean guardar=analizarRespuestaJson(response, Comentario.this);
                if (guardar) {
                    customAdapter.clear();
                    JSONArray lista = response.optJSONArray("lista");
                    if(lista!=null && lista.length()>0) {
                        for (int i = 0; i < lista.length(); i++) {
                            try
                            {
                                Comentarios comentarios= new Comentarios();
                                comentarios.setId(lista.getJSONObject(i).getInt("tipo_mensaje_id"));
                                comentarios.setNombre(lista.getJSONObject(i).getString("nombre"));
                                lista1.add(comentarios);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        customAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnEnviar:
                if(textMensaje.getText().toString().trim().length()>=10){
                    mensaje=true;
                }
                else {
                    textMensaje.setError("Minímo 10 caracteres");
                    mensaje=false;
                }
                if(mensaje && idSpinner>0){
                    guardarComentario(textMensaje.getText().toString().trim());
                    nombre=false; email=false; telefono=false; mensaje=false;
                }
                break;
            case R.id.imgCerrarSesion:
                cerrarSesion();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId())
        {
            case R.id.spinner:
                idSpinner= lista1.get(position).getId();
                //Log.i("spinn","spnner "+idSpinner);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
//para el cambio