package com.xxxx.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2021-02-04
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据用户id查询子菜单
     * @return
     */
    List<Menu> getMenusByAdminId();

    /**
     * 根据用户角色获取菜单列表
     */
    List<Menu> getMenusWithRole();

    /**
     * 查询所有菜单
     * @return
     */
    List<Menu> getAllMenus();
}
