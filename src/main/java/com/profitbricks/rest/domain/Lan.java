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
public class Lan extends ProfitbricksBase {

   private Boolean isPublic;
   private List<Nic> nics = new ArrayList<Nic>();

   /**
    * @return the isPublic
    */
   public Boolean getIsPublic() {
      return isPublic;
   }

   /**
    * @param isPublic the isPublic to set
    */
   public void setIsPublic(Boolean isPublic) {
      this.isPublic = isPublic;
   }

   /**
    * @return the nics
    */
   public List<Nic> getNics() {
      return nics;
   }

   /**
    * @param nics the nics to set
    */
   public void setNics(List<Nic> nics) {
      this.nics = nics;
   }
}
