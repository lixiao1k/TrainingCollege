package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Institution;
import com.xrom.ssh.entity.RegisterApplication;
import com.xrom.ssh.enums.ApplicationState;
import com.xrom.ssh.repository.InstitutionRepository;
import com.xrom.ssh.repository.RegisterApplicationRepository;
import com.xrom.ssh.service.InstitutionService;
import com.xrom.ssh.service.RegisterApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RegisterApplicationServiceImpl implements RegisterApplicationService{

    @Autowired(required = true)
    RegisterApplicationRepository repository;

    @Autowired(required = true)
    InstitutionService institutionService;


    @Override
    public Long saveApplication(RegisterApplication application) {
        return repository.save(application);
    }

    @Override
    public void deleteApplication(Long id) {
        repository.delete(id);
        flush();
    }

    @Override
    public RegisterApplication getApplication(Long id) {
        return repository.get(id);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public List<RegisterApplication> findAll() {
        return repository.findAll();
    }

    @Override
    public List<RegisterApplication> findAll(ApplicationState state) {
        List<RegisterApplication> agreedApplications = new ArrayList<>();
        List<RegisterApplication> rejectedApplication = new ArrayList<>();
        List<RegisterApplication> reservedApplication = new ArrayList<>();
        List<RegisterApplication> applications = findAll();
        if(applications ==  null){
            return null;
        }
        for(RegisterApplication application : applications){
            if(application.getIs_agreed() == 1 && application.getIs_rejected() == 0){
                agreedApplications.add(application);
            }else if(application.getIs_rejected() == 1 && application.getIs_agreed() == 0){
                rejectedApplication.add(application);
            }else if(application.getIs_agreed() == 0 && application.getIs_rejected() == 0){
                reservedApplication.add(application);
            }
        }
        if(state == ApplicationState.AGREED){
            return agreedApplications;
        }else if(state == ApplicationState.REJECTED){
            return rejectedApplication;
        }else {
            return reservedApplication;
        }
    }

    @Override
    public void reject(Long id) {
        repository.reject(id);
        flush();
    }

    @Override
    public void agree(Long id) {
        repository.agree(id);
        RegisterApplication application = repository.get(id);
        Institution institution = new Institution();
        institution.setDescription(application.getDescription());
        institution.setPhone(application.getPhone());
        institution.setAddress(application.getAddress());
        institution.setName(application.getName());
        institution.setPassword(application.getPassword());
        institutionService.createInstitution(institution);
        institutionService.flush();
    }

    @Override
    public void register(String name, String phone, String description, String address, String password) {
        RegisterApplication application = new RegisterApplication();
        application.setName(name);
        application.setPhone(phone);
        application.setDescription(description);
        application.setAddress(address);
        application.setCreated_time(new Date());
        application.setPassword(password);
        saveApplication(application);
        flush();
    }
}
