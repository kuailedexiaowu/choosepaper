package lut.kj.choosepaper.paper.controller;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.paper.domin.Paper;
import lut.kj.choosepaper.paper.invo.AddPaperIn;
import lut.kj.choosepaper.paper.invo.UpdatePaperIn;
import lut.kj.choosepaper.paper.service.PaperService;
import lut.kj.choosepaper.utils.PageInfo;
import lut.kj.choosepaper.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kj on 2017/3/15.
 */
@RestController
@RequestMapping("/paper")
public class PaperController {
    @Autowired
    PaperService paperService;

    @RequestMapping("/add")
    public Message addPaper(@RequestBody AddPaperIn addPaperIn){
        Paper paper = new Paper();
        BeanUtils.copyProperties(addPaperIn, paper);
        paper.setTeacherId(UserUtils.getUserId());
        return paperService.addPaper(paper);
    }
    @RequestMapping("/update")
    public Message updatePaper(@RequestBody UpdatePaperIn updatePaperIn){
        Paper newPaper = new Paper();
        Paper oldPaper = paperService.selectById(updatePaperIn.getId());
        BeanUtils.copyProperties(updatePaperIn, newPaper);
        newPaper.setCreateTime(oldPaper.getCreateTime());
        newPaper.setStudent_Id(oldPaper.getId());
        return paperService.updatePaper(newPaper);
    }

    @RequestMapping("/delete")
    public Message deletePaper(String id){
        return paperService.deletePaper(id);
    }

    @RequestMapping("/list")
    public PageInfo<Paper> listAll(int pageSize, int pageNo){
        return paperService.listAll(pageSize, pageNo);
    }

    public PageInfo<Paper> listByTeacherId(int pageSize, int pageNo){
        return paperService.listByTeacherId(pageSize,pageNo);
    }
}
