package ec.com.innovasystem.comandato.Actividades;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ec.com.innovasystem.comandato.Entidades.ObjectHash;
import ec.com.innovasystem.comandato.Entidades.TotalPuntos;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.Utils.DateDialog;
import ec.com.innovasystem.comandato.Utils.ValidatorUtil;
import ec.com.innovasystem.comandato.http.BaseActivity;
import ec.com.innovasystem.comandato.http.RequestApp;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    TextView btnFbLogin, btnRegistrarse,btnLogin, textOlvidePass;
    ImageView  ivVerContraseña;
    EditText etUsuario, etContrasenia;
    boolean correo=true,  contrasenia=true,cedula;
    private int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.activity_main);
        parametros.putInt("verbtnatras", 1);
        parametros.putInt("vertoolbar", 1);
        super.onCreate(parametros);
        Typeface tf = Typeface.createFromAsset(getAssets(),"tipografias/MyriadPro-Light.otf");
        btnFbLogin = (TextView) findViewById(R.id.btn_fb_login);
        btnFbLogin.setTypeface(tf);
        textOlvidePass=(TextView)findViewById(R.id.textOlvidePass);
        textOlvidePass.setOnClickListener(this);
        btnLogin = (TextView) findViewById(R.id.btn_login);
        btnRegistrarse = (TextView) findViewById(R.id.btn_registro);
        ivVerContraseña = (ImageView) findViewById(R.id.iv_ver_contraseña);
        etUsuario = (EditText) findViewById(R.id.txt_usuario);
        etUsuario.setTypeface(tf);
        etContrasenia= (EditText) findViewById(R.id.txt_contrasenia);
        etContrasenia.setTypeface(tf);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        if(isLoggedIn())
        {
            enviarMenu(1, MainActivity.this);
        }
        else {
            btnRegistrarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enviarMenu(0, MainActivity.this);
                }
            });
            btnFbLogin.setOnClickListener(this);
            btnLogin.setOnClickListener(this);
            ivVerContraseña.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int textLength = etContrasenia.getText().length();
                    if (flag == 0) {
                        etContrasenia.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        etContrasenia.setSelection(textLength, textLength);
                        flag = 1;
                    } else if (flag == 1) {
                        etContrasenia.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        etContrasenia.setSelection(textLength, textLength);
                        flag = 0;
                    }
                }
            });
        }

    }

   /* public void dialogTerminosYcondiciones(final String urls)
    {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    // Create a URL for the desired page
                    URL url = new URL(urls);
                    // Read all the text returned by the server
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "ISO-8859-1"));
                    String str;
                    Log.i("buffer","buffer "+in.toString());
                    StringBuilder sb = new StringBuilder(100000);
                    while ((str = in.readLine()) != null) {
                        sb.append(str+"\n");
                    }
                    in.close();
                    dialogo("Términos y Condiciones",sb.toString());
                } catch (MalformedURLException e) {
                    Log.i("error","error "+e.getMessage());
                } catch (IOException e) {
                    Log.i("error","error "+e.getMessage());
                }
            }
        }.start();
    }
*/
    public void olvidePass(String correo, String pass, final String telefono) {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("email", correo));
            lstHash.add(new ObjectHash("password", pass));
            lstHash.add(new ObjectHash("cedula", telefono));
            jsonpost =crearRequestGeneral("olvideContrasenia",lstHash);
            Log.i("param2","param "+jsonpost.toString());
            String rutaURL = Constantes.TIENDAS_COMANDATO;
            RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    boolean guardar=analizarRespuestaJson(response, MainActivity.this, 2);
                    if (guardar) {
                        String texto= response.optString("texto").trim();
                        dialogoGeneral(texto, MainActivity.this, 2);
                    }
                    else
                        enviarMenu(0, MainActivity.this);
                    onConnectionFinished();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError objVolleyError) {
                    //Log.e("State", objVolleyError.toString());
                    //    Log.i(getString(R.string.name_by_log_definition), "JSON RESPUESTA: 500- Error Inesperado");
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
            case R.id.textFechaNacimiento:
                DateDialog dialog = new DateDialog(v);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "DatePicker");
                break;
            case R.id.textOlvidePass:

                final Dialog dialog1 = new Dialog(this);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.getWindow().setBackgroundDrawableResource(R.color.transparente);
                dialog1.setContentView(R.layout.dialog_cambiar_contrasenia2);
                final TextView textCorreo= (TextView) dialog1.findViewById(R.id.textCorreo);
                final TextView textTelefono=(TextView)dialog1.findViewById(R.id.textTelefono);
                final TextView textContrasenia=(TextView)dialog1.findViewById(R.id.textContrasenia);
                final TextView textRepetirContrasenia=(TextView)dialog1.findViewById(R.id.textRepetirContrasenia);
                TextView btn_cambiar_contrasenia=(TextView)dialog1.findViewById(R.id.btn_cambiar_contrasenia);
                btn_cambiar_contrasenia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         correo= ValidatorUtil.validateEmail((EditText) textCorreo, getString(R.string.correoIncorrecto));
                        cedula = ValidatorUtil.validarIdentificacion((EditText) textTelefono);
                        if (textContrasenia.getText().toString().length() > 4 && textRepetirContrasenia.getText().toString().length() > 4
                                && textContrasenia.getText().toString().equals(textRepetirContrasenia.getText().toString()) ) {
                            contrasenia=true;
                        }
                        else {
                            textContrasenia.setError("Contraseñas no iguales");
                            textRepetirContrasenia.setError("Contraseñas no iguales");
                            contrasenia=false;
                        }
                        if(correo && cedula &&contrasenia)
                        {
                            //Log.i("entro","entro");
                            olvidePass(textCorreo.getText().toString(), textContrasenia.getText().toString(), textTelefono.getText().toString());
                            dialog1.dismiss();
                        }
                     }
                });
                dialog1.show();
                break;
            case R.id.btn_login:
                //Log.i("usuarioCon", etUsuario.getText().toString().concat(" - ".concat(etContrasenia.getText().toString())));
                if (etContrasenia.getText().toString().trim().length() > 0 && etUsuario.getText().toString().trim().length() > 0) {
                    login(etUsuario.getText().toString(), etContrasenia.getText().toString(), MainActivity.this);
                } else {
                    etUsuario.setError("Campo Obligatorio");
                    etContrasenia.setError("Campo Obligatorio");
                }
                break;
            case R.id.btn_fb_login:
                loginFacebook(MainActivity.this);
                break;
        }
    }
}
