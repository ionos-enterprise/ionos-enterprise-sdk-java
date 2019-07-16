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

import com.ionosenterprise.rest.domain.AvailabilityZone;
import com.ionosenterprise.rest.domain.Bus;
import com.ionosenterprise.rest.domain.Volume;

import java.util.ArrayList;
import java.util.List;

public class VolumeResource {
    private static Volume volume;
    private static Volume badVolume;
    private static Volume editVolume;
    private static Volume editLvsVolume;

    public static Volume getVolume() {
        if (volume == null) {
            volume = new Volume();
            volume.getProperties().setName("Java SDK Test");
            volume.getProperties().setSize(12);
            volume.getProperties().setBus(Bus.VIRTIO);
            volume.getProperties().setType("HDD");
            volume.getProperties().setAvailabilityZone(AvailabilityZone.ZONE_1);
            List<String> sshkeys = new ArrayList<String>();
            sshkeys.add("ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDgnV5MWhBqpQLt66KGlMKi/VYtmVPUt6epSVxnxrvjayNto5flG2sH4cGqdI2C0NE9/w7BFNdwWqp0mL2kYynC8l+SejW/qjx37hrEBWIXqdTyumchm0LD/7K7P7/kz14IV5NcHjNAsntPgKjx/fzJlbA1VCQYmnOq9RZeKme44rdHYW0BBfgMzekcEbyGTNDGp51NYhVafZLXsF8MzCKlJ+NCPlDqzD6w0fQe/qtMFO8NbFyS9/Lk4prp4HAWEyLSM26w1iLycYpbpWrHw6oc1U7bNIgbsa0ezDu4+OPkxeHz7aG5TeJ/dn0Wftzdfy2sy5PJy5MnYP3RTuROsOv+chu+AshZNNJ9A4ar5gFXSX40sQ0i4GzxZGrsKhW42ZP4sElzV74gEBQ2BOIOJUh4qGRtnjsQCJHBs7DLgpeVeGUq2B7p5zDAlJBGCXiHuTgIM8aVnpdnNrFwmr9SF66iaTrt7x8HinNOCIIztMU15Fk2AYSxSEuju1d3VcPt/d0= spc@spc");
            volume.getProperties().setSshKeys(sshkeys);
        }
        return volume;
    }

    public static Volume getEditVolume() {
        if (editVolume == null) {
            editVolume = new Volume();
            editVolume.getProperties().setName("Java SDK Test - RENAME");
            editVolume.getProperties().setSize(15);
        }
        return editVolume;
    }

    public static Volume getEditLvsVolume() {
        if (editLvsVolume == null) {
            editLvsVolume = new Volume();
            editLvsVolume.getProperties().setName("Java SDK Test - EDIT LVS");
            editLvsVolume.getProperties().setCpuHotPlug(true);
            editLvsVolume.getProperties().setRamHotPlug(true);
            editLvsVolume.getProperties().setNicHotPlug(true);
            editLvsVolume.getProperties().setNicHotUnplug(true);
            editLvsVolume.getProperties().setDiscVirtioHotPlug(true);
            editLvsVolume.getProperties().setDiscVirtioHotUnplug(true);
        }
        return editLvsVolume;
    }

    public static Volume getBadVolume() {
        if (badVolume == null) {
            badVolume = new Volume();
            badVolume.getProperties().setName("Java SDK Test");
        }
        return badVolume;
    }
}
