package bby.ben.servlet.history;

import bby.ben.pojo.UtypeStimeDisbVo;
import bby.ben.pojo.UtypeTimeDisbVo1;
import bby.ben.service.RealtimeService;
import cn.hutool.json.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/realtime/UTD")
public class UtypeTimeDisbServlet extends HttpServlet {
    RealtimeService service = new RealtimeService();
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String daystr=req.getParameter("day");
        int day=Integer.parseInt(daystr);
        UtypeStimeDisbVo vo=service.getReq2(day);
        List<Integer> cus= new ArrayList<>();
        List<Integer> anu= new ArrayList<>();
       // List<String> custime=new ArrayList<>();
        //List<String> anutime=new ArrayList<>();
        String initstr="00";
        String last = vo.getCus_Data().get(0);
        String last1= vo.getAnu_Data().get(0);
        int sum=0;
        for(int i=0;i<1440;i++){
            anu.add(0);
            cus.add(0);
        }
        for(String str:vo.getCus_Data()){
            if(str.substring(14,16).equals(initstr)){
                sum++;
                last=str;
            }
            else{

                cus.set(Integer.parseInt(last.substring(11,13))*60+Integer.parseInt(last.substring(14,16)),sum);
                //custime.add(str.substring(0,17));
                sum=1;
                initstr=str.substring(14,16);
            }
        }
        initstr="00";
//        String last;
        sum=0;
        for(String str:vo.getAnu_Data()){
            if(str.substring(14,16).equals(initstr)){
                sum++;
                last1=str;
            }
            else{
                anu.set(Integer.parseInt(last1.substring(11,13))*60+Integer.parseInt(last1.substring(14,16)),sum);
               // anutime.add(str.substring(0,17));
                sum=1;
                initstr=str.substring(14,16);
            }
        }
        UtypeTimeDisbVo1 vo1=new UtypeTimeDisbVo1(anu,cus);
        //System.out.println(vo.getAnu_Data().size());
        String result = JSONUtil.toJsonStr(vo1);
        res.setContentType("application/json;charset=utf-8");

        res.getWriter().write(result);
    }
}
