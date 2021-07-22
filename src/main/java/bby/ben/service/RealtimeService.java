package bby.ben.service;

import bby.ben.bbytool.MyTool;
import bby.ben.dao.RealtimeDao;
import bby.ben.pojo.*;
import cn.hutool.db.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
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
    public StationNTData findNbaBySid(int sid, int hour){
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
     * @description: 获取 start-end 时间段内sid站点的可用车数量变化
     * @param sid 站点id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/21 16:29
     */
    public StationNTData findNbaBySid(int sid, long startTime,long endTime){
        List<Entity> list = realtimeDao.listNbaBySid(sid, new Timestamp(startTime),new Timestamp(endTime));//date是java.sql.Date;
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
    public StationNTData findNdaBySid(int sid, int hour){
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
    public StationNTData findNdaBySid(int sid,long startTime,long endTime){
        List<Entity> list = realtimeDao.listNdaBySid(sid, new Timestamp(startTime),new Timestamp(endTime));
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
     * @description: 查损坏数
     * @param
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/23 0:17
     */
    public StationNTData findNbdBySid(int sid,long startTime,long endTime){
        List<Entity> list = realtimeDao.listNbdBySid(sid, new Timestamp(startTime),new Timestamp(endTime));
        List<String> xData=new ArrayList<>();
        List<Integer> yData=new ArrayList<>();
        if (list==null){
            MyTool.log("findNbdBySid() 查询数据库错误！");
            return null;
        }
        for (Entity entity :list){
            xData.add(entity.getStr("created_time"));
            yData.add(entity.getInt("num_bikes_disabled"));
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
    public StationNTData findNeaBySid(int sid, int hour){
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
        long last_reported=0;
        for (Entity entity :list){
            station_id=entity.getStr("station_id");
            bikes=entity.getInt("num_bikes_available")+entity.getInt("num_ebikes_available");
            last_reported=entity.getLong("last_reported");

            if (data.get(station_id)==null){
                data.put(station_id,new BikesLivelyData(bikes,station_id,last_reported));//新建记录
                continue;
            }
            BikesLivelyData station = data.get(station_id);
            //else
            sub=bikes-station.last;
            if (sub>0)//当前比前面大,流入了
                station.outAdd(sub);
            else if (sub<0)
                station.inAdd(-sub);
            else {
                if(station.last_reported!=last_reported){//单车数不变，但是最后还车数变了
                        station.inAdd(1);
                        station.outAdd(1);
                }
            }
            station.last_reported=last_reported;
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

    public UtypeStimeDisbVo getReq2(int day){
        List<Entity> list=realtimeDao.req2(day);
        List<String> anu_Data = new ArrayList<>();
        List<String> cus_Data = new ArrayList<>();
        for(Entity entity:list){
            if(entity.getStr("usertype").equals("Subscriber"))
                anu_Data.add(entity.getStr("starttime"));
            else
                cus_Data.add(entity.getStr("starttime"));
        }
        UtypeStimeDisbVo vo= new UtypeStimeDisbVo(cus_Data,anu_Data);
        return vo;
    }
    public UtypeSumAVgVo getReq3(Timestamp start, Timestamp end){
        List<Entity> list=realtimeDao.req3(start,end);
        List<String> usertype=new ArrayList<>();
        List<Integer> sum_triduration=new ArrayList<>();
        List<Integer> sum_num=new ArrayList<>();
        List<Integer> avg=new ArrayList<>();
        for(Entity entity:list){
            usertype.add(entity.getStr("usertype"));
            sum_triduration.add(entity.getInt("sum(tripduration)"));
            sum_num.add(entity.getInt("count(*)"));
            avg.add(entity.getInt("avg(tripduration)"));
        }
        UtypeSumAVgVo vo= new UtypeSumAVgVo(usertype,sum_triduration,sum_num,avg);
        return vo;
    }
    public HotStartEndVo getReq4(String type, Timestamp start, Timestamp end){
        List<Entity> list=realtimeDao.req4_s(start, end);
        if(type.equals("start")) {
            list = realtimeDao.req4_s(start, end);
        }
        else{
            list = realtimeDao.req4_e(start, end);
        }
        List<String> station_id=new ArrayList<>();
        List<String> act=new ArrayList<>();
        for(Entity entity:list){
            station_id.add(entity.getStr("start_station_id"));
            act.add(entity.getStr("act"));
        }
        HotStartEndVo vo=new HotStartEndVo(station_id,act);
        return vo;
    }
}
