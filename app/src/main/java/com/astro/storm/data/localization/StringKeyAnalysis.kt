package com.astro.storm.data.localization


/**
 * Transit, Panchanga, Prashna, and Varshaphala string keys
 * Part 3 of 4 split enums to avoid JVM method size limit
 */
enum class StringKeyAnalysis(override val en: String, override val ne: String) : StringKeyInterface {

    // ============================================
    // TRANSITS TAB - ADDITIONAL STRINGS
    // ============================================
    TRANSIT_OVERVIEW("Transit Overview", "गोचर सिंहावलोकन"),
    TRANSIT_FAVORABLE("Favorable", "अनुकूल"),
    TRANSIT_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    TRANSIT_ASPECTS("Aspects", "दृष्टिहरू"),
    TRANSIT_OVERALL_SCORE("Overall Transit Score", "समग्र गोचर अंक"),
    TRANSIT_CURRENT_POSITIONS("Current Planetary Positions", "हालको ग्रह स्थितिहरू"),
    TRANSIT_GOCHARA_ANALYSIS("Gochara Analysis (From Moon)", "गोचर विश्लेषण (चन्द्रबाट)"),
    TRANSIT_ASPECTS_TO_NATAL("Transit Aspects to Natal", "जन्म कुण्डलीमा गोचर दृष्टि"),
    TRANSIT_SIGNIFICANT_PERIODS("Significant Periods", "महत्त्वपूर्ण अवधिहरू"),
    TRANSIT_APPLYING("Applying", "समीप आउँदै"),
    TRANSIT_SEPARATING("Separating", "टाढा हुँदै"),
    TRANSIT_ORB("Orb: %s°", "कोणान्तर: %s°"),
    TRANSIT_VEDHA_FROM("Vedha from %s", "%s बाट वेध"),
    TRANSIT_INTENSITY("Intensity %d/5", "तीव्रता %d/५"),
    TRANSIT_HOUSE_FROM_MOON("House %d", "भाव %d"),
    TRANSIT_PLANET_POSITIONS("Planet Positions", "ग्रह स्थितिहरू"),
    TRANSIT_CURRENT_MOVEMENTS("Current planetary movements - %s", "वर्तमान ग्रह गतिहरू - %s"),
    TRANSIT_OVERALL_ASSESSMENT("Overall Assessment", "समग्र मूल्याङ्कन"),
    TRANSIT_HOUSE_LABEL("House", "भाव"),
    TRANSIT_LABEL("TRANSIT", "गोचर"),
    TRANSIT_NATAL_LABEL("NATAL", "जन्मकालीन"),
    TRANSIT_NO_PLANETS_TRANSITING("No planets transiting", "कुनै ग्रह गोचर गर्दैन"),
    TRANSIT_UPCOMING("Significant Upcoming Transits", "महत्त्वपूर्ण आगामी गोचरहरू"),
    TRANSIT_NO_UPCOMING("No significant upcoming transits in the near future", "नजिकको भविष्यमा कुनै महत्त्वपूर्ण आगामी गोचर छैन"),
    TRANSIT_TO_NATAL_ASPECTS("Transit to Natal Aspects", "जन्म कुण्डलीमा गोचर दृष्टि"),
    TRANSIT_NO_ASPECTS("No significant transit aspects currently active", "हाल कुनै महत्त्वपूर्ण गोचर दृष्टि सक्रिय छैन"),
    TRANSIT_NO_DATA("No Transit Data", "कुनै गोचर डाटा छैन"),
    TRANSIT_SELECT_CHART("Please select a birth chart to view transits", "गोचर हेर्न जन्म कुण्डली छान्नुहोस्"),
    TRANSIT_CURRENT_INFLUENCES("Current planetary influences", "वर्तमान ग्रह प्रभावहरू"),
    TRANSIT_PLANETS_COUNT("Planets", "ग्रहहरू"),
    TRANSIT_MAJOR_TRANSITS("Major Transits", "प्रमुख गोचरहरू"),
    TRANSIT_QUALITY_LABEL("Quality", "गुणस्तर"),
    TRANSIT_RETROGRADE_SYMBOL("R", "व"),

    // ============================================
    // PANCHANGA TAB - ADDITIONAL STRINGS
    // ============================================
    PANCHANGA_AT_BIRTH("Panchanga at Birth", "जन्मको समयको पञ्चाङ्ग"),
    PANCHANGA_ABOUT("About Panchanga", "पञ्चाङ्गको बारेमा"),
    PANCHANGA_ABOUT_INTRO("पञ्चाङ्ग परिचय", "पञ्चाङ्ग परिचय"),
    PANCHANGA_SANSKRIT("पञ्चाङ्ग", "पञ्चाङ्ग"),
    PANCHANGA_LUNAR_DAY("Lunar Day", "चन्द्र दिन"),
    PANCHANGA_LUNAR_DAY_SANSKRIT("तिथि", "तिथि"),
    PANCHANGA_LUNAR_MANSION("Lunar Mansion", "चन्द्र नक्षत्र"),
    PANCHANGA_LUNAR_MANSION_SANSKRIT("नक्षत्र", "नक्षत्र"),
    PANCHANGA_LUNISOLAR("Luni-Solar Combination", "चन्द्र-सूर्य संयोजन"),
    PANCHANGA_LUNISOLAR_SANSKRIT("योग", "योग"),
    PANCHANGA_HALF_LUNAR_DAY("Half Lunar Day", "अर्ध चन्द्र दिन"),
    PANCHANGA_HALF_LUNAR_DAY_SANSKRIT("करण", "करण"),
    PANCHANGA_WEEKDAY("Weekday", "वार"),
    PANCHANGA_WEEKDAY_SANSKRIT("वार", "वार"),
    PANCHANGA_NUMBER("Number", "संख्या"),
    PANCHANGA_NUMBER_OF("of", "मध्ये"),
    PANCHANGA_DEITY("Deity", "देवता"),
    PANCHANGA_LORD("Lord", "स्वामी"),
    PANCHANGA_NATURE("Nature", "प्रकृति"),
    PANCHANGA_PROGRESS("Progress", "प्रगति"),
    PANCHANGA_SYMBOL("Symbol", "चिन्ह"),
    PANCHANGA_GANA("Gana", "गण"),
    PANCHANGA_GUNA("Guna", "गुण"),
    PANCHANGA_ANIMAL("Animal", "पशु"),
    PANCHANGA_MEANING("Meaning", "अर्थ"),
    PANCHANGA_TYPE("Type", "प्रकार"),
    PANCHANGA_ELEMENT("Element", "तत्व"),
    PANCHANGA_DIRECTION("Direction", "दिशा"),
    PANCHANGA_RULING_PLANET("Ruling Planet", "शासक ग्रह"),
    PANCHANGA_SIGNIFICANCE("Significance", "महत्त्व"),
    PANCHANGA_CHARACTERISTICS("Characteristics", "विशेषताहरू"),
    PANCHANGA_EFFECTS("Effects", "प्रभावहरू"),
    PANCHANGA_FAVORABLE_ACTIVITIES("Favorable Activities", "अनुकूल गतिविधिहरू"),
    PANCHANGA_AVOID("Activities to Avoid", "टाढा रहनु पर्ने गतिविधिहरू"),
    PANCHANGA_TITHI_SUBTITLE("Lunar Day • तिथि", "चन्द्र दिन • तिथि"),
    PANCHANGA_NAKSHATRA_SUBTITLE("Lunar Mansion • नक्षत्र", "चन्द्र नक्षत्र • नक्षत्र"),
    PANCHANGA_YOGA_SUBTITLE("Luni-Solar Combination • योग", "चन्द्र-सूर्य संयोजन • योग"),
    PANCHANGA_KARANA_SUBTITLE("Half Lunar Day • करण", "अर्ध चन्द्र दिन • करण"),
    PANCHANGA_VARA_SUBTITLE("Weekday • वार", "वार • वार"),
    PANCHANGA_SANSKRIT_LABEL("Sanskrit", "संस्कृत"),
    PANCHANGA_PADA("Pada", "पद"),
    PANCHANGA_RULER("Ruler", "स्वामी"),
    PANCHANGA_ABOUT_SUBTITLE("पञ्चाङ्ग परिचय", "पञ्चाङ्ग परिचय"),
    PANCHANGA_ABOUT_DESCRIPTION("Panchanga (Sanskrit: पञ्चाङ्ग, \"five limbs\") is the traditional Hindu calendar and almanac. It tracks five fundamental elements of Vedic time-keeping, essential for determining auspicious moments (muhurta) for important activities.", "पञ्चाङ्ग (संस्कृत: पञ्चाङ्ग, \"पाँच अंग\") परम्परागत हिन्दू पात्रो र पंचांग हो। यसले वैदिक समय-गणनाको पाँच मौलिक तत्वहरूको ट्र्याक गर्दछ, जुन महत्त्वपूर्ण कार्यहरूको लागि शुभ क्षण (मुहूर्त) निर्धारण गर्न आवश्यक छ।"),
    PANCHANGA_TITHI_DESC("Based on the angular distance between Sun and Moon. Each tithi spans 12° of lunar elongation. There are 30 tithis in a lunar month.", "सूर्य र चन्द्रमाबीचको कोणीय दूरीमा आधारित। प्रत्येक तिथि १२° चन्द्र विस्तार समेट्छ। एक चान्द्र महिनामा ३० तिथि हुन्छन्।"),
    PANCHANGA_NAKSHATRA_DESC("The Moon's position among 27 stellar constellations, each spanning 13°20'. Determines the Moon's influence on consciousness.", "२७ नक्षत्र तारामण्डलहरू बीच चन्द्रमाको स्थिति, प्रत्येक १३°२०' समेट्छ। चेतनामा चन्द्रमाको प्रभाव निर्धारण गर्दछ।"),
    PANCHANGA_YOGA_DESC("Derived from the sum of Sun and Moon longitudes divided into 27 equal parts. Indicates the overall quality of time.", "सूर्य र चन्द्रमाको देशान्तरको योगफललाई २७ बराबर भागमा विभाजित गरी व्युत्पन्न। समयको समग्र गुणस्तर संकेत गर्दछ।"),
    PANCHANGA_KARANA_DESC("Each tithi has two karanas. There are 11 karanas (4 fixed, 7 repeating) cycling through the month.", "प्रत्येक तिथिमा दुई करण हुन्छन्। ११ करण (४ स्थिर, ७ दोहोरिने) महिनाभर चक्रित हुन्छन्।"),
    PANCHANGA_VARA_DESC("Each day is ruled by a planet, influencing the day's energy and suitable activities.", "प्रत्येक दिन एक ग्रहले शासन गर्दछ, दिनको ऊर्जा र उपयुक्त गतिविधिहरूलाई प्रभाव पार्दछ।"),
    PANCHANGA_BIRTH_INSIGHT("The Panchanga at birth reveals the cosmic influences active at the moment of incarnation, providing insights into one's inherent nature, tendencies, and life patterns.", "जन्मको समयको पञ्चाङ्गले अवतारको क्षणमा सक्रिय ब्रह्माण्डीय प्रभावहरू प्रकट गर्दछ, व्यक्तिको स्वभाविक प्रकृति, प्रवृत्ति र जीवन ढाँचामा अन्तर्दृष्टि प्रदान गर्दछ।"),

    // Panchanga Info Card Element Titles (with Sanskrit)
    PANCHANGA_INFO_TITHI_TITLE("Tithi (तिथि)", "तिथि (तिथि)"),
    PANCHANGA_INFO_NAKSHATRA_TITLE("Nakshatra (नक्षत्र)", "नक्षत्र (नक्षत्र)"),
    PANCHANGA_INFO_YOGA_TITLE("Yoga (योग)", "योग (योग)"),
    PANCHANGA_INFO_KARANA_TITLE("Karana (करण)", "करण (करण)"),
    PANCHANGA_INFO_VARA_TITLE("Vara (वार)", "वार (वार)"),
    PANCHANGA_INFO_TITHI_LABEL("Lunar Day", "चन्द्र दिन"),
    PANCHANGA_INFO_NAKSHATRA_LABEL("Lunar Mansion", "चन्द्र नक्षत्र"),
    PANCHANGA_INFO_YOGA_LABEL("Luni-Solar Combination", "चन्द्र-सूर्य संयोजन"),
    PANCHANGA_INFO_KARANA_LABEL("Half Tithi", "अर्ध तिथि"),
    PANCHANGA_INFO_VARA_LABEL("Weekday", "वार"),

    // Quality Indicators
    QUALITY_EXCELLENT("Excellent", "उत्कृष्ट"),
    QUALITY_GOOD("Good", "राम्रो"),
    QUALITY_NEUTRAL("Neutral", "तटस्थ"),
    QUALITY_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    QUALITY_INAUSPICIOUS("Inauspicious", "अशुभ"),

    // ============================================
    // CHART TAB - LEGEND & UI STRINGS
    // ============================================
    CHART_LEGEND_RETRO("Retro", "वक्री"),
    CHART_LEGEND_COMBUST("Combust", "अस्त"),
    CHART_LEGEND_VARGOTTAMA("Vargottama", "वर्गोत्तम"),
    CHART_LEGEND_EXALTED("Exalted", "उच्च"),
    CHART_LEGEND_DEBILITATED("Debilitated", "नीच"),
    CHART_LEGEND_OWN_SIGN("Own Sign", "स्वराशि"),
    CHART_LEGEND_MOOL_TRI("Mool Tri.", "मूलत्रि."),
    CHART_BIRTH_DETAILS("Birth Details", "जन्म विवरण"),
    CHART_PLANETARY_POSITIONS("Planetary Positions", "ग्रह स्थितिहरू"),
    CHART_TAP_FOR_DETAILS("Tap for details", "विवरणको लागि ट्याप गर्नुहोस्"),
    CHART_TAP_TO_EXPAND("Tap to expand", "विस्तार गर्न ट्याप गर्नुहोस्"),
    CHART_TAP_HOUSE_FOR_DETAILS("Tap house for details", "विवरणको लागि भाव ट्याप गर्नुहोस्"),
    CHART_ASCENDANT_LAGNA("Ascendant (Lagna)", "लग्न"),
    CHART_HOUSE_CUSPS("House Cusps", "भाव सन्धि"),
    CHART_ASTRONOMICAL_DATA("Astronomical Data", "खगोलीय तथ्याङ्क"),
    CHART_JULIAN_DAY("Julian Day", "जुलियन दिन"),
    CHART_MIDHEAVEN("Midheaven", "मध्याकाश"),
    CHART_HOUSE_SYSTEM("House System", "भाव पद्धति"),
    CHART_TAP_FULLSCREEN("Tap chart to view fullscreen", "पूर्ण स्क्रिनमा हेर्न चार्ट ट्याप गर्नुहोस्"),
    CHART_DATE("Date", "मिति"),
    CHART_TIME("Time", "समय"),
    CHART_AYANAMSA("Ayanamsa", "अयनांश"),
    CHART_LOCATION("Location", "स्थान"),

    // Common Birth Data Labels
    HOUSE("House", "भाव"),
    LOCATION("Location", "स्थान"),
    ASCENDANT("Ascendant", "लग्न"),

    // Chart Type Labels
    CHART_LAGNA("Lagna", "लग्न"),
    CHART_RASHI("Rashi Chart (D1)", "राशि कुण्डली (D1)"),
    CHART_NAVAMSA("Navamsa Chart (D9)", "नवांश कुण्डली (D9)"),

    // ============================================
    // PLANET DIALOG - SECTION HEADERS
    // ============================================
    DIALOG_POSITION_DETAILS("Position Details", "स्थिति विवरण"),
    DIALOG_ZODIAC_SIGN("Zodiac Sign", "राशि"),
    DIALOG_DEGREE("Degree", "अंश"),
    DIALOG_HOUSE("House", "भाव"),
    DIALOG_NAKSHATRA("Nakshatra", "नक्षत्र"),
    DIALOG_NAKSHATRA_LORD("Nakshatra Lord", "नक्षत्र स्वामी"),
    DIALOG_NAKSHATRA_DEITY("Nakshatra Deity", "नक्षत्र देवता"),
    DIALOG_MOTION("Motion", "गति"),
    DIALOG_RETROGRADE("Retrograde", "वक्री"),
    DIALOG_STRENGTH_ANALYSIS("Strength Analysis (Shadbala)", "बल विश्लेषण (षड्बल)"),
    DIALOG_STRENGTH_BREAKDOWN("Strength Breakdown (Virupas)", "बल विभाजन (विरुपा)"),
    DIALOG_STHANA_BALA("Sthana Bala (Positional)", "स्थान बल"),
    DIALOG_DIG_BALA("Dig Bala (Directional)", "दिग्बल"),
    DIALOG_KALA_BALA("Kala Bala (Temporal)", "काल बल"),
    DIALOG_CHESTA_BALA("Chesta Bala (Motional)", "चेष्टा बल"),
    DIALOG_NAISARGIKA_BALA("Naisargika Bala (Natural)", "नैसर्गिक बल"),
    DIALOG_DRIK_BALA("Drik Bala (Aspectual)", "दृग्बल"),
    DIALOG_BENEFIC("Benefic", "शुभ"),
    DIALOG_MALEFIC("Malefic", "पापी"),
    DIALOG_ELEMENT("Element", "तत्व"),
    DIALOG_REPRESENTS("Represents:", "प्रतिनिधित्व:"),
    DIALOG_BODY_PARTS("Body Parts:", "शरीर अंग:"),
    DIALOG_PROFESSIONS("Professions:", "पेशाहरू:"),
    DIALOG_HOUSE_PLACEMENT("House %d Placement", "भाव %d स्थिति"),
    DIALOG_STATUS_CONDITIONS("Status & Conditions", "स्थिति र अवस्था"),
    DIALOG_DIGNITY("Dignity", "मर्यादा"),
    DIALOG_COMBUSTION("Combustion", "अस्त"),
    COMBUSTION_NOT_COMBUST("Not Combust", "अस्त छैन"),
    COMBUSTION_APPROACHING("Approaching Combustion", "अस्त नजिक"),
    COMBUSTION_COMBUST("Combust", "अस्त"),
    COMBUSTION_DEEP_COMBUST("Deep Combustion", "गहिरो अस्त"),
    COMBUSTION_CAZIMI("Cazimi", "काजिमी"),
    COMBUSTION_SEPARATING("Separating", "विभाजन"),
    DIALOG_PLANETARY_WAR("Planetary War", "ग्रहयुद्ध"),
    DIALOG_AT_WAR_WITH("At war with %s", "%s सँग युद्धमा"),
    DIALOG_INSIGHTS_PREDICTIONS("Insights & Predictions", "अन्तर्दृष्टि र भविष्यवाणी"),
    DIALOG_OVERALL("Overall: %s / %s Rupas", "समग्र: %s / %s रुपा"),
    DIALOG_REQUIRED_STRENGTH("Required", "आवश्यक"),
    DIALOG_PERCENT_OF_REQUIRED("%s%% of required strength", "आवश्यक बलको %s%%"),
    DIALOG_TOTAL_VIRUPAS("Total Virupas", "कुल विरुपा"),

    // House Names
    HOUSE_1_NAME("First House (Lagna)", "पहिलो भाव (लग्न)"),
    HOUSE_2_NAME("Second House (Dhana)", "दोस्रो भाव (धन)"),
    HOUSE_3_NAME("Third House (Sahaja)", "तेस्रो भाव (सहज)"),
    HOUSE_4_NAME("Fourth House (Sukha)", "चौथो भाव (सुख)"),
    HOUSE_5_NAME("Fifth House (Putra)", "पाँचौं भाव (पुत्र)"),
    HOUSE_6_NAME("Sixth House (Ripu)", "छैटौं भाव (रिपु)"),
    HOUSE_7_NAME("Seventh House (Kalatra)", "सातौं भाव (कलत्र)"),
    HOUSE_8_NAME("Eighth House (Ayur)", "आठौं भाव (आयु)"),
    HOUSE_9_NAME("Ninth House (Dharma)", "नवौं भाव (धर्म)"),
    HOUSE_10_NAME("Tenth House (Karma)", "दसौं भाव (कर्म)"),
    HOUSE_11_NAME("Eleventh House (Labha)", "एघारौं भाव (लाभ)"),
    HOUSE_12_NAME("Twelfth House (Vyaya)", "बाह्रौं भाव (व्यय)"),

    // House Signification Descriptions
    HOUSE_1_SIG("Self, Body, Personality", "आत्म, शरीर, व्यक्तित्व"),
    HOUSE_2_SIG("Wealth, Family, Speech", "धन, परिवार, वाणी"),
    HOUSE_3_SIG("Siblings, Courage, Communication", "भाइबहिनी, साहस, सञ्चार"),
    HOUSE_4_SIG("Home, Mother, Happiness", "घर, आमा, सुख"),
    HOUSE_5_SIG("Children, Intelligence, Romance", "सन्तान, बुद्धि, प्रेम"),
    HOUSE_6_SIG("Enemies, Health, Service", "शत्रु, स्वास्थ्य, सेवा"),
    HOUSE_7_SIG("Marriage, Partnerships, Business", "विवाह, साझेदारी, व्यापार"),
    HOUSE_8_SIG("Longevity, Transformation, Occult", "आयु, रूपान्तरण, तन्त्र"),
    HOUSE_9_SIG("Fortune, Dharma, Father", "भाग्य, धर्म, पिता"),
    HOUSE_10_SIG("Career, Status, Public Image", "क्यारियर, पद, सार्वजनिक छवि"),
    HOUSE_11_SIG("Gains, Income, Desires", "लाभ, आय, इच्छाहरू"),
    HOUSE_12_SIG("Losses, Expenses, Liberation", "हानि, खर्च, मोक्ष"),

    // House Types
    HOUSE_TYPE_KENDRA("Kendra (Angular)", "केन्द्र"),
    HOUSE_TYPE_TRIKONA("Trikona (Trine)", "त्रिकोण"),
    HOUSE_TYPE_DUSTHANA("Dusthana (Malefic)", "दुःस्थान"),
    HOUSE_TYPE_UPACHAYA("Upachaya (Growth)", "उपचय"),
    HOUSE_TYPE_MARAKA("Maraka (Death-inflicting)", "मारक"),
    HOUSE_TYPE_PANAPARA("Panapara", "पणफर"),
    HOUSE_TYPE_APOKLIMA("Apoklima", "आपोक्लिम"),

    // Dialog Buttons
    DIALOG_CLOSE("Close", "बन्द गर्नुहोस्"),
    DIALOG_RESET("Reset", "रिसेट"),
    DIALOG_ZOOM_IN("Zoom In", "ठूलो पार्नुहोस्"),
    DIALOG_ZOOM_OUT("Zoom Out", "सानो पार्नुहोस्"),
    DIALOG_DOWNLOAD("Download", "डाउनलोड"),
    DIALOG_SAVING("Saving...", "सेभ गर्दै..."),
    DIALOG_CHART_SAVED("Chart saved to gallery!", "चार्ट ग्यालेरीमा सेभ भयो!"),
    DIALOG_SAVE_FAILED("Failed to save chart", "चार्ट सेभ गर्न असफल"),

    // Nakshatra Dialog
    DIALOG_BASIC_INFO("Basic Information", "आधारभूत जानकारी"),
    DIALOG_NAKSHATRA_NATURE("Nakshatra Nature", "नक्षत्र प्रकृति"),
    DIALOG_PADA_CHARACTERISTICS("Pada %d Characteristics", "पाद %d विशेषताहरू"),
    DIALOG_GENERAL_CHARACTERISTICS("General Characteristics", "सामान्य विशेषताहरू"),
    DIALOG_CAREER_INDICATIONS("Career Indications", "क्यारियर संकेतहरू"),
    DIALOG_NUMBER("Number", "क्रमांक"),
    DIALOG_DEGREE_RANGE("Degree Range", "अंश दायरा"),

    DIALOG_NAVAMSA_SIGN("Navamsa Sign", "नवांश राशि"),
    DIALOG_GENDER("Gender", "लिङ्ग"),

    // House Dialog
    DIALOG_HOUSE_INFO("House Information", "भाव जानकारी"),
    DIALOG_SIGNIFICATIONS("Significations & Nature", "करकत्व र प्रकृति"),
    DIALOG_NATURE("Nature", "प्रकृति"),
    DIALOG_PLANETS_IN_HOUSE("Planets in House", "भावमा ग्रहहरू"),
    DIALOG_DETAILED_INTERPRETATION("Detailed Interpretation", "विस्तृत व्याख्या"),
    DIALOG_CUSP_DEGREE("Cusp Degree", "सन्धि अंश"),
    DIALOG_SIGN_LORD("Sign Lord", "राशि स्वामी"),
    DIALOG_HOUSE_TYPE("House Type", "भाव प्रकार"),

    // Shadbala Dialog
    DIALOG_SHADBALA_ANALYSIS("Shadbala Analysis", "षड्बल विश्लेषण"),
    DIALOG_SIXFOLD_STRENGTH("Six-fold Planetary Strength", "छवटा ग्रह बल"),
    DIALOG_OVERALL_SUMMARY("Overall Summary", "समग्र सारांश"),
    DIALOG_CHART_STRENGTH("Chart Strength", "कुण्डली बल"),
    DIALOG_STRONGEST("Strongest", "सबैभन्दा बलियो"),
    DIALOG_WEAKEST("Weakest", "सबैभन्दा कमजोर"),

    // ============================================
    // DEBUG ACTIVITY - ERROR SCREEN
    // ============================================
    DEBUG_UNHANDLED_EXCEPTION("Unhandled Exception", "अप्रत्याशित त्रुटि"),
    DEBUG_ERROR_OCCURRED("An unexpected error occurred.", "एउटा अप्रत्याशित त्रुटि भयो।"),
    DEBUG_COPY_LOG("Copy Log", "लग कपी गर्नुहोस्"),
    DEBUG_RESTART_APP("Restart App", "एप पुनः सुरु गर्नुहोस्"),
    DEBUG_CRASH_LOG("Crash Log", "क्र्यास लग"),

    // ============================================
    // CHART EXPORTER - PDF STRINGS
    // ============================================
    EXPORT_VEDIC_REPORT("VEDIC BIRTH CHART REPORT", "वैदिक जन्म कुण्डली रिपोर्ट"),
    EXPORT_NAME("Name:", "नाम:"),
    EXPORT_DATE_TIME("Date & Time:", "मिति र समय:"),
    EXPORT_LOCATION("Location:", "स्थान:"),
    EXPORT_COORDINATES("Coordinates:", "निर्देशांक:"),
    EXPORT_PLANETARY_POSITIONS("PLANETARY POSITIONS", "ग्रह स्थितिहरू"),
    EXPORT_ASTRONOMICAL_DATA("ASTRONOMICAL DATA", "खगोलीय तथ्याङ्क"),
    EXPORT_HOUSE_CUSPS("HOUSE CUSPS", "भाव सन्धिहरू"),
    EXPORT_YOGA_ANALYSIS("YOGA ANALYSIS", "योग विश्लेषण"),
    EXPORT_TOTAL_YOGAS("Total Yogas Found:", "कुल योगहरू फेला परेको:"),
    EXPORT_OVERALL_YOGA_STRENGTH("Overall Yoga Strength:", "समग्र योग बल:"),
    EXPORT_KEY_YOGAS("Key Yogas:", "प्रमुख योगहरू:"),
    EXPORT_CHALLENGING_YOGAS("Challenging Yogas:", "चुनौतीपूर्ण योगहरू:"),
    EXPORT_MITIGATED_BY("Mitigated by:", "न्यूनीकरण:"),
    EXPORT_PLANETARY_ASPECTS("PLANETARY ASPECTS", "ग्रह दृष्टिहरू"),
    EXPORT_SHADBALA_ANALYSIS("SHADBALA ANALYSIS", "षड्बल विश्लेषण"),
    EXPORT_OVERALL_CHART_STRENGTH("Overall Chart Strength:", "समग्र कुण्डली बल:"),
    EXPORT_STRONGEST_PLANET("Strongest Planet:", "सबैभन्दा बलियो ग्रह:"),
    EXPORT_WEAKEST_PLANET("Weakest Planet:", "सबैभन्दा कमजोर ग्रह:"),
    EXPORT_STRENGTH_BREAKDOWN("Strength Breakdown:", "बल विभाजन:"),
    EXPORT_ASHTAKAVARGA_ANALYSIS("ASHTAKAVARGA ANALYSIS", "अष्टकवर्ग विश्लेषण"),
    EXPORT_SARVASHTAKAVARGA("Sarvashtakavarga (Combined Strength)", "सर्वाष्टकवर्ग (संयुक्त बल)"),
    EXPORT_BHINNASHTAKAVARGA("Bhinnashtakavarga (Individual Planet Bindus)", "भिन्नाष्टकवर्ग (व्यक्तिगत ग्रह बिन्दु)"),
    EXPORT_TRANSIT_GUIDE("Transit Interpretation Guide:", "गोचर व्याख्या गाइड:"),
    EXPORT_SAV_EXCELLENT("SAV 30+ bindus: Excellent for transits - major positive events", "SAV ३०+ बिन्दु: गोचरको लागि उत्कृष्ट - प्रमुख सकारात्मक घटनाहरू"),
    EXPORT_SAV_GOOD("SAV 28-29 bindus: Good for transits - favorable outcomes", "SAV २८-२९ बिन्दु: गोचरको लागि राम्रो - अनुकूल परिणामहरू"),
    EXPORT_SAV_AVERAGE("SAV 25-27 bindus: Average - mixed results", "SAV २५-२७ बिन्दु: औसत - मिश्रित परिणामहरू"),
    EXPORT_SAV_CHALLENGING("SAV below 25: Challenging - caution advised during transits", "SAV २५ भन्दा कम: चुनौतीपूर्ण - गोचरमा सावधानी आवश्यक"),
    EXPORT_BAV_EXCELLENT("BAV 5+ bindus: Planet transit through this sign is highly beneficial", "BAV ५+ बिन्दु: यस राशिमा ग्रह गोचर अत्यन्त लाभदायक"),
    EXPORT_BAV_GOOD("BAV 4 bindus: Good results from planet transit", "BAV ४ बिन्दु: ग्रह गोचरबाट राम्रो परिणामहरू"),
    EXPORT_BAV_AVERAGE("BAV 3 bindus: Average results", "BAV ३ बिन्दु: औसत परिणामहरू"),
    EXPORT_BAV_CHALLENGING("BAV 0-2 bindus: Difficult transit period for that planet", "BAV ०-२ बिन्दु: त्यो ग्रहको लागि कठिन गोचर अवधि"),
    EXPORT_GENERATED_BY("Generated by AstroStorm - Ultra-Precision Vedic Astrology", "AstroStorm द्वारा उत्पन्न - अति-सटीक वैदिक ज्योतिष"),
    EXPORT_PAGE("Page %d", "पृष्ठ %d"),
    EXPORT_PLANET("Planet", "ग्रह"),
    EXPORT_SIGN("Sign", "राशि"),
    EXPORT_DEGREE("Degree", "अंश"),
    EXPORT_NAKSHATRA("Nakshatra", "नक्षत्र"),
    EXPORT_PADA("Pada", "पाद"),
    EXPORT_HOUSE("House", "भाव"),
    EXPORT_STATUS("Status", "स्थिति"),
    EXPORT_TOTAL_RUPAS("Total Rupas", "कुल रुपा"),
    EXPORT_REQUIRED("Required", "आवश्यक"),
    EXPORT_PERCENT("%", "प्रतिशत"),
    EXPORT_RATING("Rating", "मूल्याङ्कन"),
    EXPORT_VIRUPAS("virupas", "विरुपा"),

    // Birth Chart Summary Labels
    EXPORT_BIRTH_INFO("BIRTH INFORMATION", "जन्म जानकारी"),
    EXPORT_CHART_SUMMARY("CHART SUMMARY", "कुण्डली सारांश"),
    EXPORT_ASCENDANT_LAGNA("Ascendant (Lagna):", "लग्न:"),
    EXPORT_MOON_SIGN_RASHI("Moon Sign (Rashi):", "चन्द्र राशि:"),
    EXPORT_SUN_SIGN("Sun Sign:", "सूर्य राशि:"),
    EXPORT_BIRTH_NAKSHATRA("Birth Nakshatra:", "जन्म नक्षत्र:"),
    EXPORT_TIMEZONE("Timezone:", "समय क्षेत्र:"),

    // Text Report Footer
    EXPORT_CALC_ENGINE("Calculation Engine: Swiss Ephemeris (JPL Mode)", "गणना इन्जिन: स्विस ईफेमेरिस (JPL मोड)"),
    EXPORT_ULTRA_PRECISION("Ultra-Precision Vedic Astrology Software", "अति-सटीक वैदिक ज्योतिष सफ्टवेयर"),
    EXPORT_GENERATED_BY_SHORT("Generated by AstroStorm", "AstroStorm द्वारा उत्पन्न"),
    EXPORT_GENERATED("Generated", "उत्पन्न"),
    EXPORT_MOON_SIGN("Moon Sign (Rashi)", "चन्द्र राशि"),

    // ============================================
    // PREDICTION TYPES
    // ============================================
    PREDICTION_STRONG_PLANET("Strong %s", "बलियो %s"),
    PREDICTION_STRONG_DESC("This planet has sufficient strength to deliver positive results. Its significations will manifest more easily in your life.", "यो ग्रहसँग सकारात्मक परिणामहरू दिनको लागि पर्याप्त बल छ। यसको करकत्वहरू तपाईंको जीवनमा सजिलैसँग प्रकट हुनेछन्।"),
    PREDICTION_WEAK_PLANET("Weak %s", "कमजोर %s"),
    PREDICTION_WEAK_DESC("This planet lacks sufficient strength. You may face challenges in areas it governs. Remedial measures may help.", "यो ग्रहमा पर्याप्त बल छैन। तपाईंले यसले शासन गर्ने क्षेत्रहरूमा चुनौतीहरू सामना गर्न सक्नुहुन्छ। उपाय उपायहरूले मद्दत गर्न सक्छ।"),
    PREDICTION_EXALTED("Exalted Planet", "उच्च ग्रह"),
    PREDICTION_EXALTED_DESC("%s is in its sign of exaltation, giving exceptional results in its significations.", "%s आफ्नो उच्च राशिमा छ, यसको करकत्वमा असाधारण परिणामहरू दिँदै।"),
    PREDICTION_DEBILITATED("Debilitated Planet", "नीच ग्रह"),
    PREDICTION_DEBILITATED_DESC("%s is in its fall. Its positive significations may be reduced or delayed.", "%s आफ्नो नीच राशिमा छ। यसको सकारात्मक करकत्वहरू कम वा ढिलो हुन सक्छन्।"),
    PREDICTION_OWN_SIGN("Planet in Own Sign", "स्वराशिमा ग्रह"),
    PREDICTION_OWN_SIGN_DESC("%s is comfortable in its own sign, giving stable and reliable results.", "%s आफ्नै राशिमा सहज छ, स्थिर र भरपर्दो परिणामहरू दिँदै।"),
    PREDICTION_RETROGRADE("Retrograde Motion", "वक्री गति"),
    PREDICTION_RETROGRADE_DESC("Retrograde planets work on an internal level. Results may be delayed but often more profound.", "वक्री ग्रहहरू आन्तरिक स्तरमा काम गर्छन्। परिणामहरू ढिलो हुन सक्छन् तर प्रायः अधिक गहिरो हुन्छन्।"),
    PREDICTION_TRIKONA("Trikona Placement", "त्रिकोण स्थिति"),
    PREDICTION_TRIKONA_DESC("%s in house %d (Trikona) is auspicious for fortune and dharma.", "भाव %d (त्रिकोण) मा %s भाग्य र धर्मको लागि शुभ छ।"),
    PREDICTION_DUSTHANA("Dusthana Placement", "दुःस्थान स्थिति"),
    PREDICTION_DUSTHANA_DESC("%s in house %d may face obstacles but can also give transformative experiences.", "भाव %d मा %s ले बाधाहरू सामना गर्न सक्छ तर रूपान्तरणकारी अनुभवहरू पनि दिन सक्छ।"),
    PREDICTION_KENDRA("Kendra Placement", "केन्द्र स्थिति"),
    PREDICTION_KENDRA_DESC("%s in house %d (Kendra) gains strength and visibility.", "भाव %d (केन्द्र) मा %s ले बल र दृश्यता प्राप्त गर्दछ।"),

    // ============================================
    // VARSHAPHALA - TAJIKA ASPECT TYPES
    // ============================================
    TAJIKA_ITHASALA("Ithasala", "इतशाल"),
    TAJIKA_ITHASALA_DESC("Applying aspect - promises fulfillment of matters", "निकटवर्ती पक्ष - कार्यहरू पूर्ण हुने वाचा"),
    TAJIKA_EASARAPHA("Easarapha", "इसराफ"),
    TAJIKA_EASARAPHA_DESC("Separating aspect - event has passed or is fading", "विलग पक्ष - घटना बितिसकेको वा क्षीण हुँदैछ"),
    TAJIKA_NAKTA("Nakta", "नक्त"),
    TAJIKA_NAKTA_DESC("Transmission of light with reception - indirect completion", "ग्रहणसहित प्रकाश प्रसारण - अप्रत्यक्ष पूर्णता"),
    TAJIKA_YAMAYA("Yamaya", "यमाया"),
    TAJIKA_YAMAYA_DESC("Translation of light - third planet connects significators", "प्रकाश अनुवाद - तेस्रो ग्रहले कारकहरू जोड्दछ"),
    TAJIKA_MANAU("Manau", "मनौ"),
    TAJIKA_MANAU_DESC("Reverse application - slower planet applies to faster", "उल्टो प्रयोग - ढिलो ग्रहले छिटोमा प्रयोग गर्छ"),
    TAJIKA_KAMBOOLA("Kamboola", "कम्बूल"),
    TAJIKA_KAMBOOLA_DESC("Powerful Ithasala with angular placement", "केन्द्र स्थानसहित शक्तिशाली इतशाल"),
    TAJIKA_GAIRI_KAMBOOLA("Gairi-Kamboola", "गैरी-कम्बूल"),
    TAJIKA_GAIRI_KAMBOOLA_DESC("Weaker form of Kamboola", "कम्बूलको कमजोर रूप"),
    TAJIKA_KHALASARA("Khalasara", "खलासर"),
    TAJIKA_KHALASARA_DESC("Mutual separation - dissolution of matters", "पारस्परिक विलगता - कार्यहरूको विघटन"),
    TAJIKA_RADDA("Radda", "रद्द"),
    TAJIKA_RADDA_DESC("Refranation - retrograde breaks the aspect", "भंग - वक्री गतिले पक्ष तोड्छ"),
    TAJIKA_DUHPHALI_KUTTHA("Duhphali-Kuttha", "दुःफली-कुट्ठ"),
    TAJIKA_DUHPHALI_KUTTHA_DESC("Malefic intervention prevents completion", "पापग्रह हस्तक्षेपले पूर्णता रोक्छ"),
    TAJIKA_TAMBIRA("Tambira", "तम्बीर"),
    TAJIKA_TAMBIRA_DESC("Indirect aspect through intermediary", "मध्यस्थ मार्फत अप्रत्यक्ष पक्ष"),
    TAJIKA_KUTTHA("Kuttha", "कुट्ठ"),
    TAJIKA_KUTTHA_DESC("Impediment to aspect completion", "पक्ष पूर्णतामा बाधा"),
    TAJIKA_DURAPHA("Durapha", "दुराफ"),
    TAJIKA_DURAPHA_DESC("Hard aspect causing difficulties", "कठिनाइहरू निम्त्याउने कडा पक्ष"),
    TAJIKA_MUTHASHILA("Muthashila", "मुथशिल"),
    TAJIKA_MUTHASHILA_DESC("Mutual application between planets", "ग्रहहरू बीच पारस्परिक प्रयोग"),
    TAJIKA_IKKABALA("Ikkabala", "इक्कबल"),
    TAJIKA_IKKABALA_DESC("Unity of strength between planets", "ग्रहहरू बीच बलको एकता"),

    // ============================================
    // VARSHAPHALA - ASPECT STRENGTH
    // ============================================
    ASPECT_VERY_STRONG("Very Strong", "अति बलियो"),
    ASPECT_STRONG("Strong", "बलियो"),
    ASPECT_MODERATE("Moderate", "मध्यम"),
    ASPECT_WEAK("Weak", "कमजोर"),
    ASPECT_VERY_WEAK("Very Weak", "अति कमजोर"),

    // ============================================
    // VARSHAPHALA - SAHAM TYPES
    // ============================================
    SAHAM_PUNYA("Fortune", "पुण्य"),
    SAHAM_PUNYA_SANSKRIT("Punya Saham", "पुण्य सहम"),
    SAHAM_PUNYA_DESC("Overall luck and prosperity", "समग्र भाग्य र समृद्धि"),
    SAHAM_VIDYA("Education", "विद्या"),
    SAHAM_VIDYA_SANSKRIT("Vidya Saham", "विद्या सहम"),
    SAHAM_VIDYA_DESC("Learning and knowledge", "सिकाइ र ज्ञान"),
    SAHAM_YASHAS("Fame", "यश"),
    SAHAM_YASHAS_SANSKRIT("Yashas Saham", "यश सहम"),
    SAHAM_YASHAS_DESC("Reputation and recognition", "प्रतिष्ठा र मान्यता"),
    SAHAM_MITRA("Friends", "मित्र"),
    SAHAM_MITRA_SANSKRIT("Mitra Saham", "मित्र सहम"),
    SAHAM_MITRA_DESC("Friendship and alliances", "मित्रता र गठबन्धन"),
    SAHAM_MAHATMYA("Greatness", "महात्म्य"),
    SAHAM_MAHATMYA_SANSKRIT("Mahatmya Saham", "महात्म्य सहम"),
    SAHAM_MAHATMYA_DESC("Spiritual achievement", "आध्यात्मिक उपलब्धि"),
    SAHAM_ASHA("Hope", "आशा"),
    SAHAM_ASHA_SANSKRIT("Asha Saham", "आशा सहम"),
    SAHAM_ASHA_DESC("Aspirations and wishes", "आकांक्षा र इच्छाहरू"),
    SAHAM_SAMARTHA("Capability", "समर्थ"),
    SAHAM_SAMARTHA_SANSKRIT("Samartha Saham", "समर्थ सहम"),
    SAHAM_SAMARTHA_DESC("Ability and competence", "क्षमता र योग्यता"),
    SAHAM_BHRATRI("Siblings", "भ्रातृ"),
    SAHAM_BHRATRI_SANSKRIT("Bhratri Saham", "भ्रातृ सहम"),
    SAHAM_BHRATRI_DESC("Brothers and sisters", "दाजुभाइ र दिदीबहिनी"),
    SAHAM_PITRI("Father", "पितृ"),
    SAHAM_PITRI_SANSKRIT("Pitri Saham", "पितृ सहम"),
    SAHAM_PITRI_DESC("Father's welfare", "बुबाको कल्याण"),
    SAHAM_MATRI("Mother", "मातृ"),
    SAHAM_MATRI_SANSKRIT("Matri Saham", "मातृ सहम"),
    SAHAM_MATRI_DESC("Mother's welfare", "आमाको कल्याण"),
    SAHAM_PUTRA("Children", "पुत्र"),
    SAHAM_PUTRA_SANSKRIT("Putra Saham", "पुत्र सहम"),
    SAHAM_PUTRA_DESC("Offspring and progeny", "सन्तान"),
    SAHAM_VIVAHA("Marriage", "विवाह"),
    SAHAM_VIVAHA_SANSKRIT("Vivaha Saham", "विवाह सहम"),
    SAHAM_VIVAHA_DESC("Matrimony and partnership", "विवाह र साझेदारी"),
    SAHAM_KARMA("Career", "कर्म"),
    SAHAM_KARMA_SANSKRIT("Karma Saham", "कर्म सहम"),
    SAHAM_KARMA_DESC("Profession and livelihood", "पेशा र जीविका"),
    SAHAM_ROGA("Disease", "रोग"),
    SAHAM_ROGA_SANSKRIT("Roga Saham", "रोग सहम"),
    SAHAM_ROGA_DESC("Health challenges", "स्वास्थ्य चुनौतीहरू"),
    SAHAM_MRITYU("Longevity", "मृत्यु"),
    SAHAM_MRITYU_SANSKRIT("Mrityu Saham", "मृत्यु सहम"),
    SAHAM_MRITYU_DESC("Life span indicators", "जीवन अवधि संकेतकहरू"),
    SAHAM_PARADESA("Foreign", "परदेश"),
    SAHAM_PARADESA_SANSKRIT("Paradesa Saham", "परदेश सहम"),
    SAHAM_PARADESA_DESC("Travel and foreign lands", "यात्रा र विदेश"),
    SAHAM_DHANA("Wealth", "धन"),
    SAHAM_DHANA_SANSKRIT("Dhana Saham", "धन सहम"),
    SAHAM_DHANA_DESC("Financial prosperity", "आर्थिक समृद्धि"),
    SAHAM_RAJA("Power", "राज"),
    SAHAM_RAJA_SANSKRIT("Raja Saham", "राज सहम"),
    SAHAM_RAJA_DESC("Authority and position", "अधिकार र पद"),
    SAHAM_BANDHANA("Bondage", "बन्धन"),
    SAHAM_BANDHANA_SANSKRIT("Bandhana Saham", "बन्धन सहम"),
    SAHAM_BANDHANA_DESC("Restrictions and obstacles", "प्रतिबन्ध र बाधाहरू"),
    SAHAM_KARYASIDDHI_TYPE("Success", "कार्यसिद्धि"),
    SAHAM_KARYASIDDHI_TYPE_SANSKRIT("Karyasiddhi Saham", "कार्यसिद्धि सहम"),
    SAHAM_KARYASIDDHI_TYPE_DESC("Accomplishment of goals", "लक्ष्य प्राप्ति"),

    // ============================================
    // VARSHAPHALA - KEY DATE TYPES
    // ============================================
    KEY_DATE_FAVORABLE("Favorable", "अनुकूल"),
    KEY_DATE_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    KEY_DATE_IMPORTANT("Important", "महत्त्वपूर्ण"),
    KEY_DATE_TRANSIT("Transit", "गोचर"),

    // ============================================
    // VARSHAPHALA - UI STRINGS
    // ============================================
    VARSHAPHALA_NO_CHART("No Chart Selected", "कुनै कुण्डली छानिएको छैन"),
    VARSHAPHALA_NO_CHART_DESC("Select a birth chart to view Varshaphala", "वर्षफल हेर्न जन्म कुण्डली छान्नुहोस्"),
    VARSHAPHALA_SUN_RETURNS("Sun returns to natal position", "सूर्य जन्मकालीन स्थानमा फर्कन्छ"),
    VARSHAPHALA_RETURN_DATE("Return Date", "प्रतिफल मिति"),
    VARSHAPHALA_AGE_FORMAT("Age %d", "उमेर %d"),
    VARSHAPHALA_IN_HOUSE("House %d", "भाव %d"),
    VARSHAPHALA_FIVEFOLD_STRENGTH("Five-fold Planetary Strength", "पञ्चवर्गीय ग्रह बल"),
    VARSHAPHALA_THREE_FLAG("Three-flag Diagram", "त्रिपताकी चक्र"),
    VARSHAPHALA_IMPORTANT_DATES("%d important dates", "%d महत्त्वपूर्ण मितिहरू"),
    VARSHAPHALA_CHART_LEGEND_ASC("Asc", "लग्न"),
    VARSHAPHALA_CHART_LEGEND_MUNTHA("Muntha", "मुन्थ"),
    VARSHAPHALA_CHART_LEGEND_BENEFIC("Benefic", "शुभ"),
    VARSHAPHALA_CHART_LEGEND_MALEFIC("Malefic", "पाप"),
    VARSHAPHALA_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    VARSHAPHALA_TOTAL("Total", "कुल"),
    VARSHAPHALA_ACTIVE("Active", "सक्रिय"),
    VARSHAPHALA_CURRENT("CURRENT", "वर्तमान"),
    VARSHAPHALA_DAYS_FORMAT("%d days", "%d दिन"),
    VARSHAPHALA_HOUSE_SIGN("House %d - %s", "भाव %d - %s"),
    VARSHAPHALA_LORD_IN_HOUSE("Lord: %s in H%d", "स्वामी: %s भाव%d मा"),
    VARSHAPHALA_RULES_HOUSES("Rules Houses: %s", "भावहरू शासन: %s"),
    VARSHAPHALA_PLANETS("Planets: ", "ग्रहहरू: "),
    VARSHAPHALA_NO_PLANETS("No planets", "कुनै ग्रह छैन"),
    VARSHAPHALA_HOUSES_PREFIX("Houses: ", "भावहरू: "),
    VARSHAPHALA_SPECIFIC_INDICATIONS("Specific Indications:", "विशिष्ट संकेतहरू:"),
    VARSHAPHALA_LORD_PREFIX("Lord: %s", "स्वामी: %s"),

    // ============================================
    // VARSHAPHALA - MUNTHA THEMES
    // ============================================
    MUNTHA_PERSONAL_GROWTH("Personal Growth", "व्यक्तिगत विकास"),
    MUNTHA_NEW_BEGINNINGS("New Beginnings", "नयाँ शुरुवातहरू"),
    MUNTHA_HEALTH_FOCUS("Health Focus", "स्वास्थ्य फोकस"),
    MUNTHA_FINANCIAL_GAINS("Financial Gains", "आर्थिक लाभ"),
    MUNTHA_FAMILY_MATTERS("Family Matters", "पारिवारिक मामिलाहरू"),
    MUNTHA_SPEECH("Speech", "वाणी"),
    MUNTHA_COMMUNICATION("Communication", "सञ्चार"),
    MUNTHA_SHORT_TRAVELS("Short Travels", "छोटो यात्राहरू"),
    MUNTHA_SIBLINGS("Siblings", "भाइबहिनी"),
    MUNTHA_HOME_AFFAIRS("Home Affairs", "घरेलु मामिलाहरू"),
    MUNTHA_PROPERTY("Property", "सम्पत्ति"),
    MUNTHA_INNER_PEACE("Inner Peace", "आन्तरिक शान्ति"),
    MUNTHA_CREATIVITY("Creativity", "सिर्जनशीलता"),
    MUNTHA_ROMANCE("Romance", "रोमान्स"),
    MUNTHA_CHILDREN("Children", "सन्तान"),
    MUNTHA_SERVICE("Service", "सेवा"),
    MUNTHA_HEALTH_ISSUES("Health Issues", "स्वास्थ्य समस्याहरू"),
    MUNTHA_COMPETITION("Competition", "प्रतिस्पर्धा"),
    MUNTHA_PARTNERSHIPS("Partnerships", "साझेदारीहरू"),
    MUNTHA_MARRIAGE("Marriage", "विवाह"),
    MUNTHA_BUSINESS("Business", "व्यापार"),
    MUNTHA_TRANSFORMATION("Transformation", "रूपान्तरण"),
    MUNTHA_RESEARCH("Research", "अनुसन्धान"),
    MUNTHA_INHERITANCE("Inheritance", "विरासत"),
    MUNTHA_FORTUNE("Fortune", "भाग्य"),
    MUNTHA_LONG_TRAVEL("Long Travel", "लामो यात्रा"),
    MUNTHA_HIGHER_LEARNING("Higher Learning", "उच्च शिक्षा"),
    MUNTHA_CAREER_ADVANCEMENT("Career Advancement", "क्यारियर प्रगति"),
    MUNTHA_RECOGNITION("Recognition", "मान्यता"),
    MUNTHA_AUTHORITY("Authority", "अधिकार"),
    MUNTHA_GAINS("Gains", "लाभ"),
    MUNTHA_FRIENDS("Friends", "मित्रहरू"),
    MUNTHA_FULFILLED_WISHES("Fulfilled Wishes", "पूरा भएका इच्छाहरू"),
    MUNTHA_SPIRITUALITY("Spirituality", "आध्यात्मिकता"),
    MUNTHA_FOREIGN_LANDS("Foreign Lands", "विदेश"),
    MUNTHA_EXPENSES("Expenses", "खर्चहरू"),
    MUNTHA_GENERAL_GROWTH("General Growth", "सामान्य विकास"),

    // ============================================
    // VARSHAPHALA - HOUSE SIGNIFICATIONS
    // ============================================
    VARSHA_HOUSE_1_SIG("personal development and health", "व्यक्तिगत विकास र स्वास्थ्य"),
    VARSHA_HOUSE_2_SIG("finances and family", "वित्त र परिवार"),
    VARSHA_HOUSE_3_SIG("communication and siblings", "सञ्चार र भाइबहिनी"),
    VARSHA_HOUSE_4_SIG("home and property", "घर र सम्पत्ति"),
    VARSHA_HOUSE_5_SIG("creativity and children", "सिर्जनशीलता र सन्तान"),
    VARSHA_HOUSE_6_SIG("health and service", "स्वास्थ्य र सेवा"),
    VARSHA_HOUSE_7_SIG("partnerships and marriage", "साझेदारी र विवाह"),
    VARSHA_HOUSE_8_SIG("transformation and inheritance", "रूपान्तरण र विरासत"),
    VARSHA_HOUSE_9_SIG("fortune and higher learning", "भाग्य र उच्च शिक्षा"),
    VARSHA_HOUSE_10_SIG("career and status", "क्यारियर र स्थिति"),
    VARSHA_HOUSE_11_SIG("gains and friendships", "लाभ र मित्रता"),
    VARSHA_HOUSE_12_SIG("spirituality and foreign matters", "आध्यात्मिकता र विदेशी मामिलाहरू"),
    VARSHA_HOUSE_VARIOUS("various life areas", "विभिन्न जीवन क्षेत्रहरू"),

    // ============================================
    // VARSHAPHALA - PLANET PERIOD PREDICTIONS
    // ============================================
    VARSHA_SUN_NATURE("vitality, authority, and self-expression", "जीवनशक्ति, अधिकार र आत्म-अभिव्यक्ति"),
    VARSHA_MOON_NATURE("emotions, nurturing, and public connections", "भावनाहरू, पालनपोषण र सार्वजनिक सम्बन्धहरू"),
    VARSHA_MARS_NATURE("energy, initiative, and competitive drive", "ऊर्जा, पहल र प्रतिस्पर्धात्मक उत्प्रेरणा"),
    VARSHA_MERCURY_NATURE("communication, learning, and business", "सञ्चार, सिकाइ र व्यापार"),
    VARSHA_JUPITER_NATURE("wisdom, expansion, and good fortune", "बुद्धि, विस्तार र सुभाग्य"),
    VARSHA_VENUS_NATURE("relationships, creativity, and pleasures", "सम्बन्धहरू, सिर्जनशीलता र आनन्द"),
    VARSHA_SATURN_NATURE("discipline, responsibility, and long-term goals", "अनुशासन, जिम्मेवारी र दीर्घकालीन लक्ष्यहरू"),
    VARSHA_RAHU_NATURE("ambition, innovation, and unconventional paths", "महत्त्वाकांक्षा, नवीनता र अपरम्परागत मार्गहरू"),
    VARSHA_KETU_NATURE("spirituality, detachment, and past karma", "आध्यात्मिकता, वैराग्य र पूर्व कर्म"),
    VARSHA_GENERAL_NATURE("general influences", "सामान्य प्रभावहरू"),

    // ============================================
    // VARSHAPHALA - STRENGTH DESCRIPTIONS
    // ============================================
    VARSHA_STRENGTH_EXALTED("Exalted", "उच्च"),
    VARSHA_STRENGTH_STRONG("Strong", "बलियो"),
    VARSHA_STRENGTH_MODERATE("Moderate", "मध्यम"),
    VARSHA_STRENGTH_ANGULAR("Angular", "केन्द्रीय"),
    VARSHA_STRENGTH_RETROGRADE("Retrograde", "वक्री"),
    VARSHA_STRENGTH_DEBILITATED("Debilitated", "नीच"),
    VARSHA_STRENGTH_UNKNOWN("Unknown", "अज्ञात"),

    // ============================================
    // VARSHAPHALA - PREDICTION PHRASES
    // ============================================
    VARSHA_PERIOD_EXCEPTIONAL("This period promises exceptional results", "यो अवधिले असाधारण परिणामहरूको वाचा गर्छ"),
    VARSHA_PERIOD_WELL_SUPPORTED("This period is well-supported for success", "यो अवधि सफलताको लागि राम्ररी समर्थित छ"),
    VARSHA_PERIOD_EXTRA_EFFORT("This period requires extra effort and patience", "यो अवधिमा थप प्रयास र धैर्य चाहिन्छ"),
    VARSHA_PERIOD_MIXED("This period brings mixed but manageable influences", "यो अवधिले मिश्रित तर व्यवस्थापनयोग्य प्रभावहरू ल्याउँछ"),
    VARSHA_DURING_PERIOD("During this %s period, focus shifts to %s, particularly affecting %s. %s.", "यो %s अवधिमा, %s मा फोकस सर्छ, विशेष गरी %s लाई असर गर्दछ। %s।"),

    // ============================================
    // VARSHAPHALA - YEAR LORD DESCRIPTIONS
    // ============================================
    VARSHA_YEARLORD_SUN("Year Lord Sun brings focus on leadership, authority, and self-expression.", "वर्ष स्वामी सूर्यले नेतृत्व, अधिकार र आत्म-अभिव्यक्तिमा फोकस ल्याउँछ।"),
    VARSHA_YEARLORD_MOON("Year Lord Moon emphasizes emotional wellbeing and public connections.", "वर्ष स्वामी चन्द्रमाले भावनात्मक कल्याण र सार्वजनिक सम्बन्धहरूमा जोड दिन्छ।"),
    VARSHA_YEARLORD_MARS("Year Lord Mars energizes initiatives and competitive endeavors.", "वर्ष स्वामी मंगलले पहलहरू र प्रतिस्पर्धात्मक प्रयासहरूलाई ऊर्जित गर्छ।"),
    VARSHA_YEARLORD_MERCURY("Year Lord Mercury enhances communication and business activities.", "वर्ष स्वामी बुधले सञ्चार र व्यापारिक गतिविधिहरू बढाउँछ।"),
    VARSHA_YEARLORD_JUPITER("Year Lord Jupiter bestows wisdom, expansion, and good fortune.", "वर्ष स्वामी बृहस्पतिले बुद्धि, विस्तार र सुभाग्य प्रदान गर्छ।"),
    VARSHA_YEARLORD_VENUS("Year Lord Venus brings harmony to relationships and creativity.", "वर्ष स्वामी शुक्रले सम्बन्धहरू र सिर्जनशीलतामा सामञ्जस्य ल्याउँछ।"),
    VARSHA_YEARLORD_SATURN("Year Lord Saturn teaches discipline and responsibility.", "वर्ष स्वामी शनिले अनुशासन र जिम्मेवारी सिकाउँछ।"),
    VARSHA_YEARLORD_GENERIC("The Year Lord influences various aspects with balanced energy.", "वर्ष स्वामीले सन्तुलित ऊर्जासहित विभिन्न पक्षहरूलाई प्रभाव पार्छ।"),

    // ============================================
    // VARSHAPHALA - TRI-PATAKI CHAKRA
    // ============================================
    TRIPATAKI_DHARMA("Dharma (1, 5, 9)", "धर्म (१, ५, ९)"),
    TRIPATAKI_ARTHA("Artha (2, 6, 10)", "अर्थ (२, ६, १०)"),
    TRIPATAKI_KAMA("Kama (3, 7, 11)", "काम (३, ७, ११)"),
    TRIPATAKI_DHARMA_DOMINANT("Spiritual growth and righteous pursuits dominate", "आध्यात्मिक वृद्धि र धार्मिक खोजीहरू प्रभावी"),
    TRIPATAKI_ARTHA_DOMINANT("Material prosperity and career emphasis", "भौतिक समृद्धि र क्यारियर जोड"),
    TRIPATAKI_KAMA_DOMINANT("Relationships and desires take center stage", "सम्बन्धहरू र इच्छाहरू केन्द्रमा"),
    TRIPATAKI_BALANCED("Balanced influences across all areas", "सबै क्षेत्रहरूमा सन्तुलित प्रभाव"),
    TRIPATAKI_NO_PLANETS("No planets in %s sector - quieter year for these matters.", "%s क्षेत्रमा कुनै ग्रह छैन - यी मामिलाहरूको लागि शान्त वर्ष।"),
    TRIPATAKI_BENEFIC_INFLUENCE("Benefic %s bring favorable influences.", "शुभ %s ले अनुकूल प्रभावहरू ल्याउँछ।"),
    TRIPATAKI_MALEFIC_INFLUENCE("Malefic %s bring challenges requiring effort.", "पाप %s ले प्रयास चाहिने चुनौतीहरू ल्याउँछ।"),
    TRIPATAKI_MIXED_INFLUENCE("Mixed influences suggest variable results.", "मिश्रित प्रभावहरूले परिवर्तनशील परिणामहरू सुझाव दिन्छ।"),
    TRIPATAKI_DHARMA_AREA("righteousness, fortune, and higher learning", "धार्मिकता, भाग्य र उच्च शिक्षा"),
    TRIPATAKI_ARTHA_AREA("wealth, career, and practical achievements", "धन, क्यारियर र व्यावहारिक उपलब्धिहरू"),
    TRIPATAKI_KAMA_AREA("relationships, desires, and social connections", "सम्बन्धहरू, इच्छाहरू र सामाजिक सम्बन्धहरू"),
    TRIPATAKI_PLANETS_IN_TRIKONA("%d planet(s) in %s trikona emphasizes %s.", "%s त्रिकोणमा %d ग्रह(हरू)ले %s मा जोड दिन्छ।"),
    TRIPATAKI_BALANCED_DISTRIBUTION("Balanced distribution of planetary energies across all life sectors.", "सबै जीवन क्षेत्रहरूमा ग्रह ऊर्जाहरूको सन्तुलित वितरण।"),

    // ============================================
    // VARSHAPHALA - OVERALL PREDICTION TONES
    // ============================================
    VARSHA_TONE_EXCELLENT("excellent", "उत्कृष्ट"),
    VARSHA_TONE_FAVORABLE("favorable", "अनुकूल"),
    VARSHA_TONE_POSITIVE("positive", "सकारात्मक"),
    VARSHA_TONE_CHALLENGING("challenging but growth-oriented", "चुनौतीपूर्ण तर विकासोन्मुख"),
    VARSHA_TONE_BALANCED("balanced", "सन्तुलित"),

    // ============================================
    // VARSHAPHALA - PANCHA VARGIYA BALA CATEGORIES
    // ============================================
    PANCHA_EXCELLENT("Excellent", "उत्कृष्ट"),
    PANCHA_GOOD("Good", "राम्रो"),
    PANCHA_AVERAGE("Average", "औसत"),
    PANCHA_BELOW_AVERAGE("Below Average", "औसतमुनि"),
    PANCHA_WEAK("Weak", "कमजोर"),

    // ============================================
    // VARSHAPHALA - DASHA KEYWORDS
    // ============================================
    DASHA_KW_LEADERSHIP("Leadership", "नेतृत्व"),
    DASHA_KW_VITALITY("Vitality", "जीवनशक्ति"),
    DASHA_KW_FATHER("Father", "बुबा"),
    DASHA_KW_EMOTIONS("Emotions", "भावनाहरू"),
    DASHA_KW_MOTHER("Mother", "आमा"),
    DASHA_KW_PUBLIC("Public", "सार्वजनिक"),
    DASHA_KW_ACTION("Action", "कार्य"),
    DASHA_KW_ENERGY("Energy", "ऊर्जा"),
    DASHA_KW_COURAGE("Courage", "साहस"),
    DASHA_KW_COMMUNICATION("Communication", "सञ्चार"),
    DASHA_KW_LEARNING("Learning", "सिकाइ"),
    DASHA_KW_WISDOM("Wisdom", "बुद्धि"),
    DASHA_KW_GROWTH("Growth", "वृद्धि"),
    DASHA_KW_LOVE("Love", "प्रेम"),
    DASHA_KW_ART("Art", "कला"),
    DASHA_KW_COMFORT("Comfort", "सुविधा"),
    DASHA_KW_DISCIPLINE("Discipline", "अनुशासन"),
    DASHA_KW_KARMA("Karma", "कर्म"),
    DASHA_KW_DELAYS("Delays", "ढिलाइ"),
    DASHA_KW_AMBITION("Ambition", "महत्त्वाकांक्षा"),
    DASHA_KW_INNOVATION("Innovation", "नवीनता"),
    DASHA_KW_FOREIGN("Foreign", "विदेशी"),
    DASHA_KW_DETACHMENT("Detachment", "वैराग्य"),
    DASHA_KW_PAST("Past", "भूतकाल"),
    DASHA_KW_GENERAL("General", "सामान्य"),
    DASHA_KW_SELF("Self", "आत्म"),
    DASHA_KW_BODY("Body", "शरीर"),
    DASHA_KW_WEALTH("Wealth", "धन"),
    DASHA_KW_SPEECH("Speech", "वाणी"),
    DASHA_KW_HOME("Home", "घर"),
    DASHA_KW_PEACE("Peace", "शान्ति"),
    DASHA_KW_CHILDREN("Children", "सन्तान"),
    DASHA_KW_HEALTH("Health", "स्वास्थ्य"),
    DASHA_KW_SERVICE("Service", "सेवा"),
    DASHA_KW_MARRIAGE("Marriage", "विवाह"),
    DASHA_KW_BUSINESS("Business", "व्यापार"),
    DASHA_KW_TRANSFORMATION("Transformation", "रूपान्तरण"),
    DASHA_KW_RESEARCH("Research", "अनुसन्धान"),
    DASHA_KW_LUCK("Luck", "भाग्य"),
    DASHA_KW_TRAVEL("Travel", "यात्रा"),
    DASHA_KW_CAREER("Career", "क्यारियर"),
    DASHA_KW_STATUS("Status", "स्थिति"),
    DASHA_KW_GAINS("Gains", "लाभ"),
    DASHA_KW_FRIENDS("Friends", "मित्रहरू"),
    DASHA_KW_LOSSES("Losses", "हानि"),

    // ============================================
    // VARSHAPHALA - HOUSE PREDICTION PHRASES
    // ============================================
    VARSHA_LORD_EXCELLENT("is excellently placed for positive outcomes.", "सकारात्मक परिणामहरूको लागि उत्कृष्ट रूपमा स्थित छ।"),
    VARSHA_LORD_WELL_POSITIONED("is well-positioned for success.", "सफलताको लागि राम्ररी स्थित छ।"),
    VARSHA_LORD_MODERATE_SUPPORT("provides moderate support.", "मध्यम समर्थन प्रदान गर्दछ।"),
    VARSHA_LORD_CHALLENGES("faces challenges requiring attention.", "ध्यान चाहिने चुनौतीहरूको सामना गर्दछ।"),
    VARSHA_LORD_VARIABLE("influences results variably.", "परिणामहरूलाई परिवर्तनशील रूपमा प्रभाव पार्छ।"),
    VARSHA_BENEFICS_ENHANCE(" %s enhance positive outcomes.", " %s ले सकारात्मक परिणामहरू बढाउँछ।"),
    VARSHA_MALEFICS_CHALLENGE(" %s may bring challenges.", " %s ले चुनौतीहरू ल्याउन सक्छ।"),
    VARSHA_MIXED_PLANETS(" Mixed influences from %s.", " %s बाट मिश्रित प्रभावहरू।"),
    VARSHA_DEPENDS_LORD(" Results depend primarily on the lord's position.", " परिणामहरू मुख्यतया स्वामीको स्थितिमा निर्भर छन्।"),
    VARSHA_MUNTHA_EMPHASIS(" Muntha emphasizes these matters this year.", " मुन्थले यस वर्ष यी मामिलाहरूमा जोड दिन्छ।"),
    VARSHA_YEARLORD_RULES(" Year Lord rules this house - significant developments expected.", " वर्ष स्वामीले यो भाव शासन गर्छ - महत्त्वपूर्ण विकासहरू अपेक्षित।"),
    VARSHA_HOUSE_GOVERNS("House %d in %s governs %s.", "भाव %d %s मा %s शासन गर्दछ।"),
    VARSHA_LORD_IN_HOUSE("The lord %s in house %d ", "स्वामी %s भाव %d मा "),

    // ============================================
    // VARSHAPHALA - SPECIFIC EVENTS
    // ============================================
    VARSHA_EVENT_VITALITY("Increased vitality and personal confidence", "बढेको जीवनशक्ति र व्यक्तिगत आत्मविश्वास"),
    VARSHA_EVENT_NEW_VENTURES("Favorable for starting new ventures", "नयाँ उद्यमहरू सुरु गर्न अनुकूल"),
    VARSHA_EVENT_SPIRITUAL("Spiritual growth and wisdom", "आध्यात्मिक वृद्धि र बुद्धि"),
    VARSHA_EVENT_ACCIDENTS("Increased energy - watch for accidents", "बढेको ऊर्जा - दुर्घटनाबाट सावधान"),
    VARSHA_EVENT_FINANCIAL("Financial gains and wealth accumulation", "आर्थिक लाभ र धन संचय"),
    VARSHA_EVENT_FAMILY("Improvement in family relationships", "पारिवारिक सम्बन्धमा सुधार"),
    VARSHA_EVENT_LUXURY("Acquisition of luxury items", "विलासी वस्तुहरूको प्राप्ति"),
    VARSHA_EVENT_CREATIVE("Creative success and recognition", "सिर्जनात्मक सफलता र मान्यता"),
    VARSHA_EVENT_CHILDREN_MATTERS("Favorable for children's matters", "सन्तान सम्बन्धी मामिलाहरूको लागि अनुकूल"),
    VARSHA_EVENT_ACADEMIC("Academic success or childbirth possible", "शैक्षिक सफलता वा सन्तान जन्म सम्भव"),
    VARSHA_EVENT_ROMANTIC("Romantic happiness", "रोमान्टिक खुशी"),
    VARSHA_EVENT_PARTNERSHIPS("Strengthening of partnerships", "साझेदारीहरूको सुदृढीकरण"),
    VARSHA_EVENT_MARRIAGE_BUSINESS("Favorable for marriage or business", "विवाह वा व्यापारको लागि अनुकूल"),
    VARSHA_EVENT_ROMANTIC_FULFILL("Romantic fulfillment", "रोमान्टिक पूर्णता"),
    VARSHA_EVENT_CAREER_ADVANCE("Career advancement or promotion", "क्यारियर प्रगति वा पदोन्नति"),
    VARSHA_EVENT_RECOGNITION("Recognition from authorities", "अधिकारीहरूबाट मान्यता"),
    VARSHA_EVENT_GOVT_FAVOR("Government favor or leadership role", "सरकारी कृपा वा नेतृत्व भूमिका"),
    VARSHA_EVENT_DESIRES("Fulfillment of desires and wishes", "इच्छाहरू र कामनाहरूको पूर्ति"),
    VARSHA_EVENT_MULTIPLE_GAINS("Gains from multiple sources", "विभिन्न स्रोतहरूबाट लाभ"),

    // ============================================
    // VARSHAPHALA - DIGNITY DESCRIPTIONS
    // ============================================
    DIGNITY_EXALTED("exalted in %s", "%s मा उच्च"),
    DIGNITY_OWN_SIGN("in its own sign of %s", "आफ्नै राशि %s मा"),
    DIGNITY_DEBILITATED("debilitated in %s", "%s मा नीच"),
    DIGNITY_FRIENDLY("in the friendly sign of %s", "%s मित्र राशिमा"),
    DIGNITY_NEUTRAL("in the neutral sign of %s", "%s तटस्थ राशिमा"),
    DIGNITY_ENEMY("in the enemy sign of %s", "%s शत्रु राशिमा"),
    DIGNITY_KENDRA("in an angular house (Kendra)", "केन्द्र भावमा"),
    DIGNITY_TRIKONA("in a trine house (Trikona)", "त्रिकोण भावमा"),
    DIGNITY_GAINS("in a house of gains", "लाभ भावमा"),
    DIGNITY_UPACHAYA("in an upachaya house", "उपचय भावमा"),
    DIGNITY_DUSTHANA("in a challenging house (Dusthana)", "दुःस्थान भावमा"),
    DIGNITY_RETROGRADE("and is retrograde", "र वक्री छ"),
    DIGNITY_YEARLORD_DESC("The Year Lord %s is %s. This suggests its influence will be potent and its results will manifest clearly throughout the year.", "वर्ष स्वामी %s %s छ। यसले संकेत गर्छ कि यसको प्रभाव शक्तिशाली हुनेछ र यसको परिणामहरू वर्षभर स्पष्ट रूपमा प्रकट हुनेछन्।"),

    // ============================================
    // VARSHAPHALA - OVERALL PREDICTION TEMPLATE
    // ============================================
    VARSHA_OVERALL_TEMPLATE("This Varshaphala year presents an overall %s outlook. %s %s The Tajika aspects show %d favorable and %d challenging configurations. By understanding these influences, the year's potential can be maximized.", "यो वर्षफल वर्षले समग्र %s दृष्टिकोण प्रस्तुत गर्दछ। %s %s ताजिक पक्षहरूले %d अनुकूल र %d चुनौतीपूर्ण विन्यासहरू देखाउँछन्। यी प्रभावहरूलाई बुझेर, वर्षको सम्भावनालाई अधिकतम बनाउन सकिन्छ।"),
    VARSHA_MUNTHA_DIRECTS("Muntha in House %d (%s) directs attention to %s.", "भाव %d (%s) मा मुन्थले %s मा ध्यान केन्द्रित गर्दछ।"),

    // ============================================
    // VARSHAPHALA - KEY DATES
    // ============================================
    KEY_DATE_SOLAR_RETURN("Solar Return", "सौर प्रतिफल"),
    KEY_DATE_SOLAR_RETURN_DESC("Beginning of the annual horoscope year", "वार्षिक राशिफल वर्षको शुरुवात"),
    KEY_DATE_DASHA_BEGINS("%s Dasha Begins", "%s दशा शुरु"),
    KEY_DATE_DASHA_DESC("Start of %s period (%d days)", "%s अवधिको शुरुवात (%d दिन)"),

    // ============================================
    // VARSHAPHALA - HOUSE STRENGTH
    // ============================================
    HOUSE_STRENGTH_EXCELLENT("Excellent", "उत्कृष्ट"),
    HOUSE_STRENGTH_STRONG("Strong", "बलियो"),
    HOUSE_STRENGTH_MODERATE("Moderate", "मध्यम"),
    HOUSE_STRENGTH_WEAK("Weak", "कमजोर"),
    HOUSE_STRENGTH_CHALLENGED("Challenged", "चुनौतीपूर्ण"),

    // ============================================
    // VARSHAPHALA - ASPECT EFFECT DESCRIPTIONS
    // ============================================
    ASPECT_ITHASALA_EFFECT("%s applying to %s promises fulfillment", "%s %s मा निकटवर्ती हुँदा पूर्तिको वाचा"),
    ASPECT_EASARAPHA_EFFECT("Separating aspect suggests matters are concluding", "विलग पक्षले मामिलाहरू समाप्त हुँदैछन् भनी सुझाव दिन्छ"),
    ASPECT_KAMBOOLA_EFFECT("Powerful angular conjunction promises prominent success", "शक्तिशाली केन्द्रीय युतिले प्रमुख सफलताको वाचा गर्छ"),
    ASPECT_RADDA_EFFECT("Retrograde motion causes delays or reversals", "वक्री गतिले ढिलाइ वा उल्टो परिणाम ल्याउँछ"),
    ASPECT_DURAPHA_EFFECT("Hard aspect creates challenges that strengthen through difficulty", "कडा पक्षले कठिनाइद्वारा बलियो बनाउने चुनौतीहरू सिर्जना गर्छ"),
    ASPECT_GENERIC_EFFECT("%s influences matters with %s energy", "%s ले %s ऊर्जासहित मामिलाहरूलाई प्रभाव पार्छ"),
    ASPECT_SUPPORTIVE("supportive", "समर्थनात्मक"),
    ASPECT_PREDICTION_TEMPLATE("The %s between %s and %s is %s for matters of %s.", "%s र %s बीचको %s %s को मामिलाहरूको लागि %s छ।"),
    ASPECT_FAVORABLE_FOR("favorable", "अनुकूल"),
    ASPECT_REQUIRING_ATTENTION("requiring attention", "ध्यान चाहिने"),

    // ============================================
    // VARSHAPHALA - SAHAM INTERPRETATION
    // ============================================
    SAHAM_INTERPRETATION_TEMPLATE("The %s Saham in %s (House %d) relates to %s this year. Its lord %s in House %d is %s.", "%s सहम %s मा (भाव %d) यस वर्ष %s सँग सम्बन्धित छ। यसको स्वामी %s भाव %d मा %s छ।"),
    SAHAM_LORD_WELL_PLACED("well-placed, promising positive outcomes", "राम्ररी स्थित, सकारात्मक परिणामहरूको वाचा"),
    SAHAM_LORD_REASONABLE("providing reasonable support", "उचित समर्थन प्रदान गर्दै"),
    SAHAM_LORD_ATTENTION("requiring attention and effort", "ध्यान र प्रयास चाहिने"),
    SAHAM_LORD_VARIABLE("influencing matters variably", "मामिलाहरूलाई परिवर्तनशील रूपमा प्रभाव पार्दै"),
    SAHAM_INDICATES("indicates specific life areas", "विशिष्ट जीवन क्षेत्रहरू संकेत गर्दछ"),

    // ============================================
    // VARSHAPHALA - REPORT SECTIONS
    // ============================================
    VARSHA_REPORT_TITLE("VARSHAPHALA (ANNUAL HOROSCOPE) REPORT", "वर्षफल (वार्षिक राशिफल) रिपोर्ट"),
    VARSHA_REPORT_NAME("Name: %s", "नाम: %s"),
    VARSHA_REPORT_YEAR("Year: %d (Age: %d)", "वर्ष: %d (उमेर: %d)"),
    VARSHA_REPORT_SOLAR_RETURN("Solar Return: %s", "सौर प्रतिफल: %s"),
    VARSHA_REPORT_YEAR_RATING("Year Rating: %s/5.0", "वर्ष मूल्याङ्कन: %s/५.०"),
    VARSHA_REPORT_SECTION_YEARLORD("YEAR LORD", "वर्ष स्वामी"),
    VARSHA_REPORT_YEARLORD_LINE("Year Lord: %s (%s)", "वर्ष स्वामी: %s (%s)"),
    VARSHA_REPORT_POSITION("Position: House %d", "स्थिति: भाव %d"),
    VARSHA_REPORT_SECTION_MUNTHA("MUNTHA", "मुन्थ"),
    VARSHA_REPORT_MUNTHA_POSITION("Muntha Position: %s° %s", "मुन्थ स्थिति: %s° %s"),
    VARSHA_REPORT_MUNTHA_HOUSE("Muntha House: %d", "मुन्थ भाव: %d"),
    VARSHA_REPORT_MUNTHA_LORD("Muntha Lord: %s in House %d", "मुन्थ स्वामी: %s भाव %d मा"),
    VARSHA_REPORT_SECTION_THEMES("MAJOR THEMES", "मुख्य विषयहरू"),
    VARSHA_REPORT_SECTION_MUDDA("MUDDA DASHA PERIODS", "मुद्द दशा अवधिहरू"),
    VARSHA_REPORT_DASHA_LINE("%s: %s to %s (%d days)%s", "%s: %s देखि %s (%d दिन)%s"),
    VARSHA_REPORT_CURRENT_MARKER(" [CURRENT]", " [वर्तमान]"),
    VARSHA_REPORT_FAVORABLE_MONTHS("FAVORABLE MONTHS: %s", "अनुकूल महिनाहरू: %s"),
    VARSHA_REPORT_CHALLENGING_MONTHS("CHALLENGING MONTHS: %s", "चुनौतीपूर्ण महिनाहरू: %s"),
    VARSHA_REPORT_SECTION_PREDICTION("OVERALL PREDICTION", "समग्र भविष्यवाणी"),
    VARSHA_REPORT_FOOTER("Generated by AstroStorm - Ultra-Precision Vedic Astrology", "AstroStorm द्वारा उत्पन्न - अति-सटीक वैदिक ज्योतिष"),

    // ============================================
    // VARSHAPHALA - MONTH NAMES (SHORT)
    // ============================================
    MONTH_JAN("Jan", "जनवरी"),
    MONTH_FEB("Feb", "फेब्रुअरी"),
    MONTH_MAR("Mar", "मार्च"),
    MONTH_APR("Apr", "अप्रिल"),
    MONTH_MAY("May", "मे"),
    MONTH_JUN("Jun", "जुन"),
    MONTH_JUL("Jul", "जुलाई"),
    MONTH_AUG("Aug", "अगस्ट"),
    MONTH_SEP("Sep", "सेप्टेम्बर"),
    MONTH_OCT("Oct", "अक्टोबर"),
    MONTH_NOV("Nov", "नोभेम्बर"),
    MONTH_DEC("Dec", "डिसेम्बर"),

    // ============================================
    // VARSHAPHALA - ORDINAL SUFFIXES
    // ============================================
    ORDINAL_ST("st", "औं"),
    ORDINAL_ND("nd", "औं"),
    ORDINAL_RD("rd", "औं"),
    ORDINAL_TH("th", "औं"),

    // ============================================
    // VARSHAPHALA - MUNTHA INTERPRETATION TEMPLATE
    // ============================================
    MUNTHA_INTERPRETATION_TEMPLATE("Muntha in %s in the %d%s house focuses the year's energy on %s. The Muntha lord %s in house %d provides %s support for these matters.", "%d%s भावमा %s मा मुन्थले वर्षको ऊर्जालाई %s मा केन्द्रित गर्दछ। मुन्थ स्वामी %s भाव %d मा यी मामिलाहरूको लागि %s समर्थन प्रदान गर्दछ।"),
    MUNTHA_SUPPORT_EXCELLENT("excellent", "उत्कृष्ट"),
    MUNTHA_SUPPORT_FAVORABLE("favorable", "अनुकूल"),
    MUNTHA_SUPPORT_CHALLENGING("challenging but growth-oriented", "चुनौतीपूर्ण तर विकासोन्मुख"),
    MUNTHA_SUPPORT_VARIABLE("variable", "परिवर्तनशील"),

    // ============================================
    // DASHA MAHADASHA INTERPRETATIONS
    // ============================================
    DASHA_INTERP_MAHADASHA_SUN(
        "A period of heightened self-expression, authority, and recognition. Focus turns to career advancement, leadership roles, government dealings, and matters related to father. Soul purpose becomes clearer. Health of heart and vitality gains prominence. Good for developing confidence and establishing one's identity in the world.",
        "आत्म-अभिव्यक्ति, अधिकार र मान्यताको उच्च अवधि। क्यारियर प्रगति, नेतृत्व भूमिका, सरकारी व्यवहार र बुबासँग सम्बन्धित मामिलाहरूमा ध्यान केन्द्रित हुन्छ। आत्माको उद्देश्य स्पष्ट हुन्छ। हृदय र जीवनशक्तिको स्वास्थ्यले प्रमुखता पाउँछ। आत्मविश्वास विकास गर्न र संसारमा आफ्नो पहिचान स्थापित गर्न राम्रो।"
    ),
    DASHA_INTERP_MAHADASHA_MOON(
        "An emotionally rich and intuitive period emphasizing mental peace, nurturing, and receptivity. Focus on mother, home life, public image, travel across water, and emotional well-being. Creativity and imagination flourish. Memory and connection to the past strengthen. Relationships with women and the public become significant.",
        "भावनात्मक रूपले समृद्ध र अन्तर्ज्ञानात्मक अवधि जसले मानसिक शान्ति, पालनपोषण र ग्रहणशीलतामा जोड दिन्छ। आमा, घरेलु जीवन, सार्वजनिक छवि, पानी पार यात्रा र भावनात्मक कल्याणमा ध्यान केन्द्रित। सिर्जनशीलता र कल्पना फस्टाउँछ। स्मृति र भूतकालसँगको सम्बन्ध बलियो हुन्छ। महिलाहरू र जनतासँगको सम्बन्ध महत्त्वपूर्ण हुन्छ।"
    ),
    DASHA_INTERP_MAHADASHA_MARS(
        "A period of heightened energy, courage, initiative, and competitive drive. Focus on property matters, real estate, siblings, technical and engineering pursuits, sports, and surgery. Decisive action is favored. Physical vitality increases. Good for tackling challenges requiring strength and determination.",
        "ऊर्जा, साहस, पहल र प्रतिस्पर्धात्मक उत्प्रेरणाको उच्च अवधि। सम्पत्ति मामिला, घरजग्गा, भाइबहिनी, प्राविधिक र इन्जिनियरिङ, खेलकुद र शल्यक्रियामा ध्यान केन्द्रित। निर्णायक कार्यलाई प्राथमिकता दिइन्छ। शारीरिक जीवनशक्ति बढ्छ। बल र दृढ संकल्प चाहिने चुनौतीहरू सामना गर्न राम्रो।"
    ),
    DASHA_INTERP_MAHADASHA_MERCURY(
        "A period of enhanced learning, communication, analytical thinking, and commerce. Focus on education, writing, publishing, accounting, trade, and intellectual pursuits. Social connections expand through skillful communication. Good for developing skills, starting businesses, and mastering information.",
        "सिकाइ, सञ्चार, विश्लेषणात्मक सोच र वाणिज्यको उन्नत अवधि। शिक्षा, लेखन, प्रकाशन, लेखा, व्यापार र बौद्धिक खोजमा ध्यान केन्द्रित। दक्ष सञ्चार मार्फत सामाजिक सम्बन्ध विस्तार हुन्छ। सीप विकास, व्यवसाय सुरु गर्न र जानकारीमा दक्षता हासिल गर्न राम्रो।"
    ),
    DASHA_INTERP_MAHADASHA_JUPITER(
        "A period of wisdom, expansion, prosperity, and divine grace (Guru's blessings). Focus on spirituality, higher learning, teaching, children, law, and philosophical pursuits. Fortune favors righteous endeavors. Faith and optimism increase. Excellent for marriage, progeny, and spiritual advancement.",
        "बुद्धि, विस्तार, समृद्धि र दैवी कृपाको अवधि (गुरुको आशीर्वाद)। आध्यात्मिकता, उच्च शिक्षा, शिक्षण, सन्तान, कानुन र दार्शनिक खोजमा ध्यान केन्द्रित। भाग्यले धार्मिक प्रयासहरूलाई साथ दिन्छ। विश्वास र आशावाद बढ्छ। विवाह, सन्तान र आध्यात्मिक प्रगतिको लागि उत्कृष्ट।"
    ),
    DASHA_INTERP_MAHADASHA_VENUS(
        "A period of luxury, beauty, relationships, artistic expression, and material comforts. Focus on marriage, partnerships, arts, music, dance, vehicles, jewelry, and sensory pleasures. Creativity and romance blossom. Refinement in all areas of life. Good for enhancing beauty, wealth, and experiencing life's pleasures.",
        "विलासिता, सौन्दर्य, सम्बन्ध, कलात्मक अभिव्यक्ति र भौतिक सुविधाहरूको अवधि। विवाह, साझेदारी, कला, सङ्गीत, नृत्य, सवारीसाधन, गहना र इन्द्रिय आनन्दमा ध्यान केन्द्रित। सिर्जनशीलता र रोमान्स फस्टाउँछ। जीवनका सबै क्षेत्रमा परिष्करण। सौन्दर्य, सम्पत्ति बढाउन र जीवनका आनन्दहरू अनुभव गर्न राम्रो।"
    ),
    DASHA_INTERP_MAHADASHA_SATURN(
        "A period of discipline, karmic lessons, perseverance, and structural growth. Focus on service, responsibility, hard work, long-term projects, and lessons through patience. Delays and obstacles ultimately lead to lasting success and maturity. Time to build solid foundations and pay karmic debts.",
        "अनुशासन, कार्मिक पाठ, दृढता र संरचनात्मक विकासको अवधि। सेवा, जिम्मेवारी, कठिन परिश्रम, दीर्घकालीन परियोजना र धैर्य मार्फत पाठहरूमा ध्यान केन्द्रित। ढिलाइ र बाधाहरूले अन्ततः स्थायी सफलता र परिपक्वता ल्याउँछ। ठोस आधार निर्माण गर्ने र कार्मिक ऋण चुक्ता गर्ने समय।"
    ),
    DASHA_INTERP_MAHADASHA_RAHU(
        "A period of intense worldly ambition, unconventional paths, and material desires. Focus on foreign connections, technology, innovation, and breaking traditional boundaries. Sudden opportunities and unexpected changes arise. Material gains through unusual or non-traditional means. Beware of illusions.",
        "तीव्र सांसारिक महत्त्वाकांक्षा, अपरम्परागत मार्ग र भौतिक इच्छाको अवधि। विदेशी सम्बन्ध, प्रविधि, नवीनता र परम्परागत सीमाहरू तोड्नमा ध्यान केन्द्रित। अचानक अवसरहरू र अप्रत्याशित परिवर्तनहरू आउँछन्। असामान्य वा गैर-परम्परागत माध्यमबाट भौतिक लाभ। भ्रमबाट सावधान रहनुहोस्।"
    ),
    DASHA_INTERP_MAHADASHA_KETU(
        "A period of spirituality, detachment, and profound inner transformation. Focus on liberation (moksha), occult research, healing practices, and resolving past-life karma. Deep introspection yields spiritual insights. Material attachments may dissolve. Excellent for meditation, research, and spiritual practices.",
        "आध्यात्मिकता, वैराग्य र गहिरो आन्तरिक रूपान्तरणको अवधि। मुक्ति (मोक्ष), तान्त्रिक अनुसन्धान, उपचार अभ्यास र पूर्वजन्मको कर्म समाधानमा ध्यान केन्द्रित। गहिरो आत्मनिरीक्षणले आध्यात्मिक अन्तर्दृष्टि दिन्छ। भौतिक आसक्तिहरू विलीन हुन सक्छन्। ध्यान, अनुसन्धान र आध्यात्मिक अभ्यासको लागि उत्कृष्ट।"
    ),
    DASHA_INTERP_MAHADASHA_DEFAULT(
        "A period of transformation and karmic unfolding according to planetary influences.",
        "ग्रहीय प्रभाव अनुसार रूपान्तरण र कार्मिक विकासको अवधि।"
    ),

    // ============================================
    // DASHA ANTARDASHA INTERPRETATIONS
    // ============================================
    DASHA_INTERP_ANTARDASHA_SUN(
        "Current sub-period (Bhukti) activates themes of authority, self-confidence, recognition, and dealings with father figures or government. Leadership opportunities may arise.",
        "हालको उप-अवधि (भुक्ति) ले अधिकार, आत्मविश्वास, मान्यता र बुबाका व्यक्ति वा सरकारसँगको व्यवहारका विषयहरू सक्रिय गर्छ। नेतृत्व अवसरहरू आउन सक्छन्।"
    ),
    DASHA_INTERP_ANTARDASHA_MOON(
        "Current sub-period emphasizes emotional matters, mental peace, mother, public image, domestic affairs, and connection with women. Intuition heightens.",
        "हालको उप-अवधिले भावनात्मक मामिला, मानसिक शान्ति, आमा, सार्वजनिक छवि, घरेलु मामिला र महिलाहरूसँगको सम्बन्धमा जोड दिन्छ। अन्तर्ज्ञान तीव्र हुन्छ।"
    ),
    DASHA_INTERP_ANTARDASHA_MARS(
        "Current sub-period brings increased energy, drive for action, courage, and matters involving property, siblings, competition, or technical endeavors.",
        "हालको उप-अवधिले बढेको ऊर्जा, कार्यको लागि उत्प्रेरणा, साहस र सम्पत्ति, भाइबहिनी, प्रतिस्पर्धा वा प्राविधिक प्रयासका मामिलाहरू ल्याउँछ।"
    ),
    DASHA_INTERP_ANTARDASHA_MERCURY(
        "Current sub-period emphasizes communication, learning, business transactions, intellectual activities, and connections with younger people or merchants.",
        "हालको उप-अवधिले सञ्चार, सिकाइ, व्यापारिक लेनदेन, बौद्धिक गतिविधिहरू र साना मानिसहरू वा व्यापारीहरूसँगको सम्बन्धमा जोड दिन्छ।"
    ),
    DASHA_INTERP_ANTARDASHA_JUPITER(
        "Current sub-period brings wisdom, expansion, good fortune, and focus on spirituality, teachers, children, higher education, or legal matters.",
        "हालको उप-अवधिले बुद्धि, विस्तार, सुभाग्य र आध्यात्मिकता, शिक्षकहरू, सन्तान, उच्च शिक्षा वा कानुनी मामिलाहरूमा ध्यान केन्द्रित गर्छ।"
    ),
    DASHA_INTERP_ANTARDASHA_VENUS(
        "Current sub-period emphasizes relationships, romance, creativity, luxury, artistic pursuits, material comforts, and partnership matters.",
        "हालको उप-अवधिले सम्बन्ध, रोमान्स, सिर्जनशीलता, विलासिता, कलात्मक खोज, भौतिक सुविधा र साझेदारी मामिलाहरूमा जोड दिन्छ।"
    ),
    DASHA_INTERP_ANTARDASHA_SATURN(
        "Current sub-period brings discipline, responsibility, hard work, delays, and lessons requiring patience. Focus on service and long-term efforts.",
        "हालको उप-अवधिले अनुशासन, जिम्मेवारी, कठिन परिश्रम, ढिलाइ र धैर्य चाहिने पाठहरू ल्याउँछ। सेवा र दीर्घकालीन प्रयासमा ध्यान केन्द्रित।"
    ),
    DASHA_INTERP_ANTARDASHA_RAHU(
        "Current sub-period emphasizes worldly ambitions, unconventional approaches, foreign matters, technology, and sudden changes or opportunities.",
        "हालको उप-अवधिले सांसारिक महत्त्वाकांक्षा, अपरम्परागत दृष्टिकोण, विदेशी मामिला, प्रविधि र अचानक परिवर्तन वा अवसरहरूमा जोड दिन्छ।"
    ),
    DASHA_INTERP_ANTARDASHA_KETU(
        "Current sub-period brings spiritual insights, detachment, introspection, research, and resolution of past karmic patterns. Material concerns recede.",
        "हालको उप-अवधिले आध्यात्मिक अन्तर्दृष्टि, वैराग्य, आत्मनिरीक्षण, अनुसन्धान र पूर्व कार्मिक ढाँचाको समाधान ल्याउँछ। भौतिक चिन्ताहरू पछाडि हट्छन्।"
    ),
    DASHA_INTERP_ANTARDASHA_DEFAULT(
        "Current sub-period brings mixed planetary influences requiring careful navigation.",
        "हालको उप-अवधिले सावधानीपूर्वक मार्गदर्शन चाहिने मिश्रित ग्रहीय प्रभावहरू ल्याउँछ।"
    ),

    // ============================================
    // CHART ANALYSIS SCREEN - TABS & UI
    // ============================================
    ANALYSIS_CHART_ANALYSIS("Chart Analysis", "कुण्डली विश्लेषण"),
    ANALYSIS_TAB_CHART("Chart", "कुण्डली"),
    ANALYSIS_TAB_PLANETS("Planets", "ग्रहहरू"),
    ANALYSIS_TAB_YOGAS("Yogas", "योगहरू"),
    ANALYSIS_TAB_DASHAS("Dashas", "दशाहरू"),
    ANALYSIS_TAB_TRANSITS("Transits", "गोचरहरू"),
    ANALYSIS_TAB_ASHTAKAVARGA("Ashtakavarga", "अष्टकवर्ग"),
    ANALYSIS_TAB_PANCHANGA("Panchanga", "पञ्चाङ्ग"),

    // ============================================
    // DIVISIONAL CHARTS - NAMES & DESCRIPTIONS
    // ============================================
    VARGA_D1_NAME("Lagna Chart (Rashi)", "लग्न कुण्डली (राशि)"),
    VARGA_D2_NAME("Hora Chart", "होरा कुण्डली"),
    VARGA_D3_NAME("Drekkana Chart", "द्रेक्काण कुण्डली"),
    VARGA_D4_NAME("Chaturthamsa Chart", "चतुर्थांश कुण्डली"),
    VARGA_D7_NAME("Saptamsa Chart", "सप्तांश कुण्डली"),
    VARGA_D9_NAME("Navamsa Chart", "नवांश कुण्डली"),
    VARGA_D10_NAME("Dasamsa Chart", "दशांश कुण्डली"),
    VARGA_D12_NAME("Dwadasamsa Chart", "द्वादशांश कुण्डली"),
    VARGA_D16_NAME("Shodasamsa Chart", "षोडशांश कुण्डली"),
    VARGA_D20_NAME("Vimsamsa Chart", "विंशांश कुण्डली"),
    VARGA_D24_NAME("Siddhamsa Chart", "चतुर्विंशांश कुण्डली"),
    VARGA_D27_NAME("Bhamsa Chart", "सप्तविंशांश कुण्डली"),
    VARGA_D30_NAME("Trimsamsa Chart", "त्रिंशांश कुण्डली"),
    VARGA_D60_NAME("Shashtiamsa Chart", "षष्टिांश कुण्डली"),

    VARGA_D3_DESC_FULL("Siblings, Courage, Vitality", "भाइबहिनी, साहस, जीवनशक्ति"),
    VARGA_D9_DESC_FULL("Marriage, Dharma, Fortune", "विवाह, धर्म, भाग्य"),
    VARGA_D10_DESC_FULL("Career, Profession", "क्यारियर, पेशा"),
    VARGA_D12_DESC_FULL("Parents, Ancestry", "आमाबुबा, पुर्खा"),
    VARGA_D16_DESC_FULL("Vehicles, Pleasures", "सवारी, आनन्द"),
    VARGA_D20_DESC_FULL("Spiritual Life", "आध्यात्मिक जीवन"),
    VARGA_D24_DESC_FULL("Education, Learning", "शिक्षा, सिकाइ"),
    VARGA_D27_DESC_FULL("Strength, Weakness", "बल, कमजोरी"),
    VARGA_D30_DESC_FULL("Evils, Misfortunes", "दुर्भाग्य, विपत्ति"),
    VARGA_D60_DESC_FULL("Past Life Karma", "पूर्वजन्मको कर्म"),

    // Divisional chart selector labels
    VARGA_LAGNA("Lagna", "लग्न"),
    VARGA_HORA("Hora", "होरा"),
    VARGA_DREKKANA("Drekkana", "द्रेक्काण"),
    VARGA_SAPTAMSA("Saptamsa", "सप्तांश"),
    VARGA_NAVAMSA("Navamsa", "नवांश"),
    VARGA_DASAMSA("Dasamsa", "दशांश"),
    VARGA_BHAMSA("Bhamsa", "भांश"),

    // ============================================
    // PLANETS TAB - HARDCODED STRINGS
    // ============================================
    PLANETS_CONDITIONS("Planetary Conditions", "ग्रह अवस्थाहरू"),
    PLANETS_RETROGRADE("Retrograde", "वक्री"),
    PLANETS_COMBUST("Combust", "अस्त"),
    PLANETS_AT_WAR("At War", "युद्धमा"),
    PLANETS_PLANETARY_WAR("Planetary War", "ग्रहयुद्ध"),
    PLANETS_SHADBALA_SUMMARY("Shadbala Summary", "षड्बल सारांश"),
    PLANETS_OVERALL("Overall", "समग्र"),
    PLANETS_VIEW_DETAILS("View Details", "विवरण हेर्नुहोस्"),
    PLANETS_TAP_FOR_DETAILS("Tap for details", "विवरणको लागि ट्याप गर्नुहोस्"),
    PLANETS_SHADBALA("Shadbala", "षड्बल"),
    PLANETS_RUPAS("%s / %s rupas (%s%%)", "%s / %s रुपा (%s%%)"),
    PLANETS_HOUSE_FORMAT("House %d", "भाव %d"),

    // Dignity status
    DIGNITY_EXALTED_STATUS("Exalted", "उच्च"),
    DIGNITY_DEBILITATED_STATUS("Debilitated", "नीच"),
    DIGNITY_OWN_SIGN_STATUS("Own Sign", "स्वराशि"),
    DIGNITY_NEUTRAL_STATUS("Neutral", "तटस्थ"),

    // ============================================
    // ASHTAKAVARGA TAB - HARDCODED STRINGS
    // ============================================
    ASHTAK_SUMMARY("Ashtakavarga Summary", "अष्टकवर्ग सारांश"),
    ASHTAK_TOTAL_SAV("Total SAV", "कुल SAV"),
    ASHTAK_STRONGEST("Strongest", "सबैभन्दा बलियो"),
    ASHTAK_WEAKEST("Weakest", "सबैभन्दा कमजोर"),
    ASHTAK_QUICK_ANALYSIS("Quick Analysis", "द्रुत विश्लेषण"),
    ASHTAK_FAVORABLE_SIGNS("Favorable Signs (28+):", "अनुकूल राशिहरू (२८+):"),
    ASHTAK_CHALLENGING_SIGNS("Challenging Signs (<25):", "चुनौतीपूर्ण राशिहरू (<२५):"),
    ASHTAK_SIGNS_COUNT("%d signs", "%d राशिहरू"),

    // Sarvashtakavarga
    ASHTAK_SAV_TITLE("Sarvashtakavarga (SAV)", "सर्वाष्टकवर्ग (SAV)"),
    ASHTAK_SAV_COMBINED_DESC("Combined strength of all planets in each sign", "प्रत्येक राशिमा सबै ग्रहहरूको संयुक्त बल"),

    // Bhinnashtakavarga
    ASHTAK_BAV_TITLE("Bhinnashtakavarga (BAV)", "भिन्नाष्टकवर्ग (BAV)"),
    ASHTAK_BAV_INDIVIDUAL_DESC("Individual planet strength in each sign (0-8 bindus)", "प्रत्येक राशिमा व्यक्तिगत ग्रहको बल (०-८ बिन्दु)"),
    ASHTAK_TOTAL("Total", "कुल"),

    // SAV Legend
    ASHTAK_SAV_EXCELLENT("30+ (Excellent)", "३०+ (उत्कृष्ट)"),
    ASHTAK_SAV_GOOD("28-29 (Good)", "२८-२९ (राम्रो)"),
    ASHTAK_SAV_AVERAGE("25-27 (Average)", "२५-२७ (औसत)"),
    ASHTAK_SAV_WEAK("<25 (Weak)", "<२५ (कमजोर)"),

    // BAV Legend
    ASHTAK_BAV_STRONG("5+ (Strong)", "५+ (बलियो)"),
    ASHTAK_BAV_GOOD("4 (Good)", "४ (राम्रो)"),
    ASHTAK_BAV_AVERAGE("3 (Average)", "३ (औसत)"),
    ASHTAK_BAV_WEAK("0-2 (Weak)", "०-२ (कमजोर)"),

    // Interpretation Guide
    ASHTAK_GUIDE_TITLE("Interpretation Guide", "व्याख्या गाइड"),
    ASHTAK_GUIDE_SAV_TITLE("Sarvashtakavarga (SAV)", "सर्वाष्टकवर्ग (SAV)"),
    ASHTAK_GUIDE_SAV_30("30+ bindus: Excellent for transits - major positive events", "३०+ बिन्दु: गोचरको लागि उत्कृष्ट - प्रमुख सकारात्मक घटनाहरू"),
    ASHTAK_GUIDE_SAV_28("28-29 bindus: Good for transits - favorable outcomes", "२८-२९ बिन्दु: गोचरको लागि राम्रो - अनुकूल परिणामहरू"),
    ASHTAK_GUIDE_SAV_25("25-27 bindus: Average - mixed results expected", "२५-२७ बिन्दु: औसत - मिश्रित परिणामहरू अपेक्षित"),
    ASHTAK_GUIDE_SAV_BELOW("Below 25: Challenging - caution during transits", "२५ भन्दा कम: चुनौतीपूर्ण - गोचरमा सावधानी"),
    ASHTAK_GUIDE_BAV_TITLE("Bhinnashtakavarga (BAV)", "भिन्नाष्टकवर्ग (BAV)"),
    ASHTAK_GUIDE_BAV_5("5+ bindus: Planet transit highly beneficial", "५+ बिन्दु: ग्रह गोचर अत्यधिक लाभदायक"),
    ASHTAK_GUIDE_BAV_4("4 bindus: Good results from transit", "४ बिन्दु: गोचरबाट राम्रो परिणाम"),
    ASHTAK_GUIDE_BAV_3("3 bindus: Average, neutral results", "३ बिन्दु: औसत, तटस्थ परिणामहरू"),
    ASHTAK_GUIDE_BAV_02("0-2 bindus: Difficult transit period", "०-२ बिन्दु: कठिन गोचर अवधि"),
    ASHTAK_GUIDE_TRANSIT_TITLE("Transit Application", "गोचर अनुप्रयोग"),
    ASHTAK_GUIDE_TRANSIT_1("Check SAV of the sign a planet transits", "ग्रहले गोचर गर्ने राशिको SAV जाँच गर्नुहोस्"),
    ASHTAK_GUIDE_TRANSIT_2("Check BAV score of that planet in transited sign", "गोचर गरिएको राशिमा त्यो ग्रहको BAV स्कोर जाँच गर्नुहोस्"),
    ASHTAK_GUIDE_TRANSIT_3("High combined scores = favorable transit", "उच्च संयुक्त स्कोर = अनुकूल गोचर"),
    ASHTAK_GUIDE_TRANSIT_4("Use for timing important decisions", "महत्त्वपूर्ण निर्णयहरूको समयको लागि प्रयोग गर्नुहोस्"),

    // ============================================
    // ASPECT TYPES (For AspectCalculator)
    // ============================================
    ASPECT_TYPE_CONJUNCTION("Conjunction", "युति"),
    ASPECT_TYPE_7TH("7th Aspect", "सप्तम दृष्टि"),
    ASPECT_TYPE_MARS_4TH("Mars 4th Aspect", "मंगलको चतुर्थ दृष्टि"),
    ASPECT_TYPE_MARS_8TH("Mars 8th Aspect", "मंगलको अष्टम दृष्टि"),
    ASPECT_TYPE_JUPITER_5TH("Jupiter 5th Aspect", "गुरुको पञ्चम दृष्टि"),
    ASPECT_TYPE_JUPITER_9TH("Jupiter 9th Aspect", "गुरुको नवम दृष्टि"),
    ASPECT_TYPE_SATURN_3RD("Saturn 3rd Aspect", "शनिको तृतीय दृष्टि"),
    ASPECT_TYPE_SATURN_10TH("Saturn 10th Aspect", "शनिको दशम दृष्टि"),

    // Aspect Strength Descriptions (Drishti Bala)
    ASPECT_STRENGTH_EXACT("Exact (Purna)", "पूर्ण (एकदम सटीक)"),
    ASPECT_STRENGTH_ADHIKA("Strong (Adhika)", "अधिक (बलियो)"),
    ASPECT_STRENGTH_MADHYA("Medium (Madhya)", "मध्यम"),
    ASPECT_STRENGTH_ALPA("Weak (Alpa)", "अल्प (कमजोर)"),
    ASPECT_STRENGTH_SUNYA("Negligible (Sunya)", "शून्य (नगण्य)"),

    // Aspect descriptions
    ASPECT_CASTS_ON("%s casts %s on %s", "%s ले %s मा %s दृष्टि राख्छ"),
    ASPECT_APPLYING("Applying", "समीप आउँदै"),
    ASPECT_SEPARATING("Separating", "टाढा हुँदै"),
    ASPECT_DRISHTI_BALA("Drishti Bala", "दृष्टि बल"),

    // ============================================
    // TRANSIT QUALITY (For AshtakavargaCalculator)
    // ============================================
    TRANSIT_QUALITY_EXCELLENT("Excellent", "उत्कृष्ट"),
    TRANSIT_QUALITY_GOOD("Good", "राम्रो"),
    TRANSIT_QUALITY_AVERAGE("Average", "औसत"),
    TRANSIT_QUALITY_BELOW_AVG("Below Average", "औसतभन्दा कम"),
    TRANSIT_QUALITY_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    TRANSIT_QUALITY_DIFFICULT("Difficult", "कठिन"),
    TRANSIT_QUALITY_UNKNOWN("Unknown", "अज्ञात"),

    // Transit interpretations
    TRANSIT_INTERP_EXCELLENT("Excellent - Highly favorable transit", "उत्कृष्ट - अत्यन्त अनुकूल गोचर"),
    TRANSIT_INTERP_GOOD("Good - Favorable results expected", "राम्रो - अनुकूल परिणामहरू अपेक्षित"),
    TRANSIT_INTERP_AVERAGE("Average - Mixed results", "औसत - मिश्रित परिणामहरू"),
    TRANSIT_INTERP_BELOW_AVG("Below Average - Some challenges", "औसतभन्दा कम - केही चुनौतीहरू"),
    TRANSIT_INTERP_DIFFICULT("Difficult - Careful navigation needed", "कठिन - सावधानीपूर्ण व्यवहार आवश्यक"),
    TRANSIT_ANALYSIS_NOT_AVAILABLE("Transit analysis not available for this planet.", "यस ग्रहको लागि गोचर विश्लेषण उपलब्ध छैन।"),

    // ============================================
    // ELEMENTS (Additional)
    // ============================================
    ELEMENT_ETHER("Ether", "आकाश"),

    // ============================================
    // PLANET SIGNIFICATIONS (For PlanetDetailDialog)
    // ============================================
    // Sun Significations
    PLANET_SUN_NATURE("Malefic", "पापी"),
    PLANET_SUN_ELEMENT("Fire", "अग्नि"),
    PLANET_SUN_REPRESENTS_1("Soul, Self, Ego", "आत्मा, स्वयं, अहंकार"),
    PLANET_SUN_REPRESENTS_2("Father, Authority Figures", "पिता, अधिकारीहरू"),
    PLANET_SUN_REPRESENTS_3("Government, Power", "सरकार, शक्ति"),
    PLANET_SUN_REPRESENTS_4("Health, Vitality", "स्वास्थ्य, जीवनशक्ति"),
    PLANET_SUN_REPRESENTS_5("Fame, Recognition", "प्रसिद्धि, मान्यता"),
    PLANET_SUN_BODY_PARTS("Heart, Spine, Right Eye, Bones", "हृदय, मेरुदण्ड, दाहिने आँखा, हड्डी"),
    PLANET_SUN_PROFESSIONS("Government jobs, Politics, Medicine, Administration, Leadership roles", "सरकारी जागिर, राजनीति, चिकित्सा, प्रशासन, नेतृत्व भूमिकाहरू"),

    // Moon Significations
    PLANET_MOON_NATURE("Benefic", "शुभ"),
    PLANET_MOON_ELEMENT("Water", "जल"),
    PLANET_MOON_REPRESENTS_1("Mind, Emotions", "मन, भावनाहरू"),
    PLANET_MOON_REPRESENTS_2("Mother, Nurturing", "आमा, पालनपोषण"),
    PLANET_MOON_REPRESENTS_3("Public, Masses", "जनता, समुदाय"),
    PLANET_MOON_REPRESENTS_4("Comforts, Happiness", "आराम, खुशी"),
    PLANET_MOON_REPRESENTS_5("Memory, Imagination", "स्मृति, कल्पना"),
    PLANET_MOON_BODY_PARTS("Mind, Left Eye, Breast, Blood, Fluids", "मन, बायाँ आँखा, स्तन, रगत, तरल पदार्थ"),
    PLANET_MOON_PROFESSIONS("Nursing, Hotel industry, Shipping, Agriculture, Psychology", "नर्सिङ, होटल उद्योग, जहाजरानी, कृषि, मनोविज्ञान"),

    // Mars Significations
    PLANET_MARS_NATURE("Malefic", "पापी"),
    PLANET_MARS_ELEMENT("Fire", "अग्नि"),
    PLANET_MARS_REPRESENTS_1("Energy, Action, Courage", "ऊर्जा, कार्य, साहस"),
    PLANET_MARS_REPRESENTS_2("Siblings, Younger Brothers", "भाइबहिनी, सानो भाइ"),
    PLANET_MARS_REPRESENTS_3("Property, Land", "सम्पत्ति, जमिन"),
    PLANET_MARS_REPRESENTS_4("Competition, Sports", "प्रतिस्पर्धा, खेलकुद"),
    PLANET_MARS_REPRESENTS_5("Technical Skills", "प्राविधिक सीपहरू"),
    PLANET_MARS_BODY_PARTS("Blood, Muscles, Marrow, Head injuries", "रगत, मांसपेशी, मज्जा, टाउकोमा चोटपटक"),
    PLANET_MARS_PROFESSIONS("Military, Police, Surgery, Engineering, Sports, Real Estate", "सेना, प्रहरी, शल्यक्रिया, इन्जिनियरिङ, खेलकुद, घरजग्गा"),

    // Mercury Significations
    PLANET_MERCURY_NATURE("Benefic", "शुभ"),
    PLANET_MERCURY_ELEMENT("Earth", "पृथ्वी"),
    PLANET_MERCURY_REPRESENTS_1("Intelligence, Communication", "बुद्धि, सञ्चार"),
    PLANET_MERCURY_REPRESENTS_2("Learning, Education", "सिकाइ, शिक्षा"),
    PLANET_MERCURY_REPRESENTS_3("Business, Trade", "व्यापार, व्यवसाय"),
    PLANET_MERCURY_REPRESENTS_4("Writing, Speech", "लेखन, वाणी"),
    PLANET_MERCURY_REPRESENTS_5("Siblings, Friends", "भाइबहिनी, साथीहरू"),
    PLANET_MERCURY_BODY_PARTS("Nervous system, Skin, Speech, Hands", "स्नायु प्रणाली, छाला, वाणी, हातहरू"),
    PLANET_MERCURY_PROFESSIONS("Writing, Teaching, Accounting, Trading, IT, Media", "लेखन, शिक्षण, लेखा, व्यापार, आईटी, मिडिया"),

    // Jupiter Significations
    PLANET_JUPITER_NATURE("Benefic", "शुभ"),
    PLANET_JUPITER_ELEMENT("Ether", "आकाश"),
    PLANET_JUPITER_REPRESENTS_1("Wisdom, Knowledge", "ज्ञान, विद्या"),
    PLANET_JUPITER_REPRESENTS_2("Teachers, Gurus", "शिक्षकहरू, गुरुहरू"),
    PLANET_JUPITER_REPRESENTS_3("Fortune, Luck", "भाग्य, किस्मत"),
    PLANET_JUPITER_REPRESENTS_4("Children, Dharma", "सन्तान, धर्म"),
    PLANET_JUPITER_REPRESENTS_5("Expansion, Growth", "विस्तार, वृद्धि"),
    PLANET_JUPITER_BODY_PARTS("Liver, Fat tissue, Ears, Thighs", "कलेजो, बोसो, कान, जाँघ"),
    PLANET_JUPITER_PROFESSIONS("Teaching, Law, Priesthood, Banking, Counseling", "शिक्षण, कानून, पुरोहित, बैंकिङ, परामर्श"),

    // Venus Significations
    PLANET_VENUS_NATURE("Benefic", "शुभ"),
    PLANET_VENUS_ELEMENT("Water", "जल"),
    PLANET_VENUS_REPRESENTS_1("Love, Beauty, Art", "प्रेम, सौन्दर्य, कला"),
    PLANET_VENUS_REPRESENTS_2("Marriage, Relationships", "विवाह, सम्बन्धहरू"),
    PLANET_VENUS_REPRESENTS_3("Luxuries, Comforts", "विलासिता, आराम"),
    PLANET_VENUS_REPRESENTS_4("Vehicles, Pleasures", "सवारी, आनन्द"),
    PLANET_VENUS_REPRESENTS_5("Creativity", "सिर्जनशीलता"),
    PLANET_VENUS_BODY_PARTS("Reproductive system, Face, Skin, Throat", "प्रजनन प्रणाली, अनुहार, छाला, घाँटी"),
    PLANET_VENUS_PROFESSIONS("Entertainment, Fashion, Art, Hospitality, Beauty industry", "मनोरञ्जन, फेसन, कला, आतिथ्य, सौन्दर्य उद्योग"),

    // Saturn Significations
    PLANET_SATURN_NATURE("Malefic", "पापी"),
    PLANET_SATURN_ELEMENT("Air", "वायु"),
    PLANET_SATURN_REPRESENTS_1("Discipline, Hard work", "अनुशासन, कडा परिश्रम"),
    PLANET_SATURN_REPRESENTS_2("Karma, Delays", "कर्म, ढिलाइ"),
    PLANET_SATURN_REPRESENTS_3("Longevity, Service", "दीर्घायु, सेवा"),
    PLANET_SATURN_REPRESENTS_4("Laborers, Servants", "श्रमिकहरू, सेवकहरू"),
    PLANET_SATURN_REPRESENTS_5("Chronic issues", "दीर्घकालीन समस्याहरू"),
    PLANET_SATURN_BODY_PARTS("Bones, Teeth, Knees, Joints, Nerves", "हड्डी, दाँत, घुँडा, जोर्नीहरू, स्नायु"),
    PLANET_SATURN_PROFESSIONS("Mining, Agriculture, Labor, Judiciary, Real Estate", "खनन, कृषि, श्रम, न्यायपालिका, घरजग्गा"),

    // Rahu Significations
    PLANET_RAHU_NATURE("Malefic", "पापी"),
    PLANET_RAHU_ELEMENT("Air", "वायु"),
    PLANET_RAHU_REPRESENTS_1("Obsession, Illusion", "जुनून, भ्रम"),
    PLANET_RAHU_REPRESENTS_2("Foreign lands, Travel", "विदेश, यात्रा"),
    PLANET_RAHU_REPRESENTS_3("Technology, Innovation", "प्रविधि, नवीनता"),
    PLANET_RAHU_REPRESENTS_4("Unconventional paths", "अपरंपरागत मार्गहरू"),
    PLANET_RAHU_REPRESENTS_5("Material desires", "भौतिक इच्छाहरू"),
    PLANET_RAHU_BODY_PARTS("Skin diseases, Nervous disorders", "छालाका रोगहरू, स्नायु विकारहरू"),
    PLANET_RAHU_PROFESSIONS("Technology, Foreign affairs, Aviation, Politics, Research", "प्रविधि, विदेशी मामिला, उड्डयन, राजनीति, अनुसन्धान"),

    // Ketu Significations
    PLANET_KETU_NATURE("Malefic", "पापी"),
    PLANET_KETU_ELEMENT("Fire", "अग्नि"),
    PLANET_KETU_REPRESENTS_1("Spirituality, Liberation", "आध्यात्मिकता, मोक्ष"),
    PLANET_KETU_REPRESENTS_2("Past life karma", "पूर्वजन्मको कर्म"),
    PLANET_KETU_REPRESENTS_3("Detachment, Isolation", "वैराग्य, एकान्त"),
    PLANET_KETU_REPRESENTS_4("Occult, Mysticism", "गुप्त विद्या, रहस्यवाद"),
    PLANET_KETU_REPRESENTS_5("Healing abilities", "उपचार क्षमता"),
    PLANET_KETU_BODY_PARTS("Skin, Spine, Nervous system", "छाला, मेरुदण्ड, स्नायु प्रणाली"),
    PLANET_KETU_PROFESSIONS("Spirituality, Research, Healing, Astrology, Philosophy", "आध्यात्मिकता, अनुसन्धान, उपचार, ज्योतिष, दर्शन"),

    // ============================================
    // CHART LEGEND LABELS (For ChartRenderer)
    // ============================================
    CHART_LEGEND_RETRO_SHORT("Retro", "वक्री"),
    CHART_LEGEND_COMBUST_SHORT("Comb", "अस्त"),
    CHART_LEGEND_VARGO_SHORT("Vargo", "वर्गो"),
    CHART_LEGEND_EXALT_SHORT("Exalt", "उच्च"),
    CHART_LEGEND_DEB_SHORT("Deb", "नीच"),
    CHART_LEGEND_OWN_SHORT("Own", "स्व"),
    CHART_ASC_ABBR("Asc", "ल"),

    // ============================================
    // ASHTAKAVARGA ANALYSIS HEADERS
    // ============================================
    ASHTAK_ANALYSIS_HEADER("ASHTAKAVARGA ANALYSIS", "अष्टकवर्ग विश्लेषण"),
    ASHTAK_SAV_HEADER("SARVASHTAKAVARGA (Combined Strength)", "सर्वाष्टकवर्ग (संयुक्त बल)"),
    ASHTAK_BAV_HEADER("BHINNASHTAKAVARGA (Individual Planet Strengths)", "भिन्नाष्टकवर्ग (व्यक्तिगत ग्रह बल)"),
    ASHTAK_TOTAL_SAV_BINDUS("Total SAV Bindus:", "कुल SAV बिन्दुहरू:"),
    ASHTAK_AVG_PER_SIGN("Average per Sign:", "प्रति राशि औसत:"),
    ASHTAK_NOT_APPLICABLE("Ashtakavarga not applicable for %s", "%s को लागि अष्टकवर्ग लागू हुँदैन"),

    // Ashtakavarga Planet Effects
    ASHTAK_SUN_EFFECTS("authority, father, health, government, career", "अधिकार, पिता, स्वास्थ्य, सरकार, क्यारियर"),
    ASHTAK_MOON_EFFECTS("mind, emotions, mother, public image", "मन, भावना, आमा, सार्वजनिक छवि"),
    ASHTAK_MARS_EFFECTS("energy, siblings, property, courage", "ऊर्जा, भाइबहिनी, सम्पत्ति, साहस"),
    ASHTAK_MERCURY_EFFECTS("communication, intellect, business, education", "सञ्चार, बुद्धि, व्यापार, शिक्षा"),
    ASHTAK_JUPITER_EFFECTS("wisdom, children, fortune, spirituality", "ज्ञान, सन्तान, भाग्य, आध्यात्मिकता"),
    ASHTAK_VENUS_EFFECTS("relationships, luxury, arts, vehicles", "सम्बन्ध, विलासिता, कला, सवारी"),
    ASHTAK_SATURN_EFFECTS("career, longevity, discipline, challenges", "क्यारियर, दीर्घायु, अनुशासन, चुनौतीहरू"),
    ASHTAK_GENERAL_EFFECTS("general matters", "सामान्य मामिलाहरू"),

    // House matters for transit interpretation
    ASHTAK_HOUSE_1_MATTERS("self, personality, health", "आफू, व्यक्तित्व, स्वास्थ्य"),
    ASHTAK_HOUSE_2_MATTERS("wealth, family, speech", "धन, परिवार, वाणी"),
    ASHTAK_HOUSE_3_MATTERS("courage, siblings, communication", "साहस, भाइबहिनी, सञ्चार"),
    ASHTAK_HOUSE_4_MATTERS("home, mother, comfort", "घर, आमा, आराम"),
    ASHTAK_HOUSE_5_MATTERS("children, intelligence, romance", "सन्तान, बुद्धि, प्रेम"),
    ASHTAK_HOUSE_6_MATTERS("enemies, health issues, service", "शत्रु, स्वास्थ्य समस्या, सेवा"),
    ASHTAK_HOUSE_7_MATTERS("partnership, marriage, business", "साझेदारी, विवाह, व्यापार"),
    ASHTAK_HOUSE_8_MATTERS("transformation, inheritance, occult", "रूपान्तरण, सम्पत्ति, गुप्त विद्या"),
    ASHTAK_HOUSE_9_MATTERS("luck, father, higher learning", "भाग्य, पिता, उच्च शिक्षा"),
    ASHTAK_HOUSE_10_MATTERS("career, status, authority", "क्यारियर, हैसियत, अधिकार"),
    ASHTAK_HOUSE_11_MATTERS("gains, friends, aspirations", "लाभ, साथीहरू, आकांक्षाहरू"),
    ASHTAK_HOUSE_12_MATTERS("losses, spirituality, foreign", "हानि, आध्यात्मिकता, विदेश"),

    // Transit interpretation templates
    TRANSIT_EXCELLENT_TEMPLATE("Transit through %s with %d BAV bindus and %d SAV bindus indicates excellent results. Matters related to %s will flourish. Areas of %s receive strong positive influence.", "%s मा %d BAV बिन्दु र %d SAV बिन्दुसँगको गोचरले उत्कृष्ट परिणाम देखाउँछ। %s सम्बन्धी मामिलाहरू फस्टाउनेछन्। %s को क्षेत्रमा बलियो सकारात्मक प्रभाव पर्नेछ।"),
    TRANSIT_GOOD_TEMPLATE("Transit through %s brings favorable results with %d BAV and %d SAV bindus. Good progress expected in %s. %s areas are positively influenced.", "%s मा %d BAV र %d SAV बिन्दुसँगको गोचरले अनुकूल परिणामहरू ल्याउँछ। %s मा राम्रो प्रगति अपेक्षित छ। %s क्षेत्रमा सकारात्मक प्रभाव पर्नेछ।"),
    TRANSIT_AVERAGE_TEMPLATE("Transit through %s (%d BAV, %d SAV) brings mixed results. Some progress in %s with occasional challenges. Balance needed in %s.", "%s मा (%d BAV, %d SAV) गोचरले मिश्रित परिणामहरू ल्याउँछ। %s मा केही प्रगति तर कहिलेकाहीँ चुनौतीहरू। %s मा सन्तुलन आवश्यक।"),
    TRANSIT_BELOW_AVG_TEMPLATE("Transit through %s (%d BAV, %d SAV) suggests caution needed. %s matters may face delays. Extra effort required in %s areas.", "%s मा (%d BAV, %d SAV) गोचरले सावधानी आवश्यक देखाउँछ। %s मामिलाहरूमा ढिलाइ हुन सक्छ। %s क्षेत्रमा थप प्रयास आवश्यक।"),
    TRANSIT_CHALLENGING_TEMPLATE("Challenging transit through %s with only %d BAV and %d SAV bindus. Difficulties possible in %s. Patience needed for %s matters.", "%s मा केवल %d BAV र %d SAV बिन्दुसँगको चुनौतीपूर्ण गोचर। %s मा कठिनाइहरू सम्भव। %s मामिलाहरूमा धैर्य आवश्यक।"),
    TRANSIT_DIFFICULT_TEMPLATE("Difficult transit period through %s (%d BAV, %d SAV). Significant challenges in %s areas. Careful handling of %s required.", "%s मा (%d BAV, %d SAV) कठिन गोचर अवधि। %s क्षेत्रमा महत्त्वपूर्ण चुनौतीहरू। %s को सावधानीपूर्वक व्यवस्थापन आवश्यक।"),

    // ============================================
    // NAKSHATRA DETAILS (For ChartDialogs)
    // ============================================
    NAKSHATRA_SYMBOL("Symbol", "प्रतीक"),
    NAKSHATRA_DEITY("Deity", "देवता"),
    NAKSHATRA_PADA("Pada", "पद"),
    NAKSHATRA_GUNA("Guna", "गुण"),
    NAKSHATRA_GANA("Gana", "गण"),
    NAKSHATRA_YONI("Yoni", "योनि"),
    NAKSHATRA_ANIMAL("Animal", "पशु"),
    NAKSHATRA_BIRD("Bird", "पक्षी"),
    NAKSHATRA_TREE("Tree", "वृक्ष"),
    NAKSHATRA_NATURE("Nature", "प्रकृति"),
    NAKSHATRA_GENDER("Gender", "लिङ्ग"),
    NAKSHATRA_MALE("Male", "पुरुष"),
    NAKSHATRA_FEMALE("Female", "महिला"),
    NAKSHATRA_CAREERS("Careers", "क्यारियर"),

    // Nakshatra Nature types
    NAKSHATRA_NATURE_SWIFT("Swift (Kshipra)", "क्षिप्र (छिटो)"),
    NAKSHATRA_NATURE_FIERCE("Fierce (Ugra)", "उग्र (तीव्र)"),
    NAKSHATRA_NATURE_MIXED("Mixed (Mishra)", "मिश्र"),
    NAKSHATRA_NATURE_FIXED("Fixed (Dhruva)", "ध्रुव (स्थिर)"),
    NAKSHATRA_NATURE_SOFT("Soft (Mridu)", "मृदु (कोमल)"),
    NAKSHATRA_NATURE_SHARP("Sharp (Tikshna)", "तीक्ष्ण"),
    NAKSHATRA_NATURE_MOVABLE("Movable (Chara)", "चर"),
    NAKSHATRA_NATURE_LIGHT("Light (Laghu)", "लघु"),

    // Guna types
    GUNA_RAJAS("Rajas", "रजस्"),
    GUNA_TAMAS("Tamas", "तमस्"),

    // ============================================
    // DASHA SANDHI ANALYZER STRINGS
    // ============================================
    SANDHI_INTENSITY_CRITICAL("Critical", "गम्भीर"),
    SANDHI_INTENSITY_HIGH("High", "उच्च"),
    SANDHI_INTENSITY_MODERATE("Moderate", "मध्यम"),
    SANDHI_INTENSITY_MILD("Mild", "हल्का"),
    SANDHI_INTENSITY_MINIMAL("Minimal", "न्यूनतम"),
    TRANSITION_FRIEND_FRIEND("Friend to Friend", "मित्र देखि मित्र"),
    TRANSITION_FRIEND_NEUTRAL("Friend to Neutral", "मित्र देखि तटस्थ"),
    TRANSITION_FRIEND_ENEMY("Friend to Enemy", "मित्र देखि शत्रु"),
    TRANSITION_NEUTRAL_FRIEND("Neutral to Friend", "तटस्थ देखि मित्र"),
    TRANSITION_NEUTRAL_NEUTRAL("Neutral to Neutral", "तटस्थ देखि तटस्थ"),
    TRANSITION_NEUTRAL_ENEMY("Neutral to Enemy", "तटस्थ देखि शत्रु"),
    TRANSITION_ENEMY_FRIEND("Enemy to Friend", "शत्रु देखि मित्र"),
    TRANSITION_ENEMY_NEUTRAL("Enemy to Neutral", "शत्रु देखि तटस्थ"),
    TRANSITION_ENEMY_ENEMY("Enemy to Enemy", "शत्रु देखि शत्रु"),

    // ============================================
    // GOCHARA VEDHA CALCULATOR STRINGS
    // ============================================
    VEDHA_COMPLETE("Complete", "पूर्ण"),
    VEDHA_STRONG("Strong", "बलियो"),
    VEDHA_MODERATE("Moderate", "मध्यम"),
    VEDHA_PARTIAL("Partial", "आंशिक"),
    VEDHA_NONE("None", "कुनै छैन"),
    TRANSIT_EXCELLENT("Excellent", "उत्कृष्ट"),
    TRANSIT_GOOD("Good", "राम्रो"),
    TRANSIT_MODERATE("Moderate", "मध्यम"),
    TRANSIT_WEAK("Weak", "कमजोर"),
    TRANSIT_NULLIFIED("Nullified", "शून्य"),
    TRANSIT_UNFAVORABLE("Unfavorable", "प्रतिकूल"),

    // ============================================
    // KEMADRUMA YOGA CALCULATOR STRINGS
    // ============================================
    KEMADRUMA_NOT_PRESENT("Not Present", "उपस्थित छैन"),
    KEMADRUMA_FULLY_CANCELLED("Fully Cancelled", "पूर्ण रद्द"),
    KEMADRUMA_MOSTLY_CANCELLED("Mostly Cancelled", "प्राय: रद्द"),
    KEMADRUMA_PARTIALLY_CANCELLED("Partially Cancelled", "आंशिक रद्द"),
    KEMADRUMA_WEAKLY_CANCELLED("Weakly Cancelled", "कमजोर रद्द"),
    KEMADRUMA_ACTIVE_MODERATE("Active - Moderate", "सक्रिय - मध्यम"),
    KEMADRUMA_ACTIVE_SEVERE("Active - Severe", "सक्रिय - गम्भीर"),
    BHANGA_KENDRA_MOON("Kendra Moon", "केन्द्र चन्द्र"),
    BHANGA_KENDRA_LAGNA("Kendra Lagna", "केन्द्र लग्न"),
    BHANGA_MOON_KENDRA("Moon in Kendra", "चन्द्र केन्द्रमा"),
    BHANGA_BENEFIC_ASPECT("Benefic Aspect", "शुभ दृष्टि"),
    BHANGA_BENEFIC_CONJUNCTION("Benefic Conjunction", "शुभ संयोग"),
    BHANGA_MOON_EXALTED("Moon Exalted", "चन्द्र उच्च"),
    BHANGA_MOON_OWN("Moon Own Sign", "चन्द्र स्वराशि"),
    BHANGA_MOON_FRIEND("Moon with Friend", "चन्द्र मित्र साथ"),
    BHANGA_FULL_MOON("Full Moon", "पूर्ण चन्द्र"),
    BHANGA_ANGULAR_MOON("Angular Moon", "केन्द्रीय चन्द्र"),
    BHANGA_STRONG_DISPOSITOR("Strong Dispositor", "बलियो स्वामी"),
    BHANGA_JUPITER_ASPECT("Jupiter Aspect", "गुरु दृष्टि"),
    BHANGA_VENUS_ASPECT("Venus Aspect", "शुक्र दृष्टि"),

    // ============================================
    // TARABALA CALCULATOR STRINGS
    // ============================================
    TARA_JANMA("Janma", "जन्म"),
    TARA_SAMPAT("Sampat", "सम्पत्ति"),
    TARA_VIPAT("Vipat", "विपत्ति"),
    TARA_KSHEMA("Kshema", "क्षेम"),
    TARA_PRATYARI("Pratyari", "प्रत्यारी"),
    TARA_SADHAKA("Sadhaka", "साधक"),
    TARA_VADHA("Vadha", "वध"),
    TARA_MITRA("Mitra", "मित्र"),
    TARA_PARAMA_MITRA("Parama Mitra", "परम मित्र"),
    TARA_JANMA_DESC("Birth Star", "जन्म नक्षत्र"),
    TARA_SAMPAT_DESC("Wealth Star", "सम्पत्ति नक्षत्र"),
    TARA_VIPAT_DESC("Misfortune Star", "विपत्ति नक्षत्र"),
    TARA_KSHEMA_DESC("Welfare Star", "क्षेम नक्षत्र"),
    TARA_PRATYARI_DESC("Enemy Star", "प्रत्यारी नक्षत्र"),
    TARA_SADHAKA_DESC("Achiever Star", "साधक नक्षत्र"),
    TARA_VADHA_DESC("Death Star", "वध नक्षत्र"),
    TARA_MITRA_DESC("Friend Star", "मित्र नक्षत्र"),
    TARA_PARAMA_MITRA_DESC("Highest Friend Star", "परम मित्र नक्षत्र"),
    CHANDRABALA_EXCELLENT("Excellent", "उत्कृष्ट"),
    CHANDRABALA_GOOD("Good", "राम्रो"),
    CHANDRABALA_NEUTRAL("Neutral", "तटस्थ"),
    CHANDRABALA_WEAK("Weak", "कमजोर"),
    CHANDRABALA_UNFAVORABLE("Unfavorable", "प्रतिकूल"),
    COMBINED_HIGHLY_FAVORABLE("Highly Favorable", "अत्यन्त अनुकूल"),
    COMBINED_FAVORABLE("Favorable", "अनुकूल"),
    COMBINED_MIXED("Mixed", "मिश्रित"),
    COMBINED_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    COMBINED_UNFAVORABLE("Unfavorable", "प्रतिकूल"),

    // ============================================
    // TARABALA SCREEN STRINGS
    // ============================================
    TARABALA_TITLE("Tarabala", "तारबल"),
    TARABALA_TODAY("Today", "आज"),
    TARABALA_WEEKLY("Weekly", "साप्ताहिक"),
    TARABALA_ALL_NAKSHATRAS("All 27 Nakshatras", "सबै २७ नक्षत्र"),
    TARABALA_WHAT_IS("What is Tarabala?", "तारबल के हो?"),
    TARABALA_UNABLE_CALCULATE("Unable to calculate Tarabala without birth details", "जन्म विवरण बिना तारबल गणना गर्न सक्षम छैन"),
    TARABALA_TODAY_STRENGTH("Today's Strength", "आजको शक्ति"),
    TARABALA_LABEL("Tarabala", "तारबल"),
    TARABALA_STAR_STRENGTH("Star Strength", "नक्षत्र शक्ति"),
    TARABALA_FAVORABLE("Favorable", "अनुकूल"),
    TARABALA_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    TARABALA_BIRTH_STAR("Birth Star", "जन्म नक्षत्र"),
    TARABALA_TRANSIT_STAR("Transit Star", "गोचर नक्षत्र"),
    TARABALA_TARA_TYPE("Tara Type", "तार प्रकार"),
    TARABALA_CYCLE("Cycle", "चक्र"),
    CHANDRABALA_LABEL("Chandrabala", "चन्द्रबल"),
    CHANDRABALA_MOON_STRENGTH("Moon Strength", "चन्द्र शक्ति"),
    CHANDRABALA_NATAL_MOON("Natal Moon", "जन्मकालीन चन्द्र"),
    CHANDRABALA_TRANSIT_MOON("Transit Moon", "गोचर चन्द्र"),
    CHANDRABALA_HOUSE("House", "भाव"),
    CHANDRABALA_SIGNIFICATIONS("Significations", "करकत्व"),
    TARABALA_ACTIVITIES("Activities", "गतिविधिहरू"),
    TARABALA_FAVORABLE_ACTIVITIES("Favorable Activities", "अनुकूल गतिविधिहरू"),
    TARABALA_AVOID_ACTIVITIES("Activities to Avoid", "टाढा रहनु पर्ने गतिविधिहरू"),
    TARABALA_DAILY_BREAKDOWN("Daily Breakdown", "दैनिक विभाजन"),
    TARABALA_WEEKLY_OVERVIEW("Weekly Overview", "साप्ताहिक सिंहावलोकन"),
    TARABALA_BEST_DAY("Best Day", "सबैभन्दा राम्रो दिन"),
    TARABALA_AVOID_DAY("Day to Avoid", "टाढा रहनु पर्ने दिन"),
    TARABALA_BEST_LABEL("Best", "सबैभन्दा राम्रो"),
    TARABALA_ALL_27_DESC("All 27 Nakshatras with their Tarabala values", "सबै २७ नक्षत्र तिनीहरूको तारबल मानहरू साथ"),
    TARABALA_EXPLANATION("Explanation", "व्याख्या"),
    CHANDRABALA_EXPLANATION("Explanation", "व्याख्या"),

    // ============================================
    // PRASHNA (HORARY) CALCULATOR STRINGS
    // ============================================
    
    // PrashnaCategory enum
    PRASHNA_CAT_YES_NO("Yes/No", "हो/होइन"),
    PRASHNA_CAT_YES_NO_DESC("Simple yes or no questions", "साधारण हो वा होइन प्रश्नहरू"),
    PRASHNA_CAT_CAREER("Career", "क्यारियर"),
    PRASHNA_CAT_CAREER_DESC("Job, profession, and career-related questions", "जागिर, पेशा र क्यारियर सम्बन्धी प्रश्नहरू"),
    PRASHNA_CAT_MARRIAGE("Marriage", "विवाह"),
    PRASHNA_CAT_MARRIAGE_DESC("Marriage and spouse-related questions", "विवाह र जीवनसाथी सम्बन्धी प्रश्नहरू"),
    PRASHNA_CAT_CHILDREN("Children", "सन्तान"),
    PRASHNA_CAT_CHILDREN_DESC("Questions about children and progeny", "सन्तान र सन्ततिका बारेमा प्रश्नहरू"),
    PRASHNA_CAT_HEALTH("Health", "स्वास्थ्य"),
    PRASHNA_CAT_HEALTH_DESC("Health and illness-related questions", "स्वास्थ्य र रोग सम्बन्धी प्रश्नहरू"),
    PRASHNA_CAT_WEALTH("Wealth", "धन-सम्पत्ति"),
    PRASHNA_CAT_WEALTH_DESC("Financial and wealth-related questions", "आर्थिक र धन-सम्पत्ति सम्बन्धी प्रश्नहरू"),
    PRASHNA_CAT_PROPERTY("Property", "सम्पत्ति"),
    PRASHNA_CAT_PROPERTY_DESC("Real estate and property questions", "जग्गा-जमिन र सम्पत्ति सम्बन्धी प्रश्नहरू"),
    PRASHNA_CAT_TRAVEL("Travel", "यात्रा"),
    PRASHNA_CAT_TRAVEL_DESC("Journey and travel-related questions", "यात्रा सम्बन्धी प्रश्नहरू"),
    PRASHNA_CAT_EDUCATION("Education", "शिक्षा"),
    PRASHNA_CAT_EDUCATION_DESC("Studies and educational questions", "अध्ययन र शैक्षिक प्रश्नहरू"),
    PRASHNA_CAT_LEGAL("Legal", "कानुनी"),
    PRASHNA_CAT_LEGAL_DESC("Court cases and legal matters", "मुद्दा-मामिला र कानुनी विषयहरू"),
    PRASHNA_CAT_LOST_OBJECT("Lost Object", "हराएको वस्तु"),
    PRASHNA_CAT_LOST_OBJECT_DESC("Finding lost or stolen items", "हराएको वा चोरी भएको वस्तु खोज्ने"),
    PRASHNA_CAT_RELATIONSHIP("Relationship", "सम्बन्ध"),
    PRASHNA_CAT_RELATIONSHIP_DESC("Love and relationship questions", "प्रेम र सम्बन्ध सम्बन्धी प्रश्नहरू"),
    PRASHNA_CAT_BUSINESS("Business", "व्यापार"),
    PRASHNA_CAT_BUSINESS_DESC("Business partnership and deals", "व्यापार साझेदारी र सौदाहरू"),
    PRASHNA_CAT_SPIRITUAL("Spiritual", "आध्यात्मिक"),
    PRASHNA_CAT_SPIRITUAL_DESC("Spiritual and religious questions", "आध्यात्मिक र धार्मिक प्रश्नहरू"),
    PRASHNA_CAT_GENERAL("General", "सामान्य"),
    PRASHNA_CAT_GENERAL_DESC("General questions and queries", "सामान्य प्रश्नहरू"),

    // Tattva enum
    PRASHNA_TATTVA_FIRE("Agni/Fire", "अग्नि"),
    PRASHNA_TATTVA_FIRE_SIGNS("Aries, Leo, Sagittarius", "मेष, सिंह, धनु"),
    PRASHNA_TATTVA_EARTH("Prithvi/Earth", "पृथ्वी"),
    PRASHNA_TATTVA_EARTH_SIGNS("Taurus, Virgo, Capricorn", "वृष, कन्या, मकर"),
    PRASHNA_TATTVA_AIR("Vayu/Air", "वायु"),
    PRASHNA_TATTVA_AIR_SIGNS("Gemini, Libra, Aquarius", "मिथुन, तुला, कुम्भ"),
    PRASHNA_TATTVA_WATER("Jala/Water", "जल"),
    PRASHNA_TATTVA_WATER_SIGNS("Cancer, Scorpio, Pisces", "कर्कट, वृश्चिक, मीन"),
    PRASHNA_TATTVA_ETHER("Akasha/Ether", "आकाश"),
    PRASHNA_TATTVA_ETHER_SIGNS("None - represents void/space", "कुनै पनि होइन - शून्य/अन्तरिक्ष"),

    // TimingUnit enum
    PRASHNA_TIMING_HOURS("Hours", "घण्टा"),
    PRASHNA_TIMING_DAYS("Days", "दिन"),
    PRASHNA_TIMING_WEEKS("Weeks", "हप्ता"),
    PRASHNA_TIMING_MONTHS("Months", "महिना"),
    PRASHNA_TIMING_YEARS("Years", "वर्ष"),

    // PrashnaVerdict enum
    PRASHNA_VERDICT_STRONGLY_YES("Strongly Yes - Success Indicated", "निश्चित हो - सफलताको संकेत"),
    PRASHNA_VERDICT_YES("Yes - Favorable Outcome", "हो - अनुकूल परिणाम"),
    PRASHNA_VERDICT_LIKELY_YES("Likely Yes - Conditions Apply", "सम्भवतः हो - सर्तहरू लागू"),
    PRASHNA_VERDICT_UNCERTAIN("Uncertain - Mixed Indications", "अनिश्चित - मिश्रित संकेत"),
    PRASHNA_VERDICT_LIKELY_NO("Likely No - Difficulties Indicated", "सम्भवतः होइन - कठिनाइहरू संकेत"),
    PRASHNA_VERDICT_NO("No - Unfavorable Outcome", "होइन - प्रतिकूल परिणाम"),
    PRASHNA_VERDICT_STRONGLY_NO("Strongly No - Failure Indicated", "निश्चित होइन - असफलताको संकेत"),
    PRASHNA_VERDICT_TIMING_DEPENDENT("Timing Dependent - Wait Indicated", "समय निर्भर - प्रतीक्षा गर्नुहोस्"),

    // CertaintyLevel enum
    PRASHNA_CERTAINTY_VERY_HIGH("Very High Certainty", "अत्यधिक निश्चितता"),
    PRASHNA_CERTAINTY_HIGH("High Certainty", "उच्च निश्चितता"),
    PRASHNA_CERTAINTY_MODERATE("Moderate Certainty", "मध्यम निश्चितता"),
    PRASHNA_CERTAINTY_LOW("Low Certainty", "न्यून निश्चितता"),
    PRASHNA_CERTAINTY_VERY_LOW("Very Low Certainty", "अति न्यून निश्चितता"),

    // MoonStrength enum
    PRASHNA_MOON_EXCELLENT("Excellent", "उत्कृष्ट"),
    PRASHNA_MOON_GOOD("Good", "राम्रो"),
    PRASHNA_MOON_AVERAGE("Average", "औसत"),
    PRASHNA_MOON_WEAK("Weak", "कमजोर"),
    PRASHNA_MOON_VERY_WEAK("Very Weak", "अत्यन्त कमजोर"),
    PRASHNA_MOON_AFFLICTED("Afflicted", "पीडित"),

    // LagnaCondition enum
    PRASHNA_LAGNA_STRONG("Strong - Well placed lord", "बलियो - राम्रोसँग स्थापित स्वामी"),
    PRASHNA_LAGNA_MODERATE("Moderate - Mixed influences", "मध्यम - मिश्रित प्रभावहरू"),
    PRASHNA_LAGNA_WEAK("Weak - Afflicted or poorly placed", "कमजोर - पीडित वा खराब स्थापित"),
    PRASHNA_LAGNA_COMBUST("Combust - Lord too close to Sun", "अस्त - स्वामी सूर्यको अत्यधिक नजिक"),
    PRASHNA_LAGNA_RETROGRADE_LORD("Lord is Retrograde", "स्वामी वक्री छ"),

    // StrengthLevel enum
    PRASHNA_STRENGTH_VERY_STRONG("Very Strong", "अत्यन्त बलियो"),
    PRASHNA_STRENGTH_STRONG("Strong", "बलियो"),
    PRASHNA_STRENGTH_MODERATE("Moderate", "मध्यम"),
    PRASHNA_STRENGTH_WEAK("Weak", "कमजोर"),
    PRASHNA_STRENGTH_VERY_WEAK("Very Weak", "अत्यन्त कमजोर"),
    PRASHNA_STRENGTH_DEBILITATED("Debilitated", "नीच"),

    // AspectType enum
    PRASHNA_ASPECT_CONJUNCTION("Conjunction", "युति"),
    PRASHNA_ASPECT_SEXTILE("Sextile", "षष्ठ"),
    PRASHNA_ASPECT_SQUARE("Square", "चतुर्थ"),
    PRASHNA_ASPECT_TRINE("Trine", "त्रिकोण"),
    PRASHNA_ASPECT_OPPOSITION("Opposition", "सप्तम"),
    PRASHNA_ASPECT_MARS_4TH("Mars 4th Aspect", "मंगल ४ औं दृष्टि"),
    PRASHNA_ASPECT_MARS_8TH("Mars 8th Aspect", "मंगल ८ औं दृष्टि"),
    PRASHNA_ASPECT_JUPITER_5TH("Jupiter 5th Aspect", "गुरु ५ औं दृष्टि"),
    PRASHNA_ASPECT_JUPITER_9TH("Jupiter 9th Aspect", "गुरु ९ औं दृष्टि"),
    PRASHNA_ASPECT_SATURN_3RD("Saturn 3rd Aspect", "शनि ३ रो दृष्टि"),
    PRASHNA_ASPECT_SATURN_10TH("Saturn 10th Aspect", "शनि १० औं दृष्टि"),

    // HouseStrength enum
    PRASHNA_HOUSE_EXCELLENT("Excellent", "उत्कृष्ट"),
    PRASHNA_HOUSE_GOOD("Good", "राम्रो"),
    PRASHNA_HOUSE_MODERATE("Moderate", "मध्यम"),
    PRASHNA_HOUSE_POOR("Poor", "कमजोर"),
    PRASHNA_HOUSE_AFFLICTED("Afflicted", "पीडित"),

    // TimingMethod enum
    PRASHNA_METHOD_MOON_TRANSIT("Moon Transit Method", "चन्द्र गोचर विधि"),
    PRASHNA_METHOD_MOON_NAKSHATRA("Moon Nakshatra Method", "चन्द्र नक्षत्र विधि"),
    PRASHNA_METHOD_HOUSE_LORD_DEGREES("House Lord Degrees", "भावेश अंश विधि"),
    PRASHNA_METHOD_LAGNA_DEGREES("Lagna Degrees Method", "लग्न अंश विधि"),
    PRASHNA_METHOD_PLANETARY_CONJUNCTION("Planetary Conjunction", "ग्रह युति विधि"),
    PRASHNA_METHOD_DASHA_BASED("Dasha-based Timing", "दशा आधारित समय"),
    PRASHNA_METHOD_MIXED("Combined Methods", "संयुक्त विधिहरू"),

    // OmenType enum
    PRASHNA_OMEN_LAGNA("Prashna Lagna Sign", "प्रश्न लग्न राशि"),
    PRASHNA_OMEN_MOON_PLACEMENT("Moon Placement", "चन्द्र स्थिति"),
    PRASHNA_OMEN_HORA_LORD("Hora Lord", "होरा स्वामी"),
    PRASHNA_OMEN_DAY_LORD("Day Lord", "दिन स्वामी"),
    PRASHNA_OMEN_NAKSHATRA("Question Nakshatra", "प्रश्न नक्षत्र"),
    PRASHNA_OMEN_PLANETARY_WAR("Planetary War", "ग्रह युद्ध"),
    PRASHNA_OMEN_COMBUSTION("Combustion", "अस्त"),
    PRASHNA_OMEN_RETROGRADE("Retrograde Planet", "वक्री ग्रह"),
    PRASHNA_OMEN_GANDANTA("Gandanta Position", "गण्डान्त स्थिति"),
    PRASHNA_OMEN_PUSHKARA("Pushkara Navamsha", "पुष्कर नवांश"),

    // ============================================
    // NITYA YOGA CALCULATOR STRINGS
    // ============================================
    
    // Auspiciousness enum
    AUSPICIOUSNESS_HIGHLY_AUSPICIOUS("Highly Auspicious", "अत्यन्त शुभ"),
    AUSPICIOUSNESS_AUSPICIOUS("Auspicious", "शुभ"),
    AUSPICIOUSNESS_NEUTRAL("Neutral", "तटस्थ"),
    AUSPICIOUSNESS_INAUSPICIOUS("Inauspicious", "अशुभ"),
    AUSPICIOUSNESS_HIGHLY_INAUSPICIOUS("Highly Inauspicious", "अत्यन्त अशुभ"),

    // YogaStrength enum (generic for Nitya Yoga)
    NITYA_STRENGTH_VERY_STRONG("Very Strong", "अत्यन्त बलियो"),
    NITYA_STRENGTH_STRONG("Strong", "बलियो"),
    NITYA_STRENGTH_MODERATE("Moderate", "मध्यम"),
    NITYA_STRENGTH_WEAK("Weak", "कमजोर"),
    NITYA_STRENGTH_VERY_WEAK("Very Weak", "अत्यन्त कमजोर"),

    // RecommendationCategory enum
    RECOMMEND_SPIRITUAL("Spiritual Practice", "आध्यात्मिक अभ्यास"),
    RECOMMEND_ACTIVITY("Activity Guidance", "गतिविधि मार्गदर्शन"),
    RECOMMEND_MANTRA("Mantra Recitation", "मन्त्र जप"),
    RECOMMEND_TIMING("Timing Advice", "समय सल्लाह"),
    RECOMMEND_GENERAL("General Guidance", "सामान्य मार्गदर्शन"),

    // ============================================
    // MUHURTA CALCULATOR STRINGS
    // ============================================
    
    // Vara enum (days of week)
    VARA_SUNDAY("Sunday", "आइतबार"),
    VARA_MONDAY("Monday", "सोमबार"),
    VARA_TUESDAY("Tuesday", "मंगलबार"),
    VARA_WEDNESDAY("Wednesday", "बुधबार"),
    VARA_THURSDAY("Thursday", "बिहीबार"),
    VARA_FRIDAY("Friday", "शुक्रबार"),
    VARA_SATURDAY("Saturday", "शनिबार"),

    // Choghadiya enum
    CHOGHADIYA_UDVEG("Udveg", "उद्वेग"),
    CHOGHADIYA_CHAR("Char", "चर"),
    CHOGHADIYA_LABH("Labh", "लाभ"),
    CHOGHADIYA_AMRIT("Amrit", "अमृत"),
    CHOGHADIYA_KAAL("Kaal", "काल"),
    CHOGHADIYA_SHUBH("Shubh", "शुभ"),
    CHOGHADIYA_ROG("Rog", "रोग"),

    // ChoghadiyaNature enum
    CHOGHADIYA_NATURE_AUSPICIOUS("Auspicious", "शुभ"),
    CHOGHADIYA_NATURE_INAUSPICIOUS("Inauspicious", "अशुभ"),
    CHOGHADIYA_NATURE_MIXED("Mixed", "मिश्रित"),

    // NakshatraNature enum
    NAKSHATRA_NATURE_DREADFUL("Dreadful", "क्रूर"),

    // HoraNature enum
    HORA_NATURE_BENEFIC("Benefic", "शुभ"),
    HORA_NATURE_MALEFIC("Malefic", "पापी"),
    HORA_NATURE_NEUTRAL("Neutral", "तटस्थ"),

    // TithiNature enum
    TITHI_NATURE_NANDA("Nanda", "नन्दा"),
    TITHI_NATURE_BHADRA("Bhadra", "भद्रा"),
    TITHI_NATURE_JAYA("Jaya", "जया"),
    TITHI_NATURE_RIKTA("Rikta", "रिक्ता"),
    TITHI_NATURE_PURNA("Purna", "पूर्णा"),

    // NakshatraGana enum
    NAKSHATRA_GANA_DEVA("Deva", "देव"),
    NAKSHATRA_GANA_MANUSHYA("Manushya", "मनुष्य"),
    NAKSHATRA_GANA_RAKSHASA("Rakshasa", "राक्षस"),

    // NakshatraElement enum
    NAKSHATRA_ELEMENT_FIRE("Fire", "अग्नि"),
    NAKSHATRA_ELEMENT_EARTH("Earth", "पृथ्वी"),
    NAKSHATRA_ELEMENT_AIR("Air", "वायु"),
    NAKSHATRA_ELEMENT_WATER("Water", "जल"),
    NAKSHATRA_ELEMENT_ETHER("Ether", "आकाश"),

    // KaranaType enum
    KARANA_MOVABLE("Movable", "चर"),
    KARANA_FIXED("Fixed", "स्थिर"),

    // ============================================
    // PANCHANGA CALCULATOR STRINGS
    // ============================================
    
    // TithiGroup enum
    TITHI_GROUP_NANDA("Nanda", "नन्दा"),
    TITHI_GROUP_NANDA_NATURE("Joyful", "आनन्दमय"),
    TITHI_GROUP_BHADRA("Bhadra", "भद्रा"),
    TITHI_GROUP_BHADRA_NATURE("Auspicious", "शुभ"),
    TITHI_GROUP_JAYA("Jaya", "जया"),
    TITHI_GROUP_JAYA_NATURE("Victorious", "विजयी"),
    TITHI_GROUP_RIKTA("Rikta", "रिक्ता"),
    TITHI_GROUP_RIKTA_NATURE("Empty", "रित्त"),
    TITHI_GROUP_PURNA("Purna", "पूर्णा"),
    TITHI_GROUP_PURNA_NATURE("Complete", "पूर्ण"),

    // YogaNature enum
    YOGA_NATURE_AUSPICIOUS("Auspicious", "शुभ"),
    YOGA_NATURE_INAUSPICIOUS("Inauspicious", "अशुभ"),
    YOGA_NATURE_MIXED("Mixed", "मिश्र"),

    // Paksha enum
    PAKSHA_SHUKLA("Shukla Paksha", "शुक्ल पक्ष"),
    PAKSHA_KRISHNA("Krishna Paksha", "कृष्ण पक्ष"),

    // ============================================
    // RETROGRADE/COMBUSTION CALCULATOR STRINGS
    // ============================================
    
    // RetrogradeStatus enum
    RETRO_DIRECT("Direct", "मार्गी"),
    RETRO_RETROGRADE("Retrograde", "वक्री"),
    RETRO_STATIONARY_RETRO("Stationary Retrograde", "स्थिर वक्री"),
    RETRO_STATIONARY_DIRECT("Stationary Direct", "स्थिर मार्गी"),
    RETRO_ALWAYS_RETROGRADE("Perpetual Retrograde", "सदा वक्री"),

    // CombustionStatus enum
    COMBUST_NOT("Not Combust", "अस्त छैन"),
    COMBUST_APPROACHING("Approaching Combustion", "अस्त हुँदै"),
    COMBUST_COMBUST("Combust", "अस्त"),
    COMBUST_DEEP("Deep Combustion", "गहिरो अस्त"),
    COMBUST_CAZIMI("Cazimi", "कज़िमी"),
    COMBUST_SEPARATING("Separating", "अलग हुँदै"),

    // SpeedStatus enum
    SPEED_VERY_FAST("Very Fast", "अति छिटो"),
    SPEED_FAST("Fast", "छिटो"),
    SPEED_NORMAL("Normal", "सामान्य"),
    SPEED_SLOW("Slow", "ढिलो"),
    SPEED_VERY_SLOW("Very Slow", "अति ढिलो"),
    SPEED_STATIONARY("Stationary", "स्थिर"),
    SPEED_RETROGRADE_MOTION("Retrograde Motion", "वक्री गति"),

    // WarAdvantage enum
    WAR_NORTHERN_LAT("Northern Latitude", "उत्तरी अक्षांश"),
    WAR_BRIGHTNESS("Greater Brightness", "अधिक चमक"),
    WAR_COMBINED("Both Factors", "दुवै कारक"),
    WAR_INDETERMINATE("Evenly Matched", "बराबर"),

    // ============================================
    // SHADBALA CALCULATOR STRINGS
    // ============================================
    
    // StrengthRating enum
    STRENGTH_EXTREMELY_WEAK("Extremely Weak", "अत्यन्त कमजोर"),
    STRENGTH_WEAK("Weak", "कमजोर"),
    STRENGTH_BELOW_AVERAGE("Below Average", "औसतभन्दा कम"),
    STRENGTH_AVERAGE("Average", "औसत"),
    STRENGTH_ABOVE_AVERAGE("Above Average", "औसतभन्दा माथि"),
    STRENGTH_STRONG("Strong", "बलियो"),
    STRENGTH_VERY_STRONG("Very Strong", "अत्यन्त बलियो"),
    STRENGTH_EXTREMELY_STRONG("Extremely Strong", "अति शक्तिशाली"),
    
    // Generic Strength/Status (Added for Batch 3)
    STRENGTH_EXCELLENT("Excellent", "उत्कृष्ट"),
    STRENGTH_GOOD("Good", "राम्रो"),
    STRENGTH_MODERATE("Moderate", "मध्यम"),
    STRENGTH_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    STRENGTH_DIFFICULT("Difficult", "कठिन"),
    BENEFIC("Benefic", "शुभ"),
    MALEFIC("Malefic", "पाप"),
    LORDS("Lords", "स्वामीहरू"),

    // ============================================
    // ARGALA ANALYSIS
    // ============================================
    ARGALA_CALCULATING("Calculating Argala...", "अर्गला गणना गर्दै..."),
    ARGALA_FAILED("Failed to calculate Argala", "अर्गला गणना गर्न असफल"),
    ARGALA_SELECT_HOUSE("Select House to Analyze", "विश्लेषण गर्न भाव चयन गर्नुहोस्"),
    ARGALA_HOUSE_SELECTOR_LABEL("Select House to Analyze", "विश्लेषण गर्न भाव चयन गर्नुहोस्"),
    ARGALA_TYPES_TITLE("Argala Types", "अर्गलाका प्रकारहरू"),
    ARGALA_PRIMARY_DESC("4th & 11th houses create primary interventions", "चौथो र एघारौं भावले प्राथमिक हस्तक्षेप सिर्जना गर्दछ"),
    ARGALA_SECONDARY_DESC("2nd house creates secondary interventions", "दोस्रो भावले माध्यमिक हस्तक्षेप सिर्जना गर्दछ"),
    ARGALA_FIFTH_HOUSE_DESC("5th house creates special interventions", "पाँचौं भावले विशेष हस्तक्षेप सिर्जना गर्दछ"),
    ARGALA_VIRODHA_DESC("12th, 10th, 3rd, 9th houses obstruct Argala", "१२औं, १०औं, ३औं, र ९औं भावले अर्गालालाई बाधा पुर्‍याउँछ"),
    ARGALA_CHART_WIDE_PATTERNS("Chart-Wide Argala Patterns", "कुण्डली-व्यापी अर्गाला ढाँचाहरू"),
    ARGALA_STRONGEST_SUPPORT("Strongest Support", "सबैभन्दा बलियो समर्थन"),
    ARGALA_GREATEST_CHALLENGE("Greatest Challenge", "सबैभन्दा ठूलो चुनौती"),
    ARGALA_MOST_OBSTRUCTED("Most Obstructed", "सबैभन्दा बाधित"),

    // Chara Dasha Analysis
    CHARA_DASHA_CALC_ERROR("Unknown error calculating Chara Dasha", "कारा दशा गणना गर्दा अज्ञात त्रुटि"),
    CALCULATING_CHARA_DASHA("Calculating Chara Dasha periods...", "कारा दशा अवधि गणना गर्दै..."),
    KARAKA_ACTIVATION("Karaka Activation", "कारक सक्रियता"),
    CHARA_DASHA_TYPE("Type", "प्रकार"),
    
    // Influence
    INFLUENCE_VERY_FAVORABLE("Very Favorable", "धेरै अनुकूल"),
    INFLUENCE_FAVORABLE("Favorable", "अनुकूल"),
    INFLUENCE_NEUTRAL("Neutral", "तटस्थ"),
    INFLUENCE_NEEDS_ATTENTION("Needs Attention", "ध्यान दिनुपर्ने"),

    // Life Areas
    AREA_CAREER("Career", "करियर"),
    AREA_RELATIONSHIPS("Relationships", "सम्बन्ध"),
    AREA_HEALTH("Health", "स्वास्थ्य"),
    AREA_SPIRITUALITY("Spirituality", "आध्यात्मिकता"),
    AREA_WEALTH("Wealth", "धन"),
    AREA_FAMILY("Family", "परिवार"),
    AREA_EDUCATION("Education", "शिक्षा"),
    AREA_FOREIGN_CONNECTIONS("Foreign Connections", "विदेशी सम्बन्ध"),

    // Aspects
    ASPECT_CONJUNCTION("Conjunction", "युति"),
    ASPECT_OPPOSITION("Opposition", "दृष्टि"), 
    ASPECT_TRINE("Trine", "त्रिकोण"),
    ASPECT_SQUARE("Square", "केन्द्र"),

    // Chara Dasha
    
    // Generic Strength/Status
    
    // Influence

    // Life Areas

    // Aspects
    ARGALA_LEAST_OBSTRUCTED("Least Obstructed", "कम से कम बाधित"),
    ARGALA_KARMA_PATTERNS("Karma Patterns", "कर्म ढाँचाहरू"),
    ARGALA_SIGNIFICANT_ARGALAS("Significant Argalas", "महत्वपूर्ण अर्गालाहरू"),
    
    // Argala Strength
    ARGALA_STRENGTH_VERY_STRONG("Very Strong", "धेरै बलियो"),
    ARGALA_STRENGTH_STRONG("Strong", "बलियो"),
    ARGALA_STRENGTH_MODERATE("Moderate", "मध्यम"),
    ARGALA_STRENGTH_WEAK("Weak", "कमजोर"),
    ARGALA_STRENGTH_OBSTRUCTED("Obstructed", "बाधित"),

    // ============================================
    // AVASTHA ANALYSIS
    // ============================================
    AVASTHA_TAB_OVERVIEW("Overview", "अवलोकन"),
    AVASTHA_TAB_PLANETS("Planets", "ग्रहहरू"),
    AVASTHA_TAB_BALADI("Baladi", "बालादि"),
    AVASTHA_TAB_JAGRADADI("Jagradadi", "जाग्रदादि"),
    AVASTHA_TAB_DEEPTADI("Deeptadi", "दीप्तादि"),
    AVASTHA_TAB_LAJJITADI("Lajjitadi", "लज्जितादि"),
    
    AVASTHA_STRONG_CONFIG("Strong overall planetary configuration", "बलियो समग्र ग्रह विन्यास"),
    AVASTHA_MODERATE_STRENGTH("Moderate overall planetary strength", "मध्यम समग्र ग्रह बल"),
    AVASTHA_NEEDS_MEASURES("Planets need strengthening measures", "ग्रहहरूलाई सवलीकरण उपायहरू आवश्यक छ"),
    
    AVASTHA_AGE_STATE("Age State (Baladi)", "अवस्था (बालादि)"),
    AVASTHA_ALERTNESS("Alertness (Jagradadi)", "सतर्कता (जाग्रदादि)"),
    AVASTHA_DIGNITY("Dignity (Deeptadi)", "इज्जत (दीप्तादि)"),
    AVASTHA_EMOTIONAL("Emotional (Lajjitadi)", "भावनात्मक (लज्जितादि)"),

    // ============================================
    // PRASHNA ANALYSIS
    // ============================================
    PRASHNA_NEW_QUESTION("New question", "नयाँ प्रश्न"),
    PRASHNA_KUNDALI("Prashna Kundali", "प्रश्न कुण्डली"),
    PRASHNA_HORARY("Horary Astrology", "प्रश्न ज्योतिष"),
    PRASHNA_INTRO("Ask your question and receive guidance based on the planetary positions at this very moment.", "आफ्नो प्रश्न सोध्नुहोस् र यस क्षणको ग्रह स्थितिको आधारमा मार्गदर्शन प्राप्त गर्नुहोस्।"),
    PRASHNA_YOUR_QUESTION("Your Question", "तपाईंको प्रश्न"),
    PRASHNA_QUESTION_HINT("Enter your question here...", "यहाँ आफ्नो प्रश्न प्रविष्ट गर्नुहोस्..."),
    PRASHNA_QUESTION_HELP("Be specific and clear. Frame your question with a sincere intent.", "स्पष्ट र विशिष्ट हुनुहोस्। ईमानदार इरादाले आफ्नो प्रश्न बनाउनुहोस्।"),
    PRASHNA_CATEGORY("Question Category", "प्रश्न वर्ग"),
    PRASHNA_LOCATION("Question Location", "प्रश्न स्थान"),
    PRASHNA_TIME_NOW("Now", "अहिले"),
    PRASHNA_ANALYZE("Analyze Question", "प्रश्न विश्लेषण गर्नुहोस्"),
    PRASHNA_ABOUT("About Prashna", "प्रश्न बारेमा"),
    PRASHNA_ANALYZING("Analyzing your question...", "तपाईंको प्रश्न विश्लेषण गर्दै..."),
    PRASHNA_CALCULATING("Calculating planetary positions and yogas", "ग्रह स्थिति र योगहरू गणना गर्दै"),
    PRASHNA_ANALYSIS_FAILED("Analysis Failed", "विश्लेषण असफल"),
    PRASHNA_UNFAVORABLE("Unfavorable", "प्रतिकूल"),
    PRASHNA_FAVORABLE("Favorable", "अनुकूल"),
    PRASHNA_SCORE("Score: %d", "अंक: %d"),
    PRASHNA_QUESTION_DETAILS("Question Details", "प्रश्न विवरण"),
    PRASHNA_MOON_ANALYSIS("Moon Analysis", "चन्द्र विश्लेषण"),
    PRASHNA_LAGNA_ANALYSIS("Lagna Analysis", "लग्न विश्लेषण"),
    PRASHNA_RISING_SIGN("Rising Sign", "उदय राशि"),
    PRASHNA_LAGNA_LORD("Lagna Lord", "लग्नेश"),
    PRASHNA_LORD_POSITION("Lord Position", "स्वामी स्थिति"),
    PRASHNA_CONDITION("Condition", "अवस्था"),
    PRASHNA_PLANETS_IN_LAGNA("Planets in Lagna", "लग्नमा ग्रहहरू"),
    PRASHNA_MOON_VOID("Moon is Void of Course - delays or changes likely", "चन्द्र शून्य गतिमा छ - ढिलाइ वा परिवर्तन सम्भावित"),
    PRASHNA_TIMING("Timing Prediction", "समय भविष्यवाणी"),
    PRASHNA_ESTIMATED_TIMEFRAME("Estimated Timeframe", "अनुमानित समयावधि"),
    PRASHNA_SPECIAL_YOGAS("Special Prashna Yogas", "विशेष प्रश्न योगहरू"),
    PRASHNA_SUPPORTING_FACTORS("Supporting Factors", "समर्थक कारकहरू"),
    PRASHNA_CHALLENGES("Challenges", "चुनौतीहरू"),
    PRASHNA_RECOMMENDATIONS("Recommendations", "सिफारिसहरू"),
    PRASHNA_MOON_PHASE_WAXING("Waxing", "शुक्ल पक्ष"),
    PRASHNA_MOON_PHASE_WANING("Waning", "कृष्ण पक्ष"),
    PRASHNA_SIGN("Sign", "राशि"),
    PRASHNA_NAKSHATRA_LORD("Nakshatra Lord", "नक्षत्र स्वामी"),
    PRASHNA_TITHI("Tithi", "तिथि"),
    PRASHNA_PHASE("Phase", "पक्ष"),
    PRASHNA_INST_1("Prashna Kundali is cast for the moment the question arises in your mind or when you ask it.", "प्रश्न कुण्डली तपाईंको मनमा प्रश्न उठेको क्षणमा वा तपाईंले सोधेको बेला बनाइन्छ।"),
    PRASHNA_INST_2("The Moon is the primary significator representing your mind and the matter at hand.", "चन्द्रमा प्राथमिक कारक हो जसले तपाईंको मन र हातमा रहेको विषयलाई प्रतिनिधित्व गर्दछ।"),
    PRASHNA_INST_3("Frame your question with sincerity and focused intent for accurate guidance.", "सटीक मार्गदर्शनको लागि आफ्नो प्रश्न ईमानदारिता र केन्द्रित उद्देश्यसहित बनाउनुहोस्।"),
    PRASHNA_INST_4("The analysis considers Lagna, Moon, relevant houses, and special Prashna yogas.", "विश्लेषणले लग्न, चन्द्रमा, सम्बन्धित भावहरू, र विशेष प्रश्न योगहरूलाई विचार गर्छ।"),
    PRASHNA_INST_5("Timing predictions are based on planetary movements and house lord positions.", "समय भविष्यवाणीहरू ग्रह गतिविधि र भाव स्वामी स्थितिमा आधारित छन्।"),
    PRASHNA_ANALYZE_ERROR("Failed to analyze question", "प्रश्न विश्लेषण गर्न असफल"),
    PRASHNA_AI_INSIGHT("AI Insight", "एआई अन्तर्दृष्टि"),
    PRASHNA_AI_INSIGHT_SUBTITLE("Powered by Stormy", "Stormy द्वारा संचालित"),
    PRASHNA_AI_INSIGHT_DESC("Get a deeper, personalized interpretation of your Prashna chart using AI. The AI will analyze the planetary positions and yogas to provide additional guidance.", "एआई प्रयोग गरेर आफ्नो प्रश्न चार्टको गहिरो, व्यक्तिगत व्याख्या प्राप्त गर्नुहोस्। एआईले थप मार्गदर्शन प्रदान गर्न ग्रह स्थिति र योगहरूको विश्लेषण गर्नेछ।"),
    PRASHNA_GENERATE_AI_INSIGHT("Generate AI Insight", "एआई अन्तर्दृष्टि उत्पन्न गर्नुहोस्"),
    PRASHNA_AI_ANALYZING("Consulting the stars...", "ताराहरूसँग परामर्श गर्दै..."),
    PRASHNA_CONFIDENCE("%d%% confidence", "%d%% विश्वास"),

    // Prashna Yogas
    PRASHNA_YOGA_ITHASALA_NAME("Ithasala Yoga", "इत्थशाल योग"),
    PRASHNA_YOGA_ITHASALA_DESC("Moon is applying to aspect with relevant significator", "चन्द्रमा सम्बन्धित भावेशसँग दृष्टिको लागि अघि बढ्दैछ"),
    PRASHNA_YOGA_ITHASALA_INTERP("Success in the matter is indicated. The event will come to fruition.", "विषयमा सफलताको संकेत छ। घटना सफल हुनेछ।"),
    PRASHNA_YOGA_MUSARIPHA_NAME("Musaripha Yoga", "मुसरिफ योग"),
    PRASHNA_YOGA_MUSARIPHA_DESC("Moon is separating from significant aspect", "चन्द्रमा महत्त्वपूर्ण दृष्टिबाट टाढा हुँदैछ"),
    PRASHNA_YOGA_MUSARIPHA_INTERP("The matter has already passed or opportunity was missed.", "विषय पहिले नै बितिसकेको छ वा अवसर गुमेको छ।"),
    PRASHNA_YOGA_NAKTA_NAME("Nakta Yoga", "नक्त योग"),
    PRASHNA_YOGA_NAKTA_DESC("Transfer of light between planets", "ग्रहहरू बीच प्रकाशको हस्तान्तरण"),
    PRASHNA_YOGA_NAKTA_INTERP("Success through an intermediary or third party assistance.", "मध्यस्थ वा तेस्रो पक्षको सहयोग मार्फत सफलता।"),
    PRASHNA_YOGA_MANAOU_NAME("Manaou Yoga", "मनाऊ योग"),
    PRASHNA_YOGA_MANAOU_DESC("Third planet prohibits completion", "तेस्रो ग्रहले पूर्णतामा अवरोध गर्दछ"),
    PRASHNA_YOGA_MANAOU_INTERP("Third party or external factor will prevent success.", "तेस्रो पक्ष वा बाह्य कारकले सफलतामा अवरोध गर्नेछ।"),
    PRASHNA_YOGA_KAMBOOLA_NAME("Kamboola Yoga", "कम्बूल योग"),
    PRASHNA_YOGA_KAMBOOLA_DESC("Moon in angular house with lord", "चन्द्रमा भावेशसँग केन्द्र भावमा"),
    PRASHNA_YOGA_KAMBOOLA_INTERP("Very favorable for success. Quick positive results expected.", "सफलताको लागि धेरै अनुकूल। छिटो सकारात्मक नतिजाको अपेक्षा।"),
    PRASHNA_YOGA_GAIRI_KAMBOOLA_NAME("Gairi Kamboola", "गैर कम्बूल"),
    PRASHNA_YOGA_GAIRI_KAMBOOLA_DESC("Moon weak though in angular house", "चन्द्रमा केन्द्र भावमा भए पनि कमजोर छ"),
    PRASHNA_YOGA_GAIRI_KAMBOOLA_INTERP("Initial hopes but eventual disappointment. Success after delays.", "सुरुमा आशा तर अन्तमा निराशा। ढिलाइ पछि सफलता।"),
    PRASHNA_YOGA_DHURUFA_NAME("Dhurufa Yoga", "धुरुफ योग"),
    PRASHNA_YOGA_DHURUFA_DESC("Moon in cadent house without strength", "चन्द्रमा बल बिना आपोक्लिम भावमा"),
    PRASHNA_YOGA_DHURUFA_INTERP("Failure is indicated. Best to abandon the matter.", "असफलताको संकेत छ। विषय छोड्नु नै राम्रो हुनेछ।"),
    PRASHNA_YOGA_PUSHKARA_NAME("Pushkara Navamsha", "पुष्कर नवांश"),
    PRASHNA_YOGA_PUSHKARA_DESC("Moon in auspicious navamsha division", "चन्द्रमा शुभ नवांश विभाजनमा"),
    PRASHNA_YOGA_PUSHKARA_INTERP("Excellent omen. The matter will have nourishing, supportive outcomes.", "उत्कृष्ट शकुन। विषयमा पोषक, सहयोगी नतिजाहरू प्राप्त हुनेछन्।"),
    PRASHNA_YOGA_GANDANTA_NAME("Gandanta Position", "गण्डान्त स्थिति"),
    PRASHNA_YOGA_GANDANTA_DESC("Moon at junction point between water and fire signs", "चन्द्रमा जल र अग्नि राशि बीचको सन्धि बिन्दुमा"),
    PRASHNA_YOGA_GANDANTA_INTERP("Danger, crisis, or difficult transformation indicated.", "खतरा, संकट वा कठिन रूपान्तरणको संकेत।"),
    PRASHNA_YOGA_UNION_NAME("Lagna-Moon Union", "लग्न-चन्द्र युति"),
    PRASHNA_YOGA_UNION_DESC("Moon with Lagna Lord", "चन्द्रमा लग्न स्वामीसँग"),
    PRASHNA_YOGA_UNION_INTERP("Strong personal involvement and favorable outcome.", "बलियो व्यक्तिगत संलग्नता र अनुकूल परिणाम।"),

    // Prashna Factors
    PRASHNA_FACTOR_MOON_EXCELLENT("Moon is excellently placed - strong foundation for success", "चन्द्रमा उत्कृष्ट रूपमा राखिएको छ - सफलताको लागि बलियो आधार"),
    PRASHNA_FACTOR_MOON_GOOD("Moon is well placed - favorable conditions", "चन्द्रमा राम्ररी राखिएको छ - अनुकूल परिस्थितिहरू"),
    PRASHNA_FACTOR_MOON_AVERAGE("Moon is average - moderate indications", "चन्द्रमा औसत छ - मध्यम संकेतहरू"),
    PRASHNA_FACTOR_MOON_WEAK("Moon is weak - challenges indicated", "चन्द्रमा कमजोर छ - चुनौतीहरूको संकेत"),
    PRASHNA_FACTOR_MOON_VERY_WEAK("Moon is very weak - significant obstacles", "चन्द्रमा धेरै कमजोर छ - महत्त्वपूर्ण बाधाहरू"),
    PRASHNA_FACTOR_MOON_AFFLICTED("Moon is afflicted - unfavorable outcome likely", "चन्द्रमा पीडित छ - प्रतिकूल परिणामको सम्भावना"),
    PRASHNA_FACTOR_MOON_WAXING("Waxing Moon - matter will grow and develop", "शुक्ल पक्षको चन्द्रमा - विषय बढ्ने र विकसित हुनेछ"),
    PRASHNA_FACTOR_MOON_WANING("Waning Moon - matter may decline or diminish", "कृष्ण पक्षको चन्द्रमा - विषय घट्न वा कम हुन सक्छ"),
    PRASHNA_FACTOR_MOON_VOID("Moon is Void of Course - nothing will come of the matter", "चन्द्रमा शून्य छ - विषयबाट केही प्राप्त हुने छैन"),
    PRASHNA_FACTOR_LAGNA_STRONG("Strong Lagna Lord - querent has power to succeed", "बलियो लग्न स्वामी - प्रश्नकर्तासँग सफल हुने शक्ति छ"),
    PRASHNA_FACTOR_LAGNA_MODERATE("Moderately strong Lagna - mixed personal influence", "मध्यम बलियो लग्न - मिश्रित व्यक्तिगत प्रभाव"),
    PRASHNA_FACTOR_LAGNA_WEAK("Weak Lagna Lord - querent lacks resources or ability", "कमजोर लग्न स्वामी - प्रश्नकर्तासँग स्रोत वा क्षमताको अभाव छ"),
    PRASHNA_FACTOR_LAGNA_COMBUST("Combust Lagna Lord - querent's efforts are hidden or ineffective", "अस्त लग्न स्वामी - प्रश्नकर्ताका प्रयासहरू लुकेका वा प्रभावहीन छन्"),
    PRASHNA_FACTOR_LAGNA_RETRO("Retrograde Lagna Lord - delays and reconsideration needed", "वक्री लग्न स्वामी - ढिलाइ र पुनर्विचार आवश्यक छ"),
    PRASHNA_FACTOR_HOUSES_FAVORABLE("Relevant houses are favorably disposed", "सम्बन्धित भावहरू अनुकूल रूपमा व्यवस्थित छन्"),
    PRASHNA_FACTOR_HOUSES_AFFLICTED("Relevant houses show affliction", "सम्बन्धित भावहरूले पीडा देखाउँछन्"),

    // Prashna Recommendations
    PRASHNA_TIMING_ABOUT("About %s", "लगभग %s"),
    PRASHNA_TIMING_WITHIN("Within %s", "%s भित्र"),
    PRASHNA_TIMING_EXPLANATION("Based on %1\$s, the estimated timing is approximately %2\$s. Moon's current position and speed (%3\$s°/day) were primary factors in this calculation.", "%1\$s को आधारमा, अनुमानित समय लगभग %2\$s हो। चन्द्रमाको वर्तमान स्थिति र गति (%3\$s°/दिन) यस गणनामा प्राथमिक कारकहरू थिए।"),
    
    PRASHNA_REC_PROCEED("Proceed with confidence. The cosmic conditions are supportive.", "आत्मविश्वासका साथ अगाडि बढ्नुहोस्। ब्रह्माण्डीय परिस्थितिहरू सहयोगी छन्।"),
    PRASHNA_REC_CAUTION("Proceed with caution. Carefully monitor developments.", "सावधानीका साथ अगाडि बढ्नुहोस्। विकासक्रमहरूलाई ध्यानपूर्वक निगरानी गर्नुहोस्।"),
    PRASHNA_REC_DELAY("Expect delays. Patience and persistence will be required.", "ढिलाइको अपेक्षा गर्नुहोस्। धैर्य र दृढता आवश्यक हुनेछ।"),
    PRASHNA_REC_WAIT("Wait for a better time. Current conditions are not conducive to success.", "राम्रो समयको लागि प्रतीक्षा गर्नुहोस्। वर्तमान परिस्थितिहरू सफलताको लागि अनुकूल छैनन्।"),
    PRASHNA_REC_RETHINK("Rethink the approach. Significant obstacles or hidden factors detected.", "दृष्टिकोण पुनर्विचार गर्नुहोस्। महत्त्वपूर्ण बाधाहरू वा लुकेका कारकहरू पत्ता लागेका छन्।"),
    
    PRASHNA_REC_STRONGLY_SUPPORT("Proceed with confidence. The chart strongly supports your endeavor.", "आत्मविश्वासका साथ अगाडि बढ्नुहोस्। कुण्डलीले तपाईंको प्रयासलाई दृढतापूर्वक समर्थन गर्दछ।"),
    PRASHNA_REC_AWARENESS("Proceed with awareness. Minor adjustments may improve outcomes.", "सचेतताका साथ अगाडि बढ्नुहोस्। साना समायोजनहरूले नतिजाहरू सुधार्न सक्छन्।"),
    PRASHNA_REC_PATIENCE("Exercise patience. Wait for clearer signs before major action.", "धैर्य धारण गर्नुहोस्। ठूलो कदम चाल्नु अघि स्पष्ट संकेतहरूको प्रतीक्षा गर्नुहोस्।"),
    PRASHNA_REC_GUIDANCE("Seek additional guidance or information before proceeding.", "अगाडि बढ्नु अघि थप मार्गदर्शन वा जानकारी खोज्नुहोस्।"),
    PRASHNA_REC_VOID_MOON("The matter requires better timing. Moon is void of course.", "विषयको लागि राम्रो समय चाहिन्छ। चन्द्रमा शून्य (Void) छ।"),
    PRASHNA_REC_OBSTACLES("Expect obstacles. Success through struggle is possible but not guaranteed.", "बाधाहरूको अपेक्षा गर्नुहोस्। संघर्ष मार्फत सफलता सम्भव छ तर ग्यारेन्टी छैन।"),
    PRASHNA_REC_UNFAVORABLE("Unfavorable conditions. Reconsider your objectives or approach.", "अतुलनीय परिस्थितिहरू। आफ्ना उद्देश्यहरू वा दृष्टिकोण पुनर्विचार गर्नुहोस्।"),
    PRASHNA_REC_MUSARIPHA("The matter is already decided or opportunity has passed (Musaripha).", "विषय पहिले नै तय भइसकेको छ वा अवसर गुमेको छ (मुसरिफ)।"),
    PRASHNA_REC_VOID_MOON_REASK("Consider re-asking when Moon enters a new sign.", "चन्द्रमा नयाँ राशिमा प्रवेश गरेपछि फेरि सोध्ने विचार गर्नुहोस्।"),
    PRASHNA_REC_RECONSIDER("Reconsider your approach. Current conditions are not supportive.", "आफ्नो दृष्टिकोण पुनर्विचार गर्नुहोस्। वर्तमान परिस्थितिहरू सहयोगी छैनन्।"),
    PRASHNA_REC_EXPLORE("Explore alternative options or modify your plans.", "वैकल्पिक विकल्पहरू अन्वेषण गर्नुहोस् वा आफ्ना योजनाहरू परिमार्जन गर्नुहोस्।"),
    PRASHNA_REC_ABANDON("It is advisable to abandon or significantly modify this pursuit.", "यस प्रयासलाई छोड्नु वा महत्त्वपूर्ण रूपमा परिमार्जन गर्नु उचित हुन्छ।"),
    PRASHNA_REC_FOCUS_FAVORABLE("Focus energy on matters with more favorable indications.", "थप अनुकूल संकेत भएका विषयहरूमा ऊर्जा केन्द्रित गर्नुहोस्।"),
    PRASHNA_REC_WANING_MOON("Waning Moon suggests completion or ending phases. Good for finishing, not starting.", "कृष्ण पक्षको चन्द्रमाले समाप्ति वा अन्त्यको चरणलाई संकेत गर्दछ। सुरु गर्नका लागि होइन, समाप्त गर्नका लागि राम्रो छ।"),
    PRASHNA_REC_STRENGTHEN_MOON("Strengthen Moon energies through white colors, pearl, and Monday observances.", "सेतो रंग, मोती र सोमबारको व्रत मार्फत चन्द्रमाको ऊर्जालाई बलियो बनाउनुहोस्।"),
    PRASHNA_REC_AVOID_CONFRONTATION("Avoid direct confrontation. Work behind the scenes temporarily.", "प्रत्यक्ष टकरावबाट बच्नुहोस्। अस्थायी रूपमा पर्दा पछाडि काम गर्नुहोस्।"),
    PRASHNA_REC_REVIEW_PAST("Review past decisions. Something may need to be reconsidered.", "विगतका निर्णयहरू समीक्षा गर्नुहोस्। केही कुरामा पुनर्विचार गर्नुपर्ने हुन सक्छ।"),
    PRASHNA_REC_ACT_PROMPTLY("Act promptly while the favorable applying aspect is in effect.", "अनुकूल पक्ष प्रभावकारी हुँदा तुरुन्तै काम गर्नुहोस्।"),
    PRASHNA_REC_SEEK_ASSISTANCE("Seek assistance from an intermediary or third party.", "मध्यस्थ वा तेस्रो पक्षबाट सहयोग खोज्नुहोस्।"),
    PRASHNA_REC_PROPITIATE("Propitiate %1\$s to strengthen %2\$dth house matters.", "%2\$d औं भावको विषयलाई बलियो बनाउन %1\$s लाई प्रसन्न पार्नुहोस्।"),
    PRASHNA_REC_REASK_NEW_SIGN("Consider re-asking when Moon enters a new sign.", "चन्द्रमा नयाँ राशिमा प्रवेश गरेपछि पुनः सोध्ने विचार गर्नुहोस्।"),

    PRASHNA_SUMMARY_TITLE("Prashna Analysis Summary", "प्रश्न विश्लेषण सारांश"),
    PRASHNA_SUMMARY_MATTER("Matter", "विषय"),
    PRASHNA_SUMMARY_CATEGORY("Category", "श्रेणी"),
    PRASHNA_SUMMARY_TIME("Time", "समय"),
    PRASHNA_SUMMARY_CONFIDENCE("Confidence Score", "निश्चितता अंक"),

    PRASHNA_REPORT_TITLE("PRASHNA ANALYSIS REPORT", "प्रश्न विश्लेषण रिपोर्ट"),
    PRASHNA_REPORT_QUESTION("QUESTION", "प्रश्न"),
    PRASHNA_REPORT_CATEGORY("CATEGORY", "श्रेणी"),
    PRASHNA_REPORT_VERDICT("VERDICT", "निर्णय"),
    PRASHNA_REPORT_CERTAINTY("Certainty", "निश्चितता"),
    PRASHNA_REPORT_PRIMARY_INDICATION("PRIMARY INDICATION", "प्राथमिक संकेत"),
    PRASHNA_REPORT_MOON_ANALYSIS("MOON ANALYSIS (Primary Significator)", "चन्द्र विश्लेषण (प्राथमिक कारक)"),
    PRASHNA_REPORT_POSITION("Position", "स्थिति"),
    PRASHNA_REPORT_HOUSE("House", "भाव"),
    PRASHNA_REPORT_NAKSHATRA("Nakshatra", "नक्षत्र"),
    PRASHNA_REPORT_PADA("Pada", "पद"),
    PRASHNA_REPORT_PHASE("Phase", "पक्ष"),
    PRASHNA_REPORT_WAXING("Waxing", "शुक्ल पक्ष"),
    PRASHNA_REPORT_WANING("Waning", "कृष्ण पक्ष"),
    PRASHNA_REPORT_STRENGTH("Strength", "बल"),
    PRASHNA_REPORT_WARNING_VOID("WARNING: Moon is Void of Course", "चेतावनी: चन्द्रमा शून्य (Void) छ"),
    PRASHNA_REPORT_LAGNA_ANALYSIS("LAGNA ANALYSIS", "लग्न विश्लेषण"),
    PRASHNA_REPORT_RISING_SIGN("Rising Sign", "उदय लग्न"),
    PRASHNA_REPORT_LAGNA_LORD("Lagna Lord", "लग्न स्वामी"),
    PRASHNA_REPORT_CONDITION("Condition", "स्थिति"),
    PRASHNA_REPORT_RELEVANT_HOUSES("RELEVANT HOUSES", "सम्बन्धित भावहरू"),
    PRASHNA_REPORT_YOGA_ANALYSIS("YOGA ANALYSIS (Tajika Yogas)", "योग विश्लेषण (ताजिक योग)"),
    PRASHNA_REPORT_TIMING_PREDICTION("TIMING PREDICTION", "समय भविष्यवाणी"),
    PRASHNA_REPORT_TIMING_EXPLANATION("Timing Explanation", "समय व्याख्या"),
    PRASHNA_REPORT_RECOMMENDATIONS("RECOMMENDATIONS", "सिफारिसहरू"),

    // ============================================
    // VARSHAPHALA - TRI-PATAKI CHAKRA
    // ============================================
    TRI_PATAKI_DHARMA("Dharma Sector", "धर्म क्षेत्र"),
    TRI_PATAKI_ARTHA("Artha Sector", "अर्थ क्षेत्र"),
    TRI_PATAKI_KAMA("Kama Sector", "काम क्षेत्र"),
    TRI_PATAKI_DHARMA_DESC("Focus on purpose, spirituality, and duties.", "उद्देश्य, आध्यात्मिकता र कर्तव्यहरूमा ध्यान।"),
    TRI_PATAKI_ARTHA_DESC("Focus on material success, career, and stability.", "भौतिक सफलता, क्यारियर र स्थिरतामा ध्यान।"),
    TRI_PATAKI_KAMA_DESC("Focus on desires, relationships, and social life.", "इच्छाहरू, सम्बन्धहरू र सामाजिक जीवनमा ध्यान।"),
    TRI_PATAKI_BALANCED("Balanced Influence", "सन्तुलित प्रभाव"),
    TRI_PATAKI_QUIET("Quiet Period", "शान्त अवधि"),
    TRI_PATAKI_FAVORABLE("Favorable Activation", "अनुकूल सक्रियता"),
    TRI_PATAKI_CHALLENGING("Challenging Activation", "चुनौतीपूर्ण सक्रियता"),
    TRI_PATAKI_VARIABLE("Variable Energy", "परिवर्तनशील ऊर्जा"),
    TRI_PATAKI_DHARMA_AREA("spiritual or professional duties", "आध्यात्मिक वा व्यावसायिक कर्तव्यहरू"),
    TRI_PATAKI_ARTHA_AREA("financial or material growth", "आर्थिक वा भौतिक विकास"),
    TRI_PATAKI_KAMA_AREA("desires and social connections", "इच्छाहरू र सामाजिक सम्बन्धहरू"),
    TRI_PATAKI_EMPHASIS("Strong emphasis on %s.", "%s मा कडा जोड।"),
    TRI_PATAKI_BALANCED_DESC("Energy is evenly distributed across life areas.", "जीवनका क्षेत्रहरूमा ऊर्जा समान रूपमा वितरित छ।"),

    // ============================================
    // VARSHAPHALA - SAHAMS & TAJIKA ASPECTS
    // ============================================
    VARSHA_SAHAM_RELATES("relates to %s", "%s सँग सम्बन्धित छ"),
    VARSHA_SAHAM_LORD_SUPPORT("The lord %s provides %s support.", "स्वामी %s ले %s समर्थन प्रदान गर्दछ।"),
    VARSHA_TONE_SUPPORTIVE("supportive", "सहयोगी"),
    TAJIKA_ITHASALA_EFFECT("Ithasala (Favorable) - indicates fruition and success through active efforts.", "इत्थशाल (अनुकूल) - सक्रिय प्रयासहरू मार्फत फल प्राप्ति र सफलता संकेत गर्दछ।"),
    TAJIKA_EASARAPHA_EFFECT("Easarapha (Separation) - indicates matters passing or moving away from fruition.", "ईशराफ (विभाजन) - विषयहरू पूरा हुनबाट टाढा जाने संकेत गर्दछ।"),
    TAJIKA_KAMBOOLA_EFFECT("Kamboola (Strong) - indicates very strong and certain results.", "कम्बूल (बलियो) - धेरै बलियो र निश्चित नतिजाहरू संकेत गर्दछ।"),
    TAJIKA_RADDA_EFFECT("Radda (Obstruction) - indicates unexpected obstacles or reversals.", "रद्द (बाधा) - अप्रत्याशित बाधाहरू वा उल्टो नतिजाहरू संकेत गर्दछ।"),
    TAJIKA_DURAPHA_EFFECT("Durapha (Weak) - indicates weak results or lack of clear direction.", "दुराफ (कमजोर) - कमजोर नतिजाहरू वा स्पष्ट दिशाको अभाव संकेत गर्दछ।"),
    TAJIKA_INFLUENCE_ENERGY("This aspect brings %s energy to %s matters.", "यो दृष्टिले %s मामिलामा %s ऊर्जा ल्याउँछ।"),
    TAJIKA_PREDICTION_X_FOR_Y("%s for %s", "%s को लागि %s"),

    // ============================================
    // VARSHAPHALA - DASHA PREDICTIONS
    // ============================================
    VARSHA_DASHA_PERIOD_FORMAT("%s Period: %s to %s", "%s अवधि: %s देखि %s"),
    PLANET_NATURE_SUN("vitality and authority", "जीवनशक्ति र अधिकार"),
    PLANET_NATURE_MOON("emotional connection and growth", "भावनात्मक सम्बन्ध र विकास"),
    PLANET_NATURE_MARS("energy and initiative", "ऊर्जा र पहल"),
    PLANET_NATURE_MERCURY("communication and skills", "सञ्चार र सीप"),
    PLANET_NATURE_JUPITER("wisdom and opportunity", "बुद्धि र अवसर"),
    PLANET_NATURE_VENUS("creativity and harmony", "सिर्जनशीलता र सद्भाव"),
    PLANET_NATURE_SATURN("discipline and persistence", "अनुशासन र दृढता"),
    PLANET_NATURE_RAHU("ambition and innovation", "महत्त्वाकांक्षा र नवीनता"),
    PLANET_NATURE_KETU("spiritual depth and detachment", "आध्यात्मिक गहिराइ र वैराग्य"),
    VARSHA_DASHA_EXCEPTIONAL("An exceptional period for %s, bringing significant rewards.", "%s को लागि एक असाधारण अवधि, महत्त्वपूर्ण पुरस्कार ल्याउँदै।"),
    VARSHA_DASHA_SUPPORTED("A well-supported time for %s and positive developments.", "%s र सकारात्मक विकासका लागि राम्रो समर्थन भएको समय।"),
    VARSHA_DASHA_CHALLENGING("A challenging phase for %s requiring extra effort and caution.", "अतिरिक्त प्रयास र सावधानी आवश्यक पर्ने %s को लागि एक चुनौतीपूर्ण चरण।"),
    VARSHA_DASHA_MIXED("A mixed period for %s with both opportunities and minor hurdles.", "अवसर र साना बाधाहरू दुवै भएको %s को लागि एक मिश्रित अवधि।"),
    VARSHA_DASHA_PREDICTION_FORMAT("%s focuses on %s. %s", "%s ले %s मा ध्यान केन्द्रित गर्दछ। %s"),

    // ============================================
    // VARSHAPHALA - KEYWORDS & STRENGTH
    // ============================================
    KEYWORD_LEADERSHIP("Leadership", "नेतृत्व"),
    KEYWORD_VITALITY("Vitality", "प्राणशक्ति"),
    KEYWORD_FATHER("Father", "पिता"),
    KEYWORD_EMOTIONS("Emotions", "भावना"),
    KEYWORD_MOTHER("Mother", "माता"),
    KEYWORD_PUBLIC("Public", "सार्वजनिक"),
    KEYWORD_ACTION("Action", "कार्य"),
    KEYWORD_ENERGY("Energy", "ऊर्जा"),
    KEYWORD_COURAGE("Courage", "साहस"),
    KEYWORD_COMMUNICATION("Communication", "सञ्चार"),
    KEYWORD_LEARNING("Learning", "सिकाइ"),
    KEYWORD_BUSINESS("Business", "व्यापार"),
    KEYWORD_WISDOM("Wisdom", "ज्ञान"),
    KEYWORD_GROWTH("Growth", "विकास"),
    KEYWORD_FORTUNE("Fortune", "भाग्य"),
    KEYWORD_LOVE("Love", "प्रेम"),
    KEYWORD_ART("Art", "कला"),
    KEYWORD_COMFORT("Comfort", "आराम"),
    KEYWORD_DISCIPLINE("Discipline", "अनुशासन"),
    KEYWORD_KARMA("Karma", "कर्म"),
    KEYWORD_DELAYS("Delays", "ढिलाइ"),
    KEYWORD_AMBITION("Ambition", "महत्त्वाकांक्षा"),
    KEYWORD_INNOVATION("Innovation", "नवीनता"),
    KEYWORD_FOREIGN("Foreign", "विदेशी"),
    KEYWORD_SPIRITUALITY("Spirituality", "आध्यात्मिकता"),
    KEYWORD_DETACHMENT("Detachment", "वैराग्य"),
    KEYWORD_PAST("Past", "अतीत"),
    KEYWORD_GENERAL("General", "सामान्य"),
    VARSHA_STRENGTH_EXCELLENT("Excellent", "उत्कृष्ट"),
    VARSHA_STRENGTH_WEAK("Weak", "कमजोर"),
    VARSHA_STRENGTH_CHALLENGED("Challenged", "चुनौतीपूर्ण"),
    KEYWORD_SELF("Self", "स्वयम्"),
    KEYWORD_PERSONALITY("Personality", "व्यक्तित्व"),
    KEYWORD_HEALTH("Health", "स्वास्थ्य"),
    KEYWORD_APPEARANCE("Appearance", "रूप"),
    KEYWORD_NEW_BEGINNINGS("New Beginnings", "नयाँ शुरुवात"),
    KEYWORD_WEALTH("Wealth", "धन"),
    KEYWORD_FAMILY("Family", "परिवार"),
    KEYWORD_SPEECH("Speech", "वाणी"),
    KEYWORD_VALUES("Values", "मूल्य मान्यता"),
    KEYWORD_FOOD("Food", "भोजन"),
    KEYWORD_SIBLINGS("Siblings", "भाइबहिनी"),
    KEYWORD_SHORT_TRAVEL("Short Travel", "छोटो यात्रा"),
    KEYWORD_SKILLS("Skills", "सीप"),
    KEYWORD_HOME("Home", "घर"),
    KEYWORD_PROPERTY("Property", "सम्पत्ति"),
    KEYWORD_VEHICLES("Vehicles", "सवारी साधन"),
    KEYWORD_INNER_PEACE("Inner Peace", "आन्तरिक शान्ति"),
    KEYWORD_CHILDREN("Children", "सन्तान"),
    KEYWORD_INTELLIGENCE("Intelligence", "बुद्धि"),
    KEYWORD_ROMANCE("Romance", "रोमान्स"),
    KEYWORD_CREATIVITY("Creativity", "सिर्जनशीलता"),
    KEYWORD_INVESTMENTS("Investments", "लगानी"),
    KEYWORD_ENEMIES("Enemies", "शत्रु"),
    KEYWORD_HEALTH_ISSUES("Health Issues", "स्वास्थ्य समस्या"),
    KEYWORD_SERVICE("Service", "सेवा"),
    KEYWORD_DEBTS("Debts", "ऋण"),
    KEYWORD_COMPETITION("Competition", "प्रतिस्पर्धा"),
    KEYWORD_MARRIAGE("Marriage", "विवाह"),
    KEYWORD_PARTNERSHIP("Partnership", "साझेदारी"),
    KEYWORD_PUBLIC_DEALINGS("Public Dealings", "सार्वजनिक कारोबार"),
    KEYWORD_CONTRACTS("Contracts", "सम्झौता"),
    KEYWORD_LONGEVITY("Longevity", "दीर्घायु"),
    KEYWORD_TRANSFORMATION("Transformation", "रूपान्तरण"),
    KEYWORD_RESEARCH("Research", "अनुसन्धान"),
    KEYWORD_INHERITANCE("Inheritance", "विरासत"),
    KEYWORD_HIDDEN_MATTERS("Hidden Matters", "लुकेका कुराहरू"),
    KEYWORD_RELIGION("Religion", "धर्म"),
    KEYWORD_HIGHER_EDUCATION("Higher Education", "उच्च शिक्षा"),
    KEYWORD_LONG_TRAVEL("Long Travel", "लामो यात्रा"),
    KEYWORD_CAREER("Career", "क्यारियर"),
    KEYWORD_STATUS("Status", "स्थिति"),
    KEYWORD_AUTHORITY("Authority", "अधिकार"),
    KEYWORD_GOVERNMENT("Government", "सरकार"),
    KEYWORD_FAME("Fame", "नाम"),
    KEYWORD_GAINS("Gains", "लाभ"),
    KEYWORD_INCOME("Income", "आय"),
    KEYWORD_FRIENDS("Friends", "मित्रहरू"),
    KEYWORD_ELDER_SIBLINGS("Elder Siblings", "ठूला भाइबहिनी"),
    KEYWORD_ASPIRATIONS("Aspirations", "आंकाक्षा"),
    KEYWORD_LOSSES("Losses", "हानि"),
    KEYWORD_EXPENSES("Expenses", "खर्च"),
    KEYWORD_FOREIGN_LANDS("Foreign Lands", "विदेशी भूमि"),
    KEYWORD_LIBERATION("Liberation", "मुक्ति"),

    // ============================================
    // VARSHAPHALA - HOUSE PREDICTIONS
    // ============================================
    VARSHA_HOUSE_LORD_POSITION("The house lord %s is positioned in H%d.", "भाव स्वामी %s भाव%d मा स्थित छ।"),
    VARSHA_HOUSE_LORD_EXCELLENT("This position is exceptionally strong, promising major success in %s.", "यो स्थिति असाधारण रूपमा बलियो छ, जसले %s मा ठूलो सफलताको वाचा गर्दछ।"),
    VARSHA_HOUSE_LORD_STRONG("The lord is well-placed, supporting steady growth and positive results in %s.", "स्वामी राम्रो स्थानमा छ, जसले %s मा स्थिर विकास र सकारात्मक नतिजाहरूलाई समर्थन गर्दछ।"),
    VARSHA_HOUSE_LORD_MODERATE("The lord's position is moderate, indicating balanced results for %s.", "स्वामीको स्थिति मध्यम छ, जसले %s को लागि सन्तुलित नतिजाहरू संकेत गर्दछ।"),
    VARSHA_HOUSE_LORD_CHALLENGED("The lord faces challenges here, requiring caution and extra effort regarding %s.", "स्वामीले यहाँ चुनौतीहरूको सामना गर्नु पर्छ, जसले %s को सम्बन्धमा सावधानी र अतिरिक्त प्रयासको आवश्यकता देखाउँछ।"),
    VARSHA_HOUSE_LORD_VARIABLE("Current placement indicates variable or inconsistent results for %s.", "वर्तमान स्थितिले %s को लागि परिवर्तनशील वा अस्थिर नतिजाहरू संकेत गर्दछ।"),
    VARSHA_HOUSE_BENEFICS_ENHANCE("Presence of benefic planets enhances the themes of %s.", "शुभ ग्रहहरूको उपस्थितिले %s का विषयहरूलाई सुधार्छ।"),
    VARSHA_HOUSE_MALEFICS_CHALLENGE("Presence of malefic planets may challenge the stability of %s.", "पाप ग्रहहरूको उपस्थितिले %s को स्थिरतालाई चुनौती दिन सक्छ।"),
    VARSHA_HOUSE_MIXED_INF("Mixed planetary influences suggest a period of ups and downs for %s.", "मिश्रित ग्रह प्रभावले %s को लागि उतारचढावको अवधि संकेत गर्दछ।"),
    VARSHA_HOUSE_LORD_DEPENDENT("Success in %s depends heavily on other planetary configurations.", "%s मा सफलता धेरै हदसम्म अन्य ग्रह विन्यासमा निर्भर गर्दछ।"),
    VARSHA_HOUSE_MUNTHA_EMPHASIS("Muntha's presence here significantly increases the priority of %s this year.", "यहाँ मुन्थको उपस्थितिले यस वर्ष %s को प्राथमिकतालाई महत्त्वपूर्ण रूपमा बढाउँछ।"),
    VARSHA_HOUSE_YEARLORD_RULE("Year Lord's rule over this house elevates the importance of %s matters.", "यस भावमा वर्ष स्वामीको शासनले %s मामिलाहरूको महत्त्व बढाउँछ।"),
    VARSHA_HOUSE_PREDICTION_FORMAT("House %d Overview: %s. %s", "भाव %d अवलोकन: %s। %s"),

    // ============================================
    // VARSHAPHALA - EVENTS & THEMES
    // ============================================
    VARSHA_EVENT_SPIRITUAL_GROWTH("Spiritual growth and self-discovery", "आध्यात्मिक विकास र आत्म-खोज"),
    VARSHA_EVENT_INCREASED_ENERGY("Increased energy and dynamic activity", "बढेको ऊर्जा र गतिशील गतिविधि"),
    VARSHA_EVENT_FINANCIAL_GAINS("Significant financial gains or new income sources", "महत्त्वपूर्ण आर्थिक लाभ वा नयाँ आय स्रोत"),
    VARSHA_EVENT_FAMILY_RELATIONS("Strengthening of family bonds and values", "पारिवारिक सम्बन्ध र मूल्य मान्यताको सुदृढीकरण"),
    VARSHA_EVENT_LUXURY_ACQUISITION("Acquisition of luxury items or increased comforts", "लक्जरी वस्तुहरूको प्राप्ति वा बढेको सुखसुविधा"),
    VARSHA_EVENT_CREATIVE_SUCCESS("Success in creative pursuits and self-expression", "सिर्जनात्मक प्रयास र आत्म-अभिव्यक्तिमा सफलता"),
    VARSHA_EVENT_ACADEMIC_SUCCESS("Academic success or new learning opportunities", "शैक्षिक सफलता वा नयाँ सिकाइका अवसरहरू"),
    VARSHA_EVENT_ROMANTIC_HAPPINESS("Happiness in romantic relationships or with children", "रोमान्टिक सम्बन्ध वा सन्तानसँगको खुशी"),
    VARSHA_EVENT_PARTNERSHIP_STRENGTH("Strengthening of business or personal partnerships", "व्यापार वा व्यक्तिगत साझेदारीको सुदृढीकरण"),
    VARSHA_EVENT_MARRIAGE_FAVORABLE("Favorable conditions for marriage or long-term commitments", "विवाह वा दीर्घकालीन प्रतिबद्धताको लागि अनुकूल परिस्थिति"),
    VARSHA_EVENT_ROMANTIC_FULFILLMENT("Emotional fulfillment through significant others", "महत्त्वपूर्ण व्यक्तिहरू मार्फत भावनात्मक सन्तुष्टि"),
    VARSHA_EVENT_CAREER_ADVANCEMENT("Advancement in career or professional recognition", "क्यारियरमा प्रगति वा व्यावसायिक मान्यता"),
    VARSHA_EVENT_AUTHORITY_RECOGNITION("Gain of authority, fame, or social status", "अधिकार, नाम वा सामाजिक स्थितिको प्राप्ति"),
    VARSHA_EVENT_GOVERNMENT_FAVOR("Favor from government or influential figures", "सरकार वा प्रभावशाली व्यक्तिहरूबाट सहयोग"),
    VARSHA_EVENT_DESIRE_FULFILLMENT("Fulfillment of long-held desires and ambitions", "लामो समयदेखिका इच्छाहरू र महत्त्वाकांक्षाहरूको पूर्ति"),
    VARSHA_THEME_YEARLORD("Year Lord (%s Influence)", "वर्ष स्वामी (%s प्रभाव)"),
    VARSHA_THEME_MUNTHA("Muntha Focus (%s)", "मुन्थ फोकस (%s)"),
    VARSHA_THEME_TRIPATAKI("Tri-Pataki Activation in %s", "%s मा त्रिपताकी सक्रियता"),
    VARSHA_THEME_FAVORABLE("Favorable year for overall progress", "समग्र प्रगतिको लागि अनुकूल वर्ष"),
    VARSHA_THEME_TAJIKA("Tajika Aspect Influence: %s", "ताजिक दृष्टि प्रभाव: %s"),
    VARSHA_EVENT_SOLAR_RETURN("Solar Return Activation", "सौर्य फिर्ती सक्रियता"),
    VARSHA_EVENT_SOLAR_RETURN_DESC("The precise moment the Sun returns to your birth position.", "सूर्य तपाईंको जन्म स्थितिमा फर्कने सटीक क्षण।"),
    VARSHA_EVENT_DASHA_BEGINS("%s Dasha Begins", "%s दशा सुरु हुन्छ"),
    VARSHA_EVENT_DASHA_BEGINS_DESC("A minor period of %s influence starts today.", "%s प्रभावको एक सानो अवधि आजदेखि सुरु हुन्छ।"),
    VARSHA_TONE_CHALLENGING_GROWTH("challenging but growth-oriented", "चुनौतीपूर्ण तर विकास उन्मुख"),
    VARSHA_YEARLORD_GENERAL("The year is ruled by %s, bringing %s focus. ", "यो वर्ष %s द्वारा शासित छ, जसले %s ध्यान ल्याउँछ।"),
    VARSHA_MUNTHA_INFLUENCE("Muntha in %s suggests emphasis on %s. ", "%s मा मुन्थले %s मा जोड दिने संकेत गर्दछ।"),
    VARSHA_OVERALL_TONE("Overall, the year appears to be %s. ", "समग्रमा, वर्ष %s देखिन्छ।"),
    VARSHA_ASPECT_SUMMARY("Planetary aspects highlight %s. ", "ग्रहका दृष्टिहरूले %s लाई उजागर गर्दछ।"),
    VARSHA_POTENTIAL_MAXIMIZED("Success can be maximized by focusing on %s.", "%s मा ध्यान केन्द्रित गरेर सफलतालाई अधिकतम बनाउन सकिन्छ।"),
    VARSHA_HOUSE_GENERAL_SIG("general life matters", "सामान्य जीवनका मामिलाहरू");
}
