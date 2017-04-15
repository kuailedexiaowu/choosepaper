package lut.kj.choosepaper.teacher.service.impl;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.mapper.PaperMapper;
import lut.kj.choosepaper.mapper.TeacherMapper;
import lut.kj.choosepaper.paper.domin.Paper;
import lut.kj.choosepaper.teacher.domin.Teacher;
import lut.kj.choosepaper.teacher.service.TeacherService;
import lut.kj.choosepaper.utils.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by kj on 2017/3/15.
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    PaperMapper paperMapper;

    @Override
    public Message addTeacher(Teacher teacher) {
        teacherMapper.insert(teacher);
        return new Message("插入成功");
    }

    @Override
    public Message updateTeacher(Teacher teacher) {
        teacherMapper.updateByPrimaryKey(teacher);
        return new Message("更新成功");
    }

    @Override
    public Message deleteTeacher(String[] ids) {
        for(String id : ids){
        teacherMapper.deleteByPrimaryKey(id);}
        return new Message("删除成功");
    }

    @Override
    public Teacher findById(String id) {
        return teacherMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Teacher> listAll(Integer pageNo, Integer pageSize) {
        int totalSize = teacherMapper.selectAll().size();
        RowBounds rowBounds = new RowBounds((pageNo-1)*pageSize, pageSize);
        List<Teacher> teachers = teacherMapper.selectByRowBounds(null, rowBounds);
        PageInfo<Teacher> pageInfo=new PageInfo<Teacher>(teachers);
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
    @Override
    public int queryPaperTotalCount(String id){
        Example example = new Example(Paper.class);
        example.createCriteria()
                .andEqualTo("teacherId",id);
        int count = paperMapper.selectCountByExample(example);
        return count;
    }

    @Override
    public int queryPaperChoosedCount(String id) {
        Example example = new Example(Paper.class);
        example.createCriteria()
                .andEqualTo("teacherId",id)
                .andIsNotNull("studentId");
        int count = paperMapper.selectCountByExample(example);
        return count;
    }

    @Override
    public int queryPaperUnchoosedCount(String id) {
        Example example = new Example(Paper.class);
        example.createCriteria()
                .andEqualTo("teacherId",id)
                .andIsNull("studentId");
        int count = paperMapper.selectCountByExample(example);
        return count;
    }

    @Override
    public int queryTeacherCount() {
        int count = teacherMapper.selectCount(null);
        return count;
    }
}
