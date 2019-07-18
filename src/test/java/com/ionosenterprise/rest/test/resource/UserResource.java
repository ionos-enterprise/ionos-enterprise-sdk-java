/*
 * Copyright (c) 2017, 1&1 IONOS Cloud GmbH
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
 * 4. Neither the name of the 1&1 IONOS Cloud nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY 1&1 IONOS Cloud GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL 1&1 IONOS Cloud GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.ionosenterprise.rest.test.resource;

import com.ionosenterprise.rest.domain.User;

public class UserResource {
    private static User user;
    private static User editUser;
    private static User userOfGroup;
    private static User contractUserWithoutAdminPriv;

    public static User getUser() {
        if (user == null) {
            user = new User();
            user.getProperties().setFirstname("John");
            user.getProperties().setLastname("Doe");
            user.getProperties().setPassword("secretpassword111");
            user.getProperties().setAdministrator(true);
        }

        user.getProperties().setEmail("no-reply" + System.currentTimeMillis() + "@example.com");
        return user;
    }

    public static User getEditUser() {
        if (editUser == null) {
            editUser = new User();
            editUser.getProperties().setFirstname("Jane");
            editUser.getProperties().setLastname("Doe");
            editUser.getProperties().setAdministrator(false);
            editUser.getProperties().setForceSecAuth(false);
        }

        editUser.getProperties().setEmail("no-reply" + System.currentTimeMillis() + ".edit@example.com");
        return editUser;
    }

    public static User getUserOfGroup() {
        if (userOfGroup == null) {
            userOfGroup = new User();
            userOfGroup.getProperties().setFirstname("User");
            userOfGroup.getProperties().setLastname("OfGroup");
            userOfGroup.getProperties().setPassword("secretpassword222");
            userOfGroup.getProperties().setAdministrator(false);
        }

        userOfGroup.getProperties().setEmail("no-reply" + System.currentTimeMillis() + ".of_group@example.com");
        return userOfGroup;
    }

    public static User getContractUserWithoutAdminPriv() {
        if (contractUserWithoutAdminPriv == null) {
            contractUserWithoutAdminPriv = new User();
            contractUserWithoutAdminPriv.getProperties().setFirstname("ContractUser");
            contractUserWithoutAdminPriv.getProperties().setLastname("WithoutAdminPriv");
            contractUserWithoutAdminPriv.getProperties().setPassword("secretpassword333");
        }

        contractUserWithoutAdminPriv.getProperties().setEmail(
                "no-reply" + System.currentTimeMillis() + ".without_admin_priv@example.com");
        return contractUserWithoutAdminPriv;
    }
}
