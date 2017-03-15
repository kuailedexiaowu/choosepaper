package lut.kj.paper.configuration;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

/**
 * Created by kj on 2017/3/14.
 */
@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
@MapperScan(basePackages={"lut.kj.paper.mapper"})
public class MybatisConfiguration {
    @Autowired
    private MybatisProperties mybatisProperties;

    @Autowired(required = false)
    private PageHelper pageHelper;

    @Autowired
    private ResourceLoader resourceLoader=new DefaultResourceLoader();

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        /*
        使用springboot自带的mybatis配置类，会读取yml文件以mybatis开头的属性
         */
        sqlSessionFactoryBean.setConfigLocation(this.resourceLoader.getResource(this.mybatisProperties.getConfig()));

        if (pageHelper != null) {//添加分页插件
            Interceptor[] interceptors = new Interceptor[]{pageHelper};
            sqlSessionFactoryBean.setPlugins(interceptors);
        }

        SqlSessionFactory sqlSessionFactory=null;
        try{
        sqlSessionFactory=sqlSessionFactoryBean.getObject();
        }catch(Exception e){
            e.printStackTrace();
        }
        return sqlSessionFactory;

    }

}
