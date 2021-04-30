package com.bjpowernode.crm.workbench.mapper;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ChartMapper {

    List<Map<String,Object>> transaction();
}
