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
    PageInfo<Topic> listTopicByUserId(int pageNo, int pageSize);
    PageInfo<Topic> listAllTopic(int pageNo, int pageSize);
    PageInfo<TopicInfo> listTopicInfoByUserId(int pageNo, int pageSize);
    PageInfo<TopicInfo> listTopicInfoAll(int pageNo, int pageSize);
}
