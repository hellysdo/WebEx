/*
 * Copyright © 2016 Cisco, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.iservicenode.DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendaylight.pojo.NodeInfoObj;

public class TopologyNodesDataBase {
	private static List<String> topologyIpInfo = new ArrayList<String>();

	private static Map<String, NodeInfoObj> topologyNodeInfo = new HashMap<String, NodeInfoObj>();

	public static List<String> getTopologyIpInfo() {
		return topologyIpInfo;
	}

	public static void setTopologyIpInfo(List<String> topologyIpInfo) {
		TopologyNodesDataBase.topologyIpInfo = topologyIpInfo;
	}

	public static Map<String, NodeInfoObj> getTopologyNodeInfo() {
		return topologyNodeInfo;
	}

	public static void setTopologyNodeInfo(Map<String, NodeInfoObj> topologyNodeInfo) {
		TopologyNodesDataBase.topologyNodeInfo = topologyNodeInfo;
	}

}
