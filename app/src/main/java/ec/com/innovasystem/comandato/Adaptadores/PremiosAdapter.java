package ec.com.innovasystem.comandato.Adaptadores;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ec.com.innovasystem.comandato.Entidades.PremiosObj;
import ec.com.innovasystem.comandato.Fragmentos.PremiosXUsuarios;
import ec.com.innovasystem.comandato.R;

/**
 * Created by InnovaCaicedo on 11/12/2016.
 */

public class PremiosAdapter extends RecyclerView.Adapter<PremiosAdapter.MetaViewHolder> {

    public List<PremiosObj> items;
    public int id;
    public boolean botonCanjear=true;
    PremiosXUsuarios premios;
    public PremiosAdapter(PremiosXUsuarios premios)
    {
        this.items=new ArrayList<PremiosObj>();
        this.premios=premios;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public MetaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_premios_obtenidos, viewGroup, false);
        return new MetaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MetaViewHolder viewHolder, int position) {
        final PremiosObj premiosObj = items.get(position);
        Typeface tf = Typeface.createFromAsset(premios.getActivity().getAssets(),"tipografias/DaxWide-Light.otf");
        Typeface tf2 = Typeface.createFromAsset(premios.getActivity().getAssets(),"tipografias/DaxWide-Bold.otf");
        viewHolder.textTitulo.setText("" + premiosObj.getTitulo().toUpperCase());
        viewHolder.textTitulo.setTypeface(tf);
        viewHolder.textTitulo.setSelected(true);
        viewHolder.textDescripcion.setText(premiosObj.getPunto());
        viewHolder.textDescripcion.setTypeface(tf2);
        viewHolder.textPuntos.setTypeface(tf);
        viewHolder.textPuntos.setText("Nivel: "+ premiosObj.getNivel());
        viewHolder.textCanjear.setTypeface(tf2);
        viewHolder.textCanjear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premios.canjear(premiosObj.getId(), viewHolder.textCanjear);
            }
        });
        Picasso.with(premios.getContext())
                .load(premiosObj.getImage())
                .placeholder(R.drawable.regalo)
                .error(R.drawable.regalo)               // optional
                .into(viewHolder.imgPremio);

        Picasso.with(premios.getContext())
                .load(premiosObj.getImage2())
                .placeholder(R.drawable.moneda_oro)
                .error(R.drawable.moneda_oro)               // optional
                .into(viewHolder.imgMonedas);
    }

    public class MetaViewHolder extends RecyclerView.ViewHolder
    {
        // Campos respectivos de un item
        public TextView textDescripcion, textFecha;
        public TextView textTitulo, textPuntos, textCanjear;
        public ImageView imgPremio, imgMonedas;

        public MetaViewHolder(final View v) {
            super(v);
//            Typeface fontTitle = Typeface.createFromAsset(context.getResources().getAssets(), "AkzidenzGrotesk-LightCond.otf");
            textDescripcion = (TextView) v.findViewById(R.id.textDescripcion);
            textTitulo = (TextView) v.findViewById(R.id.textTitulo);
            imgPremio = (ImageView)v.findViewById(R.id.imgPremio);
            imgMonedas=(ImageView)v.findViewById(R.id.imgMonedas);
            textPuntos=(TextView)v.findViewById(R.id.textPuntos);
            textFecha=(TextView)v.findViewById(R.id.textFecha);
            textCanjear=(TextView)v.findViewById(R.id.textCanjear);
        }

    }

}
