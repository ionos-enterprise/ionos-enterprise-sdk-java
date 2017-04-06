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
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.IPBlock;
import com.profitbricks.rest.domain.IPBlocks;
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
 * @author jasmin@stackpointcloud.com
 */
public class IPBlockTest {

   static ProfitbricksApi profitbricksApi;

   static {
      try {
         profitbricksApi = new ProfitbricksApi();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }   static String ipBlockId;

   @BeforeClass
   public static void setUp() throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

      profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

      IPBlock ipb = new IPBlock();

      ipb.getProperties().setLocation("us/las");
      List<String> ips = new ArrayList<String>();
      ips.add("123.123.123.123");
      ips.add("123.123.123.124");
     
      // ipb.getProperties().setIps(ips);
      ipb.getProperties().setSize(1);

      IPBlock iPBlock = profitbricksApi.getIpBlock().createIPBlock(ipb);

      assertNotNull(iPBlock);

      ipBlockId = iPBlock.getId();
   }

   @Test
   public void orderdTest() throws RestClientException, IOException {
      getAllIpBlocks();
      getIpBlock();
   }

   public void getAllIpBlocks() throws RestClientException, IOException {
      IPBlocks iPBlocks = profitbricksApi.getIpBlock().getAllIPBlocks();
      assertNotNull(iPBlocks);
   }

   public void getIpBlock() throws RestClientException, IOException {
      IPBlock iPBlock = profitbricksApi.getIpBlock().getIPBlock(ipBlockId);
      assertNotNull(iPBlock);
   }

   @AfterClass
   public static void cleanUp() throws RestClientException, IOException {
      profitbricksApi.getIpBlock().deleteIPBlock(ipBlockId);
   }
}
