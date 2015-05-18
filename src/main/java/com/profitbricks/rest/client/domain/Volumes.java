/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Volumes extends ProfitbricksBase{
   
   private List<Volume> items = new ArrayList<Volume>();

   /**
    * @return the items
    */
   public List<Volume> getItems() {
      return items;
   }

   /**
    * @param items the items to set
    */
   public void setItems(List<Volume> items) {
      this.items = items;
   }
}
