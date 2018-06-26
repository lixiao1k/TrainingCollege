package com.xrom.ssh.repository;

import com.xrom.ssh.entity.*;

import java.util.List;

public interface OrderRepository extends DomainRepository<Order,Long> {
    List<Order> findAll(Long studentId);
    List<Order> findClassOrders(Long classId);
    Order getOrder(Long classId, Long studentId);
    void payOrBreakOrder(Order order, boolean pay);
    void payOrBreakOrder4Institution(Order order, boolean pay);
    List<SOrderYearA> getSOrderYearA(Long studentId);
    List<SOrderSeasonA> getSOrderSeasonA(Long studentId);
    List<SOrderMonthA> getSOrderMonthA(Long studentId);
    SOrderTypeA getSOrderTypeA(Long studentId);
    SOrderGradeA getSOrderGradeA(Long studentId);
    List<IOrderYearA> getIOrderYearA(String code);
    List<IOrderSeasonA> getIOrderSeasonA(String code);
    List<IOrderMonthA> getIOrderMonthA(String code);
    void payOrBreakOrder4Master(Order order, boolean pay);
}
