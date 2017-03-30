package lut.kj.choosepaper.user.service.impl;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.mapper.UserMapper;
import lut.kj.choosepaper.user.domin.User;
import lut.kj.choosepaper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;

/**
 * Created by kj on 2017/3/18.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public Message addUser(User user) {
        String password=encode(user.getPassword());
        user.setPassword(password);
        userMapper.insert(user);
        return new Message("插入成功");
    }

    @Override
    public Message deleteUser(String[] ids) {
        for(String id:ids){
            userMapper.deleteByPrimaryKey(id);
        }
        return new Message("删除成功");
    }

    @Override
    public Message updateUser(User user) {
        String password=encode(user.getPassword());
        user.setPassword(password);
        userMapper.updateByPrimaryKey(user);
        return new Message("更新成功");
    }

    @Override
    public User findById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    private String encode(String password) {//对密码进行加密
        byte[] bytes=null;
        MessageDigest messageDigest=null;
        try{
        bytes=password.getBytes("utf8");
        messageDigest=MessageDigest.getInstance("MD5");
        }catch(Exception e){
            e.printStackTrace();
        }
        messageDigest.update(bytes);
        byte[] bytes1=messageDigest.digest();
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int j = bytes1.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (byte byte0 : bytes1) {
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return  new String(str);
    }
}
