package bby.ben.servlet;

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
public class StationListServlet extends HttpServlet
{
    RealtimeService realtimeService =new RealtimeService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String minute=req.getParameter("minute");
        String what=req.getParameter("what");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out=resp.getWriter();
        try {
            if ("nobike".equals(what)){
                System.out.println("------------> what="+what+", minute="+minute);
                List<String> list = realtimeService.getNoBikeList(Integer.parseInt(minute));
                out.write(JSONUtil.toJsonStr(list));
            }else if ("iotop".equals(what)){
                int hour=Integer.parseInt(req.getParameter("hour"));
                int top=Integer.parseInt(req.getParameter("top"));
                System.out.println("------------> what="+what+", hour="+minute+", top="+top);
                List<BikesLivelyData> list = realtimeService.getsLivelyListByTop(hour, top);
                System.out.println(list);
                out.write(JSONUtil.toJsonStr(list));
            }
        }catch (Exception e){
            e.printStackTrace();
            resp.getWriter().write(JSONUtil.toJsonStr("输入参数错误！"));
        }
    }
}
