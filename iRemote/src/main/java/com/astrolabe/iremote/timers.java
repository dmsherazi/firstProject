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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.DecimalFormat;
import java.util.Calendar;

public class timers extends Fragment implements View.OnClickListener, View.OnTouchListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    static int pressedButton = 0;
    private long restInterval = 2000;
    private Handler statusHandler = new Handler();
    private Handler resetHandler = new Handler();
    public static long statusInterval = 200;
    // ACTIVITY SPECIFIC
    ImageButton t1s;
    ImageButton t2s;
    ImageButton t3s;
    ImageButton t4s;
    ImageButton t5s;
    ImageButton t6s;
    ImageButton t7s;
    ImageButton t8s;
    ImageButton centerBtn;
    ImageButton SureButton;
    ImageButton done;
    public static Button statusImage;
    public static String[][] timerNames =new String[8][15];

    RelativeLayout mainRL;
    static RelativeLayout rlActivity;
    RelativeLayout dial_layout;
    RelativeLayout settings_layout;
    public static String[] TimerNames=new String[8];

    //public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";


    Button vPressed;
    TextView tvCD;

    /*static ImageButton oStatusRing;
    static ImageButton armStatusRing;
    static ImageButton doorStatusRing;
    static ImageButton gateStatusRing;*/
    static LinearLayout fl;

    private boolean canReset = true;

    // aLL ACTIVITIES
    long Account;
    private String UserName;
    private String Pass;
    main mActivity = null;
    Calendar calendar;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    public static WebView wvLoading;
    public Handler resetStatusHandler = new Handler();

    public static Button bON;
    public static Button bOFF;
    public static ToggleButton bForce;
    public static Button bAuto;

    public static EditText etName;

    public static int stateForce = 0;
    public static int stateAuto = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.timers, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        assert view != null;


        mActivity = (main) getActivity();
        mActivity.setCurrFrag(Constants.Pages.TIMERSPAGE);


        //---- set Title of the fragment in menu fragment
        // reload menu so that it updates Quick Action Menu
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        menuFrag firstFragment1 = new menuFrag();
        ft1.replace(R.id.menuFragment, firstFragment1, mActivity.getString(R.string.menuFragment));
        ft1.commit();

        //

        SupportClass sc = new SupportClass(getActivity());
        Account = mActivity.getAccount();
        mActivity.disableTouch();
        UserName = sc.getUser(Account);
        Pass = sc.getPass(Account);


        wvLoading = (WebView) view.findViewById(R.id.wvTimerLoading);
        wvLoading.setBackgroundColor(0x00000000);
        wvLoading.loadUrl("file:///android_asset/index3.html");
        wvLoading.setVisibility(View.VISIBLE);



        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Arial_Black.ttf");

        calendar = Calendar.getInstance();

        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);

        mainRL = (RelativeLayout) view.findViewById(R.id.main_timer);
        rlActivity = (RelativeLayout) view.findViewById(R.id.timer_frg);
        settings_layout = (RelativeLayout) view.findViewById(R.id.timerSettingsScreen);
        dial_layout = (RelativeLayout) view.findViewById(R.id.frameLayout);
        dial_layout.setVisibility(View.VISIBLE);
        t1s = (ImageButton) view.findViewById(R.id.t1);
        t2s = (ImageButton) view.findViewById(R.id.t2);
        t3s = (ImageButton) view.findViewById(R.id.t3);
        t4s = (ImageButton) view.findViewById(R.id.t4);
        t5s = (ImageButton) view.findViewById(R.id.t5);
        t6s = (ImageButton) view.findViewById(R.id.t6);
        t7s = (ImageButton) view.findViewById(R.id.t7);
        t8s = (ImageButton) view.findViewById(R.id.t8);
        SureButton = (ImageButton) view.findViewById(R.id.cdSureButn);
        centerBtn = (ImageButton) view.findViewById(R.id.cdial);
        done = (ImageButton) view.findViewById(R.id.imageButton2);
        tvCD = (TextView) view.findViewById(R.id.R_cd_Text);
        etName = (EditText) view.findViewById(R.id.etTimerName);
        etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        //tvName = (TextView) view.findViewById(R.id.timerName);
        // tvCD.setText("text");
        tvCD.setTypeface(tf);
        //tvCD.setTextColor(0x8d7f99);
        tvCD.setTextSize(13);
        SureButton.setEnabled(false);

        rlActivity.setVisibility(View.VISIBLE);
        showTimerDial();
        mActivity.disableTouch();
        SureButton.setEnabled(false);


        tvCD.setEnabled(false);
        centerBtn.setEnabled(false);


        // cStatus.setBackgroundResource(R.drawable.cross2);
        showTimerDial();
        // remote buttons click listeners
        t1s.setOnClickListener(this);
        t2s.setOnClickListener(this);
        t3s.setOnClickListener(this);
        t4s.setOnClickListener(this);
        t5s.setOnClickListener(this);
        t6s.setOnClickListener(this);
        t7s.setOnClickListener(this);
        t8s.setOnClickListener(this);
        tvCD.setOnClickListener(this);
        centerBtn.setOnClickListener(this);
        done.setOnClickListener(this);

        mainRL.setOnTouchListener(this);


        rlActivity.setOnTouchListener(this);
        bAuto = (Button) view.findViewById(R.id.bAutoMan);
        statusImage = (Button) view.findViewById(R.id.statusIm);
        bForce = (ToggleButton) view.findViewById(R.id.tbForceOnOff);
        bON = (Button) view.findViewById(R.id.bOnTime);
        bOFF = (Button) view.findViewById(R.id.bOffTime);

        bAuto.setOnClickListener(this);
        bForce.setOnClickListener(this);
        bON.setOnClickListener(this);
        bOFF.setOnClickListener(this);
        // but5.setOnClickListener(this);

        centerBtn.setBackgroundResource(R.drawable.t_inactive);
        SureButton.setBackgroundResource(R.drawable.u_cd);

        // flout.setOnTouchListener(this);
        // ibSettings.setOnClickListener(this);
        statusHandler.postDelayed(checkStatRun, 200);
        mActivity.enableTouch();


        return view;
    }

    public void showTimerDial() {
        canReset = true;
        pressedButton = Constants.pBs.INACTVE;
        dial_layout.setVisibility(View.VISIBLE);
        settings_layout.setVisibility(View.INVISIBLE);
        centerBtn.setBackgroundResource(R.drawable.t_inactive);
        SureButton.setBackgroundResource(R.drawable.u_cd);
        menuFrag.setTitle(getString(R.string.Tit_timers));
        tvCD.setText("");
        inactiveScreen();
        tvCD.setEnabled(false);
    }

    private void showSettingsScreen() {
        canReset = false;
        dial_layout.setVisibility(View.INVISIBLE);
        settings_layout.setVisibility(View.VISIBLE);
        bOFF.setText(getString(R.string.set_off_time));
        bON.setText(getString(R.string.set_on_time));
        stateAuto = 0;
        stateForce = 0;
        menuFrag.setTitle("TIMER " + pressedButton + " SETTINGS");

        //bForce.setBackgroundResource(R.drawable.sw_2_def);
        //bAuto.setBackgroundResource(R.drawable.but_def);
        mActivity.sendMessage("**#get timer:"+UserName+","+Pass+"-"+pressedButton+":!!",Constants.pBs.GETTIMER);

        mActivity.sendMessage("**#get timer:"+UserName+","+Pass+"-"+pressedButton+":!!",Constants.pBs.GETTIMER);

    }


    @Override
    public void onClick(View v) {
        mActivity = (main) getActivity();
        mActivity.cancelAllCroutons();
        SupportClass sc = new SupportClass(getActivity());
        if (v == t1s) {
            //siteNameAndType.setText("Disarm pressed");
            commonFunction(1, R.drawable.t_t1);
        }

        if (v == t2s) {
            commonFunction(2, R.drawable.t_t2);
        }
        if (v == t3s) {
            commonFunction(3, R.drawable.t_t3);
        }
        if (v == t4s) {
            commonFunction(4, R.drawable.t_t4);
        }
        if (v == t5s) {
            commonFunction(5, R.drawable.t_t5);
        }
        if (v == t6s) {
            commonFunction(6, R.drawable.t_t6);
        }
        if (v == t7s) {
            commonFunction(7, R.drawable.t_t7);
        }
        if (v == t8s) {
            commonFunction(8, R.drawable.t_t8);
        }
        if ((v == bOFF) || (v == bON)) {
            //timePickerDialog.setVibrate(true);
            timePickerDialog.show(getFragmentManager(), TIMEPICKER_TAG);


            vPressed = (Button) v;
        }
        if(v== bAuto){

            bON.setText(getString(R.string.set_on_time));
            bOFF.setText(getString(R.string.set_off_time));

        }
        if (v == bForce) {
            statusImage.setBackgroundResource(R.drawable.orange_status);
            if(!bForce.isChecked()){

                mActivity.sendMessage("**#force onoff:" + UserName +","+Pass+"-"+pressedButton +"forceOn/-:!!" ,Constants.pBs.GETTIMERUP);

              //  mActivity.sendMessage("**#force onoff:" + UserName +","+Pass+"-"+pressedButton +"forceOn/-:!!" ,Constants.pBs.GETTIMERUP);

            }
            else {

                mActivity.sendMessage("**#force onoff:" + UserName +","+Pass+"-"+pressedButton +"forceOff/-:!!" ,Constants.pBs.GETTIMERUP);

              //  mActivity.sendMessage("**#force onoff:" + UserName + "," + Pass + "-" + pressedButton + "forceOff/-:!!", Constants.pBs.GETTIMERUP);


            }
        }
        if (v == bAuto) {
            bON.setText(getString(R.string.set_on_time));
            bOFF.setText(getString(R.string.set_off_time));
        }
        if (v == done)
        {
            //---------- if both times are not set send timer disabled message-----------------------
            if( (bON.getText().equals(getString(R.string.set_on_time)) && bOFF.getText().equals(getString(R.string.set_off_time)))){

                mActivity.sendMessage("**#edit timer:" + UserName +","+Pass+"-"+pressedButton +"Name="+etName.getText()+"/AutoEn=0/-:!!" ,Constants.pBs.EDITTIMER);
                //mActivity.showCroutonMessage(getString(R.string.set_on_and_off_time), Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
            }
            else  if (  (bON.getText().equals(getString(R.string.set_on_time)) || bOFF.getText().equals(getString(R.string.set_off_time)))){
                mActivity.showCroutonMessage(getString(R.string.set_on_and_off_time), Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);

            }
            else
            {
               String message2send;

               message2send="Name="+etName.getText();

                   message2send=message2send+ "/AutoEn=1";
                   String tempTime =bON.getText().toString().replaceAll("[^\\d:]", "");
                   tempTime=tempTime.replace(":",".");
                   tempTime= tempTime.substring(1);
                   message2send=message2send+ tempTime;
                   tempTime =bOFF.getText().toString().replaceAll("[^\\d:]", "");
                   tempTime=tempTime.replace(":",".");
                   tempTime= tempTime.substring(1);
                   message2send=message2send+ "."+tempTime+"//-:!!";

                mActivity.sendMessage("**#edit timer:" + UserName +","+Pass+"-"+pressedButton +message2send ,Constants.pBs.EDITTIMER);
            }



        }
        if ((v == tvCD)) {

            showSettingsScreen();

        }
    }


    public void sendingScreens(SupportClass sc) {

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

    public void resetStatusRing() {
        mainRL.setEnabled(true);
        mainRL.setClickable(true);
        long restStatusInterval = 15000;
        resetStatusHandler.postDelayed(restStatusRunnable, restStatusInterval);
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
       /* oStatusRing.setBackgroundResource(R.drawable.as_dummy);
        armStatusRing.setBackgroundResource(R.drawable.as_dummy);
        gateStatusRing.setBackgroundResource(R.drawable.as_dummy);
        doorStatusRing.setBackgroundResource(R.drawable.as_dummy);*/

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

    private void commonFunction(int i, int t_t1) {
        if (pressedButton == i) {
            reset_cd(new SupportClass(getActivity()));
        } else {
            centerBtn.setBackgroundResource(t_t1);
            pressedButton = i;
            SureButton.setBackgroundResource(R.drawable.u_inner_active);
            tvCD.setEnabled(true);
            tvCD.setText(getString(R.string.Tap_to_set_timer) + "\n" + TimerNames[i-1]);
            SureButton.setEnabled(true);
        }
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
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v == mainRL || v == fl /*|| v == flout*/) {

            // tries = 0;
            Log.e("Touch", "inside");
            //reset_cd(new SupportClass(getActivity()));
            mActivity.cancelAllCroutons();
            if (dial_layout.isShown() && pressedButton==0)
                reset_cd(new SupportClass(getActivity()));
            etName.clearFocus();
        }
        Log.e("Touch", "outside");
        return false;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String hour = formatInt(hourOfDay);
        String sMinute = formatInt(minute);
        //Toast.makeText(this, "new time:" + hour + "-" + sMinute, Toast.LENGTH_LONG).show();

        if (vPressed == bON)
            vPressed.setText(getString(R.string.ONTTIMESTRING) + hour + ":" + sMinute);
        else vPressed.setText(getString(R.string.OFFTIMESTR) + hour + ":" + sMinute);
    }

    private String formatInt(int a) {
        DecimalFormat formatter = new DecimalFormat("00");
        return formatter.format(a);
    }
    //connectTask mTask;


    Runnable checkStatRun = new Runnable() {
        @Override
        public void run() {
            Log.e("Status ", "Ready to Send update");
            if (pressedButton == Constants.pBs.INACTVE || pressedButton == Constants.pBs.REFRESHPULL) {


                Log.e("Status ", "Sending update");
                mActivity.sendMessage("**#timer names:"+UserName+","+Pass+"-:!!",Constants.pBs.TIMERNAMES);

            }
            else  if (settings_layout.isShown() && !etName.hasFocus() )
            {
                mActivity.askUpdate();
            }
            else
            Log.e("Status ", "Busy --- Sending update after 5 secs " + pressedButton);


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
    }
}

