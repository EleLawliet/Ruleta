package ec.com.innovasystem.comandato.Actividades;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import ec.com.innovasystem.comandato.R;

public class MesajesPush extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.activity_mesajes_push);
        parametros.putInt("verbtnatras", 1);
        parametros.putInt("vertoolbar", 1);
        super.onCreate(parametros);
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            Log.i("extras","extras "+extras.toString());
            String titulo = extras.getString("titulo");
            String texto = extras.getString("textoLargo");
            if (titulo != null && texto != null)
                dialogoFirebase(texto, titulo);
        }
    }

        public void dialogoFirebase(String info, String titulo)
    {
        Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparente);
        dialog.setContentView(R.layout.dialogo);
        TextView textTitulo=(TextView)dialog.findViewById(R.id.textTitulo)  ;
        textTitulo.setText(titulo);
        TextView textInformacion=(TextView) dialog.findViewById(R.id.textInformacion);
        textInformacion.setText(info);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                MesajesPush.this.finish();
            }
        });
    }
}
