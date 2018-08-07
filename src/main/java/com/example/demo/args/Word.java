package com.example.demo.args;

/**
 * Created by Naq on 2018/8/7.
 */
public class Word {
    String province;
    String city;
    String wordone;

    public Word(String province, String city, String wordone) {
        this.province = province;
        this.city = city;
        this.wordone = wordone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWordone() {
        return wordone;
    }

    public void setWordone(String wordone) {
        this.wordone = wordone;
    }
}
