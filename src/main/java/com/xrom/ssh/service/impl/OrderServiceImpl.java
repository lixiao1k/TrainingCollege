package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.entity.vo.OrderVO;
import com.xrom.ssh.entity.vo.SPayInfoVO;
import com.xrom.ssh.enums.OrderState;
import com.xrom.ssh.exceptions.NoCardException;
import com.xrom.ssh.repository.OrderRepository;
import com.xrom.ssh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired(required = true)
    private OrderRepository repository;

    @Autowired(required = true)
    private CourseService courseService;

    @Autowired(required = true)
    private TeacherService teacherService;

    @Autowired(required = true)
    private ClassroomService classroomService;

    @Autowired(required = true)
    private InstitutionService institutionService;

    @Autowired(required = true)
    private CardService cardService;

    @Autowired(required = true)
    private AccountService accountService;

    @Override
    public List<OrderVO> findAllOfStudent(Long studentId) {
        return toOrderVO(repository.findAll(studentId));
    }

    @Override
    public List<OrderVO> findAllOfClass(Long classId) {
        return toOrderVO(repository.findClassOrders(classId));
    }

    @Override
    public Order get(Long studentId, Long classId) {
        return repository.getOrder(classId, studentId);
    }

    @Override
    public Order get(Long orderId) {
        return repository.get(orderId);
    }

    @Override
    public void cancel(Long studentId, Long classId) {
        Order order = get(studentId, classId);
        order.setIsCancelled(1);
        order.setIsPayed(0);
        order.setIsReserved(0);
        order.setIsPayedOffline(0);
        order.setIsUnSubscribed(0);
        repository.update(order);
        flush();
    }

    @Override
    public void cancel(Long orderId) {
        Order order = repository.get(orderId);
        order.setIsCancelled(1);
        order.setIsPayed(0);
        order.setIsReserved(0);
        order.setIsPayedOffline(0);
        order.setIsUnSubscribed(0);
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
        order.setIsUnSubscribed(0);
        repository.update(order);
        flush();
    }

    @Override
    public void pay(Long orderId, Long userId, int payment) {
        Order order = repository.get(orderId);
        order.setIsCancelled(0);
        order.setIsPayed(1);
        order.setIsReserved(0);
        order.setIsPayedOffline(0);
        order.setIsUnSubscribed(0);
        order.setPayment(payment);
        order.setPayedTime(new Date());
        repository.update(order);
        try {
            cardService.update(userId, -payment);
        } catch (NoCardException e) {
            e.printStackTrace();
        }
        flush();
    }

    @Override
    public void payOffline(Long studentId, Long classId) {
        Order order = get(studentId, classId);
        order.setIsCancelled(0);
        order.setIsPayed(1);
        order.setIsReserved(0);
        order.setIsPayedOffline(1);
        order.setIsUnSubscribed(0);
        repository.update(order);
        flush();
    }

    @Override
    public void dropClass(Long orderId) {
        Order order = repository.get(orderId);
        order.setIsCancelled(0);
        order.setIsPayed(0);
        order.setIsReserved(0);
        order.setIsPayedOffline(0);
        order.setIsUnSubscribed(1);
        repository.update(order);
        flush();
    }

    @Override
    public List<OrderVO> getAllOfStudentByState(Long studentId, OrderState state) {
        List<OrderVO> orders = findAllOfStudent(studentId);
        return getOrdersByState(orders, state);
    }

    @Override
    public List<OrderVO> getAllOfClassByState(Long classId, OrderState state) {
        List<OrderVO> orders = findAllOfClass(classId);
        return getOrdersByState(orders, state);
    }

    @Override
    public List<OrderVO> getAllOfInstituteByState(String institutionCode, OrderState state) {
        List<Classroom> classrooms = classroomService.findAll(institutionCode);
        List<OrderVO> orderVOSPayed = new ArrayList<>();
        List<OrderVO> orderVOSDroped = new ArrayList<>();
        for(Classroom classroom : classrooms){
            List<OrderVO> payed = getAllOfClassByState(classroom.getId(), OrderState.PAYED);
            if(payed == null || payed.size() == 0){
                continue;
            }
            orderVOSPayed.addAll(payed);
            List<OrderVO> dropped = getAllOfClassByState(classroom.getId(), OrderState.DROPPED);
            if(dropped == null || payed.size() == 0 ){
                continue;
            }
            orderVOSDroped.addAll(dropped);
        }
        if(state == OrderState.PAYED){
            return orderVOSPayed;
        }else if(state == OrderState.DROPPED){
            return orderVOSDroped;
        }else {
            return null;
        }
    }


    private List<OrderVO> getOrdersByState(List<OrderVO> orders, OrderState state){
        if(orders == null || orders.size() == 0){
            return null;
        }
        List<OrderVO> reservedOrders = new ArrayList<>();
        List<OrderVO> cancelledOrders = new ArrayList<>();
        List<OrderVO> payedOrders = new ArrayList<>();
        List<OrderVO> payedOfflineOrders = new ArrayList<>();
        List<OrderVO> dropedClassOrders = new ArrayList<>();
        for(OrderVO orderVO : orders){
            if(orderVO.getIsReserved() == 1){
                reservedOrders.add(orderVO);
            }else if(orderVO.getIsCancelled() == 1){
                cancelledOrders.add(orderVO);
            }else if(orderVO.getIsPayed() == 1){
                payedOrders.add(orderVO);
            }else if(orderVO.getIsPayedOffline() == 1){
                payedOfflineOrders.add(orderVO);
            }else if(orderVO.getIsUnSubscribed() == 1){
                dropedClassOrders.add(orderVO);
            }
        }

        if(state == OrderState.RESERVED){
            return reservedOrders;
        }else if(state == OrderState.CANCELLED){
            return cancelledOrders;
        }else if(state == OrderState.PAYED){
            return payedOrders;
        }else if(state == OrderState.OFFLINE){
            return payedOfflineOrders;
        }else {
            return dropedClassOrders;
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

    @Override
    public SPayInfoVO getPayInfoVO(Long orderId, Long studentId) {
        Order order = repository.get(orderId);
        SPayInfoVO sPayInfoVO = new SPayInfoVO();
        Card card = cardService.getCard(studentId);
        Account account = accountService.getAccount(studentId);
        sPayInfoVO.setOrderId(orderId);
        if(card != null){
            sPayInfoVO.setCardNumber(card.getCardNumber());
            sPayInfoVO.setCardBalance(card.getBalance());
        }
        if(account != null){
            sPayInfoVO.setBpBalance(account.getBpBalance());
        }
        int moneyFromBP = sPayInfoVO.getBpBalance()/10;
        if(moneyFromBP > order.getPrice()/4){
            moneyFromBP = order.getPrice()/4;
        }
        sPayInfoVO.setMoneyFromBP(moneyFromBP);
        sPayInfoVO.setMoneyNeedPay(order.getPrice() - moneyFromBP);
        sPayInfoVO.setOrderRawMoney(order.getPrice());
        return sPayInfoVO;
    }


    private List<OrderVO> toOrderVO(List<Order> orders){
        List<OrderVO> orderVOS = new ArrayList<>();
        if(orders == null || orders.size() == 0){
            return null;
        }
        Course course = null;
        Teacher teacher = null;
        Classroom classroom = null;
        Institution institution = null;
        for(Order order : orders){
            classroom = classroomService.getClass(order.getClassId());
            if(classroom == null)
                continue;
            course = courseService.getCourse(classroom.getCourseId());
            if(course == null)
                continue;
            teacher = teacherService.getTeacher(classroom.getTeacherId());
            institution = institutionService.getInstitution(course.getInstitutionCode());
            OrderVO orderVO = new OrderVO();
            orderVO.setClassId(classroom.getId());
            orderVO.setCourseId(course.getId());
            orderVO.setDescription(course.getDescription());
            orderVO.setCreateTime(order.getCreateTime());
            if(institution != null){
                orderVO.setInstitutionPhone(institution.getPhone());
            }
            orderVO.setIsCancelled(order.getIsCancelled());
            orderVO.setIsPayed(order.getIsPayed());
            orderVO.setIsPayedOffline(order.getIsPayedOffline());
            orderVO.setIsReserved(order.getIsReserved());
            orderVO.setIsUnSubscribed(order.getIsUnSubscribed());
            orderVO.setOrderId(order.getId());
            orderVO.setPayment(order.getPayment());
            orderVO.setAmountReturned(order.getAmountReturned());
            orderVO.setPrice(order.getPrice());
            orderVO.setStudentId(order.getStudentId());
            orderVO.setTeacherId(classroom.getTeacherId());
            orderVO.setCreateTime(order.getCreateTime());
            orderVO.setPayedTime(order.getPayedTime());
            orderVO.setDropTime(order.getDropTime());
            if(teacher != null){
                orderVO.setTeacherName(teacher.getName());
                orderVO.setTeacherPhone(teacher.getPhone());
            }
            orderVO.setType(course.getType());

            orderVOS.add(orderVO);
        }
        return orderVOS;
    }
}
