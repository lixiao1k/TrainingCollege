package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Institution;
import com.xrom.ssh.entity.ModifyApplication;
import com.xrom.ssh.enums.ApplicationState;
import com.xrom.ssh.repository.InstitutionRepository;
import com.xrom.ssh.repository.ModifyApplicationRepository;
import com.xrom.ssh.service.ModifyApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModifyApplicationServiceImpl implements ModifyApplicationService{

    @Autowired(required = true)
    private ModifyApplicationRepository repository;

    @Autowired(required = true)
    private InstitutionRepository institutionRepository;

    @Override
    public Long saveApplication(ModifyApplication modifyApplication) {
        return repository.save(modifyApplication);
    }

    @Override
    public void deleteApplication(Long id) {
        repository.delete(id);
        flush();
    }

    @Override
    public ModifyApplication getApplication(Long id) {
        return repository.get(id);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public List<ModifyApplication> findAll() {
        return repository.findAll();
    }

    private List<ModifyApplication> getAgreed(List<ModifyApplication> applications, ApplicationState state){
        List<ModifyApplication> agreedApplication = new ArrayList<>();
        List<ModifyApplication> rejectedApplication = new ArrayList<>();
        List<ModifyApplication> reservedApplication = new ArrayList<>();
        if(applications == null){
            return null;
        }
        for(ModifyApplication application : applications){
            if(application.getIsAgreed() == 1 && application.getIsRejected() == 0){ //同意
                agreedApplication.add(application);
            }else if(application.getIsRejected() == 1 && application.getIsAgreed() == 0){ //拒绝
                rejectedApplication.add(application);
            }else if(application.getIsAgreed() == 0 && application.getIsRejected() == 0){
                reservedApplication.add(application);
            }
        }
        if(state == ApplicationState.AGREED){
            return agreedApplication;
        }else if(state == ApplicationState.REJECTED){
            return rejectedApplication;
        }else {
            return reservedApplication;
        }
    }

    @Override
    public List<ModifyApplication> findAll(ApplicationState state) {
        return getAgreed(findAll(), state);
    }

    @Override
    public List<ModifyApplication> findAll(String institutionCode) {
        return repository.findAll(institutionCode);
    }

    @Override
    public void reject(Long id) {
        ModifyApplication application = getApplication(id);
        application.setIsRejected(1);
        application.setIsAgreed(0);
        repository.update(application);
        flush();
    }

    @Override
    public void agree(Long id) {
        ModifyApplication application = getApplication(id);
        application.setIsAgreed(1);
        application.setIsRejected(0);
        repository.update(application);
        flush();
        Institution institution = institutionRepository.get(application.getInstitutionCode());
        institution.setDescription(application.getDescription());
        institution.setAddress(application.getAddress());
        institution.setPhone(application.getPhone());
        institutionRepository.update(institution);
        flush();
    }
}
