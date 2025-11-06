package ru.kryu.weathertest.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.kryu.weathertest.R
import ru.kryu.weathertest.domain.model.CurrentWeatherInfo
import kotlin.math.roundToInt

@Composable
fun CurrentWeatherCard(
    current: CurrentWeatherInfo,
    locationName: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = locationName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = current.conditionIcon,
                    contentDescription = current.condition,
                    modifier = Modifier.size(96.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "${current.tempC.roundToInt()}°",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Light
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = current.condition,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.feels_like, current.feelsLikeC.roundToInt()),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherParameter(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.WaterDrop,
                            contentDescription = stringResource(R.string.humidity),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = stringResource(R.string.humidity),
                    value = stringResource(R.string.humidity_value, current.humidity)
                )

                WeatherParameter(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Air,
                            contentDescription = stringResource(R.string.wind),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = stringResource(R.string.wind),
                    value = stringResource(R.string.wind_value, current.windKph.roundToInt())
                )

                WeatherParameter(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Compress,
                            contentDescription = stringResource(R.string.pressure),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = stringResource(R.string.pressure),
                    value = stringResource(R.string.pressure_value, current.pressureMb.roundToInt())
                )
            }
        }
    }
}

@Composable
private fun WeatherParameter(
    icon: @Composable () -> Unit,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}