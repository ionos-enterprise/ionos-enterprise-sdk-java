package com.ionosenterprise.sdk;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.BackupUnits;
import com.ionosenterprise.rest.domain.SingleSignOnUrl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class BackupUnit extends BaseAPI {

    public BackupUnit() throws Exception {
        super("backupunits", "");
    }

    /**
     * Retrieve a list of BackupUnits.
     *
     * @return BackupUnits object with the list of backup units
     */
    public BackupUnits getAllBackupUnits() throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat(depth), null, BackupUnits.class);
    }

    /**
     * Retrieve a specific BackupUnit.
     *
     * @param id he unique ID of the backup unit
     * @return BackupUnit object with properties and metadata
     */
    public com.ionosenterprise.rest.domain.BackupUnit getBackupUnit(String id) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat("/").concat(id).concat(depth), null,
                com.ionosenterprise.rest.domain.BackupUnit.class);
    }

    /**
     * Retrieve a SSO URL send a GET request to.
     *
     * @param id The unique ID of the backup unit
     * @return SingleSignOnUrl object containing the ssoUrl field
     */
    public SingleSignOnUrl getSSOUrl(String id) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat("/").concat(id).concat("/ssourl"), null,
                SingleSignOnUrl.class);
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

        return client.create(getUrlBase().concat(resource), backupUnit,
                com.ionosenterprise.rest.domain.BackupUnit.class, 202);
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

        return client.update(getUrlBase().concat(resource).concat("/").concat(id), backupUnitProperties,
                com.ionosenterprise.rest.domain.BackupUnit.class, 202);
    }

    /**
     * Delete a specific backup unit.
     *
     * @param id The unique ID of the backup unit.
     */
    public void deleteBackupUnit(String id) throws RestClientException, IOException {
        client.delete(getUrlBase().concat(resource).concat("/").concat(id), 202);
    }
}
