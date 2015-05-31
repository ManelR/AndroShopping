package org.projectes.androidshopping.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOUsuaris;
import org.projectes.androidshopping.DAObject.Usuari;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.MyApplication;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.Task.DBTask_Base_Modify;
import org.projectes.androidshopping.activities.BaseActivity;
import org.projectes.androidshopping.activities.BuyActivity;
import org.projectes.androidshopping.activities.MainMenuActivity;
import org.projectes.androidshopping.activities.ManageUsersActivity;
import org.projectes.androidshopping.activities.ProductsManagerActivity;
import org.projectes.androidshopping.activities.PurchaseHistoryActivity;
import org.projectes.androidshopping.activities.SellListActivity;
import org.projectes.androidshopping.activities.UserProfileActivity;
import org.projectes.androidshopping.objects.MainMenuObject;

import java.util.LinkedList;

public class MainMenuAdapter extends BaseAdapter {

    private MainMenuObject objExit = null;

    private LinkedList<MainMenuObject> lObjectes = null;
    private Context context = null;

    public MainMenuAdapter(Context context,boolean isAdmin){
        this.lObjectes = new LinkedList<MainMenuObject>();
        this.context = context;
        this.inflateElements(isAdmin);
    }

    @Override
    public int getCount() {
        if(lObjectes != null){
            return lObjectes.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(lObjectes != null){
            return lObjectes.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View item = convertView;
        if (item == null) {

            //Inflem la vista de l'objecte amb el layout definit per cadascún dels items a pintar
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.item_main_menu_layout, parent, false);
            item.setClickable(true);

            item.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DAOUsuaris BBDD_User = new DAOUsuaris(context);
                    DBTask_Base_Modify<DAOUsuaris, Usuari> Task_User = new DBTask_Base_Modify<DAOUsuaris, Usuari>();
                    Usuari user;
                    //Si ens han fet click fem un intent de l'activitat en questió
                    if (v.getTag() != null && v.getTag() instanceof MainMenuObject) {
                        MainMenuObject obj = (MainMenuObject) v.getTag();
                        if (obj.getIsExit()) {
                            user = ((MyApplication)((BaseActivity)context).getApplication()).getUserLog();
                            user.setLogged_in(0);
                            Task_User.setResultListener(new IResult<Boolean>() {
                                @Override
                                public void onSuccess(Boolean IRresult) {
                                    ((Activity) context).finish();
                                }

                                @Override
                                public void onFail(String missatgeError) {
                                    Toast.makeText(context, "Error al sortir", Toast.LENGTH_LONG).show();
                                }
                            });
                            Task_User.execute(context, Constants.BBDD_EDIT, BBDD_User, user);
                        } else {
                            Intent intent = new Intent(context, obj.getActivityClass());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                }
            });

        }

        //Fiquem el tag al objecte per si al listener el necessitem
        item.setTag(lObjectes.get(position));

        //Recuperem els controls de la vista i assignem el text i la imatge que li correspon
        TextView txtDescripcio = (TextView) item.findViewById(R.id.txt_item_menu_principal);
        ImageView image = (ImageView) item.findViewById(R.id.img_item_menu_principal);

        txtDescripcio.setText(lObjectes.get(position).getText());
        image.setImageResource(lObjectes.get(position).getImatge());

        return item;
    }

    private void inflateElements(boolean isAdmin){
        if(isAdmin){
            MainMenuObject objProductsManager = new MainMenuObject(context.getString(R.string.activity_mainMenu_lblManageProducts),R.mipmap.products, ProductsManagerActivity.class);
            MainMenuObject objUsersManager = new MainMenuObject(context.getString(R.string.activity_mainMenu_lblManageUsers),R.mipmap.users, ManageUsersActivity.class);
            MainMenuObject objSellList = new MainMenuObject(context.getString(R.string.activity_mainMenu_lblSellList),R.mipmap.list, SellListActivity.class);
            MainMenuObject objExit = new MainMenuObject(context.getString(R.string.activity_mainMenu_lblExit),R.mipmap.exit,null);
            objExit.setIsExit(true);
            lObjectes.add(objProductsManager);
            lObjectes.add(objUsersManager);
            lObjectes.add(objSellList);
            lObjectes.add(objExit);
        }else{
            MainMenuObject objPerfil = new MainMenuObject(context.getString(R.string.activity_mainMenu_lblPerfil),R.mipmap.user, UserProfileActivity.class);
            MainMenuObject objComprar = new MainMenuObject(context.getString(R.string.activity_mainMenu_lblComprar),R.mipmap.buy, BuyActivity.class);
            MainMenuObject objPurchaseHistory = new MainMenuObject(context.getString(R.string.activity_mainMenu_lblPurchaseHistory),R.mipmap.list, PurchaseHistoryActivity.class);
            MainMenuObject objExit = new MainMenuObject(context.getString(R.string.activity_mainMenu_lblExit),R.mipmap.exit,null);
            objExit.setIsExit(true);
            lObjectes.add(objPerfil);
            lObjectes.add(objComprar);
            lObjectes.add(objPurchaseHistory);
            lObjectes.add(objExit);
        }
    }
}