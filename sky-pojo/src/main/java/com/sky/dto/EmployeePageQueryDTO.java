package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "员工分页查询时的DTO")
public class EmployeePageQueryDTO implements Serializable {

    @ApiModelProperty("员工姓名")
    //员工姓名
    private String name;

    //页码
    @ApiModelProperty("页码")
    private int page;

    //每页显示记录数
    @ApiModelProperty("每页多少个")
    private int pageSize;

}
