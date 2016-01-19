/*******************************************************************************
 * Copyright (c) 2014 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.sample;

import com.whizzosoftware.hobson.api.device.AbstractHobsonDevice;
import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.VariableConstants;

public class SampleSwitchDevice extends AbstractHobsonDevice {
    public SampleSwitchDevice(HobsonPlugin plugin, String id) {
        super(plugin, id);
    }

    @Override
    public void onStartup(PropertyContainer config) {
        super.onStartup(config);

        publishVariable(VariableConstants.ON, false, HobsonVariable.Mask.READ_WRITE, System.currentTimeMillis());
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
    protected TypedProperty[] createSupportedProperties() {
        return null;
    }

    @Override
    public void onSetVariable(String name, Object value) {
        fireVariableUpdateNotification(name, value);
    }
}
