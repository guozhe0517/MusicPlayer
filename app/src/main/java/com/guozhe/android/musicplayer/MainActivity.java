package com.guozhe.android.musicplayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements ListFragment.OnListFragmentInteractionListener
        , PremissionControl.CallBack{

    FrameLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PremissionControl.checkVersion(this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PremissionControl.onResult(this,requestCode,grantResults);
    }
   @Override
    public void init(){
        setView();
        setFragment(ListFragment.newInstance(1));
    }
    private void setView(){
        layout = (FrameLayout)findViewById(R.id.layout);
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.layout,fragment);
        transaction.commit();
    }
    private void addFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.layout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void goDetailInteraction(int position) {

        addFragment(DetailFragment.newInstance(position));
    }
}
