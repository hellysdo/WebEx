/*
 * Copyright © 2016 Cisco, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.iservicenode.impl;

import java.util.concurrent.Future;

import org.opendaylight.iservicenode.DB.TopologyNodesDataBase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.AddNodeInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.AddNodeOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.AddNodeOutputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.IservicenodeService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;

public class IservicenodeImpl implements IservicenodeService {
    public static Boolean threadIsUp = false;

    @Override
    public Future<RpcResult<AddNodeOutput>> addNode(AddNodeInput input) {
        // TODO Auto-generated method stub
        if (!TopologyNodesDataBase.getTopologyIpInfo().contains(input.getNodeIpAddress())) {
            TopologyNodesDataBase.getTopologyIpInfo().add(input.getNodeIpAddress());
            // get the node info and store in the database
            OperationalDataStore operaData = OperationalDataStore.getInstance();
            operaData.getNodeInfoAndStore(input.getNodeIpAddress());
            operaData.getInterfacesInfoAndStore(input.getNodeIpAddress());
            operaData.getLinkInfoAndFilter(input.getNodeIpAddress());
            AddNodeOutputBuilder addnodeBuilder = new AddNodeOutputBuilder();
            addnodeBuilder.setResponse("Added to Map successed !");
            operaData.initOperational();
            // start the thread
            if (!threadIsUp) {
                GetRouterInfoAuto getRouterInfo = new GetRouterInfoAuto();
                Thread t = new Thread(getRouterInfo);
                t.start();
            }
            return RpcResultBuilder.success(addnodeBuilder.build()).buildFuture();
        } else {
            AddNodeOutputBuilder addnodeBuilder = new AddNodeOutputBuilder();
            addnodeBuilder.setResponse("Error: Node Exists !");
            return RpcResultBuilder.success(addnodeBuilder.build()).buildFuture();
        }
    }

}
