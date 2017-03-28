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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SampleLightbulbDevice extends AbstractHobsonDeviceProxy {
    private static final Logger logger = LoggerFactory.getLogger(SampleLightbulbDevice.class);

    public SampleLightbulbDevice(HobsonPlugin plugin, String id) {
        super(plugin, id, "Color LED Bulb", DeviceType.LIGHTBULB);
    }

    @Override
    public void onStartup(String name, Map<String,Object> config) {
        logger.info("Lighbulb device is starting");

        long now = System.currentTimeMillis();
        publishVariables(
            createDeviceVariable(VariableConstants.COLOR, VariableMask.READ_WRITE, "#0000ff", now),
            createDeviceVariable(VariableConstants.LEVEL, VariableMask.READ_WRITE, 100, now),
            createDeviceVariable(VariableConstants.ON, VariableMask.READ_WRITE, true, now)
        );
    }

    @Override
    public void onShutdown() {
        logger.info("Lightbulb device is stopping");

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
        return "Sample Lightbulb";
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
        logger.info("Received set device variable request: {}", values);

        for (String name : values.keySet()) {
            setVariableValue(name, values.get(name), System.currentTimeMillis());
        }
    }
}
