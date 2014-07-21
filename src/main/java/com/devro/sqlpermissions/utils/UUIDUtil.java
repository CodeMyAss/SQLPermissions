package com.devro.sqlpermissions.utils;

import java.util.UUID;

/**
 * Copyright DevRo_ (c) 2014. All Rights Reserved.
 * Programmed by: DevRo_ (Erik Rosemberg)
 * Creation Date: 18, 07, 2014
 * Programmed for the SQLPermissions project.
 */
public class UUIDUtil {
    public static UUID getUUIDOf(String name) {
        try {
            return UUIDFetcher.getUUIDOf(name);
        } catch (Exception ea) {
            ea.printStackTrace();
        }

        return null;
    }
}
