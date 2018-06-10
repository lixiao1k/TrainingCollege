package com.xrom.ssh.repository;

import com.xrom.ssh.entity.Order;
import com.xrom.ssh.entity.SOrderSeasonA;
import com.xrom.ssh.entity.SOrderYearA;

import java.util.List;

public interface OrderRepository extends DomainRepository<Order,Long> {
    List<Order> findAll(Long studentId);
    List<Order> findClassOrders(Long classId);
    Order getOrder(Long classId, Long studentId);
    void payOrBreakOrder(Order order, boolean pay);
    List<SOrderYearA> getSOrderYearA(Long studentId);
    List<SOrderSeasonA> getSOrderSeasonA(Long studentId);
}
