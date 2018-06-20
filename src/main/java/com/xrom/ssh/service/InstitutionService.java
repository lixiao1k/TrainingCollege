package com.xrom.ssh.service;

import com.xrom.ssh.entity.IOrderA;
import com.xrom.ssh.entity.Institution;
import com.xrom.ssh.entity.vo.MInstitutionVO;

import java.util.List;

public interface InstitutionService {
    String createInstitution(Institution institution);
    Institution getInstitution(String code);
    Institution getInstitution(String code, String password);
    void deleteInstitution(String code);
    void updateAddress(String code, String address);
    void updatePhone(String code, String phone);
    void updateDescription(String code, String description);
    void updateName(String code, String name);
    List<Institution> getAllInstitutions();
    void flush();
    Boolean signIn(String code, String password);
    List<MInstitutionVO> getAllMInstitutionVOs();
    //获得所有机构的财务汇总
    List<MInstitutionVO> getAllInstitutionFinancial();
    IOrderA getIOrderA(String code);
}
