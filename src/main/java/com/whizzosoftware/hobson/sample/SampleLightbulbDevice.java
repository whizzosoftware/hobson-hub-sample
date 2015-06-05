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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleLightbulbDevice extends AbstractHobsonDevice {
    private static final Logger logger = LoggerFactory.getLogger(SampleLightbulbDevice.class);

    public SampleLightbulbDevice(HobsonPlugin plugin, String id) {
        super(plugin, id);
    }

    @Override
    public TypedProperty[] createConfigurationPropertyMetaData() {
        return null;
    }

    @Override
    public void onStartup(PropertyContainer config) {
        logger.info("Lighbulb device is starting");

        publishVariable(VariableConstants.COLOR, "#0000ff", HobsonVariable.Mask.READ_WRITE);
        publishVariable(VariableConstants.LEVEL, 100, HobsonVariable.Mask.READ_WRITE);
        publishVariable(VariableConstants.ON, true, HobsonVariable.Mask.READ_WRITE);
    }

    @Override
    public void onShutdown() {
        logger.info("Lightbulb device is stopping");

    }

    @Override
    public DeviceType getType() {
        return DeviceType.LIGHTBULB;
    }

    @Override
    public String getDefaultName() {
        return "Color LED Bulb";
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.ON;
    }

    @Override
    public void onSetVariable(String name, Object value) {
        logger.info("Received set device variable request: {}, {}", name, value);

        // TODO: control device

        fireVariableUpdateNotification(name, value);
    }
}
