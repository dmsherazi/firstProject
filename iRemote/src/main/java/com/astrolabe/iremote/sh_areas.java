package com.astrolabe.iremote;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

public class sh_areas extends Fragment implements View.OnClickListener, View.OnTouchListener {


    public static String[] zone4area = new String[19];
    public static ImageButton ib1;
    public static ImageButton ib2;
    public static ImageButton ib3;
    public static ImageButton ib4;
    public static ImageButton ib5;
    public static ImageButton ib6;
    public static ImageButton ib7;
    public static ImageButton ib8;
    public static ImageButton ib9;
    public static ImageButton ib10;
    public static ImageButton ib11;
    public static ImageButton ib12;
    public static ImageButton ib13;
    public static ImageButton ib14;
    public static ImageButton ib15;
    public static ImageButton ib16;
    public static ImageButton ib17;
    public static ImageButton ib18;
    public static TextView bn1;
    public static TextView bn2;
    public static TextView bn3;
    public static TextView bn4;
    public static TextView bn5;
    public static TextView bn6;
    public static TextView bn7;
    public static TextView bn8;
    public static TextView bn9;
    public static TextView bn10;
    public static TextView bn11;
    public static TextView bn12;
    public static TextView bn13;
    public static TextView bn14;
    public static TextView bn15;
    public static TextView bn16;
    public static TextView bn17;
    public static TextView bn18;
    public static int statusInterval = 5000;
    main mActivity = null;
    private long Account;
    private String UserName;
    private Handler statusHandler = new Handler();
    private String Pass;
    public static WebView wvLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.smarthome_areas, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        assert view != null;


        mActivity = (main) getActivity();
        mActivity.setCurrFrag(Constants.Pages.SMARTHOME);


        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        menuFrag firstFragment1 = new menuFrag();
        ft1.replace(R.id.menuFragment, firstFragment1, mActivity.getString(R.string.menuFragment));
        ft1.commit();

        SupportClass sc = new SupportClass(getActivity());


        ib1 = (ImageButton) view.findViewById(R.id.icon1);
        ib2 = (ImageButton) view.findViewById(R.id.icon2);
        ib3 = (ImageButton) view.findViewById(R.id.icon3);
        ib4 = (ImageButton) view.findViewById(R.id.icon4);
        ib5 = (ImageButton) view.findViewById(R.id.icon5);
        ib6 = (ImageButton) view.findViewById(R.id.icon6);
        ib7 = (ImageButton) view.findViewById(R.id.icon7);
        ib8 = (ImageButton) view.findViewById(R.id.icon8);
        ib9 = (ImageButton) view.findViewById(R.id.icon9);
        ib10 = (ImageButton) view.findViewById(R.id.icon10);
        ib11 = (ImageButton) view.findViewById(R.id.icon11);
        ib12 = (ImageButton) view.findViewById(R.id.icon12);
        ib13 = (ImageButton) view.findViewById(R.id.icon13);
        ib14 = (ImageButton) view.findViewById(R.id.icon14);
        ib15 = (ImageButton) view.findViewById(R.id.icon15);
        ib16 = (ImageButton) view.findViewById(R.id.icon16);
        ib17 = (ImageButton) view.findViewById(R.id.icon17);
        ib18 = (ImageButton) view.findViewById(R.id.icon18);

        bn1 = (TextView) view.findViewById(R.id.n1);
        bn2 = (TextView) view.findViewById(R.id.n2);
        bn3 = (TextView) view.findViewById(R.id.n3);
        bn4 = (TextView) view.findViewById(R.id.n4);
        bn5 = (TextView) view.findViewById(R.id.n5);
        bn6 = (TextView) view.findViewById(R.id.n6);
        bn7 = (TextView) view.findViewById(R.id.n7);
        bn8 = (TextView) view.findViewById(R.id.n8);
        bn9 = (TextView) view.findViewById(R.id.n9);
        bn10 = (TextView) view.findViewById(R.id.n10);
        bn11 = (TextView) view.findViewById(R.id.n11);
        bn12 = (TextView) view.findViewById(R.id.n12);
        bn13 = (TextView) view.findViewById(R.id.n13);
        bn14 = (TextView) view.findViewById(R.id.n14);
        bn15 = (TextView) view.findViewById(R.id.n15);
        bn16 = (TextView) view.findViewById(R.id.n16);
        bn17 = (TextView) view.findViewById(R.id.n17);
        bn18 = (TextView) view.findViewById(R.id.n18);

        Account = mActivity.getAccount();
        mActivity.disableTouch();
        UserName = sc.getUser(Account);
        Pass = sc.getPass(Account);

        makeAllInvisible();


        wvLoading = (WebView) view.findViewById(R.id.webView2);
        wvLoading.setBackgroundColor(0x00000000);
        wvLoading.setBackgroundColor(0x00000000);
        wvLoading.loadUrl("file:///android_asset/index3.html");
        wvLoading.setVisibility(View.VISIBLE);


        mActivity.enableTouch();

        return view;
    }

    public static void makeAllInvisible() {
        ib1.setVisibility(View.GONE);
        ib2.setVisibility(View.GONE);
        ib3.setVisibility(View.GONE);
        ib4.setVisibility(View.GONE);
        ib5.setVisibility(View.GONE);
        ib6.setVisibility(View.GONE);
        ib7.setVisibility(View.GONE);
        ib8.setVisibility(View.GONE);
        ib9.setVisibility(View.GONE);

        ib10.setVisibility(View.GONE);
        ib11.setVisibility(View.GONE);
        ib12.setVisibility(View.GONE);
        ib13.setVisibility(View.GONE);
        ib14.setVisibility(View.GONE);
        ib15.setVisibility(View.GONE);
        ib16.setVisibility(View.GONE);
        ib17.setVisibility(View.GONE);
        ib18.setVisibility(View.GONE);

        bn1.setVisibility(View.GONE);
        bn2.setVisibility(View.GONE);
        bn3.setVisibility(View.GONE);
        bn4.setVisibility(View.GONE);
        bn5.setVisibility(View.GONE);
        bn6.setVisibility(View.GONE);
        bn7.setVisibility(View.GONE);
        bn8.setVisibility(View.GONE);
        bn9.setVisibility(View.GONE);

        bn10.setVisibility(View.GONE);
        bn11.setVisibility(View.GONE);
        bn12.setVisibility(View.GONE);
        bn13.setVisibility(View.GONE);
        bn14.setVisibility(View.GONE);
        bn15.setVisibility(View.GONE);
        bn16.setVisibility(View.GONE);
        bn17.setVisibility(View.GONE);
        bn18.setVisibility(View.GONE);
    }


    Runnable checkStatRun = new Runnable() {
        @Override
        public void run() {
            Log.e("Status ", "Ready to Send update");
            if (!ib1.isShown())
                mActivity.sendMessage("**#areas:" + UserName + "," + Pass + "-:!!", Constants.pBs.AREAS);
            statusHandler.postDelayed(checkStatRun, statusInterval);

        }
    };

    @Override
    public void onClick(View v) {
        mActivity = (main) getActivity();
        mActivity.cancelAllCroutons();
        SupportClass sc = new SupportClass(getActivity());

    }


   /* public void sendingScreens(SupportClass sc) {

        tvCD.setText("");
        inactiveScreen();
        mActivity.disableTouch();
        mainRL.setEnabled(false);
        mainRL.setClickable(false);
        mainRL.setEnabled(false);

        SureButton.setBackgroundResource(R.drawable.cd_sending);
    }


    public void sentSucc(SupportClass sc) {
        mainRL.setEnabled(false);
        mActivity.handler.removeCallbacksAndMessages(null);
        SureButton.setBackgroundResource(R.drawable.cd_confirmed);
        resetHandler.postDelayed(restRunnable, restInterval);
    }



    private void inactiveScreen() {

        centerBtn.setBackgroundResource(R.drawable.t_inactive);
        SureButton.setBackgroundResource(R.drawable.u_cd_inactive);
        dial_layout.setVisibility(View.VISIBLE);
        settings_layout.setVisibility(View.INVISIBLE);
        canReset = true;
        pressedButton = Constants.pBs.INACTVE;
        tvCD.setText("");

    }

    public void removeStatusAndItsCallable() {
        resetStatusHandler.removeCallbacks(restStatusRunnable);
       *//* oStatusRing.setBackgroundResource(R.drawable.as_dummy);
        armStatusRing.setBackgroundResource(R.drawable.as_dummy);
        gateStatusRing.setBackgroundResource(R.drawable.as_dummy);
        doorStatusRing.setBackgroundResource(R.drawable.as_dummy);*//*

    }

    public void sentFailed() {
        mainRL.setEnabled(false);
        inactiveScreen();
        SureButton.setBackgroundResource(R.drawable.cd_failed);
        resetHandler.postDelayed(restRunnable, restInterval);
    }

    public void reset_StatusRing() {
        mActivity.enableTouch();
        mainRL.setEnabled(true);
        mainRL.setClickable(true);

        SureButton.setEnabled(false);
        tvCD.setEnabled(false);
    }



    public void reset_cd(SupportClass sc) {
        mActivity.enableTouch();
        mainRL.setEnabled(true);
        mainRL.setClickable(true);
        //  cStatus.setVisibility(View.GONE);
        showTimerDial();
        inactiveScreen();
        mActivity.mainLO.setBackgroundResource(R.drawable.light);
        mActivity.waiting4reply = false;

        tvCD.setText("");
        pressedButton = Constants.pBs.INACTVE;
        SureButton.setEnabled(false);
        tvCD.setEnabled(false);
    }*/

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        /*if (v == mainRL || v == fl *//*|| v == flout*//*) {

            // tries = 0;
            Log.e("Touch", "inside");
            //reset_cd(new SupportClass(getActivity()));
            mActivity.cancelAllCroutons();
            if (dial_layout.isShown() && pressedButton==0)
                reset_cd(new SupportClass(getActivity()));

        }*/
        Log.e("Touch", "outside");
        return false;
    }

    /*

      Runnable checkStatRun = new Runnable() {
          @Override
          public void run() {
              Log.e("Status ", "Ready to Send update");
              if (pressedButton == Constants.pBs.INACTVE || pressedButton == Constants.pBs.REFRESHPULL) {
                  Log.e("Status ", "Sending update");
                  mActivity.sendMessage("**#timer names:"+UserName+","+Pass+"-:!!",Constants.pBs.TIMERNAMES);

              }
              else  if (settings_layout.isShown() )
              {
                  mActivity.askUpdate();
              }
              else
              Log.e("Status ", "Busy --- Sending update after 5 secs " + pressedButton);

              long statusInterval = 7000;
              statusHandler.postDelayed(checkStatRun, statusInterval);

          }
      };
      Runnable restRunnable = new Runnable() {
          @Override
          public void run() {
              reset_cd(new SupportClass(getActivity()));
          }
      };
      Runnable restStatusRunnable = new Runnable() {
          @Override
          public void run() {
              reset_StatusRing();
          }
      };

      public void sentSuccPull(SupportClass sc) {

          mainRL.setEnabled(false);
          mActivity.handler.removeCallbacksAndMessages(null);
          centerBtn.setBackgroundResource(R.drawable.r_720_inactive);
          //SureButton.setBackgroundResource(R.drawable.cd_confirmed);
          resetHandler.postDelayed(restStatusRunnable, restInterval);
      }
  */
    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of sh areas");
        super.onResume();
        checkStatRun.run();

    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of sh areas");
        super.onPause();
        statusHandler.removeCallbacks(checkStatRun);
    }
}

