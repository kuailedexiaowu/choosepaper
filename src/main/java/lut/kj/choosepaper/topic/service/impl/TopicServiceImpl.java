package lut.kj.choosepaper.topic.service.impl;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.mapper.CommentMapper;
import lut.kj.choosepaper.mapper.ReplayMapper;
import lut.kj.choosepaper.mapper.TopicMapper;
import lut.kj.choosepaper.topic.domin.Comment;
import lut.kj.choosepaper.topic.domin.Replay;
import lut.kj.choosepaper.topic.domin.Topic;
import lut.kj.choosepaper.topic.revo.CommentInfo;
import lut.kj.choosepaper.topic.revo.TopicInfo;
import lut.kj.choosepaper.topic.service.CommentService;
import lut.kj.choosepaper.topic.service.TopicService;
import lut.kj.choosepaper.utils.PageInfo;
import lut.kj.choosepaper.utils.UserUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kj on 2017/4/11.
 */
@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ReplayMapper replayMapper;
    @Autowired
    CommentService commentService;
    @Override
    public Message addTopic(Topic topic) {
        topicMapper.insert(topic);
        return new Message("创建成功");
    }

    @Override
    public Message updateTopic(Topic topic) {
        topicMapper.updateByPrimaryKey(topic);
        return new Message("更新成功");
    }

    @Override
    public Message deleteTopic(String[] ids) {
        commentService.deleteComment(ids);
        for(String id : ids){
            topicMapper.deleteByPrimaryKey(id);
        }
        return new Message("删除成功");
    }

    @Override
    public Topic getTopicById(String id) {
        return topicMapper.selectByPrimaryKey(id);
    }

    @Override
    public TopicInfo selectById(String id) {
        Topic topic = topicMapper.selectByPrimaryKey(id);
        Example example = new Example(Comment.class);
        example.createCriteria().andEqualTo("topicId",topic.getId());
        List<Comment> comments = commentMapper.selectByExample(example);
        List<CommentInfo> commentInfos = new ArrayList<CommentInfo>();
        if(0!=comments.size()){
            for(Comment comment :comments){
                Example example1 = new Example(Replay.class);
                example1.createCriteria().andEqualTo("commentId",comment.getId());
                List<Replay> replays = replayMapper.selectByExample(example1);
                CommentInfo commentInfo = new CommentInfo();
                BeanUtils.copyProperties(comment,commentInfo);
                commentInfo.setReplays(replays);
                commentInfos.add(commentInfo);
            }
        }
        TopicInfo topicInfo = new TopicInfo();
        BeanUtils.copyProperties(topic,topicInfo);
        topicInfo.setCommentInfos(commentInfos);
        return topicInfo;
    }

    @Override
    public PageInfo<Topic> listTopicByUserId(int pageNo, int pageSize) {
        String userId = UserUtils.getUserId();
        Example example = new Example(Topic.class);
        example.createCriteria().andEqualTo("ownerId",userId);
        int total = topicMapper.selectCountByExample(example);
        RowBounds rowBounds = new RowBounds((pageNo-1)*pageSize, pageSize);
        List<Topic> topics = topicMapper.selectByExampleAndRowBounds(example,rowBounds);
        PageInfo<Topic> pageInfo = new PageInfo<Topic>(topics);
        pageInfo.setTotal(total);
        pageInfo.setCurrentPage(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(topics.size());
        if(total % pageSize != 0){
            pageInfo.setPageCount((total / pageSize) + 1);
        }
        else{
            pageInfo.setPageCount(total / pageSize);
        }
        return pageInfo;
    }

    @Override
    public PageInfo<Topic> listAllTopic(int pageNo, int pageSize) {
        int total = topicMapper.selectCount(null);
        RowBounds rowBounds = new RowBounds((pageNo-1)*pageSize, pageSize);
        List<Topic> topics = topicMapper.selectAll();
        PageInfo<Topic> pageInfo = new PageInfo<Topic>(topics);
        pageInfo.setTotal(total);
        pageInfo.setCurrentPage(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(topics.size());
        if(total % pageSize != 0){
            pageInfo.setPageCount((total / pageSize) + 1);
        }
        else{
            pageInfo.setPageCount(total / pageSize);
        }
        return pageInfo;

    }

    @Override
    public PageInfo<TopicInfo> listTopicInfoByUserId(int pageNo, int pageSize) {
        String userId = UserUtils.getUserId();
        Example example = new Example(Topic.class);
        example.createCriteria().andEqualTo("ownerId",userId);
        int total = topicMapper.selectCountByExample(example);
        RowBounds rowBounds = new RowBounds((pageNo-1)*pageSize, pageSize);
        List<Topic> topics = topicMapper.selectByExampleAndRowBounds(example,rowBounds);
        List<TopicInfo> topicInfos = new ArrayList<TopicInfo>();
        for(Topic topic: topics){
            TopicInfo topicInfo = new TopicInfo();
            topicInfo=selectById(topic.getId());
            topicInfos.add(topicInfo);
        }
        PageInfo<TopicInfo> pageInfo = new PageInfo<TopicInfo>(topicInfos);
        pageInfo.setTotal(total);
        pageInfo.setCurrentPage(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(topicInfos.size());
        if(total % pageSize != 0){
            pageInfo.setPageCount((total / pageSize) + 1);
        }
        else{
            pageInfo.setPageCount(total / pageSize);
        }
        return pageInfo;

    }

    @Override
    public PageInfo<TopicInfo> listTopicInfoAll(int pageNo, int pageSize) {
        int total = topicMapper.selectCount(null);
        RowBounds rowBounds = new RowBounds((pageNo-1)*pageSize, pageSize);
        List<Topic> topics = topicMapper.selectAll();
        List<TopicInfo> topicInfos = new ArrayList<TopicInfo>();
        for(Topic topic: topics){
            TopicInfo topicInfo = new TopicInfo();
            topicInfo=selectById(topic.getId());
            topicInfos.add(topicInfo);
        }
        PageInfo<TopicInfo> pageInfo = new PageInfo<TopicInfo>(topicInfos);
        pageInfo.setTotal(total);
        pageInfo.setCurrentPage(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(topicInfos.size());
        if(total % pageSize != 0){
            pageInfo.setPageCount((total / pageSize) + 1);
        }
        else{
            pageInfo.setPageCount(total / pageSize);
        }
        return pageInfo;
    }
}
