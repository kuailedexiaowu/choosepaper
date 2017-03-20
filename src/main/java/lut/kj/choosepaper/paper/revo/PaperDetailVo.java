package lut.kj.choosepaper.paper.revo;

import lombok.Data;
import lut.kj.choosepaper.student.domin.Student;
import lut.kj.choosepaper.teacher.domin.Teacher;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by kj on 2017/3/19.
 */
@Data
public class PaperDetailVo {
    private String id= UUID.randomUUID().toString();
    private String name;
    private String description;
    private String teacherId;
    private String studentId;
    private String demand;
    private Timestamp createTime;
    private Student student;
    private Teacher teacher;
}
