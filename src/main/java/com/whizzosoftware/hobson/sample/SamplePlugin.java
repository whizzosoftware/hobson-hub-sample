/*******************************************************************************
 * Copyright (c) 2014 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.sample;

import com.whizzosoftware.hobson.api.plugin.AbstractHobsonPlugin;
import com.whizzosoftware.hobson.api.plugin.PluginStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Dictionary;

public class SamplePlugin extends AbstractHobsonPlugin {
    private static final Logger logger = LoggerFactory.getLogger(SamplePlugin.class);

    public SamplePlugin(String pluginId) {
        super(pluginId);
    }

    @Override
    public String getName() {
        return "Sample Plugin";
    }

    @Override
    public void onStartup(Dictionary config) {
        logger.info("Plugin is starting up");

        publishDevice(new SampleLightbulbDevice(this, "bulb"));
        publishDevice(new SampleSwitchDevice(this, "switch"));
        publishDevice(new SampleCameraDevice(this, "camera"));
        publishDevice(new SampleNestStyleThermostatDevice(this, "Nesty"));
        publishDevice(new SampleTraditionalThermostat(this, "Thermostat"));

        setStatus(new PluginStatus(PluginStatus.Status.RUNNING));
    }

    @Override
    public void onShutdown() {
        logger.info("Plugin is shutting down");
    }

    @Override
    public long getRefreshInterval() {
        return 0;
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onPluginConfigurationUpdate(Dictionary config) {
        logger.info("Received plugin configuration update");
    }

    @Override
    public void onSetDeviceVariable(String deviceId, String variableName, Object value) {
        logger.info("Received set device variable request: {}, {}, {}", deviceId, variableName, value);

        getDevice(deviceId).onSetVariable(variableName, value);
    }
}
