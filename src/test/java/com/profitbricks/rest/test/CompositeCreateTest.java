/*
 * Copyright 2017.
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
import com.profitbricks.rest.domain.*;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 *
 * @author jasmin@stackpointcloud.com
 */
public class CompositeCreateTest {

    static ProfitbricksApi profitbricksApi;
    static String dataCenterId;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createDataCenter() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
        String imageId = getImageId();

        DataCenter datacenter = new DataCenter();

        datacenter.getProperties().setName("SDK TEST DC - Composite Data center");
        datacenter.getProperties().setLocation("us/las");
        datacenter.getProperties().setDescription("SDK TEST Description");

        //Add a server
        Server server = new Server();

        server.getProperties().setName("comp test");
        server.getProperties().setCores(1);
        server.getProperties().setRam(1024);

        //Add a volume to the server
        Volume volume = new Volume();
        volume.getProperties().setName("SDK TEST VOLUME - Volume");
        volume.getProperties().setSize(10);
        volume.getProperties().setImage(imageId);
        volume.getProperties().setType("HDD");

        List<String> sshkeys = new ArrayList<String>();
        sshkeys.add("ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDgnV5MOhBqpQLt66Ksdf/VYtmVPUt6epSVxnxrvjayNto5flG2sH4cGqdI2C0NE9/w7BFNdwWqp0mL2kYynC8l+SejW/qjx37hrEBWIXqdTyumchm0LD/7K7P7/kz14IV5NcHjNAsntPgKjx/fzJlbA1VCQYmnOq9RZeKme44rdHYW0BBfgMzekcEbyGTNDGp51NYhVafZLXsF8MzCKlJ+NCPlDqzD6w0fQe/qtMFO8NbFyS9/Lk4prp4HAWEyLSM26w1iLycYpbpWrHw6oc1U7bNIgbsa0ezDu4+OPkxeHz7aG5TeJ/dn0Wftzdfy2sy5PJy5MnYP3RTuROsOv+chu+AshZNNJ9A4ar5gFXSX40sQ0i4GzxZGrsKhW42ZP4sElzV74gEBQ2BOIOJUh4qGRtnjsQCJHBs7DLgpeVeGUq2B7p5zDAlJBGCXiHuTgIM8aVnpdnNrFwmr9SF66iaTrt7x8HinNOCIIztMU15Fk2AYSxSEuju1d3VcPt/d0= spc@spc");
        volume.getProperties().setSshKeys(sshkeys);

        Volumes volumes = new Volumes();
        List<Volume> volumeList = new ArrayList<Volume>();
        volumeList.add(volume);
        volumes.setItems(volumeList);
        server.getEntities().setVolumes(volumes);

        //Add a NIC to the server
        final Nic nic = new Nic();
        nic.getProperties().setName("SDK TEST NIC - Nic");
        nic.getProperties().setLan("1");
        nic.getProperties().setNat(Boolean.FALSE);

        Nics nics = new Nics();
        List<Nic> nicList = new ArrayList<Nic>();
        nicList.add(nic);
        nics.setItems(nicList);
        server.getEntities().setNics(nics);

        Servers servers = new Servers();
        List<Server> serversList = new ArrayList<Server>();
        serversList.add(server);
        servers.setItems(serversList);
        datacenter.getEntities().setServers(servers);

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(datacenter);

        waitTillProvisioned(newDatacenter.getRequestId());

        profitbricksApi.getDataCenter().deleteDataCenter(newDatacenter.getId());
        waitTillProvisioned(newDatacenter.getRequestId());
    }

    public String getImageId() throws RestClientException, IOException {
        Images images = profitbricksApi.getImage().getAllImages();
        for (Image image : images.getItems()) {
            if (image.getProperties().getName().toLowerCase().contains("ubuntu-16".toLowerCase()) && image.getProperties().getLocation().equals("us/las")
                    && image.getProperties().getIsPublic() && image.getProperties().getImageType().equals("HDD")) {
                return image.getId();
            }
        }
        return "";
    }

    public void waitTillProvisioned(String requestId) throws InterruptedException, RestClientException, IOException {
        int counter = 120;
        for (int i = 0; i < counter; i++) {
            profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));
            RequestStatus request = profitbricksApi.getRequest().getRequestStatus(requestId);
            TimeUnit.SECONDS.sleep(1);
            if (request.getMetadata().getStatus().equals("DONE")) {
                break;
            }
            if (request.getMetadata().getStatus().equals("FAILED")) {
                throw new IOException("The request execution has failed with following message: " + request.getMetadata().getMessage());
            }
        }
    }
}
