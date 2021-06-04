package com.rm.idataecs.idataecs.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rm.idataecs.idataecs.monitor.entity.MonitorConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CreateOrUpdateMonitorConfigMapper extends BaseMapper<MonitorConfigEntity> {


    List<MonitorConfigEntity> getMonitorByTableName(@Param("table_name") String tableName);


}
