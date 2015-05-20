/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.domain;

/**
 *
 * @author jasmin.gacic
 */
public class FirewallRule extends ProfitbricksBase {

   /**
    * @return the properties
    */
   public Properties getProperties() {
      return properties;
   }

   /**
    * @param properties the properties to set
    */
   public void setProperties(Properties properties) {
      this.properties = properties;
   }

   public class Properties {

      private String name;
      private Protocol protocol;
      private String sourceMac;
      private String sourceIp;
      private String targetIp;
      private String portRangeStart;
      private String portRangeEnd;
      private String icmpType;
      private String icmpCode;

      /**
       * @return the name
       */
      public String getName() {
         return name;
      }

      /**
       * @param name the name to set
       */
      public void setName(String name) {
         this.name = name;
      }

      /**
       * @return the protocol
       */
      public Protocol getProtocol() {
         return protocol;
      }

      /**
       * @param protocol the protocol to set
       */
      public void setProtocol(Protocol protocol) {
         this.protocol = protocol;
      }

      /**
       * @return the sourceMac
       */
      public String getSourceMac() {
         return sourceMac;
      }

      /**
       * @param sourceMac the sourceMac to set
       */
      public void setSourceMac(String sourceMac) {
         this.sourceMac = sourceMac;
      }

      /**
       * @return the sourceIp
       */
      public String getSourceIp() {
         return sourceIp;
      }

      /**
       * @param sourceIp the sourceIp to set
       */
      public void setSourceIp(String sourceIp) {
         this.sourceIp = sourceIp;
      }

      /**
       * @return the targetIp
       */
      public String getTargetIp() {
         return targetIp;
      }

      /**
       * @param targetIp the targetIp to set
       */
      public void setTargetIp(String targetIp) {
         this.targetIp = targetIp;
      }

      /**
       * @return the portRangeStart
       */
      public String getPortRangeStart() {
         return portRangeStart;
      }

      /**
       * @param portRangeStart the portRangeStart to set
       */
      public void setPortRangeStart(String portRangeStart) {
         this.portRangeStart = portRangeStart;
      }

      /**
       * @return the portRangeEnd
       */
      public String getPortRangeEnd() {
         return portRangeEnd;
      }

      /**
       * @param portRangeEnd the portRangeEnd to set
       */
      public void setPortRangeEnd(String portRangeEnd) {
         this.portRangeEnd = portRangeEnd;
      }

      /**
       * @return the icmpType
       */
      public String getIcmpType() {
         return icmpType;
      }

      /**
       * @param icmpType the icmpType to set
       */
      public void setIcmpType(String icmpType) {
         this.icmpType = icmpType;
      }

      /**
       * @return the icmpCode
       */
      public String getIcmpCode() {
         return icmpCode;
      }

      /**
       * @param icmpCode the icmpCode to set
       */
      public void setIcmpCode(String icmpCode) {
         this.icmpCode = icmpCode;
      }
   }

   private Properties properties = new Properties();

}
