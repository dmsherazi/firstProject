package com.astrolabe.iremote;

/**
 * Created by SheraXiii on 12/24/13.
 */
public class Constants {


    public static final int C_DEFAULT = 0;


    public static int QUITAPP = 8;

    public static class Pages {
        //=========================Pages=====================
        public static int ADDEDITSCREEN = 0;
        public static int ACCLISTS = 1;
        public static int REMOTEPAGE = 2;
        public static int TIMERSPAGE = 3;
        public static int AREASPAGE = 4;
        public static int USERSPAGE = 5;
        public static int HELPPAGE = 6;
        public static int ABOUTPAGE = 7;
        public static final int METERSPAGE = 9;
        public static final int SMARTHOME = 10;
        public static final int ADDACCOUNT = 9;
    }

    public static class pBs {
        public static final int ASKUPDATE = 11;
        //=========================Timers=====================
        public static final int GETTIMER = 40;
        public static final int EDITTIMER = 41;
        public static final int TIMERNAMES = 42;
        public static final int GETTIMERUP = 43;

        //=========================Remote=====================
        public static final int DISARM = 1;
        public static final int ARMA = 2;
        public static final int ARMAS = 3;
        public static final int GATE = 4;
        public static final int REFRESH = 5;
        public static final int DOOR = 6;
        public static final int ARMSS = 7;
        public static final int ARMS = 8;
        public static final int CHECKCONNECTION = 9;
        public static final int REFRESHPULL = 10;
        public static final int INACTVE = 0;
        //=========================Croutons=====================

        //=========================Users=====================
        public static final int ADDMU = 11;
        public static final int ADDNU = 12;
        public static final int DELMU = 13;
        public static final int DELNU = 14;
        public static final int LISTS = 15;
        public static final int EDITUSER = 16;
        public static final int USERINFO = 17;
        public static final int CHPASS = 18;
        public static int ADDMU2 = 19;
        public static final int U_TIMERS = 110;
        public static final int U_ZONES = 101;
        public static final int U_METERS = 102;
        //=========================Zones=====================
        public static final int ZoneUPATE = 31;
        public static int ZoneINFO = 32;
        public static final int ZONENAMES = 33;
        public static int AREAS = 50;
    }


    public static class crs {
        public static final boolean COUTION_MODE_DEFAULT = false;
        public static final boolean COUTION_MODE_INFINTE = true;
        public static final int ALERT_GL = 0;
        public static final int ALERT_C = 1;
        public static final int INFO_GL = 2;
        public static final int INFO_C = 3;
        public static final int SUCC_GL = 4;
        public static final int SUCC_C = 5;
        public static final int NOPERMS = 6;
    }
}
