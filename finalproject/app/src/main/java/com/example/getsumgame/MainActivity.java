package com.example.getsumgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.getsumgame.models.StreamerListItem;
import com.example.getsumgame.models.StreamerListResult;
import com.example.getsumgame.viewmodels.GameViewModel;
import com.example.getsumgame.models.GameInfo;
import com.example.getsumgame.data.Status;
import com.example.getsumgame.utils.TwitchUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{

    private Button get_game_button;
    private String CLIENT_ID;
    private String Get_Top_Game;
    private RecyclerView mGameItemsRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private GameViewModel mViewmodel;
    private GameAdapter mGameAdapter;
    private LinearLayout mainLayout;

    private static final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get_game_button=(Button) findViewById(R.id.get_game_button);
        get_game_button.setOnClickListener(this);
        CLIENT_ID=TwitchUtils.getClientId();
        Get_Top_Game=TwitchUtils.getGet_Top_Game();

        mainLayout = findViewById(R.id.get_main_layout);

        mGameAdapter=new GameAdapter();
        mGameItemsRV=findViewById(R.id.rv_game_items);
        mLoadingErrorMessageTV=findViewById(R.id.tv_loading_error_message);
        mLoadingIndicatorPB=findViewById(R.id.pb_loading_indicator);
        mViewmodel=new ViewModelProvider(this).get(GameViewModel.class);

        mGameItemsRV.setAdapter(mGameAdapter);
        mGameItemsRV.setLayoutManager(new LinearLayoutManager(this));
        mGameItemsRV.setHasFixedSize(true);

        mViewmodel.getmGameInfo().observe(this, new Observer<List<GameInfo>>() {
            @Override
            public void onChanged(List<GameInfo> gameInfos) {
//                Log.d("txtid",Integer.toString(gameInfos.size()));
                mGameAdapter.updateGameData(gameInfos);
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
            }
        });
    }


    @Override
    public void onClick(View view)
    {
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
