package lut.kj.choosepaper.topic.revo;

import lombok.Data;
import lut.kj.choosepaper.topic.domin.Replay;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kj on 2017/4/11.
 */
@Data
public class CommentInfo {
    private String id;
    private String context;
    private String speakName;
    private String topicId;
    private Timestamp createTime;
    List<Replay> replays = new ArrayList<Replay>();
}
