package com.xxxx.server.service.impl;

import com.xxxx.server.pojo.Department;
import com.xxxx.server.mapper.DepartmentMapper;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-02-04
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    /**
     * 获取所有部门
     * @return
     */
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments(-1);
    }

    /**
     * 添加部门
     * @return
     */
    @Override
    public RespBean addDep(Department dep) {
//        直接调用存储过程
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
//        返回参数 判断
        if (dep.getResult() ==1){
            return RespBean.success("添加成功",dep);
        }
        return RespBean.error("添加失败");
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @Override
    public RespBean deleteDep(Integer id) {
        Department dep = new Department();
        dep.setId(id);
        departmentMapper.deleteDep(dep);
        if (dep.getResult() == -2){
            return RespBean.error("该部门下还有子部门,无法被删除");
        }
        if (dep.getResult() == -1){
            return RespBean.error("该部门下有员工,不能删除");
        }
        if(dep.getResult() ==1){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
