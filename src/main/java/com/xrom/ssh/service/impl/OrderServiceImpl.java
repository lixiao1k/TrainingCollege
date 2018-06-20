package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.entity.vo.BillsVO;
import com.xrom.ssh.entity.vo.OrderStatistics;
import com.xrom.ssh.entity.vo.OrderVO;
import com.xrom.ssh.entity.vo.SPayInfoVO;
import com.xrom.ssh.enums.OrderState;
import com.xrom.ssh.exceptions.NoCardException;
import com.xrom.ssh.repository.OrderRepository;
import com.xrom.ssh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        classroomService.updateNumNow(order.getClassId(), 1);
        flush();
        //@管理信息系统
        repository.payOrBreakOrder(order, true);
        repository.payOrBreakOrder4Institution(order, true);
    }

    @Override
    public void payOffline(Long orderId, int payment) {
        Order order = get(orderId);
        order.setIsCancelled(0);
        order.setIsPayed(1);
        order.setIsReserved(0);
        order.setIsPayedOffline(1);
        order.setIsUnSubscribed(0);
        order.setPayment(payment);
        order.setPayedTime(new Date());
        repository.update(order);
        classroomService.updateNumNow(order.getClassId(), 1);
        flush();

        //@管理信息系统
        repository.payOrBreakOrder(order, true);
        repository.payOrBreakOrder4Institution(order, true);
    }

    @Override
    public void dropClass(Long orderId, int amountReturn) {
        Order order = repository.get(orderId);
        order.setIsCancelled(0);
        order.setIsPayed(0);
        order.setIsReserved(0);
        order.setIsPayedOffline(0);
        order.setIsUnSubscribed(1);
        order.setDropTime(new Date());
        order.setAmountReturned(amountReturn);
        repository.update(order);
        classroomService.updateNumNow(order.getClassId(), -1);
        flush();

        //@管理信息系统
        repository.payOrBreakOrder(order, false);
        repository.payOrBreakOrder4Institution(order, false);
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
        List<OrderVO> orderVOSOffline = new ArrayList<>();
        for(Classroom classroom : classrooms){
            List<OrderVO> payed = getAllOfClassByState(classroom.getId(), OrderState.PAYED);
            if(payed != null && payed.size() != 0){
                orderVOSPayed.addAll(payed);
            }
            List<OrderVO> dropped = getAllOfClassByState(classroom.getId(), OrderState.DROPPED);
            if(dropped != null && dropped.size() != 0 ){
                orderVOSDroped.addAll(dropped);
            }
            List<OrderVO> payedOffline = getAllOfClassByState(classroom.getId(), OrderState.OFFLINE);
            if(payedOffline != null && payedOffline.size() != 0){
                orderVOSOffline.addAll(payedOffline);
            }
        }
        if(state == OrderState.PAYED){
            return orderVOSPayed;
        }else if(state == OrderState.DROPPED){
            return orderVOSDroped;
        }else if(state == OrderState.OFFLINE){
            return orderVOSOffline;
        }
        else {
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
                if(orderVO.getIsPayedOffline() == 1){
                    payedOfflineOrders.add(orderVO);
                }
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

    @Override
    public OrderVO getUnsubscribeInfo(Long orderId) {
        Order order = get(orderId);
        Classroom classroom = classroomService.getClass(order.getClassId());
        Course course = courseService.getCourse(classroom.getCourseId());
        OrderVO orderVO = new OrderVO();
        orderVO.setClassId(order.getClassId());
        orderVO.setCreateTime(order.getCreateTime());
        orderVO.setPayedTime(order.getPayedTime());
        orderVO.setPayment(order.getPayment());
        orderVO.setOrderId(orderId);
        int amountReturn = 0;
        Date date = new Date();
        long days = (course.getBeginDate().getTime()-date.getTime())/(1000l*60l*60l*24l);
        if(days > 7){
            amountReturn = order.getPayment();
        }else if(days<=7 && days>=0){
            amountReturn = order.getPayment()/2;
        }else {
            amountReturn = 0;
        }
        orderVO.setAmountReturned(amountReturn);
        orderVO.setDropTime(date);
        orderVO.setCourseBeginTime(course.getBeginDate());
        return orderVO;
    }

    @Override
    public List<BillsVO> getAllOfflineBillsOfInstitute(String institutionCode) {
        List<BillsVO> billsVOS = new ArrayList<>();
        List<OrderVO> offlineOrders = getAllOfInstituteByState(institutionCode, OrderState.OFFLINE);
        if(offlineOrders != null){
            for(OrderVO orderVO : offlineOrders){
                BillsVO billsVO = new BillsVO();
                billsVO.setOrderId(orderVO.getOrderId());
                billsVO.setTime(orderVO.getPayedTime());
                billsVO.setAction("线下支付");
                billsVO.setMoneyChange(orderVO.getPayment());
                billsVOS.add(billsVO);
            }
        }
        return billsVOS;
    }

    @Override
    public List<BillsVO> getAllPayedBillsOfInstitute(String institutionCode) {
        List<BillsVO> billsVOS = new ArrayList<>();
        List<OrderVO> payedOrders = getAllOfInstituteByState(institutionCode, OrderState.PAYED);
        if(payedOrders != null){
            for(OrderVO orderVO : payedOrders){
                BillsVO billsVO = new BillsVO();
                billsVO.setOrderId(orderVO.getOrderId());
                billsVO.setTime(orderVO.getPayedTime());
                billsVO.setAction("进账");
                billsVO.setMoneyChange(orderVO.getPayment());
                billsVOS.add(billsVO);
            }
        }
        return billsVOS;
    }

    @Override
    public List<BillsVO> getAllDropedBillsOfInstitute(String institutionCode) {
        List<BillsVO> billsVOS = new ArrayList<>();
        List<OrderVO> dropedOrders = getAllOfInstituteByState(institutionCode, OrderState.DROPPED);

        if(dropedOrders != null){
            for(OrderVO orderVO : dropedOrders){
                BillsVO billsVO = new BillsVO();
                billsVO.setOrderId(orderVO.getOrderId());
                billsVO.setTime(orderVO.getDropTime());
                billsVO.setAction("出账");
                billsVO.setMoneyChange(-orderVO.getAmountReturned());
                billsVOS.add(billsVO);
            }
        }
        return billsVOS;
    }

    @Override
    public List<BillsVO> getAllOfflineBills() {
        List<Institution> institutions = institutionService.getAllInstitutions();
        List<BillsVO> offlineBills = new ArrayList<>();
        for(Institution institution : institutions){
            List<BillsVO> billsOfInstitute = getAllOfflineBillsOfInstitute(institution.getCode());
            if(billsOfInstitute != null && billsOfInstitute.size() != 0){
                offlineBills.addAll(billsOfInstitute);
            }
        }
        return offlineBills;
    }

    @Override
    public List<BillsVO> getAllPayedBills() {
        List<Institution> institutions = institutionService.getAllInstitutions();
        List<BillsVO> payedBills = new ArrayList<>();
        for(Institution institution : institutions){
            List<BillsVO> billsOfInstitute = getAllPayedBillsOfInstitute(institution.getCode());
            if(billsOfInstitute != null && billsOfInstitute.size() != 0){
                payedBills.addAll(billsOfInstitute);
            }
        }
        return payedBills;
    }

    @Override
    public List<BillsVO> getAllDropedBills() {
        List<Institution> institutions = institutionService.getAllInstitutions();
        List<BillsVO> droppedBills = new ArrayList<>();
        for(Institution institution : institutions){
            List<BillsVO> billsOfInstitute = getAllDropedBillsOfInstitute(institution.getCode());
            if(billsOfInstitute != null && billsOfInstitute.size() != 0){
                droppedBills.addAll(billsOfInstitute);
            }
        }
        return droppedBills;
    }

    @Override
    public int getBillsSum(List<BillsVO> list) {
        int sum = 0;
        if(list != null){
            for(BillsVO billsVO : list){
                sum = sum + billsVO.getMoneyChange();
            }
        }
        return sum;
    }

    @Override
    public void checkOrder() {
        List<Order> orders = repository.findAll();
        System.out.println(orders);
        for(Order order : orders){
            if(order.getIsReserved() == 1){
                Date createTime = order.getCreateTime();
                System.out.println("----------------------------");
                if((new Date().getTime() - createTime.getTime()) > 1000*60*15){
                    cancel(order.getId());
                }
            }
        }
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


//    //信息系统内容，获取学员的订单总数、总额、平均订单单价（年、季度、月）;
//    public OrderStatistics getOrderStatistics(Long studentID) {
//        List<OrderVO> list = getAllOfStudentByState(studentID, OrderState.PAYED);
//        OrderStatistics orderStatistics = new OrderStatistics();
//        for (OrderVO orderVO : list) {
//            orderStatistics.addOrder(orderVO);
//        }
//        return orderStatistics;
//    }

    @Override
    public List<SOrderYearA> getSOrderYearA(Long studentId){
        List<SOrderYearA> list = repository.getSOrderYearA(studentId);
        if(list == null){
            return null;
        }else {
            List<SOrderYearA> list1 = new ArrayList<>();
            Collections.sort(list, new Comparator<SOrderYearA>() {
                @Override
                public int compare(SOrderYearA o1, SOrderYearA o2) {
                    return o1.getYear() - o2.getYear();//升序
                }
            });
            SOrderYearA oldest = list.get(0);
            SOrderYearA newest = list.get(list.size()-1);

            //将记录按年时间顺序排列，空缺补全
            int index = 0;
            for(int i = oldest.getYear(); i <= newest.getYear(); i++){
                if(index == list.size()){
                    break;
                }
                if(list.get(index).getYear() == i){
                    list1.add(list.get(index));
                    index++;
                }else {
                    SOrderYearA yearA = new SOrderYearA();
                    yearA.setSid(studentId);
                    yearA.setYear(i);
                    list1.add(yearA);
                }
            }
            return list1;
        }
    }

    @Override
    public List<IOrderYearA> getIOrderYearA(String code){
        List<IOrderYearA> list = repository.getIOrderYearA(code);
        if(list == null){
            return null;
        }else {
            List<IOrderYearA> list1 = new ArrayList<>();
            Collections.sort(list, new Comparator<IOrderYearA>() {
                @Override
                public int compare(IOrderYearA o1, IOrderYearA o2) {
                    return o1.getYear() - o2.getYear();
                }
            });
            IOrderYearA oldest = list.get(0);
            IOrderYearA newest = list.get(list.size() - 1);

            int index = 0;
            for(int i = oldest.getYear(); i <= newest.getYear(); i++){
                if(index == list.size()){
                    break;
                }
                if(list.get(index).getYear() == i){
                    list1.add(list.get(index));
                    index++;
                }else {
                    IOrderYearA yearA = new IOrderYearA();
                    yearA.setCode(code);
                    yearA.setYear(i);
                    list1.add(yearA);
                }
            }
            return list1;
        }
    }



    @Override
    public List<SOrderSeasonA> getSOrderSeasonA(Long studentId){
        List<SOrderSeasonA> list = repository.getSOrderSeasonA(studentId);
        if(list == null){
            return null;
        }else {
            List<SOrderSeasonA> list1 = new ArrayList<>();
            Collections.sort(list, new Comparator<SOrderSeasonA>() {
                @Override
                public int compare(SOrderSeasonA o1, SOrderSeasonA o2) {
                    return o1.getSeason() - o2.getSeason();
                }
            });
            SOrderSeasonA oldest = list.get(0);
            SOrderSeasonA newest = list.get(list.size()-1);

            int index = 0;
            for(int i = oldest.getSeason(); i <= newest.getSeason(); i++){
                if(index == list.size()){
                    break;
                }
                if(list.get(index).getSeason() == i){
                    list1.add(list.get(index));
                    index++;
                }else {
                    SOrderSeasonA sOrderSeasonA = new SOrderSeasonA();
                    sOrderSeasonA.setSid(studentId);
                    sOrderSeasonA.setSeason(i);
                    list1.add(sOrderSeasonA);
                }
            }
            return list1;
        }
    }

    @Override
    public List<IOrderSeasonA> getIOrderSeasonA(String code){
        List<IOrderSeasonA> list = repository.getIOrderSeasonA(code);
        if(list == null) {
            return null;
        }else {
            List<IOrderSeasonA> list1 = new ArrayList<>();
            Collections.sort(list, new Comparator<IOrderSeasonA>() {
                @Override
                public int compare(IOrderSeasonA o1, IOrderSeasonA o2) {
                    return o1.getSeason() - o2.getSeason();
                }
            });
            IOrderSeasonA oldest = list.get(0);
            IOrderSeasonA newest = list.get(list.size() - 1);

            int index = 0;
            for(int i = oldest.getSeason(); i <= newest.getSeason(); i++){
                if(index == list.size()){
                    break;
                }
                if(list.get(index).getSeason() == i){
                    list1.add(list.get(index));
                    index++;
                }else {
                    IOrderSeasonA iOrderSeasonA = new IOrderSeasonA();
                    iOrderSeasonA.setCode(code);
                    iOrderSeasonA.setSeason(i);
                    list1.add(iOrderSeasonA);
                }
            }
            return list1;
        }
    }

    @Override
    public List<SOrderMonthA> getSOrderMonthA(Long studentId){
        List<SOrderMonthA> list = repository.getSOrderMonthA(studentId);
        if(list == null){
            return null;
        }else {
            List<SOrderMonthA> list1 = new ArrayList<>();
            Collections.sort(list, new Comparator<SOrderMonthA>() {
                @Override
                public int compare(SOrderMonthA o1, SOrderMonthA o2) {
                    return o1.getMonth() - o2.getMonth();
                }
            });
            SOrderMonthA oldest = list.get(0);
            SOrderMonthA newest = list.get(list.size() - 1);

            int index = 0;
            for(int i = oldest.getMonth(); i <= newest.getMonth(); i++){
                if(index == list.size()){
                    break;

                }
                if(list.get(index).getMonth() == i){
                    list1.add(list.get(index));
                    index++;
                } else {
                    SOrderMonthA sOrderMonthA = new SOrderMonthA();
                    sOrderMonthA.setSid(studentId);
                    sOrderMonthA.setMonth(i);
                    list1.add(sOrderMonthA);
                }
            }
            return list1;
        }
    }

    @Override
    public List<IOrderMonthA> getIOrderMonthA(String code){
        List<IOrderMonthA> list = repository.getIOrderMonthA(code);
        if(list == null){
            return null;
        }else {
            List<IOrderMonthA> list1 = new ArrayList<>();
            Collections.sort(list, new Comparator<IOrderMonthA>() {
                @Override
                public int compare(IOrderMonthA o1, IOrderMonthA o2) {
                    return o1.getMonth() - o2.getMonth();
                }
            });

            IOrderMonthA oldest = list.get(0);
            IOrderMonthA newest = list.get(list.size() - 1);

            int index = 0;
            for(int i = oldest.getMonth(); i <= newest.getMonth(); i++){
                if(index == list.size()){
                    break;
                }
                if(list.get(index).getMonth() == i){
                    list1.add(list.get(index));
                    index++;
                }else {
                    IOrderMonthA iOrderMonthA = new IOrderMonthA();
                    iOrderMonthA.setCode(code);
                    iOrderMonthA.setMonth(i);
                    list1.add(iOrderMonthA);
                }
            }
            return list1;
        }
    }

    @Override
    public SOrderTypeA getSOrderTypeA(Long studentId){
        SOrderTypeA sOrderTypeA = repository.getSOrderTypeA(studentId);
        if(sOrderTypeA == null){
            sOrderTypeA = new SOrderTypeA();
            sOrderTypeA.setSid(studentId);
            return sOrderTypeA;
        }else {
            return sOrderTypeA;
        }
    }

    @Override
    public SOrderGradeA getSOrderGradeA(Long studentId){
        SOrderGradeA sOrderGradeA = repository.getSOrderGradeA(studentId);
        if(sOrderGradeA == null){
            sOrderGradeA = new SOrderGradeA();
            sOrderGradeA.setSid(studentId);
            return sOrderGradeA;
        }else {
            return sOrderGradeA;
        }
    }


}
