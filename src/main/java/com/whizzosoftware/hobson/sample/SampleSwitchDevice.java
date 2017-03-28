/*******************************************************************************
 * Copyright (c) 2014 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.sample;

import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.device.proxy.AbstractHobsonDeviceProxy;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.VariableConstants;
import com.whizzosoftware.hobson.api.variable.VariableMask;

import java.util.Map;

public class SampleSwitchDevice extends AbstractHobsonDeviceProxy {
    public SampleSwitchDevice(HobsonPlugin plugin, String id) {
        super(plugin, id, "Switchable Outlet", DeviceType.SWITCH);
    }

    @Override
    public void onStartup(String name, Map<String,Object> config) {
        publishVariables(createDeviceVariable(VariableConstants.ON, VariableMask.READ_WRITE, false, System.currentTimeMillis()));
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public void onDeviceConfigurationUpdate(Map<String,Object> config) {
    }

    @Override
    public String getManufacturerName() {
        return "Whizzo Software LLC";
    }

    @Override
    public String getManufacturerVersion() {
        return "1.0";
    }

    @Override
    public String getModelName() {
        return "Sample Switch";
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.ON;
    }

    @Override
    protected TypedProperty[] getConfigurationPropertyTypes() {
        return null;
    }

    @Override
    public void onSetVariables(Map<String,Object> values) {
        for (String name : values.keySet()) {
            setVariableValue(name, values.get(name), System.currentTimeMillis());
        }
    }
}
