package lut.kj.choosepaper.paper.domin;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by kj on 2017/3/15.
 */
@Data
public class Paper {
    @Id
    @GeneratedValue
    private String id;
    private String name;
    private String desc;
    private String teacherId;
    private String student_Id;
    private String require;
    private Timestamp createTime=new Timestamp(System.currentTimeMillis());
}
