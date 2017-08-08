package com.example.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.example.aidl.IMyAidlInterface;

public class MyService extends Service {

    boolean flag;
    public MyService()
    {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(),"RemoteService onCreate",Toast.LENGTH_SHORT).show();
        flag = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (flag){
                    count++;
                    Log.e("count",count+"");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return new MyBinder();
//        return null;
    }

    class MyBinder extends IMyAidlInterface.Stub
    {

        @Override
        public String getName() throws RemoteException
        {
            return "test";
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        Toast.makeText(getApplicationContext(),"RemoteService onDestroy",Toast.LENGTH_SHORT).show();
    }
}
