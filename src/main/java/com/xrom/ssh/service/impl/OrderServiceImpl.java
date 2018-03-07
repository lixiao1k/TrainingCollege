package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Order;
import com.xrom.ssh.enums.OrderState;
import com.xrom.ssh.repository.OrderRepository;
import com.xrom.ssh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired(required = true)
    OrderRepository repository;

    @Override
    public List<Order> findAllOfStudent(Long studentId) {
        return repository.findAll(studentId);
    }

    @Override
    public List<Order> findAllOfClass(Long classId) {
        return repository.findClassOrders(classId);
    }

    @Override
    public Order get(Long studentId, Long classId) {
        return repository.getOrder(classId, studentId);
    }

    @Override
    public void cancel(Long studentId, Long classId) {
        Order order = get(studentId, classId);
        order.setIsCancelled(1);
        order.setIsPayed(0);
        order.setIsReserved(0);
        order.setIsPayedOffline(0);
        repository.update(order);
        flush();
    }

    @Override
    public void pay(Long studentId, Long classId) {
        Order order = get(studentId, classId);
        order.setIsCancelled(0);
        order.setIsPayed(1);
        order.setIsReserved(0);
        order.setIsPayedOffline(0);
        repository.update(order);
        flush();
    }

    @Override
    public void payOffline(Long studentId, Long classId) {
        Order order = get(studentId, classId);
        order.setIsCancelled(0);
        order.setIsPayed(1);
        order.setIsReserved(0);
        order.setIsPayedOffline(1);
        repository.update(order);
        flush();
    }

    @Override
    public List<Order> getAllOfStudentByState(Long studentId, OrderState state) {
        List<Order> orders = findAllOfStudent(studentId);
        return getOrdersByState(orders, state);

    }

    @Override
    public List<Order> getAllOfClassByState(Long classId, OrderState state) {
        List<Order> orders = findAllOfClass(classId);
        return getOrdersByState(orders, state);
    }


    private List<Order> getOrdersByState(List<Order> orders, OrderState state){
        if(orders == null || orders.size() == 0){
            return null;
        }
        List<Order> reservedOrders = new ArrayList<>();
        List<Order> cancelledOrders = new ArrayList<>();
        List<Order> payedOrders = new ArrayList<>();
        List<Order> payedOfflineOrders = new ArrayList<>();
        for(Order order : orders){
            if(order.getIsReserved() == 1){
                reservedOrders.add(order);
            }else if(order.getIsCancelled() == 1){
                cancelledOrders.add(order);
            }else if(order.getIsPayed() == 1){
                payedOrders.add(order);
            }else if(order.getIsPayedOffline() == 1){
                payedOfflineOrders.add(order);
            }
        }

        if(state == OrderState.RESERVED){
            return reservedOrders;
        }else if(state == OrderState.CANCELLED){
            return cancelledOrders;
        }else if(state == OrderState.PAYED){
            return payedOrders;
        }else {
            return payedOfflineOrders;
        }
    }


    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public Long save(Order order) {
        return repository.save(order);
    }
}
