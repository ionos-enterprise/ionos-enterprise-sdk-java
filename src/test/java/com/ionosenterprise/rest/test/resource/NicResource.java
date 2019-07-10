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

import com.ionosenterprise.rest.domain.IPBlock;
import com.ionosenterprise.rest.domain.Nic;

/**
 * @author denis@stackpointcloud.com
 */
public class NicResource {
    private static Nic nic;
    private static Nic badNic;
    private static Nic editNic;
    private static Nic nicForIPBlockTests;

    public static Nic getNic() {
        if (nic == null) {
            nic = new Nic();
            nic.getProperties().setName("Java SDK Test");
            nic.getProperties().setDhcp(true);
            nic.getProperties().setLan("1");
            nic.getProperties().setNat(Boolean.FALSE);
        }
        return nic;
    }

    public static Nic getEditNic() {
        if (editNic == null) {
            editNic = new Nic();
            editNic.getProperties().setName("Java SDK Test - RENAME");
        }
        return editNic;
    }

    public static Nic getBadNic() {
        if (badNic == null) {
            badNic = new Nic();
            badNic.getProperties().setName("Java SDK Test");
        }
        return badNic;
    }

    public static Nic getNicForLanIdAndIPBlock(String lanId, IPBlock iPBlock) {
        if (nicForIPBlockTests == null){
            nicForIPBlockTests = new Nic();
            nicForIPBlockTests.getProperties().setName("SDK TEST NIC");
            nicForIPBlockTests.getProperties().setLan(lanId);
            nicForIPBlockTests.getProperties().setNat(Boolean.FALSE);
            nicForIPBlockTests.getProperties().setIps(iPBlock.getProperties().getIps());
            nicForIPBlockTests.getEntities().setFirewallrules(null);
        }
        return nicForIPBlockTests;
    }
}
