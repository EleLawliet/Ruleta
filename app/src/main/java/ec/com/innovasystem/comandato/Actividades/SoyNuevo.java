package ec.com.innovasystem.comandato.Actividades;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.Utils.DateDialog;
import ec.com.innovasystem.comandato.http.BaseActivity;


public class SoyNuevo extends BaseActivity implements View.OnClickListener {

    private EditText textUsuario;
    private EditText textContrasenia;
    private EditText textVerificar;
    private EditText textNombre;
    private EditText textApellidos;
    private EditText textDireccionTrabajo;
    private TextView textFechaNacimiento;
    private EditText textDireccionCasa;
    private EditText textTelefonoCasa;
    private EditText textTelefonoTrabajo;
    private EditText textCedula;
    private CheckBox checkTerminos;
    private boolean terminosCondiciones=false;
    private int flag=0;
    private int flag2=0;
    private Bitmap bitmap;
    private Bitmap bitmapResize;
    private ImageView imgFoto;
    private ImageView imgOjo;
    private ImageView imgOjo2;
        private Button btnCrear;
    private int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.activity_soy_nuevo);
        parametros.putInt("verbtnatras", 1);
        parametros.putInt("vertoolbar", 1);
        super.onCreate(parametros);
        textUsuario=(EditText)findViewById(R.id.textUsuario);
        textContrasenia=(EditText)findViewById(R.id.textContrasenia);
        textVerificar=(EditText)findViewById(R.id.textVerificar);
        textNombre=(EditText)findViewById(R.id.textNombre);
        textApellidos=(EditText)findViewById(R.id.textApellidos);
        textDireccionTrabajo=(EditText)findViewById(R.id.textDireccionTrabajo);
        textFechaNacimiento=(TextView)findViewById(R.id.textFechaNacimiento);
        textFechaNacimiento.setOnClickListener(this);
        textDireccionCasa=(EditText)findViewById(R.id.textDireccionCasa);
        textTelefonoCasa=(EditText)findViewById(R.id.textTelefonoCasa);
        textTelefonoTrabajo=(EditText)findViewById(R.id.textTelefonoTrabajo);
        textCedula=(EditText)findViewById(R.id.textCedula);
        imgOjo=(ImageView)findViewById(R.id.imgOjo);
        imgOjo.setOnClickListener(this);
        imgOjo2=(ImageView)findViewById(R.id.imgOjo2);
        imgOjo2.setOnClickListener(this);
        imgFoto=(ImageView)findViewById(R.id.imgFoto);
        imgFoto.setOnClickListener(this);
        btnCrear=(Button)findViewById(R.id.btnCrear);
        btnCrear.setOnClickListener(this);
        checkTerminos=(CheckBox)findViewById(R.id.checkTerminos);
        checkTerminos.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                ImageView imageView = (ImageView) findViewById(R.id.imgFoto);
                imageView.setBackgroundColor(22000000);
                bitmap=getResizedBitmap(bitmap,200);// android:largeHeap="true"    mayor cantidad de ram destinada a la apliccacion
                //Log.i("imagen2","imagen 2 "+ bitmapResize.toString());
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        //Log.i("imagen1","imagen 1 "+ image.toString());
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imgOjo:
                if (flag == 0) {
                    textContrasenia.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    flag = 1;
                } else if (flag == 1) {
                    textContrasenia.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    flag = 0;
                }
                break;

            case R.id.imgOjo2:
                int textLength = textVerificar.getText().length();
                if (flag2 == 0) {
                    textVerificar.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    textVerificar.setSelection(textLength, textLength);
                    flag2 = 1;
                } else if (flag2 == 1) {
                    textVerificar.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    textVerificar.setSelection(textLength, textLength);
                    flag2 = 0;
                }
                break;

            case R.id.imgFoto:
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
            case R.id.btnCrear:
                revisionDatosUsuarioNuevo(textNombre, textCedula, textUsuario, textApellidos, textDireccionCasa, textDireccionTrabajo, textFechaNacimiento, textTelefonoCasa,
                        textTelefonoTrabajo, textContrasenia, textVerificar, bitmap, terminosCondiciones, SoyNuevo.this);
                break;
            case R.id.checkTerminos:
                if(checkTerminos.isChecked()) {
                    terminosCondiciones=true;
                    verTerminosYcodiciones();
                }
                else
                terminosCondiciones=false;
                break;
            case R.id.textFechaNacimiento:
                DateDialog dialog = new DateDialog(v);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "DatePicker");
                break;
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
                        Log.i("texto","texto "+str);
                        //str=str.replace(".",".\n");
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
    }*/

}
