package es.fjtorres.jasperReport.service.export;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterConfiguration;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.ExporterOutput;
import net.sf.jasperreports.export.ReportExportConfiguration;
import es.fjtorres.jasperReport.service.ReportServiceException;

public interface IExporter {

   String getContentType();

   String getExtension();

   ReportExportConfiguration getReportConfiguration();

   ExporterConfiguration getExporterConfiguration();

   Exporter<ExporterInput, ReportExportConfiguration, ExporterConfiguration, ExporterOutput> getExporter();

   byte[] export(JasperPrint jPrint) throws ReportServiceException;
}
