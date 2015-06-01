package org.projectes.androidshopping.DAO;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.projectes.androidshopping.DAObject.Venta;

import java.util.ArrayList;

/**
 * Created by mrr on 01/06/15.
 */
public class DAOVenta extends DAOBase<Venta> {
    private static final String TAULA_COMPRA = "compra";
    private static final String TAULA_COMPRA_HISTORIC = "compra_historialProducte";
    private static final String TAULA_HISTORIC = "historialProducte";
    private static final String TAULA_USUARIS = "usuaris";

    public DAOVenta(Context context) {
        super(context, TAULA_COMPRA);
    }

    @Override
    public ArrayList<Venta> selectAll(){
        ArrayList<Venta> listT = new ArrayList<Venta>();
        openReadOnly();
        String sql = "SELECT c.id as id, u.email as usuari, c.data as data, h.nom as nomProducte, ch.preu as preu, ch.quantitat as quantitat FROM " + TAULA_USUARIS + " as u, " + TAULA_COMPRA + " as c, " + TAULA_COMPRA_HISTORIC + " as ch, " + TAULA_HISTORIC+ " as h WHERE c.id_usuari = u.id AND ch.id_compra = c.id AND ch.id_historialProducte = h.id ORDER BY c.data";
        Cursor cursor = myDB.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Log.d("HA ENTRAT", "ha entrat en el bucle del select de la BBDD ventas");
            Venta element = LoadFromCursor(cursor);
            listT.add(element);
            cursor.moveToNext();
        }
        cursor.close();
        this.closeDatabase();
        return listT;
    }

    @Override
    public void delete(Venta obj) {
        //No s'utilitza
    }

    @Override
    public long insert(Venta obj) {
        //No s'utilitza
        return 0;
    }

    @Override
    public void update(Venta obj) {
        //No s'utilitza
    }

    @Override
    protected Venta LoadFromCursor(Cursor cursor) {
        Venta result = null;
        if (cursor != null){
            result = new Venta();
            result.setId(cursor.getInt(cursor.getColumnIndex("id")));
            result.setClient(cursor.getString(cursor.getColumnIndex("usuari")));
            result.setData(cursor.getInt(cursor.getColumnIndex("data")));
            result.setNomProducte(cursor.getString(cursor.getColumnIndex("nomProducte")));
            result.setPreu(cursor.getFloat(cursor.getColumnIndex("preu")));
            result.setQuantitat(cursor.getInt(cursor.getColumnIndex("quantitat")));
        }
        return result;
    }
}
