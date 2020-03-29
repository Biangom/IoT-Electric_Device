package com.example.iot_electronic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.connectTry)
    Button connectTry;
    @BindView(R.id.signalView)
    Button signalView;

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

                DialogFragment newFragment = new InputServerDialogBox();
                newFragment.show(getSupportFragmentManager(), "dialog"); //"dialog"라는 태그를 갖는 프래그먼트를 보여준다.

//                AlertDialog.Builder builder = new AlertDialog.Builder();
//                AlertDialog alertDialog = builder.create();
//
//                alertDialog.show();
                break;
            case R.id.signalView:


                break;
        }
    }
}
