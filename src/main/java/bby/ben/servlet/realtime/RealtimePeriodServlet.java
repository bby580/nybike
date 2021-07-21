package bby.ben.servlet.realtime;

import bby.ben.pojo.StationNTData;
import bby.ben.service.RealtimeService;
import cn.hutool.json.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.regex.Pattern;
/**
 * @description: 用时间段获取
 * @author: 本小蛋
 * @time: 2021/7/21 17:08
 */
@WebServlet("/realtime/period")
public class RealtimePeriodServlet extends HttpServlet {
    RealtimeService realtimeService=new RealtimeService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        String sid=req.getParameter("sid");
        String startTimeStr=req.getParameter("startTime");
        String endTimeStr=req.getParameter("endTime");
        String what=req.getParameter("what");
        System.out.println(what);
        String patten="^[0-9]+$";
        StationNTData stationNTData=null;
        if (Pattern.matches(patten,sid)&&Pattern.matches(patten,startTimeStr)&&Pattern.matches(patten,endTimeStr)){
            long startTime=Long.parseLong(startTimeStr);
            long endTime=Long.parseLong(endTimeStr);
            if ("nba".equals(what))
                stationNTData = realtimeService.findNbaBySid(sid,startTime,endTime);
            else {
                System.out.println(sid);
                System.out.println(startTime);
                System.out.println(endTime);
            }
        }
        System.out.println(stationNTData);
        resp.getWriter().write(JSONUtil.toJsonStr((stationNTData==null)?"输入参数有误！":stationNTData));
    }
}
