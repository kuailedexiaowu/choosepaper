package lut.kj.choosepaper.user.service.impl;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.mapper.StudentMapper;
import lut.kj.choosepaper.mapper.TeacherMapper;
import lut.kj.choosepaper.mapper.UserMapper;
import lut.kj.choosepaper.student.domin.Student;
import lut.kj.choosepaper.teacher.domin.Teacher;
import lut.kj.choosepaper.user.domin.User;
import lut.kj.choosepaper.user.service.UserService;
import lut.kj.choosepaper.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;

/**
 * Created by kj on 2017/3/18.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TeacherMapper teacherMapper;

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

    @Override
    public String getUserName(String id) {
        String userName = null;
        Student student = studentMapper.selectByPrimaryKey(id);
        if(null != student){
            userName = student.getName();
        }
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        if(null != teacher){
            userName = teacher.getName();
        }
        return userName;
    }

    @Override
    public Message login(User user){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession httpSession = UserUtils.getSession();
        Student student = studentMapper.selectByPrimaryKey(user.getUserName());
        Teacher teacher = teacherMapper.selectByPrimaryKey(user.getUserName());
        try{
        if(null!= student){
            User user1 = userMapper.selectByPrimaryKey(user.getUserName());
            if(user1.getPassword().equals(encode(user.getPassword()))){
            response.sendRedirect(request.getContextPath()+"/student.html");
            httpSession.setAttribute("userId",user.getUserName());
            httpSession.setAttribute("role","STUDENT");
            return null;}
        }
        if(null!= teacher){
            User user1 = userMapper.selectByPrimaryKey(user.getUserName());
            if(user1.getPassword().equals(encode(user.getPassword()))){
            response.sendRedirect(request.getContextPath()+"/teacher.html");
            httpSession.setAttribute("userId",user.getUserName());
            httpSession.setAttribute("role","TEACHER");
            return null;}
        }}catch (Exception e){
            return new Message("网络错误");
        }
        if("13240206".equals(user.getUserName())){
            User user1 = userMapper.selectByPrimaryKey(user.getUserName());
            if(user1.getPassword().equals(encode(user.getPassword()))){
                try {
                    response.sendRedirect(request.getContextPath()+"/admin.html");
                    httpSession.setAttribute("userId",user.getUserName());
                    httpSession.setAttribute("role","ADMIN");
                    return null;
                }catch (Exception e){
                    return new Message("网络错误");
                }
            }
        }
        return new Message("用户不存在");
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
