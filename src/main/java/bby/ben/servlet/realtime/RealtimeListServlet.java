package bby.ben.servlet.realtime;

import bby.ben.bbytool.MyTool;
import bby.ben.pojo.BikesLivelyData;
import bby.ben.service.RealtimeService;
import cn.hutool.json.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/realtime/list")
public class RealtimeListServlet extends HttpServlet
{
    RealtimeService realtimeService =new RealtimeService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String what=req.getParameter("what");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out=resp.getWriter();
        try {
            if ("nobike".equals(what)){
                int minute =Integer.parseInt(req.getParameter("minute"));
                MyTool.log("what="+what," minute="+minute);
                out.write(JSONUtil.toJsonStr(realtimeService.getNoBikeList(minute)));
            }else if ("iotop".equals(what)){
                int top=Integer.parseInt(req.getParameter("top"));
                int hour=Integer.parseInt(req.getParameter("hour"));
                System.out.println("------------> what="+what+", hour="+hour+", top="+top);
                out.write(JSONUtil.toJsonStr(realtimeService.getsLivelyListByTop(hour, top)));
            }
        }catch (Exception e){
            e.printStackTrace();
            out.write(JSONUtil.toJsonStr("输入参数错误！"));
        }
    }
}
