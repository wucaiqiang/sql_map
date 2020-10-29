package com.wu.sqlmap.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class UserModel {
    private Long id;
    private String name;
    private String email;
}
