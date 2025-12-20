package com.astro.storm.data.ai.agent.tools

import com.astro.storm.data.local.ChartEntity
import com.astro.storm.data.model.BirthData
import com.astro.storm.data.model.Gender
import com.astro.storm.data.model.HouseSystem
import com.astro.storm.data.model.Nakshatra
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
// Note: Calculation wrappers are defined locally in this package
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

/**
 * Extension function to convert ChartEntity to VedicChart
 * Used by tools that need to access full chart data from the database
 */
fun ChartEntity.toVedicChart(): VedicChart {
    val planetPositions = JSONArray(planetPositionsJson).let { array ->
        (0 until array.length()).map { i ->
            val obj = array.getJSONObject(i)
            PlanetPosition(
                planet = Planet.valueOf(obj.getString("planet")),
                longitude = obj.getDouble("longitude"),
                latitude = obj.getDouble("latitude"),
                distance = obj.getDouble("distance"),
                speed = obj.getDouble("speed"),
                sign = ZodiacSign.valueOf(obj.getString("sign")),
                degree = obj.getDouble("degree"),
                minutes = obj.getDouble("minutes"),
                seconds = obj.getDouble("seconds"),
                isRetrograde = obj.getBoolean("isRetrograde"),
                nakshatra = Nakshatra.valueOf(obj.getString("nakshatra")),
                nakshatraPada = obj.getInt("nakshatraPada"),
                house = obj.getInt("house")
            )
        }
    }

    val houseCuspsList = JSONArray(houseCuspsJson).let { array ->
        (0 until array.length()).map { i ->
            array.getDouble(i)
        }
    }

    return VedicChart(
        birthData = BirthData(
            name = name,
            dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            latitude = latitude,
            longitude = longitude,
            timezone = timezone,
            location = location,
            gender = Gender.fromString(gender)
        ),
        julianDay = julianDay,
        ayanamsa = ayanamsa,
        ayanamsaName = ayanamsaName,
        ascendant = ascendant,
        midheaven = midheaven,
        planetPositions = planetPositions,
        houseCusps = houseCuspsList,
        houseSystem = HouseSystem.valueOf(houseSystem),
        calculationTime = createdAt
    )
}

// ============================================
// HELPER FUNCTIONS
// ============================================

/**
 * Helper function to get VedicChart by profile ID.
 * Returns the current chart if profile_id is "current" or empty.
 * Loads and converts chart from database for other profile IDs.
 */
suspend fun getChartForProfile(
    profileId: String,
    context: ToolContext
): Pair<VedicChart?, String?> {
    return when {
        profileId.isEmpty() || profileId == "current" -> {
            context.currentChart to null
        }
        else -> {
            val profile = context.allProfiles.find { it.id.toString() == profileId }
                ?: return null to "Profile not found with ID: $profileId"

            val chartEntity = context.database.chartDao().getChartById(profile.id)
                ?: return null to "Chart data not found for profile: ${profile.name}"

            chartEntity.toVedicChart() to null
        }
    }
}

/**
 * Helper function to get profile name by ID
 */
fun getProfileName(
    profileId: String,
    context: ToolContext
): String {
    return when {
        profileId.isEmpty() || profileId == "current" -> {
            context.currentProfile?.name ?: "Current Profile"
        }
        else -> {
            context.allProfiles.find { it.id.toString() == profileId }?.name ?: "Unknown"
        }
    }
}

// ============================================
// PROFILE & CHART ACCESS TOOLS
// ============================================

/**
 * Tool to get current profile information
 */
class GetProfileInfoTool : AstrologyTool {
    override val name = "get_profile_info"
    override val description = "Get detailed information about a birth chart profile"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get info for. Use 'current' for active profile",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")

        val profile = if (profileId == "current") {
            context.currentProfile
        } else {
            context.allProfiles.find { it.id.toString() == profileId }
        }

        if (profile == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = "Profile not found",
                summary = "No profile found with ID: $profileId"
            )
        }

        val data = JSONObject().apply {
            put("id", profile.id)
            put("name", profile.name)
            put("birthDateTime", profile.dateTime)
            put("birthLocation", profile.location)
            put("createdAt", profile.createdAt)
        }

        return ToolExecutionResult(
            success = true,
            data = data,
            summary = "Profile info for ${profile.name}"
        )
    }
}

/**
 * Tool to get all available profiles
 */
class GetAllProfilesTool : AstrologyTool {
    override val name = "get_all_profiles"
    override val description = "Get list of all saved birth chart profiles"
    override val parameters = emptyList<ToolParameter>()

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profiles = context.allProfiles

        val data = JSONObject().apply {
            put("count", profiles.size)
            put("profiles", JSONArray().apply {
                profiles.forEach { profile ->
                    put(JSONObject().apply {
                        put("id", profile.id)
                        put("name", profile.name)
                        put("location", profile.location)
                        put("dateTime", profile.dateTime)
                        put("isActive", profile.id == context.currentProfile?.id)
                    })
                }
            })
        }

        return ToolExecutionResult(
            success = true,
            data = data,
            summary = "Found ${profiles.size} profiles"
        )
    }
}

/**
 * Tool to get planetary positions
 */
class GetPlanetPositionsTool : AstrologyTool {
    override val name = "get_planet_positions"
    override val description = "Get detailed planetary positions from a birth chart including sign, degree, nakshatra, house, and retrograde status"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get positions for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        ),
        ToolParameter(
            name = "planets",
            description = "Specific planets to include (comma-separated: Sun,Moon,Mars,etc.). Leave empty for all planets",
            type = ParameterType.STRING,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        val filterPlanets = arguments.optString("planets", "")
            .split(",")
            .map { it.trim().lowercase() }
            .filter { it.isNotEmpty() }

        val positions = if (filterPlanets.isEmpty()) {
            chart.planetPositions
        } else {
            chart.planetPositions.filter { pos ->
                filterPlanets.any { filter ->
                    pos.planet.displayName.lowercase().contains(filter) ||
                    pos.planet.symbol.lowercase() == filter
                }
            }
        }

        val data = JSONObject().apply {
            put("ascendant", JSONObject().apply {
                put("longitude", chart.ascendant)
                val ascSign = ZodiacSign.fromLongitude(chart.ascendant)
                put("sign", ascSign.displayName)
                put("degree", chart.ascendant % 30)
            })
            put("planets", JSONArray().apply {
                positions.forEach { pos ->
                    put(JSONObject().apply {
                        put("planet", pos.planet.displayName)
                        put("symbol", pos.planet.symbol)
                        put("sign", pos.sign.displayName)
                        put("signAbbr", pos.sign.abbreviation)
                        put("degree", String.format("%.2f", pos.degree))
                        put("minutes", pos.minutes.toInt())
                        put("seconds", pos.seconds.toInt())
                        put("longitude", String.format("%.4f", pos.longitude))
                        put("house", pos.house)
                        put("nakshatra", pos.nakshatra.displayName)
                        put("nakshatraPada", pos.nakshatraPada)
                        put("nakshatraLord", pos.nakshatra.ruler.displayName)
                        put("isRetrograde", pos.isRetrograde)
                        put("speed", String.format("%.4f", pos.speed))
                    })
                }
            })
        }

        return ToolExecutionResult(
            success = true,
            data = data,
            summary = "Retrieved positions for ${positions.size} planets"
        )
    }
}

/**
 * Tool to get house positions
 */
class GetHousePositionsTool : AstrologyTool {
    override val name = "get_house_positions"
    override val description = "Get house cusps and planets in each house (bhava)"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get house positions for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        ),
        ToolParameter(
            name = "house_number",
            description = "Specific house number (1-12). Leave empty for all houses",
            type = ParameterType.INTEGER,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        val houseNumber = arguments.optInt("house_number", 0)
        val planetsByHouse = chart.planetsByHouse

        val houseMeanings = mapOf(
            1 to "Self, personality, physical body, health",
            2 to "Wealth, family, speech, food, values",
            3 to "Siblings, courage, communication, short travels",
            4 to "Mother, home, property, emotions, education",
            5 to "Children, creativity, intelligence, romance, speculation",
            6 to "Enemies, diseases, debts, service, daily work",
            7 to "Marriage, partnerships, business, public relations",
            8 to "Longevity, transformation, inheritance, occult",
            9 to "Fortune, father, religion, higher learning, long travels",
            10 to "Career, status, authority, government, public image",
            11 to "Gains, income, friends, hopes, elder siblings",
            12 to "Losses, expenses, foreign lands, liberation, sleep"
        )

        val data = JSONObject().apply {
            put("houseSystem", chart.houseSystem.name)
            put("houses", JSONArray().apply {
                for (i in 1..12) {
                    if (houseNumber != 0 && houseNumber != i) continue

                    val cusp = if (i <= chart.houseCusps.size) chart.houseCusps[i - 1] else 0.0
                    val planetsInHouse = planetsByHouse[i] ?: emptyList()
                    val sign = ZodiacSign.fromLongitude(cusp)

                    put(JSONObject().apply {
                        put("house", i)
                        put("cusp", String.format("%.2f", cusp))
                        put("sign", sign.displayName)
                        put("degree", String.format("%.2f", cusp % 30))
                        put("signLord", sign.ruler.displayName)
                        put("meaning", houseMeanings[i])
                        put("planets", JSONArray().apply {
                            planetsInHouse.forEach { planet ->
                                put(JSONObject().apply {
                                    put("name", planet.planet.displayName)
                                    put("degree", String.format("%.2f", planet.degree))
                                    put("isRetrograde", planet.isRetrograde)
                                })
                            }
                        })
                    })
                }
            })
        }

        return ToolExecutionResult(
            success = true,
            data = data,
            summary = if (houseNumber > 0) "House $houseNumber details" else "All 12 houses with planets"
        )
    }
}

/**
 * Tool to get nakshatra information
 */
class GetNakshatraInfoTool : AstrologyTool {
    override val name = "get_nakshatra_info"
    override val description = "Get detailed nakshatra (lunar mansion) information for planets in the chart"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get nakshatra info for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        ),
        ToolParameter(
            name = "planet",
            description = "Planet name to get nakshatra for (default: Moon for Janma Nakshatra)",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "Moon"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        val planetName = arguments.optString("planet", "Moon").lowercase()
        val planetPos = chart.planetPositions.find {
            it.planet.displayName.lowercase() == planetName ||
            it.planet.symbol.lowercase() == planetName
        }

        if (planetPos == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = "Planet not found: $planetName",
                summary = "Invalid planet name"
            )
        }

        val nakshatra = planetPos.nakshatra
        val nakshatraQualities = getNakshatraQualities(nakshatra)

        val data = JSONObject().apply {
            put("planet", planetPos.planet.displayName)
            put("nakshatra", nakshatra.displayName)
            put("pada", planetPos.nakshatraPada)
            put("ruler", nakshatra.ruler.displayName)
            put("startDegree", nakshatra.ordinal * 13.333333)
            put("deity", nakshatraQualities["deity"])
            put("symbol", nakshatraQualities["symbol"])
            put("nature", nakshatraQualities["nature"])
            put("gana", nakshatraQualities["gana"])
            put("qualities", nakshatraQualities["qualities"])
            put("syllables", nakshatraQualities["syllables"])
        }

        return ToolExecutionResult(
            success = true,
            data = data,
            summary = "${planetPos.planet.displayName} in ${nakshatra.displayName} pada ${planetPos.nakshatraPada}"
        )
    }

    private fun getNakshatraQualities(nakshatra: Nakshatra): Map<String, String> {
        // Comprehensive nakshatra data
        return when (nakshatra) {
            Nakshatra.ASHWINI -> mapOf(
                "deity" to "Ashwini Kumaras (Divine Physicians)",
                "symbol" to "Horse's head",
                "nature" to "Kshipra (Swift)",
                "gana" to "Deva (Divine)",
                "qualities" to "Speed, healing, initiative, pioneering spirit",
                "syllables" to "Chu, Che, Cho, La"
            )
            Nakshatra.BHARANI -> mapOf(
                "deity" to "Yama (God of Death)",
                "symbol" to "Yoni (Female organ)",
                "nature" to "Ugra (Fierce)",
                "gana" to "Manushya (Human)",
                "qualities" to "Transformation, restraint, moral duty",
                "syllables" to "Li, Lu, Le, Lo"
            )
            Nakshatra.KRITTIKA -> mapOf(
                "deity" to "Agni (Fire God)",
                "symbol" to "Razor/Flame",
                "nature" to "Mishra (Mixed)",
                "gana" to "Rakshasa (Demon)",
                "qualities" to "Sharp intellect, purification, determination",
                "syllables" to "A, I, U, E"
            )
            Nakshatra.ROHINI -> mapOf(
                "deity" to "Brahma (Creator)",
                "symbol" to "Chariot/Ox cart",
                "nature" to "Dhruva (Fixed)",
                "gana" to "Manushya (Human)",
                "qualities" to "Beauty, growth, fertility, creativity",
                "syllables" to "O, Va, Vi, Vu"
            )
            Nakshatra.MRIGASHIRA -> mapOf(
                "deity" to "Soma (Moon God)",
                "symbol" to "Deer's head",
                "nature" to "Mridu (Soft)",
                "gana" to "Deva (Divine)",
                "qualities" to "Seeking, gentle nature, curiosity",
                "syllables" to "Ve, Vo, Ka, Ki"
            )
            Nakshatra.ARDRA -> mapOf(
                "deity" to "Rudra (Storm God)",
                "symbol" to "Teardrop",
                "nature" to "Tikshna (Sharp)",
                "gana" to "Manushya (Human)",
                "qualities" to "Intensity, transformation, emotional depth",
                "syllables" to "Ku, Gha, Ng, Chha"
            )
            Nakshatra.PUNARVASU -> mapOf(
                "deity" to "Aditi (Mother of Gods)",
                "symbol" to "Quiver of arrows",
                "nature" to "Chara (Movable)",
                "gana" to "Deva (Divine)",
                "qualities" to "Renewal, restoration, return to source",
                "syllables" to "Ke, Ko, Ha, Hi"
            )
            Nakshatra.PUSHYA -> mapOf(
                "deity" to "Brihaspati (Jupiter)",
                "symbol" to "Cow's udder/Lotus",
                "nature" to "Kshipra (Swift)",
                "gana" to "Deva (Divine)",
                "qualities" to "Nourishment, wisdom, prosperity",
                "syllables" to "Hu, He, Ho, Da"
            )
            Nakshatra.ASHLESHA -> mapOf(
                "deity" to "Nagas (Serpent Gods)",
                "symbol" to "Coiled snake",
                "nature" to "Tikshna (Sharp)",
                "gana" to "Rakshasa (Demon)",
                "qualities" to "Kundalini, mysticism, hypnotic ability",
                "syllables" to "Di, Du, De, Do"
            )
            Nakshatra.MAGHA -> mapOf(
                "deity" to "Pitris (Ancestors)",
                "symbol" to "Royal throne",
                "nature" to "Ugra (Fierce)",
                "gana" to "Rakshasa (Demon)",
                "qualities" to "Royalty, authority, ancestral connection",
                "syllables" to "Ma, Mi, Mu, Me"
            )
            Nakshatra.PURVA_PHALGUNI -> mapOf(
                "deity" to "Bhaga (God of Fortune)",
                "symbol" to "Front legs of bed",
                "nature" to "Ugra (Fierce)",
                "gana" to "Manushya (Human)",
                "qualities" to "Enjoyment, creativity, rest, pleasure",
                "syllables" to "Mo, Ta, Ti, Tu"
            )
            Nakshatra.UTTARA_PHALGUNI -> mapOf(
                "deity" to "Aryaman (God of Contracts)",
                "symbol" to "Back legs of bed",
                "nature" to "Dhruva (Fixed)",
                "gana" to "Manushya (Human)",
                "qualities" to "Friendship, patronage, contracts, marriage",
                "syllables" to "Te, To, Pa, Pi"
            )
            Nakshatra.HASTA -> mapOf(
                "deity" to "Savitar (Sun God)",
                "symbol" to "Hand/Fist",
                "nature" to "Kshipra (Swift)",
                "gana" to "Deva (Divine)",
                "qualities" to "Skill, dexterity, craftmanship",
                "syllables" to "Pu, Sha, Na, Tha"
            )
            Nakshatra.CHITRA -> mapOf(
                "deity" to "Vishvakarma (Divine Architect)",
                "symbol" to "Bright jewel",
                "nature" to "Mridu (Soft)",
                "gana" to "Rakshasa (Demon)",
                "qualities" to "Brilliance, creativity, illusion",
                "syllables" to "Pe, Po, Ra, Ri"
            )
            Nakshatra.SWATI -> mapOf(
                "deity" to "Vayu (Wind God)",
                "symbol" to "Young plant/Coral",
                "nature" to "Chara (Movable)",
                "gana" to "Deva (Divine)",
                "qualities" to "Independence, flexibility, trade",
                "syllables" to "Ru, Re, Ro, Taa"
            )
            Nakshatra.VISHAKHA -> mapOf(
                "deity" to "Indra-Agni (Power & Fire)",
                "symbol" to "Triumphal archway",
                "nature" to "Mishra (Mixed)",
                "gana" to "Rakshasa (Demon)",
                "qualities" to "Purpose, determination, achievement",
                "syllables" to "Ti, Tu, Te, To"
            )
            Nakshatra.ANURADHA -> mapOf(
                "deity" to "Mitra (God of Friendship)",
                "symbol" to "Lotus",
                "nature" to "Mridu (Soft)",
                "gana" to "Deva (Divine)",
                "qualities" to "Devotion, friendship, success abroad",
                "syllables" to "Na, Ni, Nu, Ne"
            )
            Nakshatra.JYESHTHA -> mapOf(
                "deity" to "Indra (King of Gods)",
                "symbol" to "Earring/Umbrella",
                "nature" to "Tikshna (Sharp)",
                "gana" to "Rakshasa (Demon)",
                "qualities" to "Seniority, protection, authority",
                "syllables" to "No, Ya, Yi, Yu"
            )
            Nakshatra.MULA -> mapOf(
                "deity" to "Nirriti (Goddess of Destruction)",
                "symbol" to "Bunch of roots",
                "nature" to "Tikshna (Sharp)",
                "gana" to "Rakshasa (Demon)",
                "qualities" to "Root investigation, destruction to rebuild",
                "syllables" to "Ye, Yo, Ba, Bi"
            )
            Nakshatra.PURVA_ASHADHA -> mapOf(
                "deity" to "Apas (Water Deity)",
                "symbol" to "Elephant tusk/Fan",
                "nature" to "Ugra (Fierce)",
                "gana" to "Manushya (Human)",
                "qualities" to "Invincibility, purification, declaration",
                "syllables" to "Bu, Dha, Pha, Dha"
            )
            Nakshatra.UTTARA_ASHADHA -> mapOf(
                "deity" to "Vishvadevas (Universal Gods)",
                "symbol" to "Elephant tusk/Small bed",
                "nature" to "Dhruva (Fixed)",
                "gana" to "Manushya (Human)",
                "qualities" to "Final victory, leadership, permanence",
                "syllables" to "Be, Bo, Ja, Ji"
            )
            Nakshatra.SHRAVANA -> mapOf(
                "deity" to "Vishnu (Preserver)",
                "symbol" to "Three footprints/Ear",
                "nature" to "Chara (Movable)",
                "gana" to "Deva (Divine)",
                "qualities" to "Learning, listening, connection",
                "syllables" to "Ju, Je, Jo, Gha"
            )
            Nakshatra.DHANISHTHA -> mapOf(
                "deity" to "Vasus (Eight Gods of Elements)",
                "symbol" to "Drum",
                "nature" to "Chara (Movable)",
                "gana" to "Rakshasa (Demon)",
                "qualities" to "Wealth, music, rhythm, abundance",
                "syllables" to "Ga, Gi, Gu, Ge"
            )
            Nakshatra.SHATABHISHA -> mapOf(
                "deity" to "Varuna (God of Cosmic Waters)",
                "symbol" to "Empty circle/100 flowers",
                "nature" to "Chara (Movable)",
                "gana" to "Rakshasa (Demon)",
                "qualities" to "Healing, secrecy, seclusion",
                "syllables" to "Go, Sa, Si, Su"
            )
            Nakshatra.PURVA_BHADRAPADA -> mapOf(
                "deity" to "Aja Ekapada (One-footed Goat)",
                "symbol" to "Front of funeral cot",
                "nature" to "Ugra (Fierce)",
                "gana" to "Manushya (Human)",
                "qualities" to "Purification through fire, penance",
                "syllables" to "Se, So, Da, Di"
            )
            Nakshatra.UTTARA_BHADRAPADA -> mapOf(
                "deity" to "Ahir Budhnya (Serpent of Depths)",
                "symbol" to "Back of funeral cot",
                "nature" to "Dhruva (Fixed)",
                "gana" to "Manushya (Human)",
                "qualities" to "Kundalini, wisdom, depth",
                "syllables" to "Du, Jham, Jna, Tha"
            )
            Nakshatra.REVATI -> mapOf(
                "deity" to "Pushan (Nourisher)",
                "symbol" to "Fish/Drum",
                "nature" to "Mridu (Soft)",
                "gana" to "Deva (Divine)",
                "qualities" to "Safe travel, nourishment, wealth",
                "syllables" to "De, Do, Cha, Chi"
            )
        }
    }
}

/**
 * Tool to get dasha (planetary period) information
 */
class GetDashaInfoTool : AstrologyTool {
    override val name = "get_dasha_info"
    override val description = "Get Vimshottari Dasha (planetary period) timeline for the chart"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get dasha info for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        ),
        ToolParameter(
            name = "dasha_type",
            description = "Type of dasha system (vimshottari, yogini, chara). Default: vimshottari",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "vimshottari"
        ),
        ToolParameter(
            name = "years_ahead",
            description = "How many years ahead to calculate (default: 10)",
            type = ParameterType.INTEGER,
            required = false,
            defaultValue = 10
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        val dashaType = arguments.optString("dasha_type", "vimshottari")
        val yearsAhead = arguments.optInt("years_ahead", 10)

        try {
            val dashaCalculator = VimshottariDashaCalculator()
            val dashas = dashaCalculator.calculateDashas(chart, yearsAhead)

            val data = JSONObject().apply {
                put("dashaType", dashaType)
                put("moonNakshatra", chart.planetPositions.find {
                    it.planet == Planet.MOON
                }?.nakshatra?.displayName)
                put("dashas", JSONArray().apply {
                    dashas.forEach { dasha ->
                        put(JSONObject().apply {
                            put("planet", dasha.planet.displayName)
                            put("startDate", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dasha.startDate))
                            put("endDate", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dasha.endDate))
                            put("isCurrent", dasha.isCurrent)
                            put("antarDashas", JSONArray().apply {
                                dasha.antarDashas.take(5).forEach { antar ->
                                    put(JSONObject().apply {
                                        put("planet", antar.planet.displayName)
                                        put("startDate", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(antar.startDate))
                                        put("endDate", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(antar.endDate))
                                        put("isCurrent", antar.isCurrent)
                                    })
                                }
                            })
                        })
                    }
                })
            }

            val currentDasha = dashas.find { it.isCurrent }
            val currentAntar = currentDasha?.antarDashas?.find { it.isCurrent }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Current: ${currentDasha?.planet?.displayName ?: "N/A"} Mahadasha, ${currentAntar?.planet?.displayName ?: "N/A"} Antardasha"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to calculate dashas"
            )
        }
    }
}

/**
 * Tool to get current dasha quickly
 */
class GetCurrentDashaTool : AstrologyTool {
    override val name = "get_current_dasha"
    override val description = "Get the currently running dasha (planetary period) at a glance"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get current dasha for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        try {
            val dashaCalculator = VimshottariDashaCalculator()
            val currentDasha = dashaCalculator.getCurrentDasha(chart)

            val data = JSONObject().apply {
                put("mahaDasha", JSONObject().apply {
                    put("planet", currentDasha.mahaDasha.displayName)
                    put("startDate", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDasha.mahaDashaStart))
                    put("endDate", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDasha.mahaDashaEnd))
                    put("meaning", getDashaMeaning(currentDasha.mahaDasha))
                })
                put("antarDasha", JSONObject().apply {
                    put("planet", currentDasha.antarDasha.displayName)
                    put("startDate", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDasha.antarDashaStart))
                    put("endDate", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDasha.antarDashaEnd))
                })
                currentDasha.pratyantarDasha?.let { pratyantar ->
                    put("pratyantarDasha", JSONObject().apply {
                        put("planet", pratyantar.displayName)
                    })
                }
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "${currentDasha.mahaDasha.displayName}-${currentDasha.antarDasha.displayName} period active"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to get current dasha"
            )
        }
    }

    private fun getDashaMeaning(planet: Planet): String {
        return when (planet) {
            Planet.SUN -> "Period of authority, government matters, father, soul growth, leadership"
            Planet.MOON -> "Period of emotions, mother, mind, public dealings, travel, nurturing"
            Planet.MARS -> "Period of energy, courage, property, siblings, competition, initiative"
            Planet.MERCURY -> "Period of communication, business, intellect, learning, adaptability"
            Planet.JUPITER -> "Period of wisdom, expansion, luck, teachers, children, spirituality"
            Planet.VENUS -> "Period of love, luxury, arts, marriage, vehicles, pleasures"
            Planet.SATURN -> "Period of discipline, hard work, delays, karma, longevity, service"
            Planet.RAHU -> "Period of ambition, foreign elements, unconventional paths, obsession"
            Planet.KETU -> "Period of spirituality, detachment, past karma, liberation, occult"
            else -> "Period of subtle influences"
        }
    }
}

/**
 * Tool to get yogas (planetary combinations)
 */
class GetYogasTool : AstrologyTool {
    override val name = "get_yogas"
    override val description = "Get auspicious and inauspicious yogas (planetary combinations) present in the chart"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get yogas for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        ),
        ToolParameter(
            name = "category",
            description = "Yoga category filter: raja, dhana, pancha_mahapurusha, sun_moon, all (default: all)",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "all"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        val category = arguments.optString("category", "all")

        try {
            val yogaCalculatorWrapper = YogaCalculatorWrapper()
            val allYogas = yogaCalculatorWrapper.calculateYogas(chart)

            val filteredYogas = if (category == "all") {
                allYogas
            } else {
                allYogas.filter { yoga -> yoga.category.lowercase() == category.lowercase() }
            }

            val data = JSONObject().apply {
                put("totalYogas", filteredYogas.size)
                put("auspiciousCount", filteredYogas.count { yoga -> yoga.isAuspicious })
                put("inauspiciousCount", filteredYogas.count { yoga -> !yoga.isAuspicious })
                put("yogas", JSONArray().apply {
                    filteredYogas.forEach { yoga ->
                        put(JSONObject().apply {
                            put("name", yoga.name)
                            put("sanskritName", yoga.sanskritName)
                            put("category", yoga.category)
                            put("isAuspicious", yoga.isAuspicious)
                            put("strength", yoga.strength)
                            put("description", yoga.description)
                            put("effects", yoga.effects)
                            put("formingPlanets", JSONArray(yoga.formingPlanets.map { planet -> planet.displayName }))
                        })
                    }
                })
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Found ${filteredYogas.size} yogas (${filteredYogas.count { yoga -> yoga.isAuspicious }} auspicious)"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to calculate yogas"
            )
        }
    }
}

/**
 * Tool to get Ashtakavarga strength
 */
class GetAshtakavargaTool : AstrologyTool {
    override val name = "get_ashtakavarga"
    override val description = "Get Ashtakavarga bindus (strength points) for planets and signs"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get Ashtakavarga for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        ),
        ToolParameter(
            name = "type",
            description = "Type: sarvashtakavarga (combined) or bhinnashtakavarga (individual). Default: sarvashtakavarga",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "sarvashtakavarga"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        val type = arguments.optString("type", "sarvashtakavarga")

        try {
            val ashtakavargaCalculatorWrapper = AshtakavargaCalculatorWrapper()

            val data = if (type == "sarvashtakavarga") {
                val sav = ashtakavargaCalculatorWrapper.calculateSarvashtakavarga(chart)
                JSONObject().apply {
                    put("type", "Sarvashtakavarga")
                    put("description", "Combined strength points from all planets in each sign")
                    put("signs", JSONArray().apply {
                        ZodiacSign.entries.forEachIndexed { index, sign ->
                            put(JSONObject().apply {
                                put("sign", sign.displayName)
                                put("bindus", sav.getOrElse(index) { 0 })
                                put("strength", when {
                                    sav.getOrElse(index) { 0 } >= 30 -> "Very Strong"
                                    sav.getOrElse(index) { 0 } >= 25 -> "Strong"
                                    sav.getOrElse(index) { 0 } >= 20 -> "Average"
                                    else -> "Weak"
                                })
                            })
                        }
                    })
                    put("strongest", ZodiacSign.entries[sav.indexOf(sav.maxOrNull() ?: 0)].displayName)
                    put("weakest", ZodiacSign.entries[sav.indexOf(sav.minOrNull() ?: 0)].displayName)
                }
            } else {
                val bav = ashtakavargaCalculatorWrapper.calculateBhinnashtakavarga(chart)
                JSONObject().apply {
                    put("type", "Bhinnashtakavarga")
                    put("description", "Individual planet strength in each sign (0-8 bindus)")
                    put("planets", JSONArray().apply {
                        bav.forEach { (planet, bindus) ->
                            put(JSONObject().apply {
                                put("planet", planet.displayName)
                                put("totalBindus", bindus.sum())
                                put("signBindus", JSONArray().apply {
                                    ZodiacSign.entries.forEachIndexed { index, sign ->
                                        put(JSONObject().apply {
                                            put("sign", sign.displayName)
                                            put("bindus", bindus.getOrElse(index) { 0 })
                                        })
                                    }
                                })
                            })
                        }
                    })
                }
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Ashtakavarga $type analysis complete"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to calculate Ashtakavarga"
            )
        }
    }
}

/**
 * Tool to get Panchanga (daily Vedic calendar)
 */
class GetPanchangaTool : AstrologyTool {
    override val name = "get_panchanga"
    override val description = "Get Panchanga (Vedic calendar) elements for birth time or current time"
    override val parameters = listOf(
        ToolParameter(
            name = "for_now",
            description = "Get panchanga for current time (true) or birth time (false). Default: false",
            type = ParameterType.BOOLEAN,
            required = false,
            defaultValue = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
        val forNow = arguments.optBoolean("for_now", false)

        if (!forNow && chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first or use for_now=true"
            )
        }

        try {
            val panchangaCalculator = PanchangaCalculatorWrapper(context.context)
            val panchanga = if (forNow) {
                // Use default coordinates when profile doesn't have detailed location data
                panchangaCalculator.calculateForNow(27.7172, 85.3240) // Default: Kathmandu
            } else {
                panchangaCalculator.calculate(chart!!)
            }

            val data = JSONObject().apply {
                put("tithi", JSONObject().apply {
                    put("name", panchanga.tithi.name)
                    put("number", panchanga.tithi.number)
                    put("paksha", panchanga.tithi.paksha)
                    put("deity", panchanga.tithi.deity)
                })
                put("nakshatra", JSONObject().apply {
                    put("name", panchanga.nakshatra.name)
                    put("ruler", panchanga.nakshatra.ruler)
                    put("pada", panchanga.nakshatra.pada)
                })
                put("yoga", JSONObject().apply {
                    put("name", panchanga.yoga.name)
                    put("nature", panchanga.yoga.nature)
                })
                put("karana", JSONObject().apply {
                    put("name", panchanga.karana.name)
                    put("nature", panchanga.karana.nature)
                })
                put("vara", JSONObject().apply {
                    put("name", panchanga.vara.name)
                    put("lord", panchanga.vara.lord)
                })
                put("rahuKala", panchanga.rahuKala)
                put("yamaGanda", panchanga.yamaGanda)
                put("gulikaKala", panchanga.gulikaKala)
                put("abhijitMuhurta", panchanga.abhijitMuhurta)
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Panchanga: ${panchanga.tithi.name}, ${panchanga.nakshatra.name}, ${panchanga.vara.name}"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to calculate Panchanga"
            )
        }
    }
}

/**
 * Tool to get current transits
 */
class GetTransitsTool : AstrologyTool {
    override val name = "get_transits"
    override val description = "Get current planetary transits and their effects on the birth chart"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get transits for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        ),
        ToolParameter(
            name = "planets",
            description = "Specific planets to check transits for (comma-separated). Default: all major planets",
            type = ParameterType.STRING,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        try {
            val transitCalculator = TransitCalculatorWrapper(context.context)
            val transits = transitCalculator.calculateCurrentTransits(chart)

            val data = JSONObject().apply {
                put("date", SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date()))
                put("transits", JSONArray().apply {
                    transits.forEach { transit ->
                        put(JSONObject().apply {
                            put("planet", transit.planet.displayName)
                            put("transitSign", transit.transitSign.displayName)
                            put("transitHouse", transit.transitHouse)
                            put("natalSign", transit.natalSign.displayName)
                            put("natalHouse", transit.natalHouse)
                            put("aspect", transit.aspect)
                            put("isRetrograde", transit.isRetrograde)
                            put("effect", transit.effect)
                            put("intensity", transit.intensity)
                        })
                    }
                })
                put("majorTransits", JSONArray().apply {
                    transits.filter { transitItem -> transitItem.intensity >= 3 }.forEach { transit ->
                        put("${transit.planet.displayName} transiting ${transit.transitHouse}th house: ${transit.effect}")
                    }
                })
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Current transits analyzed for ${transits.size} planets"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to calculate transits"
            )
        }
    }
}

/**
 * Tool to get compatibility analysis
 */
class GetCompatibilityTool : AstrologyTool {
    override val name = "get_compatibility"
    override val description = "Get Kundli Milan (compatibility analysis) between two charts"
    override val parameters = listOf(
        ToolParameter(
            name = "partner_profile_id",
            description = "Profile ID of the partner to check compatibility with",
            type = ParameterType.STRING,
            required = true
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart1 = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select your profile first"
            )

        val partnerId = arguments.optString("partner_profile_id", "")
        if (partnerId.isEmpty()) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = "Partner profile ID required",
                summary = "Please provide partner_profile_id"
            )
        }

        val partnerProfile = context.allProfiles.find { it.id.toString() == partnerId }
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "Partner profile not found",
                summary = "Profile ID $partnerId not found"
            )

        try {
            // Load partner chart from database using repository
            val chartRepository = com.astro.storm.data.repository.ChartRepository(context.database.chartDao())
            val partnerChart = chartRepository.getChartById(partnerProfile.id)
                ?: return ToolExecutionResult(
                    success = false,
                    data = null,
                    error = "Partner chart not found",
                    summary = "Could not load partner's chart"
                )

            val compatibilityCalculator = CompatibilityCalculatorWrapper()
            val result = compatibilityCalculator.calculateKundliMilan(chart1, partnerChart)

            val data = JSONObject().apply {
                put("profile1", context.currentProfile?.name)
                put("profile2", partnerProfile.name)
                put("totalScore", result.totalScore)
                put("maxScore", 36)
                put("percentage", String.format("%.1f", (result.totalScore / 36.0) * 100))
                put("verdict", result.verdict)
                put("kutas", JSONArray().apply {
                    result.kutas.forEach { kuta ->
                        put(JSONObject().apply {
                            put("name", kuta.name)
                            put("points", kuta.points)
                            put("maxPoints", kuta.maxPoints)
                            put("description", kuta.description)
                        })
                    }
                })
                put("doshas", JSONArray().apply {
                    result.doshas.forEach { dosha ->
                        put(JSONObject().apply {
                            put("name", dosha.name)
                            put("present", dosha.isPresent)
                            put("severity", dosha.severity)
                            put("remedy", dosha.remedy)
                        })
                    }
                })
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Compatibility: ${result.totalScore}/36 (${String.format("%.0f", (result.totalScore / 36.0) * 100)}%)"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to calculate compatibility"
            )
        }
    }
}

/**
 * Tool to get remedies
 */
class GetRemediesTool : AstrologyTool {
    override val name = "get_remedies"
    override val description = "Get personalized Vedic remedies based on the chart"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get remedies for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        ),
        ToolParameter(
            name = "focus_area",
            description = "Area to focus remedies on: career, health, relationships, wealth, spiritual, all. Default: all",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "all"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        val focusArea = arguments.optString("focus_area", "all")

        try {
            val remedyCalculator = RemedyCalculatorWrapper()
            val remedies = remedyCalculator.calculateRemedies(chart, focusArea)

            val data = JSONObject().apply {
                put("focusArea", focusArea)
                put("afflictedPlanets", JSONArray().apply {
                    remedies.afflictedPlanets.forEach { planet ->
                        put(JSONObject().apply {
                            put("planet", planet.planet.displayName)
                            put("affliction", planet.affliction)
                            put("severity", planet.severity)
                        })
                    }
                })
                put("remedies", JSONArray().apply {
                    remedies.recommendations.forEach { remedy ->
                        put(JSONObject().apply {
                            put("type", remedy.type)
                            put("planet", remedy.forPlanet?.displayName)
                            put("remedy", remedy.description)
                            put("mantra", remedy.mantra)
                            put("gemstone", remedy.gemstone)
                            put("charity", remedy.charity)
                            put("fasting", remedy.fasting)
                            put("color", remedy.color)
                            put("day", remedy.day)
                        })
                    }
                })
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "${remedies.recommendations.size} remedies suggested for ${remedies.afflictedPlanets.size} afflicted planets"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to calculate remedies"
            )
        }
    }
}

/**
 * Tool to get planetary strength analysis (Shadbala)
 */
class GetStrengthAnalysisTool : AstrologyTool {
    override val name = "get_strength_analysis"
    override val description = "Get Shadbala (six-fold planetary strength) analysis"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get strength analysis for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        ),
        ToolParameter(
            name = "planet",
            description = "Specific planet to analyze. Leave empty for all planets",
            type = ParameterType.STRING,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        val planetFilter = arguments.optString("planet", "")

        try {
            val shadbalaCalculator = ShadbalaCalculatorWrapper()
            val shadbala = shadbalaCalculator.calculate(chart)

            val filteredResults = if (planetFilter.isEmpty()) {
                shadbala
            } else {
                shadbala.filter { result -> result.planet.displayName.equals(planetFilter, ignoreCase = true) }
            }

            val data = JSONObject().apply {
                put("planets", JSONArray().apply {
                    filteredResults.forEach { strength ->
                        put(JSONObject().apply {
                            put("planet", strength.planet.displayName)
                            put("totalStrength", String.format("%.2f", strength.totalStrength))
                            put("requiredStrength", strength.requiredStrength)
                            put("isStrong", strength.totalStrength >= strength.requiredStrength)
                            put("components", JSONObject().apply {
                                put("sthana_bala", String.format("%.2f", strength.sthanaBala))
                                put("dig_bala", String.format("%.2f", strength.digBala))
                                put("kala_bala", String.format("%.2f", strength.kalaBala))
                                put("chesta_bala", String.format("%.2f", strength.chestaBala))
                                put("naisargika_bala", String.format("%.2f", strength.naisargikaBala))
                                put("drik_bala", String.format("%.2f", strength.drikBala))
                            })
                        })
                    }
                })
                put("strongestPlanet", filteredResults.maxByOrNull { it.totalStrength }?.planet?.displayName)
                put("weakestPlanet", filteredResults.minByOrNull { it.totalStrength }?.planet?.displayName)
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Shadbala analysis for ${filteredResults.size} planets"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to calculate Shadbala"
            )
        }
    }
}

/**
 * Tool to get divisional chart information
 */
class GetDivisionalChartTool : AstrologyTool {
    override val name = "get_divisional_chart"
    override val description = "Get a divisional chart (Varga) like Navamsa, Dasamsa, etc."
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get divisional chart for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        ),
        ToolParameter(
            name = "varga",
            description = "Divisional chart: D1 (Rashi), D2 (Hora), D3 (Drekkana), D4 (Chaturthamsa), D7 (Saptamsa), D9 (Navamsa), D10 (Dasamsa), D12 (Dwadashamsa), D16 (Shodashamsa), D20 (Vimshamsa), D24 (Chaturvimshamsa), D27 (Nakshatramsa), D30 (Trimshamsa), D40 (Khavedamsa), D45 (Akshavedamsa), D60 (Shashtiamsa)",
            type = ParameterType.STRING,
            required = true
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        val varga = arguments.optString("varga", "D9").uppercase()

        try {
            val vargaCalculator = VargaCalculatorWrapper()
            val divisionalChart = vargaCalculator.calculate(chart, varga)

            val vargaMeanings = mapOf(
                "D1" to "Rashi - Physical body, general life",
                "D2" to "Hora - Wealth and financial prosperity",
                "D3" to "Drekkana - Siblings and courage",
                "D4" to "Chaturthamsa - Fortune and property",
                "D7" to "Saptamsa - Children and progeny",
                "D9" to "Navamsa - Marriage, spouse, dharma, spiritual path",
                "D10" to "Dasamsa - Career, profession, public life",
                "D12" to "Dwadashamsa - Parents and ancestors",
                "D16" to "Shodashamsa - Vehicles and conveyances",
                "D20" to "Vimshamsa - Spiritual progress and worship",
                "D24" to "Chaturvimshamsa - Education and learning",
                "D27" to "Nakshatramsa - Strengths and weaknesses",
                "D30" to "Trimshamsa - Evils and misfortunes",
                "D40" to "Khavedamsa - Auspicious effects",
                "D45" to "Akshavedamsa - General well-being",
                "D60" to "Shashtiamsa - Past life karma, all matters"
            )

            val data = JSONObject().apply {
                put("varga", varga)
                put("name", divisionalChart.name)
                put("meaning", vargaMeanings[varga] ?: "Divisional chart")
                put("ascendant", JSONObject().apply {
                    put("sign", divisionalChart.ascendantSign.displayName)
                    put("degree", String.format("%.2f", divisionalChart.ascendantDegree))
                })
                put("planets", JSONArray().apply {
                    divisionalChart.planets.forEach { pos ->
                        put(JSONObject().apply {
                            put("planet", pos.planet.displayName)
                            put("sign", pos.sign.displayName)
                            put("degree", String.format("%.2f", pos.degree))
                            put("house", pos.house)
                        })
                    }
                })
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "$varga (${divisionalChart.name}) chart generated"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to calculate divisional chart"
            )
        }
    }
}

/**
 * Tool to calculate Muhurta (auspicious timing)
 */
class CalculateMuhurtaTool : AstrologyTool {
    override val name = "calculate_muhurta"
    override val description = "Find auspicious times (Muhurta) for specific activities"
    override val parameters = listOf(
        ToolParameter(
            name = "activity",
            description = "Activity type: marriage, travel, business, education, medical, property, spiritual, general",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "days_ahead",
            description = "How many days ahead to search (default: 7, max: 30)",
            type = ParameterType.INTEGER,
            required = false,
            defaultValue = 7
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val activity = arguments.optString("activity", "general")
        val daysAhead = arguments.optInt("days_ahead", 7).coerceIn(1, 30)

        try {
            val muhurtaCalculator = MuhurtaCalculatorWrapper(context.context)
            // Use default coordinates when profile doesn't have detailed location data
            val latitude = 27.7172  // Default to Kathmandu
            val longitude = 85.3240

            val muhurtas = muhurtaCalculator.findAuspiciousTimes(
                activity = activity,
                latitude = latitude,
                longitude = longitude,
                daysAhead = daysAhead
            )

            val data = JSONObject().apply {
                put("activity", activity)
                put("searchDays", daysAhead)
                put("location", context.currentProfile?.location ?: "Default")
                put("auspiciousTimes", JSONArray().apply {
                    muhurtas.take(10).forEach { muhurta ->
                        put(JSONObject().apply {
                            put("date", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(muhurta.date))
                            put("day", SimpleDateFormat("EEEE", Locale.getDefault()).format(muhurta.date))
                            put("startTime", muhurta.startTime)
                            put("endTime", muhurta.endTime)
                            put("quality", muhurta.quality)
                            put("score", muhurta.score)
                            put("tithi", muhurta.tithi)
                            put("nakshatra", muhurta.nakshatra)
                            put("yoga", muhurta.yoga)
                            put("factors", JSONArray(muhurta.favorableFactors))
                            if (muhurta.cautions.isNotEmpty()) {
                                put("cautions", JSONArray(muhurta.cautions))
                            }
                        })
                    }
                })
                put("generalGuidance", getActivityGuidance(activity))
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Found ${muhurtas.size} auspicious times for $activity"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to calculate Muhurta"
            )
        }
    }

    private fun getActivityGuidance(activity: String): String {
        return when (activity.lowercase()) {
            "marriage" -> "For marriage, also check Kundli Milan score, avoid Rahu Kala, and prefer bright lunar fortnight (Shukla Paksha)"
            "travel" -> "For travel, Moon should be strong, avoid Rahu Kala and Yamaghanda, prefer days ruled by benefic planets"
            "business" -> "For business ventures, Mercury and Jupiter should be well-placed, avoid eclipses and combustion periods"
            "education" -> "For education, Mercury's strength is crucial, prefer Pushya nakshatra and Wednesday/Thursday"
            "medical" -> "For medical procedures, avoid Mars afflictions, prefer waxing Moon, avoid surgery during nakshatra of the body part"
            "property" -> "For property matters, Venus and Mars positions are important, avoid Rahu-Ketu axis involvement"
            "spiritual" -> "For spiritual activities, Jupiter and Moon strength matters, prefer Brahma Muhurta and auspicious nakshatras"
            else -> "General timing should avoid Rahu Kala, prefer strong Moon, and consider planetary strengths"
        }
    }
}

/**
 * Tool to get Bhrigu Bindu (karmic destiny point)
 */
class GetBhriguBinduTool : AstrologyTool {
    override val name = "get_bhrigu_bindu"
    override val description = "Get Bhrigu Bindu (destiny point) analysis showing karmic themes"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get Bhrigu Bindu for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        try {
            val bhriguCalculator = BhriguBinduCalculatorWrapper()
            val result = bhriguCalculator.calculate(chart)

            val data = JSONObject().apply {
                put("bhriguBindu", JSONObject().apply {
                    put("longitude", String.format("%.2f", result.longitude))
                    put("sign", result.sign.displayName)
                    put("degree", String.format("%.2f", result.degree))
                    put("nakshatra", result.nakshatra.displayName)
                    put("nakshatraPada", result.nakshatraPada)
                    put("house", result.house)
                })
                put("interpretation", result.interpretation)
                put("karmicThemes", JSONArray(result.karmicThemes))
                put("activationPeriods", JSONArray().apply {
                    result.activationPeriods.forEach { period ->
                        put(JSONObject().apply {
                            put("trigger", period.trigger)
                            put("timing", period.timing)
                            put("effect", period.effect)
                        })
                    }
                })
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Bhrigu Bindu at ${result.degree.toInt()}° ${result.sign.displayName} in house ${result.house}"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to calculate Bhrigu Bindu"
            )
        }
    }
}

/**
 * Tool to get Argala (Jaimini intervention) analysis
 */
class GetArgalaTool : AstrologyTool {
    override val name = "get_argala"
    override val description = "Get Argala (planetary intervention) analysis using Jaimini principles"
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to get Argala analysis for. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        ),
        ToolParameter(
            name = "house",
            description = "House number to analyze Argala for (1-12). Leave empty for all houses",
            type = ParameterType.INTEGER,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        val houseFilter = arguments.optInt("house", 0)

        try {
            val argalaCalculator = ArgalaCalculatorWrapper()
            val argalas = argalaCalculator.calculate(chart)

            val filteredArgalas = if (houseFilter > 0) {
                argalas.filter { argala -> argala.targetHouse == houseFilter }
            } else {
                argalas
            }

            val data = JSONObject().apply {
                put("description", "Argala shows how planets intervene in house matters. Primary Argala from 2nd, 4th, 11th houses. Obstruction (Virodha Argala) from 12th, 10th, 3rd houses.")
                put("houses", JSONArray().apply {
                    val groupedByHouse = filteredArgalas.groupBy { argala -> argala.targetHouse }
                    groupedByHouse.forEach { (house, argalaList) ->
                        put(JSONObject().apply {
                            put("house", house)
                            put("argalas", JSONArray().apply {
                                argalaList.forEach { argala ->
                                    put(JSONObject().apply {
                                        put("type", argala.type)
                                        put("fromHouse", argala.sourceHouse)
                                        put("planets", JSONArray(argala.planets.map { planet -> planet.displayName }))
                                        put("strength", argala.strength)
                                        put("isObstructed", argala.isObstructed)
                                        put("effect", argala.effect)
                                    })
                                }
                            })
                            put("netEffect", argalaList.sumOf { argala -> if (argala.isObstructed) -argala.strength else argala.strength })
                        })
                    }
                })
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Argala analysis for ${if (houseFilter > 0) "house $houseFilter" else "all houses"}"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to calculate Argala"
            )
        }
    }
}

// Note: Compatibility analysis requires chart conversion.
// The CompatibilityCalculator should load the partner chart directly from the database
// and handle the conversion internally.

// ============================================
// PRASHNA (HORARY) ASTROLOGY TOOL
// ============================================

/**
 * Tool to perform Prashna (Horary) astrology analysis.
 * Generates a chart for the moment the question is asked and provides guidance.
 */
class GetPrashnaAnalysisTool : AstrologyTool {
    override val name = "get_prashna_analysis"
    override val description = "Perform Prashna (Horary) astrology analysis. Generates a chart for the current moment to answer a specific question. Provides verdict, confidence, and detailed interpretation based on Lagna, Moon, and relevant house analysis."
    override val parameters = listOf(
        ToolParameter(
            name = "question",
            description = "The specific question being asked (e.g., 'Will I get the job?', 'Should I invest in this property?')",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "category",
            description = "Category of question: YES_NO, CAREER, MARRIAGE, CHILDREN, HEALTH, WEALTH, PROPERTY, TRAVEL, EDUCATION, LEGAL, LOST_OBJECT, RELATIONSHIP, BUSINESS, SPIRITUAL, GENERAL",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "GENERAL"
        ),
        ToolParameter(
            name = "latitude",
            description = "Latitude of the querent's location. If not provided, uses profile's birth location.",
            type = ParameterType.NUMBER,
            required = false
        ),
        ToolParameter(
            name = "longitude",
            description = "Longitude of the querent's location. If not provided, uses profile's birth location.",
            type = ParameterType.NUMBER,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val question = arguments.optString("question", "").trim()
        if (question.isEmpty()) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = "Question is required for Prashna analysis",
                summary = "Please provide a specific question"
            )
        }

        val category = arguments.optString("category", "GENERAL").uppercase()

        // Get location from arguments or from profile's birth data
        val latitude = if (arguments.has("latitude")) {
            arguments.getDouble("latitude")
        } else {
            context.currentChart?.birthData?.latitude ?: 28.6139 // Default to Delhi
        }

        val longitude = if (arguments.has("longitude")) {
            arguments.getDouble("longitude")
        } else {
            context.currentChart?.birthData?.longitude ?: 77.2090 // Default to Delhi
        }

        val timezone = context.currentChart?.birthData?.timezone
            ?: java.util.TimeZone.getDefault().id

        try {
            val calculator = PrashnaCalculatorWrapper(context.context)
            val result = calculator.analyzePrashna(
                question = question,
                category = category,
                latitude = latitude,
                longitude = longitude,
                timezone = timezone
            )

            val data = JSONObject().apply {
                put("question", result.question)
                put("category", result.category)
                put("verdict", result.verdict)
                put("verdictScore", result.verdictScore)
                put("confidence", result.confidence)

                put("moonAnalysis", JSONObject().apply {
                    put("sign", result.moonAnalysis.sign)
                    put("nakshatra", result.moonAnalysis.nakshatra)
                    put("house", result.moonAnalysis.house)
                    put("strength", result.moonAnalysis.strength)
                    put("isFavorable", result.moonAnalysis.isFavorable)
                    put("interpretation", result.moonAnalysis.interpretation)
                })

                put("lagnaAnalysis", JSONObject().apply {
                    put("sign", result.lagnaAnalysis.sign)
                    put("lord", result.lagnaAnalysis.lord)
                    put("lordHouse", result.lagnaAnalysis.lordHouse)
                    put("lordStrength", result.lagnaAnalysis.lordStrength)
                    put("interpretation", result.lagnaAnalysis.interpretation)
                })

                put("houseAnalysis", JSONObject().apply {
                    put("relevantHouse", result.houseAnalysis.relevantHouse)
                    put("houseLord", result.houseAnalysis.houseLord)
                    put("houseLordPosition", result.houseAnalysis.houseLordPosition)
                    put("planetsInHouse", JSONArray(result.houseAnalysis.planetsInHouse))
                    put("aspects", JSONArray(result.houseAnalysis.aspects))
                    put("strength", result.houseAnalysis.strength)
                    put("interpretation", result.houseAnalysis.interpretation)
                })

                put("specialYogas", JSONArray().apply {
                    result.specialYogas.forEach { yoga ->
                        put(JSONObject().apply {
                            put("name", yoga.name)
                            put("isFavorable", yoga.isFavorable)
                            put("effect", yoga.effect)
                        })
                    }
                })

                put("omens", JSONArray().apply {
                    result.omens.forEach { omen ->
                        put(JSONObject().apply {
                            put("type", omen.type)
                            put("description", omen.description)
                            put("significance", omen.significance)
                        })
                    }
                })

                put("timingPrediction", JSONObject().apply {
                    put("shortTerm", result.timingPrediction.shortTerm)
                    put("mediumTerm", result.timingPrediction.mediumTerm)
                    put("longTerm", result.timingPrediction.longTerm)
                    put("favorableDays", JSONArray(result.timingPrediction.favorableDays))
                    put("unfavorableDays", JSONArray(result.timingPrediction.unfavorableDays))
                })

                put("recommendations", JSONArray(result.recommendations))
                put("detailedInterpretation", result.detailedInterpretation)
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Prashna analysis for '${question.take(50)}${if (question.length > 50) "..." else ""}': ${result.verdict} (${result.confidence}% confidence)"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to perform Prashna analysis"
            )
        }
    }
}

// ============================================
// COMPATIBILITY DEEP DIVE TOOL
// ============================================

/**
 * Tool for deep compatibility analysis between two profiles.
 * Goes beyond basic Guna Milan to include Manglik, doshas, and remedies.
 */
class GetCompatibilityDeepDiveTool : AstrologyTool {
    override val name = "get_compatibility_deep_dive"
    override val description = "Perform comprehensive compatibility analysis (Kundli Milan) between two profiles. Includes all 8 Gunas, Manglik analysis, Vedha Dosha, Rajju Dosha, Stree Deergha, Mahendra, special considerations, and remedies."
    override val parameters = listOf(
        ToolParameter(
            name = "profile1_id",
            description = "ID of the first profile (typically bride). Use 'current' for active profile.",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "profile2_id",
            description = "ID of the second profile (typically groom).",
            type = ParameterType.STRING,
            required = true
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profile1Id = arguments.optString("profile1_id", "current")
        val profile2Id = arguments.optString("profile2_id", "")

        if (profile2Id.isEmpty()) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = "Second profile ID is required",
                summary = "Please provide both profile IDs for compatibility analysis"
            )
        }

        // Get profiles
        val profile1 = if (profile1Id == "current") {
            context.currentProfile
        } else {
            context.allProfiles.find { it.id.toString() == profile1Id }
        }

        val profile2 = context.allProfiles.find { it.id.toString() == profile2Id }

        if (profile1 == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = "First profile not found",
                summary = "Profile with ID '$profile1Id' not found"
            )
        }

        if (profile2 == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = "Second profile not found",
                summary = "Profile with ID '$profile2Id' not found"
            )
        }

        try {
            // Load charts from database
            val chartDao = context.database.chartDao()
            val chartEntity1 = chartDao.getChartById(profile1.id)
            val chartEntity2 = chartDao.getChartById(profile2.id)

            if (chartEntity1 == null || chartEntity2 == null) {
                return ToolExecutionResult(
                    success = false,
                    data = null,
                    error = "Chart data not found for one or both profiles",
                    summary = "Unable to load chart data"
                )
            }

            val chart1 = chartEntity1.toVedicChart()
            val chart2 = chartEntity2.toVedicChart()

            val wrapper = CompatibilityDeepDiveWrapper()
            val result = wrapper.analyzeDeepCompatibility(chart1, chart2)

            val data = JSONObject().apply {
                put("profile1Name", profile1.name)
                put("profile2Name", profile2.name)
                put("totalScore", result.totalScore)
                put("maxScore", result.maxScore)
                put("percentage", (result.totalScore / result.maxScore * 100).toInt())
                put("rating", result.rating)

                put("gunaAnalysis", JSONArray().apply {
                    result.gunaAnalysis.forEach { guna ->
                        put(JSONObject().apply {
                            put("name", guna.name)
                            put("obtainedPoints", guna.obtainedPoints)
                            put("maxPoints", guna.maxPoints)
                            put("description", guna.description)
                            put("brideValue", guna.brideValue)
                            put("groomValue", guna.groomValue)
                            put("assessment", guna.assessment)
                        })
                    }
                })

                put("manglikAnalysis", JSONObject().apply {
                    put("brideIsManglik", result.manglikAnalysis.brideIsManglik)
                    put("groomIsManglik", result.manglikAnalysis.groomIsManglik)
                    put("brideManglikStrength", result.manglikAnalysis.brideManglikStrength)
                    put("groomManglikStrength", result.manglikAnalysis.groomManglikStrength)
                    put("manglikCompatibility", result.manglikAnalysis.manglikCompatibility)
                })

                put("additionalFactors", JSONObject().apply {
                    put("vedhaPresent", result.additionalFactors.vedhaPresent)
                    put("vedhaDetails", result.additionalFactors.vedhaDetails)
                    put("rajjuCompatible", result.additionalFactors.rajjuCompatible)
                    put("rajjuDetails", result.additionalFactors.rajjuDetails)
                    put("streeDeergha", result.additionalFactors.streeDeergha)
                    put("streeDeerghaCount", result.additionalFactors.streeDeerghaCount)
                    put("mahendra", result.additionalFactors.mahendra)
                    put("mahendraDetails", result.additionalFactors.mahendraDetails)
                })

                put("specialConsiderations", JSONArray(result.specialConsiderations))
                put("remedies", JSONArray(result.remedies))

                put("summary", result.summary)
                put("detailedAnalysis", result.detailedAnalysis)
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Compatibility analysis: ${profile1.name} & ${profile2.name} - ${result.totalScore}/${result.maxScore} (${result.rating})"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to perform compatibility analysis"
            )
        }
    }
}

// ============================================
// MARAKA (DEATH-INFLICTING) ANALYSIS TOOL
// ============================================

/**
 * Tool to get Maraka (death-inflicting planet) analysis.
 * Analyzes 2nd and 7th house lords and occupants for longevity indications.
 */
class GetMarakaAnalysisTool : AstrologyTool {
    override val name = "get_maraka_analysis"
    override val description = "Get Maraka (death-inflicting planet) analysis including longevity assessment, health vulnerabilities, and remedies. Analyzes 2nd and 7th house lords (primary Marakas) and occupants."
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to analyze. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        try {
            val result = com.astro.storm.ephemeris.MarakaCalculator.analyzeMaraka(chart)
                ?: return ToolExecutionResult(
                    success = false,
                    data = null,
                    error = "Failed to analyze Maraka planets",
                    summary = "Unable to perform Maraka analysis"
                )

            val profileName = getProfileName(profileId, context)

            val data = JSONObject().apply {
                put("profileName", profileName)
                put("secondHouseLord", result.secondHouseLord.displayName)
                put("seventhHouseLord", result.seventhHouseLord.displayName)

                put("primaryMarakas", JSONArray().apply {
                    result.primaryMarakas.forEach { maraka ->
                        put(JSONObject().apply {
                            put("planet", maraka.planet.displayName)
                            put("type", maraka.marakaType.displayName)
                            put("severity", maraka.severity.displayName)
                            put("house", maraka.housePosition)
                            put("sign", maraka.position.sign.displayName)
                            put("isRetrograde", maraka.isRetrograde)
                            put("isDebilitated", maraka.isDebilitated)
                            put("isExalted", maraka.isExalted)
                            put("interpretation", maraka.interpretation)
                        })
                    }
                })

                put("secondaryMarakas", JSONArray().apply {
                    result.secondaryMarakas.forEach { maraka ->
                        put(JSONObject().apply {
                            put("planet", maraka.planet.displayName)
                            put("type", maraka.marakaType.displayName)
                            put("severity", maraka.severity.displayName)
                            put("house", maraka.housePosition)
                            put("interpretation", maraka.interpretation)
                        })
                    }
                })

                put("longevityAnalysis", JSONObject().apply {
                    put("category", result.longevityAnalysis.category.displayName)
                    put("description", result.longevityAnalysis.category.description)
                    put("yearsRange", result.longevityAnalysis.category.yearsRange)
                    put("overallScore", result.longevityAnalysis.overallScore)
                    put("interpretation", result.longevityAnalysis.interpretation)
                    put("supportingFactors", JSONArray(result.longevityAnalysis.supportingFactors))
                    put("challengingFactors", JSONArray(result.longevityAnalysis.challengingFactors))
                })

                put("overallSeverity", result.overallMarakaSeverity.displayName)
                put("healthVulnerabilities", JSONArray(result.healthVulnerabilities))
                put("protectiveFactors", JSONArray(result.protectiveFactors))

                put("dashaPeriods", JSONArray().apply {
                    result.dashaPeriods.take(5).forEach { period ->
                        put(JSONObject().apply {
                            put("planet", period.planet.displayName)
                            put("marakaType", period.marakaType.displayName)
                            put("riskLevel", period.riskLevel.displayName)
                            put("description", period.periodDescription)
                            put("healthConcerns", JSONArray(period.healthConcerns))
                            put("precautions", JSONArray(period.precautions))
                        })
                    }
                })

                put("remedies", JSONArray().apply {
                    result.remedies.take(5).forEach { remedy ->
                        put(JSONObject().apply {
                            put("planet", remedy.planet?.displayName)
                            put("type", remedy.remedyType)
                            put("description", remedy.description)
                            put("mantra", remedy.mantra)
                            put("gemstone", remedy.gemstone)
                            put("charity", remedy.charity)
                            put("fasting", remedy.fasting)
                            put("deity", remedy.deity)
                        })
                    }
                })

                put("summary", result.summary)
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Maraka analysis for $profileName: ${result.longevityAnalysis.category.displayName} longevity, ${result.overallMarakaSeverity.displayName} severity"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to perform Maraka analysis"
            )
        }
    }
}

// ============================================
// BADHAKA (OBSTRUCTION) ANALYSIS TOOL
// ============================================

/**
 * Tool to get Badhaka (obstruction planet) analysis.
 * Analyzes Badhaka Sthana based on ascendant modality and its lord.
 */
class GetBadhakaAnalysisTool : AstrologyTool {
    override val name = "get_badhaka_analysis"
    override val description = "Get Badhaka (obstruction planet) analysis. Identifies Badhaka Sthana based on ascendant type (Movable/Fixed/Dual), analyzes Badhakesh lord, and provides obstacle predictions with remedies."
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to analyze. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        try {
            val result = com.astro.storm.ephemeris.BadhakaCalculator.analyzeBadhaka(chart)
                ?: return ToolExecutionResult(
                    success = false,
                    data = null,
                    error = "Failed to analyze Badhaka planets",
                    summary = "Unable to perform Badhaka analysis"
                )

            val profileName = getProfileName(profileId, context)

            val data = JSONObject().apply {
                put("profileName", profileName)
                put("ascendantSign", result.ascendantSign.displayName)
                put("signModality", result.signModality.displayName)
                put("signModalityDescription", result.signModality.description)
                put("badhakaHouse", result.badhakaHouse)
                put("badhakeshPlanet", result.badhakeshPlanet.displayName)

                put("badhakeshAnalysis", JSONObject().apply {
                    val bp = result.badhakeshPosition
                    put("planet", bp.planet.displayName)
                    put("house", bp.position.house)
                    put("sign", bp.position.sign.displayName)
                    put("severity", bp.severity.displayName)
                    put("strength", bp.strength)
                    put("isRetrograde", bp.isRetrograde)
                    put("isDebilitated", bp.isDebilitated)
                    put("isExalted", bp.isExalted)
                    put("isCombust", bp.isCombust)
                    put("interpretation", bp.interpretation)
                })

                put("planetsInBadhakaHouse", JSONArray().apply {
                    result.planetsInBadhakaHouse.forEach { planet ->
                        put(JSONObject().apply {
                            put("planet", planet.planet.displayName)
                            put("severity", planet.severity.displayName)
                            put("interpretation", planet.interpretation)
                        })
                    }
                })

                put("overallSeverity", result.overallSeverity.displayName)

                put("primaryObstacles", JSONArray().apply {
                    result.primaryObstacles.forEach { obstacle ->
                        put(JSONObject().apply {
                            put("type", obstacle.displayName)
                            put("description", obstacle.description)
                        })
                    }
                })

                put("affectedHouses", JSONArray(result.affectedHouses))

                put("dashaPeriods", JSONArray().apply {
                    result.dashaPeriods.take(5).forEach { period ->
                        put(JSONObject().apply {
                            put("planet", period.planet.displayName)
                            put("isBadhakesh", period.isBadhakesh)
                            put("obstructionLevel", period.obstructionLevel.displayName)
                            put("description", period.periodDescription)
                            put("affectedAreas", JSONArray(period.affectedAreas.map { it.displayName }))
                            put("recommendations", JSONArray(period.recommendations))
                        })
                    }
                })

                put("protectiveFactors", JSONArray(result.protectiveFactors))

                put("remedies", JSONArray().apply {
                    result.remedies.take(5).forEach { remedy ->
                        put(JSONObject().apply {
                            put("planet", remedy.planet?.displayName)
                            put("type", remedy.remedyType)
                            put("description", remedy.description)
                            put("mantra", remedy.mantra)
                            put("deity", remedy.deity)
                            put("charity", remedy.charity)
                            put("ritual", remedy.ritual)
                        })
                    }
                })

                put("summary", result.summary)
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Badhaka analysis for $profileName: ${result.signModality.displayName} ascendant, ${result.badhakaHouse}th house Badhaka, ${result.overallSeverity.displayName} severity"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to perform Badhaka analysis"
            )
        }
    }
}

// ============================================
// VIPAREETA RAJA YOGA ANALYSIS TOOL
// ============================================

/**
 * Tool to get Vipareeta Raja Yoga analysis.
 * Detects Harsha, Sarala, and Vimala yogas formed when dusthana lords occupy dusthana houses.
 */
class GetVipareetaRajaYogaTool : AstrologyTool {
    override val name = "get_vipareeta_raja_yoga"
    override val description = "Get Vipareeta Raja Yoga analysis. Detects Harsha (6th lord), Sarala (8th lord), and Vimala (12th lord) yogas formed when dusthana lords occupy dusthana houses. These 'reverse' Raja Yogas bring success through difficulties."
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to analyze. Use 'current' for active profile or leave empty.",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "current"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileId = arguments.optString("profile_id", "current")
        val (chart, error) = getChartForProfile(profileId, context)

        if (chart == null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = error ?: "No chart loaded",
                summary = error ?: "Please select a profile first"
            )
        }

        try {
            val result = com.astro.storm.ephemeris.VipareetaRajaYogaCalculator.analyzeVipareetaRajaYogas(chart)
                ?: return ToolExecutionResult(
                    success = false,
                    data = null,
                    error = "Failed to analyze Vipareeta Raja Yogas",
                    summary = "Unable to perform Vipareeta Raja Yoga analysis"
                )

            val profileName = getProfileName(profileId, context)

            val data = JSONObject().apply {
                put("profileName", profileName)
                put("ascendantSign", result.ascendantSign.displayName)
                put("sixthLord", result.sixthLord.displayName)
                put("eighthLord", result.eighthLord.displayName)
                put("twelfthLord", result.twelfthLord.displayName)

                put("harshaYoga", buildYogaJson(result.harshaYoga))
                put("saralaYoga", buildYogaJson(result.saralaYoga))
                put("vimalaYoga", buildYogaJson(result.vimalaYoga))

                put("dusthanaExchanges", JSONArray().apply {
                    result.dusthanaExchanges.forEach { exchange ->
                        put(JSONObject().apply {
                            put("planet1", exchange.planet1.displayName)
                            put("planet2", exchange.planet2.displayName)
                            put("house1", exchange.house1)
                            put("house2", exchange.house2)
                            put("isParivartana", exchange.isParivartana)
                            put("effect", exchange.effect)
                            put("strength", exchange.strength.displayName)
                        })
                    }
                })

                put("totalYogasFormed", result.totalYogasFormed)
                put("overallStrength", result.overallStrength.displayName)
                put("primaryBenefits", JSONArray(result.primaryBenefits))

                put("activationTimeline", JSONArray().apply {
                    result.activationTimeline.take(6).forEach { period ->
                        put(JSONObject().apply {
                            put("planet", period.planet.displayName)
                            put("yogaType", period.yogaType?.displayName)
                            put("description", period.description)
                            put("expectedEffects", JSONArray(period.expectedEffects))
                        })
                    }
                })

                put("enhancementFactors", JSONArray(result.enhancementFactors))
                put("cancellationFactors", JSONArray(result.cancellationFactors))

                put("summary", result.summary)
            }

            val yogasFormedStr = when (result.totalYogasFormed) {
                0 -> "No yogas formed"
                1 -> "1 yoga formed"
                2 -> "2 yogas formed"
                3 -> "All 3 yogas formed (rare!)"
                else -> "${result.totalYogasFormed} yogas"
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Vipareeta Raja Yoga analysis for $profileName: $yogasFormedStr, ${result.overallStrength.displayName} strength"
            )
        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = e.message,
                summary = "Failed to perform Vipareeta Raja Yoga analysis"
            )
        }
    }

    private fun buildYogaJson(yoga: com.astro.storm.ephemeris.VipareetaRajaYogaCalculator.VipareetaYoga): JSONObject {
        return JSONObject().apply {
            put("name", yoga.yogaType.displayName)
            put("sanskritName", yoga.yogaType.sanskritName)
            put("isFormed", yoga.isFormed)
            put("strength", yoga.strength.displayName)
            put("activationStatus", yoga.activationStatus.displayName)
            put("dusthanaLord", yoga.dusthanaLord.displayName)
            put("placedInHouse", yoga.placedInHouse)
            put("placedInSign", yoga.placedInSign.displayName)
            put("isRetrograde", yoga.isRetrograde)
            put("isExalted", yoga.isExalted)
            put("isDebilitated", yoga.isDebilitated)
            put("strengthFactors", JSONArray(yoga.strengthFactors))
            put("weaknessFactors", JSONArray(yoga.weaknessFactors))
            put("benefitsAreas", JSONArray(yoga.benefitsAreas))
            put("activationDashas", JSONArray(yoga.activationDashas.map { it.displayName }))
            put("interpretation", yoga.interpretation)
        }
    }
}
