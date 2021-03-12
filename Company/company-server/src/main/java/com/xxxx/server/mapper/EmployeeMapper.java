package com.xxxx.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.server.pojo.Employee;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2021-02-04
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 分页获取所有员工
     * @param page
     * @param employee
     * @param beginDateScope
     * @return
     */
    IPage<Employee> getEmployeeByPage(Page<Employee> page, @Param("employee")Employee employee, @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 导出所有员工
     * 先查询所有员工
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 获取所有员工的工资系信息
     * @return
     */
    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);
}
