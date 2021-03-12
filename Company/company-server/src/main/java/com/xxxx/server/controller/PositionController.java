package com.xxxx.server.controller;


import com.xxxx.server.pojo.Position;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IPositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *职位管理
 * @author zhoubin
 * @since 2021-02-04
 */

@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    private IPositionService positionService;
    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.list();
    }

    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    public RespBean addProsition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if (positionService.save(position)){
            return RespBean.success("添加成功");
        }else {
            return  RespBean.success("添加失败");
        }

    }
    @ApiOperation(value = "更新职位信息")
    @PutMapping("/")
    public  RespBean updatePosition(@RequestBody Position position){
        if (positionService.updateById(position)){
            return RespBean.success("更新成功");

        }else{
            return  RespBean.success("更新失败");

        }
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id){
        if (positionService.removeById(id)){
            return RespBean.success("删除成功");
        }else{
            return  RespBean.success("删除失败");
        }
    }

    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public  RespBean deletePositionByIds(Integer[] ids){
        if (positionService.removeByIds(Arrays.asList(ids))){
            return  RespBean.success("批量删除成功");

        }else{
            return  RespBean.success("批量删除失败");
        }
    }

}
