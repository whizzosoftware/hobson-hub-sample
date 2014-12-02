package com.whizzosoftware.hobson.sample;

import com.whizzosoftware.hobson.api.device.AbstractHobsonDevice;
import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.VariableConstants;

public class SampleTraditionalThermostat extends AbstractHobsonDevice {
    public SampleTraditionalThermostat(HobsonPlugin plugin, String id) {
        super(plugin, id);
    }

    @Override
    public void onStartup() {
        publishVariable(VariableConstants.TEMP_F, 73, HobsonVariable.Mask.READ_ONLY);
        publishVariable(VariableConstants.TARGET_COOL_TEMP_F, 73, HobsonVariable.Mask.READ_WRITE);
        publishVariable(VariableConstants.TARGET_HEAT_TEMP_F, 70, HobsonVariable.Mask.READ_WRITE);
        publishVariable(VariableConstants.TSTAT_MODE, "OFF", HobsonVariable.Mask.READ_WRITE);
        publishVariable(VariableConstants.TSTAT_FAN_MODE, "AUTO", HobsonVariable.Mask.READ_WRITE);
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public DeviceType getType() {
        return DeviceType.THERMOSTAT;
    }

    @Override
    public String getDefaultName() {
        return "Traditional Thermostat";
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.TEMP_F;
    }

    @Override
    public void onSetVariable(String name, Object value) {
        fireVariableUpdateNotification(name, value);
    }
}
