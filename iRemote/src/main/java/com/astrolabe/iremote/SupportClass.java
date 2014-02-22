package com.astrolabe.iremote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SupportClass {

    String loadNumber = "tobeloaded";

    private final Context ourContext;
    // private final EditText ourTextView;
    //private String SelNumber;

    public SupportClass(Context ourContext) {
        this.ourContext = ourContext;
    }


    public String getMeMyNumber(String number) {
        String countryCode = GetCountryZipCode();
        String countryCode_withoutPlus = countryCode.substring(1);
        Log.e("Country code", "without " + countryCode_withoutPlus + "\nand with " + countryCode);
        if (number.startsWith(countryCode_withoutPlus)) {
            number = number.substring(countryCode_withoutPlus.length());
            Log.e("Country code", "after removing " + number);
        }
        return number.replaceAll("[^0-9\\+]", "")        //remove all the non numbers (brackets dashes spaces etc.) except the + signs
                .replaceAll("(^[1-9].+)", countryCode + "$1")         //if the number is starting with no zero and +, its a local number. prepend cc
                .replaceAll("(.)(\\++)(.)", "$1$3")         //if there are left out +'s in the middle by mistake, remove them
                .replaceAll("(^0{2}|^\\+)(.+)", "+" + "$2")       //make 00XXX... numbers and +XXXXX.. numbers into XXXX...
                .replaceAll("^countryCode_withoutPlus", "")
                .replaceAll("^0([1-9])", countryCode + "$1");         //make 0XXXXXXX numbers into CCXXXXXXXX numbers
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model.toUpperCase();
        } else {
            return manufacturer.toUpperCase() + " " + model;
        }
    }

    public String getAndroidVersion() {
        Context context = ourContext.getApplicationContext(); // or activity.getApplicationContext()
        assert context != null;
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        String myVersionName = "not available"; // initialize String

        try {
            assert packageManager != null;
            myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return myVersionName;

    }

    public String GetCountryZipCode() {

        String CountryID;
        String CountryZipCode = "";

        TelephonyManager manager = (TelephonyManager) ourContext.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID = manager.getSimCountryIso().toUpperCase();
        if (CountryID.equals("")) {
            main mAct = new main();
            mAct.showCroutonMessage(ourContext.getString(R.string.country_code_failed), Constants.crs.ALERT_C, Constants.crs.COUTION_MODE_INFINTE);
            return "";
        } else {
            String[] rl = ourContext.getResources().getStringArray(R.array.CountryCodes);
            for (String aRl : rl) {
                String[] g = aRl.split(",");
                if (g[1].trim().equals(CountryID.trim())) {
                    CountryZipCode = g[0];
                    break;
                }
            }
        }
        return "+" + CountryZipCode;
    }


   /* public void showDialog(String str) {

        Toast.makeText(ourContext, str, Toast.LENGTH_LONG).show();

    }*/

    /* public void setUpQA(Drawable icon1, Drawable icon2, Drawable icon3) {
         QuickActionPopup quickActionPopup1 = new QuickActionPopup(ourContext, QuickActionPopup.HORIZONTAL);
         QuickActionItem option1 = new QuickActionItem(1, icon1);
         QuickActionItem option2 = new QuickActionItem(2, icon2);
         QuickActionItem option3 = new QuickActionItem(3, icon3);
         QuickActionItem option4 = new QuickActionItem(4, ourContext.getResources().getDrawable(R.drawable.quit_pu));
         //create QuickActionPopup. Use QuickActionPopup.VERTICAL or QuickActionPopup.HORIZONTAL //param to define orientation
         //add action items into QuickActionPopup
         quickActionPopup1.addActionItem(option1);
         quickActionPopup1.addActionItem(option2);
         quickActionPopup1.addActionItem(option3);
         quickActionPopup1.addActionItem(option4);
     }
    */
    public int getControlPanelType(long Account) {
        accountsDB entry = new accountsDB(ourContext);
        entry.Open();
        String AccountType = entry.getCType(Account);
        entry.close();
        if (AccountType.equals(ourContext.getString(R.string.shaula720)))
            return 2;
        if (AccountType.equals(ourContext.getString(R.string.shaula710)))
            return 1;
        else if (AccountType.equals(ourContext.getString(R.string.shaulaV9)))
            return 9;
        else return 1;
    }

    public String getControlName(long Account) {
        accountsDB entry = new accountsDB(ourContext);
        entry.Open();
        String AccountName = entry.getName(Account);
        entry.close();
        return AccountName;

    }

    public String getSite(long Account) {
        accountsDB entry = new accountsDB(ourContext);
        entry.Open();
        String AccountName = entry.getSite(Account);
        entry.close();
        return AccountName;

    }

    public String getUser(long Account) {
        accountsDB entry = new accountsDB(ourContext);
        entry.Open();
        String AccountName = entry.getUserName(Account);
        entry.close();
        return AccountName;

    }

    public String getPass(long Account) {
        accountsDB entry = new accountsDB(ourContext);
        entry.Open();
        String AccountName = entry.getPass(Account);
        entry.close();
        return AccountName;

    }

    public long getAccountNumber() {
        accountsDB entry = new accountsDB(ourContext);
        entry.Open();
        return entry.getProfilesCount();
        //return Accs;
    }


    public String retrieveContactName(Uri uriContact) {


        String contactName = null;
        Log.e("TEST", "Response: SET name called");
        // querying contact data store
        Cursor cursor = ourContext.getContentResolver().query(uriContact, null, null, null, null);

        assert cursor != null;
        // DISPLAY_NAME = The display name for the contact.
        // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.
        if (cursor.moveToFirst())
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        cursor.close();
        Log.e("TEST", "Response:  name rec" + contactName);

        if (contactName != null) {
            return contactName.toUpperCase();
        }
        return "";
    }

    public void retrieveContactNumber(final Uri uriContact, int k) {
        String id = uriContact.getLastPathSegment();
        Cursor c = ourContext.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                new String[]{id}, null);
        final int finalI = k;
        if (finalI == 1) {
            addEditAccount nw = new addEditAccount();
            nw.setNumber("");
        } else {
            users us = new users();
            us.setNumber("");
        }
        int phoneIdx = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int phoneType = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);

        if (c.getCount() > 0) { // contact has multiple phone numbers
            final CharSequence[] numbers = new CharSequence[c.getCount()];
            int i = 0;
            if (c.moveToFirst()) {
                while (!c.isAfterLast()) { // for each phone number, add it to the numbers array
                    String type = (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(ourContext.getResources(), c.getInt(phoneType), ""); // insert a type string in front of the number
                    String number = type + ": " + c.getString(phoneIdx);
                    numbers[i++] = number;
                    c.moveToNext();
                }
                // build and show a simple dialog that allows the user to select a number
                AlertDialog.Builder builder = new AlertDialog.Builder(ourContext);
                builder.setTitle(R.string.select_contact_phone_number_and_type);

                builder.setItems(numbers, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        String number = (String) numbers[item];
                        int index = number.indexOf(":");
                        number = number.substring(index + 2);
                        if (finalI == 1) {
                            addEditAccount nw = new addEditAccount();
                            nw.setNumber(getMeMyNumber(number));
                        }
                        //Todo: else Users.tvSetText(getMeMyNumber(number)); Todo
                        else {
                            users us = new users();
                            us.setNumber(getMeMyNumber(number));
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.setOwnerActivity((Activity) ourContext);
                alert.show();

            } //else showDialog("No results");
        } else if (c.getCount() == 0) {
            // contact has a single phone number, so there's no need to display a second dialog
            Log.e("TEST", "Response:  Contact has no phone number detail");

//            .showCroutonMessage("Contact has no phone number detail",Constants.crs.ALERT_C,Constants.crs.COUTION_MODE_DEFAULT);
        }
        c.close();

    }




}
