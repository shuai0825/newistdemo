package com.example.newistdemo.controller;

import com.example.newistdemo.entity.User;
import com.example.newistdemo.exception.UsernameIsExitedException;
import com.example.newistdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/info")
public class InfoController {
    @Autowired
    private UserRepository userRepository;
    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/userList")
    public Map<String, Object> userList(){
        List<User> users = userRepository.findAll();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("users",users);
        return map;
    }

    /**
     * 注册用户 默认开启白名单
     * @param
     */
    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        User bizUser = userRepository.findByUsername(user.getUsername());
        if(null != bizUser){
            throw new UsernameIsExitedException("用户已经存在");
        }
        user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()).getBytes()));
        return userRepository.save(user);
    }
    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/hello")
    public Map<String, Object> hello(){
        List<User> users = userRepository.findAll();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("users",users);
        return map;
    }
    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/world")
    public Map<String, Object> world(){
        List<User> users = userRepository.findAll();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("users",users);
        return map;
    }

}
