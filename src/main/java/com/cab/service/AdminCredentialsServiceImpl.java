package com.cab.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cab.dao.AdminDao;
import com.cab.model.Admin;

@Service
public class AdminCredentialsServiceImpl implements AdminCredentialsService {

	private AdminDao adminDao;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public String checkAdminCredentials(String oldUsername, String oldPassword) {
		Optional<Admin> byUsername= adminDao.findByUsername(oldUsername);
		if (byUsername.isPresent()) {
			Admin admin=byUsername.get();
			boolean matches=passwordEncoder.matches(oldPassword, admin.getPassword());
			if (matches) {
				return "Success";
			} else {
				return "Wrong Old Credentials";
			}
		}else {
			return "Wrong Old Credentials";
		}
		
	}
	@Override
	public String updateAdminCredentials(String newUsername, String newPassword, String oldUsername) {
		int updateCredentials =adminDao.updateCredentials(newUsername, passwordEncoder.encode(newPassword), oldUsername);
		if (updateCredentials==1) {
			return "Credentials Updated Successfully";
		} else {
			return "Failed to update";
		}
	}

}
