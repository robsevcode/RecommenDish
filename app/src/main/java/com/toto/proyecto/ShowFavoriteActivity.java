package com.toto.proyecto;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowFavoriteActivity extends AppCompatActivity {

    public static final String EXTRA_FOODNO = "foodNo";
    public String nameSaved;
    public String descriptionSaved;
    public String timeSaved;
    public String ingSaved;
    public int photoSaved;
    public boolean favSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favorite);

        int foodNo = (Integer) getIntent().getExtras().get(EXTRA_FOODNO);

        SQLiteOpenHelper proyectoDatabaseHelper = new ProyectoDatabaseHelper(this);
        try{
            SQLiteDatabase db = proyectoDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("FOOD", new String[]{"NAME", "DESCRIPTION", "TIME",
                            "INGREDIENTS", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?", new String[] {Integer.toString(foodNo)},
                    null, null, null);

            if(cursor.moveToFirst()){

                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                String timeText = cursor.getString(2);
                String ingredientsText = cursor.getString(3);
                int photoID = cursor.getInt(4);
                boolean isFavorite = (cursor.getInt(5) == 1);

                CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
                TextView name = (TextView) findViewById(R.id.name);
                TextView description = (TextView) findViewById(R.id.description);
                TextView time = (TextView) findViewById(R.id.time);
                TextView ingredients = (TextView) findViewById(R.id.ing);
                ImageView photo = (ImageView) findViewById(R.id.photo);

                if(savedInstanceState != null){
                    nameText = savedInstanceState.getString("name");
                    descriptionText = savedInstanceState.getString("description");
                    timeText = savedInstanceState.getString("time");
                    ingredientsText = savedInstanceState.getString("ing");
                    //photoID = savedInstanceState.getInt("photo");
                    isFavorite = savedInstanceState.getBoolean("favorite");
                }


                favorite.setChecked(isFavorite);
                name.setText(nameText);
                description.setText(descriptionText);
                time.setText(timeText);
                ingredients.setText(ingredientsText);
                photo.setImageResource(photoID);
                photo.setImageResource(photoID);
                photo.setContentDescription(nameText);
            }
            cursor.close();
            db.close();
        } catch(SQLException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        TextView name = (TextView) findViewById(R.id.name);
        nameSaved = name.getText().toString();
        savedInstanceState.putString("name", nameSaved);

        TextView description = (TextView) findViewById(R.id.description);
        descriptionSaved = description.getText().toString();
        savedInstanceState.putString("description", descriptionSaved);

        TextView time = (TextView) findViewById(R.id.time);
        timeSaved = time.getText().toString();
        savedInstanceState.putString("time", timeSaved);

        TextView ingredients = (TextView) findViewById(R.id.ing);
        ingSaved = ingredients.getText().toString();
        savedInstanceState.putString("ing", ingSaved);

        ImageView photo = (ImageView) findViewById(R.id.photo);
        photoSaved = photo.getId();
        savedInstanceState.putInt("photo", photoSaved);

        CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
        favSaved = favorite.isChecked();
        savedInstanceState.putBoolean("favorite", favSaved);
    }

    public void onFavoriteClicked(View view){
        int foodNo = (Integer)getIntent().getExtras().get("foodNo");
        new UpdateFoodTask().execute(foodNo);
    }

    private class UpdateFoodTask extends AsyncTask<Integer, Void, Boolean> {
        ContentValues foodValues;

        protected void onPreExecute(){
            CheckBox favorite = (CheckBox)findViewById(R.id.favorite);
            foodValues = new ContentValues();
            foodValues.put("FAVORITE", favorite.isChecked());
        }

        protected Boolean doInBackground(Integer... foods){
            int foodNo = foods[0];
            SQLiteOpenHelper proyectoDatabaseHelper =
                    new ProyectoDatabaseHelper(ShowFavoriteActivity.this);
            try{
                SQLiteDatabase db = proyectoDatabaseHelper.getWritableDatabase();
                db.update("FOOD", foodValues,
                        "_id = ?", new String[]{Integer.toString(foodNo)});
                db.close();
                return true;
            }catch (SQLiteException e){
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(!success){
                Toast toast = Toast.makeText(ShowFavoriteActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
