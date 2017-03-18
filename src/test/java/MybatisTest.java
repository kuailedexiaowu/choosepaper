import lut.kj.choosepaper.PaperApplication;
import lut.kj.choosepaper.mapper.TeacherMapper;
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

}
