package bby.ben.bbytool;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import jdk.nashorn.internal.runtime.regexp.joni.ast.AnchorNode;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DaoTool {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * @description: 按时间段查询数据库
     * @param sql SQL语句，注意：表明设置为‘TABLE'
     * @param from,to 起始时间
     * @param params sql参数
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/22 9:56
     */
    public List<Entity> query(String sql,Timestamp from,Timestamp to,Object... params){
        Db db=Db.use();
        List<Entity> list=null;
        Calendar start=Calendar.getInstance();
        Calendar end=Calendar.getInstance();

        try {
            start.setTime(sdf.parse(sdf.format(from)));//时间钟表置0
            end.setTime(to);
            while (start.before(end)){
                String tab="t_status_"+sdf.format(start.getTime());

                List<Entity> li=db.query(sql.replace("TABLE",tab),params);
                start.add(Calendar.DATE,1);

                MyTool.log("数据库查询表：",tab);
                if (li==null) continue;
                if (list==null) list=li;
                else list.addAll(li);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
    /**
     * @description: 查询站点信息列表，带条件
     * @param names 要查的属性
     * @param and_content sql 附加条件，如：where ... and and_content
     * @param from,to 起始时间
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/21 17:25
     */
    public List<Entity> getStationList(String names,String and_content,Timestamp from,Timestamp to){//条件
        Db db=Db.use();
        List<Entity> list=null;
        Calendar start=Calendar.getInstance();
        Calendar end=Calendar.getInstance();

        String select_content= "select "+names.trim();
        String while_content= " where created_time >= ? and created_time <= ?";
        if (and_content!=null&&and_content.length()>0) while_content=while_content+" and "+and_content.trim();

        try {
            start.setTime(sdf.parse(sdf.format(from)));//时间钟表置0
            end.setTime(to);
            while (start.before(end)){
                String tab="t_status_"+sdf.format(start.getTime());
                String sql=select_content+" from "+tab+while_content;
                List<Entity> li=db.query(sql,from,to);
                start.add(Calendar.DATE,1);

                MyTool.log("数据库查询表：",tab);
                if (li==null) continue;
                if (list==null) list=li;
                else list.addAll(li);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
    /**
     * @description: 查询站点信息列表，根据sid
     * @param names 要查的属性
     * @param from,to 起始时间
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/21 21:13
     */
    public List<Entity> getStationBySid(String names,int sid,Timestamp from, Timestamp to){
        return getStationList(names,"station_id="+sid,from,to);
    }
    /**
     * @description: 查询站点信息列表
     * @param names 要查的属性
     * @param from,to 起始时间
     * @return:
     * @author: 本小蛋
     * @time: 2021/7/21 21:13
     */
    public List<Entity> getStationList(String names,Timestamp from,Timestamp to){
        return getStationList(names,null,from,to);
    }
}
