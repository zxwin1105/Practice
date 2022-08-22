package com.pg.domain;

import com.sun.jmx.snmp.Timestamp;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: zhaixinwei
 * @date: 2022/5/17
 * @description:
 */
@Data
public class Student {

    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private String address;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
