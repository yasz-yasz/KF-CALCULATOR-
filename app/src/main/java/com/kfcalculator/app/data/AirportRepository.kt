package com.kfcalculator.app.data

import com.kfcalculator.app.model.Airport
import com.kfcalculator.app.model.FifthFreedomRoute
import com.kfcalculator.app.model.KrisFlyerZone
import com.kfcalculator.app.model.PremiumProductType

object AirportRepository {

    val fifthFreedomRoutes: List<FifthFreedomRoute> = listOf(
        FifthFreedomRoute(
            originCode = "FRA",
            destinationCode = "JFK",
            flightNumbers = "SQ26 / SQ25",
            aircraft = "Boeing 777-300ER",
            hasFirstClass = true,
            note = "Rute Fifth-Freedom transatlantik populer dengan First Class 777-300ER."
        ),
        FifthFreedomRoute(
            originCode = "NRT",
            destinationCode = "LAX",
            flightNumbers = "SQ12 / SQ11",
            aircraft = "Boeing 777-300ER",
            hasFirstClass = true,
            note = "Rute Fifth-Freedom transpasifik dengan First Class 777-300ER."
        ),
        FifthFreedomRoute(
            originCode = "MXP",
            destinationCode = "BCN",
            flightNumbers = "SQ378 / SQ377",
            aircraft = "Airbus A350-900",
            hasFirstClass = false,
            note = "Rute Fifth-Freedom intra-Eropa (tanpa First/Suites)."
        )
    )

    val airports: List<Airport> = listOf(
        // ZONE 1: Singapore
        Airport(
            code = "SIN",
            city = "Singapura",
            country = "Singapura",
            airportName = "Singapore Changi Airport",
            zone = KrisFlyerZone.ZONE_1,
            premiumProduct = PremiumProductType.BOTH_SUITES_AND_FIRST,
            hasPremiumEconomy = true,
            specialNote = "Hub utama Singapore Airlines (The Private Room & SilverKris Lounge)."
        ),

        // ZONE 2: Indonesia, Malaysia, Brunei
        Airport(
            code = "CGK",
            city = "Jakarta",
            country = "Indonesia",
            airportName = "Soekarno-Hatta International",
            zone = KrisFlyerZone.ZONE_2,
            premiumProduct = PremiumProductType.FIRST_77W,
            firstFlights = "SQ950/953, SQ964/965",
            hasPremiumEconomy = false,
            specialNote = "Rute First Class 777-300ER tersingkat dari/ke Singapura."
        ),
        Airport("DPS", "Denpasar (Bali)", "Indonesia", "Ngurah Rai International", KrisFlyerZone.ZONE_2, hasPremiumEconomy = false),
        Airport("SUB", "Surabaya", "Indonesia", "Juanda International", KrisFlyerZone.ZONE_2, hasPremiumEconomy = false),
        Airport("KNO", "Medan", "Indonesia", "Kualanamu International", KrisFlyerZone.ZONE_2, hasPremiumEconomy = false),
        Airport("KUL", "Kuala Lumpur", "Malaysia", "Kuala Lumpur International", KrisFlyerZone.ZONE_2, hasPremiumEconomy = false),
        Airport("PEN", "Penang", "Malaysia", "Penang International", KrisFlyerZone.ZONE_2, hasPremiumEconomy = false),
        Airport("BWN", "Bandar Seri Begawan", "Brunei", "Brunei International", KrisFlyerZone.ZONE_2, hasPremiumEconomy = false),

        // ZONE 3: Rest of Southeast Asia
        Airport("BKK", "Bangkok", "Thailand", "Suvarnabhumi Airport", KrisFlyerZone.ZONE_3, hasPremiumEconomy = false),
        Airport("HKT", "Phuket", "Thailand", "Phuket International", KrisFlyerZone.ZONE_3, hasPremiumEconomy = false),
        Airport("SGN", "Ho Chi Minh City", "Vietnam", "Tan Son Nhat International", KrisFlyerZone.ZONE_3, hasPremiumEconomy = false),
        Airport("HAN", "Hanoi", "Vietnam", "Noi Bai International", KrisFlyerZone.ZONE_3, hasPremiumEconomy = false),
        Airport("DAD", "Da Nang", "Vietnam", "Da Nang International", KrisFlyerZone.ZONE_3, hasPremiumEconomy = false),
        Airport("MNL", "Manila", "Filipina", "Ninoy Aquino International", KrisFlyerZone.ZONE_3, hasPremiumEconomy = false),
        Airport("CEB", "Cebu", "Filipina", "Mactan-Cebu International", KrisFlyerZone.ZONE_3, hasPremiumEconomy = false),
        Airport("PNH", "Phnom Penh", "Kamboja", "Phnom Penh International", KrisFlyerZone.ZONE_3, hasPremiumEconomy = false),
        Airport("SAI", "Siem Reap", "Kamboja", "Siem Reap-Angkor International", KrisFlyerZone.ZONE_3, hasPremiumEconomy = false),
        Airport("RGN", "Yangon", "Myanmar", "Yangon International", KrisFlyerZone.ZONE_3, hasPremiumEconomy = false),

        // ZONE 4: South China & Taiwan
        Airport(
            code = "HKG",
            city = "Hong Kong",
            country = "Hong Kong SAR",
            airportName = "Hong Kong International",
            zone = KrisFlyerZone.ZONE_4,
            premiumProduct = PremiumProductType.FIRST_77W,
            firstFlights = "SQ892/893",
            hasPremiumEconomy = true
        ),
        Airport("TPE", "Taipei", "Taiwan", "Taoyuan International", KrisFlyerZone.ZONE_4, hasPremiumEconomy = false),
        Airport("CAN", "Guangzhou", "Tiongkok", "Baiyun International", KrisFlyerZone.ZONE_4, hasPremiumEconomy = false),
        Airport("SZX", "Shenzhen", "Tiongkok", "Bao'an International", KrisFlyerZone.ZONE_4, hasPremiumEconomy = false),
        Airport("XMN", "Xiamen", "Tiongkok", "Gaoqi International", KrisFlyerZone.ZONE_4, hasPremiumEconomy = false),

        // ZONE 5: North & Central China
        Airport(
            code = "PVG",
            city = "Shanghai",
            country = "Tiongkok",
            airportName = "Pudong International",
            zone = KrisFlyerZone.ZONE_5,
            premiumProduct = PremiumProductType.BOTH_SUITES_AND_FIRST,
            suitesFlights = "SQ830/833 (Harian A380)",
            firstFlights = "SQ828/831 (777-300ER)",
            hasPremiumEconomy = true,
            specialNote = "Rute A380 Suites paling hemat miles dari Singapura."
        ),
        Airport(
            code = "PEK",
            city = "Beijing",
            country = "Tiongkok",
            airportName = "Beijing Capital International",
            zone = KrisFlyerZone.ZONE_5,
            premiumProduct = PremiumProductType.FIRST_77W,
            firstFlights = "SQ802/807",
            hasPremiumEconomy = true
        ),
        Airport("PKX", "Beijing (Daxing)", "Tiongkok", "Beijing Daxing International", KrisFlyerZone.ZONE_5, hasPremiumEconomy = false),
        Airport("TFU", "Chengdu", "Tiongkok", "Tianfu International", KrisFlyerZone.ZONE_5, hasPremiumEconomy = false),
        Airport("CKG", "Chongqing", "Tiongkok", "Jiangbei International", KrisFlyerZone.ZONE_5, hasPremiumEconomy = false),

        // ZONE 6: South Asia
        Airport(
            code = "DEL",
            city = "Delhi",
            country = "India",
            airportName = "Indira Gandhi International",
            zone = KrisFlyerZone.ZONE_6,
            premiumProduct = PremiumProductType.SUITES_A380,
            suitesFlights = "SQ406/403 (Harian A380)",
            hasPremiumEconomy = true
        ),
        Airport(
            code = "BOM",
            city = "Mumbai",
            country = "India",
            airportName = "Chhatrapati Shivaji Maharaj",
            zone = KrisFlyerZone.ZONE_6,
            premiumProduct = PremiumProductType.SUITES_A380,
            suitesFlights = "SQ424/423 (Harian A380)",
            hasPremiumEconomy = true
        ),
        Airport("BLR", "Bengaluru", "India", "Kempegowda International", KrisFlyerZone.ZONE_6, hasPremiumEconomy = true),
        Airport("MAA", "Chennai", "India", "Chennai International", KrisFlyerZone.ZONE_6, hasPremiumEconomy = false),
        Airport("HYD", "Hyderabad", "India", "Rajiv Gandhi International", KrisFlyerZone.ZONE_6, hasPremiumEconomy = false),
        Airport("CCU", "Kolkata", "India", "Netaji Subhas Chandra Bose", KrisFlyerZone.ZONE_6, hasPremiumEconomy = false),
        Airport("COK", "Kochi", "India", "Cochin International", KrisFlyerZone.ZONE_6, hasPremiumEconomy = false),
        Airport("AMD", "Ahmedabad", "India", "Sardar Vallabhbhai Patel", KrisFlyerZone.ZONE_6, hasPremiumEconomy = false),
        Airport("CMB", "Colombo", "Sri Lanka", "Bandaranaike International", KrisFlyerZone.ZONE_6, hasPremiumEconomy = false),
        Airport("MLE", "Malé", "Maladewa", "Velana International", KrisFlyerZone.ZONE_6, hasPremiumEconomy = false),
        Airport("KTM", "Kathmandu", "Nepal", "Tribhuvan International", KrisFlyerZone.ZONE_6, hasPremiumEconomy = false),
        Airport("DAC", "Dhaka", "Bangladesh", "Hazrat Shahjalal International", KrisFlyerZone.ZONE_6, hasPremiumEconomy = false),

        // ZONE 7: Japan & South Korea
        Airport(
            code = "HND",
            city = "Tokyo (Haneda)",
            country = "Jepang",
            airportName = "Tokyo Haneda Airport",
            zone = KrisFlyerZone.ZONE_7,
            premiumProduct = PremiumProductType.FIRST_77W,
            firstFlights = "SQ636/635",
            hasPremiumEconomy = true
        ),
        Airport(
            code = "NRT",
            city = "Tokyo (Narita)",
            country = "Jepang",
            airportName = "Narita International",
            zone = KrisFlyerZone.ZONE_7,
            premiumProduct = PremiumProductType.FIRST_77W,
            firstFlights = "SQ12/11 (Lanjut ke LAX)",
            hasPremiumEconomy = true
        ),
        Airport("KIX", "Osaka", "Jepang", "Kansai International", KrisFlyerZone.ZONE_7, hasPremiumEconomy = false),
        Airport("NGO", "Nagoya", "Jepang", "Chubu Centrair International", KrisFlyerZone.ZONE_7, hasPremiumEconomy = false),
        Airport("FUK", "Fukuoka", "Jepang", "Fukuoka Airport", KrisFlyerZone.ZONE_7, hasPremiumEconomy = false),
        Airport("ICN", "Seoul", "Korea Selatan", "Incheon International", KrisFlyerZone.ZONE_7, hasPremiumEconomy = true),
        Airport("PUS", "Busan", "Korea Selatan", "Gimhae International", KrisFlyerZone.ZONE_7, hasPremiumEconomy = false),

        // ZONE 8: Western & Northern Australia
        Airport("PER", "Perth", "Australia", "Perth Airport", KrisFlyerZone.ZONE_8, hasPremiumEconomy = false),
        Airport("DRW", "Darwin", "Australia", "Darwin International", KrisFlyerZone.ZONE_8, hasPremiumEconomy = false),

        // ZONE 9: Eastern/Southern Australia & New Zealand
        Airport(
            code = "SYD",
            city = "Sydney",
            country = "Australia",
            airportName = "Sydney Kingsford Smith",
            zone = KrisFlyerZone.ZONE_9,
            premiumProduct = PremiumProductType.BOTH_SUITES_AND_FIRST,
            suitesFlights = "SQ221/222, SQ231/232 (2x A380)",
            firstFlights = "SQ211/212 (777-300ER)",
            hasPremiumEconomy = true
        ),
        Airport(
            code = "MEL",
            city = "Melbourne",
            country = "Australia",
            airportName = "Melbourne Tullamarine",
            zone = KrisFlyerZone.ZONE_9,
            premiumProduct = PremiumProductType.BOTH_SUITES_AND_FIRST,
            suitesFlights = "SQ237/228 (Harian A380)",
            firstFlights = "SQ227/218 (777-300ER)",
            hasPremiumEconomy = true
        ),
        Airport("BNE", "Brisbane", "Australia", "Brisbane Airport", KrisFlyerZone.ZONE_9, hasPremiumEconomy = true),
        Airport("ADL", "Adelaide", "Australia", "Adelaide Airport", KrisFlyerZone.ZONE_9, hasPremiumEconomy = false),
        Airport("CNS", "Cairns", "Australia", "Cairns Airport", KrisFlyerZone.ZONE_9, hasPremiumEconomy = false),
        Airport(
            code = "AKL",
            city = "Auckland",
            country = "Selandia Baru",
            airportName = "Auckland Airport",
            zone = KrisFlyerZone.ZONE_9,
            premiumProduct = PremiumProductType.BOTH_SUITES_AND_FIRST,
            suitesFlights = "SQ285/286 (Musiman A380 s.d. Mar 2027)",
            firstFlights = "SQ281/282 (777-300ER)",
            hasPremiumEconomy = true
        ),
        Airport("CHC", "Christchurch", "Selandia Baru", "Christchurch International", KrisFlyerZone.ZONE_9, hasPremiumEconomy = true),

        // ZONE 10: Middle East & Africa
        Airport("DXB", "Dubai", "Uni Emirat Arab", "Dubai International", KrisFlyerZone.ZONE_10, hasPremiumEconomy = true),
        Airport(
            code = "RUH",
            city = "Riyadh",
            country = "Arab Saudi",
            airportName = "King Khalid International",
            zone = KrisFlyerZone.ZONE_10,
            hasPremiumEconomy = true,
            specialNote = "Layanan langsung SQ498/SQ499 dijadwalkan beroperasi kembali mulai 28 Maret 2027 (3x/minggu: Sel, Kam, Min)."
        ),
        Airport("JNB", "Johannesburg", "Afrika Selatan", "O. R. Tambo International", KrisFlyerZone.ZONE_10, hasPremiumEconomy = true),
        Airport("CPT", "Cape Town", "Afrika Selatan", "Cape Town International", KrisFlyerZone.ZONE_10, hasPremiumEconomy = true, specialNote = "Dilayani via Johannesburg (tanpa hak angkut lokal JNB–CPT)."),

        // ZONE 11: Europe
        Airport(
            code = "LHR",
            city = "London (Heathrow)",
            country = "Inggris",
            airportName = "London Heathrow Airport",
            zone = KrisFlyerZone.ZONE_11,
            premiumProduct = PremiumProductType.BOTH_SUITES_AND_FIRST,
            suitesFlights = "SQ308/317, SQ322/319 (2x A380)",
            firstFlights = "SQ318/321 (777-300ER)",
            hasPremiumEconomy = true
        ),
        Airport("LGW", "London (Gatwick)", "Inggris", "London Gatwick Airport", KrisFlyerZone.ZONE_11, hasPremiumEconomy = true),
        Airport("MAN", "Manchester", "Inggris", "Manchester Airport", KrisFlyerZone.ZONE_11, hasPremiumEconomy = true),
        Airport(
            code = "CDG",
            city = "Paris",
            country = "Prancis",
            airportName = "Charles de Gaulle Airport",
            zone = KrisFlyerZone.ZONE_11,
            premiumProduct = PremiumProductType.FIRST_77W,
            firstFlights = "SQ336/335",
            hasPremiumEconomy = true
        ),
        Airport(
            code = "FRA",
            city = "Frankfurt",
            country = "Jerman",
            airportName = "Frankfurt Airport",
            zone = KrisFlyerZone.ZONE_11,
            premiumProduct = PremiumProductType.FIRST_77W,
            firstFlights = "SQ26/25 (via JFK), SQ326/325, SQ330/329",
            hasPremiumEconomy = true
        ),
        Airport("MUC", "Munich", "Jerman", "Munich Airport", KrisFlyerZone.ZONE_11, hasPremiumEconomy = true),
        Airport(
            code = "AMS",
            city = "Amsterdam",
            country = "Belanda",
            airportName = "Amsterdam Schiphol",
            zone = KrisFlyerZone.ZONE_11,
            premiumProduct = PremiumProductType.FIRST_77W,
            firstFlights = "SQ324/323",
            hasPremiumEconomy = true
        ),
        Airport(
            code = "ZRH",
            city = "Zurich",
            country = "Swiss",
            airportName = "Zurich Airport",
            zone = KrisFlyerZone.ZONE_11,
            premiumProduct = PremiumProductType.FIRST_77W,
            firstFlights = "SQ346/345",
            hasPremiumEconomy = true
        ),
        Airport("MXP", "Milan", "Italia", "Milan Malpensa Airport", KrisFlyerZone.ZONE_11, hasPremiumEconomy = true),
        Airport("FCO", "Roma", "Italia", "Leonardo da Vinci–Fiumicino", KrisFlyerZone.ZONE_11, hasPremiumEconomy = true),
        Airport("BCN", "Barcelona", "Spanyol", "Josep Tarradellas Barcelona–El Prat", KrisFlyerZone.ZONE_11, hasPremiumEconomy = true),
        Airport("MAD", "Madrid", "Spanyol", "Adolfo Suárez Madrid–Barajas", KrisFlyerZone.ZONE_11, hasPremiumEconomy = true),
        Airport("CPH", "Copenhagen", "Denmark", "Copenhagen Airport", KrisFlyerZone.ZONE_11, hasPremiumEconomy = true),
        Airport("BRU", "Brussels", "Belgia", "Brussels Airport", KrisFlyerZone.ZONE_11, hasPremiumEconomy = true),
        Airport("IST", "Istanbul", "Turki", "Istanbul Airport", KrisFlyerZone.ZONE_11, hasPremiumEconomy = true),

        // ZONE 12: US West Coast
        Airport(
            code = "LAX",
            city = "Los Angeles",
            country = "Amerika Serikat",
            airportName = "Los Angeles International",
            zone = KrisFlyerZone.ZONE_12,
            premiumProduct = PremiumProductType.FIRST_77W,
            firstFlights = "SQ12/11 (via Tokyo Narita NRT)",
            hasPremiumEconomy = true,
            specialNote = "Nonstop SIN–LAX menggunakan A350-900ULR (hanya Business & Prem. Ekonomi). First Class tersedia via NRT (SQ12/11)."
        ),
        Airport("SFO", "San Francisco", "Amerika Serikat", "San Francisco International", KrisFlyerZone.ZONE_12, hasPremiumEconomy = true),
        Airport("SEA", "Seattle", "Amerika Serikat", "Seattle-Tacoma International", KrisFlyerZone.ZONE_12, hasPremiumEconomy = true),

        // ZONE 13: US East Coast
        Airport(
            code = "JFK",
            city = "New York (JFK)",
            country = "Amerika Serikat",
            airportName = "John F. Kennedy International",
            zone = KrisFlyerZone.ZONE_13,
            premiumProduct = PremiumProductType.FIRST_77W,
            firstFlights = "SQ26/25 (via Frankfurt FRA)",
            hasPremiumEconomy = true,
            specialNote = "Nonstop SIN–JFK (SQ24/23) memakai A350-900ULR (Business & Prem. Ekonomi saja). First Class tersedia via FRA (SQ26/25)."
        ),
        Airport(
            code = "EWR",
            city = "Newark (New York)",
            country = "Amerika Serikat",
            airportName = "Newark Liberty International",
            zone = KrisFlyerZone.ZONE_13,
            hasPremiumEconomy = true,
            specialNote = "A350-900ULR nonstop: hanya melayani Business Class & Premium Economy (tanpa Economy standar)."
        )
    )

    private val byCode: Map<String, Airport> = airports.associateBy { it.code }

    fun getByCode(code: String): Airport = byCode[code.uppercase()] ?: airports.first()

    fun findFifthFreedom(a: String, b: String): FifthFreedomRoute? {
        return fifthFreedomRoutes.firstOrNull {
            (it.originCode == a && it.destinationCode == b) ||
                (it.originCode == b && it.destinationCode == a)
        }
    }

    fun isDirectSqLeg(a: String, b: String): Boolean {
        if (a == b) return false
        if (a == "SIN" || b == "SIN") return true
        return findFifthFreedom(a, b) != null
    }
}
