package com.example.empty_project;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttService extends Service {

    static private int qos;
    static private String topic;
    static private MemoryPersistence persistence;
    static private MqttAndroidClient mqttAndroidClient;
    String url;
    String port;
    String clientId;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra("url");
        String port = intent.getStringExtra("port");
        String clientId = intent.getStringExtra("clientId");
        connectTry(url, port, clientId);

        return super.onStartCommand(intent, flags, startId);
    }

    private void connectTry(String url, String port, String clientId) {

        mqttAndroidClient = new MqttAndroidClient(this,
                url + port,
                clientId);
        try {
            IMqttToken token = mqttAndroidClient.connect(getMqttConnectionOption());    //mqtttoken 이라는것을 만들어 connect option을 달아줌
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());    //연결에 성공한경우
                    Log.e("Connect_success", "Success");
                    try {
                        mqttAndroidClient.subscribe("jmlee", 0);   //연결에 성공하면 jmlee 라는 토픽으로 subscribe함
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {   //연결에 실패한경우
                    Log.e("connect_fail", "Failure " + exception.toString());
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

    }

    @Override
    public void onCreate() {
        super.onCreate();



    }



    public MqttService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        url = intent.getStringExtra("url");
        port = intent.getStringExtra("port");
        clientId = intent.getStringExtra("clientId");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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
