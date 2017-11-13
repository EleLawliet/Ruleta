package ec.com.innovasystem.comandato.Adaptadores;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ec.com.innovasystem.comandato.Entidades.PremiosPromocionesObj;
import ec.com.innovasystem.comandato.Fragmentos.PremiosPromociones;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.Utils.CustomTypefaceSpan;

/**
 * Created by InnovaCaicedo on 23/12/2016.
 */

public class PremiosPromocionesAdapter extends RecyclerView.Adapter<PremiosPromocionesAdapter.MetaViewHolder> {
    public List<PremiosPromocionesObj> items;
    public int id;
    PremiosPromociones promociones;
    public PremiosPromocionesAdapter(PremiosPromociones promociones)
    {
        this.items=new ArrayList<PremiosPromocionesObj>();
        this.promociones=promociones;
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public PremiosPromocionesAdapter.MetaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_premio_promociones, viewGroup, false);
        return new PremiosPromocionesAdapter.MetaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MetaViewHolder viewHolder, int position) {
        final PremiosPromocionesObj premiosObj = items.get(position);
        Typeface tf = Typeface.createFromAsset(promociones.getActivity().getAssets(),"tipografias/DaxWide-Light.otf");
        Typeface tf2 = Typeface.createFromAsset(promociones.getActivity().getAssets(),"tipografias/DaxWide-Bold.otf");
        viewHolder.textCostoString.setTypeface(tf);
        viewHolder.textDescString.setTypeface(tf);
        viewHolder.textTotalString.setTypeface(tf);
        viewHolder.textCosto.setText(typefaceBold(String.valueOf(premiosObj.getCantidad())));
        viewHolder.textCostoPromocion.setText(typefaceBold(String.valueOf(premiosObj.getCantidad()-premiosObj.getCantidadPromocion())));
        viewHolder.textCostoTotal.setText(typefaceBold(String.valueOf(premiosObj.getCantidadPromocion())));
        viewHolder.textPuntos.setText("Nivel: "+String.valueOf(premiosObj.getNivel()));
        viewHolder.textTitulo.setText(typefaceSpanLight(premiosObj.getNombre()).toString().toUpperCase());
        viewHolder.textTitulo.setTypeface(tf);
        viewHolder.textTitulo.setSelected(true);
        viewHolder.TextFecha.setText(typefaceSpanLight("Válido hasta:"+premiosObj.getCaducidad()));
        Picasso.with(promociones.getContext())
                .load(premiosObj.getImage())
                .placeholder(R.drawable.regalo)
                .error(R.drawable.regalo)               // optional
                .into(viewHolder.imgPremio);
        Picasso.with(promociones.getContext())
                .load(premiosObj.getImage2())
                .placeholder(R.drawable.moneda_oro)

                .error(R.drawable.moneda_oro)               // optional
                .into(viewHolder.imgMonedas);
        viewHolder.textCanjear.setTypeface(tf2);
        viewHolder.textCanjear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promociones.canjear(premiosObj.getId());
            }
        });
    }

    public SpannableString typefaceSpanLight(String value)
    {
        Typeface tf = Typeface.createFromAsset(promociones.getActivity().getAssets(),"tipografias/DaxWide-Light.otf");
        SpannableString ss1= new SpannableString(value);
        ss1.setSpan (new CustomTypefaceSpan("", tf), 0, value.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return ss1;
    }

    public SpannableString typefaceBold(String value)
    {
        Typeface tf = Typeface.createFromAsset(promociones.getActivity().getAssets(),"tipografias/DaxWide-Light.otf");
        SpannableString ss1= new SpannableString(value);
        ss1.setSpan (new StyleSpan(Typeface.BOLD), 0, value.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return ss1;
    }

    public class MetaViewHolder extends RecyclerView.ViewHolder
    {
        // Campos respectivos de un item
        public TextView textTitulo, textPuntos, TextFecha, textCosto, textCostoPromocion, textCostoTotal, textCanjear
                , textCostoString, textDescString, textTotalString;
        public ImageView imgPremio, imgMonedas;

        public MetaViewHolder(final View v) {
            super(v);
//            Typeface fontTitle = Typeface.createFromAsset(context.getResources().getAssets(), "AkzidenzGrotesk-LightCond.otf");
            TextFecha = (TextView) v.findViewById(R.id.TextFecha);
            textCostoString=(TextView)v.findViewById(R.id.textCostoString);
            textDescString=(TextView)v.findViewById(R.id.textDescString);
            textTotalString=(TextView)v.findViewById(R.id.textTotalString);
            textTitulo = (TextView) v.findViewById(R.id.textTitulo);
            textCosto = (TextView) v.findViewById(R.id.textCosto);
            textCostoPromocion = (TextView) v.findViewById(R.id.textCostoPromocion);
            textCostoTotal = (TextView) v.findViewById(R.id.textCostoTotal);
            imgPremio = (ImageView)v.findViewById(R.id.imgPremio);
            imgMonedas=(ImageView)v.findViewById(R.id.imgMonedas);
            textPuntos=(TextView)v.findViewById(R.id.textPuntos);
            textCanjear=(TextView)v.findViewById(R.id.textCanjear);
        }

    }
}
