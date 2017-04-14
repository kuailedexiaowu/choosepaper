package lut.kj.choosepaper.topic.controller;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.topic.domin.Replay;
import lut.kj.choosepaper.topic.invo.ReplayAddInvo;
import lut.kj.choosepaper.topic.service.ReplayService;
import lut.kj.choosepaper.user.domin.User;
import lut.kj.choosepaper.user.service.UserService;
import lut.kj.choosepaper.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kj on 2017/4/11.
 */
@RestController
@RequestMapping("/replay")
public class ReplayController {
    @Autowired
    ReplayService replayService;

    @Autowired
    UserService userService;
    @RequestMapping("/add")
    Message addReplay(@RequestBody ReplayAddInvo replayAddInvo){
        String userName = userService.getUserName(UserUtils.getUserId());
        Replay replay = new Replay();
        replay.setSpeakName(userName);
        BeanUtils.copyProperties(replayAddInvo,replay);
        return replayService.addReplay(replay);
    }
}
