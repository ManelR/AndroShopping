package org.projectes.androidshopping.adapters;

import android.app.ActionBar;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.DAObject.Tag;
import org.projectes.androidshopping.R;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mrr on 02/06/15.
 */
public class BuyAdapter extends BaseAdapter {


    private static ArrayList<Producte> aCompres;
    private Context context = null;
    private String searchPAttern = null;

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
        Spinner spinQuantitat = null;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dp = 100f;
        float fpixel = metrics.density * dp;
        int pixels = (int)(fpixel + 0.5f);

        if (item == null) {
            //Inflem la vista de l'objecte amb el layout definit per cadascún dels items a pintar
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.item_buy_layout, parent, false);
            spinQuantitat = (Spinner)item.findViewById(R.id.item_buy_spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapterSpin = ArrayAdapter.createFromResource(context,
                    R.array.spinner_quantity, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinQuantitat.setAdapter(adapterSpin);

        }else{
            spinQuantitat = (Spinner)item.findViewById(R.id.item_buy_spinner);
        }

        //Recuperem els controls de la vista particular de cada element de l'adapter
        TextView txtData = (TextView)item.findViewById(R.id.item_buy_lblDate);
        TextView txtProducte = (TextView)item.findViewById(R.id.item_buy_lblProduct);
        TextView txtPrice = (TextView)item.findViewById(R.id.item_buy_txtPrice);
        CheckBox chckItem = (CheckBox)item.findViewById(R.id.item_buy_checkBox);




        //Assignar textos
        txtProducte.setText(aCompres.get(position).getNombre());
        txtPrice.setText(Float.toString(aCompres.get(position).getPrecio()));
        chckItem.setChecked(aCompres.get(position).isChecked());
        spinQuantitat.setSelection(aCompres.get(position).getQuantitat() - 1);

        long dv = (long)aCompres.get(position).getDate();
        Date date = new Date(dv * 1000);
        txtData.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(date));

        spinQuantitat.setTag(position);

        spinQuantitat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent != null){
                    if(parent.getTag() != null) {
                        Log.d("HOla: ", "Ha entrat al spinner de compra");
                        int p = (int) parent.getTag();
                        aCompres.get(p).setQuantitat(position + 1);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                updateInformation();
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

        if (searchPAttern != null){
            if (!hasToBeShown(position)){
                item.setVisibility(View.GONE);
                item.getLayoutParams().height = 1;
            }else{
                item.setVisibility(View.VISIBLE);
                item.getLayoutParams().height = pixels;
            }
        }else{
            item.setVisibility(View.VISIBLE);
            item.getLayoutParams().height = pixels;
        }

        return item;
    }

    private boolean hasToBeShown(int position) {
        boolean result = false;
        //Comprovar el nom
        if (aCompres.get(position).getNombre().toLowerCase().contains(this.searchPAttern)){
            return true;
        }
        for (Tag t : aCompres.get(position).getDB_tags()){
            if (t.getNom().toLowerCase().contains(this.searchPAttern)){
                return true;
            }
        }
        return result;
    }

    public void setSearchPattern(String p){
        if (p != null){
            this.searchPAttern = p.toLowerCase();
        }else{
            this.searchPAttern = null;
        }
        notifyDataSetChanged();
    }

    public void setList(ArrayList<Producte> compraUsuari) {
        this.aCompres = compraUsuari;
    }
}
