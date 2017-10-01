/*
 * Copyright © 2016 Cisco, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.iservicenode.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.iservicenode.DB.HostNameToIpAddressMapping;
import org.opendaylight.iservicenode.DB.IpToInterfacesInfo;
import org.opendaylight.iservicenode.DB.TopologyLinksDataBase;
import org.opendaylight.iservicenode.DB.TopologyNodesDataBase;
import org.opendaylight.pojo.InterfacesObjInfo;
import org.opendaylight.pojo.LinkInfoObj;
import org.opendaylight.pojo.LinkObjPartOne;
import org.opendaylight.pojo.NodeInfoObj;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.GetTopology;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.GetTopologyBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.IpAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.Ipv4Address;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.links.Link;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.links.LinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.nodes.NodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.nodes.node.CustomParam;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.nodes.node.CustomParamBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.nodes.node.Tags;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.nodes.node.TagsBuilder;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

public class OperationalDataStore implements DataChangeListener {

    private DataBroker dataService;
    private ListenerRegistration<DataChangeListener> dcReg;
    private static final Logger LOG = LoggerFactory.getLogger(OperationalDataStore.class);
    public static final InstanceIdentifier<GetTopology> GET_TOPO_IID = InstanceIdentifier.builder(GetTopology.class)
            .build();

    private OperationalDataStore() {

    }

    private static OperationalDataStore operationalInstance = null;

    public static OperationalDataStore getInstance() {
        if (operationalInstance == null) {
            operationalInstance = new OperationalDataStore();
            return operationalInstance;
        } else {
            return operationalInstance;
        }
    }

    public void initOperational() {
        // get node IpAddress
        int count = 47065;
        List<String> nodeDB = TopologyNodesDataBase.getTopologyIpInfo();
        List<LinkInfoObj> linklistInfo = new ArrayList<LinkInfoObj>();
        GetTopologyBuilder getTopo = new GetTopologyBuilder();
        List<Node> nodelist = new ArrayList<Node>();
        List<Link> linklist = new ArrayList<Link>();

        /*
         * if (nodeDB != null) { Iterator<String> iter = nodeDB.iterator();
         * while (iter.hasNext()) { String nodeIpAddress = iter.next(); String a
         * = nodeIpAddress; } }
         */
        for (int i = 0; i < nodeDB.size(); i++) {
            String nodeIpAddress = nodeDB.get(i);
            NodeInfoObj nodeInfo = TopologyNodesDataBase.getTopologyNodeInfo().get(nodeIpAddress);

            IpAddress deviceIP = new IpAddress(new Ipv4Address(nodeIpAddress));
            NodeBuilder nodeBuilder = new NodeBuilder();
            Tags tag = new TagsBuilder().build();
            List<Tags> tags = new ArrayList<Tags>();
            tags.add(tag);
            CustomParam custom = new CustomParamBuilder().build();
            Node node = nodeBuilder.setDeviceType(nodeInfo.getDeviceType()).setLabel(nodeInfo.getLabel())
                    .setIp(deviceIP).setSoftwareVersion(nodeInfo.getVersion()).setNodeType("device")
                    .setFamily("Routers").setPlatformId(nodeInfo.getPlatformID()).setTags(tags).setRole("ACCESS")
                    .setRoleSource("AUTO").setCustomParam(custom).setId("0436bd48-ba60-4165-9d13-522b44c53651").build();

            nodelist.add(node);
        }

        linklistInfo = this.getLinkInfo();
        TopologyLinksDataBase.setTopologyLinkInfo(linklistInfo);
        for (LinkInfoObj linkInfoObj : linklistInfo) {
            String sourceIP = HostNameToIpAddressMapping.getHostToIp().get(linkInfoObj.getHostName());
            String targetIP = HostNameToIpAddressMapping.getHostToIp().get(linkInfoObj.getDeviceID());
            String startPortName = linkInfoObj.getStartPortName();
            String endPortName = linkInfoObj.getEndPortName();
            Long startPortSpeed = Long.parseLong(linkInfoObj.getStartPortSpeed());
            Long endPortSpeed = Long.parseLong(linkInfoObj.getEndPortSpeed());
            String linkStatus = linkInfoObj.getLinkstatus();
            IpAddress startPortIP = new IpAddress(new Ipv4Address(linkInfoObj.getStartPortIpAddress()));
            IpAddress startPortMask = new IpAddress(new Ipv4Address(linkInfoObj.getStartPortIPMask()));
            IpAddress endPortIP = new IpAddress(new Ipv4Address(linkInfoObj.getEndPortIpAddress()));
            IpAddress endPortMask = new IpAddress(new Ipv4Address(linkInfoObj.getEndPortIpMask()));
            LinkBuilder linkBuilder = new LinkBuilder();
            Link link = linkBuilder.setSource(sourceIP).setStartPortID("e13a83f3-102e-4fb1-a0a5-50d9db0770ab")
                    .setStartPortName(startPortName).setStartPortIpv4Address(startPortIP)
                    .setStartPortIpv4Mask(startPortMask).setStartPortSpeed(startPortSpeed).setTarget(targetIP)
                    .setEndPortID("8089534d-ac86-46e3-9634-f676e2456c09").setEndPortName(endPortName)
                    .setEndPortIpv4Address(endPortIP).setEndPortIpv4Mask(endPortMask).setEndPortSpeed(endPortSpeed)
                    .setLinkStatus(linkStatus).setId(count).build();
            linklist.add(link);
            count++;
        }
        getTopo.setNode(nodelist);
        getTopo.setLink(linklist);

        // write the data to broker

        this.dataService = IservicenodeProvider.providerContext.getSALService(DataBroker.class);
        dcReg = dataService.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL, GET_TOPO_IID, this,
                DataChangeScope.SUBTREE);
        WriteTransaction tx = dataService.newWriteOnlyTransaction();
        tx.put(LogicalDatastoreType.OPERATIONAL, GET_TOPO_IID, getTopo.build());
        Futures.addCallback(tx.submit(), new FutureCallback<Void>() {

            @Override
            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
                LOG.info("initOperational: tarnsaction failed!");
            }

            @Override
            public void onSuccess(Void arg0) {
                // TODO Auto-generated method stub
                LOG.info("initOperational: tarnsaction successed!");
            }
        });
    }

    public List<LinkInfoObj> getLinkInfo() {

        List<LinkInfoObj> listLinkInfo = new ArrayList<LinkInfoObj>();
        for (LinkObjPartOne linkObj : TopologyLinksDataBase.getLinkPartOne()) {
            if (HostNameToIpAddressMapping.getHostToIp().containsKey(linkObj.getHostName())
                    && HostNameToIpAddressMapping.getHostToIp().containsKey(linkObj.getDeviceID())) {
                LinkInfoObj linkInfoObj = new LinkInfoObj();
                linkInfoObj.setHostName(linkObj.getHostName());
                String startPortTemp = linkObj.getStartPortID();
                String hostIp = HostNameToIpAddressMapping.getHostToIp().get(linkObj.getHostName());

                linkInfoObj.setStartPortName(startPortTemp);
                InterfacesObjInfo desireStartObj = this.getPortInfo(startPortTemp, hostIp);
                linkInfoObj.setStartPortIpAddress(desireStartObj.getIpAddress());
                linkInfoObj.setStartPortIPMask(desireStartObj.getMask());
                linkInfoObj.setStartPortSpeed(desireStartObj.getSpeed());
                linkInfoObj.setStartPortStatus(desireStartObj.getStatus());

                linkInfoObj.setDeviceID(linkObj.getDeviceID());
                String EndPortTemp = linkObj.getEndPortID();
                String DeviceIp = HostNameToIpAddressMapping.getHostToIp().get(linkObj.getDeviceID());
                linkInfoObj.setEndPortName(EndPortTemp);
                InterfacesObjInfo desireEndObj = this.getPortInfo(EndPortTemp, DeviceIp);
                linkInfoObj.setEndPortIpAddress(desireEndObj.getIpAddress());
                linkInfoObj.setEndPortIpMask(desireEndObj.getMask());
                linkInfoObj.setEndPortSpeed(desireEndObj.getSpeed());
                linkInfoObj.setEndPortStatus(desireEndObj.getStatus());

                if (linkInfoObj.getStartPortStatus().equals("up") && linkInfoObj.getEndPortStatus().equals("up")) {
                    linkInfoObj.setLinkstatus("up");
                } else {
                    linkInfoObj.setLinkstatus("down");
                }
                listLinkInfo.add(linkInfoObj);
            }
        }
        // generate link infoObj

        return listLinkInfo;
    }

    public InterfacesObjInfo getPortInfo(String port, String ipAddress) {
        Set<InterfacesObjInfo> interfaceInfo = IpToInterfacesInfo.getIpToPort().get(ipAddress);
        for (InterfacesObjInfo interInfo : interfaceInfo) {
            if (interInfo.getPort().equals(port)) {
                return interInfo;
            }
        }
        return null;
    }

    @Override
    public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
        // TODO Auto-generated method stub

    }

    // get the node info and store in the database
    public void getNodeInfoAndStore(String nodeIpAddress) {
        String nodearg = new String("python /home/odl/developerProject/pythonscript/nodeinfo.py" + " " + nodeIpAddress);
        HandleNodeInfo handleNode = HandleNodeInfo.getInstance();
        NodeInfoObj nodeInfo = handleNode.execute(nodearg);

        HostNameToIpAddressMapping.getHostToIp().put(nodeInfo.getLabel(), nodeIpAddress);
        TopologyNodesDataBase.getTopologyNodeInfo().put(nodeIpAddress, nodeInfo);
    }

    // get the interfaces info and store in the database
    public void getInterfacesInfoAndStore(String nodeIpAddress) {
        String portarg = new String(
                "python /home/odl/developerProject/pythonscript/portToMask.py" + " " + nodeIpAddress);
        HandlePortInfo portInfo = HandlePortInfo.getInstance();
        Set<InterfacesObjInfo> intObjInfo = new HashSet<InterfacesObjInfo>();
        intObjInfo = portInfo.execute(portarg);
        IpToInterfacesInfo.getIpToPort().put(nodeIpAddress, intObjInfo);
    }

    // get the link info and filter
    public void getLinkInfoAndFilter(String nodeIpAddress) {
        List<LinkObjPartOne> one = new ArrayList<LinkObjPartOne>();
        String linkarg = new String("python /home/odl/developerProject/pythonscript/linkinfo.py" + " " + nodeIpAddress);
        GetLinkInfo getlinkInfo = GetLinkInfo.getInstance();
        one = getlinkInfo.execute(linkarg);
        HandleLinkInfo handleLinkInfo = HandleLinkInfo.getInstance();
        handleLinkInfo.handleLink(one);
    }

}
