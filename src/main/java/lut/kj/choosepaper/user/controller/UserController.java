package lut.kj.choosepaper.user.controller;

/**
 * Created by kj on 2017/3/18.
 */

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.user.domin.User;
import lut.kj.choosepaper.user.invo.AddUserIn;
import lut.kj.choosepaper.user.invo.UpdateUserIn;
import lut.kj.choosepaper.user.service.UserService;
import lut.kj.choosepaper.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/add")
    public Message addUser(@RequestBody AddUserIn addUserIn){
        User user = new User();
        BeanUtils.copyProperties(addUserIn,user);
        return userService.addUser(user);
    }

    @RequestMapping("/delete")
    public Message deleteUser(@RequestBody String[] ids){
        return userService.deleteUser(ids);
    }

    @RequestMapping("/update")
    public Message updateUser(@RequestBody UpdateUserIn updateUserIn){
        User user = new User();
        BeanUtils.copyProperties(updateUserIn,user);
        return userService.updateUser(user);
    }

    @RequestMapping("/findById")
    public User findById(String id){
        return userService.findById(id);
    }

    @RequestMapping("/getUserName")
    public String getUserName(String id){
        return userService.getUserName(id);
    }

    @RequestMapping("/getUserId")
    public String getUserId(){
        return UserUtils.getUserId();
    }

    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public Message login(String userName,String password){
       User user = new User();
        user.setPassword(password);
        user.setUserName(userName);
        return userService.login(user);
    }
}


