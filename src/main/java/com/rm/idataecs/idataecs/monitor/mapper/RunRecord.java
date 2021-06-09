package com.rm.idataecs.idataecs.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rm.idataecs.idataecs.monitor.entity.RunRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RunRecord extends BaseMapper<RunRecordEntity> {

    /**
     * 返回数据result_sqlCheck 为null &query_time-currentTime<=10min
     * @return
     */
    List<RunRecordEntity> getRecordByQueryTime();

}
