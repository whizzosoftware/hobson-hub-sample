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
import com.whizzosoftware.hobson.api.property.PropertyConstraintType;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.*;

public class SampleCameraDevice extends AbstractDeviceProxy {
    public static final String CONFIG_USERNAME = "username";
    public static final String CONFIG_PASSWORD = "password";

    public SampleCameraDevice(HobsonPlugin plugin, String id) {
        super(plugin, id, "Sample Camera");
    }

    @Override
    public void onStartup(PropertyContainer config) {
        super.onStartup(config);

        setVariableValue(VariableConstants.IMAGE_STATUS_URL, "http://hobson-automation.com/img/security-example.jpg", System.currentTimeMillis());
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public void onDeviceConfigurationUpdate(PropertyContainer config) {

    }

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.CAMERA;
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
        return "Sample Camera";
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.IMAGE_STATUS_URL;
    }

    @Override
    public DeviceVariableDescription[] createVariableDescriptions() {
        return new DeviceVariableDescription[] {
            createDeviceVariableDescription(VariableConstants.IMAGE_STATUS_URL, DeviceVariableDescription.Mask.READ_ONLY, VariableMediaType.IMAGE_JPG)
        };
    }

    @Override
    protected TypedProperty[] createConfigurationPropertyTypes() {
        return new TypedProperty[] {
            new TypedProperty.Builder(CONFIG_USERNAME, "Username", "A username that can access the camera", TypedProperty.Type.STRING).
                constraint(PropertyConstraintType.required, true).
                build(),
            new TypedProperty.Builder(CONFIG_PASSWORD, "Password", "The password for the user", TypedProperty.Type.SECURE_STRING).
                constraint(PropertyConstraintType.required, true).
                build()
        };
    }

    @Override
    public void onSetVariable(String name, Object value) {
    }
}
