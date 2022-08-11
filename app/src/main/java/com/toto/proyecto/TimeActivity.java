package com.toto.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class TimeActivity extends AppCompatActivity {

    public static final String TIME_STRING = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        final String time = getIntent().getStringExtra(TIME_STRING);

        Button continue1Button = (Button) findViewById(R.id.Continue1_button);
        continue1Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClickContinue1(time);
            }
        });
    }

    public void onClickContinue1(String time){
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        String ing = spin.getSelectedItem().toString();

        Intent intent = new Intent(TimeActivity.this, RecommendCategoryActivity.class);

        intent.putExtra(RecommendCategoryActivity.TIME_STRING, time);
        intent.putExtra(RecommendCategoryActivity.ING_STRING, ing);
        startActivity(intent);
    }
}
