package lut.kj.paper.configuration;

import com.github.pagehelper.PageHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by kj on 2017/3/14.
 */
@Configuration
@ConditionalOnProperty(prefix = "mybatis.pagehelper", name = "enabled", matchIfMissing = true)
public class PageHelperConfig {
    @Bean
    public PageHelper mybatisPageHelper() throws Exception {
        PageHelper pageHelper = new PageHelper();
        Properties properties=new Properties();
        properties.setProperty("dialect","mysql");
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("pageSizeZero","true");
        properties.setProperty("reasonable","false");
        properties.setProperty("returnPageInfo","page");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
