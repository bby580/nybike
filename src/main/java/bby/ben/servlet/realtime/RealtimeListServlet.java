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
        String minuteStr=req.getParameter("minute");
        String what=req.getParameter("what");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out=resp.getWriter();
        if (!MyTool.isDigit(minuteStr)){
            MyTool.log("输入参数错误","minuteStr="+minuteStr,"what="+what);
            out.write(JSONUtil.toJsonStr("输入参数错误！minuteStr="));
            return;
        }
        try {
            int minute =Integer.parseInt(minuteStr);
            if ("nobike".equals(what)){
                MyTool.log("what="+what," minute="+minuteStr);
                List<String> list = realtimeService.getNoBikeList(minute);
                out.write(JSONUtil.toJsonStr(list));
            }else if ("iotop".equals(what)){
                int top=Integer.parseInt(req.getParameter("top"));
                System.out.println("------------> what="+what+", hour="+minute+", top="+top);
                List<BikesLivelyData> list = realtimeService.getsLivelyListByTop(minute, top);

                out.write(JSONUtil.toJsonStr(list));
            }
        }catch (Exception e){
            e.printStackTrace();
            resp.getWriter().write(JSONUtil.toJsonStr("输入参数错误！"));
        }
    }
}
