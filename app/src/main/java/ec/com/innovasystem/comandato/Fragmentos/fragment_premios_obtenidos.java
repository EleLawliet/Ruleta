package ec.com.innovasystem.comandato.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ec.com.innovasystem.comandato.Actividades.Constantes;
import ec.com.innovasystem.comandato.Actividades.Premios;
import ec.com.innovasystem.comandato.Adaptadores.PremiosObtenidosAdapter;
import ec.com.innovasystem.comandato.Entidades.ObjectHash;
import ec.com.innovasystem.comandato.Entidades.PremioObtenidoObj;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.http.RequestApp;

public class fragment_premios_obtenidos extends Fragment {

    public fragment_premios_obtenidos() {
        // Required empty public constructor
    }
    private Context context;
    private View rootView=null;
    private RecyclerView lista;
    private RecyclerView.LayoutManager lManager;
    private PremiosObtenidosAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =inflater.inflate(R.layout.fragment_fragment_premios_obtenidos, container, false);
        context = rootView.getContext();
        Bundle parametros = new Bundle();
        parametros.putInt("layout", R.layout.activity_premios);
        parametros.putInt("verbtnatras", 1);
        parametros.putInt("vertoolbar", 1);
        super.onCreate(parametros);
        lista=(RecyclerView)rootView.findViewById(R.id.rv_listas);
        lManager = new LinearLayoutManager(context);
        lista.setLayoutManager(lManager);
        adapter= new PremiosObtenidosAdapter(fragment_premios_obtenidos.this);
        lista.setAdapter(adapter);
        traerListaPremios();
        return rootView;
    }

    public void traerListaPremios()
    {
        JSONObject jsonpost = null;
        ArrayList<ObjectHash> lstHash=new ArrayList<>();
        try {
            lstHash.add(new ObjectHash("usuario_id", String.valueOf(((Premios) getActivity()).obtenerDatosUsuario().getId())));
            jsonpost = ((Premios) getActivity()).crearRequestGeneral("premiosObtenidosXUsuario", lstHash);
            Log.i("param2","param "+jsonpost.toString());
        } catch (Exception e) {
            Log.e(this.getString(R.string.name_by_log), e.getMessage(), e);
            Toast.makeText(null, this.getString(R.string.error_exception_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        String rutaURL = Constantes.TIENDAS_COMANDATO;

        RequestApp request = new RequestApp(Request.Method.POST, rutaURL, jsonpost, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("resutado","resultado premios obtenidos "+response.toString());
                boolean guardar= ((Premios) getActivity()).analizarRespuestaJson(response, getActivity());
                if (guardar) {
                    adapter.items.clear();
                    String exito = response.optString("texto").trim();
                    JSONArray lista = response.optJSONArray("lista");
                    if(lista!=null && lista.length()>0) {
                        for(int i=0; i<lista.length();i++)
                        {
                            PremioObtenidoObj premios= new PremioObtenidoObj();
                            try {
                                premios.setId(lista.getJSONObject(i).getInt("premio_id"));
                                premios.setNombre(lista.getJSONObject(i).getString("nombre"));
                                premios.setEstado(lista.getJSONObject(i).getString("estado"));
                                premios.setFecha(lista.getJSONObject(i).getString("fecha"));
                                premios.setImage(lista.getJSONObject(i).getString("url_premio"));
                                premios.setNivel(lista.getJSONObject(i).getInt("nivel_id"));
                                premios.setValor(lista.getJSONObject(i).getInt("cantidad"));
                                premios.setMoneda(lista.getJSONObject(i).getString("url_imagen"));
                                adapter.items.add(premios);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                ((Premios) getActivity()).onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError objVolleyError) {
                //Log.e("State", objVolleyError.toString());
                ((Premios) getActivity()).onConnectionFailed(objVolleyError);

            }
        });
        ((Premios) getActivity()).agregarPeticionHttp(request);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
