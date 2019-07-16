package com.ionosenterprise.rest.test;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.Image;
import com.ionosenterprise.rest.domain.Images;
import com.ionosenterprise.rest.domain.RequestStatus;
import com.ionosenterprise.sdk.IonosEnterpriseApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class BaseTest {

    protected static IonosEnterpriseApi ionosEnterpriseApi;

    static {
        ionosEnterpriseApi = new IonosEnterpriseApi();
        ionosEnterpriseApi.setCredentials(
                System.getenv("IONOS_ENTERPRISE_USERNAME"),
                System.getenv("IONOS_ENTERPRISE_PASSWORD"));

    }

    protected static void waitTillProvisioned(String requestId) throws InterruptedException, RestClientException,
            IOException {

        int counter = 120;
        for (int i = 0; i < counter; i++) {
            RequestStatus request = ionosEnterpriseApi.getRequestApi().getRequestStatus(requestId);
            TimeUnit.SECONDS.sleep(1);
            if (request.getMetadata().getStatus().equals("DONE")) {
                break;
            }
            if (request.getMetadata().getStatus().equals("FAILED")) {
                throw new IOException("The request execution has failed with following message: "
                        + request.getMetadata().getMessage());
            }
        }
    }

    protected static String getImageId() throws RestClientException, IOException {
        Images images = ionosEnterpriseApi.getImageApi().getAllImages();
        for (Image image : images.getItems()) {
            if (image.getProperties().getName().toLowerCase().contains("ubuntu".toLowerCase()) && image.getProperties().getLocation().equals("us/las")
                    && image.getProperties().getIsPublic() && image.getProperties().getImageType().equals("HDD")) {
                return image.getId();
            }
        }
        return "";
    }

    protected static void setIonosEnterpriseApiCredentials(String username, String password) {
        if (ionosEnterpriseApi == null){
            ionosEnterpriseApi = new IonosEnterpriseApi();
        }
        ionosEnterpriseApi.setCredentials(username, password);
    }

    protected static void resetIonosEnterpriseApiCredentials() {
        if (ionosEnterpriseApi == null){
            ionosEnterpriseApi = new IonosEnterpriseApi();
        }
        ionosEnterpriseApi.setCredentials(
                System.getenv("IONOS_ENTERPRISE_USERNAME"),
                System.getenv("IONOS_ENTERPRISE_PASSWORD"));
    }
}
