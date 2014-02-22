package com.astrolabe.iremote;

import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by Abu-Umar on 12/22/13.
 * fragment
 */
public class remote extends Fragment implements View.OnClickListener, View.OnTouchListener {


    int pressedButton = 0;
    public static long statusInterval = 200; // 5 seconds by default, can be changed later
    private long restInterval = 2000;
    private Handler statusHandler = new Handler();
    private Handler resetHandler = new Handler();

    // ACTIVITY SPECIFIC
    ImageButton disarmBtn;
    ImageButton armStayBtn;
    ImageButton armStayScBtn;
    ImageButton armAwayBtn;
    ImageButton armAwayScBtn;
    ImageButton doorBtn;
    ImageButton gateBtn;
    ImageButton refBtn;
    ImageButton centerBtn;
    ImageButton SureButton;
    static ImageButton oStatusRing;
    static ImageButton armStatusRing;
    static ImageButton doorStatusRing;
    static ImageButton wDoorStatusRing;
    static ImageButton gateStatusRing;
    static LinearLayout fl;


    TextView tvCD;
    // aLL ACTIVITIES
    RelativeLayout mainRL, flout;
    long Account;
    private int panelControl;
    private String UserName;
    private String Pass;
    main mActivity = null;
    public static WebView wvLoading;
    public Handler resetStatusHandler = new Handler();
    private long restStatusInterval = 15000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.remote, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        assert view != null;


        mActivity = (main) getActivity();
        mActivity.setCurrFrag(Constants.Pages.REMOTEPAGE);


        //---- set Title of the fragment in menu fragment
        // reload menu so that it updates Quick Action Menu
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        menuFrag firstFragment1 = new menuFrag();
        ft1.replace(R.id.menuFragment, firstFragment1, mActivity.getString(R.string.menuFragment));
        ft1.commit();


        mActivity.executeConnectTask();

        wvLoading = (WebView) view.findViewById(R.id.loadingWebView);
        wvLoading.setBackgroundColor(0x00000000);
        wvLoading.loadUrl("file:///android_asset/index3.html");
        wvLoading.setVisibility(View.VISIBLE);

        SupportClass sc = new SupportClass(getActivity());
        Account = mActivity.getAccount();
        mActivity.disableTouch();
        UserName = sc.getUser(Account);
        Pass = sc.getPass(Account);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Arial_Black.ttf");

        mainRL = (RelativeLayout) view.findViewById(R.id.main_rem);
        flout = (RelativeLayout) view.findViewById(R.id.frameLayout);
        fl = (LinearLayout) view.findViewById(R.id.LLO);
        disarmBtn = (ImageButton) view.findViewById(R.id.disarmDapan);
        armAwayBtn = (ImageButton) view.findViewById(R.id.armADapan);
        armAwayScBtn = (ImageButton) view.findViewById(R.id.armASDapan);
        gateBtn = (ImageButton) view.findViewById(R.id.gateDapan);
        refBtn = (ImageButton) view.findViewById(R.id.refDapan);
        doorBtn = (ImageButton) view.findViewById(R.id.doordapan);
        armStayScBtn = (ImageButton) view.findViewById(R.id.armSSDapan);
        armStayBtn = (ImageButton) view.findViewById(R.id.armStayDapan);
        SureButton = (ImageButton) view.findViewById(R.id.cdSureButn);
        oStatusRing = (ImageButton) view.findViewById(R.id.status_o_ring);
        armStatusRing = (ImageButton) view.findViewById(R.id.status_o_arm_state);
        doorStatusRing = (ImageButton) view.findViewById(R.id.status_door_ring);
        wDoorStatusRing = (ImageButton) view.findViewById(R.id.status_w_door_ring);
        gateStatusRing = (ImageButton) view.findViewById(R.id.status_gate_ring);
        centerBtn = (ImageButton) view.findViewById(R.id.cdial);
        tvCD = (TextView) view.findViewById(R.id.R_cd_Text);

        //
        fl.setVisibility(View.VISIBLE);

        mActivity.disableTouch();
        tvCD.setTypeface(tf);

        tvCD.setTextSize(13);
        SureButton.setEnabled(false);
        panelControl = 2;
        //mActivity.mainLO.setBackgroundResource(R.drawable.medium_dark);
        centerBtn.setBackgroundResource(R.drawable.r_720_inactive);
        armAwayScBtn.setEnabled(true);
        armStayBtn.setEnabled(true);
        armStayScBtn.setEnabled(true);
        doorBtn.setEnabled(true);
        gateBtn.setEnabled(true);

        tvCD.setEnabled(false);
        centerBtn.setEnabled(false);


        // cStatus.setBackgroundResource(R.drawable.cross2);

        disarmBtn.setOnClickListener(this);
        armAwayBtn.setOnClickListener(this);
        armAwayScBtn.setOnClickListener(this);
        gateBtn.setOnClickListener(this);
        doorBtn.setOnClickListener(this);
        armStayScBtn.setOnClickListener(this);
        armStayBtn.setOnClickListener(this);
        refBtn.setOnClickListener(this);
        tvCD.setOnClickListener(this);
        centerBtn.setOnClickListener(this);
        SureButton.setOnClickListener(this);

        //mPullRefreshScrollView.demo();
        mainRL.setOnTouchListener(this);
        // mPullRefreshScrollView.setOnTouchListener(this);
        fl.setOnTouchListener(this);
        flout.setOnTouchListener(this);
        // ibSettings.setOnClickListener(this);
        statusHandler.postDelayed(checkStatRun, 10);
        mActivity.enableTouch();


        return view;
    }

    // boolean waiting4reply = false;


    @Override
    public void onClick(View v) {
        mActivity = (main) getActivity();
        mActivity.cancelAllCroutons();
        if (v == disarmBtn) {
            //siteNameAndType.setText("Disarm pressed");
            if (pressedButton == Constants.pBs.DISARM)
                reset_cd();
            else {
                removeStatusAndItsCallable();
                tvCD.setEnabled(true);
                if (panelControl == 1)
                    centerBtn.setBackgroundResource(R.drawable.r_710_disarm_presd);
                else if (panelControl == 2)
                    centerBtn.setBackgroundResource(R.drawable.r_720_disarm_presd);
                else if (panelControl == 9)
                    centerBtn.setBackgroundResource(R.drawable.r_v9_disarm_presd);
                tvCD.setText("TAP TO:\n" +
                        "DISARM");
                pressedButton = Constants.pBs.DISARM;
                SureButton.setEnabled(true);

            }
        }

        if (v == armAwayBtn) {
            //siteNameAndType.setText("Disarm pressed");
            if (pressedButton == Constants.pBs.ARMA)
                reset_cd();
            else {
                tvCD.setEnabled(true);
                removeStatusAndItsCallable();
                if (panelControl == 1) {
                    centerBtn.setBackgroundResource(R.drawable.r_710_arm_presd);
                    tvCD.setText("TAP TO:\n" +
                            "ARM");
                } else if (panelControl == 2) {
                    centerBtn.setBackgroundResource(R.drawable.r_720_arm_away_press);
                    tvCD.setText("TAP TO:\n" +
                            "ARM AWAY MODE");
                } else if (panelControl == 9) {
                    centerBtn.setBackgroundResource(R.drawable.r_v9_arm_away_press);
                    tvCD.setText("TAP TO:\n" +
                            "ARM");
                }
                pressedButton = Constants.pBs.ARMA;
                SureButton.setEnabled(true);
            }
        }
        if (v == doorBtn) {
            //siteNameAndType.setText("Disarm pressed");
            if (pressedButton == Constants.pBs.DOOR)
                reset_cd();
            else {
                tvCD.setEnabled(true);
                removeStatusAndItsCallable();
                if (panelControl == 2)
                    centerBtn.setBackgroundResource(R.drawable.r_720_door_presd);
                else if (panelControl == 9)
                    centerBtn.setBackgroundResource(R.drawable.r_v9_door_presd);
                tvCD.setText("TAP TO:\nOPEN DOOR");
                pressedButton = Constants.pBs.DOOR;
                SureButton.setEnabled(true);
            }
        }
        if (v == gateBtn) {
            if (pressedButton == Constants.pBs.GATE)
                reset_cd();
            else {
                tvCD.setEnabled(true);
                removeStatusAndItsCallable();
                if (panelControl == 2)
                    centerBtn.setBackgroundResource(R.drawable.r_720_gate_presd);
                else if (panelControl == 9)
                    centerBtn.setBackgroundResource(R.drawable.r_v9_gate_presd);
                tvCD.setText("TAP TO:\nOPEN GATE");
                pressedButton = Constants.pBs.GATE;
                SureButton.setEnabled(true);
            }
        }
        if (v == refBtn) {
            //siteNameAndType.setText("Disarm pressed");
            if (pressedButton == Constants.pBs.REFRESH)
                reset_cd();
            else {
                tvCD.setEnabled(true);
                removeStatusAndItsCallable();
                if (panelControl == 2)
                    centerBtn.setBackgroundResource(R.drawable.r_720_ref_presd);
                else if (panelControl == 9)
                    centerBtn.setBackgroundResource(R.drawable.r_v9_ref_presd);
                else if (panelControl == 1)
                    centerBtn.setBackgroundResource(R.drawable.r_710_ref_presd);
                tvCD.setText("TAP TO:\nOPEN HOME DOOR");
                pressedButton = Constants.pBs.REFRESH;
                SureButton.setEnabled(true);
            }
        }
        if (v == armStayBtn) {
            //siteNameAndType.setText("Disarm pressed");
            if (pressedButton == Constants.pBs.ARMS)
                reset_cd();
            else {
                tvCD.setEnabled(true);
                removeStatusAndItsCallable();
                centerBtn.setBackgroundResource(R.drawable.r_720_sram_stay_presd);
                tvCD.setText("TAP TO:\n" +
                        "ARM STAY MODE");
                pressedButton = Constants.pBs.ARMS;
                SureButton.setEnabled(true);
            }

        }
        if (v == armStayScBtn) {
            if (pressedButton == Constants.pBs.ARMSS)
                reset_cd();
            else {
                tvCD.setEnabled(true);
                removeStatusAndItsCallable();
                centerBtn.setBackgroundResource(R.drawable.r_720_stay_sc_presd);
                tvCD.setText("TAP TO:\n" +
                        "ARM STAY SILENT MODE");
                pressedButton = Constants.pBs.ARMSS;
                SureButton.setEnabled(true);
            }
        }
        if (v == armAwayScBtn) {
            //siteNameAndType.setText("Disarm pressed");
            if (pressedButton == Constants.pBs.ARMAS)
                reset_cd();
            else {
                tvCD.setEnabled(true);
                removeStatusAndItsCallable();
                centerBtn.setBackgroundResource(R.drawable.r_720_arm_away_sc_presd);
                tvCD.setText("TAP TO:\n" +
                        "ARM AWAY SILENT MODE");
                pressedButton = Constants.pBs.ARMAS;
                SureButton.setEnabled(true);
            }
        }
        if ((v == tvCD)) {
            switch (pressedButton) {
                case Constants.pBs.DISARM:

                    mActivity.sendMessage(getString(R.string.commandDisarm) + UserName + "," + Pass
                            + "-" + getString(R.string.commandTail), pressedButton);

                    SureButton.setEnabled(false);
                    tvCD.setEnabled(false);
                    break;
                case Constants.pBs.ARMA:

                    mActivity.sendMessage(getString(R.string.commandArmA) + UserName + "," + Pass
                            + "-" + getString(R.string.commandTail), pressedButton);

                    SureButton.setEnabled(false);
                    tvCD.setEnabled(false);
                    break;
                case Constants.pBs.ARMAS:

                    mActivity.sendMessage(getString(R.string.commandArmAs) + UserName + "," + Pass
                            + "-" + getString(R.string.commandTail), pressedButton);

                    SureButton.setEnabled(false);
                    tvCD.setEnabled(false);

                    break;
                case Constants.pBs.GATE:
                    // code
                    mActivity.sendMessage(getString(R.string.commandGate) + UserName + "," + Pass
                            + "-" + getString(R.string.commandTail), pressedButton);

                    SureButton.setEnabled(false);
                    tvCD.setEnabled(false);

                    break;
                case Constants.pBs.REFRESH:
                    // code

                    mActivity.sendMessage(getString(R.string.commandDoor3) + UserName + "," + Pass
                            + "-" + getString(R.string.commandTail), pressedButton);
                    //wait4Timeout(sc);
                    SureButton.setEnabled(false);
                    tvCD.setEnabled(false);

                    break;
                case Constants.pBs.DOOR:
                    mActivity.sendMessage(getString(R.string.commandDoor) + UserName + "," + Pass
                            + "-" + getString(R.string.commandTail), pressedButton);

                    SureButton.setEnabled(false);
                    tvCD.setEnabled(false);


                    break;
                case Constants.pBs.ARMSS:
                    // code
                    mActivity.sendMessage(getString(R.string.commandArmSS) + UserName + "," + Pass
                            + "-" + getString(R.string.commandTail), pressedButton);

                    SureButton.setEnabled(false);
                    tvCD.setEnabled(false);

                    break;
                case Constants.pBs.ARMS:
                    mActivity.sendMessage(getString(R.string.commandArmS) + UserName + "," + Pass
                            + "-" + getString(R.string.commandTail), pressedButton);

                    SureButton.setEnabled(false);
                    tvCD.setEnabled(false);

                    break;
                default:
                    reset_cd();
                    break;
            }


        }


    }

    public void sendingScreens() {

        tvCD.setText("");
        tvCD.setVisibility(View.GONE);
        mActivity.disableTouch();
        mainRL.setEnabled(false);
        mainRL.setClickable(false);
        mainRL.setEnabled(false);

        centerBtn.setBackgroundResource(R.drawable.r_720_inactive);

        SureButton.setBackgroundResource(R.drawable.cd_sending);
    }


    public void sentSucc() {

        tvCD.setText("");
        tvCD.setVisibility(View.GONE);
        mainRL.setEnabled(false);
        mActivity.handler.removeCallbacksAndMessages(null);
        centerBtn.setBackgroundResource(R.drawable.r_720_inactive);
        SureButton.setBackgroundResource(R.drawable.cd_confirmed);
        resetHandler.postDelayed(restRunnable, restInterval);

    }

    public void resetStatusRing() {
        mainRL.setEnabled(true);
        mainRL.setClickable(true);
        resetStatusHandler.postDelayed(restStatusRunnable, restStatusInterval);
    }

    public void removeStatusAndItsCallable() {
        //resetStatusHandler.removeCallbacks(restStatusRunnable);
        oStatusRing.setBackgroundResource(R.drawable.as_dummy);
        armStatusRing.setBackgroundResource(R.drawable.as_dummy);
        gateStatusRing.setBackgroundResource(R.drawable.as_dummy);
        doorStatusRing.setBackgroundResource(R.drawable.as_dummy);
        wDoorStatusRing.setBackgroundResource(R.drawable.as_dummy);

    }

    public void sentFailed() {
        tvCD.setText("");
        tvCD.setVisibility(View.GONE);
        mainRL.setEnabled(false);
        centerBtn.setBackgroundResource(R.drawable.r_720_inactive);
        SureButton.setBackgroundResource(R.drawable.cd_failed);
        // SureButton.setBackgroundResource(R.drawable.as_dummy);

        resetHandler.postDelayed(restRunnable, restInterval);
    }

    public void reset_StatusRing() {
        mActivity.enableTouch();
        mainRL.setEnabled(true);
        mainRL.setClickable(true);
       /* mActivity.disableTouch();
        wvLoading.setVisibility(View.VISIBLE);*/
        oStatusRing.setBackgroundResource(R.drawable.as_dummy);
        armStatusRing.setBackgroundResource(R.drawable.as_dummy);
        gateStatusRing.setBackgroundResource(R.drawable.as_dummy);
        doorStatusRing.setBackgroundResource(R.drawable.as_dummy);
        wDoorStatusRing.setBackgroundResource(R.drawable.as_dummy);

        SureButton.setEnabled(false);
        tvCD.setEnabled(false);
    }

    public void reset_cd() {
        mActivity.enableTouch();
        tvCD.setVisibility(View.VISIBLE);
        tvCD.setText("");
        mainRL.setEnabled(true);
        mainRL.setClickable(true);
        //  cStatus.setVisibility(View.GONE);
        mActivity.mainLO.setBackgroundResource(R.drawable.light);
        mActivity.waiting4reply = false;
        centerBtn.setBackgroundResource(R.drawable.r_720_inactive);
        SureButton.setBackgroundResource(R.drawable.as_dummy);

        pressedButton = Constants.pBs.INACTVE;
        SureButton.setEnabled(false);
        tvCD.setEnabled(false);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v == mainRL || v == fl || v == flout) {
            Log.e("Touch", "inside");
            reset_cd();
            mActivity.cancelAllCroutons();
        }
        Log.e("Touch", "outside");
        return false;
    }

    //connectTask mTask;


    Runnable checkStatRun = new Runnable() {
        @Override
        public void run() {
            Log.e("Status ", "Ready to Send update");
            if (pressedButton == Constants.pBs.INACTVE || pressedButton == Constants.pBs.REFRESHPULL) {
                Log.e("Status ", "Sending update");
                mActivity.askUpdate();

            } else Log.e("Status ", "Busy --- Sending update after 5 secs " + pressedButton);

            statusHandler.postDelayed(checkStatRun, statusInterval);

        }
    };
    Runnable restRunnable = new Runnable() {
        @Override
        public void run() {
            reset_cd();
        }
    };
    Runnable restStatusRunnable = new Runnable() {
        @Override
        public void run() {
            reset_StatusRing();
        }
    };

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of LoginFragment");
        main.pressedButton = 0;
        super.onResume();


    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of loginFragment");
        super.onPause();
        statusHandler.removeCallbacks(checkStatRun);
        main.pressedButton = 0;
    }
}
