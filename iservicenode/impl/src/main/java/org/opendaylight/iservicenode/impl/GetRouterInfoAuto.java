/*
 * Copyright © 2016 Cisco, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.iservicenode.impl;

import java.util.HashSet;
import java.util.Set;

import org.opendaylight.iservicenode.DB.IpToInterfacesInfo;
import org.opendaylight.iservicenode.DB.TopologyNodesDataBase;
import org.opendaylight.pojo.InterfacesObjInfo;

public class GetRouterInfoAuto implements Runnable {

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            try {

                // get the update portInfo
                for (String nodeIpAddress : TopologyNodesDataBase.getTopologyIpInfo()) {

                    Thread.sleep(8 * 1000);
                    try {
                        updateNodeAndLinkInfo(nodeIpAddress);
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                continue;
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }

        }
    }

    public void updateNodeAndLinkInfo(String nodeIpAddress) {

        String portarg = new String(
                "python /home/odl/developerProject/pythonscript/portToMask.py" + " " + nodeIpAddress);
        HandlePortInfo portInfo = HandlePortInfo.getInstance();
        Set<InterfacesObjInfo> intObjInfo_update = new HashSet<InterfacesObjInfo>();
        intObjInfo_update = portInfo.execute(portarg);
        if (intObjInfo_update != null) {
            if (!(intObjInfo_update.equals(IpToInterfacesInfo.getIpToPort().get(nodeIpAddress)))) {
                IpToInterfacesInfo.getIpToPort().put(nodeIpAddress, intObjInfo_update);
                OperationalDataStore operaData = OperationalDataStore.getInstance();
                operaData.initOperational();
            }
        } /*
           * else {
           * TopologyNodesDataBase.getTopologyIpInfo().remove(nodeIpAddress);
           * TopologyNodesDataBase.getTopologyNodeInfo().remove(nodeIpAddress);
           * for (String ipAddress : TopologyNodesDataBase.getTopologyIpInfo())
           * { OperationalDataStore operaData =
           * OperationalDataStore.getInstance();
           * operaData.getNodeInfoAndStore(ipAddress);
           * operaData.getInterfacesInfoAndStore(ipAddress);
           * operaData.getLinkInfoAndFilter(ipAddress); } }
           */

    }

}
