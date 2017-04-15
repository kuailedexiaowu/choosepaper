package lut.kj.choosepaper.topic.controller;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.topic.domin.Topic;
import lut.kj.choosepaper.topic.invo.TopicAddInvo;
import lut.kj.choosepaper.topic.invo.TopicUpdateInvo;
import lut.kj.choosepaper.topic.revo.TopicInfo;
import lut.kj.choosepaper.topic.service.TopicService;
import lut.kj.choosepaper.utils.PageInfo;
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
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    TopicService topicService;
    @RequestMapping("/add")
    Message addTopic(@RequestBody TopicAddInvo topicAddInvo){
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicAddInvo,topic);
        topic.setOwnerId(UserUtils.getUserId());
        return topicService.addTopic(topic);
    }

    @RequestMapping("/update")
    Message updateTopic(@RequestBody TopicUpdateInvo topicUpdateInvo){
        Topic topic = topicService.getTopicById(topicUpdateInvo.getId());
        BeanUtils.copyProperties(topicUpdateInvo,topic);
        return topicService.updateTopic(topic);
    }

    @RequestMapping("/delete")
    Message deleteTopic(@RequestBody String[] ids){
        return topicService.deleteTopic(ids);
    }

    @RequestMapping("/detail")
    TopicInfo detail(String id){
        return topicService.selectById(id);
    }

    @RequestMapping("/listTopicByUserId")
    PageInfo<Topic> listTopicByUserId(Integer pageNo, Integer pageSize){
        return topicService.listTopicByUserId(pageNo,pageSize);
    }
    @RequestMapping("/listTopicAll")
    PageInfo<Topic> listAllTopic(Integer pageNo, Integer pageSize){
        return topicService.listAllTopic(pageNo,pageSize);
    }
    @RequestMapping("/listTopicInfoByUserId")
    PageInfo<TopicInfo> listTopicInfoByUserId(Integer pageNo, Integer pageSize){
        return topicService.listTopicInfoByUserId(pageNo,pageSize);
    }
    @RequestMapping("/listTopicInfoAll")
    PageInfo<TopicInfo> listTopicInfoAll(Integer pageNo, Integer pageSize){
        return topicService.listTopicInfoAll(pageNo,pageSize);
    }
}
