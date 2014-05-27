package com.example.awe_hack.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


//copy from tutorial


//added by David Ho
import android.util.Log;

public class MainActivity extends Activity
        implements Ari.StartCallback,
        Ari.ErrorCallback,
        ClosedHandEvent.Listener,
        LeftSwipeEvent.Listener,
        OpenHandEvent.Listener,
        RightSwipeEvent.Listener {
    //copy tutorial
    private ActiveAri mAri;
    //googled it
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //copy from tutorial
        try {
            mAri = Ari.getActiveInstance(
                    getString(R.string.ari_api_key),
                    getApplicationContext(),
                    this)
                    .addErrorCallback(this);
        } catch (KeyDecodingException e) {
            Log.e(TAG, "Failed to init Ari: " + e);
            finish();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    //copy from tutorial
    protected void onResume() {
        super.onResume();
        //...
        mAri.start(this);
    }

    @Override
    //copy from tutorial
    protected void onPause() {
        super.onPause();
        //...
        if (mAri != null) {
            mAri.stop();
        }
    }

    @Override
    //copy from tutorial
    public void onAriStart() {
        // Optionally, disable sign detection for faster swipe
        // detection. This will throw an exception if your API
        // key isn't for a Pro or Enterprise license.
        //mAri.disable(HandEvent.Type.CLOSED_HAND, HandEvent.Type.OPEN_HAND);

        // application-specific code, if any
    }

    @Override
    //copy from tutorial
    public void onAriError(Exception exception) {
        Log.e(TAG, "Ari had an error: " + exception);
        finish();
    }

    @Override
    //copy from tutorial
    public void onClosedHandEvent(ClosedHandEvent event) {
        Log.i(TAG, "Got closed hand");
    }

    @Override
    //copy from tutorial
    public void onOpenHandEvent(OpenHandEvent event) {
        Log.i(TAG, "Got open hand");
    }

    @Override
    //copy from tutorial
    public void onLeftSwipeEvent(LeftSwipeEvent event) {
        Log.i(TAG, "Got hand swipe: " + event);
    }

    @Override
    //copy from tutorialyt  2
    public void onRightSwipeEvent(RightSwipeEvent event) {
        Log.i(TAG, "Got hand swipe: " + event);
    }

}
