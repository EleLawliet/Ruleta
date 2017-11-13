package ec.com.innovasystem.comandato.Adaptadores;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ec.com.innovasystem.comandato.Actividades.PremiosFlipper;
import ec.com.innovasystem.comandato.Entidades.PremiosObj;
import ec.com.innovasystem.comandato.R;

/**
 * Created by InnovaCaicedo on 11/12/2016.
 */

public class PremioFliperAdapter extends BaseAdapter {
    public ArrayList<PremiosObj> premio;
    PremiosFlipper premiosAdap;
    LayoutInflater inflter;
    AdapterViewFlipper adapter;
    public PremioFliperAdapter(PremiosFlipper prem , AdapterViewFlipper adapter)
    {
        premio=new ArrayList<>();
        premiosAdap=prem;
        inflter=(LayoutInflater.from(premiosAdap));
       this.adapter=adapter;
    }

    @Override
    public int getCount() {
        return premio.size();
    }

    @Override
    public Object getItem(int position) {
        return premio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflter.inflate(R.layout.flipper_premio, null);
        }
        PremiosObj premiosObj =premio.get(position);
        Typeface tf = Typeface.createFromAsset(premiosAdap.getAssets(),"tipografias/MyriadPro-Regular.otf");
        Typeface tf2 = Typeface.createFromAsset(premiosAdap.getAssets(),"tipografias/MyriadPro-Light.otf");
        ImageView imgFlip=(ImageView)convertView.findViewById(R.id.imgFlip);
        ImageView imgIzquierda=(ImageView)convertView.findViewById(R.id.imgIzquierda);
        ImageView imgDerecha=(ImageView)convertView.findViewById(R.id.imgDerecha);
        TextView textTitulo=(TextView)convertView.findViewById(R.id.textTitulo);
        textTitulo.setTypeface(tf);
        TextView textDescripcion=(TextView)convertView.findViewById(R.id.textDescripcion);
        textDescripcion.setTypeface(tf2);
        TextView textLugar=(TextView)convertView.findViewById(R.id.textLugar);
        textLugar.setTypeface(tf);
        TextView textDireccion=(TextView)convertView.findViewById(R.id.textDireccion);
        textDireccion.setTypeface(tf2);
        TextView textDescripcionFactura=(TextView)convertView.findViewById(R.id.textDescripcionFactura);
        textDescripcionFactura.setTypeface(tf2);
        TextView textDescripcionCompra=(TextView)convertView.findViewById(R.id.textDescripcionCompra);
        textDescripcionCompra.setTypeface(tf2);
        TextView textFactura=(TextView)convertView.findViewById(R.id.textFactura);
        textFactura.setTypeface(tf);
        TextView textCompra=(TextView)convertView.findViewById(R.id.textCompra);
        textCompra.setTypeface(tf);
        textTitulo.setText(premiosObj.getTitulo());
       /* textDescripcion.setText(premiosObj.getDescripcion());
        textLugar.setText(premiosObj.getLugar());
        textDireccion.setText(premiosObj.getDireccion());
        textDescripcionFactura.setText(String.valueOf(premiosObj.getNumFactura()));
        textDescripcionCompra.setText(premiosObj.getDescripcionCmpra());
        imgIzquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.showPrevious();
            }
        });
        imgDerecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.showNext();
            }
        });
        Glide
                .with(premiosAdap)
                .load(premiosObj.getImage())
                .fitCenter()
                .placeholder(R.drawable.comandato_png)
                .crossFade()
                .into(imgFlip);*/

        return convertView;
    }
}
