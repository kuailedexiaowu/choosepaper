package lut.kj.choosepaper.admin.controller;


import lut.kj.choosepaper.admin.invo.*;
import lut.kj.choosepaper.admin.revo.AdminOverviewOutVo;
import lut.kj.choosepaper.admin.revo.StudentUser;
import lut.kj.choosepaper.admin.revo.TeacherUser;
import lut.kj.choosepaper.admin.service.AdminService;
import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.student.domin.Student;
import lut.kj.choosepaper.student.service.StudentService;
import lut.kj.choosepaper.teacher.domin.Teacher;
import lut.kj.choosepaper.teacher.service.TeacherService;
import lut.kj.choosepaper.user.domin.User;
import lut.kj.choosepaper.user.service.UserService;
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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @RequestMapping("/addStudent")
    public Message addStudent(@RequestBody  AddStudentIn addStudentIn){
        User user = new User();
        user.setPassword(addStudentIn.getPassword());
        user.setUserName(addStudentIn.getId());
        Student student = new Student();
        BeanUtils.copyProperties(addStudentIn,student);
        userService.addUser(user);
        studentService.addStudent(student);
        return new Message("添加成功");
    }

    @RequestMapping("/updateStudent")
    public Message updateStudent(@RequestBody UpdateStudentIn updateStudentIn){
        Student student = new Student();
        BeanUtils.copyProperties(updateStudentIn, student);
        studentService.updateStudent(student);
        return new Message("更新成功");
    }

    @RequestMapping("resetStudent")
    public Message resetStudent(@RequestBody ResetStudentIn resetStudentIn){
        User user=userService.findById(resetStudentIn.getId());
        user.setUserName(resetStudentIn.getId());
        user.setPassword(resetStudentIn.getId());
        userService.updateUser(user);
        return new Message("重置密码成功");
    }

    @RequestMapping("/deleteStudent")
    public Message deleteStudent(@RequestBody String[] ids){
        userService.deleteUser(ids);
        studentService.deleteStudent(ids);
        return new Message("删除成功");
    }

    @RequestMapping("/listStudent")
    public PageInfo<StudentUser> listStudent(int pageNo, int pageSize){
        return adminService.listStudent(pageNo, pageSize);
    }

    @RequestMapping("/addTeacher")
    public Message addTeacher(@RequestBody AddTeacherIn addTeacherIn){
        User user = new User();
        user.setPassword(addTeacherIn.getPassword());
        user.setUserName(addTeacherIn.getId());
        userService.addUser(user);
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(addTeacherIn, teacher);
        teacherService.addTeacher(teacher);
        return new Message("添加成功");
    }

    @RequestMapping("/updateTeacher")
    public Message updateTeacher(@RequestBody UpdateTeacherIn updateTeacherIn){
        Teacher teacher = teacherService.findById(updateTeacherIn.getId());
        BeanUtils.copyProperties(updateTeacherIn, teacher);
        teacherService.updateTeacher(teacher);
        return new Message("更新成功");
    }

    @RequestMapping("/resetTeacher")
    public Message resetTeacher(@RequestBody ResetTeacherIn resetTeacherIn){
        User user = userService.findById(resetTeacherIn.getId());
        user.setPassword(resetTeacherIn.getId());
        userService.updateUser(user);
        return new Message("重置密码成功");
    }

    @RequestMapping("/deleteTeacher")
    public Message deleteTeacher(@RequestBody String[] ids){
        userService.deleteUser(ids);
        teacherService.deleteTeacher(ids);
        return new Message("删除成功");
    }

    @RequestMapping("/listTeacher")
    public PageInfo<TeacherUser> listTeacher(int pageNo, int pageSize){
        return adminService.listTeacher(pageNo, pageSize);
    }

    @RequestMapping("/overview")
    public AdminOverviewOutVo overview(){
        AdminOverviewOutVo adminOverviewOutVo = new AdminOverviewOutVo();
        adminOverviewOutVo.setTeacherCount(teacherService.queryTeacherCount());
        adminOverviewOutVo.setPaperCount(studentService.queryPaperTotalCount());
        adminOverviewOutVo.setStudentCount(studentService.queryStudentCount());
        return adminOverviewOutVo;
    }

}
