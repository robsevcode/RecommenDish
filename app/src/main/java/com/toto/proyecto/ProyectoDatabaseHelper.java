package com.toto.proyecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProyectoDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "recommendish";
    private static final int DB_VERSION = 1;

    ProyectoDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 1){
            db.execSQL("CREATE TABLE FOOD (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "TIME TEXT, "
                    + "INGREDIENTS TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertFood(db, "Mole", "Molesito bien rico",
                    "3 hrs", "Pollo", R.drawable.mole);

            insertFood(db, "Enchiladas de pollo", "Enchiladas bien ricas",
                    "2.5 hrs", "Pollo", R.drawable.enchiladas);

            insertFood(db, "Pollo asado", "Pollito bien rico",
                    "2 hrs", "Pollo", R.drawable.pollo);

            insertFood(db, "Caldito de pollo", "Caldito bien rico",
                    "1.5 hrs", "Pollo", R.drawable.caldo);

            insertFood(db, "Ensalada de pollo", "Ensaladita bien rica",
                    "1 hrs", "Pollo", R.drawable.ensalada);

            insertFood(db, "Sopes", "Sopes bien ricos",
                    "1 hrs", "Pollo", R.drawable.sopes);

            insertFood(db, "Torta de jamon de pavo", "Tortita bien rico",
                    "0.5 hrs", "Pollo", R.drawable.torta);


            insertFood(db, "Pescado Zarandeado", "Pescado bien rico",
                    "3 hrs", "Mariscos", R.drawable.zarandeado);

            insertFood(db, "Burritos de Camarón", "Burritos bien ricos",
                    "2.5 hrs", "Mariscos", R.drawable.burritos);

            insertFood(db, "Caldo de Pescado", "Caldo bien rico",
                    "2 hrs", "Mariscos", R.drawable.caldopollo);

            insertFood(db, "Pescado empanizado", "Pescadito bien rico",
                    "1.5 hrs", "Mariscos", R.drawable.pescado);

            insertFood(db, "Ensalada de atún", "Atún bien rico",
                    "1 hrs", "Mariscos", R.drawable.aun);

            insertFood(db, "Tacos de Marlin", "Taquitos bien ricos",
                    "0.5 hrs", "Mariscos", R.drawable.tacos);



            insertFood(db, "Birria", "Birria bien rica",
                    "3 hrs", "Carne", R.drawable.birria);

            insertFood(db, "Pozole", "Pozole bien rico",
                    "2.5 hrs", "Carne", R.drawable.pozole);

            insertFood(db, "Bistec ranchero", "Bistec bien rico",
                    "2 hrs", "Carne", R.drawable.bistec);

            insertFood(db, "Albondigas", "Albondigas bien ricas",
                    "1.5 hrs", "Carne", R.drawable.albondigas);

            insertFood(db, "Carne asada", "Carne bien rica",
                    "1 hrs", "Carne", R.drawable.asada);

            insertFood(db, "Milanesa", "Milanesita bien rica",
                    "0.5 hrs", "Carne", R.drawable.milanesa);
        }
        if(oldVersion < 2){
            db.execSQL("ALTER TABLE FOOD ADD COLUMN FAVORITE NUMERIC;");
        }
    }

    private static void insertFood(SQLiteDatabase db, String name, String description, String time, String ingredients, int resourceId){
        ContentValues foodValues = new ContentValues();
        foodValues.put("NAME", name);
        foodValues.put("DESCRIPTION", description);
        foodValues.put("TIME", time);
        foodValues.put("INGREDIENTS", ingredients);
        foodValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("FOOD", null, foodValues);
    }
}
