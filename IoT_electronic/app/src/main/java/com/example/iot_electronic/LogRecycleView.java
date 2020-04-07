package com.example.iot_electronic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.iot_electronic.MainActivity.mqttAndroidClient;

public class LogRecycleView extends AppCompatActivity {
    @BindView(R.id.settingBtn)
    Button settingBtn;
    @BindView(R.id.log_item_recycler_view)
    RecyclerView logItemRecyclerView;
    @BindView(R.id.alarmSwtich)
    Switch alarmSwtich;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Data> list;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_recycle_view);
        ButterKnife.bind(this);
        recyclerView = (RecyclerView) findViewById(R.id.log_item_recycler_view);
        list = new ArrayList<Data>();

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        // 알림 만들기
        // notification 서비스 갖고오기
        NotificationChannel notificationChannel = new NotificationChannel("Chanel_id_1", "Chanel_name", NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel
                // 26 이상부터는 이것을 해주어야함.
                .setVibrationPattern(new long[]{1000, 1000});

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);


        builder = new NotificationCompat.Builder(this, "Chanel_id_1");

        // 알림창 제목
        // 테스트 용이었음
        //builder.setContentTitle("알림");

        // 알림창 메시지
        // 이것도 테스트 용이었음
        //builder.setContentText("알림 메시지");

        // 알림창 아이콘
        builder.setSmallIcon(R.drawable.ic_launcher_background);

        // 알림창 터치시 상단 알림상태에서 알림이 자동으로 삭제되게 합니다.
        builder.setAutoCancel(true);

        builder.setSmallIcon(R.drawable.brand);

        // pandingIntent를 builder에 설정 해줍니다.
        // 알림창 터치시 인텐트가 전달할 수 있도록 해줍니다.
        // builder.setContentIntent(pendingIntent);


        // 메시지가 도달할때 처리하는 로직 정의
        // 이 함수들이 중요함.
        mqttAndroidClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {    //모든 메시지가 올때 Callback method
                String msg = new String(message.getPayload());
                String dev = "none";
                if (topic.equals("devs/DEV1")) {     //topic 별로 분기처리하여 작업을 수행할수도있음
                    Log.e("arrive message DEV1 : ", msg);
                    dev = "DEV1";
                } else if (topic.equals("devs/DEV2")) {
                    Log.e("arrive message DEV2 : ", msg);
                    dev = "DEV2";
                } else if (topic.equals("devs/DEV3")) {
                    Log.e("arrive message DEV3 : ", msg);
                    dev = "DEV3";
                } else if (topic.equals("devs/DEV4")) {
                    Log.e("arrive message DEV4 : ", msg);
                    dev = "DEV4";
                } else if (topic.equals("devs/DEV5")) {
                    Log.e("arrive message DEV4 : ", msg);
                    dev = "DEV5";
                } else if (topic.equals("devs/DEV6")) {
                    Log.e("arrive message DEV4 : ", msg);
                    dev = "DEV6";
                } else if (topic.equals("devs/DEV7")) {
                    Log.e("arrive message DEV4 : ", msg);
                    dev = "DEV7";
                } else if (topic.equals("devs/DEV8")) {
                    Log.e("arrive message DEV4 : ", msg);
                    dev = "DEV8";
                } else {
                    return;
                }
                // 알림아이콘 호출
                builder.setContentTitle(dev);
                builder.setContentText(msg);
                // vibrator 함수로 진동을 발생할 수 있지만.
                // 그것은 백그라운드에서 돌아가지 않는다.
                // 그래서 Vibrarte를 setting 해준다.
                //builder.setVibrate(new long[]{1000, 1000}); // 오레오 이상부터는 안먹음

                if(alarmSwtich.isChecked()) {
                    Notification notification = builder.build();
                    // 알람 울리기
                    manager.notify(1, notification); // id는 channel임

                    // 진동 호출
                    // vibrator.vibrate(1000); // 0.5초간 진동
                }





                Data data = new Data(dev, msg, LocalDateTime.now());
                list.add(data);

                // mAdapter에게 Dataset이 changed() 되었다고 알린다
                // 그러면 Recycle View가 초기화된다.
                // 하지만 이러는건 너무 비효율적이다.
                // 그래도 딱히 방도가 없으니.. 이거라도 호출하자.
                mAdapter.notifyDataSetChanged();

                //mAdapter.notifyItemChanged(list.size()-1);

                for (int i = 0; i < list.size(); i++) {
                    Log.d("E", list.get(i).name + list.get(i).value);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        // specify an adapter (see also next example)
//        list = new ArrayList<>();
//        list.add(new Data("kss","1"));
//        list.add(new Data("pss","2"));

        mAdapter = new LogDataAdapter(list);
        recyclerView.setAdapter(mAdapter);
    }


    @OnClick({R.id.log_item_recycler_view, R.id.settingBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.log_item_recycler_view:
                break;
            case R.id.settingBtn:
                // dialog 뜨게하자.

//                InputServerDialogBox newFragment = new InputServerDialogBox();
//                newFragment.show(getSupportFragmentManager(), "dialog"); //"dialog"라는 태그를 갖는 프래그먼트를 보여준다.
//                newFragment.setDialogResult(new InputServerDialogBox.OnCompleteListener() {
//                    @Override
//                    public void onInputedData(Client client) {
//                        // 서버랑 연결 시도할거임
//                        connectClient = client;
//                        int ret = connectSerever(client.getClientId(), client.getIp(), client.getPort());
//                    }
//                });


                break;
        }
    }
}
