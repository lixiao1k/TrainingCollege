package com.xrom.ssh.service;

import com.xrom.ssh.entity.ModifyApplication;
import com.xrom.ssh.enums.ApplicationState;

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
}
