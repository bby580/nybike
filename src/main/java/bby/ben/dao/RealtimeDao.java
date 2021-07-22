package bby.ben.dao;

import bby.ben.bbytool.DaoTool;
import bby.ben.bbytool.MyTool;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description: 实时数据的持久层类，查询1条：getXXXById,多条：listXXXByXXX
 * @author: 本小蛋
 * @time: 2021/7/16 14:07
 */
public class RealtimeDao {
    private DaoTool daoTool=new DaoTool();
    /**
     * @description:
     * @param sid 站点id
     * @param hour 当前时间向前多少小时
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/16 14:19
     */
    public List<Entity> listNbaBySid(int sid, int hour){
//        String sql="select num_bikes_available,created_time from t_status where station_id=? and created_time > DATE_SUB(NOW(),INTERVAL ? HOUR)";
        List<Entity> list=null;
        try {
            Calendar calendar=Calendar.getInstance();
            Timestamp now=new Timestamp(calendar.getTimeInMillis());
            calendar.add(Calendar.HOUR,-hour);
//            list=Db.use().query(sql,sid,hour);
            list=daoTool.getStationBySid("num_bikes_available,created_time",sid,new Timestamp(calendar.getTimeInMillis()),now);
            MyTool.log("listNbaBySid 运行时间：",new Date().getTime()-now.getTime());
        } catch (Exception throwables) {
            throwables.printStackTrace();
            MyTool.log("数据库读取出错，sid="+sid+",hour="+hour);
        }
        return list;
    }
    /**
     * @description:
     * @param sid 站点id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/21 16:27
     */
    public List<Entity> listNbaBySid(int sid, Timestamp startTime, Timestamp endTime){
        return daoTool.getStationBySid("station_id, num_bikes_available, created_time",sid,startTime,endTime);
    }
    //;

    /**
     * @description: 查询hour小时内sid站点的可用桩数量变化
     * @param sid 站点id
     * @param hour 当前时间向前多少小时
     * @return: 
     * @author: 本小蛋
     * @time: 2021/7/16 20:47
     */
    public List<Entity> listNdaBySid(int sid, int hour){
        Calendar calendar=Calendar.getInstance();
        Timestamp now=new Timestamp(calendar.getTimeInMillis());
        calendar.add(Calendar.HOUR,-hour);
        return daoTool.getStationBySid("num_docks_available,created_time",sid,new Timestamp(calendar.getTimeInMillis()),now);
    }

    public List<Entity> listNdaBySid(int sid, Timestamp startTime, Timestamp endTime) {
        return daoTool.getStationBySid("num_docks_available,created_time",sid,startTime,endTime);
    }
    /**
     * @description: 损坏单车
     * @param
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/23 0:14
     */
    public List<Entity> listNbdBySid(int sid, Timestamp startTime, Timestamp endTime) {
        return daoTool.getStationBySid("num_bikes_disabled,created_time",sid,startTime,endTime);
    }
    /**
     * @description: 查询hour小时内sid站点的可用电动车数量变化
     * @param sid 站点id
     * @param hour 当前时间向前多少小时
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/17 14:38
     */
    public List<Entity> listNeaBySid(int sid, int hour){
        Calendar calendar=Calendar.getInstance();
        Timestamp now=new Timestamp(calendar.getTimeInMillis());
        calendar.add(Calendar.HOUR,-hour);
        return daoTool.getStationBySid("num_ebikes_available,created_time",sid,new Timestamp(calendar.getTimeInMillis()),now);
    }
    public List<Entity> listNeaBySid(int sid, Timestamp startTime, Timestamp endTime) {
        return daoTool.getStationBySid("num_ebikes_available,created_time",sid,startTime,endTime);
    }
    /**
     * @description: 获取minute分钟内没有车的站点列表
     * @param minute 向前推进分钟数
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/17 10:49
     */
    public List<Entity> listNoBike(int minute){
        String sql="select station_id from (select station_id,sum(num_bikes_available) as sum_num from TABLE where created_time > DATE_SUB(NOW(),INTERVAL ? MINUTE) group by station_id)tmp where tmp.sum_num=0;";

        Calendar calendar=Calendar.getInstance();
        Timestamp now=new Timestamp(calendar.getTimeInMillis());
        calendar.add(Calendar.MINUTE,-minute);
        return daoTool.query(sql,new Timestamp(calendar.getTimeInMillis()),now,minute);
    }
    /**
     * @description: 获取hour小时内车数和id
     * @param hour 时间
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/17 12:46
     */
    public List<Entity> listNumBikes(int hour){
        String sql="select station_id,num_bikes_available,num_ebikes_available,last_reported from TABLE where created_time > DATE_SUB(NOW(),INTERVAL ? HOUR)";
        Calendar calendar=Calendar.getInstance();
        Timestamp now=new Timestamp(calendar.getTimeInMillis());
        calendar.add(Calendar.HOUR,-hour);
        return daoTool.query(sql,new Timestamp(calendar.getTimeInMillis()),now,hour);
    }
    public List<Entity> req1(int day){
        String sql="select starttime from t_trip_202006 where day(starttime)=?";
        List<Entity> list=null;
        try {
            list=Db.use().query(sql,day);
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return list;
    }
    public List<Entity> req2(int day){
        String sql="select starttime,usertype from t_trip_202006 where day(starttime)=?";
        List<Entity> list=null;
        try {
            list=Db.use().query(sql,day);
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return list;
    }
    public  List<Entity> req3(Timestamp start,Timestamp end){
        String sql="select usertype,sum(tripduration),count(*),AVG(tripduration) from  t_trip_202006 where starttime>=? and starttime<=? group by usertype;";
        List<Entity> list=null;
        try {
            list=Db.use().query(sql,start,end);
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return list;
    }
    public  List<Entity> req4_s(Timestamp start,Timestamp end){
        String sql="select start_station_id,count(*) as act from t_trip_202006 where starttime>=? and starttime<=? GROUP BY start_station_id ORDER BY act desc limit 100";
        List<Entity>list=null;
        try {
            list=Db.use().query(sql,start,end);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return list;
    }
    public  List<Entity> req4_e(Timestamp start,Timestamp end){
        String sql="select start_station_id,count(*) as act from t_trip_202006 where stoptime>=? and stoptime<=? GROUP BY start_station_id ORDER BY act desc limit 100";
        List<Entity>list=null;
        try {
            list=Db.use().query(sql,start,end);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return list;
    }

}
