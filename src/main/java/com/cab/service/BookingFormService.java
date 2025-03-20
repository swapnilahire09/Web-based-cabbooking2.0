package com.cab.service;

import java.util.List;

import com.cab.model.BookingForm;
import com.cab.model.ContactForm;

public interface BookingFormService {
	
	public BookingForm saveBookingFormService(BookingForm bookingForm);
	public List<BookingForm> readAllBookingsService();

	public void deleteBookingService(int id);
}
