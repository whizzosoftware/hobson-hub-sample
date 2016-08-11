/*******************************************************************************
 * Copyright (c) 2014 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.sample;

import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.device.proxy.AbstractDeviceProxy;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.*;

import java.util.HashMap;
import java.util.Map;

public class SampleThermostatDevice extends AbstractDeviceProxy {
    public SampleThermostatDevice(HobsonPlugin plugin, String id) {
        super(plugin, id, "Thermostat");
    }

    private double currentTemp = 73.123;
    private long lastTempChange = System.currentTimeMillis();

    @Override
    public void onStartup(PropertyContainer config) {
        super.onStartup(config);

        Map<String,Object> updates = new HashMap<>();
        updates.put(VariableConstants.INDOOR_TEMP_F, currentTemp);
        updates.put(VariableConstants.TARGET_TEMP_F, 74.0);
        setVariableValues(updates);
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public void onDeviceConfigurationUpdate(PropertyContainer config) {

    }

    @Override
    public DeviceType getDeviceType() {
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
    public String getPreferredVariableName() {
        return VariableConstants.INDOOR_TEMP_F;
    }

    @Override
    public DeviceVariableDescription[] createVariableDescriptions() {
        return new DeviceVariableDescription[] {
            createDeviceVariableDescription(VariableConstants.INDOOR_TEMP_F, DeviceVariableDescription.Mask.READ_ONLY),
            createDeviceVariableDescription(VariableConstants.TARGET_TEMP_F, DeviceVariableDescription.Mask.READ_WRITE)
        };
    }

    @Override
    protected TypedProperty[] createConfigurationPropertyTypes() {
        return null;
    }

    @Override
    public void onSetVariable(String name, Object value) {
        setVariableValue(name, value, System.currentTimeMillis());
    }

    public void onRefresh(boolean availability, long now) {
        // if 5 minutes have passed, change the current temperature
        if (now - lastTempChange >= 300000) {
            updateCurrentTemp(now);
            setVariableValue(VariableConstants.INDOOR_TEMP_F, currentTemp, System.currentTimeMillis());
        }
        setDeviceAvailability(availability, now);
    }

    protected void updateCurrentTemp(long now) {
        currentTemp -= 0.5;
        if (currentTemp < 69) {
            currentTemp = 73.123;
        }
        lastTempChange = now;
    }
}
