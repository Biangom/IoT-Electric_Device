package com.example.iot_electronic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import butterknife.OnClick;

public class InputSettingDialogBox extends DialogFragment {

    Switch switch2;
    Switch switch1;
    Switch switch3;
    Switch switch4;
    Switch switch5;
    Switch switch6;
    Switch switch7;
    Switch switch8;

    Button completeBtn;

    private Fragment fragment;


    //
//    @OnClick(R.id.connectTryBtn)
//    public void onViewClicked() {
//    }
//
//    // 이 Dialog가 완료 될떄 하는 행위 정의.
//    // 이 dialog는 temp객체를 넘겨주게 된다.
//    // MainActivity에서 overriding 해서 데이터를 받을 예정.
    public interface OnCompleteListener {
        void onInputedData(DeviceData temp);
    }
//
    // 이 다이얼로그를 호출한 액티비티에게
    // 전달할 데이터
    private InputSettingDialogBox.OnCompleteListener mCallback;
//
//    // 자신의 값을 셋팅.
    public void setDialogResult(OnCompleteListener result) {
        mCallback = result;
    }


    // Dialog가 생성될때 실행되는 함수 정의
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // dialog를 설정해주는 코드
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_input_set_remote, null); // view를 dialog_login.xml로 설정
        builder.setView(view);

        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("SettingDialog");

        switch1 = (Switch) view.findViewById(R.id.switch1);
        switch2 = (Switch) view.findViewById(R.id.switch2);
        switch3 = (Switch) view.findViewById(R.id.switch3);
        switch4 = (Switch) view.findViewById(R.id.switch4);
        switch5 = (Switch) view.findViewById(R.id.switch5);
        switch6 = (Switch) view.findViewById(R.id.switch6);
        switch7 = (Switch) view.findViewById(R.id.switch7);
        switch8 = (Switch) view.findViewById(R.id.switch8);

        completeBtn = (Button) view.findViewById(R.id.completeBtn);


        // '닫기' 기능해 해당하는 버튼을 눌렀을 때
        //  실행되는 로직 정의
        completeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 먼저 전달할 client 객체를 선언, 그 정보를 담는다.
                DeviceData deviceData = new DeviceData(
                        switch1.isChecked(),
                        switch2.isChecked(),
                        switch3.isChecked(),
                        switch4.isChecked(),
                        switch5.isChecked(),
                        switch6.isChecked(),
                        switch7.isChecked(),
                        switch8.isChecked()
                );


                mCallback.onInputedData(deviceData);
                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismiss();
            }
        });
        return builder.create();
    }

//    EditText nameInputText;
//    EditText portInputText;
//    EditText ipInputText;
//
//    Button connectTryBtn;
//
//    private Fragment fragment;
//
//    @OnClick(R.id.connectTryBtn)
//    public void onViewClicked() {
//    }
//
//    // 이 Dialog가 완료 될떄 하는 행위 정의.
//    // 이 dialog는 temp객체를 넘겨주게 된다.
//    // MainActivity에서 overriding 해서 데이터를 받을 예정.
//    public interface OnCompleteListener {
//        void onInputedData(Client temp);
//    }
//
//    // 이 다이얼로그를 호출한 액티비티에게
//    // 전달할 데이터
//    private InputServerDialogBox.OnCompleteListener mCallback;
//
//    // 자신의 값을 셋팅.
//    public void setDialogResult(InputServerDialogBox.OnCompleteListener result) {
//        mCallback = result;
//    }
//
//
//    // Dialog가 생성될때 실행되는 함수 정의
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // dialog를 설정해주는 코드
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_input_server, null); // view를 dialog_login.xml로 설정
//        builder.setView(view);
//
//        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
//
//        connectTryBtn = (Button) view.findViewById(R.id.connectTryBtn) ;
//        nameInputText = (EditText) view.findViewById(R.id.nameInputText);
//        ipInputText = (EditText) view.findViewById(R.id.ipInputText);
//        portInputText = (EditText) view.findViewById(R.id.portInputText);
//
//
//        // '닫기' 기능해 해당하는 버튼을 눌렀을 때
//        //  실행되는 로직 정의
//        connectTryBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Log.d("3", "connect Try btn");
//                // 먼저 전달할 client 객체를 선언, 그 정보를 담는다.
//                Client client = new Client(
//                        nameInputText.getText().toString(),
//                        ipInputText.getText().toString(),
//                        portInputText.getText().toString()
//                );
//
//                mCallback.onInputedData(client);
//                DialogFragment dialogFragment = (DialogFragment) fragment;
//                dialogFragment.dismiss();
//            }
//        });
//        return builder.create();
//    }


}
