package ec.com.innovasystem.comandato.Adaptadores;

/**
 * Created by Junior on 13/11/2015.
 */

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ec.com.innovasystem.comandato.Actividades.PuntosCanjeActivity;
import ec.com.innovasystem.comandato.Entidades.Sucursales;
import ec.com.innovasystem.comandato.R;

/**
 * Created by Junior on 08/09/2015.
 */
public class LugaresAdapter extends RecyclerView.Adapter<LugaresAdapter.MetaViewHolder>  implements ItemClickListener {

    /**
     * Lista de objetos {@link Sucursales} que representan la fuente de datos
     * de inflado
     */
    public List<Sucursales.TiendasBean> items;


    /*
    Contexto donde actua el recycler view
     */
    private PuntosCanjeActivity context;

    public LugaresAdapter(PuntosCanjeActivity context) {
        this.context = context;
        this.items = new ArrayList<Sucursales.TiendasBean>();
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public MetaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_puntos_canje, viewGroup, false);

        return new MetaViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(MetaViewHolder holder, int position) {
        final Sucursales.TiendasBean publicacion = items.get(position);
        Typeface tf = Typeface.createFromAsset(context.getAssets(),"tipografias/MyriadPro-Regular.otf");
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(),"tipografias/MyriadPro-Light.otf");
        holder.nombre_publicacion.setText("" + publicacion.getTitle());
        holder.nombre_publicacion.setTypeface(tf);
        holder.descripcion_publicacion.setText(""+publicacion.getDireccion());
        holder.descripcion_publicacion.setTypeface(tf2);
        Picasso.with(context)
                .load("http://www.riocentroshopping.com/assets/img/upload/big/2b8af3f6ca83cd8e953124bd6926a473.jpg")
                .placeholder(R.drawable.comandato_png)
                .error(R.drawable.comandato_png)               // optional
                .into(holder.img_portada_publicacion);
        holder.img_portada_publicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.verGoogleMaps(publicacion);
            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        /*EjercicioFragment.launch(
                (Fragment) context, String.valueOf(items.get(position).getId()));*/
        Toast.makeText(context,"prueba",Toast.LENGTH_SHORT).show();
    }


    public class MetaViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre_publicacion, descripcion_publicacion , lugar_evento, costo_evento, fecha_evento;
        public ImageView img_portada_publicacion;

        //otros
        public ItemClickListener listener;

        public MetaViewHolder(View v, ItemClickListener listener) {
            super(v);
            nombre_publicacion = (TextView) v.findViewById(R.id.nombre_sucursal);
            img_portada_publicacion = (ImageView)v.findViewById(R.id.img_sucursal);
            descripcion_publicacion=(TextView)v.findViewById(R.id.textDireccion);

            this.listener = listener;

        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());

        }
    }
}



