package lut.kj.choosepaper.paper.service.impl;


import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.mapper.PaperMapper;
import lut.kj.choosepaper.mapper.StudentMapper;
import lut.kj.choosepaper.mapper.TeacherMapper;
import lut.kj.choosepaper.paper.domin.Paper;
import lut.kj.choosepaper.paper.revo.PaperDetailVo;
import lut.kj.choosepaper.paper.service.PaperService;
import lut.kj.choosepaper.student.domin.Student;
import lut.kj.choosepaper.teacher.domin.Teacher;
import lut.kj.choosepaper.utils.PageInfo;
import lut.kj.choosepaper.utils.UserUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kj on 2017/3/15.
 */
@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    PaperMapper paperMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public Message addPaper(Paper paper){
        paperMapper.insert(paper);
        return new Message("插入成功");
    }

    @Override
    public Message updatePaper(Paper paper) {
        paperMapper.updateByPrimaryKey(paper);
        return new Message("更新成功");
    }

    @Override
    public Paper selectById(String id) {
        Paper paper=paperMapper.selectByPrimaryKey(id);
        return paper;
    }

    @Override
    public Paper getMyPaper(String id) {
        Example example = new Example(Paper.class);
        example.createCriteria().andEqualTo("studentId",UserUtils.getUserId());
        int count = paperMapper.selectByExample(example).size();
        Paper paper = null;
        if(count > 0){
            paper = paperMapper.selectByExample(example).get(0);
        }
        if(null != paper)
            return paper;
        else
            return null;
    }

    @Override
    public Message deletePaper(String[] ids) {
        for(String id:ids){
        paperMapper.deleteByPrimaryKey(id);}
        return new Message("删除成功");
    }

    @Override
    public Message choosePaper(String id) {
        Example example = new Example(Paper.class);
        example.createCriteria().andEqualTo("studentId",UserUtils.getUserId());
        int count = paperMapper.selectCountByExample(example);
        if(count > 0){
            return new Message("你已经选择过了，不能再选！");
        }
        Paper paper=new Paper();
        paper.setId(id);
        paper=paperMapper.selectByPrimaryKey(paper);
        paper.setStudentId(UserUtils.getUserId());
        paperMapper.updateByPrimaryKey(paper);
        return new Message("选择成功");
    }

    @Override
    public PageInfo<Paper> listAll(Integer pageNo, Integer pageSize) {
        int totalSize = paperMapper.selectAll().size();
        RowBounds rowBounds = new RowBounds((pageNo-1)*pageSize, pageSize);
        List<Paper> papers = paperMapper.selectByRowBounds(null, rowBounds);
        PageInfo<Paper> pageInfo=new PageInfo<Paper>(papers);
        pageInfo.setCurrentPage(pageNo);
        pageInfo.setTotal(totalSize);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(papers.size());
        if(totalSize % pageSize != 0){
            pageInfo.setPageCount((totalSize / pageSize) + 1);
        }
        else{
            pageInfo.setPageCount(totalSize / pageSize);
        }
        return pageInfo;
    }

    @Override
    public PageInfo<Paper> listByTeacherId(Integer pageNo, Integer pageSize) {

        int totalSize = paperMapper.selectAll().size();
        RowBounds rowBounds = new RowBounds((pageNo-1)*pageSize, pageSize);
        Paper paper = new Paper();
        paper.setId(null);
        paper.setTeacherId(UserUtils.getUserId());
        List<Paper> papers = paperMapper.selectByRowBounds(paper, rowBounds);
        PageInfo<Paper> pageInfo=new PageInfo<Paper>(papers);
        pageInfo.setTotal(totalSize);
        pageInfo.setCurrentPage(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(papers.size());
        if(totalSize % pageSize != 0){
            pageInfo.setPageCount((totalSize / pageSize) + 1);
        }
        else{
            pageInfo.setPageCount(totalSize / pageSize);
        }
        return pageInfo;
    }

    @Override
    public PageInfo<Paper> listUnchoosed(Integer pageNo, Integer pageSize) {
        List<Paper> papers = paperMapper.selectAll();
        Iterator<Paper> iterator=papers.iterator();
        Paper paper;
        while(iterator.hasNext()){
            paper=iterator.next();
            if(null!=paper.getStudentId()){
                iterator.remove();
            }
        }
        int totalSize=papers.size();
        PageInfo<Paper> pageInfo=new PageInfo<Paper>(papers);
        pageInfo.setTotal(totalSize);
        pageInfo.setCurrentPage(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(papers.size());
        if(totalSize % pageSize != 0){
            pageInfo.setPageCount((totalSize / pageSize) + 1);
        }
        else{
            pageInfo.setPageCount(totalSize / pageSize);
        }
        return pageInfo;
    }

    @Override
    public PaperDetailVo detail(String id) {
        PaperDetailVo paperDetailVo=new PaperDetailVo();
        Paper paper=paperMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(paper, paperDetailVo);
        Student student=studentMapper.selectByPrimaryKey(paper.getStudentId());
        paperDetailVo.setStudent(student);
        Teacher teacher=teacherMapper.selectByPrimaryKey(paper.getTeacherId());
        paperDetailVo.setTeacher(teacher);
        return paperDetailVo;
    }


}
