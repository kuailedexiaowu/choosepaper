package lut.kj.choosepaper.topic.domin;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by kj on 2017/4/11.
 */
@Data
@Table(name = "topic")
public class Topic {
    @Id
    private String id = UUID.randomUUID().toString();
    private String title;
    private String context;
    private String ownerId;
    private Timestamp createTime=new Timestamp(System.currentTimeMillis());
}
