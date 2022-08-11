package com.toto.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class RecommendActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        Button continueButton = (Button) findViewById(R.id.Continue1_button);
        continueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClickContinue();
            }
        });
    }

    public void onClickContinue(){
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        String time = spin.getSelectedItem().toString();
        Intent intent = new Intent(RecommendActivity.this, TimeActivity.class);
        intent.putExtra(TimeActivity.TIME_STRING, time);
        startActivity(intent);
    }
}
