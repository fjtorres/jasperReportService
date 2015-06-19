package es.fjtorres.jasperReport.service;


public interface IReportService {

   <T> byte[] generate(Report<T> report, boolean includeDefaultPath) throws ReportServiceException;
}
