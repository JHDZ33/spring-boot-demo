package com.xkcoding.orm.mybatis.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xkcoding.orm.mybatis.entity.DataEntity;
import com.xkcoding.orm.mybatis.entity.DataResultEntity;
import org.junit.Test;

import java.util.List;

import static com.xkcoding.orm.mybatis.utils.getDataList;
import static com.xkcoding.orm.mybatis.utils.getDataResultList;

public class fastJsonTest {

    @Test
    public void parseObjectTest1() {
        List<DataEntity> dataList = getDataList(10);
        String json = JSON.toJSON(dataList).toString();
        List<DataEntity> dataEntity = JSON.parseObject(json, new TypeReference<List<DataEntity>>() {});
        System.out.println(dataEntity.toString());
    }

    @Test
    public void parseObjectTest2() {
        List<DataResultEntity> dataResultList = getDataResultList(10);
        String json = JSON.toJSON(dataResultList.get(0)).toString();
        DataResultEntity dataEntity = JSON.parseObject(json, new TypeReference<DataResultEntity>() {});
        System.out.println(dataEntity.toString());
    }

    @Test
    public void parseObjectTest3() {
        List<DataResultEntity> dataResultList = getDataResultList(10);
        String json = JSON.toJSON(dataResultList.get(0)).toString();
        DataResultEntity dataEntity = JSON.parseObject(json, DataResultEntity.class);
        System.out.println(dataEntity.toString());
    }


}
