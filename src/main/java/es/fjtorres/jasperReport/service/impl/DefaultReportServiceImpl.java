package es.fjtorres.jasperReport.service.impl;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.fjtorres.jasperReport.service.IReportLoader;
import es.fjtorres.jasperReport.service.IReportService;
import es.fjtorres.jasperReport.service.Objects;
import es.fjtorres.jasperReport.service.Report;
import es.fjtorres.jasperReport.service.ReportServiceException;
import es.fjtorres.jasperReport.service.export.IExporter;
import es.fjtorres.jasperReport.service.export.PdfExporter;
import es.fjtorres.jasperReport.service.export.XlsExporter;
import es.fjtorres.jasperReport.service.export.XlsxExporter;

@Named("defaultReportService")
public class DefaultReportServiceImpl implements IReportService {

   private static final String ERROR_GENERATING_REPORT = "Error generating report.";

   private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReportServiceImpl.class);

   private final IReportLoader reportLoader;

   private final String reportLocation;

   @Inject
   public DefaultReportServiceImpl(@Named("reportService.location") final String pReportLocation,
         final IReportLoader pReportLoader) {
      this.reportLocation = pReportLocation;
      this.reportLoader = pReportLoader;

      checkLocation();
   }

   @Override
   public <T> byte[] generate(final Report<T> report, final boolean includeDefaultPath)
         throws ReportServiceException {
      Objects.checkNull(report, "report is required.");
      Objects.checkExpression(!report.validate(), "The report parameter is not valid.");
      
      try {
         JasperReport jReport = null;
         if (includeDefaultPath) {
            jReport = reportLoader.load(report, reportLocation);
         } else {
            jReport = reportLoader.load(report);
         }
         final JasperPrint jPrint = print(jReport, getDatasource(report), report.getParameters());
         final IExporter exporter = resolveExporter(report);
         return exporter.export(jPrint);
      } catch (final JRException e) {
         LOGGER.error(ERROR_GENERATING_REPORT, e);
         throw new ReportServiceException(ERROR_GENERATING_REPORT, e);
      }

   }

   private JasperPrint print(final JasperReport jReport, final JRDataSource datasource,
         final Map<String, Object> parameters) throws JRException {
      return JasperFillManager.fillReport(jReport, parameters, datasource);
   }

   private <T> JRDataSource getDatasource(final Report<T> report) {
      JRDataSource datasource = null;
      if (report.getData() != null) {
         datasource = new JRBeanCollectionDataSource(report.getData());
      } else {
         datasource = new JREmptyDataSource();
      }

      return datasource;
   }

   private <T> IExporter resolveExporter(final Report<T> report) {
      IExporter exporter = null;

      switch (report.getFormat()) {
      case XLS:
         exporter = new XlsExporter();
         break;
      case XLSX:
         exporter = new XlsxExporter();
         break;
      case PDF:
      default:
         exporter = new PdfExporter();
         break;
      }

      return exporter;
   }

   /**
    * @throws IllegalArgumentException
    */
   private void checkLocation() throws IllegalArgumentException {
      try {
         Paths.get(reportLocation);
      } catch (final InvalidPathException e) {
         throw new IllegalArgumentException("reportLocation", e);
      }
   }

}
