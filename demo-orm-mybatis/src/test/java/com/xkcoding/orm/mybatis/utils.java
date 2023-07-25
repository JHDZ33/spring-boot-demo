package com.xkcoding.orm.mybatis;

import com.xkcoding.orm.mybatis.entity.DataEntity;
import com.xkcoding.orm.mybatis.entity.DataResultEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class utils {
    public static List<DataResultEntity> getDataResultList(int count) {
        List<DataResultEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DataResultEntity dataResultEntity = new DataResultEntity();
            dataResultEntity.setId(i);
            dataResultEntity.setName("name" + i);
            dataResultEntity.setNow(new Date());
            dataResultEntity.setDataEntity(getDataList(1).get(0));
            list.add(dataResultEntity);
        }
        return list;
    }

    public static List<DataEntity> getDataList(int count) {
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
