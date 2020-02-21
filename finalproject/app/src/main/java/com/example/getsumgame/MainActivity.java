package com.example.getsumgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.getsumgame.data.GameListItem;
import com.example.getsumgame.data.GameListResult;
import com.example.getsumgame.data.Status;
import com.example.getsumgame.utils.NetworkUtils;
import com.example.getsumgame.utils.TwitchUtils;

import java.io.IOException;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get_game_button=(Button) findViewById(R.id.get_game_button);
        get_game_button.setOnClickListener(this);
        CLIENT_ID=TwitchUtils.getClientId();
        Get_Top_Game=TwitchUtils.getGet_Top_Game();

        mGameAdapter=new GameAdapter();
        mGameItemsRV=findViewById(R.id.rv_game_items);
        mLoadingErrorMessageTV=findViewById(R.id.tv_loading_error_message);
        mLoadingIndicatorPB=findViewById(R.id.pb_loading_indicator);
        mViewmodel=new ViewModelProvider(this).get(GameViewModel.class);

        mGameItemsRV.setAdapter(mGameAdapter);
        mGameItemsRV.setLayoutManager(new LinearLayoutManager(this));
        mGameItemsRV.setHasFixedSize(true);

        mViewmodel.getmGameResult().observe(this, new Observer<List<GameListItem>>() {
            @Override
            public void onChanged(List<GameListItem> items) {
                mGameAdapter.updateGameData(items);
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

//    public class gameAsyncTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            String id = params[0];
//            String request=params[1];
//            String gameJSON = "ABC";
//            try {
//                gameJSON = NetworkUtils.doHTTPGet(id,request);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return gameJSON;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
////            TextView test= findViewById(R.id.test);
////            Log.d("123",s);
////            test.setText(s);
//            ArrayList<GameListItem> results= TwitchUtils.parseGameJson(s);
//            Log.d("123",results.get(0).name);
//        }
//    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.get_game_button:
                mViewmodel.loadGameResults(CLIENT_ID,Get_Top_Game);

//                new gameAsyncTask().execute(CLIENT_ID,Get_Top_Game);
        }

    }
}