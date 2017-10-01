/*
 * Copyright © 2016 Cisco, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.pojo;

public class LinkObjPartOne {
	private String HostName;
	private String DeviceID;
	private String StartPortID;
	private String EndPortID;

	public String getHostName() {
		return HostName;
	}

	public void setHostName(String hostName) {
		HostName = hostName;
	}

	public String getDeviceID() {
		return DeviceID;
	}

	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}

	public String getStartPortID() {
		return StartPortID;
	}

	public void setStartPortID(String startPortID) {
		StartPortID = startPortID;
	}

	public String getEndPortID() {
		return EndPortID;
	}

	public void setEndPortID(String endPortID) {
		EndPortID = endPortID;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof LinkObjPartOne){
			LinkObjPartOne objD = (LinkObjPartOne) obj;
			if ((objD.HostName.equals(HostName) && objD.StartPortID.equals(StartPortID)
					&& objD.DeviceID.equals(DeviceID) && objD.EndPortID.equals(EndPortID))
					|| (objD.HostName.equals(DeviceID) && objD.StartPortID.equals(EndPortID)
							&& objD.DeviceID.equals(HostName) && objD.EndPortID.equals(StartPortID))) {
				return true;
			}

		}
		return false;
	}

}
