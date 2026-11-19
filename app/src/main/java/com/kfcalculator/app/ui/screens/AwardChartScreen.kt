package com.kfcalculator.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kfcalculator.app.data.AwardChartData
import com.kfcalculator.app.model.CabinClass
import com.kfcalculator.app.model.KrisFlyerZone
import com.kfcalculator.app.ui.theme.SiaGold
import com.kfcalculator.app.ui.theme.SiaNavy

@Composable
fun AwardChartScreen(modifier: Modifier = Modifier) {
    var originZone by remember { mutableStateOf(KrisFlyerZone.ZONE_1) }
    var selectedCabin by remember { mutableStateOf(CabinClass.BUSINESS) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text(
                    text = "KrisFlyer Award Chart (One-Way)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${AwardChartData.CHART_EFFECTIVE_DATE} • Tarif Singapore Airlines (SQ) dalam miles",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Pilih Zona Asal:",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(KrisFlyerZone.entries) { zone ->
                        FilterChip(
                            selected = originZone == zone,
                            onClick = { originZone = zone },
                            label = { Text("Z${zone.number} (${zone.regionLabel.take(12)})") }
                        )
                    }
                }

                Text(
                    text = "Pilih Kelas Kabin:",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(CabinClass.entries) { cabin ->
                        FilterChip(
                            selected = selectedCabin == cabin,
                            onClick = { selectedCabin = cabin },
                            label = { Text(cabin.displayName) }
                        )
                    }
                }
            }
        }

        item {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "${originZone.title}: ${originZone.regionLabel}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = originZone.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SiaNavy)
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Zona Tujuan",
                            modifier = Modifier.weight(1.4f),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Saver",
                            modifier = Modifier.weight(0.9f),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = SiaGold,
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = "Advantage",
                            modifier = Modifier.weight(0.9f),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = "Access*",
                            modifier = Modifier.weight(0.9f),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.85f),
                            textAlign = TextAlign.End
                        )
                    }

                    KrisFlyerZone.entries.forEachIndexed { idx, destZone ->
                        if (originZone == KrisFlyerZone.ZONE_1 && destZone == KrisFlyerZone.ZONE_1) {
                            return@forEachIndexed
                        }
                        val rates = AwardChartData.getRates(originZone, destZone)
                        val saver = rates.saverFor(selectedCabin)
                        val adv = rates.advantageFor(selectedCabin)
                        val (accMin, _) = rates.accessEstimateRangeFor(selectedCabin)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1.4f)) {
                                Text(
                                    text = destZone.title,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = destZone.regionLabel,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = formatMiles(saver),
                                modifier = Modifier.weight(0.9f),
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.End
                            )
                            Text(
                                text = formatMiles(adv),
                                modifier = Modifier.weight(0.9f),
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.End
                            )
                            Text(
                                text = ">${formatMiles(accMin)}",
                                modifier = Modifier.weight(0.9f),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.End
                            )
                        }

                        if (idx < KrisFlyerZone.entries.lastIndex) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "*KrisFlyer Access bersifat dinamis (di atas tarif Advantage) dan mengikuti ketersediaan kursi komersial real-time saat pemesanan.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
