package org.projectes.androidshopping.DAO;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by mrr on 12/05/15.
 * DAO Base per gestionar la base de dades.
 */


public abstract class DAOBase<T> extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_db.s3db";
    private static final int DATABASE_VERSION = 1;
    protected String TABLE_NAME = " ";
    protected SQLiteDatabase myDB;
    private Context context;

    public DAOBase(Context context, String table_name){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.TABLE_NAME = table_name;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        AssetManager mng = this.context.getAssets();
        InputStream input;
        String CREATE_USUARIS = "CREATE TABLE usuaris(\n" +
                "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\temail TEXT NOT NULL,\n" +
                "\thash_password TEXT NOT NULL,\n" +
                "\tgenere INTEGER,\n" +
                "\tnom TEXT,\n" +
                "\tedat INTEGER,\n" +
                "\trol INTEGER,\n" +
                "\tlogged_in INTEGER,\n" +
                "\tdeleted INTEGER\n" +
                ");";
        db.execSQL(CREATE_USUARIS);
        String CREATE_COMPRA = "CREATE TABLE compra(\n" +
                "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\tid_usuari INTEGER,\n" +
                "\tdata INTEGER,\n" +
                "\tFOREIGN KEY (id_usuari) REFERENCES usuaris(id)\n" +
                ");";
        db.execSQL(CREATE_COMPRA);
        String CREATE_PRODUCTE = "CREATE TABLE producte(\n" +
                "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\tid_remot INTEGER,\n" +
                "\tnom TEXT,\n" +
                "\tdescripcio TEXT,\n" +
                "\tpreu REAL,\n" +
                "\tactiu INTEGER,\n" +
                "\tstock INTEGER,\n" +
                "\tdeleted INTEGER,\n" +
                "\timatge TEXT\n" +
                ");";
        db.execSQL(CREATE_PRODUCTE);
        String CREATE_TAG = "CREATE TABLE tag(\n" +
                "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\tnom TEXT\n" +
                ");";
        db.execSQL(CREATE_TAG);
        String CREATE_HISTORIALPRODUCTE = "CREATE TABLE historialProducte(\n" +
                "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\tnom TEXT,\n" +
                "\tpreu REAL\n" +
                ");";
        db.execSQL(CREATE_HISTORIALPRODUCTE);
        String CREATE_COMPRA_HISTORIALPRODUCTE = "CREATE TABLE compra_historialProducte(\n" +
                "\tid_compra INTEGER NOT NULL,\n" +
                "\tid_historialProducte INTEGER NOT NULL,\n" +
                "\tquantitat INTEGER,\n" +
                "\tpreu REAL,\n" +
                "\tFOREIGN KEY (id_compra) REFERENCES compra(id),\n" +
                "\tFOREIGN KEY (id_historialProducte) REFERENCES historialProducte(id)\n" +
                "); ";
        db.execSQL(CREATE_COMPRA_HISTORIALPRODUCTE);
        String CREATE_PRODUCTE_TAG = "CREATE TABLE producte_tag(\n" +
                "\tid_tag INTEGER NOT NULL,\n" +
                "\tid_producte INTEGER NOT NULL,\n" +
                "\tFOREIGN KEY (id_tag) REFERENCES tag(id),\n" +
                "\tFOREIGN KEY (id_producte) REFERENCES producte(id)\n" +
                ");";
        db.execSQL(CREATE_PRODUCTE_TAG);
        String CREATE_WS_DATA = "CREATE TABLE ws_data(\n" +
                "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\tdata INTEGER,\n" +
                "\tnomTaula TEXT\n" +
                ");";
        db.execSQL(CREATE_WS_DATA);
        //Inserts
        String INSERT_USERS = "INSERT INTO usuaris (email, hash_password, genere, nom, edat, rol, logged_in, deleted) VALUES ('admin@salleurl.edu', '3fc0a7acf087f549ac2b266baf94b8b1' , 1, 'Admin', 20, 1, 0, 0), ('user@salleurl.edu', '3fc0a7acf087f549ac2b266baf94b8b1', 1, 'User', 20, 2, 0, 0)";
        db.execSQL(INSERT_USERS);
        Log.d("BBDD:", "Creada!!!");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("BBDD:", "onUpgrade");
    }

    //Metodes per interectuar amb la BBDD
    public void openReadOnly(){
        this.myDB = super.getReadableDatabase();
    }

    public void openWrite(){
        this.myDB = super.getWritableDatabase();
    }

    public void closeDatabase(){
        if (this.myDB != null) this.myDB.close();
        super.close();
    }
    //----------------------------------

    public T selectByID(long id){
        openReadOnly();
        String sql="SELECT * FROM " + TABLE_NAME + " where id = ? LIMIT 1" ;
        Cursor cursor = myDB.rawQuery(sql, new String []{Long.toString(id)});
        cursor.moveToFirst();
        T element = LoadFromCursor(cursor);
        cursor.close();
        closeDatabase();
        return element;
    }

    public ArrayList<T> selectAllNotDeleted(){
        ArrayList<T> listT = new ArrayList<T>();
        openReadOnly();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = 0";
        Cursor cursor = myDB.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Log.d("HA ENTRAT", "ha entrat en el bucle del select de la BBDD");
            T element = LoadFromCursor(cursor);
            listT.add(element);
            cursor.moveToNext();
        }
        cursor.close();
        this.closeDatabase();
        return listT;
    }

    public ArrayList<T> selectAll(){
        ArrayList<T> listT = new ArrayList<T>();
        openReadOnly();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = myDB.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Log.d("HA ENTRAT", "ha entrat en el bucle del select de la BBDD");
            T element = LoadFromCursor(cursor);
            listT.add(element);
            cursor.moveToNext();
        }
        cursor.close();
        this.closeDatabase();
        return listT;
    }

    public int deleteById(int id){
        int nError = 1;
        try{
            openWrite();
            String sql = "UPDATE " + TABLE_NAME + " SET deleted = 1 WHERE id = ?";
            SQLiteStatement statement = myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Integer.toString(id)});
            nError = statement.executeUpdateDelete();
            Log.i("--DELETE--", "Ha entrat al delete");
            statement.close();
        }catch (Exception ex){
            Log.d("ERROR", ex.toString());
        }finally {
            this.closeDatabase();
        }
        return nError;
    }

    public abstract void delete(T obj);

    public abstract long insert(T obj);

    public abstract void update(T obj);

    protected abstract T LoadFromCursor(Cursor cursor);

}
