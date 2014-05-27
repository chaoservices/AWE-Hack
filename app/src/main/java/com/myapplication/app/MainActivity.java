package com.myapplication.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    //taking photos variable
    static final int REQUEST_IMAGE_CAPTURE = 1;

    //Saving photo information variable
    String mCurrentPhotoPath;

    //creating the photo
    static final int REQUEST_TAKE_PHOTO = 1;






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


        dispatchTakePictureIntent();






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

    //saving photo file information function
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }



    //save the photo
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                //...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //add photo to the gallery
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}
