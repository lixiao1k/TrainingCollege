package com.xrom.ssh.repository;

import com.xrom.ssh.entity.ModifyApplication;

import java.util.List;

public interface ModifyApplicationRepository extends DomainRepository<ModifyApplication,Long> {
    void reject(Long id);
    void agree(Long id);
    List<ModifyApplication> findAll(String institutionCode);
}
