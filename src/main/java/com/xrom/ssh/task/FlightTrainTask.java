package com.xrom.ssh.task;

import com.xrom.ssh.service.CourseService;
import com.xrom.ssh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FlightTrainTask {
    @Autowired(required = true)
    OrderService orderService;

    @Autowired(required = true)
    CourseService courseService;

    @Scheduled(cron = "0/5 * * * * ? ")
    public void checkOrder(){
//        System.out.println("Task");
//        System.out.println(courseService.getCourse(2L));
    }
}
