package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登陆接口")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "员工退出接口")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 新增员工 EmployeeDTO
     *
     * 问题1：用户名字已经存在的话，抛出异常后没有处理
     * 问题2：新增员工的时候，创建人的id和修改人的id设置为了固定的值
     */
    @PostMapping("")
    @ApiOperation(value = "新增员工接口")
    public Result<Employee> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        System.out.println("当前线程的id：" + Thread.currentThread().getId());
        log.info("新增员工: {}", employeeDTO);
        com.sky.entity.Employee employee = employeeService.save(employeeDTO);
        log.info("最后的员工数据:{}", employee);
        return Result.success(employee);
    }


    @GetMapping("/page")
    @ApiOperation(value = "查询员工接口")
    public Result<PageResult> pageEmployee(EmployeePageQueryDTO employeePageQueryDTO) {
        System.out.println(employeePageQueryDTO);
        log.info("员工分页查询，参数为{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageResult(employeePageQueryDTO);
//        PageResult pageResult = employeeService.pageResult(employeePageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 启用禁用员工账号
     */

//    /admin/employee/status/{status}?id=111
    @PostMapping("/status/{status}")
    @ApiOperation(value = "启用禁用员工账号接口")
    public Result startOtStop(@PathVariable Integer status, Long id) {
        log.info("路径参数:{}", status);
        log.info("员工id：{}", id);
        log.info("员工：{},{}", id, status);
        int res = employeeService.startOrStop(status, id);
        log.info("影响得行数：{}", res);
        return Result.success();
    }

}
