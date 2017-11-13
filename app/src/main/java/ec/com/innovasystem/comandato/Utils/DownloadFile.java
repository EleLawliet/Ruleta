package ec.com.innovasystem.comandato.Utils;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import ec.com.innovasystem.comandato.Actividades.MenuPrincipalActivity;
import ec.com.innovasystem.comandato.R;

/**
 * Created by InnovaCaicedo on 24/8/2017.
 */

public class DownloadFile extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... urlParams) {
        int count;
        try {
            URL url = new URL(urlParams[0]);
            URLConnection conexion = url.openConnection();
            conexion.connect();
            Log.i("musica","musica");
            // this will be useful so that you can show a tipical 0-100% progress bar
            int lenghtOfFile = conexion.getContentLength();
            //prueba
            File root = new File(Environment.getExternalStorageDirectory(), "RuletaComandato");
            File gpxfile = new File(root, "ubicacionMusica.mp3");
            // downlod the file

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(gpxfile);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                publishProgress((int) (total * 100 / lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}