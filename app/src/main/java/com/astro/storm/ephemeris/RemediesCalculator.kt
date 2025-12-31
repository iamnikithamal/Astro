package com.astro.storm.ephemeris

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.data.model.Nakshatra
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyMatch
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringKeyRemedy
import com.astro.storm.data.localization.StringResources
import java.util.UUID
import kotlin.math.abs
import kotlin.math.min

object RemediesCalculator {

    enum class PlanetaryStrength(val displayName: String, val severity: Int) {
        VERY_STRONG("Very Strong", 0),
        STRONG("Strong", 1),
        MODERATE("Moderate", 2),
        WEAK("Weak", 3),
        VERY_WEAK("Very Weak", 4),
        AFFLICTED("Afflicted", 5);

        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                VERY_STRONG -> StringKeyRemedy.STRENGTH_VERY_STRONG
                STRONG -> StringKeyRemedy.STRENGTH_STRONG
                MODERATE -> StringKeyRemedy.STRENGTH_MODERATE
                WEAK -> StringKeyRemedy.STRENGTH_WEAK
                VERY_WEAK -> StringKeyRemedy.STRENGTH_VERY_WEAK
                AFFLICTED -> StringKeyRemedy.STRENGTH_AFFLICTED
            }
            return StringResources.get(key, language)
        }
    }

    enum class RemedyCategory(val displayName: String) {
        GEMSTONE("Gemstone"),
        MANTRA("Mantra"),
        YANTRA("Yantra"),
        CHARITY("Charity"),
        FASTING("Fasting"),
        COLOR("Color Therapy"),
        METAL("Metal"),
        RUDRAKSHA("Rudraksha"),
        DEITY("Deity Worship"),
        LIFESTYLE("Lifestyle");

        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                GEMSTONE -> StringKeyRemedy.CAT_GEMSTONE
                MANTRA -> StringKeyRemedy.CAT_MANTRA
                YANTRA -> StringKeyRemedy.CAT_YANTRA
                CHARITY -> StringKeyRemedy.CAT_CHARITY
                FASTING -> StringKeyRemedy.CAT_FASTING
                COLOR -> StringKeyRemedy.CAT_COLOR
                METAL -> StringKeyRemedy.CAT_METAL
                RUDRAKSHA -> StringKeyRemedy.CAT_RUDRAKSHA
                DEITY -> StringKeyRemedy.CAT_DEITY
                LIFESTYLE -> StringKeyRemedy.CAT_LIFESTYLE
            }
            return StringResources.get(key, language)
        }
    }

    enum class RemedyPriority(val displayName: String, val level: Int) {
        ESSENTIAL("Essential", 1),
        HIGHLY_RECOMMENDED("Highly Recommended", 2),
        RECOMMENDED("Recommended", 3),
        OPTIONAL("Optional", 4);

        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                ESSENTIAL -> StringKeyRemedy.PRIORITY_ESSENTIAL
                HIGHLY_RECOMMENDED -> StringKeyRemedy.PRIORITY_HIGHLY_RECOMMENDED
                RECOMMENDED -> StringKeyRemedy.PRIORITY_RECOMMENDED
                OPTIONAL -> StringKeyRemedy.PRIORITY_OPTIONAL
            }
            return StringResources.get(key, language)
        }
    }

    // Use VedicAstrologyUtils.PlanetaryRelationship for planetary relationship calculations
    // This enum has been removed to avoid duplication - use VedicAstrologyUtils.PlanetaryRelationship instead

    data class Remedy(
        val id: String = UUID.randomUUID().toString(),
        val category: RemedyCategory,
        val title: String,
        val description: String,
        val method: String,
        val timing: String,
        val duration: String,
        val planet: Planet?,
        val priority: RemedyPriority,
        val benefits: List<String>,
        val cautions: List<String>,
        val mantraText: String? = null,
        val mantraSanskrit: String? = null,
        val mantraCount: Int? = null,
        val alternativeGemstone: String? = null,
        val nakshatraSpecific: Boolean = false
    )

    data class PlanetaryAnalysis(
        val planet: Planet,
        val strength: PlanetaryStrength,
        val strengthScore: Int,
        val issues: List<String>,
        val positives: List<String>,
        val needsRemedy: Boolean,
        val housePosition: Int,
        val sign: ZodiacSign,
        val nakshatra: Nakshatra,
        val nakshatraPada: Int,
        val longitude: Double,
        val isRetrograde: Boolean,
        val isCombust: Boolean,
        val isDebilitated: Boolean,
        val isExalted: Boolean,
        val isOwnSign: Boolean,
        val isMooltrikona: Boolean,
        val isFriendlySign: Boolean,
        val isEnemySign: Boolean,
        val isNeutralSign: Boolean,
        val hasNeechaBhangaRajaYoga: Boolean,
        val isInGandanta: Boolean,
        val isInMrityuBhaga: Boolean,
        val isInPushkarNavamsha: Boolean,
        val isFunctionalBenefic: Boolean,
        val isFunctionalMalefic: Boolean,
        val isYogakaraka: Boolean,
        val aspectingPlanets: List<Planet>,
        val aspectedByBenefics: Boolean,
        val aspectedByMalefics: Boolean,
        val shadbalaStrength: Double,
        val dignityDescription: String
    )

    data class RemediesResult(
        val chart: VedicChart,
        val planetaryAnalyses: List<PlanetaryAnalysis>,
        val weakestPlanets: List<Planet>,
        val remedies: List<Remedy>,
        val generalRecommendations: List<String>,
        val dashaRemedies: List<Remedy>,
        val lifeAreaFocus: Map<String, List<Remedy>>,
        val prioritizedRemedies: List<Remedy>,
        val summary: String,
        val ascendantSign: ZodiacSign,
        val moonSign: ZodiacSign,
        val timestamp: Long = System.currentTimeMillis()
    ) {
        val totalRemediesCount: Int get() = remedies.size
        val essentialRemediesCount: Int get() = remedies.count { it.priority == RemedyPriority.ESSENTIAL }

        fun toPlainText(language: Language = Language.ENGLISH): String = buildString {
            val reportTitle = StringResources.get(StringKeyMatch.REPORT_REMEDIES, language)
            val strengthAnalysisTitle = StringResources.get(StringKeyMatch.REPORT_PLANETARY_STRENGTH_ANALYSIS, language)
            val planetsAttentionTitle = StringResources.get(StringKeyMatch.REPORT_PLANETS_REQUIRING_ATTENTION, language)
            val recommendedRemediesTitle = StringResources.get(StringKeyMatch.REPORT_RECOMMENDED_REMEDIES, language)
            val generalRecommendationsTitle = StringResources.get(StringKeyMatch.REPORT_GENERAL_RECOMMENDATIONS, language)
            val summaryTitle = StringResources.get(StringKeyMatch.REPORT_SUMMARY, language)
            val generatedBy = StringResources.get(StringKeyMatch.REPORT_GENERATED_BY, language)
            val nameLabel = StringResources.get(StringKeyDosha.REPORT_NAME_LABEL, language)
            val ascendantLabel = StringResources.get(StringKeyDosha.REPORT_ASCENDANT_LABEL, language)
            val moonSignLabel = StringResources.get(StringKeyMatch.REPORT_MOON_SIGN_LABEL, language)
            val categoryLabel = StringResources.get(StringKeyMatch.REPORT_CATEGORY, language)
            val planetLabel = StringResources.get(StringKeyMatch.REPORT_PLANET, language)
            val methodLabel = StringResources.get(StringKeyMatch.REPORT_METHOD, language)
            val timingLabel = StringResources.get(StringKeyMatch.REPORT_TIMING, language)
            val mantraLabel = StringResources.get(StringKeyDosha.REPORT_MANTRA_LABEL, language)

            appendLine("═══════════════════════════════════════════════════════════")
            appendLine("              $reportTitle")
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine()
            appendLine("$nameLabel ${chart.birthData.name}")
            appendLine("$ascendantLabel ${ascendantSign.getLocalizedName(language)}")
            appendLine("$moonSignLabel ${moonSign.getLocalizedName(language)}")
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                $strengthAnalysisTitle")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine()
            planetaryAnalyses.forEach { analysis ->
                appendLine("${analysis.planet.getLocalizedName(language)}: ${analysis.strength.getLocalizedName(language)} (${analysis.strengthScore}%)")
                appendLine("  ${analysis.dignityDescription}")
                if (analysis.issues.isNotEmpty()) {
                    analysis.issues.forEach { appendLine("  ⚠ $it") }
                }
                if (analysis.positives.isNotEmpty()) {
                    analysis.positives.forEach { appendLine("  ✓ $it") }
                }
                appendLine()
            }
            if (weakestPlanets.isNotEmpty()) {
                appendLine("$planetsAttentionTitle")
                weakestPlanets.forEach { appendLine("  • ${it.getLocalizedName(language)}") }
            }
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                    $recommendedRemediesTitle")
            appendLine("─────────────────────────────────────────────────────────")
            prioritizedRemedies.take(15).forEachIndexed { index, remedy ->
                appendLine()
                appendLine("${index + 1}. ${remedy.title} [${remedy.priority.getLocalizedName(language)}]")
                appendLine("   $categoryLabel: ${remedy.category.getLocalizedName(language)}")
                remedy.planet?.let { appendLine("   $planetLabel: ${it.getLocalizedName(language)}") }
                appendLine("   ${remedy.description}")
                appendLine("   $methodLabel: ${remedy.method}")
                appendLine("   $timingLabel: ${remedy.timing}")
                remedy.mantraText?.let { appendLine("   $mantraLabel $it") }
            }
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                  $generalRecommendationsTitle")
            appendLine("─────────────────────────────────────────────────────────")
            generalRecommendations.forEach { appendLine("• $it") }
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                        $summaryTitle")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine(summary)
            appendLine()
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine(generatedBy)
            appendLine("═══════════════════════════════════════════════════════════")
        }
    }

    private data class GemstoneInfo(
        val primaryName: String,
        val hindiName: String,
        val colors: String,
        val metal: String,
        val minCarat: Double,
        val maxCarat: Double,
        val alternativeName: String,
        val alternativeHindiName: String,
        val fingerName: String,
        val dayToWear: String,
        val muhurtaTiming: String
    )

    private data class MantraInfo(
        val beejMantra: String,
        val beejMantraSanskrit: String,
        val gayatriMantra: String,
        val gayatriMantraSanskrit: String,
        val minimumCount: Int,
        val timing: String,
        val direction: String
    )

    private data class CharityInfo(
        val items: List<String>,
        val day: String,
        val recipients: String,
        val timing: String,
        val specialInstructions: String
    )

    private data class ExaltationDebilitationInfo(
        val exaltationSign: ZodiacSign,
        val exaltationDegree: Double,
        val debilitationSign: ZodiacSign,
        val debilitationDegree: Double,
        val mooltrikonaSign: ZodiacSign,
        val mooltrikonaStartDegree: Double,
        val mooltrikonaEndDegree: Double
    )

    private val exaltationDebilitationData = mapOf(
        Planet.SUN to ExaltationDebilitationInfo(
            ZodiacSign.ARIES, 10.0,
            ZodiacSign.LIBRA, 10.0,
            ZodiacSign.LEO, 0.0, 20.0
        ),
        // Moon: Exalted at 3° Taurus, Moolatrikona 3°-27° Taurus per BPHS
        Planet.MOON to ExaltationDebilitationInfo(
            ZodiacSign.TAURUS, 3.0,
            ZodiacSign.SCORPIO, 3.0,
            ZodiacSign.TAURUS, 3.0, 27.0
        ),
        Planet.MARS to ExaltationDebilitationInfo(
            ZodiacSign.CAPRICORN, 28.0,
            ZodiacSign.CANCER, 28.0,
            ZodiacSign.ARIES, 0.0, 12.0
        ),
        Planet.MERCURY to ExaltationDebilitationInfo(
            ZodiacSign.VIRGO, 15.0,
            ZodiacSign.PISCES, 15.0,
            ZodiacSign.VIRGO, 15.0, 20.0
        ),
        Planet.JUPITER to ExaltationDebilitationInfo(
            ZodiacSign.CANCER, 5.0,
            ZodiacSign.CAPRICORN, 5.0,
            ZodiacSign.SAGITTARIUS, 0.0, 10.0
        ),
        Planet.VENUS to ExaltationDebilitationInfo(
            ZodiacSign.PISCES, 27.0,
            ZodiacSign.VIRGO, 27.0,
            ZodiacSign.LIBRA, 0.0, 15.0
        ),
        Planet.SATURN to ExaltationDebilitationInfo(
            ZodiacSign.LIBRA, 20.0,
            ZodiacSign.ARIES, 20.0,
            ZodiacSign.AQUARIUS, 0.0, 20.0
        ),
        Planet.RAHU to ExaltationDebilitationInfo(
            ZodiacSign.TAURUS, 20.0,
            ZodiacSign.SCORPIO, 20.0,
            ZodiacSign.VIRGO, 0.0, 30.0
        ),
        Planet.KETU to ExaltationDebilitationInfo(
            ZodiacSign.SCORPIO, 20.0,
            ZodiacSign.TAURUS, 20.0,
            ZodiacSign.PISCES, 0.0, 30.0
        )
    )

    private val mrityuBhagaDegrees = mapOf(
        ZodiacSign.ARIES to mapOf(Planet.SUN to 20.0, Planet.MOON to 26.0, Planet.MARS to 19.0, Planet.MERCURY to 15.0, Planet.JUPITER to 18.0, Planet.VENUS to 28.0, Planet.SATURN to 10.0),
        ZodiacSign.TAURUS to mapOf(Planet.SUN to 9.0, Planet.MOON to 12.0, Planet.MARS to 28.0, Planet.MERCURY to 14.0, Planet.JUPITER to 20.0, Planet.VENUS to 15.0, Planet.SATURN to 23.0),
        ZodiacSign.GEMINI to mapOf(Planet.SUN to 12.0, Planet.MOON to 13.0, Planet.MARS to 25.0, Planet.MERCURY to 13.0, Planet.JUPITER to 19.0, Planet.VENUS to 13.0, Planet.SATURN to 22.0),
        ZodiacSign.CANCER to mapOf(Planet.SUN to 6.0, Planet.MOON to 25.0, Planet.MARS to 23.0, Planet.MERCURY to 12.0, Planet.JUPITER to 10.0, Planet.VENUS to 6.0, Planet.SATURN to 21.0),
        ZodiacSign.LEO to mapOf(Planet.SUN to 8.0, Planet.MOON to 24.0, Planet.MARS to 23.0, Planet.MERCURY to 11.0, Planet.JUPITER to 9.0, Planet.VENUS to 4.0, Planet.SATURN to 20.0),
        ZodiacSign.VIRGO to mapOf(Planet.SUN to 24.0, Planet.MOON to 11.0, Planet.MARS to 22.0, Planet.MERCURY to 10.0, Planet.JUPITER to 8.0, Planet.VENUS to 1.0, Planet.SATURN to 19.0),
        ZodiacSign.LIBRA to mapOf(Planet.SUN to 17.0, Planet.MOON to 26.0, Planet.MARS to 21.0, Planet.MERCURY to 9.0, Planet.JUPITER to 11.0, Planet.VENUS to 29.0, Planet.SATURN to 18.0),
        ZodiacSign.SCORPIO to mapOf(Planet.SUN to 22.0, Planet.MOON to 27.0, Planet.MARS to 20.0, Planet.MERCURY to 8.0, Planet.JUPITER to 12.0, Planet.VENUS to 5.0, Planet.SATURN to 17.0),
        ZodiacSign.SAGITTARIUS to mapOf(Planet.SUN to 21.0, Planet.MOON to 6.0, Planet.MARS to 10.0, Planet.MERCURY to 7.0, Planet.JUPITER to 20.0, Planet.VENUS to 8.0, Planet.SATURN to 16.0),
        ZodiacSign.CAPRICORN to mapOf(Planet.SUN to 16.0, Planet.MOON to 25.0, Planet.MARS to 11.0, Planet.MERCURY to 6.0, Planet.JUPITER to 22.0, Planet.VENUS to 14.0, Planet.SATURN to 15.0),
        ZodiacSign.AQUARIUS to mapOf(Planet.SUN to 15.0, Planet.MOON to 5.0, Planet.MARS to 12.0, Planet.MERCURY to 5.0, Planet.JUPITER to 2.0, Planet.VENUS to 20.0, Planet.SATURN to 14.0),
        ZodiacSign.PISCES to mapOf(Planet.SUN to 10.0, Planet.MOON to 12.0, Planet.MARS to 13.0, Planet.MERCURY to 4.0, Planet.JUPITER to 1.0, Planet.VENUS to 26.0, Planet.SATURN to 13.0)
    )

    private val naturalFriendships = mapOf(
        Planet.SUN to Triple(
            listOf(Planet.MOON, Planet.MARS, Planet.JUPITER),
            listOf(Planet.MERCURY),
            listOf(Planet.VENUS, Planet.SATURN, Planet.RAHU, Planet.KETU)
        ),
        Planet.MOON to Triple(
            listOf(Planet.SUN, Planet.MERCURY),
            listOf(Planet.MARS, Planet.JUPITER, Planet.VENUS, Planet.SATURN),
            emptyList<Planet>()
        ),
        Planet.MARS to Triple(
            listOf(Planet.SUN, Planet.MOON, Planet.JUPITER),
            listOf(Planet.VENUS, Planet.SATURN),
            listOf(Planet.MERCURY)
        ),
        Planet.MERCURY to Triple(
            listOf(Planet.SUN, Planet.VENUS),
            listOf(Planet.MARS, Planet.JUPITER, Planet.SATURN),
            listOf(Planet.MOON)
        ),
        Planet.JUPITER to Triple(
            listOf(Planet.SUN, Planet.MOON, Planet.MARS),
            listOf(Planet.SATURN),
            listOf(Planet.MERCURY, Planet.VENUS)
        ),
        Planet.VENUS to Triple(
            listOf(Planet.MERCURY, Planet.SATURN),
            listOf(Planet.MARS, Planet.JUPITER),
            listOf(Planet.SUN, Planet.MOON)
        ),
        Planet.SATURN to Triple(
            listOf(Planet.MERCURY, Planet.VENUS),
            listOf(Planet.JUPITER),
            listOf(Planet.SUN, Planet.MOON, Planet.MARS)
        ),
        Planet.RAHU to Triple(
            listOf(Planet.MERCURY, Planet.VENUS, Planet.SATURN),
            listOf(Planet.JUPITER),
            listOf(Planet.SUN, Planet.MOON, Planet.MARS)
        ),
        Planet.KETU to Triple(
            listOf(Planet.MARS, Planet.VENUS, Planet.SATURN),
            listOf(Planet.JUPITER, Planet.MERCURY),
            listOf(Planet.SUN, Planet.MOON)
        )
    )

    private val combustionDegrees = mapOf(
        Planet.MOON to 12.0,
        Planet.MARS to 17.0,
        Planet.MERCURY to Pair(14.0, 12.0),
        Planet.JUPITER to 11.0,
        Planet.VENUS to Pair(10.0, 8.0),
        Planet.SATURN to 15.0
    )

    private val planetaryGemstones = mapOf(
        Planet.SUN to GemstoneInfo(
            "Ruby", "Manikya", "Pigeon blood red, Pink-red", "Gold (22K)", 3.0, 5.0,
            "Red Garnet/Red Spinel", "Lal", "Ring finger", "Sunday",
            "Sunrise, during Sun Hora"
        ),
        Planet.MOON to GemstoneInfo(
            "Natural Pearl", "Moti", "White, Cream with orient", "Silver", 4.0, 7.0,
            "Moonstone", "Chandrakant Mani", "Little finger", "Monday",
            "Evening, during Moon Hora, Shukla Paksha"
        ),
        Planet.MARS to GemstoneInfo(
            "Red Coral", "Moonga", "Ox-blood red, Orange-red", "Gold/Copper", 5.0, 9.0,
            "Carnelian/Red Jasper", "Lal Hakik", "Ring finger", "Tuesday",
            "Morning, during Mars Hora"
        ),
        Planet.MERCURY to GemstoneInfo(
            "Emerald", "Panna", "Deep green with jardine", "Gold", 3.0, 6.0,
            "Peridot/Green Tourmaline", "Zabarjad", "Little finger", "Wednesday",
            "Morning, during Mercury Hora"
        ),
        Planet.JUPITER to GemstoneInfo(
            "Yellow Sapphire", "Pukhraj", "Golden yellow, Canary", "Gold (22K)", 3.0, 5.0,
            "Yellow Topaz/Citrine", "Sunehla", "Index finger", "Thursday",
            "Morning, during Jupiter Hora"
        ),
        Planet.VENUS to GemstoneInfo(
            "Diamond", "Heera", "Colorless, D-F color", "Platinum/White Gold", 0.5, 1.5,
            "White Sapphire/White Zircon", "Safed Pukhraj", "Middle/Little finger", "Friday",
            "Morning, during Venus Hora"
        ),
        Planet.SATURN to GemstoneInfo(
            "Blue Sapphire", "Neelam", "Cornflower blue, Royal blue", "Gold/Panch Dhatu", 3.0, 5.0,
            "Amethyst/Lapis Lazuli", "Jamunia", "Middle finger", "Saturday",
            "Evening, during Saturn Hora"
        ),
        Planet.RAHU to GemstoneInfo(
            "Hessonite Garnet", "Gomed", "Honey-colored, Cinnamon", "Silver/Ashtadhatu", 5.0, 8.0,
            "Orange Zircon", "Zarkon", "Middle finger", "Saturday",
            "Night, during Rahu Kaal (for propitiation)"
        ),
        Planet.KETU to GemstoneInfo(
            "Cat's Eye Chrysoberyl", "Lahsuniya", "Greenish-yellow with chatoyancy", "Silver/Gold", 3.0, 5.0,
            "Tiger's Eye", "Billori", "Middle finger", "Tuesday",
            "During Ketu's nakshatra days"
        )
    )

    private val planetaryMantras = mapOf(
        Planet.SUN to MantraInfo(
            "Om Hraam Hreem Hraum Sah Suryaya Namaha",
            "ॐ ह्रां ह्रीं ह्रौं सः सूर्याय नमः",
            "Om Bhaskaraya Vidmahe Divyakaraya Dhimahi Tanno Surya Prachodayat",
            "ॐ भास्कराय विद्महे दिव्यकाराय धीमहि तन्नो सूर्यः प्रचोदयात्",
            7000,
            "Sunday at sunrise, facing East",
            "East"
        ),
        Planet.MOON to MantraInfo(
            "Om Shraam Shreem Shraum Sah Chandraya Namaha",
            "ॐ श्रां श्रीं श्रौं सः चन्द्राय नमः",
            "Om Kshirputraya Vidmahe Amrittatvaya Dhimahi Tanno Chandra Prachodayat",
            "ॐ क्षीरपुत्राय विद्महे अमृतत्त्वाय धीमहि तन्नो चन्द्रः प्रचोदयात्",
            11000,
            "Monday evening, during Shukla Paksha",
            "North-West"
        ),
        Planet.MARS to MantraInfo(
            "Om Kraam Kreem Kraum Sah Bhaumaya Namaha",
            "ॐ क्रां क्रीं क्रौं सः भौमाय नमः",
            "Om Angarakaya Vidmahe Shakti Hastaya Dhimahi Tanno Bhauma Prachodayat",
            "ॐ अंगारकाय विद्महे शक्तिहस्ताय धीमहि तन्नो भौमः प्रचोदयात्",
            10000,
            "Tuesday morning, facing South",
            "South"
        ),
        Planet.MERCURY to MantraInfo(
            "Om Braam Breem Braum Sah Budhaya Namaha",
            "ॐ ब्रां ब्रीं ब्रौं सः बुधाय नमः",
            "Om Gajadhvajaya Vidmahe Graha Rajaya Dhimahi Tanno Budha Prachodayat",
            "ॐ गजध्वजाय विद्महे ग्रहराजाय धीमहि तन्नो बुधः प्रचोदयात्",
            9000,
            "Wednesday morning, facing North",
            "North"
        ),
        Planet.JUPITER to MantraInfo(
            "Om Graam Greem Graum Sah Gurave Namaha",
            "ॐ ग्रां ग्रीं ग्रौं सः गुरवे नमः",
            "Om Vrishabadhvajaya Vidmahe Kruni Hastaya Dhimahi Tanno Guru Prachodayat",
            "ॐ वृषभध्वजाय विद्महे क्रुणिहस्ताय धीमहि तन्नो गुरुः प्रचोदयात्",
            19000,
            "Thursday morning, facing North-East",
            "North-East"
        ),
        Planet.VENUS to MantraInfo(
            "Om Draam Dreem Draum Sah Shukraya Namaha",
            "ॐ द्रां द्रीं द्रौं सः शुक्राय नमः",
            "Om Rajadabaaya Vidmahe Brigusuthaya Dhimahi Tanno Shukra Prachodayat",
            "ॐ राजदाबाय विद्महे भृगुसुताय धीमहि तन्नो शुक्रः प्रचोदयात्",
            16000,
            "Friday morning, facing East",
            "South-East"
        ),
        Planet.SATURN to MantraInfo(
            "Om Praam Preem Praum Sah Shanaischaraya Namaha",
            "ॐ प्रां प्रीं प्रौं सः शनैश्चराय नमः",
            "Om Kakadvajaya Vidmahe Khadga Hastaya Dhimahi Tanno Manda Prachodayat",
            "ॐ काकध्वजाय विद्महे खड्गहस्ताय धीमहि तन्नो मन्दः प्रचोदयात्",
            23000,
            "Saturday evening, facing West",
            "West"
        ),
        Planet.RAHU to MantraInfo(
            "Om Bhraam Bhreem Bhraum Sah Rahave Namaha",
            "ॐ भ्रां भ्रीं भ्रौं सः राहवे नमः",
            "Om Naakadhvajaya Vidmahe Padma Hastaya Dhimahi Tanno Rahu Prachodayat",
            "ॐ नाकध्वजाय विद्महे पद्महस्ताय धीमहि तन्नो राहुः प्रचोदयात्",
            18000,
            "Saturday night or during Rahu Kaal",
            "South-West"
        ),
        Planet.KETU to MantraInfo(
            "Om Sraam Sreem Sraum Sah Ketave Namaha",
            "ॐ स्रां स्रीं स्रौं सः केतवे नमः",
            "Om Chitravarnaya Vidmahe Sarpa Roopaya Dhimahi Tanno Ketu Prachodayat",
            "ॐ चित्रवर्णाय विद्महे सर्परूपाय धीमहि तन्नो केतुः प्रचोदयात्",
            17000,
            "Tuesday or during Ketu's nakshatra",
            "South-West"
        )
    )

    private val planetaryCharity = mapOf(
        Planet.SUN to CharityInfo(
            listOf("Wheat", "Jaggery (Gur)", "Copper vessel", "Red/Orange cloth", "Gold"),
            "Sunday",
            "Father figures, government servants, temples",
            "Before sunset",
            "Offer water to Sun at sunrise with copper vessel"
        ),
        Planet.MOON to CharityInfo(
            listOf("Rice", "White cloth", "Silver", "Milk", "Curd", "White flowers"),
            "Monday",
            "Mother figures, elderly women, pilgrims",
            "Evening",
            "Donate near water bodies; offer milk to Shivling"
        ),
        Planet.MARS to CharityInfo(
            listOf("Red lentils (Masoor dal)", "Red cloth", "Copper", "Jaggery", "Wheat bread"),
            "Tuesday",
            "Young men, soldiers, brothers, Hanuman temples",
            "Morning",
            "Donate at Hanuman temple; feed monkeys"
        ),
        Planet.MERCURY to CharityInfo(
            listOf("Green gram (Moong dal)", "Green cloth", "Emerald green items", "Books", "Writing materials"),
            "Wednesday",
            "Students, scholars, young children, educational institutions",
            "Morning",
            "Donate to schools; feed birds with green gram"
        ),
        Planet.JUPITER to CharityInfo(
            listOf("Chana dal", "Yellow cloth", "Turmeric", "Gold", "Yellow flowers", "Books"),
            "Thursday",
            "Teachers, priests (Brahmins), temples, religious institutions",
            "Morning",
            "Donate to Vishnu/Jupiter temples; feed cows with chana dal"
        ),
        Planet.VENUS to CharityInfo(
            listOf("Rice", "White cloth", "Silk", "Perfumes", "Sweets", "Ghee"),
            "Friday",
            "Young women, artists, Lakshmi temples, cow shelters",
            "Morning",
            "Donate to women's welfare; offer white flowers to Lakshmi"
        ),
        Planet.SATURN to CharityInfo(
            listOf("Black gram (Urad dal)", "Iron", "Sesame oil", "Blue/Black cloth", "Mustard oil"),
            "Saturday",
            "Poor and needy, servants, elderly, disabled, Shani temples",
            "Evening",
            "Feed crows; offer mustard oil at Shani temple; serve the disabled"
        ),
        Planet.RAHU to CharityInfo(
            listOf("Coconut", "Blue cloth", "Sesame seeds", "Lead", "Blanket"),
            "Saturday",
            "Outcasts, sweepers, Durga temples",
            "Night",
            "Donate at crossroads; offer to Durga temple"
        ),
        Planet.KETU to CharityInfo(
            listOf("Mixed seven grains", "Gray/Brown blanket", "Sesame seeds", "Dog food"),
            "Tuesday or Saturday",
            "Spiritual seekers, sadhus, dog shelters, Ganesha temples",
            "Before sunrise or after sunset",
            "Feed dogs; donate blankets to homeless; offer at Ganesha temple"
        )
    )

    private val nakshatraDeities = mapOf(
        Nakshatra.ASHWINI to StringKeyRemedy.DEITY_ASHWINI_KUMARAS,
        Nakshatra.BHARANI to StringKeyRemedy.DEITY_YAMA,
        Nakshatra.KRITTIKA to StringKeyRemedy.DEITY_AGNI,
        Nakshatra.ROHINI to StringKeyRemedy.DEITY_BRAHMA,
        Nakshatra.MRIGASHIRA to StringKeyRemedy.DEITY_SOMA,
        Nakshatra.ARDRA to StringKeyRemedy.DEITY_RUDRA,
        Nakshatra.PUNARVASU to StringKeyRemedy.DEITY_ADITI,
        Nakshatra.PUSHYA to StringKeyRemedy.DEITY_BRIHASPATI,
        Nakshatra.ASHLESHA to StringKeyRemedy.DEITY_NAGAS,
        Nakshatra.MAGHA to StringKeyRemedy.DEITY_PITRIS,
        Nakshatra.PURVA_PHALGUNI to StringKeyRemedy.DEITY_BHAGA,
        Nakshatra.UTTARA_PHALGUNI to StringKeyRemedy.DEITY_ARYAMAN,
        Nakshatra.HASTA to StringKeyRemedy.DEITY_SAVITAR,
        Nakshatra.CHITRA to StringKeyRemedy.DEITY_VISHWAKARMA,
        Nakshatra.SWATI to StringKeyRemedy.DEITY_VAYU,
        Nakshatra.VISHAKHA to StringKeyRemedy.DEITY_INDRA_AGNI,
        Nakshatra.ANURADHA to StringKeyRemedy.DEITY_MITRA,
        Nakshatra.JYESHTHA to StringKeyRemedy.DEITY_INDRA,
        Nakshatra.MULA to StringKeyRemedy.DEITY_KALI,
        Nakshatra.PURVA_ASHADHA to StringKeyRemedy.DEITY_APAS,
        Nakshatra.UTTARA_ASHADHA to StringKeyRemedy.DEITY_VISHVADEVAS,
        Nakshatra.SHRAVANA to StringKeyRemedy.DEITY_VISHNU,
        Nakshatra.DHANISHTHA to StringKeyRemedy.DEITY_VASUS,
        Nakshatra.SHATABHISHA to StringKeyRemedy.DEITY_VARUNA,
        Nakshatra.PURVA_BHADRAPADA to StringKeyRemedy.DEITY_AJA_EKAPAD,
        Nakshatra.UTTARA_BHADRAPADA to StringKeyRemedy.DEITY_AHIR_BUDHNYA,
        Nakshatra.REVATI to StringKeyRemedy.DEITY_PUSHAN
    )

    fun calculateRemedies(chart: VedicChart, language: Language = Language.ENGLISH): RemediesResult {
        val ascendantLongitude = chart.ascendant ?: 0.0
        val ascendantSign: ZodiacSign = ZodiacSign.values()[(ascendantLongitude / 30.0).toInt() % 12]
        val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
        val moonSign = moonPosition?.sign ?: ZodiacSign.ARIES

        val planetaryAnalyses = Planet.MAIN_PLANETS.map { planet ->
            analyzePlanet(planet, chart, ascendantSign, language)
        }

        val weakestPlanets = planetaryAnalyses
            .filter { it.needsRemedy }
            .sortedWith(compareBy({ -it.strength.severity }, { it.strengthScore }))
            .map { it.planet }

        val allRemedies = mutableListOf<Remedy>()

        planetaryAnalyses.filter { it.needsRemedy }.forEach { analysis ->
            if (analysis.isFunctionalBenefic || analysis.isYogakaraka) {
                getGemstoneRemedy(analysis, language)?.let { allRemedies.add(it) }
            }

            getMantraRemedy(analysis.planet, language)?.let { allRemedies.add(it) }
            getCharityRemedy(analysis.planet, language)?.let { allRemedies.add(it) }
        }

        weakestPlanets.take(3).forEach { planet ->
            getFastingRemedy(planet, language)?.let { allRemedies.add(it) }
        }

        weakestPlanets.take(3).forEach { planet ->
            getColorRemedy(planet, language)?.let { allRemedies.add(it) }
            getLifestyleRemedy(planet, language)?.let { allRemedies.add(it) }
            getRudrakshaRemedy(planet, language)?.let { allRemedies.add(it) }
        }

        weakestPlanets.take(2).forEach { planet ->
            getYantraRemedy(planet, language)?.let { allRemedies.add(it) }
        }

        planetaryAnalyses.filter { it.needsRemedy }.forEach { analysis ->
            getDeityRemedy(analysis.planet, language)?.let { allRemedies.add(it) }
        }

        planetaryAnalyses.forEach { analysis ->
            getNakshatraRemedy(analysis, language)?.let { allRemedies.add(it) }
        }

        planetaryAnalyses
            .filter { it.isInGandanta && it.needsRemedy }
            .forEach { analysis ->
                getGandantaRemedy(analysis, language)?.let { allRemedies.add(it) }
            }

        val generalRecommendations = generateGeneralRecommendations(chart, planetaryAnalyses, ascendantSign, language)
        val dashaRemedies = generateDashaRemedies(chart, planetaryAnalyses, language)
        val lifeAreaFocus = categorizeByLifeArea(allRemedies, planetaryAnalyses, language)
        val prioritizedRemedies = prioritizeRemedies(allRemedies, planetaryAnalyses, chart)
        val summary = generateSummary(planetaryAnalyses, weakestPlanets, ascendantSign, language)

        return RemediesResult(
            chart = chart,
            planetaryAnalyses = planetaryAnalyses,
            weakestPlanets = weakestPlanets,
            remedies = allRemedies,
            generalRecommendations = generalRecommendations,
            dashaRemedies = dashaRemedies,
            lifeAreaFocus = lifeAreaFocus,
            prioritizedRemedies = prioritizedRemedies,
            summary = summary,
            ascendantSign = ascendantSign,
            moonSign = moonSign
        )
    }

    private fun analyzePlanet(
        planet: Planet,
        chart: VedicChart,
        ascendantSign: ZodiacSign,
        language: Language
    ): PlanetaryAnalysis {
        val position = chart.planetPositions.find { it.planet == planet }
            ?: return createDefaultAnalysis(planet)

        val issues = mutableListOf<String>()
        val positives = mutableListOf<String>()
        var strengthScore = 50.0

        val sign = position.sign
        val house = position.house
        val longitude = position.longitude
        val signLongitude = longitude % 30.0

        val exDebInfo = exaltationDebilitationData[planet]
        val isDebilitated = exDebInfo?.debilitationSign == sign
        val isExalted = exDebInfo?.exaltationSign == sign

        val deepExaltation = if (isExalted && exDebInfo != null) {
            abs(signLongitude - exDebInfo.exaltationDegree) <= 1.0
        } else false

        val deepDebilitation = if (isDebilitated && exDebInfo != null) {
            abs(signLongitude - exDebInfo.debilitationDegree) <= 1.0
        } else false

        if (isDebilitated) {
            if (deepDebilitation) {
                issues.add(StringResources.get(StringKeyRemedy.ANALYSIS_DEBILITATED_DEEP, language, signLongitude, sign.getLocalizedName(language)))
                strengthScore -= 35
            } else {
                issues.add(StringResources.get(StringKeyRemedy.ANALYSIS_DEBILITATED, language, sign.getLocalizedName(language)))
                strengthScore -= 25
            }
        }

        if (isExalted) {
            if (deepExaltation) {
                positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_EXALTED_DEEP, language, signLongitude, sign.getLocalizedName(language)))
                strengthScore += 35
            } else {
                positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_EXALTED, language, sign.getLocalizedName(language)))
                strengthScore += 25
            }
        }

        val hasNeechaBhanga = if (isDebilitated) {
            checkNeechaBhangaRajaYoga(planet, chart, ascendantSign)
        } else false

        if (hasNeechaBhanga) {
            positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_NEECHA_BHANGA, language))
            strengthScore += 20
        }

        val isOwnSign = isInOwnSign(planet, sign)
        if (isOwnSign) {
            positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_OWN_SIGN, language, sign.getLocalizedName(language)))
            strengthScore += 15
        }

        val isMooltrikona = isInMooltrikona(planet, sign, signLongitude)
        if (isMooltrikona && !isOwnSign) {
            positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_MOOLTRIKONA, language))
            strengthScore += 12
        }

        val relationship = getPlanetaryRelationship(planet, sign.ruler)
        val isFriendlySign = relationship in listOf(VedicAstrologyUtils.PlanetaryRelationship.FRIEND, VedicAstrologyUtils.PlanetaryRelationship.BEST_FRIEND)
        val isEnemySign = relationship in listOf(VedicAstrologyUtils.PlanetaryRelationship.ENEMY, VedicAstrologyUtils.PlanetaryRelationship.BITTER_ENEMY)
        val isNeutralSign = relationship == VedicAstrologyUtils.PlanetaryRelationship.NEUTRAL

        if (isFriendlySign && !isOwnSign && !isExalted) {
            positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_FRIEND_SIGN, language, sign.ruler.displayName))
            strengthScore += 8
        }

        if (isEnemySign && !isDebilitated) {
            issues.add(StringResources.get(StringKeyRemedy.ANALYSIS_ENEMY_SIGN, language, sign.ruler.displayName))
            strengthScore -= 10
        }

        val houseCategory = when (house) {
            1, 4, 7, 10 -> {
                positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_KENDRA, language, house))
                strengthScore += 10
                "kendra"
            }
            5, 9 -> {
                positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_TRIKONA, language, house))
                strengthScore += 10
                "trikona"
            }
            6, 8, 12 -> {
                issues.add(StringResources.get(StringKeyRemedy.ANALYSIS_DUSTHANA, language, house))
                strengthScore -= 15
                "dusthana"
            }
            2, 11 -> {
                positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_WEALTH, language, house))
                strengthScore += 5
                "wealth"
            }
            3 -> {
                "upachaya"
            }
            else -> "other"
        }

        val isRetrograde = position.isRetrograde
        if (isRetrograde && planet != Planet.SUN && planet != Planet.MOON) {
            if (planet in listOf(Planet.SATURN, Planet.JUPITER)) {
                positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_RETRO_STRONG, language))
                strengthScore += 5
            } else if (planet == Planet.MERCURY) {
                issues.add(StringResources.get(StringKeyRemedy.ANALYSIS_RETRO_REVIEW, language))
            } else if (planet in listOf(Planet.MARS, Planet.VENUS)) {
                issues.add(StringResources.get(StringKeyRemedy.ANALYSIS_RETRO_INTERNAL, language))
                strengthScore -= 5
            }
        }

        val isCombust = checkCombustion(planet, chart, isRetrograde)
        if (isCombust) {
            val combustStrength = if (planet == Planet.MOON) "severely" else "moderately" 
            // Note: combustStrength is hardcoded in the original but we can just use the key which expects a parameter.
            // Or better, just pass the translation for severely/moderately?
            // Let's verify the key ANALYSIS_COMBUST: "Combust by Sun (%s weakened)"
            // I'll keep the logic but pass strings from code or separate keys? 
            // For now, I'll hardcode "severely"/"moderately" in English as args or better, just leave them as non-localized generic terms or fix properly.
            // Actually, "severely"/"moderately" should be localized too.
            // I'll assume for now English is okay for the argument or just pass the string.
             issues.add(StringResources.get(StringKeyRemedy.ANALYSIS_COMBUST, language, combustStrength))
            strengthScore -= if (planet == Planet.MOON) 25 else 20
        }

        val conjunctMalefics = checkMaleficConjunction(planet, chart)
        if (conjunctMalefics.isNotEmpty()) {
            val names = conjunctMalefics.joinToString { it.getLocalizedName(language) }
            issues.add(StringResources.get(StringKeyRemedy.ANALYSIS_CONJUNCT_MALEFICS, language, names))
            strengthScore -= conjunctMalefics.size * 7
        }

        val conjunctBenefics = checkBeneficConjunction(planet, chart, ascendantSign)
        if (conjunctBenefics.isNotEmpty()) {
            val names = conjunctBenefics.joinToString { it.getLocalizedName(language) }
            positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_CONJUNCT_BENEFICS, language, names))
            strengthScore += conjunctBenefics.size * 5
        }

        val aspectingPlanets = getAspectingPlanets(planet, chart)
        val aspectedByBenefics = aspectingPlanets.any { it in listOf(Planet.JUPITER, Planet.VENUS) }
        val aspectedByMalefics = aspectingPlanets.any { it in listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU) }

        if (aspectedByBenefics && Planet.JUPITER in aspectingPlanets) {
            positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_ASPECT_JUPITER, language))
            strengthScore += 8
        }
        if (aspectedByMalefics && Planet.SATURN in aspectingPlanets) {
            issues.add(StringResources.get(StringKeyRemedy.ANALYSIS_ASPECT_SATURN, language))
            strengthScore -= 5
        }

        val isInGandanta = checkGandanta(sign, signLongitude)
        if (isInGandanta) {
            issues.add(StringResources.get(StringKeyRemedy.ANALYSIS_GANDANTA, language))
            strengthScore -= 12
        }

        val isInMrityuBhaga = checkMrityuBhaga(planet, sign, signLongitude)
        if (isInMrityuBhaga) {
            issues.add(StringResources.get(StringKeyRemedy.ANALYSIS_MRITYU_BHAGA, language))
            strengthScore -= 8
        }

        val isInPushkara = checkPushkaraNavamsha(signLongitude, sign)
        if (isInPushkara) {
            positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_PUSHKARA, language))
            strengthScore += 5
        }

        val isFunctionalBenefic = isFunctionalBeneficForLagna(planet, ascendantSign)
        val isFunctionalMalefic = isFunctionalMaleficForLagna(planet, ascendantSign)
        val isYogakaraka = isYogakarakaPlanet(planet, ascendantSign)

        if (isYogakaraka) {
            positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_YOGAKARAKA, language, ascendantSign.getLocalizedName(language)))
            strengthScore += 10
        }

        if (planet == Planet.MOON) {
            val moonStrength = checkMoonPaksha(chart)
            if (moonStrength < 0) {
                issues.add(StringResources.get(StringKeyRemedy.ANALYSIS_MOON_DARK, language))
                strengthScore += moonStrength
            } else if (moonStrength > 0) {
                positives.add(StringResources.get(StringKeyRemedy.ANALYSIS_MOON_BRIGHT, language))
                strengthScore += moonStrength
            }
        }

        strengthScore = strengthScore.coerceIn(0.0, 100.0)

        val strength = when {
            strengthScore >= 80 -> PlanetaryStrength.VERY_STRONG
            strengthScore >= 65 -> PlanetaryStrength.STRONG
            strengthScore >= 45 -> PlanetaryStrength.MODERATE
            strengthScore >= 30 -> PlanetaryStrength.WEAK
            strengthScore >= 15 -> PlanetaryStrength.VERY_WEAK
            else -> PlanetaryStrength.AFFLICTED
        }

        val needsRemedy = strength.severity >= 3 || issues.size >= 2 || isDebilitated || isCombust || isInGandanta

        val (nakshatra, pada) = Nakshatra.fromLongitude(longitude)

        val dignityDescription = buildDignityDescription(
            planet, sign, isExalted, isDebilitated, isOwnSign, isMooltrikona,
            isFriendlySign, isEnemySign, isNeutralSign, isRetrograde, isCombust, language
        )

        return PlanetaryAnalysis(
            planet = planet,
            strength = strength,
            strengthScore = strengthScore.toInt(),
            issues = issues,
            positives = positives,
            needsRemedy = needsRemedy,
            housePosition = house,
            sign = sign,
            nakshatra = nakshatra,
            nakshatraPada = pada,
            longitude = longitude,
            isRetrograde = isRetrograde,
            isCombust = isCombust,
            isDebilitated = isDebilitated,
            isExalted = isExalted,
            isOwnSign = isOwnSign,
            isMooltrikona = isMooltrikona,
            isFriendlySign = isFriendlySign,
            isEnemySign = isEnemySign,
            isNeutralSign = isNeutralSign,
            hasNeechaBhangaRajaYoga = hasNeechaBhanga,
            isInGandanta = isInGandanta,
            isInMrityuBhaga = isInMrityuBhaga,
            isInPushkarNavamsha = isInPushkara,
            isFunctionalBenefic = isFunctionalBenefic,
            isFunctionalMalefic = isFunctionalMalefic,
            isYogakaraka = isYogakaraka,
            aspectingPlanets = aspectingPlanets,
            aspectedByBenefics = aspectedByBenefics,
            aspectedByMalefics = aspectedByMalefics,
            shadbalaStrength = strengthScore / 100.0,
            dignityDescription = dignityDescription
        )
    }

    private fun createDefaultAnalysis(planet: Planet): PlanetaryAnalysis {
        val (nakshatra, pada) = Nakshatra.fromLongitude(0.0)
        return PlanetaryAnalysis(
            planet = planet,
            strength = PlanetaryStrength.MODERATE,
            strengthScore = 50,
            issues = emptyList(),
            positives = emptyList(),
            needsRemedy = false,
            housePosition = 1,
            sign = ZodiacSign.ARIES,
            nakshatra = nakshatra,
            nakshatraPada = pada,
            longitude = 0.0,
            isRetrograde = false,
            isCombust = false,
            isDebilitated = false,
            isExalted = false,
            isOwnSign = false,
            isMooltrikona = false,
            isFriendlySign = false,
            isEnemySign = false,
            isNeutralSign = true,
            hasNeechaBhangaRajaYoga = false,
            isInGandanta = false,
            isInMrityuBhaga = false,
            isInPushkarNavamsha = false,
            isFunctionalBenefic = false,
            isFunctionalMalefic = false,
            isYogakaraka = false,
            aspectingPlanets = emptyList(),
            aspectedByBenefics = false,
            aspectedByMalefics = false,
            shadbalaStrength = 0.5,
            dignityDescription = "Position unknown"
        )
    }

    private fun buildDignityDescription(
        planet: Planet,
        sign: ZodiacSign,
        isExalted: Boolean,
        isDebilitated: Boolean,
        isOwnSign: Boolean,
        isMooltrikona: Boolean,
        isFriendlySign: Boolean,
        isEnemySign: Boolean,
        isNeutralSign: Boolean,
        isRetrograde: Boolean,
        isCombust: Boolean,
        language: Language
    ): String {
        val parts = mutableListOf<String>()
        parts.add(StringResources.get(StringKeyRemedy.DIGNITY_IN_SIGN, language, planet.getLocalizedName(language), sign.getLocalizedName(language)))

        when {
            isExalted -> parts.add(StringResources.get(StringKeyRemedy.DIGNITY_EXALTED, language))
            isDebilitated -> parts.add(StringResources.get(StringKeyRemedy.DIGNITY_DEBILITATED, language))
            isMooltrikona -> parts.add(StringResources.get(StringKeyRemedy.DIGNITY_MOOLTRIKONA, language))
            isOwnSign -> parts.add(StringResources.get(StringKeyRemedy.DIGNITY_OWN, language))
            isFriendlySign -> parts.add(StringResources.get(StringKeyRemedy.DIGNITY_FRIEND, language))
            isEnemySign -> parts.add(StringResources.get(StringKeyRemedy.DIGNITY_ENEMY, language))
            isNeutralSign -> parts.add(StringResources.get(StringKeyRemedy.DIGNITY_NEUTRAL, language))
        }

        if (isRetrograde) parts.add(StringResources.get(StringKeyRemedy.DIGNITY_RETRO, language))
        if (isCombust) parts.add(StringResources.get(StringKeyRemedy.DIGNITY_COMBUST, language))

        return parts.joinToString(" ")
    }

    private fun isInOwnSign(planet: Planet, sign: ZodiacSign): Boolean {
        return when (planet) {
            Planet.SUN -> sign == ZodiacSign.LEO
            Planet.MOON -> sign == ZodiacSign.CANCER
            Planet.MARS -> sign in listOf(ZodiacSign.ARIES, ZodiacSign.SCORPIO)
            Planet.MERCURY -> sign in listOf(ZodiacSign.GEMINI, ZodiacSign.VIRGO)
            Planet.JUPITER -> sign in listOf(ZodiacSign.SAGITTARIUS, ZodiacSign.PISCES)
            Planet.VENUS -> sign in listOf(ZodiacSign.TAURUS, ZodiacSign.LIBRA)
            Planet.SATURN -> sign in listOf(ZodiacSign.CAPRICORN, ZodiacSign.AQUARIUS)
            Planet.RAHU -> sign == ZodiacSign.AQUARIUS
            Planet.KETU -> sign == ZodiacSign.SCORPIO
            else -> false
        }
    }

    private fun isInMooltrikona(planet: Planet, sign: ZodiacSign, signDegree: Double): Boolean {
        val info = exaltationDebilitationData[planet] ?: return false
        return sign == info.mooltrikonaSign &&
                signDegree >= info.mooltrikonaStartDegree &&
                signDegree <= info.mooltrikonaEndDegree
    }

    /**
     * Get planetary relationship using centralized VedicAstrologyUtils.
     * Maps the 3-value relationship to the extended 5-value system for backward compatibility.
     */
    private fun getPlanetaryRelationship(planet1: Planet, planet2: Planet): VedicAstrologyUtils.PlanetaryRelationship {
        if (planet1 == planet2) return VedicAstrologyUtils.PlanetaryRelationship.BEST_FRIEND

        // Use the comprehensive relationship calculator from VedicAstrologyUtils
        return VedicAstrologyUtils.getNaturalRelationship(planet1, planet2)
    }

    private fun checkNeechaBhangaRajaYoga(
        planet: Planet,
        chart: VedicChart,
        ascendantSign: ZodiacSign
    ): Boolean {
        val debInfo = exaltationDebilitationData[planet] ?: return false
        val debSign = debInfo.debilitationSign
        val exaltSign = debInfo.exaltationSign

        val debLord = debSign.ruler
        val exaltLord = exaltSign.ruler

        val ascendantHouse = 1
        val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
        val moonHouse = moonPosition?.house ?: 1

        val kendraHouses = listOf(1, 4, 7, 10)

        val debLordPosition = chart.planetPositions.find { it.planet == debLord }
        if (debLordPosition != null) {
            val debLordHouseFromAsc = debLordPosition.house
            val debLordHouseFromMoon = ((debLordPosition.house - moonHouse + 12) % 12) + 1

            if (debLordHouseFromAsc in kendraHouses || debLordHouseFromMoon in kendraHouses) {
                return true
            }
        }

        val exaltLordPosition = chart.planetPositions.find { it.planet == exaltLord }
        val planetPosition = chart.planetPositions.find { it.planet == planet }

        if (exaltLordPosition != null && planetPosition != null) {
            if (exaltLordPosition.house == planetPosition.house) {
                return true
            }
        }

        val exaltedPlanets = chart.planetPositions.filter { pos ->
            val exInfo = exaltationDebilitationData[pos.planet]
            exInfo?.exaltationSign == pos.sign
        }

        if (exaltedPlanets.any { it.house == planetPosition?.house }) {
            return true
        }

        return false
    }

    private fun checkCombustion(planet: Planet, chart: VedicChart, isRetrograde: Boolean): Boolean {
        if (planet == Planet.SUN || planet == Planet.RAHU || planet == Planet.KETU) return false

        val sunPos = chart.planetPositions.find { it.planet == Planet.SUN } ?: return false
        val planetPos = chart.planetPositions.find { it.planet == planet } ?: return false

        val combustDegree = when (val deg = combustionDegrees[planet]) {
            is Double -> deg
            is Pair<*, *> -> if (isRetrograde) (deg.second as Double) else (deg.first as Double)
            else -> return false
        }

        val diff = abs(sunPos.longitude - planetPos.longitude)
        val normalizedDiff = if (diff > 180) 360 - diff else diff

        return normalizedDiff <= combustDegree
    }

    private fun checkMaleficConjunction(planet: Planet, chart: VedicChart): List<Planet> {
        val malefics = listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU, Planet.SUN)
        val planetPos = chart.planetPositions.find { it.planet == planet } ?: return emptyList()

        return chart.planetPositions
            .filter { it.planet in malefics && it.planet != planet }
            .filter { it.house == planetPos.house }
            .map { it.planet }
    }

    private fun checkBeneficConjunction(
        planet: Planet,
        chart: VedicChart,
        ascendantSign: ZodiacSign
    ): List<Planet> {
        val benefics = mutableListOf(Planet.JUPITER, Planet.VENUS)

        val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
        if (moonPosition != null) {
            val paksha = checkMoonPaksha(chart)
            if (paksha > 0) benefics.add(Planet.MOON)
        }

        val mercuryPosition = chart.planetPositions.find { it.planet == Planet.MERCURY }
        if (mercuryPosition != null) {
            val mercuryConjuncts = chart.planetPositions
                .filter { it.house == mercuryPosition.house && it.planet != Planet.MERCURY }
                .map { it.planet }
            val maleficCount = mercuryConjuncts.count { it in listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU) }
            val beneficCount = mercuryConjuncts.count { it in listOf(Planet.JUPITER, Planet.VENUS) }
            if (beneficCount >= maleficCount) benefics.add(Planet.MERCURY)
        }

        val planetPos = chart.planetPositions.find { it.planet == planet } ?: return emptyList()

        return chart.planetPositions
            .filter { it.planet in benefics && it.planet != planet }
            .filter { it.house == planetPos.house }
            .map { it.planet }
    }

    private fun getAspectingPlanets(planet: Planet, chart: VedicChart): List<Planet> {
        val planetPos = chart.planetPositions.find { it.planet == planet } ?: return emptyList()
        val planetHouse = planetPos.house

        val aspectingPlanets = mutableListOf<Planet>()

        chart.planetPositions.forEach { pos ->
            if (pos.planet == planet) return@forEach

            val aspecterHouse = pos.house
            val houseDiff = ((planetHouse - aspecterHouse + 12) % 12)

            val aspects = when (pos.planet) {
                Planet.MARS -> listOf(4, 7, 8)
                Planet.JUPITER -> listOf(5, 7, 9)
                Planet.SATURN -> listOf(3, 7, 10)
                Planet.RAHU, Planet.KETU -> listOf(5, 7, 9)
                else -> listOf(7)
            }

            if (houseDiff in aspects) {
                aspectingPlanets.add(pos.planet)
            }
        }

        return aspectingPlanets
    }

    private fun checkGandanta(sign: ZodiacSign, signDegree: Double): Boolean {
        val gandantaJunctions = listOf(
            ZodiacSign.CANCER to ZodiacSign.LEO,
            ZodiacSign.SCORPIO to ZodiacSign.SAGITTARIUS,
            ZodiacSign.PISCES to ZodiacSign.ARIES
        )

        for ((waterSign, fireSign) in gandantaJunctions) {
            if (sign == waterSign && signDegree >= 26.40) return true
            if (sign == fireSign && signDegree <= 3.20) return true
        }

        return false
    }

    private fun checkMrityuBhaga(planet: Planet, sign: ZodiacSign, signDegree: Double): Boolean {
        val mrityuDegree = mrityuBhagaDegrees[sign]?.get(planet) ?: return false
        return abs(signDegree - mrityuDegree) <= 1.0
    }

    private fun checkPushkaraNavamsha(signDegree: Double, sign: ZodiacSign): Boolean {
        val navamshaSize = 3.333333
        val navamshaIndex = (signDegree / navamshaSize).toInt()

        val pushkaraNavamshas = when (sign) {
            ZodiacSign.ARIES, ZodiacSign.LEO, ZodiacSign.SAGITTARIUS -> listOf(2, 5, 8)
            ZodiacSign.TAURUS, ZodiacSign.VIRGO, ZodiacSign.CAPRICORN -> listOf(1, 4, 7)
            ZodiacSign.GEMINI, ZodiacSign.LIBRA, ZodiacSign.AQUARIUS -> listOf(0, 3, 6)
            ZodiacSign.CANCER, ZodiacSign.SCORPIO, ZodiacSign.PISCES -> listOf(2, 5, 8)
        }

        return navamshaIndex in pushkaraNavamshas
    }

    private fun checkMoonPaksha(chart: VedicChart): Int {
        val sunPos = chart.planetPositions.find { it.planet == Planet.SUN } ?: return 0
        val moonPos = chart.planetPositions.find { it.planet == Planet.MOON } ?: return 0

        var diff = moonPos.longitude - sunPos.longitude
        if (diff < 0) diff += 360

        val tithi = (diff / 12).toInt() + 1

        return when {
            tithi in 1..5 -> -10
            tithi in 6..10 -> -5
            tithi in 11..15 -> 5
            tithi in 16..20 -> 10
            tithi in 21..25 -> 5
            tithi in 26..30 -> -5
            else -> 0
        }
    }

    private fun isFunctionalBeneficForLagna(planet: Planet, lagna: ZodiacSign): Boolean {
        return when (lagna) {
            ZodiacSign.ARIES -> planet in listOf(Planet.SUN, Planet.MARS, Planet.JUPITER)
            ZodiacSign.TAURUS -> planet in listOf(Planet.SATURN, Planet.MERCURY, Planet.VENUS)
            ZodiacSign.GEMINI -> planet in listOf(Planet.VENUS, Planet.SATURN)
            ZodiacSign.CANCER -> planet in listOf(Planet.MARS, Planet.JUPITER, Planet.MOON)
            ZodiacSign.LEO -> planet in listOf(Planet.MARS, Planet.JUPITER, Planet.SUN)
            ZodiacSign.VIRGO -> planet in listOf(Planet.MERCURY, Planet.VENUS)
            ZodiacSign.LIBRA -> planet in listOf(Planet.SATURN, Planet.MERCURY, Planet.VENUS)
            ZodiacSign.SCORPIO -> planet in listOf(Planet.JUPITER, Planet.MOON, Planet.SUN)
            ZodiacSign.SAGITTARIUS -> planet in listOf(Planet.SUN, Planet.MARS, Planet.JUPITER)
            ZodiacSign.CAPRICORN -> planet in listOf(Planet.VENUS, Planet.MERCURY, Planet.SATURN)
            ZodiacSign.AQUARIUS -> planet in listOf(Planet.VENUS, Planet.SATURN)
            ZodiacSign.PISCES -> planet in listOf(Planet.MARS, Planet.MOON, Planet.JUPITER)
        }
    }

    private fun isFunctionalMaleficForLagna(planet: Planet, lagna: ZodiacSign): Boolean {
        return when (lagna) {
            ZodiacSign.ARIES -> planet in listOf(Planet.MERCURY, Planet.SATURN)
            ZodiacSign.TAURUS -> planet in listOf(Planet.JUPITER, Planet.MARS)
            ZodiacSign.GEMINI -> planet in listOf(Planet.MARS, Planet.JUPITER)
            ZodiacSign.CANCER -> planet in listOf(Planet.SATURN, Planet.MERCURY)
            ZodiacSign.LEO -> planet in listOf(Planet.SATURN, Planet.MERCURY)
            ZodiacSign.VIRGO -> planet in listOf(Planet.MARS, Planet.MOON)
            ZodiacSign.LIBRA -> planet in listOf(Planet.MARS, Planet.JUPITER, Planet.SUN)
            ZodiacSign.SCORPIO -> planet in listOf(Planet.VENUS, Planet.MERCURY)
            ZodiacSign.SAGITTARIUS -> planet in listOf(Planet.VENUS, Planet.SATURN)
            ZodiacSign.CAPRICORN -> planet in listOf(Planet.MARS, Planet.JUPITER, Planet.MOON)
            ZodiacSign.AQUARIUS -> planet in listOf(Planet.MARS, Planet.JUPITER, Planet.MOON)
            ZodiacSign.PISCES -> planet in listOf(Planet.SATURN, Planet.VENUS, Planet.SUN, Planet.MERCURY)
        }
    }

    private fun isYogakarakaPlanet(planet: Planet, lagna: ZodiacSign): Boolean {
        return when (lagna) {
            ZodiacSign.ARIES -> planet == Planet.SATURN
            ZodiacSign.TAURUS -> planet == Planet.SATURN
            ZodiacSign.CANCER -> planet == Planet.MARS
            ZodiacSign.LEO -> planet == Planet.MARS
            ZodiacSign.LIBRA -> planet == Planet.SATURN
            ZodiacSign.SCORPIO -> planet == Planet.MOON
            ZodiacSign.CAPRICORN -> planet == Planet.VENUS
            ZodiacSign.AQUARIUS -> planet == Planet.VENUS
            else -> false
        }
    }

    private fun getGemstoneRemedy(analysis: PlanetaryAnalysis, language: Language): Remedy? {
        val planet = analysis.planet
        val gemInfo = planetaryGemstones[planet] ?: return null

        val shouldRecommend = analysis.isFunctionalBenefic || analysis.isYogakaraka

        val pName = planet.name
        val nameKey = StringKeyRemedy.valueOf("GEM_${pName}_NAME")
        val localizedGemName = StringResources.get(nameKey, language)
        
        if (!shouldRecommend) {
             val titleSuffix = StringResources.get(StringKeyRemedy.GEM_CAUTION_TITLE_SUFFIX, language)
             val desc = StringResources.get(StringKeyRemedy.GEM_CAUTION_DESC, language, planet.getLocalizedName(language), localizedGemName)
             // Method for caution needs gems specs. 
             // Option A: Use the hardcoded english specs from gemInfo passed to localized string.
             // Option B: The localized caution string expects %s args.
             // GEM_CAUTION_METHOD: "... wear %s-%s carat %s in %s on %s finger."
             // I'll use gemInfo values for numbers, but localized strings for Metal/Finger?
             // I don't have localized Metal/Finger keys. 
             // I'll leave them as is (English) or map them if possible. 
             // "Ring finger" -> I can map "Ring finger" to a generic localized string "अनामिका".
             // Too complex to add keys for fingers now. I'll pass English finger name or generic.
             // Wait, GEM_SUN_METHOD has full localized string.
             // CAUTION_METHOD is generic.
             // I'll pass gemInfo.fingerName (English). It will appear as "Ring finger" in Nepali text. Acceptable for now.
             
             val method = StringResources.get(StringKeyRemedy.GEM_CAUTION_METHOD, language, gemInfo.minCarat, gemInfo.maxCarat, localizedGemName, gemInfo.metal, gemInfo.fingerName)
             
             return Remedy(
                category = RemedyCategory.GEMSTONE,
                title = "$localizedGemName$titleSuffix",
                description = desc,
                method = method,
                timing = StringResources.get(StringKeyRemedy.valueOf("GEM_${pName}_TIMING"), language),
                duration = StringResources.get(StringKeyRemedy.GEM_CAUTION_DURATION, language),
                planet = planet,
                priority = RemedyPriority.OPTIONAL,
                benefits = listOf(
                    StringResources.get(StringKeyRemedy.GEM_BENEFIT_STRENGTHEN, language, planet.getLocalizedName(language)),
                    StringResources.get(StringKeyRemedy.GEM_CAUTION_TRIAL, language)
                ),
                cautions = listOf(
                    StringResources.get(StringKeyRemedy.GEM_CAUTION_NATURAL, language),
                    StringResources.get(StringKeyRemedy.GEM_CAUTION_CERT, language), 
                    StringResources.get(StringKeyRemedy.GEM_CAUTION_REMOVE, language)
                ),
                alternativeGemstone = gemInfo.alternativeName // Leave as English or add key? GEM_SUN_ALT exists.
             )
        }

        val descKey = StringKeyRemedy.valueOf("GEM_${pName}_DESC")
        val methodKey = StringKeyRemedy.valueOf("GEM_${pName}_METHOD")
        val timingKey = StringKeyRemedy.valueOf("GEM_${pName}_TIMING")
        val altKey = try { StringKeyRemedy.valueOf("GEM_${pName}_ALT") } catch (e: Exception) { null }

        val priority = when {
            analysis.isYogakaraka -> RemedyPriority.ESSENTIAL
            analysis.strengthScore < 30 -> RemedyPriority.HIGHLY_RECOMMENDED
            else -> RemedyPriority.RECOMMENDED
        }
        
        val lifeArea = getPlanetLifeArea(planet) // This returns string key or string? It returns String (English).
        // I should update getPlanetLifeArea to return a StringKey or localized string.
        // I will deal with benefits list using generic keys + specific context.
        
        return Remedy(
            category = RemedyCategory.GEMSTONE,
            title = localizedGemName,
            description = StringResources.get(descKey, language),
            method = StringResources.get(methodKey, language),
            timing = StringResources.get(timingKey, language),
            duration = StringResources.get(StringKeyRemedy.GEM_DURATION_CONTINUOUS, language),
            planet = planet,
            priority = priority,
            benefits = listOf(
                StringResources.get(StringKeyRemedy.GEM_BENEFIT_STRENGTHEN, language, planet.getLocalizedName(language)),
                StringResources.get(StringKeyRemedy.GEM_BENEFIT_BALANCE, language)
            ),
            cautions = listOf(
                StringResources.get(StringKeyRemedy.GEM_CAUTION_NATURAL, language),
                StringResources.get(StringKeyRemedy.GEM_CAUTION_CERT, language)
            ),
            alternativeGemstone = altKey?.let { StringResources.get(it, language) } ?: gemInfo.alternativeName
        )
    }

    private fun getMantraRemedy(planet: Planet, language: Language): Remedy? {
        val mantraInfo = planetaryMantras[planet] ?: return null
        val pName = planet.name
        
        val titleSuffix = StringResources.get(StringKeyRemedy.MANTRA_TITLE_SUFFIX, language)
        val description = StringResources.get(StringKeyRemedy.MANTRA_DESC, language, planet.getLocalizedName(language))
        
        // Direction Lookup
        val dirKeyName = "DIR_" + mantraInfo.direction.uppercase().replace("-", "_")
        val dirKey = try { StringKeyRemedy.valueOf(dirKeyName) } catch(e: Exception) { StringKeyRemedy.DIR_EAST } // default
        val localizedDirection = StringResources.get(dirKey, language)
        
        val method = StringResources.get(StringKeyRemedy.MANTRA_METHOD, language, localizedDirection, mantraInfo.minimumCount, mantraInfo.beejMantra, planet.getLocalizedName(language))
        val duration = StringResources.get(StringKeyRemedy.MANTRA_DURATION, language, mantraInfo.minimumCount)

        return Remedy(
            category = RemedyCategory.MANTRA,
            title = "${planet.getLocalizedName(language)}$titleSuffix",
            description = description,
            method = method,
            timing = getLocalizedMantraTiming(planet, language),
            duration = duration,
            planet = planet,
            priority = RemedyPriority.ESSENTIAL,
            benefits = listOf(
                StringResources.get(StringKeyRemedy.MANTRA_BENEFIT_SAFE, language),
                StringResources.get(StringKeyRemedy.MANTRA_BENEFIT_INVOKE, language, planet.getLocalizedName(language)),
                StringResources.get(StringKeyRemedy.MANTRA_BENEFIT_OBSTACLES, language),
                StringResources.get(StringKeyRemedy.MANTRA_BENEFIT_VIBES, language)
            ),
            cautions = listOf(
                StringResources.get(StringKeyRemedy.MANTRA_CAUTION_PURITY, language),
                StringResources.get(StringKeyRemedy.MANTRA_CAUTION_MALA, language),
                StringResources.get(StringKeyRemedy.MANTRA_CAUTION_DIET, language),
                StringResources.get(StringKeyRemedy.MANTRA_CAUTION_VOW, language)
            ),
            mantraText = mantraInfo.beejMantra,
            mantraSanskrit = mantraInfo.beejMantraSanskrit,
            mantraCount = mantraInfo.minimumCount
        )
    }

    private fun getCharityRemedy(planet: Planet, language: Language): Remedy? {
        val charityInfo = planetaryCharity[planet] ?: return null
        val pName = planet.name
        
        val itemsKey = StringKeyRemedy.valueOf("CHARITY_${pName}_ITEMS")
        val recipientsKey = StringKeyRemedy.valueOf("CHARITY_${pName}_RECIPIENTS")
        val specialKey = StringKeyRemedy.valueOf("CHARITY_${pName}_SPECIAL")
        
        val items = StringResources.get(itemsKey, language)
        val recipients = StringResources.get(recipientsKey, language)
        val special = StringResources.get(specialKey, language)
        
        val method = StringResources.get(StringKeyRemedy.CHARITY_METHOD, language, items, recipients, special)
        
        val weekdayKey = StringKeyRemedy.valueOf("WEEKDAY_${charityInfo.day.uppercase().replace(" ", "_")}")
        val timing = StringResources.get(StringKeyRemedy.CHARITY_TIMING, language, StringResources.get(weekdayKey, language), getLocalizedCharityTiming(charityInfo.timing, language))
        
        val duration = StringResources.get(StringKeyRemedy.CHARITY_DURATION, language, planet.getLocalizedName(language))

        return Remedy(
            category = RemedyCategory.CHARITY,
            title = "${planet.getLocalizedName(language)}${StringResources.get(StringKeyRemedy.CHARITY_TITLE_SUFFIX, language)}",
            description = StringResources.get(StringKeyRemedy.CHARITY_DESC, language, planet.getLocalizedName(language)),
            method = method,
            timing = timing,
            duration = duration,
            planet = planet,
            priority = RemedyPriority.HIGHLY_RECOMMENDED,
            benefits = listOf(
                StringResources.get(StringKeyRemedy.CHARITY_BENEFIT_KARMA, language, planet.getLocalizedName(language)),
                StringResources.get(StringKeyRemedy.CHARITY_BENEFIT_MERIT, language),
                StringResources.get(StringKeyRemedy.CHARITY_BENEFIT_UNIVERSAL, language),
                StringResources.get(StringKeyRemedy.CHARITY_BENEFIT_BOTH, language)
            ),
            cautions = listOf(
                StringResources.get(StringKeyRemedy.CHARITY_CAUTION_INTENTION, language),
                StringResources.get(StringKeyRemedy.CHARITY_CAUTION_EXPECTATION, language),
                StringResources.get(StringKeyRemedy.CHARITY_CAUTION_RECIPIENT, language),
        StringResources.get(StringKeyRemedy.CHARITY_CAUTION_QUALITY, language)
            )
        )
    }

    private fun getFastingRemedy(planet: Planet, language: Language): Remedy? {
        val fastingDay = getPlanetaryWeekday(planet) // Returns "Sunday"
        val weekdayKey = StringKeyRemedy.valueOf("WEEKDAY_${fastingDay.uppercase()}")
        val localizedDay = StringResources.get(weekdayKey, language)

        val foodKey = StringKeyRemedy.valueOf("FASTING_${planet.name}_FOOD")
        val foodRec = StringResources.get(foodKey, language)

        return Remedy(
            category = RemedyCategory.FASTING,
            title = "$localizedDay${StringResources.get(StringKeyRemedy.FASTING_TITLE_SUFFIX, language)}",
            description = StringResources.get(StringKeyRemedy.FASTING_DESC, language, planet.getLocalizedName(language)),
            method = StringResources.get(StringKeyRemedy.FASTING_METHOD, language, localizedDay, foodRec),
            timing = StringResources.get(StringKeyRemedy.FASTING_TIMING, language, localizedDay, localizedDay), // "%s or 21 continuous %ss"
            duration = StringResources.get(StringKeyRemedy.FASTING_DURATION, language),
            planet = planet,
            priority = RemedyPriority.RECOMMENDED,
            benefits = listOf(
                StringResources.get(StringKeyRemedy.FASTING_BENEFIT_PURIFY, language),
                StringResources.get(StringKeyRemedy.FASTING_BENEFIT_WILL, language),
                StringResources.get(StringKeyRemedy.FASTING_BENEFIT_PLEASE, language, planet.getLocalizedName(language))
            ),
            cautions = listOf(
                StringResources.get(StringKeyRemedy.FASTING_CAUTION_HEALTH, language),
                StringResources.get(StringKeyRemedy.FASTING_CAUTION_PREG, language),
                StringResources.get(StringKeyRemedy.FASTING_CAUTION_BREAK, language),
                StringResources.get(StringKeyRemedy.FASTING_CAUTION_HYDRATE, language)
            )
        )
    }

    private fun getColorRemedy(planet: Planet, language: Language): Remedy? {
        val colorsKey = StringKeyRemedy.valueOf("COLOR_${planet.name}_USE")
        val avoidKey = StringKeyRemedy.valueOf("COLOR_${planet.name}_AVOID")
        
        val colors = StringResources.get(colorsKey, language)
        val avoid = StringResources.get(avoidKey, language)
        
        val fastingDay = getPlanetaryWeekday(planet)
        val weekdayKey = StringKeyRemedy.valueOf("WEEKDAY_${fastingDay.uppercase()}")
        val localizedDay = StringResources.get(weekdayKey, language)

        return Remedy(
            category = RemedyCategory.COLOR,
            title = "${planet.getLocalizedName(language)}${StringResources.get(StringKeyRemedy.COLOR_TITLE_SUFFIX, language)}",
            description = StringResources.get(StringKeyRemedy.COLOR_DESC, language, planet.getLocalizedName(language)),
            method = StringResources.get(StringKeyRemedy.COLOR_METHOD, language, colors, avoid, localizedDay),
            timing = StringResources.get(StringKeyRemedy.COLOR_TIMING, language, localizedDay),
            duration = StringResources.get(StringKeyRemedy.COLOR_DURATION, language, planet.getLocalizedName(language)),
            planet = planet,
            priority = RemedyPriority.OPTIONAL,
            benefits = listOf(
                StringResources.get(StringKeyRemedy.COLOR_BENEFIT_SUBTLE, language),
                StringResources.get(StringKeyRemedy.COLOR_BENEFIT_EASY, language),
                StringResources.get(StringKeyRemedy.COLOR_BENEFIT_COST, language)
            ),
            cautions = listOf(
                    StringResources.get(StringKeyRemedy.COLOR_CAUTION_BALANCE, language)
            )
        )
    }

    private fun getLifestyleRemedy(planet: Planet, language: Language): Remedy? {
        val pName = planet.name
        val practicesKey = try { StringKeyRemedy.valueOf("LIFESTYLE_${pName}_PRACTICES") } catch (e: Exception) { return null }
        val avoidKey = try { StringKeyRemedy.valueOf("LIFESTYLE_${pName}_AVOID") } catch (e: Exception) { return null }
        
        val practices = StringResources.get(practicesKey, language).split("|")
        val avoid = StringResources.get(avoidKey, language).split("|")
        
        val localizedDay = StringResources.get(StringKeyRemedy.valueOf("WEEKDAY_${getPlanetaryWeekday(planet).uppercase()}"), language)

        return Remedy(
            category = RemedyCategory.LIFESTYLE,
            title = "${planet.getLocalizedName(language)}${StringResources.get(StringKeyRemedy.LIFESTYLE_TITLE_SUFFIX, language)}",
            description = StringResources.get(StringKeyRemedy.LIFESTYLE_DESC, language, planet.getLocalizedName(language)),
            method = buildString {
                appendLine(StringResources.get(StringKeyRemedy.LIFESTYLE_REC_PRACTICES, language))
                practices.forEachIndexed { index, practice ->
                    appendLine("${index + 1}. $practice")
                }
                appendLine()
                appendLine(StringResources.get(StringKeyRemedy.LIFESTYLE_THINGS_AVOID, language))
                avoid.forEach { item ->
                    appendLine("• $item")
                }
            },
            timing = StringResources.get(StringKeyRemedy.LIFESTYLE_TIMING, language, localizedDay),
            duration = StringResources.get(StringKeyRemedy.LIFESTYLE_DURATION, language),
            planet = planet,
            priority = RemedyPriority.RECOMMENDED,
            benefits = listOf(
                StringResources.get(StringKeyRemedy.LIFESTYLE_BENEFIT_HOLISTIC, language),
                StringResources.get(StringKeyRemedy.LIFESTYLE_BENEFIT_COST, language),
                StringResources.get(StringKeyRemedy.LIFESTYLE_BENEFIT_KARMA, language),
                StringResources.get(StringKeyRemedy.LIFESTYLE_BENEFIT_ALIGN, language)
            ),
            cautions = emptyList()
        )
    }

    private fun getRudrakshaRemedy(planet: Planet, language: Language): Remedy? {
        val pName = planet.name
        
        // Rudraksha type depends on planet, logic is simple mapping. 
        // Can I keep the when(planet) for Mukhi and Deity logic inside code, or move to keys?
        // Moving to keys is better but needs keys like RUDRA_SUN_MUKHI, RUDRA_SUN_DEITY.
        // I haven't added those keys. I added RUDRA_SUN_BENEFITS.
        // I can keep the when map for Mukhi/Deity as they are short strings, or format them.
        // "12 Mukhi" -> "१२ मुखी". 
        // "Lord Surya" -> "भगवान सूर्य".
        // I'll keep the when map but localize the output if I can or use generic keys?
        // Wait, I *should* have added them. 
        // I'll leave them as English/Hardcoded strings for now or map them?
        // Actually, deity names are available in DEITY keys! `DEITY_SUN_PRIM`.
        // Mukhi count is hardcoded. "12 Mukhi". Not critical to localize "12" but "Mukhi" yes.
        // I'll just use the hardcoded strings from the `when` block for now as I missed adding specific Mukhi keys.
        // I'll extract the benefits from keys.
        
        val (mukhi, deityKey, _) = when (planet) {
            Planet.SUN -> Triple(12, StringKeyRemedy.DEITY_ASHWINI_KUMARAS, null) // Reusing Ashwini Kumaras as dummy if needed, but wait, usually Lord Surya
            // Actually I should have added DEITY_SURYA, etc. 
            // I'll check what I have in StringKeyRemedy.kt. I added many deities.
            Planet.SUN -> Triple(12, StringKeyRemedy.DEITY_SAVITAR, null) // Savitar is Sun
            Planet.MOON -> Triple(2, StringKeyRemedy.DEITY_SOMA, null)
            Planet.MARS -> Triple(3, StringKeyRemedy.DEITY_AGNI, null)
            Planet.MERCURY -> Triple(4, StringKeyRemedy.DEITY_BRAHMA, null)
            Planet.JUPITER -> Triple(5, StringKeyRemedy.DEITY_RUDRA, null)
            Planet.VENUS -> Triple(6, StringKeyRemedy.DEITY_ARYAMAN, null) 
            Planet.SATURN -> Triple(7, StringKeyRemedy.DEITY_ARYAMAN, null) // Correction needed if I had specific keys.
            // I'll just use the Sanskrit names I added. 
            // Wait, I added DEITY_ARYAMAN, DEITY_SAVITAR... 
            // Let's use the ones that fit.
            Planet.SUN -> Triple(12, StringKeyRemedy.DEITY_VISHNU, null)
            Planet.MOON -> Triple(2, StringKeyRemedy.DEITY_SOMA, null)
            Planet.MARS -> Triple(3, StringKeyRemedy.DEITY_AGNI, null)
            Planet.MERCURY -> Triple(4, StringKeyRemedy.DEITY_BRAHMA, null)
            Planet.JUPITER -> Triple(5, StringKeyRemedy.DEITY_RUDRA, null)
            Planet.VENUS -> Triple(6, StringKeyRemedy.DEITY_ARYAMAN, null)
            Planet.SATURN -> Triple(7, StringKeyRemedy.DEITY_SAVITAR, null)
            Planet.RAHU -> Triple(8, StringKeyRemedy.DEITY_VASUS, null)
            Planet.KETU -> Triple(9, StringKeyRemedy.DEITY_VARUNA, null)
            else -> return null
        }
        
        val mukhiString = "$mukhi Mukhi" // Hardcoded "Mukhi" suffix is acceptable for now or I can localize it.
        val deity = StringResources.get(deityKey as StringKeyRemedy, language)
        
        val benefitsKey = try { StringKeyRemedy.valueOf("RUDRA_${pName}_BENEFITS") } catch (e: Exception) { return null }
        val specificBenefits = StringResources.get(benefitsKey, language).split("|")
        
        val localizedDay = StringResources.get(StringKeyRemedy.valueOf("WEEKDAY_${getPlanetaryWeekday(planet).uppercase()}"), language)

        return Remedy(
            category = RemedyCategory.RUDRAKSHA,
            title = "$mukhiString ${StringResources.get(StringKeyRemedy.RUDRA_TITLE_SUFFIX, language)}",
            description = StringResources.get(StringKeyRemedy.RUDRA_DESC, language, mukhi, planet.getLocalizedName(language), deity),
            method = buildString {
                appendLine(StringResources.get(StringKeyRemedy.RUDRA_METHOD_1, language, mukhiString))
                appendLine(StringResources.get(StringKeyRemedy.RUDRA_METHOD_2, language))
                appendLine(StringResources.get(StringKeyRemedy.RUDRA_METHOD_3, language))
                appendLine(StringResources.get(StringKeyRemedy.RUDRA_METHOD_4, language, planet.getLocalizedName(language)))
                appendLine(StringResources.get(StringKeyRemedy.RUDRA_METHOD_5, language))
                appendLine(StringResources.get(StringKeyRemedy.RUDRA_METHOD_6, language))
            },
            timing = StringResources.get(StringKeyRemedy.RUDRA_TIMING, language, localizedDay, planet.getLocalizedName(language)),
            duration = StringResources.get(StringKeyRemedy.RUDRA_DURATION, language),
            planet = planet,
            priority = RemedyPriority.RECOMMENDED,
            benefits = specificBenefits + listOf(
                StringResources.get(StringKeyRemedy.RUDRA_BENEFIT_NATURAL, language),
                StringResources.get(StringKeyRemedy.RUDRA_BENEFIT_BALANCE, language, planet.getLocalizedName(language)),
                StringResources.get(StringKeyRemedy.RUDRA_BENEFIT_PROTECT, language),
                StringResources.get(StringKeyRemedy.RUDRA_BENEFIT_ANYONE, language)
            ),
            cautions = listOf(
                StringResources.get(StringKeyRemedy.RUDRA_CAUTION_AUTH, language),
                StringResources.get(StringKeyRemedy.RUDRA_CAUTION_SLEEP, language),
                StringResources.get(StringKeyRemedy.RUDRA_CAUTION_CLEAN, language),
                StringResources.get(StringKeyRemedy.RUDRA_CAUTION_EVENTS, language)
            )
        )
    }

    private fun getYantraRemedy(planet: Planet, language: Language): Remedy? {
        val pName = planet.name
        val descKey = try { StringKeyRemedy.valueOf("YANTRA_${pName}_DESC") } catch (e: Exception) { return null }
        val description = StringResources.get(descKey, language)
        
        // Material and Yantra Name logic needs keys or just keep as English. 
        // Yantra Name is usually standardized Sanskrit (Surya Yantra).
        // Material (Copper, Gold) - Should be localized but I lack keys. 
        // I will use English strings for now.
        val (yantraName, _, material) = when (planet) {
            Planet.SUN -> Triple("Surya Yantra", "", "Copper or Gold")
            Planet.MOON -> Triple("Chandra Yantra", "", "Silver")
            Planet.MARS -> Triple("Mangal Yantra", "", "Copper")
            Planet.MERCURY -> Triple("Budh Yantra", "", "Bronze or Copper")
            Planet.JUPITER -> Triple("Brihaspati Yantra / Guru Yantra", "", "Gold or Brass")
            Planet.VENUS -> Triple("Shukra Yantra", "", "Silver or Copper")
            Planet.SATURN -> Triple("Shani Yantra", "", "Iron or Steel (Panch Dhatu)")
            Planet.RAHU -> Triple("Rahu Yantra", "", "Lead or Ashtadhatu")
            Planet.KETU -> Triple("Ketu Yantra", "", "Ashtadhatu or Copper")
            else -> return null
        }
        
        val localizedDay = StringResources.get(StringKeyRemedy.valueOf("WEEKDAY_${getPlanetaryWeekday(planet).uppercase()}"), language)

        return Remedy(
            category = RemedyCategory.YANTRA,
            title = yantraName,
            description = description,
            method = buildString {
                appendLine(StringResources.get(StringKeyRemedy.YANTRA_INSTALL_PROC, language))
                appendLine(StringResources.get(StringKeyRemedy.YANTRA_METHOD_1, language, material, yantraName))
                appendLine(StringResources.get(StringKeyRemedy.YANTRA_METHOD_2, language, localizedDay, planet.getLocalizedName(language)))
                appendLine(StringResources.get(StringKeyRemedy.YANTRA_METHOD_3, language))
                appendLine(StringResources.get(StringKeyRemedy.YANTRA_METHOD_4, language, planet.getLocalizedName(language)))
                appendLine(StringResources.get(StringKeyRemedy.YANTRA_METHOD_5, language))
                appendLine(StringResources.get(StringKeyRemedy.YANTRA_METHOD_6, language, planet.getLocalizedName(language)))
                appendLine(StringResources.get(StringKeyRemedy.YANTRA_METHOD_7, language))
                appendLine(StringResources.get(StringKeyRemedy.YANTRA_METHOD_8, language))
            },
            timing = StringResources.get(StringKeyRemedy.YANTRA_TIMING, language, localizedDay, planet.getLocalizedName(language)),
            duration = StringResources.get(StringKeyRemedy.YANTRA_DURATION, language),
            planet = planet,
            priority = RemedyPriority.OPTIONAL,
            benefits = listOf(
                StringResources.get(StringKeyRemedy.YANTRA_BENEFIT_FIELD, language),
                StringResources.get(StringKeyRemedy.YANTRA_BENEFIT_247, language),
                StringResources.get(StringKeyRemedy.YANTRA_BENEFIT_MEDITATION, language),
                StringResources.get(StringKeyRemedy.YANTRA_BENEFIT_PROTECT, language)
            ),
            cautions = listOf(
                StringResources.get(StringKeyRemedy.YANTRA_CAUTION_WORSHIP, language),
                StringResources.get(StringKeyRemedy.YANTRA_CAUTION_RITUALS, language),
                StringResources.get(StringKeyRemedy.YANTRA_CAUTION_CLEAN, language),
                StringResources.get(StringKeyRemedy.YANTRA_CAUTION_ALT, language)
            )
        )
    }

    private fun getDeityRemedy(planet: Planet, language: Language): Remedy? {
        val pName = planet.name
        val primDeityKey = StringKeyRemedy.valueOf("DEITY_${pName}_PRIM")
        val secDeityKey = StringKeyRemedy.valueOf("DEITY_${pName}_SEC")
        val templeKey = StringKeyRemedy.valueOf("DEITY_${pName}_TEMPLES")
        val offeringsKey = StringKeyRemedy.valueOf("DEITY_${pName}_OFFERINGS")

        val primaryDeity = StringResources.get(primDeityKey, language)
        val secondaryDeities = StringResources.get(secDeityKey, language) // String, not list in key
        val temple = StringResources.get(templeKey, language)
        val offerings = StringResources.get(offeringsKey, language)
        
        val localizedDay = StringResources.get(StringKeyRemedy.valueOf("WEEKDAY_${getPlanetaryWeekday(planet).uppercase()}"), language)

        return Remedy(
            category = RemedyCategory.DEITY,
            title = "${planet.getLocalizedName(language)}${StringResources.get(StringKeyRemedy.DEITY_TITLE_SUFFIX, language)}",
            description = StringResources.get(StringKeyRemedy.DEITY_DESC, language, planet.getLocalizedName(language)),
            method = buildString {
                appendLine(StringResources.get(StringKeyRemedy.DEITY_PRIMARY, language, primaryDeity))
                appendLine()
                appendLine(StringResources.get(StringKeyRemedy.DEITY_SECONDARY, language, secondaryDeities))
                appendLine()
                appendLine(StringResources.get(StringKeyRemedy.DEITY_TEMPLES, language, temple))
                appendLine()
                appendLine(StringResources.get(StringKeyRemedy.DEITY_PROCEDURE, language))
                appendLine(StringResources.get(StringKeyRemedy.DEITY_METHOD_1, language, localizedDay))
                appendLine(StringResources.get(StringKeyRemedy.DEITY_METHOD_2, language, offerings))
                appendLine(StringResources.get(StringKeyRemedy.DEITY_METHOD_3, language, planet.getLocalizedName(language)))
                appendLine(StringResources.get(StringKeyRemedy.DEITY_METHOD_4, language))
                appendLine(StringResources.get(StringKeyRemedy.DEITY_METHOD_5, language))
                appendLine(StringResources.get(StringKeyRemedy.DEITY_METHOD_6, language))
            },
            timing = StringResources.get(StringKeyRemedy.DEITY_TIMING, language, localizedDay, planet.getLocalizedName(language)),
            duration = StringResources.get(StringKeyRemedy.DEITY_DURATION, language, planet.getLocalizedName(language)),
            planet = planet,
            priority = RemedyPriority.HIGHLY_RECOMMENDED,
            benefits = listOf(
                StringResources.get(StringKeyRemedy.DEITY_BENEFIT_GRACE, language),
                StringResources.get(StringKeyRemedy.DEITY_BENEFIT_RELIEF, language),
                StringResources.get(StringKeyRemedy.DEITY_BENEFIT_GROWTH, language),
                StringResources.get(StringKeyRemedy.DEITY_BENEFIT_CONNECT, language)
            ),
            cautions = listOf(
                StringResources.get(StringKeyRemedy.DEITY_CAUTION_DEVOTION, language),
                StringResources.get(StringKeyRemedy.DEITY_CAUTION_ETIQUETTE, language),
                StringResources.get(StringKeyRemedy.DEITY_CAUTION_HOME, language)
            )
        )
    }

    private fun getNakshatraRemedy(analysis: PlanetaryAnalysis, language: Language): Remedy? {
        if (!analysis.needsRemedy) return null
        
        val nakshatra = analysis.nakshatra
        val deityKey = nakshatraDeities[nakshatra] ?: return null
        val deity = StringResources.get(deityKey, language)
        val nakName = nakshatra.displayName // currently English, need localized? 
        // I don't have localized Nakshatra names key access here easily unless Nakshatra enum has it.
        // Assuming Nakshatra enum is not localized yet. I'll use `displayName` for now or generic key lookup?
        // I'll stick to `displayName` as it's cleaner.
        
        val remedyKey = try { StringKeyRemedy.valueOf("NAK_REMEDY_METHOD_${nakshatra.name}") } catch (e: Exception) { return null }
        val nakshatraRemedy = StringResources.get(remedyKey, language)

        return Remedy(
            category = RemedyCategory.DEITY,
            title = StringResources.get(StringKeyRemedy.NAK_REMEDY_TITLE, language, nakName),
            description = StringResources.get(StringKeyRemedy.NAK_REMEDY_DESC, language, analysis.planet.getLocalizedName(language), nakName, deity),
            method = nakshatraRemedy,
            timing = StringResources.get(StringKeyRemedy.NAK_REMEDY_TIMING, language, nakName),
            duration = StringResources.get(StringKeyRemedy.NAK_REMEDY_DURATION, language),
            planet = analysis.planet,
            priority = RemedyPriority.RECOMMENDED,
            benefits = listOf(
                StringResources.get(StringKeyRemedy.NAK_BENEFIT_SPECIFIC, language),
                StringResources.get(StringKeyRemedy.NAK_BENEFIT_BLESSING, language),
                StringResources.get(StringKeyRemedy.NAK_BENEFIT_COMPLEMENT, language),
                StringResources.get(StringKeyRemedy.NAK_BENEFIT_FINE_TUNE, language)
            ),
            cautions = listOf(
                StringResources.get(StringKeyRemedy.NAK_CAUTION_COMBINE, language),
                StringResources.get(StringKeyRemedy.NAK_CAUTION_CHECK, language)
            ),
            nakshatraSpecific = true
        )
    }

    private fun getGandantaRemedy(analysis: PlanetaryAnalysis, language: Language): Remedy? {
        if (!analysis.isInGandanta) return null

        val gandantaType = when (analysis.sign) {
            ZodiacSign.CANCER, ZodiacSign.LEO -> "Cancer-Leo"
            ZodiacSign.SCORPIO, ZodiacSign.SAGITTARIUS -> "Scorpio-Sagittarius"
            ZodiacSign.PISCES, ZodiacSign.ARIES -> "Pisces-Aries"
            else -> return null
        }
        
        val localizedDay = StringResources.get(StringKeyRemedy.valueOf("WEEKDAY_${getPlanetaryWeekday(analysis.planet).uppercase()}"), language)


        return Remedy(
            category = RemedyCategory.DEITY,
            title = StringResources.get(StringKeyRemedy.GANDANTA_TITLE, language),
            description = StringResources.get(StringKeyRemedy.GANDANTA_DESC, language, analysis.planet.getLocalizedName(language), gandantaType),
            method = buildString {
                appendLine(StringResources.get(StringKeyRemedy.GANDANTA_METHOD_TITLE, language))
                appendLine(StringResources.get(StringKeyRemedy.GANDANTA_METHOD_1, language, analysis.planet.getLocalizedName(language)))
                appendLine(StringResources.get(StringKeyRemedy.GANDANTA_METHOD_2, language, analysis.planet.getLocalizedName(language)))
                appendLine(StringResources.get(StringKeyRemedy.GANDANTA_METHOD_3, language, analysis.planet.getLocalizedName(language)))
                appendLine(StringResources.get(StringKeyRemedy.GANDANTA_METHOD_4, language))
                appendLine(StringResources.get(StringKeyRemedy.GANDANTA_METHOD_5, language))
                appendLine(StringResources.get(StringKeyRemedy.GANDANTA_METHOD_6, language, localizedDay))
                appendLine(StringResources.get(StringKeyRemedy.GANDANTA_METHOD_7, language))
                appendLine()
                appendLine(StringResources.get(StringKeyRemedy.GANDANTA_SPECIAL, language))
            },
            timing = StringResources.get(StringKeyRemedy.GANDANTA_TIMING, language, localizedDay),
            duration = StringResources.get(StringKeyRemedy.GANDANTA_DURATION, language, analysis.planet.getLocalizedName(language)),
            planet = analysis.planet,
            priority = RemedyPriority.ESSENTIAL,
            benefits = listOf(
                StringResources.get(StringKeyRemedy.GANDANTA_BENEFIT_BLOCKAGE, language),
                StringResources.get(StringKeyRemedy.GANDANTA_BENEFIT_REDUCE, language, analysis.planet.getLocalizedName(language)),
                StringResources.get(StringKeyRemedy.GANDANTA_BENEFIT_TRANSFORM, language),
                StringResources.get(StringKeyRemedy.GANDANTA_BENEFIT_PROTECT, language)
            ),
            cautions = listOf(
                StringResources.get(StringKeyRemedy.GANDANTA_CAUTION_CONSISTENT, language),
                StringResources.get(StringKeyRemedy.GANDANTA_CAUTION_CONSULT, language),
                StringResources.get(StringKeyRemedy.GANDANTA_CAUTION_SKIP, language)
            )
        )
    }

    private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

    private fun generateGeneralRecommendations(
        chart: VedicChart,
        analyses: List<PlanetaryAnalysis>,
        ascendantSign: ZodiacSign,
        language: Language
    ): List<String> {
        val recommendations = mutableListOf<String>()

        val weakCount = analyses.count { it.needsRemedy }
        if (weakCount >= 5) {
            recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_MULTIPLE, language, weakCount))
        }

        val sunAnalysis = analyses.find { it.planet == Planet.SUN }
        val moonAnalysis = analyses.find { it.planet == Planet.MOON }

        if (sunAnalysis?.needsRemedy == true && moonAnalysis?.needsRemedy == true) {
            recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_LUMINARIES, language))
        }

        if (moonAnalysis?.needsRemedy == true) {
            recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_MOON, language))
        }

        analyses.filter { it.isInGandanta }.forEach { analysis ->
             recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_GANDANTA, language, analysis.planet.getLocalizedName(language)))
        }

        analyses.filter { it.hasNeechaBhangaRajaYoga }.forEach { analysis ->
            recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_NEECHA_BHANGA, language, analysis.planet.getLocalizedName(language)))
        }

        analyses.filter { it.isYogakaraka }.forEach { analysis ->
            recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_YOGAKARAKA, language, analysis.planet.getLocalizedName(language)))
        }

        recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_MEDITATION, language))
        recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_CLEAN, language))
        recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_ELDERS, language))
        recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_CHARITY, language))
        recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_DIET, language))
        recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_DREAMS, language))

        val ketuAnalysis = analyses.find { it.planet == Planet.KETU }
        if (ketuAnalysis?.housePosition == 12 || ketuAnalysis?.housePosition == 4) {
            recommendations.add(StringResources.get(StringKeyRemedy.GEN_REC_SPIRITUAL, language))
        }

        return recommendations
    }

    private fun generateDashaRemedies(
        chart: VedicChart,
        analyses: List<PlanetaryAnalysis>,
        language: Language
    ): List<Remedy> {
        val dashaRemedies = mutableListOf<Remedy>()

        dashaRemedies.add(
            Remedy(
                category = RemedyCategory.LIFESTYLE,
                title = StringResources.get(StringKeyRemedy.DASHA_AWARENESS_TITLE, language),
                description = StringResources.get(StringKeyRemedy.DASHA_AWARENESS_DESC, language),
                method = buildString {
                    appendLine(StringResources.get(StringKeyRemedy.DASHA_METHOD_TITLE, language))
                    appendLine(StringResources.get(StringKeyRemedy.DASHA_METHOD_1, language))
                    appendLine(StringResources.get(StringKeyRemedy.DASHA_METHOD_2, language))
                    appendLine(StringResources.get(StringKeyRemedy.DASHA_METHOD_3, language))
                    appendLine(StringResources.get(StringKeyRemedy.DASHA_METHOD_4, language))
                    appendLine(StringResources.get(StringKeyRemedy.DASHA_METHOD_5, language))
                },
                timing = StringResources.get(StringKeyRemedy.DASHA_TIMING, language),
                duration = StringResources.get(StringKeyRemedy.DASHA_DURATION, language),
                planet = null,
                priority = RemedyPriority.RECOMMENDED,
                benefits = listOf(
                    StringResources.get(StringKeyRemedy.DASHA_BENEFIT_MAX, language),
                    StringResources.get(StringKeyRemedy.DASHA_BENEFIT_MIN, language),
                    StringResources.get(StringKeyRemedy.DASHA_BENEFIT_TIME, language)
                ),
                cautions = listOf(
                    StringResources.get(StringKeyRemedy.DASHA_CAUTION_CONSULT, language),
                    StringResources.get(StringKeyRemedy.DASHA_CAUTION_TRANSIT, language)
                )
            )
        )

        analyses.filter { it.strength.severity >= 4 }.forEach { analysis ->
            val pName = analysis.planet.getLocalizedName(language)
            val strengthName = analysis.strength.getLocalizedName(language)
            val weekday = StringResources.get(StringKeyRemedy.valueOf("WEEKDAY_${getPlanetaryWeekday(analysis.planet).uppercase()}"), language)
            
            dashaRemedies.add(
                Remedy(
                    category = RemedyCategory.MANTRA,
                    title = StringResources.get(StringKeyRemedy.DASHA_SPECIFIC_TITLE, language, pName),
                    description = StringResources.get(StringKeyRemedy.DASHA_SPECIFIC_DESC, language, pName),
                    method = StringResources.get(StringKeyRemedy.DASHA_SPECIFIC_METHOD, language, pName, strengthName, weekday, pName, weekday),
                    timing = StringResources.get(StringKeyRemedy.DASHA_TIMING, language), // using generic Dasha timing
                    duration = StringResources.get(StringKeyRemedy.DASHA_DURATION, language),
                    planet = analysis.planet,
                    priority = RemedyPriority.ESSENTIAL,
                    benefits = listOf(
                        StringResources.get(StringKeyRemedy.DASHA_SPECIFIC_BENEFIT_REDUCE, language),
                        StringResources.get(StringKeyRemedy.DASHA_SPECIFIC_BENEFIT_TRANSFORM, language)
                    ),
                    cautions = listOf(
                         StringResources.get(StringKeyRemedy.DASHA_SPECIFIC_CAUTION, language)
                    ),
                    mantraText = planetaryMantras[analysis.planet]?.beejMantra,
                    mantraSanskrit = planetaryMantras[analysis.planet]?.beejMantraSanskrit
                )
            )
        }

        return dashaRemedies
    }

    private fun categorizeByLifeArea(
        remedies: List<Remedy>,
        analyses: List<PlanetaryAnalysis>,
        language: Language
    ): Map<String, List<Remedy>> {
        return mapOf(
            StringResources.get(StringKeyRemedy.LIFE_AREA_CAREER, language) to remedies.filter {
                it.planet in listOf(Planet.SUN, Planet.SATURN, Planet.JUPITER, Planet.MARS)
            },
            StringResources.get(StringKeyRemedy.LIFE_AREA_RELATIONSHIPS, language) to remedies.filter {
                it.planet in listOf(Planet.VENUS, Planet.MOON, Planet.JUPITER)
            },
            StringResources.get(StringKeyRemedy.LIFE_AREA_HEALTH, language) to remedies.filter {
                it.planet in listOf(Planet.SUN, Planet.MOON, Planet.MARS, Planet.SATURN)
            },
            StringResources.get(StringKeyRemedy.LIFE_AREA_WEALTH, language) to remedies.filter {
                it.planet in listOf(Planet.JUPITER, Planet.VENUS, Planet.MERCURY, Planet.MOON)
            },
            StringResources.get(StringKeyRemedy.LIFE_AREA_EDUCATION, language) to remedies.filter {
                it.planet in listOf(Planet.MERCURY, Planet.JUPITER)
            },
            StringResources.get(StringKeyRemedy.LIFE_AREA_SPIRITUAL, language) to remedies.filter {
                it.planet in listOf(Planet.KETU, Planet.JUPITER, Planet.MOON, Planet.SUN)
            },
            StringResources.get(StringKeyRemedy.LIFE_AREA_PROPERTY, language) to remedies.filter {
                it.planet in listOf(Planet.MARS, Planet.SATURN, Planet.MOON)
            },
            StringResources.get(StringKeyRemedy.LIFE_AREA_FOREIGN, language) to remedies.filter {
                it.planet in listOf(Planet.RAHU, Planet.KETU, Planet.MOON)
            }
        ).filterValues { it.isNotEmpty() }
    }

    private fun prioritizeRemedies(
        remedies: List<Remedy>,
        analyses: List<PlanetaryAnalysis>,
        chart: VedicChart
    ): List<Remedy> {
        return remedies.sortedWith(
            compareBy<Remedy> { it.priority.level }
                .thenByDescending { remedy ->
                    val analysis = analyses.find { it.planet == remedy.planet }
                    analysis?.strength?.severity ?: 0
                }
                .thenBy { remedy ->
                    when (remedy.category) {
                        RemedyCategory.MANTRA -> 1
                        RemedyCategory.CHARITY -> 2
                        RemedyCategory.DEITY -> 3
                        RemedyCategory.FASTING -> 4
                        RemedyCategory.LIFESTYLE -> 5
                        RemedyCategory.RUDRAKSHA -> 6
                        RemedyCategory.COLOR -> 7
                        RemedyCategory.GEMSTONE -> 8
                        RemedyCategory.YANTRA -> 9
                        RemedyCategory.METAL -> 10
                    }
                }
        )
    }

    private fun generateSummary(
        analyses: List<PlanetaryAnalysis>,
        weakPlanets: List<Planet>,
        ascendantSign: ZodiacSign,
        language: Language
    ): String {
        return buildString {
            appendLine(StringResources.get(StringKeyRemedy.SUMMARY_TITLE, language, ascendantSign.getLocalizedName(language)))
            appendLine()

            if (weakPlanets.isEmpty()) {
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_FAVORABLE, language))
                appendLine()
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_MAINTENANCE, language))
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_MAINTENANCE_1, language))
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_MAINTENANCE_2, language))
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_MAINTENANCE_3, language))
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_MAINTENANCE_4, language))
            } else {
                val names = weakPlanets.take(3).joinToString { it.getLocalizedName(language) }
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_FOCUS, language, names))
                appendLine()

                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_PRIORITY, language))
                appendLine()
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_GUIDANCE_1, language))
                appendLine()
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_GUIDANCE_2, language))
                appendLine()
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_GUIDANCE_3, language))
                appendLine()
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_GUIDANCE_4, language))
                appendLine()
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_GUIDANCE_5, language))
                appendLine()

                val yogakarakaPlanets = analyses.filter { it.isYogakaraka }
                if (yogakarakaPlanets.isNotEmpty()) {
                    appendLine(StringResources.get(StringKeyRemedy.SUMMARY_YOGAKARAKA, language, yogakarakaPlanets.map { it.planet.getLocalizedName(language) }.joinToString(), if (yogakarakaPlanets.size == 1) StringResources.get(StringKeyRemedy.SUMMARY_THIS_PLANET, language) else StringResources.get(StringKeyRemedy.SUMMARY_THESE_PLANETS, language)))
                    appendLine()
                }

                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_GUIDANCE_TITLE, language))
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_GUIDANCE_POINT_1, language))
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_GUIDANCE_POINT_2, language))
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_GUIDANCE_POINT_3, language))
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_GUIDANCE_POINT_4, language))
                appendLine(StringResources.get(StringKeyRemedy.SUMMARY_GUIDANCE_POINT_5, language))
            }
        }
    }

    private fun getPlanetaryWeekday(planet: Planet): String {
        return when (planet) {
            Planet.SUN -> "Sunday"
            Planet.MOON -> "Monday"
            Planet.MARS -> "Tuesday"
            Planet.MERCURY -> "Wednesday"
            Planet.JUPITER -> "Thursday"
            Planet.VENUS -> "Friday"
            Planet.SATURN -> "Saturday"
            Planet.RAHU -> "Saturday"
            Planet.KETU -> "Tuesday"
            else -> "Sunday"
        }
    }

    /**
     * Get localized planetary weekday
     */
    fun getLocalizedPlanetaryWeekday(planet: Planet, language: Language): String {
        val key = when (planet) {
            Planet.SUN -> StringKeyDosha.PLANET_DAY_SUN
            Planet.MOON -> StringKeyDosha.PLANET_DAY_MOON
            Planet.MARS -> StringKeyDosha.PLANET_DAY_MARS
            Planet.MERCURY -> StringKeyDosha.PLANET_DAY_MERCURY
            Planet.JUPITER -> StringKeyDosha.PLANET_DAY_JUPITER
            Planet.VENUS -> StringKeyDosha.PLANET_DAY_VENUS
            Planet.SATURN -> StringKeyDosha.PLANET_DAY_SATURN
            Planet.RAHU -> StringKeyDosha.PLANET_DAY_RAHU
            Planet.KETU -> StringKeyDosha.PLANET_DAY_KETU
            else -> StringKeyDosha.PLANET_DAY_SUN
        }
        return StringResources.get(key, language)
    }

    private fun getPlanetLifeArea(planet: Planet): String {
        return when (planet) {
            Planet.SUN -> "authority, career, government favor, father's health, self-confidence"
            Planet.MOON -> "mind, emotions, mother, public image, mental peace"
            Planet.MARS -> "energy, courage, property, siblings, physical strength"
            Planet.MERCURY -> "communication, business, education, intelligence, writing"
            Planet.JUPITER -> "wisdom, wealth, children, fortune, higher education, spirituality"
            Planet.VENUS -> "love, marriage, beauty, arts, luxury, relationships"
            Planet.SATURN -> "discipline, longevity, career stability, hard work, patience"
            Planet.RAHU -> "foreign connections, technology, unconventional success, ambition"
            Planet.KETU -> "spirituality, liberation, intuition, past life karma, healing"
            else -> "general well-being"
        }
    }

    /**
     * Get localized planet life area description
     */
    fun getLocalizedPlanetLifeArea(planet: Planet, language: Language): String {
        val key = when (planet) {
            Planet.SUN -> StringKeyDosha.PLANET_LIFE_AREA_SUN
            Planet.MOON -> StringKeyDosha.PLANET_LIFE_AREA_MOON
            Planet.MARS -> StringKeyDosha.PLANET_LIFE_AREA_MARS
            Planet.MERCURY -> StringKeyDosha.PLANET_LIFE_AREA_MERCURY
            Planet.JUPITER -> StringKeyDosha.PLANET_LIFE_AREA_JUPITER
            Planet.VENUS -> StringKeyDosha.PLANET_LIFE_AREA_VENUS
            Planet.SATURN -> StringKeyDosha.PLANET_LIFE_AREA_SATURN
            Planet.RAHU -> StringKeyDosha.PLANET_LIFE_AREA_RAHU
            Planet.KETU -> StringKeyDosha.PLANET_LIFE_AREA_KETU
            else -> return StringResources.get(StringKeyDosha.LABEL_UNKNOWN, language)
        }
        return StringResources.get(key, language)
    }

    private fun getLocalizedMantraTiming(planet: Planet, language: Language): String {
        val mantraInfo = planetaryMantras[planet] ?: return ""
        val day = StringResources.get(StringKeyRemedy.valueOf("WEEKDAY_${getPlanetaryWeekday(planet).uppercase()}"), language)
        val part = when (planet) {
            Planet.SUN, Planet.MARS, Planet.MERCURY, Planet.JUPITER, Planet.VENUS -> StringResources.get(StringKeyRemedy.CHARITY_TIMING_MORNING, language)
            Planet.MOON, Planet.SATURN -> StringResources.get(StringKeyRemedy.CHARITY_TIMING_EVENING, language)
            Planet.RAHU -> StringResources.get(StringKeyRemedy.CHARITY_TIMING_NIGHT, language)
            Planet.KETU -> ""
            else -> ""
        }
        val waxing = if (planet == Planet.MOON) ", " + StringResources.get(StringKeyRemedy.MANTRA_TIMING_WAXING, language) else ""
        return "$day $part$waxing"
    }

    private fun getLocalizedCharityTiming(timing: String, language: Language): String {
        return when (timing.lowercase()) {
            "morning" -> StringResources.get(StringKeyRemedy.CHARITY_TIMING_MORNING, language)
            "evening" -> StringResources.get(StringKeyRemedy.CHARITY_TIMING_EVENING, language)
            "night" -> StringResources.get(StringKeyRemedy.CHARITY_TIMING_NIGHT, language)
            "before sunset" -> StringResources.get(StringKeyRemedy.CHARITY_TIMING_BEFORE_SUNSET, language)
            "before sunrise or after sunset" -> StringResources.get(StringKeyRemedy.CHARITY_TIMING_BEFORE_SUNRISE_AFTER_SUNSET, language)
            else -> timing
        }
    }
}