package com.toto.proyecto;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor favoritesCursor;

    private Button recomendButton;
    private Button shuffleButton;
    private Button favoriteButton;
    private TextView welcomeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcomeTitle = (TextView)findViewById(R.id.welcome_title);

        recomendButton = (Button)findViewById(R.id.recommend_button);
        recomendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClickRecommend();
            }
        });
        shuffleButton = (Button)findViewById(R.id.shuffle_button);

        favoriteButton = (Button)findViewById(R.id.favorites_button);
        favoriteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClickFavorite();
            }
        });
    }

    public void onClickRecommend(){
        Intent intent = new Intent(MainActivity.this, RecommendActivity.class);
        startActivity(intent);
    }

    public void onClickShuffle(View v){
        Intent intent = new Intent(MainActivity.this, ShuffleActivity.class);
        startActivity(intent);
    }

    public void onClickFavorite(){
        Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
        startActivity(intent);
    }
}
