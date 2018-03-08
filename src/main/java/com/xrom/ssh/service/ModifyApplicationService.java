package com.xrom.ssh.service;

import com.xrom.ssh.entity.ModifyApplication;
import com.xrom.ssh.enums.ApplicationState;
import com.xrom.ssh.exceptions.NoInstitutionException;

import java.util.List;

public interface ModifyApplicationService {
    Long saveApplication(ModifyApplication modifyApplication);
    void deleteApplication(Long id);
    ModifyApplication getApplication(Long id);
    void flush();
    List<ModifyApplication> findAll();
    List<ModifyApplication> findAll(ApplicationState state);
    List<ModifyApplication> findAll(String institutionCode);
    void reject(Long id);
    void agree(Long id);
    void modifyApplication(String code, String address, String description, String phone, String name) throws NoInstitutionException;
}
