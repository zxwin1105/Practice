package com.pg.mapper;

import com.pg.domain.Student;

/**
 * @author: zhaixinwei
 * @date: 2022/5/17
 * @description:
 */
public interface StudentMapper {

    int insertStudent (Student student);
//    int batchInsertStudent(Student student);

    int deleteStudent(Integer id);

    int updateStudent(Student student);
}
