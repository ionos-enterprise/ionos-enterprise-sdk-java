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
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.IPBlock;
import com.profitbricks.rest.domain.IPBlocks;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jasmin.gacic
 */
public class IPBlockTest {

   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
   static String ipBlockId;

   @BeforeClass
   public static void setUp() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

      profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

      IPBlock ipb = new IPBlock();

      ipb.getProperties().setLocation("us/las");
      List<String> ips = new ArrayList<String>();
      ips.add("123.123.123.123");
      ips.add("123.123.123.124");
     
      // ipb.getProperties().setIps(ips);
      ipb.getProperties().setSize("1");

      IPBlock iPBlock = profitbricksApi.getIpBlockApi().createIPBlock("us/las", "1", ipb);

      assertNotNull(iPBlock);

      ipBlockId = iPBlock.getId();
   }

   @Test
   public void orderdTest() throws RestClientException, IOException {
      getAllIpBlocks();
      getIpBlock();
   }

   public void getAllIpBlocks() throws RestClientException, IOException {
      IPBlocks iPBlocks = profitbricksApi.getIpBlockApi().getAllIPBlocks();
      assertNotNull(iPBlocks);
   }

   public void getIpBlock() throws RestClientException, IOException {
      IPBlock iPBlock = profitbricksApi.getIpBlockApi().getIPBlock(ipBlockId);
      assertNotNull(iPBlock);
   }

   @AfterClass
   public static void cleanUp() throws RestClientException, IOException {
      profitbricksApi.getIpBlockApi().deleteIPBlock(ipBlockId);
   }
}
