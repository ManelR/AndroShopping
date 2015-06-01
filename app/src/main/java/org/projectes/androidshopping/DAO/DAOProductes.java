package org.projectes.androidshopping.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.DAObject.Tag;

/**
 * Created by mrr on 30/05/15.
 */
public class DAOProductes extends DAOBase<Producte> {

    private static final String TABLE_NAME_PRODUCTE = "producte";
    private Context context = null;

    public DAOProductes(Context context) {
        super(context, TABLE_NAME_PRODUCTE);
        this.context = context;
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
        DAOTags daoTag = new DAOTags(context);
        int nError = 1;
        try{
            openWrite();
            String sql = "UPDATE " + TABLE_NAME_PRODUCTE + " SET deleted = 1 WHERE id = ?";
            SQLiteStatement statement = myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Integer.toString(obj.getId())});
            nError = statement.executeUpdateDelete();
            Log.i("--DELETE--", "Ha entrat al delete: " + obj.getNombre());
            statement.close();
            //Eliminar les relacions amb el producte
            daoTag.deleteProducte_TagByIDProduct(obj.getId());
        }catch (Exception ex){
            Log.d("ERROR", ex.toString());
        }finally {
            this.closeDatabase();
        }
    }

    @Override
    public long insert(Producte producte) {
        long inserted_id = -1;
        DAOTags BBDD_Tag = new DAOTags(this.context);
        try{
            openWrite();
            String sql="INSERT INTO "+TABLE_NAME_PRODUCTE+" (id_remot, nom, descripcio, preu, actiu, stock, imatge, deleted, data) VALUES(?,?,?,?,?,?,?,0, strftime('%s', 'now'))";
            SQLiteStatement statement = this.myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Integer.toString(producte.getId_remot()), producte.getNombre(), producte.getDescripcion(), Float.toString(producte.getPrecio()), Integer.toString(producte.isActivo() ? 1 : 0), Integer.toString(producte.getStock()), producte.getImage()});
            inserted_id=statement.executeInsert();
            Log.i("---ID---", new Long(inserted_id).toString());
            Log.i("---SQL---", sql);
            statement.close();
            for (int i = 0; i < producte.getWS_tags().size(); i++){
                Tag tagActual = BBDD_Tag.selectByName(producte.getWS_tags().get(i));
                if (tagActual == null){
                    tagActual = new Tag();
                    tagActual.setNom(producte.getWS_tags().get(i));
                    tagActual.setId((int)BBDD_Tag.insert(tagActual));
                    Log.d("TAG INSERT:", tagActual.getNom());
                }
                BBDD_Tag.insertProducte_Tag(tagActual.getId(), (int)inserted_id);
            }
        }catch(Exception ex){
            Log.e("ERROR", ex.toString());
        }finally{
            super.closeDatabase();
        }
        return inserted_id;
    }

    @Override
    public void update(Producte p) {
        DAOTags BBDD_Tag = new DAOTags(this.context);
        int nError = 0;
        try{
            openWrite();
            String sql = "UPDATE " + TABLE_NAME_PRODUCTE + " SET id_remot = ?, nom = ?, descripcio = ?, preu = ?, actiu = ?, stock = ?, imatge = ?, deleted = ?, data = strftime('%s', 'now') WHERE id = ?";
            SQLiteStatement statement = myDB.compileStatement(sql);
            statement.bindAllArgsAsStrings(new String[]{Integer.toString(p.getId_remot()), p.getNombre(), p.getDescripcion(), Float.toString(p.getPrecio()), Integer.toString(p.isActivo() ? 1 : 0), Integer.toString(p.getStock()), p.getImage(), Integer.toString(p.getDeleted()), Integer.toString(p.getId())});
            nError = statement.executeUpdateDelete();
            Log.i("--UPDATE--", statement.toString());
            statement.close();
            BBDD_Tag.deleteProducte_TagByIDProduct(p.getId());
            for (int i = 0; i < p.getWS_tags().size(); i++){
                Tag tagActual = BBDD_Tag.selectByName(p.getWS_tags().get(i));
                if (tagActual == null){
                    tagActual = new Tag();
                    tagActual.setNom(p.getWS_tags().get(i));
                    tagActual.setId((int)BBDD_Tag.insert(tagActual));
                    Log.d("TAG INSERT:", tagActual.getNom());
                }
                BBDD_Tag.insertProducte_Tag(tagActual.getId(), p.getId());
            }
        }catch (Exception ex){
            Log.e("ERROR", ex.toString());
        }finally {
            this.closeDatabase();
        }
    }

    @Override
    protected Producte LoadFromCursor(Cursor cursor) {
        Producte result = null;
        DAOTags daoTag = new DAOTags(context);
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
                result.setDate(cursor.getInt(cursor.getColumnIndex("data")));
                result.setDB_tags(daoTag.selectAllTagsFromProduct(result));
            }
        }
        return result;
    }


}
