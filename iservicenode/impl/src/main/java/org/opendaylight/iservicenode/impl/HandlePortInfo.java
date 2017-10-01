/*
 * Copyright © 2016 Cisco, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.iservicenode.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.opendaylight.pojo.InterfacesObjInfo;

import com.google.gson.Gson;

public class HandlePortInfo {

    private static HandlePortInfo instce_port;

    private HandlePortInfo() {

    }

    public static HandlePortInfo getInstance() {
        if (instce_port == null) {
            instce_port = new HandlePortInfo();
            return instce_port;
        } else {
            return instce_port;
        }
    }

    public Set<InterfacesObjInfo> execute(String myarg) {
        Set<InterfacesObjInfo> result = new HashSet<InterfacesObjInfo>();
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        Process process;
        InterfacesObjInfo ioi = new InterfacesObjInfo();
        try {
            process = Runtime.getRuntime().exec(myarg);
            inputStream = process.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Gson sgson = new Gson();
                ioi = sgson.fromJson(line, InterfacesObjInfo.class);
                result.add(ioi);
            }
            return result;
        } catch (Exception e1) {
            // TODO: handle exception
            e1.printStackTrace();

        } finally {
            myclose(bufferedReader, inputStreamReader, inputStream);

        }
        return null;

    }

    public void myclose(BufferedReader mybr, InputStreamReader myisr, InputStream myis) {
        try {
            if (mybr != null) {
                mybr.close();
            }
            if (myisr != null) {
                myisr.close();
            }
            if (myis != null) {
                myis.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

}
