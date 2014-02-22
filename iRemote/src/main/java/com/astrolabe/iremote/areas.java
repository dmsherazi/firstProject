package com.astrolabe.iremote;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * Created by Abu-Umar on 12/22/13.
 * fragment
 */
public class areas extends Fragment implements View.OnClickListener, View.OnTouchListener {


    public static long statusInterval = 200; // 5 seconds by default, can be changed later
    private long restInterval = 2000;
    private Handler statusHandler = new Handler();
    private Handler resetHandler = new Handler();

    // ACTIVITY SPECIFIC
    static RadioGroup alarmControl;
    static RadioButton radioAway;
    static RadioButton radioStay;
    static RadioButton radioBoth;
    static EditText etZoneName;
    ImageButton ibUpdate;
    ImageButton cdiaaal;
    ScrollView zoneDropList;

    public static Button bZone1;
    public static Button bZone2;
    public static Button bZone3;
    public static Button bZone4;
    public static Button bZone5;
    public static Button bZone6;
    public static Button bZone7;
    public static Button bZone8;
    public static Button bZone9;
    public static Button bZoneA;
    public static Button bZoneB;
    public static Button bZoneC;
    public static Button bZoneD;
    public static Button bZoneE;
    public static Button bZoneF;
    public static Button bZoneG;

    RelativeLayout layout_areas;
    volatile int pressedButton1 = 0;
    volatile int pressedButton = 0;
    RelativeLayout otherThanDropList;
    FrameLayout fm;
    TextView tv;
    boolean listOpen;

    {
        listOpen = false;
    }

    String zoneLetters[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G"};
    SupportClass sc = new SupportClass(getActivity());
    RelativeLayout mainRL;
    static RelativeLayout rlActivity;


    // aLL ACTIVITIES
    Integer tries = 0;
    long Account;
    private String UserName;
    private String Pass;
    main mActivity = null;

    public static Spinner sp;
    public static WebView wvLoading;
    public Handler resetStatusHandler = new Handler();
    private long restStatusInterval = 15000;

    public areas() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.areas, container, false);


        assert view != null;


        mActivity = (main) getActivity();
        mActivity.setCurrFrag(Constants.Pages.AREASPAGE);

        sc = new SupportClass(getActivity());
        //---- set Title of the fragment in menu fragment
        // reload menu so that it updates Quick Action Menu
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        menuFrag firstFragment1 = new menuFrag();
        ft1.replace(R.id.menuFragment, firstFragment1, mActivity.getString(R.string.menuFragment));
        ft1.commit();

        //


        Account = mActivity.getAccount();
        mActivity.disableTouch();
        UserName = sc.getUser(Account);
        Pass = sc.getPass(Account);


        int panelControl = sc.getControlPanelType(Account);


        mainRL = (RelativeLayout) view.findViewById(R.id.main_areas);
        rlActivity = (RelativeLayout) view.findViewById(R.id.areas_frag);
        rlActivity.setVisibility(View.GONE);

        wvLoading = (WebView) view.findViewById(R.id.wvAreasLoading);
        wvLoading.setBackgroundColor(0x00000000);
        wvLoading.loadUrl("file:///android_asset/index3.html");
        wvLoading.setVisibility(View.VISIBLE);

        layout_areas = (RelativeLayout) view.findViewById(R.id.layout);
        fm = (FrameLayout) view.findViewById(R.id.frameLayout34);
        cdiaaal = (ImageButton) view.findViewById(R.id.cd);
        bZone1 = (Button) view.findViewById(R.id.bz1);
        bZone2 = (Button) view.findViewById(R.id.bz2);
        bZone3 = (Button) view.findViewById(R.id.bz3);
        bZone4 = (Button) view.findViewById(R.id.bz4);
        bZone5 = (Button) view.findViewById(R.id.bz5);
        bZone6 = (Button) view.findViewById(R.id.bz6);
        bZone7 = (Button) view.findViewById(R.id.bz7);
        bZone8 = (Button) view.findViewById(R.id.bz8);
        bZone9 = (Button) view.findViewById(R.id.bz9);
        bZoneA = (Button) view.findViewById(R.id.bzA);
        bZoneB = (Button) view.findViewById(R.id.bzB);
        bZoneC = (Button) view.findViewById(R.id.bzC);
        bZoneD = (Button) view.findViewById(R.id.bzD);
        bZoneE = (Button) view.findViewById(R.id.bzE);
        bZoneF = (Button) view.findViewById(R.id.bzF);
        bZoneG = (Button) view.findViewById(R.id.bzG);
        ibUpdate = (ImageButton) view.findViewById(R.id.imageButton3);
        etZoneName = (EditText) view.findViewById(R.id.etZoneName);
        sp = (Spinner) view.findViewById(R.id.spinner);
        zoneDropList = (ScrollView) view.findViewById(R.id.zoneDropListLF);
        tv = (TextView) view.findViewById(R.id.tvZoneSelect);
        radioAway = (RadioButton) view.findViewById(R.id.cZAway);
        radioStay = (RadioButton) view.findViewById(R.id.cZStay);
        radioBoth = (RadioButton) view.findViewById(R.id.cZBoth);
        alarmControl = (RadioGroup) view.findViewById(R.id.radGroup12);
        fm.setOnClickListener(this);
        otherThanDropList = (RelativeLayout) view.findViewById(R.id.other);
        zoneDropList.setVisibility(View.INVISIBLE);
        cdiaaal.setVisibility(View.INVISIBLE);
        etZoneName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        etZoneName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        statusHandler.postDelayed(checkStatRun, 500);
        rlActivity.setVisibility(View.INVISIBLE);

        mActivity.disableTouch();
        panelControl = 2;


        // cStatus.setBackgroundResource(R.drawable.cross2);

        // remote buttons click listeners
        bZone1.setOnClickListener(this);
        bZone2.setOnClickListener(this);
        bZone3.setOnClickListener(this);
        bZone4.setOnClickListener(this);
        bZone5.setOnClickListener(this);
        bZone6.setOnClickListener(this);
        bZone7.setOnClickListener(this);
        bZone8.setOnClickListener(this);
        bZone9.setOnClickListener(this);
        bZoneA.setOnClickListener(this);
        bZoneB.setOnClickListener(this);
        bZoneC.setOnClickListener(this);
        bZoneD.setOnClickListener(this);
        bZoneE.setOnClickListener(this);
        bZoneF.setOnClickListener(this);
        bZoneG.setOnClickListener(this);
        ibUpdate.setOnClickListener(this);


        etZoneName.setText("");
        tv.setOnClickListener(this);

        mainRL.setOnTouchListener(this);


        rlActivity.setOnTouchListener(this);

        // flout.setOnTouchListener(this);
        // ibSettings.setOnClickListener(this);

        mActivity.enableTouch();


        return view;
    }


    @Override
    public void onClick(View v) {
        mActivity = (main) getActivity();
        mActivity.cancelAllCroutons();
        if ((v == fm) || (v == tv)) {
            if (!listOpen) {
                listOpen = true;
                tv.setText("SELECT ZONE");
                zoneDropList.setVisibility(View.VISIBLE);
                mActivity.sendMessage("**#zone names:" + UserName + "," + Pass + "-:!!", Constants.pBs.ZONENAMES);
                zoneDropList.setEnabled(true);
                otherThanDropList.setVisibility(View.INVISIBLE);
            } else {
                selectZoneDialog();
            }
        }

        if (v == ibUpdate) {
            String[] TypeString = new String[]{
                    ",P=H", ",P=C", ",P=K", ",P=B", ",P=G", ",P=O", ",P=S", ",P=b", ",P=s", ",P=k"
                    , ",P=h", ",P=L", ",P=o"};

            // etNumber.setText("");///////////////////////Todo: debug code
            Log.w("values of spiiner=", "" + String.valueOf(sp.getSelectedItem()) + "is at pos" + sp.getSelectedItemPosition());
            String ModeString = "", NameString = "";
            if (radioAway.isChecked())
                ModeString = ",M=A";
            else if (radioStay.isChecked())
                ModeString = ",M=S";
            else if (radioBoth.isChecked())
                ModeString = ",M=B";

            String PhType = TypeString[sp.getSelectedItemPosition()];

            if (etZoneName.getText().toString().length() > 0) {
                NameString = ",N=" + etZoneName.getText().toString();
            }
            if ((etZoneName.getText().toString().length() > 0) || (ModeString.length() > 0))
                mActivity.sendMessage("**#zon up:" + UserName + "," + Pass + "-" + "n" + zoneLetters[pressedButton1]
                        + ModeString + PhType + NameString
                        + ",:!!", Constants.pBs.ZoneUPATE);
            else {
                mActivity.showCroutonMessage(getString(R.string.no_data_to_update), Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
            }


        }
        if (v == bZone1) {
            pressedButton1 = 1;
            buttonPress();
        }
        if (v == bZone2) {
            pressedButton1 = 2;
            buttonPress();
        }
        if (v == bZone3) {
            pressedButton1 = 3;
            buttonPress();
        }
        if (v == bZone4) {
            pressedButton1 = 4;
            buttonPress();
        }
        if (v == bZone5) {
            pressedButton1 = 5;
            buttonPress();
        }
        if (v == bZone6) {
            pressedButton1 = 6;
            buttonPress();
        }
        if (v == bZone7) {
            pressedButton1 = 7;
            buttonPress();
        }
        if (v == bZone8) {
            pressedButton1 = 8;
            buttonPress();
        }
        if (v == bZone9) {
            pressedButton1 = 9;
            buttonPress();
        }
        if (v == bZoneA) {
            pressedButton1 = 10;
            buttonPress();
        }
        if (v == bZoneB) {
            pressedButton1 = 11;
            buttonPress();
        }
        if (v == bZoneC) {
            pressedButton1 = 12;
            buttonPress();
        }
        if (v == bZoneD) {
            pressedButton1 = 13;
            buttonPress();
        }
        if (v == bZoneE) {
            pressedButton1 = 14;
            buttonPress();
        }
        if (v == bZoneF) {
            pressedButton1 = 15;
            buttonPress();
        }
        if (v == bZoneG) {
            pressedButton1 = 16;
            buttonPress();
        }
    }

    public void selectZoneDialog() {
        Log.e("Areas ", "selctZoneDialog");
        mActivity.enableTouch();
        mainRL.setEnabled(true);
        mainRL.setClickable(true);
        listOpen = false;
        pressedButton1 = 0;
        pressedButton = 0;
        tv.setText(getString(R.string.select_zone));
        zoneDropList.setVisibility(View.INVISIBLE);
        zoneDropList.setEnabled(false);
        otherThanDropList.setVisibility(View.INVISIBLE);
        alarmControl.clearCheck();
        layout_areas.setVisibility(View.VISIBLE);
        cdiaaal.setVisibility(View.INVISIBLE);
    }

    private void buttonPress() {
        tv.setText("ZONE " + zoneLetters[pressedButton1]);

        mActivity.sendMessage("**#zon in:" + UserName + "," + Pass + "-n" + zoneLetters[pressedButton1] + "-:!!", Constants.pBs.ZoneINFO);
        zoneDropList.setVisibility(View.INVISIBLE);
        zoneDropList.setEnabled(false);

        otherThanDropList.setVisibility(View.VISIBLE);
        etZoneName.setText("");
        alarmControl.clearCheck();
        listOpen = false;
        Log.e("Areas ", "ButtonPressed");


    }

    public void sendingScreens() {

        tv.setText("");
        mActivity.disableTouch();
        mainRL.setEnabled(false);
        mainRL.setClickable(false);
        mainRL.setEnabled(false);
        etZoneName.setText("");
        mainRL.setEnabled(false);
        layout_areas.setVisibility(View.INVISIBLE);
        cdiaaal.setVisibility(View.VISIBLE);
        cdiaaal.setBackgroundResource(R.drawable.cd_sending);
    }


    public void sentSucc() {
        mainRL.setEnabled(false);
        mActivity.handler.removeCallbacksAndMessages(null);
        cdiaaal.setBackgroundResource(R.drawable.cd_confirmed);
        resetHandler.postDelayed(restRunnable, restInterval);
    }


    public void sentFailed() {
        mainRL.setEnabled(false);
        cdiaaal.setBackgroundResource(R.drawable.cd_failed);
        resetHandler.postDelayed(restRunnable, restInterval);
    }

    public void reset_StatusRing() {
        mActivity.enableTouch();
        mainRL.setEnabled(true);
        mainRL.setClickable(true);


    }


    public void reset_cd() {
        Log.e("Areas ", "RESET CD");
        mActivity.enableTouch();
        mainRL.setEnabled(true);
        mainRL.setClickable(true);
        selectZoneDialog();
        mActivity.mainLO.setBackgroundResource(R.drawable.light);
        mActivity.waiting4reply = false;
        wvLoading.setVisibility(View.INVISIBLE);
        rlActivity.setVisibility(View.VISIBLE);

        tv.setText("");
        pressedButton1 = Constants.pBs.INACTVE;
        pressedButton = 0;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v == mainRL || v == rlActivity /*|| v == flout*/) {

            // tries = 0;
            Log.e("Touch", "inside");
            //reset_cd(new SupportClass(getActivity()));
            if (listOpen) {
                selectZoneDialog();
            } else {
                rlActivity.setVisibility(View.VISIBLE);
                tries = 0;
            }
            mActivity.cancelAllCroutons();

        }
        Log.e("Touch", "outside");
        return false;
    }


    Runnable checkStatRun = new Runnable() {
        @Override
        public void run() {
            Log.e("Status ", "Ready to Send update");

            if (ibUpdate.isShown() || listOpen) {
                Log.e("Status ", "Busy --- Sending update after 5 secs " + pressedButton1);
            } else {
                Log.e("Status ", "Sending update");
                mActivity.askUpdate();
            }
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
        tries = 0;
        selectZoneDialog();
        main.pressedButton = 0;
        super.onResume();


    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of loginFragment");
        super.onPause();
        statusHandler.removeCallbacks(checkStatRun);
    }
}

