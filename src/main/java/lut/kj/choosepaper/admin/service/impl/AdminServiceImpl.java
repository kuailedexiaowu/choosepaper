package lut.kj.choosepaper.admin.service.impl;

import lut.kj.choosepaper.admin.revo.StudentUser;
import lut.kj.choosepaper.admin.revo.TeacherUser;
import lut.kj.choosepaper.admin.service.AdminService;
import lut.kj.choosepaper.mapper.StudentMapper;
import lut.kj.choosepaper.mapper.TeacherMapper;
import lut.kj.choosepaper.mapper.UserMapper;
import lut.kj.choosepaper.student.domin.Student;
import lut.kj.choosepaper.teacher.domin.Teacher;
import lut.kj.choosepaper.utils.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kj on 2017/3/15.
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Override
    public PageInfo<StudentUser> listStudent(int pageNo, int pageSize) {
        int totalSize = studentMapper.selectAll().size();
        RowBounds rowBounds = new RowBounds((pageNo-1)*pageSize, pageSize);
        List<Student> students = studentMapper.selectByRowBounds(null, rowBounds);
        List<StudentUser> studentUsers = new ArrayList<StudentUser>();
        String id = null ;
        String password = null;
        for(int i=0;i<students.size();i++){
            StudentUser studentUser = new StudentUser();
            id=students.get(i).getId();
            password = userMapper.selectByPrimaryKey(id).getPassword();
            studentUser.setPassword(password);
            BeanUtils.copyProperties(students.get(i), studentUser);
            studentUsers.add(studentUser);
        }
        PageInfo<StudentUser> pageInfo = new PageInfo<StudentUser>(studentUsers);
        pageInfo.setTotal(totalSize);
        pageInfo.setCurrentPage(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(students.size());
        if(totalSize % pageSize != 0){
            pageInfo.setPageCount((totalSize / pageSize) + 1);
        }
        else{
            pageInfo.setPageCount(totalSize / pageSize);
        }
        return pageInfo;
    }

    @Override
    public PageInfo<TeacherUser> listTeacher(int pageNo, int pageSize) {
        int totalSize = teacherMapper.selectAll().size();
        RowBounds rowBounds = new RowBounds((pageNo-1)*pageSize, pageSize);
        List<Teacher> teachers = teacherMapper.selectByRowBounds(null, rowBounds);
        List<TeacherUser> teacherUsers = new ArrayList<TeacherUser>();
        String id = null ;
        String password = null;
        for(int i=0;i<teachers.size();i++){
            TeacherUser teacherUser = new TeacherUser();
            BeanUtils.copyProperties(teachers.get(i), teacherUser);
            id=teachers.get(i).getId();
            password = userMapper.selectByPrimaryKey(id).getPassword();
            teacherUser.setPassword(password);
            teacherUsers.add(teacherUser);
        }
        PageInfo<TeacherUser> pageInfo = new PageInfo<TeacherUser>(teacherUsers);
        pageInfo.setTotal(totalSize);
        pageInfo.setCurrentPage(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(teachers.size());
        if(totalSize % pageSize != 0){
            pageInfo.setPageCount((totalSize / pageSize) + 1);
        }
        else{
            pageInfo.setPageCount(totalSize / pageSize);
        }
        return pageInfo;
    }
}
