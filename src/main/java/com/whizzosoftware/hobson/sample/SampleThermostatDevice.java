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

public class SampleThermostatDevice extends AbstractHobsonDevice {
    public SampleThermostatDevice(HobsonPlugin plugin, String id) {
        super(plugin, id);
    }

    @Override
    public TypedProperty[] createConfigurationPropertyMetaData() {
        return null;
    }

    private int currentTemp = 73;
    private long lastTempChange = System.currentTimeMillis();

    @Override
    public void onStartup(PropertyContainer config) {
        publishVariable(VariableConstants.TEMP_F, currentTemp, HobsonVariable.Mask.READ_ONLY);
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
        return "Thermostat";
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.TEMP_F;
    }

    @Override
    public String[] getTelemetryVariableNames() {
        return new String[] {VariableConstants.TEMP_F, VariableConstants.TARGET_TEMP_F};
    }

    @Override
    public void onSetVariable(String name, Object value) {
        fireVariableUpdateNotification(name, value);
    }

    public void onRefresh() {
        long now = System.currentTimeMillis();

        // if 5 minutes have passed, change the current temperature
        if (now - lastTempChange >= 300000) {
            updateCurrentTemp(now);
            fireVariableUpdateNotification(VariableConstants.TEMP_F, currentTemp);
        }
    }

    protected void updateCurrentTemp(long now) {
        currentTemp--;
        if (currentTemp < 69) {
            currentTemp = 73;
        }
        lastTempChange = now;
    }
}
