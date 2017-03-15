package lut.kj.choosepaper.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Created by kj on 2017/1/20.
 */
@ConfigurationProperties(prefix = DruidProperties.PREFIX)
@Data
public class DruidProperties {
    public static final String PREFIX = "druid.datasource";

    @NotNull
    private String url;
    @NotNull
    private String driverClassName = "com.mysql.jdbc.Driver";//驱动
    @NotNull
    private String username;//用户名
    @NotNull
    private String password;//密码

    private int maxActive = 20;//池内最大激活链接数

    private int minIdle = 1;//最少可用链接数

    private int maxWait = 5000;//获取链接最大等待时间

    public Properties toProperties() throws Exception{
        Class c=this.getClass();
        Field fields[]=c.getDeclaredFields();
        Properties properties=new Properties();
        for(Field field:fields)
            if ((field.getModifiers() & java.lang.reflect.Modifier.STATIC) != java.lang.reflect.Modifier.STATIC)
                properties.setProperty(field.getName(), field.get(this).toString());
        return properties;

    }

}
