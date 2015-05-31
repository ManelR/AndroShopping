package org.projectes.androidshopping.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.projectes.androidshopping.R;

public class NewProductFragment3 extends Fragment {
    private View fragmentView;

    public NewProductFragment3(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.fragment_new_product_fragment3, container, false);
        return this.fragmentView;
    }

}
