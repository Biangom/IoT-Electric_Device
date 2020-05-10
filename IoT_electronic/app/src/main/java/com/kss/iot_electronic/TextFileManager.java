package com.kss.iot_electronic;

import android.content.Context;
import android.util.Log;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 * Created by kss78 on 2018-04-20.
 */

public class TextFileManager {
    private static final String FILE_NAME = "log.txt";
    private File folder; // 파일 객체
    private String folderPath;  // 폴더 경로를 담을 string
    private String filePath;    // 파일 경로를 담을 string(파일포함)

    public TextFileManager(Context context) {
        // 외부 공용 디렉토리 중 Download 디렉토리에 대한 File 객체 얻음
        // log파일은 참고로 Downloads폴더에 저장되어있다.

        // 절대 경로 String 값을 얻음
        //folderPath = folder.getAbsolutePath();
        //folderPath = "iotelect";
        //Log.d("SEONGSIK",folderPath);
        // 로그 파일 절대 경로 생성 (String 값)
//        filePath = folderPath + "/" + FILE_NAME;


        folder = new File(context.getFilesDir(), "log.bin");
        filePath = folder.getPath();
        Log.d("SEONGSIK",filePath);
    }

    // 파일에 문자열 데이터를 쓰는 메소드
    public void save(Data data) {
        if (data == null) {
            return;
        }
        FileOutputStream fos;
        try {
            Log.d("SEONGSIK",data.toString());
            Log.d("SEONGSIK","writeOK");
            fos = new FileOutputStream(filePath, true); // 두번째 매개변수가 true이면 append 모드로 쓰기
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);

            oos.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일에서 데이터를 읽고 문자열 데이터로 반환하는 메소드
    public ArrayList<Data> load() {
        try {
            // 로그 파일의 절대 경로를 이용하여 File 객체 생성
            File log = new File(filePath);
            ArrayList<Data> list = new ArrayList<Data>();
            // File 객체를 이용하여 해당 파일이 실제로 존재하는지 검사
            if(log.exists()) {
                Log.d("SEONGSIK","loadOK");
                // 파일이 존재하는 경우 읽기 수행
                FileInputStream fis = new FileInputStream(log);
                ObjectInputStream ois = new ObjectInputStream(fis);

                int i = 1;
                while(true) {
                    Log.d("SEONGSIK", i++ + "    ################");
                    try {
                        Data data = (Data) ois.readObject();
                        if(data == null) break;
                        list.add(data);
                    }
                    catch (EOFException e) {
                        ois.close();
                        fis.close();
                        e.printStackTrace();
                        break;
                    }
                }
                ois.close();
                fis.close();
                return list;
            } else {
                Log.i("FileManager", log.getName() + " file does not exist");
            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 파일 삭제 메소드
    public boolean delete() {
        // 로그 파일의 절대 경로를 이용하여 File 객체 생성
        File log = new File(filePath);
        try {
            // File 객체를 이용하여 해당 파일 삭제
            boolean result = log.delete();

            if (result) {
                // file is successfully deleted
                Log.i("FileManager", log.getName() + " successfully deleted");
                return true;
            } else {
                Log.i("FileManager", "delete failed");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}