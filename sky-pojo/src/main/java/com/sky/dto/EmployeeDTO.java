package com.sky.dto;

import lombok.Data;

import java.io.Serializable;
//@Data 等价于同时使用了 以下 Lombok 注解：
//
//注解	功能
//@Getter	为 所有非静态字段 生成 getXXX() 方法
//@Setter	为 所有非 final 字段 生成 setXXX() 方法
//@ToString	自动生成 toString() 方法，输出对象字段信息
//@EqualsAndHashCode	自动生成 equals() 和 hashCode() 方法
//@RequiredArgsConstructor	自动生成 带 final 字段和 @NonNull 字段 的构造方法
@Data
public class EmployeeDTO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}
