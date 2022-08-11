package com.toto.proyecto;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RecommendCategoryActivity extends AppCompatActivity {

    public static final String TIME_STRING = "time";
    public static final String ING_STRING = "ing";

    private SQLiteDatabase db;
    private Cursor recommendedCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_category);

        String time = getIntent().getStringExtra(TIME_STRING);
        String ing =  getIntent().getStringExtra(ING_STRING);

        ListView listRecommended = (ListView)findViewById(R.id.list_recommended);
        try{
            SQLiteOpenHelper proyectoDatabaseHelper = new ProyectoDatabaseHelper(this);
            db = proyectoDatabaseHelper.getWritableDatabase();
            recommendedCursor = db.query("FOOD",
                    new String[] {"_id", "NAME"},
                    "TIME = ? AND INGREDIENTS = ?",
                    new String[]{time, ing}, null, null, null);

            CursorAdapter recommendedAdapter = new SimpleCursorAdapter(RecommendCategoryActivity.this,
                    android.R.layout.simple_list_item_1,
                    recommendedCursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1}, 0);
            listRecommended.setAdapter(recommendedAdapter);
            CursorAdapter adapter = (CursorAdapter) listRecommended.getAdapter();

            adapter.changeCursor(recommendedCursor);

        } catch(SQLException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        listRecommended.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id){
                Intent intent = new Intent(RecommendCategoryActivity.this, RecommendedDishActivity.class);
                intent.putExtra(RecommendedDishActivity.EXTRA_FOODNO, (int)id);
                startActivity(intent);
            }
        });
    }
}
