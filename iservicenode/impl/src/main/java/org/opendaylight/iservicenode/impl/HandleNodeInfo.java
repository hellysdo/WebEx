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
import java.util.List;

import org.opendaylight.pojo.NodeInfoObj;

import com.google.gson.Gson;

public class HandleNodeInfo {

    private static HandleNodeInfo instce_node;

    private HandleNodeInfo() {

    }

    public static HandleNodeInfo getInstance() {
        if (instce_node == null) {
            instce_node = new HandleNodeInfo();
            return instce_node;
        } else {
            return instce_node;
        }
    }

    public NodeInfoObj execute(String myarg) {
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        Process process;
        NodeInfoObj obj = new NodeInfoObj();
        try {
            process = Runtime.getRuntime().exec(myarg);
            inputStream = process.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Gson sgson = new Gson();
                obj = sgson.fromJson(line, NodeInfoObj.class);
            }
            return obj;
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
