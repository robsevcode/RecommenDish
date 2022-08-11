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
import android.widget.Toast;

public class FavoritesActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor favoritesCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ListView listFavorites = (ListView)findViewById(R.id.list_favorites);
        try{
            SQLiteOpenHelper starbuzzDatabaseHelper = new ProyectoDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getWritableDatabase();
            favoritesCursor = db.query("FOOD",
                    new String[] {"_id", "NAME"},
                    "FAVORITE = 1", null, null, null, null);

            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(FavoritesActivity.this,
                    android.R.layout.simple_list_item_1,
                    favoritesCursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
            CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();

            adapter.changeCursor(favoritesCursor);

        } catch(SQLException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id){
                Intent intent = new Intent(FavoritesActivity.this, ShowFavoriteActivity.class);
                intent.putExtra(ShowFavoriteActivity.EXTRA_FOODNO, (int)id);
                startActivity(intent);
            }
        });
    }

    public void onRestart(){
        super.onRestart();
        try{
            ProyectoDatabaseHelper proyectoDatabaseHelper = new ProyectoDatabaseHelper(this);
            db = proyectoDatabaseHelper.getReadableDatabase();
            Cursor newCursor = db.query("FOOD",
                    new String[] {"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            ListView listFavorites = (ListView)findViewById(R.id.list_favorites);
            CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
            adapter.changeCursor(newCursor);
            favoritesCursor = newCursor;
        } catch (SQLException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        favoritesCursor.close();
        db.close();
    }
}
