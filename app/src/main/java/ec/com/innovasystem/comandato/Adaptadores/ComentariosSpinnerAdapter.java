package ec.com.innovasystem.comandato.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ec.com.innovasystem.comandato.Entidades.Comentarios;
import ec.com.innovasystem.comandato.R;

/**
 * Created by InnovaCaicedo on 8/12/2016.
 */

public class ComentariosSpinnerAdapter extends ArrayAdapter<Comentarios> {
    Context context;
    List<Comentarios> countryNames;
    LayoutInflater inflter;
    public ComentariosSpinnerAdapter(Context applicationContext, int textViewResourceId, List<Comentarios> countryNames) {
        super(applicationContext, textViewResourceId, countryNames);
        this.context = applicationContext;
        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return countryNames.size();
    }

    @Override
    public Comentarios getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_escoja_sorteo, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(countryNames.get(i).getNombre());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        convertView = inflter.inflate(R.layout.spinner_escoja_sorteo, null);
        TextView names = (TextView) convertView.findViewById(R.id.textView);
        names.setText(countryNames.get(position).getNombre());

        return convertView;
    }

}
