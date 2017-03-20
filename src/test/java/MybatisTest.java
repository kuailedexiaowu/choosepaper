import lut.kj.choosepaper.PaperApplication;
import lut.kj.choosepaper.mapper.StudentMapper;
import lut.kj.choosepaper.mapper.TeacherMapper;
import lut.kj.choosepaper.paper.domin.Paper;
import lut.kj.choosepaper.paper.service.PaperService;
import lut.kj.choosepaper.student.domin.Student;
import lut.kj.choosepaper.teacher.domin.Teacher;
import lut.kj.choosepaper.teacher.service.TeacherService;
import lut.kj.choosepaper.utils.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by kj on 2017/3/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(PaperApplication.class)
public class MybatisTest {
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    TeacherService teacherService;
    @Autowired
    PaperService paperService;
    @Autowired
    StudentMapper studentMapper;
    @Test
    public void insert(){
       SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        User user=new User();
        user.setId(3);
        user.setPassword("3");
        user.setUsername("3");
        user.setVisitor(3);
        userMapper.insert(user);
        sqlSession.commit();

     }
    @Test
    public void listTeacher(){
        PageInfo<Teacher> pageInfo=teacherService.listAll(1,10);
        System.out.println(pageInfo.toString()+pageInfo.getList().toString());
    }

    @Test
    public void listUnchoose(){
        PageInfo<Paper> paper =paperService.listUnchoosed(1,5);
        System.out.println(paper.toString()+paper.getList().toString());
    }
    @Test
    public void insertStudent(){
        Student student=new Student();
        student.setId("1");
        student.setName("1");
        student.setMajor("1");
        student.setGender(1);
        student.setTel("1");
        studentMapper.insert(student);
    }
    @Test
    public void findTeacherById(){
        Teacher teacher = new Teacher();
        teacher.setId("19201313");
        teacher=teacherMapper.selectByPrimaryKey("19201313");
        System.out.println(teacher.getName());
    }
    @Test
    public void findByStudentId(){
        studentMapper.selectByPrimaryKey("13240206");
    }
}
