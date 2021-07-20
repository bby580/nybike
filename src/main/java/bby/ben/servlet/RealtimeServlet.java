package bby.ben.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/realtime")
public class RealtimeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String htmlDir=getServletContext().getRealPath("/html/");
        resp.setContentType("text/html;charset=UTF-8");
//        req.getRequestDispatcher("html/homeworks/homework2.html").forward(req,resp);
        PrintWriter out= resp.getWriter();
//        System.out.println(htmlDir);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(htmlDir+"homeworks/homework2.html"),"UTF-8"))){
            String line=null;
            while ((line=br.readLine())!=null) out.println(line);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
