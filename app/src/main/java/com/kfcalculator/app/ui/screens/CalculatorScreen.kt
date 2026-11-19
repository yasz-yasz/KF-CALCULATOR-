package com.kfcalculator.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.CompareArrows
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Flight
import androidx.compose.material.icons.rounded.FlightLand
import androidx.compose.material.icons.rounded.FlightTakeoff
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kfcalculator.app.data.AirportRepository
import com.kfcalculator.app.data.AwardChartData
import com.kfcalculator.app.model.Airport
import com.kfcalculator.app.model.CabinClass
import com.kfcalculator.app.model.PremiumProductType
import com.kfcalculator.app.model.RouteCalculationResult
import com.kfcalculator.app.ui.components.AirportPickerDialog
import com.kfcalculator.app.ui.theme.SiaGold
import com.kfcalculator.app.ui.theme.SiaGoldDark
import com.kfcalculator.app.ui.theme.SiaGoldSoft
import com.kfcalculator.app.ui.theme.SiaNavy
import com.kfcalculator.app.ui.theme.SiaNavyLight
import com.kfcalculator.app.ui.theme.SuccessGreen
import com.kfcalculator.app.ui.theme.SuccessGreenSoft
import com.kfcalculator.app.ui.theme.WarningAmber
import com.kfcalculator.app.ui.theme.WarningAmberSoft
import java.text.NumberFormat
import java.util.Locale

private data class QuickPreset(
    val label: String,
    val codes: List<String>,
    val cabin: CabinClass
)

private val presets = listOf(
    QuickPreset("HKG–SIN–RUH (Biz)", listOf("HKG", "SIN", "RUH"), CabinClass.BUSINESS),
    QuickPreset("CGK–SIN (First)", listOf("CGK", "SIN"), CabinClass.FIRST_SUITES),
    QuickPreset("SIN–PVG (Suites)", listOf("SIN", "PVG"), CabinClass.FIRST_SUITES),
    QuickPreset("CGK–SIN–LHR (Suites)", listOf("CGK", "SIN", "LHR"), CabinClass.FIRST_SUITES),
    QuickPreset("FRA–JFK (5th Freedom)", listOf("FRA", "JFK"), CabinClass.FIRST_SUITES),
    QuickPreset("CGK–SIN–NRT–LAX", listOf("CGK", "SIN", "NRT", "LAX"), CabinClass.BUSINESS)
)

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    val waypoints = remember {
        mutableStateListOf(
            AirportRepository.getByCode("HKG"),
            AirportRepository.getByCode("SIN"),
            AirportRepository.getByCode("RUH")
        )
    }
    var selectedCabin by remember { mutableStateOf(CabinClass.BUSINESS) }
    var isRoundTrip by remember { mutableStateOf(false) }
    var passengerCount by remember { mutableIntStateOf(1) }
    var autoTransitSin by remember { mutableStateOf(true) }

    var editingWaypointIndex by remember { mutableStateOf<Int?>(null) }
    var isAddingTransit by remember { mutableStateOf(false) }

    val calculationResult = remember(
        waypoints.toList(),
        selectedCabin,
        isRoundTrip,
        passengerCount,
        autoTransitSin
    ) {
        AwardChartData.calculateRoute(
            rawWaypoints = waypoints.toList(),
            cabin = selectedCabin,
            isRoundTrip = isRoundTrip,
            passengerCount = passengerCount,
            autoInsertSinTransit = autoTransitSin
        )
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Quick Preset Chips
        item {
            Column {
                Text(
                    text = "Rute Populer (Klik Cepat)",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(presets) { preset ->
                        AssistChip(
                            onClick = {
                                waypoints.clear()
                                preset.codes.forEach { waypoints.add(AirportRepository.getByCode(it)) }
                                selectedCabin = preset.cabin
                            },
                            label = { Text(preset.label) }
                        )
                    }
                }
            }
        }

        // Route Builder Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Susun Rute Penerbangan",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Mendukung 1 leg nonstop atau multi-leg dalam 1 PNR",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        IconButton(
                            onClick = {
                                val reversed = waypoints.reversed()
                                waypoints.clear()
                                waypoints.addAll(reversed)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.SwapHoriz,
                                contentDescription = "Balikkan rute",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    waypoints.forEachIndexed { index, airport ->
                        val roleLabel = when (index) {
                            0 -> "Asal (Origin)"
                            waypoints.lastIndex -> "Tujuan Akhir (Destination)"
                            else -> "Transit $index"
                        }
                        val icon = when (index) {
                            0 -> Icons.Rounded.FlightTakeoff
                            waypoints.lastIndex -> Icons.Rounded.FlightLand
                            else -> Icons.Rounded.Flight
                        }

                        WaypointRow(
                            roleLabel = roleLabel,
                            airport = airport,
                            icon = icon,
                            canRemove = waypoints.size > 2 && index in 1 until waypoints.lastIndex,
                            onClick = { editingWaypointIndex = index },
                            onRemove = { waypoints.removeAt(index) }
                        )

                        if (index < waypoints.lastIndex) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { isAddingTransit = true },
                            enabled = waypoints.size < 5,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Rounded.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Tambah Transit")
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Otomatis sisipkan transit SIN",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Jika 2 kota tidak terhubung nonstop SQ",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = autoTransitSin,
                            onCheckedChange = { autoTransitSin = it }
                        )
                    }
                }
            }
        }

        // Cabin, Trip Type & Passenger Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Kelas Kabin & Opsi Perjalanan",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(CabinClass.entries) { cabin ->
                            FilterChip(
                                selected = selectedCabin == cabin,
                                onClick = { selectedCabin = cabin },
                                label = {
                                    Text(
                                        text = cabin.shortName,
                                        fontWeight = if (selectedCabin == cabin) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(
                                selected = !isRoundTrip,
                                onClick = { isRoundTrip = false },
                                label = { Text("Sekali Jalan") }
                            )
                            FilterChip(
                                selected = isRoundTrip,
                                onClick = { isRoundTrip = true },
                                label = { Text("Pulang Pergi") }
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable { if (passengerCount > 1) passengerCount-- }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("-", fontWeight = FontWeight.Bold)
                                }
                            }
                            Text(
                                text = "$passengerCount Pax",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable { if (passengerCount < 9) passengerCount++ }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("+", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Main Award Result Card (1 PNR: Saver, Advantage, Access)
        item {
            AwardResultHeroCard(result = calculationResult)
        }

        // Comparison Card (1 PNR vs Multi-PNR) when multi-leg
        if (calculationResult.isMultiLeg) {
            item {
                PnrComparisonCard(result = calculationResult)
            }
        }

        // Per-Leg Breakdown Card
        item {
            LegBreakdownCard(result = calculationResult)
        }

        // Stopover & Advisories Card
        item {
            AdvisoriesCard(result = calculationResult)
        }
    }

    // Dialog for editing existing waypoint
    editingWaypointIndex?.let { index ->
        val dialogTitle = when (index) {
            0 -> "Pilih Bandara Asal"
            waypoints.lastIndex -> "Pilih Bandara Tujuan"
            else -> "Pilih Bandara Transit $index"
        }
        AirportPickerDialog(
            title = dialogTitle,
            onDismiss = { editingWaypointIndex = null },
            onSelect = { chosen ->
                waypoints[index] = chosen
                editingWaypointIndex = null
            }
        )
    }

    // Dialog for inserting a new transit stop before destination
    if (isAddingTransit) {
        AirportPickerDialog(
            title = "Tambah Bandara Transit",
            onDismiss = { isAddingTransit = false },
            onSelect = { chosen ->
                waypoints.add(waypoints.lastIndex, chosen)
                isAddingTransit = false
            }
        )
    }
}

@Composable
private fun WaypointRow(
    roleLabel: String,
    airport: Airport,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    canRemove: Boolean,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.45f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = roleLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = airport.code,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${airport.city} (${airport.zone.title})",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            if (canRemove) {
                IconButton(onClick = onRemove, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Hapus transit",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AwardResultHeroCard(result: RouteCalculationResult) {
    val routeString = result.waypoints.joinToString(" → ") { it.code }
    val origin = result.waypoints.first()
    val destination = result.waypoints.last()
    val tripLabel = if (result.isRoundTrip) "Pulang Pergi (PP)" else "Sekali Jalan (One-Way)"

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(listOf(SiaNavy, SiaNavyLight))
                )
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = SiaGold.copy(alpha = 0.22f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "1 PNR • ${result.cabin.displayName.uppercase()}",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = SiaGold
                    )
                }
                Text(
                    text = AwardChartData.CHART_EFFECTIVE_DATE,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.72f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = routeString,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                text = "${origin.zone.title} → ${destination.zone.title} • $tripLabel • ${result.passengerCount} Penumpang",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3 Tiers: Saver, Advantage, Access
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TierBox(
                    title = "SAVER",
                    milesText = formatMiles(result.singlePnrSaverTotal),
                    subtext = if (result.passengerCount > 1) "${formatMiles(result.singlePnrSaverPerPax)}/pax" else "Tarif termurah",
                    highlight = true,
                    modifier = Modifier.weight(1f)
                )
                TierBox(
                    title = "ADVANTAGE",
                    milesText = formatMiles(result.singlePnrAdvantageTotal),
                    subtext = if (result.passengerCount > 1) "${formatMiles(result.singlePnrAdvantagePerPax)}/pax" else "+ Gratis stopover",
                    highlight = false,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // KrisFlyer Access (Seatasnya / Dynamic Tier)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Color.White.copy(alpha = 0.10f),
                border = BorderStroke(1.dp, SiaGold.copy(alpha = 0.45f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "ACCESS (SEATASNYA / DINAMIS)",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SiaGold
                        )
                        Text(
                            text = "Tarif dinamis mengikuti harga kursi komersial saat Saver & Advantage habis.",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.78f)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "> ${formatMiles(result.singlePnrAccessMinTotal)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "est. s.d. ${formatMiles(result.singlePnrAccessMaxTotal)}+",
                            style = MaterialTheme.typography.labelSmall,
                            color = SiaGold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "*Belum termasuk pajak bandara (airport taxes) dan biaya tambahan.",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.65f)
            )
        }
    }
}

@Composable
private fun TierBox(
    title: String,
    milesText: String,
    subtext: String,
    highlight: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = if (highlight) SiaGold else Color.White.copy(alpha = 0.13f)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = if (highlight) SiaNavy else SiaGold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = milesText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = if (highlight) SiaNavy else Color.White
            )
            Text(
                text = "miles • $subtext",
                style = MaterialTheme.typography.labelSmall,
                color = if (highlight) SiaNavy.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.75f)
            )
        }
    }
}

@Composable
private fun PnrComparisonCard(result: RouteCalculationResult) {
    val diffTotal = result.saverDifferencePerPax * result.passengerCount
    val advDiffTotal = result.advantageDifferencePerPax * result.passengerCount

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.CompareArrows,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Perbandingan 1 PNR vs ${result.legBreakdown.size} PNR Terpisah",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ComparisonRow(
                label = "1 PNR (Transit < 24 jam)",
                saver = "${formatMiles(result.singlePnrSaverTotal)} miles",
                advantage = "${formatMiles(result.singlePnrAdvantageTotal)} miles",
                emphasize = result.isSinglePnrCheaperSaver
            )
            Spacer(modifier = Modifier.height(6.dp))
            ComparisonRow(
                label = "${result.legBreakdown.size} PNR (Tiket Terpisah)",
                saver = "${formatMiles(result.splitPnrSaverTotal)} miles",
                advantage = "${formatMiles(result.splitPnrAdvantageTotal)} miles",
                emphasize = !result.isSinglePnrCheaperSaver
            )

            Spacer(modifier = Modifier.height(10.dp))

            val verdictBg = if (result.isSinglePnrCheaperSaver) SuccessGreenSoft else WarningAmberSoft
            val verdictColor = if (result.isSinglePnrCheaperSaver) SuccessGreen else WarningAmber
            val verdictText = if (result.isSinglePnrCheaperSaver) {
                "1 PNR lebih hemat ${formatMiles(diffTotal)} miles (Saver) dan ${formatMiles(advDiffTotal)} miles (Advantage) dibanding memecah tiket."
            } else {
                "Pisah PNR justru lebih hemat ${formatMiles(diffTotal)} miles pada Saver, namun bagasi & perlindungan keterlambatan terpisah."
            }

            Surface(
                color = verdictBg,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = null,
                        tint = verdictColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = verdictText,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = verdictColor
                    )
                }
            }
        }
    }
}

@Composable
private fun ComparisonRow(
    label: String,
    saver: String,
    advantage: String,
    emphasize: Boolean
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (emphasize) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                modifier = Modifier.weight(1.2f),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (emphasize) FontWeight.Bold else FontWeight.Medium
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Saver: $saver",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Adv: $advantage",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun LegBreakdownCard(result: RouteCalculationResult) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Rincian Segmen & Info Armada",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            result.legBreakdown.forEachIndexed { idx, leg ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${leg.legIndex}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "${leg.origin.code} → ${leg.destination.code}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${leg.origin.zone.title} → ${leg.destination.zone.title}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${formatMiles(leg.saverMilesOneWay)} miles",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Adv: ${formatMiles(leg.advantageMilesOneWay)} (OW)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (leg.premiumProduct != PremiumProductType.NONE) {
                            Surface(
                                color = SiaGoldSoft,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = leg.premiumProduct.label,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SiaGoldDark
                                )
                            }
                        } else {
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = leg.aircraftCabinInfo,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        if (leg.isFifthFreedom) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "Fifth-Freedom",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                    leg.flightHint?.let { hint ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Penerbangan First/Suites: $hint",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    leg.specialNotice?.let { note ->
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = note,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (idx < result.legBreakdown.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                    )
                }
            }
        }
    }
}

@Composable
private fun AdvisoriesCard(result: RouteCalculationResult) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Aturan Stopover & Catatan Rute",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = result.stopoverSummary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            result.advisories.forEach { advisory ->
                Surface(
                    color = WarningAmberSoft,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = null,
                            tint = WarningAmber,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = advisory,
                            style = MaterialTheme.typography.bodySmall,
                            color = WarningAmber
                        )
                    }
                }
            }
        }
    }
}

fun formatMiles(miles: Int): String {
    return NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID")).format(miles)
}
