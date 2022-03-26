package com.plusEntity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @NoArgsConstructor : 无参
 * @AllArgsConstructor ： 有参
 * @RequiredArgsConstructor ：生成一个包含 "特定参数" 的构造器，特定参数指的是那些有加上 final 修饰词的变量们（特定参数为final修饰的）
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "authic")
public class Authic implements Serializable {
    // 主键
    @TableId(value = "id")
    private String id;
    // 用户名
    private String name;
    // 密码
    private String password;
    // 权限
    private String roles;
    // 分配权限,需要指定url,才能访问
    private String authc;

}
