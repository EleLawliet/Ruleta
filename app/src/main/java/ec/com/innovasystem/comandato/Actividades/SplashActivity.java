package ec.com.innovasystem.comandato.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.http.BaseActivity;

/**
 * Create by wVera on 21/09/16.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.activity_splash);
        parametros.putInt("verbtnatras", 1);
        parametros.putInt("vertoolbar", 1);
        super.onCreate(parametros);
        setContentView(R.layout.activity_splash);
        if(isLoggedIn()) {
            Intent intent = new Intent(this, MenuPrincipalActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }, Constantes.SPLASH_TIEMPO);
        }

    }

}