package bby.ben.bbytool;

import java.util.Date;
import java.util.regex.Pattern;

public class MyTool {
//    public static void log(String info){
//        System.out.println("-------"+new Date()+"----->"+info);
//    }
    public static void log(Object... objs){
        System.out.print("-------"+new Date()+"----->");
        for (Object obj :objs) System.out.println(obj+", ");
    }
    public static boolean isDigit(String str){
        return Pattern.matches("^[0-9]+$",str);
    }
    public static boolean isDigit(String... args){
        String str="";
        for (String s:args)str+=s;
        return isDigit(str);
    }
}
