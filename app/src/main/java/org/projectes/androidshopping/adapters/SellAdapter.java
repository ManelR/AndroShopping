package org.projectes.androidshopping.adapters;

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
import org.projectes.androidshopping.DAO.DAOUsuaris;
import org.projectes.androidshopping.DAObject.Usuari;
import org.projectes.androidshopping.DAObject.Venta;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_Modify;
import org.projectes.androidshopping.activities.ShowUserActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mrr on 01/06/15.
 */
public class SellAdapter extends BaseAdapter {

    private ArrayList<Venta> aVenta = null;
    private Context context = null;

    public SellAdapter(Context context, ArrayList<Venta> aVenta){
        this.aVenta = aVenta;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(aVenta != null){
            return aVenta.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(aVenta != null){
            return aVenta.get(position);
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
            item = inflater.inflate(R.layout.item_sell_list_layout, parent, false);

        }

        TextView txtData = (TextView) item.findViewById(R.id.item_sell_list_lblDate);
        TextView txtUser = (TextView) item.findViewById(R.id.item_sell_list_txtCustomer);
        TextView txtPreu = (TextView) item.findViewById(R.id.item_sell_list_txtPrice);
        TextView txtQuantitat = (TextView) item.findViewById(R.id.item_sell_list_txtQuantity);
        TextView txtProducte = (TextView) item.findViewById(R.id.item_sell_list_lblProduct);

        long dv = aVenta.get(position).getData() * 1000;
        Date df = new Date(dv);
        txtData.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(df));
        txtPreu.setText(Float.toString(aVenta.get(position).getPreu()) + " €");
        txtProducte.setText(aVenta.get(position).getNomProducte());
        txtQuantitat.setText(Integer.toString(aVenta.get(position).getQuantitat()));
        txtUser.setText(aVenta.get(position).getClient());



        //Canviem el color en funció de si és parell o no
        if(position % 2 == 0){
            item.setBackgroundColor(0xFFF5F5F5);
        }else{
            item.setBackgroundColor(0xFFFFFFFF);
        }

        return item;
    }

}
