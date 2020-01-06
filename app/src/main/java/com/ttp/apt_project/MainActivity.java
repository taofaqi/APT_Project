package com.ttp.apt_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ttp.apt_project.bean.ChapterResult;
import com.ttp.http.HttpListener;

import java.util.List;

/**
 * @author faqi.tao
 * @time 2019/12/31
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "HttpManager";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        HttpApiManager.getService().getChapterResult().launch(this, new HttpListener<List<ChapterResult>>() {
            @Override
            public void onSuccess(List<ChapterResult> result) {
                Log.e(TAG, "onSuccess");

                Log.e(TAG, result.get(0).toString());
            }

            @Override
            public void onError(String errorResult) {
                Log.e(TAG, "onError");
            }

            @Override
            public void onFinal() {
                Log.e(TAG, "onFinal");
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                startActivity(new Intent(MainActivity.this,PersonalActivity.class));
            }
        }).start();
    }
}
