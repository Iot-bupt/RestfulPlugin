package cn.bupt.restful.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckState {
    @Autowired
    RestfulService restfulService;

    @Pointcut("@annotation(cn.bupt.restful.service.Timer)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object before(ProceedingJoinPoint point) throws Throwable {

        if(restfulService.getState().equals("ACTIVE")){
            return point.proceed();
        }
        else{
            return new AsyncResult<String>("HTTP请求插件暂停中");
        }

    }
}
