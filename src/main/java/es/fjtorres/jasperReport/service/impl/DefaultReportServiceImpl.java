package es.fjtorres.jasperReport.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.fjtorres.jasperReport.service.Format;
import es.fjtorres.jasperReport.service.IReportService;
import es.fjtorres.jasperReport.service.ReportServiceException;

@Named("defaultReportService")
public class DefaultReportServiceImpl implements IReportService {

   private static final String ERROR_GENERATING_REPORT = "Error generating report.";

   private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReportServiceImpl.class);

   private final String reportLocation;

   @Inject
   public DefaultReportServiceImpl(@Named("reportService.location") final String pReportLocation) {
      this.reportLocation = pReportLocation;

      checkLocation();
   }

   @Override
   public byte[] generateReport(Format pFormat, String pReportDesign,
         Map<String, Object> pParameters, JRDataSource pDataSource)
         throws IllegalArgumentException, ReportServiceException {
      checkReport(pReportDesign);

      byte[] outputReport = null;
      try {
         final JasperDesign jdReport = JRXmlLoader.load(Files.newInputStream(Paths.get(
               reportLocation).resolve(pReportDesign)));
         final JasperReport jrReport = JasperCompileManager.compileReport(jdReport);
         final JasperPrint jpReport = JasperFillManager.fillReport(jrReport, pParameters,
               pDataSource);

         switch (pFormat) {
         case PDF:
            outputReport = JasperExportManager.exportReportToPdf(jpReport);
            break;
         case XLS:
            outputReport = exportToXls(jpReport);
            break;
         }

         if (outputReport == null) {

         }
      } catch (final JRException | IOException e) {
         LOGGER.error(ERROR_GENERATING_REPORT, e);
         throw new ReportServiceException(ERROR_GENERATING_REPORT, e);
      }

      return outputReport;
   }

   @Override
   public byte[] generateReport(final Format pFormat, final String pReportDesign,
         final JRDataSource pDataSource) throws IllegalArgumentException, ReportServiceException {

      return generateReport(pFormat, pReportDesign, null, pDataSource);
   }

   /**
    * 
    * @param pReportDesign
    * @throws IllegalArgumentException
    */
   private void checkReport(final String pReportDesign) throws IllegalArgumentException {
      final Path location = Paths.get(reportLocation);
      try {
         Files.exists(location.resolve(pReportDesign));
      } catch (final InvalidPathException e) {
         throw new IllegalArgumentException("reportDesign", e);
      }
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

   private byte[] exportToXls(final JasperPrint jpReport) throws JRException {
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();

      final JRXlsExporter exporter = new JRXlsExporter();
      exporter.setExporterInput(new SimpleExporterInput(jpReport));
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

      final SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
      configuration.setOnePagePerSheet(false);
      configuration.setDetectCellType(true);
      configuration.setCollapseRowSpan(false);
      configuration.setWhitePageBackground(false);
      configuration.setRemoveEmptySpaceBetweenRows(true);
      exporter.setConfiguration(configuration);
      exporter.setConfiguration(new SimpleXlsExporterConfiguration());

      exporter.exportReport();

      return baos.toByteArray();
   }
}
