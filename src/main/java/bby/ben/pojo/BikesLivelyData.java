package bby.ben.pojo;

public class BikesLivelyData {
    public int out=0;
    public int in=0;
    public int last=0;
    public long last_reported=0;
    public String station_id;
    public BikesLivelyData(int last,String station_id,long last_reported){
        this.last=last;
        this.station_id=station_id;
        this.last_reported=last_reported;
    }
    public void outAdd(int num){
        out+=num;
    }
    public void inAdd(int num){
        in+=num;
    }
    @Override
    public String toString() {
        return "BikesLivelyData{" +
                "out=" + out +
                ", in=" + in +
                ", last=" + last +
                ", station_id='" + station_id + '\'' +
                '}';
    }
}
