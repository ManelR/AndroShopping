package org.projectes.androidshopping.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.projectes.androidshopping.R;

/**
 * Created by alloveras on 2/06/15.
 */
public class BuyActivityNormalFragment extends Fragment {

    private View fragmentView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_buy_normal_layout, container, false);
        return fragmentView;
    }

}
