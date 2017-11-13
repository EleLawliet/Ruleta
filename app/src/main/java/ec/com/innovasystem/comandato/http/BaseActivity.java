package ec.com.innovasystem.comandato.http;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ec.com.innovasystem.comandato.Actividades.Constantes;
import ec.com.innovasystem.comandato.Actividades.MainActivity;
import ec.com.innovasystem.comandato.Actividades.MenuPrincipalActivity;
import ec.com.innovasystem.comandato.Actividades.SoyNuevo;
import ec.com.innovasystem.comandato.Entidades.ObjectHash;
import ec.com.innovasystem.comandato.Entidades.TotalPuntos;
import ec.com.innovasystem.comandato.Entidades.Usuario;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.Ruleta.RuletaActivity;
import ec.com.innovasystem.comandato.Utils.DateDialog;
import ec.com.innovasystem.comandato.Utils.ValidatorUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


/**
 * Creado por Andres Cantos el 13/11/2015
 */
public class BaseActivity extends AppCompatActivity {
    RecursoVolley objvolley;
    RequestQueue colaPeticioneshttp;
    Toolbar toolbar;
    ProgressDialog barraprogreso;
    //private PreferenciasDD preferenciadd;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private boolean terminosCondiciones=false;
    int PRIVATE_MODE = Context.MODE_PRIVATE;
    final String PREF_NAME = "preferenciasnutricionistasapp";
    final String IS_LOGIN = "IsLoggedIn";
    final String KEY_NOMBRE = "nombreusuariologin";
    final String KEY_APELLIDO = "apellidousuariologin";
    final int    USER_ID = 10;
    final String KEY_CORREO = "usuarioususu";
    final String REGISTRO = "false";
    final String KEY_FACEBOOK_ID = "facebook123";
    final String KEY_TELEFONO= "2365435";
    final String KEY_CELULAR= "0999999999";
    final String KEY_CEDULA= "0888888888";
    final String KEY_FECHA = "25/08/2005";
    final String KEY_DIRECCION_CASA = "dieccioncasa";
    final String KEY_DIRECCION_TRABAJO = "diecciontrabajo";
    final String KEY_FOTO="valorfoto12356";
    final  String KEY_MONTO="999.99";
    CallbackManager callbackManager;
    public Usuario usuarioSesion;
    public boolean nombre, apellidos,email, telefono, celular, fecha, usuario,validCedula, repetir, foto, direccionCasa, direccionTrabajo, ciudad, correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(savedInstanceState.getInt("layout"));
        objvolley = RecursoVolley.getInstancia(this);
        colaPeticioneshttp = objvolley.getColaVolley();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    public void agregarPeticionHttp(RequestApp request) {
        HttpsTrustManager.allowAllSSL();
        //Log.i("DINARDAP","Si se ejecuto.");
        if (request != null) {
            request.setTag(this);
            if (colaPeticioneshttp == null)
                colaPeticioneshttp = objvolley.getColaVolley();
            request.setRetryPolicy(new DefaultRetryPolicy(60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            onPreStartConnection();
            colaPeticioneshttp.add(request);
        }
    }

    public void agregarPeticionHttp(ImageRequest request) {
        HttpsTrustManager.allowAllSSL();
        //Log.i("DINARDAP","Si se ejecuto.");
        if (request != null) {
            request.setTag(this);
            if (colaPeticioneshttp == null)
                colaPeticioneshttp = objvolley.getColaVolley();
            request.setRetryPolicy(new DefaultRetryPolicy(60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            onPreStartConnection();
            colaPeticioneshttp.add(request);
        }
    }

    public void agregarPeticionHttp(InputStreamVolleyRequest request) {
        HttpsTrustManager.allowAllSSL();
        //Log.i("DINARDAP","Si se ejecuto.");
        if (request != null) {
            request.setTag(this);
            if (colaPeticioneshttp == null)
                colaPeticioneshttp = objvolley.getColaVolley();
            request.setRetryPolicy(new DefaultRetryPolicy(60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            onPreStartConnection();
            colaPeticioneshttp.add(request);
        }
    }

    public void agregarPeticionHttp(StringRequest request) {
        HttpsTrustManager.allowAllSSL();
        //Log.i("DINARDAP","Si se ejecuto.");
        if (request != null) {
            request.setTag(this);
            if (colaPeticioneshttp == null)
                colaPeticioneshttp = objvolley.getColaVolley();
            request.setRetryPolicy(new DefaultRetryPolicy(60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            onPreStartConnection();
            colaPeticioneshttp.add(request);
        }
    }


    public void agregarPeticionHttpRuleta(RequestApp request) {
        try {
            HttpsTrustManager.allowAllSSL();
            //CustomSSLSocketFactory.ValidarHttps();
            Log.i("https", "Si se ejecuto.");
            if (request != null) {
                request.setTag(this);
                if (colaPeticioneshttp == null)
                    colaPeticioneshttp = objvolley.getColaVolley();
                request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                colaPeticioneshttp.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPreStartConnection() {
        //this.setSupportProgressBarIndeterminateVisibility(true);
        if(barraprogreso==null) barraprogreso = new ProgressDialog(this,ProgressDialog.THEME_HOLO_LIGHT);
        barraprogreso.show();
        barraprogreso.setContentView(R.layout.progressdialogeneral);
        barraprogreso.setCancelable(false);
        //barraprogreso.setProgressStyle(Widge);
    }


    public void onConnectionFinished() {
        //this.setProgressBarIndeterminateVisibility(false);
        if(barraprogreso!=null) barraprogreso.dismiss();
    }

    public void onConnectionFailed(String error) {
        if(barraprogreso!=null) barraprogreso.dismiss();
        Toast.makeText(this, "error base activvity", Toast.LENGTH_SHORT).show();
    }

    public void onConnectionFailed(VolleyError error) {
        //this.setProgressBarIndeterminateVisibility(false);
        if (barraprogreso != null) barraprogreso.dismiss();
        String message = null;
        if (error instanceof NetworkError) {
            message = "No se puede conectar a internet...Por favor chequea tu conexión!";
        } else if (error instanceof ServerError) {
            message = "No se encontró el servidor. Por favor intente mas tarde!!";
        } else if (error instanceof AuthFailureError) {
            message = "No se puede conectar a internet...Por favor chequea tu conexión!";
        } else if (error instanceof ParseError) {
            message = "Error por favor trata mas tarde!!";
        } else if (error instanceof NoConnectionError) {
            message = getString(R.string.str_not_connection);
        } else if (error instanceof TimeoutError) {
            message = "Sin conexión. Chequea tu conexión";
        }
        Log.i("error","error conection "+error.toString());
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


    public void onConnectionFailed() {
        //this.setProgressBarIndeterminateVisibility(false);
        Log.i("conexion fallida ","conexion fallida");
        if (barraprogreso != null) barraprogreso.dismiss();
        Toast.makeText(getApplicationContext(), getString(R.string.str_not_connection),Toast.LENGTH_LONG).show();
    }

    public boolean analizarRespuestaJson(JSONObject objeto) {
        String resultado = objeto.optString("resultado").trim();
        return resultado.equalsIgnoreCase("ok");
    }

    public boolean analizarRespuestaJson(JSONObject objeto, Activity contexto, int numero) {
        String resultado = objeto.optString("resultado").trim();
        boolean correcto = resultado.equalsIgnoreCase("ok");
        if (!correcto) {
            String error = objeto.optString("texto").trim();
            dialogoGeneral(error, contexto, numero);
        }
        return correcto;
    }

    public JSONObject crearRequestGeneral(String nombreMetodo, ArrayList<ObjectHash> lstHash) throws Exception {
        if (nombreMetodo == null || nombreMetodo.trim().isEmpty())
            throw new Exception("El nombre del metodo esta vacio");
        HashMap<String, Object> objMapData = new HashMap<>();
        JSONObject jsonpost = null;

        objMapData.put("metodo", nombreMetodo);

        if (lstHash == null && lstHash.isEmpty())
            throw new Exception("No esta enviando valores");

        objMapData.put("parametros", ValidatorUtil.traerValores(lstHash));
        jsonpost = new JSONObject(objMapData);
        return jsonpost;
    }

    public JSONObject crearRequestGeneral(String nombreMetodo) throws Exception {
        if (nombreMetodo == null || nombreMetodo.trim().isEmpty())
            throw new Exception("El nombre del metodo esta vacio");
        HashMap<String, Object> objMapData = new HashMap<>();
        JSONObject jsonpost = null;
        objMapData.put("metodo", nombreMetodo);
        jsonpost = new JSONObject(objMapData);
        return jsonpost;
    }
    /**
     * Realiza el análisis del json de respuesta del servicio
     *
     * @param objeto   Json de respuesta de webservices
     * @param contexto Contexto de la aplicación
     * @return Verdadero en caso del string esté string
     */
    public boolean analizarRespuestaJson(JSONObject objeto, Context contexto) {
        String resultado = objeto.optString("resultado").trim();
        boolean correcto = resultado.equalsIgnoreCase("ok");
        if (!correcto) {
            String error = objeto.optString("texto").trim();
            Toast.makeText(contexto, error, Toast.LENGTH_LONG).show();
            Log.i("error", "error WS " + error);
        }
        return correcto;
    }

    public boolean analizarRespuestaJsonHabilitarBoton(JSONObject objeto, Context contexto, View view) {
        String resultado = objeto.optString("resultado").trim();
        boolean correcto = resultado.equalsIgnoreCase("ok");
        if (!correcto) {
            view.setEnabled(true);
            String error = objeto.optString("texto").trim();
            Toast.makeText(contexto, error, Toast.LENGTH_LONG).show();
            Log.i("error", "error WS " + error);
        }
        return correcto;
    }

    /* Método que realiza inicio de sesión, crea la variable de sesión
    * @param contexto Contexto de la actividad principal
    * @param usuario Usuario a registrar en sesión
    * @throws Exception
    */
    public void iniciarSesion( Usuario usuario) throws Exception {
        if (usuario != null && usuario.getId() > 0) {
            createLogin(usuario);
        } else {
            throw new Exception("El usuario enviado está nulo");
        }
    }

    public void dialogoTerminos(final String titulo, final String url)
    {
        try {
            runOnUiThread(new Runnable() {//, if your app uses multiple threads, you can use the runOnUiThread() method to ensure your code executes on the UI thread:

                @Override
                public void run()
                {
                    try {

                        Dialog dialog = new Dialog(BaseActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparente);
                        dialog.setContentView(R.layout.dialogoterminos);
                        TextView textTitulo = (TextView) dialog.findViewById(R.id.textTitulo);
                        textTitulo.setText(titulo);
                        WebView textInformacion = (WebView) dialog.findViewById(R.id.textInformacion);
                        WebSettings webSettings = textInformacion.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        textInformacion.loadUrl(url);
                        dialog.show();

                    }catch (Exception e){
                        e.printStackTrace();
                        Log.i("excepxcion","dialogo "+e.getMessage());
                    }
                }
            });
        }catch (Exception e)
        {
            Log.i("error","error "+e.getMessage());
        }
    }

    public void cerrarSesion()
    {
        try{
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
                                //Log.i("facemain", "main cerrar sesion face ");
                                LoginManager.getInstance().logOut();
                                logoutUser();
                                Intent intent= new Intent(BaseActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                //Log.i("facemain","logueado en face ");
                                //  LoginManager.getInstance().logOut();
                                logoutUser();
                                Intent intent= new Intent(BaseActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        catch (Exception e){
            Log.i("error","error "+e.getMessage());
        }
    }

    public void revisionDatosUsuarioNuevo(EditText txtNombre, EditText cedula, EditText txtEmail, EditText txtApellidos, EditText direccionCasa, EditText direccionTrabajo
            , TextView txtFecha, EditText telefono, EditText textCelular, EditText contrasenia, EditText contraseniaRepetida, Bitmap bitmap, boolean terminosYCondiciones, Activity activity)
    {
        try {
            //GuardarNuevo();
            nombre = ValidatorUtil.Texto(txtNombre, getString(R.string.textoError));
            validCedula=ValidatorUtil.validarIdentificacion(cedula);
            email = ValidatorUtil.validateEmail(txtEmail, getString(R.string.correoIncorrecto));
            apellidos=ValidatorUtil.Texto(txtApellidos, getString(R.string.textoError));
            fecha = ValidatorUtil.validarFecha(txtFecha,getString(R.string.fechaIncorrecta));
            celular=ValidatorUtil.verTelefono(textCelular, getString(R.string.numeroIncorrecto));
            repetir = ValidatorUtil.Texto(contrasenia, getString(R.string.textoError));
            if (repetir && contrasenia.getText().toString().trim().equalsIgnoreCase(contraseniaRepetida.getText().toString().trim())) {
                repetir = true;
            } else {
                repetir = false;
                contrasenia.setError(getString(R.string.contraseñaIncorrecto));
                contraseniaRepetida.setError(getString(R.string.contraseñaIncorrecto));
            }
            /*if(bitmap==null)
                Toast.makeText(BaseActivity.this,"Imagen requerida", Toast.LENGTH_SHORT).show();*/
            if(!terminosYCondiciones)
                Toast.makeText(BaseActivity.this,"Lea términos y condiciones", Toast.LENGTH_SHORT).show();
            if (nombre && email && apellidos && fecha && celular && repetir
                    && validCedula  && terminosYCondiciones) {
                //Log.i("cargar","upload foto ");
                guardarNuevoUsuario(txtNombre, cedula, txtEmail, txtApellidos, direccionCasa, direccionTrabajo
                        , txtFecha, telefono, textCelular, contrasenia, contraseniaRepetida, bitmap, activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStringImage(Bitmap bmp){
        String encodedImage="";
        try {
            byte[] imageBytes;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(BaseActivity.this,"error "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return encodedImage;
    }


    public void guardarNuevoUsuario(EditText txtNombre, EditText cedula, EditText txtEmail, EditText txtApellidos, EditText direccionCasa, EditText direccionTrabajo
            , TextView txtFecha, EditText telefono, EditText textCelular, EditText contrasenia, EditText contraseniaRepetida, Bitmap bitmap, final Activity activity) {
        String image="";
        if(bitmap!=null) {
            image = getStringImage(bitmap);
        }
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("email", txtEmail.getText().toString().trim()));
            lstHash.add(new ObjectHash("password", contrasenia.getText().toString().trim()));
            lstHash.add(new ObjectHash("token", " "));
            lstHash.add(new ObjectHash("nombre", txtNombre.getText().toString().trim()));
            lstHash.add(new ObjectHash("apellido", txtApellidos.getText().toString().trim()));
            lstHash.add(new ObjectHash("fecha_nacimiento", txtFecha.getText().toString().trim()));
            lstHash.add(new ObjectHash("direccion_casa", direccionCasa.getText().toString().trim()));
            lstHash.add(new ObjectHash("direccion_trabajo", direccionTrabajo.getText().toString().trim()));
            lstHash.add(new ObjectHash("telefono", telefono.getText().toString().trim()));
            lstHash.add(new ObjectHash("celular", textCelular.getText().toString().trim()));
            lstHash.add(new ObjectHash("correo_personal", txtEmail.getText().toString().trim()));
            lstHash.add(new ObjectHash("cedula", cedula.getText().toString().trim()));
            lstHash.add(new ObjectHash("foto", image));
            jsonpost = crearRequestGeneral("insertarUsuario", lstHash);
            String rutaURL = Constantes.TIENDAS_COMANDATO;
            Log.i("resutado","jason "+jsonpost);

            RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String respuesta = response.toString();
                    Log.i("resutado","resultado "+respuesta);
                    boolean guardar=analizarRespuestaJson(response, activity, 2);
                    if (guardar) {
                        String exito = response.optString("texto").trim();
                        dialogoGeneral(exito, activity,2);
                        Intent intent= new Intent(BaseActivity.this, MainActivity.class)    ;
                        startActivity(intent);
                        finish();
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
        } catch (Exception e) {
            //Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void dialogoGeneral(String info, final Activity activity, final int numero)
    {
        Typeface tf2=Typeface.createFromAsset(getAssets(), "tipografias/DaxWide-Light.otf");
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparente);
        dialog.setContentView(R.layout.dialogogeneral);
        dialog.setCanceledOnTouchOutside(false);
        TextView textInformacion=(TextView) dialog.findViewById(R.id.textInformacion);
        textInformacion.setText(info);
        textInformacion.setTypeface(tf2);
        TextView btnCerrar=(TextView) dialog.findViewById(R.id.btnCerrar);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(numero==1){
                    activity.finish();
                }else{
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void requestOkHttpPost(final String url, final String json) throws Exception {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .header("Accept", "application/json")
                    .header("Content-type", "application/json")
                    .header("authorization", "Basic Y29tYW5kYXRvOnJ1bGV0NDIwMTY=")
                    .url(url)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("Error", "error " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, final okhttp3.Response response) throws IOException {

                    if (!response.isSuccessful()) throw new IOException(
                            "Unexpected code " + response);

                    String respuesta = response.body().string();
                    Log.i("respuesta","respuesta "+respuesta);
                    try {
                        JSONObject jsonObj = null;
                        try {
                            jsonObj = new JSONObject(respuesta);
                            String resultado = jsonObj.optString("resultado").trim();
                            if (resultado.equalsIgnoreCase("ok")) {
                                String exito = jsonObj.optString("texto").trim();
                                Toast.makeText(BaseActivity.this,exito, Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(BaseActivity.this, MainActivity.class)    ;
                                startActivity(intent);
                                finish();
                            }
                            Log.i("respuesta","respuesta "+respuesta.toString());

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        Log.i("okhttpjson", "okhttpjson " + jsonObj.toString());
                        String resultado = jsonObj.optString("resultado").trim();
                        if (resultado.equalsIgnoreCase("ok")) {

                        } else {
                            String error = jsonObj.optString("texto").trim();
                            //Toast.makeText(BaseActivity.this, error, Toast.LENGTH_SHORT).show();
                            Log.i("error", "error WS " + error);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            final String error = "Problemas con tu conexión, inténtalo más tarde...";

        }catch (Exception e){
            Log.i("error", "error WS " + e.getMessage());
        }
    }

    public void login(String usuario, String clave, final Activity activity) {

        try {
            ArrayList<ObjectHash> lstHash=new ArrayList<>();
            JSONObject jsonpost = null;
            lstHash.add(new ObjectHash("email", usuario.trim()));
            lstHash.add(new ObjectHash("password", clave.trim()));
            jsonpost =crearRequestGeneral("consultarUsuario",lstHash);
            Log.i("param2","param "+jsonpost.toString());
            String rutaURL = Constantes.TIENDAS_COMANDATO;
            RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("resutadomain","resultado "+response.toString());
                    boolean guardar=analizarRespuestaJson(response, BaseActivity.this);
                    if (guardar) {
                        JSONObject lista = response.optJSONObject("lista");
                        JSONObject usuarioJson = lista.optJSONObject("usuario");
                        JSONObject datos= lista.optJSONObject("datos");
                        JSONObject datosComandato=lista.optJSONObject("datosComandato");
                        if (datos != null && datos.length() > 0) {
                            try {
                                Usuario usuario = new Usuario(datos, usuarioJson, datosComandato);
                                iniciarSesion(usuario);
                                enviarMenu(1, activity);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
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
        } catch (Exception e) {
            Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
        }
    }

    public void loginFacebook(final Activity activity)
    {
        LoginManager.getInstance().logInWithReadPermissions(BaseActivity.this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        JSONObject jsonpost = null;
                        ArrayList<ObjectHash> lstHash = new ArrayList<>();
                        try {
                            lstHash.add(new ObjectHash("facebook_id", loginResult.getAccessToken().getUserId()));
                            jsonpost = crearRequestGeneral("consultarUsuario", lstHash);
                            Log.i("objects", "json " + jsonpost.toString());
                        } catch (Exception e) {
                            Log.e(getString(R.string.name_by_log), e.getMessage(), e);
                            Toast.makeText(null, getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String rutaURL = Constantes.TIENDAS_COMANDATO;
                        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String resultado = response.optString("resultado").trim();
                                Log.i("resultadomain","main "+response.toString());
                                if (resultado.equalsIgnoreCase("ok")) {
                                    JSONObject lista = response.optJSONObject("lista");
                                    JSONObject datos= lista.optJSONObject("datos");
                                    JSONObject usuarioJson= lista.optJSONObject("usuario");
                                    JSONObject datosComandato=lista.optJSONObject("datosComandato");
                                    if (datos != null && datos.length() > 0) {
                                        try {
                                            Usuario usuario = new Usuario(datos, usuarioJson, datosComandato);
                                            iniciarSesion(usuario);
                                            enviarMenu(1, activity);
                                            finish();
                                        }
                                        catch (Exception e) {
                                            Log.i("error", "error "+e.getMessage());
                                        }
                                    }
                                } else {
                                    Log.i("dentro", "dentro " + loginResult.getAccessToken().getUserId());
                                    GraphRequest request = GraphRequest.newMeRequest(
                                            loginResult.getAccessToken(),
                                            new GraphRequest.GraphJSONObjectCallback() {
                                                @Override
                                                public void onCompleted(JSONObject object, GraphResponse response) {
                                                    //Log.v("LoginActivity", object   .toString());

                                                    try {
                                                        Log.i("facebook", " " + object.toString());
                                                        final String id = object.getString("id");
                                                        String name = "";
                                                        if (object.has("first_name")) {
                                                            name = object.getString("first_name");
                                                        } else {
                                                            Log.i("name", "no hay name");
                                                        }
                                                        String city = "";
                                                        if (object.has("city")) {
                                                            city = object.getString("city");
                                                            Log.i("city", " city " + city);
                                                        } else {
                                                            Log.i("city", "no hay city");
                                                        }
                                                        String phone = "";
                                                        if (object.has("phone")) {
                                                            phone = object.getString("phone");
                                                            Log.i("phone", " phone " + phone);
                                                        } else {
                                                            Log.i("phone", "no hay phone");
                                                        }
                                                        String email = "";
                                                        if (object.has("email")) {

                                                            email = object.getString("email");
                                                        } else {
                                                            Log.i("email", "no hay email");
                                                        }
                                                        String street = "";
                                                        if (object.has("street")) {
                                                            street = object.getString("street");
                                                            Log.i("street", " street " + street);
                                                        } else {
                                                            Log.i("street", "no hay street");
                                                        }
                                                        char gender = '\u0000';
                                                        String birthday = "";//object.getString("birthday")==null
                                                        if (object.has("birthday")) {

                                                            birthday = object.getString("birthday");
                                                        } else {
                                                            Log.i("birthday", "no hay cumpleaños");
                                                        }
                                                        URL imageURL = null;
                                                        imageURL = new URL("https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?type=large");
                                                        try {
                                                            Handler handler = new Handler();
                                                            final String finalName = name;
                                                            final String finalEmail = email;
                                                            final String finalPhone = phone;
                                                            final URL finalImageURL = imageURL;
                                                            handler.postDelayed(
                                                                    new Runnable() {
                                                                        public void run() {
                                                                            revisionDatosFacebook(finalName, id, finalEmail, finalPhone, String.valueOf(finalImageURL), activity);
                                                                            Log.i("loginFace", " login " + loginResult.getAccessToken().getUserId() + " imagen " + finalImageURL);

                                                                        }
                                                                    }, 1000);
                                                        }catch (Exception e){
                                                            e.printStackTrace();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                    Bundle parameters = new Bundle();
                                    parameters.putString("fields", "id,first_name,email, birthday, picture ");
                                    Log.i("login  ", " ");
                                    request.setParameters(parameters);
                                    request.executeAsync();
                                    String error = response.optString("texto").trim();
                                    Log.i("error", "error WS " + error);
                                }
                                onConnectionFinished();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError objVolleyError) {
                                Log.e("State", objVolleyError.toString());
                                if (objVolleyError.networkResponse == null) {
                                    //    Log.i(getString(R.string.name_by_log_definition), "JSON RESPUESTA: 500- Error Inesperado");
                                    onConnectionFailed();

                                } else {
                                    //    Log.i(getString(R.string.name_by_log_definition), "JSON RESPUESTA: " + objVolleyError.networkResponse.statusCode + "-" + new String(objVolleyError.networkResponse.data));
                                    //    objUtils.getProccessMessageObjectExceptionOAUTH(objVolleyError.networkResponse, getView(), objFather);
                                }
                            }
                        });
                        agregarPeticionHttp(request);
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.i("facee", "error " + error.getMessage());
                        if (error instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void enviarMenu(int i, Activity activity) {
        Class destino = null;
        if(i==0){
            destino = SoyNuevo.class;
        }else if(i==1) {
            destino = MenuPrincipalActivity.class;
        }
        Intent intent = new Intent(activity.getApplicationContext(), destino);
        startActivity(intent);
    }

    public void revisionDatosFacebook(final String name, final String id, final String email,
                                      final String  phone, final String image, final Activity activity)
    {
        try {
            boolean emails = true, nombre = true, apellidos = true, telfCasa = true, nacimiento = true;
            LoginManager.getInstance().logOut();
            Log.i("revision ", "revision " + id);
            final EditText textEmail;
            final EditText textNombre;
            final EditText textApellidos;
            final EditText textCedula;
            final TextView textFechaNacimiento;
            final EditText textCell;
            final EditText textCellCasa;
            final EditText textDireccionCasa;
            final EditText textDireccionTrabajo;
            final CheckBox checkTerminos;
            final Dialog dialog2 = new Dialog(this);
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog2.getWindow().setBackgroundDrawableResource(R.color.BLANCO);
            dialog2.setContentView(R.layout.dialog);
            textEmail = (EditText) dialog2.findViewById(R.id.textEmail);
            textEmail.setHint("Email");
            textApellidos = (EditText) dialog2.findViewById(R.id.textApellidos);
            textApellidos.setHint("Apellidos");
            textNombre = (EditText) dialog2.findViewById(R.id.textNombre);
            textNombre.setHint("Nombre");
            checkTerminos = (CheckBox) dialog2.findViewById(R.id.checkTerminos);
            textCedula = (EditText) dialog2.findViewById(R.id.textCedula);
            textCedula.setHint("Cédula");
            textFechaNacimiento = (TextView) dialog2.findViewById(R.id.textFechaNacimiento);
            textFechaNacimiento.setHint("Fecha de Nacimiento");
            textFechaNacimiento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            });
            checkTerminos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkTerminos.isChecked()) {
                        terminosCondiciones = true;
                        verTerminosYcodiciones();
                    } else
                        terminosCondiciones = false;
                }
            });
            textCell = (EditText) dialog2.findViewById(R.id.textTelfCasa);
            textCell.setHint("Celular");
            textCellCasa = (EditText) dialog2.findViewById(R.id.textTelfTrab);
            textCellCasa.setHint("Convencional");
            textDireccionCasa = (EditText) dialog2.findViewById(R.id.textDireccionCasa);
            textDireccionCasa.setHint("Dirección casa");
            textDireccionTrabajo = (EditText) dialog2.findViewById(R.id.textDireccionTrabajo);
            textDireccionTrabajo.setHint("Dirección trabajo");
            //Log.i("datos","datos email "+email+" name "+name+" cumple "+birthday+" telef "+phone);
            if (email != null && email.trim().length() > 0) {
                textEmail.setVisibility(View.GONE);
                emails = false;
            }
            if (name != null && name.trim().length() > 0) {
                textNombre.setVisibility(View.GONE);
                nombre = false;
            }
            if (phone != null && phone.trim().length() > 0) {
                textCell.setVisibility(View.GONE);
                telfCasa = false;
            }

            Button btnEnviar = (Button) dialog2.findViewById(R.id.btnEnviar);
            final boolean finalEmails = emails;
            final boolean finalNombre = nombre;
            final boolean finalNacimiento = nacimiento;
            final boolean finalTelfCasa = telfCasa;
            final boolean finalApellido = apellidos;

            btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean telefono, email3, telefonoTrabajo, cedula, fecha = false, lastname2, direcasa, diretrabajo;
                    String email2 = null, nombre2 = null, fechNacimi = null, telfCasa2, latname = null;
                    if (!finalEmails)
                        textEmail.setText(email);
                    else textEmail.getText().toString();

                    if (!finalNombre)
                        nombre2 = name;
                    else nombre2 = textNombre.getText().toString();

                    fechNacimi = textFechaNacimiento.getText().toString();
                    if (!finalTelfCasa) {
                        textCell.setText(phone);
                    } else textCell.getText().toString();
                    cedula = ValidatorUtil.validarIdentificacion(textCedula);
                    email3 = ValidatorUtil.validateEmail(textEmail, getString(R.string.correoIncorrecto));
                    telefono = ValidatorUtil.verTelefono(textCell, getString(R.string.numeroIncorrecto));
                    //telefonoTrabajo=ValidatorUtil.verTelefonoLocal(textCellCasa, getString(R.string.numeroIncorrecto));
                    try {
                        fecha = ValidatorUtil.verFecha2(fechNacimi);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        fecha = ValidatorUtil.validarFecha(textFechaNacimiento, getString(R.string.fechaIncorrecta));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (!fecha)
                        textFechaNacimiento.setError("Fecha Incorrecta");
                    lastname2 = ValidatorUtil.Texto(textApellidos.getText().toString());
                    if (!lastname2)
                        textApellidos.setError("Minimo 4 caracteres");
                    //direcasa=ValidatorUtil.Texto(textDireccionCasa.getText().toString());

                    //diretrabajo=ValidatorUtil.Texto(textDireccionTrabajo.getText().toString());
                    if (!terminosCondiciones)
                        Toast.makeText(BaseActivity.this, "Acepte los términos y condiciones", Toast.LENGTH_LONG).show();
                    if (email3 && nombre2.length() > 0 && fecha &&
                            lastname2 && telefono && cedula && terminosCondiciones) {
                        GuardarFacebook(nombre2, fechNacimi, id, textEmail.getText().toString(), textCell.getText().toString(), textApellidos.getText().toString(), textDireccionCasa.getText().toString(),
                                textDireccionTrabajo.getText().toString(), textCellCasa.getText().toString()
                                , textCedula.getText().toString(), image, activity);
                    } else {
                        Toast.makeText(BaseActivity.this, "Ingrese todo los datos", Toast.LENGTH_LONG).show();
                    }
                }
            });
            dialog2.show();
        }catch (Exception e){
            Log.i("excepion","excepcion "+e.getMessage());
        }
    }

    /**
     *
     * @param activity es la actividad a la cual pretenecen los TextView
     * @param nombreFuente es la ruta del proyeccto en donde se encuentra la fuente a usar
     * @param lstTextView el arreglo que contiene la lista de los TextView
    Este metodo setea un tipo de fuente a los textview
     */
    public void tipoLetraTextView(Activity activity, String nombreFuente, Object... lstTextView){
        try {
            Typeface tf = Typeface.createFromAsset(activity.getAssets(), nombreFuente);
            if(isListaVacia( lstTextView)) {
                for (int i = 0; i < lstTextView.length; i++) {
                    if(lstTextView[i] instanceof TextView){//condicional si es que es un textview
                        ((TextView)lstTextView[i]).setTypeface(tf);
                    }
                    else if(lstTextView[i] instanceof EditText){
                            ((EditText)lstTextView[i]).setTypeface(tf);
                        }
                    else if(lstTextView[i] instanceof Button){
                        ((Button)lstTextView[i]).setTypeface(tf);
                    }
                }
            }else {
                Toast.makeText(activity,"La lista de TextView esta vacia", Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     *
     * @param name nombre de la persona a registrar
     * @param birthday
     * @param id
     * @param email
     * @param phone
     * @param apellidos
     * @param direCasa
     * @param direcTrabajo
     * @param cellTrab
     * @param cedula
     * @param image
     * @param activity
     */
    public void GuardarFacebook(String name, String birthday, String id, String email, String phone, String apellidos, String direCasa
            , String direcTrabajo, String cellTrab, String cedula, String image, final Activity activity) {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("correo_personal", email));
            lstHash.add(new ObjectHash("facebook_id", id));
            lstHash.add(new ObjectHash("nombre", name));
            lstHash.add(new ObjectHash("apellido", apellidos));
            lstHash.add(new ObjectHash("fecha_nacimiento", birthday));
            lstHash.add(new ObjectHash("direccion_casa", direCasa));
            lstHash.add(new ObjectHash("direccion_trabajo", direcTrabajo));
            lstHash.add(new ObjectHash("celular", phone));
            lstHash.add(new ObjectHash("telefono", cellTrab));
            lstHash.add(new ObjectHash("cedula", cedula));
            lstHash.add(new ObjectHash("foto", image));
            jsonpost = crearRequestGeneral("insertarUsuario", lstHash);
            Log.i("param2","param "+jsonpost.toString());
            String rutaURL = Constantes.TIENDAS_COMANDATO;
            RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("resutado","resultado "+response.toString());
                    boolean guardar=analizarRespuestaJson(response, BaseActivity.this);
                    if (guardar) {
                        JSONObject lista = response.optJSONObject("lista");
                        JSONObject datos= lista.optJSONObject("datos");
                        JSONObject usuarioJson= lista.optJSONObject("usuario");
                        JSONObject datosComandato=lista.optJSONObject("datosComandato");
                        if (datos != null && datos.length() > 0) {
                            try {
                                Usuario usuario = new Usuario(datos, usuarioJson, datosComandato);
                                iniciarSesion(usuario);
                                enviarMenu(1, activity);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
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
        } catch (Exception e) {
            //Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void revisionDatosPerfil(EditText txtNombre, EditText txtApellido, EditText txtCedula, EditText txtCelular, EditText txtTelefonoCasa, EditText txtCorreo
            , EditText txtDireccioCasa, EditText txtDireccionTrabajo, TextView txtFecha, Bitmap bitmap, Activity activity)
    {
        boolean name=false, surname=false, number=false, email=false, birthday=false, cedula=false;
        try {
            name= ValidatorUtil.Texto(txtNombre, getString(R.string.textoError));
            surname=ValidatorUtil.Texto(txtApellido, getString(R.string.textoError));
            cedula=ValidatorUtil.validarIdentificacion(txtCedula);
            number=ValidatorUtil.verTelefono(txtCelular, getString(R.string.numeroIncorrecto));
            //numerHouse=ValidatorUtil.verTelefonoLocal(et_Telefono, getString(R.string.numeroIncorrecto));
            email=ValidatorUtil.validateEmail(txtCorreo, getString(R.string.correoIncorrecto));
            //address=ValidatorUtil.Texto(direccion.getText().toString().trim());
            //addressWork=ValidatorUtil.Texto(et_direccion_Trabajo, getString(R.string.textoError));
            birthday=ValidatorUtil.validarFecha(txtFecha, getString(R.string.fechaIncorrecta));
            if(name && surname && number && email  && birthday   && cedula)
            {
                guardarPerfil(txtNombre.getText().toString().trim(), txtApellido.getText().toString().trim(),txtCedula.getText().toString().trim(),
                        txtCelular.getText().toString().trim(), txtCorreo.getText().toString().trim(), txtDireccioCasa.getText().toString().trim()
                        , txtFecha.getText().toString().trim(), txtTelefonoCasa.getText().toString().trim(), txtDireccionTrabajo.getText().toString().trim()
                        , bitmap, activity);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void guardarPerfil(final String nombre, final String apellido, final String cedula, final String numero, final String correo, final String direccion,
                              final String fecha, final String telefonoCasa, final String direcccionTrabajo, Bitmap bitmap, final Activity activity)
    {
        try {
            String image="";
            boolean photo=false;
            if(bitmap!=null)
            {
                image = getStringImage(bitmap);
                photo=true;
            }
            JSONObject jsonpost = null;
            ArrayList<ObjectHash> lstHash=new ArrayList<>();
            lstHash.add(new ObjectHash("nombre", nombre));
            lstHash.add(new ObjectHash("apellido", apellido));
            lstHash.add(new ObjectHash("fecha_nacimiento", fecha));
            lstHash.add(new ObjectHash("correo_personal", correo));
            lstHash.add(new ObjectHash("direccion_casa", direccion));
            lstHash.add(new ObjectHash("direccion_trabajo", direcccionTrabajo));
            lstHash.add(new ObjectHash("telefono", telefonoCasa));
            lstHash.add(new ObjectHash("celular", numero));
            lstHash.add(new ObjectHash("usuario_id", String.valueOf(obtenerDatosUsuario().getId())));
            lstHash.add(new ObjectHash("cedula", cedula));
            if (photo)
                lstHash.add(new ObjectHash("foto", image.toString()));
            else
                lstHash.add(new ObjectHash("foto", obtenerDatosUsuario().getFoto()));
            jsonpost = crearRequestGeneral("editarUsuario", lstHash);
            //Log.i("param2","param "+jsonpost.toString());

            String rutaURL = Constantes.TIENDAS_COMANDATO;

            final String finalImage = image;
            final boolean finalPhoto = photo;
            RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    boolean guardar=analizarRespuestaJson(response, activity.getApplicationContext());
                    //Log.i("respnse","response "+response.toString());
                    if (guardar) {
                        String exito = response.optString("texto").trim();
                        Toast.makeText( activity.getApplicationContext(),exito,Toast.LENGTH_SHORT).show();
                        Usuario usuario= new Usuario();
                        usuario.setId(obtenerDatosUsuario().getId());
                        usuario.setNombre(nombre);
                        usuario.setApellido(apellido);
                        usuario.setFecha(fecha);
                        usuario.setCorreo(correo);
                        usuario.setDireccionCasa(direccion);
                        usuario.setDireccionTrabajo(direcccionTrabajo);
                        usuario.setTelefono(telefonoCasa);
                        usuario.setCelular(numero);
                        usuario.setCedula(cedula);
                        usuario.setFacwbookId(obtenerDatosUsuario().getFacwbookId());
                        if (finalPhoto)
                            usuario.setFoto(finalImage);
                        else {
                            usuario.setFoto(obtenerDatosUsuario().getFoto());
                        }
                        try {
                            removercredenciales();
                            iniciarSesion(usuario);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        activity. finish();
                    }
                    onConnectionFinished();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError objVolleyError) {
                    //Log.e("State", objVolleyError.toString());
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
        } catch (Exception e) {
            //Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void verTerminosYcodiciones()
    {
        JSONObject jsonpost = null;
        try {
            jsonpost = crearRequestGeneral("terminosCondiciones");
            Log.i("param2","param "+jsonpost.toString());

            String rutaURL = Constantes.TIENDAS_COMANDATO;

            RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("sector","sector response "+response.toString());
                    boolean guardar=analizarRespuestaJson(response, BaseActivity.this);
                    try {
                        if (guardar) {
                            JSONArray lista = response.optJSONArray("lista");
                            if(lista!=null && lista.length()>0)
                            {
                                String texto="";
                                for(int i=0;i<lista.length();i++)
                                {
                                    texto=lista.getJSONObject(i).optString("url_termino").toString();//valores
                                }
                                dialogoTerminos("Términos y Condiciones",texto);
                            }
                            else
                                dialogoTerminos("Términos y Condiciones",Constantes.URL_TERMINOS_CONDICIONES);
                        }
                        else
                            dialogoTerminos("Términos y Condiciones",Constantes.URL_TERMINOS_CONDICIONES);
                    }
                    catch (Exception e)
                    {
                        Log.i("error",e.getMessage());
                        Toast.makeText(BaseActivity.this,"Error intente mas tarde",Toast.LENGTH_LONG).show();
                        dialogoTerminos("Términos y Condiciones",Constantes.URL_TERMINOS_CONDICIONES);
                    }
                    onConnectionFinished();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError objVolleyError) {
                    Log.e("State", objVolleyError.getMessage());
                    dialogoTerminos("Términos y Condiciones",Constantes.URL_TERMINOS_CONDICIONES);
                    onConnectionFailed(objVolleyError);
                }
            });
            agregarPeticionHttp(request);
        } catch (Exception e) {
            Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void dialogo(String titulo, String info)
    {
        Typeface tf2=Typeface.createFromAsset(getAssets(), "tipografias/DaxWide-Light.otf");
        Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparente);
        dialog.setContentView(R.layout.dialogo);
        TextView textTitulo=(TextView)dialog.findViewById(R.id.textTitulo);
        textTitulo.setText(titulo);
        TextView textInformacion=(TextView) dialog.findViewById(R.id.textInformacion);
        textInformacion.setText(info);
        textInformacion.setTypeface(tf2);
        dialog.show();
    }

    public SharedPreferences.Editor obtenerEditor(){
        if(editor==null) {
            editor = obtenerSharedPreferences().edit();
            editor.commit();
        }
        return editor;
    }



    public void createLogin(Usuario usuario) {
        Log.i("crearLogin", " foto shir " + usuario.getFecha());
        obtenerEditor().clear();
        obtenerEditor().putBoolean(IS_LOGIN, true);
        obtenerEditor().putInt(String.valueOf(USER_ID), usuario.getId());
        obtenerEditor().putString(KEY_CORREO, usuario.getCorreo());
        obtenerEditor().putString(KEY_NOMBRE, usuario.getNombre());
        obtenerEditor().putString(KEY_APELLIDO,usuario.getApellido());
        obtenerEditor().putString(KEY_CEDULA, usuario.getCedula());
        obtenerEditor().putString(KEY_TELEFONO, usuario.getTelefono());
        obtenerEditor().putString(KEY_FECHA, usuario.getFecha());
        obtenerEditor().putString(KEY_CELULAR, usuario.getCelular());
        obtenerEditor().putString(KEY_DIRECCION_CASA, usuario.getDireccionCasa());
        obtenerEditor().putString(KEY_DIRECCION_TRABAJO, usuario.getDireccionTrabajo());
        obtenerEditor().putString(KEY_FOTO, usuario.getFoto());
        obtenerEditor().putString(KEY_FACEBOOK_ID, usuario.getFacwbookId());
        obtenerEditor().putString(KEY_MONTO, usuario.getMonto());
        obtenerEditor().commit();
    }

    public boolean isLoggedIn(){
        if(obtenerSharedPreferences()==null)
            return false;
        return obtenerSharedPreferences().getBoolean(IS_LOGIN, false);
    }

    public void logoutUser(){
        removercredenciales();
    }

    public Usuario obtenerDatosUsuario() {
        return obtenerUserData();
    }

    public void removercredenciales(){
        Log.i("removi","removio credenciales");
        obtenerEditor().remove(IS_LOGIN);
        obtenerEditor().remove(KEY_CORREO);
        obtenerEditor().remove(KEY_NOMBRE);
        obtenerEditor().remove(KEY_APELLIDO);
        obtenerEditor().remove(KEY_TELEFONO);
        obtenerEditor().remove(KEY_FECHA);
        obtenerEditor().remove(KEY_CELULAR);
        obtenerEditor().remove(KEY_DIRECCION_CASA);
        obtenerEditor().remove(KEY_DIRECCION_TRABAJO);
        obtenerEditor().remove(KEY_CEDULA);
        obtenerEditor().remove(String.valueOf(USER_ID));
        obtenerEditor().remove(KEY_FOTO);
        obtenerEditor().remove(KEY_FACEBOOK_ID);
        obtenerEditor().remove(KEY_MONTO);
        obtenerEditor().commit();
    }


    public Usuario obtenerUserData(){
        if(usuarioSesion==null)
            usuarioSesion = new Usuario();
        usuarioSesion.setSesion(obtenerSharedPreferences().getBoolean(IS_LOGIN, false));
        usuarioSesion.setId(obtenerSharedPreferences().getInt(String.valueOf(USER_ID), 0));
        usuarioSesion.setNombre(obtenerSharedPreferences().getString(KEY_NOMBRE, ""));
        usuarioSesion.setApellido(obtenerSharedPreferences().getString(KEY_APELLIDO, ""));
        usuarioSesion.setCorreo(obtenerSharedPreferences().getString(KEY_CORREO, ""));
        usuarioSesion.setCedula(obtenerSharedPreferences().getString(KEY_CEDULA, ""));
        usuarioSesion.setTelefono(obtenerSharedPreferences().getString(KEY_TELEFONO, ""));
        usuarioSesion.setDireccionCasa(obtenerSharedPreferences().getString(KEY_DIRECCION_CASA, ""));
        usuarioSesion.setDireccionTrabajo(obtenerSharedPreferences().getString(KEY_DIRECCION_TRABAJO, ""));
        usuarioSesion.setCelular(obtenerSharedPreferences().getString(KEY_CELULAR, ""));
        usuarioSesion.setFecha(obtenerSharedPreferences().getString(KEY_FECHA, ""));
        usuarioSesion.setFoto(obtenerSharedPreferences().getString(KEY_FOTO, ""));
        usuarioSesion.setFacwbookId(obtenerSharedPreferences().getString(KEY_FACEBOOK_ID, ""));
        usuarioSesion.setMonto(obtenerSharedPreferences().getString(KEY_MONTO,""));
        Log.i("userData"," nombre de usuario "+obtenerSharedPreferences().getString(KEY_NOMBRE, ""));
        return usuarioSesion;
    }

    public SharedPreferences obtenerSharedPreferences(){
        pref = getApplicationContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return pref;
    }

    public void crearArchivo(MenuPrincipalActivity context, String sFileName, String sBody){
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "RuletaComandato");
            if (!root.exists()) {
                root.mkdirs();
                File gpxfile = new File(root, sFileName);
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(sBody);
                writer.flush();
                writer.close();
                leerArchivo(context);
            }else {
                leerArchivo(context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void leerArchivo(MenuPrincipalActivity context){
        File root = new File(Environment.getExternalStorageDirectory(), "RuletaComandato");
        File file = new File(root,"DatosACargar");
        JSONObject jsonObject;
        String ret = "";

        try {
            InputStream inputStream = new FileInputStream(file);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                jsonObject= new JSONObject(ret);
                context.compararDatos(jsonObject.getString("id"));
                Log.i("json","json "+jsonObject.get("id"));

            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void actualizarDatos(MenuPrincipalActivity menu, String datos){
        File root = new File(Environment.getExternalStorageDirectory(), "RuletaComandato");
        File file = new File(root,"DatosACargar");
        JSONObject object;
        FileOutputStream overWrite = null;
        try {
            overWrite = new FileOutputStream(file, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            overWrite.write(datos.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            overWrite.flush();
            overWrite.close();
            object= new JSONObject(datos);
            menu.imagenes(object.getString("fondoMenu"),"fondoMenu.jpg");
            menu.imagenes(object.getString("fondoPremios"), "fondoPremios.jpg");
            menu.imagenes(object.getString("fondoRuleta"),"fondoRuleta.jpg");
            menu.imagenes(object.getString("fondoMonedas"), "fondoMonedas.jpg");
            menu.imagenes(object.getString("fondoRegalos"), "fondoRegalos.jpg");
            menu.descargarArchivo(object.getString("tonoRuleta"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public JSONObject leerArchivoUso(){
        File root = new File(Environment.getExternalStorageDirectory(), "RuletaComandato");
        File file = new File(root,"DatosACargar");
        JSONObject jsonObject = null;
        String ret = "";
        try {
            InputStream inputStream = new FileInputStream(file);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
                jsonObject= new JSONObject(ret);
                Log.i("json","json "+jsonObject.get("id"));
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void guardarImagen(Bitmap bitmap,String nombreImagen){
        File root = new File(Environment.getExternalStorageDirectory(), "RuletaComandato");
        File file = new File(root,nombreImagen);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void guardarArchivo(byte[] archivo, String nombre){
        int count;
        try{
            File root = new File(Environment.getExternalStorageDirectory(), "RuletaComandato");
            File file = new File(root,nombre);
            InputStream input = new ByteArrayInputStream(archivo);
            OutputStream output = new FileOutputStream(file);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
            Log.i("musica","musica guardada");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isListaVacia(Object lista){
        if (lista != null) {
            if (lista instanceof ArrayList) {
                return ((ArrayList)lista).isEmpty();
            } else if (lista instanceof Object[]) {
                // Log.i("valor","valor "+((Object[])lista).length);
                return ((Object[])lista).length>0;
            }
            return true;
        } else
            return true;
    }

}