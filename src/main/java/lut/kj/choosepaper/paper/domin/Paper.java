package lut.kj.choosepaper.paper.domin;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by kj on 2017/3/15.
 */
@Data
@Table(name = "paper")
public class Paper {
    @Id
    private String id= UUID.randomUUID().toString();
    private String name;
    private String description;
    @Column(name = "teacher_id")
    private String teacherId;
    @Column(name = "student_id")
    private String studentId;
    private String demand;
    private Timestamp createTime=new Timestamp(System.currentTimeMillis());
}
