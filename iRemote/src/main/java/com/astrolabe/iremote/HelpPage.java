package com.astrolabe.iremote;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by Abu-Umar on 12/22/13.
 * fragment
 */
public class HelpPage extends Fragment implements View.OnClickListener {

    Button helpApp;
    Button helpV9;
    Button help720;
    public static RelativeLayout ButtonsLO;
    public static WebView wv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.help_page, container, false);


        assert view != null;
        SupportClass sc = new SupportClass(getActivity());
        main mActivity = (main) getActivity();
        mActivity.setCurrFrag(Constants.Pages.HELPPAGE);

         wv = (WebView) view.findViewById(R.id.webHelp);
        wv.setVisibility(View.INVISIBLE);
        ButtonsLO = (RelativeLayout)view.findViewById(R.id.ButtonsLO);

        helpApp = (Button)view.findViewById(R.id.button);
        helpV9 = (Button)view.findViewById(R.id.button2);
        help720 = (Button)view.findViewById(R.id.button3);
        help720.setOnClickListener(this);
        helpV9.setOnClickListener(this);
        helpApp.setOnClickListener(this);

        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        //ft1.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        menuFrag firstFragment1 = new menuFrag();
        ft1.replace(R.id.menuFragment, firstFragment1, "menuFragment");
        ft1.commit();


        wv.setBackgroundColor(Color.argb(1, 0, 0, 0));
       // wv.setBackgroundColor(0x00000000);
        //wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("file:///android_asset/help12.html");
        //final WebView page = (WebView) findViewById(R.id.webview);




        return view;
    }

    @Override
    public void onClick(View view) {
        if(view==help720){
            ButtonsLO.setVisibility(View.INVISIBLE);
            wv.setVisibility(View.VISIBLE);
            wv.setBackgroundColor(Color.argb(1, 0, 0, 0));
            // wv.setBackgroundColor(0x00000000);
            //wv.getSettings().setJavaScriptEnabled(true);
            wv.loadUrl("file:///android_asset/help12.html");
            //final WebView page = (WebView) findViewById(R.id.webview);
        }
        if(view==helpApp){
            ButtonsLO.setVisibility(View.INVISIBLE);
            wv.setVisibility(View.VISIBLE);
            wv.setBackgroundColor(Color.argb(1, 0, 0, 0));
            // wv.setBackgroundColor(0x00000000);
            //wv.getSettings().setJavaScriptEnabled(true);
            wv.loadUrl("file:///android_asset/index3.html");
            //final WebView page = (WebView) findViewById(R.id.webview);
        }
        if(view==helpV9){
            ButtonsLO.setVisibility(View.INVISIBLE);
            wv.setVisibility(View.VISIBLE);
            wv.setBackgroundColor(Color.argb(1, 0, 0, 0));
            // wv.setBackgroundColor(0x00000000);
            //wv.getSettings().setJavaScriptEnabled(true);
            wv.loadUrl("file:///android_asset/index3.html");
            //final WebView page = (WebView) findViewById(R.id.webview);
        }

    }
}
