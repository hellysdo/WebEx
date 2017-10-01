/*
 * Copyright © 2016 Cisco, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.iservicenode.DB;

import java.util.ArrayList;
import java.util.List;

import org.opendaylight.pojo.LinkInfoObj;
import org.opendaylight.pojo.LinkObjPartOne;
import org.opendaylight.pojo.LinkObjPartTwo;

public class TopologyLinksDataBase {
    private static List<LinkObjPartOne> linkPartOne = new ArrayList<LinkObjPartOne>();
    private static List<LinkObjPartTwo> linkPartTwo = new ArrayList<LinkObjPartTwo>();
    private static List<LinkInfoObj> topologyLinkInfo = new ArrayList<LinkInfoObj>();

    public static List<LinkObjPartOne> getLinkPartOne() {
        return linkPartOne;
    }

    public static void setLinkPartOne(List<LinkObjPartOne> linkPartOne) {
        TopologyLinksDataBase.linkPartOne.addAll(linkPartOne);
    }

    public static List<LinkObjPartTwo> getLinkPartTwo() {
        return linkPartTwo;
    }

    public static void setLinkPartTwo(List<LinkObjPartTwo> linkPartTwo) {
        TopologyLinksDataBase.linkPartTwo = linkPartTwo;
    }

    public static List<LinkInfoObj> getTopologyLinkInfo() {
        return topologyLinkInfo;
    }

    public static void setTopologyLinkInfo(List<LinkInfoObj> topologyLinkInfo) {
        TopologyLinksDataBase.topologyLinkInfo.addAll(topologyLinkInfo);
    }

}
