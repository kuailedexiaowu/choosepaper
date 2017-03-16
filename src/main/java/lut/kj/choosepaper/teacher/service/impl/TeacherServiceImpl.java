package lut.kj.choosepaper.teacher.service.impl;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.mapper.TeacherMapper;
import lut.kj.choosepaper.paper.domin.Paper;
import lut.kj.choosepaper.teacher.domin.Teacher;
import lut.kj.choosepaper.teacher.service.TeacherService;
import lut.kj.choosepaper.utils.PageInfo;
import lut.kj.choosepaper.utils.UserUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kj on 2017/3/15.
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;
    @Override
    public Message addTeacher(Teacher teacher) {
        teacherMapper.insert(teacher);
        return new Message("插入成功");
    }

    @Override
    public Message updateTeacher(Teacher teacher) {
        teacherMapper.insert(teacher);
        return new Message("更新成功");
    }

    @Override
    public Message deleteTeacher(String id) {
        teacherMapper.deleteByPrimaryKey(id);
        return new Message("删除成功");
    }

    @Override
    public PageInfo<Teacher> listAll(int pageSize, int pageNo) {
        int totalSize = teacherMapper.selectAll().size();
        RowBounds rowBounds = new RowBounds((pageNo-1)*pageSize, pageSize);
        Teacher teacher = new Teacher();
        List<Teacher> teachers = teacherMapper.selectByRowBounds(teacher, rowBounds);
        PageInfo<Teacher> pageInfo=new PageInfo<Teacher>(teachers);
        pageInfo.setTotal(totalSize);
        pageInfo.setCurrentPage(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(teachers.size());
        if(totalSize % pageSize != 0){
            pageInfo.setPageCount((totalSize % pageSize) + 1);
        }
        else{
            pageInfo.setPageCount(totalSize % pageSize);
        }
        return pageInfo;
    }
}
