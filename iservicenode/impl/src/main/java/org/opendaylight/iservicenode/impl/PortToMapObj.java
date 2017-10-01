/*
 * Copyright © 2016 Cisco, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.iservicenode.impl;

import java.util.ArrayList;
import java.util.List;

import org.opendaylight.pojo.InterfacesObjInfo;

public class PortToMapObj {
	private List<InterfacesObjInfo> interObjInfo = new ArrayList<InterfacesObjInfo>();

	public List<InterfacesObjInfo> getInterObjInfo() {
		return interObjInfo;
	}

	public void setInterObjInfo(List<InterfacesObjInfo> interObjInfo) {
		this.interObjInfo = interObjInfo;
	}

}
