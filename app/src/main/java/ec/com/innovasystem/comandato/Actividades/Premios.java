package ec.com.innovasystem.comandato.Actividades;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import ec.com.innovasystem.comandato.Adaptadores.ViewPageAdapter;
import ec.com.innovasystem.comandato.Fragmentos.PremiosPromociones;
import ec.com.innovasystem.comandato.Fragmentos.PremiosXUsuarios;
import ec.com.innovasystem.comandato.Fragmentos.fragment_premios_obtenidos;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.http.BaseActivity;

public class Premios extends BaseActivity implements View.OnClickListener{
    private TabLayout tabs;
    private ViewPager viewPager;
    ViewPageAdapter viewPageAdapter;
    private ImageView imgCerrarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.activity_premios);
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
        imgCerrarSesion=(ImageView)findViewById(R.id.imgCerrarSesion);
        imgCerrarSesion.setOnClickListener(this);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        tabs=(TabLayout)findViewById(R.id.tabs);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewPageAdapter=new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new PremiosXUsuarios(),"Premios Disponibles");
        viewPageAdapter.addFragment(new fragment_premios_obtenidos(),"Premios Obtenidos");
        viewPageAdapter.addFragment(new PremiosPromociones(), "Premios Promociones");
        viewPager.setAdapter(viewPageAdapter);
        tabs.setupWithViewPager(viewPager);
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgCerrarSesion:
                cerrarSesion();
                break;
        }
    }
}
