package com.pg.service;

import com.pg.domain.Student;

/**
 * @author: zhaixinwei
 * @date: 2022/5/17
 * @description:
 */
public interface IStudentService {
    int insertStudent(Student student);

    int deleteStudent(Integer id);

    int updateStudent(Student student);
}
