package lut.kj.choosepaper.configuration;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by kj on 2017/1/19.
 */
@Configuration
@EnableConfigurationProperties({DruidProperties.class})
public class DataSourceConfiguration {
    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
    @Autowired
    public DataSource dataSource(DruidProperties druidProperties)throws Exception{
        return  DruidDataSourceFactory.createDataSource(druidProperties.toProperties());
    }

}
