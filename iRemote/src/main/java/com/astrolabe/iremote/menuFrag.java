package com.astrolabe.iremote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.londatiga.android.ActionItem;
import com.londatiga.android.QuickAction;

import java.util.List;
import java.util.Map;

/**
 * Created by Abu-Umar on 12/22/13.
 * fragment
 */
public class menuFrag extends Fragment implements View.OnClickListener {

    QuickAction quickActionPopup1;
    ImageButton ibSettings, users, timers, remote, areas;
    static TextView title;
    List<Map<String, String>> moreList;
    private PopupWindow pwMyPopWindow;// popupwindow
    private ListView lvPopupList;// popupwindowä¸­çš„ListView
    private int NUM_OF_VISIBLE_LIST_ROWS = 4;//

    main mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.menu, container, false);

        Log.e("menu fragment", "created");
        assert view != null;
        users = (ImageButton) view.findViewById(R.id.ibUsers);
        areas = (ImageButton) view.findViewById(R.id.ibArea);
        timers = (ImageButton) view.findViewById(R.id.ibTimer);
        remote = (ImageButton) view.findViewById(R.id.ibRemote);
        ibSettings = (ImageButton) view.findViewById(R.id.ibSettings);

        ibSettings.setOnClickListener(this);
        users.setOnClickListener(this);
        remote.setOnClickListener(this);
        timers.setOnClickListener(this);
        areas.setOnClickListener(this);
        title = (TextView) view.findViewById(R.id.tvANameMenu);

        mActivity = (main) getActivity();
        final int frag = mActivity.getCurrFrag();


        ActionItem option1, option2, option3, option4;
        //------ if Accounts fragment shown---------------------
        if (frag == Constants.Pages.ACCLISTS) {
            option1 = new ActionItem(Constants.Pages.ADDEDITSCREEN, "add acc", getResources().getDrawable(R.drawable.add_account_pu));
            title.setText("ACCOUNTS LIST");
            mActivity.setAccount(25);
        } else {
            option1 = new ActionItem(Constants.Pages.ACCLISTS, "acc list", getResources().getDrawable(R.drawable.list_pu));
        }

        //------ if About app or HElp fragment shown---------------------
        if ((frag == Constants.Pages.ABOUTPAGE) || (frag == Constants.Pages.HELPPAGE)) {
            option2 = new ActionItem(Constants.Pages.ADDEDITSCREEN, "add acc", getResources().getDrawable(R.drawable.add_account_pu));
        } else
            option2 = new ActionItem(Constants.Pages.HELPPAGE, "help", getResources().getDrawable(R.drawable.help_pu));

        //------ if About app fragment shown--------------------
        if (frag == Constants.Pages.ABOUTPAGE) {
            title.setText("ABOUT iREMOTE");
            option3 = new ActionItem(Constants.Pages.HELPPAGE, "help", getResources().getDrawable(R.drawable.help_pu));
        } else
            option3 = new ActionItem(Constants.Pages.ABOUTPAGE, "about", getResources().getDrawable(R.drawable.info_pu));

        //------ if Remote fragment shown---------------------
        if (frag == Constants.Pages.REMOTEPAGE) {
            title.setText("REMOTE");
            remote.setImageResource(R.drawable.remote_selected);
        }
        //------ if Remote fragment shown---------------------
        if (frag == Constants.Pages.AREASPAGE) {
            title.setText("SETTINGS");
            users.setImageResource(R.drawable.settings_icon_selected);
        }

        //------ if add Account shown---------------------
        if (frag == Constants.Pages.ADDACCOUNT) title.setText("ADD ACCOUNT");

        //------ if Users shown---------------------
        if (frag == Constants.Pages.USERSPAGE) {
            title.setText("SETTINGS");
            users.setImageResource(R.drawable.settings_icon_selected);
        }

        //------ if HELP fragment shown---------------------
        if (frag == Constants.Pages.HELPPAGE) title.setText("HELP");

        //------ if Add/modify Account fragment shown---------------------
        if (frag == Constants.Pages.ADDEDITSCREEN) title.setText("ADD/EDIT ACCOUNTS");

        //------ if Add/modify Account fragment shown---------------------
        if (frag == Constants.Pages.TIMERSPAGE) {
            users.setImageResource(R.drawable.settings_icon_selected);
            title.setText("SETTINGS");
        }

        if (frag == Constants.Pages.METERSPAGE) {
            users.setImageResource(R.drawable.settings_icon_selected);
            title.setText("METERS");
        }
        if (frag == Constants.Pages.SMARTHOME) {
            areas.setImageResource(R.drawable.areas_active);
            title.setText("AREAS");
        }
        //areas.setImageResource(R.drawable.areas_active);

        option4 = new ActionItem(Constants.QUITAPP, "quit", getResources().getDrawable(R.drawable.quit_pu));

        //create QuickActionPopup. Use QuickActionPopup.VERTICAL or QuickActionPopup.HORIZONTAL //param to define orientation
        quickActionPopup1 = new QuickAction(getActivity());

        //add action items into QuickActionPopup
        quickActionPopup1.addActionItem(option1);
        quickActionPopup1.addActionItem(option2);
        quickActionPopup1.addActionItem(option3);
        quickActionPopup1.addActionItem(option4);


        //Set listener for action item clicked
        //Set listener for action item clicked

        //Set listener for action item clicked
        quickActionPopup1.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(QuickAction source, int pos, int actionId) {

                SupportClass sc = new SupportClass(getActivity());
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ReplaceFragments rp = new ReplaceFragments();
                if (actionId == Constants.Pages.ADDEDITSCREEN) {
                    mActivity.setAccount(25);
                    rp.replaceWithAddEditAccount(ft, fm, true);
                } else if (actionId == Constants.Pages.ACCLISTS) {
                    rp.replaceWithAL(ft, fm, true, true);

                } else if (actionId == Constants.Pages.ABOUTPAGE) {
                    rp.replaceWithAboutApp(ft, fm, true);
                } else if (actionId == Constants.Pages.HELPPAGE) {
                    rp.replaceWithHelp(ft, fm, true);
                } else if (actionId == Constants.QUITAPP) {
                    quitApp2();

                }
            }
        });
        //set dismiss listener
        quickActionPopup1.setOnDismissListener(new QuickAction.OnDismissListener() {
            @Override
            public void onDismiss() {
                ibSettings.setImageResource(R.drawable.ibsettings_1);

            }
        });


        return view;
    }

    public static void setTitle(String Text) {
        title.setText(Text); //Todo :Check for null pointer exception Errors

    }

    public void quitApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        getActivity().startActivity(intent);
        System.exit(0);
    }

    public void quitApp2() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ReplaceFragments rp = new ReplaceFragments();
        switch (v.getId()) {


            case R.id.ibSettings:
                //fragment.setText("Settings");
                quickActionPopup1.show(v);

                ibSettings.setImageResource(R.drawable.ibsettings);
                break;


            case R.id.ibArea:
                if (mActivity.getAccount() != 25) {
                    /*if (mActivity.getCurrFrag() == Constants.Pages.AREASPAGE) {
                        areas timerFrag;
                        timerFrag = (areas) fm.findFragmentById(R.id.detailFragment);
                        timerFrag.selectZoneDialog();
                    }*/
                    rp.replaceWithSHAreas(ft, fm, true);
                } else showChooseAccount();
                break;

            case R.id.ibTimer:
                if (mActivity.getAccount() != 25) {
                    /*if (mActivity.getCurrFrag() == Constants.Pages.TIMERSPAGE) {
                        timers timerFrag;
                        timerFrag = (timers) fm.findFragmentById(R.id.detailFragment);
                        timerFrag.showTimerDial();
                    }
                    else*/
                    //rp.replaceWithMeters(ft, fm, true);
                } else
                    showChooseAccount();
                break;
            case R.id.ibUsers:
                //mActivity.showCroutonMessage("Users clicked", Constants.SUCC_C, Constants.crs.COUTION_MODE_DEFAULT, true);
                if (mActivity.getAccount() != 25) {
                    if (mActivity.getCurrFrag() == Constants.Pages.USERSPAGE) {
                        users timerFrag;
                        timerFrag = (users) fm.findFragmentById(R.id.detailFragment);
                        timerFrag.reset_cd();
                    } else
                        rp.replaceWithUsers(ft, fm, true);
                } else showChooseAccount();
                break;
            case R.id.ibRemote:
                if (mActivity.getAccount() != 25) {
                    if (mActivity.getCurrFrag() == Constants.Pages.REMOTEPAGE) {
                        remote timerFrag;
                        timerFrag = (remote) fm.findFragmentById(R.id.detailFragment);
                        timerFrag.reset_cd();
                    } else
                        rp.replaceWithRemote(ft, fm, true);
                } else showChooseAccount();
                break;

        }

    }

    private void showChooseAccount() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ReplaceFragments rp = new ReplaceFragments();
        mActivity.showCroutonMessage("Choose an account from List", Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
        if (mActivity.getCurrFrag() != Constants.Pages.ACCLISTS)
            rp.replaceWithAL(ft, fm, true, true);
    }


}
