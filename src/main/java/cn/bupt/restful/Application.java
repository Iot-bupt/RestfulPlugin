package cn.bupt.restful;

import com.codahale.metrics.ConsoleReporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Created by tangjialiang on 2017/12/19.
 *
 */

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        ConsoleReporter reporter = ctx.getBean(ConsoleReporter.class);
        reporter.start(1, TimeUnit.MINUTES);
    }
}
