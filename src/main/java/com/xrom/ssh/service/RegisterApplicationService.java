package com.xrom.ssh.service;

import com.xrom.ssh.entity.RegisterApplication;
import com.xrom.ssh.enums.ApplicationState;

import java.util.List;

public interface RegisterApplicationService {
    Long saveApplication(RegisterApplication application);
    void deleteApplication(Long id);
    RegisterApplication getApplication(Long id);
    void flush();
    List<RegisterApplication> findAll();
    List<RegisterApplication> findAll(ApplicationState state);
    void reject(Long id);
    void agree(Long id);
    void register(String name, String phone, String description, String address);

}
