package com.kss.iot_electronic;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Data implements Serializable {
    String name;
    String value;
    LocalDateTime time;

    public Data() {

    }

    public Data(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 일단은 메시지가 도착한 시간으로 하자..

    public Data(String name, String value, LocalDateTime localDateTime) {
        this.name = name;
        this.value = value;
        this.time = localDateTime;
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", time=" + time +
                '}';
    }
}
