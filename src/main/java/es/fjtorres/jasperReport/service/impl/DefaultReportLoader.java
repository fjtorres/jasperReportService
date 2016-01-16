package es.fjtorres.jasperReport.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import javax.inject.Named;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import es.fjtorres.jasperReport.service.IReportLoader;
import es.fjtorres.jasperReport.service.Report;

@Named
public class DefaultReportLoader implements IReportLoader {

   @Override
   public <T> JasperReport load(final Report<T> pReport) throws JRException {
      return load(pReport.getTemplate());
   }

   @Override
   public <T> JasperReport load(final Report<T> pReport, final String pTemplatePath)
         throws JRException {
      String location = pTemplatePath;
      if (!pTemplatePath.endsWith(File.separator)) {
         location += File.separator;
      }

      return load(location + pReport.getTemplate());
   }

   private JasperReport load(final String template) throws JRException {
      checkTemplate(template);

      JasperReport jReport = null;
      if (template.endsWith(".jrxml")) {
         jReport = JasperCompileManager.compileReport(template);
      } else {
         jReport = (JasperReport) JRLoader.loadObjectFromFile(template);
      }
      return jReport;
   }

   /**
    * 
    * @param pathToTemplate
    * @throws IllegalArgumentException
    */
   private void checkTemplate(final String pathToTemplate) throws IllegalArgumentException {
      try {
         Files.exists(Paths.get(pathToTemplate));
      } catch (final InvalidPathException e) {
         throw new IllegalArgumentException(
               String.format("Template not found, %s", pathToTemplate), e);
      }
   }
}
