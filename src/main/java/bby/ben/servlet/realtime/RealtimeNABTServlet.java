package bby.ben.servlet.realtime;

import bby.ben.bbytool.MyTool;
import bby.ben.pojo.StationNTData;
import bby.ben.service.RealtimeService;
import cn.hutool.json.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/realtime/nabt")
public class RealtimeNABTServlet extends HttpServlet {
    RealtimeService realtimeService =new RealtimeService();
    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        String sidStr=req.getParameter("sid");
        String what=req.getParameter("what");
        String startTimeStr=req.getParameter("startTime");
        String endTimeStr=req.getParameter("endTime");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        StationNTData stationNTData=null;
        MyTool.log(what,sidStr,startTimeStr,endTimeStr);
        if (MyTool.isDigit(sidStr,startTimeStr,endTimeStr)){
            try {
                int sid=Integer.parseInt(sidStr);
                long startTime=Long.parseLong(startTimeStr);
                long endTime=Long.parseLong(endTimeStr);
                if ("nba".equals(what)) stationNTData = realtimeService.findNbaBySid(sid,startTime,endTime);
                else if ("nda".equals(what))stationNTData=realtimeService.findNdaBySid(sid,startTime,endTime);
                else if ("nbd".equals(what))stationNTData=realtimeService.findNbdBySid(sid,startTime,endTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resp.getWriter().write(JSONUtil.toJsonStr((stationNTData==null)?"输入参数有误！":stationNTData));
    }
}
