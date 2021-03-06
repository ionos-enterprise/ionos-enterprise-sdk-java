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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

 public class IPBlock extends BaseResource {

     private Properties properties = new Properties();

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

     @JsonIgnoreProperties(ignoreUnknown = true)
     @JsonInclude(JsonInclude.Include.NON_NULL)
     public static class Properties {

         private List<String> ips;
         private String location;
         private Integer size;
         private String name;
         private List<IPConsumer> ipConsumers;

         /**
          * @return the ips
          */
         public List<String> getIps() {
             return ips;
         }

         /**
          * @param ips the ips to set
          */
         public void setIps(List<String> ips) {
             if (ips == null) {
                 ips = new ArrayList<>();
             }
             this.ips = ips;
         }

         /**
          * @return the location
          */
         public String getLocation() {
             return location;
         }

         /**
          * @param location the location to set
          */
         public void setLocation(String location) {
             this.location = location;
         }

         /**
          * @return the size
          */
         public Integer getSize() {
             return size;
         }

         /**
          * @param size the size to set
          */
         public void setSize(Integer size) {
             this.size = size;
         }

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
          * @return the ipConsumers
          */
         public List<IPConsumer> getIpConsumers() {
             return ipConsumers;
         }

         /**
          * @param ipConsumers the ipConsumers to be set
          */
         public void setIpConsumers(List<IPConsumer> ipConsumers) {
             this.ipConsumers = ipConsumers;
         }
     }
 }
