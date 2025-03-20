package com.cab.service;

import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cab.dao.ServiceFormCrud;
import com.cab.model.ServiceForm;

import jakarta.transaction.Transactional;

@Service
public class ServiceFormServiceImpl implements ServiceFormService {

	private ServiceFormCrud serviceFormCrud;

	@Autowired
	public void setServiceFormCrud(ServiceFormCrud serviceFormCrud) {
		this.serviceFormCrud = serviceFormCrud;
	}

	@Transactional(rollbackOn=Exception.class)
	@Override
	public ServiceForm addService(ServiceForm serviceForm, MultipartFile multipartFile) throws Exception{
		ServiceForm save = null;
		try {
			save = serviceFormCrud.save(serviceForm);
			if (save!=null) {
				String path = "C:\\Users\\dell\\Downloads\\Cab Booking\\Cab-Booking\\src\\main\\resources\\static\\myserviceimg\\"
						+ multipartFile.getOriginalFilename();
				byte[] bytes = multipartFile.getBytes();
				FileOutputStream fos = new FileOutputStream(path);
				fos.write(bytes);
			}
			
		} catch (Exception e) {
			save=null;
			throw e;
		}
		return save;
	}

	@Override
	public List<ServiceForm> readAllServices() {
		
		return serviceFormCrud.findAll();
	}

}
