package com.mp.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zhaixinwei
 * @date 2022/8/3
 * @description 用户表
 */
@Data
@Entity
@TableName(value = "user")
public class User {

    @Id
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Integer id;

    private String name;

    @OrderBy
    private Integer age;

    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createTime;
}
