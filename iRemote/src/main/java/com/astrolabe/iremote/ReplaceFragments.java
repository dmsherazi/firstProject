package com.astrolabe.iremote;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ReplaceFragments {

    public void replaceWithAddEditAccount(FragmentTransaction ft, FragmentManager fm, boolean kill) {

        main.pressedButton = 0;
        kill = false;
        ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        addEditAccount firstFragment = new addEditAccount();
        ft.replace(R.id.detailFragment, firstFragment, "detailFragment");
        // if (kill) fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //    else ft.addToBackStack(null);
        ft.commit();
    }

    public void replaceWithAL(FragmentTransaction ft, FragmentManager fm, boolean kill, boolean anim) {

        main.pressedButton = 0;
        kill = false;
        if (anim)
            ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        accountLists firstFragment = new accountLists();
        ft.replace(R.id.detailFragment, firstFragment, "detailFragment");
        // if (kill) fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //    else ft.addToBackStack(null);
        ft.commit();
    }

    public void replaceWithRemote(FragmentTransaction ft, FragmentManager fm, boolean kill) {

        main.pressedButton = 0;
        kill = false;
        ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        remote firstFragment = new remote();
        ft.replace(R.id.detailFragment, firstFragment, "detailFragment");
        // if (kill) fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //    else ft.addToBackStack(null);
        ft.commit();
    }

    public void replaceWithUsers(FragmentTransaction ft, FragmentManager fm, boolean kill) {

        main.pressedButton = 0;
        kill = false;
        ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        users firstFragment = new users();
        ft.replace(R.id.detailFragment, firstFragment, "detailFragment");
        // if (kill) fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //    else ft.addToBackStack(null);
        ft.commit();
    }

    public void replaceWithAboutApp(FragmentTransaction ft, FragmentManager fm, boolean kill) {

        main.pressedButton = 0;
        kill = false;
        ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        aboutApp firstFragment = new aboutApp();
        ft.replace(R.id.detailFragment, firstFragment, "detailFragment");
        // if (kill) fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //    else ft.addToBackStack(null);
        ft.commit();
    }

    public void replaceWithAddAccount(FragmentTransaction ft, FragmentManager fm, boolean kill) {
        main.pressedButton = 0;
        kill = false;
        ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        addAccount firstFragment = new addAccount();
        ft.replace(R.id.detailFragment, firstFragment, "detailFragment");
        // if (kill) fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //    else ft.addToBackStack(null);
        ft.commit();
    }

    public void replaceWithTimers(FragmentTransaction ft, FragmentManager fm, boolean kill) {
        main.pressedButton = 0;
        kill = false;
        // ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        timers firstFragment = new timers();
        ft.replace(R.id.detailFragment, firstFragment, "detailFragment");
        // if (kill) fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //    else ft.addToBackStack(null);
        ft.commit();
    }

    public void replaceWithMeters(FragmentTransaction ft, FragmentManager fm, boolean kill) {
        main.pressedButton = 0;
        kill = false;
        // ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        meters firstFragment = new meters();
        ft.replace(R.id.detailFragment, firstFragment, "detailFragment");
        // if (kill) fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //    else ft.addToBackStack(null);
        ft.commit();
    }


    public void replaceWithAreas(FragmentTransaction ft, FragmentManager fm, boolean kill) {
        main.pressedButton = 0;
        kill = false;
        kill = false;
        //ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        areas firstFragment = new areas();
        ft.replace(R.id.detailFragment, firstFragment, "detailFragment");
        // if (kill) fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //    else ft.addToBackStack(null);
        ft.commit();
    }

    public void replaceWithSHAreas(FragmentTransaction ft, FragmentManager fm, boolean kill) {
        main.pressedButton = 0;
        kill = false;
        kill = false;
        //ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        sh_areas firstFragment = new sh_areas();
        ft.replace(R.id.detailFragment, firstFragment, "detailFragment");
        // if (kill) fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //    else ft.addToBackStack(null);
        ft.commit();
    }

    public void replaceWithHelp(FragmentTransaction ft, FragmentManager fm, boolean kill) {
        main.pressedButton = 0;
        kill = false;
        kill = false;
        ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        HelpPage firstFragment = new HelpPage();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit);//, R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(R.id.detailFragment, firstFragment, "detailFragment");
        // if (kill) fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //    else ft.addToBackStack(null);
        ft.commit();
    }


}
