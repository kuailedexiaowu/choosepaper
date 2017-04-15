package lut.kj.choosepaper.paper.service;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.paper.domin.Paper;
import lut.kj.choosepaper.paper.revo.PaperDetailVo;
import lut.kj.choosepaper.utils.PageInfo;

/**
 * Created by kj on 2017/3/15.
 */
public interface PaperService {
     Message addPaper(Paper paper);
     Message updatePaper(Paper paper);
     Paper selectById(String id);
     Message deletePaper(String[] ids);
    Message choosePaper(String id);
    PageInfo<Paper> listAll(Integer pageNo, Integer pageSize);
    PageInfo<Paper> listByTeacherId(Integer pageNo, Integer pageSize);
    PageInfo<Paper> listUnchoosed(Integer pageNo, Integer pageSize);
    PaperDetailVo detail(String id);


}
