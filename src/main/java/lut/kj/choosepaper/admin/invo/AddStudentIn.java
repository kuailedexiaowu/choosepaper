package lut.kj.choosepaper.admin.invo;

import lombok.Data;

/**
 * Created by kj on 2017/3/20.
 */
@Data
public class AddStudentIn {
    private String id;
    private String name;
    private String password;
    private int gender;
    private String tel;
    private String major;
}
