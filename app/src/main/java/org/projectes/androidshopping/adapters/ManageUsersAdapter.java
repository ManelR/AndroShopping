package org.projectes.androidshopping.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOProductes;
import org.projectes.androidshopping.DAO.DAOUsuaris;
import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.DAObject.Usuari;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_Modify;

import java.util.ArrayList;

public class ManageUsersAdapter extends BaseAdapter {

    private ArrayList<Usuari> aUsuaris = null;
    private DAOUsuaris daoUsuaris = null;
    private DBTask_Base_Modify<DAOUsuaris,Usuari> taskModify = null;
    private Context context = null;

    public ManageUsersAdapter(Context context, ArrayList<Usuari> aUsuaris){
        this.aUsuaris = aUsuaris;
        this.context = context;
        this.daoUsuaris = new DAOUsuaris(context);
    }

    @Override
    public int getCount() {
        if(aUsuaris != null){
            return aUsuaris.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(aUsuaris != null){
            return aUsuaris.get(position);
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
            item = inflater.inflate(R.layout.item_manage_users_layout, parent, false);

        }

        TextView txtEmail = (TextView) item.findViewById(R.id.item_manage_users_txtName);
        TextView txtAge = (TextView) item.findViewById(R.id.item_manage_users_txtAge);
        TextView txtGenere = (TextView) item.findViewById(R.id.item_manage_users_txtGenere);
        ImageView btnDelete = (ImageView) item.findViewById(R.id.item_manage_users_btnDelete);

        if(txtEmail != null){
            txtEmail.setText(aUsuaris.get(position).getEmail());
        }

        if(txtAge != null){
            txtAge.setText(aUsuaris.get(position).getEdat() + " años");
        }

        if(txtGenere != null){
            txtGenere.setText(aUsuaris.get(position).getGenere() == 1 ? "Hombre" : "Mujer");
        }

        if(btnDelete != null){
            btnDelete.setTag(aUsuaris.get(position));
            btnDelete.setClickable(true);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Usuari auxUsuari = null;
                    if(view.getTag() instanceof  Usuari){
                        auxUsuari = (Usuari) view.getTag();
                        AlertDialog.Builder confirmation = new AlertDialog.Builder(context,R.style.Theme_AppCompat_Light_Dialog_Alert);
                        confirmation.setTitle(context.getString(R.string.item_manage_products_lblTitleConfirmation));
                        confirmation.setIcon(R.mipmap.delete);
                        confirmation.setMessage(context.getString(R.string.item_manage_products_lblBodyConfirmation) + " " + auxUsuari.getEmail() + " ?");
                        final Usuari finalAuxUsuari = auxUsuari;
                        confirmation.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                taskModify = new DBTask_Base_Modify<DAOUsuaris, Usuari>();
                                taskModify.execute(context, Constants.BBDD_DELETE, daoUsuaris, finalAuxUsuari);
                                aUsuaris.remove(finalAuxUsuari);
                                updateInformation();
                            }
                        });
                        confirmation.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //No fem res
                            }
                        });

                        confirmation.show();
                    }


                }
            });
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