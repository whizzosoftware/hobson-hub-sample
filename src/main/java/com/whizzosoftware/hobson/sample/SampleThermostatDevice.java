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

    private double currentTemp = 73.0;
    private long lastTempChange = System.currentTimeMillis();

    @Override
    public void onStartup(PropertyContainer config) {
        super.onStartup(config);

        publishVariable(VariableConstants.INDOOR_TEMP_F, currentTemp, HobsonVariable.Mask.READ_ONLY, lastTempChange);
        publishVariable(VariableConstants.TARGET_TEMP_F, 74.0, HobsonVariable.Mask.READ_WRITE, lastTempChange);
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public DeviceType getType() {
        return DeviceType.THERMOSTAT;
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
        return "Sample Thermostat";
    }

    @Override
    public String getDefaultName() {
        return "Thermostat";
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.INDOOR_TEMP_F;
    }

    @Override
    public String[] getTelemetryVariableNames() {
        return new String[] {VariableConstants.INDOOR_TEMP_F, VariableConstants.TARGET_TEMP_F};
    }

    @Override
    protected TypedProperty[] createSupportedProperties() {
        return null;
    }

    @Override
    public void onSetVariable(String name, Object value) {
        fireVariableUpdateNotification(name, value);
    }

    public void onRefresh(boolean availability, long now) {
        // if 5 minutes have passed, change the current temperature
        if (now - lastTempChange >= 300000) {
            updateCurrentTemp(now);
            fireVariableUpdateNotification(VariableConstants.INDOOR_TEMP_F, currentTemp);
        }

        setDeviceAvailability(availability, now);
    }

    protected void updateCurrentTemp(long now) {
        currentTemp -= 0.5;
        if (currentTemp < 69) {
            currentTemp = 73.0;
        }
        lastTempChange = now;
    }
}
