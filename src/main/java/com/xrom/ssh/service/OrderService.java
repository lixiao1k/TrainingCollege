package com.xrom.ssh.service;

import com.xrom.ssh.entity.Order;
import com.xrom.ssh.entity.vo.OrderVO;
import com.xrom.ssh.entity.vo.SPayInfoVO;
import com.xrom.ssh.enums.OrderState;

import java.util.List;

public interface OrderService {
    List<OrderVO> findAllOfStudent(Long studentId);
    List<OrderVO> findAllOfClass(Long classId);
    Order get(Long studentId, Long classId);
    Order get(Long orderId);
    void cancel(Long studentId, Long classId);
    void cancel(Long orderId);
    void pay(Long studentId, Long classId);
    void pay(Long orderId, Long userId, int payment);
    void payOffline(Long studentId, Long classId);
    void dropClass(Long orderId);
    List<OrderVO> getAllOfStudentByState(Long studentId, OrderState state);
    List<OrderVO> getAllOfClassByState(Long classId, OrderState state);
    List<OrderVO> getAllOfInstituteByState(String institutionCode, OrderState state);
    void flush();
    Long save(Order order);
    SPayInfoVO getPayInfoVO(Long orderId, Long studentId);
}
