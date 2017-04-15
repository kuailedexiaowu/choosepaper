package lut.kj.choosepaper.student.service;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.student.domin.Student;
import lut.kj.choosepaper.utils.PageInfo;

/**
 * Created by kj on 2017/3/15.
 */
public interface StudentService {
    Message addStudent(Student student);
    Message updateStudent(Student student);
    Message deleteStudent(String[] ids);
    Student findById(String id);
    PageInfo<Student> listAll(Integer pageNo, Integer pageSize);
    int queryPaperTotalCount();
    int queryPaperUnchoosedCount();
    int queryPaperChoosedCount();
    int queryStudentCount();
}
