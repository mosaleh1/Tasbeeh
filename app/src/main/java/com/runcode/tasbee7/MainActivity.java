package com.runcode.tasbee7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    TextView totalTasbee7 , counterTotal ;
    int totalTasbee7Number , counterTotalNumber ;
    FloatingActionButton resetButton , incrementButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        declareViews();

        setListenersToIncrement();
    }

    private void setListenersToIncrement() {
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterTotalNumber = 0 ;
                counterTotal.setText(""+counterTotalNumber);
            }
        });

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalTasbee7Number ++ ;
                counterTotalNumber ++ ;
                counterTotal.setText(""+counterTotalNumber);
                totalTasbee7.setText(""+totalTasbee7Number);
            }
        });
    }

    private void declareViews() {
        totalTasbee7 = findViewById(R.id.text_total_number_of_tasbee7);
        counterTotal = findViewById(R.id.counter_text);
        resetButton = findViewById(R.id.reset_button);
        incrementButton = findViewById(R.id.counter_button);
    }
}