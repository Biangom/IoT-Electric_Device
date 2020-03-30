package com.example.iot_electronic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class InputServerDialogBox extends DialogFragment {
    @BindView(R.id.nameInputText)
    EditText nameInputText;
    @BindView(R.id.portInputText)
    EditText portInputText;
    @BindView(R.id.ipInputText)
    EditText ipInputText;
    @BindView(R.id.connectTryBtn)
    Button connectTryBtn;

    // 이 Dialog가 완료 될떄 하는 행위 정의, 이 dialog는 BtDevice 객체를 넘겨주게 된다.
//    public interface OnCompleteListener{
//        void onInputedData(BtDevice temp);
//    }
//    private OnCompleteListener mCallback;

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mCallback = (OnCompleteListener) activity;
//        }
//        catch (ClassCastException e) {
//            Log.d("DialogFragmentExample", "Activity doesn't implement the OnCompleteListener interface");
//        }
//    }

    // Dialog가 생성될때 실행되는 함수 정의
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // dialog를 설정해주는 코드
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_input_server, null); // view를 dialog_login.xml로 설정
        builder.setView(view);


        // 완료(클릭)를 눌렀을 때 하는 로직 정의
//        submit.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // 먼저 temp객체를 선언하여, 그 객체에 정보를 담는다.
//                // 블루투스 디바이스 이름과, 유저 이름을 담을 것이므로 temp의 속성값에 셋팅해준다.
//                BtDevice temp = new BtDevice();
//            temp.btName = btName.getText().toString();
//            temp.userName = userName.getText().toString();
//            temp.isEncountering = false;
//            temp.count = 0;
//            dismiss();
//                mCallback.onInputedData(temp);
//            // 전송한다.
//        }
//        });
        return builder.create();
    }

    @OnClick({R.id.nameInputText, R.id.portInputText, R.id.ipInputText, R.id.connectTryBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nameInputText:
                break;
            case R.id.portInputText:
                break;
            case R.id.ipInputText:
                break;
            case R.id.connectTryBtn:
                break;
        }
    }

}
