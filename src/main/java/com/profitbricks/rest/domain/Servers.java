/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */

public class Servers  extends ProfitbricksBase{

   private List<Server> items = new ArrayList<Server>();


   /**
    * @return the items
    */
   public List<Server> getItems() {
      return items;
   }

   /**
    * @param items the items to set
    */
   public void setItems(List<Server> items) {
      this.items = items;
   }

}
