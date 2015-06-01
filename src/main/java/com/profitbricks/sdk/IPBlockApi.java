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
package com.profitbricks.sdk;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.IPBlock;
import com.profitbricks.rest.domain.IPBlocks;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author jasmin.gacic
 */
public class IPBlockApi extends ProfitbricksAPIBase {

   public IPBlockApi() {
      super("ipblocks", null);
   }

   public IPBlocks getAllIPBlocks() throws RestClientException, IOException {
      return client.get(urlBase.concat(resource).concat(depth), null, IPBlocks.class);
   }

   public IPBlock getIPBlock(String ipBlockId) throws RestClientException, IOException {
      return client.get(urlBase.concat(resource).concat("/").concat(ipBlockId).concat(depth), null, IPBlock.class);
   }

   public IPBlock createIPBlock(String location, String size, IPBlock ipBlock) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return client.create(urlBase.concat(resource), ipBlock, IPBlock.class, 202);
   }

   public void deleteIPBlock(String ipBlockId) throws RestClientException, IOException {
      client.delete(urlBase.concat(resource).concat("/").concat(ipBlockId), 202);
   }
}
