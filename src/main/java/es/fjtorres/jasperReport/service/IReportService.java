package es.fjtorres.jasperReport.service;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

public interface IReportService {

   /**
    * @param format
    * @param reportDesign
    * @param dataSource
    * @return
    * @throws IllegalArgumentException
    */
   byte[] generateReport(Format format, String reportDesign, JRDataSource dataSource)
         throws IllegalArgumentException, ReportServiceException;

   /**
    * @param format
    * @param reportDesign
    * @param parameters
    * @param dataSource
    * @return
    * @throws IllegalArgumentException
    * @throws ReportServiceException
    */
   byte[] generateReport(Format format, String reportDesign, Map<String, Object> parameters,
         JRDataSource dataSource) throws IllegalArgumentException, ReportServiceException;
}
