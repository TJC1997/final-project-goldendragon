package com.example.getsumgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


/*
 * TODO
 *  [x] Get the values associated with the Game
 *     [x] Game ID
 *     [x] Game Title
 *  [ ] Get the values from the Stream
 *     [ ] Get available streams
 *     [ ] Choose a stream
 *     [ ] Get Streamer Name
 *     [ ] Get Preview
 *     [ ] Get Link to stream
 *     [ ] Get Hours streamed
 *  [ ] Get the Values from ChickenCoup
 *     [ ] Game Rating
 *     [ ] Store in an object in case someone wants to use review too
 *  [ ] Update UI
 *     [ ] Update Streamer Texts with Info
 *     [ ] Politely exit if ID does not makes sense
 *     [ ] Populate Game Rating from Chicken Coup
 *  [ ] Handle Intent
 *     [ ] Handle click on image
 *     [ ] Create intent to Twitch
 */
public class DetailActivity extends AppCompatActivity {

    // Strings for identification of intent extras
    public static final String EXTRA_GAME_ID = "game_id";
    public static final String EXTRA_GAME_NAME = "game_name";
    public static final String EXTRA_ERROR_CODE = "detail_error";

    // Error codes for failure within launching detail
    public static int ERROR_CODE_OK = 0;
    public static int ERROR_CODE_BAD = 1;

    // Tags for logging
    private static final String TAG = DetailActivity.class.getName();

    // UI Manipulation Fields
    private ImageView streamPreview = null;

    // Intent Data Fields
    private String gameId = null;
    private String gameName = null;

    public static boolean isGoodIntent(Intent intent){
        if(!intent.hasExtra(EXTRA_GAME_ID) || intent.getStringExtra(EXTRA_GAME_ID) == null){
            return false;
        }

        if(!intent.hasExtra(EXTRA_GAME_NAME) || intent.getStringExtra(EXTRA_GAME_NAME) == null){
            return false;
        }

        return true;
    }

    public static void setErrorCode(Intent intent, int errorCode){
        if(!intent.hasExtra(EXTRA_ERROR_CODE)) {
            intent.putExtra(EXTRA_ERROR_CODE, errorCode);
        }else{
            intent.removeExtra(EXTRA_ERROR_CODE);
            intent.putExtra(EXTRA_ERROR_CODE, errorCode);
        }
    }

    public void setStreamPreview(Drawable image){
        if (streamPreview != null) {
            this.streamPreview.setImageDrawable(image);
        }
    }


    private void setUpGraphics(){
        // Populate image view with dummy image
        this.setStreamPreview(this.getDrawable(R.drawable.abstract_image));

        ((TextView) this.findViewById(R.id.detail_game_header))
                .setText(this.gameName);

        // I want a blank bar.
        this.setTitle("");

        // @author Rob Hess
        ActionBar actionBar = this.getActionBar();
        if(actionBar != null){
            // I want that sick back button.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setDefaultValues();

        // Check for a good intent
        if(!isGoodIntent(this.getIntent())){
            this.politelyExit();
        }else{
            setErrorCode(this.getIntent(), ERROR_CODE_OK);
        }

        /* ------ Populate fields ------ */
        setContentView(R.layout.activity_detail);

        // Populate private variable with reference to image view
        this.streamPreview = this.findViewById(R.id.detail_preview_image);
        this.gameId = this.getIntent().getStringExtra(EXTRA_GAME_ID);
        this.gameName = this.getIntent().getStringExtra(EXTRA_GAME_NAME);



        /* ------ Call Setup Subroutines ------ */

        this.setUpGraphics();

    }

    /*
     * Politely exit if not given all data.
     * This function navigates up to its calling program.
     */
    public void politelyExit(){
        setErrorCode(this.getIntent(), ERROR_CODE_BAD);
        this.setResult(RESULT_CANCELED, this.getIntent());
        this.navigateUpToFromChild(this, this.getIntent());
        Log.e(TAG, "I need more data to start!");
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.navigateUpToFromChild(this, this.getIntent());
        return true;
    }


    /* -- Code To be deleted. Used for Testing! -- */
    private void setDefaultValues(){
        this.getIntent().putExtra(EXTRA_GAME_ID, "21779");
        this.getIntent().putExtra(EXTRA_GAME_NAME, "League of Legends");
    }
}
