/*******************************************************************************
 * Copyright (c) 2014 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.sample;

import com.whizzosoftware.hobson.api.config.Configuration;
import com.whizzosoftware.hobson.api.config.ConfigurationPropertyMetaData;
import com.whizzosoftware.hobson.api.device.AbstractHobsonDevice;
import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.VariableConstants;

public class SampleSwitchDevice extends AbstractHobsonDevice {
    public SampleSwitchDevice(HobsonPlugin plugin, String id) {
        super(plugin, id);
    }

    @Override
    public ConfigurationPropertyMetaData[] createConfigurationPropertyMetaData() {
        return null;
    }

    @Override
    public void onStartup(Configuration config) {
        publishVariable(VariableConstants.ON, false, HobsonVariable.Mask.READ_WRITE);
    }

    @Override
    public void onShutdown() {
    }

    public String getDefaultName() {
        return "Switchable Outlet";
    }

    @Override
    public DeviceType getType() {
        return DeviceType.SWITCH;
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.ON;
    }

    @Override
    public void onSetVariable(String name, Object value) {
        fireVariableUpdateNotification(name, value);
    }
}
