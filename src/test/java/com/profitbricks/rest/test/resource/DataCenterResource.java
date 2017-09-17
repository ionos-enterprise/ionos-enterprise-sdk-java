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
package com.profitbricks.rest.test.resource;

import com.profitbricks.rest.domain.DataCenter;

/**
 *
 * @author denis@stackpointcloud.com
 */
public class DataCenterResource {

    private static DataCenter dataCenter;
    private static DataCenter compositeDataCenter;
    private static DataCenter badDataCenter;
    private static DataCenter editDataCenter;

    public static DataCenter getDataCenter(){
        if(dataCenter == null){
            dataCenter = new DataCenter();
            dataCenter.getProperties().setName("Java SDK Test");
            dataCenter.getProperties().setDescription("Java SDK test datacenter");
            dataCenter.getProperties().setLocation("us/las");
        }
        return dataCenter;
    }

    public static DataCenter getCompositeDataCenter(){
        if(compositeDataCenter == null){
            compositeDataCenter = new DataCenter();
            compositeDataCenter.getProperties().setName("Java SDK Test Composite");
            compositeDataCenter.getProperties().setDescription("Java SDK test composite datacenter");
            compositeDataCenter.getProperties().setLocation("us/las");
        }
        return compositeDataCenter;
    }

    public static DataCenter getEditDataCenter(){
        if(editDataCenter == null){
            editDataCenter = new DataCenter();
            editDataCenter.getProperties().setName("Java SDK Test - RENAME");
        }
        return editDataCenter;
    }

    public static DataCenter getBadDataCenter(){
        if(badDataCenter == null){
            badDataCenter = new DataCenter();
            badDataCenter.getProperties().setName("Java SDK Test");
        }

        return badDataCenter;
    }
}
