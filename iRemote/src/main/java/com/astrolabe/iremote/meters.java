package com.astrolabe.iremote;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.Random;

public class meters extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private GaugeView gvP1;
    private GaugeView gvP2;
    private GaugeView gvP3;
    private GaugeView gvW;
    private GaugeView gvG;
    private GaugeView gvT;

    private final Random RAND = new Random();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.meters, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        assert view != null;


        main mActivity = (main) getActivity();
        mActivity.setCurrFrag(Constants.Pages.METERSPAGE);


        //---- set Title of the fragment in menu fragment
        // reload menu so that it updates Quick Action Menu
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        menuFrag firstFragment1 = new menuFrag();
        ft1.replace(R.id.menuFragment, firstFragment1, mActivity.getString(R.string.menuFragment));
        ft1.commit();

        int[] colorRange = {Color.rgb(27, 202, 33), Color.rgb(232, 231, 33), Color.rgb(232, 111, 33), Color.rgb(231, 32, 43)};
        float[] RANGE_VALUES1 = {40.0f, 60.0f, 70.0f, 100.0f};

        SupportClass sc = new SupportClass(getActivity());
        gvP1 = (GaugeView) view.findViewById(R.id.gP1);
        gvP2 = (GaugeView) view.findViewById(R.id.gP2);
        gvP3 = (GaugeView) view.findViewById(R.id.gP3);
        gvW = (GaugeView) view.findViewById(R.id.gW);
        gvG = (GaugeView) view.findViewById(R.id.gG);
        gvT = (GaugeView) view.findViewById(R.id.gT);
        gvP1.setTargetValue(0);
        gvP2.setTargetValue(0);
        gvP3.setTargetValue(0);
        gvW.setTargetValue(0);
        gvG.setTargetValue(0);
        gvT.setTargetValue(0);


        mTimer.start();


        return view;
    }

    private final CountDownTimer mTimer = new CountDownTimer(3000000, 6000) {

        @Override
        public void onTick(final long millisUntilFinished) {
            gvP1.setTargetValue(RAND.nextInt(101));
            gvP2.setTargetValue(RAND.nextInt(59));
            gvP3.setTargetValue(RAND.nextInt(78));
            gvW.setTargetValue(RAND.nextInt(101));
            gvG.setTargetValue(RAND.nextInt(101));
            gvT.setTargetValue(RAND.nextInt(40));
        }

        @Override
        public void onFinish() {
        }
    };


    @Override
    public void onClick(View v) {

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Log.e("Touch", "outside");
        return false;
    }


}

