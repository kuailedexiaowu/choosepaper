package lut.kj.choosepaper.student.controller;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.mapper.TeacherMapper;
import lut.kj.choosepaper.paper.invo.UpdatePaperIn;
import lut.kj.choosepaper.student.domin.Student;
import lut.kj.choosepaper.student.invo.AddStudentIn;
import lut.kj.choosepaper.student.revo.OverViewVo;
import lut.kj.choosepaper.student.service.StudentService;
import lut.kj.choosepaper.teacher.domin.Teacher;
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
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;


    @RequestMapping("/add")
    public Message addStudent(@RequestBody AddStudentIn addStudentIn){
        Student student=new Student();
        BeanUtils.copyProperties(addStudentIn,student);
        return studentService.addStudent(student);
    }

    @RequestMapping("/update")
    public Message updateStudent(@RequestBody UpdatePaperIn updatePaperIn){
        Student student=new Student();
        BeanUtils.copyProperties(updatePaperIn,student);
        return studentService.updateStudent(student);
    }

    @RequestMapping("/delete")
    public Message deleteStudent(String[] ids){
        return studentService.deleteStudent(ids);
    }

    @RequestMapping("/list")
    public PageInfo<Student> listAll(Integer pageNo, Integer pageSize){
        return studentService.listAll(pageNo, pageSize);
    }

    @RequestMapping("/findById")
    public Student findById(String id){
        return studentService.findById(id);
    }

    @RequestMapping("/overview")
    public OverViewVo overview(){
        OverViewVo overViewVo = new OverViewVo();
        overViewVo.setUnchoosed(studentService.queryPaperUnchoosedCount());
        overViewVo.setChoosed(studentService.queryPaperChoosedCount());
        overViewVo.setTotal(studentService.queryPaperTotalCount());
        return overViewVo;
    }
}
