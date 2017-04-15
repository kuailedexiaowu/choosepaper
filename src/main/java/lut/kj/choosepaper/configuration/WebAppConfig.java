package lut.kj.choosepaper.configuration;

import lut.kj.choosepaper.core.AdminInterceptor;
import lut.kj.choosepaper.core.StudentInterceptor;
import lut.kj.choosepaper.core.TeacherInterceptor;
import lut.kj.choosepaper.core.TopicAndPaperInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by kj on 2017/2/13.
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Bean
    public AdminInterceptor adminInterceptor(){return new AdminInterceptor();}

    @Bean
    public StudentInterceptor studentInterceptor(){return new StudentInterceptor();}

    @Bean
    public TeacherInterceptor teacherInterceptor(){return new TeacherInterceptor();}

    @Bean
    public TopicAndPaperInterceptor topicAndPaperInterceptor(){return new TopicAndPaperInterceptor();}

    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(adminInterceptor())
                    .addPathPatterns("/admin/**");
        registry.addInterceptor(studentInterceptor())
                    .addPathPatterns("/student/**");
        registry.addInterceptor(teacherInterceptor())
                    .addPathPatterns("/teacher/**");
        registry.addInterceptor(topicAndPaperInterceptor())
                    .addPathPatterns("/topic/**")
                    .addPathPatterns("/paper/**");
    }
}
