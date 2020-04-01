package com.example.iot_electronic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.connectTry)
    Button connectTry;
    @BindView(R.id.signalView)
    Button signalView;
    @BindView(R.id.resultText)
    TextView resultText;

    // 연결할 클라이언트
    private Client connectClient;

    // mqtt 접속을 위한 클라이언트
    private MqttAndroidClient mqttAndroidClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.connectTry, R.id.signalView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.connectTry:

                InputServerDialogBox newFragment = new InputServerDialogBox();
                newFragment.show(getSupportFragmentManager(), "dialog"); //"dialog"라는 태그를 갖는 프래그먼트를 보여준다.
                newFragment.setDialogResult(new InputServerDialogBox.OnCompleteListener() {


                    @Override
                    public void onInputedData(Client client) {
                        // 서버랑 연결 시도할거임
                        connectClient = client;
                    }

                });

                int ret = conenctSerever(connectClient.getClientId(), connectClient.getIp(), connectClient.getPort());

//                AlertDialog.Builder builder = new AlertDialog.Builder();
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();

                break;
            case R.id.signalView:


                break;
        }
    }

    private int conenctSerever(String clientId, String ip, String port) {

        //String url = "tcp://" + ipText.getText();
        ip = "tcp://m16.cloudmqtt.com";

        //String port = portText.getText().toString();
        port = "14593";

        // String clientId = nameText.getText().toString();
//        clientId = "uztrfyhg";
        clientId = "test"

        mqttAndroidClient = new MqttAndroidClient(this,
                url + ":" + port,
                clientId);
        try {
            IMqttToken token = mqttAndroidClient.connect(getMqttConnectionOption());    //mqtttoken 이라는것을 만들어 connect option을 달아줌
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());    //연결에 성공한경우
                    Log.e("Connect_success", "Success");
                    // Intent intent = new Intent(MainActivity.this, LogActivity.class);
                    // 그쪽으로 가게 하기


                    try {
                        mqttAndroidClient.subscribe("kss", 0);   //연결에 성공하면 jmlee 라는 토픽으로 subscribe함
                        //startActivity(intent);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {   //연결에 실패한경우
                    Log.e("connect_fail", "Failure " + exception.toString());
                    resultText.setText("Connect fail!");
                }
            });

        } catch (
                MqttException e) {
            e.printStackTrace();
        }

        mqttAndroidClient.setCallback(new MqttCallback() {  //클라이언트의 콜백을 처리하는부분
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {    //모든 메시지가 올때 Callback method
                if (topic.equals("jmlee")) {     //topic 별로 분기처리하여 작업을 수행할수도있음
                    String msg = new String(message.getPayload());
                    //resultView.setText(msg);
                    Log.e("arrive message : ", msg);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });

        resultText.setText("Connect fail! (end)");

    }

    private DisconnectedBufferOptions getDisconnectedBufferOptions() {
        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
        disconnectedBufferOptions.setBufferEnabled(true);
        disconnectedBufferOptions.setBufferSize(100);
        disconnectedBufferOptions.setPersistBuffer(true);
        disconnectedBufferOptions.setDeleteOldestMessages(false);
        return disconnectedBufferOptions;
    }

    private MqttConnectOptions getMqttConnectionOption() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

        mqttConnectOptions.setUserName("uztrfyhg");
        mqttConnectOptions.setPassword("zN6Oiudz0plw".toCharArray());
        mqttConnectOptions.setCleanSession(false);
        //mqttConnectOptions.setAutomaticReconnect(true);
        //mqttConnectOptions.setWill("aaa", "I am going offlineasd".getBytes(), 1, true);
        return mqttConnectOptions;
    }
}
