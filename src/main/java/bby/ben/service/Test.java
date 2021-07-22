package bby.ben.service;

import bby.ben.bbytool.MyTool;
import bby.ben.pojo.BikesLivelyData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {
    static void log(Object... objs){
        for (Object obj :objs)
            System.out.println(obj);
    }
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar start=Calendar.getInstance();
        Calendar end=Calendar.getInstance();
        start.setTimeInMillis(1626796800000l);
//        try {
//            start.setTime(sdf.parse(sdf.format(start.getTime())));//时间钟表置0
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        start.set(Calendar.HOUR,0);
//        start.set(Calendar.MINUTE,0);
//        start.set(Calendar.SECOND,0);
        end.setTimeInMillis(1629647940000l);
        System.out.println(end.getTime());
        System.out.println("---------");
        while (start.before(end)){
            System.out.println(start.getTime());
            start.add(Calendar.DATE,1);
        }
//        System.out.println(sdf.format(new Date(1629647940000l)));//1626796800000
//        try {
//            System.out.println(sdf.parse(sdf.format(new Date())));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }
}
