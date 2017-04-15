package lut.kj.choosepaper.teacher.service;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.teacher.domin.Teacher;
import lut.kj.choosepaper.utils.PageInfo;

/**
 * Created by kj on 2017/3/15.
 */
public interface TeacherService {
    Message addTeacher(Teacher teacher);
    Message updateTeacher(Teacher teacher);
    Message deleteTeacher(String[] ids);
    Teacher findById(String id);
    PageInfo<Teacher> listAll(Integer pageNo, Integer pageSize);
    int queryPaperTotalCount(String id);
    int queryPaperChoosedCount(String id);
    int queryPaperUnchoosedCount(String id);
    int queryTeacherCount();
}
