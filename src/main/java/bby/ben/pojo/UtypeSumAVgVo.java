package bby.ben.pojo;

import java.util.List;

public class UtypeSumAVgVo {
    private List<String> usertype;
    private List<Integer> sum_triduration;
    private List<Integer>  sum_num;
    private List<Integer>  avg;

    public UtypeSumAVgVo(List<String> usertype, List<Integer> sum_triduration, List<Integer> sum_num, List<Integer> avg) {
        this.usertype = usertype;
        this.sum_triduration = sum_triduration;
        this.sum_num = sum_num;
        this.avg = avg;
    }

    public UtypeSumAVgVo() {
    }

    public List<String> getUsertype() {
        return usertype;
    }

    public void setUsertype(List<String> usertype) {
        this.usertype = usertype;
    }

    public List<Integer> getSum_triduration() {
        return sum_triduration;
    }

    public void setSum_triduration(List<Integer> sum_triduration) {
        this.sum_triduration = sum_triduration;
    }

    public List<Integer> getSum_num() {
        return sum_num;
    }

    public void setSum_num(List<Integer> sum_num) {
        this.sum_num = sum_num;
    }

    public List<Integer> getAvg() {
        return avg;
    }

    public void setAvg(List<Integer> avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "Req3Vo{" +
                "usertype=" + usertype +
                ", sum_triduration=" + sum_triduration +
                ", sum_num=" + sum_num +
                ", avg=" + avg +
                '}';
    }
}
