package lut.kj.choosepaper.paper.domin;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by kj on 2017/3/15.
 */
@Data
public class Paper {
    private String id;
    private String name;
    private String desc;
    private String teacherId;
    private String student_Id;
    private Timestamp createTime;
}
