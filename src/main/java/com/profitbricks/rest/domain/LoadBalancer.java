/*
 * Copyright 2015.
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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class LoadBalancer extends ProfitbricksBase {

   private String ip;
   private boolean dhcp;
   private List<Nic> balancednics = new ArrayList<Nic>();

   /**
    * @return the ip
    */
   public String getIp() {
      return ip;
   }

   /**
    * @param ip the ip to set
    */
   public void setIp(String ip) {
      this.ip = ip;
   }

   /**
    * @return the dhcp
    */
   public boolean isDhcp() {
      return dhcp;
   }

   /**
    * @param dhcp the dhcp to set
    */
   public void setDhcp(boolean dhcp) {
      this.dhcp = dhcp;
   }

   /**
    * @return the balancednics
    */
   public List<Nic> getBalancednics() {
      return balancednics;
   }

   /**
    * @param balancednics the balancednics to set
    */
   public void setBalancednics(List<Nic> balancednics) {
      this.balancednics = balancednics;
   }
}
