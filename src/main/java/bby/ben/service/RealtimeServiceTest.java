package bby.ben.service;

import bby.ben.pojo.BikesLivelyData;
import bby.ben.pojo.StationNTData;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RealtimeServiceTest {

    @Test
    public void findNbaBySid() {
        RealtimeService service=new RealtimeService();
        long now=new Date().getTime();
        StationNTData list = service.findNbaBySid("72", now - 600000, now);
        System.out.println(list);
    }
    @Test
    public void getsLivelyListByTop() {
        RealtimeService realtimeService=new RealtimeService();
        List<BikesLivelyData> list = realtimeService.getsLivelyListByTop(1, 100);
        System.out.println(list);
    }
}