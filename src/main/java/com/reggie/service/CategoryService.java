package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.entity.Category;

/**
 * @author tkzc
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long ids);
}
