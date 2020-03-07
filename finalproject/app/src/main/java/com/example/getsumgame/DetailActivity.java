package com.example.getsumgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.getsumgame.data.Status;
import com.example.getsumgame.models.StreamerListItem;
import com.example.getsumgame.models.StreamerListResult;
import com.example.getsumgame.viewmodels.StreamViewModel;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Locale;


/*
 * TODO
 *  [x] Get the values associated with the Game
 *     [x] Game ID
 *     [x] Game Title
 *  [x] Get the values from the Stream
 *     [x] Get available streams
 *     [x] Choose a stream
 *     [x] Get Streamer Name
 *     [x] Get Preview
 *     [ ] Get Link to stream
 *     [x] Get Hours streamed
 *  [x] Get the Values from ChickenCoup
 *     [x] Game Rating
 *     [x] Store in an object in case someone wants to use review too
 *  [x] Update UI
 *     [x] Update Streamer Texts with Info
 *     [x] Politely exit if ID does not makes sense
 *     [x] Populate Game Rating from Chicken Coup
 *  [ ] Handle Intent
 *     [ ] Handle click on image
 *     [ ] Create intent to Twitch
 */
public class DetailActivity extends AppCompatActivity {

    // Strings for identification of intent extras
    public static final String EXTRA_GAME_ID = "game_id";
    public static final String EXTRA_GAME_NAME = "game_name";
    public static final String EXTRA_STREAMERS_SERIAL = "streamers_data";
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
    private StreamerListItem streamerDetails = null;

    // API Data Reference
    private StreamViewModel streamViewModel = null;


    /****************** STATIC FUNCTIONS ********************************/

    public static boolean isGoodIntent(Intent intent){
        if(!intent.hasExtra(EXTRA_GAME_ID) || intent.getStringExtra(EXTRA_GAME_ID) == null){
            return false;
        }

        if(!intent.hasExtra(EXTRA_GAME_NAME) || intent.getStringExtra(EXTRA_GAME_NAME) == null){
            return false;
        }

        if(
                !intent.hasExtra(EXTRA_STREAMERS_SERIAL) ||
                intent.getSerializableExtra(EXTRA_STREAMERS_SERIAL) == null ||
                !(intent.getSerializableExtra(EXTRA_STREAMERS_SERIAL) instanceof StreamerListResult)
        ){
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

    public static String getHours(long millis){
        Log.d(TAG, "Number of milliseconds: " + millis);
        long hours = millis / 3600000;
        return Long.toString(hours);
    }

    /****************** UTILITY FUNCTIONS ********************************/

    public void setStreamPreview(Drawable image){
        if (streamPreview != null) {
            this.streamPreview.setImageDrawable(image);
        }
    }

    /****************** PRIVATE HELPER FUNCTIONS ********************************/


    /********* Graphical **************/
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

    private void pushViewModel(){
        Intent intent = this.getIntent();
        StreamerListResult itemsContainer =
                (StreamerListResult) intent.getSerializableExtra(EXTRA_STREAMERS_SERIAL);
        if(itemsContainer != null) {
            ArrayList<StreamerListItem> items = itemsContainer.data;
            this.streamViewModel.streamDataPush(items);
        }else{
            this.politelyExit();
        }
    }

    private void populateLoadStreamerDetails(){
        if(this.streamViewModel.getStreamData().getValue() != null) {
            // Just get first item
            this.streamerDetails = this.streamViewModel.getStreamData().getValue().get(0);

            // We can do more advanced stuff later if we want!
        }else{
            Log.e(TAG, "Pushed new data, but repo still has null reference");
            Log.e(TAG, "Cannot populate Text Views!");
        }
    }

    private void populateTextViews(){
        if(this.streamViewModel != null){
            TextView header = (TextView) this.findViewById(R.id.detail_streamer_header);
            header.setText(this.streamerDetails.user_name);

            this.populateHoursStremaingText();
        }
    }

    private void populateHoursStremaingText(){
        Date prev = null;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

        try {
            prev = format.parse(this.streamerDetails.started_at);
        } catch (ParseException e) {
            Log.e(TAG, "Could not parse!");
            Log.e(TAG, e.toString());
        }

        if(prev == null){
            prev = new Date();
        }

        Date now = new Date();
        Date difference = new Date(now.getTime() - prev.getTime());
        String hours = getHours(difference.getTime());
        String text = getResources().getString(R.string.stream_span);

        // This is a really cool function. I did not know about this. Pretty Snazzy.
        text = text.replaceFirst("XX", hours);

        TextView timeStamp = (TextView) this.findViewById(R.id.detail_stream_span_text);
        timeStamp.setText(text);
    }

    private void hideRating(){
        TextView ratingText = findViewById(R.id.detail_rating_text);
        ratingText.setVisibility(View.INVISIBLE);
    }

    private void populateRating(boolean hasValidRating){
        TextView ratingText = findViewById(R.id.detail_rating_text);
        String template = getString(R.string.unknown_game_rating);


        if(hasValidRating && this.streamViewModel.getCoupData().getValue() != null){
            if(this.streamViewModel.getCoupData().getValue().result != null) {
                int rating = this.streamViewModel.getCoupData().getValue().result.score;
                template = template.replaceFirst("XX", Integer.toString(rating));
            }else{
                template = template.replaceFirst("XX", "NA");

            }
            ratingText.setText(template);
        }else{
            ratingText.setText(R.string.bad_game_rating);
        }

        ratingText.setVisibility(View.VISIBLE);
    }

    /********* Background/Networking **************/
    private void loadPreview(){

    }

    private void startObservers(){
        // TODO add observer to SingleGameRepsository
        this.streamViewModel.getCoupRepoStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                switch(status){
                    case SUCCESS:
                        populateRating(true);
                        break;
                    case EMPTY:
                    case LOADING:
                    case ERROR:
                        hideRating();
                        break;
                    case BAD:
                    default:
                        populateRating(false);
                }
            }
        });
    }


    private void pullOnRepos(){
        this.streamViewModel.coupDataPull(this.gameName);
    }

    /****************** PUBLIC FUNCTIONS ********************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // Populate private fields with intents
        this.gameId = this.getIntent().getStringExtra(EXTRA_GAME_ID);
        this.gameName = this.getIntent().getStringExtra(EXTRA_GAME_NAME);

        // Populate the Game View Model field with a new reference to the view model
        this.streamViewModel = new ViewModelProvider(this).get(StreamViewModel.class);


        /* ------ Call Setup Subroutines ------ */

        this.setUpGraphics();
        this.pushViewModel();
        this.populateLoadStreamerDetails();
        this.populateTextViews();
        this.loadPreview();
        this.startObservers();
        this.pullOnRepos();
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


    /****************** TEST FUNCTIONS ********************************/

    private void setDefaultValues(){
        final long id = 21799;
        final String url
                = "https://static-cdn.jtvnw.net/previews-ttv/live_user_lirik-{width}x{height}.jpg";

        this.getIntent().putExtra(EXTRA_GAME_ID, Long.toString(id));
        this.getIntent().putExtra(EXTRA_GAME_NAME, "League of Legends");
        ArrayList<StreamerListItem> temp = new ArrayList<>();
        for(int i = 0; i < 3; i ++){
            StreamerListItem item = new StreamerListItem();
            item.game_id = id;
            item.started_at = "2020-02-29T11:00:00Z";
            item.user_name = "Fin Mercers";
            item.thumbnail_url = url;
            item.viewer_count = 1000;
            item.user_id = 105458682; // Hehehehehehehe
            temp.add(item);
        }
        StreamerListResult result = new StreamerListResult();
        result.data = temp;
        this.getIntent().putExtra(EXTRA_STREAMERS_SERIAL, result);
    }
}
