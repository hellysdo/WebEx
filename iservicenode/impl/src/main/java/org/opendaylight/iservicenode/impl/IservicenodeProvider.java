/*
 * Copyright © 2016 Cisco, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.iservicenode.impl;

import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.iservicenode.rev150105.IservicenodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IservicenodeProvider implements BindingAwareProvider, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(IservicenodeProvider.class);
    public static ProviderContext providerContext;

    private RpcRegistration<IservicenodeService> iservicenodeservice;

    @Override
    public void onSessionInitiated(ProviderContext session) {
        LOG.info("IservicenodeProvider Session Initiated");
        IservicenodeProvider.providerContext = session;
        iservicenodeservice = session.addRpcImplementation(IservicenodeService.class, new IservicenodeImpl());

    }

    @Override
    public void close() throws Exception {
        LOG.info("IservicenodeProvider Closed");
        if (iservicenodeservice != null) {
            iservicenodeservice.close();
        }
    }

}
