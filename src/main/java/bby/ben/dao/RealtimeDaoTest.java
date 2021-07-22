package bby.ben.dao;

import bby.ben.bbytool.DaoTool;
import bby.ben.bbytool.MyTool;
import cn.hutool.db.Entity;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class RealtimeDaoTest {

    @org.junit.Test
    public void test() {
        RealtimeDao realtimeDao=new RealtimeDao();
        List<Entity> list = realtimeDao.listNbaBySid(72, 24);
        System.out.println(list);
    }
    @Test
    public void test2(){
        DaoTool daoTool =new DaoTool();
        List<Entity> list = daoTool.getStationBySid("num_bikes_available",72,new Timestamp(1626701476000L), new Timestamp(1626787876000L));
        System.out.println(list);
    }
    @Test
    public void listNoBikeTest(){
        RealtimeDao realtimeDao=new RealtimeDao();
        List<Entity> list = realtimeDao.listNoBike(320);
        System.out.println(list);
    }
    void fun1(Object... params){
        int addLen=1;
        Object[] objs = new Object[params.length+addLen];
        for (int i=0;i<params.length;i++)
            objs[i+addLen]=params[i];
        objs[0]="aaaaaaaaaaaaa";
        MyTool.log(objs);
    }
}