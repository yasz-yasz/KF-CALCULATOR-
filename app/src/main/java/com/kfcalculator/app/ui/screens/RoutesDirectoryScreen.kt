package com.kfcalculator.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
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
import com.kfcalculator.app.data.AirportRepository
import com.kfcalculator.app.data.AwardChartData
import com.kfcalculator.app.model.Airport
import com.kfcalculator.app.model.CabinClass
import com.kfcalculator.app.model.KrisFlyerZone
import com.kfcalculator.app.model.PremiumProductType
import com.kfcalculator.app.ui.theme.SiaGoldDark
import com.kfcalculator.app.ui.theme.SiaGoldSoft

private enum class RouteFilter(val label: String) {
    ALL("Semua Rute"),
    SUITES("A380 Suites (7)"),
    FIRST_77W("777-300ER First"),
    FIFTH_FREEDOM("Fifth-Freedom")
}

@Composable
fun RoutesDirectoryScreen(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(RouteFilter.ALL) }
    var selectedZone by remember { mutableStateOf<KrisFlyerZone?>(null) }

    val filteredAirports = remember(searchQuery, selectedFilter, selectedZone) {
        AirportRepository.airports.filter { airport ->
            if (airport.code == "SIN" && selectedFilter != RouteFilter.ALL) return@filter false

            val matchesSearch = searchQuery.isBlank() ||
                airport.code.contains(searchQuery, ignoreCase = true) ||
                airport.city.contains(searchQuery, ignoreCase = true) ||
                airport.country.contains(searchQuery, ignoreCase = true)

            val matchesFilter = when (selectedFilter) {
                RouteFilter.ALL -> true
                RouteFilter.SUITES -> airport.premiumProduct == PremiumProductType.SUITES_A380 ||
                    airport.premiumProduct == PremiumProductType.BOTH_SUITES_AND_FIRST
                RouteFilter.FIRST_77W -> airport.premiumProduct == PremiumProductType.FIRST_77W ||
                    airport.premiumProduct == PremiumProductType.BOTH_SUITES_AND_FIRST
                RouteFilter.FIFTH_FREEDOM -> false
            }

            val matchesZone = selectedZone == null || airport.zone == selectedZone
            matchesSearch && matchesFilter && matchesZone
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Jaringan Rute Singapore Airlines",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${AirportRepository.airports.size} destinasi SQ di 13 Zona KrisFlyer • 7 rute A380 Suites • 14+ destinasi First Class",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Cari bandara, kota, atau negara...") },
                leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(RouteFilter.entries) { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter.label) }
                    )
                }
            }
        }

        if (selectedFilter != RouteFilter.FIFTH_FREEDOM) {
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
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
            }
        }

        if (selectedFilter == RouteFilter.FIFTH_FREEDOM) {
            items(AirportRepository.fifthFreedomRoutes) { route ->
                val origin = AirportRepository.getByCode(route.originCode)
                val dest = AirportRepository.getByCode(route.destinationCode)
                val rates = AwardChartData.getRates(origin.zone, dest.zone)

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${route.originCode} ↔ ${route.destinationCode}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Surface(color = SiaGoldSoft, shape = RoundedCornerShape(8.dp)) {
                                Text(
                                    text = route.flightNumbers,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SiaGoldDark
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${origin.city} ↔ ${dest.city} • ${route.aircraft}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = route.note,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Saver OW — Eko: ${formatMiles(rates.economySaver)} | Biz: ${formatMiles(rates.businessSaver)}" +
                                if (route.hasFirstClass) " | First: ${formatMiles(rates.firstSaver)}" else "",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        } else {
            items(filteredAirports, key = { it.code }) { airport ->
                AirportDirectoryCard(airport = airport)
            }
        }
    }
}

@Composable
private fun AirportDirectoryCard(airport: Airport) {
    val ratesFromSin = AwardChartData.getRates(KrisFlyerZone.ZONE_1, airport.zone)

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = airport.code,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${airport.city}, ${airport.country}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${airport.zone.title} (${airport.zone.regionLabel})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (airport.code != "SIN") {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Dari SIN (Saver OW): Eko ${formatMiles(ratesFromSin.saverFor(CabinClass.ECONOMY))} • Biz ${formatMiles(ratesFromSin.saverFor(CabinClass.BUSINESS))}" +
                        if (airport.premiumProduct != PremiumProductType.NONE) {
                            " • First/Suites ${formatMiles(ratesFromSin.saverFor(CabinClass.FIRST_SUITES))}"
                        } else "",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (airport.premiumProduct != PremiumProductType.NONE && airport.code != "SIN") {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(color = SiaGoldSoft, shape = RoundedCornerShape(8.dp)) {
                    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
                        Text(
                            text = airport.premiumProduct.label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SiaGoldDark
                        )
                        airport.suitesFlights?.let {
                            Text(
                                text = "Suites: $it",
                                style = MaterialTheme.typography.labelSmall,
                                color = SiaGoldDark
                            )
                        }
                        airport.firstFlights?.let {
                            Text(
                                text = "First: $it",
                                style = MaterialTheme.typography.labelSmall,
                                color = SiaGoldDark
                            )
                        }
                    }
                }
            }

            airport.specialNote?.let { note ->
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = note,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
