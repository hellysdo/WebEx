/*
 * Copyright © 2016 Cisco, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.pojo;

public class LinkInfoObj {
	private String HostName;
	private String StartPortName;
	private String StartPortIpAddress;
	private String StartPortIPMask;
	private String StartPortStatus;
	private String StartPortSpeed;
	private String EndPortName;
	private String EndPortIpAddress;
	private String EndPortIpMask;
	private String EndPortStatus;
	private String EndPortSpeed;
	private String DeviceID;
	private String linkstatus;

	public String getStartPortStatus() {
		return StartPortStatus;
	}

	public void setStartPortStatus(String startPortStatus) {
		StartPortStatus = startPortStatus;
	}

	public String getEndPortStatus() {
		return EndPortStatus;
	}

	public void setEndPortStatus(String endPortStatus) {
		EndPortStatus = endPortStatus;
	}

	public String getLinkstatus() {
		return linkstatus;
	}

	public void setLinkstatus(String linkstatus) {
		this.linkstatus = linkstatus;
	}

	public String getHostName() {
		return HostName;
	}

	public void setHostName(String hostName) {
		HostName = hostName;
	}

	public String getStartPortName() {
		return StartPortName;
	}

	public void setStartPortName(String startPortName) {
		StartPortName = startPortName;
	}

	public String getStartPortIpAddress() {
		return StartPortIpAddress;
	}

	public void setStartPortIpAddress(String startPortIpAddress) {
		StartPortIpAddress = startPortIpAddress;
	}

	public String getStartPortIPMask() {
		return StartPortIPMask;
	}

	public void setStartPortIPMask(String startPortIPMask) {
		StartPortIPMask = startPortIPMask;
	}

	public String getStartPortSpeed() {
		return StartPortSpeed;
	}

	public void setStartPortSpeed(String startPortSpeed) {
		StartPortSpeed = startPortSpeed;
	}

	public String getEndPortName() {
		return EndPortName;
	}

	public void setEndPortName(String endPortName) {
		EndPortName = endPortName;
	}

	public String getEndPortIpAddress() {
		return EndPortIpAddress;
	}

	public void setEndPortIpAddress(String endPortIpAddress) {
		EndPortIpAddress = endPortIpAddress;
	}

	public String getEndPortIpMask() {
		return EndPortIpMask;
	}

	public void setEndPortIpMask(String endPortIpMask) {
		EndPortIpMask = endPortIpMask;
	}

	public String getEndPortSpeed() {
		return EndPortSpeed;
	}

	public void setEndPortSpeed(String endPortSpeed) {
		EndPortSpeed = endPortSpeed;
	}

	public String getDeviceID() {
		return DeviceID;
	}

	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}

}
