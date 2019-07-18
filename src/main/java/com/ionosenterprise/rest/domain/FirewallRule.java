/*
 * Copyright (c) 2017, 1&1 IONOS Cloud GmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the 1&1 IONOS Cloud nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY 1&1 IONOS Cloud GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL 1&1 IONOS Cloud GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.ionosenterprise.rest.domain;

public class FirewallRule extends BaseResource {

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
      private String protocol;
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
      public String getProtocol() {
         return protocol;
      }

      /**
       * @param protocol the protocol to set
       */
      public void setProtocol(String protocol) {
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
