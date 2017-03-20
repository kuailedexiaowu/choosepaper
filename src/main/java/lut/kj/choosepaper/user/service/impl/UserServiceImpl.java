package lut.kj.choosepaper.user.service.impl;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.mapper.UserMapper;
import lut.kj.choosepaper.user.domin.User;
import lut.kj.choosepaper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kj on 2017/3/18.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public Message addUser(User user) {
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
        userMapper.updateByPrimaryKey(user);
        return new Message("更新成功");
    }

    @Override
    public User findById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
