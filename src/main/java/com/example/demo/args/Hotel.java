package com.example.demo.args;

/**
 * Created by Naq on 2018/8/6.
 */
public class Hotel {

    String province;
    String city;
    String wordone;
    String wordtwo;
    String finalword;

    public Hotel(String province, String city, String wordone, String wordtwo, String finalword) {
        this.province = province;
        this.city = city;
        this.wordone = wordone;
        this.wordtwo = wordtwo;
        this.finalword = finalword;
    }

    public String getFinalword() {
        return finalword;
    }

    public void setFinalword(String finalword) {
        this.finalword = finalword;
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

    public String getWordtwo() {
        return wordtwo;
    }

    public void setWordtwo(String wordtwo) {
        this.wordtwo = wordtwo;
    }
}
