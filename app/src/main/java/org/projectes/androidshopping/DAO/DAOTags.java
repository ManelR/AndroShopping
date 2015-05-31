package org.projectes.androidshopping.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.projectes.androidshopping.DAObject.Tag;

/**
 * Created by mrr on 30/05/15.
 */
public class DAOTags extends DAOBase<Tag> {
    private static final String TAULA_TAG = "tag";
    private static final String TAULA_PRODUCTE_TAG = "producte_tag";


    public DAOTags(Context context) {
        super(context, TAULA_TAG);
    }

    public Tag selectByName(String name){
        openReadOnly();
        String sql="SELECT * FROM " + TAULA_TAG + " where nom = ? LIMIT 1" ;
        Cursor cursor = myDB.rawQuery(sql, new String []{name});
        cursor.moveToFirst();
        Tag element = LoadFromCursor(cursor);
        cursor.close();
        closeDatabase();
        return element;
    }

    public void deleteProducte_TagByIDProduct(int id_product){
        long nError = -1;
        try{
            openWrite();
            String sql = "DELETE FROM " + TAULA_PRODUCTE_TAG + " WHERE id_producte = ?";
            SQLiteStatement statement = myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Integer.toString(id_product)});
            nError = statement.executeUpdateDelete();
            Log.i("--DELETE--", Integer.toString(id_product));
            statement.close();
        }catch (Exception ex){
            Log.e("ERROR", ex.toString());
        }finally {
            this.closeDatabase();
        }
    }

    public void insertProducte_Tag(int id_tag, int id_product){
        long id = -1;
        try{
            openWrite();
            String sql="INSERT INTO "+TAULA_PRODUCTE_TAG+" (id_tag, id_producte) VALUES(?, ?)";
            SQLiteStatement statement = this.myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Integer.toString(id_tag), Integer.toString(id_product)});
            id=statement.executeInsert();
            Log.i("---ID---", new Long(id).toString());
            Log.i("---SQL---", sql);
            statement.close();
        }catch(Exception ex){
            Log.e("ERROR", ex.toString());
        }finally{
            super.closeDatabase();
        }
    }


    @Override
    public void delete(Tag obj) {
        //NO S'utilitza
    }

    @Override
    public long insert(Tag obj) {
        long id = -1;
        try{
            openWrite();
            String sql="INSERT INTO "+TAULA_TAG+" (nom) VALUES(?)";
            SQLiteStatement statement = this.myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{obj.getNom()});
            id=statement.executeInsert();
            Log.i("---ID---", new Long(id).toString());
            Log.i("---SQL---", sql);
            statement.close();
        }catch(Exception ex){
            Log.e("ERROR", ex.toString());
        }finally{
            super.closeDatabase();
        }
        return id;
    }

    @Override
    public void update(Tag obj) {

    }

    @Override
    protected Tag LoadFromCursor(Cursor cursor) {
        Tag result = null;
        if (cursor != null){
            if (!cursor.isAfterLast()){
                result = new Tag();
                result.setId(cursor.getInt(cursor.getColumnIndex("id")));
                result.setNom(cursor.getString(cursor.getColumnIndex("nom")));
            }
        }
        return result;
    }


}
