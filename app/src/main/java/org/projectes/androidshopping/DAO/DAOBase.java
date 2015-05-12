package org.projectes.androidshopping.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by mrr on 12/05/15.
 */


public abstract class DAOBase<T> extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_db.db";
    private static final int DATABASE_VERSION = 1;
    protected String TABLE_NAME = " ";
    protected SQLiteDatabase myDB;

    //TODO crear el database create
    private static final String DATABASE_CREATE_1 = "CREATE TABLE Product(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, precio REAL , activo INTEGER, stock INTEGER, foto TEXT, deleted INTEGER)";
    private static final String DATABASE_CREATE_2 = "CREATE TABLE Tag(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT)";
    private static final String DATABASE_CREATE_3 = "CREATE TABLE R_Product_Tag(id INTEGER PRIMARY KEY AUTOINCREMENT, id_product INTEGER, id_tag INTEGER, FOREIGN KEY (id_product) REFERENCES Product (id), FOREIGN KEY (id_tag) REFERENCES Tag (id))";


    public DAOBase(Context context, String table_name){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.TABLE_NAME = table_name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
        String sql="SELECT * FROM " + TABLE_NAME + " where id = ? AND deleted = 0 LIMIT 1" ;
        Cursor cursor = myDB.rawQuery(sql, new String []{new Long(id).toString()});
        cursor.moveToFirst();
        T element = LoadFromCursor(cursor);
        cursor.close();
        closeDatabase();
        return element;
    }

    public ArrayList<T> selectAll(){
        ArrayList<T> listT = new ArrayList<T>();
        openReadOnly();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = 0";
        Cursor cursor = myDB.rawQuery(sql, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
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
            statement.bindAllArgsAsStrings(new String[]{new Integer(id).toString()});
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

    public abstract T LoadFromCursor(Cursor cursor);
}
