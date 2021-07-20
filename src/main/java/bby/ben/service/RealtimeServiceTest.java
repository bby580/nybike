package bby.ben.service;

import bby.ben.pojo.BikesLivelyData;
import bby.ben.pojo.StationNTData;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class RealtimeServiceTest {

//    @Test
    public void findNbaBySid() {
        HashMap<String,Integer> out=new HashMap<>();
        System.out.println(out.get("1234"));
    }
    @Test
    public void getsLivelyListByTop() {
        RealtimeService realtimeService=new RealtimeService();
        List<BikesLivelyData> list = realtimeService.getsLivelyListByTop(1, 100);
        System.out.println(list);
    }
}