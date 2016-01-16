package es.fjtorres.jasperReport.service;


public interface IReportService {

   String REPORT_LOCALE = "REPORT_LOCALE";
   String REPORT_RESOURCE_BUNDLE = "REPORT_RESOURCE_BUNDLE";
   
   <T> byte[] generate(Report<T> report, boolean includeDefaultPath) throws ReportServiceException;
}
