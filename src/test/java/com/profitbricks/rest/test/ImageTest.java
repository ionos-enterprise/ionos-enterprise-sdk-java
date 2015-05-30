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
import com.profitbricks.rest.domain.Image;
import com.profitbricks.rest.domain.Images;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author jasmin.gacic
 */
public class ImageTest {

   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
   private static String imageId;

   @Test
   public void getAllImages() throws RestClientException, IOException {
      Images images = profitbricksApi.getImageApi().getAllImages();
      assertNotNull(images);
      imageId = images.getItems().get(0).getId();
   }


}
