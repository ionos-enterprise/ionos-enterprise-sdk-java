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
public class CDRoms extends ProfitbricksBase {

   private List<CDRom> items = new ArrayList<CDRom>();

   /**
    * @return the items
    */
   public List<CDRom> getItems() {
      return items;
   }

   /**
    * @param items the items to set
    */
   public void setItems(List<CDRom> items) {
      this.items = items;
   }
}
