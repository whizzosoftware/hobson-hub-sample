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
import com.whizzosoftware.hobson.api.variable.VariableProxyType;

public class SampleCameraDevice extends AbstractHobsonDevice {
    public static final String CONFIG_USERNAME = "username";
    public static final String CONFIG_PASSWORD = "password";

    public SampleCameraDevice(HobsonPlugin plugin, String id) {
        super(plugin, id);
    }

    @Override
    public TypedProperty[] createConfigurationPropertyMetaData() {
        return new TypedProperty[] {
            new TypedProperty(CONFIG_USERNAME, "Username", "A username that can access the camera", TypedProperty.Type.STRING),
            new TypedProperty(CONFIG_PASSWORD, "Password", "The password for the user", TypedProperty.Type.SECURE_STRING)
        };
    }

    @Override
    public void onStartup(PropertyContainer config) {
        super.onStartup(config);

        publishVariable(VariableConstants.IMAGE_STATUS_URL, "http://hobson-automation.com/img/security-example.jpg", HobsonVariable.Mask.READ_ONLY, VariableProxyType.MEDIA_URL);
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public DeviceType getType() {
        return DeviceType.CAMERA;
    }

    @Override
    public String getDefaultName() {
        return "Security Camera";
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.IMAGE_STATUS_URL;
    }

    @Override
    public void onSetVariable(String name, Object value) {
    }
}
