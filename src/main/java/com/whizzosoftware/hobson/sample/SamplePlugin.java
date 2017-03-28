/*
 *******************************************************************************
 * Copyright (c) 2014 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.sample;

import com.whizzosoftware.hobson.api.plugin.AbstractHobsonPlugin;
import com.whizzosoftware.hobson.api.plugin.PluginStatus;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.Map;

public class SamplePlugin extends AbstractHobsonPlugin {
    private static final Logger logger = LoggerFactory.getLogger(SamplePlugin.class);

    public SamplePlugin(String pluginId, String version, String description) {
        super(pluginId, version, description);
    }

    @Override
    public String getName() {
        return "Sample Plugin";
    }

    @Override
    public void onStartup(PropertyContainer config) {
        logger.info("Plugin is starting up");

        publishDeviceProxy(new SampleLightbulbDevice(this, "bulb"));
        publishDeviceProxy(new SampleSwitchDevice(this, "switch"));
        publishDeviceProxy(new SampleCameraDevice(this, "camera"));
        publishDeviceProxy(new WeatherStationDevice(this, "wstation"));
        publishDeviceProxy(new SampleThermostatDevice(this, "thermostat"));

        BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
        Hashtable<String,Object> c = new Hashtable<>();
        c.put("osgi.command.scope", "sample");
        c.put("osgi.command.function", new String[] {
            "avail"
        });
        context.registerService(
            SampleCommandHandler.class.getName(),
            new SampleCommandHandler(this),
            c
        );

        setStatus(new PluginStatus(PluginStatus.Code.RUNNING));
    }

    @Override
    public void onShutdown() {
        logger.info("Plugin is shutting down");
    }

    @Override
    protected TypedProperty[] getConfigurationPropertyTypes() {
        return new TypedProperty[] {
            new TypedProperty.Builder("test", "Test", "A test parameter", TypedProperty.Type.STRING).build()
        };
    }

    @Override
    public long getRefreshInterval() {
        return 60;
    }

    @Override
    public void onRefresh() {
        long now = System.currentTimeMillis();

        // check-in devices so they don't display as inactive
        getDeviceProxy("bulb").setLastCheckin(now);
        getDeviceProxy("switch").setLastCheckin(now);
        getDeviceProxy("camera").setLastCheckin(now);
        getDeviceProxy("wstation").setLastCheckin(now);

        // refresh the thermostat temperature
        ((SampleThermostatDevice)getDeviceProxy("thermostat")).onRefresh(now);
    }

    @Override
    public void onPluginConfigurationUpdate(PropertyContainer config) {
        logger.info("Received plugin configuration update");
    }

    public void setDeviceAvailability(String deviceId, boolean available) {
        getDeviceProxy(deviceId).setLastCheckin(available ? System.currentTimeMillis() : System.currentTimeMillis() - 700000);
    }
}
