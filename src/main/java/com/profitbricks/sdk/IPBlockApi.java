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
import com.profitbricks.rest.domain.Helper;
import com.profitbricks.rest.domain.IPBlock;
import com.profitbricks.rest.domain.raw.IPBlockRaw;
import com.profitbricks.rest.domain.raw.IPBlocksRaw;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author jasmin.gacic
 */
public class IPBlockApi extends ProfitbricksAPIBase {

   public IPBlockApi() {
      super("ipblocks", null);
   }

   public List<IPBlock> getAllIPBlocks() throws RestClientException, IOException {
      return Helper.convertIPBlocks(client.get(getUrlBase().concat(resource).concat(depth), null, IPBlocksRaw.class));
   }

   public IPBlock getIPBlock(String ipBlockId) throws RestClientException, IOException {
      return Helper.convertIPBlock(client.get(getUrlBase().concat(resource).concat("/").concat(ipBlockId).concat(depth), null, IPBlockRaw.class));
   }

   public IPBlock createIPBlock(String location, String size, IPBlockRaw ipBlock) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return Helper.convertIPBlock(client.create(getUrlBase().concat(resource), ipBlock, IPBlockRaw.class, 202));
   }

   public void deleteIPBlock(String ipBlockId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat(resource).concat("/").concat(ipBlockId), 202);
   }
}
