package bby.ben.dao;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 实时数据的持久层类，查询1条：getXXXById,多条：listXXXByXXX
 * @author: 本小蛋
 * @time: 2021/7/16 14:07
 */
public class RealtimeDao {
    /**
     * @description:
     * @param sid 站点id
     * @param hour 当前时间向前多少小时
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/16 14:19
     */
    public List<Entity> listNbaBySid(String sid, int hour){
        String sql="select num_bikes_available,created_time from t_status where station_id=? and created_time > DATE_SUB(NOW(),INTERVAL ? HOUR)";
        List<Entity> list=null;
        try {
            list=Db.use().query(sql,sid,hour);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("--------------->数据库读取出错，sid="+sid+",hour="+hour);
        }
        return list;
    }
    /**
     * @description: 查询hour小时内sid站点的可用桩数量变化
     * @param sid 站点id
     * @param hour 当前时间向前多少小时
     * @return: 
     * @author: 本小蛋
     * @time: 2021/7/16 20:47
     */
    public List<Entity> listNdaBySid(String sid, int hour){
        String sql="select num_docks_available,created_time from t_status where station_id=? and created_time > DATE_SUB(NOW(),INTERVAL ? HOUR)";
        List<Entity> list = null;
        try {
            list=Db.use().query(sql,sid,hour);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("--------------->数据库读取出错，sid="+sid+",hour="+hour);
        }
        return list;
    }
    /**
     * @description: 查询hour小时内sid站点的可用电动车数量变化
     * @param sid 站点id
     * @param hour 当前时间向前多少小时
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/17 14:38
     */
    public List<Entity> listNeaBySid(String sid, int hour){
        String sql="select num_ebikes_available,created_time from t_status where station_id=? and created_time > DATE_SUB(NOW(),INTERVAL ? HOUR)";
        List<Entity> list = null;
        try {
            list=Db.use().query(sql,sid,hour);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("--------------->数据库读取出错，sid="+sid+",hour="+hour);
        }
        return list;
    }
    /**
     * @description: 获取minute分钟内没有车的站点列表
     * @param minute 向前推进分钟数
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/17 10:49
     */
    public List<Entity> listNoBike(int minute){
        String sql="select station_id from (select station_id,sum(num_bikes_available) as sum_num from t_status where created_time > DATE_SUB(NOW(),INTERVAL ? MINUTE) group by station_id)tmp where tmp.sum_num=0;";
        List<Entity> list = null;
        try {
            list=Db.use().query(sql,minute);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("--------------->数据库读取出错，minute="+minute);
        }
        return list;
    }
    /**
     * @description: 获取hour小时内车数和id
     * @param hour 时间
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/17 12:46
     */
    public List<Entity> listNumBikes(int hour){
        String sql="select station_id,num_bikes_available,num_ebikes_available from t_status where created_time > DATE_SUB(NOW(),INTERVAL ? HOUR)";
        List<Entity> list = null;
        try {
            list=Db.use().query(sql,hour);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("--------------->数据库读取出错，hour="+hour);
        }
        return list;
    }
}
