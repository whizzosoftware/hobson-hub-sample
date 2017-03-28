package com.whizzosoftware.hobson.sample;

import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.device.proxy.AbstractHobsonDeviceProxy;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.VariableConstants;
import com.whizzosoftware.hobson.api.variable.VariableMask;

import java.util.Map;

public class WeatherStationDevice extends AbstractHobsonDeviceProxy {

    public WeatherStationDevice(HobsonPlugin plugin, String id) {
        super(plugin, id, "Weather Station", DeviceType.WEATHER_STATION);
    }

    @Override
    public void onStartup(String name, Map<String,Object> config) {
        long now = System.currentTimeMillis();
        publishVariables(
            createDeviceVariable(VariableConstants.OUTDOOR_TEMP_F, VariableMask.READ_ONLY, 74, now),
            createDeviceVariable(VariableConstants.INDOOR_TEMP_F, VariableMask.READ_ONLY, 76, now),
            createDeviceVariable(VariableConstants.INDOOR_RELATIVE_HUMIDITY, VariableMask.READ_ONLY, 32, now),
            createDeviceVariable(VariableConstants.OUTDOOR_RELATIVE_HUMIDITY, VariableMask.READ_ONLY, 30, now),
            createDeviceVariable(VariableConstants.WIND_SPEED_MPH, VariableMask.READ_ONLY, 2, now),
            createDeviceVariable(VariableConstants.WIND_DIRECTION_DEGREES, VariableMask.READ_ONLY, 270, now)
        );
    }

    @Override
    protected TypedProperty[] getConfigurationPropertyTypes() {
        return new TypedProperty[] {
            new TypedProperty.Builder("foo", "Foo", "The foo thang", TypedProperty.Type.STRING).build()
        };
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
        return "Sample Weather Station";
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.OUTDOOR_TEMP_F;
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public void onDeviceConfigurationUpdate(Map<String,Object> config) {
    }

    @Override
    public void onSetVariables(Map<String,Object> values) {
    }
}
