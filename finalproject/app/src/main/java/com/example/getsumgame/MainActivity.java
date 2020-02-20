package com.example.getsumgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.getsumgame.utils.NetworkUtils;
import com.example.getsumgame.utils.TwitchUtils;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{

    private Button get_game_button;
    private String CLIENT_ID;
    private String Get_Top_Game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get_game_button=(Button) findViewById(R.id.get_game_button);
        get_game_button.setOnClickListener(this);
        CLIENT_ID=TwitchUtils.getClientId();
        Get_Top_Game=TwitchUtils.getGet_Top_Game();
    }

    public class gameAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String request=params[1];
            String forecastJSON = "ABC";
            try {
                forecastJSON = NetworkUtils.doHTTPGet(id,request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return forecastJSON;
        }

        @Override
        protected void onPostExecute(String s) {
            TextView test= findViewById(R.id.test);

            test.setText(s);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.get_game_button:
                new gameAsyncTask().execute(CLIENT_ID,Get_Top_Game);
        }

    }
}
