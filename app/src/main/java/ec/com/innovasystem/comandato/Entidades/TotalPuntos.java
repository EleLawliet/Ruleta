package ec.com.innovasystem.comandato.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by InnovaCaicedo on 15/12/2016.
 */

public class TotalPuntos implements Parcelable {

    private String nombre;
    private int id;
    private int total;
    private String image;
    private int padre;
    public TotalPuntos(Parcel in) {
        nombre = in.readString();
        id=in.readInt();
        total=in.readInt();
        image=in.readString();
        padre=in.readInt();
    }

    public static final Creator<TotalPuntos> CREATOR = new Creator<TotalPuntos>() {
        @Override
        public TotalPuntos createFromParcel(Parcel in) {
            return new TotalPuntos(in);
        }

        @Override
        public TotalPuntos[] newArray(int size) {
            return new TotalPuntos[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeInt(id);
        dest.writeInt(total);
        dest.writeString(image);
        dest.writeInt(padre);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPadre() {
        return padre;
    }

    public void setPadre(int padre) {
        this.padre = padre;
    }
}
