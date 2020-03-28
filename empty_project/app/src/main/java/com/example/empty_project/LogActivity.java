package com.example.empty_project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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


    @BindView(R.id.textView1)
    TextView textView1;
    boolean toggle1 = false;

    @BindView(R.id.textView2)
    TextView textView2;
    boolean toggle2 = false;

    @BindView(R.id.textView3)
    TextView textView3;
    boolean toggle3 = false;

    @BindView(R.id.textView4)
    TextView textView4;
    boolean toggle4 = false;

    @BindView(R.id.toggleBtn3)
    Button toggleBtn3;
    @BindView(R.id.toggleBtn4)
    Button toggleBtn4;

    @BindView(R.id.switch1)
    Switch switch1;
    @BindView(R.id.switch2)
    Switch switch2;
    @BindView(R.id.switch3)
    Switch switch3;
    @BindView(R.id.switch4)
    Switch switch4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);


        //
        // Set SwitchButton
        //
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if(isChecked) {
                        // subscribe하고 싶을 때
                        // 초기값은 false였음
                        mqttAndroidClient.subscribe("devs/dev1", 0);
                    }
                    else {
                        // unsubscribe하고 싶을 대
                        mqttAndroidClient.unsubscribe("devs/dev1");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if(isChecked) {
                        mqttAndroidClient.subscribe("devs/dev2", 0);
                    }
                    else {
                        mqttAndroidClient.unsubscribe("devs/dev2");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if(isChecked) {
                        // subscribe
                        // 하고 싶을 때
                        // 초기값은 false였음
                        mqttAndroidClient.subscribe("devs/dev3", 0);
                        //logText.setText("subscribe");
                    }
                    else {
                        // unsubscribe하고 싶을 대
                        mqttAndroidClient.unsubscribe("devs/dev3");
                        //logText.setText("unsubscribe");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if(isChecked) {
                        // subscribe하고 싶을 때
                        // 초기값은 false였음
                        mqttAndroidClient.subscribe("devs/dev4", 0);
                        //logText.setText("subscribe");
                    }
                    else {
                        // unsubscribe하고 싶을 대
                        mqttAndroidClient.unsubscribe("devs/dev4");
                        //logText.setText("unsubscribe");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });


        //
        // setup 초기 설정
        //
        try {
            mqttAndroidClient.unsubscribe("devs/dev1");
            mqttAndroidClient.unsubscribe("devs/dev2");
            mqttAndroidClient.unsubscribe("devs/dev3");
            mqttAndroidClient.unsubscribe("devs/dev4");
        } catch (MqttException e) {
            e.printStackTrace();
        }

        //
        // Set Callback
        //
        mqttAndroidClient.setCallback(new MqttCallback() {  //클라이언트의 콜백을 처리하는부분
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {    //모든 메시지가 올때 Callback method
                //String msg = new String(message.getPayload());
                if (topic.equals("devs/dev1")) {     //topic 별로 분기처리하여 작업을 수행할수도있음
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

    @OnClick({R.id.sendBtn, R.id.toggleBtn2, R.id.toggleBtn1, R.id.toggleBtn3, R.id.toggleBtn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendBtn:

                try {
                    if (!mqttAndroidClient.isConnected()) {
                        System.out.println("**** disconnected!!!! ***");
                    }
                    mqttAndroidClient.publish("devs/temper", "1000".getBytes(), 0, false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.toggleBtn1:
                try {
                    if (toggle1) {
                        // subscribe하고 싶을 때
                        // 초기값은 false였음
                        toggle1 = !toggle1;
                        mqttAndroidClient.subscribe("devs/dev1", 0);
                    } else {
                        // unsubscribe하고 싶을 대
                        toggle1 = !toggle1;
                        mqttAndroidClient.unsubscribe("devs/dev1");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.toggleBtn2:
                try {
                    if (toggle2) {
                        // subscribe하고 싶을 때
                        // 초기값은 false였음
                        toggle2 = !toggle2;
                        mqttAndroidClient.subscribe("devs/dev2", 0);
                    } else {
                        // unsubscribe하고 싶을 대
                        toggle2 = !toggle2;
                        mqttAndroidClient.unsubscribe("devs/dev2");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.toggleBtn3:
                try {
                    if (toggle3) {
                        // subscribe하고 싶을 때
                        // 초기값은 false였음
                        toggle3 = !toggle3;
                        mqttAndroidClient.subscribe("devs/dev3", 0);
                    } else {
                        // unsubscribe하고 싶을 대
                        toggle3 = !toggle3;
                        mqttAndroidClient.unsubscribe("devs/dev3");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.toggleBtn4:
                try {
                    if (toggle4) {
                        // subscribe하고 싶을 때
                        // 초기값은 false였음
                        toggle4 = !toggle4;
                        mqttAndroidClient.subscribe("devs/dev4", 0);
                    } else {
                        // unsubscribe하고 싶을 대
                        toggle4 = !toggle4;
                        mqttAndroidClient.unsubscribe("devs/dev4");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.switch1:
                logText.setText("1");
                break;
            case R.id.switch2:
                logText.setText("2");
                break;
            case R.id.switch3:
                logText.setText("3");
                break;
            case R.id.switch4:
                logText.setText("4");
                break;

        }
    }
}
