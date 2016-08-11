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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SampleLightbulbDevice extends AbstractDeviceProxy {
    private static final Logger logger = LoggerFactory.getLogger(SampleLightbulbDevice.class);

    public SampleLightbulbDevice(HobsonPlugin plugin, String id) {
        super(plugin, id, "Color LED Bulb");
    }

    @Override
    public void onStartup(PropertyContainer config) {
        super.onStartup(config);

        logger.info("Lighbulb device is starting");

        Map<String,Object> updates = new HashMap<>();
        updates.put(VariableConstants.COLOR, "#0000ff");
        updates.put(VariableConstants.LEVEL, 100);
        updates.put(VariableConstants.ON, true);
        setVariableValues(updates);
    }

    @Override
    public void onShutdown() {
        logger.info("Lightbulb device is stopping");

    }

    @Override
    public void onDeviceConfigurationUpdate(PropertyContainer config) {

    }

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.LIGHTBULB;
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
    public DeviceVariableDescription[] createVariableDescriptions() {
        return new DeviceVariableDescription[] {
            createDeviceVariableDescription(VariableConstants.COLOR, DeviceVariableDescription.Mask.READ_WRITE),
            createDeviceVariableDescription(VariableConstants.LEVEL, DeviceVariableDescription.Mask.READ_WRITE),
            createDeviceVariableDescription(VariableConstants.ON, DeviceVariableDescription.Mask.READ_WRITE)
        };
    }

    @Override
    protected TypedProperty[] createConfigurationPropertyTypes() {
        return null;
    }

    @Override
    public void onSetVariable(String name, Object value) {
        logger.info("Received set device variable request: {}, {}", name, value);

        // TODO: control device

        setVariableValue(name, value, System.currentTimeMillis());
    }
}
