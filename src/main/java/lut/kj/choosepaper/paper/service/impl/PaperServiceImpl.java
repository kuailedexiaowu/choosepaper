package lut.kj.choosepaper.paper.service.impl;


import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.mapper.PaperMapper;
import lut.kj.choosepaper.paper.domin.Paper;
import lut.kj.choosepaper.paper.service.PaperService;
import lut.kj.choosepaper.utils.PageInfo;
import lut.kj.choosepaper.utils.UserUtils;
import org.apache.ibatis.session.RowBounds;
import org.h2.mvstore.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kj on 2017/3/15.
 */
@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    PaperMapper paperMapper;

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
    public Message deletePaper(String[] ids) {
        for(String id:ids){
        paperMapper.deleteByPrimaryKey(id);}
        return new Message("删除成功");
    }

    @Override
    public PageInfo<Paper> listAll(int pageNo, int pageSize) {
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
    public PageInfo<Paper> listByTeacherId(int pageNo, int pageSize) {

        int totalSize = paperMapper.selectAll().size();
        RowBounds rowBounds = new RowBounds((pageNo-1)*pageSize, pageSize);
        List<Paper> papers = paperMapper.selectByRowBounds(null, rowBounds);
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
}
