package com.cab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cab.model.ServiceForm;
import com.cab.service.AdminCredentialsService;
import com.cab.service.BookingFormService;
import com.cab.service.ContactFormService;
import com.cab.service.ServiceFormService;

@Controller
@RequestMapping("admin")
public class AdminController {
	private ContactFormService contactFormService;
	private AdminCredentialsService adminCredentialsService;
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
	public void setAdminCredentialsService(AdminCredentialsService adminCredentialsService) {
		this.adminCredentialsService = adminCredentialsService;
	}

	@Autowired
	public void setContactFormService(ContactFormService contactFormService) {
		this.contactFormService = contactFormService;
	}
	
	@GetMapping(path = {"dashboard","admin"})
	public String adminDashboard() {
		return "admin/dashboard";
	}
	
	@GetMapping("readallcontacts")
	public String readAllContacts(Model model) {
		model.addAttribute("allcontacts",contactFormService.readAllContactsService());
		return "admin/readallcontacts";
	}

	@GetMapping("deletecontact/{id}")
	public String deleteContacts(@PathVariable int id ,RedirectAttributes redirectAttributes) {
		contactFormService.deleteContactService(id);
		redirectAttributes.addFlashAttribute("message","Contact Deleted Successfully");
		return "redirect:/admin/readallcontacts";
		
	}
	@GetMapping("changecredentials")
	public String changeCredentalsVeiw() {
		return "admin/changecredentials";
		
	}
	@PostMapping("changecredentials")
	public String changeCredentals(
			@RequestParam("oldusername") String oldusername,
			@RequestParam("oldpassword") String oldpassword,
			@RequestParam("newusername") String newusername,
			@RequestParam("newpassword") String newpassword,
			RedirectAttributes attributes
			) {
		String result= adminCredentialsService.checkAdminCredentials(oldusername, oldpassword);
		if (result.equals("Success")) {
			//Password Update
			result=adminCredentialsService.updateAdminCredentials(newusername, newpassword, oldusername);
			attributes.addFlashAttribute("message", result);
		}
		else {
			attributes.addFlashAttribute("message", result);
		}
		return "redirect:/admin/dashboard";
		
	}
	@GetMapping("readallbookings")
	public String readAllBookings(Model model) {
		model.addAttribute("allBookings",bookingFormService.readAllBookingsService());
		return "admin/readallbookings";
	}
	
	@GetMapping("deleteBooking/{id}")
	public String deleteBooking(@PathVariable int id ,RedirectAttributes redirectAttributes) {
		bookingFormService.deleteBookingService(id);
		redirectAttributes.addFlashAttribute("message","Booking Deleted Successfully");
		return "redirect:/admin/readallbookings";
		
	}
	@GetMapping("addservice")
	public String addServiceView() {
		
		return "admin/addservice";
		
	}
	@InitBinder
	public void stopBinding(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("image");
	}
	
	@PostMapping("addservice")
	public String addService(@ModelAttribute ServiceForm serviceForm,@RequestParam("image") MultipartFile multipartFile,RedirectAttributes redirectAttributes) {
		String originalFileName=multipartFile.getOriginalFilename();
		serviceForm.setImage(originalFileName);
		
		try {
			ServiceForm service= serviceFormService.addService(serviceForm, multipartFile);
			if (service!=null) {
				redirectAttributes.addFlashAttribute("msg", "Service Added Successfully");
			}
			else {
				redirectAttributes.addFlashAttribute("msg","Something Went Wrong");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:addservice";
		
	}
	
}
