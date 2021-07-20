package bby.ben.pojo;

import java.util.ArrayList;
import java.util.List;

public class StationNTData {
    private List<String> xData;
    private List<Integer> yData;

    public StationNTData(List<String> xData, List<Integer> yData) {
        this.xData = xData;
        this.yData = yData;
    }

    public StationNTData() {
        xData=new ArrayList<>();
        yData=new ArrayList<>();
    }

    public void setxData(List<String> xData) {
        this.xData = xData;
    }

    public void setyData(List<Integer> yData) {
        this.yData = yData;
    }

    public List<String> getxData() {
        return xData;
    }

    public List<Integer> getyData() {
        return yData;
    }

    @Override
    public String toString() {
        return "StationNTData{" +
                "xData=" + xData +
                ", yData=" + yData +
                '}';
    }
}
