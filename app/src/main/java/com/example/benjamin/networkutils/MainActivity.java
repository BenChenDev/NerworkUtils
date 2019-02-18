package com.example.benjamin.networkutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button fetchData;
    EditText search;
    TextView mTitleView, mAuthorView;

    //option2 using intent and resultReceiver
    private ResultReceiver mResultReceiver = new ResultReceiver(new Handler()){
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if(resultCode == 1){
                if(resultData != null){
                    String res = resultData.getString("RESULT");
                    processJSONRespose(res);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchData = findViewById(R.id.fetchdata);
        search = findViewById(R.id.search);
        mTitleView = findViewById(R.id.title);
        mAuthorView = findViewById(R.id.author);
        fetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = search.getText().toString();
//                new MyAsyncTask().execute(s);
                //option2 using intent and resultReceiver
                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                intent.putExtra("QUERY", s);
                intent.putExtra("RECEIVER", mResultReceiver);
                startService(intent);
            }
        });
    }



    //option4 using volley create thread pool


    //option 1 using async task
//    private class MyAsyncTask extends AsyncTask<String, Void, String>{
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            Log.i("JSON:  ", s);
//            processJSONRespose(s);
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            return NetworkUtils.getBookInfo(strings[0]);
//        }
//    }

    private void processJSONRespose(String s){
    try {
        JSONObject jsonObject = new JSONObject(s);
        JSONArray itemsArray = jsonObject.getJSONArray("items");
        for(int i = 0; i<itemsArray.length(); i++){
            JSONObject book = itemsArray.getJSONObject(i);
            String title=null;
            String authors=null;
            JSONObject volumeInfo = book.getJSONObject("volumeInfo");


            try {
                title = volumeInfo.getString("title");
                authors = volumeInfo.getString("authors");
            } catch (Exception e){
                e.printStackTrace();
            }


            if (title != null && authors != null){
                mTitleView.setText(title);
                mAuthorView.setText(authors);
                return;
            }
        }


        mTitleView.setText("No Results Found");
        mAuthorView.setText("");


    } catch (Exception e){
        mTitleView.setText("No Results Found");
        mAuthorView.setText("");
        e.printStackTrace();
    }
}
    //option3 using broadReceiver
//    private class MyBroadcastReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        IntentFilter filter = new IntentFilter();
//    }
}
