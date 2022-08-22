package com.pg.controller;

import com.pg.domain.Student;
import com.pg.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhaixinwei
 * @date: 2022/5/17
 * @description:
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @DeleteMapping("/{id}")
    public String delete(Integer id){
        int res = studentService.deleteStudent(id);
       return res > 0 ? "success" : "failure";
    }

    @PutMapping
    public String update(Student student){
        int res = studentService.updateStudent(student);
        return res > 0 ? "success" : "failure";
    }

    @PostMapping
    public String insert(Student student){
        int res = studentService.insertStudent(student);
        return res > 0 ? "success" : "failure";
    }
}
