package com.astro.storm.data.localization



/**
 * Divisional charts, Shadbala, and Dosha (Sade Sati, Manglik, Pitra) string keys
 * Part 4 of 4 split enums to avoid JVM method size limit
 */
enum class StringKeyDosha(override val en: String, override val ne: String) : StringKeyInterface {
    GUNA_SATTVA("Sattva", "सत्त्व"),

    // ============================================
    // DIVISIONAL CHART TITLES (For DivisionalChartCalculator)
    // ============================================
    VARGA_D1_TITLE("Rashi (D1)", "राशि (D1)"),
    VARGA_D1_DESC("Physical Body, General Life", "भौतिक शरीर, सामान्य जीवन"),
    VARGA_D2_TITLE("Hora (D2)", "होरा (D2)"),
    VARGA_D2_DESC("Wealth, Prosperity", "धन, समृद्धि"),
    VARGA_D3_TITLE("Drekkana (D3)", "द्रेक्काण (D3)"),
    VARGA_D3_DESC("Siblings, Courage", "भाइबहिनी, साहस"),
    VARGA_D4_TITLE("Chaturthamsa (D4)", "चतुर्थांश (D4)"),
    VARGA_D4_DESC("Fortune, Property", "भाग्य, सम्पत्ति"),
    VARGA_D7_TITLE("Saptamsa (D7)", "सप्तमांश (D7)"),
    VARGA_D7_DESC("Children, Progeny", "सन्तान"),
    VARGA_D9_TITLE("Navamsa (D9)", "नवमांश (D9)"),
    VARGA_D9_DESC("Marriage, Dharma", "विवाह, धर्म"),
    VARGA_D10_TITLE("Dasamsa (D10)", "दशमांश (D10)"),
    VARGA_D10_DESC("Career, Profession", "क्यारियर, पेशा"),
    VARGA_D12_TITLE("Dwadasamsa (D12)", "द्वादशांश (D12)"),
    VARGA_D12_DESC("Parents, Ancestry", "आमाबुवा, पुर्खा"),
    VARGA_D16_TITLE("Shodasamsa (D16)", "षोडशांश (D16)"),
    VARGA_D16_DESC("Vehicles, Pleasures", "सवारी, आनन्द"),
    VARGA_D20_TITLE("Vimsamsa (D20)", "विंशांश (D20)"),
    VARGA_D20_DESC("Spiritual Life", "आध्यात्मिक जीवन"),
    VARGA_D24_TITLE("Siddhamsa (D24)", "सिद्धांश (D24)"),
    VARGA_D24_DESC("Education, Learning", "शिक्षा, सिकाइ"),
    VARGA_D27_TITLE("Bhamsa (D27)", "भांश (D27)"),
    VARGA_D27_DESC("Strength, Weakness", "बल, कमजोरी"),
    VARGA_D30_TITLE("Trimsamsa (D30)", "त्रिंशांश (D30)"),
    VARGA_D30_DESC("Evils, Misfortunes", "अशुभ, दुर्भाग्य"),
    VARGA_D40_TITLE("Khavedamsa (D40)", "खवेदांश (D40)"),
    VARGA_D40_DESC("Auspicious/Inauspicious Effects", "शुभाशुभ प्रभावहरू"),
    VARGA_D45_TITLE("Akshavedamsa (D45)", "अक्षवेदांश (D45)"),
    VARGA_D45_DESC("General Indications", "सामान्य सङ्केतहरू"),
    VARGA_D60_TITLE("Shashtiamsa (D60)", "षष्ट्यंश (D60)"),
    VARGA_D60_DESC("Past Life Karma", "पूर्वजन्मको कर्म"),
    VARGA_PLANETARY_POSITIONS("PLANETARY POSITIONS", "ग्रह स्थितिहरू"),

    // ============================================
    // HOROSCOPE CALCULATOR (Life Areas & Themes)
    // ============================================
    LIFE_AREA_SPIRITUAL("Spiritual Growth", "आध्यात्मिक वृद्धि"),

    // Weekly Themes
    THEME_BALANCE("Balance", "सन्तुलन"),
    THEME_DYNAMIC_ACTION("Dynamic Action", "गतिशील कार्य"),
    THEME_PRACTICAL_PROGRESS("Practical Progress", "व्यावहारिक प्रगति"),
    THEME_SOCIAL_CONNECTIONS("Social Connections", "सामाजिक सम्बन्धहरू"),
    THEME_EMOTIONAL_INSIGHT("Emotional Insight", "भावनात्मक अन्तर्दृष्टि"),
    THEME_SELF_EXPRESSION("Self-Expression", "आत्म-अभिव्यक्ति"),
    THEME_TRANSFORMATION("Transformation", "रूपान्तरण"),
    THEME_SPIRITUAL_LIBERATION("Spiritual Liberation", "आध्यात्मिक मुक्ति"),

    // Element-based advice
    ADVICE_FIRE_ELEMENT("Take bold action and express yourself confidently.", "साहसिक कदम चाल्नुहोस् र आत्मविश्वासका साथ आफूलाई अभिव्यक्त गर्नुहोस्।"),
    ADVICE_EARTH_ELEMENT("Focus on practical matters and material progress.", "व्यावहारिक मामिलाहरू र भौतिक प्रगतिमा ध्यान दिनुहोस्।"),
    ADVICE_AIR_ELEMENT("Engage in social activities and intellectual pursuits.", "सामाजिक क्रियाकलापहरू र बौद्धिक खोजमा संलग्न हुनुहोस्।"),
    ADVICE_WATER_ELEMENT("Trust your intuition and honor your emotions.", "आफ्नो अन्तर्ज्ञानमा विश्वास गर्नुहोस् र भावनाहरूलाई सम्मान गर्नुहोस्।"),

    // Week types
    WEEK_OPPORTUNITIES("Week of Opportunities", "अवसरहरूको हप्ता"),
    WEEK_STEADY_PROGRESS("Steady Progress", "स्थिर प्रगति"),
    WEEK_MINDFUL_NAVIGATION("Mindful Navigation", "सचेत नेभिगेसन"),

    // Time periods
    TIME_MORNING("Morning hours", "बिहानको समय"),
    TIME_AFTERNOON("Afternoon hours", "दिउँसोको समय"),
    TIME_EVENING("Evening hours", "साँझको समय"),

    // ============================================
    // MATCHMAKING CALCULATOR (Additional Vashya types)
    // ============================================
    VASHYA_QUADRUPED("Quadruped", "चतुष्पाद"),
    VASHYA_HUMAN("Human", "मनुष्य"),
    VASHYA_AQUATIC("Aquatic", "जलचर"),
    VASHYA_WILD("Wild", "वन्य"),
    VASHYA_INSECT("Insect", "कीट"),

    // Compatibility ratings (additional)
    COMPAT_BELOW_AVG("Below Average", "औसतभन्दा कम"),
    COMPAT_BELOW_AVG_DESC("Caution advised. Several compatibility issues that need addressing through remedies and counseling.", "सावधानी सल्लाह दिइएको। उपाय र परामर्शबाट सम्बोधन गर्नुपर्ने धेरै अनुकूलता समस्याहरू।"),

    // Yogini planet associations (additional)
    YOGINI_CHANDRA("Chandra (Moon)", "चन्द्र"),
    YOGINI_SURYA("Surya (Sun)", "सूर्य"),
    YOGINI_GURU("Guru (Jupiter)", "गुरु"),
    YOGINI_MANGAL("Mangal (Mars)", "मंगल"),
    YOGINI_BUDHA("Budha (Mercury)", "बुध"),
    YOGINI_SHANI("Shani (Saturn)", "शनि"),
    YOGINI_SHUKRA("Shukra (Venus)", "शुक्र"),
    YOGINI_RAHU("Rahu", "राहु"),

    // ============================================
    // REPORT HEADERS
    // ============================================
    REPORT_VEDIC_REMEDIES("VEDIC ASTROLOGY REMEDIES REPORT", "वैदिक ज्योतिष उपाय प्रतिवेदन"),
    REPORT_PLANETS_NEEDING_ATTENTION("PLANETS REQUIRING ATTENTION:", "ध्यान आवश्यक ग्रहहरू:"),

    // ============================================
    // ERROR MESSAGES (Additional for GeocodingService)
    // ============================================
    ERROR_QUERY_MIN_CHARS("Query must be at least 2 characters", "खोजी कम्तीमा २ वर्णको हुनुपर्छ"),
    ERROR_CONNECTION_TIMEOUT("Connection timeout. Please check your internet.", "जडान समय समाप्त। कृपया आफ्नो इन्टरनेट जाँच गर्नुहोस्।"),
    ERROR_NO_INTERNET("No internet connection.", "इन्टरनेट जडान छैन।"),
    ERROR_UNKNOWN("Unknown error", "अज्ञात त्रुटि"),

    // ============================================
    // GENERAL UI LABELS
    // ============================================
    LABEL_UNKNOWN("Unknown", "अज्ञात"),
    LABEL_REQUIRED("Required:", "आवश्यक:"),
    LABEL_RUPAS("Rupas", "रूपा"),
    LABEL_PERCENT_REQUIRED("% of required", "आवश्यकको %"),
    LABEL_TODAY("Today", "आज"),
    LABEL_PREVIOUS_MONTH("Previous month", "अघिल्लो महिना"),
    LABEL_NEXT_MONTH("Next month", "अर्को महिना"),
    LABEL_AD("AD", "ई.सं."),
    LABEL_BS("BS", "बि.सं."),

    // Weekday abbreviations
    WEEKDAY_SU("Su", "आ"),
    WEEKDAY_MO("Mo", "सो"),
    WEEKDAY_TU("Tu", "मं"),
    WEEKDAY_WE("We", "बु"),
    WEEKDAY_TH("Th", "बि"),
    WEEKDAY_FR("Fr", "शु"),
    WEEKDAY_SA("Sa", "श"),

    // ============================================
    // COLORS (For Horoscope advice)
    // ============================================
    COLOR_RED_ORANGE_GOLD("Red, Orange, or Gold", "रातो, सुन्तला, वा सुनौलो"),
    COLOR_GREEN_BROWN_WHITE("Green, Brown, or White", "हरियो, खैरो, वा सेतो"),
    COLOR_BLUE_LIGHT_SILVER("Blue, Light Blue, or Silver", "निलो, हल्का निलो, वा चाँदी"),
    COLOR_WHITE_CREAM_SEA("White, Cream, or Sea Green", "सेतो, क्रिम, वा समुद्री हरियो"),

    // ============================================
    // GEMSTONES
    // ============================================
    GEMSTONE_RUBY("Ruby", "माणिक"),
    GEMSTONE_PEARL("Pearl", "मोती"),
    GEMSTONE_RED_CORAL("Red Coral", "मूंगा"),
    GEMSTONE_EMERALD("Emerald", "पन्ना"),
    GEMSTONE_YELLOW_SAPPHIRE("Yellow Sapphire", "पुखराज"),
    GEMSTONE_DIAMOND("Diamond/White Sapphire", "हीरा/सेतो नीलम"),
    GEMSTONE_BLUE_SAPPHIRE("Blue Sapphire", "नीलम"),
    GEMSTONE_HESSONITE("Hessonite", "गोमेद"),
    GEMSTONE_CATS_EYE("Cat's Eye", "लहसुनिया"),

    // ============================================
    // MOON PHASES
    // ============================================
    MOON_FIRST_QUARTER("First Quarter Moon", "पहिलो चौथाई चन्द्रमा"),
    MOON_FIRST_QUARTER_DESC("Good for taking action", "कार्य गर्नको लागि राम्रो"),
    MOON_FULL("Full Moon", "पूर्णिमा"),
    MOON_FULL_DESC("Emotional peak - completion energy", "भावनात्मक शिखर - पूर्णता ऊर्जा"),

    // ============================================
    // PRASHNA HOUSE SIGNIFICATIONS
    // ============================================
    PRASHNA_HOUSE_1_NAME("Lagna/Querent", "लग्न/प्रश्नकर्ता"),
    PRASHNA_HOUSE_2_NAME("Dhana", "धन"),
    PRASHNA_HOUSE_3_NAME("Sahaja", "सहज"),
    PRASHNA_HOUSE_4_NAME("Sukha", "सुख"),
    PRASHNA_HOUSE_5_NAME("Putra", "पुत्र"),
    PRASHNA_HOUSE_6_NAME("Ripu", "रिपु"),
    PRASHNA_HOUSE_7_NAME("Kalatra", "कलत्र"),
    PRASHNA_HOUSE_8_NAME("Ayu", "आयु"),
    PRASHNA_HOUSE_9_NAME("Dharma", "धर्म"),
    PRASHNA_HOUSE_10_NAME("Karma", "कर्म"),
    PRASHNA_HOUSE_11_NAME("Labha", "लाभ"),
    PRASHNA_HOUSE_12_NAME("Vyaya", "व्यय"),

    // Body parts for Prashna
    BODY_PART_HEAD("Head", "टाउको"),
    BODY_PART_FACE("Face/Mouth", "अनुहार/मुख"),
    BODY_PART_ARMS("Arms/Shoulders", "पाखुरा/काँध"),
    BODY_PART_CHEST("Chest", "छाती"),
    BODY_PART_UPPER_ABDOMEN("Upper Abdomen", "माथिल्लो पेट"),
    BODY_PART_LOWER_ABDOMEN("Lower Abdomen", "तल्लो पेट"),
    BODY_PART_BELOW_NAVEL("Below Navel", "नाभिमुनि"),
    BODY_PART_REPRODUCTIVE("Reproductive organs", "प्रजनन अंगहरू"),
    BODY_PART_THIGHS("Thighs", "जाँघ"),
    BODY_PART_KNEES("Knees", "घुँडा"),
    BODY_PART_CALVES("Calves/Shins", "पिँडौला"),
    BODY_PART_FEET("Feet", "खुट्टा"),

    // Prashna terms
    PRASHNA_MOOK("Mook (Dumb) Prashna", "मूक प्रश्न"),
    PRASHNA_ARUDHA("Arudha Lagna", "आरूढ लग्न"),

    // ============================================
    // REMEDIES REPORT LABELS
    // ============================================
    REPORT_NAME_LABEL("Name:", "नाम:"),
    REPORT_ASCENDANT_LABEL("Ascendant:", "लग्न:"),
    REPORT_MANTRA_LABEL("Mantra:", "मन्त्र:"),

    // ============================================
    // PLANET LIFE AREAS (For remedies)
    // ============================================
    PLANET_LIFE_AREA_SUN("authority, career, government favor, father's health, self-confidence", "अधिकार, करियर, सरकारी कृपा, बुबाको स्वास्थ्य, आत्मविश्वास"),
    PLANET_LIFE_AREA_MOON("mental peace, mother's health, emotional stability, public relations", "मानसिक शान्ति, आमाको स्वास्थ्य, भावनात्मक स्थिरता, जनसम्पर्क"),
    PLANET_LIFE_AREA_MARS("courage, siblings, property matters, physical strength, competition", "साहस, भाइबहिनी, सम्पत्ति मामिला, शारीरिक शक्ति, प्रतिस्पर्धा"),
    PLANET_LIFE_AREA_MERCURY("intellect, communication, business, education, nervous system", "बुद्धि, सञ्चार, व्यापार, शिक्षा, स्नायु प्रणाली"),
    PLANET_LIFE_AREA_JUPITER("wisdom, children, wealth, spirituality, teachers, dharma", "ज्ञान, सन्तान, धन, आध्यात्मिकता, गुरु, धर्म"),
    PLANET_LIFE_AREA_VENUS("marriage, love, luxury, art, vehicles, pleasure", "विवाह, प्रेम, विलासिता, कला, सवारी, आनन्द"),
    PLANET_LIFE_AREA_SATURN("longevity, service, discipline, karma, delays, chronic issues", "दीर्घायु, सेवा, अनुशासन, कर्म, ढिलाइ, दीर्घकालीन समस्या"),
    PLANET_LIFE_AREA_RAHU("foreign connections, unconventional success, material desires", "विदेशी सम्बन्ध, अपरम्परागत सफलता, भौतिक इच्छा"),
    PLANET_LIFE_AREA_KETU("spirituality, liberation, past karma, psychic abilities", "आध्यात्मिकता, मोक्ष, पूर्व कर्म, मानसिक क्षमता"),

    // ============================================
    // PLANETARY WEEKDAYS
    // ============================================
    PLANET_DAY_SUN("Sunday", "आइतबार"),
    PLANET_DAY_MOON("Monday", "सोमबार"),
    PLANET_DAY_MARS("Tuesday", "मंगलबार"),
    PLANET_DAY_MERCURY("Wednesday", "बुधबार"),
    PLANET_DAY_JUPITER("Thursday", "बिहिबार"),
    PLANET_DAY_VENUS("Friday", "शुक्रबार"),
    PLANET_DAY_SATURN("Saturday", "शनिबार"),
    PLANET_DAY_RAHU("Saturday", "शनिबार"),
    PLANET_DAY_KETU("Tuesday", "मंगलबार"),

    // ============================================
    // SYNASTRY / CHART COMPARISON
    // ============================================
    SYNASTRY_TITLE("Chart Comparison", "कुण्डली तुलना"),
    SYNASTRY_SUBTITLE("Synastry Analysis", "सिनेस्ट्री विश्लेषण"),
    SYNASTRY_SELECT_CHARTS("Select Charts to Compare", "तुलना गर्न कुण्डलीहरू छान्नुहोस्"),
    SYNASTRY_CHART_1("Chart 1", "कुण्डली १"),
    SYNASTRY_CHART_2("Chart 2", "कुण्डली २"),
    SYNASTRY_OVERVIEW("Overview", "अवलोकन"),
    SYNASTRY_ASPECTS("Inter-Aspects", "अन्तर-दृष्टिहरू"),
    SYNASTRY_HOUSES("House Overlays", "भाव ओभरले"),
    SYNASTRY_COMPATIBILITY("Compatibility", "अनुकूलता"),
    SYNASTRY_NO_ASPECTS("No significant aspects found", "कुनै महत्त्वपूर्ण दृष्टि फेला परेन"),
    SYNASTRY_OVERALL_SCORE("Overall Compatibility Score", "समग्र अनुकूलता स्कोर"),
    SYNASTRY_HARMONIOUS("Harmonious Aspects", "सामञ्जस्यपूर्ण दृष्टिहरू"),
    SYNASTRY_CHALLENGING("Challenging Aspects", "चुनौतीपूर्ण दृष्टिहरू"),
    SYNASTRY_PLANET_CONNECTIONS("Planetary Connections", "ग्रह सम्बन्धहरू"),
    SYNASTRY_HOUSE_INFLUENCE("House Influences", "भाव प्रभावहरू"),
    SYNASTRY_PLANET_IN_HOUSE("%s in %s's House %d", "%s को भाव %d मा %s"),
    SYNASTRY_ASPECT_ORB("Orb: %.1fÂ°", "ओर्ब: %.1f°"),
    SYNASTRY_APPLYING("Applying", "निकट आउँदै"),
    SYNASTRY_SEPARATING("Separating", "टाढा जाँदै"),
    SYNASTRY_CONJUNCTION("Conjunction", "युति"),
    SYNASTRY_OPPOSITION("Opposition", "प्रतिपक्ष"),
    SYNASTRY_TRINE("Trine", "त्रिकोण"),
    SYNASTRY_SQUARE("Square", "चतुर्थांश"),
    SYNASTRY_SEXTILE("Sextile", "षड्भाग"),
    SYNASTRY_STRONG("Strong", "बलियो"),
    SYNASTRY_MODERATE("Moderate", "मध्यम"),
    SYNASTRY_WEAK("Weak", "कमजोर"),
    SYNASTRY_KEY_ASPECTS("Key Synastry Aspects", "मुख्य सिनेस्ट्री दृष्टिहरू"),
    SYNASTRY_EMOTIONAL_BOND("Emotional Bond", "भावनात्मक बन्धन"),
    SYNASTRY_COMMUNICATION("Communication", "सञ्चार"),
    SYNASTRY_ROMANCE("Romance & Attraction", "रोमान्स र आकर्षण"),
    SYNASTRY_STABILITY("Long-term Stability", "दीर्घकालीन स्थिरता"),
    SYNASTRY_GROWTH("Growth & Evolution", "वृद्धि र विकास"),
    SYNASTRY_INFO_TITLE("About Synastry", "सिनेस्ट्रीको बारेमा"),
    SYNASTRY_INFO_DESC("Synastry compares two birth charts to analyze relationship dynamics, compatibility, and areas of harmony or challenge between individuals.", "सिनेस्ट्रीले दुई जन्म कुण्डलीहरू तुलना गरेर व्यक्तिहरू बीचको सम्बन्ध गतिशीलता, अनुकूलता, र सामञ्जस्य वा चुनौतीका क्षेत्रहरू विश्लेषण गर्दछ।"),
    SYNASTRY_SWAP("Swap Charts", "कुण्डलीहरू स्वाप गर्नुहोस्"),
    SYNASTRY_CLEAR("Clear Selection", "छनौट खाली गर्नुहोस्"),
    SYNASTRY_CALCULATE("Analyze Synastry", "सिनेस्ट्री विश्लेषण गर्नुहोस्"),
    SYNASTRY_ANALYZING("Analyzing synastry...", "सिनेस्ट्री विश्लेषण गर्दै..."),
    SYNASTRY_SUN_MOON("Sun-Moon Aspects", "सूर्य-चन्द्र दृष्टिहरू"),
    SYNASTRY_VENUS_MARS("Venus-Mars Aspects", "शुक्र-मंगल दृष्टिहरू"),
    SYNASTRY_ASCENDANT("Ascendant Connections", "लग्न सम्बन्धहरू"),
    SYNASTRY_MUTUAL_ASPECTS("Mutual Aspects", "पारस्परिक दृष्टिहरू"),
    SYNASTRY_SELECT_BOTH("Please select both charts to compare", "कृपया तुलना गर्न दुवै कुण्डली छान्नुहोस्"),
    SYNASTRY_QUINCUNX("Quincunx", "क्विनकनक्स"),
    SYNASTRY_SEMI_SEXTILE("Semi-Sextile", "अर्ध-षड्भाग"),
    SYNASTRY_INTERPRET_HARMONIOUS("%1\$s and %2\$s work together harmoniously, creating mutual understanding and support.", "%1\$s र %2\$s सामञ्जस्यपूर्ण रूपमा सँगै काम गर्छन्, जसले आपसी समझदारी र समर्थन सिर्जना गर्दछ।"),
    SYNASTRY_INTERPRET_CHALLENGING("%1\$s and %2\$s create tension that requires conscious effort to integrate.", "%1\$s र %2\$s ले तनाव सिर्जना गर्दछन् जसलाई एकीकृत गर्न सचेत प्रयासको आवश्यक पर्दछ।"),
    SYNASTRY_INTERPRET_MAJOR("%1\$s and %2\$s are closely connected, amplifying each other's energies.", "%1\$s र %2\$s नजिकबाट जोडिएका छन्, एक अर्काको ऊर्जालाई बढाउँदै।"),
    SYNASTRY_INTERPRET_MINOR("%1\$s and %2\$s have a subtle connection that adds nuance to the relationship.", "%1\$s र %2\$s बीच एक सूक्ष्म सम्बन्ध छ जसले सम्बन्धमा नयाँ पक्ष थप्दछ।"),
    SYNASTRY_INTERPRET_ASCENDANT("%1\$s conjunct Person %2\$d's Ascendant creates a strong personal connection.", "%1\$s व्यक्ति %2\$d को लग्नमा हुनुले एक बलियो व्यक्तिगत सम्बन्ध सिर्जना गर्दछ।"),
    SYNASTRY_INTERPRET_OVERLAY("Person %1\$d's %2\$s falls in the %3\$dth house, influencing %4\$s.", "व्यक्ति %1\$d को %2\$s %3\$d भावमा पर्दछ, जसले %4\$s लाई प्रभावित गर्दछ।"),
        SYNASTRY_DESC_EMOTIONAL("Emotional understanding and nurturing", "भावनात्मक समझदारी र पोषण"),
    SYNASTRY_DESC_ROMANCE("Physical attraction and passion", "शारीरिक आकर्षण र जुनून"),
    SYNASTRY_DESC_COMMUNICATION("Mental connection and dialogue", "मानसिक सम्बन्ध र संवाद"),
    SYNASTRY_DESC_STABILITY("Commitment and endurance", "प्रतिबद्धता र धैर्यता"),
    SYNASTRY_DESC_GROWTH("Mutual expansion and learning", "आपसी विस्तार र सिकाइ"),
    SYNASTRY_FINDING_ASPECT("Strong %1\$s between %2\$s and %3\$s", "%2\$s र %3\$s बीच बलियो %1\$s"),
    SYNASTRY_FINDING_HOUSE("%1\$s activates the %2\$dth house of %3\$s", "%1\$s ले %3\$s को %2\$d भावलाई सक्रिय गर्दछ"),
    SYNASTRY_CALC_FAILED("Calculation failed", "गणना असफल भयो"),
    SYNASTRY_AVAILABLE_CHARTS("%d charts available", "%d कुण्डलीहरू उपलब्ध छन्"),
    SYNASTRY_LIFE_AREA_GENERAL("General", "सामान्य"),

    // ============================================
    // NAKSHATRA ANALYSIS
    // ============================================
    NAKSHATRA_TITLE("Nakshatra Analysis", "नक्षत्र विश्लेषण"),
    NAKSHATRA_SUBTITLE("Lunar Mansion Analysis", "चन्द्र भवन विश्लेषण"),
    NAKSHATRA_OVERVIEW("Overview", "अवलोकन"),
    NAKSHATRA_DETAILS("Details", "विवरणहरू"),
    NAKSHATRA_COMPATIBILITY("Compatibility", "अनुकूलता"),
    NAKSHATRA_REMEDIES("Remedies", "उपायहरू"),
    NAKSHATRA_BIRTH_STAR("Birth Nakshatra", "जन्म नक्षत्र"),
    NAKSHATRA_MOON_POSITION("Moon Nakshatra", "चन्द्र नक्षत्र"),
    NAKSHATRA_RULER("Ruling Planet", "स्वामी ग्रह"),
    NAKSHATRA_ELEMENT("Element", "तत्व"),
    NAKSHATRA_QUALITY("Quality", "गुण"),
    NAKSHATRA_CASTE("Caste", "वर्ण"),
    NAKSHATRA_DIRECTION("Direction", "दिशा"),
    NAKSHATRA_BODY_PART("Body Part", "शरीरको अंग"),
    NAKSHATRA_DOSHA("Dosha", "दोष"),
    NAKSHATRA_FAVORABLE_DAYS("Favorable Days", "अनुकूल दिनहरू"),
    NAKSHATRA_LUCKY_NUMBERS("Lucky Numbers", "भाग्यशाली अंकहरू"),
    NAKSHATRA_LUCKY_COLORS("Lucky Colors", "भाग्यशाली रंगहरू"),
    NAKSHATRA_LUCKY_STONES("Lucky Gemstones", "भाग्यशाली रत्नहरू"),
    NAKSHATRA_CHARACTERISTICS("Characteristics", "विशेषताहरू"),
    NAKSHATRA_STRENGTHS("Strengths", "शक्तिहरू"),
    NAKSHATRA_WEAKNESSES("Weaknesses", "कमजोरीहरू"),
    NAKSHATRA_CAREER("Career Aptitude", "क्यारियर योग्यता"),
    NAKSHATRA_HEALTH("Health Tendencies", "स्वास्थ्य प्रवृत्तिहरू"),
    NAKSHATRA_RELATIONSHIP("Relationship Style", "सम्बन्ध शैली"),
    NAKSHATRA_SPIRITUAL("Spiritual Path", "आध्यात्मिक मार्ग"),
    NAKSHATRA_MANTRA("Nakshatra Mantra", "नक्षत्र मन्त्र"),
    NAKSHATRA_INFO_TITLE("About Nakshatras", "नक्षत्रहरूको बारेमा"),
    NAKSHATRA_INFO_DESC("Nakshatras are the 27 lunar mansions in Vedic astrology, each spanning 13Â°20' of the zodiac. They reveal deeper psychological patterns and spiritual tendencies.", "नक्षत्रहरू वैदिक ज्योतिषमा २७ चन्द्र भवनहरू हुन्, प्रत्येक राशिचक्रको १३°२०' फैलिएको। तिनीहरूले गहिरो मनोवैज्ञानिक ढाँचा र आध्यात्मिक प्रवृत्तिहरू प्रकट गर्छन्।"),
    NAKSHATRA_ALL_PLANETS("Planetary Nakshatras", "ग्रह नक्षत्रहरू"),
    NAKSHATRA_DASHA_LORD("Dasha Lord", "दशा स्वामी"),
    NAKSHATRA_SPAN("Nakshatra Span", "नक्षत्र विस्तार"),
    NAKSHATRA_DEGREE_IN("Degree in Nakshatra", "नक्षत्रमा अंश"),
    NAKSHATRA_TARABALA("Tarabala Analysis", "ताराबल विश्लेषण"),
    NAKSHATRA_CHANDRABALA("Chandrabala", "चन्द्रबल"),
    NAKSHATRA_COMPATIBLE_WITH("Compatible with", "अनुकूल"),
    NAKSHATRA_INCOMPATIBLE_WITH("Incompatible with", "अनुकूल छैन"),
    NAKSHATRA_VEDHA_PAIRS("Vedha Pairs", "वेध जोडीहरू"),
    NAKSHATRA_RAJJU_TYPE("Rajju Type", "रज्जु प्रकार"),

    // ============================================
    // SHADBALA ANALYSIS
    // ============================================
    SHADBALA_TITLE("Shadbala", "षड्बल"),
    SHADBALA_SUBTITLE("Six-fold Planetary Strength", "छवटा ग्रह शक्ति"),
    SHADBALA_OVERVIEW("Overview", "अवलोकन"),
    SHADBALA_DETAILS("Detailed Analysis", "विस्तृत विश्लेषण"),
    SHADBALA_COMPARISON("Comparison", "तुलना"),
    SHADBALA_TOTAL_STRENGTH("Total Strength", "कुल शक्ति"),
    SHADBALA_RUPAS("Rupas", "रूपा"),
    SHADBALA_VIRUPAS("Virupas", "विरूपा"),
    SHADBALA_REQUIRED("Required", "आवश्यक"),
    SHADBALA_PERCENTAGE("Percentage", "प्रतिशत"),
    SHADBALA_RATING("Rating", "मूल्यांकन"),
    SHADBALA_STRONGEST_PLANET("Strongest Planet", "सबैभन्दा बलियो ग्रह"),
    SHADBALA_WEAKEST_PLANET("Weakest Planet", "सबैभन्दा कमजोर ग्रह"),
    SHADBALA_OVERALL_STRENGTH("Overall Chart Strength", "समग्र कुण्डली शक्ति"),
    SHADBALA_STRONG_COUNT("%d planets above required", "%d ग्रह आवश्यकताभन्दा माथि"),
    SHADBALA_WEAK_COUNT("%d planets below required", "%d ग्रह आवश्यकताभन्दा तल"),
    SHADBALA_STHANA_BALA("Sthana Bala", "स्थान बल"),
    SHADBALA_STHANA_BALA_DESC("Positional Strength", "स्थितिगत शक्ति"),
    SHADBALA_DIG_BALA("Dig Bala", "दिग् बल"),
    SHADBALA_DIG_BALA_DESC("Directional Strength", "दिशागत शक्ति"),
    SHADBALA_KALA_BALA("Kala Bala", "काल बल"),
    SHADBALA_KALA_BALA_DESC("Temporal Strength", "समयगत शक्ति"),
    SHADBALA_CHESTA_BALA("Chesta Bala", "चेष्टा बल"),
    SHADBALA_CHESTA_BALA_DESC("Motional Strength", "गतिशील शक्ति"),
    SHADBALA_NAISARGIKA_BALA("Naisargika Bala", "नैसर्गिक बल"),
    SHADBALA_NAISARGIKA_BALA_DESC("Natural Strength", "प्राकृतिक शक्ति"),
    SHADBALA_DRIK_BALA("Drik Bala", "दृक् बल"),
    SHADBALA_DRIK_BALA_DESC("Aspectual Strength", "दृष्टिगत शक्ति"),
    SHADBALA_UCCHA_BALA("Uccha Bala", "उच्च बल"),
    SHADBALA_SAPTAVARGAJA_BALA("Saptavargaja Bala", "सप्तवर्गज बल"),
    SHADBALA_OJHAYUGMA_BALA("Ojhayugma Bala", "ओझायुग्म बल"),
    SHADBALA_KENDRADI_BALA("Kendradi Bala", "केन्द्रादि बल"),
    SHADBALA_DREKKANA_BALA("Drekkana Bala", "द्रेक्काण बल"),
    SHADBALA_NATHONNATHA_BALA("Nathonnatha Bala", "नथोन्नथ बल"),
    SHADBALA_PAKSHA_BALA("Paksha Bala", "पक्ष बल"),
    SHADBALA_TRIBHAGA_BALA("Tribhaga Bala", "त्रिभाग बल"),
    SHADBALA_HORA_BALA("Hora/Dina/Masa/Varsha", "होरा/दिन/मास/वर्ष"),
    SHADBALA_AYANA_BALA("Ayana Bala", "अयन बल"),
    SHADBALA_YUDDHA_BALA("Yuddha Bala", "युद्ध बल"),
    SHADBALA_INFO_TITLE("About Shadbala", "षड्बलको बारेमा"),
    SHADBALA_INFO_DESC("Shadbala is the six-fold strength calculation system in Vedic astrology that determines a planet's capacity to deliver its significations. Each planet needs to meet a minimum threshold to be considered functionally strong.", "षड्बल वैदिक ज्योतिषमा छवटा शक्ति गणना प्रणाली हो जसले ग्रहको आफ्नो संकेतहरू प्रदान गर्ने क्षमता निर्धारण गर्दछ। प्रत्येक ग्रहलाई कार्यात्मक रूपमा बलियो मानिनको लागि न्यूनतम सीमा पूरा गर्नुपर्छ।"),
    SHADBALA_INFO_INTRO("The six sources of strength:", "शक्तिका छवटा स्रोतहरू:"),
    SHADBALA_INFO_ITEM_1("1. Sthana Bala - Positional strength based on exaltation, own sign, etc.", "१. स्थान बल - उच्चता, स्वराशि, आदिमा आधारित स्थितिगत शक्ति।"),
    SHADBALA_INFO_ITEM_2("2. Dig Bala - Directional strength based on house placement", "२. दिग् बल - भाव स्थितिमा आधारित दिशागत शक्ति"),
    SHADBALA_INFO_ITEM_3("3. Kala Bala - Temporal strength from time of birth", "३. काल बल - जन्म समयबाट समयगत शक्ति"),
    SHADBALA_INFO_ITEM_4("4. Chesta Bala - Motional strength from planetary motion", "४. चेष्टा बल - ग्रह गतिबाट गतिशील शक्ति"),
    SHADBALA_INFO_ITEM_5("5. Naisargika Bala - Natural inherent strength", "५. नैसर्गिक बल - प्राकृतिक अन्तर्निहित शक्ति"),
    SHADBALA_INFO_ITEM_6("6. Drik Bala - Strength from aspects received", "६. दृक् बल - प्राप्त दृष्टिहरूबाट शक्ति"),
    SHADBALA_INTERPRETATION("Interpretation", "व्याख्या"),
    SHADBALA_PLANET_ANALYSIS("%s Analysis", "%s विश्लेषण"),
    SHADBALA_MEETS_REQUIREMENT("Meets required strength", "आवश्यक शक्ति पूरा गर्दछ"),
    SHADBALA_BELOW_REQUIREMENT("Below required strength", "आवश्यक शक्तिभन्दा तल"),
    SHADBALA_BREAKDOWN("Strength Breakdown", "शक्ति विवरण"),
    SHADBALA_CALCULATING("Calculating Shadbala...", "षड्बल गणना गर्दै..."),
    SHADBALA_CHART_ANALYSIS("Chart Strength Analysis", "कुण्डली शक्ति विश्लेषण"),
    SHADBALA_CALCULATION_ERROR("Failed to calculate Shadbala", "षडबल गणना गर्न असफल भयो"),

    // ============================================
    // SADE SATI ANALYSIS
    // ============================================
    SADE_SATI_TITLE("Sade Sati Analysis", "साढेसाती विश्लेषण"),
    SADE_SATI_SUBTITLE("Saturn's 7.5 Year Transit", "शनिको ७.५ वर्षे गोचर"),
    SADE_SATI_ACTIVE("Sade Sati Active", "साढेसाती सक्रिय"),
    SADE_SATI_NOT_ACTIVE("Sade Sati is not currently active", "साढेसाती हाल सक्रिय छैन"),
    SADE_SATI_PHASE_RISING("Rising Phase", "उदय चरण"),
    SADE_SATI_PHASE_PEAK("Peak Phase", "शिखर चरण"),
    SADE_SATI_PHASE_SETTING("Setting Phase", "अस्त चरण"),
    SADE_SATI_RISING_DESC("Saturn transiting 12th from Moon - Beginning of Sade Sati", "शनि चन्द्रबाट १२औं राशिमा गोचर - साढेसातीको शुरुआत"),
    SADE_SATI_PEAK_DESC("Saturn transiting over natal Moon - Most intense phase", "शनि जन्म चन्द्रमाथि गोचर - सबैभन्दा तीव्र चरण"),
    SADE_SATI_SETTING_DESC("Saturn transiting 2nd from Moon - Final phase of Sade Sati", "शनि चन्द्रबाट २औं राशिमा गोचर - साढेसातीको अन्तिम चरण"),
    SADE_SATI_ACTIVE_SUMMARY("{phase} phase active with {severity} intensity", "{phase} चरण {severity} तीव्रताका साथ सक्रिय"),
    SMALL_PANOTI_FOURTH("Kantak Shani (4th from Moon)", "कण्टक शनि (चन्द्रबाट ४औं)"),
    SMALL_PANOTI_EIGHTH("Ashtama Shani (8th from Moon)", "अष्टम शनि (चन्द्रबाट ८औं)"),
    SMALL_PANOTI_ACTIVE_SUMMARY("{type} is active", "{type} सक्रिय छ"),
    SEVERITY_MILD("Mild", "हल्का"),
    SEVERITY_MODERATE("Moderate", "मध्यम"),
    SEVERITY_SIGNIFICANT("Significant", "महत्त्वपूर्ण"),
    SEVERITY_INTENSE("Intense", "तीव्र"),
    SADE_SATI_DAYS_REMAINING("Days Remaining", "बाँकी दिनहरू"),
    SADE_SATI_PROGRESS("Progress in Phase", "चरणमा प्रगति"),
    SADE_SATI_MOON_SIGN("Natal Moon Sign", "जन्म चन्द्र राशि"),
    SADE_SATI_SATURN_SIGN("Transit Saturn Sign", "गोचर शनि राशि"),

    // Sade Sati Remedies
    REMEDY_SHANI_MANTRA_TITLE("Shani Mantra", "शनि मन्त्र"),
    REMEDY_SHANI_MANTRA_DESC("Recite 'Om Sham Shanaishcharaya Namah' 108 times daily", "'ॐ शं शनैश्चराय नमः' दैनिक १०८ पटक जप गर्नुहोस्"),
    REMEDY_SATURDAY_CHARITY_TITLE("Saturday Charity", "शनिबार दान"),
    REMEDY_SATURDAY_CHARITY_DESC("Donate black sesame, mustard oil, or iron items to the needy", "कालो तिल, सर्स्यूको तेल, वा फलामका सामानहरू गरिबलाई दान गर्नुहोस्"),
    REMEDY_SATURDAY_FAST_TITLE("Saturday Fasting", "शनिबार व्रत"),
    REMEDY_SATURDAY_FAST_DESC("Observe fast on Saturdays and eat only after sunset", "शनिबार व्रत राख्नुहोस् र सूर्यास्त पछि मात्र खानुहोस्"),
    REMEDY_HANUMAN_WORSHIP_TITLE("Hanuman Worship", "हनुमान पूजा"),
    REMEDY_HANUMAN_WORSHIP_DESC("Recite Hanuman Chalisa daily, especially on Tuesdays and Saturdays", "हनुमान चालीसा दैनिक पाठ गर्नुहोस्, विशेष गरी मंगलबार र शनिबार"),
    REMEDY_BLUE_SAPPHIRE_TITLE("Blue Sapphire (Neelam)", "नीलम रत्न"),
    REMEDY_BLUE_SAPPHIRE_DESC("Wear after proper testing and astrologer consultation", "उचित परीक्षण र ज्योतिषी परामर्श पछि लगाउनुहोस्"),

    // ============================================
    // MANGLIK DOSHA ANALYSIS
    // ============================================
    MANGLIK_TITLE("Manglik Dosha Analysis", "मांगलिक दोष विश्लेषण"),
    MANGLIK_SUBTITLE("Mars Placement Analysis for Marriage", "विवाहको लागि मंगल स्थिति विश्लेषण"),
    MANGLIK_NONE_LEVEL("No Manglik Dosha", "मांगलिक दोष छैन"),
    MANGLIK_MILD("Mild Manglik", "हल्का मांगलिक"),
    MANGLIK_PARTIAL_LEVEL("Partial Manglik", "आंशिक मांगलिक"),
    MANGLIK_FULL_LEVEL("Full Manglik", "पूर्ण मांगलिक"),
    MANGLIK_SEVERE("Severe Manglik", "गम्भीर मांगलिक"),
    MANGLIK_SUMMARY_PRESENT("{level} present with {intensity}% intensity", "{level} {intensity}% तीव्रताका साथ उपस्थित"),
    MANGLIK_SUMMARY_ABSENT("No Manglik Dosha in this chart", "यस कुण्डलीमा मांगलिक दोष छैन"),
    MANGLIK_FROM_LAGNA("From Lagna", "लग्नबाट"),
    MANGLIK_FROM_MOON("From Moon", "चन्द्रबाट"),
    MANGLIK_FROM_VENUS("From Venus", "शुक्रबाट"),
    MANGLIK_CANCELLATIONS("Cancellation Factors", "रद्द गर्ने कारकहरू"),
    MANGLIK_EFFECTIVE_LEVEL("Effective Level", "प्रभावी स्तर"),

    // Manglik Cancellation Factors
    MANGLIK_CANCEL_OWN_SIGN_TITLE("Mars in Own Sign", "मंगल स्वराशिमा"),
    MANGLIK_CANCEL_OWN_SIGN_DESC("Mars in Aries or Scorpio reduces Manglik effects", "मंगल मेष वा वृश्चिकमा मांगलिक प्रभाव कम गर्छ"),
    MANGLIK_CANCEL_EXALTED_TITLE("Mars Exalted", "मंगल उच्च"),
    MANGLIK_CANCEL_EXALTED_DESC("Mars in Capricorn cancels Manglik Dosha completely", "मंगल मकरमा मांगलिक दोष पूर्ण रूपमा रद्द गर्छ"),
    MANGLIK_CANCEL_JUPITER_CONJUNCT_TITLE("Jupiter Conjunction", "गुरु युति"),
    MANGLIK_CANCEL_JUPITER_CONJUNCT_DESC("Jupiter conjunct Mars cancels Manglik effects", "गुरुले मंगलसँग युतिले मांगलिक प्रभाव रद्द गर्छ"),
    MANGLIK_CANCEL_VENUS_CONJUNCT_TITLE("Venus Conjunction", "शुक्र युति"),
    MANGLIK_CANCEL_VENUS_CONJUNCT_DESC("Venus conjunct Mars significantly reduces effects", "शुक्रले मंगलसँग युतिले प्रभाव उल्लेखनीय रूपमा कम गर्छ"),
    MANGLIK_CANCEL_JUPITER_ASPECT_TITLE("Jupiter's Aspect", "गुरुको दृष्टि"),
    MANGLIK_CANCEL_JUPITER_ASPECT_DESC("Jupiter aspecting Mars reduces Manglik effects", "गुरुको मंगलमा दृष्टिले मांगलिक प्रभाव कम गर्छ"),
    MANGLIK_CANCEL_SECOND_MERCURY_TITLE("Mars in 2nd in Mercury Sign", "बुध राशिमा २औं मंगल"),
    MANGLIK_CANCEL_SECOND_MERCURY_DESC("Mars in 2nd house in Gemini/Virgo cancels dosha", "मिथुन/कन्यामा २औं भावमा मंगलले दोष रद्द गर्छ"),
    MANGLIK_CANCEL_FOURTH_OWN_TITLE("Mars in 4th in Own Sign", "स्वराशिमा ४औं मंगल"),
    MANGLIK_CANCEL_FOURTH_OWN_DESC("Mars in 4th house in Aries/Scorpio cancels dosha", "मेष/वृश्चिकमा ४औं भावमा मंगलले दोष रद्द गर्छ"),
    MANGLIK_CANCEL_SEVENTH_SPECIAL_TITLE("Mars in 7th Special", "७औं मंगल विशेष"),
    MANGLIK_CANCEL_SEVENTH_SPECIAL_DESC("Mars in 7th in Cancer/Capricorn reduces effects", "कर्कट/मकरमा ७औं भावमा मंगलले प्रभाव कम गर्छ"),
    MANGLIK_CANCEL_EIGHTH_JUPITER_TITLE("Mars in 8th Jupiter Sign", "गुरु राशिमा ८औं मंगल"),
    MANGLIK_CANCEL_EIGHTH_JUPITER_DESC("Mars in 8th in Sagittarius/Pisces reduces effects", "धनु/मीनमा ८औं भावमा मंगलले प्रभाव कम गर्छ"),
    MANGLIK_CANCEL_TWELFTH_VENUS_TITLE("Mars in 12th Venus Sign", "शुक्र राशिमा १२औं मंगल"),
    MANGLIK_CANCEL_TWELFTH_VENUS_DESC("Mars in 12th in Taurus/Libra cancels dosha", "वृषभ/तुलामा १२औं भावमा मंगलले दोष रद्द गर्छ"),
    MANGLIK_CANCEL_BENEFIC_ASC_TITLE("Benefic Ascendant", "शुभ लग्न"),
    MANGLIK_CANCEL_BENEFIC_ASC_DESC("For Aries/Cancer/Leo/Scorpio ascendants, Mars is benefic", "मेष/कर्कट/सिंह/वृश्चिक लग्नको लागि मंगल शुभ हो"),

    // Manglik Remedies
    REMEDY_KUMBH_VIVAH_TITLE("Kumbh Vivah", "कुम्भ विवाह"),
    REMEDY_KUMBH_VIVAH_DESC("Ceremonial marriage to a clay pot or Peepal tree before actual marriage", "वास्तविक विवाह अघि माटोको घडा वा पीपलको रूखसँग विवाह समारोह"),
    REMEDY_MANGAL_SHANTI_TITLE("Mangal Shanti Puja", "मंगल शान्ति पूजा"),
    REMEDY_MANGAL_SHANTI_DESC("Perform Mars pacification ritual at a temple or home", "मन्दिर वा घरमा मंगल शान्ति विधि गर्नुहोस्"),
    REMEDY_MARS_MANTRA_TITLE("Mars Mantra", "मंगल मन्त्र"),
    REMEDY_MARS_MANTRA_DESC("Recite 'Om Kram Kreem Kroum Sah Bhaumaya Namah' 108 times on Tuesdays", "मंगलबार 'ॐ क्रां क्रीं क्रौं सः भौमाय नमः' १०८ पटक जप गर्नुहोस्"),
    REMEDY_CORAL_TITLE("Red Coral (Moonga)", "मूंगा रत्न"),
    REMEDY_CORAL_DESC("Wear red coral in gold or copper on right hand ring finger", "दाहिने हातको अनामिकामा सुन वा तामामा मूंगा लगाउनुहोस्"),
    REMEDY_TUESDAY_CHARITY_TITLE("Tuesday Charity", "मंगलबार दान"),
    REMEDY_TUESDAY_CHARITY_DESC("Donate red lentils, red cloth, or copper items on Tuesdays", "मंगलबार रातो दाल, रातो कपडा, वा तामाका सामान दान गर्नुहोस्"),

    // ============================================
    // PITRA DOSHA ANALYSIS
    // ============================================
    PITRA_DOSHA_TITLE("Pitra Dosha Analysis", "पितृ दोष विश्लेषण"),
    PITRA_DOSHA_SUBTITLE("Ancestral Karma Assessment", "पुर्खाको कर्म मूल्यांकन"),
    PITRA_DOSHA_NONE("No Pitra Dosha", "पितृ दोष छैन"),
    PITRA_DOSHA_MINOR("Minor Pitra Dosha", "हल्का पितृ दोष"),
    PITRA_DOSHA_MODERATE("Moderate Pitra Dosha", "मध्यम पितृ दोष"),
    PITRA_DOSHA_SIGNIFICANT("Significant Pitra Dosha", "महत्त्वपूर्ण पितृ दोष"),
    PITRA_DOSHA_SEVERE("Severe Pitra Dosha", "गम्भीर पितृ दोष"),
    PITRA_DOSHA_PRESENT_SUMMARY("{level} detected in chart", "कुण्डलीमा {level} पत्ता लाग्यो"),
    PITRA_DOSHA_ABSENT_SUMMARY("No significant Pitra Dosha indicators found", "कुनै महत्त्वपूर्ण पितृ दोष संकेतकहरू फेला परेनन्"),

    // Pitra Dosha Types
    PITRA_TYPE_SURYA_RAHU("Sun-Rahu Conjunction", "सूर्य-राहु युति"),
    PITRA_TYPE_SURYA_KETU("Sun-Ketu Conjunction", "सूर्य-केतु युति"),
    PITRA_TYPE_SURYA_SHANI("Sun-Saturn Affliction", "सूर्य-शनि पीडा"),
    PITRA_TYPE_NINTH_HOUSE("9th House Affliction", "९औं भाव पीडा"),
    PITRA_TYPE_NINTH_LORD("9th Lord Affliction", "९औं भावेश पीडा"),
    PITRA_TYPE_RAHU_NINTH("Rahu in 9th House", "९औं भावमा राहु"),
    PITRA_TYPE_COMBINED("Combined Affliction", "संयुक्त पीडा"),

    // Pitra Dosha Descriptions
    PITRA_DESC_SURYA_RAHU("Primary indicator - eclipsed Sun indicates blocked ancestral blessings", "प्राथमिक संकेतक - ग्रहण लागेको सूर्यले अवरुद्ध पुर्खाको आशीर्वाद संकेत गर्छ"),
    PITRA_DESC_SURYA_KETU("Past-life karmic debts from paternal lineage", "पितृ वंशबाट पूर्व जन्मको कर्म ऋण"),
    PITRA_DESC_SURYA_SHANI("Father-related karmic lessons and delays", "पिता-सम्बन्धित कर्म पाठ र ढिलाइ"),
    PITRA_DESC_NINTH_HOUSE("House of ancestors afflicted by malefics", "पुर्खाको भाव अशुभ ग्रहबाट पीडित"),
    PITRA_DESC_NINTH_LORD("Lord of father and fortune is weakened", "पिता र भाग्यको स्वामी कमजोर छ"),
    PITRA_DESC_RAHU_NINTH("Strong indication of ancestral debts", "पुर्खाको ऋणको बलियो संकेत"),
    PITRA_DESC_COMBINED("Multiple factors indicate significant ancestral karma", "बहु कारकहरूले महत्त्वपूर्ण पुर्खाको कर्म संकेत गर्छन्"),

    // Pitra Dosha Remedies
    REMEDY_PITRA_TARPAN_TITLE("Pitra Tarpan", "पितृ तर्पण"),
    REMEDY_PITRA_TARPAN_DESC("Offer water with sesame seeds to ancestors during Amavasya", "अमावस्यामा पुर्खालाई तिल पानी अर्पण गर्नुहोस्"),
    REMEDY_SHRADDHA_TITLE("Shraddha Ceremony", "श्राद्ध विधि"),
    REMEDY_SHRADDHA_DESC("Perform annual death anniversary rituals for departed ancestors", "दिवंगत पुर्खाको वार्षिक मृत्यु वर्षगाँठ विधि गर्नुहोस्"),
    REMEDY_CROW_FEEDING_TITLE("Crow Feeding", "काग भोजन"),
    REMEDY_CROW_FEEDING_DESC("Feed crows daily as they are considered messengers of ancestors", "कागलाई दैनिक खुवाउनुहोस् किनभने तिनीहरूलाई पुर्खाको दूत मानिन्छ"),
    REMEDY_NARAYAN_BALI_TITLE("Narayan Bali", "नारायण बलि"),
    REMEDY_NARAYAN_BALI_DESC("Special ritual for departed souls performed at Trimbakeshwar", "त्र्यम्बकेश्वरमा दिवंगत आत्माको लागि विशेष विधि"),
    REMEDY_PIND_DAAN_TITLE("Pind Daan", "पिण्ड दान"),
    REMEDY_PIND_DAAN_DESC("Offer rice balls to ancestors at Gaya or other sacred places", "गया वा अन्य पवित्र स्थानमा पुर्खालाई भातको पिण्ड अर्पण गर्नुहोस्"),
    REMEDY_PITRA_GAYATRI_TITLE("Pitra Gayatri Mantra", "पितृ गायत्री मन्त्र"),
    REMEDY_PITRA_GAYATRI_DESC("Recite Pitra Gayatri daily during Brahma Muhurta for ancestral peace", "पुर्खाको शान्तिको लागि ब्रह्म मुहूर्तमा पितृ गायत्री दैनिक जप गर्नुहोस्"),

    // ============================================
    // COMMON DOSHA ANALYSIS STRINGS
    // ============================================
    DOSHA_ANALYSIS("Dosha Analysis", "दोष विश्लेषण"),
    DOSHA_INDICATORS("Indicators Found", "संकेतकहरू फेला परे"),
    DOSHA_AFFECTED_AREAS("Affected Life Areas", "प्रभावित जीवन क्षेत्रहरू"),
    DOSHA_REMEDIES_SECTION("Recommended Remedies", "सिफारिस गरिएका उपायहरू"),
    DOSHA_INTERPRETATION("Interpretation", "व्याख्या"),
    DOSHA_SEVERITY_SCORE("Severity Score", "गम्भीरता अंक"),
    DOSHA_AUSPICIOUS_TIMES("Auspicious Times for Remedies", "उपायहरूको लागि शुभ समय"),

    // ============================================
    // INTERPRETATION SECTION HEADERS
    // ============================================
    INTERP_ANALYSIS_HEADER("ANALYSIS", "विश्लेषण"),
    INTERP_INDICATORS_FOUND("INDICATORS FOUND:", "पाइएका संकेतकहरू:"),
    INTERP_INTERPRETATION("INTERPRETATION:", "व्याख्या:"),
    INTERP_SEVERITY("SEVERITY:", "गम्भीरता:"),
    INTERP_LEVEL("Level:", "स्तर:"),

    // ============================================
    // MANGLIK DOSHA INTERPRETATION
    // ============================================
    MANGLIK_INTERP_NO_DOSHA("NO MANGLIK DOSHA", "मांगलिक दोष छैन"),
    MANGLIK_INTERP_MARS_NOT_PLACED(
        "Mars is not placed in houses 1, 2, 4, 7, 8, or 12 from your Lagna, Moon, or Venus.",
        "मंगल तपाईंको लग्न, चन्द्र वा शुक्रबाट १, २, ४, ७, ८ वा १२ भावमा छैन।"
    ),
    MANGLIK_INTERP_HEADER("MANGLIK DOSHA ANALYSIS", "मांगलिक दोष विश्लेषण"),
    MANGLIK_INTERP_FROM_REFERENCE("ANALYSIS FROM THREE REFERENCE POINTS:", "तीन सन्दर्भ बिन्दुबाट विश्लेषण:"),
    MANGLIK_INTERP_FROM_LAGNA("From Lagna", "लग्नबाट"),
    MANGLIK_INTERP_FROM_MOON("From Moon", "चन्द्रबाट"),
    MANGLIK_INTERP_FROM_VENUS("From Venus", "शुक्रबाट"),
    MANGLIK_INTERP_MARS_IN_HOUSE("Mars in house", "मंगल भावमा"),
    MANGLIK_INTERP_MANGLIK_YES("YES", "छ"),
    MANGLIK_INTERP_MANGLIK_NO("NO", "छैन"),
    MANGLIK_INTERP_CANCELLATION_PRESENT("CANCELLATION FACTORS PRESENT:", "निरसन कारकहरू उपस्थित:"),
    MANGLIK_INTERP_EFFECTIVE_LEVEL("Effective Level After Cancellations:", "निरसन पछिको प्रभावकारी स्तर:"),
    MANGLIK_INTERP_HOUSE_SUFFIX_ST("st", "औं"),
    MANGLIK_INTERP_HOUSE_SUFFIX_ND("nd", "औं"),
    MANGLIK_INTERP_HOUSE_SUFFIX_RD("rd", "औं"),
    MANGLIK_INTERP_HOUSE_SUFFIX_TH("th", "औं"),

    // ============================================
    // MANGLIK MARRIAGE CONSIDERATIONS
    // ============================================
    MANGLIK_MARRIAGE_HEADER("MARRIAGE CONSIDERATIONS", "विवाह विचार"),
    MANGLIK_MARRIAGE_NONE_NO_RESTRICTION(
        "No restrictions based on Manglik Dosha",
        "मांगलिक दोषको आधारमा कुनै प्रतिबन्ध छैन"
    ),
    MANGLIK_MARRIAGE_NONE_COMPATIBLE(
        "Compatible with both Manglik and non-Manglik partners",
        "मांगलिक र गैर-मांगलिक दुवै साझेदारसँग मिल्दो"
    ),
    MANGLIK_MARRIAGE_MILD_EFFECTS(
        "Mild Manglik effects - marriage with non-Manglik is possible",
        "हल्का मांगलिक प्रभाव - गैर-मांगलिकसँग विवाह सम्भव छ"
    ),
    MANGLIK_MARRIAGE_MILD_REMEDIES(
        "Simple remedies recommended before marriage",
        "विवाह अघि सरल उपायहरू सिफारिस गरिन्छ"
    ),
    MANGLIK_MARRIAGE_MILD_MATCHING(
        "Matching with another Manglik is beneficial but not essential",
        "अर्को मांगलिकसँग मिलान लाभदायक तर अनिवार्य होइन"
    ),
    MANGLIK_MARRIAGE_PARTIAL_REMEDIES(
        "Partial Manglik - remedies strongly recommended",
        "आंशिक मांगलिक - उपायहरू जोडदार सिफारिस"
    ),
    MANGLIK_MARRIAGE_PARTIAL_PREFERABLE(
        "Marriage with Manglik partner is preferable",
        "मांगलिक साझेदारसँग विवाह उपयुक्त"
    ),
    MANGLIK_MARRIAGE_PARTIAL_KUMBH(
        "If marrying non-Manglik, perform Kumbh Vivah",
        "गैर-मांगलिकसँग विवाह गर्दा कुम्भ विवाह गर्नुहोस्"
    ),
    MANGLIK_MARRIAGE_FULL_PRESENT(
        "Full Manglik Dosha present",
        "पूर्ण मांगलिक दोष उपस्थित"
    ),
    MANGLIK_MARRIAGE_FULL_RECOMMENDED(
        "Marriage with Manglik partner highly recommended",
        "मांगलिक साझेदारसँग विवाह अत्यधिक सिफारिस"
    ),
    MANGLIK_MARRIAGE_FULL_KUMBH_ESSENTIAL(
        "Kumbh Vivah or equivalent ritual essential before marriage",
        "विवाह अघि कुम्भ विवाह वा समान अनुष्ठान आवश्यक"
    ),
    MANGLIK_MARRIAGE_FULL_PROPITIATION(
        "Regular Mars propitiation recommended",
        "नियमित मंगल शान्ति सिफारिस गरिन्छ"
    ),
    MANGLIK_MARRIAGE_SEVERE_CONSIDERATION(
        "Severe Manglik Dosha - careful consideration required",
        "गम्भीर मांगलिक दोष - सावधानीपूर्ण विचार आवश्यक"
    ),
    MANGLIK_MARRIAGE_SEVERE_ONLY_MANGLIK(
        "Only marry Manglik partner with similar intensity",
        "समान तीव्रता भएको मांगलिक साझेदारसँग मात्र विवाह गर्नुहोस्"
    ),
    MANGLIK_MARRIAGE_SEVERE_MULTIPLE_REMEDIES(
        "Multiple remedies required before and after marriage",
        "विवाह अघि र पछि धेरै उपायहरू आवश्यक"
    ),
    MANGLIK_MARRIAGE_SEVERE_DELAY(
        "Consider delaying marriage until after age 28 (Mars maturity)",
        "मंगल परिपक्वता (२८ वर्ष) सम्म विवाह ढिला गर्ने विचार गर्नुहोस्"
    ),
    MANGLIK_MARRIAGE_FULL_CANCELLATION(
        "NOTE: Full cancellation present - Manglik Dosha effectively nullified",
        "नोट: पूर्ण निरसन उपस्थित - मांगलिक दोष प्रभावकारी रूपमा शून्य"
    ),

    // ============================================
    // MANGLIK COMPATIBILITY
    // ============================================
    MANGLIK_COMPAT_EXCELLENT(
        "Excellent Manglik compatibility - no concerns",
        "उत्कृष्ट मांगलिक मिलान - कुनै चिन्ता छैन"
    ),
    MANGLIK_COMPAT_GOOD(
        "Good compatibility - minor remedies may help",
        "राम्रो मिलान - साना उपायहरू सहायक हुन सक्छ"
    ),
    MANGLIK_COMPAT_AVERAGE(
        "Average compatibility - remedies recommended",
        "औसत मिलान - उपायहरू सिफारिस गरिन्छ"
    ),
    MANGLIK_COMPAT_BELOW_AVERAGE(
        "Below average - significant remedies required",
        "औसतभन्दा कम - महत्वपूर्ण उपायहरू आवश्यक"
    ),
    MANGLIK_COMPAT_POOR(
        "Challenging combination - expert consultation advised",
        "कठिन संयोजन - विशेषज्ञ परामर्श सल्लाह दिइन्छ"
    ),

    // ============================================
    // MANGLIK REMEDY EFFECTIVENESS
    // ============================================
    REMEDY_EFFECTIVENESS_TRADITIONAL(
        "Traditional remedy - highly effective",
        "परम्परागत उपाय - अत्यधिक प्रभावकारी"
    ),
    REMEDY_EFFECTIVENESS_ALL_LEVELS(
        "Recommended for all Manglik levels",
        "सबै मांगलिक स्तरहरूको लागि सिफारिस"
    ),
    REMEDY_EFFECTIVENESS_TUESDAY(
        "Daily recitation on Tuesdays",
        "मंगलबार दैनिक पाठ"
    ),
    REMEDY_EFFECTIVENESS_CONSULT(
        "Consult astrologer before wearing",
        "लगाउनु अघि ज्योतिषीसँग परामर्श गर्नुहोस्"
    ),
    REMEDY_EFFECTIVENESS_EVERY_TUESDAY(
        "Every Tuesday",
        "हरेक मंगलबार"
    ),

    // ============================================
    // MANGLIK REFERENCE POINTS
    // ============================================
    MANGLIK_REF_LAGNA("Lagna", "लग्न"),
    MANGLIK_REF_MOON("Moon", "चन्द्र"),
    MANGLIK_REF_VENUS("Venus", "शुक्र"),
    YES("Yes", "छ"),
    NO("No", "छैन"),

    // ============================================
    // MANGLIK INTERPRETATION KEYS
    // ============================================
    MANGLIK_INTERP_NO_DOSHA_TITLE("NO MANGLIK DOSHA", "मांगलिक दोष छैन"),
    MANGLIK_INTERP_NO_DOSHA_DESC(
        "Mars is not placed in houses 1, 2, 4, 7, 8, or 12 from your Lagna, Moon, or Venus. There is no Manglik Dosha in your chart.",
        "मंगल तपाईंको लग्न, चन्द्र वा शुक्रबाट १, २, ४, ७, ८ वा १२औं भावमा छैन। तपाईंको कुण्डलीमा मांगलिक दोष छैन।"
    ),
    MANGLIK_INTERP_TITLE("MANGLIK DOSHA ANALYSIS", "मांगलिक दोष विश्लेषण"),
    MANGLIK_INTERP_MARS_POSITION("Mars Position: {sign} in House {house}", "मंगल स्थिति: {sign} भाव {house} मा"),
    MANGLIK_INTERP_MARS_RETROGRADE("Note: Mars is retrograde which can intensify or modify its effects.", "नोट: मंगल वक्री छ जसले यसको प्रभावहरू तीव्र वा परिमार्जन गर्न सक्छ।"),
    MANGLIK_INTERP_THREE_REF("ANALYSIS FROM THREE REFERENCE POINTS:", "तीन सन्दर्भ बिन्दुबाट विश्लेषण:"),
    MANGLIK_INTERP_MARS_HOUSE("Mars in House {house}", "मंगल भाव {house} मा"),
    MANGLIK_INTERP_IS_MANGLIK("Manglik", "मांगलिक"),
    MANGLIK_INTERP_INITIAL_LEVEL("Initial Level: {level}", "प्रारम्भिक स्तर: {level}"),

    // ============================================
    // MANGLIK MARRIAGE CONSIDERATIONS
    // ============================================
    MANGLIK_MARRIAGE_TITLE("MARRIAGE CONSIDERATIONS", "विवाह सम्बन्धी विचारहरू"),
    MANGLIK_MARRIAGE_NONE_1("No restrictions based on Manglik Dosha.", "मांगलिक दोषमा आधारित कुनै प्रतिबन्ध छैन।"),
    MANGLIK_MARRIAGE_NONE_2("Compatible with both Manglik and non-Manglik partners.", "मांगलिक र गैर-मांगलिक दुवै साझेदारसँग मिल्दो।"),
    MANGLIK_MARRIAGE_MILD_1("Mild Manglik effects - marriage with non-Manglik is possible.", "हल्का मांगलिक प्रभाव - गैर-मांगलिकसँग विवाह सम्भव।"),
    MANGLIK_MARRIAGE_MILD_2("Simple remedies recommended before marriage.", "विवाह अघि सरल उपायहरू सिफारिस।"),
    MANGLIK_MARRIAGE_MILD_3("Matching with another Manglik is beneficial but not essential.", "अर्को मांगलिकसँग मिलान लाभदायक तर अनिवार्य होइन।"),
    MANGLIK_MARRIAGE_PARTIAL_1("Partial Manglik - remedies strongly recommended.", "आंशिक मांगलिक - उपायहरू जोडदार सिफारिस।"),
    MANGLIK_MARRIAGE_PARTIAL_2("Marriage with Manglik partner is preferable.", "मांगलिक साझेदारसँग विवाह उपयुक्त।"),
    MANGLIK_MARRIAGE_PARTIAL_3("If marrying non-Manglik, perform Kumbh Vivah.", "गैर-मांगलिकसँग विवाह गर्दा कुम्भ विवाह गर्नुहोस्।"),
    MANGLIK_MARRIAGE_FULL_1("Full Manglik Dosha present.", "पूर्ण मांगलिक दोष उपस्थित।"),
    MANGLIK_MARRIAGE_FULL_2("Marriage with Manglik partner highly recommended.", "मांगलिक साझेदारसँग विवाह अत्यधिक सिफारिस।"),
    MANGLIK_MARRIAGE_FULL_3("Kumbh Vivah or equivalent ritual essential before marriage.", "विवाह अघि कुम्भ विवाह वा समतुल्य अनुष्ठान अनिवार्य।"),
    MANGLIK_MARRIAGE_FULL_4("Regular Mars propitiation recommended.", "नियमित मंगल शान्ति सिफारिस।"),
    MANGLIK_MARRIAGE_SEVERE_1("Severe Manglik Dosha - careful consideration required.", "गम्भीर मांगलिक दोष - सावधानीपूर्ण विचार आवश्यक।"),
    MANGLIK_MARRIAGE_SEVERE_2("Only marry Manglik partner with similar intensity.", "समान तीव्रता भएको मांगलिक साझेदारसँग मात्र विवाह।"),
    MANGLIK_MARRIAGE_SEVERE_3("Multiple remedies required before and after marriage.", "विवाह अघि र पछि बहु उपायहरू आवश्यक।"),
    MANGLIK_MARRIAGE_SEVERE_4("Consider delaying marriage until after age 28 (Mars maturity).", "मंगल परिपक्वता (२८ वर्ष) सम्म विवाह ढिला गर्ने विचार गर्नुहोस्।"),
    MANGLIK_MARRIAGE_FULL_CANCEL_NOTE("NOTE: Full cancellation present - Manglik Dosha effectively nullified.", "नोट: पूर्ण निरसन उपस्थित - मांगलिक दोष प्रभावकारी रूपमा शून्य।"),

    // ============================================
    // PITRA DOSHA INTERPRETATION
    // ============================================
    PITRA_INTERP_NO_DOSHA("NO SIGNIFICANT PITRA DOSHA", "कुनै महत्वपूर्ण पित्र दोष छैन"),
    PITRA_INTERP_NO_DOSHA_DESC(
        "Your chart does not show significant indicators of Pitra Dosha.",
        "तपाईंको कुण्डलीमा पित्र दोषको महत्वपूर्ण संकेतकहरू देखिँदैनन्।"
    ),
    PITRA_INTERP_SUPPORTIVE(
        "The ancestral lineage appears supportive of your life journey.",
        "पैतृक वंश तपाईंको जीवन यात्रामा सहायक देखिन्छ।"
    ),
    PITRA_INTERP_BENEFICIAL(
        "However, performing regular ancestral offerings (Shraddha) is always beneficial for maintaining positive ancestral blessings.",
        "तथापि, सकारात्मक पैतृक आशीर्वाद कायम राख्न नियमित श्राद्ध गर्नु सधैं लाभदायक हुन्छ।"
    ),
    PITRA_INTERP_HEADER("PITRA DOSHA ANALYSIS", "पित्र दोष विश्लेषण"),
    PITRA_INTERP_NINTH_HOUSE("9TH HOUSE ANALYSIS (House of Ancestors):", "९औं भाव विश्लेषण (पूर्वजको भाव):"),
    PITRA_INTERP_NINTH_LORD("9th Lord:", "९औं स्वामी:"),
    PITRA_INTERP_NINTH_LORD_POSITION("9th Lord Position:", "९औं स्वामीको स्थिति:"),
    PITRA_INTERP_LORD_AFFLICTED("9th Lord Afflicted:", "९औं स्वामी पीडित:"),
    PITRA_INTERP_HOUSE_AFFLICTED("9th House Afflicted:", "९औं भाव पीडित:"),
    PITRA_INTERP_BENEFIC_INFLUENCE("Benefic Influence:", "शुभ प्रभाव:"),
    PITRA_INTERP_YES_MITIGATING("Yes - Mitigating", "छ - न्यूनीकरण गर्दै"),

    // Pitra Dosha indicator descriptions
    PITRA_DESC_SUN_RAHU_HOUSE(
        "Sun conjunct Rahu in House %d - Primary Pitra Dosha indicator",
        "भाव %d मा सूर्य-राहु युति - प्रमुख पित्र दोष संकेतक"
    ),
    PITRA_DESC_SUN_KETU_HOUSE(
        "Sun conjunct Ketu in House %d - Indicates past-life ancestral karma",
        "भाव %d मा सूर्य-केतु युति - पूर्वजन्मको पैतृक कर्म संकेत"
    ),
    PITRA_DESC_SUN_SATURN_CONJUNCT(
        "Sun conjunct Saturn in House %d - Father-related karmic issues",
        "भाव %d मा सूर्य-शनि युति - पितृ-सम्बन्धी कार्मिक मुद्दाहरू"
    ),
    PITRA_DESC_SATURN_ASPECT(
        "Saturn aspects Sun from House %d - Delayed results due to ancestral karma",
        "शनिले भाव %d बाट सूर्यलाई हेर्छ - पैतृक कर्मले गर्दा ढिलो परिणाम"
    ),
    PITRA_DESC_MALEFICS_NINTH(
        "Malefics in 9th house - Ancestral blessings blocked",
        "९औं भावमा पाप ग्रह - पैतृक आशीर्वाद अवरुद्ध"
    ),
    PITRA_DESC_NINTH_LORD_AFFLICTED(
        "9th lord %s is afflicted - Ancestral lineage karma",
        "९औं स्वामी %s पीडित छ - पैतृक वंश कर्म"
    ),

    // Pitra interpretation levels
    PITRA_LEVEL_MINOR_DESC(
        "Minor ancestral karma is indicated. This may manifest as occasional obstacles or delays that seem unexplained. Regular ancestral prayers and offerings during Pitru Paksha should be sufficient.",
        "साना पैतृक कर्म संकेत गरिएको छ। यसले अव्याख्येय देखिने कहिलेकाहीं अवरोधहरू वा ढिलाइको रूपमा प्रकट हुन सक्छ। पितृ पक्षमा नियमित पैतृक प्रार्थना र अर्पण पर्याप्त हुनुपर्छ।"
    ),
    PITRA_LEVEL_MODERATE_DESC(
        "Moderate Pitra Dosha suggests unresolved ancestral obligations. You may experience recurring challenges in life that feel karmic. Regular Tarpan and Shraddha ceremonies are recommended.",
        "मध्यम पित्र दोषले अपूर्ण पैतृक दायित्वहरू संकेत गर्दछ। तपाईंले कार्मिक जस्तो लाग्ने जीवनमा आवर्ती चुनौतीहरू अनुभव गर्न सक्नुहुन्छ। नियमित तर्पण र श्राद्ध समारोहहरू सिफारिस गरिन्छ।"
    ),
    PITRA_LEVEL_SIGNIFICANT_DESC(
        "Significant ancestral karma is present. This may manifest as: delayed marriage or relationship issues, difficulties with children or progeny, career obstacles despite qualifications, and family disharmony. Comprehensive remedies including Narayan Bali may be beneficial.",
        "महत्वपूर्ण पैतृक कर्म उपस्थित छ। यसले विवाहमा ढिलाइ वा सम्बन्ध समस्या, सन्तान वा सन्ततिमा कठिनाइहरू, योग्यता भएता पनि क्यारियरमा अवरोधहरू, र पारिवारिक विसंगतिको रूपमा प्रकट हुन सक्छ। नारायण बलि सहित व्यापक उपायहरू लाभदायक हुन सक्छ।"
    ),
    PITRA_LEVEL_SEVERE_DESC(
        "Severe Pitra Dosha indicates deep ancestral karma that requires serious attention and remedial measures. This level of dosha often indicates: ancestors who departed with unfulfilled wishes, interrupted or improper last rites in the lineage, and significant karmic debts carried forward. Consult a qualified priest for Narayan Bali/Nagbali and Pind Daan at sacred places like Gaya.",
        "गम्भीर पित्र दोषले गहिरो पैतृक कर्म संकेत गर्दछ जसलाई गम्भीर ध्यान र उपचारात्मक उपायहरू आवश्यक छ। यस स्तरको दोषले प्रायः संकेत गर्दछ: अपूर्ण इच्छाहरूसँग गएका पूर्वजहरू, वंशमा अवरुद्ध वा अनुचित अन्तिम संस्कार, र अगाडि लगिएको महत्वपूर्ण कार्मिक ऋणहरू। गयामा नारायण बलि/नागबलि र पिण्डदान को लागि योग्य पुजारीसँग परामर्श गर्नुहोस्।"
    ),

    // Pitra life areas
    PITRA_AREA_FATHER("Father and paternal lineage", "पिता र पैतृक वंश"),
    PITRA_AREA_SPIRITUAL("Spiritual progress and dharma", "आध्यात्मिक प्रगति र धर्म"),
    PITRA_AREA_SELF("Self, health, and overall life direction", "आत्म, स्वास्थ्य, र समग्र जीवन दिशा"),
    PITRA_AREA_FAMILY_WEALTH("Family wealth and accumulated assets", "पारिवारिक सम्पत्ति र संचित सम्पत्ति"),
    PITRA_AREA_SIBLINGS("Siblings and communication", "भाइबहिनी र संचार"),
    PITRA_AREA_MOTHER("Mother, property, and domestic peace", "आमा, सम्पत्ति, र घरेलु शान्ति"),
    PITRA_AREA_CHILDREN("Children, education, and creativity", "बच्चाहरू, शिक्षा, र सृजनात्मकता"),
    PITRA_AREA_HEALTH("Health, debts, and service", "स्वास्थ्य, ऋण, र सेवा"),
    PITRA_AREA_MARRIAGE("Marriage and partnerships", "विवाह र साझेदारी"),
    PITRA_AREA_LONGEVITY("Longevity and inherited wealth", "दीर्घायु र विरासत सम्पत्ति"),
    PITRA_AREA_FORTUNE("Fortune, higher learning, and spirituality", "भाग्य, उच्च शिक्षा, र आध्यात्मिकता"),
    PITRA_AREA_CAREER("Career and public reputation", "क्यारियर र सार्वजनिक प्रतिष्ठा"),
    PITRA_AREA_GAINS("Gains and social network", "लाभ र सामाजिक नेटवर्क"),
    PITRA_AREA_LIBERATION("Spiritual liberation and foreign lands", "आध्यात्मिक मुक्ति र विदेश"),

    // Pitra remedy timings
    PITRA_TIMING_AMAVASYA("Amavasya (New Moon) or Pitru Paksha", "औंसी वा पितृ पक्ष"),
    PITRA_TIMING_DEATH_ANNIVERSARY("Father's death anniversary or Pitru Paksha", "पिताको पुण्यतिथि वा पितृ पक्ष"),
    PITRA_TIMING_DAILY_PITRU("Daily, especially during Pitru Paksha", "दैनिक, विशेष गरी पितृ पक्षमा"),
    PITRA_TIMING_NARAYAN_BALI("Once in lifetime at Trimbakeshwar or Gaya", "जीवनकालमा एकपटक त्र्यम्बकेश्वर वा गयामा"),
    PITRA_TIMING_PIND_DAAN("Pitru Paksha at Gaya", "गयामा पितृ पक्ष"),
    PITRA_TIMING_BRAHMA_MUHURTA("Daily during Brahma Muhurta", "ब्रह्म मुहूर्तमा दैनिक"),

    // Pitra auspicious periods
    PITRA_PERIOD_PITRU_PAKSHA("Pitru Paksha (15-day period in Bhadrapada month)", "पितृ पक्ष (भाद्र महिनामा १५ दिनको अवधि)"),
    PITRA_PERIOD_AMAVASYA("Amavasya (New Moon days)", "औंसी (अमावस्या दिनहरू)"),
    PITRA_PERIOD_ECLIPSE("Solar/Lunar eclipses", "सूर्य/चन्द्र ग्रहण"),
    PITRA_PERIOD_DEATH_ANNIV("Father's death anniversary", "पिताको पुण्यतिथि"),
    PITRA_PERIOD_MAHALAYA("Mahalaya Amavasya", "महालय अमावस्या"),
    PITRA_PERIOD_AKSHAYA("Akshaya Tritiya", "अक्षय तृतीया"),
    PITRA_PERIOD_GAYA("Gaya Shraddha periods", "गया श्राद्ध अवधिहरू"),

    // ============================================
    // SADE SATI INTERPRETATION
    // ============================================
    SADE_SATI_ACTIVE_HEADER("SADE SATI ACTIVE - %s PHASE", "साढ़े साती सक्रिय - %s चरण"),
    SADE_SATI_TRANSIT_DESC(
        "Saturn is currently transiting %s, which is the %s from your natal Moon in %s.",
        "शनि हाल %s मा गोचर गर्दैछ, जुन तपाईंको जन्म चन्द्र %s बाट %s हो।"
    ),
    SADE_SATI_12TH_HOUSE("12th house", "१२औं भाव"),
    SADE_SATI_SAME_SIGN("same sign", "उही राशि"),
    SADE_SATI_2ND_HOUSE("2nd house", "२औं भाव"),
    SADE_SATI_RISING_HEADER("RISING PHASE CHARACTERISTICS:", "उदय चरण विशेषताहरू:"),
    SADE_SATI_RISING_BEGIN("Beginning of Sade Sati period", "साढ़े साती अवधिको सुरुवात"),
    SADE_SATI_RISING_EXPENSES("Focus on expenses and losses (12th house)", "खर्च र हानिमा केन्द्रित (१२औं भाव)"),
    SADE_SATI_RISING_SLEEP("Sleep disturbances possible", "निद्रामा गडबडी सम्भव"),
    SADE_SATI_RISING_ENEMIES("Hidden enemies may become active", "लुकेका शत्रुहरू सक्रिय हुन सक्छन्"),
    SADE_SATI_RISING_SPIRITUAL("Spiritual growth opportunities", "आध्यात्मिक वृद्धिका अवसरहरू"),
    SADE_SATI_PEAK_HEADER("PEAK PHASE CHARACTERISTICS:", "शिखर चरण विशेषताहरू:"),
    SADE_SATI_PEAK_INTENSE("Most intense phase of Sade Sati", "साढ़े साती को सबैभन्दा तीव्र चरण"),
    SADE_SATI_PEAK_MIND("Direct impact on mind and emotions", "मन र भावनाहरूमा प्रत्यक्ष प्रभाव"),
    SADE_SATI_PEAK_HEALTH("Health may need attention", "स्वास्थ्यमा ध्यान आवश्यक हुन सक्छ"),
    SADE_SATI_PEAK_SELF("Self-image transformation", "आत्म-छवि रूपान्तरण"),
    SADE_SATI_PEAK_RESTRUCTURE("Major life restructuring possible", "प्रमुख जीवन पुनर्संरचना सम्भव"),
    SADE_SATI_SETTING_HEADER("SETTING PHASE CHARACTERISTICS:", "अस्त चरण विशेषताहरू:"),
    SADE_SATI_SETTING_FINAL("Final phase of Sade Sati", "साढ़े साती को अन्तिम चरण"),
    SADE_SATI_SETTING_FINANCES("Focus on finances and family (2nd house)", "वित्त र परिवारमा केन्द्रित (२औं भाव)"),
    SADE_SATI_SETTING_SPEECH("Speech and communication impacted", "वाणी र संचार प्रभावित"),
    SADE_SATI_SETTING_WEALTH("Accumulated wealth may fluctuate", "संचित सम्पत्तिमा उतारचढाव"),
    SADE_SATI_SETTING_LESSONS("Integration of lessons learned", "सिकेका पाठहरूको एकीकरण"),
    SADE_SATI_NOT_ACTIVE_HEADER("SADE SATI NOT ACTIVE", "साढ़े साती सक्रिय छैन"),
    SADE_SATI_NOT_ACTIVE_DESC(
        "Saturn is currently transiting %s, which does not form Sade Sati or Small Panoti with your natal Moon in %s.",
        "शनि हाल %s मा गोचर गर्दैछ, जसले तपाईंको जन्म चन्द्र %s सँग साढ़े साती वा सानो पनोती बनाउँदैन।"
    ),
    SADE_SATI_FAVORABLE_PERIOD(
        "This is generally a favorable period regarding Saturn's influence on emotional and mental well-being.",
        "भावनात्मक र मानसिक कल्याणमा शनिको प्रभाव सम्बन्धमा यो सामान्यतया अनुकूल अवधि हो।"
    ),
    SADE_SATI_SMALL_PANOTI_HEADER("SMALL PANOTI (DHAIYA) ACTIVE", "सानो पनोती (ढैया) सक्रिय"),
    SADE_SATI_FOURTH_TRANSIT("Saturn is transiting the 4th house from your Moon.", "शनि तपाईंको चन्द्रबाट ४औं भावमा गोचर गर्दैछ।"),
    SADE_SATI_FOURTH_DOMESTIC("Domestic peace may be disturbed", "घरेलु शान्ति अशान्त हुन सक्छ"),
    SADE_SATI_FOURTH_MOTHER("Mother's health may need attention", "आमाको स्वास्थ्यमा ध्यान आवश्यक"),
    SADE_SATI_FOURTH_PROPERTY("Property matters require caution", "सम्पत्ति मामिलाहरूमा सावधानी आवश्यक"),
    SADE_SATI_FOURTH_MENTAL("Mental peace may fluctuate", "मानसिक शान्तिमा उतारचढाव"),
    SADE_SATI_ASHTAMA_HEADER("ASHTAMA SHANI - Saturn in 8th from Moon", "अष्टम शनि - चन्द्रबाट ८औं मा शनि"),
    SADE_SATI_ASHTAMA_CHALLENGING(
        "This is considered one of the most challenging Saturn transits.",
        "यो शनिको सबैभन्दा चुनौतीपूर्ण गोचर मध्ये एक मानिन्छ।"
    ),
    SADE_SATI_ASHTAMA_CHANGES("Sudden changes and transformations", "अचानक परिवर्तन र रूपान्तरणहरू"),
    SADE_SATI_ASHTAMA_HEALTH("Health requires vigilance", "स्वास्थ्यमा सतर्कता आवश्यक"),
    SADE_SATI_ASHTAMA_OBSTACLES("Obstacles in ventures", "उद्यमहरूमा अवरोधहरू"),
    SADE_SATI_ASHTAMA_PSYCHOLOGICAL("Deep psychological transformation", "गहिरो मनोवैज्ञानिक रूपान्तरण"),

    // Sade Sati favorable/challenging factors
    SADE_SATI_FACTOR_EXALTED("Saturn is exalted in transit - effects significantly reduced", "शनि गोचरमा उच्च - प्रभावहरू उल्लेखनीय कम"),
    SADE_SATI_FACTOR_OWN_SIGN("Saturn is in own sign - effects well-managed", "शनि स्वराशिमा - प्रभावहरू राम्रोसँग व्यवस्थित"),
    SADE_SATI_FACTOR_YOGAKARAKA("Saturn is Yogakaraka for your ascendant - may bring positive results", "शनि तपाईंको लग्नको लागि योगकारक - सकारात्मक परिणाम ल्याउन सक्छ"),
    SADE_SATI_FACTOR_NATAL_STRONG("Natal Saturn is strong - better equipped to handle transit", "जन्म शनि बलियो - गोचर सम्हाल्न राम्रो सुसज्जित"),
    SADE_SATI_FACTOR_DEBILITATED("Saturn is debilitated in transit - effects may be more challenging", "शनि गोचरमा नीच - प्रभावहरू थप चुनौतीपूर्ण हुन सक्छ"),
    SADE_SATI_FACTOR_WEAK_MOON("Natal Moon is weak - emotional resilience may be tested", "जन्म चन्द्र कमजोर - भावनात्मक लचिलोपन परीक्षण हुन सक्छ"),
    SADE_SATI_FACTOR_NATAL_WEAK("Natal Saturn is weak - transit effects may be more pronounced", "जन्म शनि कमजोर - गोचर प्रभाव थप उच्चारित हुन सक्छ"),
    SADE_SATI_FACTOR_RETROGRADE("Natal Saturn is retrograde - internal processing of karmic lessons", "जन्म शनि वक्री - कार्मिक पाठहरूको आन्तरिक प्रशोधन"),

    // Sade Sati remedy timings
    SADE_SATI_TIMING_SATURN_HORA("Saturday during Saturn Hora", "शनि होरामा शनिबार"),
    SADE_SATI_TIMING_EVERY_SATURDAY("Every Saturday", "हरेक शनिबार"),
    SADE_SATI_TIMING_TUE_SAT("Tuesday and Saturday", "मंगलबार र शनिबार"),

    // Error messages
    ERROR_MOON_NOT_FOUND("Unable to calculate - Moon position not found", "गणना गर्न असमर्थ - चन्द्र स्थिति फेला परेन"),
    ERROR_SADE_SATI_CALC("Unable to calculate Sade Sati - Moon position not available in chart.", "साढ़े साती गणना गर्न असमर्थ - कुण्डलीमा चन्द्र स्थिति उपलब्ध छैन।"),
    ERROR_BAV_NOT_FOUND("Bhinnashtakavarga not found for %s", "%s को लागि भिन्नाष्टकवर्ग फेला परेन"),
    ERROR_ASHTAKAVARGA_NOT_APPLICABLE("Ashtakavarga not applicable for %s", "%s को लागि अष्टकवर्ग लागू हुँदैन"),

    // ============================================
    // SYNASTRY SCREEN STRINGS
    // ============================================
    SYNASTRY_LAGNA("Lagna", "लग्न"),
    SYNASTRY_MOON("Moon", "चन्द्र"),
    SYNASTRY_VENUS("Venus", "शुक्र"),
    SYNASTRY_HOUSE_IN("in House", "भावमा"),

    // ============================================
    // BHRIGU BINDU ANALYSIS
    // ============================================
    BHRIGU_BINDU_TITLE("Bhrigu Bindu Analysis", "भृगु बिन्दु विश्लेषण"),
    BHRIGU_BINDU_SUBTITLE("Karmic Destiny Point", "कार्मिक भाग्य बिन्दु"),
    BHRIGU_BINDU_LONGITUDE("Bhrigu Bindu Longitude", "भृगु बिन्दु देशान्तर"),
    BHRIGU_BINDU_SIGN("Sign", "राशि"),
    BHRIGU_BINDU_NAKSHATRA("Nakshatra", "नक्षत्र"),
    BHRIGU_BINDU_PADA("Pada", "पद"),
    BHRIGU_BINDU_HOUSE("House", "भाव"),
    BHRIGU_BINDU_LORD("Sign Lord", "राशि स्वामी"),
    BHRIGU_BINDU_NAKSHATRA_LORD("Nakshatra Lord", "नक्षत्र स्वामी"),
    BHRIGU_BINDU_STRENGTH("Strength Assessment", "शक्ति मूल्यांकन"),
    BHRIGU_BINDU_EXCELLENT("Excellent", "उत्कृष्ट"),
    BHRIGU_BINDU_GOOD("Good", "राम्रो"),
    BHRIGU_BINDU_MODERATE("Moderate", "मध्यम"),
    BHRIGU_BINDU_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    BHRIGU_BINDU_DIFFICULT("Difficult", "कठिन"),
    BHRIGU_BINDU_ASPECTS("Aspecting Planets", "दृष्टि गर्ने ग्रहहरू"),
    BHRIGU_BINDU_CONJUNCTIONS("Conjunct Planets", "युति ग्रहहरू"),
    BHRIGU_BINDU_TRANSITS("Transit Analysis", "गोचर विश्लेषण"),
    BHRIGU_BINDU_KARMIC_SIGNIFICANCE("Karmic Significance", "कार्मिक महत्त्व"),
    BHRIGU_BINDU_LIFE_AREAS("Life Area Influences", "जीवन क्षेत्र प्रभावहरू"),
    BHRIGU_BINDU_RECOMMENDATIONS("Recommendations", "सिफारिसहरू"),
    BHRIGU_BINDU_AUSPICIOUS_DAYS("Auspicious Days", "शुभ दिनहरू"),
    BHRIGU_BINDU_REMEDIES("Remedial Measures", "उपचारात्मक उपायहरू"),
    BHRIGU_BINDU_ABOUT("About Bhrigu Bindu", "भृगु बिन्दुको बारेमा"),
    BHRIGU_BINDU_ABOUT_DESC("Bhrigu Bindu is a sensitive point calculated from the midpoint of Rahu and Moon. It indicates where significant karmic events manifest in life and is used for precise event timing.", "भृगु बिन्दु राहु र चन्द्रको मध्यबिन्दुबाट गणना गरिएको संवेदनशील बिन्दु हो। यसले जीवनमा महत्त्वपूर्ण कार्मिक घटनाहरू कहाँ प्रकट हुन्छन् भनेर संकेत गर्छ र सटीक घटना समयको लागि प्रयोग गरिन्छ।"),
    BHRIGU_BINDU_CALCULATION("Calculation: (Rahu + Moon) / 2", "गणना: (राहु + चन्द्र) / २"),
    BHRIGU_BINDU_RAHU_POSITION("Rahu Position", "राहु स्थिति"),
    BHRIGU_BINDU_MOON_POSITION("Moon Position", "चन्द्र स्थिति"),

    // ============================================
    // YOGINI DASHA SYSTEM
    // ============================================
    YOGINI_DASHA_TITLE("Yogini Dasha", "योगिनी दशा"),
    YOGINI_DASHA_SUBTITLE("36-Year Cycle Dasha System", "३६-वर्षे चक्र दशा प्रणाली"),
    YOGINI_DASHA_ABOUT("About Yogini Dasha", "योगिनी दशाको बारेमा"),
    YOGINI_DASHA_ABOUT_DESC("Yogini Dasha is a nakshatra-based timing system with a 36-year cycle. It is particularly effective for female horoscopes and relationship timing.", "योगिनी दशा ३६-वर्षे चक्रको साथ नक्षत्रमा आधारित समय प्रणाली हो। यो महिला कुण्डली र सम्बन्ध समयको लागि विशेष प्रभावकारी छ।"),
    YOGINI_DASHA_CURRENT("Current Yogini", "वर्तमान योगिनी"),
    YOGINI_DASHA_ANTARDASHA("Current Antardasha", "वर्तमान अन्तर्दशा"),
    YOGINI_DASHA_BALANCE("Balance at Birth", "जन्ममा सन्तुलन"),
    YOGINI_DASHA_SEQUENCE("Yogini Sequence", "योगिनी क्रम"),
    YOGINI_DASHA_APPLICABILITY("Applicability", "प्रयोज्यता"),
    YOGINI_DASHA_RECOMMENDED("Recommended for this chart", "यो कुण्डलीको लागि सिफारिस"),
    YOGINI_DASHA_TIMELINE("Dasha Timeline", "दशा समयरेखा"),

    // Yogini Names
    YOGINI_MANGALA("Mangala", "मंगला"),
    YOGINI_MANGALA_DESC("Auspicious beginnings, prosperity, and happiness. Moon's nurturing energy.", "शुभ शुरुआत, समृद्धि, र खुशी। चन्द्रको पोषणकारी ऊर्जा।"),
    YOGINI_PINGALA("Pingala", "पिंगला"),
    YOGINI_PINGALA_DESC("Authority, father-related matters, recognition. Sun's illuminating energy.", "अधिकार, पिता-सम्बन्धित मामिलाहरू, मान्यता। सूर्यको प्रकाशित ऊर्जा।"),
    YOGINI_DHANYA("Dhanya", "धान्या"),
    YOGINI_DHANYA_DESC("Wealth, wisdom, children, spiritual growth. Jupiter's expansive grace.", "धन, ज्ञान, सन्तान, आध्यात्मिक वृद्धि। गुरुको विस्तारित कृपा।"),
    YOGINI_BHRAMARI("Bhramari", "भ्रामरी"),
    YOGINI_BHRAMARI_DESC("Energy, conflicts, property matters. Mars brings action and courage.", "ऊर्जा, द्वन्द्व, सम्पत्ति मामिलाहरू। मंगलले कार्य र साहस ल्याउँछ।"),
    YOGINI_BHADRIKA("Bhadrika", "भद्रिका"),
    YOGINI_BHADRIKA_DESC("Intelligence, communication, business success. Mercury's intellectual wit.", "बुद्धि, सञ्चार, व्यापार सफलता। बुधको बौद्धिक कुशलता।"),
    YOGINI_ULKA("Ulka", "उल्का"),
    YOGINI_ULKA_DESC("Hardship, discipline, eventual success. Saturn teaches patience.", "कठिनाई, अनुशासन, अन्ततः सफलता। शनिले धैर्य सिकाउँछ।"),
    YOGINI_SIDDHA("Siddha", "सिद्धा"),
    YOGINI_SIDDHA_DESC("Success, luxury, marriage, artistic achievements. Venus brings love.", "सफलता, विलासिता, विवाह, कलात्मक उपलब्धिहरू। शुक्रले प्रेम ल्याउँछ।"),
    YOGINI_SANKATA("Sankata", "संकटा"),
    YOGINI_SANKATA_DESC("Obstacles, foreign influences, sudden changes. Rahu brings transformation.", "अवरोधहरू, विदेशी प्रभाव, अचानक परिवर्तन। राहुले रूपान्तरण ल्याउँछ।"),

    // Yogini period durations
    YOGINI_YEARS_1("1 Year", "१ वर्ष"),
    YOGINI_YEARS_2("2 Years", "२ वर्ष"),
    YOGINI_YEARS_3("3 Years", "३ वर्ष"),
    YOGINI_YEARS_4("4 Years", "४ वर्ष"),
    YOGINI_YEARS_5("5 Years", "५ वर्ष"),
    YOGINI_YEARS_6("6 Years", "६ वर्ष"),
    YOGINI_YEARS_7("7 Years", "७ वर्ष"),
    YOGINI_YEARS_8("8 Years", "८ वर्ष"),

    // Yogini nature
    YOGINI_NATURE_HIGHLY_AUSPICIOUS("Highly Auspicious", "अत्यन्त शुभ"),
    YOGINI_NATURE_AUSPICIOUS("Auspicious", "शुभ"),
    YOGINI_NATURE_MIXED("Mixed", "मिश्रित"),
    YOGINI_NATURE_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    YOGINI_NATURE_DIFFICULT("Difficult", "कठिन"),

    // Yogini effects labels
    YOGINI_GENERAL_EFFECTS("General Effects", "सामान्य प्रभावहरू"),
    YOGINI_CAREER_EFFECTS("Career Effects", "क्यारियर प्रभावहरू"),
    YOGINI_RELATIONSHIP_EFFECTS("Relationship Effects", "सम्बन्ध प्रभावहरू"),
    YOGINI_HEALTH_EFFECTS("Health Effects", "स्वास्थ्य प्रभावहरू"),
    YOGINI_SPIRITUAL_EFFECTS("Spiritual Effects", "आध्यात्मिक प्रभावहरू"),
    YOGINI_CAUTION_AREAS("Caution Areas", "सावधानी क्षेत्रहरू"),

    // Mangala (Moon)
    YOGINI_MANGALA_CAREER("Career brings emotional satisfaction. Good for nurturing professions, public relations, and hospitality industry.", "क्यारियरले भावनात्मक सन्तुष्टि ल्याउँछ। हेरचाह गर्ने पेशाहरू, जनसम्पर्क, र आतिथ्य उद्योगको लागि राम्रो।"),
    YOGINI_MANGALA_RELATIONSHIP("Emotional connections deepen. Good for starting relationships. Mother's influence prominent. Nurturing partnerships.", "भावनात्मक सम्बन्ध गहिरो हुन्छ। सम्बन्ध सुरु गर्न राम्रो। आमाको प्रभाव प्रमुख। पालनपोषण गर्ने साझेदारी।"),
    YOGINI_MANGALA_HEALTH("Generally good health. Focus on emotional and mental well-being. Water-related activities beneficial.", "सामान्यतया राम्रो स्वास्थ्य। भावनात्मक र मानसिक कल्याणमा ध्यान दिनुहोस्। पानी सम्बन्धित गतिविधिहरू लाभदायक।"),
    YOGINI_MANGALA_SPIRITUAL("Devotional practices strengthen. Mother goddess worship beneficial. Emotional cleansing through meditation.", "भक्ति अभ्यास बलियो हुन्छ। मातृ देवीको पूजा लाभदायक। ध्यान मार्फत भावनात्मक सफाई।"),
    YOGINI_MANGALA_REC_1("Wear pearl or moonstone", "मोती वा चन्द्रकान्त मणि लगाउनुहोस्"),
    YOGINI_MANGALA_REC_2("Honor your mother and maternal figures", "आमा र मातृ व्यक्तित्वहरूको सम्मान गर्नुहोस्"),
    YOGINI_MANGALA_REC_3("Practice moon salutations", "चन्द्र नमस्कार अभ्यास गर्नुहोस्"),
    YOGINI_MANGALA_REC_4("Donate white items on Mondays", "सोमबार सेतो वस्तुहरू दान गर्नुहोस्"),
    YOGINI_MANGALA_CAUTION_1("Emotional volatility", "भावनात्मक अस्थिरता"),
    YOGINI_MANGALA_CAUTION_2("Over-attachment", "अत्यधिक लगाव"),
    YOGINI_MANGALA_CAUTION_3("Water-related issues", "पानी सम्बन्धित समस्याहरू"),

    // Pingala (Sun)
    YOGINI_PINGALA_CAREER("Leadership opportunities arise. Government jobs, authority positions, and recognition in career. Father may influence career.", "नेतृत्वको अवसर आउँछ। सरकारी जागिर, अधिकारिक पद, र क्यारियरमा मान्यता। पिताले क्यारियरलाई प्रभाव पार्न सक्छन्।"),
    YOGINI_PINGALA_RELATIONSHIP("Relationships with authority figures. Father-related matters in marriage. May face ego conflicts with partners.", "अधिकारी वर्गसँग सम्बन्ध। विवाहमा पिता सम्बन्धित विषयहरू। साझेदारहरूसँग अहंकार द्वन्द्व हुन सक्छ।"),
    YOGINI_PINGALA_HEALTH("Watch heart, eyes, and overall vitality. Morning sun exposure beneficial. Maintain healthy ego.", "मुटु, आँखा, र समग्र जीवनशक्तिमा ध्यान दिनुहोस्। बिहानी घाम लाभदायक। स्वस्थ अहंकार कायम राख्नुहोस्।"),
    YOGINI_PINGALA_SPIRITUAL("Solar meditation and surya namaskar beneficial. Connection with divine father principle. Self-realization focus.", "सूर्य ध्यान र सूर्य नमस्कार लाभदायक। दिव्य पिता तत्त्वसँग सम्बन्ध। आत्म-साक्षात्कारमा ध्यान।"),
    YOGINI_PINGALA_REC_1("Wear ruby if suitable", "उपयुक्त भएमा माणिक लगाउनुहोस्"),
    YOGINI_PINGALA_REC_2("Practice Surya Namaskar at sunrise", "सूर्योदयमा सूर्य नमस्कार अभ्यास गर्नुहोस्"),
    YOGINI_PINGALA_REC_3("Honor your father", "पिताको सम्मान गर्नुहोस्"),
    YOGINI_PINGALA_REC_4("Donate wheat or jaggery on Sundays", "आइतबार गहुँ वा सख्खर दान गर्नुहोस्"),
    YOGINI_PINGALA_CAUTION_1("Ego conflicts", "अहंकार द्वन्द्व"),
    YOGINI_PINGALA_CAUTION_2("Eye problems", "आँखा समस्याहरू"),
    YOGINI_PINGALA_CAUTION_3("Father's health", "पिताको स्वास्थ्य"),

    // Dhanya (Jupiter)
    YOGINI_DHANYA_CAREER("Expansion in career, promotions, and higher learning. Excellent for teaching, consulting, and advisory roles.", "क्यारियरमा विस्तार, पदोन्नति, र उच्च शिक्षा। शिक्षण, परामर्श, र सल्लाहकार भूमिकाको लागि उत्कृष्ट।"),
    YOGINI_DHANYA_RELATIONSHIP("Excellent for marriage and childbirth. Spiritual connections in relationships. Teachers and mentors become important.", "विवाह र सन्तानको लागि उत्कृष्ट। सम्बन्धमा आध्यात्मिक सम्बन्ध। शिक्षक र गुरुहरू महत्त्वपूर्ण हुन्छन्।"),
    YOGINI_DHANYA_HEALTH("Good health generally. Watch liver and weight. Spiritual practices enhance well-being.", "सामान्यतया राम्रो स्वास्थ्य। कलेजो र तौलमा ध्यान दिनुहोस्। आध्यात्मिक अभ्यासले कल्याण बढाउँछ।"),
    YOGINI_DHANYA_SPIRITUAL("Excellent for spiritual growth and higher learning. Guru connection strengthens. Sacred knowledge flows.", "आध्यात्मिक वृद्धि र उच्च शिक्षाको लागि उत्कृष्ट। गुरु सम्बन्ध बलियो हुन्छ। पवित्र ज्ञान प्रवाहित हुन्छ।"),
    YOGINI_DHANYA_REC_1("Wear yellow sapphire if suitable", "उपयुक्त भएमा पुष्पराज लगाउनुहोस्"),
    YOGINI_DHANYA_REC_2("Seek blessings from teachers", "शिक्षकहरूबाट आशीर्वाद लिनुहोस्"),
    YOGINI_DHANYA_REC_3("Study sacred texts", "पवित्र ग्रन्थहरूको अध्ययन गर्नुहोस्"),
    YOGINI_DHANYA_REC_4("Donate to educational causes on Thursdays", "बिहीबार शैक्षिक कार्यमा दान गर्नुहोस्"),
    YOGINI_DHANYA_CAUTION_1("Over-expansion", "अत्यधिक विस्तार"),
    YOGINI_DHANYA_CAUTION_2("Weight issues", "तौल समस्याहरू"),
    YOGINI_DHANYA_CAUTION_3("Over-optimism", "अत्यधिक आशावाद"),

    // Bhramari (Mars)
    YOGINI_BHRAMARI_CAREER("Active period in career with competition. Good for technical, engineering, and defense-related fields. Property dealings.", "प्रतिस्पर्धा सहित क्यारियरमा सक्रिय अवधि। प्राविधिक, इन्जिनियरिङ, र रक्षा सम्बन्धित क्षेत्रहरूको लागि राम्रो। सम्पत्ति कारोबार।"),
    YOGINI_BHRAMARI_RELATIONSHIP("Passionate but potentially conflicting relationships. Brothers/sisters prominent. Physical attraction strong.", "जोसिलो तर सम्भावित द्वन्द्वपूर्ण सम्बन्ध। दाजुभाइ/दिदीबहिनी प्रमुख। शारीरिक आकर्षण बलियो।"),
    YOGINI_BHRAMARI_HEALTH("Watch for injuries, inflammations, and accidents. Physical exercise important but avoid overexertion.", "चोटपटक, सुजन, र दुर्घटनाहरूबाट बच्नुहोस्। शारीरिक व्यायाम महत्त्वपूर्ण तर अत्यधिक परिश्रम नगर्नुहोस्।"),
    YOGINI_BHRAMARI_SPIRITUAL("Active spiritual practices like yoga and pranayama. Kundalini may activate. Mars-related deity worship helps.", "योग र प्राणायाम जस्ता सक्रिय आध्यात्मिक अभ्यासहरू। कुण्डलिनी सक्रिय हुन सक्छ। मंगल सम्बन्धित देवता पूजाले मद्दत गर्छ।"),
    YOGINI_BHRAMARI_REC_1("Wear red coral if suitable", "उपयुक्त भएमा मुगा लगाउनुहोस्"),
    YOGINI_BHRAMARI_REC_2("Practice physical exercise regularly", "नियमित शारीरिक व्यायाम गर्नुहोस्"),
    YOGINI_BHRAMARI_REC_3("Channel energy constructively", "ऊर्जालाई रचनात्मक रूपमा प्रयोग गर्नुहोस्"),
    YOGINI_BHRAMARI_REC_4("Donate red items on Tuesdays", "मंगलबार रातो वस्तुहरू दान गर्नुहोस्"),
    YOGINI_BHRAMARI_CAUTION_1("Anger management", "क्रोध व्यवस्थापन"),
    YOGINI_BHRAMARI_CAUTION_2("Accidents", "दुर्घटनाहरू"),
    YOGINI_BHRAMARI_CAUTION_3("Property disputes", "सम्पत्ति विवाद"),

    // Bhadrika (Mercury)
    YOGINI_BHADRIKA_CAREER("Business acumen peaks. Excellent for trade, communication, writing, and intellectual pursuits. Good for learning new skills.", "व्यापारिक कौशल शिखरमा। व्यापार, सञ्चार, लेखन, र बौद्धिक कार्यहरूको लागि उत्कृष्ट। नयाँ सीपहरू सिक्न राम्रो।"),
    YOGINI_BHADRIKA_RELATIONSHIP("Communication improves relationships. Intellectual compatibility matters. Good for understanding partners better.", "सञ्चारले सम्बन्ध सुधार गर्छ। बौद्धिक अनुकूलता महत्त्वपूर्ण छ। साझेदारहरूलाई राम्रोसँग बुझ्नको लागि राम्रो।"),
    YOGINI_BHADRIKA_HEALTH("Nervous system needs attention. Skin issues possible. Mental relaxation techniques helpful.", "स्नायु प्रणालीमा ध्यान आवश्यक छ। छाला समस्याहरू सम्भव छ। मानसिक विश्राम प्रविधिहरू लाभदायक।"),
    YOGINI_BHADRIKA_SPIRITUAL("Intellectual approach to spirituality. Study of scriptures beneficial. Mantra practice effective.", "आध्यात्मिकतामा बौद्धिक दृष्टिकोण। शास्त्रहरूको अध्ययन लाभदायक। मन्त्र अभ्यास प्रभावकारी।"),
    YOGINI_BHADRIKA_REC_1("Wear emerald if suitable", "उपयुक्त भएमा पन्ना लगाउनुहोस्"),
    YOGINI_BHADRIKA_REC_2("Engage in learning and communication", "सिक्ने र सञ्चारमा संलग्न हुनुहोस्"),
    YOGINI_BHADRIKA_REC_3("Write and express yourself", "लेख्नुहोस् र आफूलाई व्यक्त गर्नुहोस्"),
    YOGINI_BHADRIKA_REC_4("Donate green items on Wednesdays", "बुधबार हरियो वस्तुहरू दान गर्नुहोस्"),
    YOGINI_BHADRIKA_CAUTION_1("Nervousness", "घबराहट"),
    YOGINI_BHADRIKA_CAUTION_2("Overthinking", "अत्यधिक सोच"),
    YOGINI_BHADRIKA_CAUTION_3("Skin issues", "छाला समस्याहरू"),

    // Ulka (Saturn)
    YOGINI_ULKA_CAREER("Slow but steady career progress. Hard work brings delayed rewards. Good for perseverance in long-term projects.", "सुस्त तर स्थिर क्यारियर प्रगति। कडा परिश्रमले ढिलो पुरस्कार ल्याउँछ। दीर्घकालीन परियोजनाहरूमा धैर्यताको लागि राम्रो।"),
    YOGINI_ULKA_RELATIONSHIP("Delays or challenges in relationships. Karmic partners appear. Long-distance relationships possible.", "सम्बन्धमा ढिलाइ वा चुनौतीहरू। कार्मिक साझेदारहरू देखा पर्छन्। लामो-दूरीको सम्बन्ध सम्भव छ।"),
    YOGINI_ULKA_HEALTH("Chronic issues may arise. Bones, joints, and teeth need care. Patience in recovery.", "दीर्घकालीन समस्याहरू उत्पन्न हुन सक्छ। हड्डी, जोर्नी, र दाँतको हेरचाह आवश्यक। निको हुन धैर्यता।"),
    YOGINI_ULKA_SPIRITUAL("Deep karmic cleansing period. Meditation on impermanence. Service-oriented spirituality.", "गहिरो कार्मिक सफाई अवधि। अनित्यतामा ध्यान। सेवा-उन्मुख आध्यात्मिकता।"),
    YOGINI_ULKA_REC_1("Wear blue sapphire with caution", "सावधानीपूर्वक नीलम लगाउनुहोस्"),
    YOGINI_ULKA_REC_2("Practice patience and discipline", "धैर्य र अनुशासन अभ्यास गर्नुहोस्"),
    YOGINI_ULKA_REC_3("Serve the elderly and disadvantaged", "वृद्ध र विपन्नहरूको सेवा गर्नुहोस्"),
    YOGINI_ULKA_REC_4("Donate oil and black items on Saturdays", "शनिबार तेल र कालो वस्तुहरू दान गर्नुहोस्"),
    YOGINI_ULKA_CAUTION_1("Depression", "अवसाद"),
    YOGINI_ULKA_CAUTION_2("Delays", "ढिलाइ"),
    YOGINI_ULKA_CAUTION_3("Chronic health issues", "दीर्घकालीन स्वास्थ्य समस्याहरू"),

    // Siddha (Venus)
    YOGINI_SIDDHA_CAREER("Career success through creativity and charm. Excellent for arts, entertainment, luxury goods, and beauty industries.", "सृजनशीलता र आकर्षण मार्फत क्यारियर सफलता। कला, मनोरञ्जन, विलासिताका सामान, र सौन्दर्य उद्योगका लागि उत्कृष्ट।"),
    YOGINI_SIDDHA_RELATIONSHIP("Excellent for romance, marriage, and love. Harmonious relationships. Beauty and pleasure in partnerships.", "रोमान्स, विवाह, र प्रेमको लागि उत्कृष्ट। सामंजस्यपूर्ण सम्बन्ध। साझेदारीमा सौन्दर्य र आनन्द।"),
    YOGINI_SIDDHA_HEALTH("Generally good health. Watch reproductive system. Beauty treatments beneficial.", "सामान्यतया राम्रो स्वास्थ्य। प्रजनन प्रणालीमा ध्यान दिनुहोस्। सौन्दर्य उपचार लाभदायक।"),
    YOGINI_SIDDHA_SPIRITUAL("Tantric practices may attract. Beauty in spirituality. Heart-centered practices beneficial.", "तान्त्रिक अभ्यासहरूले आकर्षित गर्न सक्छ। आध्यात्मिकतामा सौन्दर्य। हृदय-केन्द्रित अभ्यासहरू लाभदायक।"),
    YOGINI_SIDDHA_REC_1("Wear diamond or white sapphire if suitable", "उपयुक्त भएमा हीरा वा सेतो पुखराज लगाउनुहोस्"),
    YOGINI_SIDDHA_REC_2("Engage in arts and creativity", "कला र सृजनशीलतामा संलग्न हुनुहोस्"),
    YOGINI_SIDDHA_REC_3("Cultivate harmonious relationships", "सामंजस्यपूर्ण सम्बन्ध विकास गर्नुहोस्"),
    YOGINI_SIDDHA_REC_4("Donate white items on Fridays", "शुक्रबार सेतो वस्तुहरू दान गर्नुहोस्"),
    YOGINI_SIDDHA_CAUTION_1("Indulgence", "भोगविला"),
    YOGINI_SIDDHA_CAUTION_2("Relationship complications", "सम्बन्ध जटिलताहरू"),
    YOGINI_SIDDHA_CAUTION_3("Luxury overspending", "विलासितामा अत्यधिक खर्च"),

    // Sankata (Rahu)
    YOGINI_SANKATA_CAREER("Unconventional career paths, foreign opportunities. May bring sudden changes in profession. Research and technology favored.", "अपरम्परागत क्यारियर पथहरू, विदेशी अवसरहरू। पेशामा अचानक परिवर्तन ल्याउन सक्छ। अनुसन्धान र प्रविधि अनुकूल।"),
    YOGINI_SANKATA_RELATIONSHIP("Unusual or foreign partners. Sudden attractions or separations. Need to address karmic relationship patterns.", "असामान्य वा विदेशी साझेदारहरू। अचानक आकर्षण वा अलगाव। कार्मिक सम्बन्ध ढाँचाहरू सम्बोधन गर्न आवश्यक।"),
    YOGINI_SANKATA_HEALTH("Unusual or hard-to-diagnose health issues. Mental health important. Alternative therapies may help.", "असामान्य वा निदान गर्न गाह्रो स्वास्थ्य समस्याहरू। मानसिक स्वास्थ्य महत्त्वपूर्ण। वैकल्पिक उपचारले मद्दत गर्न सक्छ।"),
    YOGINI_SANKATA_SPIRITUAL("Deep transformation possible. Occult interests may arise. Breaking free from illusions.", "गहिरो रूपान्तरण सम्भव छ। तन्त्रमन्त्रमा रुचि जाग्न सक्छ। भ्रमबाट मुक्ति।"),
    YOGINI_SANKATA_REC_1("Wear hessonite after careful analysis", "सावधानीपूर्वक विश्लेषण पछि गोमेद लगाउनुहोस्"),
    YOGINI_SANKATA_REC_2("Stay grounded during sudden changes", "अचानक परिवर्तनको समयमा स्थिर रहनुहोस्"),
    YOGINI_SANKATA_REC_3("Address past-life patterns through meditation", "ध्यान मार्फत पूर्व-जन्मका ढाँचाहरू सम्बोधन गर्नुहोस्"),
    YOGINI_SANKATA_REC_4("Donate blue/black items on Saturdays", "शनिबार निलो/कालो वस्तुहरू दान गर्नुहोस्"),
    YOGINI_SANKATA_CAUTION_1("Sudden obstacles", "अचानक अवरोधहरू"),
    YOGINI_SANKATA_CAUTION_2("Confusion", "भ्रम"),
    YOGINI_SANKATA_CAUTION_3("Unusual diseases", "असामान्य रोगहरू"),

    // Antardasha Relationships
    YOGINI_ANTAR_FRIENDLY(
        "%s-%s: Harmonious sub-period with complementary energies. %s's significations blend well with %s's ongoing influence. Good for collaborative efforts and relationship building.",
        "%s-%s: पूरक ऊर्जाको साथ सामंजस्यपूर्ण उप-अवधि। %s को प्रभावहरू %s को चलिरहेको प्रभावसँग राम्रोसँग मिल्छन्। सहयोगात्मक प्रयास र सम्बन्ध निर्माणको लागि राम्रो।"
    ),
    YOGINI_ANTAR_HOSTILE(
        "%s-%s: Potentially challenging sub-period with conflicting energies. %s may create tension with %s's ongoing themes. Requires patience, remedies, and conscious effort for harmony.",
        "%s-%s: द्वन्द्वपूर्ण ऊर्जाको साथ सम्भावित चुनौतीपूर्ण उप-अवधि। %s ले %s को चलिरहेको विषयहरूसँग तनाव सिर्जना गर्न सक्छ। धैर्य, उपाय, र सद्भावको लागि सचेत प्रयास आवश्यक।"
    ),
    YOGINI_ANTAR_NEUTRAL(
        "%s-%s: Balanced sub-period requiring conscious integration. %s's themes activate within %s's framework. Results depend on individual chart placements.",
        "%s-%s: सचेत एकीकरण आवश्यक पर्ने सन्तुलित उप-अवधि। %s को विषयहरू %s को ढाँचा भित्र सक्रिय हुन्छन्। परिणामहरू व्यक्तिगत कुण्डली स्थितिमा निर्भर हुन्छन्।"
    ),

    // Applicability Reasons
    YOGINI_APP_UNIVERSAL("Yogini Dasha is universally applicable for timing specific events", "विशिष्ट घटनाहरूको समय निर्धारणको लागि योगिनी दशा सर्वव्यापी रूपमा लागू हुन्छ"),
    YOGINI_APP_FEMALE("Yogini Dasha is traditionally considered more accurate for female horoscopes", "योगिनी दशा परम्परागत रूपमा महिला कुण्डलीको लागि बढी सही मानिन्छ"),
    YOGINI_APP_STRONG_MOON("Strong Moon in %s enhances Yogini Dasha applicability", "%s मा बलियो चन्द्रले योगिनी दशाको प्रयोज्यता बढाउँछ"),
    YOGINI_APP_NIGHT_BIRTH("Night birth traditionally favors Yogini Dasha", "रात्रि जन्मले परम्परागत रूपमा योगिनी दशालाई पक्ष दिन्छ"),
    YOGINI_APP_RELATIONSHIP("Yogini Dasha excels at timing relationship and marriage events", "योगिनी दशा सम्बन्ध र विवाह घटनाहरूको समय निर्धारणमा उत्कृष्ट छ"),
    YOGINI_APP_VALIDATION("Can be used alongside Vimsottari Dasha for validation", "पुष्टिको लागि विंशोत्तरी दशासँगै प्रयोग गर्न सकिन्छ"),

    // ============================================
    // ARGALA (JAIMINI) ANALYSIS
    // ============================================
    ARGALA_TITLE("Argala Analysis", "अर्गला विश्लेषण"),
    ARGALA_SUBTITLE("Jaimini Intervention System", "जैमिनी हस्तक्षेप प्रणाली"),
    ARGALA_ABOUT("About Argala", "अर्गलाको बारेमा"),
    ARGALA_ABOUT_DESC("Argala is a Jaimini system showing how planets in certain houses create interventions (Argala) that influence the results of other houses. Understanding Argala helps predict how life events unfold.", "अर्गला एक जैमिनी प्रणाली हो जसले निश्चित भावहरूमा ग्रहहरूले कसरी हस्तक्षेप (अर्गला) सिर्जना गर्छन् जसले अन्य भावहरूको परिणामलाई प्रभावित गर्छ भनेर देखाउँछ। अर्गला बुझ्दा जीवनका घटनाहरू कसरी प्रकट हुन्छन् भनेर भविष्यवाणी गर्न मद्दत गर्छ।"),
    ARGALA_PRIMARY("Primary Argala", "प्राथमिक अर्गला"),
    ARGALA_SECONDARY("Secondary Argala", "माध्यमिक अर्गला"),
    ARGALA_VIRODHA("Virodha Argala (Obstruction)", "विरोध अर्गला (बाधा)"),
    ARGALA_SHUBHA("Shubha Argala (Benefic)", "शुभ अर्गला (लाभकारी)"),
    ARGALA_ASHUBHA("Ashubha Argala (Malefic)", "अशुभ अर्गला (हानिकारक)"),
    ARGALA_UNOBSTRUCTED("Unobstructed Argala", "अबाधित अर्गला"),
    ARGALA_OBSTRUCTED("Obstructed Argala", "बाधित अर्गला"),
    ARGALA_NET_EFFECT("Net Effect", "शुद्ध प्रभाव"),
    ARGALA_POSITIVE("Positive", "सकारात्मक"),
    ARGALA_NEGATIVE("Negative", "नकारात्मक"),
    ARGALA_MIXED("Mixed", "मिश्रित"),
    ARGALA_FROM_HOUSE("From House %d", "भाव %d बाट"),
    ARGALA_TO_HOUSE("To House %d", "भाव %d मा"),
    ARGALA_SECOND_HOUSE("2nd House Argala", "द्वितीय भाव अर्गला"),
    ARGALA_FOURTH_HOUSE("4th House Argala", "चतुर्थ भाव अर्गला"),
    ARGALA_ELEVENTH_HOUSE("11th House Argala", "एकादश भाव अर्गला"),
    ARGALA_FIFTH_HOUSE("5th House Argala", "पञ्चम भाव अर्गला"),
    ARGALA_KARMA_DHARMA("Dharma Argala", "धर्म अर्गला"),
    ARGALA_KARMA_ARTHA("Artha Argala", "अर्थ अर्गला"),
    ARGALA_KARMA_KAMA("Kama Argala", "काम अर्गला"),
    ARGALA_KARMA_MOKSHA("Moksha Argala", "मोक्ष अर्गला"),
    ARGALA_HOUSE_STRENGTH("House Argala Strength", "भाव अर्गला बल"),
    ARGALA_PLANET_CAUSES("Planet Argala Effects", "ग्रह अर्गला प्रभाव"),

    // ============================================
    // CHARA DASHA (JAIMINI) SYSTEM
    // ============================================
    CHARA_DASHA_TITLE("Chara Dasha", "चर दशा"),
    CHARA_DASHA_SUBTITLE("Jaimini Sign-Based Dasha", "जैमिनी राशि-आधारित दशा"),
    CHARA_DASHA_ABOUT("About Chara Dasha", "चर दशाको बारेमा"),
    CHARA_DASHA_ABOUT_DESC("Chara Dasha is Jaimini's primary sign-based timing system. Unlike Vimsottari which uses planets, Chara Dasha uses zodiac signs as periods, providing unique insights into life events.", "चर दशा जैमिनीको प्राथमिक राशि-आधारित समय प्रणाली हो। विम्सोत्तरी जसले ग्रह प्रयोग गर्छ त्यसको विपरीत, चर दशाले अवधिको रूपमा राशिहरू प्रयोग गर्छ, जीवनका घटनाहरूमा अद्वितीय अन्तर्दृष्टि प्रदान गर्छ।"),
    CHARA_DASHA_CURRENT("Current Sign Dasha", "वर्तमान राशि दशा"),
    CHARA_DASHA_ANTARDASHA("Current Antardasha", "वर्तमान अन्तर्दशा"),
    CHARA_DASHA_SEQUENCE("Dasha Sequence", "दशा क्रम"),
    CHARA_DASHA_DIRECTION("Dasha Direction", "दशा दिशा"),
    CHARA_DASHA_DIRECT("Direct (Zodiacal)", "सीधो (राशिक्रम)"),
    CHARA_DASHA_REVERSE("Reverse (Anti-zodiacal)", "उल्टो (विपरीत-राशिक्रम)"),
    CHARA_DASHA_ODD_LAGNA("Odd Sign Lagna", "विषम राशि लग्न"),
    CHARA_DASHA_EVEN_LAGNA("Even Sign Lagna", "सम राशि लग्न"),
    CHARA_DASHA_YEARS("Years: %d", "वर्ष: %d"),
    CHARA_DASHA_TIMELINE("Chara Dasha Timeline", "चर दशा समयरेखा"),

    // Chara Karakas
    KARAKA_TITLE("Chara Karakas", "चर कारकहरू"),
    KARAKA_SUBTITLE("Jaimini Variable Significators", "जैमिनी परिवर्तनशील कारकहरू"),
    KARAKA_ABOUT("About Chara Karakas", "चर कारकको बारेमा"),
    KARAKA_ABOUT_DESC("Chara Karakas are Jaimini's system of variable significators based on planetary degrees. Unlike fixed karakas, these change based on each chart.", "चर कारकहरू ग्रहको अंशमा आधारित जैमिनीको परिवर्तनशील कारक प्रणाली हो। स्थिर कारकहरूको विपरीत, यी प्रत्येक कुण्डली अनुसार परिवर्तन हुन्छन्।"),
    KARAKA_AK("Atmakaraka (AK)", "आत्मकारक (AK)"),
    KARAKA_AK_DESC("Soul significator - Highest degree planet, represents the self and spiritual evolution", "आत्मा कारक - उच्चतम अंश ग्रह, आत्म र आध्यात्मिक विकासको प्रतिनिधित्व"),
    KARAKA_AMK("Amatyakaraka (AmK)", "अमात्यकारक (AmK)"),
    KARAKA_AMK_DESC("Minister significator - Career, profession, and life purpose", "मन्त्री कारक - क्यारियर, पेशा, र जीवन उद्देश्य"),
    KARAKA_BK("Bhratrikaraka (BK)", "भ्रातृकारक (BK)"),
    KARAKA_BK_DESC("Sibling significator - Brothers, sisters, and courage", "भाइबहिनी कारक - भाइ, बहिनी, र साहस"),
    KARAKA_MK("Matrikaraka (MK)", "मातृकारक (MK)"),
    KARAKA_MK_DESC("Mother significator - Mother, nurturing, emotional foundation", "आमा कारक - आमा, पालनपोषण, भावनात्मक आधार"),
    KARAKA_PIK("Pitrikaraka (PiK)", "पितृकारक (PiK)"),
    KARAKA_PIK_DESC("Father significator - Father, authority, guidance", "बुबा कारक - बुबा, अधिकार, मार्गदर्शन"),
    KARAKA_PUK("Putrakaraka (PuK)", "पुत्रकारक (PuK)"),
    KARAKA_PUK_DESC("Children significator - Children, creativity, intelligence", "सन्तान कारक - सन्तान, सृजनशीलता, बुद्धि"),
    KARAKA_GK("Gnatikaraka (GK)", "ज्ञातिकारक (GK)"),
    KARAKA_GK_DESC("Relative significator - Relatives, obstacles, competition", "सम्बन्धी कारक - आफन्त, बाधा, प्रतिस्पर्धा"),
    KARAKA_DK("Darakaraka (DK)", "दारकारक (DK)"),
    KARAKA_DK_DESC("Spouse significator - Marriage partner, business partners", "जीवनसाथी कारक - विवाह साथी, व्यापार साझेदार"),
    KARAKAMSHA_TITLE("Karakamsha", "कारकांश"),
    KARAKAMSHA_DESC("Navamsa sign of Atmakaraka - Key for spiritual and material destiny", "आत्मकारकको नवांश राशि - आध्यात्मिक र भौतिक भाग्यको लागि महत्त्वपूर्ण"),

    // ============================================
    // SHODASHVARGA (16 DIVISIONAL CHARTS) STRENGTH
    // ============================================
    SHODASHVARGA_TITLE("Shodashvarga Strength", "षोडशवर्ग बल"),
    SHODASHVARGA_SUBTITLE("16-Divisional Chart Analysis", "१६-विभाजन चार्ट विश्लेषण"),
    SHODASHVARGA_ABOUT("About Shodashvarga", "षोडशवर्गको बारेमा"),
    SHODASHVARGA_ABOUT_DESC("Shodashvarga Bala evaluates planetary strength across all 16 divisional charts. This comprehensive analysis reveals how effectively each planet can deliver its results.", "षोडशवर्ग बलले सबै १६ विभाजन चार्टहरूमा ग्रह बलको मूल्यांकन गर्दछ। यो व्यापक विश्लेषणले प्रत्येक ग्रहले आफ्नो परिणाम कति प्रभावकारी रूपमा प्रदान गर्न सक्छ भनेर प्रकट गर्दछ।"),
    SHODASHVARGA_BALA("Shodashvarga Bala", "षोडशवर्ग बल"),
    SHADVARGA_BALA("Shadvarga Bala (6-Varga)", "षड्वर्ग बल (६-वर्ग)"),
    SAPTAVARGA_BALA("Saptavarga Bala (7-Varga)", "सप्तवर्ग बल (७-वर्ग)"),
    DASHAVARGA_BALA("Dashavarga Bala (10-Varga)", "दशवर्ग बल (१०-वर्ग)"),
    VIMSOPAKA_BALA("Vimsopaka Bala", "विम्शोपक बल"),
    VIMSOPAKA_POORVA("Poorva Scheme", "पूर्व योजना"),
    VIMSOPAKA_MADHYA("Madhya Scheme", "मध्य योजना"),
    VIMSOPAKA_PARA("Para Scheme", "पर योजना"),
    VARGOTTAMA_TITLE("Vargottama Planets", "वर्गोत्तम ग्रहहरू"),
    VARGOTTAMA_DESC("Planet in same sign in D1 and divisional chart", "D1 र विभाजन चार्टमा समान राशिमा ग्रह"),
    VARGOTTAMA_NAVAMSA("Navamsa Vargottama", "नवांश वर्गोत्तम"),
    VARGOTTAMA_DASHAMSA("Dashamsa Vargottama", "दशांश वर्गोत्तम"),
    DIGNITY_EXALTED("Exalted", "उच्च"),
    DIGNITY_MOOLATRIKONA("Moolatrikona", "मूलत्रिकोण"),
    DIGNITY_OWN_SIGN("Own Sign", "स्वराशि"),
    DIGNITY_GREAT_FRIEND("Great Friend", "अधिमित्र"),
    DIGNITY_FRIEND("Friend", "मित्र"),
    DIGNITY_NEUTRAL("Neutral", "सम"),
    DIGNITY_ENEMY("Enemy", "शत्रु"),
    DIGNITY_GREAT_ENEMY("Great Enemy", "अधिशत्रु"),
    DIGNITY_DEBILITATED("Debilitated", "नीच"),
    STRENGTH_EXCELLENT("Excellent", "उत्कृष्ट"),
    STRENGTH_GOOD("Good", "राम्रो"),
    STRENGTH_AVERAGE("Average", "औसत"),
    STRENGTH_WEAK("Weak", "कमजोर"),
    STRENGTH_VERY_WEAK("Very Weak", "अति कमजोर"),
    STRONGEST_PLANET("Strongest Planet", "बलवान ग्रह"),
    WEAKEST_PLANET("Weakest Planet", "दुर्बल ग्रह"),
    AVERAGE_STRENGTH("Average Strength", "औसत बल"),
    KEY_INSIGHTS("Key Insights", "मुख्य अन्तर्दृष्टि"),
    VARGA_POSITIONS("Varga Positions", "वर्ग स्थितिहरू"),
    SHODASHVARGA_CALCULATION_ERROR("Failed to calculate Shodashvarga analysis", "षोडशवर्ग विश्लेषण गणना गर्न असफल"),
    SHODASHVARGA_VARGOTTAMA_COUNT_FMT("Vargottama in %d chart(s)", "वर्गोत्तम %d कुण्डलीमा"),
    SHODASHVARGA_NO_VARGOTTAMA("No Vargottama planets found", "कुनै वर्गोत्तम ग्रह फेला परेन"),
    SHODASHVARGA_16_DIAGRAMS_HEADER("The 16 Divisional Charts:", "१६ षोडशवर्ग कुण्डलीहरू:"),

    // ============================================
    // MRITYU BHAGA (SENSITIVE DEGREES) ANALYSIS
    // ============================================
    MRITYU_BHAGA_TITLE("Mrityu Bhaga Analysis", "मृत्यु भाग विश्लेषण"),
    MRITYU_BHAGA_SUBTITLE("Sensitive Degrees Analysis", "संवेदनशील अंश विश्लेषण"),
    MRITYU_BHAGA_ABOUT("About Mrityu Bhaga", "मृत्यु भागको बारेमा"),
    MRITYU_BHAGA_ABOUT_DESC("Mrityu Bhaga indicates critical degrees where planets become vulnerable. Analysis includes Gandanta junctions and auspicious Pushkara placements.", "मृत्यु भागले ग्रहहरू कमजोर हुने महत्त्वपूर्ण अंशहरू देखाउँछ। विश्लेषणमा गण्डान्त सन्धिहरू र शुभ पुष्कर स्थितिहरू समावेश छन्।"),
    MRITYU_BHAGA_EXACT("Exact", "यथार्थ"),
    MRITYU_BHAGA_VERY_CLOSE("Very Close", "अति नजिक"),
    MRITYU_BHAGA_WITHIN_ORB("Within Orb", "परिधि भित्र"),
    MRITYU_BHAGA_APPROACHING("Approaching", "नजिक आउँदै"),
    MRITYU_BHAGA_SAFE("Safe", "सुरक्षित"),
    MRITYU_BHAGA_DEGREE("Mrityu Bhaga Degree", "मृत्यु भाग अंश"),
    MRITYU_BHAGA_DISTANCE("Distance from Critical Degree", "महत्त्वपूर्ण अंशबाट दूरी"),
    MRITYU_BHAGA_EFFECTS("Effects", "प्रभावहरू"),
    MRITYU_BHAGA_VULNERABILITIES("Vulnerability Areas", "कमजोर क्षेत्रहरू"),
    GANDANTA_TITLE("Gandanta Analysis", "गण्डान्त विश्लेषण"),
    GANDANTA_SUBTITLE("Water-Fire Sign Junction Points", "जल-अग्नि राशि सन्धि बिन्दुहरू"),
    GANDANTA_EXACT_JUNCTION("Exact Junction", "यथार्थ सन्धि"),
    GANDANTA_CRITICAL("Critical", "गम्भीर"),
    GANDANTA_SEVERE("Severe", "तीव्र"),
    GANDANTA_MODERATE("Moderate", "मध्यम"),
    GANDANTA_MILD("Mild", "हल्का"),
    GANDANTA_BRAHMA("Brahma Gandanta (Cancer-Leo)", "ब्रह्म गण्डान्त (कर्कट-सिंह)"),
    GANDANTA_VISHNU("Vishnu Gandanta (Scorpio-Sagittarius)", "विष्णु गण्डान्त (वृश्चिक-धनु)"),
    GANDANTA_SHIVA("Shiva Gandanta (Pisces-Aries)", "शिव गण्डान्त (मीन-मेष)"),
    PUSHKARA_NAVAMSA_TITLE("Pushkara Navamsa", "पुष्कर नवांश"),
    PUSHKARA_NAVAMSA_DESC("Highly auspicious navamsa placements providing protection", "सुरक्षा प्रदान गर्ने अत्यधिक शुभ नवांश स्थितिहरू"),
    PUSHKARA_BHAGA_TITLE("Pushkara Bhaga", "पुष्कर भाग"),
    PUSHKARA_BHAGA_DESC("Single auspicious degrees providing nourishment", "पोषण प्रदान गर्ने एकल शुभ अंशहरू"),
    SENSITIVE_DEGREES_CRITICAL("Critical Planets", "महत्त्वपूर्ण ग्रहहरू"),
    SENSITIVE_DEGREES_AUSPICIOUS("Auspicious Planets", "शुभ ग्रहहरू"),
    SENSITIVE_DEGREES_NEEDS_ATTENTION("Needs Attention", "ध्यान आवश्यक"),
    SENSITIVE_DEGREES_MODERATE_CONCERN("Moderate Concern", "मध्यम चिन्ता"),
    SENSITIVE_DEGREES_BALANCED("Balanced", "सन्तुलित"),
    SENSITIVE_DEGREES_POSITIVE("Generally Positive", "सामान्यतया सकारात्मक"),
    SENSITIVE_DEGREES_HIGHLY_AUSPICIOUS("Highly Auspicious", "अत्यधिक शुभ"),

    // ============================================
    // ASHTOTTARI DASHA SYSTEM
    // ============================================
    ASHTOTTARI_DASHA_TITLE("Ashtottari Dasha", "अष्टोत्तरी दशा"),
    ASHTOTTARI_DASHA_SUBTITLE("108-Year Dasha Cycle", "१०८-वर्षे दशा चक्र"),
    ASHTOTTARI_DASHA_ABOUT("About Ashtottari Dasha", "अष्टोत्तरी दशाको बारेमा"),
    ASHTOTTARI_DASHA_ABOUT_DESC("Ashtottari Dasha is a 108-year cycle using 8 planets (excluding Ketu). It is applicable when Rahu is in Kendra or Trikona from Lagna lord.", "अष्टोत्तरी दशा ८ ग्रहहरू (केतु बाहेक) प्रयोग गर्ने १०८-वर्षे चक्र हो। यो लग्नेशबाट राहु केन्द्र वा त्रिकोणमा हुँदा लागू हुन्छ।"),
    ASHTOTTARI_APPLICABLE("Applicable", "लागू"),
    ASHTOTTARI_NOT_APPLICABLE("Not Applicable", "लागू हुँदैन"),
    ASHTOTTARI_REASON("Applicability Reason", "प्रयोज्यता कारण"),
    ASHTOTTARI_RAHU_FROM_LAGNA_LORD("Rahu from Lagna Lord", "लग्नेशबाट राहु"),
    ASHTOTTARI_CURRENT_MD("Current Mahadasha", "वर्तमान महादशा"),
    ASHTOTTARI_CURRENT_AD("Current Antardasha", "वर्तमान अन्तर्दशा"),
    ASHTOTTARI_BALANCE_AT_BIRTH("Balance at Birth", "जन्ममा सन्तुलन"),
    ASHTOTTARI_STARTING_LORD("Starting Dasha Lord", "सुरुवात दशा स्वामी"),
    ASHTOTTARI_PERIOD_YEARS("Period (Years)", "अवधि (वर्ष)"),
    ASHTOTTARI_COMPARISON("Vimsottari vs Ashtottari", "विंशोत्तरी बनाम अष्टोत्तरी"),
    ASHTOTTARI_IN_AGREEMENT("Both systems agree", "दुवै प्रणाली सहमत"),
    ASHTOTTARI_DIFFERENT("Systems show different planets", "प्रणालीहरूले फरक ग्रह देखाउँछन्"),
    ASHTOTTARI_FRIEND("Friendly Period", "मैत्रीपूर्ण अवधि"),
    ASHTOTTARI_ENEMY("Challenging Period", "चुनौतीपूर्ण अवधि"),
    ASHTOTTARI_NEUTRAL("Neutral Period", "तटस्थ अवधि"),
    ASHTOTTARI_SAME("Intensified Period", "तीव्र अवधि"),

    // ============================================
    // SUDARSHANA CHAKRA DASHA
    // ============================================
    SUDARSHANA_CHAKRA_TITLE("Sudarshana Chakra", "सुदर्शन चक्र"),
    SUDARSHANA_CHAKRA_SUBTITLE("Triple Reference Annual Analysis", "त्रिगुण सन्दर्भ वार्षिक विश्लेषण"),
    SUDARSHANA_CHAKRA_ABOUT("About Sudarshana Chakra", "सुदर्शन चक्रको बारेमा"),
    SUDARSHANA_CHAKRA_ABOUT_DESC("Sudarshana Chakra analyzes yearly results from three references: Lagna, Moon, and Sun. Each house progresses one year at a time in a 12-year cycle.", "सुदर्शन चक्रले तीन सन्दर्भबाट वार्षिक परिणाम विश्लेषण गर्छ: लग्न, चन्द्र, र सूर्य। प्रत्येक भाव १२-वर्षे चक्रमा एक पटकमा एक वर्ष अगाडि बढ्छ।"),
    SUDARSHANA_CURRENT_AGE("Current Age", "वर्तमान उमेर"),
    SUDARSHANA_LAGNA_REFERENCE("Lagna Reference", "लग्न सन्दर्भ"),
    SUDARSHANA_CHANDRA_REFERENCE("Moon Reference", "चन्द्र सन्दर्भ"),
    SUDARSHANA_SURYA_REFERENCE("Sun Reference", "सूर्य सन्दर्भ"),
    SUDARSHANA_ACTIVE_HOUSE("Active House", "सक्रिय भाव"),
    SUDARSHANA_ACTIVE_SIGN("Active Sign", "सक्रिय राशि"),
    SUDARSHANA_SIGN_LORD("Sign Lord", "राशि स्वामी"),
    SUDARSHANA_CYCLE_NUMBER("Cycle Number", "चक्र संख्या"),
    SUDARSHANA_HOUSE_THEMES("House Themes", "भाव विषयहरू"),
    SUDARSHANA_SYNTHESIS("Synthesis", "संश्लेषण"),
    SUDARSHANA_COMBINED_STRENGTH("Combined Strength", "संयुक्त बल"),
    SUDARSHANA_COMMON_THEMES("Common Themes", "साझा विषयहरू"),
    SUDARSHANA_CONFLICTING_THEMES("Conflicting Themes", "विरोधाभासी विषयहरू"),
    SUDARSHANA_PRIMARY_FOCUS("Primary Focus", "प्राथमिक केन्द्र"),
    SUDARSHANA_SECONDARY_FOCUS("Secondary Focus", "माध्यमिक केन्द्र"),
    SUDARSHANA_YEARLY_PROGRESSION("Yearly Progression", "वार्षिक प्रगति"),
    SUDARSHANA_PREVIOUS_YEAR("Previous Year", "गत वर्ष"),
    SUDARSHANA_CURRENT_YEAR("Current Year", "वर्तमान वर्ष"),
    SUDARSHANA_NEXT_YEAR("Next Year", "आगामी वर्ष"),
    SUDARSHANA_TREND("Trend", "प्रवृत्ति"),
    SUDARSHANA_EXCELLENT("Excellent", "उत्कृष्ट"),
    SUDARSHANA_GOOD("Good", "राम्रो"),
    SUDARSHANA_MODERATE("Moderate", "मध्यम"),
    SUDARSHANA_WEAK("Weak", "कमजोर"),
    SUDARSHANA_VERY_WEAK("Very Weak", "अति कमजोर"),

    // ============================================
    // UPACHAYA TRANSIT TRACKER
    // ============================================
    UPACHAYA_TRANSIT_TITLE("Upachaya Transits", "उपचय गोचर"),
    UPACHAYA_TRANSIT_SUBTITLE("Growth House Transit Analysis", "वृद्धि भाव गोचर विश्लेषण"),
    UPACHAYA_TRANSIT_ABOUT("About Upachaya Houses", "उपचय भावहरूको बारेमा"),
    UPACHAYA_TRANSIT_ABOUT_DESC("Upachaya houses (3, 6, 10, 11) are growth houses where planets, especially natural malefics, give increasingly positive results over time.", "उपचय भावहरू (३, ६, १०, ११) वृद्धि भावहरू हुन् जहाँ ग्रहहरू, विशेष गरी प्राकृतिक पापग्रह, समयसँगै बढ्दो सकारात्मक परिणामहरू दिन्छन्।"),
    UPACHAYA_HOUSE_3("3rd House (Courage)", "३ भाव (साहस)"),
    UPACHAYA_HOUSE_6("6th House (Enemies)", "६ भाव (शत्रु)"),
    UPACHAYA_HOUSE_10("10th House (Career)", "१० भाव (करियर)"),
    UPACHAYA_HOUSE_11("11th House (Gains)", "११ भाव (लाभ)"),
    UPACHAYA_FROM_MOON("From Moon", "चन्द्रबाट"),
    UPACHAYA_FROM_LAGNA("From Lagna", "लग्नबाट"),
    UPACHAYA_TRANSIT_QUALITY("Transit Quality", "गोचर गुणस्तर"),
    UPACHAYA_QUALITY_EXCELLENT("Excellent", "उत्कृष्ट"),
    UPACHAYA_QUALITY_GOOD("Good", "राम्रो"),
    UPACHAYA_QUALITY_FAVORABLE("Favorable", "अनुकूल"),
    UPACHAYA_QUALITY_NEUTRAL("Neutral", "तटस्थ"),
    UPACHAYA_SIGNIFICANCE("Significance", "महत्त्व"),
    UPACHAYA_DURATION("Duration", "अवधि"),
    UPACHAYA_ACTIVE_TRANSITS("Active Upachaya Transits", "सक्रिय उपचय गोचरहरू"),
    UPACHAYA_MOST_SIGNIFICANT("Most Significant Transits", "सबैभन्दा महत्त्वपूर्ण गोचरहरू"),
    UPACHAYA_HOUSE_WISE("House-wise Analysis", "भाव-अनुसार विश्लेषण"),
    UPACHAYA_ASSESSMENT("Overall Assessment", "समग्र मूल्यांकन"),
    UPACHAYA_LEVEL_EXCEPTIONAL("Exceptional Period", "असाधारण अवधि"),
    UPACHAYA_LEVEL_HIGH("High Activity", "उच्च सक्रियता"),
    UPACHAYA_LEVEL_MODERATE("Moderate Activity", "मध्यम सक्रियता"),
    UPACHAYA_LEVEL_LOW("Low Activity", "कम सक्रियता"),
    UPACHAYA_ALERTS("Transit Alerts", "गोचर सचेतनाहरू"),
    UPACHAYA_ALERT_OPPORTUNITY("Opportunity", "अवसर"),
    UPACHAYA_ALERT_MAJOR_TRANSIT("Major Transit", "प्रमुख गोचर"),
    UPACHAYA_ALERT_FORTUNE("Fortune", "भाग्य"),
    UPACHAYA_UPCOMING("Upcoming Transits", "आगामी गोचरहरू"),
    UPACHAYA_HOUSE_INACTIVE("Inactive", "निष्क्रिय"),
    UPACHAYA_HOUSE_MILD("Mild", "हल्का"),
    UPACHAYA_HOUSE_MODERATE("Moderate", "मध्यम"),
    UPACHAYA_HOUSE_STRONG("Strong", "बलियो"),
    UPACHAYA_HOUSE_VERY_STRONG("Very Strong", "अति बलियो"),

    // Upachaya Effects & Recommendations
    UPACHAYA_EFFECT_COURAGE("Courage and initiative enhanced", "साहस र पहल वृद्धि"),
    UPACHAYA_EFFECT_TRAVELS("Short travels and communications favored", "छोटो यात्रा र सञ्चार अनुकूल"),
    UPACHAYA_EFFECT_SIBLINGS("Sibling relationships in focus", "भाइबहिनीको सम्बन्धमा ध्यान"),
    UPACHAYA_EFFECT_SKILLS("Skills and hobbies development", "सीप र शौकको विकास"),
    UPACHAYA_EFFECT_ENEMIES("Victory over enemies and competitors", "शत्रु र प्रतिस्पर्धीहरूमाथि विजय"),
    UPACHAYA_EFFECT_HEALTH_IMPROVE("Health improvements through discipline", "अनुशासन मार्फत स्वास्थ्यमा सुधार"),
    UPACHAYA_EFFECT_SERVICE("Service opportunities arise", "सेवाका अवसरहरू प्राप्त"),
    UPACHAYA_EFFECT_DEBTS("Debt resolution possibilities", "ऋण समाधानको सम्भावना"),
    UPACHAYA_EFFECT_CAREER_ADVANCE("Career advancement opportunities", "क्यारियर प्रगतिका अवसरहरू"),
    UPACHAYA_EFFECT_PROFESSIONAL("Professional recognition possible", "व्यावसायिक मान्यता सम्भव"),
    UPACHAYA_EFFECT_STATUS("Authority and status improvements", "अधिकार र स्थितिमा सुधार"),
    UPACHAYA_EFFECT_GOVT_FAVORED("Government-related matters favored", "सरकारी सम्बन्धित मामिलाहरू अनुकूल"),
    UPACHAYA_EFFECT_GAINS("Gains and income increase likely", "लाभ र आयमा वृद्धिको सम्भावना"),
    UPACHAYA_EFFECT_DESIRES("Desires and aspirations fulfilled", "इच्छा र आकांक्षाहरू पूरा"),
    UPACHAYA_EFFECT_FRIENDSHIP("Friendships and social network expansion", "मित्रता र सामाजिक सञ्जाल विस्तार"),
    UPACHAYA_EFFECT_ELDER_SIBLINGS("Elder siblings related benefits", "ठूला भाइबहिनीबाट लाभ"),

    UPACHAYA_PLANET_SATURN_1("Slow but lasting results through discipline", "अनुशासन मार्फत ढिलो तर स्थायी परिणाम"),
    UPACHAYA_PLANET_SATURN_2("Karmic rewards for past efforts", "विगतका प्रयासहरूका लागि कार्मिक पुरस्कार"),
    UPACHAYA_PLANET_SATURN_3("Structure and organization bring success", "संरचना र संगठनले सफलता ल्याउँछ"),
    UPACHAYA_PLANET_MARS_1("Energy and drive for action", "कार्यका लागि ऊर्जा र प्रेरणा"),
    UPACHAYA_PLANET_MARS_2("Competitive success possible", "प्रतिस्पर्धात्मक सफलता सम्भव"),
    UPACHAYA_PLANET_MARS_3("Physical activities favored", "शारीरिक गतिविधिहरू अनुकूल"),
    UPACHAYA_PLANET_RAHU_1("Unconventional opportunities arise", "अपरम्परागत अवसरहरू प्राप्त"),
    UPACHAYA_PLANET_RAHU_2("Foreign connections beneficial", "विदेशी सम्बन्धहरू लाभदायक"),
    UPACHAYA_PLANET_RAHU_3("Technology and innovation favored", "प्रविधि र नवीनता अनुकूल"),
    UPACHAYA_PLANET_KETU_1("Spiritual insights through practical matters", "व्यावहारिक मामिलाहरू मार्फत आध्यात्मिक अन्तर्दृष्टि"),
    UPACHAYA_PLANET_KETU_2("Detachment brings clarity", "वैराग्यले स्पष्टता ल्याउँछ"),
    UPACHAYA_PLANET_KETU_3("Past karma resolution", "विगतको कर्म समाधान"),
    UPACHAYA_PLANET_JUPITER_1("Wisdom and expansion in %s matters", "%s मामिलाहरूमा ज्ञान र विस्तार"),
    UPACHAYA_PLANET_JUPITER_2("Teachers and guides appear", "शिक्षक र मार्गदर्शकहरू देखा पर्छन्"),
    UPACHAYA_PLANET_JUPITER_3("Fortune through righteous action", "धार्मिक कार्य मार्फत भाग्य"),
    UPACHAYA_PLANET_SUN_1("Authority and confidence boosted", "अधिकार र आत्मविश्वास वृद्धि"),
    UPACHAYA_PLANET_SUN_2("Government favor possible", "सरकारी कृपा सम्भव"),
    UPACHAYA_PLANET_SUN_3("Leadership opportunities", "नेतृत्वका अवसरहरू"),
    UPACHAYA_PLANET_GENERIC("%s influences %s house themes", "%s ले %s भावको विषयवस्तुहरूलाई प्रभाव पार्छ"),

    UPACHAYA_DURATION_MOON("~2.5 days per sign", "नजिकै २.५ दिन प्रति राशि"),
    UPACHAYA_DURATION_SUN("~1 month per sign", "नजिकै १ महिना प्रति राशि"),
    UPACHAYA_DURATION_MERCURY("~3-4 weeks per sign", "नजिकै ३-४ हप्ता प्रति राशि"),
    UPACHAYA_DURATION_VENUS("~3-4 weeks per sign", "नजिकै ३-४ हप्ता प्रति राशि"),
    UPACHAYA_DURATION_MARS("~6-8 weeks per sign", "नजिकै ६-८ हप्ता प्रति राशि"),
    UPACHAYA_DURATION_JUPITER("~1 year per sign", "नजिकै १ वर्ष प्रति राशि"),
    UPACHAYA_DURATION_SATURN("~2.5 years per sign", "नजिकै २.५ वर्ष प्रति राशि"),
    UPACHAYA_DURATION_RAHU("~1.5 years per sign", "नजिकै १.५ वर्ष प्रति राशि"),

    UPACHAYA_REC_HOUSE_3_1("Take initiative on pending matters", "बाँकी मामिलाहरूमा पहल गर्नुहोस्"),
    UPACHAYA_REC_HOUSE_3_2("Communicate your ideas boldly", "आफ्ना विचारहरू साहसका साथ व्यक्त गर्नुहोस्"),
    UPACHAYA_REC_HOUSE_3_3("Learn a new skill or hobby", "नयाँ सीप वा शौक सिक्नुहोस्"),
    UPACHAYA_REC_HOUSE_6_1("Address health matters proactively", "स्वास्थ्य मामिलाहरूमा सक्रिय रूपमा ध्यान दिनुहोस्"),
    UPACHAYA_REC_HOUSE_6_2("Resolve conflicts decisively", "विवादहरू निर्णायक रूपठप्पा समाधान गर्नुहोस्"),
    UPACHAYA_REC_HOUSE_6_3("Organize daily routines", "दैनिक दिनचर्या व्यवस्थित गर्नुहोस्"),
    UPACHAYA_REC_HOUSE_10_1("Focus on career goals", "क्यारियर लक्ष्यहरूमा ध्यान दिनुहोस्"),
    UPACHAYA_REC_HOUSE_10_2("Seek recognition for your work", "आफ्नो कामका लागि मान्यता खोज्नुहोस्"),
    UPACHAYA_REC_HOUSE_10_3("Take on leadership responsibilities", "नेतृत्वको जिम्मेवारी लिनुहोस्"),
    UPACHAYA_REC_HOUSE_11_1("Network and expand connections", "नेटवर्क र सम्बन्धहरू विस्तार गर्नुहोस्"),
    UPACHAYA_REC_HOUSE_11_2("Pursue financial opportunities", "आर्थिक अवसरहरू पछ्याउनुहोस्"),
    UPACHAYA_REC_HOUSE_11_3("Support friends and community", "साथीहरू र समुदायलाई समर्थन गर्नुहोस्"),

    UPACHAYA_REC_SATURN_1("Work with discipline and patience", "अनुशासन र धैर्यका साथ काम गर्नुहोस्"),
    UPACHAYA_REC_SATURN_2("Don't expect quick results", "छिटो परिणामको आशा नगर्नुहोस्"),
    UPACHAYA_REC_SATURN_3("Honor commitments and responsibilities", "प्रतिबद्धता र जिम्मेवारीहरूको सम्मान गर्नुहोस्"),
    UPACHAYA_REC_MARS_1("Channel energy constructively", "ऊर्जालाई रचनात्मक रूपमा प्रयोग गर्नुहोस्"),
    UPACHAYA_REC_MARS_2("Avoid impulsive actions", "आवेगी कार्यहरूबाट बच्नुहोस्"),
    UPACHAYA_REC_MARS_3("Physical exercise recommended", "शारीरिक व्यायाम सिफारिस गरिन्छ"),
    UPACHAYA_REC_RAHU_1("Stay grounded amid opportunities", "अवसरहरूको बीचमा संयमित रहनुहोस्"),
    UPACHAYA_REC_RAHU_2("Avoid shortcuts", "छोटो बाटोहरू नखोज्नुहोस्"),
    UPACHAYA_REC_RAHU_3("Embrace innovation carefully", "सावधानीपूर्वक नवीनता अपनाउनुहोस्"),
    UPACHAYA_REC_JUPITER_1("Expand through wisdom", "ज्ञान मार्फत विस्तार गर्नुहोस्"),
    UPACHAYA_REC_JUPITER_2("Share knowledge generously", "उदारतापूर्वक ज्ञान साझा गर्नुहोस्"),
    UPACHAYA_REC_JUPITER_3("Maintain ethical standards", "नैतिक मापदण्डहरू कायम राख्नुहोस्"),

    UPACHAYA_HOUSE_NO_TRANSIT("No active Upachaya transits in %s house", "%s भावमा कुनै सक्रिय उपचय गोचर छैन"),
    UPACHAYA_HOUSE_PLANETS_COUNT("%d planet(s) transiting %s house", "%s भावमा %d ग्रह(हरू) गोचर गर्दै"),
    UPACHAYA_SATURN_PROGRESS("Saturn brings slow, steady progress in %s", "शनिले %s मा ढिलो तर स्थिर प्रगति ल्याउँछ"),
    UPACHAYA_MARS_PROGRESS("Mars energizes action in %s", "मंगलले %s मा कार्यलाई ऊर्जित गर्छ"),
    UPACHAYA_JUPITER_PROGRESS("Jupiter expands opportunities in %s", "बृहस्पतिले %s मा अवसरहरू विस्तार गर्छ"),

    UPACHAYA_ASSESS_NO_ACTIVE("No significant Upachaya transits currently active", "हाल कुनै महत्त्वपूर्ण उपचय गोचर सक्रिय छैन"),
    UPACHAYA_ASSESS_WAIT("Wait for slow planets to enter Upachaya houses", "ढिलो हिड्ने ग्रहहरू उपचय भावहरूमा प्रवेश गर्ने प्रतीक्षा गर्नुहोस्"),
    UPACHAYA_ASSESS_EXCEPTIONAL("Exceptional period! Multiple powerful transits favor growth in career, gains, and overcoming obstacles", "असाधारण अवधि! बहु शक्तिशाली गोचरहरूले क्यारियर, लाभ र बाधाहरू पार गर्न मद्दत गर्छन्"),
    UPACHAYA_ASSESS_HIGH("Favorable period with strong Upachaya transits supporting professional and material growth", "व्यावसायिक र भौतिक वृद्धिलाई समर्थन गर्ने बलियो उपचय गोचरसहितको अनुकूल अवधि"),
    UPACHAYA_ASSESS_MODERATE("Moderate Upachaya support - steady progress possible with focused effort", "मध्यम उपचय समर्थन - केन्द्रित प्रयासको साथ स्थिर प्रगति सम्भव छ"),
    UPACHAYA_ASSESS_LOW("Limited Upachaya activation - focus on preparation for upcoming favorable transits", "सीमित उपचय सक्रियता - आगामी अनुकूल गोचरहरूको तयारीमा ध्यान दिनुहोस्"),
    UPACHAYA_ASSESS_KEY_TRANSIT("%s in %s house is the key transit", "%s भावमा %s मुख्य गोचर हो"),
    UPACHAYA_ASSESS_DAILY_ACTION("Focus on daily actions to maximize current transits", "वर्तमान गोचरहरूलाई अधिकतम बनाउन दैनिक कार्यहरूमा ध्यान दिनुहोस्"),

    UPACHAYA_REC_BOLD_INITIATIVE("Take bold initiatives - courage is supported now", "साहसी पहलहरू गर्नुहोस् - साहस अहिले समर्थित छ"),
    UPACHAYA_REC_HEALTH_CONFLICTS("Address health and resolve conflicts - victory over obstacles favored", "स्वास्थ्य र विवाद समाधानमा ध्यान दिनुहोस् - बाधाहरूमाथि विजय अनुकूल छ"),
    UPACHAYA_REC_CAREER_FOCUS("Focus on career advancement - professional recognition possible", "क्यारियर प्रगतिमा ध्यान दिनुहोस् - व्यावसायिक मान्यता सम्भव छ"),
    UPACHAYA_REC_FINANCIAL_GOALS("Pursue financial goals - gains and fulfillment of desires supported", "आर्थिक लक्ष्यहरू पछ्याउनुहोस् - लाभ र इच्छाहरूको पूर्ति समर्थित छ"),
    UPACHAYA_REC_SLOW_PLANET("Major slow-planet transit active - commit to long-term goals", "प्रमुख ढिलो ग्रह गोचर सक्रिय छ - दीर्घकालीन लक्ष्यहरूमा प्रतिबद्ध रहनुहोस्"),
    UPACHAYA_REC_PREPARE_UPCOMING("Prepare for upcoming Upachaya transits by organizing current affairs", "वर्तमान मामिलाहरू व्यवस्थित गरेर आगामी उपचय गोचरहरूको लागि तयारी गर्नुहोस्"),

    UPACHAYA_ALERT_EXCELLENT("%s in %s house - Excellent opportunity period!", "%s भावमा %s - उत्कृष्ट अवसर अवधि!"),
    UPACHAYA_ALERT_SATURN_10("Saturn transiting 10th house - Major career period! Work hard for lasting results", "शनिको १०औं भावमा गोचर - प्रमुख क्यारियर अवधि! स्थायी परिणामका लागि कडा परिश्रम गर्नुहोस्"),
    UPACHAYA_ALERT_JUPITER_11("Jupiter transiting 11th house - Excellent for gains and fulfillment of desires!", "बृहस्पतिको ११औं भावमा गोचर - लाभ र इच्छाहरूको पूर्तिका लागि उत्कृष्ट!"),

    UPACHAYA_UPCOMING_SIGNIFICANCE("When %s enters %s - %s activated", "जब %s ले %s मा प्रवेश गर्छ - %s सक्रिय हुन्छ"),
    UPACHAYA_UPCOMING_REC("Prepare for %s related activities", "%s सम्बन्धित गतिविधिहरूको तयारी गर्नुहोस्"),


    // ============================================
    // LAL KITAB REMEDIES
    // ============================================
    LAL_KITAB_TITLE("Lal Kitab Analysis", "लाल किताब विश्लेषण"),
    LAL_KITAB_SUBTITLE("Traditional Remedial System", "परम्परागत उपचार प्रणाली"),
    LAL_KITAB_ABOUT("About Lal Kitab", "लाल किताबको बारेमा"),
    LAL_KITAB_ABOUT_DESC("Lal Kitab is a distinct astrological system with practical, cost-effective remedies. It differs from classical Vedic astrology in house analysis and remedy approach.", "लाल किताब व्यावहारिक, लागत-प्रभावी उपचारहरू भएको एक विशिष्ट ज्योतिष प्रणाली हो। यो भाव विश्लेषण र उपचार दृष्टिकोणमा शास्त्रीय वैदिक ज्योतिषभन्दा फरक छ।"),
    LAL_KITAB_SYSTEM_NOTE("System Note", "प्रणाली नोट"),
    LAL_KITAB_PLANETARY_AFFLICTIONS("Planetary Afflictions", "ग्रह पीडाहरू"),
    LAL_KITAB_KARMIC_DEBTS("Karmic Debts (Rin)", "कार्मिक ऋण"),
    LAL_KITAB_HOUSE_ANALYSIS("House Analysis", "भाव विश्लेषण"),
    LAL_KITAB_REMEDIES("Remedies", "उपायहरू"),
    LAL_KITAB_COLOR_REMEDIES("Color Remedies", "रंग उपायहरू"),
    LAL_KITAB_DIRECTION_REMEDIES("Direction Remedies", "दिशा उपायहरू"),
    LAL_KITAB_ANNUAL_CALENDAR("Weekly Remedy Calendar", "साप्ताहिक उपाय पात्रो"),
    LAL_KITAB_GENERAL_RECOMMENDATIONS("General Recommendations", "सामान्य सिफारिसहरू"),
    LAL_KITAB_AFFLICTION_TYPE("Affliction Type", "पीडा प्रकार"),
    LAL_KITAB_AFFLICTION_SEVERITY("Severity", "गम्भीरता"),
    LAL_KITAB_PITRU_DOSH("Pitru Dosh", "पितृ दोष"),
    LAL_KITAB_MATRU_RIN("Matru Rin", "मातृ ऋण"),
    LAL_KITAB_STRI_RIN("Stri Rin", "स्त्री ऋण"),
    LAL_KITAB_KANYA_RIN("Kanya Rin", "कन्या ऋण"),
    LAL_KITAB_GRAHAN_DOSH("Grahan Dosh", "ग्रहण दोष"),
    LAL_KITAB_SHANI_PEEDA("Shani Peeda", "शनि पीडा"),
    LAL_KITAB_SEVERITY_MINIMAL("Minimal", "न्यूनतम"),
    LAL_KITAB_SEVERITY_MILD("Mild", "हल्का"),
    LAL_KITAB_SEVERITY_MODERATE("Moderate", "मध्यम"),
    LAL_KITAB_SEVERITY_SEVERE("Severe", "गम्भीर"),
    LAL_KITAB_DEBT_TYPE("Debt Type", "ऋण प्रकार"),
    LAL_KITAB_DEBT_INDICATORS("Indicators", "सूचकहरू"),
    LAL_KITAB_DEBT_EFFECTS("Effects", "प्रभावहरू"),
    LAL_KITAB_HOUSE_STATUS("House Status", "भाव स्थिति"),
    LAL_KITAB_HOUSE_EMPTY("Empty", "खाली"),
    LAL_KITAB_HOUSE_OCCUPIED("Occupied", "भरिएको"),
    LAL_KITAB_HOUSE_BENEFIC("Benefic Influence", "शुभ प्रभाव"),
    LAL_KITAB_HOUSE_AFFLICTED("Afflicted", "पीडित"),
    LAL_KITAB_REMEDY_CATEGORY("Remedy Category", "उपाय श्रेणी"),
    LAL_KITAB_CATEGORY_PLANETARY("Planetary Remedy", "ग्रह उपाय"),
    LAL_KITAB_CATEGORY_KARMIC("Karmic Debt Remedy", "कार्मिक ऋण उपाय"),
    LAL_KITAB_CATEGORY_HOUSE("House-based Remedy", "भाव-आधारित उपाय"),
    LAL_KITAB_CATEGORY_GENERAL("General Remedy", "सामान्य उपाय"),
    LAL_KITAB_REMEDY_METHOD("Method", "विधि"),
    LAL_KITAB_REMEDY_FREQUENCY("Frequency", "आवृत्ति"),
    LAL_KITAB_REMEDY_DURATION("Duration", "अवधि"),
    LAL_KITAB_REMEDY_EFFECTIVENESS("Effectiveness", "प्रभावकारिता"),
    LAL_KITAB_FAVORABLE_COLORS("Favorable Colors", "शुभ रंगहरू"),
    LAL_KITAB_AVOID_COLORS("Colors to Avoid", "परिहार गर्ने रंगहरू"),
    LAL_KITAB_COLOR_APPLICATION("Application", "प्रयोग"),
    LAL_KITAB_FAVORABLE_DIRECTION("Favorable Direction", "शुभ दिशा"),
    LAL_KITAB_AVOID_DIRECTION("Direction to Avoid", "परिहार गर्ने दिशा"),
    LAL_KITAB_DIRECTION_APPLICATION("Direction Application", "दिशा प्रयोग"),
    LAL_KITAB_SUNDAY("Sunday - Sun", "आइतबार - सूर्य"),
    LAL_KITAB_MONDAY("Monday - Moon", "सोमबार - चन्द्र"),
    LAL_KITAB_TUESDAY("Tuesday - Mars", "मंगलबार - मंगल"),
    LAL_KITAB_WEDNESDAY("Wednesday - Mercury", "बुधबार - बुध"),
    LAL_KITAB_THURSDAY("Thursday - Jupiter", "बिहीबार - बृहस्पति"),
    LAL_KITAB_FRIDAY("Friday - Venus", "शुक्रबार - शुक्र"),
    LAL_KITAB_SATURDAY("Saturday - Saturn", "शनिबार - शनि"),

    // Bhrigu Bindu
    BHRIGU_BINDU_CALC_ERROR("Unable to calculate Bhrigu Bindu. Please check chart data.", "भृगु बिन्दु गणना गर्न असमर्थ। कृपया कुण्डली डाटा जाँच गर्नुहोस्।"),
    POSITION_INTERPRETATION("Position Interpretation", "स्थिति व्याख्या"),
    STRENGTH_FACTORS("Strength Factors", "बल कारकहरू"),
    TRANSIT_DATA_UNAVAILABLE("Transit data not available", "गोचर डाटा उपलब्ध छैन"),
    CURRENT_PLANETARY_POSITIONS("Current Planetary Positions", "वर्तमान ग्रह स्थितिहरू"),
    UPCOMING_TRANSITS("Upcoming Significant Transits", "आगामी महत्त्वपूर्ण गोचरहरू"),
    TRANSIT_TIMING("Transit Timing", "गोचर समय"),
    TRANSIT_TIMING_DESC("When slow-moving planets (Saturn, Jupiter, Rahu, Ketu) transit over your Bhrigu Bindu, significant karmic events tend to manifest.", "जब ढिलो हिड्ने ग्रहहरू (शनि, बृहस्पति, राहु, केतु) तपाइँको भृगु बिन्दु माथि गोचर गर्छन्, महत्त्वपूर्ण कार्मिक घटनाहरू प्रकट हुन्छन्।"),
    CONJUNCT("Conjunct", "युति"),
    APPLYING("Applying", "लागू हुँदै"),

    // ============================================
    // DIVISIONAL CHART ANALYZER
    // ============================================
    HORA_ANALYSIS_TITLE("Hora (D-2) Analysis", "होरा (D-2) विश्लेषण"),
    HORA_ANALYSIS_SUBTITLE("Wealth and Financial Potential", "धन र आर्थिक सम्भावना"),
    HORA_ANALYSIS_ABOUT("About Hora Chart", "होरा चार्टको बारेमा"),
    HORA_ANALYSIS_ABOUT_DESC("Hora chart divides each sign into Sun and Moon halves, revealing wealth accumulation patterns and financial potential.", "होरा चार्टले प्रत्येक राशिलाई सूर्य र चन्द्र आधामा विभाजन गर्छ, धन संचय ढाँचा र आर्थिक सम्भावना प्रकट गर्दछ।"),
    HORA_SUN_PLANETS("Planets in Sun Hora", "सूर्य होरामा ग्रहहरू"),
    HORA_MOON_PLANETS("Planets in Moon Hora", "चन्द्र होरामा ग्रहहरू"),
    HORA_DOMINANT("Dominant Hora", "प्रभावशाली होरा"),
    HORA_BALANCE("Hora Balance", "होरा सन्तुलन"),
    HORA_WEALTH_INDICATORS("Wealth Indicators", "धन सूचकहरू"),
    HORA_INCOME_POTENTIAL("Income Potential", "आय सम्भावना"),
    DREKKANA_ANALYSIS_TITLE("Drekkana (D-3) Analysis", "द्रेष्काण (D-3) विश्लेषण"),
    DREKKANA_ANALYSIS_SUBTITLE("Siblings and Courage", "भाइबहिनी र साहस"),
    DREKKANA_ANALYSIS_ABOUT("About Drekkana Chart", "द्रेष्काण चार्टको बारेमा"),
    DREKKANA_ANALYSIS_ABOUT_DESC("Drekkana chart reveals sibling relationships, courage, and personal initiative. Each sign is divided into three 10-degree portions.", "द्रेष्काण चार्टले भाइबहिनी सम्बन्ध, साहस, र व्यक्तिगत पहलकदमी प्रकट गर्दछ। प्रत्येक राशि तीन १०-अंश भागमा विभाजित छ।"),
    DREKKANA_TYPE("Drekkana Type", "द्रेष्काण प्रकार"),
    DREKKANA_FIRST("First Drekkana (0-10Â°)", "पहिलो द्रेष्काण (०-१०°)"),
    DREKKANA_SECOND("Second Drekkana (10-20Â°)", "दोस्रो द्रेष्काण (१०-२०°)"),
    DREKKANA_THIRD("Third Drekkana (20-30Â°)", "तेस्रो द्रेष्काण (२०-३०°)"),
    DREKKANA_SIBLING_KARMA("Sibling Karma", "भाइबहिनी कर्म"),
    DREKKANA_COURAGE_LEVEL("Courage Level", "साहस स्तर"),
    NAVAMSA_MARRIAGE_TITLE("Navamsa Marriage Analysis", "नवांश विवाह विश्लेषण"),
    NAVAMSA_MARRIAGE_SUBTITLE("D-9 Partnership Timing", "D-9 साझेदारी समय"),
    NAVAMSA_MARRIAGE_ABOUT("About Navamsa for Marriage", "विवाहको लागि नवांशको बारेमा"),
    NAVAMSA_MARRIAGE_ABOUT_DESC("Navamsa is the most important divisional chart for marriage timing, spouse characteristics, and relationship quality.", "नवांश विवाह समय, जीवनसाथी विशेषताहरू, र सम्बन्ध गुणस्तरको लागि सबैभन्दा महत्त्वपूर्ण विभाजन चार्ट हो।"),
    NAVAMSA_VENUS_POSITION("Venus in Navamsa", "नवांशमा शुक्र"),
    NAVAMSA_7TH_LORD("7th Lord in Navamsa", "नवांशमा ७म स्वामी"),
    NAVAMSA_SPOUSE_INDICATORS("Spouse Indicators", "जीवनसाथी सूचकहरू"),
    NAVAMSA_MARRIAGE_TIMING("Marriage Timing", "विवाह समय"),
    NAVAMSA_RELATIONSHIP_QUALITY("Relationship Quality", "सम्बन्ध गुणस्तर"),
    DASHAMSA_CAREER_TITLE("Dashamsa (D-10) Career Analysis", "दशांश (D-10) करियर विश्लेषण"),
    DASHAMSA_CAREER_SUBTITLE("Professional Life and Status", "व्यावसायिक जीवन र स्थिति"),
    DASHAMSA_CAREER_ABOUT("About Dashamsa Chart", "दशांश चार्टको बारेमा"),
    DASHAMSA_CAREER_ABOUT_DESC("Dashamsa reveals career potential, professional achievements, and worldly status through 10th harmonic analysis.", "दशांशले १०औं हार्मोनिक विश्लेषण मार्फत करियर सम्भावना, व्यावसायिक उपलब्धि, र सांसारिक स्थिति प्रकट गर्दछ।"),
    DASHAMSA_10TH_LORD("10th Lord in D-10", "D-10 मा १०औं स्वामी"),
    DASHAMSA_LAGNA_LORD("D-10 Lagna Lord", "D-10 लग्न स्वामी"),
    DASHAMSA_CAREER_HOUSES("Career Houses Analysis", "करियर भाव विश्लेषण"),
    DASHAMSA_PROFESSION_TYPE("Profession Indicators", "पेशा सूचकहरू"),
    DASHAMSA_STATUS_POTENTIAL("Status Potential", "स्थिति सम्भावना"),
    DWADASAMSA_TITLE("Dwadasamsa (D-12) Analysis", "द्वादशांश (D-12) विश्लेषण"),
    DWADASAMSA_SUBTITLE("Parents and Ancestral Karma", "अभिभावक र पूर्वज कर्म"),
    DWADASAMSA_ABOUT("About Dwadasamsa Chart", "द्वादशांश चार्टको बारेमा"),
    DWADASAMSA_ABOUT_DESC("Dwadasamsa reveals parental relationships, ancestral inheritance, and genetic predispositions through 12th harmonic analysis.", "द्वादशांशले १२औं हार्मोनिक विश्लेषण मार्फत अभिभावक सम्बन्ध, पैतृक विरासत, र आनुवंशिक प्रवृत्तिहरू प्रकट गर्दछ।"),
    DWADASAMSA_SUN_POSITION("Sun in D-12 (Father)", "D-12 मा सूर्य (बुबा)"),
    DWADASAMSA_MOON_POSITION("Moon in D-12 (Mother)", "D-12 मा चन्द्र (आमा)"),
    DWADASAMSA_FATHER_ANALYSIS("Father Analysis", "बुबा विश्लेषण"),
    DWADASAMSA_MOTHER_ANALYSIS("Mother Analysis", "आमा विश्लेषण"),
    DWADASAMSA_ANCESTRAL_KARMA("Ancestral Karma", "पैतृक कर्म"),
    DWADASAMSA_INHERITANCE("Inheritance Potential", "विरासत सम्भावना"),

    // ============================================
    // ADDITIONAL SCREEN-SPECIFIC STRING KEYS
    // ============================================
    // Ashtottari Screen specific
    ASHTOTTARI_TITLE("Ashtottari Dasha", "अष्टोत्तरी दशा"),
    ASHTOTTARI_BALANCE("Balance at Birth", "जन्ममा शेष"),
    ASHTOTTARI_SUBTITLE("108-Year Conditional Dasha System", "१०८ वर्षको सशर्त दशा प्रणाली"),
    ASHTOTTARI_ABOUT("About Ashtottari Dasha", "अष्टोत्तरी दशाको बारेमा"),
    ASHTOTTARI_ABOUT_DESC("Ashtottari Dasha is a conditional planetary period system spanning 108 years. It uses 8 planets (excluding Ketu). The system is particularly effective for daytime births with Moon in Krishna Paksha or nighttime births with Moon in Shukla Paksha.", "अष्टोत्तरी दशा १०८ वर्षको सशर्त ग्रह अवधि प्रणाली हो। यसमा ८ ग्रह प्रयोग गरिन्छ (केतु बाहेक)। यो प्रणाली कृष्ण पक्षमा चन्द्रमासहित दिनको जन्म वा शुक्ल पक्षमा चन्द्रमासहित रातको जन्मको लागि प्रभावकारी छ।"),
    ASHTOTTARI_APPLICABILITY("Applicability Check", "लागू हुने जाँच"),
    ASHTOTTARI_IDEAL_CONDITION("Ideal Condition: Day birth with Krishna Paksha Moon or Night birth with Shukla Paksha Moon", "आदर्श अवस्था: कृष्ण पक्षको चन्द्रमासहित दिनको जन्म वा शुक्ल पक्षको चन्द्रमासहित रातको जन्म"),
    ASHTOTTARI_TOTAL_YEARS("Total Cycle: 108 Years", "कुल चक्र: १०८ वर्ष"),
    ASHTOTTARI_PLANETS_USED("8 Planets Used (No Sun, No Ketu)", "८ ग्रह प्रयोग (सूर्य र केतु बिना)"),
    ASHTOTTARI_CURRENT_PERIOD("Current Period", "वर्तमान अवधि"),
    ASHTOTTARI_TIMELINE("Dasha Timeline", "दशा समयरेखा"),
    ASHTOTTARI_SUN_DURATION("Sun: 6 years", "सूर्य: ६ वर्ष"),
    ASHTOTTARI_MOON_DURATION("Moon: 15 years", "चन्द्र: १५ वर्ष"),
    ASHTOTTARI_MARS_DURATION("Mars: 8 years", "मंगल: ८ वर्ष"),
    ASHTOTTARI_MERCURY_DURATION("Mercury: 17 years", "बुध: १७ वर्ष"),
    ASHTOTTARI_SATURN_DURATION("Saturn: 10 years", "शनि: १० वर्ष"),
    ASHTOTTARI_JUPITER_DURATION("Jupiter: 19 years", "बृहस्पति: १९ वर्ष"),
    ASHTOTTARI_RAHU_DURATION("Rahu: 12 years", "राहु: १२ वर्ष"),

    // Chara Dasha
    CHARA_DASHA_TYPE("Type", "प्रकार"),

    // Divisional Charts Additional
    DIVISIONAL_HORA_TAB("Hora (D-2)", "होरा (D-2)"),
    DIVISIONAL_DREKKANA_TAB("Drekkana (D-3)", "द्रेष्काण (D-3)"),
    DIVISIONAL_NAVAMSA_TAB("Navamsa (D-9)", "नवांश (D-9)"),
    DIVISIONAL_DASHAMSA_TAB("Dashamsa (D-10)", "दशांश (D-10)"),
    DIVISIONAL_DWADASAMSA_TAB("Dwadasamsa (D-12)", "द्वादशांश (D-12)"),
    
    // Note: Other Divisional keys already existed in DIVISIONAL_CHART_ANALYZER block above
    HORA_SELF_EARNED("Self Earned", "आर्जित"),
    HORA_INHERITED("Inherited", "पैतृक"),
    HORA_POTENTIAL("Wealth Potential", "धन सम्भावना"),
    HORA_POTENTIAL_EXCEPTIONAL("Exceptional Wealth", "असाधारण धन"),
    HORA_POTENTIAL_HIGH("High Wealth", "उच्च धन"),
    HORA_POTENTIAL_MODERATE("Moderate Wealth", "मध्यम धन"),
    HORA_POTENTIAL_AVERAGE("Average Wealth", "औसत धन"),
    HORA_POTENTIAL_NEEDS_EFFORT("Needs Effort", "प्रयास आवश्यक"),
    
    COURAGE_EXCEPTIONAL("Exceptional Courage", "असाधारण साहस"),
    COURAGE_HIGH("High Courage", "उच्च साहस"),
    COURAGE_MODERATE("Moderate Courage", "मध्यम साहस"),
    COURAGE_DEVELOPING("Developing Courage", "विकासशील साहस"),
    COURAGE_NEEDS_WORK("Needs Work", "काम आवश्यक"),
    
    DREKKANA_COURAGE_TITLE("Courage Analysis", "साहस विश्लेषण"),
    DREKKANA_SHORT_JOURNEYS("Short Journeys", "छोटो यात्रा"),
    DREKKANA_YOUNGER("Younger Siblings", "साना भाइबहिनी"),
    DREKKANA_ELDER("Elder Siblings", "ठूला भाइबहिनी"),
    DREKKANA_RELATIONSHIP("Relationship", "सम्बन्ध"),
    DREKKANA_COMMUNICATION_TITLE("Communication", "सञ्चार"),
    DREKKANA_OVERALL("Overall", "समग्र"),
    DREKKANA_WRITING("Writing", "लेखन"),
    DREKKANA_SPEAKING("Speaking", "बोल्ने"),
    DREKKANA_ARTISTIC_TALENTS("Artistic Talents", "कलात्मक प्रतिभा"),
    
    NAVAMSA_SPOUSE_TITLE("Spouse Characteristics", "जीवनसाथी विशेषताहरू"),
    NAVAMSA_TIMING_TITLE("Marriage Timing", "विवाह समय"),
    NAVAMSA_KEY_PLANETS_TITLE("Key Planets", "मुख्य ग्रहहरू"),
    NAVAMSA_NATURE("General Nature", "सामान्य प्रकृति"),
    NAVAMSA_PHYSICAL_TRAITS("Physical Traits", "शारीरिक लक्षण"),
    NAVAMSA_FAMILY_BACKGROUND("Family Background", "पारिवारिक पृष्ठभूमि"),
    NAVAMSA_DIRECTION("Direction", "दिशा"),
    NAVAMSA_PROBABLE_PROFESSIONS("Probable Professions", "सम्भावित पेशाहरू"),
    NAVAMSA_VENUS("Venus", "शुक्र"),
    NAVAMSA_JUPITER("Jupiter", "बृहस्पति"),
    NAVAMSA_7TH_LORD_LABEL("7th Lord", "७म स्वामी"),
    NAVAMSA_DARAKARAKA("Darakaraka", "दाराकारक"),
    NAVAMSA_FAVORABLE_DASHA("Favorable Dasha", "शुभ दशा"),
    NAVAMSA_UPAPADA("Upapada Lagna", "उपपद लग्न"),
    NAVAMSA_RELATIONSHIP_STABILITY("Relationship Stability", "सम्बन्ध स्थिरता"),
    NAVAMSA_AREAS_ATTENTION("Areas for Attention", "ध्यान दिनुपर्ने क्षेत्रहरू"),
    NAVAMSA_PROTECTIVE_FACTORS("Protective Factors", "रक्षात्मक कारकहरू"),
    
    DASHAMSA_BUSINESS_VS_SERVICE("Business vs Service", "व्यापार बनाम सेवा"),
    DASHAMSA_BUSINESS("Business Aptitude", "व्यापार योग्यता"),
    DASHAMSA_SERVICE("Service Aptitude", "सेवा योग्यता"),
    DASHAMSA_GOVT_SERVICE_TITLE("Government Service", "सरकारी सेवा"),
    DASHAMSA_POTENTIAL("Potential", "सम्भावना"),
    DASHAMSA_RECOMMENDED_AREAS("Recommended Areas", "सिफारिस गरिएका क्षेत्रहरू"),
    DASHAMSA_PROFESSIONAL_STRENGTHS("Professional Strengths", "व्यावसायिक शक्तिहरू"),
    
    DWADASAMSA_FATHER("Father", "बुबा"),
    DWADASAMSA_MOTHER("Mother", "आमा"),
    DWADASAMSA_INHERITANCE_TITLE("Inheritance Analysis", "विरासत विश्लेषण"),
    DWADASAMSA_ANCESTRAL_PROPERTY("Ancestral Property", "पैतृक सम्पत्ति"),
    DWADASAMSA_LONGEVITY_TITLE("Parental Longevity", "अभिभावक दीर्घायु"),
    DWADASAMSA_POTENTIAL("Potential", "सम्भावना"),
    DWADASAMSA_TIMING("Timing", "समय"),
    DWADASAMSA_SOURCES("Sources", "स्रोतहरू"),
    DWADASAMSA_SIGNIFICATOR("Significator", "कारक"),
    DWADASAMSA_HOUSE_LORD("House Lord", "भाव स्वामी"),
    DWADASAMSA_CHARACTERISTICS("Characteristics", "विशेषताहरू"),
    DWADASAMSA_RELATIONSHIP("Relationship", "सम्बन्ध"),
    ASHTOTTARI_VENUS_DURATION("Venus: 21 years", "शुक्र: २१ वर्ष"),

    // Sudarshana Screen specific
    SUDARSHANA_TITLE("Sudarshana Chakra Dasha", "सुदर्शन चक्र दशा"),
    SUDARSHANA_SUBTITLE("Triple-Reference Timing System", "त्रि-सन्दर्भ समय प्रणाली"),
    SUDARSHANA_ABOUT("About Sudarshana Chakra", "सुदर्शन चक्रको बारेमा"),
    SUDARSHANA_ABOUT_DESC("Sudarshana Chakra Dasha simultaneously considers the Lagna, Chandra, and Surya charts. Each year progresses through signs from these three reference points, giving a holistic view of life events.", "सुदर्शन चक्र दशाले लग्न, चन्द्र र सूर्य कुण्डली एकैसाथ विचार गर्दछ। प्रत्येक वर्ष यी तीन सन्दर्भ बिन्दुहरूबाट राशिहरू मार्फत अगाडि बढ्छ।"),
    SUDARSHANA_LAGNA_CHAKRA("Lagna Chakra", "लग्न चक्र"),
    SUDARSHANA_CHANDRA_CHAKRA("Chandra Chakra", "चन्द्र चक्र"),
    SUDARSHANA_SURYA_CHAKRA("Surya Chakra", "सूर्य चक्र"),
    SUDARSHANA_AGE("Age", "उमेर"),
    SUDARSHANA_COMBINED_ANALYSIS("Combined Period Analysis", "संयुक्त अवधि विश्लेषण"),
    SUDARSHANA_CONVERGENCE("Convergence Analysis", "अभिसरण विश्लेषण"),
    SUDARSHANA_TRIPLE_VIEW("Triple View", "त्रिगुण दृश्य"),
    SUDARSHANA_YEAR_ANALYSIS("Year Analysis", "वार्षिक विश्लेषण"),
    SUDARSHANA_LAGNA_INFLUENCE("Lagna Influence", "लग्न प्रभाव"),
    SUDARSHANA_CHANDRA_INFLUENCE("Chandra Influence", "चन्द्र प्रभाव"),
    SUDARSHANA_SURYA_INFLUENCE("Surya Influence", "सूर्य प्रभाव"),
    SUDARSHANA_CURRENT_SIGNS("Current Signs", "वर्तमान राशिहरू"),
    SUDARSHANA_HOUSE_SIGNIFICATIONS("House Significations", "भाव अर्थहरू"),
    SUDARSHANA_PLANETS_IN_SIGN("Planets in Sign", "राशिमा ग्रहहरू"),
    SUDARSHANA_ASPECTS_RECEIVED("Aspects Received", "प्राप्त दृष्टिहरू"),
    SUDARSHANA_STRONG_CONVERGENCE("Strong Convergence", "बलियो अभिसरण"),
    SUDARSHANA_WEAK_CONVERGENCE("Weak Convergence", "कमजोर अभिसरण"),

    // Mrityu Bhaga Screen specific
    MRITYU_BHAGA_SCREEN_TITLE("Mrityu Bhaga Analysis", "मृत्यु भाग विश्लेषण"),
    MRITYU_BHAGA_SCREEN_SUBTITLE("Sensitive Degrees in Chart", "कुण्डलीमा संवेदनशील अंशहरू"),
    MRITYU_BHAGA_SCREEN_ABOUT("About Mrityu Bhaga", "मृत्यु भागको बारेमा"),
    MRITYU_BHAGA_SCREEN_ABOUT_DESC("Mrityu Bhaga refers to specific sensitive degrees in each zodiac sign. When planets occupy these degrees, they indicate areas of life requiring extra attention. These are sensitive points that may indicate health vulnerabilities or significant life challenges.", "मृत्यु भागले प्रत्येक राशिमा विशेष संवेदनशील अंशहरूलाई जनाउँछ। जब ग्रहहरू यी अंशहरूमा हुन्छन्, तिनीहरूले थप ध्यान चाहिने जीवनका क्षेत्रहरू संकेत गर्दछन्।"),
    MRITYU_BHAGA_PLANETS_AFFECTED("Planets in Mrityu Bhaga", "मृत्यु भागमा ग्रहहरू"),
    MRITYU_BHAGA_NO_PLANETS("No planets in Mrityu Bhaga", "मृत्यु भागमा कुनै ग्रह छैन"),
    MRITYU_BHAGA_SENSITIVE_DEGREE("Sensitive Degree", "संवेदनशील अंश"),
    MRITYU_BHAGA_ORB("Orb", "कोणान्तर"),
    MRITYU_BHAGA_SEVERITY("Severity Level", "गम्भीरता स्तर"),
    MRITYU_BHAGA_LIFE_AREAS("Affected Life Areas", "प्रभावित जीवन क्षेत्रहरू"),
    MRITYU_BHAGA_REMEDIES_SECTION("Suggested Remedies", "सुझाव गरिएका उपायहरू"),
    MRITYU_BHAGA_TRANSIT_WARNING("Transit Alerts", "गोचर चेतावनीहरू"),
    MRITYU_BHAGA_SIGN_DEGREES("Mrityu Bhaga Degrees by Sign", "राशि अनुसार मृत्यु भाग अंशहरू"),
    MRITYU_BHAGA_ALL_SIGNS("All Signs Reference", "सबै राशि सन्दर्भ"),
    MRITYU_BHAGA_PRECAUTIONS("Precautionary Measures", "सावधानीका उपायहरू"),

    // Mrityu Bhaga Additional
    MRITYU_BHAGA_GANDANTA_PLACEMENTS("Gandanta Placements", "गण्डान्त स्थितिहरू"),
    MRITYU_BHAGA_AUSPICIOUS_PLACEMENTS("Auspicious Placements", "शुभ स्थितिहरू"),
    MRITYU_BHAGA_NO_CRITICAL("Your chart is free from critical Mrityu Bhaga placements.", "तपाईंको कुण्डली गम्भीर मृत्यु भाग स्थितिहरूबाट मुक्त छ।"),
    
    // Assessment Levels
    MRITYU_BHAGA_LEVEL_NEEDS_ATTENTION("Needs Attention", "ध्यान दिन आवश्यक"),
    MRITYU_BHAGA_LEVEL_MODERATE_CONCERN("Moderate Concern", "मध्यम चिन्ता"),
    MRITYU_BHAGA_LEVEL_BALANCED("Balanced", "सन्तुलित"),
    MRITYU_BHAGA_LEVEL_GENERALLY_POSITIVE("Generally Positive", "सामान्यतया सकारात्मक"),
    MRITYU_BHAGA_LEVEL_HIGHLY_AUSPICIOUS("Highly Auspicious", "अत्यधिक शुभ"),

    MRITYU_BHAGA_CRITICAL_COUNT("Critical", "गम्भीर"),
    MRITYU_BHAGA_AUSPICIOUS_COUNT("Auspicious", "शुभ"),
    
    // Gandanta Types
    MRITYU_BHAGA_GANDANTA_BRAHMA("Brahma Gandanta", "ब्रह्म गण्डान्त"),
    MRITYU_BHAGA_GANDANTA_VISHNU("Vishnu Gandanta", "विष्णु गण्डान्त"),
    MRITYU_BHAGA_GANDANTA_SHIVA("Shiva Gandanta", "शिव गण्डान्त"),
    
    MRITYU_BHAGA_JUNCTION_DESC("Junction between %1\$2 and %2\$2", "%1\$2 र %2\$2 बीचको सन्धि"),
    MRITYU_BHAGA_DISTANCE_JUNCTION("Distance from Junction: %1\$2", "सन्धिबाट दूरी: %1\$2"),
    
    // Pushkara
    MRITYU_BHAGA_PUSHKARA_NAVAMSA_TITLE("Pushkara Navamsa", "पुष्कर नवमांश"),
    MRITYU_BHAGA_PUSHKARA_BHAGA_TITLE("Pushkara Bhaga", "पुष्कर भाग"),
    MRITYU_BHAGA_BENEFITS("Benefits", "लाभहरू"),
    
    MRITYU_BHAGA_OVERALL_ASSESSMENT("Overall Assessment", "समग्र मूल्यांकन"),

    // Mrityu Bhaga Severity
    MB_SEV_EXACT("Exact", "पूर्ण"),
    MB_SEV_VERY_CLOSE("Very Close", "धेरै नजिक"),
    MB_SEV_WITHIN_ORB("Within Orb", "प्रभाव क्षेत्र भित्र"),
    MB_SEV_APPROACHING("Approaching", "आउँदै गरेको"),
    MB_SEV_SAFE("Safe", "सुरक्षित"),

    // Gandanta Severity
    GANDANTA_SEV_EXACT("Exact Junction", "पूर्ण सन्धि"),
    GANDANTA_SEV_CRITICAL("Critical", "गम्भीर"),
    GANDANTA_SEV_SEVERE("Severe", "कडा"),
    GANDANTA_SEV_MODERATE("Moderate", "मध्यम"),
    GANDANTA_SEV_MILD("Mild", "हल्का"),

    // Lal Kitab Screen specific
    LAL_KITAB_SCREEN_TITLE("Lal Kitab Remedies", "लाल किताब उपायहरू"),
    
    // Additional Lal Kitab Keys
    LAL_KITAB_INDICATORS("Indicators", "संकेतहरू"),
    LAL_KITAB_REMEDIES_LABEL("Remedies", "उपायहरू"),
    LAL_KITAB_TYPES_TITLE("Types of Karmic Debts", "कार्मिक ऋणका प्रकारहरू"),
    LAL_KITAB_DESC_PITRU("Debt towards father and ancestors", "पिता र पूर्वजहरूप्रति ऋण"),
    LAL_KITAB_DESC_MATRU("Debt towards mother and maternal lineage", "आमा र मातृपक्षप्रति ऋण"),
    LAL_KITAB_DESC_STRI("Debt towards wife/women", "पत्नी/स्त्रीहरूप्रति ऋण"),
    LAL_KITAB_DESC_SELF("Debt towards self and karma", "स्वयं र कर्मप्रति ऋण"),
    LAL_KITAB_DAILY_REMEDIES_DESC("Daily remedies for each planetary day as per Lal Kitab tradition", "लाल किताब परम्परा अनुसार प्रत्येक ग्रह बारका दैनिक उपायहरू"),
    LAL_KITAB_FAVORABLE("Favorable", "अनुकूल"),
    LAL_KITAB_AVOID("Avoid", "त्याग गर्नुहोस्"),
    LAL_KITAB_DEBT_KANYA("Girl Child Debt", "कन्या ऋण"),
    LAL_KITAB_SCREEN_SUBTITLE("Practical Karmic Remedies", "व्यावहारिक कार्मिक उपायहरू"),
    LAL_KITAB_SCREEN_ABOUT("About Lal Kitab", "लाल किताबको बारेमा"),
    LAL_KITAB_SCREEN_ABOUT_DESC("Lal Kitab offers simple, practical remedies using everyday items like turmeric, milk, honey, and specific actions. These remedies work on karmic debts and planetary afflictions without expensive rituals.", "लाल किताबले बेसार, दूध, मह जस्ता दैनिक वस्तुहरू प्रयोग गरी सरल, व्यावहारिक उपायहरू प्रदान गर्दछ। यी उपायहरूले महँगो विधि बिना कार्मिक ऋण र ग्रह पीडामा काम गर्दछन्।"),

    // Kalachakra Dasha Keys
    KALACHAKRA_GROUP_SAVYA("Savya (Direct)", "सव्य (सीधो)"),
    KALACHAKRA_GROUP_APSAVYA("Apsavya (Retrograde)", "अपसव्य (उल्टो)"),
    KALACHAKRA_DESC_SAVYA("Clockwise progression through signs - generally smoother life flow", "राशिहरूको घडीको दिशामा प्रगति - सामान्यतया सहज जीवन प्रवाह"),
    KALACHAKRA_DESC_APSAVYA("Anti-clockwise progression - more karmic intensity and transformation", "राशिहरूको घडीको विपरीत दिशामा प्रगति - अधिक कार्मिक तीव्रता र परिवर्तन"),
    
    KALACHAKRA_HEALTH_EXCELLENT("Excellent", "उत्कृष्ट"),
    KALACHAKRA_HEALTH_GOOD("Good", "राम्रो"),
    KALACHAKRA_HEALTH_MODERATE("Moderate", "मध्यम"),
    KALACHAKRA_HEALTH_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    KALACHAKRA_HEALTH_CRITICAL("Critical", "गम्भीर"),
    
    KALACHAKRA_HEALTH_DESC_EXCELLENT("Very favorable for health and vitality", "स्वास्थ्य र जोसका लागि धेरै अनुकूल"),
    KALACHAKRA_HEALTH_DESC_GOOD("Generally supportive of health", "सामान्यतया स्वास्थ्यको लागि सहयोगी"),
    KALACHAKRA_HEALTH_DESC_MODERATE("Mixed health indications", "मिश्रित स्वास्थ्य संकेतहरू"),
    KALACHAKRA_HEALTH_DESC_CHALLENGING("Need to take care of health", "स्वास्थ्यको ख्याल राख्नु आवश्यक"),
    KALACHAKRA_HEALTH_DESC_CRITICAL("Extra caution needed - follow remedies", "अतिरिक्त सावधानी आवश्यक - उपायहरू अपनाउनुहोस्"),

    KALACHAKRA_REL_HARMONIOUS("Harmonious", "सामञ्जस्यपूर्ण"),
    KALACHAKRA_REL_SUPPORTIVE("Supportive", "सहयोगी"),
    KALACHAKRA_REL_NEUTRAL("Neutral", "तटस्थ"),
    KALACHAKRA_REL_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    KALACHAKRA_REL_TRANSFORMATIVE("Transformative", "परिवर्तनकारी"),

    KALACHAKRA_REL_DESC_HARMONIOUS("Body and soul are aligned - good health and spiritual progress", "शरिर र आत्मा एकरुप छन् - राम्रो स्वास्थ्य र आध्यात्मिक प्रगति"),
    KALACHAKRA_REL_DESC_SUPPORTIVE("Jeeva supports Deha - spiritual practices benefit health", "जीवले देहलाई सहयोग गर्छ - आध्यात्मिक अभ्यासले स्वास्थ्यलाई फाइदा पुर्‍याउँछ"),
    KALACHAKRA_REL_DESC_NEUTRAL("Independent functioning of body and spirit", "शरिर र आत्माको स्वतन्त्र कार्य"),
    KALACHAKRA_REL_DESC_CHALLENGING("Some friction between material and spiritual needs", "भौतिक र आध्यात्मिक आवश्यकताहरू बीच केही घर्षण"),
    KALACHAKRA_REL_DESC_TRANSFORMATIVE("Deep karmic work needed to align body and soul", "शरिर र आत्मा मिलाउन गहिरो कार्मिक कार्य आवश्यक"),

    KALACHAKRA_OVERVIEW_TEXT("Kalachakra Dasha is a sophisticated timing system particularly useful for health predictions and spiritual transformation timing. It operates on the principle that body (Deha) and soul (Jeeva) follow different but related cycles.", "कालचक्र दशा स्वास्थ्य भविष्यवाणी र आध्यात्मिक रूपान्तरण समयको लागि विशेष गरी उपयोगी एक परिष्कृत समय प्रणाली हो। यो शरीर (देह) र आत्मा (जीव) ले फरक तर सम्बन्धित चक्रहरू पछ्याउँछन् भन्ने सिद्धान्तमा काम गर्दछ।"),
    KALACHAKRA_GUIDANCE_1("Monitor transits over Deha Rashi for physical health events", "शारीरिक स्वास्थ्य घटनाहरूको लागि देह राशि माथिको गोचर निगरानी गर्नुहोस्"),
    KALACHAKRA_GUIDANCE_2("Observe transits over Jeeva Rashi for spiritual opportunities", "आध्यात्मिक अवसरहरूको लागि जीव राशि माथिको गोचर अवलोकन गर्नुहोस्"),
    KALACHAKRA_GUIDANCE_3("When Deha and Jeeva sign lords are strong in transit, both health and spiritual progress are favored", "जब देह र जीव राशिका स्वामीहरू गोचरमा बलियो हुन्छन्, स्वास्थ्य र आध्यात्मिक प्रगति दुबै अनुकूल हुन्छन्"),
    KALACHAKRA_GUIDANCE_4("Use Kalachakra Dasha alongside Vimsottari for comprehensive analysis", "विस्तृत विश्लेषणको लागि विंशोत्तरी सँगै कालचक्र दशा प्रयोग गर्नुहोस्"),
    KALACHAKRA_GUIDANCE_5("Pay special attention when malefics transit your Deha Rashi", "जब पापी ग्रहहरूले तपाईंको देह राशिमा गोचर गर्छन् विशेष ध्यान दिनुहोस्"),

    // Kalachakra Sign Effects
    KALACHAKRA_EFFECT_ARIES("Period of initiative, new beginnings, and physical vitality. Mars energy brings action and courage.", "पहल, नयाँ शुरुआत र शारीरिक जोसको अवधि। मंगलको ऊर्जाले कर्म र साहस ल्याउँछ।"),
    KALACHAKRA_EFFECT_TAURUS("Focus on stability, wealth accumulation, and sensory pleasures. Venus brings comfort and beauty.", "स्थिरता, धन सञ्चय र इन्द्रिय सुखमा ध्यान। शुक्रले आराम र सौन्दर्य ल्याउँछ।"),
    KALACHAKRA_EFFECT_GEMINI("Communication, learning, and intellectual pursuits are highlighted. Mercury brings versatility.", "सञ्चार, सिकाइ र बौद्धिक प्रयासहरूलाई जोड दिइन्छ। बुधले बहुमुखी प्रतिभा ल्याउँछ।"),
    KALACHAKRA_EFFECT_CANCER("Emotional growth, home, and family matters take center stage. Moon brings nurturing energy.", "भावनात्मक वृद्धि, घर र पारिवारिक मामिलाहरू मुख्य हुन्छन्। चन्द्रमाले पोषण गर्ने ऊर्जा ल्याउँछ।"),
    KALACHAKRA_EFFECT_LEO("Recognition, authority, and creative self-expression. Sun brings confidence and vitality.", "मान्यता, अधिकार र रचनात्मक आत्म-अभिव्यक्ति। सूर्यले आत्मविश्वास र जोस ल्याउँछ।"),
    KALACHAKRA_EFFECT_VIRGO("Analysis, health consciousness, and service. Mercury brings discrimination and healing potential.", "विश्लेषण, स्वास्थ्य चेतना र सेवा। बुधले विवेक र निको पार्ने क्षमता ल्याउँछ।"),
    KALACHAKRA_EFFECT_LIBRA("Partnerships, balance, and aesthetic pursuits. Venus brings harmony and relationships.", "साझेदारी, सन्तुलन र सौन्दर्य प्रयासहरू। शुक्रले सद्भाव र सम्बन्धहरू ल्याउँछ।"),
    KALACHAKRA_EFFECT_SCORPIO("Deep transformation, hidden matters, and research. Mars/Ketu bring intensity and rebirth.", "गहिरो रूपान्तरण, गुप्त मामिला र अनुसन्धान। मंगल/केतुले तीव्रता र पुनर्जन्म ल्याउँछन्।"),
    KALACHAKRA_EFFECT_SAGITTARIUS("Higher learning, philosophy, and expansion. Jupiter brings wisdom and fortune.", "उच्च सिकाइ, दर्शन र विस्तार। बृहस्पतिले ज्ञान र भाग्य ल्याउँछ।"),
    KALACHAKRA_EFFECT_CAPRICORN("Career, discipline, and long-term achievements. Saturn brings structure and maturity.", "क्यारियर, अनुशासन र दीर्घकालीन उपलब्धिहरू। शनिले संरचना र परिपक्वता ल्याउँछ।"),
    KALACHAKRA_EFFECT_AQUARIUS("Innovation, humanitarian concerns, and group activities. Saturn/Rahu bring progressive change.", "नवप्रवर्तन, मानवीय चासो र समूह गतिविधिहरू। शनि/राहुले प्रगतिशील परिवर्तन ल्याउँछन्।"),
    KALACHAKRA_EFFECT_PISCES("Spirituality, imagination, and transcendence. Jupiter brings divine connection and liberation.", "आध्यात्मिकता, कल्पना र पारलौकिकता। बृहस्पतिले ईश्वरीय सम्बन्ध र मुक्ति ल्याउँछ।"),

    KALACHAKRA_BRIEF_ARIES("Energy, initiative, new starts.", "ऊर्जा, पहल, नयाँ शुरुआत।"),
    KALACHAKRA_BRIEF_TAURUS("Stability, wealth, comfort.", "स्थिरता, धन, आराम।"),
    KALACHAKRA_BRIEF_GEMINI("Communication, learning, versatility.", "सञ्चार, सिकाइ, बहुमुखी प्रतिभा।"),
    KALACHAKRA_BRIEF_CANCER("Emotions, home, nurturing.", "भावनाहरू, घर, पालनपोषण।"),
    KALACHAKRA_BRIEF_LEO("Recognition, creativity, authority.", "मान्यता, रचनात्मकता, अधिकार।"),
    KALACHAKRA_BRIEF_VIRGO("Analysis, health, service.", "विश्लेषण, स्वास्थ्य, सेवा।"),
    KALACHAKRA_BRIEF_LIBRA("Balance, relationships, harmony.", "सन्तुलन, सम्बन्ध, सद्भाव।"),
    KALACHAKRA_BRIEF_SCORPIO("Transformation, depth, intensity.", "रूपान्तरण, गहिराई, तीव्रता।"),
    KALACHAKRA_BRIEF_SAGITTARIUS("Expansion, wisdom, fortune.", "विस्तार, ज्ञान, भाग्य।"),
    KALACHAKRA_BRIEF_CAPRICORN("Career, discipline, achievement.", "क्यारियर, अनुशासन, उपलब्धि।"),
    KALACHAKRA_BRIEF_AQUARIUS("Innovation, humanity, progress.", "नवप्रवर्तन, मानवता, प्रगति।"),
    KALACHAKRA_BRIEF_PISCES("Spirituality, imagination, liberation.", "आध्यात्मिकता, कल्पना, मुक्ति।"),
    
    // Planet Strength
    PLANET_STRENGTH_EXALTED("Exalted - Very Strong", "उच्च - धेरै बलियो"),
    PLANET_STRENGTH_OWN_SIGN("Own Sign - Strong", "स्वराशि - बलियो"),
    PLANET_STRENGTH_DEBILITATED("Debilitated - Weak", "नीच - कमजोर"),
    PLANET_STRENGTH_RETROGRADE("Retrograde - Introspective", "बक्री - आत्मनिरीक्षक"),
    PLANET_STRENGTH_MODERATE("Moderate", "मध्यम"),
    PLANET_STRENGTH_UNKNOWN("Unknown", "अज्ञात"),

    // Kalachakra Predictions
    KALACHAKRA_PRED_INTRO_DEHA("Based on Deha lord's %1\$2 status and %2\$2 Deha-Jeeva relationship: ", "देह स्वामीको %1\$2 अवस्था र %2\$2 देह-जीव सम्बन्धको आधारमा: "),
    KALACHAKRA_PRED_INTRO_JEEVA("Jeeva lord's %1\$2 condition indicates ", "जीव स्वामीको %1\$2 अवस्थाले संकेत गर्दछ "),

    KALACHAKRA_HEALTH_PRED_HARMONIOUS("Physical health is well-supported by spiritual practices. Body responds well to holistic healing.", "शारीरिक स्वास्थ्यलाई आध्यात्मिक अभ्यासहरूले राम्रो समर्थन गर्दछ। शरीरले समग्र उपचारलाई राम्रो प्रतिक्रिया दिन्छ।"),
    KALACHAKRA_HEALTH_PRED_SUPPORTIVE("Good baseline health with spiritual practices enhancing physical wellbeing.", "राम्रो आधारभूत स्वास्थ्यको साथ आध्यात्मिक अभ्यासहरूले शारीरिक कल्याण बढाउँछ।"),
    KALACHAKRA_HEALTH_PRED_NEUTRAL("Health matters follow their natural course. Maintain regular routines.", "स्वास्थ्य मामिलाहरूले आफ्नो प्राकृतिक मार्ग पछ्याउँछन्। नियमित दिनचर्या कायम राख्नुहोस्।"),
    KALACHAKRA_HEALTH_PRED_CHALLENGING("May experience tension between physical demands and spiritual aspirations. Balance is key.", "शारीरिक माग र आध्यात्मिक आकांक्षाहरू बीच तनाव अनुभव हुन सक्छ। सन्तुलन कुञ्जी हो।"),
    KALACHAKRA_HEALTH_PRED_TRANSFORMATIVE("Health challenges may serve as catalysts for spiritual growth. Deep healing possible.", "स्वास्थ्य चुनौतीहरूले आध्यात्मिक वृद्धिको लागि उत्प्रेरकको रूपमा काम गर्न सक्छन्। गहिरो उपचार सम्भव छ।"),

    KALACHAKRA_SPIRITUAL_PRED_HARMONIOUS("natural spiritual progress. Meditation and dharmic practices flow easily.", "प्राकृतिक आध्यात्मिक प्रगति। ध्यान र धार्मिक अभ्यासहरू सजिलै प्रवाहित हुन्छन्।"),
    KALACHAKRA_SPIRITUAL_PRED_SUPPORTIVE("spiritual growth through practical application of wisdom.", "ज्ञानको व्यावहारिक प्रयोग मार्फत आध्यात्मिक वृद्धि।"),
    KALACHAKRA_SPIRITUAL_PRED_NEUTRAL("steady spiritual development through consistent practice.", "निरन्तर अभ्यास मार्फत स्थिर आध्यात्मिक विकास।"),
    KALACHAKRA_SPIRITUAL_PRED_CHALLENGING("spiritual growth through overcoming material attachments.", "भौतिक आसक्तिहरू जितेर आध्यात्मिक वृद्धि।"),
    KALACHAKRA_SPIRITUAL_PRED_TRANSFORMATIVE("profound spiritual transformation through life's challenges.", "जीवनका चुनौतीहरू मार्फत गहिरो आध्यात्मिक रूपान्तरण।"),

    // Recommendations
    KALACHAKRA_REC_DEHA_LORD("Strengthen Deha lord (%1\$2) through appropriate mantras and gemstones", "उपयुक्त मन्त्र र रत्नहरू मार्फत देह स्वामी (%1\$2) लाई बलियो बनाउनुहोस्"),
    KALACHAKRA_REC_JEEVA_LORD("Honor Jeeva lord (%1\$2) through spiritual practices", "आध्यात्मिक अभ्यासहरू मार्फत जीव स्वामी (%1\$2) लाई सम्मान गर्नुहोस्"),
    
    KALACHAKRA_REC_HARMONIOUS_1("Continue current spiritual and health practices - they are aligned", "वर्तमान आध्यात्मिक र स्वास्थ्य अभ्यासहरू जारी राख्नुहोस् - तिनीहरू पङ्क्तिबद्ध छन्"),
    KALACHAKRA_REC_HARMONIOUS_2("Use this favorable period for deepening meditation", "यो अनुकूल अवधिलाई ध्यान गहिरो बनाउन प्रयोग गर्नुहोस्"),
    KALACHAKRA_REC_SUPPORTIVE_1("Integrate physical yoga with spiritual practices", "शारीरिक योगलाई आध्यात्मिक अभ्यासहरूसँग एकीकृत गर्नुहोस्"),
    KALACHAKRA_REC_SUPPORTIVE_2("Serve others as part of spiritual path", "आध्यात्मिक मार्गको भागको रूपमा अरूको सेवा गर्नुहोस्"),
    KALACHAKRA_REC_NEUTRAL_1("Establish regular routines for both physical and spiritual health", "शारीरिक र आध्यात्मिक स्वास्थ्य दुवैको लागि नियमित दिनचर्या स्थापना गर्नुहोस्"),
    KALACHAKRA_REC_NEUTRAL_2("Create balance between material and spiritual pursuits", "भौतिक र आध्यात्मिक प्रयासहरू बीच सन्तुलन सिर्जना गर्नुहोस्"),
    KALACHAKRA_REC_CHALLENGING_1("Practice patience and acceptance with physical limitations", "शारीरिक सीमाहरूसँग धैर्य र स्वीकृति अभ्यास गर्नुहोस्"),
    KALACHAKRA_REC_CHALLENGING_2("Transform challenges into spiritual growth opportunities", "चुनौतीहरूलाई आध्यात्मिक वृद्धिको अवसरमा रूपान्तरण गर्नुहोस्"),
    KALACHAKRA_REC_TRANSFORMATIVE_1("Embrace transformation as part of soul's journey", "रूपान्तरणलाई आत्माको यात्राको भागको रूपमा अँगाल्नुहोस्"),
    KALACHAKRA_REC_TRANSFORMATIVE_2("Seek guidance from spiritual teachers during difficult periods", "कठिन समयमा आध्यात्मिक गुरुहरूबाट मार्गदर्शन लिनुहोस्"),
    LAL_KITAB_SCREEN_KARMIC_DEBTS("Karmic Debts (Rin)", "कार्मिक ऋणहरू"),
    LAL_KITAB_SCREEN_PLANETARY_AFFLICTIONS("Planetary Afflictions", "ग्रह पीडाहरू"),
    LAL_KITAB_HOUSE_REMEDIES("House-Based Remedies", "भाव-आधारित उपायहरू"),
    LAL_KITAB_DAILY_REMEDIES("Daily Remedies", "दैनिक उपायहरू"),
    LAL_KITAB_ITEM_BASED("Item-Based Remedies", "वस्तु-आधारित उपायहरू"),
    LAL_KITAB_COLOR_THERAPY("Color Therapy", "रंग चिकित्सा"),
    LAL_KITAB_DIRECTION_GUIDANCE("Direction Guidance", "दिशा मार्गदर्शन"),
    LAL_KITAB_WEEKLY_SCHEDULE("Weekly Remedy Schedule", "साप्ताहिक उपाय तालिका"),
    LAL_KITAB_REMEDY_ITEM("Remedy Item", "उपाय वस्तु"),
    LAL_KITAB_REMEDY_ACTION("Action Required", "आवश्यक कार्य"),
    LAL_KITAB_REMEDY_TIMING("Best Timing", "उत्तम समय"),
    LAL_KITAB_EFFECTIVENESS_HIGH("Highly Effective", "अत्यधिक प्रभावकारी"),
    LAL_KITAB_EFFECTIVENESS_MEDIUM("Moderately Effective", "मध्यम प्रभावकारी"),
    LAL_KITAB_DEBT_PITRU("Pitru Rin (Ancestral)", "पितृ ऋण (पूर्वज)"),
    LAL_KITAB_DEBT_MATRU("Matru Rin (Maternal)", "मातृ ऋण (मातृपक्ष)"),
    LAL_KITAB_DEBT_STRI("Stri Rin (Feminine)", "स्त्री ऋण (स्त्री)"),
    LAL_KITAB_DEBT_SELF("Swayam Rin (Self)", "स्वयं ऋण (स्वयं)"),

    // Divisional Charts Screen specific
    DIVISIONAL_CHARTS_TITLE("Divisional Charts Analysis", "विभागीय कुण्डली विश्लेषण"),
    DIVISIONAL_CHARTS_SUBTITLE("Detailed Varga Analysis", "विस्तृत वर्ग विश्लेषण"),
    DIVISIONAL_CHARTS_ABOUT("About Divisional Charts", "विभागीय कुण्डलीको बारेमा"),
    DIVISIONAL_CHARTS_ABOUT_DESC("Divisional charts (Vargas) provide deeper insights into specific life areas. D-2 for wealth, D-3 for siblings, D-9 for marriage, D-10 for career, D-12 for parents.", "विभागीय कुण्डलीहरूले विशेष जीवन क्षेत्रहरूमा गहिरो अन्तर्दृष्टि प्रदान गर्दछन्। D-2 धनको लागि, D-3 भाइबहिनीको लागि, D-9 विवाहको लागि, D-10 क्यारियरको लागि, D-12 अभिभावकको लागि।"),
    DIVISIONAL_WEALTH_ANALYSIS("Wealth & Finance", "धन र वित्त"),
    DIVISIONAL_SIBLING_ANALYSIS("Siblings & Courage", "भाइबहिनी र साहस"),
    DIVISIONAL_MARRIAGE_ANALYSIS("Marriage & Dharma", "विवाह र धर्म"),
    DIVISIONAL_CAREER_ANALYSIS("Career & Status", "क्यारियर र स्थिति"),
    DIVISIONAL_PARENTS_ANALYSIS("Parents & Ancestry", "अभिभावक र वंश"),
    DIVISIONAL_KEY_PLANETS("Key Planets", "मुख्य ग्रहहरू"),
    DIVISIONAL_STRENGTH_ASSESSMENT("Strength Assessment", "बल मूल्याङ्कन"),
    DIVISIONAL_DETAILED_RESULTS("Detailed Results", "विस्तृत परिणामहरू"),

    // Upachaya Transit Screen specific
    UPACHAYA_SCREEN_TITLE("Upachaya Transits", "उपचय गोचर"),
    UPACHAYA_SCREEN_SUBTITLE("Growth House Transit Tracker", "वृद्धि भाव गोचर ट्र्याकर"),
    UPACHAYA_SCREEN_ABOUT("About Upachaya Houses", "उपचय भावहरूको बारेमा"),
    UPACHAYA_SCREEN_ABOUT_DESC("Upachaya houses (3, 6, 10, 11) are growth houses where malefic planets give good results over time. Natural malefics improve with age in these houses.", "उपचय भावहरू (३, ६, १०, ११) वृद्धि भावहरू हुन् जहाँ पापग्रहले समयसँगै राम्रो परिणाम दिन्छन्।"),
    UPACHAYA_HOUSES("Upachaya Houses (3, 6, 10, 11)", "उपचय भावहरू (३, ६, १०, ११)"),
    UPACHAYA_SCREEN_FROM_MOON("Transit from Moon Sign", "चन्द्र राशिबाट गोचर"),
    UPACHAYA_CURRENT_TRANSITS("Current Beneficial Transits", "वर्तमान लाभदायक गोचर"),
    UPACHAYA_SCREEN_UPCOMING("Upcoming Favorable Periods", "आगामी अनुकूल अवधिहरू"),
    UPACHAYA_SATURN_TRANSIT("Saturn in Upachaya", "उपचयमा शनि"),
    UPACHAYA_MARS_TRANSIT("Mars in Upachaya", "उपचयमा मंगल"),
    UPACHAYA_RAHU_TRANSIT("Rahu in Upachaya", "उपचयमा राहु"),
    UPACHAYA_JUPITER_TRANSIT("Jupiter in Upachaya", "उपचयमा बृहस्पति"),
    UPACHAYA_SCREEN_HOUSE_3("3rd House - Courage, Siblings, Communication", "तेस्रो भाव - साहस, भाइबहिनी, सञ्चार"),
    UPACHAYA_SCREEN_HOUSE_6("6th House - Enemies, Service, Health", "छैटौं भाव - शत्रु, सेवा, स्वास्थ्य"),
    UPACHAYA_SCREEN_HOUSE_10("10th House - Career, Status, Authority", "दसौं भाव - क्यारियर, स्थिति, अधिकार"),
    UPACHAYA_SCREEN_HOUSE_11("11th House - Gains, Desires, Friends", "एघारौं भाव - लाभ, इच्छा, मित्र"),
    UPACHAYA_GROWTH_INDICATOR("Growth Indicator", "वृद्धि सूचक"),
    UPACHAYA_ACTIVE_NOW("Active Now", "अहिले सक्रिय"),
    UPACHAYA_COMING_SOON("Coming Soon", "छिट्टै आउँदैछ"),
    UPACHAYA_SCREEN_DURATION("Transit Duration", "गोचर अवधि"),
    UPACHAYA_SCREEN_EFFECTS("Expected Effects", "अपेक्षित प्रभावहरू"),
    UPACHAYA_IMPROVEMENT_AREAS("Areas of Improvement", "सुधारका क्षेत्रहरू"),
    UPACHAYA_HOUSE_ANALYSIS("House Analysis", "भाव विश्लेषण"),
    UPACHAYA_TRANSIT_DETAILS("Transit Details", "गोचर विवरण"),
    UPACHAYA_UPCOMING_TRANSITS("Upcoming Transits", "आगामी गोचरहरू"),
    UPACHAYA_ACTIVE_ALERTS("Active Alerts", "सक्रिय सूचनाहरू"),
    UPACHAYA_SIGNIFICANT_TRANSITS("Significant Transits", "महत्त्वपूर्ण गोचरहरू"),
    UPACHAYA_TRANSIT_ASSESSMENT("Transit Assessment", "गोचर मूल्याङ्कन"),
    UPACHAYA_REFERENCE_POINTS("Reference Points", "सन्दर्भ बिन्दुहरू"),
    UPACHAYA_ABOUT("About Upachaya", "उपचयको बारेमा"),

    // General UI labels needed by screens
    BIRTH_NAKSHATRA("Birth Nakshatra", "जन्म नक्षत्र"),
    BALANCE_LABEL("Balance at Birth", "जन्ममा सन्तुलन"),
    KEY_THEMES("Key Themes", "मुख्य विषयहरू"),
    EFFECTS_LABEL("Effects", "प्रभावहरू"),
    CURRENT_LABEL("Current", "वर्तमान"),
    PREVIOUS_LABEL("Previous", "अघिल्लो"),
    NEXT_LABEL("Next", "अर्को"),
    PRIMARY_LABEL("Primary", "प्राथमिक"),
    SECONDARY_LABEL("Secondary", "माध्यमिक"),
    LAGNA_LABEL("Lagna", "लग्न"),
    MOON_LABEL("Moon", "चन्द्र"),
    SUN_LABEL("Sun", "सूर्य"),
    SIGN_LABEL("Sign", "राशि"),
    HOUSE_LABEL("House", "भाव"),
    STRENGTH_LABEL("Strength", "बल"),
    RELATIONSHIP_FRIEND("Friendly", "मैत्रीपूर्ण"),
    RELATIONSHIP_ENEMY("Inimical", "शत्रुतापूर्ण"),
    RELATIONSHIP_NEUTRAL("Neutral", "तटस्थ"),
    RELATIONSHIP_SAME("Intensified", "तीव्र"),
    PANCHA_EXCELLENT("Excellent (5+)", "उत्कृष्ट (५+)"),
    PANCHA_GOOD("Good (4)", "राम्रो (४)"),
    PANCHA_AVERAGE("Average (3)", "औसत (३)"),
    PANCHA_BELOW_AVERAGE("Below Average (2)", "औसत भन्दा कम (२)"),
    PANCHA_WEAK("Weak (0-1)", "कमजोर (०-१)"),

    // ============================================
    // DASHA SYSTEMS
    // ============================================
    DASHA_VIMSOTTARI("Vimsottari", "तपाईंको"), // Correct translation for Vimsottari? "Vimsottari" is usually kept or transliterated. "विंशोत्तरी".
    // Wait, "तपाईंको" means "Yours". That's a mistake.
    // I will use proper transliteration.
    DASHA_VIMSOTTARI_NAME("Vimsottari", "विंशोत्तरी"),
    DASHA_VIMSOTTARI_DESC("Most widely used Nakshatra-based planetary period system", "सबैभन्दा बढी प्रयोग हुने नक्षत्र-आधारित ग्रह अवधि प्रणाली"),
    DASHA_VIMSOTTARI_DURATION("120 years", "१२० वर्ष"),

    DASHA_YOGINI("Yogini", "योगिनी"),
    DASHA_YOGINI_DESC("Feminine energy-based system, especially for female charts", "स्त्री ऊर्जा-आधारित प्रणाली, विशेष गरी महिला कुण्डलीको लागि"),
    DASHA_YOGINI_DURATION("36 years", "३६ वर्ष"),

    DASHA_ASHTOTTARI("Ashtottari", "अष्टोत्तरी"),
    DASHA_ASHTOTTARI_DESC("Traditional for night births, uses 8 planets", "रात्री जन्मको लागि परम्परागत, ८ ग्रहहरू प्रयोग गर्दछ"),
    DASHA_ASHTOTTARI_DURATION("108 years", "१०८ वर्ष"),

    DASHA_SUDARSHANA("Sudarshana", "सुदर्शन"),
    DASHA_SUDARSHANA_DESC("Chakra Dasha from Lagna, Moon, and Sun simultaneously", "लग्न, चन्द्र र सूर्यबाट एक साथ चक्र दशा"),
    DASHA_SUDARSHANA_DURATION("Triple view", "त्रिपक्षीय दृश्य"),
    
    DASHA_CHARA("Chara", "कारा"),
    DASHA_CHARA_DESC("Jaimini sign-based system with Karakamsha analysis", "जैमिनी राशि-आधारित प्रणाली, कारकांश विश्लेषण सहित"),
    DASHA_CHARA_DURATION("Variable", "परिवर्तनशील"),
    
    DASHA_TITLE_YOGINI("Yogini Dasha", "योगिनी दशा"),
    DASHA_TITLE_CHARA("Chara Dasha", "कारा दशा"),
    DASHA_VIEW_SYSTEM("View %s", "%s हेर्नुहोस्"),
    DASHA_CYCLE_DURATION("Cycle Duration: %s", "चक्र अवधि: %s"),
    
    DASHA_CALC_LOADING("Calculating %s Dasha timeline...", "%s दशा समयरेखा गणना गर्दै..."),
    DASHA_NO_CHART("No chart selected. Please select a birth chart to view dasha periods.", "कुनै कुण्डली चयन गरिएको छैन। दशा अवधिहरू हेर्न कृपया जन्म कुण्डली चयन गर्नुहोस्।"),
    DASHA_NOT_AVAILABLE("%s calculation not available for this chart.", "%s गणना यो कुण्डलीको लागि उपलब्ध छैन।"),

    // ============================================
    // KALACHAKRA DASHA SYSTEM
    // ============================================
    KALACHAKRA_DASHA_TITLE("Kalachakra Dasha", "कालचक्र दशा"),
    KALACHAKRA_DASHA_SUBTITLE("Body-Soul Timing System", "देह-आत्मा समय प्रणाली"),
    KALACHAKRA_DASHA_ABOUT("About Kalachakra Dasha", "कालचक्र दशाको बारेमा"),
    KALACHAKRA_DASHA_ABOUT_DESC("Kalachakra Dasha is a highly respected Nakshatra-based timing system from BPHS, particularly useful for health predictions and spiritual transformation. It operates on the principle that body (Deha) and soul (Jeeva) follow different but related cycles. Savya (clockwise) and Apsavya (anti-clockwise) groups determine life's flow direction.", "कालचक्र दशा BPHS बाट अत्यन्त सम्मानित नक्षत्र-आधारित समय प्रणाली हो, विशेष गरी स्वास्थ्य भविष्यवाणी र आध्यात्मिक रूपान्तरणको लागि उपयोगी। यो देह र आत्माले फरक तर सम्बन्धित चक्रहरू पछ्याउँछन् भन्ने सिद्धान्तमा काम गर्छ। सव्य (घडीको दिशामा) र अपसव्य (घडीको विपरीत दिशामा) समूहहरूले जीवनको प्रवाह दिशा निर्धारण गर्दछन्।"),
    KALACHAKRA_CURRENT("Current Period", "वर्तमान अवधि"),
    KALACHAKRA_TIMELINE("Timeline", "समयरेखा"),
    KALACHAKRA_DEHA_JEEVA("Deha-Jeeva", "देह-जीव"),
    KALACHAKRA_DEHA_JEEVA_TITLE("Deha-Jeeva Analysis", "देह-जीव विश्लेषण"),
    KALACHAKRA_DEHA_JEEVA_SUBTITLE("Body and Soul Relationship", "शरीर र आत्माको सम्बन्ध"),
    KALACHAKRA_DEHA("Deha", "देह"),
    KALACHAKRA_JEEVA("Jeeva", "जीव"),
    KALACHAKRA_BODY("Body", "शरीर"),
    KALACHAKRA_SOUL("Soul", "आत्मा"),
    KALACHAKRA_EFFECTS("Current Effects", "वर्तमान प्रभावहरू"),
    KALACHAKRA_NAKSHATRA_GROUP("Nakshatra Group", "नक्षत्र समूह"),
    KALACHAKRA_BIRTH_NAKSHATRA("Birth Nakshatra", "जन्म नक्षत्र"),
    KALACHAKRA_PADA("Pada", "पाद"),
    KALACHAKRA_APPLICABILITY("System Applicability", "प्रणाली प्रयोज्यता"),
    KALACHAKRA_HEALTH_INDICATOR("Health Outlook", "स्वास्थ्य दृष्टिकोण"),
    KALACHAKRA_PARAMA_AYUSH("Parama Ayush Sign - Special longevity indicator", "परम आयुष राशि - विशेष दीर्घायु सूचक"),
    KALACHAKRA_INTERPRETATION("Interpretation & Guidance", "व्याख्या र मार्गदर्शन"),
    KALACHAKRA_GUIDANCE("Guidance", "मार्गदर्शन"),
    KALACHAKRA_DEHA_ANALYSIS("Deha (Body) Analysis", "देह (शरीर) विश्लेषण"),
    KALACHAKRA_JEEVA_ANALYSIS("Jeeva (Soul) Analysis", "जीव (आत्मा) विश्लेषण"),
    KALACHAKRA_DEHA_LORD("Deha Lord", "देह स्वामी"),
    KALACHAKRA_JEEVA_LORD("Jeeva Lord", "जीव स्वामी"),
    KALACHAKRA_STRENGTH("Strength", "बल"),
    KALACHAKRA_RELATIONSHIP("Deha-Jeeva Relationship", "देह-जीव सम्बन्ध"),
    KALACHAKRA_SAVYA("Savya (Direct)", "सव्य (सीधो)"),
    KALACHAKRA_APSAVYA("Apsavya (Retrograde)", "अपसव्य (उल्टो)"),
    KALACHAKRA_RELATIONSHIP_HARMONIOUS("Harmonious", "सामञ्जस्यपूर्ण"),
    KALACHAKRA_RELATIONSHIP_SUPPORTIVE("Supportive", "सहयोगी"),
    KALACHAKRA_RELATIONSHIP_NEUTRAL("Neutral", "तटस्थ"),
    KALACHAKRA_RELATIONSHIP_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    KALACHAKRA_RELATIONSHIP_TRANSFORMATIVE("Transformative", "परिवर्तनकारी"),

    // ============================================
    // COMMON SCREEN STRINGS
    // ============================================
    SCREEN_CALCULATING("Calculating analysis...", "विश्लेषण गणना गर्दै..."),
    SCREEN_NO_DATA("No data available", "कुनै डाटा उपलब्ध छैन"),
    SCREEN_ERROR_CALCULATION("Error calculating results", "परिणाम गणनामा त्रुटि"),
    SCREEN_OVERVIEW("Overview", "सिंहावलोकन"),
    SCREEN_DETAILS("Details", "विवरणहरू"),
    SCREEN_TIMELINE("Timeline", "समयरेखा"),
    SCREEN_INTERPRETATION("Interpretation", "व्याख्या"),
    SCREEN_RECOMMENDATIONS("Recommendations", "सिफारिसहरू"),

    // ============================================
    // TARABALA AND CHANDRABALA
    // ============================================
    TARABALA_TITLE("Tarabala & Chandrabala", "ताराबल र चन्द्रबल"),
    TARABALA_SUBTITLE("Daily Strength Analysis", "दैनिक बल विश्लेषण"),
    TARABALA_TODAY("Today's Tarabala", "आजको ताराबल"),
    TARABALA_WEEKLY("Weekly Forecast", "साप्ताहिक पूर्वानुमान"),
    TARABALA_ALL_TARAS("All 27 Nakshatras", "सबै २७ नक्षत्रहरू"),
    TARABALA_BIRTH_NAKSHATRA("Birth Nakshatra", "जन्म नक्षत्र"),
    TARABALA_TRANSIT_NAKSHATRA("Transit Nakshatra", "गोचर नक्षत्र"),
    TARABALA_CYCLE("Cycle", "चक्र"),
    TARABALA_FAVORABLE("Favorable", "अनुकूल"),
    TARABALA_UNFAVORABLE("Unfavorable", "प्रतिकूल"),
    TARABALA_DAILY_SCORE("Daily Strength Score", "दैनिक बल स्कोर"),
    TARABALA_BEST_DAY("Best Day", "उत्तम दिन"),
    TARABALA_WORST_DAY("Challenging Day", "चुनौतीपूर्ण दिन"),
    TARABALA_FAVORABLE_ACTIVITIES("Favorable Activities", "अनुकूल गतिविधिहरू"),
    TARABALA_AVOID_ACTIVITIES("Activities to Avoid", "टाढा रहने गतिविधिहरू"),
    TARABALA_GENERAL_ADVICE("General Advice", "सामान्य सल्लाह"),
    TARABALA_WEEKLY_ADVICE("Weekly Guidance", "साप्ताहिक मार्गदर्शन"),

    // Nine Taras
    TARA_JANMA("Janma", "जन्म"),
    TARA_SAMPAT("Sampat", "सम्पत्"),
    TARA_VIPAT("Vipat", "विपत्"),
    TARA_KSHEMA("Kshema", "क्षेम"),
    TARA_PRATYARI("Pratyari", "प्रत्यारि"),
    TARA_SADHAKA("Sadhaka", "साधक"),
    TARA_VADHA("Vadha", "वध"),
    TARA_MITRA("Mitra", "मित्र"),
    TARA_PARAMA_MITRA("Parama Mitra", "परम मित्र"),

    // Tara Descriptions
    TARA_JANMA_DESC("Birth star day - rest and introspection recommended", "जन्म नक्षत्र दिन - आराम र आत्मचिन्तन सिफारिस"),
    TARA_SAMPAT_DESC("Wealth star - excellent for financial matters", "धन नक्षत्र - आर्थिक मामिलाहरूको लागि उत्कृष्ट"),
    TARA_VIPAT_DESC("Danger star - avoid important activities", "विपत् नक्षत्र - महत्त्वपूर्ण गतिविधिहरू टाळ्नुहोस्"),
    TARA_KSHEMA_DESC("Well-being star - favorable for health matters", "क्षेम नक्षत्र - स्वास्थ्य मामिलाहरूको लागि अनुकूल"),
    TARA_PRATYARI_DESC("Obstacle star - expect minor hindrances", "प्रत्यारि नक्षत्र - सानातिना बाधाहरू अपेक्षा गर्नुहोस्"),
    TARA_SADHAKA_DESC("Achievement star - good for completing goals", "साधक नक्षत्र - लक्ष्य पूरा गर्नको लागि राम्रो"),
    TARA_VADHA_DESC("Death star - highly inauspicious, maximum caution", "वध नक्षत्र - अत्यन्त अशुभ, अधिकतम सावधानी"),
    TARA_MITRA_DESC("Friend star - favorable for relationships", "मित्र नक्षत्र - सम्बन्धहरूको लागि अनुकूल"),
    TARA_PARAMA_MITRA_DESC("Great friend star - most auspicious for everything", "परम मित्र नक्षत्र - सबैको लागि सबैभन्दा शुभ"),

    // Chandrabala
    CHANDRABALA_TITLE("Chandrabala Analysis", "चन्द्रबल विश्लेषण"),
    CHANDRABALA_TRANSIT_MOON("Transit Moon Sign", "गोचर चन्द्र राशि"),
    CHANDRABALA_NATAL_MOON("Natal Moon Sign", "जन्म चन्द्र राशि"),
    CHANDRABALA_HOUSE_FROM_MOON("House from Moon", "चन्द्रबाट भाव"),
    CHANDRABALA_EXCELLENT("Excellent", "उत्कृष्ट"),
    CHANDRABALA_GOOD("Good", "राम्रो"),
    CHANDRABALA_NEUTRAL("Neutral", "तटस्थ"),
    CHANDRABALA_WEAK("Weak", "कमजोर"),
    CHANDRABALA_UNFAVORABLE("Unfavorable", "प्रतिकूल"),
    CHANDRABALA_SIGNIFICATIONS("House Significations", "भाव संकेतहरू"),

    // Combined Strength
    COMBINED_HIGHLY_FAVORABLE("Highly Favorable", "अत्यन्त अनुकूल"),
    COMBINED_FAVORABLE("Favorable", "अनुकूल"),
    COMBINED_MIXED("Mixed Results", "मिश्रित परिणाम"),
    COMBINED_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    COMBINED_UNFAVORABLE("Unfavorable", "प्रतिकूल"),

    // ============================================
    // DASHA SANDHI ANALYSIS
    // ============================================
    SANDHI_TITLE("Dasha Sandhi Analysis", "दशा सन्धि विश्लेषण"),
    SANDHI_SUBTITLE("Period Junction Analysis", "अवधि जोड विश्लेषण"),
    SANDHI_CURRENT("Current Sandhi", "वर्तमान सन्धि"),
    SANDHI_UPCOMING("Upcoming Sandhis", "आगामी सन्धिहरू"),
    SANDHI_RECENT("Recent Sandhis", "हालैका सन्धिहरू"),
    SANDHI_CALENDAR("Sandhi Calendar", "सन्धि क्यालेन्डर"),
    SANDHI_VOLATILITY("Volatility Score", "अस्थिरता स्कोर"),
    SANDHI_TRANSITION("Transition", "संक्रमण"),
    SANDHI_FROM_PLANET("From", "बाट"),
    SANDHI_TO_PLANET("To", "मा"),
    SANDHI_DAYS_UNTIL("Days Until Transition", "संक्रमणसम्म दिन"),
    SANDHI_PROGRESS("Sandhi Progress", "सन्धि प्रगति"),
    SANDHI_KEY_DATES("Key Dates", "मुख्य मितिहरू"),
    SANDHI_PREDICTIONS("Predictions", "भविष्यवाणीहरू"),
    SANDHI_CAREER_EFFECTS("Career Effects", "क्यारियर प्रभावहरू"),
    SANDHI_HEALTH_CONCERNS("Health Considerations", "स्वास्थ्य विचारणाहरू"),
    SANDHI_RELATIONSHIP_IMPACT("Relationship Impact", "सम्बन्ध प्रभाव"),
    SANDHI_FINANCIAL_TREND("Financial Trend", "आर्थिक प्रवृत्ति"),
    SANDHI_SPIRITUAL("Spiritual Opportunities", "आध्यात्मिक अवसरहरू"),
    SANDHI_AFFECTED_AREAS("Affected Life Areas", "प्रभावित जीवन क्षेत्रहरू"),
    SANDHI_GENERAL_GUIDANCE("General Guidance", "सामान्य मार्गदर्शन"),

    // Sandhi Intensity
    SANDHI_INTENSITY_CRITICAL("Critical", "गम्भीर"),
    SANDHI_INTENSITY_HIGH("High", "उच्च"),
    SANDHI_INTENSITY_MODERATE("Moderate", "मध्यम"),
    SANDHI_INTENSITY_MILD("Mild", "हल्का"),
    SANDHI_INTENSITY_MINIMAL("Minimal", "न्यूनतम"),

    // Transition Types
    TRANSITION_FRIEND_FRIEND("Friend to Friend", "मित्रबाट मित्रमा"),
    TRANSITION_FRIEND_NEUTRAL("Friend to Neutral", "मित्रबाट तटस्थमा"),
    TRANSITION_FRIEND_ENEMY("Friend to Enemy", "मित्रबाट शत्रुमा"),
    TRANSITION_NEUTRAL_FRIEND("Neutral to Friend", "तटस्थबाट मित्रमा"),
    TRANSITION_NEUTRAL_NEUTRAL("Neutral to Neutral", "तटस्थबाट तटस्थमा"),
    TRANSITION_NEUTRAL_ENEMY("Neutral to Enemy", "तटस्थबाट शत्रुमा"),
    TRANSITION_ENEMY_FRIEND("Enemy to Friend", "शत्रुबाट मित्रमा"),
    TRANSITION_ENEMY_NEUTRAL("Enemy to Neutral", "शत्रुबाट तटस्थमा"),
    TRANSITION_ENEMY_ENEMY("Enemy to Enemy", "शत्रुबाट शत्रुमा"),

    // ============================================
    // GOCHARA VEDHA ANALYSIS
    // ============================================
    VEDHA_TITLE("Gochara Vedha Analysis", "गोचर वेध विश्लेषण"),
    VEDHA_SUBTITLE("Transit Obstruction Analysis", "गोचर अवरोध विश्लेषण"),
    VEDHA_OVERVIEW("Transit Overview", "गोचर सिंहावलोकन"),
    VEDHA_ACTIVE("Active Vedhas", "सक्रिय वेधहरू"),
    VEDHA_PLANET_TRANSITS("Planetary Transits", "ग्रह गोचरहरू"),
    VEDHA_OVERALL_SCORE("Overall Transit Score", "समग्र गोचर स्कोर"),
    VEDHA_FAVORABLE_COUNT("Favorable Transits", "अनुकूल गोचरहरू"),
    VEDHA_OBSTRUCTED_COUNT("Obstructed Transits", "अवरुद्ध गोचरहरू"),
    VEDHA_KEY_INSIGHTS("Key Insights", "मुख्य अन्तर्दृष्टिहरू"),
    VEDHA_OBSTRUCTED_BY("Obstructed by", "द्वारा अवरुद्ध"),
    VEDHA_LOST_BENEFITS("Lost Benefits", "गुमाएको फाइदाहरू"),

    // Vedha Severity
    VEDHA_COMPLETE("Complete Obstruction", "पूर्ण अवरोध"),
    VEDHA_STRONG("Strong Obstruction", "बलियो अवरोध"),
    VEDHA_MODERATE("Moderate Obstruction", "मध्यम अवरोध"),
    VEDHA_PARTIAL("Partial Obstruction", "आंशिक अवरोध"),
    VEDHA_NONE("No Obstruction", "कुनै अवरोध छैन"),

    // Transit Effectiveness
    TRANSIT_EXCELLENT("Excellent", "उत्कृष्ट"),
    TRANSIT_GOOD("Good", "राम्रो"),
    TRANSIT_MODERATE("Moderate", "मध्यम"),
    TRANSIT_WEAK("Weak", "कमजोर"),
    TRANSIT_NULLIFIED("Nullified by Vedha", "वेधद्वारा रद्द"),
    TRANSIT_UNFAVORABLE("Unfavorable Transit", "प्रतिकूल गोचर"),

    // ============================================
    // KEMADRUMA YOGA ANALYSIS
    // ============================================
    KEMADRUMA_TITLE("Kemadruma Yoga Analysis", "केमद्रुम योग विश्लेषण"),
    KEMADRUMA_SUBTITLE("Moon Support Analysis", "चन्द्र समर्थन विश्लेषण"),
    KEMADRUMA_MOON_ANALYSIS("Moon Analysis", "चन्द्र विश्लेषण"),
    KEMADRUMA_FORMATION("Formation Details", "गठन विवरण"),
    KEMADRUMA_CANCELLATIONS("Cancellation Factors", "रद्दीकरण कारकहरू"),
    KEMADRUMA_EMOTIONAL("Emotional Impact", "भावनात्मक प्रभाव"),
    KEMADRUMA_FINANCIAL("Financial Impact", "आर्थिक प्रभाव"),
    KEMADRUMA_SOCIAL("Social Impact", "सामाजिक प्रभाव"),
    KEMADRUMA_ACTIVATION("Activation Periods", "सक्रियता अवधिहरू"),
    KEMADRUMA_MENTAL_PEACE("Mental Peace", "मानसिक शान्ति"),
    KEMADRUMA_STABILITY("Emotional Stability", "भावनात्मक स्थिरता"),
    KEMADRUMA_CONFIDENCE("Confidence Level", "आत्मविश्वास स्तर"),
    KEMADRUMA_ANXIETY("Anxiety Tendency", "चिन्ता प्रवृत्ति"),
    KEMADRUMA_DEPRESSION_RISK("Depression Risk", "अवसाद जोखिम"),
    KEMADRUMA_WEALTH_RETENTION("Wealth Retention", "धन संरक्षण"),
    KEMADRUMA_FAMILY_SUPPORT("Family Support", "पारिवारिक समर्थन"),
    KEMADRUMA_FRIENDSHIP("Friendship Quality", "मित्रता गुणस्तर"),
    KEMADRUMA_ISOLATION("Isolation Tendency", "एकान्त प्रवृत्ति"),

    // Kemadruma Status
    KEMADRUMA_NOT_PRESENT("Not Present", "उपस्थित छैन"),
    KEMADRUMA_FULLY_CANCELLED("Fully Cancelled", "पूर्ण रूपमा रद्द"),
    KEMADRUMA_MOSTLY_CANCELLED("Mostly Cancelled", "प्रायः रद्द"),
    KEMADRUMA_PARTIALLY_CANCELLED("Partially Cancelled", "आंशिक रूपमा रद्द"),
    KEMADRUMA_WEAKLY_CANCELLED("Weakly Cancelled", "कमजोर रूपमा रद्द"),
    KEMADRUMA_ACTIVE_MODERATE("Active (Moderate)", "सक्रिय (मध्यम)"),
    KEMADRUMA_ACTIVE_SEVERE("Active (Severe)", "सक्रिय (गम्भीर)"),

    // Bhanga (Cancellation) Types
    BHANGA_KENDRA_MOON("Planets in Kendra from Moon", "चन्द्रबाट केन्द्रमा ग्रहहरू"),
    BHANGA_KENDRA_LAGNA("Planets in Kendra from Lagna", "लग्नबाट केन्द्रमा ग्रहहरू"),
    BHANGA_MOON_KENDRA("Moon in Kendra from Lagna", "लग्नबाट केन्द्रमा चन्द्र"),
    BHANGA_BENEFIC_ASPECT("Benefic planet aspects Moon", "शुभ ग्रहले चन्द्रलाई देख्छ"),
    BHANGA_BENEFIC_CONJUNCTION("Benefic planet conjuncts Moon", "शुभ ग्रह चन्द्रसँग युति"),
    BHANGA_MOON_EXALTED("Moon in exaltation", "चन्द्र उच्चमा"),
    BHANGA_MOON_OWN("Moon in own sign", "चन्द्र स्वराशिमा"),
    BHANGA_MOON_FRIEND("Moon in friendly sign", "चन्द्र मित्र राशिमा"),
    BHANGA_FULL_MOON("Full or bright Moon", "पूर्ण वा उज्यालो चन्द्र"),
    BHANGA_ANGULAR_MOON("Moon in angular house", "कोणीय भावमा चन्द्र"),
    BHANGA_STRONG_DISPOSITOR("Strong dispositor", "बलियो राशीश"),
    BHANGA_JUPITER_ASPECT("Jupiter aspects Moon", "बृहस्पतिले चन्द्रलाई देख्छ"),
    BHANGA_VENUS_ASPECT("Venus aspects Moon", "शुक्रले चन्द्रलाई देख्छ"),

    // Moon Brightness
    MOON_BRIGHTNESS_FULL("Full Moon", "पूर्णिमा"),
    MOON_BRIGHTNESS_BRIGHT("Bright Moon", "उज्यालो चन्द्र"),
    MOON_BRIGHTNESS_AVERAGE("Average Moon", "औसत चन्द्र"),
    MOON_BRIGHTNESS_DIM("Dim Moon", "धमिलो चन्द्र"),
    MOON_BRIGHTNESS_NEW("New Moon", "अमावस्या"),

    // Lunar Paksha
    PAKSHA_SHUKLA("Shukla Paksha", "शुक्ल पक्ष"),
    PAKSHA_KRISHNA("Krishna Paksha", "कृष्ण पक्ष"),

    // Impact Levels
    IMPACT_SEVERE("Severe", "गम्भीर"),
    IMPACT_HIGH("High", "उच्च"),
    IMPACT_MODERATE("Moderate", "मध्यम"),
    IMPACT_MILD("Mild", "हल्का"),
    IMPACT_MINIMAL("Minimal", "न्यूनतम"),
    IMPACT_POSITIVE("Positive", "सकारात्मक"),

    // ============================================
    // CHAT & STORMY AI STRINGS
    // ============================================
    CHAT_NEW("New Chat", "नयाँ च्याट"),
    CHAT_DELETE("Delete Chat", "च्याट मेटाउनुहोस्"),
    CHAT_DELETE_CONFIRM("Are you sure you want to delete \"%s\"? This cannot be undone.", "के तपाईं \"%s\" मेटाउन निश्चित हुनुहुन्छ? यो पूर्ववत गर्न सकिँदैन।"),
    CHAT_DELETE_BTN("Delete", "मेटाउनुहोस्"),
    CHAT_CANCEL_BTN("Cancel", "रद्द गर्नुहोस्"),
    CHAT_ARCHIVE("Archive", "संग्रह"),
    CHAT_MORE_OPTIONS("More options", "थप विकल्पहरू"),
    CHAT_MESSAGES_COUNT("%d messages", "%d सन्देशहरू"),
    CHAT_JUST_NOW("Just now", "अहिले भर्खरै"),
    CHAT_MINUTES_AGO("%dm ago", "%dमि. पहिले"),
    CHAT_HOURS_AGO("%dh ago", "%d घण्टा पहिले"),
    CHAT_DAYS_AGO("%dd ago", "%d दिन पहिले"),

    // Stormy AI Welcome
    STORMY_TITLE("Stormy", "स्टर्मी"),
    STORMY_MEET("Meet Stormy", "स्टर्मीलाई भेट्नुहोस्"),
    STORMY_SUBTITLE("Your Vedic astrology AI assistant", "तपाईंको वैदिक ज्योतिष एआई सहायक"),
    STORMY_INTRO("Ask about your birth chart, planetary periods, transits, compatibility, remedies, and more.", "आफ्नो जन्म कुण्डली, ग्रह अवधि, गोचर, अनुकूलता, उपाय र थप बारेमा सोध्नुहोस्।"),
    STORMY_HELLO("Hello! I'm Stormy", "नमस्ते! म स्टर्मी हुँ"),
    STORMY_HELLO_DESC("Your Vedic astrology assistant. Ask me anything about your birth chart, planetary periods, transits, compatibility, or remedies.", "तपाईंको वैदिक ज्योतिष सहायक। आफ्नो जन्म कुण्डली, ग्रह अवधि, गोचर, अनुकूलता, वा उपायहरूको बारेमा मलाई केही पनि सोध्नुहोस्।"),
    STORMY_START_CHAT("Start New Chat", "नयाँ च्याट सुरु गर्नुहोस्"),
    STORMY_CONFIGURE_MODELS("Configure AI Models", "एआई मोडेलहरू कन्फिगर गर्नुहोस्"),
    STORMY_ENABLE_MODELS("Enable AI models to start chatting", "च्याट सुरु गर्न एआई मोडेलहरू सक्षम गर्नुहोस्"),
    STORMY_ASK_PLACEHOLDER("Ask Stormy...", "स्टर्मीलाई सोध्नुहोस्..."),

    // Stormy Status Messages
    STORMY_THINKING("Stormy is thinking...", "स्टर्मीले सोच्दैछ..."),
    STORMY_REASONING("Stormy is reasoning...", "स्टर्मीले तर्क गर्दैछ..."),
    STORMY_TYPING("Stormy is typing...", "स्टर्मीले टाइप गर्दैछ..."),
    STORMY_ANALYZING("Stormy is analyzing...", "स्टर्मीले विश्लेषण गर्दैछ..."),
    STORMY_ANALYZING_COMPATIBILITY("Stormy is analyzing compatibility...", "स्टर्मीले अनुकूलता विश्लेषण गर्दैछ..."),
    STORMY_CALLING_TOOL("Calling %s...", "%s कल गर्दैछ..."),
    STORMY_USING_TOOLS("Using tools: %s", "उपकरणहरू प्रयोग गर्दैछ: %s"),
    STORMY_USING_TOOL("Using %s...", "%s प्रयोग गर्दैछ..."),
    STORMY_REASONING_VEDIC("Reasoning through Vedic principles...", "वैदिक सिद्धान्तहरूमा तर्क गर्दैछ..."),
    STORMY_GATHERING_DATA("Gathering astrological data...", "ज्योतिषीय डाटा संकलन गर्दैछ..."),
    STORMY_COMPOSING("Composing response...", "प्रतिक्रिया रचना गर्दैछ..."),
    STORMY_APPLYING_VEDIC("Applying Vedic principles...", "वैदिक सिद्धान्तहरू लागू गर्दैछ..."),
    STORMY_PROCESSING("Processing...", "प्रशोधन गर्दैछ..."),
    STORMY_NEEDS_INPUT("Stormy needs your input", "स्टर्मीलाई तपाईंको इनपुट चाहिन्छ"),

    // Agentic Section UI Strings
    SECTION_REASONING("Reasoning", "तर्क"),
    SECTION_ASTROLOGICAL_TOOLS("Astrological Tools", "ज्योतिषीय उपकरणहरू"),
    SECTION_TASK_STARTED("Started: %s", "सुरु भयो: %s"),
    SECTION_TASK_COMPLETED("Completed: %s", "सम्पन्न भयो: %s"),
    SECTION_REASONED_FOR("Reasoned for %s", "%s को लागि तर्क गरियो"),
    SECTION_ANALYZING("Analyzing...", "विश्लेषण गर्दैछ..."),
    SECTION_TAP_TO_VIEW("Tap to view", "हेर्न ट्याप गर्नुहोस्"),
    SECTION_TAP_TO_VIEW_REASONING("Tap to view reasoning", "तर्क हेर्न ट्याप गर्नुहोस्"),
    SECTION_VEDIC_ANALYSIS("Vedic analysis process", "वैदिक विश्लेषण प्रक्रिया"),
    SECTION_COLLAPSE("Collapse", "संकुचित गर्नुहोस्"),
    SECTION_EXPAND("Expand", "विस्तार गर्नुहोस्"),

    // Tool Execution Status
    TOOL_STATUS_PENDING("Pending", "पर्खिएको"),
    TOOL_STATUS_RUNNING("Running...", "चलिरहेको..."),
    TOOL_STATUS_COMPLETED_IN("Completed in %s", "%s मा सम्पन्न"),
    TOOL_STATUS_FAILED("Failed", "असफल"),
    TOOLS_USED_LABEL("Used: %s", "प्रयोग गरियो: %s"),
    TOOLS_MORE_COUNT(" +%d more", " +%d थप"),

    // Todo List
    TODO_IN_PROGRESS("In Progress", "प्रगतिमा"),

    // Ask User Placeholder
    PLACEHOLDER_TYPE_RESPONSE("Type your response...", "तपाईंको प्रतिक्रिया टाइप गर्नुहोस्..."),
    RESPONSE_SUBMITTED("Response submitted", "प्रतिक्रिया पेश गरियो"),

    // Profile Operation Status
    PROFILE_STATUS_PENDING("Pending", "पर्खिएको"),
    PROFILE_STATUS_IN_PROGRESS("In Progress", "प्रगतिमा"),
    PROFILE_STATUS_SUCCESS("Success", "सफल"),
    PROFILE_STATUS_FAILED("Failed", "असफल"),

    // Profile Operation Types
    PROFILE_OP_CREATING("Creating Profile", "प्रोफाइल बनाउँदै"),
    PROFILE_OP_UPDATING("Updating Profile", "प्रोफाइल अपडेट गर्दै"),
    PROFILE_OP_DELETING("Deleting Profile", "प्रोफाइल हटाउँदै"),
    PROFILE_OP_VIEWING("Viewing Profile", "प्रोफाइल हेर्दै"),
    PROFILE_ID_LABEL("ID: %s", "आईडी: %s"),

    // AI Generation Status
    AI_GENERATING("Generating...", "उत्पन्न गर्दैछ..."),
    AI_ANALYZING_QUESTION("Analyzing your question...", "तपाईंको प्रश्न विश्लेषण गर्दै..."),

    // Chat Suggestions
    CHAT_SUGGESTION_DASHA("What's my current dasha period?", "मेरो हालको दशा अवधि के हो?"),
    CHAT_SUGGESTION_CHART("Analyze my birth chart", "मेरो जन्म कुण्डली विश्लेषण गर्नुहोस्"),
    CHAT_SUGGESTION_YOGAS("What yogas are present in my chart?", "मेरो कुण्डलीमा कुन योगहरू छन्?"),

    // Chat Actions
    CHAT_CLEAR("Clear Chat", "च्याट खाली गर्नुहोस्"),
    CHAT_CLEAR_CONFIRM("Are you sure you want to clear all messages in this conversation?", "के तपाईं यस कुराकानीका सबै सन्देशहरू हटाउन निश्चित हुनुहुन्छ?"),
    CHAT_CLEAR_BTN("Clear", "खाली गर्नुहोस्"),
    CHAT_STOP("Stop", "रोक्नुहोस्"),
    CHAT_SEND("Send", "पठाउनुहोस्"),
    CHAT_BACK("Back", "पछाडि"),
    CHAT_CHANGE_MODEL("Change model", "मोडेल परिवर्तन गर्नुहोस्"),
    CHAT_MODEL_OPTIONS("Model options", "मोडेल विकल्पहरू"),

    // Model Options
    MODEL_OPTIONS_TITLE("Model Options", "मोडेल विकल्पहरू"),
    MODEL_THINKING_MODE("Thinking Mode", "सोच्ने मोड"),
    MODEL_THINKING_DESC("Extended reasoning before answering", "जवाफ दिनु अघि विस्तारित तर्क"),
    MODEL_WEB_SEARCH("Web Search", "वेब खोज"),
    MODEL_WEB_SEARCH_DESC("Search the web for current information", "वर्तमान जानकारीको लागि वेब खोज्नुहोस्"),
    MODEL_DONE("Done", "सम्पन्न"),

    // Model Selector
    MODEL_SELECT_TITLE("Select AI Model", "एआई मोडेल छान्नुहोस्"),
    MODEL_AI_LABEL("AI Model", "एआई मोडेल"),
    MODEL_SELECT_PROMPT("Select a model", "मोडेल छान्नुहोस्"),
    MODEL_NONE_AVAILABLE("No models available", "कुनै मोडेल उपलब्ध छैन"),
    MODEL_CONFIGURE("Configure Models", "मोडेलहरू कन्फिगर गर्नुहोस्"),
    MODEL_MANAGE("Manage AI Models", "एआई मोडेलहरू व्यवस्थापन गर्नुहोस्"),

    // ============================================
    // DIVISIONAL CHARTS - HORA (D-2) WEALTH
    // ============================================
    HORA_SUN_TITLE("Sun Hora - Self-Earned Wealth", "सूर्य होरा - स्व-अर्जित धन"),
    HORA_SUN_DESC("These planets indicate potential for wealth through your own efforts", "यी ग्रहहरूले तपाईंको आफ्नै प्रयासबाट धनको सम्भावना संकेत गर्छन्"),
    HORA_MOON_TITLE("Moon Hora - Inherited/Liquid Wealth", "चन्द्र होरा - विरासत/तरल धन"),
    HORA_MOON_DESC("These planets indicate potential for inherited or liquid assets", "यी ग्रहहरूले विरासत वा तरल सम्पत्तिको सम्भावना संकेत गर्छन्"),
    HORA_WEALTH_SOURCES("Wealth Sources", "धनका स्रोतहरू"),

    // ============================================
    // DIVISIONAL CHARTS - DREKKANA (D-3) SIBLINGS
    // ============================================
    DREKKANA_PHYSICAL("Physical", "शारीरिक"),
    DREKKANA_MENTAL("Mental", "मानसिक"),
    DREKKANA_INITIATIVE("Initiative", "पहल"),
    DREKKANA_ARTISTIC("Artistic Talents", "कलात्मक प्रतिभाहरू"),

    // Courage Levels
    COURAGE_LEVEL("Level", "स्तर"),
    COURAGE_PHYSICAL("Physical Courage", "शारीरिक साहस"),
    COURAGE_MENTAL("Mental Courage", "मानसिक साहस"),
    COURAGE_INITIATIVE("Initiative", "पहल"),

    // ============================================
    // DIVISIONAL CHARTS - NAVAMSA (D-9) MARRIAGE
    // ============================================

    // ============================================
    // DIVISIONAL CHARTS - DASHAMSA (D-10) CAREER
    // ============================================

    // ============================================
    // DIVISIONAL CHARTS - DWADASAMSA (D-12) PARENTS
    // ============================================
    DWADASAMSA_HEALTH_CONCERNS("Health Concerns", "स्वास्थ्य चिन्ताहरू"),

    // ============================================
    // AI MODELS SCREEN
    // ============================================
    AI_MODELS_TITLE("AI Models", "AI मोडेलहरू"),
    AI_MODELS_ENABLED_COUNT("%d models enabled", "%d मोडेलहरू सक्षम"),
    AI_MODELS_BACK("Back", "पछाडि"),
    AI_MODELS_REFRESH("Refresh models", "मोडेलहरू रिफ्रेस गर्नुहोस्"),
    AI_MODELS_FREE_TITLE("Free AI Models", "निःशुल्क AI मोडेलहरू"),
    AI_MODELS_FREE_DESC("These models are provided by free API providers and don't require any API keys. Model availability may vary.", "यी मोडेलहरू निःशुल्क API प्रदायकहरूबाट प्रदान गरिएका हुन् र कुनै API कुञ्जी आवश्यक पर्दैन। मोडेल उपलब्धता फरक हुन सक्छ।"),
    AI_MODELS_DEFAULT("Default Model", "पूर्वनिर्धारित मोडेल"),
    AI_MODELS_NOT_SET("Not set", "सेट गरिएको छैन"),
    AI_MODELS_SELECT_DEFAULT("Select Default Model", "पूर्वनिर्धारित मोडेल छान्नुहोस्"),
    AI_MODELS_CANCEL("Cancel", "रद्द गर्नुहोस्"),
    AI_MODELS_MODELS_ENABLED("%d/%d models enabled", "%d/%d मोडेलहरू सक्षम"),
    AI_MODELS_ENABLE_ALL("Enable All", "सबै सक्षम गर्नुहोस्"),
    AI_MODELS_DISABLE_ALL("Disable All", "सबै असक्षम गर्नुहोस्"),
    AI_MODELS_TOOLS("Tools", "उपकरणहरू"),
    AI_MODELS_REASONING("Reasoning", "तर्क"),
    AI_MODELS_VISION("Vision", "दृष्टि"),
    AI_MODELS_NONE("No Models Available", "कुनै मोडेल उपलब्ध छैन"),
    AI_MODELS_NONE_DESC("Unable to fetch AI models. Check your internet connection and try again.", "AI मोडेलहरू प्राप्त गर्न असमर्थ। आफ्नो इन्टरनेट जडान जाँच गर्नुहोस् र पुन: प्रयास गर्नुहोस्।"),
    AI_MODELS_RETRY("Retry", "पुन: प्रयास गर्नुहोस्"),
    
    // AI Providers
    AI_PROVIDER_DEEPINFRA("DeepInfra", "डीपइन्फ्रा"),
    AI_PROVIDER_QWEN("Qwen", "क्वेन"),
    AI_PROVIDER_BLACKBOX("Blackbox AI", "ब्ल्याकबक्स AI"),
    AI_PROVIDER_DDG("DuckDuckGo AI", "डकडकगो AI"),

    // ============================================
    // COMMON UI STRINGS
    // ============================================
    BTN_GOT_IT("Got it", "बुझें"),

    // ============================================
    // SHADBALA SCREEN
    // ============================================
    SHADBALA_STHANA("Sthana", "स्थान"),
    SHADBALA_DIG("Dig", "दिक्"),
    SHADBALA_KALA("Kala", "काल"),
    SHADBALA_CHESTA("Chesta", "चेष्टा"),
    SHADBALA_TOTAL("Total", "कुल"),

    // ============================================
    // UPACHAYA TRANSIT SCREEN
    // ============================================
    UPACHAYA_MOON_SIGN("Moon Sign", "चन्द्र राशि"),
    UPACHAYA_LAGNA("Lagna", "लग्न"),
    UPACHAYA_ACTIVE("Active", "सक्रिय"),

    // ============================================
    // UI SCREENS & EMPTY STATES
    // ============================================
    UI_NO_CHART_DATA("No Chart Data", "कुन्डली डाटा उपलब्ध छैन"),
    UI_NO_BIRTH_CHART("No Birth Chart", "कुनै जन्म कुन्डली छैन"),
    UI_SELECT_CHART("Please select a birth chart", "कृपया जन्म कुन्डली चयन गर्नुहोस्"),
    UI_ERROR_LOADING("Error loading chart", "कुन्डली लोड गर्दा त्रुटि"),
    
    // Ashtakavarga
    UI_NO_ASHTAKAVARGA_DATA("No Ashtakavarga Data", "अष्टकवर्ग डाटा छैन"),
    
    // Arudha
    UI_NO_ARUDHA_YOGAS("No significant Arudha Yogas found", "कुनै महत्त्वपूर्ण आरुढ योग फेला परेन"),
    
    // Dasha Sandhi
    UI_NO_ACTIVE_SANDHI("No Active Sandhi", "कुनै सक्रिय सन्धि छैन"),
    UI_NO_UPCOMING_SANDHI("No upcoming Sandhi periods in the analysis window.", "विश्लेषण अवधिमा कुनै आगामी सन्धि अवधि छैन।"),
    UI_NO_CALENDAR_ENTRIES("No calendar entries available.", "कुनै क्यालेन्डर प्रविष्टिहरू उपलब्ध छैनन्।"),
    
    // Gochara Vedha
    UI_NO_ACTIVE_VEDHAS("No Active Vedhas", "कुनै सक्रिय वेध छैन"),
    
    // Graha Yuddha
    UI_NO_PLANET_WARS("No Active Planetary Wars", "कुनै सक्रिय ग्रह युद्ध छैन"),
    UI_NO_WAR_DASHA("No War-Related Dasha Effects", "कुनै युद्ध-सम्बन्धित दशा प्रभावहरू छैन"),
    UI_NO_WAR_REMEDIES("No Specific Remedies Needed", "कुनै विशिष्ट उपाय आवश्यक छैन"),
    UI_NO_ACTIVE_WAR_REMEDIES("No active wars to remediate", "उपचार गर्न कुनै सक्रिय युद्ध छैन"),
    
    // Kemadruma
    UI_KEMADRUMA_CONDITION_MET("No planets in houses adjacent to Moon - Kemadruma condition met", "चन्द्रमाको छेउका भावहरूमा कुनै ग्रह छैन - केमद्रुम अवस्था पूरा भयो"),
    UI_NO_REMEDIES_NEEDED("No Remedies Needed", "कुनै उपाय आवश्यक छैन"),
    
    // Panch Mahapurusha / Yogas
    UI_NO_YOGAS_FORMED("No Yogas Formed", "कुनै योग बनेको छैन"),
    UI_NO_YOGAS_DISPLAY("No Yogas to Display", "देखाउनको लागि कुनै योग छैन"),
    
    // ============================================
    // ARUDHA PADA SCREEN
    // ============================================
    ARUDHA_SCREEN_TITLE("Arudha Pada", "आरुढ पद"),
    ARUDHA_ANALYSIS_TITLE("Arudha Pada Analysis", "आरुढ पद विश्लेषण"),
    ARUDHA_KEY_POSITIONS("Key Arudha Positions", "मुख्य आरुढ स्थितिहरू"),
    ARUDHA_MANIFESTATION("Manifestation Strength", "प्रकटीकरण शक्ति"),
    ARUDHA_PUBLIC_IMAGE("Public Image", "सार्वजनिक छवि"),
    ARUDHA_CAREER("Career", "क्यारियर"),
    ARUDHA_GAINS("Gains", "लाभ"),
    ARUDHA_RELATIONSHIPS("Relationships", "सम्बन्धहरू"),
    ARUDHA_KEY_THEMES("Key Themes", "मुख्य विषयवस्तुहरू"),
    ARUDHA_YOGAS_TITLE("Arudha Yogas", "आरुढ योगहरू"),
    ARUDHA_RECOMMENDATIONS("Recommendations", "सिफारिसहरू"),
    ARUDHA_CALCULATING("Calculating Arudha Padas...", "आरुढ पद गणना गर्दै..."),
    ARUDHA_ABOUT_BTN("About Arudha Pada", "आरुढ पदको बारेमा"),
    
    // ============================================
    // ASHTAKAVARGA SCREEN
    // ============================================
    ASHTAKAVARGA_KEY_INSIGHTS("Key Insights", "मुख्य अन्तर्दृष्टि"),
    ASHTAKAVARGA_STRONGEST("STRONGEST HOUSES", "सबैभन्दा बलियो भावहरू"),
    ASHTAKAVARGA_WEAKEST("WEAKEST HOUSES", "सबैभन्दा कमजोर भावहरू"),
    ASHTAKAVARGA_PLANET_TOTALS("Planet Bindu Totals", "ग्रह बिन्दु योग"),
    ASHTAKAVARGA_SARVA_DESC("Sarvashtakavarga is the combined bindu strength from all seven planets for each house/sign.", "सर्वाष्टकवर्ग प्रत्येक भाव/राशिको लागि सातै ग्रहहरूको संयुक्त बिन्दु शक्ति हो।"),
    ASHTAKAVARGA_ABOUT_TITLE("About Ashtakavarga", "अष्टकवर्गको बारेमा"),
    ASHTAKAVARGA_ABOUT_DESC("Ashtakavarga is a unique Vedic system that calculates the strength of each house by analyzing beneficial points (bindus) contributed by seven planets.", "अष्टकवर्ग एक अद्वितीय वैदिक प्रणाली हो जसले सात ग्रहहरूद्वारा प्रदान गरिएको शुभ बिन्दुहरूको विश्लेषण गरेर प्रत्येक भावको शक्ति गणना गर्दछ।"),
    ASHTAKAVARGA_INTERPRETATION("Interpretation Guide:", "व्याख्या मार्गदर्शन:"),
    ASHTAKAVARGA_GUIDE_TEXT("• 30+ bindus: Very strong, excellent results\n• 25-30 bindus: Good strength, favorable\n• 20-25 bindus: Average, mixed results\n• Below 20: Weak, challenges expected", "• ३०+ बिन्दु: धेरै बलियो, उत्कृष्ट परिणाम\n• २५-३० बिन्दु: राम्रो शक्ति, अनुकूल\n• २०-२५ बिन्दु: औसत, मिश्रित परिणाम\n• २० भन्दा कम: कमजोर, चुनौतीहरू अपेक्षित"),
    ASHTAKAVARGA_BINDUS_SUFFIX("bindus", "बिन्दुहरू"),
    
    // House Significations (Short)
    HOUSE_SIG_1("Self, Body, Personality", "स्वयं, शरीर, व्यक्तित्व"),
    HOUSE_SIG_2("Wealth, Speech, Family", "धन, वाणी, परिवार"),
    HOUSE_SIG_3("Siblings, Courage", "दाजुभाइ, साहस"),
    HOUSE_SIG_4("Home, Mother, Property", "घर, आमा, सम्पत्ति"),
    HOUSE_SIG_5("Children, Education", "सन्तान, शिक्षा"),
    HOUSE_SIG_6("Health, Enemies", "स्वास्थ्य, शत्रु"),
    HOUSE_SIG_7("Marriage, Partnership", "विवाह, साझेदारी"),
    HOUSE_SIG_8("Transformation", "रूपान्तरण"),
    HOUSE_SIG_9("Fortune, Father", "भाग्य, पिता"),
    HOUSE_SIG_10("Career, Fame", "क्यारियर, प्रसिद्धि"),
    HOUSE_SIG_11("Gains, Wishes", "लाभ, इच्छा"),
    HOUSE_SIG_12("Losses, Liberation", "हानी, मोक्ष"),
    
    // ============================================
    // AVASTHA SCREEN
    // ============================================
    AVASTHA_SCREEN_TITLE("Planetary Avasthas", "ग्रह अवस्थाहरू"),
    AVASTHA_SUBTITLE("Planetary States & Conditions", "ग्रह स्थिति र अवस्थाहरू"),
    AVASTHA_NO_CHART_DESC("Create or select a birth chart to analyze planetary Avasthas.", "ग्रह अवस्थाहरू विश्लेषण गर्न जन्म कुण्डली सिर्जना गर्नुहोस् वा छान्नुहोस्।"),
    AVASTHA_ABOUT_TITLE("About Planetary Avasthas", "ग्रह अवस्थाहरूको बारेमा"),
    AVASTHA_ABOUT_DESC("Avasthas are planetary states that indicate how effectively a planet can deliver its results.\n\nFour Types of Avasthas:\n\n1. Baladi (Age-based):\nBala (Infant), Kumara (Youth), Yuva (Adult), Vriddha (Old), Mrita (Dead)\n\n2. Jagradadi (Alertness):\nJagrat (Awake), Swapna (Dreaming), Sushupti (Deep Sleep)\n\n3. Deeptadi (Dignity):\nDeepta, Swastha, Mudita, Shanta, Dina, Vikala, Khala, Kopa, Bhita\n\n4. Lajjitadi (Emotional):\nLajjita, Garvita, Kshudita, Trushita, Mudita, Kshobhita\n\nA planet in good avasthas gives its full results, while one in poor avasthas struggles to manifest its significations.", "अवस्थाहरू ग्रहका स्थितिहरू हुन् जसले ग्रहले आफ्ना नतिजाहरू कत्तिको प्रभावकारी रूपमा दिन सक्छ भनेर संकेत गर्दछ।\n\nचार प्रकारका अवस्थाहरू:\n\n१. बालादि (उमेरमा आधारित):\nबाल, कुमार, युवा, वृद्ध, मृत\n\n२. जाग्रदादि (जागरूकता):\nजाग्रत, स्वप्न, सुषुप्ति\n\n३. दीप्तादि (मर्यादा):\nदीप्त, स्वस्थ, मुदित, शान्त, दीन, विकल, खल, कोप, भीत\n\n४. लज्जितादि (भावनात्मक):\nलज्जित, गर्वित, क्षुधित, तृषित, मुदित, क्षोभित\n\nराम्रो अवस्थामा रहेको ग्रहले पूर्ण फल दिन्छ, जबकि कमजोर अवस्थामा रहेको ग्रहले आफ्ना कारकहरू व्यक्त गर्न संघर्ष गर्दछ।"),
    AVASTHA_OVERALL_STRENGTH("Overall Planetary Strength", "समग्र ग्रह बल"),
    AVASTHA_STRONGEST("Strongest", "सबैभन्दा बलियो"),
    AVASTHA_NEEDS_ATTENTION("Needs Attention", "ध्यान दिनुपर्ने"),
    
    // ============================================
    // NITYA YOGA SCREEN
    // ============================================
    NITYA_SCREEN_TITLE("Nitya Yoga", "नित्य योग"),
    NITYA_SUBTITLE("27 Daily Yogas", "२७ दैनिक योगहरू"),
    NITYA_NO_DATA_DESC("Create or select a birth chart to view Nitya Yoga analysis.", "नित्य योग विश्लेषण हेर्न जन्म कुण्डली सिर्जना गर्नुहोस् वा छान्नुहोस्।"),
    NITYA_ABOUT_TITLE("About Nitya Yoga", "नित्य योगको बारेमा"),
    NITYA_ABOUT_DESC("Nitya Yoga (Daily Yoga) is one of the five elements of the Panchanga (Hindu almanac).\n\nThere are 27 Nitya Yogas, each spanning 13Â°20' of the combined longitude of Sun and Moon.\n\nCalculation:\nNitya Yoga = (Sun longitude + Moon longitude) Ã· 13Â°20'\n\nThese yogas indicate the general auspiciousness of a moment and are used in Muhurta (electional astrology) to select favorable times for important activities.\n\nEach yoga is ruled by a planet and has specific characteristics affecting health, wealth, relationships, and spiritual matters.", "नित्य योग पञ्चाङ्गका पाँच अंगहरू मध्ये एक हो।\n\nत्यहाँ २७ नित्य योगहरू छन्, प्रत्येकले सूर्य र चन्द्रमाको संयुक्त देशान्तरको १३°२०' समेट्छ।\n\nगणना:\nनित्य योग = (सूर्य स्पष्ट + चन्द्र स्पष्ट) ÷ १३°२०'\n\nयी योगहरूले समयको सामान्य शुभतालाई संकेत गर्दछ र महत्त्वपूर्ण गतिविधिहरूको लागि शुभ समय छनौट गर्न मुहूर्तमा प्रयोग गरिन्छ।\n\nप्रत्येक योग एक ग्रहद्वारा शासित हुन्छ र यसले स्वास्थ्य, धन, सम्बन्ध र आध्यात्मिक मामिलाहरूलाई असर गर्ने विशिष्ट विशेषताहरू राख्छ।"),
    
    NITYA_PROGRESS("Progress", "प्रगति"),
    NITYA_POSITION("Position", "स्थिति"),
    NITYA_NATURE("Nature", "प्रकृति"),
    NITYA_CURRENT_PROGRESS("Progress in Current Yoga", "वर्तमान योगमा प्रगति"),
    NITYA_SUITABLE("Suitable Activities", "उपयुक्त गतिविधिहरू"),
    NITYA_AVOID("Activities to Avoid", "बच्नुपर्ने गतिविधिहरू"),
    NITYA_NEXT("Next:", "अर्को:"),
    NITYA_REMAINING("remaining", "बाँकी"),
    
    // Common
    UI_INTERPRETATION("Interpretation", "व्याख्या"),
    UI_RECOMMENDATIONS("Recommendations", "सिफारिसहरू"),
    UI_OVERVIEW("Overview", "अवलोकन"),
    UI_YOGAS("Yogas", "योगहरू"),
    UI_EFFECTS("Effects", "प्रभावहरू"),
    UI_TIMING("Timing", "समय"),
    UI_REMEDIES("Remedies", "उपायहरू"),
    UI_TRANSITS("Transits", "गोचरहरू"),
    UI_CURRENT("Current", "वर्तमान"),
    UI_UPCOMING("Upcoming", "आगामी"),
    UI_CALENDAR("Calendar", "पात्रो"),
    UI_KEY_INSIGHTS("Key Insights", "मुख्य अन्तरदृष्टिहरू"),
    UI_START("Start:", "सुरु:"),
    UI_END("End:", "अन्त्य:"),

    // ============================================
    // PANCH MAHAPURUSHA SCREEN
    // ============================================
    PANCHA_SCREEN_TITLE("Panch Mahapurusha Yoga", "पञ्च महापुरुष योग"),
    PANCHA_SUBTITLE("Five Great Person Yogas", "पाँच महापुरुष योगहरू"),
    PANCHA_ABOUT_TITLE("About Panch Mahapurusha Yoga", "पञ्च महापुरुष योगको बारेमा"),
    PANCHA_ABOUT_DESC("Panch Mahapurusha Yoga (Five Great Person Yogas) are special planetary combinations that indicate exceptional qualities and achievements.\n\nThe five yogas are:\n• Ruchaka (Mars) - Courage, leadership, military prowess\n• Bhadra (Mercury) - Intelligence, communication, commerce\n• Hamsa (Jupiter) - Wisdom, spirituality, fortune\n• Malavya (Venus) - Beauty, luxury, artistic talents\n• Sasha (Saturn) - Discipline, authority, longevity\n\nFormation requirements:\nThe planet must be in a Kendra house (1st, 4th, 7th, or 10th) AND in its own sign or exaltation sign.\n\nHaving one or more of these yogas in a chart indicates the native will possess the exceptional qualities of that planet and achieve success in related areas.", "पञ्च महापुरुष योगहरू विशेष ग्रह संयोजनहरू हुन् जसले असाधारण गुणहरू र उपलब्धिहरूलाई संकेत गर्दछ।\n\nपाँच योगहरू हुन्:\n• रुचक (मंगल) - साहस, नेतृत्व, सैन्य कौशल\n• भद्र (बुध) - बुद्धि, सञ्चार, वाणिज्य\n• हंस (बृहस्पति) - ज्ञान, आध्यात्मिकता, भाग्य\n• मालव्य (शुक्र) - सौन्दर्य, विलासिता, कलात्मक प्रतिभा\n• शश (शनि) - अनुशासन, अधिकार, दीर्घायु\n\nबन्ने शर्तहरू:\nग्रह केन्द्र भाव (१, ४, ७, वा १०) मा हुनुपर्छ र आफ्नै राशि वा उच्च राशिमा हुनुपर्छ।\n\nकुण्डलीमा यी मध्ये एक वा बढी योगहरू हुनुले जातकमा त्यस ग्रहका असाधारण गुणहरू हुनेछन् र सम्बन्धित क्षेत्रमा सफलता प्राप्त गर्नेछन् भन्ने संकेत गर्दछ।"),
    PANCHA_NO_YOGAS_DISPLAY("No Yogas to Display", "देखाउनको लागि कुनै योगहरू छैनन्"),
    PANCHA_NO_YOGAS_DESC("For a Mahapurusha Yoga to form, Mars, Mercury, Jupiter, Venus, or Saturn must be in Kendra (1,4,7,10) in its own or exaltation sign.", "महापुरुष योग बन्नको लागि, मंगल, बुध, बृहस्पति, शुक्र वा शनि केन्द्र (१, ४, ७, १०) मा आफ्नै वा उच्च राशिमा हुनुपर्छ।"),
    PANCHA_STATUS_FOUND("Yoga(s) Found!", "योग(हरू) फेला पर्यो!"),
    PANCHA_STATUS_FOUND_DESC("You have %1\$d Panch Mahapurusha Yoga(s) in your chart", "तपाईंको कुण्डलीमा %1\$d पञ्च महापुरुष योग(हरू) छन्"),
    PANCHA_STATUS_NONE_DESC("None of the five Mahapurusha Yogas are formed", "पाँच महापुरुष योगहरू मध्ये कुनै पनि बनेको छैन"),
    PANCHA_COMBINED("Combined Effects", "संयुक्त प्रभावहरू"),
    PANCHA_SYNERGIES("Synergies:", "समन्वयहरू:"),
    PANCHA_NO_CHART_DESC("Create or select a birth chart to analyze Panch Mahapurusha Yogas.", "पञ्च महापुरुष योगहरू विश्लेषण गर्न जन्म कुण्डली सिर्जना गर्नुहोस् वा छान्नुहोस्।"),

    // ============================================
    // NAKSHATRA SCREEN
    // ============================================
    NAKSHATRA_NATURE_FIXED("Fixed (Dhruva)", "स्थिर (ध्रुव)"),
    NAKSHATRA_NATURE_MOVEABLE("Moveable (Chara)", "चर"),
    NAKSHATRA_NATURE_SHARP("Sharp (Tikshna)", "तीक्ष्ण"),
    NAKSHATRA_NATURE_SOFT("Soft (Mridu)", "मृदु"),
    NAKSHATRA_NATURE_MIXED("Mixed (Mishra)", "मिश्र"),
    NAKSHATRA_NATURE_LIGHT("Light (Laghu)", "लघु"),
    NAKSHATRA_NATURE_FIERCE("Fierce (Ugra)", "उग्र"),

    NAKSHATRA_GANA_DEVA("Deva (Divine)", "देव"),
    NAKSHATRA_GANA_MANUSHYA("Manushya (Human)", "मनुष्य"),
    NAKSHATRA_GANA_RAKSHASA("Rakshasa (Demon)", "राक्षस"),

    NAKSHATRA_ELEMENT_FIRE("Fire (Agni)", "अग्नि"),
    NAKSHATRA_ELEMENT_EARTH("Earth (Prithvi)", "पृथ्वी"),
    NAKSHATRA_ELEMENT_AIR("Air (Vayu)", "वायु"),
    NAKSHATRA_ELEMENT_WATER("Water (Jala)", "जल"),
    NAKSHATRA_ELEMENT_ETHER("Ether (Akasha)", "आकाश"),

    NAKSHATRA_CASTE_BRAHMIN("Brahmin", "ब्राह्मण"),
    NAKSHATRA_CASTE_KSHATRIYA("Kshatriya", "क्षत्रिय"),
    NAKSHATRA_CASTE_VAISHYA("Vaishya", "वैश्य"),
    NAKSHATRA_CASTE_SHUDRA("Shudra", "शूद्र"),

    NAKSHATRA_GENDER_MALE("Male", "पुरुष"),
    NAKSHATRA_GENDER_FEMALE("Female", "स्त्री"),
    NAKSHATRA_GENDER_NEUTRAL("Neutral", "नपुंसक"),

    NAKSHATRA_DOSHA_VATA("Vata", "वात"),
    NAKSHATRA_DOSHA_PITTA("Pitta", "पित्त"),
    NAKSHATRA_DOSHA_KAPHA("Kapha", "कफ"),

    NAKSHATRA_RAJJU_PAADA("Paada", "पाद"),
    NAKSHATRA_RAJJU_KATI("Kati", "कटि"),
    NAKSHATRA_RAJJU_NABHI("Nabhi", "नाभि"),
    NAKSHATRA_RAJJU_KANTHA("Kantha", "कण्ठ"),
    NAKSHATRA_RAJJU_SHIRO("Shiro", "शिरो"),
    
    // Tarabala
    TARABALA_JANMA("Janma", "जन्म"),
    TARABALA_SAMPAT("Sampat", "सम्पत"),
    TARABALA_VIPAT("Vipat", "विफत"),
    TARABALA_KSHEMA("Kshema", "क्षेम"),
    TARABALA_PRATYARI("Pratyari", "प्रत्यरि"),
    TARABALA_SADHAKA("Sadhaka", "साधक"),
    TARABALA_VADHA("Vadha", "वध"),
    TARABALA_MITRA("Mitra", "मित्र"),
    TARABALA_PARAMA_MITRA("Parama Mitra", "परम मित्र"),
    
    // Tarabala Descriptions
    TARABALA_DESC_JANMA("Birth star - challenging for new beginnings", "जन्म नक्षत्र - नयाँ सुरुवातीका लागि चुनौतीपूर्ण"),
    TARABALA_DESC_SAMPAT("Wealth - favorable for financial matters", "सम्पत्ति - आर्थिक मामिलाका लागि शुभ"),
    TARABALA_DESC_VIPAT("Danger - avoid important activities", "खतरा - महत्त्वपूर्ण कार्यहरू नगर्नुहोस्"),
    TARABALA_DESC_KSHEMA("Well-being - favorable for health matters", "कल्याण - स्वास्थ्य मामिलाका लागि शुभ"),
    TARABALA_DESC_PRATYARI("Obstacle - creates hindrances", "बाधा - अवरोधहरू सिर्जना गर्दछ"),
    TARABALA_DESC_SADHAKA("Achievement - good for goals", "उपलब्धि - लक्ष्यका लागि राम्रो"),
    TARABALA_DESC_VADHA("Death - highly inauspicious", "वध - अत्यन्त अशुभ"),
    TARABALA_DESC_MITRA("Friend - favorable for relationships", "मित्र - सम्बन्धका लागि शुभ"),
    TARABALA_DESC_PARAMA_MITRA("Great Friend - highly auspicious", "परम मित्र - अत्यन्त शुभ"),

    // Misc Nakshatra
    NAKSHATRA_REMEDY_TITLE("Remedies for %s", "%s का लागि उपायहरू"),
    NAKSHATRA_REMEDY_DESC("Personalized remedies based on your birth nakshatra to enhance positive qualities and mitigate challenges.", "तपाइँको जन्म नक्षत्रमा आधारित व्यक्तिगत उपायहरू सकारात्मक गुणहरू बढाउन र चुनौतीहरू कम गर्न।"),
    NAKSHATRA_REMEDY_TIMING("Best performed during %s hora or on %s", "%s होरा वा %s मा उत्तम"),
    NAKSHATRA_BODY_PART_FEET("Feet", "पाउ"),
    NAKSHATRA_BODY_PART_WAIST("Waist", "कम्मर"),
    NAKSHATRA_BODY_PART_NAVEL("Navel", "नाभि"),
    NAKSHATRA_BODY_PART_NECK("Neck", "घाँटी"),
    NAKSHATRA_BODY_PART_HEAD("Head", "टाउको"),
    
    // Gemstones
    GEM_RUBY("Ruby", "माणिक"),
    GEM_PEARL("Pearl", "मोती"),
    GEM_RED_CORAL("Red Coral", "मुगा"),
    GEM_EMERALD("Emerald", "पन्ना"),
    GEM_YELLOW_SAPPHIRE("Yellow Sapphire", "पुष्पराज"),
    GEM_DIAMOND("Diamond", "हीरा"),
    GEM_BLUE_SAPPHIRE("Blue Sapphire", "नीलम"),
    GEM_HESSONITE("Hessonite", "गोमेद"),
    GEM_CATS_EYE("Cat's Eye", "लहसुनिया"),

    // Colors
    COLOR_ORANGE("Orange", "सुन्तला"),
    COLOR_GOLD("Gold", "सुनौलो"),
    COLOR_RED("Red", "रातो"),
    COLOR_WHITE("White", "सेतो"),
    COLOR_SILVER("Silver", "चाँदी"),
    COLOR_PEARL("Pearl White", "मोती"),
    COLOR_CORAL("Coral", "मुगा रंग"),
    COLOR_SCARLET("Scarlet", "सिन्दूरे"),
    COLOR_GREEN("Green", "हरियो"),
    COLOR_EMERALD("Emerald", "पन्ना"),
    COLOR_TURQUOISE("Turquoise", "फिरोजा"),
    COLOR_YELLOW("Yellow", "हेलों"),
    COLOR_PINK("Pink", "गुलाबी"),
    COLOR_PASTEL("Pastel", "पास्टेल"),
    COLOR_BLUE("Blue", "नीलो"),
    COLOR_BLACK("Black", "कालो"),
    COLOR_DARK("Dark colors", "गाढा रंगहरू"),
    COLOR_SMOKY("Smoky", "धुवाँ"),
    COLOR_GREY("Grey", "खरानी"),
    COLOR_MIXED("Mixed colors", "मिश्रित रंगहरू"),
    COLOR_BROWN("Brown", "खैरो"),
    COLOR_MULTI("Multi-colored", "बहुरंगी"),
    
    // Days
    DAY_SUNDAY("Sunday", "आइतबार"),
    DAY_MONDAY("Monday", "सोमबार"),
    DAY_TUESDAY("Tuesday", "मङ्गलबार"),
    DAY_WEDNESDAY("Wednesday", "बुधबार"),
    DAY_THURSDAY("Thursday", "बिहीबार"),
    DAY_FRIDAY("Friday", "शुक्रबार"),
    DAY_SATURDAY("Saturday", "शनिबार"),
    DAY_ANY("Any day", "कुनै पनि दिन"),
    
    // ============================================
    // GOCHARA VEDHA SCREEN
    // ============================================
    GOCHARA_SCREEN_TITLE("Gochara Vedha", "गोचर वेध"),
    GOCHARA_SUBTITLE("Transit Obstructions", "गोचर अवरोधहरू"),
    GOCHARA_ABOUT_TITLE("About Gochara Vedha", "गोचर वेधको बारेमा"),
    GOCHARA_ABOUT_DESC("Gochara refers to planetary transits, while Vedha means \"obstruction\" in Sanskrit.\n\nWhen a planet transits through a favorable house from your natal Moon, its positive effects can be blocked (Vedha) by another planet positioned in specific houses.\n\nKey concepts:\n• Each house has specific Vedha points\n• Benefic transits can be nullified by malefic planets\n• The severity of Vedha varies based on planetary combinations\n• Understanding Vedha helps predict when good transits may not deliver expected results\n\nThis analysis helps you identify periods when planetary energies are blocked and plan accordingly.", "गोचर भन्नाले ग्रहहरूको वर्तमान चाललाई जनाउँछ, जबकि वेधको अर्थ संस्कृतमा \"अवरोध\" हो।\n\nजब कुनै ग्रह तपाईंको जन्म चन्द्रमाबाट शुभ भावमा गोचर गर्छ, यसको सकारात्मक प्रभावहरू विशिष्ट भावहरूमा रहेका अन्य ग्रहहरूद्वारा अवरोधित (वेध) हुन सक्छन्।\n\nमुख्य अवधारणाहरू:\n• प्रत्येक भावका विशिष्ट वेध बिन्दुहरू हुन्छन्\n• शुभ गोचरहरू पाप ग्रहहरूद्वारा निष्फल हुन सक्छन्\n• वेधको गम्भीरता ग्रह संयोजनहरूमा आधारित हुन्छ\n• वेध बुझ्नाले राम्रो गोचरले कहिले अपेक्षित नतिजा नदिन सक्छ भनी अनुमान गर्न मद्दत गर्छ\n\nयो विश्लेषणले तपाईंलाई ग्रह उर्जाहरू कहिले अवरोधित हुन्छन् भनी पहिचान गर्न र सोही अनुसार योजना बनाउन मद्दत गर्छ।"),
    GOCHARA_SCORE("Overall Transit Score", "समग्र गोचर अङ्क"),
    GOCHARA_FAVORABLE("Favorable", "शुभ"),
    GOCHARA_OBSTRUCTED("Obstructed", "अवरोधित"),
    GOCHARA_MOON_SIGN("Natal Moon Sign", "जन्म राशी"),
    GOCHARA_ACTIVE_VEDHAS("Active Vedhas", "सक्रिय वेध"),
    GOCHARA_TRANSITS_TRACKED("Transits Tracked", "ट्र्याक गरिएका गोचरहरू"),
    GOCHARA_CURRENT_TRANSITS("Current Planetary Transits", "वर्तमान ग्रह गोचरहरू"),
    GOCHARA_NATURALLY_FAVORABLE("Naturally Favorable", "स्वाभाविक रूपमा शुभ"),
    GOCHARA_NATURALLY_UNFAVORABLE("Naturally Unfavorable", "स्वाभाविक रूपमा अशुभ"),
    GOCHARA_VEDHA_LABEL("VEDHA", "वेध"),
    GOCHARA_TRANSIT_EFFECTS("Transit Effects", "गोचर प्रभावहरू"),
    GOCHARA_VEDHA_OBSTRUCTION("Vedha Obstruction", "वेध अवरोध"),
    GOCHARA_FROM("From:", "बाट:"),
    GOCHARA_SEVERITY("Severity:", "गम्भीरता:"),
    GOCHARA_NO_CHART_DESC("Create or select a birth chart to analyze Gochara Vedha effects.", "गोचर वेध प्रभावहरू विश्लेषण गर्न जन्म कुण्डली सिर्जना गर्नुहोस् वा छान्नुहोस्।"),
    GOCHARA_VEDHAS("Vedhas", "वेधहरू"),
    GOCHARA_FORECAST("Forecast", "पूर्वानुमान"),

    // ============================================
    // DASHA SANDHI SCREEN
    // ============================================
    SANDHI_SCREEN_TITLE("Dasha Sandhi", "दशा सन्धि"),
    SANDHI_ABOUT_TITLE("About Dasha Sandhi", "दशा सन्धिको बारेमा"),
    SANDHI_ABOUT_DESC("Dasha Sandhi refers to the junction or transition point between two planetary periods (Dashas) in Vedic astrology.\n\nThese transition periods are considered sensitive times when:\n• The energy shifts from one planetary influence to another\n• Both planets' effects may be felt simultaneously\n• Major life changes often occur\n• Careful planning is advised\n\nThe intensity of a Sandhi depends on:\n• The nature of the transitioning planets\n• Their relationship in your chart\n• Current transits and aspects\n\nUnderstanding your Sandhi periods helps you prepare for and navigate these significant life transitions.", "दशा सन्धि भन्नाले वैदिक ज्योतिषमा दुई ग्रह दशा (समय अवधि) बीचको मिलन वा संक्रमण बिन्दुलाई जनाउँछ।\n\nयी संक्रमण अवधिहरूलाई संवेदनशील समय मानिन्छ जब:\n• ऊर्जा एक ग्रहको प्रभावबाट अर्कोमा सर्छ\n• दुबै ग्रहका प्रभावहरू एक साथ महसुस हुन सक्छ\n• अक्सर ठूला जीवन परिवर्तनहरू हुन्छन्\n• सावधानीपूर्वक योजना बनाउन सल्लाह दिइन्छ\n\nसन्धिको तीव्रता यसमा निर्भर गर्दछ:\n• संक्रमण भइरहेका ग्रहहरूको प्रकृति\n• तपाईंको कुण्डलीमा तिनीहरूको सम्बन्ध\n• वर्तमान गोचर र दृष्टिहरू\n\nतपाईंको सन्धि अवधिहरू बुझ्नाले तपाईंलाई यी महत्त्वपूर्ण जीवन संक्रमणहरूको तयारी गर्न र नेभिगेट गर्न मद्दत गर्छ।"),
    SANDHI_VOLATILITY_DESC("Based on current and upcoming period transitions", "वर्तमान र आगामी दशा परिवर्तनहरूमा आधारित"),
    SANDHI_ACTIVE("Active", "सक्रिय"),
    SANDHI_GUIDANCE("General Guidance", "सामान्य मार्गदर्शन"),
    SANDHI_CURRENT_ACTIVE("Currently in Sandhi Period", "वर्तमानमा सन्धि अवधिमा"),
    SANDHI_INTENSITY("Intensity:", "तीव्रता:"),
    SANDHI_ENDS("Ends:", "अन्त्य:"),
    SANDHI_NO_ACTIVE("No Active Sandhi", "कुनै सक्रिय सन्धि छैन"),
    SANDHI_NO_ACTIVE_DESC("You are not currently in a Dasha Sandhi period. Check the Upcoming tab for future transitions.", "तपाईं हाल दशा सन्धि अवधिमा हुनुहुन्न। भविष्यका परिवर्तनहरूको लागि आगामी ट्याब जाँच गर्नुहोस्।"),
    SANDHI_IMPACTS("Life Area Impacts", "जीवन क्षेत्र प्रभावहरू"),
    SANDHI_REMEDIES("Recommended Remedies", "सिफारिस गरिएका उपायहरू"),
    SANDHI_NO_CHART_DESC("Create or select a birth chart to analyze Dasha Sandhi periods.", "दशा सन्धि अवधिहरू विश्लेषण गर्न जन्म कुण्डली सिर्जना गर्नुहोस् वा छान्नुहोस्।"),
    SANDHI_NO_UPCOMING("No upcoming Sandhi periods in the analysis window.", "विश्लेषण अवधिमा कुनै आगामी सन्धि अवधिहरू छैनन्।"),
    SANDHI_NO_CALENDAR("No calendar entries available.", "कुनै पात्रो प्रविष्टिहरू उपलब्ध छैनन्।"),

    // Graha Yuddha
    GRAHA_SCREEN_TITLE("Graha Yuddha", "ग्रह युद्ध"),
    GRAHA_SUBTITLE("Planetary War Analysis", "ग्रह युद्ध विश्लेषण"),
    GRAHA_ACTIVE_WARS("Active Wars", "सक्रिय युद्धहरू"),
    GRAHA_DASHA_EFFECTS("Dasha Effects", "दशा प्रभावहरू"),
    GRAHA_ANALYZING("Analyzing Planetary Wars...", "ग्रह युद्ध विश्लेषण गर्दै..."),
    GRAHA_WAR_STATUS("War Status", "युद्ध स्थिति"),
    GRAHA_ANALYSIS_SUMMARY("Analysis Summary", "विश्लेषण सारांश"),
    GRAHA_OVERALL_IMPACT("Overall War Impact", "समग्र युद्ध प्रभाव"),
    GRAHA_VICTORY("Victorious", "विजयी"),
    GRAHA_DEFEATED("Defeated", "पराजित"),
    GRAHA_AFFECTED_AREAS("Affected Life Areas", "प्रभावित जीवन क्षेत्रहरू"),
    GRAHA_NO_WARS("No Active Planetary Wars", "कुनै सक्रिय ग्रह युद्ध छैन"),
    GRAHA_NO_WARS_DESC("All planets are operating peacefully", "सबै ग्रहहरू शान्तिपूर्ण रूपमा काम गरिरहेका छन्"),
    GRAHA_VS("vs", "विरुद्ध"),
    GRAHA_SEPARATION("Separation", "अन्तर"),
    GRAHA_ADVANTAGE("Advantage", "फाइदा"),
    GRAHA_NO_DASHA_EFFECTS("No War-Related Dasha Effects", "कुनै युद्ध-सम्बन्धित दशा प्रभावहरू छैनन्"),
    GRAHA_NO_REMEDIES("No Specific Remedies Needed", "कुनै विशेष उपाय आवश्यक छैन"),
    GRAHA_NO_DASHA_DESC("Planets in War not currently activated in Dasha/Antardasha.", "युद्धमा रहेका ग्रहहरू हाल दशा/अन्तर्दशामा सक्रिय छैनन्।"),
    GRAHA_NO_REMEDIES_DESC("Since there are no active planetary wars, remedial measures are not required.", "कुनै सक्रिय ग्रह युद्ध नभएकोले, उपायहरू आवश्यक छैनन्।"),
    GRAHA_ABOUT_TITLE("About Graha Yuddha", "ग्रह युद्धको बारेमा"),
    GRAHA_ABOUT_DESC("Graha Yuddha (Planetary War) occurs when two planets (excluding Sun and Moon) are within 1 degree of each other.\n\nThe planet with the lower longitude is usually considered the winner, while the one with higher longitude is the loser.\n\nImpacts:\n• The losing planet's significations may suffer\n• If the losing planet rules important houses, those areas may be affected\n• The war is most intense during close conjunctions", "ग्रह युद्ध तब हुन्छ जब दुई ग्रहहरू (सूर्य र चन्द्रमा बाहेक) एक अर्काको १ डिग्री भित्र हुन्छन्।\n\nकम देशान्तर भएको ग्रहलाई सामान्यतया विजेता मानिन्छ, जबकि उच्च देशान्तर भएको ग्रहलाई पराजित मानिन्छ।\n\nप्रभावहरू:\n• पराजित ग्रहका कारकत्वहरूमा असर पर्न सक्छ\n• यदि पराजित ग्रहले महत्त्वपूर्ण भावहरूको स्वामी हो भने, ती क्षेत्रहरू प्रभावित हुन सक्छन्\n• नजिकको युतिमा युद्ध सबैभन्दा तीव्र हुन्छ"),
    GRAHA_DEFEATS_MSG("%1\$2 defeats %2\$2", "%1\$2 ले %2\$2 लाई जित्छ"),
    GRAHA_IN_HOUSE_MSG("in %1\$2 (House %2\$2)", "%1\$2 (भाव %2\$2) मा"),

    // Kemadruma Yoga
    KEMA_SCREEN_TITLE("Kemadruma Yoga", "केमद्रुम योग"),
    KEMA_SUBTITLE("Moon Isolation Analysis", "चन्द्रमा एक्लोपन विश्लेषण"),
    KEMA_MOON("Moon", "चन्द्रमा"),
    KEMA_CANCELLATIONS("Cancellations", "भंग"),
    KEMA_IMPACTS("Impacts", "प्रभावहरू"),
    KEMA_FORMED("Kemadruma Yoga is formed in this chart", "यो कुण्डलीमा केमद्रुम योग बनेको छ"),
    KEMA_NOT_FORMED("Kemadruma Yoga is not formed", "केमद्रुम योग बनेको छैन"),
    KEMA_FORMATION_DETAILS("Formation Details", "योग निर्माण विवरण"),
    KEMA_MOON_POSITION("Moon Position", "चन्द्रमाको स्थिति"),
    KEMA_SURROUNDING_HOUSES("Surrounding Houses Analysis", "वरपरका भावहरूको विश्लेषण"),
    KEMA_BEFORE_MOON("Before Moon", "चन्द्रमा अघि"),
    KEMA_AFTER_MOON("After Moon", "चन्द्रमा पछि"),
    KEMA_MOON_HOUSE("Moon's House", "चन्द्रमाको भाव"),
    KEMA_CONDITION_MET("No planets in houses adjacent to Moon - Kemadruma condition met", "चन्द्रमाको नजिकका भावहरूमा कुनै ग्रह छैन - केमद्रुम शर्त पूरा भयो"),
    KEMA_NO_CANCELLATIONS("No Cancellations Found", "कुनै भंग फेला परेन"),
    KEMA_NA("Not Applicable", "लागू हुँदैन"),
    KEMA_NO_CANCELLATIONS_DESC("No Kemadruma Bhanga (cancellation) factors were found in your chart.", "तपाईंको कुण्डलीमा कुनै केमद्रुम भंग कारकहरू फेला परेनन्।"),
    KEMA_NO_IMPACTS("No Negative Impacts", "कुनै नकारात्मक प्रभाव छैन"),
    KEMA_NO_IMPACTS_DESC("Moon is well-supported, ensuring emotional stability and mental peace.", "चन्द्रमा राम्रोसँग समर्थित छ, जसले भावनात्मक स्थिरता र मानसिक शान्ति सुनिश्चित गर्दछ।"),
    KEMA_NO_REMEDIES("No Remedies Needed", "कुनै उपाय आवश्यक छैन"),
    KEMA_NO_REMEDIES_DESC("Since Kemadruma Yoga is not formed or is fully cancelled, no remedies are required.", "केमद्रुम योग नबनेको वा पूर्ण रूपमा भंग भएकोले, कुनै उपाय आवश्यक छैन।"),
    KEMA_ABOUT_TITLE("About Kemadruma Yoga", "केमद्रुम योगको बारेमा"),
    KEMA_ABOUT_DESC("Kemadruma Yoga forms when the Moon has no planets (except Sun, Rahu, Ketu) in the 2nd and 12th houses from it, and no planets are in Kendra from the Moon or Lagna.\n\nIt is considered an inauspicious yoga indicating loneliness, mental unrest, and financial instability. However, it is often cancelled (Bhanga) by the presence of planets in Kendra houses or if the Moon interacts with other planets.", "केमद्रुम योग तब बन्छ जब चन्द्रमाको दोस्रो र बाह्रौं भावमा कुनै ग्रहहरू (सूर्य, राहु, केतु बाहेक) हुँदैनन्, र चन्द्रमा वा लग्नबाट केन्द्रमा कुनै ग्रहहरू हुँदैनन्।\n\nयसलाई अशुभ योग मानिन्छ जसले एक्लोपन, मानसिक अशान्ति र आर्थिक अस्थिरतालाई संकेत गर्दछ। यद्यपि, केन्द्र भावहरूमा ग्रहहरूको उपस्थिति वा चन्द्रमाले अन्य ग्रहहरूसँग अन्तरक्रिया गरेमा यो अक्सर भंग हुन्छ।"),
    KEMA_CANCELLATIONS_FOUND("%1\$2 Cancellation(s) Found", "%1\$2 भंग(हरू) फेला पर्यो"),
    KEMA_FORMATION_MAIN("Moon lacks planetary support in adjacent houses.", "चन्द्रमालाई नजिकका भावहरूमा ग्रहहरूको समर्थन छैन।"),
    KEMA_FORMATION_2ND_EMPTY("2nd house from Moon is empty.", "चन्द्रमाबाट दोस्रो भाव खाली छ।"),
    KEMA_FORMATION_12TH_EMPTY("12th house from Moon is empty.", "चन्द्रमाबाट बाह्रौं भाव खाली छ।"),
    KEMA_FORMATION_UNASPECTED("Moon has no planetary conjunction.", "चन्द्रमाको कुनै ग्रहसँग युति छैन।"),
    KEMA_REASON_2ND("No planets in 2nd house from Moon", "चन्द्रमाबाट दोस्रो भावमा कुनै ग्रह छैन"),
    KEMA_REASON_12TH("No planets in 12th house from Moon", "चन्द्रमाबाट बाह्रौं भावमा कुनै ग्रह छैन"),
    KEMA_REASON_CONJUNCT("Moon is not conjunct with any planet", "चन्द्रमा कुनै ग्रहसँग युतिमा छैन"),
    KEMA_FORMATION_STRENGTH("Formation strength: %1\$2%%", "योग बल: %1\$2%%"),
    KEMA_NAKSHATRA("Nakshatra", "नक्षत्र"),
    KEMA_PAKSHA("Paksha", "पक्ष"),
    KEMA_BRIGHTNESS("Brightness", "चमक"),
    KEMA_MOON_STRENGTH("Moon Strength:", "चन्द्रमा बल:"),

    // Panchanga Redesigned
    PANCHANGA_LIMBS_TITLE("Five Limbs of Hindu Calendar", "हिन्दु पात्रोका पाँच अंगहरू"),
    PANCHANGA_FIVE_LIMBS("Five Limbs (Pancha Anga)", "पाँच अंगहरू (पञ्चाङ्ग)"),
    PANCHANGA_TIMING_AUSPICIOUSNESS("Timing & Auspiciousness", "समय र शुभता"),
    PANCHANGA_SUN_MOON("Sun & Moon", "सूर्य र चन्द्रमा"),
    PANCHANGA_RISE("Rise", "उदय"),
    PANCHANGA_SET("Set", "अस्त"),
    PANCHANGA_LUNAR_DAY_FMT("Lunar day (%1\$2)", "चन्द्र दिन (%1\$2)"),
    PANCHANGA_VARA_DESC("Weekday, ruled by specific planet", "बार, विशिष्ट ग्रहद्वारा शासित"),
    PANCHANGA_NAKSHATRA_DESC_FMT("Lunar mansion (Pada %1\$2)", "नक्षत्र (पद %1\$2)"),
    PANCHANGA_YOGA_DESC("Auspicious combination", "शुभ संयोग"),
    PANCHANGA_KARANA_DESC("Half of a tithi", "तिथिको आधा भाग"),
    PANCHANGA_AVOID_ACTIVITIES("Avoid important activities", "महत्त्वपूर्ण कार्यहरू नगर्नुहोस्"),
    PANCHANGA_AUSPICIOUS_TIMING("Auspicious timing", "शुभ समय"),
    PANCHANGA_CALCULATE_DAY("Calculate based on day", "दिनको आधारमा गणना गर्नुहोस्"),
    PANCHANGA_MIDDAY_HOUR("Midday hour", "मध्यान्ह समय"),
    PANCHANGA_SUN("Sun", "सूर्य"),
    PANCHANGA_MOON("Moon", "चन्द्रमा"),
    PANCHANGA_TITHI_LABEL("Tithi", "तिथि"),
    PANCHANGA_VARA_LABEL("Vara", "वार"),
    PANCHANGA_NAKSHATRA_LABEL("Nakshatra", "नक्षत्र"),
    PANCHANGA_YOGA_LABEL("Yoga", "योग"),
    PANCHANGA_KARANA_LABEL("Karana", "करण"),
    PANCHANGA_RAHU("Rahu Kaal", "राहु काल"),
    PANCHANGA_YAMAGANDAM("Yamagandam", "यमगण्डम"),
    PANCHANGA_GULIKA("Gulika Kaal", "गुलिक काल"),
    PANCHANGA_ABHIJIT("Abhijit Muhurta", "अभिजित मुहूर्त"),
    PANCHANGA_INTRO("Panchanga comprises five essential elements (Pancha Anga) that determine the quality and auspiciousness of any moment in the Hindu calendar system.", "पञ्चाङ्गमा पाँच आवश्यक तत्वहरू (पञ्च अंग) हुन्छन् जसले हिन्दु पात्रो प्रणालीमा कुनै पनि क्षणको गुणस्तर र शुभता निर्धारण गर्दछ।"),
    PANCHANGA_TITHI_DESC_LONG("The lunar day, calculated based on the angular distance between the Sun and Moon. Each tithi represents 12 degrees of lunar motion. There are 30 tithis in a lunar month, divided into Shukla (waxing) and Krishna (waning) Paksha.", "चन्द्र दिन, जुन सूर्य र चन्द्रमा बीचको कोणीय दूरीको आधारमा गणना गरिन्छ। प्रत्येक तिथिले १२ डिग्रीको चन्द्र गति प्रतिनिधित्व गर्दछ। एक चन्द्र मासमा ३० तिथिहरू हुन्छन्, जुन शुक्ल (बढ्दो) र कृष्ण (घट्दो) पक्षमा विभाजित हुन्छन्।"),
    PANCHANGA_TITHI_SIG("Determines the nature of activities suitable for the day. Some tithis like Purnima (full moon) and Amavasya (new moon) have special significance.", "दिनको लागि उपयुक्त गतिविधिहरूको प्रकृति निर्धारण गर्दछ। पूर्णिमा र औंशी जस्ता केही तिथिहरूको विशेष महत्त्व हुन्छ।"),
    PANCHANGA_VARA_DESC_LONG("The weekday, each ruled by a specific planet. The planetary ruler influences the nature and appropriate activities for that day.", "वार, प्रत्येक एक विशिष्ट ग्रहद्वारा शासित हुन्छ। ग्रह शासकले त्यस दिनको प्रकृति र उपयुक्त गतिविधिहरूलाई प्रभाव पार्छ।"),
    PANCHANGA_VARA_SIG("Each vara has specific activities that are considered favorable. For example, Sunday is good for authority matters, Monday for emotional work.", "प्रत्येक वारमा विशिष्ट गतिविधिहरू हुन्छन् जुन अनुकूल मानिन्छन्। उदाहरणका लागि, आइतबार अधिकार मामिलाहरूको लागि राम्रो हुन्छ, सोमबार भावनात्मक कार्यको लागि।"),
    PANCHANGA_NAKSHATRA_DESC_LONG("The lunar mansion where the Moon resides. There are 27 nakshatras, each spanning 13Â°20' of the zodiac. Each nakshatra has a ruling deity and planet.", "चन्द्रमा बस्ने नक्षत्र। त्यहाँ २७ नक्षत्रहरू छन्, प्रत्येकले राशिको १३°२०' ओगटेका छन्। प्रत्येक नक्षत्रको एक शासक देवता र ग्रह हुन्छ।"),
    PANCHANGA_NAKSHATRA_SIG("Birth nakshatra determines personality traits and compatibility. Moon's nakshatra is crucial for muhurta selection.", "जन्म नक्षत्रले व्यक्तित्व गुणहरू र अनुकूलता निर्धारण गर्दछ। मुहूर्त चयनको लागि चन्द्रमाको नक्षत्र महत्त्वपूर्ण छ।"),
    PANCHANGA_YOGA_DESC_LONG("Calculated from the combined longitudes of Sun and Moon. There are 27 yogas, each with specific qualities ranging from highly auspicious to inauspicious.", "सूर्य र चन्द्रमाको संयुक्त देशान्तरबाट गणना गरिएको। त्यहाँ २७ योगहरू छन्, प्रत्येकसँग अत्यधिक शुभ देखि अशुभ सम्मका विशिष्ट गुणहरू छन्।"),
    PANCHANGA_YOGA_SIG("Yogas indicate the general nature of results from activities undertaken. Siddha, Amrita, and Shubha yogas are highly favorable.", "योगहरूले गरिएका गतिविधिहरूको परिणामको सामान्य प्रकृति संकेत गर्दछ। सिद्ध, अमृत र शुभ योगहरू अत्यधिक अनुकूल हुन्छन्।"),
    PANCHANGA_KARANA_DESC_LONG("Half of a tithi, there are 11 karanas total. Seven are movable and four are fixed. Each karana has its own nature and suitable activities.", "तिथिको आधा भाग, कुल ११ करणहरू छन्। सात चर र चार स्थिर छन्। प्रत्येक करणको आफ्नै प्रकृति र उपयुक्त गतिविधिहरू छन्।"),
    PANCHANGA_KARANA_SIG("Karanas fine-tune the effects of tithis. Movable karanas recur throughout the month while fixed ones appear only once.", "करणहरूले तिथिका प्रभावहरूलाई सुष्म बनाउँछन्। चर करणहरू महिनाभर दोहोरिन्छन् भने स्थिर करणहरू एक पटक मातॠर आउँछन्।"),
    PANCHANGA_ABOUT_TITLE("About Panchanga", "पञ्चाङ्गको बारेमा"),
    PANCHANGA_ABOUT_DESC_1("Panchanga (Sanskrit: पञ्चाङ्ग) means 'five limbs' and refers to the traditional Hindu almanac that tracks five elements of time: Tithi, Vara, Nakshatra, Yoga, and Karana.", "पञ्चाङ्ग (संस्कृत: पञ्चाङ्ग) को अर्थ 'पाँच अंग' हो र यसले समयका पाँच तत्वहरू: तिथि, वार, नक्षत्र, योग र करण ट्र्याक गर्ने परम्परागत हिन्दु पात्रोलाई जनाउँछ।"),
    PANCHANGA_ABOUT_DESC_2("These five elements together determine the auspiciousness of any moment and are crucial for selecting muhurtas (auspicious timings) for important activities.", "यी पाँच तत्वहरूले मिलेर कुनै पनि क्षणको शुभता निर्धारण गर्दछ र महत्त्वपूर्ण गतिविधिहरूको लागि मुहूर्त (शुभ समय) चयन गर्न महत्त्वपूर्ण छन्।"),
    PANCHANGA_SIGNIFICANCE("Significance", "महत्त्व"),

    // ============================================
    // SUDARSHANA CHAKRA ANALYSIS
    // ============================================
    SUDARSHANA_HOUSE_1_DESC("Year of self-focus, health matters, and personal initiatives", "आत्म-केन्द्रित, स्वास्थ्य मामिला, र व्यक्तिगत पहलहरूको वर्ष"),
    SUDARSHANA_HOUSE_2_DESC("Year of financial matters, family affairs, and resource building", "आर्थिक मामिला, पारिवारिक मामिला, र स्रोत निर्माणको वर्ष"),
    SUDARSHANA_HOUSE_3_DESC("Year of courage, communication, and sibling matters", "साहस, संचार, र भाईबहिनी मामिलाहरूको वर्ष"),
    SUDARSHANA_HOUSE_4_DESC("Year of home, mother, property, and emotional well-being", "घर, आमा, सम्पत्ति, र भावनात्मक कल्याणको वर्ष"),
    SUDARSHANA_HOUSE_5_DESC("Year of children, romance, creativity, and spiritual practices", "सन्तान, रोमान्स, रचनात्मकता, र आध्यात्मिक अभ्यासहरूको वर्ष"),
    SUDARSHANA_HOUSE_6_DESC("Year of health focus, overcoming obstacles, and service", "स्वास्थ्यमा ध्यान, बाधाहरू पार गर्ने, र सेवाको वर्ष"),
    SUDARSHANA_HOUSE_7_DESC("Year of relationships, partnerships, and public dealings", "सम्बन्ध, साझेदारी, र सार्वजनिक व्यवहारहरूको वर्ष"),
    SUDARSHANA_HOUSE_8_DESC("Year of transformation, research, and handling sudden events", "रूपान्तरण, अनुसन्धान, र अचानक घटनाहरू संहाल्ने वर्ष"),
    SUDARSHANA_HOUSE_9_DESC("Year of fortune, spiritual growth, and father-related matters", "भाग्य, आध्यात्मिक वृद्धि, र पितासम्बन्धी मामिलाहरूको वर्ष"),
    SUDARSHANA_HOUSE_10_DESC("Year of career growth, recognition, and professional achievements", "करियर वृद्धि, मान्यता, र व्यावसायिक उपलब्धिहरूको वर्ष"),
    SUDARSHANA_HOUSE_11_DESC("Year of gains, fulfillment of desires, and social expansion", "लाभ, इच्छाहरूको पूर्ति, र सामाजिक विस्तारको वर्ष"),
    SUDARSHANA_HOUSE_12_DESC("Year of spiritual growth, foreign connections, and completion of cycles", "आध्यात्मिक वृद्धि, विदेशी सम्बन्ध, र चक्रहरूको समापनको वर्ष"),

    SUDARSHANA_EFFECT_ACTIVATED("%s activated - %s element emphasized", "%s सक्रिय - %s तत्वलाई जोड"),
    SUDARSHANA_EFFECT_MOVABLE("Movable sign - new initiatives and changes favored", "चर राशि - नयाँ पहल र परिवर्तनहरू अनुकूल"),
    SUDARSHANA_EFFECT_FIXED("Fixed sign - stability and consolidation emphasized", "स्थिर राशि - स्थिरता र समेकनलाई जोड"),
    SUDARSHANA_EFFECT_DUAL("Dual sign - adaptability and flexibility needed", "द्विस्वभाव राशि - अनुकूलनशीलता र लचिलोपन आवश्यक"),
    SUDARSHANA_EFFECT_LORD_POS("Lord %s in %d from active sign", "स्वामी %s सक्रिय राशिबाट %d मा"),
    SUDARSHANA_EFFECT_EXALTED("Sign lord is exalted - strong positive results", "राशि स्वामी उच्च - बलियो सकारात्मक परिणाम"),
    SUDARSHANA_EFFECT_DEBILITATED("Sign lord is debilitated - extra efforts needed", "राशि स्वामी नीच - अतिरिक्त प्रयास आवश्यक"),
    SUDARSHANA_EFFECT_NORMAL("Sign lord is in normal dignity", "राशि स्वामी सामान्य अवस्थामा"),

    SUDARSHANA_INFLUENCE_PRESENT("%s present - direct influence on year's themes", "%s उपस्थित - वर्षका विषयहरूमा प्रत्यक्ष प्रभाव"),
    SUDARSHANA_INFLUENCE_ASPECT("%s aspects from %s", "%s ले %s बाट दृष्टि दिन्छ"),

    SUDARSHANA_FACTOR_DIGNITY("Sign lord %s dignity: %s", "राशि स्वामी %s अवस्था: %s"),
    SUDARSHANA_FACTOR_PLANETS("%d planets in active sign", "%d ग्रहहरू सक्रिय राशिमा"),
    SUDARSHANA_FACTOR_ASPECTS("%d planets aspecting", "%d ग्रहहरूको दृष्टि"),

    SUDARSHANA_ASSESSMENT_HIGHLY_FAVORABLE("Highly favorable year with aligned energies from all three references", "तीनै सन्दर्भहरूबाट समान ऊर्जासहित अत्यन्त अनुकूल वर्ष"),
    SUDARSHANA_ASSESSMENT_POSITIVE("Generally positive year with good support from chart references", "कुण्डली सन्दर्भहरूबाट राम्रो समर्थनसहित सामान्यतया सकारात्मक वर्ष"),
    SUDARSHANA_ASSESSMENT_MIXED("Mixed year requiring balanced approach to different life areas", "विभिन्न जीवन क्षेत्रहरूमा सन्तुलित दृष्टिकोण आवश्यक पर्ने मिश्रित वर्ष"),
    SUDARSHANA_ASSESSMENT_CHALLENGING("Challenging year requiring extra effort and remedial measures", "अतिरिक्त प्रयास र उपचारात्मक उपायहरू आवश्यक पर्ने चुनौतीपूर्ण वर्ष"),

    SUDARSHANA_REC_FAVORABLE("Favorable year - take initiative in %s", "अनुकूल वर्ष - %s मा पहल गर्नुहोस्"),
    SUDARSHANA_REC_EXPAND("Expand activities related to %s", "%s सम्बन्धी गतिविधिहरू विस्तार गर्नुहोस्"),
    SUDARSHANA_REC_BALANCED("Balanced approach needed - focus on %s", "सन्तुलित दृष्टिकोण आवश्यक - %s मा ध्यान दिनुहोस्"),
    SUDARSHANA_REC_PATIENCE("Be patient with %s", "%s सँग धैर्य राख्नुहोस्"),
    SUDARSHANA_REC_CHALLENGING("Challenging year - strengthen remedies for weak areas", "चुनौतीपूर्ण वर्ष - कमजोर क्षेत्रहरूको लागि उपायहरू बलियो बनाउनुहोस्"),
    SUDARSHANA_REC_SPIRITUAL("Focus on spiritual practices and patience", "आध्यात्मिक अभ्यास र धैर्यमा ध्यान दिनुहोस्"),
    SUDARSHANA_REC_REVIEW("Review all three references monthly for detailed guidance", "विस्तृत मार्गदर्शनको लागि मासिक रूपमा तीनै सन्दर्भहरू समीक्षा गर्नुहोस्"),

    SUDARSHANA_THEME_MULTIPLE("Multiple references point to %s house themes", "धेरै सन्दर्भहरूले %s भाव विषयहरूलाई औंल्याउँछन्"),
    SUDARSHANA_THEME_ACTIVATED("%s activated from multiple references", "%s धेरै सन्दर्भहरूबाट सक्रिय"),
    SUDARSHANA_THEME_VARIATION("Significant variation in strength across references - uneven year expected", "सन्दर्भहरूमा शक्तिको महत्त्वपूर्ण भिन्नता - असमान वर्षको अपेक्षा"),

    SUDARSHANA_FOCUS_GENERAL("General matters", "सामान्य मामिलाहरू"),
    SUDARSHANA_FOCUS_FROM("%s from %s reference", "%s सन्दर्भबाट %s"),

    SUDARSHANA_CONTRIB_LAGNA("Physical matters, personality, and health - %s", "भौतिक मामिला, व्यक्तित्व, र स्वास्थ्य - %s"),
    SUDARSHANA_CONTRIB_CHANDRA("Mind, emotions, and public life - %s", "मन, भावना, र सार्वजनिक जीवन - %s"),
    SUDARSHANA_CONTRIB_SURYA("Soul, authority, and career - %s", "आत्मा, अधिकार, र करियर - %s"),

    SUDARSHANA_YEAR_SUMMARY("Age %d: %s house activated - %s", "उमेर %d: %s भाव सक्रिय - %s"),
    SUDARSHANA_TREND_FAVORABLE("Favorable period in annual cycle", "वार्षिक चक्रमा अनुकूल अवधि"),
    SUDARSHANA_TREND_CHALLENGING("Challenging period requiring extra attention", "अतिरिक्त ध्यान आवश्यक पर्ने चुनौतीपूर्ण अवधि"),
    SUDARSHANA_TREND_NEUTRAL("Neutral period with mixed opportunities", "मिश्रित अवसरहरूसहित तटस्थ अवधि"),

    SUDARSHANA_DIG_EXALTED("Exalted", "उच्च"),
    SUDARSHANA_DIG_OWN("Own Sign", "स्वराशि"),
    SUDARSHANA_DIG_DEBILITATED("Debilitated", "नीच"),
    SUDARSHANA_DIG_NORMAL("Normal", "सामान्य"),

    // Sudarshana House Themes (Top 2 for each house)
    SUDARSHANA_THEME_H1_1("Self", "स्वयं"),
    SUDARSHANA_THEME_H1_2("Body", "शरीर"),
    SUDARSHANA_THEME_H2_1("Wealth", "धन"),
    SUDARSHANA_THEME_H2_2("Family", "परिवार"),
    SUDARSHANA_THEME_H3_1("Siblings", "भाईबहिनी"),
    SUDARSHANA_THEME_H3_2("Courage", "साहस"),
    SUDARSHANA_THEME_H4_1("Mother", "आमा"),
    SUDARSHANA_THEME_H4_2("Home", "घर"),
    SUDARSHANA_THEME_H5_1("Children", "सन्तान"),
    SUDARSHANA_THEME_H5_2("Romance", "प्रेम"),
    SUDARSHANA_THEME_H6_1("Health", "स्वास्थ्य"),
    SUDARSHANA_THEME_H6_2("Enemies", "शत्रु"),
    SUDARSHANA_THEME_H7_1("Marriage", "विवाह"),
    SUDARSHANA_THEME_H7_2("Partnership", "साझेदारी"),
    SUDARSHANA_THEME_H8_1("Transformation", "रूपान्तरण"),
    SUDARSHANA_THEME_H8_2("Longevity", "आयु"),
    SUDARSHANA_THEME_H9_1("Fortune", "भाग्य"),
    SUDARSHANA_THEME_H9_2("Father", "पिता"),
    SUDARSHANA_THEME_H10_1("Career", "करियर"),
    SUDARSHANA_THEME_H10_2("Status", "प्रतिष्ठा"),
    SUDARSHANA_THEME_H11_1("Gains", "लाभ"),
    SUDARSHANA_THEME_H11_2("Income", "आम्दानी"),
    SUDARSHANA_THEME_H12_1("Losses", "हानि"),
    SUDARSHANA_THEME_H12_2("Expenses", "खर्च"),

    // ============================================
    // LAL KITAB REMEDIES SCREEN
    // ============================================
    LAL_KITAB_NOTE_DISTINCT("Lal Kitab remedies are unique and different from traditional Vedic remedies.", "लाल किताब उपायहरू अद्वितीय छन् र परम्परागत वैदिक उपायहरूभन्दा फरक छन्।"),
    LAL_KITAB_RECOMMENDED_REMEDIES("Recommended Remedies", "सिफारिस गरिएका उपायहरू"),
    LAL_KITAB_AFFLICTION_KANYA("Kanya Rin (Debt of Young Women)", "कन्या ऋण"),
    LAL_KITAB_AFFLICTION_GRAHAN("Grahan Dosha", "ग्रहण दोष"),
    LAL_KITAB_AFFLICTION_SHANI("Shani Affliction", "शनि पीडा"),
    LAL_KITAB_AFFLICTION_GENERAL("General Affliction", "सामान्य पीडा"),
    LAL_KITAB_SECTION_REMEDIES("Remedies", "उपायहरू"),
    LAL_KITAB_GENERAL_PRINCIPLES("General Principles of Lal Kitab", "लाल किताबका सामान्य सिद्धान्तहरू"),
    LAL_KITAB_RIN_TITLE("Karmic Debts (Rin)", "कर्म ऋणहरू"),
    LAL_KITAB_RIN_DESC("Karmic debts indicate areas where remedial measures are needed to balance past karma.", "कर्म ऋणहरूले विगतको कर्म सन्तुलन गर्न उपायहरू आवश्यक पर्ने क्षेत्रहरू संकेत गर्दछ।"),
    LAL_KITAB_RIN_NONE_TITLE("No Significant Karmic Debts", "कुनै महत्त्वपूर्ण कर्म ऋण छैन"),
    LAL_KITAB_RIN_NONE_DESC("Your chart does not indicate major karmic debts requiring immediate remedies.", "तपाईंको कुण्डलीले तत्काल उपाय आवश्यक पर्ने प्रमुख कर्म ऋणहरू संकेत गर्दैन।"),

    // ============================================
    // PANCH MAHAPURUSHA SCREEN MISSING KEYS
    // ============================================
    PANCHA_NO_YOGAS("No Yogas", "कुनै योग छैन"),

    // ============================================
    // SHADBALA SCREEN MISSING KEYS
    // ============================================
    REPORT_PLANET("Planet Report", "ग्रह रिपोर्ट"),

    // ============================================
    // DASHA SYSTEMS SCREEN MISSING KEYS
    // ============================================
    ERROR_CALCULATION("Calculation Error", "गणना त्रुटि"),
    DASHA_SHORT_VIM("Vim", "विं"),
    DASHA_SHORT_YOG("Yog", "यो"),
    DASHA_SHORT_ASH("Ash", "अष्टो"),
    DASHA_SHORT_SUD("Sud", "सुद"),
    DASHA_SHORT_CHA("Cha", "चर"),

    // ============================================
    // YOGINI DASHA SCREEN MISSING KEYS
    // ============================================
    YOGINI_SEQUENCE("Yogini Sequence", "योगिनी क्रम"),

    // ============================================
    // UPACHAYA TRANSIT SCREEN MISSING KEYS
    // ============================================
    QUALITY_LABEL("Quality", "गुणस्तर"),
    FROM_LABEL("from", "बाट"),
    PLANETS_TITLE("Transiting Planets", "गोचर ग्रहहरू"),
    UPACHAYA_TRANSITS_LABEL("Transits", "गोचरहरू");
}
