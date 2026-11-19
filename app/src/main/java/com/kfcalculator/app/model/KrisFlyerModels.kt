package com.kfcalculator.app.model

enum class KrisFlyerZone(
    val number: Int,
    val title: String,
    val regionLabel: String,
    val description: String
) {
    ZONE_1(1, "Zone 1", "Singapura", "Hub utama Bandara Changi Singapura (SIN)"),
    ZONE_2(2, "Zone 2", "Indonesia, Malaysia & Brunei", "Jakarta, Denpasar, Surabaya, Medan, Kuala Lumpur, Penang, Brunei"),
    ZONE_3(3, "Zone 3", "Asia Tenggara Lainnya", "Thailand, Vietnam, Filipina, Kamboja, Myanmar"),
    ZONE_4(4, "Zone 4", "Tiongkok Selatan & Taiwan", "Hong Kong, Guangzhou, Shenzhen, Xiamen, Taipei"),
    ZONE_5(5, "Zone 5", "Tiongkok Utara & Tengah", "Shanghai, Beijing, Chengdu, Chongqing"),
    ZONE_6(6, "Zone 6", "Asia Selatan", "India, Sri Lanka, Maladewa, Nepal, Bangladesh"),
    ZONE_7(7, "Zone 7", "Jepang & Korea Selatan", "Tokyo, Osaka, Nagoya, Fukuoka, Seoul, Busan"),
    ZONE_8(8, "Zone 8", "Australia Barat & Utara", "Perth, Darwin"),
    ZONE_9(9, "Zone 9", "Australia Timur/Selatan & Selandia Baru", "Sydney, Melbourne, Brisbane, Adelaide, Cairns, Auckland, Christchurch"),
    ZONE_10(10, "Zone 10", "Timur Tengah & Afrika", "Dubai, Riyadh, Johannesburg, Cape Town"),
    ZONE_11(11, "Zone 11", "Eropa", "London, Paris, Frankfurt, Munich, Amsterdam, Zurich, Milan, Barcelona, dll."),
    ZONE_12(12, "Zone 12", "Amerika Serikat Pantai Barat", "Los Angeles, San Francisco, Seattle"),
    ZONE_13(13, "Zone 13", "Amerika Serikat Pantai Timur", "New York (JFK), Newark (EWR)");

    companion object {
        fun fromNumber(num: Int): KrisFlyerZone = entries.first { it.number == num }
    }
}

enum class CabinClass(
    val displayName: String,
    val shortName: String,
    val subtitle: String
) {
    ECONOMY("Economy", "Ekonomi", "Standard Economy Class"),
    PREMIUM_ECONOMY("Premium Economy", "Prem. Eko", "A350 / A380 / 777-300ER"),
    BUSINESS("Business", "Bisnis", "Regional & Long-Haul Flat Bed"),
    FIRST_SUITES("First / Suites", "First/Suites", "A380 Suites & 777-300ER First")
}

enum class PremiumProductType(val label: String) {
    NONE("Tanpa First/Suites"),
    SUITES_A380("A380 Suites"),
    FIRST_77W("777-300ER First Class"),
    BOTH_SUITES_AND_FIRST("A380 Suites & 777-300ER First")
}

data class Airport(
    val code: String,
    val city: String,
    val country: String,
    val airportName: String,
    val zone: KrisFlyerZone,
    val premiumProduct: PremiumProductType = PremiumProductType.NONE,
    val suitesFlights: String? = null,
    val firstFlights: String? = null,
    val hasPremiumEconomy: Boolean = true,
    val specialNote: String? = null
)

data class FifthFreedomRoute(
    val originCode: String,
    val destinationCode: String,
    val flightNumbers: String,
    val aircraft: String,
    val hasFirstClass: Boolean,
    val note: String
)

data class AwardRates(
    val economySaver: Int,
    val economyAdvantage: Int,
    val premiumEconomySaver: Int,
    val premiumEconomyAdvantage: Int,
    val businessSaver: Int,
    val businessAdvantage: Int,
    val firstSaver: Int,
    val firstAdvantage: Int
) {
    fun saverFor(cabin: CabinClass): Int = when (cabin) {
        CabinClass.ECONOMY -> economySaver
        CabinClass.PREMIUM_ECONOMY -> premiumEconomySaver
        CabinClass.BUSINESS -> businessSaver
        CabinClass.FIRST_SUITES -> firstSaver
    }

    fun advantageFor(cabin: CabinClass): Int = when (cabin) {
        CabinClass.ECONOMY -> economyAdvantage
        CabinClass.PREMIUM_ECONOMY -> premiumEconomyAdvantage
        CabinClass.BUSINESS -> businessAdvantage
        CabinClass.FIRST_SUITES -> firstAdvantage
    }

    /**
     * KrisFlyer Access (tier dinamis di atas Advantage efektif Nov 2025).
     * Tarif sebenarnya dinamis mengikuti ketersediaan kursi komersial.
     * Estimasi rentang awal dimulai ~15% hingga ~65% di atas Advantage.
     */
    fun accessEstimateRangeFor(cabin: CabinClass): Pair<Int, Int> {
        val adv = advantageFor(cabin)
        val minEst = ((adv * 1.15) / 500.0).toInt() * 500
        val maxEst = ((adv * 1.65) / 500.0).toInt() * 500
        return minEst to maxEst
    }
}

data class LegCalculation(
    val legIndex: Int,
    val origin: Airport,
    val destination: Airport,
    val isDirectSqRoute: Boolean,
    val isFifthFreedom: Boolean,
    val aircraftCabinInfo: String,
    val premiumProduct: PremiumProductType,
    val flightHint: String?,
    val saverMilesOneWay: Int,
    val advantageMilesOneWay: Int,
    val specialNotice: String? = null
)

data class RouteCalculationResult(
    val waypoints: List<Airport>,
    val cabin: CabinClass,
    val isRoundTrip: Boolean,
    val passengerCount: Int,
    val legBreakdown: List<LegCalculation>,
    val singlePnrSaverPerPax: Int,
    val singlePnrAdvantagePerPax: Int,
    val singlePnrAccessMinPerPax: Int,
    val singlePnrAccessMaxPerPax: Int,
    val splitPnrSaverPerPax: Int,
    val splitPnrAdvantagePerPax: Int,
    val isSinglePnrCheaperSaver: Boolean,
    val saverDifferencePerPax: Int,
    val advantageDifferencePerPax: Int,
    val advisories: List<String>,
    val stopoverSummary: String
) {
    val singlePnrSaverTotal: Int get() = singlePnrSaverPerPax * passengerCount
    val singlePnrAdvantageTotal: Int get() = singlePnrAdvantagePerPax * passengerCount
    val singlePnrAccessMinTotal: Int get() = singlePnrAccessMinPerPax * passengerCount
    val singlePnrAccessMaxTotal: Int get() = singlePnrAccessMaxPerPax * passengerCount
    val splitPnrSaverTotal: Int get() = splitPnrSaverPerPax * passengerCount
    val splitPnrAdvantageTotal: Int get() = splitPnrAdvantagePerPax * passengerCount
    val isMultiLeg: Boolean get() = legBreakdown.size > 1
}
