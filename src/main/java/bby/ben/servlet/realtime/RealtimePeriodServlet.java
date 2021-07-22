package bby.ben.servlet.realtime;

import bby.ben.bbytool.MyTool;
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
        String sidStr=req.getParameter("sid");
        String startTimeStr=req.getParameter("startTime");
        String endTimeStr=req.getParameter("endTime");
        String what=req.getParameter("what");
        StationNTData stationNTData=null;
        if (MyTool.isDigit(sidStr)&&MyTool.isDigit(startTimeStr)&&MyTool.isDigit(endTimeStr)){
            long startTime=Long.parseLong(startTimeStr);
            long endTime=Long.parseLong(endTimeStr);
            int sid=Integer.parseInt(sidStr);
            if ("nba".equals(what))
                stationNTData = realtimeService.findNbaBySid(sid,startTime,endTime);
        }
        resp.getWriter().write(JSONUtil.toJsonStr((stationNTData==null)?"输入参数有误！":stationNTData));
    }
}
