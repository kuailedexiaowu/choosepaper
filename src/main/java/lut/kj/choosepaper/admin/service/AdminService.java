package lut.kj.choosepaper.admin.service;

import lut.kj.choosepaper.admin.revo.StudentUser;
import lut.kj.choosepaper.admin.revo.TeacherUser;
import lut.kj.choosepaper.utils.PageInfo;

/**
 * Created by kj on 2017/3/15.
 */
public interface AdminService {
    PageInfo<StudentUser> listStudent(Integer pageNo, Integer pageSize);
    PageInfo<TeacherUser> listTeacher(Integer pageNo, Integer pageSize);
}
