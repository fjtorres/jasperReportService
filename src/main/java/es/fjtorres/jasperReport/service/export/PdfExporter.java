package es.fjtorres.jasperReport.service.export;

import es.fjtorres.jasperReport.service.Format;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterConfiguration;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.ExporterOutput;
import net.sf.jasperreports.export.ReportExportConfiguration;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

public class PdfExporter extends AbstractExporter {

   protected SimplePdfReportConfiguration reportConfiguration = new SimplePdfReportConfiguration();
   protected SimplePdfExporterConfiguration exportConfiguration = new SimplePdfExporterConfiguration();

   public PdfExporter() {
      exportConfiguration.setCompressed(Boolean.TRUE);
   }

   public String getContentType() {
      return Format.PDF.getContentType();
   }

   public String getExtension() {
      return Format.PDF.getExtension();
   }

   public SimplePdfReportConfiguration getReportConfiguration() {
      return reportConfiguration;
   }

   public SimplePdfExporterConfiguration getExporterConfiguration() {
      return exportConfiguration;
   }

   @SuppressWarnings({
         "unchecked", "rawtypes"
   })
   @Override
   public Exporter<ExporterInput, ReportExportConfiguration, ExporterConfiguration, ExporterOutput> getExporter() {
      return (Exporter) new JRPdfExporter();
   }

}
