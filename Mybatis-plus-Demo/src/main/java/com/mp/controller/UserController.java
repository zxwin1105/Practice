package com.mp.controller;

import com.mp.entity.User;
import com.mp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author zhaixinwei
 * @date 2022/8/3
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public boolean saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/{id}")
    public User queryUser(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @GetMapping("/query/all")
    public List<User> queryAllUser(){
        return userService.list();
    }

    @GetMapping("/date")
    public User date(){
        User user = new User();
        user.setAge(32);
        user.setCreateTime(new Date());

        return user;
    }


}
