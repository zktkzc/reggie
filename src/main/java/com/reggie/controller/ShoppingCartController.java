package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reggie.common.BaseContext;
import com.reggie.common.R;
import com.reggie.entity.ShoppingCart;
import com.reggie.service.ShoppIngCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.compiler.ast.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 购物车管理
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppIngCartService shoppIngCartService;

    /**
     * 添加购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车数据：{}", shoppingCart);

        // 设置用户id，指定当前是那个用户的购物车
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        // 查询当前菜品或套餐是否在购物车中
        Long dishId = shoppingCart.getDishId();
        if (dishId != null) {
            // 添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            // 添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart cartServiceOne = shoppIngCartService.getOne(queryWrapper);

        if (cartServiceOne != null) {
            // 如果已经存在，就在原来数量基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            shoppIngCartService.updateById(cartServiceOne);
        } else {
            // 如果不存在，则添加到购物车，默认数量就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppIngCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }

        return R.success(cartServiceOne);
    }

    /**
     * 查看购物车
     *
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        log.info("查看购物车...");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppIngCartService.list(queryWrapper);

        return R.success(list);
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean() {

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        shoppIngCartService.remove(queryWrapper);

        return R.success("清空购物车成功");
    }
}
