package com.whizzosoftware.hobson.sample;

import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.device.proxy.AbstractDeviceProxy;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.DeviceVariableContext;
import com.whizzosoftware.hobson.api.variable.DeviceVariableDescription;
import com.whizzosoftware.hobson.api.variable.VariableConstants;

import java.util.HashMap;
import java.util.Map;

public class WeatherStationDevice extends AbstractDeviceProxy {

    public WeatherStationDevice(HobsonPlugin plugin, String id) {
        super(plugin, id, "Weather Station");
    }

    @Override
    public void onStartup(PropertyContainer config) {
        super.onStartup(config);

        long now = System.currentTimeMillis();

        Map<String,Object> updates = new HashMap<>();
        updates.put(VariableConstants.OUTDOOR_TEMP_F, 74);
        updates.put(VariableConstants.INDOOR_TEMP_F, 76);
        updates.put(VariableConstants.INDOOR_RELATIVE_HUMIDITY, 32);
        updates.put(VariableConstants.OUTDOOR_RELATIVE_HUMIDITY, 30);
        updates.put(VariableConstants.WIND_SPEED_MPH, 2);
        updates.put(VariableConstants.WIND_DIRECTION_DEGREES, 270);
        setVariableValues(updates);
    }

    @Override
    protected TypedProperty[] createConfigurationPropertyTypes() {
        return new TypedProperty[] {
            new TypedProperty.Builder("foo", "Foo", "The foo thang", TypedProperty.Type.STRING).build()
        };
    }

    @Override
    public DeviceType getDeviceType() {
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
    public DeviceVariableDescription[] createVariableDescriptions() {
        return new DeviceVariableDescription[] {
            createDeviceVariableDescription(VariableConstants.OUTDOOR_TEMP_F, DeviceVariableDescription.Mask.READ_ONLY),
            createDeviceVariableDescription(VariableConstants.INDOOR_TEMP_F, DeviceVariableDescription.Mask.READ_ONLY),
            createDeviceVariableDescription(VariableConstants.INDOOR_RELATIVE_HUMIDITY, DeviceVariableDescription.Mask.READ_ONLY),
            createDeviceVariableDescription(VariableConstants.OUTDOOR_RELATIVE_HUMIDITY, DeviceVariableDescription.Mask.READ_ONLY),
            createDeviceVariableDescription(VariableConstants.WIND_SPEED_MPH, DeviceVariableDescription.Mask.READ_ONLY),
            createDeviceVariableDescription(VariableConstants.WIND_DIRECTION_DEGREES, DeviceVariableDescription.Mask.READ_ONLY)
        };
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public void onDeviceConfigurationUpdate(PropertyContainer config) {
    }

    @Override
    public void onSetVariable(String variableName, Object value) {
    }
}
