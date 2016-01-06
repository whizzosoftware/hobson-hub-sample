/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.sample;

import com.whizzosoftware.hobson.api.osgi.activator.HobsonBundleActivator;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

/**
 * Sub-class of HobsonBundleActivator that registers an OSGi command service to control sample devices.
 *
 * @author Dan Noguerol
 */
public class SampleBundleActivator extends HobsonBundleActivator {
    @Override
    public void init(BundleContext context, DependencyManager manager) throws Exception {
        super.init(context, manager);

        Hashtable<String,Object> config = new Hashtable<>();
        config.put("osgi.command.scope", "sample");
        config.put("osgi.command.function", new String[] {
            "avail"
        });

        context.registerService(
            SampleCommandHandler.class.getName(),
            new SampleCommandHandler(context),
            config
        );
    }
}
