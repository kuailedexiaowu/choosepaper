package lut.kj.choosepaper.topic.service.impl;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.mapper.CommentMapper;
import lut.kj.choosepaper.topic.domin.Comment;
import lut.kj.choosepaper.topic.service.CommentService;
import lut.kj.choosepaper.topic.service.ReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by kj on 2017/4/11.
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ReplayService replayService;
    @Override
    public Message addCmment(Comment comment) {
        commentMapper.insert(comment);
        return new Message("success");
    }

    @Override
    public Message deleteComment(String[] topicIds) {
        for(String id : topicIds){
            Example example = new Example(Comment.class);
            example.createCriteria().andEqualTo("topicId",id);
            List<Comment> comments = commentMapper.selectByExample(example);
            if(0!=comments.size()){
                String[] commentIds = new String[comments.size()];
                for(int i = 0;i < commentIds.length; i++){
                    commentIds[i]=comments.get(i).getId();
                }
                replayService.deleteReplay(commentIds);
            }
        }
        return new Message("success");
    }
}
