package bby.ben.dao;

import cn.hutool.db.Entity;

import java.util.List;

import static org.junit.Assert.*;

public class RealtimeDaoTest {

    @org.junit.Test
    public void listNbaBySid() {
        RealtimeDao realtimeDao=new RealtimeDao();
        List<Entity> list = realtimeDao.listNbaBySid("72", 1);
        for (Entity e :list){
            System.out.println(e);
        }
        System.out.println();
    }
}