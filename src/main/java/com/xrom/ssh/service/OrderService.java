package com.xrom.ssh.service;

import com.xrom.ssh.entity.*;
import com.xrom.ssh.entity.vo.BillsVO;
import com.xrom.ssh.entity.vo.OrderVO;
import com.xrom.ssh.entity.vo.SPayInfoVO;
import com.xrom.ssh.enums.OrderState;

import javax.swing.event.ListDataEvent;
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
    void payOffline(Long orderId, int payment);
    void dropClass(Long orderId, int amountReturn);
    List<OrderVO> getAllOfStudentByState(Long studentId, OrderState state);
    List<OrderVO> getAllOfClassByState(Long classId, OrderState state);
    List<OrderVO> getAllOfInstituteByState(String institutionCode, OrderState state);
    void flush();
    Long save(Order order);
    SPayInfoVO getPayInfoVO(Long orderId, Long studentId);
    //获得退款时展示信息的OrderVO对象，包含退款数量
    OrderVO getUnsubscribeInfo(Long orderId);
    //获得机构所有线下支付账目
    List<BillsVO> getAllOfflineBillsOfInstitute(String institutionCode);

    //获得机构所有支付账目
    List<BillsVO> getAllPayedBillsOfInstitute(String institutionCode);

    //获得机构所有退款账目
    List<BillsVO> getAllDropedBillsOfInstitute(String institutionCode);

    //获得平台所有线下支付账目
    List<BillsVO> getAllOfflineBills();

    //获得平台所有支付账目
    List<BillsVO> getAllPayedBills();

    //获得平台所有退款账目
    List<BillsVO> getAllDropedBills();

    //求账目总金额
    int getBillsSum(List<BillsVO> list);

    void checkOrder();

    List<SOrderYearA> getSOrderYearA(Long studentId);
    List<SOrderSeasonA> getSOrderSeasonA(Long studentId);
    List<SOrderMonthA> getSOrderMonthA(Long studentId);
    SOrderTypeA getSOrderTypeA(Long studentId);
    SOrderGradeA getSOrderGradeA(Long studentId);
    List<IOrderYearA> getIOrderYearA(String code);
    List<IOrderSeasonA> getIOrderSeasonA(String code);
    List<IOrderMonthA> getIOrderMonthA(String code);
}
