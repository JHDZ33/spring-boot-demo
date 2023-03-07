### 常用动态sql

摘自：https://segmentfault.com/a/1190000039335704介绍得十分详细，在此摘抄重点和易疏忽部分

##### 1. if

```xml
<select id="findUser">
    select * from User where 1=1
    <if test=" age != null ">
        and age > #{age}
    </if>
    
    <if test=" name != null and name !=''">
        and name like concat(#{name},'%')
    </if>
</select>
```

```xml
	<!-- name为List时判空 -->
	<if test=" name != null and name.size() > 0">
     
    </if>
```



##### 2. choose

```xml
<select id="findUser">
    select * from User where 1=1 
    <choose>
        <when test=" age != null ">
            and age > #{age}
        </when>
        <when test=" name != null ">
            and name like concat(#{name},'%')
        </when>
        <otherwise>
            and sex = '男'
        </otherwise>
    </choose>
</select>
```

##### 3. foreach

```xml
<select id="findAll">
    select  * from user where ids in 
    <foreach collection="list"
        item="item" index="index" 
        open="(" separator="," close=")">
            #{item}
    </foreach>
</select>

<select id="findAll">
    select  * from user where 
    <foreach collection="list"
        item="item" index="index" 
        open=" " separator=" or " close=" ">
            id = #{item}
    </foreach>
</select>
```

- foreach 标签：顶层的遍历标签，单独使用无意义

- collection 属性：必填，Map 或者数组或者列表的属性名（List为list、数组为array）

  ```xml
  <!-- 传入参数为List时xml 配置 -->
  
  <select id="findAll">
      select  * from user where ids in 
      <foreach collection="list"
          item="item" index="index" 
          open="(" separator="," close=")">
              #{item}
      </foreach>
  </select>
  <!-- 传入参数为数组时xml 配置 -->
  <select id="findAll">
      select  * from user where ids in 
      <foreach collection="array"
          item="item" index="index" 
          open="(" separator="," close=")">
              #{item}
      </foreach>
  </select>
  ```

  **当传入的参数为 Map 对象时**

  系统并 **不会** 默认添加一个 key 值，需要手工传入，例如传入 key 值为 map2 的集合对象，在  foreach 标签中可以直接通过 collection="map2" 获取到 Map 对象，并且 item 代表每次迭代的的 value  值，index 代表每次迭代的 key 值。其中 item 和 index 的值名词可以随意定义，例如 item =  "value111"，index ="key111"。

  ```java
  // java 代码
  Map map2 = new HashMap<>();
  map2.put("k1",1);
  map2.put("k2",2);
  map2.put("k3",3);
  
  Map map1 = new HashMap<>();
  map1.put("map2",map2);
  sqlSession.selectList("findAll",map1);	
  ```

  ```xml
  <!-- xml 配置 -->
  <select id="findAll">
      select  * from user where
      <foreach collection="map2"
          item="value111" index="key111" 
          open=" " separator=" or " close=" ">
          id = #{value111}
      </foreach>
  </select>
  ```

- item 属性：变量名，值为遍历的每一个值（可以是对象或基础类型），如果是对象那么依旧是 OGNL 表达式取值即可，例如 #{item.id} 、#{ user.name } 等

- index 属性：索引的属性名，在遍历列表或数组时为当前索引值，当迭代的对象时 Map 类型时，该值为 Map 的键值（key）

- open 属性：循环内容开头拼接的字符串，可以是空字符串

- close 属性：循环内容结尾拼接的字符串，可以是空字符串

- separator 属性：每次循环的分隔符

**注意**：使用 foreach 标签时，需要对传入的 collection  参数（List/Map/Set等）进行为空性判断，否则动态 SQL 会出现语法异常，例如你的查询语句可能是 select  * from  user where ids in () ，导致以上语法异常就是传入参数为空，解决方案可以用 if 标签或 choose  标签进行为空性判断处理，或者直接在 Java 代码中进行逻辑处理即可，例如判断为空则不执行 SQL 。

##### 4. where、set

需要配合 if 标签使用，单独使用无意义

在 where 子句后面拼接了 1=1 的条件语句块，目的是为了保证后续条件能够正确拼接，以前在程序代码中使用字符串拼接 SQL 条件语句常常如此使用，但是确实此种方式不够体面，也显得我们不高级。

```xml
<select id="findUser">
    select * from User where 1=1
    <if test=" age != null ">
        and age > #{age}
    </if>
    <if test=" name != null ">
        and name like concat(#{name},'%')
    </if>
</select>
```

那么怎么替代呢

```xml
<select id="findUser">
    select * from User 
    <where>
        <if test=" age != null ">
            and age > #{age}
        </if>
        <if test=" name != null ">
            and name like concat(#{name},'%')
        </if>
    </where>
</select>
```

我们只需把 where 关键词以及 1=1 改为 < where > 标签即可

另外还有一个特殊的处理能力，就是 where  标签能够智能的去除（忽略）首个满足条件语句的前缀，例如以上条件如果 age 和 name 都满足，那么 age 前缀 and  会被智能去除掉，无论你是使用 and 运算符或是 or 运算符，Mybatis 框架都会帮你智能处理。

如果在 where 标签之后添加了注释，那么当有子元素满足条件时，除了 < !-- --> 注释会被 where  忽略解析以外，其它注释例如 // 或 /**/ 或 -- 等都会被 where 当成首个子句元素处理，导致后续真正的首个 AND 子句元素或  OR 子句元素没能被成功替换掉前缀，从而引起语法错误！



```xml
<update id="updateUser">
    update user 
       set age = #{age},
           username = #{username},
           password = #{password} 
     where id =#{id}
</update> 
```

```xml
<update id="updateUser">
    update user 
        <set>
           <if test="age !=null">
               age = #{age},
           </if>
           <if test="username !=null">
                  username = #{username},
           </if> 
           <if test="password !=null">
                  password = #{password},
           </if>
        </set>    
     where id =#{id}
</update> 
```

注意：set 标签下需要保证至少有一个条件满足，否则依然会产生语法错误，例如在无子句条件满足的场景下

一般需要在应用程序中进行逻辑处理，判断是否存在至少一个参数，否则不执行更新 SQL 。所以原则上要求 set 标签下至少存在一个条件满足，同时每个条件子句都建议在句末添加逗号 ,最后一个条件语句可加可不加。**或者** 每个条件子句都在句首添加逗号 ,第一个条件语句可加可不加

同时set标签和where一样也会智能去除处理，也会有注释问题

##### 5. trim

不常用

##### 6. bind

不常用

##### 7. sql + include

```xml
<!-- 可复用的字段语句块 -->
<sql id="userColumns">
    id,username,password 
</sql>
```

```xml
<!-- 查询时简单复用 -->
<select id="selectUsers" resultType="map">
  select
    <include refid="userColumns"></include> 
  from user 
</select>

<!-- 插入时简单复用 -->
<insert id="insertUser" resultType="map">
  insert into user(
    <include refid="userColumns"></include> 
  )values(
    #{id},#{username},#{password} 
  )  
</insert>
```

##### 8. resulitMap

```xml
<resultMap type="com.zyiot.road.modules.base.entity.TerminalEntity" id="terminalMap">
    <result property="id" column="ID"/>
    <result property="tenantId" column="TENANT_ID"/>
    <result property="deptId" column="DEPT_ID"/>
    <result property="name" column="NAME"/>
    <result property="identifier" column="IDENTIFIER"/>
    <result property="simCard" column="SIM_CARD"/>
    <result property="code" column="CODE"/>
    <result property="model" column="MODEL"/>
</resultMap>
```











### 其他

模糊查询：

```sql
select * from user where name like concat('%',#{name},'%')
```

