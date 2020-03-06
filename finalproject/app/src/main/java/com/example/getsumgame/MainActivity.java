package com.example.getsumgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getsumgame.models.StreamerListItem;
import com.example.getsumgame.models.StreamerListResult;
import com.example.getsumgame.viewmodels.GameViewModel;
import com.example.getsumgame.models.GameInfo;
import com.example.getsumgame.data.Status;
import com.example.getsumgame.utils.TwitchUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private Button get_game_button;
    private String CLIENT_ID;
    private String Get_Top_Game;
    private RecyclerView mGameItemsRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private GameViewModel mViewmodel;
    private GameAdapter mGameAdapter;
    private LinearLayout mainLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Toast myToast;
    private long myLastClickTime;
    private DrawerLayout mDrawerLayout;

    private static final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        myLastClickTime = 0; // initialize last click time
        myToast = null; //initialize toast object
        get_game_button=(Button) findViewById(R.id.get_game_button);
        get_game_button.setOnClickListener(this);

        CLIENT_ID=TwitchUtils.getClientId();
        Get_Top_Game=TwitchUtils.getGet_Top_Game();

        mainLayout = findViewById(R.id.get_main_layout);
        mSwipeRefreshLayout=findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mGameAdapter=new GameAdapter();
        mGameItemsRV=findViewById(R.id.rv_game_items);
        mLoadingErrorMessageTV=findViewById(R.id.tv_loading_error_message);
        mLoadingIndicatorPB=findViewById(R.id.pb_loading_indicator);
        mViewmodel=new ViewModelProvider(this).get(GameViewModel.class);
        mDrawerLayout = findViewById(R.id.drawer_layout); //drawer

        mGameItemsRV.setAdapter(mGameAdapter);
        mGameItemsRV.setLayoutManager(new LinearLayoutManager(this));
        mGameItemsRV.setHasFixedSize(true);

        mViewmodel.getmGameInfo().observe(this, new Observer<List<GameInfo>>() {
            @Override
            public void onChanged(List<GameInfo> gameInfos) {
//                Log.d("txtid",Integer.toString(gameInfos.size()));
                mGameAdapter.updateGameData(gameInfos);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        mViewmodel.getmLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mGameItemsRV.setVisibility(View.VISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mGameItemsRV.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void myUpdateOperation(){
        if (myToast!=null){
            myToast.cancel();
        }
        if(SystemClock.elapsedRealtime() - myLastClickTime < 30000) //30 seconds
        {
            long seconds=(SystemClock.elapsedRealtime() - myLastClickTime)/1000;
            seconds=30-seconds;
            myToast = Toast.makeText(this,"Request is Too Frequent,please wait for "+
                    Long.toString(seconds)+"seconds",Toast.LENGTH_LONG);
            myToast.show();
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        myLastClickTime = SystemClock.elapsedRealtime();
        mainLayout.setBackgroundColor(Color.WHITE);
        mViewmodel.loadGameResults(CLIENT_ID,Get_Top_Game);
        Log.d("fresh","freshed");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        Log.d("fresh", "onRefresh called from SwipeRefreshLayout");
        myUpdateOperation();
    }

    @Override
    public void onClick(View view)
    {
        if (myToast!=null){
            myToast.cancel();
        }
        if(SystemClock.elapsedRealtime() - myLastClickTime < 30000) //30 seconds
        {
            myToast = Toast.makeText(this,"Request is Too Frequent",Toast.LENGTH_LONG);
            myToast.show();
            return;
        }
        myLastClickTime = SystemClock.elapsedRealtime();
        switch (view.getId()) {
            case R.id.get_game_button:
                mainLayout.setBackgroundColor(Color.WHITE);
                mViewmodel.loadGameResults(CLIENT_ID,Get_Top_Game);

//                new gameAsyncTask().execute(CLIENT_ID,Get_Top_Game);
                break;
            default:
                Log.d(TAG, "Unhandled Click!");
                Log.d(TAG, "Came from: " + view.getId());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_refresh:
                Log.d("fresh", "Refresh menu item selected");

                // Signal SwipeRefreshLayout to start the progress indicator
                mSwipeRefreshLayout.setRefreshing(true);

                // Start the refresh background task.
                // This method calls setRefreshing(false) when it's finished.
                myUpdateOperation();
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void launchDetails(String gameId, String gameName, int index){
        Log.d(TAG, "Details Launch Initiated!");

        // Feel free to use DetailActivity.isGoodIntent(Intent) to verify a good intent.

        Intent intent = new Intent();

        // Grab streamers for serialization
        ArrayList<ArrayList<StreamerListItem>> streamers =
                this.mViewmodel.getStreamers().getValue();

        if(streamers != null && !streamers.isEmpty()) {
            StreamerListResult temp = new StreamerListResult();
            temp.data = streamers.get(index);
            intent.putExtra(DetailActivity.EXTRA_STREAMERS_SERIAL, temp);
            intent.putExtra(DetailActivity.EXTRA_GAME_ID, gameId);
            intent.putExtra(DetailActivity.EXTRA_GAME_NAME, gameName);
            intent.setClass(this, DetailActivity.class);
            this.startActivity(intent);
        }else{
            Log.e(TAG, "Could not serialize Streamer Information to detail activity!");
        }    
    }
}
