package lut.kj.paper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by kj on 2017/3/14.
 */
@SpringBootApplication
@ComponentScan(basePackages = "lut.kj.paper")
public class PaperApplication {
    public static void main(String[] args){
        SpringApplication.run(PaperApplication.class, args);
    }

}
