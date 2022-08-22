package com.pg.service.impl;

import com.pg.domain.Student;
import com.pg.mapper.StudentMapper;
import com.pg.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhaixinwei
 * @date: 2022/5/17
 * @description:
 */
@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public int insertStudent(Student student){
        return studentMapper.insertStudent(student);
    }

    @Override
    public int deleteStudent(Integer id){
        return studentMapper.deleteStudent(id);
    }

    @Override
    public int updateStudent(Student student){
        return studentMapper.updateStudent(student);
    }
}
