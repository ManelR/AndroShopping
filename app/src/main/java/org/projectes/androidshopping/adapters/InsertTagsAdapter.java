package org.projectes.androidshopping.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.projectes.androidshopping.R;

import java.util.List;

/**
 * Created by alloveras on 31/05/15.
 */

public class InsertTagsAdapter extends BaseAdapter {

    private List<String> lTags;
    private Context context;

    public InsertTagsAdapter(List<String> lTags, Context context) {
        this.lTags = lTags;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lTags.size();
    }

    @Override
    public Object getItem(int position) {
        return lTags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateInformation(){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        if (item == null) {

            //Inflem la vista de l'objecte amb el layout definit per cadascún dels items a pintar
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.item_newtag_layout, parent, false);


            //Recuperem els botons de la vista
            ImageView imgUp = (ImageView) item.findViewById(R.id.item_newTag_btnUp);
            ImageView imgDown = (ImageView) item.findViewById(R.id.item_newTag_btnDown);
            ImageView imgDelete = (ImageView) item.findViewById(R.id.item_newTag_btnDelete);

            //Fem que els tres elements siguin clicables
            imgUp.setClickable(true);
            imgDown.setClickable(true);
            imgDelete.setClickable(true);


            //Afegim els respectius listeners per cada element
            imgUp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = (Integer) v.getTag();

                    //Si no és el primer element permetem pujar-lo
                    if (position > 0) {
                        //Fem el swap d'elements dins de la llista
                        String sHigh = lTags.get(position - 1);
                        lTags.set(position - 1, lTags.get(position));
                        lTags.set(position, sHigh);
                        //Notifiquem els canvis al adapter
                        notifyDataSetChanged();
                    }
                }
            });


            imgDown.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //Recuperem la posicio
                    int position = (Integer) v.getTag();

                    //Si no és l'ultim element permetem baixar-lo
                    if (position < lTags.size() - 1) {
                        //Fem el swap d'elements dins de la llista
                        String sTemarioLow = lTags.get(position + 1);
                        lTags.set(position + 1, lTags.get(position));
                        lTags.set(position, sTemarioLow);
                        //Notifiquem el canvi al adapter
                        notifyDataSetChanged();
                    }
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //Recuperem la posició
                    int position = (Integer) v.getTag();

                    //Eliminem el temari
                    lTags.remove(position);

                    //Notifiquem que ha canviat la informació al adapter
                    notifyDataSetChanged();

                }
            });

        }

        //Recuperem la informació de la LinkedList i l'inserim al adapter
        TextView txtNumber = (TextView) item.findViewById(R.id.item_newTag_lblTagNumber);
        TextView txtName = (TextView) item.findViewById(R.id.item_newTag_lblTagName);
        ImageView imgUp = (ImageView) item.findViewById(R.id.item_newTag_btnUp);
        ImageView imgDown = (ImageView) item.findViewById(R.id.item_newTag_btnDown);
        ImageView imgDelete = (ImageView) item.findViewById(R.id.item_newTag_btnDelete);

        txtNumber.setText(Integer.toString(position + 1));
        txtName.setText(lTags.get(position));

        //Afegim com a tag del objecte la posició a la linked list
        imgUp.setTag(position);
        imgDown.setTag(position);
        imgDelete.setTag(position);

        return item;
    }

}
