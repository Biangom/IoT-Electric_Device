package com.example.iot_electronic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    }
}
