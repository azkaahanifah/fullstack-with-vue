package com.coder.jasperreport.controller;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_PDF;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coder.jasperreport.model.Order;
import com.coder.jasperreport.service.InvoiceService;
import com.coder.jasperreport.service.MockOrderService;

import org.springframework.core.io.InputStreamResource;

@Controller
@RequestMapping("/invoice/generator")
public class InvoiceController {
	
	static private Logger LOGGER = LogManager.getLogger(InvoiceController.class);
	
	@Resource
	private MockOrderService mockOrderService;
	
	@Resource
	private InvoiceService invoiceService;
	
	 @GetMapping("/forms")
	 public String invoiceForms() {
	    return "forms";
	 }
	
	@PostMapping(value = "/generate", produces = "application/pdf")
	public ResponseEntity<InputStreamResource> invoiceGenerate(@RequestParam(name = "code", defaultValue = "XYZ123456789") String code,
															   @RequestParam(name = "lang", defaultValue = "en") String lang) throws IOException {
		LOGGER.info("Start invoice generation...");
		final Order order = mockOrderService.getOrderByCode(code);
		final File invoicePdf = invoiceService.generateInvoiceFor(order, Locale.forLanguageTag(lang));
		LOGGER.info("Invoice generated successfully!");
		
		final HttpHeaders headers = getHttpHeaders(code, lang, invoicePdf);
		return new ResponseEntity<>(new InputStreamResource(new FileInputStream(invoicePdf)), headers, OK);
	}
	
	private HttpHeaders getHttpHeaders(String code, String lang, File invoicePdf) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(APPLICATION_PDF);
        respHeaders.setContentLength(invoicePdf.length());
        respHeaders.setContentDispositionFormData("attachment", format("%s-%s.pdf", code, lang));
        return respHeaders;
    }
	
	/**
	 * just to check service running or not
	 * 
	 * @return OK
	 */
	@GetMapping(value = "/checkok")
	public ResponseEntity<String> getCheckOk() {
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	

}
