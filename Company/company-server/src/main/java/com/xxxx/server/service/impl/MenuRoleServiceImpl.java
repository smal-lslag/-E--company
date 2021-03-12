package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.server.pojo.MenuRole;
import com.xxxx.server.mapper.MenuRoleMapper;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IMenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-02-04
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    /**
     * 更新用户菜单
     * @param rid
     * @param mids
     * @return
     * 先删除这用户下的全部菜单id   之后加上新增加的菜单id]
     */
    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    @Transactional
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
//      用用户id  删除 对应的菜单id    t_menu_role 表
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid" ,rid));
//        添加
        if (null==mids || 0 ==mids.length){
            return RespBean.success("更新成功");
        }
//        批量更新
        Integer result =  menuRoleMapper.insertRecord(rid,mids);
        if (result == mids.length){
            return RespBean.success("更新成功");
        }

        return RespBean.error("更新失败");
    }
}
