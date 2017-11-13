package ec.com.innovasystem.comandato.Adaptadores;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ec.com.innovasystem.comandato.Entidades.PremioObtenidoObj;
import ec.com.innovasystem.comandato.Fragmentos.fragment_premios_obtenidos;
import ec.com.innovasystem.comandato.R;

/**
 * Created by InnovaCaicedo on 22/12/2016.
 */

public class PremiosObtenidosAdapter extends RecyclerView.Adapter<PremiosObtenidosAdapter.MetaViewHolder> {
    public List<PremioObtenidoObj> items;
    public int id;
    fragment_premios_obtenidos premios;
    public PremiosObtenidosAdapter(fragment_premios_obtenidos premios)
    {
        this.items=new ArrayList<PremioObtenidoObj>();
        this.premios=premios;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public PremiosObtenidosAdapter.MetaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_premios_disponibles, viewGroup, false);
        return new PremiosObtenidosAdapter.MetaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PremiosObtenidosAdapter.MetaViewHolder viewHolder, int position) {
        final PremioObtenidoObj premiosObj = items.get(position);
        SimpleDateFormat fecha= new SimpleDateFormat("yyyy-MM-dd");//cambios cuando se realicen solo la fecha
        Typeface tf = Typeface.createFromAsset(premios.getActivity().getAssets(),"tipografias/DaxWide-Light.otf");
        viewHolder.textNombre.setText(premiosObj.getNombre().toString().toUpperCase());
        viewHolder.textNombre.setTypeface(tf);
        viewHolder.textNombre.setSelected(true);
        viewHolder.textNivel.setText("Nivel: " + premiosObj.getNivel());
        SpannableString ss1=  new SpannableString("Valor");
        SpannableString ss2=  new SpannableString(String.valueOf(premiosObj.getValor()));
        ss1.setSpan(new RelativeSizeSpan(0.5f), 0,5, 0); // set size
        ss2.setSpan(new StyleSpan(Typeface.BOLD), 0, String.valueOf(premiosObj.getValor()).length(), 0);
        viewHolder.textDescripcion.setText("");
        //viewHolder.textDescripcion.setTypeface(tf);
        viewHolder.textDescripcion.append(ss1);
        viewHolder.textDescripcion.append(" "+ss2);
        viewHolder.textEstado.setText(premiosObj.getEstado());
        try {
            viewHolder.textFecha.setText(fecha.format(fecha.parse(premiosObj.getFecha())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Picasso.with(premios.getContext())
                .load(premiosObj.getImage())
                .placeholder(R.drawable.regalo)

                .error(R.drawable.regalo)               // optional
                .into(viewHolder.imgPremio);
        Picasso.with(premios.getContext())
                .load(premiosObj.getMoneda())
                .placeholder(R.drawable.regalo)
                .fit()
                .error(R.drawable.regalo)               // optional
                .into(viewHolder.imgMonedas);
    }

    public class MetaViewHolder extends RecyclerView.ViewHolder
    {
        // Campos respectivos de un item
        public TextView textNombre, textNivel;
        public TextView textDescripcion, textFecha, textEstado;
        public ImageView imgPremio, imgMonedas;

        public MetaViewHolder(final View v) {
            super(v);
//            Typeface fontTitle = Typeface.createFromAsset(context.getResources().getAssets(), "AkzidenzGrotesk-LightCond.otf");
            textNombre = (TextView) v.findViewById(R.id.textNombre);
            textDescripcion = (TextView) v.findViewById(R.id.textDescripcion);
            imgPremio = (ImageView)v.findViewById(R.id.imgPremio);
            imgMonedas=(ImageView)v.findViewById(R.id.imgMonedas);
            textFecha=(TextView)v.findViewById(R.id.textFecha);
            textEstado=(TextView)v.findViewById(R.id.textEstado);
            textNivel=(TextView)v.findViewById(R.id.textNivel);
        }

    }
}
