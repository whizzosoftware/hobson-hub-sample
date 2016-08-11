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
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class SamplePlugin extends AbstractHobsonPlugin {
    private static final Logger logger = LoggerFactory.getLogger(SamplePlugin.class);

    private SampleLightbulbDevice bulb;
    private SampleSwitchDevice sw;
    private SampleCameraDevice camera;
    private SampleThermostatDevice thermostat;
    private WeatherStationDevice ws;
    private Map<String,Boolean> availMap = new HashMap<>();

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

        registerDeviceProxy(bulb);
        registerDeviceProxy(sw);
        registerDeviceProxy(camera);
        registerDeviceProxy(ws);
        registerDeviceProxy(thermostat);

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
        return 30;
    }

    @Override
    public void onRefresh() {
        long now = System.currentTimeMillis();

        // check-in devices so they don't display as inactive
        setDeviceAvailability(bulb.getDeviceId(), getAvailability("bulb"), now);
        setDeviceAvailability(sw.getDeviceId(), getAvailability("switch"), now);
        setDeviceAvailability(camera.getDeviceId(), getAvailability("camera"), now);
        setDeviceAvailability(ws.getDeviceId(), getAvailability("wstation"), now);

        // refresh the thermostat temperature
        thermostat.onRefresh(getAvailability("thermostat"), now);
    }

    @Override
    public void onPluginConfigurationUpdate(PropertyContainer config) {
        logger.info("Received plugin configuration update");
    }

    public void setAvailability(String name, boolean avail) {
        availMap.put(name, avail);
        setDeviceAvailability(name, avail, null);
    }

    public boolean getAvailability(String name) {
        Boolean b = availMap.get(name);
        return (b == null || b);
    }
}
