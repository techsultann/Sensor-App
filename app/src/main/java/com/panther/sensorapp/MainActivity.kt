package com.panther.sensorapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.panther.sensorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val deviceSensors: MutableList<Sensor>? = sensorManager.getSensorList(Sensor.TYPE_ALL)

        val sensorListTextView = binding.sensorsList

        if (deviceSensors != null) {
            for (sensor in deviceSensors) {
                val sensorInfo = "${sensor.name} - ${sensor.version}"
                sensorListTextView.append("$sensorInfo \n")
            }
        }

        // Light Sensor
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (lightSensor != null) {
            sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            binding.value.text = "Light sensor not available"
        }

        // Proximity Sensor
        val proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if (proximitySensor != null) {
            sensorManager.registerListener(proximitySensorListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            binding.value2.text = "Proximity sensor not available"
        }

    }

    private val lightSensorListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            val lightValue = event?.values?.get(0)
            binding.value.text = "$lightValue lux"
        }
    }

    private val proximitySensorListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            val proximityValue = event?.values?.get(0)
            binding.value2.text = "$proximityValue cm"
        }
    }


}
