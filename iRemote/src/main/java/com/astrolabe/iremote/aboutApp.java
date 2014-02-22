package com.astrolabe.iremote;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Abu-Umar on 12/22/13.
 * fragment
 */
public class aboutApp extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.about_app, container, false);


        assert view != null;
        SupportClass sc = new SupportClass(getActivity());
        main mActivity = (main) getActivity();
        mActivity.setCurrFrag(Constants.Pages.ABOUTPAGE);
        TextView tv = (TextView) view.findViewById(R.id.tvInfo);

        // reload menu so that it updates Quick Action Menu
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        menuFrag firstFragment1 = new menuFrag();
        ft1.replace(R.id.menuFragment, firstFragment1, mActivity.getString(R.string.menuFragment));
        ft1.commit();

        tv.setText(Html.fromHtml(
                " <h4><u>ASTROLABE iREMOTE</u></h4>\n" +
                        "<pre><font size=\"2\" >This app lets you control your <b>SHAULA</b>" +
                        " products from your smart-phone in an easy way using Internet or Wifi as a " +
                        "means of communication." +
                        " <br><a href=\"https://play.google.com/store/apps/developer?id=Astrolabe+tech\">APPS FROM ASTROLABE</a><br><br>" +
                        "<br><br>" +
                        "We want to make iRemote better and more useful, Please send feedback<br>" +
                        "<a href=\"mailto:apps@astrolabetech.com?subject=iREMOTE&body=Version%20" + sc.getAndroidVersion()
                        + "%0A" + sc.getDeviceName() +
                        "%0AAndroid version%20" + Build.VERSION.RELEASE + "%0A%0AYour Message below this line%0A------------------------------%0A%0A\"" +
                        ">apps@astrolabetech.com</a></font></pre>"
        ));
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        /*FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        //ft1.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        menuFrag firstFragment1 = new menuFrag();
        ft1.replace(R.id.menuFragment, firstFragment1, "menuFragment");
        ft1.commit();*/


        return view;
    }
}
