package com.example.empty_project;

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
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    static TextView resultView = null;
    static private MqttClient sampleClient;

    @BindView(R.id.ipText)
    TextView ipText;

    @BindView(R.id.portText)
    TextView portText;

    @BindView(R.id.nameText)
    TextView nameText;



    @BindView(R.id.sendButton)
    Button sendButton;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.connectTry)
    Button connectTry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //resultView = findViewById(R.id.resultView);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @OnClick({R.id.sendButton, R.id.button2, R.id.connectTry})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendButton:
                break;
            case R.id.button2:
                break;
            case R.id.connectTry:
                // log 화면으로 가기 위한 인텐트
//                Intent logIntent = new Intent(MainActivity.this, LogActivity.class);
//                MainActivity.this.startActivity(logIntent);

                String url = "tcp://m16.cloudmqtt.com";
                String port = portText.getText().toString();
                String clientId = "uztrfyhg";

                Intent mqttIntent = new Intent(MainActivity.this, MqttService.class);
                mqttIntent.putExtra("url",url);
                mqttIntent.putExtra("port",port);
                mqttIntent.putExtra("clientId",clientId);
                startService(mqttIntent);



                break;
        }
    }


    // Button.OnclickListener를 implements하므로 onClick() 함수를 오버라이딩.


//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.connectTry:
//                // log 화면으로 가기 위한 인텐트
//                Intent logIntent = new Intent(MainActivity.this, LogActivity.class);
//                MainActivity.this.startActivity(logIntent);
//
//                String url = "123";
//                String port = "123";
//                String clientId = "uztrfyhg";
//
//                mqttAndroidClient = new MqttAndroidClient(this,
//                        "tcp://" +
//                                "m16.cloudmqtt.com" +
//                                ":14593",
//                        "uztrfyhg");
//                try {
//                    IMqttToken token = mqttAndroidClient.connect(getMqttConnectionOption());    //mqtttoken 이라는것을 만들어 connect option을 달아줌
//                    token.setActionCallback(new IMqttActionListener() {
//                        @Override
//                        public void onSuccess(IMqttToken asyncActionToken) {
//                            mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());    //연결에 성공한경우
//                            Log.e("Connect_success", "Success");
//                            try {
//                                mqttAndroidClient.subscribe("jmlee", 0);   //연결에 성공하면 jmlee 라는 토픽으로 subscribe함
//                            } catch (MqttException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {   //연결에 실패한경우
//                            Log.e("connect_fail", "Failure " + exception.toString());
//                        }
//                    });
//
//                } catch (
//                        MqttException e) {
//                    e.printStackTrace();
//                }
//
//                mqttAndroidClient.setCallback(new MqttCallback() {  //클라이언트의 콜백을 처리하는부분
//                    @Override
//                    public void connectionLost(Throwable cause) {
//                    }
//
//                    @Override
//                    public void messageArrived(String topic, MqttMessage message) throws Exception {    //모든 메시지가 올때 Callback method
//                        if (topic.equals("jmlee")) {     //topic 별로 분기처리하여 작업을 수행할수도있음
//                            String msg = new String(message.getPayload());
//                            resultView.setText(msg);
//                            Log.e("arrive message : ", msg);
//                        }
//                    }
//
//                    @Override
//                    public void deliveryComplete(IMqttDeliveryToken token) {
//
//                    }
//
//
//                });
//
//                break;
//
//        }

//        resultView = (TextView) findViewById(R.id.resultView);
//        editText = findViewById(R.id.ipText);
//
//        switch (view.getId()) {
//            case R.id.sendButton:
//                try {
//                    if(!mqttAndroidClient.isConnected()) {
//                        System.out.println("**** disconnected!!!! ***");
//                    }
//                    mqttAndroidClient.publish("temper", editText.getText().toString().getBytes(), 0 , false );
//                } catch (MqttException e) {
//                    e.printStackTrace();
//                }
//
//
////                topic       = "MQTT Examples";
////                qos         = 0;
////                String broker       = "tcp://m16.cloudmqtt.com:14593";
////                String clientId     = "TestName";
////                persistence = new MemoryPersistence();
////
////                try {
////                    sampleClient = new MqttClient(broker, clientId, persistence);
////
////                    MqttConnectOptions connOpts = new MqttConnectOptions();
////                    connOpts.setCleanSession(true);
////                    connOpts.setUserName("uztrfyhg");
////                    connOpts.setPassword("6Zi8XPdWFqed".toCharArray());
////                    System.out.println("Connecting to broker: "+broker);
////                    sampleClient.connect(connOpts);
////                    if(sampleClient.isConnected()) {
////                        System.out.println("###client complete=d");
////                    }
////                    System.out.println("Connected");
////                    String content;
////                    content = editText.getText().toString();
////                    //resultView.setText("Red");
////                    MqttMessage message = new MqttMessage(content.getBytes());
////
////                    message.setQos(qos);
////                    sampleClient.publish(topic, message);
////                    System.out.println("Publishing message: "+content);
////                    System.out.println("Message published");
////                    //textView1.setBackgroundColor(Color.rgb(255, 0, 0));
////                    break ;
////
////                } catch(MqttException me) {
////                    System.out.println("reason "+me.getReasonCode());
////                    System.out.println("msg "+me.getMessage());
////                    System.out.println("loc "+me.getLocalizedMessage());
////                    System.out.println("cause "+me.getCause());
////                    System.out.println("excep "+me);
////                    me.printStackTrace();
////                }
//
//
////            case R.id.buttonGreen :
////                textView1.setText("Green") ;
////                textView1.setBackgroundColor(Color.rgb(0, 255, 0));
////                break ;
////            case R.id.buttonBlue :
////                textView1.setText("Blue") ;
////                textView1.setBackgroundColor(Color.rgb(0, 0, 255));
////                break ;
//                break;
//
//            case R.id.connectTry:
//                System.out.println("123123123");
//                mqttAndroidClient = new MqttAndroidClient(this,
//                        "tcp://" +
//                                "m16.cloudmqtt.com" +
//                                ":14593" ,
//                        "uztrfyhg");
//                try {
//                    IMqttToken token = mqttAndroidClient.connect(getMqttConnectionOption());    //mqtttoken 이라는것을 만들어 connect option을 달아줌
//                    token.setActionCallback(new IMqttActionListener() {
//                        @Override
//                        public void onSuccess(IMqttToken asyncActionToken) {
//                            mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());    //연결에 성공한경우
//                            Log.e("Connect_success", "Success");
//                            try {
//                                mqttAndroidClient.subscribe("jmlee", 0);   //연결에 성공하면 jmlee 라는 토픽으로 subscribe함
//                            } catch (MqttException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        @Override
//                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {   //연결에 실패한경우
//                            Log.e("connect_fail", "Failure " + exception.toString());
//                        }
//                    });
//
//                } catch (
//                        MqttException e) {
//                    e.printStackTrace();
//                }
//
//                mqttAndroidClient.setCallback(new MqttCallback() {  //클라이언트의 콜백을 처리하는부분
//                    @Override
//                    public void connectionLost(Throwable cause) {
//                    }
//                    @Override
//                    public void messageArrived(String topic, MqttMessage message) throws Exception {    //모든 메시지가 올때 Callback method
//                        if (topic.equals("jmlee")){     //topic 별로 분기처리하여 작업을 수행할수도있음
//                            String msg = new String(message.getPayload());
//                            resultView.setText(msg);
//                            Log.e("arrive message : ", msg);
//                        }
//                    }
//                    @Override
//                    public void deliveryComplete(IMqttDeliveryToken token) {
//
//                    }
//
//
//                });
//                break;
//        }

//    }



}
