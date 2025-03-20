package com.cab.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cab.model.ServiceForm;

public interface ServiceFormService {
	public ServiceForm addService(ServiceForm formServiceForm,MultipartFile multipartFile) throws Exception;
	public List<ServiceForm> readAllServices();
}
