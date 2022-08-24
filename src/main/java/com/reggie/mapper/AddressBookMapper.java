package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tkzc
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
