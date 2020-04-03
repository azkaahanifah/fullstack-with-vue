package com.coder.jasperreport.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import com.coder.jasperreport.model.Order;
import com.coder.jasperreport.service.InvoiceService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

	private static Logger LOGGER = LogManager.getLogger(InvoiceServiceImpl.class);

	@Value("${invoice.logo.path}")
	private String logoPath;

	@Value("${invoice.template.path}")
	private String invoiceTemplate;

	@Override
	public File generateInvoiceFor(Order order, Locale locale) throws IOException {
		File pdfFile = File.createTempFile("my-invoice", ".pdf");
		LOGGER.info(String.format("Invoice PDF path : %s", pdfFile.getAbsolutePath()));
		try (FileOutputStream pos = new FileOutputStream(pdfFile)) {
			final JasperReport report = loadTemplate();
			final Map<String, Object> parameters = parameters(order, locale);
			// Create empty datasource
			final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
					Collections.singletonList("Invoice"));
			// Render the invoice as a PDF file
			JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);
			return pdfFile;
		} catch (final Exception e) {
			LOGGER.error(String.format("An error occured during PDF creation : %s", e));
			throw new RuntimeException(e);
		}
	}

	/* Fill template order params */
	private Map<String, Object> parameters(Order order, Locale locale) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("logo", getClass().getResourceAsStream(logoPath));
		parameters.put("order", order);
		parameters.put("REPORT_LOCALE", locale);
		return parameters;
	}

	/* Load invoice .jrxml template */
	private JasperReport loadTemplate() throws JRException {
		LOGGER.info(String.format("Invoice template path : %s", invoiceTemplate));
		final InputStream reportInputStream = getClass().getResourceAsStream(invoiceTemplate);
		final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
		return JasperCompileManager.compileReport(jasperDesign);
	}

}
