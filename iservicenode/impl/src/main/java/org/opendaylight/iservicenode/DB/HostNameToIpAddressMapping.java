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

public class HostNameToIpAddressMapping {
	private static Map<String, String> hostToIp = new HashMap<String, String>();

	public static Map<String, String> getHostToIp() {
		return hostToIp;
	}

	public static void setHostToIp(Map<String, String> hostToIp) {
		HostNameToIpAddressMapping.hostToIp = hostToIp;
	}


}
