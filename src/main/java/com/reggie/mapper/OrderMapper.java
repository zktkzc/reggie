package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tkzc
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
