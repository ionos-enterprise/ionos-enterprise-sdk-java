/*
 * Copyright 2015 jasmin.gacic.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
