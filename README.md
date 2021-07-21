# nybike

##需求

###实时数据分析模块：
一个地图：
1. 地图上标记所有站点；
2. 地图上标记不能服务的站点，没有可用车的站点，没有可用桩的站点；
3. 点击某个站点，弹出站点当前实时数据；可进一步查看历史数据
4. 点击选择某个站点后，可以查看这个站点某段时间内的可用车趋势，进出流量等；
   API: /nybike/realtime_page?what=nba&sid=72&startTime=1626856470117&endTime=1626856530117
   数据：{xData=[2021-07-21 16:21:01.0, 2021-07-21 16:23:01.0, 2021-07-21 16:25:01.0, 2021-07-21 16:26:00.0, 2021-07-21 16:34:00.0], yData=[21, 21, 21, 21, 21]}
   xData:记录时间，yData：可用车数量
5. 查询从当前时间开始向前m分钟内没有可用车的站点id，在地图上显示；
   API: /nybike/realtime/list?what=nobike&minute=10
   数据：["153","173","3134","3136","3152","3163","3233","3282","3355","3359","3375","3379","3421","343","3430","3443","3457","3481","3501","3526","3535","3536","3539","3604","3610","3615","362","3640","3686","3724","3735","3757","3768","3792","3798","3848","3904","3913","393","3944","3959","3967","397","3978","4002","4005","4010","4013","402","4022","4029","4034","4051","4052","4054","4056","4059","4061","4068","4070","4071","4074","4102","4120","4128","4142","4152","4169","4173","4202","4204","4209","4212","4229","4230","4251","4263","4276","4283","4300","4301","4308","4310","4315","4320","4322","4323","4337","4344","4355","4394","442","4430","4437","4454","4486","4500","4512","4515","4519","4527","4528","4544","4545","4553","4556","4560","4569","457","4573","4576","4577","4578","4585","4604","4607","4614","4615","4628","4629","4631","4634","4636","464","469","484","485","492","499","503","519","526","528","533","537","545"]
   
###历史数据分析模块：
1. 某天内所有用户骑车的起始时间在一整天的分布（图）。
   select starttime from t_trip_202006 where day(starttime)=?;
2. 某天内会员和非会员骑车的起始时间在一整天的分布（图）。
   select starttime from t_trip_202006 where day(starttime) groupby usertype;
3. 某段时间内每天会员和非会员的总骑行时长，人数，平均时长。
   select usertype,sum(tripduration),count(*),AVG(tripduration) from  t_trip_202006 group by usertype;
4. 某段时间内(起始时间)男女/未知性别人数占比，总骑行时长，平均骑行时长 2020-06-05-88:55:55
   select gender,count(*),sum(tripduration),AVG(tripduration)
   from  t_trip_202006
   where starttime>=STR_TO_DATE('2020-06-21 15:00:00','%Y-%m-%d %H:%i:%s') and  starttime<=STR_TO_DATE('2020-06-22 16:00:00','%Y-%m-%d %H:%i:%s')
   group by gender;
5. 某天内男女用户骑车的起始时间在一整天的分布（图）。
   select gender,starttime,count(*) from t_trip_202006 where day(starttime) group by gender,hour(starttime);
6. 出生年与平均骑行时长，用户类型，起始时间的关系（柱形图）。
   select birth_year,,AVG(tripduration),usertype
   from  t_trip_202006
   group by birth_year,usertype;

   出生与起始时间：
   select birth_year,count(*),hour(starttime) from t_trip_202006 group by birth_year,hour(starttime);

7. 某段时间内用户每天的平均骑行时长，骑行距离的分布。时间带上天气标注。
   select bikeid,sum(tripduration),count(*) as num
   from  t_trip_202006
   where starttime>=STR_TO_DATE('2020-06-21 15:00:00','%Y-%m-%d %H:%i:%s') and  starttime<=STR_TO_DATE('2020-06-22 16:00:00','%Y-%m-%d %H:%i:%s')
   group by bikeid ORDER BY num desc;
8. 最近n天热门和冷门站点排行榜。地图显示热门、冷门站点。
   select start_station_id,count(*) as act
   from  t_trip_202006
   where day(starttime)>day(DATE_SUB(NOW(),INTERVAL  5 DAY))
   GROUP BY start_station_id;
   select end_station_id,count(*) as act
   from  t_trip_202006
   where day(starttime)>day(DATE_SUB(NOW(),INTERVAL  5 DAY))
   GROUP BY end_station_id;
9. 给一个日期，查询这天的天气、节日信息，平均骑行时长/距离，总骑行时长/距离等。
   select sum(tripduration) as total_trip,,AVG(tripduration) as avg_trip
   from  t_trip_202006
   where year(starttime)='2020' and MONTH(starttime)='6' and day(starttime)='5';