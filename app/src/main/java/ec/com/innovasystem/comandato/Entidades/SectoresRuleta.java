package ec.com.innovasystem.comandato.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by InnovaCaicedo on 15/10/2016.
 */
public class SectoresRuleta implements Parcelable{

    private int pos;
    private String puntos;
    private int item_id;
    private int cantidad;
    public SectoresRuleta(Parcel in) {
        pos = in.readInt();
        puntos=in.readString();
        item_id=in.readInt();
        cantidad=in.readInt();
    }

    public static final Creator<SectoresRuleta> CREATOR = new Creator<SectoresRuleta>() {
        @Override
        public SectoresRuleta createFromParcel(Parcel in) {
            return new SectoresRuleta(in);
        }

        @Override
        public SectoresRuleta[] newArray(int size) {
            return new SectoresRuleta[size];
        }
    };

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pos);
        dest.writeString(puntos);
        dest.writeInt(item_id);
        dest.writeInt(cantidad);
    }
    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
