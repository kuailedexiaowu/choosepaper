package lut.kj.choosepaper.topic.service.impl;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.mapper.ReplayMapper;
import lut.kj.choosepaper.topic.domin.Replay;
import lut.kj.choosepaper.topic.service.ReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * Created by kj on 2017/4/11.
 */
@Service
public class ReplayServiceImpl implements ReplayService {
    @Autowired
    ReplayMapper replayMapper;
    @Override
    public Message addReplay(Replay replay) {
        replayMapper.insert(replay);
        return new Message("success");
    }

    @Override
    public Message deleteReplay(String[] commentIds) {
        for(String id : commentIds){
            Example example = new Example(Replay.class);
            example.createCriteria().andEqualTo("commentId",id);
            replayMapper.deleteByExample(example);
        }
        return new Message("success");
    }
}
