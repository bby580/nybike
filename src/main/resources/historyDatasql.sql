#历史数据处理
#1. 某天内所有用户骑车的起始时间在一整天的分布（图）。
select starttime from t_trip_202006 where day(starttime)='1' group by hour(starttime);
#2. 某天内会员和非会员骑车的起始时间在一整天的分布（图）。
select usertype,hour(starttime),count(*) from t_trip_202006 where day(starttime)='2' group by usertype,hour(starttime);
#3. 会员和非会员的总骑行时长，人数，平均骑行时长。
select usertype,sum(tripduration),count(*),AVG(tripduration) from  t_trip_202006 group by usertype;
#4.某段时间内(起始时间)男女/未知性别人数占比，总骑行时长，平均骑行时长 2020-06-05-88:55:55 
select gender,count(*),sum(tripduration),AVG(tripduration) 
from  t_trip_202006 
where starttime>=STR_TO_DATE('2020-06-21 15:00:00','%Y-%m-%d %H:%i:%s') and  starttime<=STR_TO_DATE('2020-06-22 16:00:00','%Y-%m-%d %H:%i:%s') 
group by gender;
#5. 某天内男女用户骑车的起始时间在一整天的分布（图）
select gender,starttime,count(*) from t_trip_202006 where day(starttime) group by gender,hour(starttime);
#出生年与平均骑行时长，用户类型，起始时间的关系（柱形图）
select birth_year,,AVG(tripduration),usertype 
from  t_trip_202006 
group by birth_year,usertype;
#出生与起始时间
select birth_year,count(*),hour(starttime) from t_trip_202006 group by birth_year,hour(starttime);
#7. 某段时间内某车辆的总骑行时长，骑行次数的分布。时间带上天气标注。
select bikeid,sum(tripduration),count(*) as num 
from  t_trip_202006 
where starttime>=STR_TO_DATE('2020-06-21 15:00:00','%Y-%m-%d %H:%i:%s') and  starttime<=STR_TO_DATE('2020-06-22 16:00:00','%Y-%m-%d %H:%i:%s') 
group by bikeid ORDER BY num desc;
#8. 最近n天热门和冷门站点排行榜。地图显示热门、冷门站点
select start_station_id,count(*) as act 
from  t_trip_202006 
where day(starttime)>day(DATE_SUB(NOW(),INTERVAL  5 DAY)) 
GROUP BY start_station_id;
select end_station_id,count(*) as act 
from  t_trip_202006 
where day(starttime)>day(DATE_SUB(NOW(),INTERVAL  5 DAY)) 
GROUP BY end_station_id;
#给一个日期，查询这天的天气、节日信息，平均骑行时长/距离，总骑行时长/距离等
select sum(tripduration) as total_trip,,AVG(tripduration) as avg_trip 
from  t_trip_202006 
where year(starttime)='2020' and MONTH(starttime)='6' and day(starttime)='5';