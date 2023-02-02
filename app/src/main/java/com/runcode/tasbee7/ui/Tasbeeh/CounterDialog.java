package com.runcode.tasbee7.ui.Tasbeeh;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.runcode.tasbee7.R;

public class CounterDialog extends AppCompatDialogFragment
{
    EditText numberEd ;

    private CounterDialogListener mListener ;

    public void setListener(CounterDialogListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogTheme);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.prayer_counter_dialog,null);
        numberEd = view.findViewById(R.id.prayerNumberED);
        builder.setView(view)
                .setNegativeButton(R.string.canceled, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int number = Integer.parseInt(numberEd.getText().toString()) ;
                        mListener.applyCounter(number);
                    }
                })
        ;

       return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (CounterDialogListener) context;
        } catch (ClassCastException cce){
            cce.printStackTrace();
        }
    }

    public interface CounterDialogListener
    {
        void applyCounter(int number);
    }
}
