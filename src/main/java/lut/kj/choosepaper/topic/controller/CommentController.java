package lut.kj.choosepaper.topic.controller;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.topic.domin.Comment;
import lut.kj.choosepaper.topic.invo.CommentAddInvo;
import lut.kj.choosepaper.topic.service.CommentService;
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
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @RequestMapping("/add")
    Message addComment(@RequestBody CommentAddInvo commentAddInvo){
        Comment comment = new Comment();
        String userName = userService.getUserName(UserUtils.getUserId());
        comment.setSpeakName(userName);
        BeanUtils.copyProperties(commentAddInvo,comment);
        return commentService.addCmment(comment);
    }
}
