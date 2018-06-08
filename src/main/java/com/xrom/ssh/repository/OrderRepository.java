package com.xrom.ssh.repository;

import com.xrom.ssh.entity.Order;

import java.util.List;

public interface OrderRepository extends DomainRepository<Order,Long> {
    List<Order> findAll(Long studentId);
    List<Order> findClassOrders(Long classId);
    Order getOrder(Long classId, Long studentId);
    void payOrder(Order order);
}
