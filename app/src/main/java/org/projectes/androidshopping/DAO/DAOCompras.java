package org.projectes.androidshopping.DAO;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.projectes.androidshopping.DAObject.Compra;
import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.MyApplication;

import java.util.ArrayList;

/**
 * Created by alloveras on 1/06/15.
 */
public class DAOCompras extends DAOBase<Compra>{

    private static final String TABLE_COMPRAS = "compra";
    private static final String TABLE_COMPRA_HISTORIAL_PRODUCTE = "compra_historialProducte";
    private static final String TABLE_HISTORIAL_PRODUCTE = "historialProducte";
    private Context context = null;

    public DAOCompras(Context context) {
        super(context, TABLE_COMPRAS);
        this.context = context;
    }

    public ArrayList<Compra> selectAll() {
        ArrayList<Compra> listT = new ArrayList<Compra>();
        Activity act = (Activity) context;
        MyApplication app = (MyApplication) act.getApplication();
        openReadOnly();
        String sql = "SELECT c.id AS id, c.data AS data, chp.preu AS preu, chp.quantitat AS quantitat, hp.nom as nom FROM " + TABLE_COMPRAS + " AS c, " + TABLE_COMPRA_HISTORIAL_PRODUCTE + " AS chp, " + TABLE_HISTORIAL_PRODUCTE + " AS hp WHERE c.id = chp.id_compra AND chp.id_historialProducte = hp.id AND c.id_usuari = " + app.getUserLog().getId() + " order by c.data desc";
        Cursor cursor = myDB.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Compra element = LoadFromCursor(cursor);
            listT.add(element);
            cursor.moveToNext();
        }
        cursor.close();
        this.closeDatabase();
        return listT;
    }

    public ArrayList<Compra> selectAllFromUser(int id_user) {
        ArrayList<Compra> listT = new ArrayList<Compra>();

        openReadOnly();
        String sql = "SELECT c.id AS id, c.data AS data, chp.preu AS preu, chp.quantitat AS quantitat, hp.nom as nom FROM " + TABLE_COMPRAS + " AS c, " + TABLE_COMPRA_HISTORIAL_PRODUCTE + " AS chp, " + TABLE_HISTORIAL_PRODUCTE + " AS hp WHERE c.id = chp.id_compra AND chp.id_historialProducte = hp.id AND c.id_usuari = " + id_user + " order by c.data desc";
        Cursor cursor = myDB.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Compra element = LoadFromCursor(cursor);
            listT.add(element);
            cursor.moveToNext();
        }
        cursor.close();
        this.closeDatabase();
        return listT;
    }


    @Override
    public void delete(Compra obj) {
        //No s'utilitza perquè MAI BORRARÀS UNA COMPRA (Christian)
    }

    @Override
    public long insert(Compra obj) {
        long compra_id = -1;
        long historialProducte_id = -1;
        try{
            Activity activity = (Activity) context;
            MyApplication app = (MyApplication) activity.getApplication();
            openWrite();
            String sql="INSERT INTO " + TABLE_COMPRAS + " (id_usuari,data) VALUES (?,strftime('%s','now'))";
            SQLiteStatement statement = this.myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Integer.toString(app.getUserLog().getId())});
            compra_id=statement.executeInsert();
            statement.close();
            for(Producte p : obj.getlProductes()){
                sql = "INSERT INTO " + TABLE_HISTORIAL_PRODUCTE + " (nom,preu) VALUES (?,?)";
                statement = this.myDB.compileStatement(sql);
                statement.bindAllArgsAsStrings(new String[]{p.getNombre(),Float.toString(p.getPrecio())});
                historialProducte_id = statement.executeInsert();
                sql = "INSERT INTO " + TABLE_COMPRA_HISTORIAL_PRODUCTE + " (id_compra,id_historialProducte,quantitat,preu) VALUES (?,?,?,?)";
                statement = this.myDB.compileStatement(sql);
                Log.d("INSERT COMPRA:", String.format("%.2f", p.getPrecio() * p.getQuantitat()).replace(",", "."));
                statement.bindAllArgsAsStrings(new String[]{Long.toString(compra_id),Long.toString(historialProducte_id),Integer.toString(p.getQuantitat()),String.format("%.2f", p.getPrecio() * p.getQuantitat()).replace(",", ".")});
                statement.executeInsert();
            }
        }catch(Exception ex){
            Log.e("ERROR", ex.toString());
        }finally{
            super.closeDatabase();
        }
        return compra_id;
    }

    @Override
    public void update(Compra obj) {
        //No s'utilitza perquè MAI BORRARÀS UNA COMPRA (Christian)
    }

    @Override
    protected Compra LoadFromCursor(Cursor cursor) {
        Compra result = null;
        if(cursor != null && !cursor.isAfterLast()){
            result = new Compra();
            result.setId(cursor.getInt(cursor.getColumnIndex("id")));
            result.setDate(cursor.getInt(cursor.getColumnIndex("data")));
            Producte auxProducte = new Producte();
            auxProducte.setNombre(cursor.getString(cursor.getColumnIndex("nom")));
            result.setPrice(cursor.getFloat(cursor.getColumnIndex("preu")));
            auxProducte.setQuantitat(cursor.getInt(cursor.getColumnIndex("quantitat")));
            result.setProducteShow(auxProducte);
        }
        return result;
    }


}