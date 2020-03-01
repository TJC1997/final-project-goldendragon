package com.example.getsumgame.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.getsumgame.models.CoupResult;
import com.example.getsumgame.utils.CoupUtils;
import com.example.getsumgame.utils.NetworkUtils;

public class CoupAsyncTask extends AsyncTask<String, Void, String> {
    private static final String TAG = CoupAsyncTask.class.getName();
    private Callback mCallback;

    public interface Callback{
        void result(CoupResult result);
    }

    public CoupAsyncTask(Callback callback){
        this.mCallback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        if(strings.length >= 1){
            String query = CoupUtils.getQuery(strings[0]);
            return NetworkUtils.doSimpleHTTPGet(query);
        }else{
            Log.e(TAG, "We need a name to query. Input did not have a valid string!");
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        CoupResult res = null;

        if(s != null) {
            res = CoupUtils.parseJson(s);
        }

        if(mCallback != null){
            mCallback.result(res);
        }
    }
}
