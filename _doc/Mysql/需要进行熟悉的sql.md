##### 关联表删除

```sql
-- 关联表删除
DELETE r
FROM
	bill_rate_rule_rel r
	LEFT JOIN bill_rate br ON br.ID = r.RATE_ID 
WHERE
	br.TENANT_ID = 1
```

##### 删除指定列重复的数据

```sql
-- 删除指定列重复的数据
DELETE n1 FROM table1 n1, table1 n2 
WHERE n1.id > n2.id AND n1.column1 = n2.column1;
```

##### 关联表更新

```sql
-- 关联表更新
UPDATE tran_order_record tor
LEFT JOIN invoice_order_record_rel iorr ON tor.ID = iorr.ORDER_RECORD_ID
LEFT JOIN invoice_record ir ON iorr.INVOICE_RECORD_ID = ir.ID 
SET tor.INVOICE_STATUS =2
WHERE
	ir.STATUS = 2 
	AND ir.TENANT_ID = 1 
	AND tor.INVOICE_STATUS = 1
```

相比于使用in子查询，这样不会全表扫描，执行较快



##### 查出重复项并将其一个字段最后一位改成b

```sql
-- 查出重复项并将其一个字段最后一位改成b
UPDATE table t1
INNER JOIN 
(
  SELECT MIN(id) AS id, name
  FROM table
  GROUP BY name
  HAVING COUNT(*) > 1
) t2 ON t1.id = t2.id
SET t1.name = CONCAT(LEFT(name, LENGTH(name)-1), 'b')
```



##### 将一张表中的两个字段互换

```sql
-- 将一张表中的两个字段互换
UPDATE base_road_section 
SET LONGITUDE_BAIDU = ( @temp := LONGITUDE_BAIDU ),
LONGITUDE_BAIDU = LATITUDE_BAIDU,
LATITUDE_BAIDU = @temp;	
```





##### INTERVAL(N,N1,N2,N3,..........)

其中，N是要判断的数值，`N1,N2,N3,...`是分段的间隔。

这个函数的返回值是段的位置：

如果`N<N1`，则返回0，

如果`N1<=N<N2`，则返回1，

如果`N2<=N<N3`，则返回2。

```sql
SELECT
    po.TENANT_ID as tenantId,
    po.ROAD_SECTION_ID AS roadSectionId,
    elt(interval(DATE_FORMAT( po.ARRIVE_TIME, '%H:%i:%S' ), '00:00:00', '08:00:00', '12:00:00', '14:00:00', '16:00:00', '18:00:00', '20:00:00', '23:59:59'),'0','1','2','3','4','5','6','7') as timeRange,
    COUNT(*) AS arriveParkingCount
FROM
    tran_parking_order po
WHERE
    po.ARRIVE_TIME BETWEEN #{startTime}
    AND #{endTime}
GROUP BY
    po.ROAD_SECTION_ID,
    timeRange
```

```sql
-- 注意这里有个坑点，interval后
SELECT interval('08:22:00', '08:30:00', '10:30:00','12:30:00', '14:30:00', '16:30:00', '18:30:00', '20:30:00','23:59:00') as timeRange
-- 按理说08:22:00<08:30:00 查询出来应为1
-- 实际为0，似乎只对比小时，分和秒无效
```



##### DATE_SUB

```sql
DATE_SUB(NOW(), INTERVAL 1 MONTH)
```

DATE_SUB()和INTERVAL来计算一个月前的日期。
具体解释如下:
- NOW() 获取当前的日期时间
- DATE_SUB()是一个日期减法函数,用来计算一个日期减去一个时间间隔后的结果
- INTERVAL 1 MONTH 表示一个时间间隔,INTERVAL后跟数字加时间单位,这里是1个月
- 所以DATE_SUB(NOW(), INTERVAL 1 MONTH)表示:
从当前日期时间NOW()中减去一个月的时间间隔
也就是计算当前日期的一个月前的日期
例如:
现在是2023-03-05,那么语句的值结果就是:
2023-02-05
所以这个MySQL语句可以用来查询一个月前的记录,比如:

```sql
sql
SELECT * 
FROM table
WHERE date_col > DATE_SUB(NOW(), INTERVAL 1 MONTH)
```

这可以查询日期在一个月前之后(不包括一个月前)的记录。
总之,DATE_SUB()+INTERVAL组合可以用来进行日期时间的减法计算。
