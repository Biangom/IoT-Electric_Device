package com.example.empty_project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.empty_project.MainActivity.mqttAndroidClient;

public class LogActivity extends AppCompatActivity {


    @BindView(R.id.sendBtn)
    Button sendBtn;
    @BindView(R.id.toggleBtn2)
    Button toggleBtn2;
    @BindView(R.id.toggleBtn1)
    Button toggleBtn1;

    @BindView(R.id.logText)
    TextView logText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);


        mqttAndroidClient.setCallback(new MqttCallback() {  //클라이언트의 콜백을 처리하는부분
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {    //모든 메시지가 올때 Callback method
                //String msg = new String(message.getPayload());
                if (topic.equals("kss")) {     //topic 별로 분기처리하여 작업을 수행할수도있음
                    String msg = new String(message.getPayload());
                    logText.setText(msg);
                    Log.e("arrive message : ", msg);
                }
                //Log.e("arrive message : ", msg);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });

    }

    @OnClick({R.id.sendBtn, R.id.toggleBtn2, R.id.toggleBtn1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendBtn:

                try {
                    if (!mqttAndroidClient.isConnected()) {
                        System.out.println("**** disconnected!!!! ***");
                    }
                    mqttAndroidClient.publish("temper", "1000".getBytes(), 0, false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.toggleBtn2:
                break;
            case R.id.toggleBtn1:
                break;
        }
    }
}
