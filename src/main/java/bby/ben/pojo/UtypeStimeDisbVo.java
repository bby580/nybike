package bby.ben.pojo;

import java.util.List;

public class UtypeStimeDisbVo {
    private List<String> cus_Data;
    private List<String> anu_Data;

    public UtypeStimeDisbVo(List<String> cus_Data, List<String> anu_Data) {
        this.cus_Data = cus_Data;
        this.anu_Data = anu_Data;
    }

    public UtypeStimeDisbVo() {
    }

    public List<String> getCus_Data() {
        return cus_Data;
    }

    public void setCus_Data(List<String> cus_Data) {
        this.cus_Data = cus_Data;
    }

    public List<String> getAnu_Data() {
        return anu_Data;
    }

    public void setAnu_Data(List<String> anu_Data) {
        this.anu_Data = anu_Data;
    }

    @Override
    public String toString() {
        return "Req2Vo{" +
                "cus_Data=" + cus_Data +
                ", anu_Data=" + anu_Data +
                '}';
    }
}
