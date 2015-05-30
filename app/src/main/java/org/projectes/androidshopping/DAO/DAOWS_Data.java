package org.projectes.androidshopping.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.projectes.androidshopping.DAObject.WS_Data;

/**
 * Created by mrr on 30/05/15.
 */
public class DAOWS_Data extends DAOBase<WS_Data> {
    private static final String TABLE_NAME_WS_DATE = "ws_data";

    public DAOWS_Data(Context context) {
        super(context, TABLE_NAME_WS_DATE);
    }

    public WS_Data selectBynomTaula(String nomTaula){
        openReadOnly();
        String sql="SELECT * FROM " + TABLE_NAME_WS_DATE + " where nomTaula = ? LIMIT 1" ;
        Cursor cursor = myDB.rawQuery(sql, new String []{nomTaula});
        cursor.moveToFirst();
        WS_Data element = LoadFromCursor(cursor);
        cursor.close();
        closeDatabase();
        return element;
    }

    @Override
    public void delete(WS_Data obj) {
        //No s'utilitza
    }

    @Override
    public long insert(WS_Data obj) {
        long inserted_id = -1;
        try{
            openWrite();
            String sql="INSERT INTO "+TABLE_NAME_WS_DATE+" (data, nomTaula) VALUES(?,?)";
            SQLiteStatement statement = this.myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Long.toString(obj.getDate()), obj.getNomTaula()});
            inserted_id=statement.executeInsert();
            Log.i("---ID---", new Long(inserted_id).toString());
            Log.i("---SQL---", sql);
            statement.close();
        }catch(Exception ex){
            Log.e("ERROR", ex.toString());
        }finally{
            super.closeDatabase();
        }
        return inserted_id;
    }

    @Override
    public void update(WS_Data obj) {
        int nError = 0;
        try{
            Log.i("---VALORS UPDATE---", obj.getNomTaula() + " " + obj.getDate());
            openWrite();
            String sql = "UPDATE " + TABLE_NAME_WS_DATE + " SET data = ? WHERE id = ?";
            SQLiteStatement statement = myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Long.toString(obj.getDate()), new Integer(obj.getId()).toString()});
            nError = statement.executeUpdateDelete();
            Log.i("--UPDATE--", new Integer(nError).toString());
            statement.close();
        }catch (Exception ex){
            Log.e("ERROR", ex.toString());
        }finally {
            this.closeDatabase();
        }
    }

    @Override
    protected WS_Data LoadFromCursor(Cursor cursor) {
        WS_Data result = null;
        if (cursor != null){
            if (!cursor.isAfterLast()){
                result = new WS_Data();
                result.setId(cursor.getInt(cursor.getColumnIndex("id")));
                result.setDate(cursor.getLong(cursor.getColumnIndex("data")));
                result.setNomTaula(cursor.getString(cursor.getColumnIndex("nomTaula")));
            }
        }
        return result;
    }
}
