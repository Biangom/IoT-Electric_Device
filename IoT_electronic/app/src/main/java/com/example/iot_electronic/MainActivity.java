package com.example.iot_electronic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    public Client connectClient;

    // mqtt 접속을 위한 클라이언트
    static MqttAndroidClient mqttAndroidClient;
    IMqttToken token;


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
                        int ret = connectSerever(client.getClientId(), client.getIp(), client.getPort());
                    }
                });

//                AlertDialog.Builder builder = new AlertDialog.Builder();
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();

                break;
            case R.id.signalView:
                // 연결이 되었을 때만 실행
                if(mqttAndroidClient == null || !mqttAndroidClient.isConnected()) {
                    Toast.makeText(getApplicationContext(), "서버와 연결이 되지 않아 조회할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, LogRecycleView.class);
                    startActivity(intent);
                }
                break;
        }
    }

    private int connectSerever(String clientId, String ip, String port) {



        Log.d("##############","Connect Try!!");

        //String url = "tcp://" + ipText.getText();
        ip = "tcp://m16.cloudmqtt.com";

        //String port = portText.getText().toString();
        port = "14593";

        // String clientId = nameText.getText().toString();
        clientId = "uztrfyhg";

        Log.d(" ",ip + ":" + port + clientId);

        Log.d("S1", "conenctSerever: Step1");
        mqttAndroidClient = new MqttAndroidClient(this,
                ip + ":" + port,
                clientId);
        Log.d("S1", "conenctSerever: Step2");


        // 토큰 사실 잘모르겠따.. 일단 OK

        try {
            // mqttAndroidCleint를 통해 연결 시도. 인자값은 연결옵션값
            // 연결이 시도되면 토큰을 반환한다.
            token = mqttAndroidClient.connect(getMqttConnectionOption());
            if(mqttAndroidClient.isConnected()) {
                Log.d("S1", "conenctSerever: Connected");
            }
            else {
                Log.d("S1", "conenctSerever: Disconnected");
            }
            Log.d("S1", "conenctSerever: Step3");
            token.setActionCallback(new IMqttActionListener() {
                // 해당 토근에 대한 Listener 정의
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());    //연결에 성공한경우
                    Log.e("Connect_success", "#### Success");
                    resultText.setText("서버와 연결이 되었습니다.");
                    // Intent intent = new Intent(MainActivity.this, LogActivity.class);
                    // 그쪽으로 가게 하기

                    try {
                        // 테스트를 위해 등록하기
                        mqttAndroidClient.subscribe("devs/DEV1", 0);
                        mqttAndroidClient.subscribe("devs/DEV2", 0);
                        mqttAndroidClient.subscribe("devs/DEV3", 0);
                        mqttAndroidClient.subscribe("devs/DEV4", 0);
                        //startActivity(intent);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {   //연결에 실패한경우
                    Log.e("connect_fail", "##### Failure " + exception.toString());
                    resultText.setText("서버와 연결을 하지 못했습니다.");
                }
            });
            Log.d("S1", "conenctSerever: Step4");

        } catch ( MqttException e) {
            e.printStackTrace();
            Log.e("connect faile","############# Execpet");
        }
        Log.d("S1", "conenctSerever: Step5");

//        // 메시지가 도달할때 처리하는 로직 정의
//        // 이 함수들이 중요함.
//        mqttAndroidClient.setCallback(new MqttCallback() {
//            @Override
//            public void connectionLost(Throwable cause) {
//            }
//
//            @Override
//            public void messageArrived(String topic, MqttMessage message) throws Exception {    //모든 메시지가 올때 Callback method
//                if (topic.equals("devs/DEV1")) {     //topic 별로 분기처리하여 작업을 수행할수도있음
//                    String msg = new String(message.getPayload());
//                    //resultView.setText(msg);
//                    Log.e("arrive message : ", msg);
//                }
//                else if(topic.equals("devs/DEV2")){
//
//                }
//                else if(topic.equals("devs/DEV3")){
//
//                }
//                else if(topic.equals("devs/DEV4")){
//
//                }
//
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken token) {
//            }
//        });
        return 0;
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
