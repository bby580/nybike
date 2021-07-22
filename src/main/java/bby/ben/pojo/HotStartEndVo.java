package bby.ben.pojo;

import java.util.List;

public class HotStartEndVo {
    private List<String> station_id;
    private List<String> act;

    public HotStartEndVo(List<String> station_id, List<String> act) {
        this.station_id = station_id;
        this.act = act;
    }

    public HotStartEndVo() {
    }

    public List<String> getStation_id() {
        return station_id;
    }

    public void setStation_id(List<String> station_id) {
        this.station_id = station_id;
    }

    public List<String> getAct() {
        return act;
    }

    public void setAct(List<String> act) {
        this.act = act;
    }

    @Override
    public String toString() {
        return "HotStartEnd{" +
                "station_id=" + station_id +
                ", act=" + act +
                '}';
    }
}
