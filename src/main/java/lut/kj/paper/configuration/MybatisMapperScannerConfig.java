package lut.kj.paper.configuration;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * Created by kj on 2017/3/14.
 */
@AutoConfigureAfter(MybatisConfiguration.class)
public class MybatisMapperScannerConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("lut.kj.mapper");
        Properties properties = new Properties();
        properties.setProperty("mappers", "lut.kj.paper.utils.TkMapper");
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        return mapperScannerConfigurer;
    }
}
