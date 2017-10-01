/*
 * Copyright © 2016 Cisco, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.iservicenode.impl;

import java.util.List;

import org.opendaylight.iservicenode.DB.TopologyLinksDataBase;
import org.opendaylight.pojo.LinkObjPartOne;

public class HandleLinkInfo {

    private static HandleLinkInfo instce_link;

    private HandleLinkInfo() {

    }

    public static HandleLinkInfo getInstance() {
        if (instce_link == null) {
            instce_link = new HandleLinkInfo();
            return instce_link;
        } else {
            return instce_link;
        }
    }

    public void handleLink(List<LinkObjPartOne> linkdata) {
        List<LinkObjPartOne> listObjPartOne = TopologyLinksDataBase.getLinkPartOne();
        if (listObjPartOne != null && listObjPartOne.size() > 0) {
            for (LinkObjPartOne a : linkdata) {
                if (!listObjPartOne.contains(a)) {
                    listObjPartOne.add(a);
                }
                /*
                 * for (LinkObjPartOne b : listObjPartOne) { if
                 * ((a.getStartPortID().equals(b.getStartPortID()) &&
                 * a.getEndPortID().equals(b.getEndPortID())) ||
                 * (a.getStartPortID().equals(b.getEndPortID()) &&
                 * a.getEndPortID().equals(b.getStartPortID()))) {
                 *
                 * } else { listObjPartOne.add(a); } }
                 */
            }
        } else {
            // add to the topology
            listObjPartOne.addAll(linkdata);
        }
    }

}
