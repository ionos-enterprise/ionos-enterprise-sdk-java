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
import com.profitbricks.rest.domain.Image;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.Images;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author jasmin.gacic
 */
public class ImageApi extends ProfitbricksAPIBase {

   public ImageApi() {
      super("images", null);
   }

   public Images getAllImages() throws RestClientException, IOException {
      return client.get(getUrlBase().concat(resource).concat(depth), null, Images.class);
   }

   public Image getImage(String imageId) throws RestClientException, IOException {
      return client.get(getUrlBase().concat(resource).concat("/").concat(imageId).concat(depth), null, Image.class);
   }

   public void deleteImage(String imageId) throws RestClientException, IOException {
      client.delete(getUrlBase().concat(resource).concat("/").concat(imageId));
   }

   public Image updateImage(String imageId, PBObject object) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      return client.update(getUrlBase().concat(resource).concat("/").concat(imageId), object, Image.class, 202);
   }

}
