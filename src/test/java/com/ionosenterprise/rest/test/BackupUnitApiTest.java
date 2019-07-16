package com.ionosenterprise.rest.test;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.BackupUnit;
import com.ionosenterprise.rest.domain.BackupUnits;
import com.ionosenterprise.rest.domain.SingleSignOnUrl;
import com.ionosenterprise.rest.test.resource.BackupUnitResource;
import com.ionosenterprise.rest.test.resource.CommonResource;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BackupUnitApiTest extends BaseTest {

    private static String backupUnitId;

    @BeforeClass
    public static void t1_testCreateBackupUnit() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException, InterruptedException {

        BackupUnit backupUnit = BackupUnitResource.getBackupUnit();
        BackupUnit newBackupUnit = ionosEnterpriseApi.getBackupUnitApi().createBackupUnit(backupUnit);
        assertNotNull(newBackupUnit);
        assertEquals(newBackupUnit.getProperties().getName(), backupUnit.getProperties().getName());
        assertEquals(newBackupUnit.getProperties().getEmail(), backupUnit.getProperties().getEmail());
        assertEquals(newBackupUnit.getProperties().getPassword(), null);
        backupUnitId = newBackupUnit.getId();
        waitTillProvisioned(newBackupUnit.getRequestId());
    }

    @Test
    public void t2_testGetAllBackupUnits() throws RestClientException, IOException {
        BackupUnits backupUnits = ionosEnterpriseApi.getBackupUnitApi().getAllBackupUnits();
        assertNotNull(backupUnits);
        assertTrue(backupUnits.getItems().size() > 0);
    }

    @Test
    public void t3_testGetBackupUnit() throws RestClientException, IOException {
        BackupUnit backupUnit = ionosEnterpriseApi.getBackupUnitApi().getBackupUnit(backupUnitId);
        assertNotNull(backupUnit);
        BackupUnit.Properties properties = BackupUnitResource.getBackupUnit().getProperties();
        assertEquals(backupUnit.getProperties().getName(), properties.getName());
        assertEquals(backupUnit.getProperties().getEmail(), properties.getEmail());
        assertEquals(backupUnit.getProperties().getPassword(), null);
    }

    @Test
    public void t4_testGetBackupUnitFail() throws IOException {
        try {
            ionosEnterpriseApi.getBackupUnitApi().getBackupUnit(CommonResource.getBadId());
        }catch (RestClientException ex){
            assertEquals(ex.response().getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }

    @Test
    public void t5_testUpdateBackupUnit() throws NoSuchMethodException, IOException, IllegalAccessException,
            RestClientException, InvocationTargetException {

        BackupUnit.Properties properties = BackupUnitResource.getBackupUnitEdited().getProperties();
        BackupUnit backupUnit = ionosEnterpriseApi.getBackupUnitApi().updateBackupUnit(backupUnitId, properties);
        assertNotNull(backupUnit);
        assertEquals(backupUnit.getProperties().getEmail(), properties.getEmail());
        assertEquals(backupUnit.getProperties().getPassword(), null);
    }

    @Test
    public void t6_testGetSSOUrl() throws RestClientException, IOException {
        SingleSignOnUrl ssoUrl = ionosEnterpriseApi.getBackupUnitApi().getSSOUrl(backupUnitId);
        assertNotNull(ssoUrl);
        assertNotNull(ssoUrl.getSsoUrl());
    }

    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        ionosEnterpriseApi.getBackupUnitApi().deleteBackupUnit(backupUnitId);
    }
}
