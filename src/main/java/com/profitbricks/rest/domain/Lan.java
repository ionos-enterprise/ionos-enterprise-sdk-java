/*
 * Copyright (c) 2017, ProfitBricks GmbH
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
 * 4. Neither the name of the ProfitBricks nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY ProfitBricks GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ProfitBricks GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.profitbricks.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class Lan extends ProfitbricksBase {

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
   public class Properties {

      @JsonProperty("public")
      private Boolean isPublic;
      private String name;
      private List<IpFailover> ipFailover;

      /**
       * @return the isPublic
       */
      public boolean isIsPublic() {
         return isPublic;
      }

      /**
       * @param isPublic the isPublic to set
       */
      public void setIsPublic(boolean isPublic) {
         this.isPublic = isPublic;
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

       public List<IpFailover> getIpFailover() {
           return ipFailover;
       }

       public void setIpFailover(List<IpFailover> ipFailover) {
           this.ipFailover = ipFailover;
       }
   }

   @JsonIgnoreProperties(ignoreUnknown = true)
   public class Entities {

      private Nics nics = new Nics();

      /**
       * @return the nics
       */
      public Nics getNics() {
         return nics;
      }

      /**
       * @param nics the nics to set
       */
      public void setNics(Nics nics) {
         this.nics = nics;
      }
   }

   @JsonIgnoreProperties(ignoreUnknown = true)
   public class IpFailover{
       private String Ip;
       private String NicUuid;

       public String getIp() {
           return Ip;
       }

       public void setIp(String ip) {
           Ip = ip;
       }

       public String getNicUuid() {
           return NicUuid;
       }

       public void setNicUuid(String nicUuid) {
           NicUuid = nicUuid;
       }
   }

   private Entities entities = new Entities();

   private Properties properties = new Properties();

   /**
    * @return the entities
    */
   public Entities getEntities() {
      return entities;
   }

   /**
    * @param entities the entities to set
    */
   public void setEntities(Entities entities) {
      this.entities = entities;
   }
}
