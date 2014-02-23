package com.astrolabe.iremote;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Abu-Umar on 12/22/13.
 * fragment
 */
public class addEditAccount extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, View.OnTouchListener {

    //QuickActionPopup quickActionPopup1;
    FrameLayout flName;
    FrameLayout flSite;
    FrameLayout flUName;
    FrameLayout flPass;
    FrameLayout cTHead;

    static EditText tvSite;
    EditText tvUN, tvPass, tvName;
    RadioGroup rCType;
    TextView tvCT;
    Button importContact;
    ImageButton done;


    private static final int REQUEST_CODE_PICK_CONTACTS = 11;
    RelativeLayout mainRL, rlActivity;
    long Account;
    main mActivity;

    public addEditAccount() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_edit_account, container, false);

        assert view != null;

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //Account = 25; //todo: get extra or from the main class
        //

        mActivity = (main) getActivity();
        mActivity.setCurrFrag(Constants.Pages.ADDEDITSCREEN);
        Account = mActivity.getAccount();

        mainRL = (RelativeLayout) view.findViewById(R.id.RL_AS);
        rlActivity = (RelativeLayout) view.findViewById(R.id.visInviz);


        rCType = (RadioGroup) view.findViewById(R.id.ControlTYpe);
        cTHead = (FrameLayout) view.findViewById(R.id.FLCTH);
        done = (ImageButton) view.findViewById(R.id.Done);

        importContact = (Button) view.findViewById(R.id.importContacts);
        tvCT = (TextView) view.findViewById(R.id.as_tv_ct);
        tvName = (EditText) view.findViewById(R.id.etName);
        tvPass = (EditText) view.findViewById(R.id.etPass);
        tvSite = (EditText) view.findViewById(R.id.etSite);
        tvUN = (EditText) view.findViewById(R.id.etUserName);
        flName = (FrameLayout) view.findViewById(R.id.FLName);
        flSite = (FrameLayout) view.findViewById(R.id.FLSiteNumber);
        flUName = (FrameLayout) view.findViewById(R.id.FLUsername);
        flPass = (FrameLayout) view.findViewById(R.id.FLPass);

        mActivity.cancelAllCroutons();
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        //ft1.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        menuFrag firstFragment1 = new menuFrag();
        ft1.replace(R.id.menuFragment, firstFragment1, "menuFragment");
        ft1.commit();


        tvCT.setHint(Html.fromHtml(getActivity().getString(R.string.hint_ct)));
        tvName.setHint(Html.fromHtml(getActivity().getString(R.string.hint_name)));
        tvSite.setHint(Html.fromHtml(getActivity().getString(R.string.hint_site)));
        tvPass.setHint(Html.fromHtml(getActivity().getString(R.string.hint_pas)));
        tvUN.setHint(Html.fromHtml(getActivity().getString(R.string.hint_un)));
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Arial_Black.ttf");
        tvCT.setTypeface(tf);

        tvName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        tvUN.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        tvSite.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        tvPass.setImeOptions(EditorInfo.IME_ACTION_DONE);


        tvName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        tvSite.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        tvUN.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        tvPass.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        tvPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        SupportClass sc1 = new SupportClass(getActivity());

        rlActivity.setVisibility(View.INVISIBLE);
        importContact.setOnClickListener(this);
        rCType.setOnCheckedChangeListener(this);
        cTHead.setOnClickListener(this);
        tvCT.setOnClickListener(this);
        mainRL.setOnTouchListener(this);
        done.setOnClickListener(this);
        if (Account == 25) done.setBackgroundResource(R.drawable.done_button);
        else {

            accountsDB db = new accountsDB(getActivity());
            db.Open();
            int AcType = sc1.getControlPanelType(Account);
            if (AcType == 2) SetCType720();


            done.setBackgroundResource(R.drawable.update_button);

            tvName.setText(db.getName(Account));
            tvSite.setText(db.getSite(Account));
            tvUN.setText(db.getUserName(Account));
            tvPass.setText(db.getPass(Account));
            if (db.getName(Account).equals(getString(R.string.demoAccount))) {
                SetCTypeDemo();
            }
            db.close();

            // if (AcType == 2) SetCType720();
            // else if (AcType == 1)   SetCType710();
            // else if (AcType == 9)   SetCTypeV9();
        }
        if (sc1.getAccountNumber() > 7) {
            mActivity.showCroutonMessage(getString(R.string.List_is_full), Constants.crs.INFO_C, Constants.crs.COUTION_MODE_DEFAULT);

            accountLists firstFragment = new accountLists();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.detailFragment, firstFragment, "accountsList");
            ft.commit();

        }

        return view;
    }

    public void setName(String text) {
        tvName.setText(text);
    }

    public void setNumber(String text) {
        tvSite.setText(text);
    }

    private void SetCType720() {
        tvCT.setText(getString(R.string.shaula720));
        tvCT.setTextColor(0xFF8d7f99);
        rCType.setVisibility(View.INVISIBLE);
        rlActivity.setVisibility(View.VISIBLE);
        done.setVisibility(View.VISIBLE);
        tvPass.setImeOptions(EditorInfo.IME_ACTION_DONE);
        tvSite.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        tvSite.setEnabled(true);
        tvPass.setEnabled(true);
        tvUN.setEnabled(true);
    }

    private void SetCTypeDemo() {
        tvCT.setText(getString(R.string.demoAccount));
        tvCT.setTextColor(0xFF8d7f99);
        rCType.setVisibility(View.INVISIBLE);
        rlActivity.setVisibility(View.VISIBLE);
        done.setVisibility(View.VISIBLE);
        tvPass.setImeOptions(EditorInfo.IME_ACTION_DONE);
        tvSite.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        tvName.setText("ONLINE DEMO");
        tvSite.setText("demo account");
        tvUN.setText("demo");
        tvPass.setText("demo");
        tvSite.setEnabled(false);
        tvPass.setEnabled(false);
        tvUN.setEnabled(false);
    }


    @Override
    public void onClick(View v) {
        SupportClass sc = new SupportClass(getActivity());
        if ((v == cTHead) || (v == tvCT)) {
            if (!rCType.isShown()) {
                rCType.clearCheck();
                //done.setVisibility(View.INVISIBLE);
                rCType.setVisibility(View.VISIBLE);
                rlActivity.setVisibility(View.INVISIBLE);
                tvCT.setText("");
            } else showSelectCPT();
        }

        if (v == importContact) {

            getActivity().startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            Log.e("TEST", "Response: impot called " + REQUEST_CODE_PICK_CONTACTS);
            main mAct = (main) getActivity();
            mAct.gettingContact = true;


        }

        if (v == done) {

            String cType = tvCT.getText().toString();
            String siteName = tvName.getText().toString();
            String siteNum = tvSite.getText().toString();
            String username = tvUN.getText().toString();
            String pass = tvPass.getText().toString();

            String textError = null;
            if ((tvName.getText().toString().length() < 1) && (tvSite.getText().toString().length() < 6)) {
                textError = getString(R.string.site_name_and_number_cant_be);
                tvName.requestFocus();
                mActivity.showCroutonMessage(textError, Constants.crs.ALERT_GL, Constants.crs.COUTION_MODE_DEFAULT);
                //tvName.requestFocus();
            } else if ((tvName.getText().toString().length() < 1)) {
                textError = getString(R.string.name_cant_be_empty);
                tvName.requestFocus();
                mActivity.showCroutonMessage(textError, Constants.crs.ALERT_GL, Constants.crs.COUTION_MODE_DEFAULT);
                //tvName.requestFocus();
            } else if ((tvUN.getText().toString().length() < 1)) {
                textError = getString(R.string.username_cant_be_empty);
                tvName.requestFocus();
                mActivity.showCroutonMessage(textError, Constants.crs.ALERT_GL, Constants.crs.COUTION_MODE_DEFAULT);
                //tvName.requestFocus();
            } else if ((tvPass.getText().toString().length() < 1)) {
                textError = getString(R.string.pass_cant_be_empty);
                tvName.requestFocus();
                mActivity.showCroutonMessage(textError, Constants.crs.ALERT_GL, Constants.crs.COUTION_MODE_DEFAULT);
                //tvName.requestFocus();
            } else if ((tvSite.getText().toString().length() < 6)) {
                textError = getString(R.string.number_cant_be_less_than);
                tvSite.requestFocus();
                mActivity.showCroutonMessage(textError, Constants.crs.ALERT_GL, Constants.crs.COUTION_MODE_DEFAULT);
                //tvSite.requestFocus();
            } else {
                accountsDB acDb;
                acDb = new accountsDB(getActivity());
                acDb.Open();

                if (Account != 25) {
                    if (tvCT.getText().equals(getString(R.string.demoAccount))) {

                        mActivity.showCroutonMessage("Demo Account can't be modified", Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_DEFAULT);
                    } else {
                        Log.e("TEST", "Response: SET updatingggggg " + Account + "-" + cType + "-" + siteName + "-" + siteNum + "-" + username + "-" + pass);
                        acDb.updateEntry(Account + 1, cType, siteName, siteNum, username, pass);
                    }

                } else {
                    if (tvCT.getText().equals(getString(R.string.demoAccount))) {
                        acDb.createEntry(getString(R.string.shaula720), siteName, "95220407", "96897775603", "123456");
                    } else {
                        acDb.createEntry(cType, siteName, siteNum, username, pass);
                        Log.e("TEST", "Response: created " + Account + "-" + cType + "-" + siteName + "-" + siteNum + "-" + username + "-" + pass);
                    }
                }

                acDb.close();


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ReplaceFragments rp = new ReplaceFragments();
                if (sc.getAccountNumber() > 1)
                    rp.replaceWithAL(ft, fm, true, false);
                else {
                    main.setAccount(0);
                    rp.replaceWithRemote(ft, fm, true);
                }

            }
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.S720) {
            SetCType720();
        }
        if (checkedId == R.id.S710) {
            SetCTypeDemo();
        }
    }

    private void showSelectCPT() {
        tvCT.setText("");
        rCType.clearCheck();
        rCType.setVisibility(View.INVISIBLE);
        rlActivity.setVisibility(View.INVISIBLE);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (rCType.isShown()) showSelectCPT();
        return false;
    }

}
