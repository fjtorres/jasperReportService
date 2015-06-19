package es.fjtorres.jasperReport.service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;

public interface IReportLoader {

   <T> JasperReport load(Report<T> report) throws JRException;

   <T> JasperReport load(Report<T> report, String templatePath) throws JRException;
}
