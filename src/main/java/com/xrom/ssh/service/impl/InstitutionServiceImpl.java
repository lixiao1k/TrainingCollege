package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Institution;
import com.xrom.ssh.repository.InstitutionRepository;
import com.xrom.ssh.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired(required = true)
    private InstitutionRepository institutionRepository;

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
}
