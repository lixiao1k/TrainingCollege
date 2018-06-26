package com.xrom.ssh.repository;

import com.xrom.ssh.entity.IOrderA;
import com.xrom.ssh.entity.Institution;

public interface InstitutionRepository extends DomainRepository<Institution,String> {
    Institution get(String code, String password);
    IOrderA getIOrderA(String code);
    void addInstitution(Institution institution);
}
