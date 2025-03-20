package com.cab.service;

import org.springframework.stereotype.Component;


public interface AdminCredentialsService {
	public String checkAdminCredentials(String oldUsername,String oldPassword);
	public String updateAdminCredentials(String newUsername,String newPassword,String oldUsername);
}
