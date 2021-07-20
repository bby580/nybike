create database nybikedb;

use nybikedb;
create table t_statues(
    station_id varchar(20) comment '站点id',
    num_bikes_available int,
    num_bikes_disabled int,
    num_docks_available int,
    num_docks_disabled int,
    num_ebikes_available int,
    station_status varchar(30) comment '站点状态',
    last_reported bigint,
    created_time timestamp  default  current_timestamp
);

-- tripduration,starttime,stoptime,start station id,start station name,start station latitude,start station longitude,end station id,end station name,end station latitude,end station longitude,bikeid,usertype,birth year,gender
-- 1062,"2020-06-01 00:00:03.3720","2020-06-01 00:17:46.2080",3419,"Douglass St & 4 Ave",40.6792788,-73.98154004,3419,"Douglass St & 4 Ave",40.6792788,-73.98154004,39852,"Customer",1997,2
--保存历史数据的表
create t_trip_202006(
    tripduration int,
    starttime varchar(30),
    stoptime varchar(30),
    start_station_id int,
    start_station_name varchar(50),
    start_station_latitude double,
    start_station_longitude double,
    end_station_id int,
    end_station_name varchar(50),
    end_station_latitude double,
    end_station_longitude double,
    bikeid int,
    usertype char(20),
    birth_year int,
    gender int comment '0-未知，1-男，2-女'
)
load data infile '/opt/tripData/202006-citibike-tripdata.csv'
into table t_trip_202006
fields terminated by ','
optionally enclosed by '"'
lines terminated by '\n'
ignore 1 lines;