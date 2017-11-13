package ec.com.innovasystem.comandato.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ec.com.innovasystem.comandato.Entidades.Sucursales;
import ec.com.innovasystem.comandato.R;

public class CustomAdapter extends ArrayAdapter<Sucursales.TiendasBean> {
    Context context;
    List<Sucursales.TiendasBean> countryNames;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int textViewResourceId, List<Sucursales.TiendasBean> countryNames) {
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
    public Sucursales.TiendasBean getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view =inflter.inflate(R.layout.spinner_escoja_sorteo, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(countryNames.get(i).getCiudad());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        convertView = inflter.inflate(R.layout.spinner_escoja_sorteo, null);
        TextView names = (TextView) convertView.findViewById(R.id.textView);
        names.setText(countryNames.get(position).getCiudad());

        return convertView;
    }

}