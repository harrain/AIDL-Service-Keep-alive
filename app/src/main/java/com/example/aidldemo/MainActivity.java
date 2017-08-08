package com.example.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.aidl.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface iMyAidlInterface;
    TextView tv;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);

        intent = new Intent();
        intent.setClassName("com.example.aidldemo","com.example.aidldemo.MyService");
    }

    public void bindService(View view){
        startService(intent);
        bindService(intent,mServiceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection()
    {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            tv.setText("onServiceConnected"+"\n");
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);

            try {
                tv.setText(iMyAidlInterface.getName()+"\n");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            tv.setText("onServiceDisconnected");

        }
    };

    public void unBindService(View view){
        unbindService(mServiceConnection);
    }

    public void stopService(View view){
        stopService(intent);
    }
}
