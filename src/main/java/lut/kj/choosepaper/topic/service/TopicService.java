package lut.kj.choosepaper.topic.service;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.topic.domin.Topic;
import lut.kj.choosepaper.topic.revo.TopicInfo;
import lut.kj.choosepaper.utils.PageInfo;

/**
 * Created by kj on 2017/4/11.
 */
public interface TopicService {
    Message addTopic(Topic topic);
    Message updateTopic(Topic topic);
    Message deleteTopic(String[] ids);
    Topic getTopicById(String id);
    TopicInfo selectById(String id);
    PageInfo<Topic> listTopicByUserId(Integer pageNo, Integer pageSize);
    PageInfo<Topic> listAllTopic(Integer pageNo, Integer pageSize);
    PageInfo<TopicInfo> listTopicInfoByUserId(Integer pageNo, Integer pageSize);
    PageInfo<TopicInfo> listTopicInfoAll(Integer pageNo, Integer pageSize);
}
