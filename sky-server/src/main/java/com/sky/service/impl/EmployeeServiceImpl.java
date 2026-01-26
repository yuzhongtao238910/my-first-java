package com.sky.service.impl;
import lombok.extern.slf4j.Slf4j;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);




        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // ENDTODO 后期需要进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(password);
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }


    @Override
    public Employee save(EmployeeDTO employeeDTO) {
        System.out.println("当前线程的id：" + Thread.currentThread().getId());
//        Long currentUser = Thread.currentThread().getId();
        // 对象的转换
        Employee employee = new Employee();

        // 对象的属性拷贝的方式
        // 前提是属性名字必须是一致的
        BeanUtils.copyProperties(employeeDTO, employee);

        // 设置账号的状态 1表示正常 0是锁定
        employee.setStatus(StatusConstant.ENABLE);

        // 还需要设置这个password,初始密码都是 1 2 3 4 5 6
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        // 设置当前记录创建时间 修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // 设置创建人 修改人
        // 指的是当前登陆用户的id 先写死哈 后期需要改为当前的登陆用户的id
        // TODO
        // 获取当前的登陆用户的id
        // 基于jwt的认证的方式
        // 获取当前登陆用户的id


        Long currentId = BaseContext.getCurrentId();
        System.out.println("currentId: " + currentId);


        employee.setCreateUser(currentId);
        employee.setUpdateUser(currentId);


        int result = employeeMapper.insert(employee);







        // 2- 判断这个电话号是否是符合格式

        return employee;
    }


    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageResult(EmployeePageQueryDTO employeePageQueryDTO) {

        // select * from employee limit 0, 10

        // 新的插件 mybatis pageHelper

        // 一个线程里可以有 多个 ThreadLocal
        // 开始分页查询, 这个使用了这个ThreadLocal
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        Page<Employee> result = employeeMapper.pageQuery(employeePageQueryDTO);


        System.out.println(result.getResult());

        return new PageResult(result.getTotal(), result.getResult());
    }


    @Override
    public int startOrStop(Integer status, Long id) {

        // update employee set status = ? where id = ?



        // 方式1：创建一个employee对象
//        Employee employee = new Employee();
//        employee.setStatus(status);
//        employee.setId(id);

        // 方式2：使用builder
        Employee employee = Employee.builder()
                        .status(status)
                        .id(id)
                        .build();

        int result = employeeMapper.update(employee);

        System.out.println(result);
//        log.info("当前影响得行数:{}", result);
        return result;
    }
}
