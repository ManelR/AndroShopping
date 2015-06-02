package org.projectes.androidshopping.adapters;

/**
 * Created by alloveras on 2/06/15.
*/

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOCompras;
import org.projectes.androidshopping.DAO.DAOUsuaris;
import org.projectes.androidshopping.DAObject.Compra;
import org.projectes.androidshopping.DAObject.Usuari;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_Modify;
import org.projectes.androidshopping.activities.ShowUserActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PurchaseHistoryAdapter extends BaseAdapter {

    private ArrayList<Compra> aCompres = null;
    private Context context = null;

    public PurchaseHistoryAdapter(Context context, ArrayList<Compra> lCompres){
        this.aCompres = lCompres;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(aCompres != null){
            return aCompres.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(aCompres != null){
            return aCompres.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateInformation(){
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View item = convertView;
        if (item == null) {

            //Inflem la vista de l'objecte amb el layout definit per cadascún dels items a pintar
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.item_purchase_history_layout, parent, false);

        }

        TextView txtData = (TextView) item.findViewById(R.id.item_purchase_history_txtDate);
        TextView txtPreu = (TextView) item.findViewById(R.id.item_purchase_history_txtPrice);
        TextView txtQuantiat = (TextView) item.findViewById(R.id.item_purchase_history_txtQuantity);
        TextView txtProducte = (TextView) item.findViewById(R.id.item_purchase_history_txtProduct);

        if(txtData != null){
            long dv = (long)aCompres.get(position).getDate();
            Date date = new Date(dv * 1000);
            txtData.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(date));
        }

        if(txtPreu != null){
            txtPreu.setText(Float.toString(aCompres.get(position).getPrice()));
        }

        if(txtProducte != null){
            txtProducte.setText(aCompres.get(position).getProducteShow().getNombre());
        }

        if(txtQuantiat != null){
            txtQuantiat.setText(Integer.toString(aCompres.get(position).getProducteShow().getQuantitat()));
        }

        //Canviem el color en funció de si és parell o no
        if(position % 2 == 0){
            item.setBackgroundColor(0xFFF5F5F5);
        }else{
            item.setBackgroundColor(0xFFFFFFFF);
        }

        return item;
    }

}
