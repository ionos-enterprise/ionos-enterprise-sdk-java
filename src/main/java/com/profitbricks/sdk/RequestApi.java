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
import com.profitbricks.rest.domain.Request;
import com.profitbricks.sdk.ProfitbricksAPIBase;
import java.io.IOException;

/**
 *
 * @author jasmin.gacic
 */
public class RequestApi extends ProfitbricksAPIBase {

   public RequestApi() {
      super("status", "requests");
   }

   public Request getRequest(String url) throws RestClientException, IOException {
      return client.get(url, null, Request.class);
   }

}
