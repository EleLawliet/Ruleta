package ec.com.innovasystem.comandato.Adaptadores;

import android.content.Context;
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

import ec.com.innovasystem.comandato.Entidades.TotalPuntos;
import ec.com.innovasystem.comandato.R;

/**
 * Created by InnovaCaicedo on 16/12/2016.
 */

public class MonedasAdapter extends RecyclerView.Adapter<MonedasAdapter.MetaViewHolder> {

    public List<TotalPuntos> items;
    public int id;
    private Context context;

    public MonedasAdapter(Context context)
    {
        this.items=new ArrayList<>();
        this.context=context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public MetaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_monedas, viewGroup, false);
        return new MetaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MetaViewHolder viewHolder, int position) {
        final TotalPuntos monedasObj = items.get(position);
        Typeface tf = Typeface.createFromAsset(context.getAssets(),"tipografias/MyriadPro-Light.otf");
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(),"tipografias/MyriadPro-Semibold_2.otf");
        viewHolder.textTitulo.setText("" + monedasObj.getNombre().toUpperCase());
        viewHolder.textTitulo.setTypeface(tf);
        viewHolder.textDescripcion.setText(String.valueOf(monedasObj.getTotal()));
        viewHolder.textDescripcion.setTypeface(tf2);
        Picasso.with(context)
                .load(monedasObj.getImage())
                .placeholder(R.drawable.moneda_oro)
                .error(R.drawable.moneda_oro)               // optional
                .into(viewHolder.imgMoneda);
    }

    public class MetaViewHolder extends RecyclerView.ViewHolder
    {
        // Campos respectivos de un item
        public TextView textDescripcion;
        public TextView textTitulo;
        public ImageView imgMoneda;

        public MetaViewHolder(final View v) {
            super(v);
//            Typeface fontTitle = Typeface.createFromAsset(context.getResources().getAssets(), "AkzidenzGrotesk-LightCond.otf");
            textDescripcion = (TextView) v.findViewById(R.id.textDescripcion);
            textTitulo = (TextView) v.findViewById(R.id.textTitulo);
            imgMoneda = (ImageView)v.findViewById(R.id.iv_moneda);
        }

    }

}
