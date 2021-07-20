package bby.ben.service;

import bby.ben.bbytool.MyTool;
import bby.ben.dao.RealtimeDao;
import bby.ben.pojo.BikesLivelyData;
import bby.ben.pojo.StationNTData;
import cn.hutool.db.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RealtimeService {
    private RealtimeDao realtimeDao=new RealtimeDao();
    /**
     * @description: 获取hour小时内sid站点的可用车数量变化
     * @param sid 站点id
     * @param hour 当前时间向前多少小时
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/16 15:51
     */
    public StationNTData findNbaBySid(String sid, int hour){
        List<Entity> list = realtimeDao.listNbaBySid(sid, hour);
        List<String> xData=new ArrayList<>();
        List<Integer> yData=new ArrayList<>();
        if (list==null){
            MyTool.log("findNbaBySid() 查询数据库错误！");
            return null;
        }
        for (Entity entity :list){
            xData.add(entity.getStr("created_time"));
            yData.add(entity.getInt("num_bikes_available"));
        }

        return new StationNTData(xData,yData);
    }
    /**
     * @description: 获取hour小时内sid站点的可用桩数量变化
     * @param sid 站点id
     * @param hour 当前时间向前多少小时
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/16 20:57
     */
    public StationNTData findNdaBySid(String sid, int hour){
        List<Entity> list = realtimeDao.listNdaBySid(sid, hour);
        List<String> xData=new ArrayList<>();
        List<Integer> yData=new ArrayList<>();
        if (list==null){
            MyTool.log("findNdaBySid() 查询数据库错误！");
            return null;
        }
        for (Entity entity :list){
            xData.add(entity.getStr("created_time"));
            yData.add(entity.getInt("num_docks_available"));
        }

        return new StationNTData(xData,yData);
    }
    /**
     * @description: 获取hour小时内sid站点的可用电动车数量变化
     * @param sid 站点id
     * @param hour 当前时间向前多少小时
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/17 14:40
     */
    public StationNTData findNeaBySid(String sid, int hour){
        List<Entity> list = realtimeDao.listNeaBySid(sid, hour);
        List<String> xData=new ArrayList<>();
        List<Integer> yData=new ArrayList<>();
        if (list==null){
            MyTool.log("findNeaBySid() 查询数据库错误！");
            return null;
        }
        for (Entity entity :list){
            xData.add(entity.getStr("created_time"));
            yData.add(entity.getInt("num_ebikes_available"));
        }

        return new StationNTData(xData,yData);
    }
    /**
     * @description: 获取minute分钟内没有车的站点列表
     * @param minute 向前推进分钟数
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/17 10:54
     */
    public List<String> getNoBikeList(int minute){
        List<Entity> list = realtimeDao.listNoBike(minute);
        List<String> data=new ArrayList<>();
        if (list==null){
            MyTool.log("getNoBikeList() 查询数据库错误！");
            return null;
        }
        for (Entity entity :list){
            data.add(entity.getStr("station_id"));
        }
        return data;
    }
    /**
     * @description: 获取hour小时内各站点的进出流量
     * @param hour 计算时间
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/17 13:22
     */
    public  HashMap<String, BikesLivelyData> getLivelyIOList(int hour){
        List<Entity> list = realtimeDao.listNumBikes(hour);

        if (list==null){
            MyTool.log("getLivelyIOList() 查询数据库错误！");
            return null;
        }

        HashMap<String, BikesLivelyData> data=new HashMap<>();
        String station_id="";
        int bikes=0;int sub=0;
        for (Entity entity :list){
            station_id=entity.getStr("station_id");
            bikes=entity.getInt("num_bikes_available")+entity.getInt("num_ebikes_available");
            if (data.get(station_id)==null){
                data.put(station_id,new BikesLivelyData(bikes,station_id));//新建记录
                continue;
            }
            BikesLivelyData station = data.get(station_id);
            //else
            sub=bikes-station.last;
            if (sub>0)//当前比前面大,流入了
                station.outAdd(sub);
            else
                station.inAdd(-sub);
            station.last=bikes;//记录这次的车数，用以下次判断
        }
        return data;
    }
    public List<BikesLivelyData> getsLivelyListByTop(int hour,int top){
        HashMap<String, BikesLivelyData> data=getLivelyIOList(hour);
        if (data==null) return null;

        List<BikesLivelyData> ioList=new ArrayList<>();
        data.forEach((station_id,station)->ioList.add(station));
        ioList.sort((s1,s2)->s2.in+s2.out-s1.in-s1.out);
        int len=ioList.size()<top? ioList.size() : top;
        return ioList.subList(0,len);
    }
}
