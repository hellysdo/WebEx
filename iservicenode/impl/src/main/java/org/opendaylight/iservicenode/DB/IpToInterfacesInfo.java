/*
 * Copyright © 2016 Cisco, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.iservicenode.DB;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.opendaylight.pojo.InterfacesObjInfo;

public class IpToInterfacesInfo {
    private static Map<String, Set<InterfacesObjInfo>> ipToPort = new HashMap<String, Set<InterfacesObjInfo>>();

    public static Map<String, Set<InterfacesObjInfo>> getIpToPort() {
        return ipToPort;
    }

    public static void setIpToPort(Map<String, Set<InterfacesObjInfo>> ipToPort) {
        IpToInterfacesInfo.ipToPort = ipToPort;
    }

}
