package com.coder.jasperreport.service;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import com.coder.jasperreport.model.Order;

public interface InvoiceService {
	
	public File generateInvoiceFor(Order order, Locale locale) throws IOException;

}
