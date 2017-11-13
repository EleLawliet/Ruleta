package ec.com.innovasystem.comandato.Actividades;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.Utils.DateDialog;
import ec.com.innovasystem.comandato.http.BaseActivity;

public class  PerfilActivity extends BaseActivity implements View.OnClickListener{
private Button editar ;
    private ImageView imgCerrarSesion;
    private CircleImageView btnFoto;
    private CollapsingToolbarLayout ctlLayout;
    private TextView textNombrePerfil, textCorreo, textFechaNacimiento,et_fecha;
    private EditText nombres,apellidos,direccion,correo, et_direccion_Trabajo, et_Celular, et_Telefono, et_Cedula;
    private boolean name, surname, number, numerHouse, email,address, addressWork, birthday, cedula;
    private Bitmap bitmap=null;
    private int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.activity_perfil);
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
        btnFoto=(CircleImageView)findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(this);
        verFoto();
        imgCerrarSesion=(ImageView)findViewById(R.id.imgCerrarSesion);
        imgCerrarSesion.setOnClickListener(this);
        ctlLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        ctlLayout.setTitleEnabled(false);
        ctlLayout.setTitle("Perfil");
        nombres = (EditText)findViewById(R.id.et_nombres);
        apellidos = (EditText)findViewById(R.id.et_apellidos);
        direccion = (EditText)findViewById(R.id.et_direccion);
        correo = (EditText)findViewById(R.id.et_correo);
        editar = (Button)findViewById(R.id.buttonEditar);
        textNombrePerfil=(TextView)findViewById(R.id.textNombrePerfil);
        textCorreo=(TextView)findViewById(R.id.textCorreo);
        et_Cedula=(EditText)findViewById(R.id.et_Cedula);
        textFechaNacimiento=(TextView)findViewById(R.id.textFechaNacimiento);
        et_fecha=(TextView)findViewById(R.id.et_fecha);
        et_Telefono=(EditText)findViewById(R.id.et_Telefono);
        et_Celular=(EditText)findViewById(R.id.et_Celular);
        et_direccion_Trabajo=(EditText)findViewById(R.id.et_direccion_Trabajo);
        et_fecha.setText(obtenerDatosUsuario().getFecha());
        et_fecha.setOnClickListener(this);
        editar.setOnClickListener(this);
        mostrarDatos();
    }

    public void verFoto()
    {
        if(obtenerDatosUsuario().getFoto()!=null && obtenerDatosUsuario().getFoto().length()>0) {
            Picasso.with(PerfilActivity.this)
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
            Picasso.with(PerfilActivity.this)
                    .load(R.drawable.com_facebook_profile_picture_blank_square)
                    .into(btnFoto);
        }
    }

    public void mostrarDatos()
    {
        nombres.setText(obtenerDatosUsuario().getNombre());
        apellidos.setText(obtenerDatosUsuario().getApellido());
        direccion.setText(obtenerDatosUsuario().getDireccionCasa());
        et_Celular.setText(obtenerDatosUsuario().getCelular());
        et_Telefono.setText(obtenerDatosUsuario().getTelefono());
        et_Cedula.setText(obtenerDatosUsuario().getCedula());
        correo.setText(obtenerDatosUsuario().getCorreo());
        et_direccion_Trabajo.setText(obtenerDatosUsuario().getDireccionTrabajo());
        textNombrePerfil.setText(obtenerDatosUsuario().getNombre().toUpperCase()+" "+obtenerDatosUsuario().getApellido().toUpperCase());
        textCorreo.setText(obtenerDatosUsuario().getCorreo()+"   ");
        textFechaNacimiento.setText("C.I."+obtenerDatosUsuario().getCedula());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.btnFoto);
                imageView.setBackgroundColor(22000000);
                bitmap=getResizedBitmap(bitmap,200);// android:largeHeap="true"    mayor cantidad de ram destinada a la apliccacion
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

    public String getStringImage(Bitmap bmp){
        String encodedImage="";
        try {
            byte[] imageBytes;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(PerfilActivity.this,"error "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return encodedImage;
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
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.buttonEditar:
            revisionDatosPerfil(nombres, apellidos, et_Cedula, et_Celular, et_Telefono, correo, direccion, et_direccion_Trabajo, et_fecha
            , bitmap, PerfilActivity.this);
            break;
            case R.id.et_fecha:
                DateDialog dialog = new DateDialog(view);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "DatePicker");
                break;
            case R.id.btnFoto:
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
            case R.id.imgCerrarSesion:
                cerrarSesion();
                break;
        }
    }
}
