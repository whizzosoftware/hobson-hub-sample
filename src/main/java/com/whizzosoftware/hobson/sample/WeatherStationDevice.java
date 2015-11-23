package com.whizzosoftware.hobson.sample;

import com.whizzosoftware.hobson.api.device.AbstractHobsonDevice;
import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.VariableConstants;

public class WeatherStationDevice extends AbstractHobsonDevice {

    public WeatherStationDevice(HobsonPlugin plugin, String id) {
        super(plugin, id);
    }

    @Override
    public void onStartup(PropertyContainer config) {
        super.onStartup(config);

        publishVariable(VariableConstants.OUTDOOR_TEMP_F, 74, HobsonVariable.Mask.READ_ONLY);
        publishVariable(VariableConstants.INDOOR_TEMP_F, 76, HobsonVariable.Mask.READ_ONLY);
        publishVariable(VariableConstants.INDOOR_RELATIVE_HUMIDITY, 32, HobsonVariable.Mask.READ_ONLY);
        publishVariable(VariableConstants.OUTDOOR_RELATIVE_HUMIDITY, 30, HobsonVariable.Mask.READ_ONLY);
        publishVariable(VariableConstants.WIND_SPEED_MPH, 2, HobsonVariable.Mask.READ_ONLY);
        publishVariable(VariableConstants.WIND_DIRECTION_DEGREES, 270, HobsonVariable.Mask.READ_ONLY);
    }

    @Override
    public String getDefaultName() {
        return "Weather Station";
    }

    @Override
    protected TypedProperty[] createSupportedProperties() {
        return null;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.WEATHER_STATION;
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
    public void onSetVariable(String variableName, Object value) {

    }
}
