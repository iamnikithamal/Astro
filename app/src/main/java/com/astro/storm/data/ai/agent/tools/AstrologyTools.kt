package com.astro.storm.data.ai.agent.tools

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.data.model.Nakshatra
// Note: Calculation wrappers are defined locally in this package
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            name = "planets",
            description = "Specific planets to include (comma-separated: Sun,Moon,Mars,etc.). Leave empty for all planets",
            type = ParameterType.STRING,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

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
            name = "house_number",
            description = "Specific house number (1-12). Leave empty for all houses",
            type = ParameterType.INTEGER,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

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
            name = "planet",
            description = "Planet name to get nakshatra for (default: Moon for Janma Nakshatra)",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "Moon"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

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
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

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
    override val parameters = emptyList<ToolParameter>()

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

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
            name = "category",
            description = "Yoga category filter: raja, dhana, pancha_mahapurusha, sun_moon, all (default: all)",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "all"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

        val category = arguments.optString("category", "all")

        try {
            val yogaCalculator = YogaCalculator()
            val allYogas = yogaCalculator.calculateYogas(chart)

            val filteredYogas = if (category == "all") {
                allYogas
            } else {
                allYogas.filter { it.category.lowercase() == category.lowercase() }
            }

            val data = JSONObject().apply {
                put("totalYogas", filteredYogas.size)
                put("auspiciousCount", filteredYogas.count { it.isAuspicious })
                put("inauspiciousCount", filteredYogas.count { !it.isAuspicious })
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
                            put("formingPlanets", JSONArray(yoga.formingPlanets.map { it.displayName }))
                        })
                    }
                })
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Found ${filteredYogas.size} yogas (${filteredYogas.count { it.isAuspicious }} auspicious)"
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
            name = "type",
            description = "Type: sarvashtakavarga (combined) or bhinnashtakavarga (individual). Default: sarvashtakavarga",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "sarvashtakavarga"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

        val type = arguments.optString("type", "sarvashtakavarga")

        try {
            val ashtakavargaCalculator = AshtakavargaCalculator()

            val data = if (type == "sarvashtakavarga") {
                val sav = ashtakavargaCalculator.calculateSarvashtakavarga(chart)
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
                val bav = ashtakavargaCalculator.calculateBhinnashtakavarga(chart)
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
            val panchangaCalculator = PanchangaCalculator()
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
            name = "planets",
            description = "Specific planets to check transits for (comma-separated). Default: all major planets",
            type = ParameterType.STRING,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

        try {
            val transitCalculator = TransitCalculator()
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
                    transits.filter { it.intensity >= 3 }.forEach { transit ->
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

            val compatibilityCalculator = CompatibilityCalculator()
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
            name = "focus_area",
            description = "Area to focus remedies on: career, health, relationships, wealth, spiritual, all. Default: all",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "all"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

        val focusArea = arguments.optString("focus_area", "all")

        try {
            val remedyCalculator = RemedyCalculator()
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
                            put("planet", remedy.forPlanet.displayName)
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
            name = "planet",
            description = "Specific planet to analyze. Leave empty for all planets",
            type = ParameterType.STRING,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

        val planetFilter = arguments.optString("planet", "")

        try {
            val shadbalaCalculator = ShadbalaCalculator()
            val shadbala = shadbalaCalculator.calculate(chart)

            val filteredResults = if (planetFilter.isEmpty()) {
                shadbala
            } else {
                shadbala.filter { it.planet.displayName.equals(planetFilter, ignoreCase = true) }
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
            name = "varga",
            description = "Divisional chart: D1 (Rashi), D2 (Hora), D3 (Drekkana), D4 (Chaturthamsa), D7 (Saptamsa), D9 (Navamsa), D10 (Dasamsa), D12 (Dwadashamsa), D16 (Shodashamsa), D20 (Vimshamsa), D24 (Chaturvimshamsa), D27 (Nakshatramsa), D30 (Trimshamsa), D40 (Khavedamsa), D45 (Akshavedamsa), D60 (Shashtiamsa)",
            type = ParameterType.STRING,
            required = true
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

        val varga = arguments.optString("varga", "D9").uppercase()

        try {
            val vargaCalculator = VargaCalculator()
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
            val muhurtaCalculator = MuhurtaCalculator()
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
    override val parameters = emptyList<ToolParameter>()

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

        try {
            val bhriguCalculator = BhriguBinduCalculator()
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
            name = "house",
            description = "House number to analyze Argala for (1-12). Leave empty for all houses",
            type = ParameterType.INTEGER,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val chart = context.currentChart
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "No chart loaded",
                summary = "Please select a profile first"
            )

        val houseFilter = arguments.optInt("house", 0)

        try {
            val argalaCalculator = ArgalaCalculator()
            val argalas = argalaCalculator.calculate(chart)

            val filteredArgalas = if (houseFilter > 0) {
                argalas.filter { it.targetHouse == houseFilter }
            } else {
                argalas
            }

            val data = JSONObject().apply {
                put("description", "Argala shows how planets intervene in house matters. Primary Argala from 2nd, 4th, 11th houses. Obstruction (Virodha Argala) from 12th, 10th, 3rd houses.")
                put("houses", JSONArray().apply {
                    val groupedByHouse = filteredArgalas.groupBy { it.targetHouse }
                    groupedByHouse.forEach { (house, argalaList) ->
                        put(JSONObject().apply {
                            put("house", house)
                            put("argalas", JSONArray().apply {
                                argalaList.forEach { argala ->
                                    put(JSONObject().apply {
                                        put("type", argala.type)
                                        put("fromHouse", argala.sourceHouse)
                                        put("planets", JSONArray(argala.planets.map { it.displayName }))
                                        put("strength", argala.strength)
                                        put("isObstructed", argala.isObstructed)
                                        put("effect", argala.effect)
                                    })
                                }
                            })
                            put("netEffect", argalaList.sumOf { if (it.isObstructed) -it.strength else it.strength })
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
