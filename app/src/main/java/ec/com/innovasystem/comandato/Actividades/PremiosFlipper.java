package ec.com.innovasystem.comandato.Actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterViewFlipper;

import java.util.ArrayList;

import ec.com.innovasystem.comandato.Adaptadores.PremioFliperAdapter;
import ec.com.innovasystem.comandato.Entidades.PremiosObj;
import ec.com.innovasystem.comandato.R;

public class PremiosFlipper extends AppCompatActivity {
    ArrayList<PremiosObj> lstPremios;
    AdapterViewFlipper adapter;
    PremioFliperAdapter preAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premios_flipper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        lstPremios= new ArrayList<>();
        adapter=(AdapterViewFlipper)findViewById(R.id.fliper);
        preAdapter= new PremioFliperAdapter(this, adapter);
        adapter.setAdapter(preAdapter);
       // traerFlippe();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

   /* public void traerFlippe()
    {
        preAdapter.premio.clear();
        PremiosObj premios= new PremiosObj();
        premios.setId(1);
        premios.setTitulo("Casco");
        premios.setDescripcion("POR COMPRA DE MOTOCICLETA");
        premios.setImage(R.drawable.casco);
        premios.setDescripcionCmpra("Motocicleta");
        premios.setDireccion("Joaquín José Orrantia González, Guayaquil 090513");
        premios.setLugar("Mall del sol");
        premios.setNumFactura(1234);
        preAdapter.premio.add(premios);
        PremiosObj premios1= new PremiosObj();
        premios1.setId(2);
        premios1.setTitulo("Guante");
        premios1.setDescripcion("POR COMPRA DE HORNO");
        premios1.setImage(R.drawable.guante);
        premios1.setDescripcionCmpra("Horno");
        premios1.setLugar("Mall del sol");
        premios1.setDireccion("Joaquín José Orrantia González, Guayaquil 090513");
        premios1.setNumFactura(1234);
        preAdapter.premio.add(premios1);
        PremiosObj premios2= new PremiosObj();
        premios2.setId(3);
        premios2.setTitulo("Destapador");
        premios2.setDescripcion("POR COMPRA DE VASOS");
        premios2.setImage(R.drawable.destapador);
        premios2.setDescripcionCmpra("Vasos");
        premios2.setDireccion("Joaquín José Orrantia González, Guayaquil 090513");
        premios2.setLugar("Mall del sol");
        premios2.setNumFactura(1234);
        preAdapter.premio.add(premios2);
        preAdapter.notifyDataSetChanged();
    }*/
}
