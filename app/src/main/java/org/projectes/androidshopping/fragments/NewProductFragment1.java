package org.projectes.androidshopping.fragments;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.projectes.androidshopping.R;

public class NewProductFragment1 extends Fragment {
    private View fragmentView;
    private EditText txtName;
    private EditText txtDesc;


    public NewProductFragment1(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_new_product_fragment1, container, false);
        this.txtName = (EditText)fragmentView.findViewById(R.id.fragment_newProduct1_txtName);
        this.txtDesc = (EditText)fragmentView.findViewById(R.id.fragment_newProduct1_txtDesc);
        return this.fragmentView;
    }

    public String getTxtName(){
        return this.txtName.getText().toString();
    }

    public String getTxtDesc(){
        return this.txtDesc.getText().toString();
    }
}
