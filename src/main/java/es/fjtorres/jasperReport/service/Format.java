package es.fjtorres.jasperReport.service;

public enum Format {
   PDF("pdf", "application/pdf"), XLS("xls", "application/vnd.ms-excel"), XLSX("xlsx",
         "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

   private final String extension;

   private final String contentType;

   private Format(final String pExtension, final String pContentType) {
      this.extension = pExtension;
      this.contentType = pContentType;
   }

   public String getExtension() {
      return extension;
   }

   public String getContentType() {
      return contentType;
   }

}
