package com.cab.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cab.model.BookingForm;
import com.cab.model.ContactForm;
import com.cab.model.ServiceForm;
import com.cab.service.BookingFormService;
import com.cab.service.ContactFormService;
import com.cab.service.ServiceFormService;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class MyController {
	
	private ContactFormService contactFormService;
	private BookingFormService bookingFormService;
	private ServiceFormService serviceFormService;
	
	
	@Autowired
	public void setServiceFormService(ServiceFormService serviceFormService) {
		this.serviceFormService = serviceFormService;
	}
	@Autowired
	public void setBookingFormService(BookingFormService bookingFormService) {
		this.bookingFormService = bookingFormService;
	}
	@Autowired
	public void setContactFormService(ContactFormService contactFormService) {
		this.contactFormService = contactFormService;
	}
	@GetMapping(path = {"/","home","welcome","index"})
	public String welcomeView(HttpServletRequest httpServletRequest,Model model) {
		String requestUrl=httpServletRequest.getRequestURI();
		model.addAttribute("mycurrentpage",requestUrl);
		model.addAttribute("bookingForm",new BookingForm());
		return "index";
	}
	@GetMapping("about")
	public String aboutView(HttpServletRequest httpServletRequest,Model model) {
		String requestUrl=httpServletRequest.getRequestURI();
		model.addAttribute("mycurrentpage",requestUrl);
		return "about";
	}
	@GetMapping("cars")
	public String carsView(HttpServletRequest httpServletRequest,Model model) {
		String requestUrl=httpServletRequest.getRequestURI();
		model.addAttribute("mycurrentpage",requestUrl);
		return "cars";
	}
	@GetMapping("services")
	public String servicesView(HttpServletRequest httpServletRequest,Model model) {
		String requestUrl=httpServletRequest.getRequestURI();
		model.addAttribute("mycurrentpage",requestUrl);
		
		List<ServiceForm> allServices=serviceFormService.readAllServices();
		model.addAttribute("allservices",allServices);
		
		return "services";
	}
	@GetMapping("contacts")
	public String contactsView(HttpServletRequest httpServletRequest,Model model) {
		String requestUrl=httpServletRequest.getRequestURI();
		model.addAttribute("mycurrentpage",requestUrl);
		model.addAttribute("contactForm", new ContactForm());
		return "contacts";
	}

	@PostMapping("contactform")
	public String contactForm(@Valid @ModelAttribute ContactForm contactForm,BindingResult bindingResult,  Model model,RedirectAttributes attributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("bindingResult", bindingResult);
			return "contacts";
		}
		ContactForm saveContactFromService= contactFormService.saveContactFormService(contactForm);
		if (saveContactFromService!=null) {
			attributes.addFlashAttribute("message","Message Sent Successfuly");
		}
		else {
			attributes.addFlashAttribute("message","Something went Wrong");
		}
		return "redirect:/contacts";
	}
	
	@GetMapping("/login")
	public String adminLoginView(HttpServletRequest httpServletRequest,Model model) {
		ServletContext servletContext= httpServletRequest.getServletContext();
	    Object attribute= servletContext.getAttribute("logout");
		if (attribute instanceof Boolean) {
			model.addAttribute("logout",attribute);
			servletContext.removeAttribute("logout");
		}
		
		return "adminlogin";
	}
	
	@PostMapping("bookingform")
	public String bookingForm(@Valid @ModelAttribute BookingForm bookingForm,BindingResult bindingResult,  Model model,RedirectAttributes attributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("bindingResult", bindingResult);
			return "index";
		}
		else if (bookingForm.getAdult()+bookingForm.getChildren()>4) {
			model.addAttribute("message","the total no of adult and children cannot exceed 4");
			return "index";
		}
		
		//service
		BookingForm saveBookingFormService= bookingFormService.saveBookingFormService(bookingForm);
		
		if (saveBookingFormService!=null) {
			attributes.addFlashAttribute("message","Booking Successfuly");
		}
		else {
			attributes.addFlashAttribute("message","Something went Wrong");
		}
		
		return "redirect:/index";
	}

}
