package lut.kj.choosepaper.user.service;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.user.domin.User;

/**
 * Created by kj on 2017/3/18.
 */
public interface UserService {
    Message addUser(User user);
    Message deleteUser(String[] ids);
    Message updateUser(User user);
    User findById(String id);
    String getUserName(String id);
    Message login(User user);
    Message logout();
}
