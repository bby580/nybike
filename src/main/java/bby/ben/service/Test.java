package bby.ben.service;

import bby.ben.bbytool.MyTool;
import bby.ben.pojo.BikesLivelyData;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Test {
    static void log(Object... objs){
        for (Object obj :objs)
            System.out.println(obj);
    }
    public static void main(String[] args) {
        MyTool.log("aaa");
        MyTool.log("aaa","asdd");
    }
}
