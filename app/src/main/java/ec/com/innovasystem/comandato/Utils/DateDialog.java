package ec.com.innovasystem.comandato.Utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by thinkpadlenovo on 6/14/2016.
 */
@SuppressLint("ValidFragment")
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TextView txtDate;

    public DateDialog(View view)
    {
        txtDate=(TextView)view;
    }

    public Dialog onCreateDialog(Bundle saveInstanceState)
    {
        final Calendar c= Calendar.getInstance();
        c.add(Calendar.YEAR,-18);
        int year= c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this, year,month,day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        String date=year+"-"+(month+1)+"-"+day;
        txtDate.setText(date);
    }
}
