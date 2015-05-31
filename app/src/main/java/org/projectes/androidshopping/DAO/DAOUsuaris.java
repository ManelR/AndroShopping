package org.projectes.androidshopping.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.projectes.androidshopping.Constants.MD5;
import org.projectes.androidshopping.DAObject.Usuari;

/**
 * Created by mrr on 30/05/15.
 */
public class DAOUsuaris extends DAOBase<Usuari> {
    private static final String TABLE_NAME_USER = "usuaris";

    public DAOUsuaris(Context context) {
        super(context, TABLE_NAME_USER);
    }

    public Usuari selectByEmail(String email){
        openReadOnly();
        String sql="SELECT * FROM " + TABLE_NAME_USER + " where email = ? LIMIT 1" ;
        Cursor cursor = myDB.rawQuery(sql, new String []{email});
        cursor.moveToFirst();
        Usuari element = LoadFromCursor(cursor);
        cursor.close();
        closeDatabase();
        return element;
    }

    public Usuari selectByLogged_in(){
        openReadOnly();
        String sql="SELECT * FROM " + TABLE_NAME_USER + " where logged_in = ? LIMIT 1" ;
        Cursor cursor = myDB.rawQuery(sql, new String []{Integer.toString(1)});
        cursor.moveToFirst();
        Usuari element = LoadFromCursor(cursor);
        cursor.close();
        closeDatabase();
        return element;
    }

    @Override
    public void delete(Usuari obj) {
        int nError = 1;
        try{
            openWrite();
            String sql = "UPDATE " + TABLE_NAME_USER + " SET deleted = 1 WHERE id = ?";
            SQLiteStatement statement = myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Integer.toString(obj.getId())});
            nError = statement.executeUpdateDelete();
            Log.i("--DELETE--", "Ha entrat al delete usuari");
            statement.close();
        }catch (Exception ex){
            Log.d("ERROR", ex.toString());
        }finally {
            this.closeDatabase();
        }
    }

    @Override
    public long insert(Usuari user) {
        long id = -1;
        try{
            String hash_pass = MD5.md5(user.getPass());
            openWrite();
            String sql="INSERT INTO "+TABLE_NAME_USER+" (email, hash_password, genere, nom, edat, rol, logged_in, deleted) VALUES(?,?,?,?,?,?,?,0)";
            SQLiteStatement statement = this.myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{user.getEmail(), hash_pass, Integer.toString(user.getGenere()), user.getNom(), Integer.toString(user.getEdat()), Integer.toString(user.getRol()), Integer.toString(user.getLogged_in())});
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
    public void update(Usuari obj) {
        int nError = 0;
        try{
            String hash_pass;
            if (obj.getPass() != null){
                hash_pass = MD5.md5(obj.getPass());
            }else{
                hash_pass = obj.getHash_pass();
            }
            openWrite();
            String sql = "UPDATE " + TABLE_NAME_USER + " SET email = ?, hash_password = ?, genere = ?, nom = ?, edat = ?, rol = ?, logged_in = ?, deleted = ? WHERE id = ?";
            SQLiteStatement statement = myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{obj.getEmail(), hash_pass, Integer.toString(obj.getGenere()), obj.getNom(), Integer.toString(obj.getEdat()), Integer.toString(obj.getRol()), Integer.toString(obj.getLogged_in()), Integer.toString(obj.getDeleted()), Integer.toString(obj.getId())});
            nError = statement.executeUpdateDelete();
            Log.i("--UPDATE--", Integer.toString(obj.getId()));
            statement.close();
        }catch (Exception ex){
            Log.e("ERROR", ex.toString());
        }finally {
            this.closeDatabase();
        }
    }

    @Override
    protected Usuari LoadFromCursor(Cursor cursor) {
        Usuari usuari = null;
        if (cursor != null){
            if (!cursor.isAfterLast()){
                usuari = new Usuari();
                usuari.setId(cursor.getInt(cursor.getColumnIndex("id")));
                usuari.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                usuari.setHash_pass(cursor.getString(cursor.getColumnIndex("hash_password")));
                usuari.setGenere(cursor.getInt(cursor.getColumnIndex("genere")));
                usuari.setNom(cursor.getString(cursor.getColumnIndex("nom")));
                usuari.setEdat(cursor.getInt(cursor.getColumnIndex("edat")));
                usuari.setRol(cursor.getInt(cursor.getColumnIndex("rol")));
                usuari.setLogged_in(cursor.getInt(cursor.getColumnIndex("logged_in")));
                usuari.setDeleted(cursor.getInt(cursor.getColumnIndex("deleted")));
            }
        }
        return usuari;
    }
}
