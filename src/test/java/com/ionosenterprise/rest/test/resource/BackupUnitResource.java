package com.ionosenterprise.rest.test.resource;

import com.ionosenterprise.rest.domain.BackupUnit;

public class BackupUnitResource {

    private static BackupUnit backupUnit;
    private static BackupUnit backupUnitEdited;

    public static BackupUnit getBackupUnit(){
        if (backupUnit == null){
            backupUnit = new BackupUnit();
            backupUnit.getProperties().setName("backupunit01");
            backupUnit.getProperties().setPassword("BackupUnit111");
            backupUnit.getProperties().setEmail("no-reply" + System.currentTimeMillis() + "@example.com");
        }
        return backupUnit;
    }

    public static BackupUnit getBackupUnitEdited() {
        if (backupUnitEdited == null){
            backupUnitEdited = new BackupUnit();
            backupUnitEdited.getProperties().setPassword("BackupUnit222");
            backupUnitEdited.getProperties().setEmail("no-reply" + System.currentTimeMillis() + ".edit@example.com");
        }
        return backupUnitEdited;
    }
}
