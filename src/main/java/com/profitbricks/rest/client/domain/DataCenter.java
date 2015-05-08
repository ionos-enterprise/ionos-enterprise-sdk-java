/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.client.domain;

/**
 *
 * @author jasmin.gacic
 */
public class DataCenter {

   private String id;
   private String type;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.id = type;
   }

   /*
   
    {
    "id": "datacenter-id",
    "type": "datacenter",
    "href": "https://api.profitbricks.com/rest/1.0/datacenters/datacenter-id",
    "metadata": {
    "lastModifiedDate": "datacenter-last-modified-date",
    "lastModifiedBy": "datacenter-last-modified-by-user",
    "createdDate": "datacenter-creation-date",
    "createdBy": "datacenter-created-by-user",
    "state": "datacenter-state",
    "etag": "datacenter-etag"
    },
    "properties": {
    "name": "datacenter-name",
    "description": "datacenter-description",
    "location": "datacenter-location",
    "version": datacenter-version
    },
    "entities": {
    "servers": [],
    "volumes": [],
    "loadbalancers": [],
    "lans": []
    }
    }
    */
}
