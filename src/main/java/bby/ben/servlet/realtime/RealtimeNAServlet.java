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
import java.util.regex.Pattern;

@WebServlet("/realtime/num_available")
public class RealtimeNAServlet extends HttpServlet {
    RealtimeService realtimeService=new RealtimeService();

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        String sidStr=req.getParameter("sid");
        String hourStr=req.getParameter("hour");
        String what=req.getParameter("what");

        StationNTData stationNTData=null;
        if (MyTool.isDigit(hourStr) &&MyTool.isDigit(sidStr)){
            int hour=Integer.parseInt(hourStr);
            int sid=Integer.parseInt(sidStr);
            if ("nba".equals(what))
                stationNTData = realtimeService.findNbaBySid(sid,hour);
            else if ("nda".equals(what))
                stationNTData = realtimeService.findNdaBySid(sid,hour);
            else if ("nea".equals(realtimeService.findNeaBySid(sid,hour)))
                stationNTData = realtimeService.findNeaBySid(sid,hour);
        }

        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(JSONUtil.toJsonStr((stationNTData==null)?"输入参数有误！":stationNTData));
    }
}
