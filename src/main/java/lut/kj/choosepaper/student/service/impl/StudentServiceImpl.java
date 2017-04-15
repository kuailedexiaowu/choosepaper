package lut.kj.choosepaper.student.service.impl;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.mapper.PaperMapper;
import lut.kj.choosepaper.mapper.StudentMapper;
import lut.kj.choosepaper.paper.domin.Paper;
import lut.kj.choosepaper.student.domin.Student;
import lut.kj.choosepaper.student.service.StudentService;
import lut.kj.choosepaper.utils.PageInfo;
import lut.kj.choosepaper.utils.UserUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by kj on 2017/3/15.
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    PaperMapper paperMapper;

    @Override
    public Message addStudent(Student student) {
        studentMapper.insert(student);
        return new Message("插入成功");
    }

    @Override
    public Message updateStudent(Student student) {
        studentMapper.updateByPrimaryKey(student);
        return new Message("更新成功");
    }

    @Override
    public Student findById(String id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    @Override
    public Message deleteStudent(String[] ids) {
        for(String id : ids){
        studentMapper.deleteByPrimaryKey(id);}
        return new Message("删除成功");
    }

    @Override
    public PageInfo<Student> listAll(Integer pageNo, Integer pageSize) {
        int totalSize = studentMapper.selectAll().size();
        RowBounds rowBounds = new RowBounds((pageNo-1)*pageSize, pageSize);
        List<Student> students = studentMapper.selectByRowBounds(null, rowBounds);
        PageInfo<Student> pageInfo=new PageInfo<Student>(students);
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
    public int queryPaperTotalCount() {
        return paperMapper.selectCount(null);
    }

    @Override
    public int queryPaperUnchoosedCount() {
        Example example = new Example(Paper.class);
        example.createCriteria().andIsNull("studentId");
        return paperMapper.selectCountByExample(example);
    }

    @Override
    public int queryPaperChoosedCount() {
        Example example = new Example(Paper.class);
        example.createCriteria().andIsNotNull("studentId");
        return paperMapper.selectCountByExample(example);
    }

    @Override
    public int queryStudentCount() {
        return studentMapper.selectCount(null);
    }
}
