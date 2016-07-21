/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.sample;

/**
 * A command handler for sample devices.
 *
 * @author Dan Noguerol
 */
public class SampleCommandHandler {
    private SamplePlugin plugin;

    public SampleCommandHandler(SamplePlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Set a device's availability.
     *
     * @param deviceId the device ID
     * @param available the device's current availability
     */
    public void avail(String deviceId, String available) {
        plugin.setAvailability(deviceId, "true".equals(available));
    }
}
