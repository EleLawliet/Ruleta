package ec.com.innovasystem.comandato.Adaptadores;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import android.media.MediaPlayer;
import java.util.ArrayList;
import java.util.List;

import ec.com.innovasystem.comandato.Entidades.TotalPuntos;
import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.Ruleta.RuletaActivity;

/**
 * Created by InnovaCaicedo on 27/12/2016.
 */

public class Monedas_Ruletas_Adapter extends RecyclerView.Adapter<Monedas_Ruletas_Adapter.MetaViewHolder> {

    public List<TotalPuntos> items;
    public int id;
    public int monedasGanadas=0 ;
    public ArrayList<TotalPuntos> lstPuntosMaximos;
    private Context context;
    public List<TotalPuntos> itemsAnteriores;
    public int pos;
    MediaPlayer mp;
    Handler handler;
    Runnable mRunnable;
    public Monedas_Ruletas_Adapter(Context context)
    {
        this.items=new ArrayList<>();
        this.context=context;
        handler= new Handler();
        mp = MediaPlayer.create(context,R.raw.sonido_monedas);
        lstPuntosMaximos= new ArrayList<>();
        this.itemsAnteriores= new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Monedas_Ruletas_Adapter.MetaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_monedas_ruleta, viewGroup, false);
        return new Monedas_Ruletas_Adapter.MetaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Monedas_Ruletas_Adapter.MetaViewHolder viewHolder, int position) {
        final TotalPuntos monedasObj = items.get(position);
        final TotalPuntos monedasObjAnteriores=itemsAnteriores.get(position);
        final  TotalPuntos puntosMaximos=lstPuntosMaximos.get(position);
        final Typeface tf = Typeface.createFromAsset(context.getAssets(),"tipografias/MyriadPro-BoldCond_2.otf");

        viewHolder.textTitulo.setTypeface(tf);
        Log.i("monedas","monedas pos "+pos);
        Picasso.with(context)
                .load(monedasObj.getImage())
                .placeholder(R.drawable.moneda_oro)
                .error(R.drawable.moneda_oro)               // optional
                .into(viewHolder.imgMoneda);
        viewHolder.textTitulo.setText("" + monedasObj.getTotal());
        if (pos==monedasObj.getId()){
            //primero sumar

            Glide.with(context)
                    .load(R.drawable.giftcomprimido)
                    .asGif()
                    .placeholder(R.drawable.moneda_oro)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(viewHolder.iv_animacion);
            mp.start();

            /*if(monedasObj.getTotal()!=monedasObjAnteriores.getTotal()){//MAXIMO ES 5
                ValueAnimator animator = ValueAnimator.ofInt(monedasObjAnteriores.getTotal(),monedasObjAnteriores.getTotal()+monedasGanadas);
                animator.setDuration(2000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        viewHolder.textTitulo.setText("" + animation.getAnimatedValue().toString());
                    }
                });
                animator.start();
                if(monedasObjAnteriores.getTotal()+monedasGanadas >= puntosMaximos.getPadre()){//si monedas anteriores + moendas actuales es mayorr a padre resta
                    mRunnable= new Runnable() {
                        @Override
                        public void run() {
                            ValueAnimator animator2 = ValueAnimator.ofInt(monedasObjAnteriores.getTotal()+monedasGanadas,monedasObj.getTotal());
                            animator2.setDuration(3000);
                            animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    viewHolder.textTitulo.setText("" + animation.getAnimatedValue().toString());
                                }
                            });
                            animator2.start();
                        }
                    };
                    handler.postDelayed(mRunnable,3000);
                }
            }
        }else {
            if(monedasObj.getTotal()<monedasObjAnteriores.getTotal()) {//MAXIMO ES 5
                ValueAnimator animator = ValueAnimator.ofInt(monedasObjAnteriores.getTotal(),(monedasObjAnteriores.getTotal()+monedasObj.getTotal()));
                animator.setDuration(2000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        viewHolder.textTitulo.setText("" + animation.getAnimatedValue().toString());
                    }
                });
                animator.start();
                mRunnable= new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator animator2 = ValueAnimator.ofInt((monedasObjAnteriores.getTotal()+monedasObj.getTotal())+monedasGanadas,monedasObj.getTotal());
                        animator2.setDuration(3000);
                        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator animation) {
                                viewHolder.textTitulo.setText("" + animation.getAnimatedValue().toString());
                            }
                        });
                        animator2.start();
                    }
                };
                handler.postDelayed(mRunnable,3000);
            }
            else if(monedasObj.getTotal()>monedasObjAnteriores.getTotal()){
                ValueAnimator animator = ValueAnimator.ofInt(monedasObjAnteriores.getTotal(),monedasObj.getTotal());
                animator.setDuration(2000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        viewHolder.textTitulo.setText("" + animation.getAnimatedValue().toString());
                    }
                });
                animator.start();
            }else {
                viewHolder.textTitulo.setText("" + monedasObj.getTotal());
            }*/
        }

    }


    public class MetaViewHolder extends RecyclerView.ViewHolder
    {

        public TextView textTitulo;
        public ImageView imgMoneda;
        public ImageView iv_animacion;

        public MetaViewHolder(final View v) {
            super(v);
            textTitulo = (TextView) v.findViewById(R.id.textTitulo);
            imgMoneda = (ImageView)v.findViewById(R.id.iv_moneda);
            iv_animacion=(ImageView)v.findViewById(R.id.iv_animacion);
        }

    }
}
