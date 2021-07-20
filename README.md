# nybike

##需求

###实时数据分析模块：
一个地图：
1. 地图上标记所有站点；
2. 地图上标记不能服务的站点，没有可用车的站点，没有可用桩的站点；
3. 点击某个站点，弹出站点当前实时数据；可进一步查看历史数据
4. 点击选择某个站点后，可以查看这个站点某段时间内的可用车趋势，进出流量等；
5. 查询从当前时间开始向前m分钟内没有可用车的站点id，在地图上显示；

###历史数据分析模块：
1. 某天内所有用户骑车的起始时间在一整天的分布（图）。
2. 某天内会员和非会员骑车的起始时间在一整天的分布（图）。
3. 会员和非会员的总骑行时长，骑行频数。
4. 某段时间内男女人数占比，男女的总骑行时长，骑行频率对比。
5. 某天内男女用户骑车的起始时间在一整天的分布（图）。
6. 出生年与骑行时长，用户类型，骑行频率，起始时间的关系（柱形图）。
7. 某段时间内用户每天的平均骑行时长，骑行距离的分布。时间带上天气标注。
8. 最近n天热门和冷门站点排行榜。地图显示热门、冷门站点。
9. 给一个日期，查询这天的天气、节日信息，平均骑行时长/距离，总骑行时长/距离等。