package org.projectes.androidshopping.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.projectes.androidshopping.DAObject.Producte;

/**
 * Created by mrr on 30/05/15.
 */
public class DAOProductes extends DAOBase<Producte> {
    private static final String TABLE_NAME_PRODUCTE = "producte";


    public DAOProductes(Context context) {
        super(context, TABLE_NAME_PRODUCTE);
    }

    public Producte selectByRemoteID(long id){
        openReadOnly();
        String sql="SELECT * FROM " + TABLE_NAME + " where id_remot = ? LIMIT 1" ;
        Cursor cursor = myDB.rawQuery(sql, new String []{Long.toString(id)});
        cursor.moveToFirst();
        Producte element = LoadFromCursor(cursor);
        cursor.close();
        closeDatabase();
        return element;
    }

    @Override
    public void delete(Producte obj) {
        int nError = 1;
        try{
            openWrite();
            String sql = "UPDATE " + TABLE_NAME_PRODUCTE + " SET deleted = 1 WHERE id = ?";
            SQLiteStatement statement = myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Integer.toString(obj.getId())});
            nError = statement.executeUpdateDelete();
            Log.i("--DELETE--", "Ha entrat al delete");
            statement.close();
        }catch (Exception ex){
            Log.d("ERROR", ex.toString());
        }finally {
            this.closeDatabase();
        }
    }

    @Override
    public long insert(Producte producte) {
        long inserted_id = -1;
        try{
            openWrite();
            String sql="INSERT INTO "+TABLE_NAME_PRODUCTE+" (id_remot, nom, descripcio, preu, actiu, stock, imatge, deleted) VALUES(?,?,?,?,?,?,?,0)";
            SQLiteStatement statement = this.myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Integer.toString(producte.getId_remot()), producte.getNombre(), producte.getDescripcion(), Float.toString(producte.getPrecio()), Integer.toString(producte.isActivo() ? 1 : 0), Integer.toString(producte.getStock()), producte.getImage()});
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
    public void update(Producte p) {
        int nError = 0;
        try{
            openWrite();
            String sql = "UPDATE " + TABLE_NAME_PRODUCTE + " SET id_remot = ?, nom = ?, descripcio = ?, preu = ?, actiu = ?, stock = ?, imatge = ?, deleted = ? WHERE id = ?";
            SQLiteStatement statement = myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Integer.toString(p.getId_remot()), p.getNombre(), p.getDescripcion(), Float.toString(p.getPrecio()), Integer.toString(p.isActivo() ? 1 : 0), Integer.toString(p.getStock()), p.getImage(), Integer.toString(p.getDeleted()), Integer.toString(p.getId())});
            nError = statement.executeUpdateDelete();
            Log.i("--UPDATE--", Integer.toString(p.getId_remot()));
            statement.close();
        }catch (Exception ex){
            Log.e("ERROR", ex.toString());
        }finally {
            this.closeDatabase();
        }
    }

    @Override
    protected Producte LoadFromCursor(Cursor cursor) {
        Producte result = null;
        if (cursor != null){
            if (!cursor.isAfterLast()){
                result = new Producte();
                result.setId(cursor.getInt(cursor.getColumnIndex("id")));
                result.setId_remot(cursor.getInt(cursor.getColumnIndex("id_remot")));
                result.setNombre(cursor.getString(cursor.getColumnIndex("nom")));
                result.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcio")));
                result.setPrecio(cursor.getFloat(cursor.getColumnIndex("preu")));
                result.setActivo(cursor.getInt(cursor.getColumnIndex("actiu")) == 1);
                result.setStock(cursor.getInt(cursor.getColumnIndex("stock")));
                result.setImage(cursor.getString(cursor.getColumnIndex("imatge")));
            }
        }
        return result;
    }


}
