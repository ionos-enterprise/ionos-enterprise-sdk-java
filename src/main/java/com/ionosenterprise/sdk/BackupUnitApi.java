package com.ionosenterprise.sdk;

import com.ionosenterprise.rest.client.RestClient;
import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.BackupUnits;
import com.ionosenterprise.rest.domain.SingleSignOnUrl;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class BackupUnitApi extends AbstractBaseApi {

    public BackupUnitApi(RestClient client) {
        super(client);
    }

    protected String getPathFormat() {
        return "backupunits";
    }

    /**
     * Retrieve a list of BackupUnits.
     *
     * @return BackupUnits object with the list of backup units
     */
    public BackupUnits getAllBackupUnits() throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().withDepth().build(), Collections.EMPTY_MAP, BackupUnits.class);
    }

    /**
     * Retrieve a specific BackupUnit.
     *
     * @param id he unique ID of the backup unit
     * @return BackupUnit object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.BackupUnit getBackupUnit(String id) throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().appendPathSegment(id).withDepth().build(), Collections.EMPTY_MAP,
                com.ionosenterprise.rest.domain.BackupUnit.class);
    }

    /**
     * Retrieve a SSO URL send a GET request to.
     *
     * @param id The unique ID of the backup unit
     * @return SingleSignOnUrl object containing the ssoUrl field
     */
    public SingleSignOnUrl getSSOUrl(String id) throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().appendPathSegment(id).appendPathSegment("/ssourl").build(),
                Collections.EMPTY_MAP, SingleSignOnUrl.class);
    }

    /**
     * Create a single backup unit.
     *
     * @param backupUnit object has the following properties:
     * <br>
     * name = The name of the data center.
     * <br>
     * email = The e-mail address assigned to the backup unit.
     * <br>
     * password = Alphanumeric password assigned to the backup unit.
     * @return BackupUnit object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.BackupUnit createBackupUnit(
            com.ionosenterprise.rest.domain.BackupUnit backupUnit) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException, RestClientException, IOException {

        return client.create(getResourcePathBuilder().build(), backupUnit,
                com.ionosenterprise.rest.domain.BackupUnit.class, HttpStatus.SC_ACCEPTED);
    }

    /**
     * Update a specific backup unit.
     *
     * @param id The unique ID of the backup unit.
     * @param backupUnitProperties object has the following properties:
     * <br>
     * email = The e-mail address assigned to the backup unit.
     * <br>
     * password = Alphanumeric password assigned to the backup unit.
     * @return BackupUnit object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.BackupUnit updateBackupUnit(String id,
            com.ionosenterprise.rest.domain.BackupUnit.Properties backupUnitProperties) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException, RestClientException, IOException {

        return client.update(getResourcePathBuilder().appendPathSegment(id).build(), backupUnitProperties,
                com.ionosenterprise.rest.domain.BackupUnit.class, HttpStatus.SC_ACCEPTED);
    }

    /**
     * Delete a specific backup unit.
     *
     * @param id The unique ID of the backup unit.
     */
    public void deleteBackupUnit(String id) throws RestClientException, IOException {
        client.delete(getResourcePathBuilder().appendPathSegment(id).build(), HttpStatus.SC_ACCEPTED);
    }
}
