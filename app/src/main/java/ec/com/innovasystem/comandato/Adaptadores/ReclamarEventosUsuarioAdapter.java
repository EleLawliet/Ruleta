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

import ec.com.innovasystem.comandato.Actividades.ReclamarEventousuario;
import ec.com.innovasystem.comandato.Entidades.EventoUsuarioObj;
import ec.com.innovasystem.comandato.R;

/**
 * Created by InnovaCaicedo on 3/1/2017.
 */

public class ReclamarEventosUsuarioAdapter extends RecyclerView.Adapter<ReclamarEventosUsuarioAdapter.MetaViewHolder> {

    public List<EventoUsuarioObj> items;
    public int id;
    ReclamarEventousuario reclamar;
    public ReclamarEventosUsuarioAdapter(ReclamarEventousuario reclamar)
    {
        this.items=new ArrayList<>();
        this.reclamar=reclamar;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ReclamarEventosUsuarioAdapter.MetaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_eventos_usuarios, viewGroup, false);
        return new ReclamarEventosUsuarioAdapter.MetaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MetaViewHolder viewHolder, final int position) {
        final EventoUsuarioObj premiosObj = items.get(position);
        Typeface tf = Typeface.createFromAsset(reclamar.getAssets(),"tipografias/DaxWide-Light.otf");
        viewHolder.textTitulo.setText(premiosObj.getNombre());
        viewHolder.textDescripcion.setText(premiosObj.getDescripcion());
        viewHolder.textDescripcion.setTypeface(tf);
        viewHolder.textFecha.setText("Válido hasta: "+premiosObj.getFecha());
        viewHolder.textFecha.setTypeface(tf);
        viewHolder.textCosto.setText(String.valueOf(premiosObj.getCosto()));
        Picasso.with(reclamar)
                .load(premiosObj.getImage())
                .placeholder(R.drawable.icono_puntocanje)
                .error(R.drawable.icono_puntocanje)               // optional
                .into(viewHolder.imgMoneda);
        viewHolder.textCanjear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reclamar.reclamarEventoUsuario(premiosObj, position, viewHolder.textCanjear)    ;
            }
        });
    }

    public class MetaViewHolder extends RecyclerView.ViewHolder
    {
        // Campos respectivos de un item
        private TextView textTitulo, textCanjear, textFecha, textNivel, textCosto, textDescripcion;
        private ImageView imgMoneda;
        public MetaViewHolder(final View v) {
            super(v);
//            Typeface fontTitle = Typeface.createFromAsset(context.getResources().getAssets(), "AkzidenzGrotesk-LightCond.otf");
            textTitulo=(TextView)v.findViewById(R.id.textTitulo);
            textCanjear=(TextView)v.findViewById(R.id.textCanjear);
            textFecha=(TextView)v.findViewById(R.id.textFecha);
            textNivel=(TextView)v.findViewById(R.id.textNivel);
            textCosto=(TextView)v.findViewById(R.id.textCosto);
            textDescripcion=(TextView)v.findViewById(R.id.textDescripcion);
            imgMoneda=(ImageView)v.findViewById(R.id.imgMoneda);
        }

    }

}
