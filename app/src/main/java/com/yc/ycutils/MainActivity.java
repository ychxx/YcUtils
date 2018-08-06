package com.yc.ycutils;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {
    private int i = 0;
    private String[] s = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ycIntentAction = new YcActionUtils();
//        YcUtilPermission.requestPermissions(this, YcUtilPermission.PERMISSION_STORAGE, new YcUtilPermission.PermissionCall() {
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onFail() {
//                Log.e("asd", "onFail");
//            }
//        });

        findViewById(R.id.button).setOnClickListener(v -> {

//            Log.e("asd","开始1");
//            Observable.fromFuture(Observable.just(1).toFuture())
//                    .doOnComplete(() -> System.out.println("complete"))
//                    .subscribe();
        });
        findViewById(R.id.button2).setOnClickListener(v -> {
            Log.e("asd","开始2");
            Observable.fromCallable(() -> Arrays.asList("hello", "gaga"))
                    .subscribe(strings -> System.out.println(strings));
        });
//            ycIntentAction.openFileManager(MainActivity.this, new YcActionUtils.OpenFileCall() {
//                @Override
//                public void fileCall(int resultCode, Intent data) {
//                    if (resultCode == Activity.RESULT_OK) {
//                        Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
//                    } else {
//                        Log.e("asd", "打开文件失败");
//                    }
//                }
//        );
//    });
    }

    private void getPermission() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ycIntentAction.onActivityResult(requestCode, resultCode, data);
    }
}
