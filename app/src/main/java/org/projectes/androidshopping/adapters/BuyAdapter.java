package org.projectes.androidshopping.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mrr on 02/06/15.
 */
public class BuyAdapter extends BaseAdapter {


    private ArrayList<Producte> aCompres = null;
    private Context context = null;

    public BuyAdapter(Context context, ArrayList<Producte> aCompres){
        this.aCompres = aCompres;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        if (item == null) {

            //Inflem la vista de l'objecte amb el layout definit per cadascún dels items a pintar
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.item_buy_layout, parent, false);

        }

        //Recuperem els controls de la vista particular de cada element de l'adapter
        TextView txtData = (TextView)item.findViewById(R.id.item_buy_lblDate);
        TextView txtProducte = (TextView)item.findViewById(R.id.item_buy_lblProduct);
        TextView txtPrice = (TextView)item.findViewById(R.id.item_buy_txtPrice);
        CheckBox chckItem = (CheckBox)item.findViewById(R.id.item_buy_checkBox);
        Spinner spinQuantitat = (Spinner)item.findViewById(R.id.item_buy_spinner);

        //Assignar textos
        txtProducte.setText(aCompres.get(position).getNombre());
        txtPrice.setText(Float.toString(aCompres.get(position).getPrecio()));
        chckItem.setChecked(aCompres.get(position).isChecked());
        spinQuantitat.setSelection(aCompres.get(position).getQuantitat() - 1);

        long dv = aCompres.get(position).getDate() * 1000;
        Date df = new Date(dv);
        txtData.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(df));

        item.setTag(position);

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                if (aCompres.get(position).isChecked()){
                    aCompres.get(position).setChecked(false);
                }else{
                    aCompres.get(position).setChecked(true);
                }
                Spinner spin = (Spinner)v.findViewById(R.id.item_buy_spinner);
                aCompres.get(position).setQuantitat(Integer.valueOf((String)spin.getSelectedItem()));
            }
        });

        //Assignem els clickable objects
        item.setClickable(true);
        item.setFocusable(true);

        //Canviem el color en funció de si és parell o no
        if(position % 2 == 0){
            item.setBackgroundColor(0xFFF5F5F5);
        }else{
            item.setBackgroundColor(0xFFFFFFFF);
        }

        return item;
    }

}
