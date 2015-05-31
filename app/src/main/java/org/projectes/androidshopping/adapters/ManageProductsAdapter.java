package org.projectes.androidshopping.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.activities.ShowProductActivity;

import java.util.ArrayList;

public class ManageProductsAdapter extends BaseAdapter {

    private ArrayList<Producte> aProductes = null;
    private Context context = null;

    public ManageProductsAdapter(Context context, ArrayList<Producte> aProductes){
        this.aProductes = aProductes;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(aProductes != null){
            return aProductes.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(aProductes != null){
            return aProductes.get(position);
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
            item = inflater.inflate(R.layout.item_manage_products_layout, parent, false);

        }

        //Recuperem els controls de la vista particular de cada element de l'adapter
        TextView txtNom = (TextView) item.findViewById(R.id.item_manage_products_txtName);
        TextView txtTags = (TextView) item.findViewById(R.id.item_manage_products_txtTags);
        TextView txtPrice = (TextView) item.findViewById(R.id.item_manage_products_txtPrice);
        ImageButton btnDelete = (ImageButton) item.findViewById(R.id.item_manage_products_btnDelete);
        ImageView imgProduct = (ImageView) item.findViewById(R.id.item_manage_products_imgProduct);

        imgProduct.setClickable(false);
        imgProduct.setFocusable(false);

        item.setTag(new Integer(aProductes.get(position).getId()));

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getTag() instanceof Integer){
                    int id = (Integer) v.getTag();
                    Intent i = new Intent(context, ShowProductActivity.class);
                    i.putExtra("id_product", id);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }

            }
        });

        if(txtNom != null){
            txtNom.setText(aProductes.get(position).getNombre());
        }

        if(txtPrice != null){
            txtPrice.setText(Float.toString(aProductes.get(position).getPrecio()) + " €");
        }

        //Recuperem la imatge
        Picasso.with(context).load(aProductes.get(position).getImage()).resize(128, 128).into(imgProduct);

        //Assignem els tags
        for(int i = 0 ; i < aProductes.get(position).getDB_tags().size() ; i++){
            if(i == 0){
                txtTags.setText(aProductes.get(position).getDB_tags().get(i).getNom());
            }else{
                txtTags.setText(txtTags.getText() + ", " +aProductes.get(position).getDB_tags().get(i).getNom());
            }
        }

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