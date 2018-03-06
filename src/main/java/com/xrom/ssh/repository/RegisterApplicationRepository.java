package com.xrom.ssh.repository;

import com.xrom.ssh.entity.RegisterApplication;

public interface RegisterApplicationRepository extends DomainRepository<RegisterApplication,Long> {
    void agree(Long id);
    void reject(Long id);
}
