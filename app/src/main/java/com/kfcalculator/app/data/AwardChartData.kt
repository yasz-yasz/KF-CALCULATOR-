package com.kfcalculator.app.data

import com.kfcalculator.app.model.Airport
import com.kfcalculator.app.model.AwardRates
import com.kfcalculator.app.model.CabinClass
import com.kfcalculator.app.model.KrisFlyerZone
import com.kfcalculator.app.model.LegCalculation
import com.kfcalculator.app.model.PremiumProductType
import com.kfcalculator.app.model.RouteCalculationResult
import kotlin.math.abs

/**
 * KrisFlyer Award Chart (Singapore Airlines Metal) - Efektif 1 November 2025.
 * Tarif berlaku sekali jalan (one-way) per penumpang berdasarkan pasangan zona (1-13).
 */
object AwardChartData {

    const val CHART_EFFECTIVE_DATE = "Efektif 1 November 2025"

    private val matrix: Map<Pair<Int, Int>, AwardRates> = mapOf(
        (1 to 1) to AwardRates(8000, 16500, 14500, 24000, 22000, 42500, 32000, 58500),
        (1 to 2) to AwardRates(8000, 16500, 14500, 24000, 22000, 42500, 32000, 58500),
        (1 to 3) to AwardRates(13000, 24000, 19500, 31500, 25000, 47000, 39500, 68000),
        (1 to 4) to AwardRates(15500, 29000, 26500, 40500, 35500, 57500, 47000, 79000),
        (1 to 5) to AwardRates(20500, 37000, 34500, 52000, 45000, 79000, 61500, 103500),
        (1 to 6) to AwardRates(19000, 36000, 34500, 51500, 45000, 74500, 61500, 99500),
        (1 to 7) to AwardRates(25500, 47000, 45000, 65000, 54500, 89500, 81000, 138000),
        (1 to 8) to AwardRates(20500, 37000, 34500, 51500, 42500, 74500, 61500, 103500),
        (1 to 9) to AwardRates(29000, 52000, 53500, 76500, 72000, 109000, 98000, 178000),
        (1 to 10) to AwardRates(31500, 53000, 54000, 85000, 68000, 122000, 98000, 178000),
        (1 to 11) to AwardRates(44000, 73500, 74500, 110500, 108500, 141500, 148000, 259000),
        (1 to 12) to AwardRates(44000, 73500, 78500, 115000, 112500, 148000, 156000, 266500),
        (1 to 13) to AwardRates(46000, 78500, 84500, 121000, 117000, 165000, 156000, 279000),
        (2 to 2) to AwardRates(13000, 24000, 19500, 31500, 25000, 47000, 39500, 68000),
        (2 to 3) to AwardRates(14500, 26500, 24500, 37500, 30500, 53500, 44500, 76000),
        (2 to 4) to AwardRates(20000, 36000, 34000, 50500, 43000, 72500, 57500, 98000),
        (2 to 5) to AwardRates(24500, 43000, 40500, 59500, 50500, 86500, 69500, 118500),
        (2 to 6) to AwardRates(23000, 41500, 39500, 58000, 50500, 83000, 69500, 115000),
        (2 to 7) to AwardRates(29000, 52000, 50500, 72500, 61000, 99500, 89500, 153000),
        (2 to 8) to AwardRates(24500, 43000, 40500, 59500, 49500, 84500, 69500, 118500),
        (2 to 9) to AwardRates(32500, 58000, 59500, 85000, 77500, 119000, 108500, 194000),
        (2 to 10) to AwardRates(36500, 62000, 62500, 98000, 78000, 139000, 112000, 202000),
        (2 to 11) to AwardRates(46000, 78000, 79000, 116500, 112500, 152000, 153500, 270000),
        (2 to 12) to AwardRates(46000, 78000, 83000, 121000, 117000, 159000, 161500, 278000),
        (2 to 13) to AwardRates(48500, 83000, 89000, 127000, 121500, 174000, 161500, 290000),
        (3 to 3) to AwardRates(18500, 33000, 29500, 45000, 38000, 64500, 54000, 92000),
        (3 to 4) to AwardRates(22000, 39500, 37000, 54500, 46000, 77500, 63000, 106000),
        (3 to 5) to AwardRates(26000, 46000, 43500, 64000, 54500, 92000, 75000, 127000),
        (3 to 6) to AwardRates(25000, 44500, 42500, 62500, 54500, 88500, 75000, 123000),
        (3 to 7) to AwardRates(31000, 55000, 53500, 77000, 65000, 105500, 95500, 162000),
        (3 to 8) to AwardRates(26000, 46000, 43500, 64000, 53500, 89500, 75000, 127000),
        (3 to 9) to AwardRates(34500, 61000, 62500, 89000, 81500, 125500, 114000, 203000),
        (3 to 10) to AwardRates(39500, 67000, 68000, 106000, 85000, 151000, 122000, 218000),
        (3 to 11) to AwardRates(48500, 82500, 83000, 122000, 116500, 159000, 159500, 279000),
        (3 to 12) to AwardRates(48500, 82500, 87000, 126500, 121000, 166000, 167500, 287000),
        (3 to 13) to AwardRates(50500, 87500, 93000, 132500, 125500, 181000, 167500, 299000),
        (4 to 4) to AwardRates(24500, 43000, 40500, 59500, 51500, 85500, 70000, 118000),
        (4 to 5) to AwardRates(29000, 51000, 48000, 70000, 60500, 101500, 84000, 141000),
        (4 to 6) to AwardRates(29000, 51000, 48000, 70000, 62500, 103000, 86000, 144000),
        (4 to 7) to AwardRates(35500, 62000, 60000, 86500, 74500, 121000, 108000, 183000),
        (4 to 8) to AwardRates(29000, 51000, 48000, 70000, 61000, 101500, 84000, 141000),
        (4 to 9) to AwardRates(38000, 67000, 67500, 96000, 89000, 138000, 124500, 221000),
        (4 to 10) to AwardRates(47000, 80000, 79000, 125000, 100000, 177000, 145000, 255000),
        (4 to 11) to AwardRates(52000, 88000, 89500, 131500, 124000, 171000, 169000, 295000),
        (4 to 12) to AwardRates(46000, 78000, 83000, 121000, 117000, 160000, 161500, 281000),
        (4 to 13) to AwardRates(52000, 89000, 95500, 136500, 129000, 188000, 175000, 310000),
        (5 to 5) to AwardRates(32500, 57000, 54000, 78500, 69500, 115000, 96000, 161000),
        (5 to 6) to AwardRates(33500, 59000, 56000, 81500, 72000, 119000, 99500, 167000),
        (5 to 7) to AwardRates(39500, 69500, 66500, 95500, 83500, 135500, 121000, 205000),
        (5 to 8) to AwardRates(33500, 59000, 56000, 81500, 70000, 117000, 96000, 161000),
        (5 to 9) to AwardRates(41500, 73000, 73500, 104500, 96500, 150000, 135000, 240000),
        (5 to 10) to AwardRates(50500, 86000, 85500, 134000, 108500, 192000, 156000, 276000),
        (5 to 11) to AwardRates(54500, 92500, 94000, 138000, 129500, 180000, 177000, 310000),
        (5 to 12) to AwardRates(52000, 88000, 91000, 133000, 125500, 174000, 172000, 300000),
        (5 to 13) to AwardRates(56500, 96500, 101500, 145000, 136500, 199000, 184000, 326000),
        (6 to 6) to AwardRates(31500, 56000, 54000, 78500, 69500, 113000, 96000, 158000),
        (6 to 7) to AwardRates(39500, 69500, 66500, 95500, 83500, 134500, 121000, 204000),
        (6 to 8) to AwardRates(33500, 59000, 56000, 81500, 70000, 116000, 96000, 160000),
        (6 to 9) to AwardRates(41500, 73000, 73500, 104500, 96500, 149000, 135000, 238000),
        (6 to 10) to AwardRates(49000, 83500, 83000, 130000, 105500, 186000, 152000, 268000),
        (6 to 11) to AwardRates(54500, 92500, 94000, 138000, 129500, 180000, 177000, 310000),
        (6 to 12) to AwardRates(54500, 92500, 95500, 140000, 131500, 183000, 180000, 315000),
        (6 to 13) to AwardRates(56500, 96500, 101500, 145000, 136500, 199000, 184000, 326000),
        (7 to 7) to AwardRates(40500, 72000, 69500, 100500, 87500, 141000, 128000, 217000),
        (7 to 8) to AwardRates(38000, 67000, 65000, 94000, 81500, 132000, 118000, 200000),
        (7 to 9) to AwardRates(45500, 80000, 80500, 115000, 106000, 165000, 148000, 262000),
        (7 to 10) to AwardRates(55000, 94000, 94000, 147000, 120000, 211000, 172000, 304000),
        (7 to 11) to AwardRates(57500, 97500, 99500, 146000, 137000, 191000, 188000, 329000),
        (7 to 12) to AwardRates(40500, 68500, 76500, 109000, 108500, 148000, 126000, 226000),
        (7 to 13) to AwardRates(58500, 100000, 106000, 152000, 143000, 209000, 194000, 342000),
        (8 to 8) to AwardRates(32500, 57000, 54000, 78500, 67500, 112000, 94000, 158000),
        (8 to 9) to AwardRates(40500, 71500, 71500, 102000, 94000, 146000, 132000, 234000),
        (8 to 10) to AwardRates(48000, 82000, 81500, 128000, 103000, 182000, 148000, 262000),
        (8 to 11) to AwardRates(54500, 92500, 94000, 138000, 129500, 180000, 177000, 310000),
        (8 to 12) to AwardRates(54500, 92500, 95500, 140000, 131500, 183000, 180000, 315000),
        (8 to 13) to AwardRates(56500, 96500, 101500, 145000, 136500, 199000, 184000, 326000),
        (9 to 9) to AwardRates(47000, 82500, 84500, 120500, 114000, 176000, 158000, 281000),
        (9 to 10) to AwardRates(55000, 94000, 95500, 149000, 124500, 218000, 178000, 314000),
        (9 to 11) to AwardRates(59500, 101500, 104500, 153000, 144000, 202000, 198000, 346000),
        (9 to 12) to AwardRates(59500, 101500, 106500, 156000, 146500, 206000, 201000, 352000),
        (9 to 13) to AwardRates(62000, 106000, 112500, 162000, 152500, 223000, 208000, 366000),
        (10 to 10) to AwardRates(52000, 88000, 89500, 140000, 114000, 202000, 162000, 288000),
        (10 to 11) to AwardRates(63000, 107000, 108500, 165000, 148000, 225000, 205000, 362000),
        (10 to 12) to AwardRates(63000, 107000, 112000, 170000, 152000, 232000, 211000, 372000),
        (10 to 13) to AwardRates(65500, 112000, 118000, 177000, 158000, 248000, 217000, 386000),
        (11 to 11) to AwardRates(13000, 22500, 21500, 33000, 30500, 48000, 45000, 76000),
        (11 to 12) to AwardRates(68500, 116000, 121000, 178000, 168000, 235000, 229000, 402000),
        (11 to 13) to AwardRates(26000, 44000, 54500, 79000, 85000, 115000, 102000, 175000),
        (12 to 12) to AwardRates(68500, 116000, 121000, 178000, 168000, 235000, 229000, 402000),
        (12 to 13) to AwardRates(71000, 121000, 127000, 185000, 174000, 250000, 236000, 416000),
        (13 to 13) to AwardRates(73500, 125000, 132000, 192000, 180000, 265000, 242000, 430000)
    )

    fun getRates(originZone: KrisFlyerZone, destinationZone: KrisFlyerZone): AwardRates {
        val a = minOf(originZone.number, destinationZone.number)
        val b = maxOf(originZone.number, destinationZone.number)
        return matrix[a to b] ?: matrix[1 to 2]!!
    }

    /**
     * Jika pengguna memasukkan 2 bandara yang tidak terhubung nonstop SQ (mis. HKG -> RUH),
     * sistem otomatis menyisipkan transit di SIN (atau hub fifth-freedom yang sesuai) agar rute valid.
     */
    fun normalizeWaypointsWithAutoTransit(rawWaypoints: List<Airport>): List<Airport> {
        if (rawWaypoints.size < 2) return rawWaypoints
        val sin = AirportRepository.getByCode("SIN")
        val normalized = mutableListOf(rawWaypoints.first())
        for (i in 0 until rawWaypoints.lastIndex) {
            val from = rawWaypoints[i]
            val to = rawWaypoints[i + 1]
            if (from.code == to.code) continue
            if (!AirportRepository.isDirectSqLeg(from.code, to.code) && from.code != "SIN" && to.code != "SIN") {
                normalized.add(sin)
            }
            normalized.add(to)
        }
        return normalized
    }

    fun calculateRoute(
        rawWaypoints: List<Airport>,
        cabin: CabinClass,
        isRoundTrip: Boolean,
        passengerCount: Int,
        autoInsertSinTransit: Boolean = true
    ): RouteCalculationResult {
        val waypoints = if (autoInsertSinTransit) {
            normalizeWaypointsWithAutoTransit(rawWaypoints)
        } else {
            rawWaypoints
        }

        val multiplier = if (isRoundTrip) 2 else 1
        val origin = waypoints.first()
        val destination = waypoints.last()

        val singleRates = getRates(origin.zone, destination.zone)
        val singleSaverPerPax = singleRates.saverFor(cabin) * multiplier
        val singleAdvantagePerPax = singleRates.advantageFor(cabin) * multiplier
        val (accessMinOneWay, accessMaxOneWay) = singleRates.accessEstimateRangeFor(cabin)

        val legCalculations = mutableListOf<LegCalculation>()
        val advisories = mutableListOf<String>()

        var splitSaverOneWaySum = 0
        var splitAdvantageOneWaySum = 0

        for (i in 0 until waypoints.lastIndex) {
            val from = waypoints[i]
            val to = waypoints[i + 1]
            val legRates = getRates(from.zone, to.zone)
            val legSaver = legRates.saverFor(cabin)
            val legAdv = legRates.advantageFor(cabin)
            splitSaverOneWaySum += legSaver
            splitAdvantageOneWaySum += legAdv

            val fifthFreedom = AirportRepository.findFifthFreedom(from.code, to.code)
            val isDirect = AirportRepository.isDirectSqLeg(from.code, to.code)

            val nonHubAirport = when {
                from.code == "SIN" -> to
                to.code == "SIN" -> from
                else -> null
            }

            val legPremiumProduct = when {
                fifthFreedom != null -> if (fifthFreedom.hasFirstClass) PremiumProductType.FIRST_77W else PremiumProductType.NONE
                nonHubAirport != null -> nonHubAirport.premiumProduct
                else -> PremiumProductType.NONE
            }

            val flightHint = when {
                fifthFreedom != null -> "${fifthFreedom.flightNumbers} (${fifthFreedom.aircraft})"
                nonHubAirport != null -> listOfNotNull(nonHubAirport.suitesFlights, nonHubAirport.firstFlights).joinToString(" | ").ifBlank { null }
                else -> null
            }

            val aircraftInfo = when {
                fifthFreedom != null -> "Fifth-Freedom • ${fifthFreedom.aircraft}"
                legPremiumProduct == PremiumProductType.BOTH_SUITES_AND_FIRST -> "A380 Suites & 777-300ER First"
                legPremiumProduct == PremiumProductType.SUITES_A380 -> "Airbus A380-800 Suites"
                legPremiumProduct == PremiumProductType.FIRST_77W -> "Boeing 777-300ER First Class"
                !isDirect -> "Rute tidak nonstop SQ (memerlukan transit SIN)"
                else -> "Singapore Airlines Direct"
            }

            val legNote = listOfNotNull(
                fifthFreedom?.note,
                nonHubAirport?.specialNote
            ).joinToString(" ").ifBlank { null }

            legCalculations.add(
                LegCalculation(
                    legIndex = i + 1,
                    origin = from,
                    destination = to,
                    isDirectSqRoute = isDirect,
                    isFifthFreedom = fifthFreedom != null,
                    aircraftCabinInfo = aircraftInfo,
                    premiumProduct = legPremiumProduct,
                    flightHint = flightHint,
                    saverMilesOneWay = legSaver,
                    advantageMilesOneWay = legAdv,
                    specialNotice = legNote
                )
            )
        }

        val splitSaverPerPax = splitSaverOneWaySum * multiplier
        val splitAdvantagePerPax = splitAdvantageOneWaySum * multiplier

        // Advisories
        if (cabin == CabinClass.FIRST_SUITES) {
            val noFirstLegs = legCalculations.filter { it.premiumProduct == PremiumProductType.NONE }
            if (noFirstLegs.isNotEmpty()) {
                val legLabels = noFirstLegs.joinToString(", ") { "${it.origin.code}–${it.destination.code}" }
                advisories.add(
                    "Segmen $legLabels tidak mengoperasikan kabin First/Suites. Pada 1 PNR, segmen tersebut umumnya dialokasikan ke Business Class namun tarif miles tetap mengikuti tarif First/Suites."
                )
            }
        }

        if (cabin == CabinClass.PREMIUM_ECONOMY) {
            val noPeLegs = legCalculations.filter {
                val target = if (it.origin.code == "SIN") it.destination else it.origin
                !target.hasPremiumEconomy && !it.isFifthFreedom
            }
            if (noPeLegs.isNotEmpty()) {
                val legLabels = noPeLegs.joinToString(", ") { "${it.origin.code}–${it.destination.code}" }
                advisories.add(
                    "Segmen $legLabels umumnya tidak memiliki kabin Premium Economy (pesawat regional). Pada 1 PNR, segmen regional akan ditempatkan di Economy Class."
                )
            }
        }

        if (cabin == CabinClass.ECONOMY && waypoints.any { it.code == "EWR" }) {
            advisories.add(
                "Rute nonstop Singapura–Newark (EWR) menggunakan A350-900ULR yang hanya memiliki Business & Premium Economy (tanpa Economy standar)."
            )
        }

        if (waypoints.any { it.code == "RUH" }) {
            advisories.add(
                "Rute langsung SIN–RUH (SQ498/SQ499) dijadwalkan beroperasi kembali mulai 28 Maret 2027 (3x/minggu: Sel, Kam, Min)."
            )
        }

        if (waypoints.size > 3) {
            advisories.add(
                "Rute memiliki ${waypoints.size - 1} segmen. Pastikan seluruh transit < 24 jam (kecuali memakai jatah stopover) dan tidak melanggar aturan backtracking KrisFlyer agar tetap dihitung dalam 1 PNR."
            )
        }

        if (origin.zone == destination.zone && legCalculations.size > 1 && origin.zone != KrisFlyerZone.ZONE_11) {
            advisories.add(
                "Asal dan tujuan berada di zona yang sama (${origin.zone.title}). Tarif 1 PNR di atas mengikuti tabel intra-zona untuk transit < 24 jam di Singapura."
            )
        }

        val stopoverSummary = if (isRoundTrip) {
            "Pulang Pergi (PP): Saver mendapat 1x gratis stopover (>24 jam, maks. 30 hari); Advantage mendapat 2x gratis stopover."
        } else {
            "Sekali Jalan (One-Way): Saver tidak mengizinkan stopover (transit wajib < 24 jam); Advantage mencakup 1x gratis stopover hingga 30 hari."
        }

        return RouteCalculationResult(
            waypoints = waypoints,
            cabin = cabin,
            isRoundTrip = isRoundTrip,
            passengerCount = passengerCount,
            legBreakdown = legCalculations,
            singlePnrSaverPerPax = singleSaverPerPax,
            singlePnrAdvantagePerPax = singleAdvantagePerPax,
            singlePnrAccessMinPerPax = accessMinOneWay * multiplier,
            singlePnrAccessMaxPerPax = accessMaxOneWay * multiplier,
            splitPnrSaverPerPax = splitSaverPerPax,
            splitPnrAdvantagePerPax = splitAdvantagePerPax,
            isSinglePnrCheaperSaver = singleSaverPerPax <= splitSaverPerPax,
            saverDifferencePerPax = abs(splitSaverPerPax - singleSaverPerPax),
            advantageDifferencePerPax = abs(splitAdvantagePerPax - singleAdvantagePerPax),
            advisories = advisories,
            stopoverSummary = stopoverSummary
        )
    }
}
