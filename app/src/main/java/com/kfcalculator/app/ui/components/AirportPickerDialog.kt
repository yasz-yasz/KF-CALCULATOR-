package com.kfcalculator.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kfcalculator.app.data.AirportRepository
import com.kfcalculator.app.model.Airport
import com.kfcalculator.app.model.KrisFlyerZone
import com.kfcalculator.app.model.PremiumProductType
import com.kfcalculator.app.ui.theme.SiaGoldDark
import com.kfcalculator.app.ui.theme.SiaGoldSoft

@Composable
fun AirportPickerDialog(
    title: String,
    onDismiss: () -> Unit,
    onSelect: (Airport) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var selectedZone by remember { mutableStateOf<KrisFlyerZone?>(null) }
    var onlyFirstSuites by remember { mutableStateOf(false) }

    val filtered = remember(query, selectedZone, onlyFirstSuites) {
        AirportRepository.airports.filter { airport ->
            val matchesQuery = query.isBlank() ||
                airport.code.contains(query, ignoreCase = true) ||
                airport.city.contains(query, ignoreCase = true) ||
                airport.country.contains(query, ignoreCase = true) ||
                airport.airportName.contains(query, ignoreCase = true)
            val matchesZone = selectedZone == null || airport.zone == selectedZone
            val matchesPremium = !onlyFirstSuites || airport.premiumProduct != PremiumProductType.NONE
            matchesQuery && matchesZone && matchesPremium
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.86f),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Rounded.Close, contentDescription = "Tutup")
                    }
                }

                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Cari kode IATA, kota, atau negara (mis. HKG, RUH, Tokyo)") },
                    leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        FilterChip(
                            selected = onlyFirstSuites,
                            onClick = { onlyFirstSuites = !onlyFirstSuites },
                            label = { Text("First / Suites") }
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedZone == null,
                            onClick = { selectedZone = null },
                            label = { Text("Semua Zona") }
                        )
                    }
                    items(KrisFlyerZone.entries) { zone ->
                        FilterChip(
                            selected = selectedZone == zone,
                            onClick = { selectedZone = if (selectedZone == zone) null else zone },
                            label = { Text("Z${zone.number}") }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${filtered.size} bandara tersedia",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(filtered, key = { it.code }) { airport ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelect(airport) }
                                .padding(vertical = 10.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = airport.code,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${airport.city}, ${airport.country}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Text(
                                    text = "${airport.zone.title} • ${airport.airportName}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                if (airport.premiumProduct != PremiumProductType.NONE && airport.code != "SIN") {
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Surface(
                                        color = SiaGoldSoft,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = airport.premiumProduct.label,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = SiaGoldDark
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
                    }
                }
            }
        }
    }
}
