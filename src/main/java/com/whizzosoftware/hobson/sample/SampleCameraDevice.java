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
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.VariableConstants;

public class SampleCameraDevice extends AbstractHobsonDevice {
    public SampleCameraDevice(HobsonPlugin plugin, String id) {
        super(plugin, id);
    }

    @Override
    public void onStartup() {
        publishVariable(VariableConstants.IMAGE_STATUS_URL, "http://hobson-automation.com/img/security-example.jpg", HobsonVariable.Mask.READ_ONLY);
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
