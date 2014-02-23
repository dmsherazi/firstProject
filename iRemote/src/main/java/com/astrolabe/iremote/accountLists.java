package com.astrolabe.iremote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Abu-Umar on 12/22/13.
 * fragment
 */
public class accountLists extends Fragment implements View.OnClickListener {

    Button list1, list2, list3, list4, list5, list6, list7, list8;
    long AccountNumbers = 0;//
    View contextId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.accounts_list, container, false);


        assert view != null;
        SupportClass sc = new SupportClass(getActivity());
        main mActivity = (main) getActivity();
        mActivity.setCurrFrag(Constants.Pages.ACCLISTS);

        // reload menu so that it updates Quick Action Menu
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        menuFrag firstFragment1 = new menuFrag();
        ft1.replace(R.id.menuFragment, firstFragment1, mActivity.getString(R.string.menuFragment));
        ft1.commit();


        AccountNumbers = sc.getAccountNumber();
        if (AccountNumbers < 1) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            ReplaceFragments rp = new ReplaceFragments();
            rp.replaceWithAddEditAccount(ft, fm, true);
            mActivity.showCroutonMessage(getString(R.string.no_record_in_accounts), Constants.crs.INFO_C, Constants.crs.COUTION_MODE_DEFAULT);
            // sc.showDialog();

        } else
            showCroutonMessage("long press to edit or delete", Constants.crs.INFO_C, Constants.crs.COUTION_MODE_DEFAULT);
        mActivity.cancelAllCroutons();
        list1 = (Button) view.findViewById(R.id.acc_1);
        list2 = (Button) view.findViewById(R.id.acc_2);
        list3 = (Button) view.findViewById(R.id.acc_3);
        list4 = (Button) view.findViewById(R.id.acc_4);
        list5 = (Button) view.findViewById(R.id.acc_5);
        list6 = (Button) view.findViewById(R.id.acc_6);
        list7 = (Button) view.findViewById(R.id.acc_7);
        list8 = (Button) view.findViewById(R.id.acc_8);


        registerForContextMenu(list1);
        registerForContextMenu(list2);
        registerForContextMenu(list3);
        registerForContextMenu(list4);
        registerForContextMenu(list5);
        registerForContextMenu(list6);
        registerForContextMenu(list7);
        registerForContextMenu(list8);
        mActivity.showCroutonMessage("long press to edit or delete", Constants.crs.INFO_C, Constants.crs.COUTION_MODE_DEFAULT);
        long mInt = AccountNumbers;
        makeAllInvisible();
        if (mInt > 0) {

            list1.setVisibility(View.VISIBLE);
            list1.setText(sc.getControlName(0));
        }
        if (mInt > 1) {
            list2.setVisibility(View.VISIBLE);
            list2.setText(sc.getControlName(1));
        }
        if (mInt > 2) {
            list3.setVisibility(View.VISIBLE);
            list3.setText(sc.getControlName(2));
        }
        if (mInt > 3) {
            list4.setText(sc.getControlName(3));
            list4.setVisibility(View.VISIBLE);
        }

        if (mInt > 4) {
            list5.setVisibility(View.VISIBLE);
            list5.setText(sc.getControlName(4));
        }
        if (mInt > 5) {
            list6.setVisibility(View.VISIBLE);
            list6.setText(sc.getControlName(5));
        }
        if (mInt > 6) {
            list7.setText(sc.getControlName(6));
            list7.setVisibility(View.VISIBLE);
        }

        if (mInt > 7) {
            list8.setVisibility(View.VISIBLE);
            list8.setText(sc.getControlName(7));
        }
        list1.setOnClickListener(this);
        list2.setOnClickListener(this);
        list3.setOnClickListener(this);
        list4.setOnClickListener(this);
        list5.setOnClickListener(this);
        list6.setOnClickListener(this);
        list7.setOnClickListener(this);
        list8.setOnClickListener(this);


        return view;
    }

    private static final Style ALERT_G_LEFT = new Style.Builder()
            .setGravity(Gravity.LEFT)
            .setBackgroundColorValue(Style.holoRedLight).build();
    private static final Style ALERT = new Style.Builder()
            .setBackgroundColorValue(Style.holoRedLight).build();

    private static final Style INFO_G_LEFT = new Style.Builder()
            .setGravity(Gravity.LEFT)
            .setBackgroundColorValue(Style.holoBlueLight).build();
    private static final Style INFO = new Style.Builder().
            setBackgroundColorValue(Style.holoBlueLight).build();

    private static final Style SUCC_G_LEFT = new Style.Builder()
            .setGravity(Gravity.LEFT)
            .setBackgroundColorValue(Style.holoGreenLight).build();
    private static final Style SUCC = new Style.Builder().
            setBackgroundColorValue(Style.holoGreenLight).build();

    private static final Configuration CONFIGURATION_INFINITE = new Configuration.Builder()
            .setDuration(Configuration.DURATION_INFINITE)
            .build();
    Crouton crouton;
    private Crouton infiniteCrouton;

    public void showCroutonMessage(String Text, int style, boolean mode) {


        Crouton.clearCroutonsForActivity(getActivity());
        Log.e("Crouton", " called");
        // Todo: Here i am forcing display on Top
        // Follow it

        Style croutonStyle = null;
        if (style == Constants.crs.ALERT_GL)// 0 == alert
            croutonStyle = ALERT_G_LEFT;//, R.id.alternate_view_group);

        else if (style == Constants.crs.ALERT_C)// 0 == alert
            croutonStyle = ALERT;//, R.id.alternate_view_group);

        if (style == Constants.crs.INFO_GL)// 0 == alert
            croutonStyle = INFO_G_LEFT;//, R.id.alternate_view_group);

        else if (style == Constants.crs.INFO_C)// 0 == alert
            croutonStyle = INFO;//, R.id.alternate_view_group);

        else if (style == Constants.crs.SUCC_GL)// 0 == alert
            croutonStyle = SUCC_G_LEFT;//, R.id.alternate_view_group);

        else if (style == Constants.crs.SUCC_C)
            croutonStyle = SUCC;//, R.id.alternate_view_group);


        crouton = Crouton.makeText(getActivity(), Text, croutonStyle);

        if (mode) infiniteCrouton = crouton;

        crouton.setOnClickListener(this).setConfiguration(mode ? CONFIGURATION_INFINITE : Configuration.DEFAULT).show();


    }

    public void makeAllInvisible() {
        list1.setVisibility(View.INVISIBLE);
        list2.setVisibility(View.INVISIBLE);
        list3.setVisibility(View.INVISIBLE);
        list4.setVisibility(View.INVISIBLE);
        list5.setVisibility(View.INVISIBLE);
        list6.setVisibility(View.INVISIBLE);
        list7.setVisibility(View.INVISIBLE);
        list8.setVisibility(View.INVISIBLE);
    }

    final int MENU_EDIT = 0;
    final int MENU_DELETE = 1;


    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, MENU_EDIT, 0, "Edit");
        menu.add(0, MENU_DELETE, 0, "Delete");
        contextId = v;

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case MENU_EDIT:
                function1(contextId);
                break;
            case MENU_DELETE:
                function2(contextId);
                break;
        }
        return true;
    }


    public void function1(View v) {
        long account = 8;
        if (v == list1) account = 0;
        else if (v == list2) account = 1;
        else if (v == list3) account = 2;
        else if (v == list4) account = 3;
        else if (v == list5) account = 4;
        else if (v == list6) account = 5;
        else if (v == list7) account = 6;
        else if (v == list8) account = 7;

        //main mActivity = (main) getActivity();
        main.setAccount(account);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ReplaceFragments rp = new ReplaceFragments();
        rp.replaceWithAddEditAccount(ft, fm, true);

    }

    public void function2(View v) {
        accountsDB db = new accountsDB(getActivity());
        db.Open();
        if (v == list1) db.deleteEntry(0);
        else if (v == list2) db.deleteEntry(1);
        else if (v == list3) db.deleteEntry(2);
        else if (v == list4) db.deleteEntry(3);
        else if (v == list5) db.deleteEntry(4);
        else if (v == list6) db.deleteEntry(5);
        else if (v == list7) db.deleteEntry(6);
        else if (v == list8) db.deleteEntry(7);
        //else if(v==ac1)      db.deleteEntry(8);

        db.close();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ReplaceFragments rp = new ReplaceFragments();
        rp.replaceWithAL(ft, fm, true, false);

    }


    @Override
    public void onClick(View v) {
        //long account = 1;
        if (v == list1) intentFunction(0);
        else if (v == list2) intentFunction(1);
        else if (v == list3) intentFunction(2);
        else if (v == list4) intentFunction(3);
        else if (v == list5) intentFunction(4);
        else if (v == list6) intentFunction(5);
        else if (v == list7) intentFunction(6);
        else if (v == list8) intentFunction(7);
    }

    private void intentFunction(long account) {

        //main mActivity = (main) getActivity();
        main.setAccount(account);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ReplaceFragments rp = new ReplaceFragments();
        rp.replaceWithRemote(ft, fm, true);
    }

}
