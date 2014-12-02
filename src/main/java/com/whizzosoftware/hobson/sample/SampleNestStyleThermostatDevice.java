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
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.VariableConstants;

public class SampleNestStyleThermostatDevice extends AbstractHobsonDevice {
    public SampleNestStyleThermostatDevice(HobsonPlugin plugin, String id) {
        super(plugin, id);
    }

    @Override
    public void onStartup() {
        publishVariable(VariableConstants.TEMP_F, 73, HobsonVariable.Mask.READ_ONLY);
        publishVariable(VariableConstants.TARGET_TEMP_F, 74, HobsonVariable.Mask.READ_WRITE);
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public DeviceType getType() {
        return DeviceType.THERMOSTAT;
    }

    @Override
    public String getDefaultName() {
        return "Nest-style Thermostat";
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.TEMP_F;
    }

    @Override
    public void onSetVariable(String name, Object value) {
        fireVariableUpdateNotification(name, value);
    }
}
