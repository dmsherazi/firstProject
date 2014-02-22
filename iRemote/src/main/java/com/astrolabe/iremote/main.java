package com.astrolabe.iremote;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import static android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI;
import static android.provider.ContactsContract.PhoneLookup.DISPLAY_NAME;


/**
 * Created by Abu-Umar on 12/22/13.
 * for Kindows Tech Solutions
 */
public class main extends FragmentActivity implements View.OnClickListener, View.OnTouchListener {
    private Crouton infiniteCrouton;
    public static int pressedButton = 0;
    SupportClass sc = new SupportClass(this);
    private static final int REQUEST_CODE_PICK_CONTACTS = 11;
    static long account = 25;
    private int curFrag = 0;
    Crouton crouton;
    private boolean _doubleBackToExitPressedOnce = false;
    static TCPClient mTcpClient;
    public boolean waiting4reply;
    public boolean gettingContact = false;
    RelativeLayout mainLO;
    public boolean asked4update = false;
    public static boolean editing = false;

    private static final Style ALERT_G_LEFT = new Style.Builder()
            .setGravity(Gravity.LEFT)
            .setBackgroundColorValue(Style.holoRedLight).build();
    private static final Style NoPerms = new Style.Builder()
            .setGravity(Gravity.LEFT)
            .setBackgroundColorValue(Style.noPerms).build();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mainLO = (RelativeLayout) findViewById(R.id.main_act_LLO);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ReplaceFragments rp = new ReplaceFragments();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit);

        addAccount aC = new addAccount();
        menuFrag firstFragment = new menuFrag();

        ft.replace(R.id.detailFragment, aC, "addacc");
        ft.replace(R.id.menuFragment, firstFragment, "menuFragment");
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.commit();

        mainLO.setOnTouchListener(this);
        if (mTcpClient != null)
            mTcpClient.stopClient();
    }

    public void setCurrFrag(int acc) {
        // based on the id you'll know which fragment is trying to save data(see below)
        // the Bundle will hold the data
        curFrag = acc;
    }

    public int getCurrFrag() {
        // here you'll save the data previously retrieved from the fragments and
        // return it in a Bundle
        return curFrag;
    }

    public static boolean getTCPClientStat() {
        // here you'll save the data previously retrieved from the fragments and
        // return it in a Bundle
        if (mTcpClient != null) {
            Log.e("CLIENT ", "Running");
            return true;
        } else {
            Log.e("CLIENT ", "already stopped");
            return false;
        }
    }

    public void stopClient() {
        if (mTcpClient != null)
            mTcpClient.stopClient();
        Log.e("CLIENT ", "STOPPED");
    }

    public void sendMessage(String message, int pBtn) {
        if (mTcpClient != null) {
           /* Log.e("SENDING MESSAGE", "address " + mTcpClient.socket.getInetAddress().toString());
            Log.e("SENDING MESSAGE", "is bound " + mTcpClient.socket.isBound());
            Log.e("SENDING MESSAGE", "is connected " + mTcpClient.isConnected);*/

            mTcpClient.sendMessage(message);
            pressedButton = pBtn;
            sendingScreens();
            handler.removeCallbacks(wait4TO);
            wait4Timeout();
        }


    }

    public void disableTouch() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        // LinearLayout dimOutScreen = (LinearLayout)findViewById(R.id.dimout_screen);
        // dimOutScreen.setVisibility(View.VISIBLE);
    }

    public void enableTouch() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        // LinearLayout dimOutScreen = (LinearLayout)findViewById(R.id.dimout_screen);
        //dimOutScreen.setVisibility(View.GONE);
    }

    public static void setAccount(long acc) {
        // based on the id you'll know which fragment is trying to save data(see below)
        // the Bundle will hold the data
        account = acc;
    }

    public long getAccount() {
        // here you'll save the data previously retrieved from the fragments and
        // return it in a Bundle
        return account;
    }

    addEditAccount fragment;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SupportClass scc = new SupportClass(this);
        Log.e("TEST", "Response: ONactivity called " + REQUEST_CODE_PICK_CONTACTS + " RqC " + requestCode + " qC " + resultCode + "tobe " + RESULT_OK);
        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.e("TEST", "Response: " + data.toString());
            Uri uriContact = data.getData();
            if (curFrag == Constants.Pages.ADDEDITSCREEN) {
                fragment = (addEditAccount) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
                fragment.setName(scc.retrieveContactName(uriContact));
                scc.retrieveContactNumber(uriContact, 1);
            } else scc.retrieveContactNumber(uriContact, 2);
            gettingContact = true;
        }
    }


    @Override
    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();

        if (curFrag == Constants.Pages.TIMERSPAGE) {
            timers timerFrag;
            timerFrag = (timers) fm.findFragmentById(R.id.detailFragment);

            if (timerFrag.settings_layout.isShown()) {
                timerFrag.showTimerDial();
                return;
            }
            if (timerFrag.dial_layout.isShown() && timerFrag.pressedButton != 0) {
                timerFrag.reset_cd(sc);
                return;
            }

        } else if (curFrag == Constants.Pages.REMOTEPAGE) {
            remote timerFrag;
            timerFrag = (remote) fm.findFragmentById(R.id.detailFragment);

            if (timerFrag.pressedButton != 0) {
                timerFrag.reset_cd(sc);
                return;
            }
        } else if (curFrag == Constants.Pages.USERSPAGE) {
            users timerFrag;
            timerFrag = (users) fm.findFragmentById(R.id.detailFragment);

            if (timerFrag.UsersPermissions.isShown() || timerFrag.ListLO.isShown() || timerFrag.changePass.isShown()) {
                timerFrag.reset_cd();
                return;
            }
            if (timerFrag.UsersDial.isShown() && users.pressedButton != 0) {
                timerFrag.reset_cd(sc);
                return;
            }
        } else if (curFrag == Constants.Pages.HELPPAGE) {

            if (HelpPage.wv.isShown()) {
                HelpPage.ButtonsLO.setVisibility(View.VISIBLE);
                HelpPage.wv.setVisibility(View.INVISIBLE);
                return;
            }

        }
        {
            Toast mToast = new Toast(this);
            if (_doubleBackToExitPressedOnce) {
                super.onBackPressed();
                mToast.cancel();
                return;
            }
            this._doubleBackToExitPressedOnce = true;
            showCroutonMessage(getString(R.string.double_press_to_quit), Constants.crs.INFO_C, Constants.crs.COUTION_MODE_DEFAULT);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    _doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    int returnedVal;

    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        assert CONTENT_FILTER_URI != null;
        Uri uri = Uri.withAppendedPath(CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        assert uri != null;
        Cursor cursor = cr.query(uri, new String[]{DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }

    @Override
    public void onClick(View view) {
        if (infiniteCrouton != null) {
            Crouton.hide(infiniteCrouton);
            infiniteCrouton = null;
        }

    }

    public void cancelAllCroutons() {
        Crouton.cancelAllCroutons();
        infiniteCrouton = null;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Crouton.clearCroutonsForActivity(this);
        Crouton.cancelAllCroutons();
        infiniteCrouton = null;

        return false;

    }


    public void askUpdate() {
        // Log.e("Status ", "asking an  update");
        asked4update = true;

        if (account >= 0 && account < 8) {

            if (curFrag == Constants.Pages.TIMERSPAGE) {
                sendMessage("**#get timer:" + sc.getUser(account) + "," + sc.getPass(account)
                        + "-" + timers.pressedButton + getString(R.string.commandTail), pressedButton);
            }
            if (curFrag == Constants.Pages.USERSPAGE) {
                sendMessage("**#list:" + sc.getUser(account) + "," + sc.getPass(account)
                        + "-" + getString(R.string.commandTail), pressedButton);
            }
            if (curFrag == Constants.Pages.AREASPAGE) {
                sendMessage("**#zone names:" + sc.getUser(account) + "," + sc.getPass(account) + "-:!!", Constants.pBs.ZONENAMES);
            } else {
                sendMessage(getString(R.string.commandUpdate) + sc.getUser(account) + "," + sc.getPass(account)
                        + "-" + getString(R.string.commandTail), pressedButton);
            }
            //else Log.e("Asking update ", "account out of range");
            //
        }
    }

    public class connectTask extends AsyncTask<String, String, TCPClient> {

        @Override
        protected TCPClient doInBackground(String... message) {
            // Log.e("TEST", "Rconnecting " + account + "-" + "-" + sc.getSite(account) + "-" + sc.getUser(account) + "-" + sc.getPass(account));
            //we create a TCPClient object and
            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            returnedVal = mTcpClient.run(sc.getSite(account));


            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            enableTouch();
            Log.e(getString(R.string.TagTest), getString(R.string.REC) + values[0] + pressedButton + "  ");
            if (curFrag == Constants.Pages.REMOTEPAGE) {
                remote.wvLoading.setVisibility(View.INVISIBLE);
                remote.fl.setVisibility(View.VISIBLE);
                remote.statusInterval = 5000;

            }

            else if (curFrag == Constants.Pages.USERSPAGE) {
                users.wvUsersLoading.setVisibility(View.INVISIBLE);
                users.UsersPage.setVisibility(View.VISIBLE);
                users.statusInterval = 8000;
            } else if (curFrag == Constants.Pages.TIMERSPAGE) {
                timers.wvLoading.setVisibility(View.INVISIBLE);
                timers.rlActivity.setVisibility(View.VISIBLE);
                timers.statusInterval = 8000;
            } else if (curFrag == Constants.Pages.AREASPAGE) {
                areas.wvLoading.setVisibility(View.INVISIBLE);
                areas.rlActivity.setVisibility(View.VISIBLE);
                areas.statusInterval = 9000;
            }
            //-------------- on Login Failed-------------------------------------
            if (values[0].equals(getString(R.string.LoginFailed))) {
                if (asked4update) {
                    asked4update = false;
                } else
                    sentFailed();
                //showSimpleAlert("LOGIN FAILED\nIncorrect username & password combination");
                if (infiniteCrouton == null) {
                    showCroutonMessage(getString(R.string.LoginFailed), Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
                    pressedButton = Constants.pBs.INACTVE;
                }
            }
            //-------------- on Bad Auth-------------------------------------
            if (values[0].equals(getString(R.string.BadAuth))) {
                if (asked4update) asked4update = false;
                else
                    sentFailed();
                if (infiniteCrouton == null) {
                    showCroutonMessage(getString(R.string.AccessDenied), Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
                    pressedButton = Constants.pBs.INACTVE;
                }
            }
            //-------------- on Bad Auth-------------------------------------
            if (values[0].equals(getString(R.string.AcessDenied))) {
                if (asked4update) asked4update = false;
                else
                    sentFailed();
                if (infiniteCrouton == null) {
                    showCroutonMessage(getString(R.string.AccesDeniedCheckPerms), Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
                    pressedButton = Constants.pBs.INACTVE;
                }
            }
            //-------------- Door-------------------------------------
            if (pressedButton == Constants.pBs.DOOR && waiting4reply) {
                if (values[0].equals(getString(R.string.Door_Opened))) {
                    askUpdate();
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                }
            }

            //-------------- Gate-------------------------------------
            else if (pressedButton == Constants.pBs.GATE && waiting4reply) {
                if (values[0].equals(getString(R.string.Gate_Opened))) {
                    askUpdate();
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                }
            }
            //-------------- Arm AS-------------------------------------
            else if (pressedButton == Constants.pBs.ARMAS && waiting4reply) {
                if (values[0].contains(getString(R.string.Armed_Away_scl))) {
                    String openZones = values[0].replaceAll(getString(R.string.Armed_Away_scl), getString(R.string.emptyString));
                    askUpdate();
                    sentSucc();
                    if (openZones.length() > 5) {
                        showCroutonMessage(getString(R.string.Armed) +
                                getString(R.string.ALERT) + openZones, Constants.crs.INFO_GL, Constants.crs.COUTION_MODE_INFINTE);
                        // Log.e("ARM ", "Show Crouton" + openZones);
                    }
                    pressedButton = Constants.pBs.INACTVE;
                }

            }
            //-------------- Arm AQ-------------------------------------
            else if (pressedButton == Constants.pBs.ARMA && waiting4reply) {
                if (values[0].contains(getString(R.string.Armed_Away_Sr))) {
                    String openZones = values[0].replaceAll(getString(R.string.Armed_Away_Sr), getString(R.string.emptyString));
                    //askUpdate();
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                    // String openZones = values[0].replaceAll(getString(R.string.Armed_Away_Sr), getString(R.string.emptyString));
                    if (openZones.length() > 5) {
                        showCroutonMessage(openZones, Constants.crs.INFO_GL, Constants.crs.COUTION_MODE_INFINTE);
                    }

                }

            }
            //-------------- Arm SQ-------------------------------------
            else if (pressedButton == Constants.pBs.ARMS && waiting4reply) {
                if (values[0].contains(getString(R.string.Armed_Stay))) {
                    String openZones = values[0].replaceAll(getString(R.string.Armed_Stay), getString(R.string.emptyString));
                    askUpdate();
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                    //String openZones = values[0].replaceAll(getString(R.string.Armed_Stay), getString(R.string.emptyString));
                    if (openZones.length() > 5) {
                        showCroutonMessage(openZones, Constants.crs.INFO_GL, Constants.crs.COUTION_MODE_INFINTE);
                    }

                }

            }
            //-------------- Arm SS-------------------------------------
            else if (pressedButton == Constants.pBs.ARMSS && waiting4reply) {
                if (values[0].contains(getString(R.string.Armed_Stay_scl))) {
                    String openZones = values[0].replaceAll(getString(R.string.Armed_Stay_scl), getString(R.string.emptyString));
                    askUpdate();
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                    //String openZones = values[0].replaceAll(getString(R.string.Armed_Stay_scl), getString(R.string.emptyString));
                    if (openZones.length() > 5) {
                        showCroutonMessage(openZones, Constants.crs.INFO_GL, Constants.crs.COUTION_MODE_INFINTE);
                    }

                }

            }
            //-------------- disarm-------------------------------------
            else if (pressedButton == Constants.pBs.DISARM && waiting4reply) {
                if (values[0].equals(getString(R.string.disarmed))) {
                    askUpdate();
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                }

            }
            //-------------- users page-------------------------------------
            else if ((pressedButton == Constants.pBs.ADDMU || pressedButton == Constants.pBs.ADDNU) && waiting4reply) {
                if (values[0].contains(getString(R.string.success)) ||
                        values[0].contains(getString(R.string.deleted)) ||
                        values[0].contains(getString(R.string.mu_list))
                        ) {
                    //askUpdate();
                    // showCroutonMessage(values[0], Constants.crs.INFO_GL, Constants.crs.COUTION_MODE_INFINTE, true);
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                } else {
                    showCroutonMessage(values[0], Constants.crs.ALERT_GL, Constants.crs.COUTION_MODE_DEFAULT);
                    sentFailed();
                    pressedButton = Constants.pBs.INACTVE;
                    handler.removeCallbacks(wait4TO);
                }


            }
            //-------------- users page-------------------------------------
            else if ((pressedButton == Constants.pBs.EDITUSER) && waiting4reply) {
                if (values[0].contains(getString(R.string.edit_success))) {
                    //askUpdate();
                    // showCroutonMessage(values[0], Constants.crs.INFO_GL, Constants.crs.COUTION_MODE_INFINTE, true);
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                } else {
                    showCroutonMessage(values[0], Constants.crs.ALERT_GL, Constants.crs.COUTION_MODE_DEFAULT);
                    sentFailed();
                    handler.removeCallbacks(wait4TO);
                }
                editing = false;

            }
            //-------------- users page-------------------------------------
            else if ((pressedButton == Constants.pBs.CHPASS) && waiting4reply) {
                if (values[0].contains(getString(R.string.change_success))) {
                    //askUpdate();
                    showCroutonMessage(values[0], Constants.crs.SUCC_GL, Constants.crs.COUTION_MODE_INFINTE);
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                } else {
                    showCroutonMessage(values[0], Constants.crs.ALERT_GL, Constants.crs.COUTION_MODE_DEFAULT);
                    sentFailed();
                    handler.removeCallbacks(wait4TO);
                }

            } else if ((pressedButton == Constants.pBs.EDITTIMER) && waiting4reply) {
                if (values[0].contains("timer details")) {
                    //askUpdate();
                    //showCroutonMessage(values[0], Constants.crs.SUCC_GL, Constants.crs.COUTION_MODE_INFINTE);
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                } else {
                    showCroutonMessage(values[0], Constants.crs.ALERT_GL, Constants.crs.COUTION_MODE_DEFAULT);
                    sentFailed();
                    handler.removeCallbacks(wait4TO);
                }

            }
            //Edit Success!!
            //------------------------EDIT USERS get Info-----------------------------
            else if (pressedButton == Constants.pBs.USERINFO && waiting4reply) {

                if (values[0].contains(getString(R.string.isnotOnList))) {
                    sentFailed();
                    showCroutonMessage(getString(R.string.notOnList), Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
                } else if (values[0].contains("!!")) {
                    values[0] = values[0].replaceAll("!!", getString(R.string.emptyString));
                    handler.removeCallbacks(wait4TO);
                    FragmentManager fm = getSupportFragmentManager();
                    users userFrag;
                    userFrag = (users) fm.findFragmentById(R.id.detailFragment);
                    userFrag.addmuShowFunc();
                    values[0] = values[0].replaceAll("!!", getString(R.string.emptyString));
                    String[] parts = values[0].split("/");
                    if (parts.length > 0 && parts[0].length() > 1)
                        users.etNumber.setText(getString(R.string.plus) + parts[0]);
                    if (parts.length > 1 && parts[1].length() > 1)
                        users.etPass.setText(parts[1]);
                    if (parts.length > 2 && parts[2].length() > 1) {
                        String Perms = parts[2];
                        users.cBoxGate.setChecked(false);
                        users.cBoxUsers.setChecked(false);
                        users.cBoxMeters.setChecked(false);
                        users.cBoxTimers.setChecked(false);
                        users.radioAway.setChecked(false);
                        users.radioStay.setChecked(false);
                        users.radioBoth.setChecked(false);
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
                        //users.radioAway.setSelected(false);
                        if (Perms.contains("u")) {
                            users.cBoxUsers.setChecked(true);
                        }
                        if (Perms.contains("t")) {
                            users.cBoxTimers.setChecked(true);
                        }
                        if (Perms.contains("m")) {
                            users.cBoxMeters.setChecked(true);
                        }
                        if (Perms.contains("g")) {
                            users.cBoxGate.setChecked(true);
                        }
                        if (Perms.contains("c")) {
                            users.cBoxAlarm.setChecked(true);
                        }
                        if (Perms.contains("z")) {
                            users.cBoxZones.setChecked(true);
                        }
                        if (Perms.contains("a")) {
                            users.radioAway.setChecked(true);
                        }
                        if (Perms.contains("b")) {
                            users.radioBoth.setChecked(true);
                        }
                        if (Perms.contains("s")) {
                            users.radioStay.setChecked(true);
                        }

                        if (Perms.contains("1")) {
                            users.check1.setChecked(true);
                        }
                        if (Perms.contains("2")) {
                            users.check2.setChecked(true);
                        }
                        if (Perms.contains("3")) {
                            users.check3.setChecked(true);
                        }
                        if (Perms.contains("4")) {
                            users.check4.setChecked(true);
                        }
                        if (Perms.contains("5")) {
                            users.check5.setChecked(true);
                        }
                        if (Perms.contains("6")) {
                            users.check6.setChecked(true);
                        }
                        if (Perms.contains("7")) {
                            users.check7.setChecked(true);
                        }
                        if (Perms.contains("8")) {
                            users.check8.setChecked(true);
                        }
                        if (Perms.contains("9")) {
                            users.check9.setChecked(true);
                        }
                        if (Perms.contains("A")) {
                            users.checkA.setChecked(true);
                        }
                        if (Perms.contains("B")) {
                            users.checkB.setChecked(true);
                        }
                        if (Perms.contains("C")) {
                            users.checkC.setChecked(true);
                        }
                        if (Perms.contains("D")) {
                            users.checkD.setChecked(true);
                        }
                        if (Perms.contains("E")) {
                            users.checkE.setChecked(true);
                        }
                        if (Perms.contains("F")) {
                            users.checkF.setChecked(true);
                        }
                        if (Perms.contains("G")) {
                            users.checkG.setChecked(true);
                        }


                    }
                    mainLO.setBackgroundResource(R.drawable.light);
                    enableTouch();

                    users.etNumber.setFocusableInTouchMode(false);
                    users.etNumber.setFocusable(false);

                    users.pressedButton = Constants.pBs.EDITUSER;
                    users.etNumber.setEnabled(false);
                    users.etNumber.setInputType(InputType.TYPE_NULL);
                }
            }
            //--------------------GET TIMER------------------------------
            else if (pressedButton == Constants.pBs.GETTIMER && waiting4reply) {

                if (values[0].startsWith("timer details")) {
                    GetTimer(values[0]);
                }
            }

            //--------------------GET TIMER------------------------------
            else if (pressedButton == Constants.pBs.GETTIMERUP && waiting4reply) {

                if (values[0].startsWith("timer details")) {
                    handler.removeCallbacks(wait4TO);

                    values[0] = values[0].replaceAll("!!", getString(R.string.emptyString));
                    String[] parts = values[0].split("/");

                    if (parts.length > 5 && parts[5].length() > 1) {
                        if (parts[5].contains(getString(R.string.off))) {
                            //timers.bForce.setBackgroundResource(R.drawable.sw_2_off);
                            //timers.tvF.setText(getString(R.string.emptyString));
                            timers.statusImage.setBackgroundResource(R.drawable.red_status);
                            timers.bForce.setChecked(true);
                            // timers.stateForce=0;
                        } else if (parts[5].contains(getString(R.string.On))) {
                            // timers.bForce.setBackgroundResource(R.drawable.sw_2_on);
                            // timers.tvF.setText(getString(R.string.emptyString));
                            timers.statusImage.setBackgroundResource(R.drawable.green_status);
                            //timers.stateForce=1;
                            timers.bForce.setChecked(false);
                        } else timers.statusImage.setBackgroundResource(R.drawable.orange_status);
                    }

                    mainLO.setBackgroundResource(R.drawable.light);
                    enableTouch();

                }
            }
            //--------------------ZONENAMES
            else if (pressedButton == Constants.pBs.TIMERNAMES && waiting4reply) {

                if (values[0].startsWith(getString(R.string.timers))) {
                    TimersNamesFunction(values[0]);
                }
            }
            //--------------------ZONENAMES
            else if (pressedButton == Constants.pBs.ZONENAMES && waiting4reply) {

                zoneNames_func(values[0]);
            }
            //--------------------ZONENAMES
            else if (pressedButton == Constants.pBs.AREAS && waiting4reply) {
                Log.w("Areas","inside areas");
                 if (values[0].startsWith(getString(R.string.areas))) {
                     Log.w("Areas","starts with areas");
                    handler.removeCallbacks(wait4TO);
                     values[0] = values[0].replaceAll("!!", getString(R.string.emptyString));
                    if (curFrag == Constants.Pages.SMARTHOME) {
                        sh_areas.wvLoading.setVisibility(View.INVISIBLE);
                        sh_areas.statusInterval = 15000;

                    }showCroutonMessage(values[0],Constants.crs.INFO_C,Constants.crs.COUTION_MODE_DEFAULT);
                    String[] parts = values[0].split("/");
                    if (parts.length > 1 && parts[1].length() > 1) {
                        String[] subParts = parts[1].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib1, sh_areas.bn1, subParts[0], 1, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 2 && parts[2].length() > 1) {
                        String[] subParts = parts[2].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib2, sh_areas.bn2, subParts[0], 2, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 3 && parts[3].length() > 1) {
                        String[] subParts = parts[3].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib3, sh_areas.bn3, subParts[0], 3, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 4 && parts[4].length() > 1) {
                        String[] subParts = parts[4].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib4, sh_areas.bn4, subParts[0], 4, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 5 && parts[5].length() > 1) {
                        String[] subParts = parts[5].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib5, sh_areas.bn5, subParts[0], 5, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 6 && parts[6].length() > 1) {
                        String[] subParts = parts[6].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib6, sh_areas.bn6, subParts[0], 6, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 7 && parts[7].length() > 1) {
                        String[] subParts = parts[7].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib7, sh_areas.bn7, subParts[0], 7, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 8 && parts[8].length() > 1) {
                        String[] subParts = parts[8].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib8, sh_areas.bn8, subParts[0], 8, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 9 && parts[9].length() > 1) {
                        String[] subParts = parts[9].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib9, sh_areas.bn9, subParts[0], 9, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 10 && parts[10].length() > 1) {
                        String[] subParts = parts[10].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib10, sh_areas.bn10, subParts[0], 10, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 11 && parts[11].length() > 1) {
                        String[] subParts = parts[11].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib11, sh_areas.bn11, subParts[0], 11, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 12 && parts[12].length() > 1) {
                        String[] subParts = parts[12].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib12, sh_areas.bn12, subParts[0], 12, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 13 && parts[13].length() > 1) {
                        String[] subParts = parts[13].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib13, sh_areas.bn13, subParts[0], 13, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 14 && parts[14].length() > 1) {
                        String[] subParts = parts[14].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib14, sh_areas.bn14, subParts[0], 14, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 15 && parts[15].length() > 1) {
                        String[] subParts = parts[15].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib15, sh_areas.bn15, subParts[0], 15, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 16 && parts[16].length() > 1) {
                        String[] subParts = parts[16].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib16, sh_areas.bn16, subParts[0], 16, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 17 && parts[17].length() > 1) {
                        String[] subParts = parts[17].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib17, sh_areas.bn17, subParts[0], 17, subParts[1], subParts[2]);
                        }
                    }
                    if (parts.length > 18 && parts[18].length() > 1) {
                        String[] subParts = parts[18].split("\\.");
                        if (subParts.length > 2) {
                            setArea(sh_areas.ib18, sh_areas.bn18, subParts[0], 18, subParts[1], subParts[2]);
                        }
                    }
                    mainLO.setBackgroundResource(R.drawable.light);
                    enableTouch();
                    Log.e("areas ", "drawings completed");
                }
            }
            //------------------------LIST USERS-----------------------------
            else if (pressedButton == Constants.pBs.LISTS && waiting4reply) {
                pressedButton = Constants.pBs.INACTVE;
                if (values[0].contains(getString(R.string.mu_list))) {

                    values[0] = values[0].replaceAll("!!", getString(R.string.emptyString));
                    //Log.e("remove !!", "values si now " + values[0] + " count of parts ");
                    showCroutonMessage(getString(R.string.longPress2EditOrDelete), Constants.crs.INFO_C, Constants.crs.COUTION_MODE_DEFAULT);

                    FragmentManager fm = getSupportFragmentManager();
                    users userFrag;
                    userFrag = (users) fm.findFragmentById(R.id.detailFragment);
                    userFrag.makeAllInvisible();
                    userFrag.showUsersList();
                    //userFrag.makeAllInvisible();
                    String[] parts = values[0].split("/");
                    menuFrag.setTitle("USER LIST");
                    if (parts.length == 1) {
                        showCroutonMessage(getString(R.string.list_is_empty), Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
                        userFrag.list4.setText(getString(R.string.add_new_user));
                        users.listIsEmpty = true;
                        userFrag.list4.setVisibility(View.VISIBLE);
                    } else
                        showCroutonMessage(getString(R.string.longPress2EditOrDelete), Constants.crs.INFO_C, Constants.crs.COUTION_MODE_DEFAULT);
                    if (parts.length > 1) {
                        if (parts[1] != null) {
                            userFrag.list1.setText(getString(R.string.plus) + parts[1]);
                            userFrag.list1.setVisibility(View.VISIBLE);
                            users.list1num = getString(R.string.plus) + parts[1];
                            if (getContactName(getApplicationContext(), parts[1]) != null)
                                userFrag.list1.setText(getContactName(getApplicationContext(), parts[1]));
                        }
                    }
                    if (parts.length > 2) {
                        if (parts[2] != null) {
                            userFrag.list2.setText(getString(R.string.plus) + parts[2]);
                            userFrag.list2.setVisibility(View.VISIBLE);
                            users.list2num = getString(R.string.plus) + parts[2];
                            if (getContactName(getApplicationContext(), parts[2]) != null)
                                userFrag.list2.setText(getContactName(getApplicationContext(), parts[2]));

                        }
                    }
                    if (parts.length > 3) {
                        if (parts[3] != null) {
                            userFrag.list3.setText(getString(R.string.plus) + parts[3]);
                            userFrag.list3.setVisibility(View.VISIBLE);
                            users.list3num = getString(R.string.plus) + parts[3];
                            if (getContactName(getApplicationContext(), parts[3]) != null)
                                userFrag.list3.setText(getContactName(getApplicationContext(), parts[3]));

                        }
                    }
                    if (parts.length > 4) {
                        if (parts[4] != null) {
                            userFrag.list4.setText(getString(R.string.plus) + parts[4]);
                            userFrag.list4.setVisibility(View.VISIBLE);
                            users.list4num = getString(R.string.plus) + parts[4];
                            if (getContactName(getApplicationContext(), parts[4]) != null)
                                userFrag.list4.setText(getContactName(getApplicationContext(), parts[4]));

                        }
                    }
                    if (parts.length > 5) {
                        if (parts[5] != null) {
                            userFrag.list5.setText(getString(R.string.plus) + parts[5]);
                            userFrag.list5.setVisibility(View.VISIBLE);
                            users.list5num = getString(R.string.plus) + parts[5];
                            if (getContactName(getApplicationContext(), parts[5]) != null)
                                userFrag.list5.setText(getContactName(getApplicationContext(), parts[5]));

                        }
                    }
                    if (parts.length > 6) {
                        Log.e("list", "above show user list6");
                        if (parts[6] != null) {
                            userFrag.list6.setText(getString(R.string.plus) + parts[6]);
                            userFrag.list6.setVisibility(View.VISIBLE);
                            users.list6num = getString(R.string.plus) + parts[6];
                            if (getContactName(getApplicationContext(), parts[6]) != null)
                                userFrag.list6.setText(getContactName(getApplicationContext(), parts[6]));

                        }
                    }
                    if (parts.length > 7) {
                        Log.e("list", "above show user list7");
                        if (parts[7] != null) {
                            userFrag.list7.setText(getString(R.string.plus) + parts[7]);
                            userFrag.list7.setVisibility(View.VISIBLE);
                            users.list7num = getString(R.string.plus) + parts[7];
                            if (getContactName(getApplicationContext(), parts[7]) != null)
                                userFrag.list7.setText(getContactName(getApplicationContext(), parts[7]));

                        }
                    }
                    if (parts.length > 8) {
                        Log.e("list", "above show user list8");
                        if (parts[8] != null) {
                            userFrag.list8.setText(getString(R.string.plus) + parts[8]);
                            userFrag.list8.setVisibility(View.VISIBLE);
                            users.list8num = getString(R.string.plus) + parts[8];
                            if (getContactName(getApplicationContext(), parts[8]) != null)
                                userFrag.list8.setText(getContactName(getApplicationContext(), parts[8]));

                        }
                    }

                    mainLO.setBackgroundResource(R.drawable.light);
                    enableTouch();


                } else {
                    showCroutonMessage(values[0], Constants.crs.ALERT_GL, Constants.crs.COUTION_MODE_DEFAULT);
                    sentFailed();
                    handler.removeCallbacks(wait4TO);
                }

            }
            //-------------- SVI/Refresh-------------------------------------
            else if (pressedButton == Constants.pBs.REFRESH && waiting4reply) {
                if (values[0].equals(getString(R.string.WDoor_Opened))) {
                    askUpdate();
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                }
            }
            //-------------- Zone Update-------------------------------------
            else if (pressedButton == Constants.pBs.ZoneUPATE && waiting4reply) {
                if (values[0].contains(getString(R.string.Zone))) {
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                    //showCroutonMessage(values[0], Constants.crs.INFO_GL, Constants.crs.COUTION_MODE_DEFAULT);
                }
            }
            //-------------- Zone INF-------------------------------------
            else if (pressedButton == Constants.pBs.ZoneINFO && waiting4reply) {
                if (values[0].contains(getString(R.string.Zone))) {

                    pressedButton = Constants.pBs.INACTVE;
                    String[] parts = values[0].split("/");
                    areas.etZoneName.setText(parts[1]);
                    areas.alarmControl.clearCheck();
                    if (parts[4].equals("A")) areas.radioAway.setChecked(true);
                    else if (parts[4].equals("S")) areas.radioStay.setChecked(true);
                    else if (parts[4].equals("B")) areas.radioBoth.setChecked(true);
                    String[] TypeString = new String[]{
                            "H", "C", "K", "B", "G", "O", "S", "b", "s", "k"
                            , "h", "L", "o"};
                    int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
                    for (int item : numbers) {
                        if (parts[5].equals(TypeString[item])) {
                            areas.sp.setSelection(item, true);
                            break;
                        }
                    }

                    // showCroutonMessage(values[0], Constants.crs.INFO_GL, Constants.crs.COUTION_MODE_DEFAULT);
                }
            }
            //-------------- DEL MU-------------------------------------
            else if (pressedButton == Constants.pBs.DELMU && waiting4reply) {
                if (values[0].contains(getString(R.string.deleted))) {
                    pressedButton = Constants.pBs.INACTVE;
                    sentSucc();
                    pressedButton = Constants.pBs.INACTVE;
                    //sendMessage("**#list:" + sc.getUser(account) + "," + sc.getPass(account) + "-:!!", Constants.Pages.USERSPAGE, pressedButton);

                } else {
                    showCroutonMessage(values[0], Constants.crs.ALERT_GL, Constants.crs.COUTION_MODE_DEFAULT);
                    sentFailed();
                    handler.removeCallbacks(wait4TO);
                }
            }
            //-------------- Update/Pull2Refresh-------------------------------------
            else if (asked4update) {

                if (values[0].startsWith("timer details")) {
                    GetTimer(values[0]);
                } else if (values[0].startsWith(getString(R.string.timers))) {
                    TimersNamesFunction(values[0]);
                } else if (values[0].startsWith(getString(R.string.update))) {

                    //pressedButton = Constants.pBs.INACTVE;
                    if (curFrag == Constants.Pages.REMOTEPAGE) {
                        FragmentManager fm = getSupportFragmentManager();
                        remote remoteFrag;
                        remoteFrag = (remote) fm.findFragmentById(R.id.detailFragment);
                        remoteFrag.resetStatusRing();
                        // remote.mPullRefreshScrollView.onRefreshComplete();
                        //remote.oStatusRing.setBackgroundResource(R.drawable.sr_orange);
                    } else if (curFrag == Constants.Pages.USERSPAGE) {
                        FragmentManager fm = getSupportFragmentManager();
                        users userFrag;
                        userFrag = (users) fm.findFragmentById(R.id.detailFragment);
                        userFrag.resetStatusRing();
                    } else if (curFrag == Constants.Pages.AREASPAGE) {
                        zoneNames_func(values[0]);
                    }
                    //=======For Remote Page ===================
                    if (curFrag == Constants.Pages.REMOTEPAGE) {
                        // ------- Status for arming modes-------------------------
                        Log.e("remote page", "starts for update");
                        if (values[0].contains(getString(R.string.statusUpdateDisarmed))) {
                            remote.armStatusRing.setBackgroundResource(R.drawable.sr_arrm_disarm);
                        } else if (values[0].contains(getString(R.string.statusUpdateArmedA))) {
                            remote.armStatusRing.setBackgroundResource(R.drawable.sr_arm_a);
                        } else if (values[0].contains(getString(R.string.statusUpdateArmedAS))) {
                            remote.armStatusRing.setBackgroundResource(R.drawable.sr_arm_as);
                        } else if (values[0].contains(getString(R.string.statusUpdateArmedS))) {
                            remote.armStatusRing.setBackgroundResource(R.drawable.sr_arm_s);
                        } else if (values[0].contains(getString(R.string.statusUpdateArmedSS))) {
                            remote.armStatusRing.setBackgroundResource(R.drawable.sr_arm_ss);
                        } else {
                            Log.e("remote page unklown", "starts for alarm");
                            remote.armStatusRing.setBackgroundResource(R.drawable.sr_arm_disarm_unknown);
                        }
                        //-------- Status for Doors-------------------------------------
                        if (values[0].contains(getString(R.string.statusUpdateDoorOpen))) {
                            remote.doorStatusRing.setBackgroundResource(R.drawable.sr_door_open);
                        } else if (values[0].contains(getString(R.string.statusUpdateDoorClosed))) {
                            remote.doorStatusRing.setBackgroundResource(R.drawable.sr_door_cl);
                        } else {
                            remote.doorStatusRing.setBackgroundResource(R.drawable.sr_door_unknown);
                            Log.e("remote page unklown", "starts for door");
                        }
                        if (values[0].contains(getString(R.string.statusUpdateDoor2Open))) {
                            remote.wDoorStatusRing.setBackgroundResource(R.drawable.sr_w_door_open);
                        } else if (values[0].contains(getString(R.string.statusUpdateDoor2Closed))) {
                            remote.wDoorStatusRing.setBackgroundResource(R.drawable.sr_w_door_close);
                        } else {
                            remote.wDoorStatusRing.setBackgroundResource(R.drawable.sr_w_door_unknown);
                        }
                        //-------- Status for gates-------------------------------------
                        if (values[0].contains(getString(R.string.statusUpdateGateOpen))) {
                            remote.gateStatusRing.setBackgroundResource(R.drawable.sr_gate_op);
                        } else if (values[0].contains(getString(R.string.statusUpdateGtaeClosed))) {
                            remote.gateStatusRing.setBackgroundResource(R.drawable.sr_gate_cl);
                        } else {
                            remote.gateStatusRing.setBackgroundResource(R.drawable.sr_gate_unknown);
                        }

                    }

                }

                mainLO.setBackgroundResource(R.drawable.light);
                //enableTouch();
                asked4update = false;
                if (curFrag == Constants.Pages.REMOTEPAGE) {
                    FragmentManager fm = getSupportFragmentManager();
                    remote remoteFrag;
                    remoteFrag = (remote) fm.findFragmentById(R.id.detailFragment);
                    remoteFrag.resetStatusHandler.removeCallbacks(remoteFrag.restStatusRunnable);
                    remoteFrag.resetStatusRing();
                    // Log.e(getString(R.string.TagRefresh), getString(R.string.PressedButtonIsEqualTo) + pressedButton);
                } else if (curFrag == Constants.Pages.USERSPAGE) {
                    FragmentManager fm = getSupportFragmentManager();
                    users user;
                    user = (users) fm.findFragmentById(R.id.detailFragment);
                    //Todo get this updated
                    user.resetStatusHandler.removeCallbacks(user.restStatusRunnable);
                    // user.resetStatusHandler.removeCallbacks(user.restStatusRunnable);
                    //Log.e(getString(R.string.TagRefresh), getString(R.string.PressedButtonIsEqualTo) + pressedButton);
                } else if (curFrag == Constants.Pages.TIMERSPAGE) {
                    FragmentManager fm = getSupportFragmentManager();
                    timers user;
                    user = (timers) fm.findFragmentById(R.id.detailFragment);

                    user.resetStatusHandler.removeCallbacks(user.restStatusRunnable);
                    // user.resetStatusHandler.removeCallbacks(user.restStatusRunnable);
                    //Log.e(getString(R.string.TagRefresh), getString(R.string.PressedButtonIsEqualTo) + pressedButton);
                } else if (curFrag == Constants.Pages.AREASPAGE) {
                    FragmentManager fm = getSupportFragmentManager();
                    areas user;
                    user = (areas) fm.findFragmentById(R.id.detailFragment);

                    user.resetStatusHandler.removeCallbacks(user.restStatusRunnable);
                    // user.resetStatusHandler.removeCallbacks(user.restStatusRunnable);
                    // Log.e(getString(R.string.TagRefresh), getString(R.string.PressedButtonIsEqualTo) + pressedButton);
                }

            }

        }

        private void areas_func(String value) {


        }
        private void setArea(ImageButton icon, Button textButton, String Name, int position, String zone, String physicalType) {
            textButton.setText(Name);
            textButton.setVisibility(View.VISIBLE);
            icon.setVisibility(View.VISIBLE);
            sh_areas.zone4area[position] = zone;
            if (physicalType.startsWith("g"))
                icon.setImageResource(R.drawable.door2);
            else if (physicalType.startsWith("c"))
                icon.setImageResource(R.drawable.sh_car);
            else if (physicalType.startsWith("H"))
                icon.setImageResource(R.drawable.sh_common);
            else if (physicalType.startsWith("K"))
                icon.setImageResource(R.drawable.sh_kitchen);

            else if (physicalType.startsWith("B"))
                icon.setImageResource(R.drawable.sh_bedroom);

            else if (physicalType.startsWith("L"))
                icon.setImageResource(R.drawable.sh_lawn);

            else if (physicalType.startsWith("b"))
                icon.setImageResource(R.drawable.sh_bath_room);

            else if (physicalType.startsWith("O"))
                icon.setImageResource(R.drawable.sh_office);

            else if (physicalType.startsWith("S"))
                icon.setImageResource(R.drawable.sh_study_room);

            else if (physicalType.startsWith("s"))
                icon.setImageResource(R.drawable.sh_stair);

            else if (physicalType.startsWith("k"))
                icon.setImageResource(R.drawable.sh_store);
            else if (physicalType.startsWith("o"))
                icon.setImageResource(R.drawable.sh_other);
            else if (physicalType.startsWith("h"))
                icon.setImageResource(R.drawable.sh_security);
            else if (physicalType.startsWith("G"))
                icon.setImageResource(R.drawable.sh_guest_room);
            else if (physicalType.startsWith("c"))
                icon.setImageResource(R.drawable.sh_common);



        }
        private void zoneNames_func(String value) {
            if (value.startsWith(getString(R.string.zones))) {

                handler.removeCallbacks(wait4TO);

                value = value.replaceAll("!!", getString(R.string.emptyString));
                String[] parts = value.split("/");

                if (parts.length > 1 && parts[1].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.check1.setText("1- " + parts[1]);
                    else areas.bZone1.setText("1- " + parts[1]);
                }
                if (parts.length > 2 && parts[2].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.check2.setText("2- " + parts[2]);
                    else areas.bZone2.setText("2- " + parts[2]);
                }
                if (parts.length > 3 && parts[3].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.check3.setText("3- " + parts[3]);
                    else areas.bZone3.setText("3- " + parts[3]);
                }
                if (parts.length > 4 && parts[4].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.check4.setText("4- " + parts[4]);
                    else areas.bZone4.setText("4- " + parts[4]);
                }
                if (parts.length > 5 && parts[5].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.check5.setText("5- " + parts[5]);
                    else areas.bZone5.setText("5- " + parts[5]);
                }
                if (parts.length > 6 && parts[6].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.check6.setText("6- " + parts[6]);
                    else areas.bZone6.setText("6- " + parts[6]);
                }
                if (parts.length > 7 && parts[7].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.check7.setText("7- " + parts[7]);
                    else areas.bZone7.setText("7- " + parts[7]);
                }
                if (parts.length > 8 && parts[8].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.check8.setText("8- " + parts[8]);
                    else areas.bZone8.setText("8- " + parts[8]);
                }
                if (parts.length > 9 && parts[9].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.check9.setText("9- " + parts[9]);
                    else areas.bZone9.setText("9- " + parts[9]);
                }
                if (parts.length > 10 && parts[10].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.checkA.setText("A- " + parts[10]);
                    else areas.bZoneA.setText("A- " + parts[10]);
                }
                if (parts.length > 11 && parts[11].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.checkB.setText("B- " + parts[11]);
                    else areas.bZoneB.setText("B- " + parts[11]);
                }
                if (parts.length > 12 && parts[12].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.checkC.setText("C- " + parts[12]);
                    else areas.bZoneC.setText("C- " + parts[12]);
                }
                if (parts.length > 13 && parts[13].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.checkD.setText("D- " + parts[13]);
                    else areas.bZoneD.setText("D- " + parts[13]);
                }
                if (parts.length > 14 && parts[14].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.checkE.setText("E- " + parts[14]);
                    else areas.bZoneE.setText("E- " + parts[14]);
                }
                if (parts.length > 15 && parts[15].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.checkF.setText("F- " + parts[15]);
                    else areas.bZoneF.setText("F- " + parts[15]);
                }
                if (parts.length > 16 && parts[16].length() > 1) {
                    if (curFrag == Constants.Pages.USERSPAGE)
                        users.checkG.setText("G- " + parts[16]);
                    else areas.bZoneG.setText("G- " + parts[16]);
                }
                mainLO.setBackgroundResource(R.drawable.light);
                enableTouch();
                if (curFrag == Constants.Pages.USERSPAGE) {
                    if (users.etNumber.isShown()) {
                        users.etNumber.setFocusableInTouchMode(false);
                        users.etNumber.setFocusable(false);
                        if (editing)
                            users.pressedButton = Constants.pBs.EDITUSER;
                        else
                            users.pressedButton = Constants.pBs.ADDMU;
                    }
                }
            }
        }

        private void GetTimer(String value) {
            handler.removeCallbacks(wait4TO);

            value = value.replaceAll("!!", getString(R.string.emptyString));
            String[] parts = value.split("/");

            if (parts.length > 1 && parts[1].length() > 1) {
                if (parts[1].equals(getString(R.string.timerEnabled))) {
                    // timers.bAuto.setBackgroundResource(R.drawable.but_auto);

                    timers.stateAuto = 1;
                    if (parts.length > 3 && parts[3].length() > 1) {
                        timers.bON.setText(getString(R.string.ONTTIMESTRING) + parts[3]);
                    }
                    if (parts.length > 4 && parts[4].length() > 1) {
                        timers.bOFF.setText(getString(R.string.OFFTIMESTR) + parts[4]);
                    }
                } else if (parts[1].equals(getString(R.string.timerDisabled))) {
                    //timers.bAuto.setBackgroundResource(R.drawable.but_ma);

                    timers.bON.setText(getString(R.string.set_on_time));
                    timers.bOFF.setText(getString(R.string.set_off_time));
                    timers.stateAuto = 0;
                }

            }
            if (parts.length > 2 && parts[2].length() > 1) {
                timers.etName.setText(parts[2]);
            }
            if (parts.length > 5 && parts[5].length() > 1) {
                if (parts[5].contains(getString(R.string.off))) {
                    timers.bForce.setChecked(true);
                    timers.statusImage.setBackgroundResource(R.drawable.red_status);
                    timers.stateForce = 0;
                } else if (parts[5].contains(getString(R.string.On))) {
                    timers.bForce.setChecked(false);
                    timers.statusImage.setBackgroundResource(R.drawable.green_status);
                    timers.stateForce = 1;
                } else if (parts[5].contains(getString(R.string.xx))) {
                    //timers.bForce.setBackgroundResource(R.drawable.sw_2_def);
                    timers.statusImage.setBackgroundResource(R.drawable.orange_status);
                }
            }

            mainLO.setBackgroundResource(R.drawable.light);
            enableTouch();

        }

        private void TimersNamesFunction(String values) {
            handler.removeCallbacks(wait4TO);

            values = values.replaceAll("!!", getString(R.string.emptyString));
            String[] parts = values.split("/");

            if (parts.length > 1 && parts[1].length() > 1) {

                timers.TimerNames[0] = parts[1];

            }
            if (parts.length > 2 && parts[2].length() > 1) {
                timers.TimerNames[1] = parts[2];
            }
            if (parts.length > 3 && parts[3].length() > 1) {
                timers.TimerNames[2] = parts[3];
            }
            if (parts.length > 4 && parts[4].length() > 1) {
                timers.TimerNames[3] = parts[4];
            }
            if (parts.length > 5 && parts[5].length() > 1) {
                timers.TimerNames[4] = parts[5];
            }
            if (parts.length > 6 && parts[6].length() > 1) {
                timers.TimerNames[5] = parts[6];
            }
            if (parts.length > 7 && parts[7].length() > 1) {
                timers.TimerNames[6] = parts[7];
            }
            if (parts.length > 8 && parts[8].length() > 1) {
                timers.TimerNames[7] = parts[8];
            }

            mainLO.setBackgroundResource(R.drawable.light);
            enableTouch();
        }
        @Override
        protected void onPostExecute(TCPClient tcpClient) {
            super.onPostExecute(tcpClient);
            if (returnedVal == 1) {
                showCroutonMessage(getString(R.string.ConnectionFailed), Constants.crs.ALERT_GL, Constants.crs.COUTION_MODE_DEFAULT);
            }
        }

    }








    private void sentSucc() {
        Log.e("Areas ", "SENT SUCCES");
        handler.removeCallbacks(wait4TO);
        if (curFrag == Constants.Pages.REMOTEPAGE) {
            mainLO.setBackgroundResource(R.drawable.light);
            // remote.mPullRefreshScrollView.onRefreshComplete();
            FragmentManager fm = getSupportFragmentManager();
            remote remoteFrag;
            remoteFrag = (remote) fm.findFragmentById(R.id.detailFragment);
            remoteFrag.sentSucc();
        } else if (curFrag == Constants.Pages.USERSPAGE) {
            mainLO.setBackgroundResource(R.drawable.light);
            // remote.mPullRefreshScrollView.onRefreshComplete();
            FragmentManager fm = getSupportFragmentManager();
            users userFrag;
            userFrag = (users) fm.findFragmentById(R.id.detailFragment);
            userFrag.sentSucc(sc);
        } else if (curFrag == Constants.Pages.TIMERSPAGE) {
            mainLO.setBackgroundResource(R.drawable.light);
            // remote.mPullRefreshScrollView.onRefreshComplete();
            FragmentManager fm = getSupportFragmentManager();
            timers userFrag;
            userFrag = (timers) fm.findFragmentById(R.id.detailFragment);
            userFrag.sentSucc(sc);
        } else if (curFrag == Constants.Pages.AREASPAGE) {
            mainLO.setBackgroundResource(R.drawable.light);
            // remote.mPullRefreshScrollView.onRefreshComplete();
            FragmentManager fm = getSupportFragmentManager();
            areas userFrag;
            userFrag = (areas) fm.findFragmentById(R.id.detailFragment);
            userFrag.sentSucc(sc);
        }
        //Todo:::

    }

    public TCPClient executeConnectTask() {
        new connectTask().execute(getString(R.string.emptyString));
        return mTcpClient;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTcpClient != null) {
            mTcpClient.stopClient();
        }
        Log.e("Connection", " Stopped client if running");
        Crouton.clearCroutonsForActivity(this);
    }

    protected void onResume() {
        super.onResume();

        mainLO.setBackgroundResource(R.drawable.light);
        enableTouch();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ReplaceFragments rp = new ReplaceFragments();
        if (!gettingContact) {
            if (sc.getAccountNumber() == 1) {
                setAccount(0);
                rp.replaceWithRemote(ft, fm, true);
            }
            if (sc.getAccountNumber() == 0) {
                rp.replaceWithAddAccount(ft, fm, true);
            }
            if (sc.getAccountNumber() > 1) {
                rp.replaceWithAL(ft, fm, true, false);
            }
        }
    }

    Handler handler = new Handler();

    public void wait4Timeout() {
        waiting4reply = true;
        sendingScreens();
        handler.removeCallbacks(wait4TO);
        handler.postDelayed(wait4TO, 5000);
    }

    Runnable wait4TO = new Runnable() {
        @Override
        public void run() {
            if (pressedButton == Constants.pBs.INACTVE || pressedButton == Constants.pBs.REFRESHPULL || pressedButton == Constants.pBs.ASKUPDATE
                    || pressedButton == Constants.pBs.TIMERNAMES || pressedButton == Constants.pBs.ZONENAMES
                    || pressedButton == Constants.pBs.GETTIMER) {//Todo asked for update
                handler.removeCallbacks(wait4TO);
            } else {
                if (!asked4update)
                    sentFailed();
                waiting4reply = false;
            }
        }
    };

    private void sentFailed() {
        // mainLO.setBackgroundResource(R.drawable.light);
        Log.e("Areas ", "SENT Failed");
        //Log.e("SENT FAILED", "Strated run" + curFrag);
        handler.removeCallbacks(wait4TO);
        FragmentManager fm = getSupportFragmentManager();
        if (curFrag == Constants.Pages.REMOTEPAGE) {
            remote remoteFrag;
            remoteFrag = (remote) fm.findFragmentById(R.id.detailFragment);
            remoteFrag.sentFailed();
        }
        if (curFrag == Constants.Pages.USERSPAGE) {
            users userFrag;
            userFrag = (users) fm.findFragmentById(R.id.detailFragment);
            userFrag.sentFailed();
        }
        if (curFrag == Constants.Pages.TIMERSPAGE) {
            timers userFrag;
            userFrag = (timers) fm.findFragmentById(R.id.detailFragment);
            userFrag.sentFailed();
        }
        if (curFrag == Constants.Pages.AREASPAGE) {
            areas userFrag;
            userFrag = (areas) fm.findFragmentById(R.id.detailFragment);
            userFrag.sentFailed();
        }
        //todo

    }

    private void sendingScreens() {

        if ((pressedButton == Constants.pBs.REFRESHPULL || asked4update || pressedButton == Constants.pBs.ZoneINFO
                || pressedButton == Constants.pBs.GETTIMER || pressedButton == Constants.pBs.GETTIMERUP || pressedButton == Constants.pBs.ZONENAMES
                || pressedButton == Constants.pBs.TIMERNAMES  || pressedButton==Constants.pBs.AREAS ))
            ;
        else {
            mainLO.setBackgroundResource(R.drawable.medium_dark);
            if (curFrag == Constants.Pages.REMOTEPAGE) {
                FragmentManager fm = getSupportFragmentManager();
                remote remoteFrag;
                remoteFrag = (remote) fm.findFragmentById(R.id.detailFragment);
                remoteFrag.sendingScreens(new SupportClass(this));
            }
            if (curFrag == Constants.Pages.USERSPAGE) {
                FragmentManager fm = getSupportFragmentManager();
                users userFrag;
                userFrag = (users) fm.findFragmentById(R.id.detailFragment);
                userFrag.sendingScreens(new SupportClass(this));
            }
            if (curFrag == Constants.Pages.TIMERSPAGE) {
                FragmentManager fm = getSupportFragmentManager();
                timers userFrag;
                userFrag = (timers) fm.findFragmentById(R.id.detailFragment);
                userFrag.sendingScreens(new SupportClass(this));
            }
            if (curFrag == Constants.Pages.AREASPAGE) {
                FragmentManager fm = getSupportFragmentManager();
                areas userFrag;
                userFrag = (areas) fm.findFragmentById(R.id.detailFragment);
                userFrag.sendingScreens(new SupportClass(this));
            }
            //todo
        }
    }

    public void showCroutonMessage(String Text, int style, boolean mode) {


        Crouton.clearCroutonsForActivity(this);
        Log.e("Crouton", " called");
        // Todo: Here i am forcing display on Top
        // Follow it

        Style croutonStyle = null;
        if (style == Constants.crs.ALERT_GL)// 0 == alert
            croutonStyle = ALERT_G_LEFT;//, R.id.alternate_view_group);

        else if (style == Constants.crs.ALERT_C)// 0 == alert
            croutonStyle = ALERT;//, R.id.alternate_view_group);

        else if (style == Constants.crs.INFO_GL)// 0 == alert
            croutonStyle = INFO_G_LEFT;//, R.id.alternate_view_group);

        else if (style == Constants.crs.NOPERMS)// 0 == alert
            croutonStyle = NoPerms;//, R.id.alternate_view_group);

        else if (style == Constants.crs.INFO_C)// 0 == alert
            croutonStyle = INFO;//, R.id.alternate_view_group);

        else if (style == Constants.crs.SUCC_GL)// 0 == alert
            croutonStyle = SUCC_G_LEFT;//, R.id.alternate_view_group);

        else if (style == Constants.crs.SUCC_C)
            croutonStyle = SUCC;//, R.id.alternate_view_group);


        crouton = Crouton.makeText(this, Text, croutonStyle);

        if (mode) infiniteCrouton = crouton;

        crouton.setOnClickListener(this).setConfiguration(mode ? CONFIGURATION_INFINITE : Configuration.DEFAULT).show();


    }

    @Override
    protected void onDestroy() {
        Crouton.clearCroutonsForActivity(this);
        super.onDestroy();
        unbindDrawables(findViewById(R.id.main_act_LLO));
        System.gc();
    }

    static void unbindDrawables(View view) {
        try {
            System.out.println("UNBINDING" + view);
            if (view.getBackground() != null) {

                ((BitmapDrawable) view.getBackground()).getBitmap().recycle();


                view.getBackground().setCallback(null);
                view = null;
            }

            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
                ((ViewGroup) view).removeAllViews();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}