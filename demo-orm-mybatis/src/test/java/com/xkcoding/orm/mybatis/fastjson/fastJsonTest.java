package com.xkcoding.orm.mybatis.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xkcoding.orm.mybatis.entity.DataEntity;
import com.xkcoding.orm.mybatis.entity.DataResultEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private List<DataResultEntity> getDataResultList(int count) {
        List<DataResultEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DataResultEntity dataResultEntity = new DataResultEntity();
            dataResultEntity.setId(i);
            dataResultEntity.setName("name" + i);
            dataResultEntity.setNow(new Date());
            dataResultEntity.setDataEntityList(getDataList(2));
            list.add(dataResultEntity);
        }
        return list;
    }
    private List<DataEntity> getDataList(int count) {
        List<DataEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DataEntity dataEntity = new DataEntity();
            dataEntity.setId(i);
            dataEntity.setName("name" + i);
            dataEntity.setNow(new Date());
            list.add(dataEntity);
        }
        return list;
    }
}
