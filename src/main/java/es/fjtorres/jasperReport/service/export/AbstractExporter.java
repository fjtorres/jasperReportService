package es.fjtorres.jasperReport.service.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterConfiguration;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.ExporterOutput;
import net.sf.jasperreports.export.ReportExportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import es.fjtorres.jasperReport.service.ReportServiceException;

public abstract class AbstractExporter implements IExporter {

   @Override
   public byte[] export(final JasperPrint jPrint) throws ReportServiceException {
      final ByteArrayOutputStream output = new ByteArrayOutputStream();

      final List<JasperPrint> jPrints = Arrays.asList(jPrint);

      final Exporter<ExporterInput, ReportExportConfiguration, ExporterConfiguration, ExporterOutput> exporter = getExporter();

      exporter.setExporterInput(SimpleExporterInput.getInstance(jPrints));

      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output));

      final ExporterConfiguration exporterConfiguration = getExporterConfiguration();
      if (exporterConfiguration != null) {
         exporter.setConfiguration(exporterConfiguration);
      }

      final ReportExportConfiguration reportConfiguration = getReportConfiguration();
      if (reportConfiguration != null) {
         exporter.setConfiguration(reportConfiguration);
      }

      try {
         exporter.exportReport();
         output.flush();
         return output.toByteArray();
      } catch (final JRException e) {
         throw new ReportServiceException("", e);
      } catch (final IOException e) {
         throw new ReportServiceException("", e);
      } finally {
         try {
            output.close();
         } catch (final IOException e) {
            throw new ReportServiceException("", e);
         }
      }
   }

}
