package com.jhdz.excel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jhdz.excel.entity.DemoData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DemoDao extends BaseMapper<DemoData> {

}
