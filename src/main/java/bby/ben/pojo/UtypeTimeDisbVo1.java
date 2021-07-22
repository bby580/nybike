package bby.ben.pojo;

import java.util.List;

public class UtypeTimeDisbVo1 {
    private List<Integer> anuData;
    private List<Integer> cusData;
//    private List<String> anuDatatime;
//    private List<String> cusDatatime;


    public UtypeTimeDisbVo1(List<Integer> anuData, List<Integer> cusData) {
        this.anuData = anuData;
        this.cusData = cusData;
    }

    public UtypeTimeDisbVo1() {
    }

    public List<Integer> getAnuData() {
        return anuData;
    }

    public void setAnuData(List<Integer> anuData) {
        this.anuData = anuData;
    }

    public List<Integer> getCusData() {
        return cusData;
    }

    public void setCusData(List<Integer> cusData) {
        this.cusData = cusData;
    }

    @Override
    public String toString() {
        return "Req2VoNum{" +
                "anuData=" + anuData +
                ", cusData=" + cusData +
                '}';
    }
}
