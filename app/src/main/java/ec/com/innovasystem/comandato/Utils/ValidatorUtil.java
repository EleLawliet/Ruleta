package ec.com.innovasystem.comandato.Utils;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ec.com.innovasystem.comandato.Entidades.ObjectHash;
import ec.com.innovasystem.comandato.R;

/**
 * Created by InnovaCaicedo on 7/4/2016.
 */
public class ValidatorUtil {
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    /**
     * Validate given email with regular expression.
     *
     * @param email
     *            email for validation
     * @return true valid email, otherwise false
     */
    public static boolean validateEmail(EditText email, String mensaje) {
        boolean  valido=Texto(email, "minimo 4 caracteres");;
        if(valido) {
            // Compiles the given regular expression into a pattern.
            Pattern pattern = Pattern.compile(PATTERN_EMAIL);
            Matcher matcher = pattern.matcher(email.getText().toString());
            if(!matcher.matches())
                email.setError(mensaje);
            return matcher.matches();
        }
        return valido;
    }
    public static boolean Texto(EditText editText, String mensaje){
        Log.i("texto","valor texto "+editText.getText());
        boolean valido = editText.getText()!=null&& editText.getText().toString().trim().length()>=3;
        if(!valido)
            editText.setError(mensaje);
        return valido;
    }
    public static boolean Texto(String texto)
    {
        Log.i("texto","valor texto "+texto);
        return texto!=null&& texto.trim().length()>=3;
    }

    public static boolean validarIdentificacion(EditText textNumero){
        boolean validaCedula=false;
        String numero;
        if(textNumero.getText().toString()==null){
            textNumero.setError("La identificación no puede ser nulo");
            return validaCedula;
        }else
        {
            numero=textNumero.getText().toString();
            int suma = 0;
            int residuo = 0;
            boolean privada = false;
            boolean publica = false;
            boolean natural = false;
            int numeroProvincias = 24;
            int digitoVerificador = 0;
            int modulo = 11;

            int d1, d2, d3, d4, d5, d6, d7, d8, d9, d10;
            int p1, p2, p3, p4, p5, p6, p7, p8, p9;

            d1 = d2 = d3 = d4 = d5 = d6 = d7 = d8 = d9 = d10 = 0;
            p1 = p2 = p3 = p4 = p5 = p6 = p7 = p8 = p9 = 0;


				/* Verifico que el campo no contenga letras */

            try {
                Long.parseLong(numero);
            }
            catch (NumberFormatException nfe){
                textNumero.setError("No puede ingresar caracteres en el número");
                return validaCedula;
            }

            if (numero.length() < 10 || numero.length() > 13) {
                textNumero.setError("El número de cédula es incorrecto");
                return validaCedula;
            }

            // Los primeros dos digitos corresponden al codigo de la provincia
            int provincia = Integer.parseInt(numero.substring(0, 2));

            if (provincia <= 0 || provincia > numeroProvincias) {
                textNumero.setError("El código de la provincia (dos primeros dígitos) es inválido");
                return validaCedula;
            }

            // Aqui almacenamos los digitos de la cedula en variables.
            d1 = Integer.parseInt(numero.substring(0, 1));
            d2 = Integer.parseInt(numero.substring(1, 2));
            d3 = Integer.parseInt(numero.substring(2, 3));
            d4 = Integer.parseInt(numero.substring(3, 4));
            d5 = Integer.parseInt(numero.substring(4, 5));
            d6 = Integer.parseInt(numero.substring(5, 6));
            d7 = Integer.parseInt(numero.substring(6, 7));
            d8 = Integer.parseInt(numero.substring(7, 8));
            d9 = Integer.parseInt(numero.substring(8, 9));
            d10 = Integer.parseInt(numero.substring(9, 10));

            // El tercer digito es:
            // 9 para sociedades privadas y extranjeros
            // 6 para sociedades publicas
            // menor que 6 (0,1,2,3,4,5) para personas naturales
            if (d3 == 7 || d3 == 8) {
                textNumero.setError("El tercer dígito es inválido");
                return validaCedula;
            }

            // Solo para personas naturales (modulo 10)
            if (d3 < 6) {
                natural = true;
                modulo = 10;
                p1 = d1 * 2;
                if (p1 >= 10)
                    p1 -= 9;
                p2 = d2 * 1;
                if (p2 >= 10)
                    p2 -= 9;
                p3 = d3 * 2;
                if (p3 >= 10)
                    p3 -= 9;
                p4 = d4 * 1;
                if (p4 >= 10)
                    p4 -= 9;
                p5 = d5 * 2;
                if (p5 >= 10)
                    p5 -= 9;
                p6 = d6 * 1;
                if (p6 >= 10)
                    p6 -= 9;
                p7 = d7 * 2;
                if (p7 >= 10)
                    p7 -= 9;
                p8 = d8 * 1;
                if (p8 >= 10)
                    p8 -= 9;
                p9 = d9 * 2;
                if (p9 >= 10)
                    p9 -= 9;
            }

            // Solo para sociedades publicas (modulo 11)
            // Aqui el digito verficador esta en la posicion 9, en las otras 2
            // en la pos. 10
            if (d3 == 6) {
                publica = true;
                p1 = d1 * 3;
                p2 = d2 * 2;
                p3 = d3 * 7;
                p4 = d4 * 6;
                p5 = d5 * 5;
                p6 = d6 * 4;
                p7 = d7 * 3;
                p8 = d8 * 2;
                p9 = 0;
            }

				/* Solo para entidades privadas (modulo 11) */
            if (d3 == 9) {
                privada = true;
                p1 = d1 * 4;
                p2 = d2 * 3;
                p3 = d3 * 2;
                p4 = d4 * 7;
                p5 = d5 * 6;
                p6 = d6 * 5;
                p7 = d7 * 4;
                p8 = d8 * 3;
                p9 = d9 * 2;
            }

            suma = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;
            residuo = suma % modulo;

            // Si residuo=0, dig.ver.=0, caso contrario 10 - residuo
            digitoVerificador = residuo == 0 ? 0 : modulo - residuo;
            int longitud = numero.length(); // Longitud del string

            // ahora comparamos el elemento de la posicion 10 con el dig. ver.
            if (publica == true) {
                if (digitoVerificador != d9) {
                    textNumero.setError("El ruc de la empresa del sector público es incorrecto.");
                    return validaCedula;
                }
					/* El ruc de las empresas del sector publico terminan con 0001 */
                if (!numero.substring(9, longitud).equals("0001")) {
                    textNumero.setError("El ruc de la empresa del sector público debe terminar con 0001.");
                    return validaCedula;
                }
            }

            if (privada == true) {
                if (digitoVerificador != d10) {
                    textNumero.setError("El ruc de la empresa del sector privado es incorrecto.");
                    return validaCedula;
                }
                if (!numero.substring(10, longitud).equals("001")) {
                    textNumero.setError("El ruc de la empresa del sector privado debe terminar con 001.");
                    return validaCedula;
                }
            }

            if (natural == true) {
                if (digitoVerificador != d10) {
                    textNumero.setError("El número de cédula es incorrecto.");
                    return validaCedula;
                }
                if (numero.length() > 10 && !numero.substring(10, longitud).equals("001")) {
                    textNumero.setError("El ruc de la persona natural debe terminar con 001.");
                    return validaCedula;
                }
            }
            return validaCedula=true;
        }
    }

    public static boolean validarFecha(TextView fech, String mensaje) throws ParseException {
        Calendar c= Calendar.getInstance();
        Calendar calendar2= Calendar.getInstance();
        boolean fecha;
        try {
            String fechaVerficar=fech.getText().toString().replaceAll("-","/");;
            SimpleDateFormat fechas = new SimpleDateFormat("yyyy/MM/dd");
            if(fech.getText()!=null && fech.getText().toString().length()>0) {
                c.add(Calendar.YEAR, -18);
                int year = c.get(Calendar.YEAR);
                calendar2.setTime(fechas.parse(fechaVerficar));
                int year2 = calendar2.get(Calendar.YEAR);
                if (year2 > 1916 && year2 <= year) {
                    fecha = true;
                } else {
                    fecha = false;
                    fech.setError(mensaje);
                }
                return fecha;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        fech.setError(String.valueOf(R.string.fechaIncorrecta));
        return false;
    }

    public static boolean optArray(JSONArray json)
    {
        // http://code.google.com/p/android/issues/detail?id=13830
        if (json==null ||json.length()==0)
            return false;
        else
            return true;
    }

    public static boolean verFecha2(String fech) throws ParseException {
        fech=fech.replaceAll("-","/");
        SimpleDateFormat fechas1 = new SimpleDateFormat("MM/dd/yyyy");
        Date face=fechas1.parse(fech);
        SimpleDateFormat fechas = new SimpleDateFormat("yyyy/MM/dd");
        Date mm=fechas.parse(fechas.format(face));
        Calendar c= Calendar.getInstance();
        Calendar calendar2= Calendar.getInstance();
        boolean fecha=false;
        try {
            c.add(Calendar.YEAR,-18);
            int year= c.get(Calendar.YEAR);
            calendar2.setTime(mm);
            int year2=calendar2.get(Calendar.YEAR);
            if(year2>1916 &&  year2 <= year )
            {
                fecha=true;
            }
            else
            {
                fecha=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fecha;
    }

     public static boolean verTelefonoLocal(EditText textTelefono, String mensaje)
    {
        boolean telef=false, valido;
        valido=Texto(textTelefono, "ingrese información");
        if (valido) {
            String telefono=textTelefono.getText().toString();
            if (telefono.length() == 9 || telefono.length() == 7) {
                if (telefono.length() == 9) {
                    String sub = telefono.substring(0, 2);
                    if (sub.equals("02") || sub.equals("03") || sub.equals("04") || sub.equals("05") || sub.equals("06") || sub.equals("07")) {
                        telef = true;
                    } else {
                        telef = false;
                        textTelefono.setError(mensaje);
                    }
                }
                if (telefono.length() == 7)
                    telef = true;
            } else
                telef = false;
            return telef;
        }
        return valido;
    }

    public static boolean verTelefono(EditText textTelefono, String mensaje)
    {
        boolean telef, valido;
        String telefono;
        valido=Texto(textTelefono, "ingrese información");
        if (valido) {
            telefono=textTelefono.getText().toString();
            if (telefono.length() == 10) {
                String sub = telefono.substring(0, 2);
                if (sub.equals("09"))
                    telef = true;
                else {
                    telef = false;
                    textTelefono.setError(mensaje);
                }
            } else
                telef = false;
            return telef;
        }
        return  valido;
    }

    public static JSONArray traerValores(ArrayList<ObjectHash> lstText)
    {
        JSONArray parametros= new JSONArray();
        for(int i=0; i<lstText.size();i++)
        {
            JSONObject param= new JSONObject();
            try {
                param.put("nombre",lstText.get(i).getNombre());
                param.put("valor", lstText.get(i).getValor());
                parametros.put(param);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return parametros;
    }

}
