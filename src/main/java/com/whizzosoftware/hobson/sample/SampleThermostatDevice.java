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
import com.whizzosoftware.hobson.api.variable.*;

import java.util.Map;

public class SampleThermostatDevice extends AbstractHobsonDeviceProxy {
    public SampleThermostatDevice(HobsonPlugin plugin, String id) {
        super(plugin, id, "Thermostat", DeviceType.THERMOSTAT);
    }

    private double currentTemp = 73.123;
    private long lastTempChange = System.currentTimeMillis();

    @Override
    public void onStartup(String name, Map<String,Object> config) {
        long now = System.currentTimeMillis();
        publishVariables(
            createDeviceVariable(VariableConstants.INDOOR_TEMP_F, VariableMask.READ_ONLY, currentTemp, now),
            createDeviceVariable(VariableConstants.TARGET_TEMP_F, VariableMask.READ_WRITE, 74.0, now)
        );
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
        return "Sample Thermostat";
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.INDOOR_TEMP_F;
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

    public void onRefresh(long now) {
        // if 5 minutes have passed, change the current temperature
        if (now - lastTempChange >= 300000) {
            updateCurrentTemp(now);
            setVariableValue(VariableConstants.INDOOR_TEMP_F, currentTemp, System.currentTimeMillis());
        }
        setLastCheckin(now);
    }

    protected void updateCurrentTemp(long now) {
        currentTemp -= 0.5;
        if (currentTemp < 69) {
            currentTemp = 73.123;
        }
        lastTempChange = now;
    }
}
