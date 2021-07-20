#### 数据爬取项目打包

1. 选中项目，点击右键，选择打开“open Module settings”

   ![](1.png)

2.  然后选择左边的“Artifacts”，点击“加号”添加jar包，“From modules with dependencies...”

   ![](2.png)

3. 设置主类“Main Class”，一定要选择“extract to the target JAR”，选择源文件生成的路径，都设置好后点击“ok”

   ![](3.png)

4. 再设置打包的名称，打包的类型，打包生成的路径，然后添加“Library Files”，将需要的maven依赖包选中，点击ok，ok即可

   ![](4.png)

5. 设置完成后，点击菜单栏的build=》Build Artifacts，然后选择弹出菜单的“Build”，然后可以在指定目录中查找到打包好的jar包！

#### 云主机运行Java程序

```shell
nohup java -cp /opt/DataCrawler.jar cn.tedu.App >/opt/log.txt  &
```

使用jps命令查看程序运行情况：

![](5.png)

可以通过cat命令查看日志：

```
cat /opt/log.txt
```

如果程序启动异常，也可以在`log.txt`中查看到报错信息，根据信息来排查问题。

#### 需求3：对采集到的状态数据进行分析和可视化

问题1：数据在老师的云主机的数据库中，同学们想要使用，如何操作？

解决方案：当本地项目直接访问老师云主机的数据库

![](6.png)

1. 老师需要开发云主机数据库的访问权限
2. 同学们自己项目中配置的数据库连接信息，要修改成老师的数据库信息

具体操作：

1. 在云主机数据库中配置一个新的账号，仅拥有`nybikedb`库下所有表的查询权限，对所有的主机开放

   ```sql
   -- 创建新账号密码，给予nybikdb库下所有表的查看权限
   GRANT SELECT ON nybikedb.* TO 'student'@'%' IDENTIFIED BY 'P@ssw0rdAbc!'; 
   -- 刷新权限设置使新账号生效
   FLUSH PRIVILEGES;
   ```

   * `student`是用户名
   * `P@ssw0rdAbc!` 是密码

2. 在云主机控制台中开放云主机的3306端口

   ![](7.png)

   ![](8.png)

   ![](9.png)

3. 在本地测试能否访问云主机数据库

   ```
   mysql -ustudent -pP@ssw0rdAbc! -h121.41.16.197
   ```

![](10.png)

具体需求：查询任意站点过去24小时的可用车数量走势并进行可视化

1. 能够查询1个站点过去24小时的可用车走势

   1. 以id为72的站点

      语法：

      ```sql
      select 列名的列表 或 *
      from 表名
      where 列名='特定值'
      ```

      ```sql
      select 
      	station_id, num_bikes_available, created_time 
      from 
      	t_status 
      where 
      	station_id='72' and created_time > DATE_SUB(NOW(),INTERVAL 1 HOUR);
      ```

2. 开发服务器用例，从数据库中查询所需数据，返回JSON格式字符串

   ![](11.png)

   ![](12.png)

#### 持久层开发

1. 在项目的pom.xml中添加对hutool和mysql-connector的依赖，点击maven的刷新按钮，引入依赖。

   ```xml
       <dependency>
         <groupId>cn.hutool</groupId>
         <artifactId>hutool-all</artifactId>
         <version>5.7.4</version>
       </dependency>
       <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
       <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>5.1.47</version>
       </dependency>
   ```

2. 在项目的resources文件夹下添加数据库配置文件db.setting，在其中添加数据库配置：

   ```properties
   ## db.setting文件
   
   url = jdbc:mysql://121.41.16.197:3306/nybikedb
   user = student
   pass = P@ssw0rdAbc!
   
   ## 可选配置
   # 是否在日志中显示执行的SQL
   showSql = true
   # 是否格式化显示的SQL
   formatSql = false
   # 是否显示SQL参数
   showParams = true
   # 打印SQL的日志等级，默认debug，可以是info、warn、error
   sqlLevel = debug
   ```

3. 开发`cn.tedu.dao.RealtimeDao`类，在其中开发响应的方法：

   ```java
   package cn.tedu.dao;
   
   import cn.hutool.db.Db;
   import cn.hutool.db.Entity;
   
   import java.sql.SQLException;
   import java.util.List;
   
   /**
    * 实时数据的持久层类
    * 查询：
    *  查1条   getXXXByXXX getUserById
    *  查多条 listXXXByXXX
    */
   public class RealtimeDao {
   
       /**
        * 基于站点id查询指定
        * 周期内站点可用车数量
        * @param sid 站点id
        * @param hour 自当前时间向前多少个小时
        * @return
        */
       public List<Entity> listNbaBySid(String sid,int hour){
           // 声明要执行的SQL语句
           String sql="select " +
                   "station_id, num_bikes_available, created_time " +
                   "from " +
                   "t_status " +
                   "where " +
                   "station_id=? and created_time > DATE_SUB(NOW(),INTERVAL ? HOUR)";
           // 使用hutool执行查询
           List<Entity> list=null;
           try {
               list=Db.use().query(sql,sid,hour);
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
           return list;
       }
   
   }
   ```

4. 开发测试用例：

   * 选中项目的src文件夹，右键New->Directory-> 双击`test/java`，在项目中生成测试文件夹

   * 在`RealtimeDao`类空白位置右键->Generate...->Test...，配置测试用例：

     ![](13.png)

   * 在测试类中开发具体的测试用例：

     ```java
     package cn.tedu.dao;
     
     import cn.hutool.db.Entity;
     import org.junit.Test;
     
     import java.util.List;
     
     import static org.junit.Assert.*;
     
     public class RealtimeDaoTest {
     
         @Test
         public void listNbaBySid() {
             RealtimeDao dao=new RealtimeDao();
             List<Entity> list
                     =dao.listNbaBySid("72",1);
             for(Entity entity:list){
                 System.out.println(entity);
             }
         }
     }
     ```

     点击测试方法前的播放键，运行测试用例，查看控制台中是否正确输出查询到的数据：

     ![](14.png)

#### 业务层开发

1. VO类开发

   1. VO类是后台按照前端所需格式封装数据的实体类

   2. 创建`cn.tedu.pojo`包，在该包下创建`StationNbaVO`类

      ```JAVA
      package cn.tedu.pojo;
      
      import java.util.List;
      
      public class StationNbaVO {
      
          private List<String> xData;
          private List<Integer> yData;
      
          public StationNbaVO() {
          }
      
          public StationNbaVO(List<String> xData, List<Integer> yData) {
              this.xData = xData;
              this.yData = yData;
          }
      
          public List<String> getxData() {
              return xData;
          }
      
          public void setxData(List<String> xData) {
              this.xData = xData;
          }
      
          public List<Integer> getyData() {
              return yData;
          }
      
          public void setyData(List<Integer> yData) {
              this.yData = yData;
          }
      
          @Override
          public String toString() {
              return "StationNbaVO{" +
                      "xData=" + xData +
                      ", yData=" + yData +
                      '}';
          }
      }
      ```

2. 业务层类开发：创建`cn.tedu.service.RealtimeService`类，封装业务层具体逻辑。

   ```java
   package cn.tedu.service;
   
   import cn.hutool.db.Entity;
   import cn.tedu.dao.RealtimeDao;
   import cn.tedu.pojo.StationNbaVO;
   
   import java.util.ArrayList;
   import java.util.List;
   
   public class RealtimeService {
       private RealtimeDao dao=new RealtimeDao();
       public StationNbaVO findNbaBySid(String sid,int hour){
           //1. 调用持久层方法查询数据
           List<Entity> list=dao.listNbaBySid(sid,hour);
           //2. 声明封装数据的容器
           List<String> xData=new ArrayList<>();
           List<Integer> yData=new ArrayList<>();
           //3. 遍历结果，封装数据
           for(Entity entity:list){
               xData.add(entity.getStr("created_time"));
               yData.add(entity.getInt("num_bikes_available"));
           }
           StationNbaVO vo=new StationNbaVO(xData,yData);
           //4. 返回封装的数据
           return vo;
       }
   }
   ```

   业务层测试用例代码如下：

   ```java
   package cn.tedu.service;
   
   import cn.tedu.pojo.StationNbaVO;
   import org.junit.Test;
   
   import static org.junit.Assert.*;
   
   public class RealtimeServiceTest {
   
       @Test
       public void findNbaBySid() {
           RealtimeService service=new RealtimeService();
           StationNbaVO vo=service.findNbaBySid("72",1);
           System.out.println(vo);
       }
   }
   ```

3. 开发Servlet，接收用户访问，返回查询到的数据

   1. 将servlet的依赖添加到pom.xml中

      ```xml
      <dependency>
          <groupId>javax.servlet</groupId>
          <artifactId>javax.servlet-api</artifactId>
          <version>3.0.1</version>
          <scope>provided</scope>
      </dependency>
      ```

   2. 创建`cn.tedu.web.StationNbaServlet`类，具体代码如下：

      ```java
      package cn.tedu.web;
      
      
      import cn.hutool.json.JSON;
      import cn.hutool.json.JSONUtil;
      import cn.tedu.pojo.StationNbaVO;
      import cn.tedu.service.RealtimeService;
      
      import javax.servlet.ServletException;
      import javax.servlet.annotation.WebServlet;
      import javax.servlet.http.HttpServlet;
      import javax.servlet.http.HttpServletRequest;
      import javax.servlet.http.HttpServletResponse;
      import java.io.IOException;
      @WebServlet("/realtime/nba")
      public class StationNbaServlet extends HttpServlet {
          RealtimeService service=new RealtimeService();
          @Override
          protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
              // 1. 获取请求参数
              String sid=req.getParameter("sid");
              String hourStr=req.getParameter("hour");
              // 2. 类型转换
              int hour=Integer.parseInt(hourStr);
              // 3. 调用业务层方法
              StationNbaVO vo=service.findNbaBySid(sid,hour);
              // 4. 结果转JSON
              String result= JSONUtil.toJsonStr(vo);
              // 5. 通知浏览器返回json格式
              resp.setContentType("application/json;charset=utf-8");
              // 6. 发送响应数据
              resp.getWriter().write(result);
          }
      
      }
      ```

4. 在IDEA中配置关联Tomcat

5. 点击启动Tomcat，在项目正常启动后，会自动在浏览器中弹出项目首页内容。在浏览器地址栏输入`http://localhost:3306/nybike/realtime/nba?sid=72&hour=1`，查看是否可以正确收到json数据

6. 开发前端页面：后台正确返回JSON格式数据后，需要开发前端页面，通过AJAX请求后台数据，使用收到的数据实现可视化图表。本例中先设计使用输入框输入信息的可视化图表。用户先在输入框中输入站点id和查询周期，然后点击查询按钮，触发点击事件，在点击事件中基于jQuery的API收集页面中输入框中的信息，生成请求参数，再发送AJAX请求，在AJAX的回调函数中，从收到的响应数据中拿到x轴数据和y轴数据，基于ECharts的API开发对应的可视化图表。参考代码如下：

   ```html
   <!DOCTYPE html>
   <html lang="en">
   <head>
       <meta charset="UTF-8">
       <title>Title</title>
       <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
       <script src="js/echarts.min.js"></script>
   </head>
   <body>
   站点id：<input id="sid" type="text" ><br>
   时间周期：<input id="hour" type="number" min="1" max="24"><br>
   <button id="btn-search" onclick="fn1()">查询</button>
   <div id="main" style="height:400px;margin: 0px auto"></div>
   <script type="text/javascript">
       // 请求的路径
       var url="realtime/nba";
   
       function fn1(){
           // 1. 收集表单数据
           var sid=$("#sid").val();
           if(sid==""){
               alert("站点id不能为空");
               return;
           }
           var hour=$("#hour").val();
           if(hour==""){
               alert("时间周期不能为空");
               return;
           }
           // 封装请求参数
           var params={
               "sid":sid,
               "hour":hour
           }
           // 发送AJAX请求
           $.get(url,params,function (result) {
               console.log(result);
               var myChart = echarts.init(document.getElementById('main'));
               option = {
                   xAxis: {
                       type: 'category',
                       data: result.xData
                   },
                   yAxis: {
                       type: 'value'
                   },
                   series: [{
                       data: result.yData,
                       type: 'line'
                   }]
               };
               myChart.setOption(option);
           });
       }
   </script>
   </body>
   </html>
   ```

   

#### 任务

1. 每组的数据工程师要保证对外开放自己云主机的`nybikedb`库，其他组员可以访问到该库，组长监督
2. 根据代码示例，完成服务器端所有代码
3. 跟随笔记，完成浏览器端代码，实现整个用例
4. 额外开发一个类似的用例：根据给定的站点id，查询指定周期内该站点的可用桩数量走势。



答辩需求：

1. 开发2个基于采集到的状态数据进行统计分析和可视化的用例