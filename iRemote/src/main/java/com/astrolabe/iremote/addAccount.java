package com.astrolabe.iremote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Abu-Umar on 12/22/13.
 * fragment
 */
public class addAccount extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_account, container, false);


        assert view != null;
        final main mActivity = (main) getActivity();
        mActivity.setCurrFrag(Constants.Pages.ADDACCOUNT);
        // TextView tv = (TextView) view.findViewById(R.id.tvInfo);

        // reload menu so that it updates Quick Action Menu
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        menuFrag firstFragment1 = new menuFrag();
        ft1.replace(R.id.menuFragment, firstFragment1, mActivity.getString(R.string.menuFragment));
        ft1.commit();

        ImageButton addAcc = (ImageButton) view.findViewById(R.id.addAccountButton);
        addAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setAccount(25);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ReplaceFragments rp = new ReplaceFragments();
                rp.replaceWithAddEditAccount(ft, fm, true);
            }
        });


        return view;
    }
}
