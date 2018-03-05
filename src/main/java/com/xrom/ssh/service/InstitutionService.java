package com.xrom.ssh.service;

import com.xrom.ssh.entity.Institution;

import java.util.List;

public interface InstitutionService {
    String createInstitution(Institution institution);
    Institution getInstitution(String code);
    void deleteInstitution(String code);
    void updateAddress(String code, String address);
    void updatePhone(String code, String phone);
    void updateDescription(String code, String description);
    List<Institution> getAllInstitutions();
    void flush();
}
