package ec.com.innovasystem.comandato.http;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by InnovaCaicedo on 10/2/2017.
 */

public class StringRequest extends com.android.volley.toolbox.StringRequest {
    public StringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json; charset=utf-8");
        params.put(
                "Authorization",
                String.format("Basic %s", Base64.encodeToString(
                        String.format("%s:%s", "comandato", "rulet42016").getBytes(), Base64.DEFAULT)));
        return params;

    }
}
