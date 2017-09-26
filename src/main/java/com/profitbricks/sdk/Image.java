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

package com.profitbricks.sdk;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.Images;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class Image extends ProfitbricksAPIBase {

    public Image() throws Exception {
        super("images", null);
    }

    /**
     * Retrieve a list of images.
     *
     * @return Images object with a list of Images
     */
    public Images getAllImages() throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat(depth), null, Images.class);
    }

    /**
     * Retrieves the attributes of a specific image
     *
     * @param imageId The unique ID of the image.
     * @return Image object with properties and metadata
     */
    public com.profitbricks.rest.domain.Image getImage(String imageId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat("/").concat(imageId).concat(depth), null, com.profitbricks.rest.domain.Image.class);
    }

    /**
     * Deletes a specific image.
     *
     * @param imageId The unique ID of the image.
     */
    public void deleteImage(String imageId) throws RestClientException, IOException {
        client.delete(getUrlBase().concat(resource).concat("/").concat(imageId));
    }

    /**
     * Updates a specific image.
     *
     * @param imageId The unique ID of the image.
     * @return Image object with properties and metadata
     */
    public com.profitbricks.rest.domain.Image updateImage(String imageId, Object object) throws RestClientException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return client.update(getUrlBase().concat(resource).concat("/").concat(imageId), object, com.profitbricks.rest.domain.Image.class, 202);
    }

}
