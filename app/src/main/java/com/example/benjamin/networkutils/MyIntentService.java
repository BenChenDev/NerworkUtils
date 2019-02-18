package com.example.benjamin.networkutils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class MyIntentService extends IntentService {

    private ResultReceiver mResultReceiver;
    public MyIntentService(){
        super(MyIntentService.class.getName());
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        //catch intent and mResultReceiver object
        String s = intent.getStringExtra("QUERY");
        mResultReceiver = intent.getParcelableExtra("RECEIVER");
        String result = NetworkUtils.getBookInfo(s);
        Bundle bundle = new Bundle();
        bundle.putString("RESULT", result);


        mResultReceiver.send(1,bundle);



        //
    }
}
