package com.example.empty_project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.DatagramSocket;

public class MainActivity extends AppCompatActivity {

    static TextView resultView = null;
    static private MqttClient sampleClient;
    static private TextView editText;
    static private int qos;
    static private String topic;
    static private MemoryPersistence persistence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = findViewById(R.id.resultView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            sampleClient.disconnect();
            System.out.println("Disconnected");

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // Button.OnclickListener를 implements하므로 onClick() 함수를 오버라이딩.

    public void onClick(View view) {
        resultView = (TextView) findViewById(R.id.resultView);
        editText = findViewById(R.id.editText);

        switch (view.getId()) {
            case R.id.sendButton:
                topic       = "MQTT Examples";
                qos         = 0;
                String broker       = "tcp://m16.cloudmqtt.com:14593";
                String clientId     = "TestName";
                persistence = new MemoryPersistence();

                try {
                    sampleClient = new MqttClient(broker, clientId, persistence);

                    MqttConnectOptions connOpts = new MqttConnectOptions();
                    connOpts.setCleanSession(true);
                    connOpts.setUserName("uztrfyhg");
                    connOpts.setPassword("6Zi8XPdWFqed".toCharArray());
                    System.out.println("Connecting to broker: "+broker);
                    sampleClient.connect(connOpts);
                    if(sampleClient.isConnected()) {
                        System.out.println("###client complete=d");
                    }
                    System.out.println("Connected");
                    String content;
                    content = editText.getText().toString();
                    //resultView.setText("Red");
                    MqttMessage message = new MqttMessage(content.getBytes());

                    message.setQos(qos);
                    sampleClient.publish(topic, message);
                    System.out.println("Publishing message: "+content);
                    System.out.println("Message published");
                    //textView1.setBackgroundColor(Color.rgb(255, 0, 0));
                    break ;

                } catch(MqttException me) {
                    System.out.println("reason "+me.getReasonCode());
                    System.out.println("msg "+me.getMessage());
                    System.out.println("loc "+me.getLocalizedMessage());
                    System.out.println("cause "+me.getCause());
                    System.out.println("excep "+me);
                    me.printStackTrace();
                }


//            case R.id.buttonGreen :
//                textView1.setText("Green") ;
//                textView1.setBackgroundColor(Color.rgb(0, 255, 0));
//                break ;
//            case R.id.buttonBlue :
//                textView1.setText("Blue") ;
//                textView1.setBackgroundColor(Color.rgb(0, 0, 255));
//                break ;
        }

    }

}
