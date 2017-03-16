package lut.kj.choosepaper.teacher.controller;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.teacher.domin.Teacher;
import lut.kj.choosepaper.teacher.invo.AddTeacherIn;
import lut.kj.choosepaper.teacher.invo.UpdateTeacherIn;
import lut.kj.choosepaper.teacher.service.TeacherService;
import lut.kj.choosepaper.utils.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kj on 2017/3/15.
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @RequestMapping("/add")
    public Message addTeacher(@RequestBody AddTeacherIn addTeacherIn){
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(addTeacherIn, teacher);
        return teacherService.addTeacher(teacher);
    }

    @RequestMapping("/update")
    public Message updateTeacher(@RequestBody UpdateTeacherIn updateTeacherIn){
        Teacher newTeacher = new Teacher();
        BeanUtils.copyProperties(updateTeacherIn, newTeacher);
        return teacherService.updateTeacher(newTeacher);
    }

    @RequestMapping("/delete")
    public Message deleteTeacher(String id){
        return teacherService.deleteTeacher(id);
    }

    @RequestMapping("/list")
    public PageInfo<Teacher> listAll(int pageSize, int pageNo){
        return teacherService.listAll(pageSize, pageNo);
    }
}
