package es.fjtorres.jasperReport.service.export;

import es.fjtorres.jasperReport.service.Format;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterConfiguration;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.ExporterOutput;
import net.sf.jasperreports.export.ReportExportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

public class XlsxExporter extends AbstractExporter {

   protected SimpleXlsxReportConfiguration reportConfiguration = new SimpleXlsxReportConfiguration();
   protected SimpleXlsxExporterConfiguration exportConfiguration = new SimpleXlsxExporterConfiguration();

   public XlsxExporter() {
      reportConfiguration.setIgnoreGraphics(Boolean.FALSE);
      reportConfiguration.setCollapseRowSpan(Boolean.FALSE);
      reportConfiguration.setDetectCellType(Boolean.TRUE);
      reportConfiguration.setFontSizeFixEnabled(Boolean.TRUE);
      reportConfiguration.setRemoveEmptySpaceBetweenColumns(Boolean.FALSE);
      reportConfiguration.setRemoveEmptySpaceBetweenRows(Boolean.FALSE);
      reportConfiguration.setIgnoreCellBorder(Boolean.FALSE);
      reportConfiguration.setImageBorderFixEnabled(Boolean.FALSE);
      reportConfiguration.setShowGridLines(Boolean.FALSE);
      reportConfiguration.setIgnoreCellBackground(Boolean.FALSE);
      reportConfiguration.setWrapText(Boolean.FALSE);
      reportConfiguration.setOnePagePerSheet(Boolean.TRUE);
      reportConfiguration.setColumnWidthRatio(1.25f);
   }

   public String getContentType() {
      return Format.XLSX.getContentType();
   }

   public String getExtension() {
      return Format.XLSX.getExtension();
   }

   public SimpleXlsxReportConfiguration getReportConfiguration() {
      return reportConfiguration;
   }

   public SimpleXlsxExporterConfiguration getExporterConfiguration() {
      return exportConfiguration;
   }

   @SuppressWarnings({
         "rawtypes", "unchecked"
   })
   @Override
   public Exporter<ExporterInput, ReportExportConfiguration, ExporterConfiguration, ExporterOutput> getExporter() {
      return (Exporter) new JRXlsxExporter();
   }

}
