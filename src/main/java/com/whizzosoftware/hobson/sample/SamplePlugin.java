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
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SamplePlugin extends AbstractHobsonPlugin {
    private static final Logger logger = LoggerFactory.getLogger(SamplePlugin.class);

    private SampleLightbulbDevice bulb;
    private SampleSwitchDevice sw;
    private SampleCameraDevice camera;
    private SampleThermostatDevice thermostat;
    private WeatherStationDevice ws;

    public SamplePlugin(String pluginId) {
        super(pluginId);
    }

    @Override
    public String getName() {
        return "Sample Plugin";
    }

    @Override
    public void onStartup(PropertyContainer config) {
        logger.info("Plugin is starting up");

        bulb = new SampleLightbulbDevice(this, "bulb");
        sw = new SampleSwitchDevice(this, "switch");
        camera = new SampleCameraDevice(this, "camera");
        ws = new WeatherStationDevice(this, "wstation");
        thermostat = new SampleThermostatDevice(this, "thermostat");

        publishDevice(bulb);
        publishDevice(sw);
        publishDevice(camera);
        publishDevice(ws);
        publishDevice(thermostat);

        setStatus(new PluginStatus(PluginStatus.Code.RUNNING));
    }

    @Override
    public void onShutdown() {
        logger.info("Plugin is shutting down");
    }

    @Override
    protected TypedProperty[] createSupportedProperties() {
        return new TypedProperty[] {
            new TypedProperty.Builder("test", "Test", "A test parameter", TypedProperty.Type.STRING).build()
        };
    }

    @Override
    public long getRefreshInterval() {
        return 30;
    }

    @Override
    public void onRefresh() {
        long now = System.currentTimeMillis();

        // check-in devices so they don't display as inactive
        bulb.checkInDevice(now);
        sw.checkInDevice(now);
        camera.checkInDevice(now);
        ws.checkInDevice(now);

        // refresh the thermostat temperature
        thermostat.onRefresh(now);
    }

    @Override
    public void onPluginConfigurationUpdate(PropertyContainer config) {
        logger.info("Received plugin configuration update");
    }
}
