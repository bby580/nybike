package bby.ben.servlet.history;

import bby.ben.pojo.HotStartEndVo;
import bby.ben.service.RealtimeService;
import cn.hutool.json.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.Timestamp;
@WebServlet("/realtime/HSE")
public class HotStaEndServlet extends HttpServlet {
    public RealtimeService service=new RealtimeService();

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String type=req.getParameter("type");
        String startstr=req.getParameter("start");
        String endstr=req.getParameter("end");
        Timestamp start=Timestamp.valueOf(startstr);
        Timestamp end=Timestamp.valueOf(endstr);
        HotStartEndVo vo=service.getReq4(type,start,end);
        String result = JSONUtil.toJsonStr(vo);

        res.setContentType("application/json;charset=utf-8");

        res.getWriter().write(result);
    }
}
