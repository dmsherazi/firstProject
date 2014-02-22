package com.astrolabe.iremote;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class users extends Fragment implements View.OnClickListener, View.OnTouchListener {

    public static long statusInterval = 5000;
    public static boolean listIsEmpty = false;
    main mActivity;
    static WebView wvUsersLoading;

    SupportClass sc;
    private long Account;
    private String UserName;
    private String Pass;
    private RelativeLayout mainRL;
    private ScrollView zonePerms;
    public static RelativeLayout UsersPage;
    public RelativeLayout UsersDial;
    public RelativeLayout UsersPermissions;
    public LinearLayout ListLO;
    static String list1num;
    static String list2num;
    static String list3num;
    static String list4num;
    static String list5num;
    static String list6num;
    static String list7num;
    static String list8num;

    static CheckBox check1;
    static CheckBox check2;
    static CheckBox check3;
    static CheckBox check4;
    static CheckBox check5;
    static CheckBox check6;
    static CheckBox check7;
    static CheckBox check8;
    static CheckBox check9;
    static CheckBox checkA;
    static CheckBox checkB;
    static CheckBox checkC;
    static CheckBox checkD;
    static CheckBox checkE;
    static CheckBox checkF;
    static CheckBox checkG;


    static int pressedButton = 0;
    private Handler statusHandler = new Handler();
    private Handler resetHandler = new Handler();
    public Handler resetStatusHandler = new Handler();

    private Button importContacts;
    private ImageButton addMU;
    private ImageButton addNU;
    private ImageButton delMU;
    private ImageButton bMeters;
    private ImageButton bTimers;
    private ImageButton bZones;
    private ImageButton delNU;
    private ImageButton lists;
    private ImageButton uCDialInner;
    private ImageButton ucDialOuter;
    private RelativeLayout scrollView;
    private ImageButton doneNext;
    private FrameLayout numberFrame;
    private FrameLayout passFrame;
    public static EditText etNumber;
    public static EditText etPass;
    public static CheckBox cBoxUsers;
    public static CheckBox cBoxGate;
    public static CheckBox cBoxTimers;
    public static CheckBox cBoxMeters;
    public static CheckBox cBoxAlarm;
    public static CheckBox cBoxZones;
    public static DeSelectableRadioButton radioAway;
    public static DeSelectableRadioButton radioBoth;
    public static DeSelectableRadioButton radioStay;
    //public static DeSelectableRadioButton radioControl;

    private TextView uCText;
    private static final int REQUEST_CODE_PICK_CONTACTS = 11;
    private String numb = "";
    private String paswrd = "";

    private boolean canReset = true;
    public LinearLayout changePass;
    public EditText curPass;
    public EditText newPass;
    public EditText confirmPass;

    Button list1;
    Button list2;
    Button list3;
    Button list4;
    Button list5;
    Button list6;
    Button list7;
    Button list8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.users, container, false);


        assert view != null;

        // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mActivity = (main) getActivity();
        mActivity.setCurrFrag(Constants.Pages.USERSPAGE);


        //---- set Title of the fragment in menu fragment
        // reload menu so that it updates Quick Action Menu
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        menuFrag firstFragment1 = new menuFrag();
        ft1.replace(R.id.menuFragment, firstFragment1, mActivity.getString(R.string.menuFragment));
        ft1.commit();

        sc = new SupportClass(getActivity());
        Account = mActivity.getAccount();
        mActivity.disableTouch();
        UserName = sc.getUser(Account);
        Pass = sc.getPass(Account);

        wvUsersLoading = (WebView) view.findViewById(R.id.wvL);
        mainRL = (RelativeLayout) view.findViewById(R.id.main_users);
        UsersPage = (RelativeLayout) view.findViewById(R.id.users_frag);
        UsersDial = (RelativeLayout) view.findViewById(R.id.frame1);
        UsersPermissions = (RelativeLayout) view.findViewById(R.id.RL_numAndPass);
        zonePerms = (ScrollView) view.findViewById(R.id.zoneListCB);

        importContacts = (Button) view.findViewById(R.id.importContactsss);
        addMU = (ImageButton) view.findViewById(R.id.addMu);
        addNU = (ImageButton) view.findViewById(R.id.addNu);
        delMU = (ImageButton) view.findViewById(R.id.delMu);
        delNU = (ImageButton) view.findViewById(R.id.delNu);
        lists = (ImageButton) view.findViewById(R.id.listUser);

        bMeters = (ImageButton) view.findViewById(R.id.meters);
        bTimers = (ImageButton) view.findViewById(R.id.timerss);
        bZones = (ImageButton) view.findViewById(R.id.zones);

        uCDialInner = (ImageButton) view.findViewById(R.id.cdialUsers);
        ucDialOuter = (ImageButton) view.findViewById(R.id.usersCD);
        scrollView = (RelativeLayout) view.findViewById(R.id.scrollview);
        doneNext = (ImageButton) view.findViewById(R.id.u_done);

        numberFrame = (FrameLayout) view.findViewById(R.id.FLUserNumber);
        ListLO = (LinearLayout) view.findViewById(R.id.usersListLO);
        changePass = (LinearLayout) view.findViewById(R.id.changePass);

        passFrame = (FrameLayout) view.findViewById(R.id.u_pass);
        etNumber = (EditText) view.findViewById(R.id.u_et_number);
        etPass = (EditText) view.findViewById(R.id.etPassass);

        curPass = (EditText) view.findViewById(R.id.curPass);
        newPass = (EditText) view.findViewById(R.id.newPass);
        confirmPass = (EditText) view.findViewById(R.id.confirmPass);

        cBoxUsers = (CheckBox) view.findViewById(R.id.checkBoxVzUsers);
        cBoxGate = (CheckBox) view.findViewById(R.id.checkBoxVzGate);
        cBoxTimers = (CheckBox) view.findViewById(R.id.checkBoxVzTimers);
        cBoxMeters = (CheckBox) view.findViewById(R.id.checkBoxMeters);
        cBoxAlarm = (CheckBox) view.findViewById(R.id.checkBoxAlarm);
        cBoxZones = (CheckBox) view.findViewById(R.id.checkBoxZones);

        check1 = (CheckBox) view.findViewById(R.id.check1);
        check2 = (CheckBox) view.findViewById(R.id.check2);
        check3 = (CheckBox) view.findViewById(R.id.check3);
        check4 = (CheckBox) view.findViewById(R.id.check4);
        check5 = (CheckBox) view.findViewById(R.id.check5);
        check6 = (CheckBox) view.findViewById(R.id.check6);
        check7 = (CheckBox) view.findViewById(R.id.check7);
        check8 = (CheckBox) view.findViewById(R.id.check8);
        check9 = (CheckBox) view.findViewById(R.id.check9);
        checkA = (CheckBox) view.findViewById(R.id.checkA);
        checkB = (CheckBox) view.findViewById(R.id.checkB);
        checkC = (CheckBox) view.findViewById(R.id.checkC);
        checkD = (CheckBox) view.findViewById(R.id.checkD);
        checkE = (CheckBox) view.findViewById(R.id.checkE);
        checkF = (CheckBox) view.findViewById(R.id.checkF);
        checkG = (CheckBox) view.findViewById(R.id.checkG);

        radioAway = (DeSelectableRadioButton) view.findViewById(R.id.cAway);
        radioBoth = (DeSelectableRadioButton) view.findViewById(R.id.cBoth);
        radioStay = (DeSelectableRadioButton) view.findViewById(R.id.cStay);




        curPass.setFilters(new InputFilter[]{filter});
        newPass.setFilters(new InputFilter[]{filter});
        confirmPass.setFilters(new InputFilter[]{filter});
        curPass.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        newPass.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        confirmPass.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Arial_Black.ttf");
        uCText = (TextView) view.findViewById(R.id.uCdtextv);
        // tvCD.setText("text");
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
        makeAllInvisible();
        list1.setOnClickListener(this);
        list2.setOnClickListener(this);
        list3.setOnClickListener(this);
        list4.setOnClickListener(this);
        list5.setOnClickListener(this);
        list6.setOnClickListener(this);
        list7.setOnClickListener(this);
        list8.setOnClickListener(this);


       /* wvUsersLoading.setBackgroundColor(0x00000000);
        wvUsersLoading.loadUrl("file:///android_asset/index3.html");
        //wvUsersLoading.setVisibility(View.VISIBLE);*/
        ListLO.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        UsersDial.setVisibility(View.GONE);
        UsersPermissions.setVisibility(View.GONE);


        uCText.setTypeface(tf);
        //tvCD.setTextColor(0x8d7f99);
        uCText.setTextSize(13);
        uCDialInner.setEnabled(false);
        ucDialOuter.setEnabled(false);

        statusHandler.postDelayed(checkStatRun, 200);
        mActivity.enableTouch();


        etNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});

        etPass.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        mainRL.setOnTouchListener(this);
        //users buttons click listeners
        addMU.setOnClickListener(this);
        addNU.setOnClickListener(this);
        delMU.setOnClickListener(this);
        delNU.setOnClickListener(this);
        lists.setOnClickListener(this);

        bTimers.setOnClickListener(this);
        bZones.setOnClickListener(this);
        bMeters.setOnClickListener(this);
        uCText.setOnClickListener(this);
        uCDialInner.setOnClickListener(this);
        doneNext.setOnClickListener(this);
        importContacts.setOnClickListener(this);
        zonePerms.setVisibility(View.GONE);
        /*tvCD.setOnClickListener(this);
        centerBtn.setOnClickListener(this);

        SureButton.setOnClickListener(this);*//**//**/
        etNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etNumber.setText(sc.getMeMyNumber(etNumber.getText().toString()));
                    if (!etPass.hasFocus()) {
                        hideKEYB();
                    }
                }
            }
        });
        etPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    if (!etNumber.hasFocus()) {
                        hideKEYB();
                    }
            }
        });

        etNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_NEXT)) {

                    etNumber.setText(sc.getMeMyNumber(etNumber.getText().toString()));
                }
                return false;
            }


        });

        return view;
    }

    public void hideKEYB() {
       /* InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }*/
    }

    public static void tvSetText(String str) {
        etNumber.setText(str);
    }

    public void makeAllInvisible() {
        Log.e("List", "making Invisible");
        list1.setVisibility(View.INVISIBLE);
        list2.setVisibility(View.INVISIBLE);
        list3.setVisibility(View.INVISIBLE);
        list4.setVisibility(View.INVISIBLE);
        list5.setVisibility(View.INVISIBLE);
        list6.setVisibility(View.INVISIBLE);
        list7.setVisibility(View.INVISIBLE);
        list8.setVisibility(View.INVISIBLE);
        Log.e("List", "done making  Invisible");
    }

    public void showUsersList() {
        Log.e("List", "showing UserList");
        UsersDial.setVisibility(View.GONE);
        UsersPermissions.setVisibility(View.GONE);
        zonePerms.setVisibility(View.GONE);
        ListLO.setVisibility(View.VISIBLE);
        Log.e("List", "done showing");
    }

    private void inactiveScreen() {
        uCText.setText("");
        menuFrag.setTitle(getString(R.string.Tit_users));
        zonePerms.setVisibility(View.GONE);
        UsersDial.setVisibility(View.VISIBLE);
        ListLO.setVisibility(View.GONE);
        UsersPermissions.setVisibility(View.GONE);
        if (sc.getControlPanelType(Account) == 2) {
            addNU.setEnabled(false);
            delNU.setEnabled(false);
            ucDialOuter.setBackgroundResource(R.drawable.users_inactive);
            uCDialInner.setBackgroundResource(R.drawable.u_cd_inactive);
        } else {
            ucDialOuter.setBackgroundResource(R.drawable.u_v9_inactive);
            uCDialInner.setBackgroundResource(R.drawable.u_cd_inactive);
        }

    }

    @Override
    public void onClick(View v) {

        if (v == importContacts) {
            getActivity().startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            Log.e("TEST", "Response: impot called " + REQUEST_CODE_PICK_CONTACTS);
            main mAct = (main) getActivity();
            mAct.gettingContact = true;
        }
        if (v == list1 || v == list2 || v == list3 || v == list4 || v == list5 || v == list6 || v == list7 || v == list8) {
            if (listIsEmpty && v == list4) {
                //Todo:

                pressedButton = Constants.pBs.ADDMU;
                main.editing = false;
                addmuShowFunc();
                addMUFunction();
                etNumber.setText("");

            }
            mActivity.showCroutonMessage("long press to edit or delete", Constants.crs.INFO_C, Constants.crs.COUTION_MODE_DEFAULT);
        }
        if (v == addMU) {
            if (pressedButton == Constants.pBs.ADDMU)
                reset_cd(sc);
            else {

                uCDialInner.setBackgroundResource(R.drawable.u_inner_active);
                if (sc.getControlPanelType(Account) == 9) {
                    uCText.setText("TAP TO:\n" +
                            "ADD MU");
                    ucDialOuter.setBackgroundResource(R.drawable.u_v9_add_mu);
                } else {
                    ucDialOuter.setBackgroundResource(R.drawable.users_adduser);
                    uCText.setText("TAP TO:\n" +
                            "ADD USER");
                }
                pressedButton = Constants.pBs.ADDMU;
                main.editing = false;
                uCDialInner.setEnabled(true);
            }
        }
        if (v == bTimers) {
            if (pressedButton == Constants.pBs.U_TIMERS)
                reset_cd(sc);
            else {
                uCDialInner.setBackgroundResource(R.drawable.u_inner_active);
                ucDialOuter.setBackgroundResource(R.drawable.users_timers);
                uCText.setText("TAP TO:\n" +  "SET TIMERS");
                pressedButton = Constants.pBs.U_TIMERS;
                main.editing = false;
                uCDialInner.setEnabled(true);
            }
        }
        if (v == bZones) {
            if (pressedButton == Constants.pBs.U_ZONES)
                reset_cd(sc);
            else {
                uCDialInner.setBackgroundResource(R.drawable.u_inner_active);
                ucDialOuter.setBackgroundResource(R.drawable.users_zones);
                uCText.setText("TAP TO:\n" +  "SET ZONES");
                pressedButton = Constants.pBs.U_ZONES;
                main.editing = false;
                uCDialInner.setEnabled(true);
            }
        }
        if (v == bMeters) {
            if (pressedButton == Constants.pBs.U_METERS)
                reset_cd(sc);
            else {
                uCDialInner.setBackgroundResource(R.drawable.u_inner_active);
                ucDialOuter.setBackgroundResource(R.drawable.users_meters);
                uCText.setText("TAP TO:\n" +  "VIEW METERS");
                pressedButton = Constants.pBs.U_METERS;
                main.editing = false;
                uCDialInner.setEnabled(true);
            }
        }

        if (v == addNU) {
            if (pressedButton == Constants.pBs.ADDNU)
                reset_cd(sc);
            else {

                ucDialOuter.setBackgroundResource(R.drawable.u_v9_add_nu);
                uCDialInner.setBackgroundResource(R.drawable.u_inner_active);
                uCText.setText("TAP TO:\n" +
                        "ADD NU");
                pressedButton = Constants.pBs.ADDNU;
                uCDialInner.setEnabled(true);
            }
        }

        if (v == doneNext) {

            // etNumber.setText("");///////////////////////Todo: debug code


            if (pressedButton == Constants.pBs.DELNU) {
                numb = etNumber.getText().toString();
                if (numb.startsWith("+") && numb.length() > 6) {
                    UsersPermissions.setVisibility(View.GONE);
                    UsersDial.setVisibility(View.VISIBLE);
                    sendSms("**#del nu:" + UserName + "," + Pass + "-" + numb + ":!!");
                } else {
                    numberShouldStartWithPlus();
                }

            }

            if (pressedButton == Constants.pBs.DELMU) {
                numb = etNumber.getText().toString();
                if (numb.startsWith("+") && numb.length() > 6) {
                    UsersPermissions.setVisibility(View.GONE);
                    UsersDial.setVisibility(View.VISIBLE);
                    //sendSms();
                    //pressedButton = Constants.pBs.DELMU;
                    mActivity.sendMessage("**#del mu:" + UserName + "," + Pass + "-" + numb + ":!!", pressedButton);
                } else {
                    numberShouldStartWithPlus();
                }
            }
            if (pressedButton == Constants.pBs.CHPASS) {
                String cur, naya, confirm;
                cur = curPass.getText().toString();
                naya = newPass.getText().toString();
                confirm = confirmPass.getText().toString();
                if (cur.equals(Pass)) {
                    if (naya.length() > 0) {
                        if (naya.equals(confirm)) {
                            mActivity.sendMessage("**#ch psw:" + UserName + "," + Pass + "-=" + naya + "-:!!", pressedButton);
                        } else
                            mActivity.showCroutonMessage("new and old password should match", Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
                    } else
                        mActivity.showCroutonMessage("new password can't be empty", Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
                } else
                    mActivity.showCroutonMessage("cur Password is invalid", Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
            }
            if (pressedButton == Constants.pBs.ADDMU2) {


            }
            if (pressedButton == Constants.pBs.ADDMU || pressedButton == Constants.pBs.EDITUSER) {

                addMUFunction();

            }

        }
        if (v == delMU) {
            if (pressedButton == Constants.pBs.DELMU)
                reset_cd(sc);
            else {

                uCDialInner.setBackgroundResource(R.drawable.u_inner_active);
                if (sc.getControlPanelType(Account) == 9) {
                    ucDialOuter.setBackgroundResource(R.drawable.u_v9_del_mu);
                    uCText.setText("TAP TO:\n" +
                            "DELETE MU");
                } else {
                    ucDialOuter.setBackgroundResource(R.drawable.users_ch_pass);
                    uCText.setText("TAP TO:\n" +
                            "CHANGE PASS");
                }
                pressedButton = Constants.pBs.CHPASS;
                uCDialInner.setEnabled(true);
            }
        }
        if (v == delNU) {
            if (pressedButton == Constants.pBs.DELNU)
                reset_cd(sc);
            else {
                ucDialOuter.setBackgroundResource(R.drawable.u_v9_del_nu);
                uCDialInner.setBackgroundResource(R.drawable.u_inner_active);
                uCText.setText("TAP TO:\n" +
                        "DELETE NU");
                pressedButton = Constants.pBs.DELNU;
                uCDialInner.setEnabled(true);
            }
        }
        if (v == lists) {
            if (pressedButton == Constants.pBs.LISTS)
                reset_cd(sc);
            else {

                if (sc.getControlPanelType(Account) == 9)
                    ucDialOuter.setBackgroundResource(R.drawable.u_v9_lists);
                else ucDialOuter.setBackgroundResource(R.drawable.users_lists_active);
                uCDialInner.setBackgroundResource(R.drawable.u_inner_active);
                uCText.setText("TAP TO:\n" +
                        "GET USERS LIST");
                pressedButton = Constants.pBs.LISTS;
                uCDialInner.setEnabled(true);
            }
        }
        if ((v == uCText)) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            ReplaceFragments rp = new ReplaceFragments();

            switch (pressedButton) {
                case Constants.pBs.ADDMU:
                    addmuShowFunc();
                    break;

                case Constants.pBs.ADDNU:
                    addnuShowFunc();
                    break;
                case Constants.pBs.CHPASS:
                    delmuShowFunc();
                    break;
                case Constants.pBs.DELNU:
                    delnuShowFunc();
                    break;
                case Constants.pBs.U_ZONES:
                    rp.replaceWithAreas(ft, fm, true);
                    break;
                case Constants.pBs.U_METERS:
                    rp.replaceWithMeters(ft, fm, true);
                    break;
                case Constants.pBs.U_TIMERS:
                    rp.replaceWithTimers(ft, fm, true);
                    break;
                case Constants.pBs.LISTS:

                    mActivity.sendMessage("**#list:" + UserName + "," + Pass + "-:!!", pressedButton);
                    //pressedButton = Constants.pBs.INACTVE;
                    uCDialInner.setEnabled(false);
                    break;

            }


        }
    }

    private void addMUFunction() {
        Log.e("zone perms", "add mu started");
        if (zonePerms.isShown()) {

            Log.e("zone perms", "add mu2");
            UsersPermissions.setVisibility(View.GONE);
            zonePerms.setVisibility(View.GONE);
            UsersDial.setVisibility(View.VISIBLE);
            if (check1.isChecked()) numb = numb + "1";
            if (check2.isChecked()) numb = numb + "2";
            if (check3.isChecked()) numb = numb + "3";
            if (check4.isChecked()) numb = numb + "4";
            if (check5.isChecked()) numb = numb + "5";
            if (check6.isChecked()) numb = numb + "6";
            if (check7.isChecked()) numb = numb + "7";
            if (check8.isChecked()) numb = numb + "8";
            if (check9.isChecked()) numb = numb + "9";
            if (checkA.isChecked()) numb = numb + "A";
            if (checkB.isChecked()) numb = numb + "B";
            if (checkC.isChecked()) numb = numb + "C";
            if (checkD.isChecked()) numb = numb + "D";
            if (checkE.isChecked()) numb = numb + "E";
            if (checkF.isChecked()) numb = numb + "F";
            if (checkG.isChecked()) numb = numb + "G";
            if (pressedButton == Constants.pBs.EDITUSER) {
                mActivity.sendMessage("**#edit:" + UserName + "," + Pass + "-" + numb + ":!!", pressedButton);
            } else {
                pressedButton = Constants.pBs.ADDMU;
                mActivity.sendMessage("**#add mu:" + UserName + "," + Pass + "-" + numb + ":!!", pressedButton);
                Log.e("sending message","add mu ");
            }
        } else {
            numb = etNumber.getText().toString();
            if (numb.startsWith("+") && numb.length() > 0) {

                if (sc.getControlPanelType(Account) == 9) {
                    sendSms("**#add mu:" + UserName + "," + Pass + "-" + numb + ":!!");

                    // UsersPermissions.setVisibility(View.GONE);

                    UsersDial.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                } else if (sc.getControlPanelType(Account) == 2) {

                    paswrd = etPass.getText().toString();
                    if (paswrd.length() > 0) {
                        numb = numb + "," + paswrd + ",p=";
                        if (cBoxGate.isChecked())
                            numb = numb + "g";
                        if (cBoxUsers.isChecked())
                            numb = numb + "u";
                        if (cBoxTimers.isChecked())
                            numb = numb + "t";
                        if (cBoxMeters.isChecked())
                            numb = numb + "m";
                        if (cBoxZones.isChecked())
                            numb = numb + "z";
                        if (cBoxAlarm.isChecked())
                            numb = numb + "l";
                        if (radioBoth.isChecked())
                            numb = numb + "b";
                        else if (radioAway.isChecked())
                            numb = numb + "a";
                        else if (radioStay.isChecked())
                            numb = numb + "s";
                        numberFrame.setVisibility(View.GONE);
                        UsersDial.setVisibility(View.GONE);
                        passFrame.setVisibility(View.GONE);
                        scrollView.setVisibility(View.GONE);
                        changePass.setVisibility(View.GONE);
                        zonePerms.setVisibility(View.VISIBLE);
                        menuFrag.setTitle("ZONE PERMISSIONS");
                        doneNext.setBackgroundResource(R.drawable.done_button);
                        Log.e("zone perms", "show list");
                        if (pressedButton == Constants.pBs.EDITUSER)
                            main.editing = true;
                            mActivity.sendMessage("**#zone names:" + UserName + "," + Pass + "-:!!", Constants.pBs.ZONENAMES);
                    } else {
                        passCantBeEmpty();
                        Log.e("zone perms", "pass cant be empty");
                    }
                }

            } else {
                numberShouldStartWithPlus();
            }
        }
    }

    private void passCantBeEmpty() {
        mActivity.showCroutonMessage(getString(R.string.pass_cant_be_empty),
                Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);

        etPass.setText("");

        etPass.requestFocus();
        scrollView.setVisibility(View.VISIBLE);
        UsersDial.setVisibility(View.GONE);
        UsersPermissions.setVisibility(View.VISIBLE);
    }

    public void sendingScreens(SupportClass sc) {

        uCText.setText("");
        mainRL.setEnabled(false);
        mainRL.setClickable(false);
        inactiveScreen();
        uCDialInner.setBackgroundResource(R.drawable.cd_sending);
        mActivity.disableTouch();
        mainRL.setEnabled(false);
        mainRL.setClickable(false);
        mainRL.setEnabled(false);
    }

    private void numberShouldStartWithPlus() {
        //());
        mActivity.showCroutonMessage(getString(R.string.num_starts_withPlus),
                Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
        String etText = etNumber.getText().toString();
        etNumber.setText("+" + etText);

        etNumber.requestFocus();/*
        etNumber.setSelection(1);*/
    }

    private void delnuShowFunc() {
        canReset = false;
        scrollView.setVisibility(View.GONE);
        UsersDial.setVisibility(View.GONE);
        UsersPermissions.setVisibility(View.VISIBLE);
        numberFrame.setVisibility(View.VISIBLE);
        passFrame.setVisibility(View.GONE);
        ListLO.setVisibility(View.GONE);
        etNumber.setHint(Html.fromHtml("<small>" + "Number starting with +" + "</small>"));
        etNumber.setText("");
        numb = "";
        paswrd = "";
        etNumber.setImeOptions(EditorInfo.IME_ACTION_DONE);
        doneNext.setBackgroundResource(R.drawable.done_button);
    }

    private void delmuShowFunc() {
        changePass.setVisibility(View.VISIBLE);

        menuFrag.setTitle(getString(R.string.change_your_pass));
        ListLO.setVisibility(View.GONE);
        canReset = false;
        scrollView.setVisibility(View.GONE);
        UsersDial.setVisibility(View.GONE);
        UsersPermissions.setVisibility(View.VISIBLE);
        numberFrame.setVisibility(View.GONE);
        passFrame.setVisibility(View.GONE);
        curPass.setText("");
        newPass.setText("");
        confirmPass.setText("");
        curPass.setHint(Html.fromHtml("<small>" + "Enter  current Pass" + "</small>"));
        newPass.setHint(Html.fromHtml("<small>" + "Enter  new  Pass" + "</small>"));
        confirmPass.setHint(Html.fromHtml("<small>" + "Confirm new Pass" + "</small>"));

        doneNext.setBackgroundResource(R.drawable.done_button);
    }

    final int MENU_EDIT = 0;
    final int MENU_DELETE = 1;

    View contextId;

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (list4.isShown() && (list4.getText().toString().equals(getString(R.string.add_new_user))))
            return;
        else {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.add(0, MENU_EDIT, 0, "Edit");
            menu.add(0, MENU_DELETE, 0, "Delete");
            contextId = v;
        }

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
        String command2send = "**#user info:" + UserName + "," + Pass + "-";

        if (v == list1) {
            command2send = command2send + list1num;
        } else if (v == list2) {
            command2send = command2send + list2num;
        } else if (v == list3) {
            command2send = command2send + list3num;
        } else if (v == list4) {
            command2send = command2send + list4num;
        } else if (v == list5) {
            command2send = command2send + list5num;
        } else if (v == list6) {
            command2send = command2send + list6num;
        } else if (v == list7) {
            command2send = command2send + list7num;
        } else if (v == list8) {
            command2send = command2send + list8num;
        }
        main.editing = true;
        mActivity.sendMessage(command2send + ":!!", Constants.pBs.USERINFO);
    }

    public void function2(View v) {

        if (v == list1) {
            mActivity.sendMessage("**#del mu:" + UserName + "," + Pass + "-" + list1num + ":!!", Constants.pBs.DELMU);
        } else if (v == list2) {
            mActivity.sendMessage("**#del mu:" + UserName + "," + Pass + "-" + list2num + ":!!", Constants.pBs.DELMU);
        } else if (v == list3)
            mActivity.sendMessage("**#del mu:" + UserName + "," + Pass + "-" + list3num + ":!!", Constants.pBs.DELMU);
        else if (v == list4)
            mActivity.sendMessage("**#del mu:" + UserName + "," + Pass + "-" + list4num + ":!!", Constants.pBs.DELMU);
        else if (v == list5)
            mActivity.sendMessage("**#del mu:" + UserName + "," + Pass + "-" + list5num + ":!!", Constants.pBs.DELMU);
        else if (v == list6)
            mActivity.sendMessage("**#del mu:" + UserName + "," + Pass + "-" + list6num + ":!!", Constants.pBs.DELMU);
        else if (v == list7)
            mActivity.sendMessage("**#del mu:" + UserName + "," + Pass + "-" + list7num + ":!!", Constants.pBs.DELMU);
        else if (v == list8)
            mActivity.sendMessage("**#del mu:" + UserName + "," + Pass + "-" + list8num + ":!!", Constants.pBs.DELMU);


    }

    private void addnuShowFunc() {
        canReset = false;
        scrollView.setVisibility(View.GONE);
        UsersDial.setVisibility(View.GONE);
        UsersPermissions.setVisibility(View.VISIBLE);
        numberFrame.setVisibility(View.VISIBLE);
        passFrame.setVisibility(View.GONE);
        ListLO.setVisibility(View.GONE);
        etNumber.setText("");
        numb = "";
        paswrd = "";
        etNumber.setHint(Html.fromHtml("<small>" + "Number starting with +" + "</small>"));
        etNumber.setImeOptions(EditorInfo.IME_ACTION_DONE);
        doneNext.setBackgroundResource(R.drawable.done_button);
    }

    InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                String checkMe = String.valueOf(source.charAt(i));

                Pattern pattern = Pattern.compile("[+0123456789]");
                Matcher matcher = pattern.matcher(checkMe);
                boolean valid = matcher.matches();
                if (!valid) {
                    Log.d("", "invalid");
                    return "";
                }
            }
            return null;
        }
    };

    public void addmuShowFunc() {
        etNumber.setFocusableInTouchMode(true);
        etNumber.setFocusable(true);
        etNumber.setFilters(new InputFilter[]{filter});
        zonePerms.setVisibility(View.GONE);
        etNumber.setEnabled(true);
        etNumber.setInputType(InputType.TYPE_CLASS_PHONE);
        importContacts.setEnabled(true);
        //setInputType(InputType.TYPE_CLASS_PHONE);
        //etNumber.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        menuFrag.setTitle(getString(R.string.add_users));
        canReset = false;
        changePass.setVisibility(View.GONE);
        etNumber.setHint(Html.fromHtml("<small>" + "Number starting with +" + "</small>"));
        ListLO.setVisibility(View.GONE);
        etPass.setHint(Html.fromHtml("<small>" + "Desired password" + "</small>"));
        scrollView.setVisibility(View.GONE);
        UsersDial.setVisibility(View.GONE);
        UsersPermissions.setVisibility(View.VISIBLE);
        numberFrame.setVisibility(View.VISIBLE);
        etNumber.setImeOptions(EditorInfo.IME_ACTION_DONE);
        if (sc.getControlPanelType(Account) == 2) {
            etNumber.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            // etPass.setImeOptions(EditorInfo.IME_ACTION_DONE);
            passFrame.setVisibility(View.VISIBLE);
            etPass.setText("");
            paswrd = "";
            scrollView.setVisibility(View.VISIBLE);
        } else
            passFrame.setVisibility(View.GONE);


        etNumber.setText("");
        numb = "";
        if (main.editing == true) {
            Log.e("EDIT USER", "used functions");
            etNumber.setEnabled(false);
            etNumber.setEnabled(false);
            etNumber.setInputType(InputType.TYPE_NULL);
            importContacts.setEnabled(false);
        } else {
            Log.e("ADD USER", "used functions");
            users.cBoxGate.setChecked(false);
            users.cBoxUsers.setChecked(false);
            users.cBoxMeters.setChecked(false);
            users.cBoxTimers.setChecked(false);
            users.radioAway.setChecked(false);
            users.radioStay.setChecked(false);
            users.radioBoth.setChecked(false);
            users.cBoxAlarm.setChecked(false);
            users.cBoxZones.setChecked(false);
            users.check1.setChecked(false);
            users.check2.setChecked(false);
            users.check3.setChecked(false);
            users.check4.setChecked(false);
            users.check5.setChecked(false);
            users.check6.setChecked(false);
            users.check7.setChecked(false);
            users.check8.setChecked(false);
            users.check9.setChecked(false);
            users.checkA.setChecked(false);
            users.checkB.setChecked(false);
            users.checkC.setChecked(false);
            users.checkD.setChecked(false);
            users.checkE.setChecked(false);
            users.checkF.setChecked(false);
            users.checkG.setChecked(false);
            etNumber.setEnabled(true);
            etNumber.setEnabled(true);
            etNumber.setInputType(InputType.TYPE_CLASS_PHONE);
            importContacts.setEnabled(true);
        }
        doneNext.setBackgroundResource(R.drawable.next_button);

    }

    public void reset_cd() {

        inactiveScreen();
        uCText.setVisibility(View.VISIBLE);
        ListLO.setVisibility(View.GONE);
        // mActivity.mainLO.setBackgroundResource(R.drawable.light);
        mainRL.setEnabled(true);
        mainRL.setClickable(true);
        uCText.setText("");
        pressedButton = Constants.pBs.INACTVE;
        uCDialInner.setEnabled(false);
        canReset = true;
        mActivity.enableTouch();
        mActivity.mainLO.setBackgroundResource(R.drawable.light);
        mActivity.waiting4reply = false;
    }

    private void sendSms(String list) {

        mActivity.sendMessage(list, pressedButton);
    }

    public void sentFailed() {
        uCText.setText("");
        uCText.setVisibility(View.GONE);
        inactiveScreen();
        uCDialInner.setBackgroundResource(R.drawable.cd_failed);
        Handler handler23 = new Handler();
        handler23.postDelayed(new Runnable() {
            public void run() {
                reset_cd();
            }
        }, 3000);
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {

        if (v == mainRL) {
            // fLibSettings.setVisibility(View.GONE);
            // sc.showDialog("touch");
            //rlActivity.setVisibility(View.VISIBLE);
            //ibSettings.setImageResource(R.drawable.ibsettings_1);
            // tries = 0;
            if (UsersDial.isShown())
                reset_cd();
            // else sc.showDialog("cant reset");

        }
        return false;

    }

    Runnable checkStatRun = new Runnable() {
        @Override
        public void run() {
            Log.e("Status ", "Ready to Send update");
            if (pressedButton == Constants.pBs.INACTVE ) {
                if (UsersDial.isShown()) {
                    Log.e("Status ", "Sending update");
                    // pressedButton = Constants.pBs.REFRESHPULL;
                    mActivity.askUpdate();
                }

            } else Log.e("Status ", "Busy --- Sending update after 5 secs " + pressedButton);


            statusHandler.postDelayed(checkStatRun, statusInterval);

        }
    };
    Runnable restRunnable = new Runnable() {
        @Override
        public void run() {
            reset_cd(new SupportClass(getActivity()));
        }
    };

    public void reset_cd(SupportClass supportClass) {
        inactiveScreen();
        uCText.setVisibility(View.VISIBLE);
        mainRL.setEnabled(true);
        mainRL.setClickable(true);
        uCText.setText("");
        pressedButton = Constants.pBs.INACTVE;
        uCDialInner.setEnabled(false);
        canReset = true;
        mActivity.enableTouch();

    }

    Runnable restStatusRunnable = new Runnable() {
        @Override
        public void run() {
            reset_StatusRing();
        }
    };

    public void reset_StatusRing() {
    }

    public void resetStatusRing() {
        mainRL.setEnabled(true);
        mainRL.setClickable(true);
        long restStatusInterval = 20000;
        resetStatusHandler.postDelayed(restStatusRunnable, restStatusInterval);
    }

    @Override
    public void onResume() {
        Log.e("USERS", "onResume of users");
        main.pressedButton = 0;
        super.onResume();

        if (canReset)
            reset_cd();

    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of loginFragment");
        super.onPause();
        statusHandler.removeCallbacks(checkStatRun);
    }

    public void setNumber(String meMyNumber) {
        etNumber.setText(meMyNumber);
    }

    public void sentSucc(SupportClass sc) {
        mainRL.setEnabled(false);
        uCText.setVisibility(View.GONE);
        mActivity.handler.removeCallbacksAndMessages(null);
        inactiveScreen();
        uCDialInner.setBackgroundResource(R.drawable.cd_confirmed);
        long restInterval = 2000;
        resetHandler.postDelayed(restRunnable, restInterval);
    }


}





