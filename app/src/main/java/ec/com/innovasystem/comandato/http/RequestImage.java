package ec.com.innovasystem.comandato.http;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestImage extends ImageRequest {


    public RequestImage(String url, Response.Listener<Bitmap> listener, int maxWidth, int maxHeight, ImageView.ScaleType scaleType, Bitmap.Config decodeConfig, Response.ErrorListener errorListener) {
        super(url, listener, maxWidth, maxHeight, scaleType, decodeConfig, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json; charset=utf-8");
        params.put(
                "Authorization",
                String.format("Basic %s", Base64.encodeToString(
                        String.format("%s:%s", "comandato", "rulet42016").getBytes(), Base64.DEFAULT)));
        //Log.i("Authorization", ""+ String.format("Basic %s", Base64.encodeToString(String.format("%s:%s", "wsappdinardap", "ctp$bio*t@t@").getBytes(), Base64.DEFAULT)));
        return params;
        /*
        return super.getHeaders();
        */
    }

}