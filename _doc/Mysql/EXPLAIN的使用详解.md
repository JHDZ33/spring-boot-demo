### explain介绍

MYSQL有查询优化器的存在，对于每一条sql语句都会自行计算出最优的执行计划，而explain关键字就是输出优化器对每一条sql的执行计划，用于说明其执行sql语句的信息，从而为查询优化提供帮助

```sql
explain select * from user where User= 'root' \G
```

执行结果如下图12列

| id   | select_type | table | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra |
| ---- | ----------- | ----- | ---------- | ---- | ------------- | ---- | ------- | ---- | ---- | -------- | ----- |
| 1    | SIMPLE      | user  | NULL       | ALL  | NULL          | NULL | NULL    | NULL | 4    | 100.00   | NULL  |

| 字段名          | 作用                                                         |
| --------------- | ------------------------------------------------------------ |
| `id`            | 选择优先级标识符                                             |
| `select_type`   | 查询的类型                                                   |
| `table`         | 输出结果集的表                                               |
| `partitions`    | 记录与查询匹配的分区，值为NULL表示为非分区表( 5.7及以上版本 ) |
| `type`          | 表的访问方式                                                 |
| `possible_keys` | 查询时，可能使用的索引                                       |
| `key`           | 实际使用的索引                                               |
| `key_len`       | 索引字段的长度                                               |
| `ref`           | 列与索引的比较                                               |
| `rows`          | 扫描出的行数(估算的行数)                                     |
| `filtered`      | 按表条件过滤的行百分比，与`rows` 列的值一起使用( 5.7及以上版本 ) |
| `Extra`         | 执行情况的描述和说明                                         |

### explain输出各列含义

##### 1. 优先级-id

`id` 列就是 `select` 的序列号，有几个 `select` 就有几个`id`，并且其顺序是按 select 出现的顺序增长的。

字段 `id` 需要与字段 `table` 结合阅读， `id`的值越大，对应的 `table`执行的优先级就越高， `id`值相同时，`table`执行的顺序则从上到下排列。



##### 2. 查询类型 - select_type

MySQL 将 select 查询分为简单查询和复杂查询。复杂查询分为三类：简单子查询、派生表（from语句中的子查询）、union 查询。

| 候选值                  | 含义                                                         |
| ----------------------- | ------------------------------------------------------------ |
| `SIMPLE`                | 简单SELECT，不使用UNION或子查询等                            |
| `PRIMARY`               | 子查询中最外层查询，查询中若包含任何复杂的子部分，**最外层的 select 被标记为 PRIMARY** |
| `UNION`                 | UNION中的第二个或后面的SELECT语句                            |
| `DEPENDENT UNION`       | UNION中的第二个或后面的SELECT语句，取决于外面的查询          |
| `UNION RESULT`          | UNION的结果，union语句中第二个select开始后面所有select       |
| `SUBQUERY`              | 子查询中的第一个SELECT，结果不依赖于外部查询                 |
| `DEPENDENT SUBQUERY`    | 子查询中的第一个SELECT，依赖于外部查询                       |
| `DERIVED`               | 派生表的SELECT, FROM子句的子查询                             |
| ` UNCACHEABLE SUBQUERY` | 一个子查询的结果不能被缓存，必须重新评估外链接的第一行       |

##### 3. 查询的表名 - table

表示 explain 的这一行在访问哪个表。当 `from` 子句中有子查询时，table 列是` < derivenN > `格式，表示当前查询依赖 id=N 的查询，于是先执行 id=N 的查询。当有 `union` 时，`UNION RESULT` 的 `table` 列的值为 `<union N,M>`，N 和 M 表示参与 union 的 select 行 `id`

##### 4. 查询的范围 - type

表示关联类型或访问类型，即 MySQL 决定如何查找表中的行。性能从最优到最差为：

`system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery >  index_subquery > range > index > ALL`

| 候选值            | 含义                                                         |
| ----------------- | ------------------------------------------------------------ |
| `NULL`            | mysql能够在优化阶段分解查询语句，在执行阶段不用再访问表或索引。例如在索引列中选取最小值，可以单独查找索引来完成，不需要在执行时访问表： `explain select min(User) from user;` |
| `system`          | 这是 const 类型的一个特例，表仅有一行时才满足条件            |
| `const`           | 查询开始时读取，最多匹配出一行记录。由于只有一行，因此该行中列的值会被优化器视为常量，并且它只读一次，所以 const 速度非常快 |
| `eq_ref`          | primary key 或 unique key 索引的所有部分被连接使用 ，最多只会返回一条符合条件的记录。这是在 const 之外最好的联接类型，可以用于`=运算符`进行比较的索引列，比较值可以是一个常量，也可以是一个表达式 |
| `ref`             | 相比 eq_ref，不使用唯一索引，而是使用普通索引或者唯一性索引的部分前缀，索引要和某个值相比较，可能会找到多个符合条件的行, 比如关联表某个字段值相等的条件 |
| `ref_or_null`     | 类似ref，但是可以搜索值为NULL的行，常作用在解析子查询中      |
| `index_merge`     | 表示使用了索引合并的优化方法。 例如表：id是主键，ten_id是普通索引。`or` 的时候使用了 primary key(id)条件 or 连接 ten_id 索引条件的形式 |
| `unique_subquery` | 该类型替换了 `IN` 子查询的ref：`value IN (SELECT primary_key FROM single_table WHERE some_expr)` |
| `index_subquery`  | 该类型类似于unique_subquery，可以替换IN子查询，但只适合下列形式的子查询中的非唯一索引：`value IN (SELECT key_column FROM single_table WHERE some_expr)` |
| `range`           | 使用一个索引来检索给定范围的行，当使用`=、<>、>、>=、<、<=、IS NULL、<=>、BETWEEN或者IN操作符`，用常量比较关键字列时，可以使用 range |
| `index`           | 和ALL一样，不同就是mysql只需扫描索引树，通常比ALL快一点，因为索引文件一般比数据文件小 |
| `ALL`             | 即全表扫描，意味着mysql需要从头到尾去查找所需要的行。通常情况下这需要增加索引来进行优化了 |



##### 5. 可使用的索引- possible_keys

显示查询可能使用哪些索引来查找目标数据。`explain` 时可能出现 `possible_keys` 有列，而 `key` 显示 `NULL` 的情况，这种情况通常是因为表中数据不多，`mysql` 认为索引对此查询帮助不大，选择了全表查询。 如果该列是 `NULL`，则没有相关的索引。在这种情况下，可以通过检查 where 子句看是否可以创造一个适当的索引来提高查询性能，然后用 explain 查看效果

##### 6. 实际使用的索引- key

这一列显示`mysql`实际采用哪个索引来优化对该表的访问。如果没有使用索引，则该列是 NULL。如果想强制`mysql `使用或忽视 `possible_keys`列中的索引，在查询中使用 `FORCE INDEX`、`USE INDEX`或者`IGNORE INDEX`

##### 7 索引的字段长度 - key_len

`key_len`显示了`mysql`在索引里使用的字节数，字节数越大，精度越高。相同精度下，字节数越小越好。另外，通过这个值可以算出具体使用了索引中的哪些列

```
key_len计算规则如下：

字符串
char(n)：n 字节长度
varchar(n)：2 字节存储字符串长度，如果是utf-8，则长度 3n + 2
数值类型
tinyint：1 字节
smallint：2 字节
int：4 字节
bigint：8 字节
时间类型　
date：3 字节
imestamp：4 字节 (5.6.4版本开始非小数部分占用4个字节，小数部分占用0-3个字节)
datetime：8 字节 (5.6.4版本开始非小数时间部分仅占用5字节，如果有秒的小数部分会占用0-3个字节)

如果字段允许为 NULL，需要 1 字节记录是否为 NULL。索引最大长度是768字节，当字符串过长时，mysql会做一个类似左前缀索引的处理，将前半部分的字符提取出来做索引
```

##### 8. 进行索引比较的列或者常量 - ref

ref 显示了在 key 列记录的索引中，表查找值所用到的入参的列或常量，常见的有：const（常量），func，NULL，字段名（例如 user.id）

##### 9. 找到所需记录需要读取的行数 - rows

这一列是 mysql 估计要读取并检测的行数，需注意这个不是结果集里的行数

##### 10. 附加信息 - Extra

这一列展示的是额外信息。常见的重要值如下：

| 候选值                                         | 含义                                                         |
| ---------------------------------------------- | ------------------------------------------------------------ |
| `Using index condition`                        | 使用了 `ICP(Index Condition Pushed)` 优化，也就是在数据存储层取出索引的同时，判断是否可以使用 where 条件过滤，某些情况下可以大幅减少上层SQL 层对记录的索取 |
| `Using MRR`                                    | 使用了 `MRR(Multi-Range Read)` 优化，也就是在查询辅助索引时，将得到的结果按照主键进行排序，然后按照主键排序的顺序进行数据查找，从而将随机访问转化为较为顺序的数据访问 |
| `distinct`                                     | 一旦 `mysql` 找到了与行相联合匹配的行，就不再搜索了          |
| `Not exists`                                   | `mysql `优化了`LEFT JOIN`，一旦它找到了匹配`LEFT JOIN`标准的行，就不再搜索了 |
| `Using index`                                  | 这发生在对表的请求列都是同一索引的部分的时候，返回的列数据只使用了索引中的信息，而没有再去访问表中的行记录，也就是`覆盖索引`，是性能高的表现 |
| `Using where`                                  | `mysql `服务器将在存储引擎检索行后再进行过滤。就是先读取整行数据，再按 where 条件进行检查，符合就留下，不符合就丢弃 |
| `Using temporary`                              | `mysql `需要创建一张临时表来处理查询，常见于 `group by ` `order by`。出现这种情况一般是要进行优化的，首先是想到用索引来优化 |
| `Using filesort`                               | `mysql `会对结果使用一个外部索引排序，而不是按索引次序从表里读取行。此时`mysql`会根据联接类型浏览所有符合条件的记录，并保存排序关键字和行指针，然后排序关键字并按顺序检索行信息。这种情况下一般也是要考虑使用索引来优化的 |
| `Range checked for each Record（index map:N）` | 没有找到理想的索引，因此对于从前面表中来的每一个行组合，`mysql`检查使用哪个索引，并用它来从表中返回行。这是使用索引的最慢的连接之一 |
| `Using join buffer`                            | 强调了在获取连接条件时没有使用索引，并且需要连接缓冲区来存储中间结果。如果出现了这个值，那么根据查询的具体情况可能需要添加索引来改进性能 |

























