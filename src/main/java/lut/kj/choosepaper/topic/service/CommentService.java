package lut.kj.choosepaper.topic.service;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.topic.domin.Comment;

/**
 * Created by kj on 2017/4/11.
 */
public interface CommentService {
    Message addCmment(Comment comment);
    Message deleteComment(String[] topicIds);
}
