package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Course;
import com.xrom.ssh.entity.IOrderA;
import com.xrom.ssh.entity.Institution;
import com.xrom.ssh.entity.vo.BillsVO;
import com.xrom.ssh.entity.vo.MInstitutionVO;
import com.xrom.ssh.repository.InstitutionRepository;
import com.xrom.ssh.service.CourseService;
import com.xrom.ssh.service.InstitutionService;
import com.xrom.ssh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.util.*;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired(required = true)
    private InstitutionRepository institutionRepository;

    @Autowired(required = true)
    private CourseService courseService;

    @Autowired(required = true)
    private OrderService orderService;

    @Override
    public String createInstitution(Institution institution) {
        String code = UUID.randomUUID().toString().replace("-","").substring(0,7);
        System.out.print(code);
        institution.setCode(code);
        return institutionRepository.save(institution);
    }

    @Override
    public Institution getInstitution(String code) {
        return institutionRepository.get(code);
    }

    @Override
    public Institution getInstitution(String code, String password) {
        return institutionRepository.get(code, password);
    }

    @Override
    public void deleteInstitution(String code) {
        institutionRepository.delete(code);
        flush();
    }

    @Override
    public void updateAddress(String code, String address) {
        Institution institution = getInstitution(code);
        institution.setAddress(address);
        institutionRepository.update(institution);
        flush();
    }

    @Override
    public void updatePhone(String code, String phone) {
        Institution institution = getInstitution(code);
        institution.setPhone(phone);
        institutionRepository.update(institution);
        flush();
    }

    @Override
    public void updateDescription(String code, String description) {
        Institution institution = getInstitution(code);
        institution.setDescription(description);
        institutionRepository.update(institution);
        flush();
    }

    @Override
    public void updateName(String code, String name) {
        Institution institution = getInstitution(code);
        institution.setName(name);
        institutionRepository.update(institution);
        flush();
    }

    @Override
    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }

    @Override
    public void flush() {
        institutionRepository.flush();
    }

    @Override
    public Boolean signIn(String code, String password) {
        Institution institution = getInstitution(code, password);
        if(institution == null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public List<MInstitutionVO> getAllMInstitutionVOs() {
        List<Institution> institutions = getAllInstitutions();
        List<MInstitutionVO> mInstitutionVOs = new ArrayList<>();
        List<Course> courses = null;
        List<Course> openedCourses = null;
        MInstitutionVO mInstitutionVO = null;
        for (Institution institution : institutions){
            mInstitutionVO = new MInstitutionVO();
            courses = courseService.findAll(institution.getCode());
            openedCourses = courseService.findAll(institution.getCode(), true);
            if(courses != null){
                mInstitutionVO.setCoursesNum(courses.size());
            }
            if(openedCourses != null){
                mInstitutionVO.setOpenedCoursesNum(openedCourses.size());
            }
            mInstitutionVO.setCode(institution.getCode());
            mInstitutionVO.setAddress(institution.getAddress());
            mInstitutionVO.setDescription(institution.getDescription());
            mInstitutionVO.setName(institution.getName());
            mInstitutionVO.setPassword(institution.getPassword());
            mInstitutionVO.setPhone(institution.getPhone());
            mInstitutionVOs.add(mInstitutionVO);
        }
        return mInstitutionVOs;
    }

    @Override
    public List<MInstitutionVO> getAllInstitutionFinancial() {
        List<MInstitutionVO> mInstitutionVOS = getAllMInstitutionVOs();
        for(MInstitutionVO mInstitutionVO : mInstitutionVOS){
            List<BillsVO> payedBills = orderService.getAllPayedBillsOfInstitute(mInstitutionVO.getCode());
            if(payedBills != null && payedBills.size() != 0){
                mInstitutionVO.setPayedSum(orderService.getBillsSum(payedBills));
            }
            List<BillsVO> droppedBills = orderService.getAllDropedBillsOfInstitute(mInstitutionVO.getCode());
            if(droppedBills != null && droppedBills.size() != 0){
                mInstitutionVO.setDroppedSum(orderService.getBillsSum(droppedBills));
            }
            List<BillsVO> payedOfflineBills = orderService.getAllOfflineBillsOfInstitute(mInstitutionVO.getCode());
            if(payedOfflineBills != null && payedOfflineBills.size() != 0){
                mInstitutionVO.setPayedOffline(orderService.getBillsSum(payedOfflineBills));
            }
            mInstitutionVO.setPayForInstitution
                            ((mInstitutionVO.getPayedSum()+mInstitutionVO.getDroppedSum())*7/10 - mInstitutionVO.getPayedOffline()*3/10);
        }
        return mInstitutionVOS;
    }


    //@管理信息系统
    @Override
    public IOrderA getIOrderA(String code){
        return institutionRepository.getIOrderA(code);
    }

    @Override
    public HashMap getAllInstitutionA(){
        List<Institution> list = institutionRepository.findAll();
        List<IOrderA> list1 = new ArrayList<>();
        for(Institution institution : list){
            IOrderA iOrderA = institutionRepository.getIOrderA(institution.getCode());
            if(iOrderA != null){
                list1.add(iOrderA);
            }else {
                iOrderA = new IOrderA();
                iOrderA.setCode(institution.getCode());
                list1.add(iOrderA);
            }
        }
        Collections.sort(list1, new Comparator<IOrderA>() {
            @Override
            public int compare(IOrderA o1, IOrderA o2) {
                return o2.getTotalPrice() - o1.getTotalPrice();
            }
        });
        list = new ArrayList<>();
        for(IOrderA iOrderA : list1){
            list.add(institutionRepository.get(iOrderA.getCode()));
        }
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("institutions", list);
        hashMap.put("institutionAs",list1);
        return hashMap;
    }
}
