package com.myapplication.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

//copy from tutorial
import io.onthego.ari.KeyDecodingException;
import io.onthego.ari.android.ActiveAri;
import io.onthego.ari.android.Ari;
import io.onthego.ari.event.ClosedHandEvent;
import io.onthego.ari.event.HandEvent;
import io.onthego.ari.event.LeftSwipeEvent;
import io.onthego.ari.event.OpenHandEvent;
import io.onthego.ari.event.RightSwipeEvent;

//added by David Ho
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.TextView;

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
    static final int REQUEST_IMAGE_CAPTURE = 1;






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


        TextView test = (TextView) findViewById(R.id.testing123);
        test.setText("hand closed");
    }

    @Override
    //copy from tutorial
    public void onOpenHandEvent(OpenHandEvent event) {
        Log.i(TAG, "Got open hand");

        TextView test = (TextView) findViewById(R.id.testing123);
        test.setText("open hand");
    }

    @Override
    //copy from tutorial
    public void onLeftSwipeEvent(LeftSwipeEvent event) {
        Log.i(TAG, "Got hand swipe: " + event);

        TextView test = (TextView) findViewById(R.id.testing123);
        test.setText("Got hand swipe left");
    }

    @Override
    //copy from tutorial
    public void onRightSwipeEvent(RightSwipeEvent event) {
        Log.i(TAG, "Got hand swipe: " + event);

        TextView test = (TextView) findViewById(R.id.testing123);
        test.setText("Got hand swipe right");
    }

}

class Preview extends ViewGroup implements SurfaceHolder.Callback {

    SurfaceView mSurfaceView;
    SurfaceHolder mHolder;

    Preview(Context context) {
        super(context);

        mSurfaceView = new SurfaceView(context);
        addView(mSurfaceView);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void onLayout(boolean b, int i, int i2, int i3, int i4) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

}



