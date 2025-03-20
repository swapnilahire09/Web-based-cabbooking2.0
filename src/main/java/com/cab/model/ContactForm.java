package com.cab.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contactform")
public class ContactForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty(message = "*Name Cannot be Empty")
	@Size(min=2,max= 30, message = "*Invalid Name Size")
	@Column(length = 30)
	private String name;
	
	@NotEmpty(message = "*Email Cannot be Empty")
	@Size(min = 5,max=50,message = "*Invalid Email Size")
	@Column(length = 50)
	private String email;
	
	@NotNull(message = "*Phone Cannot be empty")
	@Min(value = 1000000000,message = "*Phone number must be at least 10 digits")
	@Max(value = 9999999999l,message = "*Phone number must be at least 10 digits")
	@Column(length = 10)
	private Long phone;
	
	@NotEmpty(message = "*Message Cannot be empty")
	@Size(min=3,max = 500,message = "*Invalid Message Size")
	@Column(length = 500)
	private String msg;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ContactForm [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", msg=" + msg
				+ "]";
	}
	
	

}
