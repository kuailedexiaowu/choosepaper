package lut.kj.choosepaper.paper.controller;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.paper.domin.Paper;
import lut.kj.choosepaper.paper.invo.AddPaperIn;
import lut.kj.choosepaper.paper.invo.UpdatePaperIn;
import lut.kj.choosepaper.paper.revo.PaperDetailVo;
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
        BeanUtils.copyProperties(oldPaper, newPaper);
        BeanUtils.copyProperties(updatePaperIn, newPaper);
        return paperService.updatePaper(newPaper);
    }

    @RequestMapping("/delete")
    public Message deletePaper(@RequestBody String[] ids){
        return paperService.deletePaper(ids);
    }

    @RequestMapping("/choose")
    public Message choosePaper(String id){
        return paperService.choosePaper(id);
    }

    @RequestMapping("/getMyPaper")
    public PaperDetailVo getMyPaper(String id){
       Paper paper = paperService.getMyPaper(id);
        if(null != paper){
           return paperService.detail(paper.getId());
        }
        else{
            PaperDetailVo paperDetailVo = new PaperDetailVo();
            paperDetailVo.setId("-1");
            return paperDetailVo;
        }
    }

    @RequestMapping("/list")
    public PageInfo<Paper> listAll(Integer pageNo, Integer pageSize){
        return paperService.listAll(pageNo, pageSize);
    }

    @RequestMapping("/listByTeacherId")
    public PageInfo<Paper> listByTeacherId(Integer pageNo, Integer pageSize){
        return paperService.listByTeacherId(pageNo,pageSize);
    }

    @RequestMapping("/listUnchoosed")
    public  PageInfo<Paper> listUnchoosed(Integer pageNo, Integer pageSize){
        return paperService.listUnchoosed(pageNo, pageSize);
    }

    @RequestMapping("/detail")
    public PaperDetailVo detail(String id){
        return paperService.detail(id);
    }
}
