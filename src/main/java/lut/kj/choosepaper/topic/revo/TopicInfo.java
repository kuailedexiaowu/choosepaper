package lut.kj.choosepaper.topic.revo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kj on 2017/4/11.
 */
@Data
public class TopicInfo {
    private String id;
    private String title;
    private String context;
    private String ownerId;
    private Timestamp createTime;
    List<CommentInfo> commentInfos = new ArrayList<CommentInfo>();
}
