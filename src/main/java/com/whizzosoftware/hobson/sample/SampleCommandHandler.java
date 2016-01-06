/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.sample;

import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import java.lang.reflect.Method;

/**
 * A command handler for sample devices.
 *
 * @author Dan Noguerol
 */
public class SampleCommandHandler {
    private BundleContext context = null;

    public SampleCommandHandler(BundleContext context) {
        this.context = context;
    }

    /**
     * Set a device's availability.
     *
     * @param deviceId the device ID
     * @param available the device's current availability
     */
    public void avail(String deviceId, String available) {
        try {
            for (ServiceReference sr : context.getAllServiceReferences(HobsonPlugin.class.getName(), null)) {
                HobsonPlugin p = (HobsonPlugin)context.getService(sr);
                if ("com.whizzosoftware.hobson.hub.hobson-hub-sample".equals(p.getContext().getPluginId())) {
                    try {
                        Method m = p.getClass().getMethod("getPlugin", null);
                        SamplePlugin plugin = (SamplePlugin)m.invoke(p, null);
                        plugin.setAvailability(deviceId, "true".equals(available));
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                    break;
                }
            }
        } catch (InvalidSyntaxException e) {
            System.err.println(e);
        }
    }
}
