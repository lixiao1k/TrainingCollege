package com.xrom.ssh.service;

import com.xrom.ssh.entity.Order;
import com.xrom.ssh.entity.vo.OrderVO;
import com.xrom.ssh.enums.OrderState;

import java.util.List;

public interface OrderService {
    List<OrderVO> findAllOfStudent(Long studentId);
    List<OrderVO> findAllOfClass(Long classId);
    Order get(Long studentId, Long classId);
    void cancel(Long studentId, Long classId);
    void pay(Long studentId, Long classId);
    void payOffline(Long studentId, Long classId);
    List<OrderVO> getAllOfStudentByState(Long studentId, OrderState state);
    List<OrderVO> getAllOfClassByState(Long classId, OrderState state);
    void flush();
    Long save(Order order);
}
