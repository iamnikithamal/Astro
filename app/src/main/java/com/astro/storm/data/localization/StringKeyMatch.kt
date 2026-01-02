package com.astro.storm.data.localization


/**
 * Matchmaking and Guna string keys
 * Part 2 of 4 split enums to avoid JVM method size limit
 */
enum class StringKeyMatch(override val en: String, override val ne: String) : StringKeyInterface {
    // ============================================
    // MATCHMAKING
    // ============================================
    MATCH_TITLE("Kundli Milan", "कुण्डली मिलान"),
    MATCH_SELECT_PROFILES("Select Profiles", "प्रोफाइलहरू छान्नुहोस्"),
    MATCH_PERSON_1("Person 1", "व्यक्ति १"),
    MATCH_PERSON_2("Person 2", "व्यक्ति २"),
    MATCH_CALCULATE("Calculate Match", "मिलान गणना गर्नुहोस्"),
    MATCH_TOTAL_POINTS("Total Points", "कुल अंकहरू"),
    MATCH_OUT_OF("out of", "मध्ये"),
    MATCH_COMPATIBILITY("Compatibility", "अनुकूलता"),
    MATCH_EXCELLENT("Excellent", "उत्कृष्ट"),
    MATCH_GOOD("Good", "राम्रो"),
    MATCH_AVERAGE("Average", "औसत"),
    MATCH_BELOW_AVERAGE("Below Average", "औसतमुनि"),

    // Matchmaking Tabs & Sections
    MATCH_OVERVIEW("Overview", "अवलोकन"),
    MATCH_GUNAS("Gunas", "गुणहरू"),
    MATCH_DOSHAS("Doshas", "दोषहरू"),
    MATCH_NAKSHATRAS("Nakshatras", "नक्षत्रहरू"),
    MATCH_SELECT_BRIDE("Select Bride", "वधू छान्नुहोस्"),
    MATCH_SELECT_GROOM("Select Groom", "वर छान्नुहोस्"),
    MATCH_SWAP_PROFILES("Swap Profiles", "प्रोफाइलहरू स्वाप गर्नुहोस्"),
    MATCH_ASHTAKOOTA("Ashtakoota Points", "अष्टकूट अंकहरू"),
    MATCH_SHARE_REPORT("Share Report", "रिपोर्ट साझा गर्नुहोस्"),
    MATCH_COPY_REPORT("Copy Report", "रिपोर्ट कपी गर्नुहोस्"),
    MATCH_SHARE_AS_TEXT("Share as Text", "पाठको रूपमा साझा गर्नुहोस्"),
    MATCH_SHARE_AS_IMAGE("Share as Image", "छविको रूपमा साझा गर्नुहोस्"),
    MATCH_CALCULATING("Calculating compatibility...", "अनुकूलता गणना गर्दै..."),
    MATCH_NO_PROFILES("No profiles available", "कुनै प्रोफाइल उपलब्ध छैन"),
    MATCH_SELECT_BOTH("Please select both profiles", "कृपया दुवै प्रोफाइल छान्नुहोस्"),

    // Additional Matchmaking Entries
    MATCH_REMEDIES("Remedies", "उपायहरू"),
    MATCH_COPIED_TO_CLIPBOARD("Report copied to clipboard", "रिपोर्ट क्लिपबोर्डमा कपी भयो"),
    MATCH_CLEAR_SELECTION("Clear Selection", "छनौट हटाउनुहोस्"),
    MATCH_BRIDE("Bride", "वधू"),
    MATCH_GROOM("Groom", "वर"),
    MATCH_CREATE_CHARTS_FIRST("Create birth charts first to use matchmaking", "मिलान प्रयोग गर्न पहिले जन्म कुण्डली बनाउनुहोस्"),
    MATCH_TAP_TO_SELECT("Tap to select", "छान्न ट्याप गर्नुहोस्"),
    MATCH_CONNECTED("Connected", "जोडिएको"),
    MATCH_NOT_CONNECTED("Not Connected", "जोडिएको छैन"),
    MATCH_ANALYZING_COMPATIBILITY("Analyzing compatibility...", "अनुकूलता विश्लेषण गर्दै..."),
    MATCH_CALCULATING_DOSHAS("Calculating doshas...", "दोषहरू गणना गर्दै..."),
    MATCH_CALCULATION_ERROR("Calculation Error", "गणना त्रुटि"),
    MATCH_MANGLIK("Manglik", "मांगलिक"),
    MATCH_NADI("Nadi", "नाडी"),
    MATCH_DOSHA_PRESENT("Dosha Present", "दोष उपस्थित"),
    MATCH_BHAKOOT("Bhakoot", "भकूट"),
    MATCH_NEEDS_ATTENTION("Needs Attention", "ध्यान आवश्यक"),
    MATCH_GUNA_DISTRIBUTION("Guna Distribution", "गुण वितरण"),
    MATCH_PROFILE_COMPARISON("Profile Comparison", "प्रोफाइल तुलना"),
    MATCH_AI_INSIGHT("AI Insight", "एआई अन्तर्दृष्टि"),
    MATCH_AI_INSIGHT_SUBTITLE("Powered by Stormy", "Stormy द्वारा संचालित"),
    MATCH_AI_INSIGHT_DESC("Get a deeper, personalized interpretation of your compatibility analysis using AI. The AI will analyze the Guna scores and Doshas to provide additional relationship guidance.", "एआई प्रयोग गरेर आफ्नो अनुकूलता विश्लेषणको गहिरो, व्यक्तिगत व्याख्या प्राप्त गर्नुहोस्। एआईले थप सम्बन्ध मार्गदर्शन प्रदान गर्न गुण स्कोर र दोषहरूको विश्लेषण गर्नेछ।"),
    MATCH_GENERATE_AI_INSIGHT("Generate AI Insight", "एआई अन्तर्दृष्टि उत्पन्न गर्नुहोस्"),
    MATCH_AI_ANALYZING("Analyzing compatibility...", "अनुकूलता विश्लेषण गर्दै..."),
    MATCH_VS("vs", "विरुद्ध"),
    MATCH_MOON_SIGN("Moon Sign", "चन्द्र राशि"),
    MATCH_NAKSHATRA("Nakshatra", "नक्षत्र"),
    MATCH_PADA("Pada", "पद"),
    MATCH_PADA_NUMBER("Pada %d", "पद %d"),
    MATCH_KEY_CONSIDERATIONS("Key Considerations", "मुख्य विचारहरू"),
    MATCH_FAVORABLE("Favorable", "अनुकूल"),
    MATCH_TOTAL_SCORE("Total Score", "कुल अंक"),
    MATCH_MANGLIK_DOSHA_ANALYSIS("Manglik Dosha Analysis", "मांगलिक दोष विश्लेषण"),
    MATCH_MARS_PLACEMENT("Mars Placement", "मंगल स्थिति"),
    MATCH_MARS_IN_HOUSE("Mars in House %d", "भाव %d मा मंगल"),
    MATCH_CONTRIBUTING_FACTORS("Contributing Factors", "योगदान गर्ने कारकहरू"),
    MATCH_CANCELLATION_FACTORS("Cancellation Factors", "रद्द गर्ने कारकहरू"),
    MATCH_NADI_DOSHA("Nadi Dosha", "नाडी दोष"),
    MATCH_HEALTH_PROGENY("Health & Progeny", "स्वास्थ्य र सन्तान"),
    MATCH_PRESENT("Present", "उपस्थित"),
    MATCH_ABSENT("Absent", "अनुपस्थित"),
    MATCH_NADI_WARNING("Same Nadi can indicate health and progeny concerns", "एउटै नाडीले स्वास्थ्य र सन्तान सम्बन्धी चिन्ता संकेत गर्न सक्छ"),
    MATCH_BHAKOOT_DOSHA("Bhakoot Dosha", "भकूट दोष"),
    MATCH_FINANCIAL_HARMONY("Financial Harmony", "आर्थिक सामञ्जस्य"),
    MATCH_BRIDE_RASHI("Bride Rashi", "वधू राशि"),
    MATCH_GROOM_RASHI("Groom Rashi", "वर राशि"),
    MATCH_NAKSHATRA_COMPATIBILITY("Nakshatra Compatibility", "नक्षत्र अनुकूलता"),
    MATCH_BIRTH_STAR("Birth Star", "जन्म नक्षत्र"),
    MATCH_BIRTH_NAKSHATRA("Birth Nakshatra", "जन्म नक्षत्र"),
    MATCH_NAKSHATRA_LORD("Nakshatra Lord", "नक्षत्र स्वामी"),
    MATCH_GANA("Gana", "गण"),
    MATCH_YONI("Yoni", "योनि"),
    MATCH_RAJJU_MATCHING("Rajju Matching", "रज्जु मिलान"),
    MATCH_LONGEVITY("Longevity", "दीर्घायु"),
    MATCH_CONFLICT("Conflict", "द्वन्द्व"),
    MATCH_COMPATIBLE("Compatible", "अनुकूल"),
    MATCH_RAJJU_DESCRIPTION("Rajju indicates the body parts and their compatibility in marriage.", "रज्जुले शरीरका अंगहरू र विवाहमा तिनीहरूको अनुकूलता संकेत गर्दछ।"),
    MATCH_VEDHA_ANALYSIS("Vedha Analysis", "वेध विश्लेषण"),
    MATCH_OBSTRUCTION_CHECK("Obstruction Check", "बाधा जाँच"),
    MATCH_NONE("None", "कुनै पनि छैन"),
    MATCH_VEDHA_DESCRIPTION("Vedha indicates mutual affliction between nakshatras.", "वेधले नक्षत्रहरू बीचको पारस्परिक पीडा संकेत गर्दछ।"),
    MATCH_STREE_DEERGHA("Stree Deergha", "स्त्री दीर्घ"),
    MATCH_PROSPERITY_FACTORS("Prosperity Factors", "समृद्धि कारकहरू"),
    MATCH_STREE_DEERGHA_LABEL("Stree Deergha", "स्त्री दीर्घ"),
    MATCH_MAHENDRA("Mahendra", "महेन्द्र"),
    MATCH_BENEFICIAL("Beneficial", "लाभदायक"),
    MATCH_SUGGESTED_REMEDIES("Suggested Remedies", "सुझाव गरिएका उपायहरू"),
    MATCH_VEDIC_RECOMMENDATIONS("Vedic Recommendations", "वैदिक सिफारिसहरू"),
    MATCH_REMEDIES_DISCLAIMER("Remedies should be performed under guidance of a qualified astrologer.", "उपायहरू योग्य ज्योतिषीको मार्गदर्शनमा गर्नुपर्छ।"),
    MATCH_NO_CHARTS("No Charts", "कुनै कुण्डली छैन"),
    MATCH_SELECT_BRIDE_PROFILE("Select bride profile", "वधू प्रोफाइल छान्नुहोस्"),
    MATCH_SELECT_GROOM_PROFILE("Select groom profile", "वर प्रोफाइल छान्नुहोस्"),
    MATCH_PREPARING_ANALYSIS("Preparing analysis...", "विश्लेषण तयार गर्दै..."),
    MATCH_CREATE_CHARTS("Create Charts", "कुण्डली बनाउनुहोस्"),
    MATCH_SELECT_TAP_CARDS("Select by tapping cards", "कार्डहरू ट्याप गरेर छान्नुहोस्"),
    MATCH_TAP_BRIDE_CARD("Tap bride card to select", "छान्न वधू कार्ड ट्याप गर्नुहोस्"),
    MATCH_TAP_GROOM_CARD("Tap groom card to select", "छान्न वर कार्ड ट्याप गर्नुहोस्"),
    MATCH_CHARTS_AVAILABLE("%d charts available", "%d कुण्डली उपलब्ध"),
    MATCH_NO_CHARTS_AVAILABLE("No charts available", "कुनै कुण्डली उपलब्ध छैन"),
    MATCH_SELECTED("Selected", "छानिएको"),
    MATCH_COPY_FULL_REPORT("Copy Full Report", "पूर्ण रिपोर्ट कपी गर्नुहोस्"),
    MATCH_COPY_FULL_DESC("Complete compatibility analysis", "पूर्ण अनुकूलता विश्लेषण"),
    MATCH_COPY_SUMMARY("Copy Summary", "सारांश कपी गर्नुहोस्"),
    MATCH_COPY_SUMMARY_DESC("Brief compatibility overview", "संक्षिप्त अनुकूलता अवलोकन"),
    MATCH_COPY_SCORES("Copy Scores", "अंकहरू कपी गर्नुहोस्"),
    MATCH_COPY_SCORES_DESC("Guna scores only", "गुण अंकहरू मात्र"),

    // Guna Details
    GUNA_VARNA("Varna", "वर्ण"),
    GUNA_VASHYA("Vashya", "वश्य"),
    GUNA_TARA("Tara", "तारा"),
    GUNA_YONI("Yoni", "योनि"),
    GUNA_GRAHA_MAITRI("Graha Maitri", "ग्रह मैत्री"),
    GUNA_GANA("Gana", "गण"),
    GUNA_BHAKOOT("Bhakoot", "भकूट"),
    GUNA_NADI("Nadi", "नाडी"),
    GUNA_POINTS("Points", "अंकहरू"),

    // Dosha Analysis
    DOSHA_MANGLIK("Manglik Dosha", "मांगलिक दोष"),
    DOSHA_NADI("Nadi Dosha", "नाडी दोष"),
    DOSHA_BHAKOOT("Bhakoot Dosha", "भकूट दोष"),
    DOSHA_PRESENT("Present", "उपस्थित"),
    DOSHA_ABSENT("Absent", "अनुपस्थित"),
    DOSHA_CANCELLED("Cancelled", "रद्द"),
    DOSHA_REMEDIES_AVAILABLE("Remedies Available", "उपायहरू उपलब्ध"),

    // ============================================
    // PANCHANGA
    // ============================================
    PANCHANGA_TITHI("Tithi", "तिथि"),
    PANCHANGA_VARA("Vara (Day)", "वार"),
    PANCHANGA_NAKSHATRA_LABEL("Nakshatra", "नक्षत्र"),
    PANCHANGA_YOGA("Yoga", "योग"),
    PANCHANGA_KARANA("Karana", "करण"),
    PANCHANGA_SUNRISE("Sunrise", "सूर्योदय"),
    PANCHANGA_SUNSET("Sunset", "सूर्यास्त"),
    PANCHANGA_MOONRISE("Moonrise", "चन्द्रोदय"),
    PANCHANGA_MOONSET("Moonset", "चन्द्रास्त"),

    // ============================================
    // MUHURTA
    // ============================================
    MUHURTA_TITLE("Muhurta Finder", "मुहूर्त खोजकर्ता"),
    MUHURTA_SELECT_EVENT("Select Event Type", "घटना प्रकार छान्नुहोस्"),
    MUHURTA_DATE_RANGE("Date Range", "मिति दायरा"),
    MUHURTA_FIND("Find Auspicious Times", "शुभ समय खोज्नुहोस्"),
    MUHURTA_RESULTS("Auspicious Muhurtas", "शुभ मुहूर्तहरू"),
    MUHURTA_PREV_DAY("Previous day", "अघिल्लो दिन"),
    MUHURTA_NEXT_DAY("Next day", "अर्को दिन"),
    MUHURTA_CALCULATING("Calculating muhurta...", "मुहूर्त गणना गर्दै..."),
    MUHURTA_ERROR("Something went wrong", "केही गलत भयो"),
    MUHURTA_SCORE("Score", "अंक"),
    MUHURTA_AUSPICIOUS_TIME("Auspicious Time", "शुभ समय"),
    MUHURTA_AVERAGE_TIME("Average Time", "सामान्य समय"),
    MUHURTA_PANCHANGA("Panchanga", "पञ्चाङ्ग"),
    MUHURTA_INAUSPICIOUS_PERIODS("Inauspicious Periods", "अशुभ अवधिहरू"),
    MUHURTA_RAHUKALA("Rahukala", "राहुकाल"),
    MUHURTA_RAHUKALA_DESC("Avoid important work", "महत्त्वपूर्ण कार्य बच्नुहोस्"),
    MUHURTA_YAMAGHANTA("Yamaghanta", "यमघन्ता"),
    MUHURTA_YAMAGHANTA_DESC("Avoid travel", "यात्रा बच्नुहोस्"),
    MUHURTA_GULIKA_KALA("Gulika Kala", "गुलिक काल"),
    MUHURTA_GULIKA_KALA_DESC("Avoid new beginnings", "नयाँ शुरुआत बच्नुहोस्"),
    MUHURTA_ACTIVE("ACTIVE", "सक्रिय"),
    MUHURTA_DAY_CHOGHADIYA("Day Choghadiya", "दिनको चोघड़िया"),
    MUHURTA_PERIODS("%d periods", "%d अवधिहरू"),
    MUHURTA_NOW("NOW", "अहिले"),
    MUHURTA_FROM("From", "बाट"),
    MUHURTA_TO("To", "सम्म"),
    MUHURTA_SEARCHING("Searching...", "खोज्दै..."),
    MUHURTA_FIND_DATES("Find Auspicious Dates", "शुभ मिति खोज्नुहोस्"),
    MUHURTA_FIND_AUSPICIOUS("Find Auspicious Dates", "शुभ मितिहरू खोज्नुहोस्"),
    MUHURTA_SEARCH_EMPTY("Search for Auspicious Times", "शुभ समय खोज्नुहोस्"),
    MUHURTA_SEARCH_HELP("Select an activity and date range to find the most favorable muhurtas", "सबैभन्दा अनुकूल मुहूर्तहरू खोज्न गतिविधि र मिति दायरा छान्नुहोस्"),
    MUHURTA_FINDING("Finding auspicious times...", "शुभ समय खोज्दै..."),
    MUHURTA_NO_RESULTS("No Auspicious Times Found", "कुनै शुभ समय फेला परेन"),
    MUHURTA_NO_RESULTS_HELP("Try expanding your date range", "मिति दायरा विस्तार गर्ने प्रयास गर्नुहोस्"),
    MUHURTA_SEARCH_ERROR("Search Failed", "खोज असफल"),

    // MUHURTA ACTIVITIES
    ACTIVITY_MARRIAGE_NAME("Marriage", "विवाह"),
    ACTIVITY_MARRIAGE_DESC("Wedding ceremonies, engagement", "विवाह समारोह, इन्गेज्मेन्ट"),
    ACTIVITY_TRAVEL_NAME("Travel", "यात्रा"),
    ACTIVITY_TRAVEL_DESC("Long journeys, business trips", "लामो यात्रा, व्यापारिक यात्रा"),
    ACTIVITY_BUSINESS_NAME("Business", "व्यापार"),
    ACTIVITY_BUSINESS_DESC("Opening shop, new ventures", "पसल खोल्ने, नयाँ उद्यमहरू"),
    ACTIVITY_PROPERTY_NAME("Property", "सम्पत्ति"),
    ACTIVITY_PROPERTY_DESC("Buying/selling land, house", "जग्गा, घर खरीद/बिक्री"),
    ACTIVITY_EDUCATION_NAME("Education", "शिक्षा"),
    ACTIVITY_EDUCATION_DESC("Admissions, starting studies", "भर्ना, अध्ययन सुरु"),
    ACTIVITY_MEDICAL_NAME("Medical", "चिकित्सा"),
    ACTIVITY_MEDICAL_DESC("Surgery, treatment start", "शल्यक्रिया, उपचार सुरु"),
    ACTIVITY_VEHICLE_NAME("Vehicle", "सवारी साधन"),
    ACTIVITY_VEHICLE_DESC("Buying new vehicle", "नयाँ सवारी साधन खरीद"),
    ACTIVITY_SPIRITUAL_NAME("Spiritual", "आध्यात्मिक"),
    ACTIVITY_SPIRITUAL_DESC("Initiation, ceremonies", "दीक्षा, समारोहहरू"),
    ACTIVITY_GRIHA_PRAVESHA_NAME("Griha Pravesha", "गृह प्रवेश"),
    ACTIVITY_GRIHA_PRAVESHA_DESC("House warming", "गृह प्रवेश"),
    ACTIVITY_NAMING_NAME("Naming", "न्वारन"),
    ACTIVITY_NAMING_DESC("Naming ceremony", "नामकरण समारोह"),
    ACTIVITY_GENERAL_NAME("General", "सामान्य"),
    ACTIVITY_GENERAL_DESC("General auspicious activities", "सामान्य शुभ गतिविधिहरू"),

    // VARA NAMES
    VARA_SUNDAY("Sunday", "आइतबार"),
    VARA_MONDAY("Monday", "सोमबार"),
    VARA_TUESDAY("Tuesday", "मंगलबार"),
    VARA_WEDNESDAY("Wednesday", "बुधबार"),
    VARA_THURSDAY("Thursday", "बिहिबार"),
    VARA_FRIDAY("Friday", "शुक्रबार"),
    VARA_SATURDAY("Saturday", "शनिबार"),

    // CHOGHADIYA
    CHOGHADIYA_UDVEG("Udveg", "उद्वेग"),
    CHOGHADIYA_CHAR("Char", "चर"),
    CHOGHADIYA_LABH("Labh", "लाभ"),
    CHOGHADIYA_AMRIT("Amrit", "अमृत"),
    CHOGHADIYA_KAAL("Kaal", "काल"),
    CHOGHADIYA_SHUBH("Shubh", "शुभ"),
    CHOGHADIYA_ROG("Rog", "रोग"),
    MUHURTA_RESULTS_TITLE("Auspicious Times", "शुभ समयहरू"),
    MUHURTA_RESULTS_COUNT("%d found", "%d फेला परेको"),
    MUHURTA_DETAIL_DAY("Day", "दिन"),
    MUHURTA_DETAIL_CHOGHADIYA("Choghadiya", "चोघड़िया"),
    MUHURTA_TODAY("Today", "आज"),
    MUHURTA_PREV_DAY_A11Y("Previous day", "अघिल्लो दिन"),
    MUHURTA_NEXT_DAY_A11Y("Next day", "अर्को दिन"),
    MUHURTA_VARA("Vara", "वार"),
    MUHURTA_TITHI("Tithi", "तिथि"),
    MUHURTA_YOGA("Yoga", "योग"),
    MUHURTA_KARANA("Karana", "करण"),
    MUHURTA_SUNRISE_SUNSET("Sunrise / Sunset", "सूर्योदय / सूर्यास्त"),
    MUHURTA_SELECT_ACTIVITY("Select Activity", "गतिविधि चयन गर्नुहोस्"),
    MUHURTA_SUITABLE_ACTIVITIES("Suitable Activities", "उपयुक्त गतिविधिहरू"),
    MUHURTA_AVOID_ACTIVITIES("Activities to Avoid", "बच्नुपर्ने गतिविधिहरू"),
    MUHURTA_RECOMMENDATIONS("Recommendations", "सिफारिसहरू"),
    MUHURTA_DATE_RANGE_LABEL("Date Range", "मिति दायरा"),
    MUHURTA_CHOGHADIYA_SUFFIX("Choghadiya: %s", "चौघडिया: %s"),
    MUHURTA_HORA_SUFFIX("Hora: %s", "होरा: %s"),

    // ============================================
    // MISCELLANEOUS / SHARED
    // ============================================
    HOUSE_LABEL("House %d", "भाव %d"),
    PANCHA_NO_YOGAS("No major Panch Mahapurusha Yogas found.", "कुनै प्रमुख पञ्च महापुरुष योग फेला परेन।"),

    // ============================================
    // REMEDIES
    // ============================================
    REMEDIES_TITLE("Vedic Remedies", "वैदिक उपायहरू"),
    REMEDIES_GEMSTONES("Gemstones", "रत्नहरू"),
    REMEDIES_MANTRAS("Mantras", "मन्त्रहरू"),
    REMEDIES_YANTRAS("Yantras", "यन्त्रहरू"),
    REMEDIES_RITUALS("Rituals", "पूजा विधिहरू"),
    REMEDIES_CHARITY("Charity", "दान"),
    REMEDIES_OVERVIEW("Overview", "अवलोकन"),
    REMEDIES_PLANETS("Planets", "ग्रहहरू"),
    REMEDIES_ANALYSIS("Remedies Analysis", "उपाय विश्लेषण"),
    REMEDIES_CHART_STRENGTH("Chart Strength", "कुण्डली बल"),
    REMEDIES_PLANETS_WELL_PLACED("%d of %d planets well-placed", "%d मध्ये %d ग्रह राम्ररी स्थित"),
    REMEDIES_PLANETS_ATTENTION("Planets Requiring Attention", "ध्यान आवश्यक ग्रहहरू"),
    REMEDIES_ESSENTIAL("Essential Remedies", "आवश्यक उपायहरू"),
    REMEDIES_WEEKLY_SCHEDULE("Weekly Remedy Schedule", "साप्ताहिक उपाय तालिका"),
    REMEDIES_WEEKLY_SCHEDULE_DESC("Perform planet-specific remedies on their designated days for maximum effect", "अधिकतम प्रभावको लागि तिनीहरूको तोकिएको दिनमा ग्रह-विशेष उपायहरू गर्नुहोस्"),
    REMEDIES_LIFE_AREA_FOCUS("Life Area Focus", "जीवन क्षेत्र फोकस"),
    REMEDIES_GENERAL_RECOMMENDATIONS("General Recommendations", "सामान्य सिफारिसहरू"),
    REMEDIES_METHOD("Method", "विधि"),
    REMEDIES_TIMING("Timing", "समय"),
    REMEDIES_DURATION("Duration", "अवधि"),
    REMEDIES_MANTRA_SECTION("Mantra", "मन्त्र"),
    REMEDIES_BENEFITS("Benefits", "लाभहरू"),
    REMEDIES_CAUTIONS("Cautions", "सावधानीहरू"),
    REMEDIES_SEARCH("Search remedies...", "उपायहरू खोज्नुहोस्..."),
    REMEDIES_FILTER_ALL("All", "सबै"),
    REMEDIES_NO_RESULTS("No remedies found", "कुनै उपाय फेला परेन"),
    REMEDIES_NO_RESULTS_SEARCH("No remedies found for \"%s\"", "\"%s\" को लागि कुनै उपाय फेला परेन"),
    REMEDIES_NO_CATEGORY("No remedies in this category", "यस वर्गमा कुनै उपाय छैन"),
    REMEDIES_NO_CHART("No chart selected", "कुनै कुण्डली छानिएको छैन"),
    REMEDIES_SELECT_CHART("Select a chart to view remedies", "उपायहरू हेर्न कुण्डली छान्नुहोस्"),
    REMEDIES_ANALYZING("Analyzing your chart...", "तपाईंको कुण्डली विश्लेषण गर्दै..."),
    REMEDIES_PREPARING("Preparing personalized remedies", "व्यक्तिगत उपायहरू तयार गर्दै"),
    REMEDIES_COPY_MANTRA("Copy mantra", "मन्त्र कपी गर्नुहोस्"),
    REMEDIES_RECOMMENDED("%d remedies recommended", "%d उपायहरू सिफारिस गरिएको"),
    REMEDIES_BEST_DAY("Best performed on %s", "%s मा गर्नु उत्तम"),
    REMEDIES_TOTAL("Total", "कुल"),
    REMEDIES_ESSENTIAL_COUNT("Essential", "आवश्यक"),

    // Planetary Status
    PLANETARY_STATUS_EXALTED("Exalted", "उच्च"),
    PLANETARY_STATUS_DEBILITATED("Debilitated", "नीच"),
    PLANETARY_STATUS_RETROGRADE("Retrograde", "वक्री"),
    PLANETARY_STATUS_COMBUST("Combust", "अस्त"),
    PLANETARY_STATUS_OWN_SIGN("Own Sign", "स्वराशि"),
    PLANETARY_STATUS_MOOLATRIKONA("Moolatrikona", "मूलत्रिकोण"),
    PLANETARY_STATUS_FRIENDLY("Friendly", "मित्र"),
    PLANETARY_STATUS_ENEMY_SIGN("Enemy Sign", "शत्रु राशि"),

    // ============================================
    // TIME DURATION LABELS
    // ============================================
    TIME_DAYS("%dd", "%d दिन"),
    TIME_WEEKS("%dw", "%d हप्ता"),
    TIME_MONTHS("%dm", "%d महिना"),
    TIME_YEARS("%dy", "%d वर्ष"),
    TIME_IN("in %s", "%s मा"),

    // ============================================
    // BS DATE PICKER
    // ============================================
    BS_DATE_PICKER_TITLE("Select BS Date", "वि.सं. मिति छान्नुहोस्"),
    BS_YEAR("Year", "वर्ष"),
    BS_MONTH("Month", "महिना"),
    BS_DAY("Day", "दिन"),

    // BS Months
    BS_MONTH_BAISHAKH("Baishakh", "बैशाख"),
    BS_MONTH_JESTHA("Jestha", "जेठ"),
    BS_MONTH_ASHADH("Ashadh", "असार"),
    BS_MONTH_SHRAWAN("Shrawan", "साउन"),
    BS_MONTH_BHADRA("Bhadra", "भदौ"),
    BS_MONTH_ASHWIN("Ashwin", "असोज"),
    BS_MONTH_KARTIK("Kartik", "कार्तिक"),
    BS_MONTH_MANGSIR("Mangsir", "मंसिर"),
    BS_MONTH_POUSH("Poush", "पुष"),
    BS_MONTH_MAGH("Magh", "माघ"),
    BS_MONTH_FALGUN("Falgun", "फाल्गुन"),
    BS_MONTH_CHAITRA("Chaitra", "चैत्र"),

    // Days of week (Nepali)
    DAY_SUNDAY("Sunday", "आइतबार"),
    DAY_MONDAY("Monday", "सोमबार"),
    DAY_TUESDAY("Tuesday", "मंगलबार"),
    DAY_WEDNESDAY("Wednesday", "बुधबार"),
    DAY_THURSDAY("Thursday", "बिहिबार"),
    DAY_FRIDAY("Friday", "शुक्रबार"),
    DAY_SATURDAY("Saturday", "शनिबार"),
    DAY_ANY("any day", "कुनै पनि दिन"),

    // ============================================
    // MISCELLANEOUS
    // ============================================
    MISC_UNAVAILABLE("Unavailable", "उपलब्ध छैन"),
    MISC_LOADING("Loading...", "लोड हुँदैछ..."),
    MISC_NO_DATA("No data available", "डाटा उपलब्ध छैन"),
    MISC_UNKNOWN("Unknown", "अज्ञात"),
    MISC_EXPAND("Expand", "विस्तार"),
    MISC_COLLAPSE("Collapse", "संकुचन"),
    MISC_MORE("More", "थप"),
    MISC_LESS("Less", "कम"),

    // ============================================
    // MATCHMAKING CALCULATOR - VARNA
    // ============================================
    VARNA_BRAHMIN("Brahmin", "ब्राह्मण"),
    VARNA_KSHATRIYA("Kshatriya", "क्षत्रिय"),
    VARNA_VAISHYA("Vaishya", "वैश्य"),
    VARNA_SHUDRA("Shudra", "शूद्र"),

    // ============================================
    // MATCHMAKING CALCULATOR - VASHYA
    // ============================================
    VASHYA_CHATUSHPADA("Quadruped", "चतुष्पद"),
    VASHYA_MANAVA("Human", "मानव"),
    VASHYA_JALACHARA("Aquatic", "जलचर"),
    VASHYA_VANACHARA("Wild", "वनचर"),
    VASHYA_KEETA("Insect", "कीट"),

    // ============================================
    // MATCHMAKING CALCULATOR - GANA
    // ============================================
    GANA_DEVA("Deva", "देव"),
    GANA_DEVA_DESC("Divine - Sattvik, gentle, spiritual", "दैवी - सात्त्विक, सौम्य, आध्यात्मिक"),
    GANA_MANUSHYA("Manushya", "मनुष्य"),
    GANA_MANUSHYA_DESC("Human - Rajasik, balanced, worldly", "मानव - राजसिक, सन्तुलित, सांसारिक"),
    GANA_RAKSHASA("Rakshasa", "राक्षस"),
    GANA_RAKSHASA_DESC("Demon - Tamasik, aggressive, dominant", "दानव - तामसिक, आक्रामक, प्रभावशाली"),

    // ============================================
    // MATCHMAKING CALCULATOR - YONI ANIMALS
    // ============================================
    YONI_ANIMAL_HORSE("Horse", "घोडा"),
    YONI_ANIMAL_ELEPHANT("Elephant", "हात्ती"),
    YONI_ANIMAL_SHEEP("Sheep", "भेडा"),
    YONI_ANIMAL_SERPENT("Serpent", "सर्प"),
    YONI_ANIMAL_DOG("Dog", "कुकुर"),
    YONI_ANIMAL_CAT("Cat", "बिरालो"),
    YONI_ANIMAL_RAT("Rat", "मुसो"),
    YONI_ANIMAL_COW("Cow", "गाई"),
    YONI_ANIMAL_BUFFALO("Buffalo", "भैंसी"),
    YONI_ANIMAL_TIGER("Tiger", "बाघ"),
    YONI_ANIMAL_DEER("Deer", "मृग"),
    YONI_ANIMAL_MONKEY("Monkey", "बाँदर"),
    YONI_ANIMAL_MONGOOSE("Mongoose", "न्यौरी"),
    YONI_ANIMAL_LION("Lion", "सिंह"),
    
    GENDER_MALE("Male", "पुरुष"),
    GENDER_FEMALE("Female", "स्त्री"),

    // ============================================
    // MATCHMAKING CALCULATOR - NADI
    // ============================================
    NADI_ADI("Adi (Vata)", "आदि (वात)"),
    NADI_ADI_DESC("Beginning - Wind element, controls movement and nervous system", "आदि - वायु तत्व, गति र स्नायु प्रणाली नियन्त्रण गर्छ"),
    NADI_MADHYA("Madhya (Pitta)", "मध्य (पित्त)"),
    NADI_MADHYA_DESC("Middle - Fire element, controls digestion and metabolism", "मध्य - अग्नि तत्व, पाचन र चयापचय नियन्त्रण गर्छ"),
    NADI_ANTYA("Antya (Kapha)", "अन्त्य (कफ)"),
    NADI_ANTYA_DESC("End - Water element, controls structure and lubrication", "अन्त्य - जल तत्व, संरचना र स्नेहन नियन्त्रण गर्छ"),

    // ============================================
    // MATCHMAKING CALCULATOR - RAJJU
    // ============================================
    RAJJU_PADA("Pada Rajju", "पाद रज्जु"),
    RAJJU_PADA_BODY("Feet", "पाउ"),
    RAJJU_KATI("Kati Rajju", "कटि रज्जु"),
    RAJJU_KATI_BODY("Waist", "कम्मर"),
    RAJJU_NABHI("Nabhi Rajju", "नाभि रज्जु"),
    RAJJU_NABHI_BODY("Navel", "नाभि"),
    RAJJU_KANTHA("Kantha Rajju", "कण्ठ रज्जु"),
    RAJJU_KANTHA_BODY("Neck", "घाँटी"),
    RAJJU_SIRO("Siro Rajju", "शिरो रज्जु"),
    RAJJU_SIRO_BODY("Head", "शिर"),
    RAJJU_SIRO_WARNING("Most serious - affects longevity of spouse", "सबैभन्दा गम्भीर - जीवनसाथीको आयुमा असर पार्छ"),
    RAJJU_KANTHA_WARNING("May cause health issues to both", "दुवैलाई स्वास्थ्य समस्या हुन सक्छ"),
    RAJJU_NABHI_WARNING("May affect children", "सन्तानमा असर पर्न सक्छ"),
    RAJJU_KATI_WARNING("May cause financial difficulties", "आर्थिक कठिनाइहरू हुन सक्छ"),
    RAJJU_PADA_WARNING("May cause wandering tendencies", "भ्रमण प्रवृत्ति हुन सक्छ"),

    // ============================================
    // MATCHMAKING CALCULATOR - MANGLIK DOSHA
    // ============================================
    MANGLIK_NONE("No Manglik Dosha", "मांगलिक दोष छैन"),
    MANGLIK_PARTIAL("Partial Manglik", "आंशिक मांगलिक"),
    MANGLIK_FULL("Full Manglik", "पूर्ण मांगलिक"),
    MANGLIK_DOUBLE("Double Manglik (Severe)", "दोहोरो मांगलिक (गम्भीर)"),
    MANGLIK_NO_DOSHA_DESC("No Manglik Dosha present.", "मांगलिक दोष छैन।"),
    MANGLIK_DETECTED("detected", "पत्ता लाग्यो"),
    MANGLIK_INTENSITY("intensity", "तीव्रता"),
    MANGLIK_MARS_IN("Mars in", "मंगल"),
    FROM_LAGNA("from Lagna", "लग्नबाट"),
    FROM_MOON("from Moon", "चन्द्रबाट"),
    FROM_VENUS("from Venus", "शुक्रबाट"),
    MANGLIK_FACTOR_FROM_LAGNA("Mars in House {house} from Lagna", "लग्नबाट भाव {house} मा मंगल"),
    MANGLIK_FACTOR_FROM_MOON("Mars in House {house} from Moon", "चन्द्रबाट भाव {house} मा मंगल"),
    MANGLIK_FACTOR_FROM_VENUS("Mars in House {house} from Venus", "शुक्रबाट भाव {house} मा मंगल"),
    MANGLIK_BOTH_NON("Both non-Manglik - No concerns", "दुवै गैर-मांगलिक - कुनै चिन्ता छैन"),
    MANGLIK_BOTH_MATCH("Both Manglik - Doshas cancel each other (Manglik to Manglik match is recommended)", "दुवै मांगलिक - दोषहरू एकअर्कालाई निष्क्रिय गर्छन् (मांगलिकसँग मांगलिक मिलान सिफारिस गरिएको)"),
    MANGLIK_MINOR_IMBALANCE("Minor Manglik imbalance - Manageable with remedies", "सानो मांगलिक असन्तुलन - उपायहरूद्वारा व्यवस्थापनयोग्य"),
    MANGLIK_BRIDE_ONLY("Bride is Manglik while Groom is not - Kumbh Vivah or other remedies advised", "दुलही मांगलिक छिन् जबकि दुलाहा छैनन् - कुम्भ विवाह वा अन्य उपायहरू सल्लाह दिइएको"),
    MANGLIK_GROOM_ONLY("Groom is Manglik while Bride is not - Remedies strongly recommended", "दुलाहा मांगलिक छन् जबकि दुलही छैनन् - उपायहरू दृढतापूर्वक सिफारिस गरिएको"),
    MANGLIK_SIGNIFICANT_IMBALANCE("Significant Manglik imbalance - Careful consideration and remedies essential", "महत्त्वपूर्ण मांगलिक असन्तुलन - सावधानीपूर्ण विचार र उपायहरू आवश्यक"),

    // ============================================
    // MATCHMAKING CALCULATOR - COMPATIBILITY RATINGS
    // ============================================
    COMPAT_EXCELLENT("Excellent Match", "उत्कृष्ट मिलान"),
    COMPAT_EXCELLENT_DESC("Highly recommended for marriage. Strong compatibility across all factors with harmonious planetary alignments.", "विवाहको लागि अत्यधिक सिफारिस गरिएको। सबै कारकहरूमा बलियो अनुकूलता र सामञ्जस्यपूर्ण ग्रह संरेखण।"),
    COMPAT_GOOD("Good Match", "राम्रो मिलान"),
    COMPAT_GOOD_DESC("Recommended. Good overall compatibility with minor differences that can be easily managed.", "सिफारिस गरिएको। सजिलैसँग व्यवस्थापन गर्न सकिने सानातिना भिन्नताहरूसहित राम्रो समग्र अनुकूलता।"),
    COMPAT_AVERAGE("Average Match", "औसत मिलान"),
    COMPAT_AVERAGE_DESC("Acceptable with some remedies. Moderate compatibility requiring mutual understanding and effort.", "केही उपायहरूसहित स्वीकार्य। पारस्परिक बुझाइ र प्रयास आवश्यक पर्ने मध्यम अनुकूलता।"),
    COMPAT_BELOW_AVERAGE("Below Average", "औसतमुनि"),
    COMPAT_BELOW_AVERAGE_DESC("Caution advised. Several compatibility issues that need addressing through remedies and counseling.", "सावधानी आवश्यक। उपाय र परामर्शद्वारा सम्बोधन गर्नुपर्ने धेरै अनुकूलता समस्याहरू।"),
    COMPAT_POOR("Poor Match", "कमजोर मिलान"),
    COMPAT_POOR_DESC("Not recommended. Significant compatibility challenges that may cause ongoing difficulties.", "सिफारिस गरिएको छैन। निरन्तर कठिनाइहरू ल्याउन सक्ने महत्त्वपूर्ण अनुकूलता चुनौतीहरू।"),

    // ============================================
    // MATCHMAKING CALCULATOR - TARA NAMES
    // ============================================
    TARA_JANMA("Janma (Birth)", "जन्म"),
    TARA_SAMPAT("Sampat (Wealth)", "सम्पत् (धन)"),
    TARA_VIPAT("Vipat (Danger)", "विपत् (खतरा)"),
    TARA_KSHEMA("Kshema (Wellbeing)", "क्षेम (कल्याण)"),
    TARA_PRATYARI("Pratyari (Obstacle)", "प्रत्यरि (बाधा)"),
    TARA_SADHANA("Sadhana (Achievement)", "साधना (उपलब्धि)"),
    TARA_VADHA("Vadha (Death)", "वध (मृत्यु)"),
    TARA_MITRA("Mitra (Friend)", "मित्र"),
    TARA_PARAMA_MITRA("Parama Mitra (Best Friend)", "परम मित्र"),

    // ============================================
    // GUNA MILAN ANALYSIS STRINGS
    // ============================================
    // Varna Analysis
    VARNA_DESC("Spiritual compatibility and ego harmony", "आध्यात्मिक अनुकूलता र अहंकार सामञ्जस्य"),
    VARNA_COMPATIBLE("Compatible: Groom's Varna ({groom}) is equal to or higher than Bride's ({bride}). This indicates spiritual harmony.", "अनुकूल: दुलाहाको वर्ण ({groom}) दुलहीको ({bride}) बराबर वा माथि छ। यसले आध्यात्मिक सामञ्जस्य संकेत गर्छ।"),
    VARNA_INCOMPATIBLE("Mismatch: Bride's Varna ({bride}) is higher than Groom's ({groom}). May cause ego-related issues.", "बेमेल: दुलहीको वर्ण ({bride}) दुलाहाको ({groom}) भन्दा माथि छ। अहंकार-सम्बन्धित समस्या हुन सक्छ।"),

    // Vashya Analysis
    VASHYA_DESC("Mutual attraction and influence", "पारस्परिक आकर्षण र प्रभाव"),
    VASHYA_EXCELLENT("Excellent mutual attraction and influence. Both partners can positively influence each other.", "उत्कृष्ट पारस्परिक आकर्षण र प्रभाव। दुवै साझेदारहरू एकअर्कालाई सकारात्मक रूपमा प्रभाव पार्न सक्छन्।"),
    VASHYA_VERY_GOOD("Very good compatibility with balanced influence between partners.", "साझेदारहरू बीच सन्तुलित प्रभावसहित धेरै राम्रो अनुकूलता।"),
    VASHYA_GOOD("Good compatibility with moderate mutual influence.", "मध्यम पारस्परिक प्रभावसहित राम्रो अनुकूलता।"),
    VASHYA_PARTIAL("Partial compatibility. One partner may dominate relationship dynamics.", "आंशिक अनुकूलता। एउटा साझेदारले सम्बन्धको गतिशीलतामा प्रभुत्व जमाउन सक्छ।"),
    VASHYA_INCOMPATIBLE("Incompatible Vashya types. May cause power struggles in the relationship.", "असंगत वश्य प्रकारहरू। सम्बन्धमा शक्ति संघर्ष हुन सक्छ।"),

    // Tara Analysis
    TARA_DESC("Destiny and birth star compatibility", "भाग्य र जन्म तारा अनुकूलता"),
    TARA_EXCELLENT("Both have auspicious Taras - excellent destiny compatibility. Harmonious life path.", "दुवैको शुभ तारा छ - उत्कृष्ट भाग्य अनुकूलता। सामञ्जस्यपूर्ण जीवन मार्ग।"),
    TARA_MODERATE("One auspicious Tara present - moderate destiny compatibility.", "एउटा शुभ तारा उपस्थित - मध्यम भाग्य अनुकूलता।"),
    TARA_INAUSPICIOUS("Both Taras are inauspicious - may face obstacles together. Remedies recommended.", "दुवै तारा अशुभ - सँगै अवरोधहरू सामना गर्न सक्छन्। उपायहरू सिफारिस गरिएको।"),

    // Yoni Analysis
    YONI_DESC("Physical and sexual compatibility", "शारीरिक र यौन अनुकूलता"),
    YONI_SAME("Same Yoni animal - perfect physical and instinctual compatibility. Strong natural attraction.", "उही योनि पशु - उत्तम शारीरिक र सहज अनुकूलता। बलियो प्राकृतिक आकर्षण।"),
    YONI_FRIENDLY("Friendly Yonis - very good physical compatibility. Natural understanding.", "मित्र योनि - धेरै राम्रो शारीरिक अनुकूलता। प्राकृतिक बुझाइ।"),
    YONI_NEUTRAL("Neutral Yonis - moderate physical compatibility. Requires some adjustment.", "तटस्थ योनि - मध्यम शारीरिक अनुकूलता। केही समायोजन आवश्यक।"),
    YONI_UNFRIENDLY("Unfriendly Yonis - some physical and instinctual differences. Needs conscious effort.", "अमैत्रीपूर्ण योनि - केही शारीरिक र सहज भिन्नताहरू। सचेत प्रयास आवश्यक।"),
    YONI_ENEMY("Enemy Yonis - significant physical incompatibility. May face intimacy challenges.", "शत्रु योनि - महत्त्वपूर्ण शारीरिक असंगतता। अन्तरंगता चुनौतीहरू सामना गर्न सक्छ।"),

    // Graha Maitri Analysis
    GRAHA_MAITRI_DESC("Mental compatibility and friendship", "मानसिक अनुकूलता र मित्रता"),
    GRAHA_MAITRI_EXCELLENT("Same lord or mutual friends - excellent mental compatibility. Natural understanding.", "उही स्वामी वा पारस्परिक मित्र - उत्कृष्ट मानसिक अनुकूलता। प्राकृतिक बुझाइ।"),
    GRAHA_MAITRI_VERY_GOOD("One friend, one neutral - very good mental harmony. Good communication.", "एउटा मित्र, एउटा तटस्थ - धेरै राम्रो मानसिक सामञ्जस्य। राम्रो सञ्चार।"),
    GRAHA_MAITRI_AVERAGE("Neutral relationship - average mental compatibility. Requires effort for understanding.", "तटस्थ सम्बन्ध - औसत मानसिक अनुकूलता। बुझाइको लागि प्रयास आवश्यक।"),
    GRAHA_MAITRI_FRICTION("One enemy present - some mental friction. Different thought processes.", "एउटा शत्रु उपस्थित - केही मानसिक घर्षण। फरक विचार प्रक्रियाहरू।"),
    GRAHA_MAITRI_INCOMPATIBLE("Mutual enemies - significant mental incompatibility. May face frequent misunderstandings.", "पारस्परिक शत्रु - महत्त्वपूर्ण मानसिक असंगतता। बारम्बार गलतफहमीहरू हुन सक्छ।"),

    // Gana Analysis
    GANA_DESC("Temperament and behavior compatibility", "स्वभाव र व्यवहार अनुकूलता"),
    GANA_SAME("Same Gana - perfect temperamental harmony. Similar approach to life and values.", "उही गण - उत्तम स्वभावगत सामञ्जस्य। जीवन र मूल्यहरूमा समान दृष्टिकोण।"),
    GANA_COMPATIBLE("Compatible Ganas - good temperamental harmony with minor differences.", "अनुकूल गण - सानातिना भिन्नताहरूसहित राम्रो स्वभावगत सामञ्जस्य।"),
    GANA_PARTIAL("Partially compatible - some temperamental adjustment needed.", "आंशिक अनुकूल - केही स्वभावगत समायोजन आवश्यक।"),
    GANA_DIFFERENT("Different temperaments - significant adjustment required. May cause lifestyle clashes.", "फरक स्वभाव - महत्त्वपूर्ण समायोजन आवश्यक। जीवनशैली टकराव हुन सक्छ।"),
    GANA_OPPOSITE("Opposite temperaments - major incompatibility. Frequent conflicts likely.", "विपरीत स्वभाव - प्रमुख असंगतता। बारम्बार द्वन्द्व हुन सक्छ।"),

    // Bhakoot Analysis
    BHAKOOT_DESC("Love, health, and financial compatibility", "प्रेम, स्वास्थ्य र आर्थिक अनुकूलता"),
    BHAKOOT_NO_DOSHA("No Bhakoot dosha - excellent compatibility for love, health, and finances.", "भकुट दोष छैन - प्रेम, स्वास्थ्य र वित्तको लागि उत्कृष्ट अनुकूलता।"),
    BHAKOOT_CANCELLED("Bhakoot dosha cancelled by same sign lord - no adverse effects.", "उही राशि स्वामीद्वारा भकुट दोष रद्द - कुनै प्रतिकूल प्रभाव छैन।"),
    BHAKOOT_2_12("Dhan-Vyaya (2-12) Bhakoot Dosha - financial concerns possible.", "धन-व्यय (२-१२) भकुट दोष - आर्थिक चिन्ता सम्भव।"),
    BHAKOOT_2_12_DESC("May cause financial fluctuations and differences in spending habits.", "आर्थिक उतार-चढाव र खर्च बानीमा भिन्नता हुन सक्छ।"),
    BHAKOOT_6_8("Shadashtak (6-8) Bhakoot Dosha - health concerns may arise.", "षडाष्टक (६-८) भकुट दोष - स्वास्थ्य चिन्ता हुन सक्छ।"),
    BHAKOOT_6_8_DESC("May affect health and cause separation tendencies. Most serious Bhakoot dosha.", "स्वास्थ्यमा असर पार्न र विच्छेद प्रवृत्ति हुन सक्छ। सबैभन्दा गम्भीर भकुट दोष।"),
    BHAKOOT_5_9("Signs are in 5-9 (Trine) relationship - actually favorable.", "राशिहरू ५-९ (त्रिकोण) सम्बन्धमा छन् - वास्तवमा अनुकूल।"),
    BHAKOOT_5_9_DESC("Trine relationship is auspicious for progeny, dharma, and spiritual growth.", "त्रिकोण सम्बन्ध सन्तान, धर्म र आध्यात्मिक विकासको लागि शुभ छ।"),
    BHAKOOT_FAVORABLE("Signs are in favorable positions for marital harmony.", "राशिहरू वैवाहिक सामञ्जस्यको लागि अनुकूल स्थानमा छन्।"),
    BHAKOOT_CANCEL_SAME_LORD("Same lord ({lord}) rules both Moon signs - Full Cancellation", "उही स्वामी ({lord}) ले दुवै चन्द्र राशि शासन गर्छ - पूर्ण रद्द"),
    BHAKOOT_CANCEL_MUTUAL_FRIENDS("Moon sign lords ({lord1} & {lord2}) are mutual friends - Full Cancellation", "चन्द्र राशि स्वामी ({lord1} र {lord2}) पारस्परिक मित्र हुन् - पूर्ण रद्द"),
    BHAKOOT_CANCEL_EXALTATION("Lord is exalted in partner's sign - Partial Cancellation", "स्वामी साथीको राशिमा उच्च छ - आंशिक रद्द"),
    BHAKOOT_CANCEL_FRIENDLY("Moon sign lords have friendly disposition - Partial Cancellation", "चन्द्र राशि स्वामीहरूको मैत्रीपूर्ण स्वभाव छ - आंशिक रद्द"),
    BHAKOOT_CANCEL_ELEMENT("Both Moon signs share same element ({element}) - Partial Cancellation", "दुवै चन्द्र राशिले एउटै तत्व ({element}) साझा गर्छन् - आंशिक रद्द"),

    // Nadi Analysis
    NADI_DESC("Health and progeny compatibility (most important)", "स्वास्थ्य र सन्तान अनुकूलता (सबैभन्दा महत्त्वपूर्ण)"),
    NADI_DOSHA_PRESENT("NADI DOSHA PRESENT: Same Nadi ({nadi}) without cancellation. Serious concern affecting health and progeny.", "नाडी दोष उपस्थित: रद्द बिना उही नाडी ({nadi})। स्वास्थ्य र सन्तानलाई असर गर्ने गम्भीर चिन्ता।"),
    NADI_DOSHA_CANCELLED("Same Nadi but CANCELLED:", "उही नाडी तर रद्द:"),
    NADI_DIFFERENT("Different Nadis ({nadi1} & {nadi2}) - excellent health and progeny compatibility.", "फरक नाडी ({nadi1} र {nadi2}) - उत्कृष्ट स्वास्थ्य र सन्तान अनुकूलता।"),
    NADI_CANCEL_SAME_NAK_DIFF_RASHI("Same Nakshatra ({nakshatra}) but different Rashis - Full Cancellation", "उही नक्षत्र ({nakshatra}) तर फरक राशि - पूर्ण रद्द"),
    NADI_CANCEL_SAME_RASHI_DIFF_NAK("Same Rashi ({rashi}) but different Nakshatras - Full Cancellation", "उही राशि ({rashi}) तर फरक नक्षत्र - पूर्ण रद्द"),
    NADI_CANCEL_DIFF_PADA("Same Nakshatra and Rashi but different Padas ({pada1} vs {pada2}) - Partial Cancellation", "उही नक्षत्र र राशि तर फरक पाद ({pada1} बनाम {pada2}) - आंशिक रद्द"),
    NADI_CANCEL_SPECIAL_PAIR("Special Nakshatra pair ({nak1}-{nak2}) cancels Nadi dosha per classical texts", "विशेष नक्षत्र जोडी ({nak1}-{nak2}) ले शास्त्रीय ग्रन्थ अनुसार नाडी दोष रद्द गर्छ"),
    NADI_CANCEL_LORDS_FRIENDS("Moon sign lords ({lord1} & {lord2}) are mutual friends - Partial Cancellation", "चन्द्र राशि स्वामी ({lord1} र {lord2}) पारस्परिक मित्र हुन् - आंशिक रद्द"),
    NADI_CANCEL_SAME_NAK_LORD("Both Nakshatras ruled by {lord} - Partial Cancellation", "दुवै नक्षत्र {lord} द्वारा शासित - आंशिक रद्द"),

    // ============================================
    // DASHA CALCULATOR - LEVELS
    // ============================================
    DASHA_LEVEL_MAHADASHA("Mahadasha", "महादशा"),
    DASHA_LEVEL_ANTARDASHA("Antardasha", "अन्तर्दशा"),
    DASHA_LEVEL_PRATYANTARDASHA("Pratyantardasha", "प्रत्यन्तर्दशा"),
    DASHA_LEVEL_SOOKSHMADASHA("Sookshmadasha", "सूक्ष्मदशा"),
    DASHA_LEVEL_PRANADASHA("Pranadasha", "प्राणदशा"),
    DASHA_LEVEL_DEHADASHA("Dehadasha", "देहदशा"),

    // Dasha Tab Content Strings
    DASHA_CURRENT_DASHA_PERIOD("Current Dasha Period", "हालको दशा अवधि"),
    DASHA_BIRTH_NAKSHATRA("Birth Nakshatra", "जन्म नक्षत्र"),
    DASHA_LORD("Lord", "स्वामी"),
    DASHA_PADA("Pada", "पाद"),
    DASHA_PERIOD_INSIGHTS("Period Insights", "अवधि अन्तर्दृष्टि"),
    DASHA_SANDHI_ALERTS("Dasha Sandhi Alerts", "दशा सन्धि अलर्टहरू"),
    DASHA_UPCOMING_TRANSITIONS("%1\$d upcoming transition(s) within %2\$d days", "%2\$d दिनभित्र %1\$d आगामी सन्क्रमण(हरू)"),
    DASHA_TRANSITION("transition", "सन्क्रमण"),
    DASHA_SANDHI_EXPLANATION("Sandhi periods mark transitions between planetary periods. These are sensitive times requiring careful attention as the energy shifts from one planet to another.", "सन्धि अवधिहरूले ग्रह अवधिहरूबीचको सन्क्रमण चिन्ह लगाउँछन्। यी संवेदनशील समयहरू हुन् जहाँ एउटा ग्रहबाट अर्को ग्रहमा ऊर्जा परिवर्तन हुन्छ।"),
    DASHA_ACTIVE_NOW("Active Now", "अहिले सक्रिय"),
    DASHA_TODAY("Today", "आज"),
    DASHA_TOMORROW("Tomorrow", "भोलि"),
    DASHA_IN_DAYS("In %d days", "%d दिनमा"),
    DASHA_TIMELINE("Dasha Timeline", "दशा समयरेखा"),
    DASHA_COMPLETE_CYCLE("Complete 120-year Vimshottari cycle", "पूर्ण १२०-वर्षे विम्शोत्तरी चक्र"),
    DASHA_SUB_PERIODS("%d sub-periods", "%d उप-अवधिहरू"),
    DASHA_ANTARDASHAS("Antardashas", "अन्तर्दशाहरू"),
    DASHA_UNABLE_CALCULATE("Unable to calculate current dasha period", "हालको दशा अवधि गणना गर्न असक्षम"),
    DASHA_ABOUT_VIMSHOTTARI("About Vimshottari Dasha", "विम्शोत्तरी दशाको बारेमा"),
    DASHA_VIMSHOTTARI_DESC("The Vimshottari Dasha is the most widely used planetary period system in Vedic astrology (Jyotish). Derived from the Moon's nakshatra (lunar mansion) at birth, it divides the 120-year human lifespan into six levels of planetary periods. Starting from Mahadashas (major periods spanning years), it subdivides into Antardasha (months), Pratyantardasha (weeks), Sookshmadasha (days), Pranadasha (hours), and finally Dehadasha (minutes) — each governed by one of the nine Grahas.", "विम्शोत्तरी दशा वैदिक ज्योतिष (ज्योतिष) मा सबैभन्दा व्यापक रूपमा प्रयोग हुने ग्रह अवधि प्रणाली हो। जन्मको समयमा चन्द्रमाको नक्षत्रबाट व्युत्पन्न, यसले १२०-वर्षे मानव आयुलाई छ तहको ग्रह अवधिहरूमा विभाजन गर्छ।"),
    DASHA_PERIODS_SEQUENCE("Dasha Periods (Vimshottari Sequence)", "दशा अवधिहरू (विम्शोत्तरी क्रम)"),
    DASHA_TOTAL_CYCLE("Total Cycle: 120 Years", "कुल चक्र: १२० वर्ष"),
    DASHA_HIERARCHY("Dasha Hierarchy", "दशा पदानुक्रम"),
    DASHA_MAJOR_PERIOD_YEARS("Major period (years)", "मुख्य अवधि (वर्ष)"),
    DASHA_SUB_PERIOD_MONTHS("Sub-period (months)", "उप-अवधि (महिना)"),
    DASHA_SUB_SUB_PERIOD_WEEKS("Sub-sub-period (weeks)", "उप-उप-अवधि (हप्ता)"),
    DASHA_SUBTLE_PERIOD_DAYS("Subtle period (days)", "सूक्ष्म अवधि (दिन)"),
    DASHA_BREATH_PERIOD_HOURS("Breath period (hours)", "श्वास अवधि (घण्टा)"),
    DASHA_BODY_PERIOD_MINUTES("Body period (minutes)", "शरीर अवधि (मिनेट)"),
    DASHA_SANDHI_NOTE("Dasha Sandhi (junction periods) occur when transitioning between planetary periods and are considered sensitive times requiring careful attention.", "दशा सन्धि (जोड अवधिहरू) ग्रह अवधिहरूबीच सन्क्रमण हुँदा हुन्छ र यी संवेदनशील समयहरू मानिन्छन्।"),
    DASHA_PERCENT_COMPLETE("%s%% complete", "%s%% पूरा"),
    DASHA_YEARS_ABBR("yrs", "वर्ष"),
    DASHA_COLLAPSE("Collapse", "संकुचन गर्नुहोस्"),
    DASHA_EXPAND("Expand", "विस्तार गर्नुहोस्"),

    // ============================================
    // ASHTAKAVARGA CALCULATOR - STRENGTH LEVELS
    // ============================================
    STRENGTH_STRONG("Strong", "बलियो"),
    STRENGTH_GOOD("Good", "राम्रो"),
    STRENGTH_AVERAGE("Average", "औसत"),
    STRENGTH_WEAK("Weak", "कमजोर"),
    STRENGTH_EXCELLENT("Excellent", "उत्कृष्ट"),
    STRENGTH_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    STRENGTH_DIFFICULT("Difficult", "कठिन"),
    STRENGTH_ERROR("Error", "त्रुटि"),
    STRENGTH_ASCENDANT("Ascendant", "लग्न"),

    // ============================================
    // ASPECT CALCULATOR - TYPES
    // ============================================
    ASPECT_CONJUNCTION("Conjunction", "युति"),
    ASPECT_NATURE_HARMONIOUS("Harmonious", "सामञ्जस्यपूर्ण"),
    ASPECT_NATURE_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    ASPECT_NATURE_VARIABLE("Variable", "परिवर्तनशील"),
    ASPECT_NATURE_SIGNIFICANT("Significant", "महत्त्वपूर्ण"),

    // ============================================
    // COLORS (FOR HOROSCOPE)
    // ============================================
    COLOR_RED("Red", "रातो"),
    COLOR_ORANGE("Orange", "सुन्तला"),
    COLOR_GOLD("Gold", "सुनौलो"),
    COLOR_GREEN("Green", "हरियो"),
    COLOR_BROWN("Brown", "खैरो"),
    COLOR_WHITE("White", "सेतो"),
    COLOR_BLUE("Blue", "नीलो"),
    COLOR_LIGHT_BLUE("Light Blue", "हल्का नीलो"),
    COLOR_SILVER("Silver", "चाँदी"),
    COLOR_SEA_GREEN("Sea Green", "समुद्री हरियो"),
    COLOR_YELLOW("Yellow", "पहेँलो"),
    COLOR_PINK("Pink", "गुलाबी"),
    COLOR_PURPLE("Purple", "बैजनी"),
    COLOR_BLACK("Black", "कालो"),
    COLOR_GREY("Grey", "खरानी"),
    COLOR_CREAM("Cream", "क्रीम"),
    COLOR_MAROON("Maroon", "मरुन"),
    COLOR_INDIGO("Indigo", "इन्डिगो"),

    // ============================================
    // DIRECTIONS
    // ============================================
    DIR_EAST("East", "पूर्व"),
    DIR_WEST("West", "पश्चिम"),
    DIR_NORTH("North", "उत्तर"),
    DIR_SOUTH("South", "दक्षिण"),
    DIR_NORTH_EAST("North-East", "उत्तर-पूर्व"),
    DIR_NORTH_WEST("North-West", "उत्तर-पश्चिम"),
    DIR_SOUTH_EAST("South-East", "दक्षिण-पूर्व"),
    DIR_SOUTH_WEST("South-West", "दक्षिण-पश्चिम"),

    // ============================================
    // ELEMENTS
    // ============================================
    ELEMENT_FIRE("Fire", "अग्नि"),
    ELEMENT_EARTH("Earth", "पृथ्वी"),
    ELEMENT_AIR("Air", "वायु"),
    ELEMENT_WATER("Water", "जल"),

    // ============================================
    // PLANETARY RELATIONSHIPS
    // ============================================
    RELATION_FRIEND("Friend", "मित्र"),
    RELATION_ENEMY("Enemy", "शत्रु"),
    RELATION_NEUTRAL("Neutral", "तटस्थ"),
    RELATION_MUTUAL_FRIENDS("Mutual Friends", "पारस्परिक मित्र"),
    RELATION_MUTUAL_ENEMIES("Mutual Enemies", "पारस्परिक शत्रु"),
    RELATION_ONE_FRIENDLY("One Friendly", "एक मैत्रीपूर्ण"),
    RELATION_ONE_INIMICAL("One Inimical", "एक शत्रुतापूर्ण"),

    // ============================================
    // MUHURTA - EVENT TYPES
    // ============================================
    MUHURTA_EVENT_MARRIAGE("Marriage", "विवाह"),
    MUHURTA_EVENT_ENGAGEMENT("Engagement", "सगाई"),
    MUHURTA_EVENT_GRIHA_PRAVESH("Griha Pravesh", "गृह प्रवेश"),
    MUHURTA_EVENT_BUSINESS_START("Business Start", "व्यापार शुरुआत"),
    MUHURTA_EVENT_TRAVEL("Travel", "यात्रा"),
    MUHURTA_EVENT_VEHICLE_PURCHASE("Vehicle Purchase", "सवारी खरिद"),
    MUHURTA_EVENT_EDUCATION_START("Education Start", "शिक्षा शुरुआत"),
    MUHURTA_EVENT_MEDICAL_TREATMENT("Medical Treatment", "चिकित्सा उपचार"),
    MUHURTA_EVENT_CONSTRUCTION("Construction", "निर्माण"),
    MUHURTA_EVENT_INVESTMENT("Investment", "लगानी"),
    MUHURTA_EVENT_NAME_CEREMONY("Name Ceremony", "नामकरण"),
    MUHURTA_EVENT_MUNDAN("Mundan", "चौलकर्म"),
    MUHURTA_EVENT_UPANAYANA("Upanayana", "ब्रतबन्ध"),

    // ============================================
    // VARSHAPHALA - SAHAMS
    // ============================================
    SAHAM_KARYASIDDHI("Karyasiddhi Saham", "कार्यसिद्धि सहम"),
    SAHAM_SUCCESS("Success", "सफलता"),
    SAHAM_FORTUNE("Fortune Saham", "भाग्य सहम"),
    SAHAM_WEALTH("Wealth Saham", "धन सहम"),
    SAHAM_MARRIAGE("Marriage Saham", "विवाह सहम"),
    SAHAM_CHILDREN("Children Saham", "सन्तान सहम"),
    SAHAM_HEALTH("Health Saham", "स्वास्थ्य सहम"),
    SAHAM_EDUCATION("Education Saham", "शिक्षा सहम"),
    SAHAM_TRAVEL("Travel Saham", "यात्रा सहम"),
    SAHAM_PROFESSION("Profession Saham", "पेशा सहम"),

    // ============================================
    // HOUSE SIGNIFICATIONS
    // ============================================
    HOUSE_DHARMA("Dharma (1, 5, 9)", "धर्म (१, ५, ९)"),
    HOUSE_ARTHA("Artha (2, 6, 10)", "अर्थ (२, ६, १०)"),
    HOUSE_KAMA("Kama (3, 7, 11)", "काम (३, ७, ११)"),
    HOUSE_MOKSHA("Moksha (4, 8, 12)", "मोक्ष (४, ८, १२)"),

    // ============================================
    // PRASHNA CALCULATOR
    // ============================================
    PRASHNA_CAT_GENERAL("General", "सामान्य"),
    PRASHNA_CAT_CAREER("Career & Profession", "क्यारियर र पेशा"),
    PRASHNA_CAT_RELATIONSHIP("Relationships", "सम्बन्धहरू"),
    PRASHNA_CAT_HEALTH("Health", "स्वास्थ्य"),
    PRASHNA_CAT_FINANCE("Finance", "वित्त"),
    PRASHNA_CAT_TRAVEL("Travel", "यात्रा"),
    PRASHNA_CAT_EDUCATION("Education", "शिक्षा"),
    PRASHNA_CAT_LEGAL("Legal Matters", "कानुनी मामिलाहरू"),
    PRASHNA_CAT_PROPERTY("Property", "सम्पत्ति"),
    PRASHNA_CAT_LOST_ITEM("Lost Items", "हराएका वस्तुहरू"),

    // ============================================
    // RAJJU ARUDHA
    // ============================================
    RAJJU_ASCENDING("Aarohana (Ascending)", "आरोहण"),
    RAJJU_DESCENDING("Avarohana (Descending)", "अवरोहण"),

    // ============================================
    // MATCHMAKING - ADDITIONAL FACTORS
    // ============================================
    MAHENDRA_FAVORABLE("Mahendra favorable at position {count} - promotes longevity and progeny", "महेन्द्र स्थिति {count} मा अनुकूल - दीर्घायु र सन्तान प्रवर्धन गर्छ"),
    MAHENDRA_NOT_APPLICABLE("Mahendra position not in favorable sequence", "महेन्द्र स्थिति अनुकूल क्रममा छैन"),
    VEDHA_PRESENT("Vedha (obstruction) present between {nak1} and {nak2} - may cause obstacles", "वेध (बाधा) {nak1} र {nak2} बीच उपस्थित - अवरोधहरू हुन सक्छ"),
    VEDHA_NOT_PRESENT("No Vedha between the nakshatras - favorable", "नक्षत्रहरू बीच वेध छैन - अनुकूल"),
    RAJJU_COMPATIBLE("Different Rajju types - compatible, no concerns related to body part compatibility", "फरक रज्जु प्रकार - अनुकूल, शरीर भाग अनुकूलतासम्बन्धी कुनै चिन्ता छैन"),
    RAJJU_SAME_DIFF_ARUDHA("Same {rajju} but different Arudha ({arudha1} vs {arudha2}) - partially compatible, reduced concern", "उही {rajju} तर फरक अरुढा ({arudha1} बनाम {arudha2}) - आंशिक अनुकूल, कम चिन्ता"),
    RAJJU_SAME_SAME_ARUDHA("Same {rajju} ({body}) and same {arudha} - Rajju Dosha present. {warning}", "उही {rajju} ({body}) र उही {arudha} - रज्जु दोष उपस्थित। {warning}"),

    // ============================================
    // MATCHMAKING - SPECIAL CONSIDERATIONS
    // ============================================
    SPECIAL_NADI_DOSHA("NADI DOSHA: Same Nadi can affect health and progeny. Consider remedies before proceeding.", "नाडी दोष: उही नाडीले स्वास्थ्य र सन्तानमा असर पार्न सक्छ। अगाडि बढ्नु अघि उपायहरू विचार गर्नुहोस्।"),
    SPECIAL_BHAKOOT_DOSHA("BHAKOOT DOSHA present: {analysis}. May affect love, finances, or health.", "भकुट दोष उपस्थित: {analysis}। प्रेम, वित्त, वा स्वास्थ्यमा असर पर्न सक्छ।"),
    SPECIAL_BRIDE_MANGLIK("MANGLIK IMBALANCE: Bride has {dosha} while Groom is non-Manglik. Kumbh Vivah or remedies strongly advised.", "मांगलिक असन्तुलन: दुलहीमा {dosha} छ जबकि दुलाहा गैर-मांगलिक छन्। कुम्भ विवाह वा उपायहरू दृढतापूर्वक सल्लाह दिइएको।"),
    SPECIAL_GROOM_MANGLIK("MANGLIK IMBALANCE: Groom has {dosha} while Bride is non-Manglik. Remedies strongly recommended.", "मांगलिक असन्तुलन: दुलाहामा {dosha} छ जबकि दुलही गैर-मांगलिक छिन्। उपायहरू दृढतापूर्वक सिफारिस गरिएको।"),
    SPECIAL_GANA_INCOMPAT("GANA INCOMPATIBILITY: Opposite temperaments (Deva-Rakshasa). May cause frequent conflicts without conscious effort.", "गण असंगतता: विपरीत स्वभाव (देव-राक्षस)। सचेत प्रयास बिना बारम्बार द्वन्द्व हुन सक्छ।"),
    SPECIAL_YONI_INCOMPAT("YONI INCOMPATIBILITY: Enemy Yonis present. Physical and instinctual harmony may require extra effort.", "योनि असंगतता: शत्रु योनि उपस्थित। शारीरिक र सहज सामञ्जस्यको लागि अतिरिक्त प्रयास आवश्यक पर्न सक्छ।"),
    SPECIAL_VEDHA("VEDHA PRESENT: {details}. Nakshatra obstruction may cause challenges in specific life areas.", "वेध उपस्थित: {details}। नक्षत्र बाधाले जीवनका विशेष क्षेत्रहरूमा चुनौतीहरू ल्याउन सक्छ।"),
    SPECIAL_RAJJU("RAJJU DOSHA: {details}. Related body part may face health concerns in marriage.", "रज्जु दोष: {details}। सम्बन्धित शरीर भागमा विवाहमा स्वास्थ्य चिन्ता हुन सक्छ।"),
    SPECIAL_STREE_DEERGHA("STREE DEERGHA not satisfied: Nakshatra difference is {diff} (requires 13+). Bride's prosperity may need attention.", "स्त्री दीर्घ सन्तुष्ट छैन: नक्षत्र भिन्नता {diff} छ (१३+ आवश्यक)। दुलहीको समृद्धिमा ध्यान दिनुपर्ने हुन सक्छ।"),
    SPECIAL_MULTIPLE_LOW("MULTIPLE CONCERNS: {count} Gunas scored below threshold. Overall compatibility requires attention.", "बहु चिन्ताहरू: {count} गुणले न्यूनतम भन्दा कम अंक पाए। समग्र अनुकूलतामा ध्यान आवश्यक।"),
    SPECIAL_7TH_LORDS_ENEMY("7TH HOUSE LORDS INCOMPATIBLE: {lord1} and {lord2} are mutual enemies. Marriage house lords in conflict.", "७औं भाव स्वामी असंगत: {lord1} र {lord2} पारस्परिक शत्रु हुन्। विवाह भाव स्वामीहरू द्वन्द्वमा।"),
    SPECIAL_NO_ISSUES("No significant special concerns noted. The match appears harmonious across additional factors.", "कुनै महत्त्वपूर्ण विशेष चिन्ता छैन। मिलान अतिरिक्त कारकहरूमा सामञ्जस्यपूर्ण देखिन्छ।"),

    // ============================================
    // MATCHMAKING - REMEDIES
    // ============================================
    REMEDY_NADI_1("Nadi Dosha: Donate grains, gold, or cow on an auspicious day after consulting a priest", "नाडी दोष: पुजारीसँग परामर्श पछि शुभ दिनमा अन्न, सुन, वा गाई दान गर्नुहोस्"),
    REMEDY_NADI_2("Nadi Dosha: Perform Maha Mrityunjaya Jaap (108 times daily for 40 days)", "नाडी दोष: महा मृत्युञ्जय जाप गर्नुहोस् (४० दिन दैनिक १०८ पटक)"),
    REMEDY_NADI_3("Nadi Dosha: Worship Lord Shiva and Goddess Parvati together on Mondays", "नाडी दोष: सोमबारमा भगवान शिव र देवी पार्वतीको सँगै पूजा गर्नुहोस्"),
    REMEDY_BHAKOOT_1("Bhakoot Dosha: Perform Graha Shanti puja for Moon sign lords of both partners", "भकुट दोष: दुवै साझेदारको चन्द्र राशि स्वामीको ग्रह शान्ति पूजा गर्नुहोस्"),
    REMEDY_BHAKOOT_2("Bhakoot Dosha: Chant Vishnu Sahasranama on Thursdays for 21 weeks", "भकुट दोष: २१ हप्ता बिहीबारमा विष्णु सहस्रनाम जाप गर्नुहोस्"),
    REMEDY_SHADASHTAK("Shadashtak (6-8) Dosha: Perform Rudrabhishek and donate medicines to the needy", "षडाष्टक (६-८) दोष: रुद्राभिषेक गर्नुहोस् र जरुरतमन्दलाई औषधि दान गर्नुहोस्"),
    REMEDY_MANGLIK_1("Manglik Dosha: Perform Kumbh Vivah (symbolic marriage to a pot or banana tree) before actual marriage", "मांगलिक दोष: वास्तविक विवाह अघि कुम्भ विवाह (घैंटो वा केराको बोटसँग प्रतीकात्मक विवाह) गर्नुहोस्"),
    REMEDY_MANGLIK_2("Manglik Dosha: Chant Mangal Mantra 'Om Kraam Kreem Kraum Sah Bhaumaya Namah' 108 times on Tuesdays", "मांगलिक दोष: मंगलबारमा मंगल मन्त्र 'ॐ क्रां क्रीं क्रौं सः भौमाय नमः' १०८ पटक जाप गर्नुहोस्"),
    REMEDY_MANGLIK_3("Manglik Dosha: Wear a Red Coral (Moonga) after proper energization and astrological consultation", "मांगलिक दोष: उचित ऊर्जावान र ज्योतिषीय परामर्श पछि मुंगा (प्रवाल) धारण गर्नुहोस्"),
    REMEDY_MANGLIK_BRIDE("For Bride's Manglik: Visit Hanuman temple on Tuesdays and offer vermilion and jasmine oil", "दुलहीको मांगलिकको लागि: मंगलबारमा हनुमान मन्दिरमा जानुहोस् र सिन्दूर र चमेलीको तेल चढाउनुहोस्"),
    REMEDY_MANGLIK_GROOM("For Groom's Manglik: Perform Mars-related charity on Tuesdays (donate red lentils, jaggery, copper)", "दुलाहाको मांगलिकको लागि: मंगलबारमा मंगल-सम्बन्धित दान गर्नुहोस् (रातो दाल, गुड, तामा दान गर्नुहोस्)"),
    REMEDY_DOUBLE_MANGLIK("Double Manglik: Requires extended Kumbh Vivah ritual and 11 Rudrabhisheks over 11 consecutive Mondays", "दोहोरो मांगलिक: विस्तारित कुम्भ विवाह विधि र लगातार ११ सोमबारमा ११ रुद्राभिषेक आवश्यक"),
    REMEDY_GANA_1("Gana Incompatibility: Chant Ganapati Atharvasheersham daily for 41 days", "गण असंगतता: ४१ दिन दैनिक गणपति अथर्वशीर्षम् जाप गर्नुहोस्"),
    REMEDY_GANA_2("Gana Incompatibility: Perform Navgraha Shanti puja together before marriage", "गण असंगतता: विवाह अघि सँगै नवग्रह शान्ति पूजा गर्नुहोस्"),
    REMEDY_GANA_3("Gana Incompatibility: Practice mutual respect and conscious communication daily", "गण असंगतता: दैनिक पारस्परिक सम्मान र सचेत सञ्चार अभ्यास गर्नुहोस्"),
    REMEDY_GRAHA_MAITRI_1("Graha Maitri: Strengthen Mercury through green charity and Budh mantra chanting on Wednesdays", "ग्रह मैत्री: बुधबारमा हरियो दान र बुध मन्त्र जापद्वारा बुध बलियो बनाउनुहोस्"),
    REMEDY_GRAHA_MAITRI_2("Graha Maitri: Both partners should meditate together daily to improve mental harmony", "ग्रह मैत्री: मानसिक सामञ्जस्य सुधार गर्न दुवै साझेदारले दैनिक सँगै ध्यान गर्नुपर्छ"),
    REMEDY_YONI_1("Yoni Incompatibility: Worship Kamadeva (God of Love) and offer flowers on Fridays", "योनि असंगतता: शुक्रबारमा कामदेव (प्रेमको देवता) पूजा गर्नुहोस् र फूल चढाउनुहोस्"),
    REMEDY_YONI_2("Yoni Incompatibility: Perform Ashwamedha or Gajamedha symbolic puja to neutralize animal enmity", "योनि असंगतता: पशु वैमनस्य निष्क्रिय गर्न अश्वमेध वा गजमेध प्रतीकात्मक पूजा गर्नुहोस्"),
    REMEDY_VEDHA_1("Vedha Dosha: Perform Nakshatra Shanti puja for both birth stars", "वेध दोष: दुवै जन्म नक्षत्रको लागि नक्षत्र शान्ति पूजा गर्नुहोस्"),
    REMEDY_VEDHA_2("Vedha Dosha: Donate black sesame and urad dal on Saturdays", "वेध दोष: शनिबारमा कालो तिल र उडदको दाल दान गर्नुहोस्"),
    REMEDY_RAJJU_SIRO("Siro Rajju Dosha (Head): Perform Ayushya Homa and worship Lord Mrityunjaya Shiva", "शिरो रज्जु दोष (शिर): आयुष्य होम गर्नुहोस् र भगवान मृत्युञ्जय शिवको पूजा गर्नुहोस्"),
    REMEDY_RAJJU_KANTHA("Kantha Rajju Dosha (Neck): Wear Rudraksha mala and chant Vishnu mantras", "कण्ठ रज्जु दोष (घाँटी): रुद्राक्ष माला लगाउनुहोस् र विष्णु मन्त्र जाप गर्नुहोस्"),
    REMEDY_RAJJU_NABHI("Nabhi Rajju Dosha (Navel): Perform Santan Gopal puja and donate to orphanages", "नाभि रज्जु दोष (नाभि): सन्तान गोपाल पूजा गर्नुहोस् र अनाथाश्रममा दान गर्नुहोस्"),
    REMEDY_RAJJU_KATI("Kati Rajju Dosha (Waist): Perform Lakshmi puja and donate to poverty relief", "कटि रज्जु दोष (कम्मर): लक्ष्मी पूजा गर्नुहोस् र गरिबी उन्मूलनमा दान गर्नुहोस्"),
    REMEDY_RAJJU_PADA("Pada Rajju Dosha (Feet): Worship at a pilgrimage site together before marriage", "पाद रज्जु दोष (पाउ): विवाह अघि सँगै तीर्थस्थलमा पूजा गर्नुहोस्"),
    REMEDY_GENERAL_1("General: Perform Satyanarayan Puja together on Purnima (full moon)", "सामान्य: पूर्णिमामा सँगै सत्यनारायण पूजा गर्नुहोस्"),
    REMEDY_GENERAL_2("General: Chant Swayamvara Parvati Mantra for marital harmony", "सामान्य: वैवाहिक सामञ्जस्यको लागि स्वयंवर पार्वती मन्त्र जाप गर्नुहोस्"),
    REMEDY_GENERAL_3("General: Donate to couples in need or contribute to marriage funds for the poor", "सामान्य: जरुरतमन्द जोडीहरूलाई दान गर्नुहोस् वा गरिबको विवाह कोषमा योगदान गर्नुहोस्"),
    REMEDY_GENERAL_4("General: Both partners should observe Monday fasts for Lord Shiva", "सामान्य: दुवै साझेदारले भगवान शिवको लागि सोमबार व्रत गर्नुपर्छ"),
    REMEDY_SERIOUS_1("Serious Concerns: Consult a qualified Vedic astrologer for personalized guidance", "गम्भीर चिन्ताहरू: व्यक्तिगत मार्गदर्शनको लागि योग्य वैदिक ज्योतिषीसँग परामर्श गर्नुहोस्"),
    REMEDY_SERIOUS_2("Serious Concerns: Consider Maha Mrityunjaya Homa for overall protection", "गम्भीर चिन्ताहरू: समग्र सुरक्षाको लागि महा मृत्युञ्जय होम विचार गर्नुहोस्"),
    REMEDY_NONE_NEEDED("Excellent compatibility - no specific remedies required.", "उत्कृष्ट अनुकूलता - कुनै विशेष उपाय आवश्यक छैन।"),
    REMEDY_SATYANARAYAN("For auspiciousness: Perform Satyanarayan Katha together on the first Purnima after marriage", "शुभताको लागि: विवाह पछिको पहिलो पूर्णिमामा सँगै सत्यनारायण कथा गर्नुहोस्"),

    // ============================================
    // MATCHMAKING - SUMMARY & ANALYSIS STRINGS
    // ============================================
    SUMMARY_TITLE("KUNDLI MILAN SUMMARY", "कुण्डली मिलान सारांश"),
    SUMMARY_OVERALL_SCORE("Overall Score", "समग्र अंक"),
    SUMMARY_RATING("Rating", "मूल्यांकन"),
    SUMMARY_STRENGTHS("Strengths", "शक्तिहरू"),
    SUMMARY_CONCERNS("Areas of Concern", "चिन्ताका क्षेत्रहरू"),
    SUMMARY_MANGLIK("Manglik Status", "मांगलिक स्थिति"),
    SUMMARY_ADDITIONAL("Additional Factors", "अतिरिक्त कारकहरू"),
    SUMMARY_RECOMMENDATION("Recommendation", "सिफारिस"),
    DETAILED_TITLE("DETAILED MATCHMAKING ANALYSIS", "विस्तृत मिलान विश्लेषण"),
    BIRTH_DATA_SUMMARY("Birth Data Summary", "जन्म डाटा सारांश"),
    MOON_SIGN("Moon Sign", "चन्द्र राशि"),
    NAKSHATRA_LABEL("Nakshatra", "नक्षत्र"),
    PADA_LABEL("Pada", "पाद"),
    MOON_LONGITUDE("Moon Longitude", "चन्द्र देशान्तर"),
    PURPOSE("Purpose", "उद्देश्य"),
    ANALYSIS_LABEL("Analysis", "विश्लेषण"),
    ADDITIONAL_FACTORS_TITLE("Additional Compatibility Factors", "अतिरिक्त अनुकूलता कारकहरू"),
    OBSTRUCTION("Obstruction", "बाधा"),
    COSMIC_BOND("Cosmic Bond", "ब्रह्माण्डीय बन्धन"),
    WARNING_LABEL("Warning", "चेतावनी"),
    WIFE_PROSPERITY("Wife's Prosperity", "पत्नीको समृद्धि"),
    NAKSHATRA_DIFF("Nakshatra Difference", "नक्षत्र भिन्नता"),
    SATISFIED("Satisfied", "सन्तुष्ट"),
    NOT_SATISFIED("Not Satisfied", "सन्तुष्ट छैन"),
    LONGEVITY_PROSPERITY("Longevity & Prosperity", "दीर्घायु र समृद्धि"),
    NOT_APPLICABLE("Not Applicable", "लागू हुँदैन"),

    // ============================================
    // MATCHMAKING - COMMON STATUS STRINGS
    // ============================================
    BRIDE("Bride", "दुलही"),
    GROOM("Groom", "दुलाहा"),
    STATUS("Status", "स्थिति"),
    COMPATIBLE("Compatible", "अनुकूल"),
    NEEDS_ATTENTION("Needs Attention", "ध्यान आवश्यक"),
    PRESENT("Present", "उपस्थित"),
    NOT_PRESENT("Not Present", "उपस्थित छैन"),
    FAVORABLE("Favorable", "अनुकूल"),
    SAME_RAJJU("Same Rajju", "उही रज्जु"),
    DETAILS("Details", "विवरण"),

    // ============================================
    // MATCHMAKING - GUNA DESCRIPTIONS
    // ============================================
    GUNA_DESC_VARNA("Varna indicates spiritual compatibility based on the Moon sign's element. It measures ego harmony and how partners relate on a spiritual level. Higher compatibility suggests natural understanding of values.", "वर्णले चन्द्र राशिको तत्वमा आधारित आध्यात्मिक अनुकूलता संकेत गर्दछ। यसले अहंकार सामञ्जस्य र साझेदारहरू आध्यात्मिक स्तरमा कसरी सम्बन्धित छन् मापन गर्दछ।"),
    GUNA_DESC_VASHYA("Vashya measures the mutual attraction and influence between partners. It indicates who can influence whom and the power dynamics in the relationship.", "वश्यले साझेदारहरू बीचको पारस्परिक आकर्षण र प्रभाव मापन गर्दछ। यसले कसले कसलाई प्रभाव पार्न सक्छ र सम्बन्धमा शक्ति गतिशीलता संकेत गर्दछ।"),
    GUNA_DESC_TARA("Tara analyzes destiny compatibility through the birth stars (Nakshatras). It determines the auspiciousness of the couple's combined destiny path.", "तारा जन्म तारा (नक्षत्र) मार्फत भाग्य अनुकूलता विश्लेषण गर्दछ। यसले जोडीको संयुक्त भाग्य मार्गको शुभता निर्धारण गर्दछ।"),
    GUNA_DESC_YONI("Yoni represents physical and sexual compatibility based on the animal nature assigned to each Nakshatra. Same or friendly animals indicate better physical harmony.", "योनीले प्रत्येक नक्षत्रलाई तोकिएको पशु प्रकृतिमा आधारित शारीरिक र यौन अनुकूलता प्रतिनिधित्व गर्दछ। उही वा मित्र पशुहरूले राम्रो शारीरिक सामञ्जस्य संकेत गर्दछ।"),
    GUNA_DESC_GRAHA_MAITRI("Graha Maitri analyzes mental compatibility through the friendship of Moon sign lords. It indicates how well the couple can understand each other intellectually.", "ग्रह मैत्रीले चन्द्र राशि स्वामीहरूको मित्रता मार्फत मानसिक अनुकूलता विश्लेषण गर्दछ। यसले जोडीले बौद्धिक रूपमा एकअर्कालाई कत्तिको राम्रोसँग बुझ्न सक्छ संकेत गर्दछ।"),
    GUNA_DESC_GANA("Gana measures temperament compatibility through Deva (divine), Manushya (human), or Rakshasa (demon) classification based on Nakshatra.", "गणले नक्षत्रमा आधारित देव, मनुष्य, वा राक्षस वर्गीकरण मार्फत स्वभाव अनुकूलता मापन गर्दछ।"),
    GUNA_DESC_BHAKOOT("Bhakoot indicates love, health, and financial compatibility based on the Moon sign positions. It's crucial for long-term marital harmony.", "भकुटले चन्द्र राशि स्थितिहरूमा आधारित प्रेम, स्वास्थ्य र आर्थिक अनुकूलता संकेत गर्दछ। यो दीर्घकालीन वैवाहिक सामञ्जस्यको लागि महत्त्वपूर्ण छ।"),
    GUNA_DESC_NADI("Nadi is the most important factor (8 points), indicating health and progeny compatibility. Same Nadi can cause health issues and affect children.", "नाडी सबैभन्दा महत्त्वपूर्ण कारक (८ अंक) हो, स्वास्थ्य र सन्तान अनुकूलता संकेत गर्दछ। उही नाडीले स्वास्थ्य समस्या र सन्तानमा असर पार्न सक्छ।"),
    GUNA_DESC_NOT_AVAILABLE("Detailed description not available for this Guna.", "यस गुणको विस्तृत विवरण उपलब्ध छैन।"),

    // ============================================
    // MATCHMAKING - DETAILED GUNA INTERPRETATIONS
    // ============================================
    // Varna Detailed
    VARNA_DETAILED_COMPATIBLE("This creates excellent spiritual harmony with natural respect flowing from bride to groom. Partners will have compatible life philosophies, values, and approaches to dharma. The groom's natural leadership in spiritual matters will be appreciated.", "यसले दुलहीबाट दुलाहातिर प्राकृतिक सम्मान प्रवाहसहित उत्कृष्ट आध्यात्मिक सामञ्जस्य सिर्जना गर्दछ। साझेदारहरूको जीवन दर्शन, मूल्यहरू र धर्मप्रति दृष्टिकोण मिल्नेछ। आध्यात्मिक विषयहरूमा दुलाहाको प्राकृतिक नेतृत्व सराहना गरिनेछ।"),
    VARNA_DETAILED_INCOMPATIBLE("This may create ego conflicts and power struggles. The bride may feel she has to compromise her values or suppress her intellect. Both partners need conscious effort to respect each other's perspectives and maintain balance.", "यसले अहंकार द्वन्द्व र शक्ति संघर्ष सिर्जना गर्न सक्छ। दुलहीले आफ्ना मूल्यहरूमा सम्झौता गर्नु परोस् वा आफ्नो बुद्धि दमन गर्नुपर्ला जस्तो महसुस गर्न सक्छिन्। दुवै साझेदारहरूले एकअर्काको दृष्टिकोणलाई सम्मान गर्न र सन्तुलन कायम राख्न सचेत प्रयास चाहिन्छ।"),

    // Vashya Detailed
    VASHYA_REMEDY_LOW("Remedy: Worship Lord Shiva and Goddess Parvati together on Mondays. Practice mutual respect and avoid trying to dominate each other. Strengthen Venus through Friday fasts and donations of white items.", "उपाय: सोमबारमा भगवान शिव र देवी पार्वतीको सँगै पूजा गर्नुहोस्। पारस्परिक सम्मान अभ्यास गर्नुहोस् र एकअर्कालाई हावी गर्न नगर्नुहोस्। शुक्रबार व्रत र सेतो वस्तुहरूको दानद्वारा शुक्रलाई बलियो बनाउनुहोस्।"),

    // Tara Detailed
    TARA_REMEDY_LOW("Remedy: Perform Nakshatra Shanti puja for both birth stars. Donate black sesame, iron, and blue cloth on Saturdays. Chant Maha Mrityunjaya Mantra 108 times daily for 40 days to neutralize destiny obstacles.", "उपाय: दुवै जन्म नक्षत्रको लागि नक्षत्र शान्ति पूजा गर्नुहोस्। शनिबारमा कालो तिल, फलाम र नीलो कपडा दान गर्नुहोस्। भाग्य बाधाहरू निष्क्रिय गर्न ४० दिन दैनिक १०८ पटक महा मृत्युञ्जय मन्त्र जाप गर्नुहोस्।"),

    // Yoni Detailed
    YONI_REMEDY_LOW("Remedy: Worship Kamadeva (God of Love) on Fridays. Offer jasmine flowers, rose water, and sandalwood. Practice physical affection and conscious intimacy. Strengthen Venus by wearing diamond or white sapphire after proper consultation.", "उपाय: शुक्रबारमा कामदेव (प्रेमको देवता) पूजा गर्नुहोस्। जुईको फूल, गुलाबको पानी र चन्दन चढाउनुहोस्। शारीरिक स्नेह र सचेत अन्तरंगता अभ्यास गर्नुहोस्। उचित परामर्श पछि हीरा वा सेतो नीलम लगाएर शुक्रलाई बलियो बनाउनुहोस्।"),

    // Graha Maitri Detailed
    GRAHA_MAITRI_REMEDY_LOW("Remedy: Meditate together daily for at least 15 minutes. Practice active listening and clear communication. Strengthen Mercury by chanting Budh mantra on Wednesdays, donating green items, and wearing emerald after consultation.", "उपाय: दैनिक कम्तिमा १५ मिनेट सँगै ध्यान गर्नुहोस्। सक्रिय सुनाइ र स्पष्ट सञ्चार अभ्यास गर्नुहोस्। बुधबारमा बुध मन्त्र जाप गरेर, हरियो वस्तुहरू दान गरेर र परामर्श पछि पन्ना लगाएर बुधलाई बलियो बनाउनुहोस्।"),

    // Gana Detailed
    GANA_REMEDY_LOW("Remedy: Chant Ganapati Atharvasheersham daily for 41 days. Perform joint Ganesh puja every month. Practice patience, tolerance, and conscious communication to bridge temperamental differences. Couples counseling may be beneficial.", "उपाय: ४१ दिन दैनिक गणपति अथर्वशीर्षम् जाप गर्नुहोस्। प्रत्येक महिना संयुक्त गणेश पूजा गर्नुहोस्। स्वभावगत भिन्नताहरू पाट्न धैर्य, सहनशीलता र सचेत सञ्चार अभ्यास गर्नुहोस्। जोडी परामर्श लाभदायक हुन सक्छ।"),

    // Bhakoot Detailed
    BHAKOOT_REMEDY_LOW("Remedy: Perform Graha Shanti puja for Moon sign lords of both partners. Chant Vishnu Sahasranama on Thursdays for 21 weeks. Donate white items, rice, and silver on Mondays. Financial planning together is essential.", "उपाय: दुवै साझेदारको चन्द्र राशि स्वामीको ग्रह शान्ति पूजा गर्नुहोस्। २१ हप्ता बिहीबारमा विष्णु सहस्रनाम जाप गर्नुहोस्। सोमबारमा सेता वस्तुहरू, चामल र चाँदी दान गर्नुहोस्। सँगै वित्तीय योजना आवश्यक छ।"),

    // Nadi Detailed
    NADI_REMEDY_LOW("Remedy: This is critical - consult qualified astrologer immediately. Perform Maha Mrityunjaya Jaap (108 times daily for 40 days). Donate grains, gold, or cow on auspicious day. Worship Lord Shiva and Goddess Parvati together on Mondays. Consider Kumbh Vivah if dosha is severe.", "उपाय: यो महत्त्वपूर्ण छ - तुरुन्त योग्य ज्योतिषीसँग परामर्श गर्नुहोस्। महा मृत्युञ्जय जाप गर्नुहोस् (४० दिन दैनिक १०८ पटक)। शुभ दिनमा अन्न, सुन वा गाई दान गर्नुहोस्। सोमबारमा भगवान शिव र देवी पार्वतीको सँगै पूजा गर्नुहोस्। दोष गम्भीर भएमा कुम्भ विवाह विचार गर्नुहोस्।"),

    // ============================================
    // MATCHMAKING - EXPANDED GUNA ANALYSIS
    // ============================================
    // Varna Expanded
    VARNA_IMPORTANCE("Varna represents the spiritual evolution and work nature of the couple. It measures ego compatibility and mutual respect in the relationship.", "वर्णले जोडीको आध्यात्मिक विकास र कार्य प्रकृति प्रतिनिधित्व गर्दछ। यसले सम्बन्धमा अहंकार अनुकूलता र पारस्परिक सम्मान मापन गर्दछ।"),
    VARNA_BRAHMIN_NATURE("Brahmin (Fire signs): Knowledge seekers, intellectual, teaching orientation, spiritual inclinations", "ब्राह्मण (अग्नि राशि): ज्ञान खोज्ने, बौद्धिक, शिक्षण अभिमुखीकरण, आध्यात्मिक झुकाव"),
    VARNA_KSHATRIYA_NATURE("Kshatriya (Earth signs): Leaders, protectors, administrative skills, power-oriented", "क्षत्रिय (पृथ्वी राशि): नेता, रक्षक, प्रशासनिक कौशल, शक्ति-उन्मुख"),
    VARNA_VAISHYA_NATURE("Vaishya (Air signs): Business minded, commerce, communication, wealth creation", "वैश्य (वायु राशि): व्यापार मानसिकता, वाणिज्य, सञ्चार, धन सिर्जना"),
    VARNA_SHUDRA_NATURE("Shudra (Water signs): Service orientation, emotional depth, nurturing, supportive", "शूद्र (जल राशि): सेवा अभिमुखीकरण, भावनात्मक गहिराई, पालन-पोषण, सहयोगी"),

    // Vashya Expanded
    VASHYA_IMPORTANCE("Vashya indicates natural attraction, influence capacity, and control dynamics. It shows who holds sway in decision-making and emotional matters.", "वश्यले प्राकृतिक आकर्षण, प्रभाव क्षमता र नियन्त्रण गतिशीलता संकेत गर्दछ। यसले निर्णय लिने र भावनात्मक मामिलाहरूमा कसले प्रभाव राख्छ भनेर देखाउँछ।"),
    VASHYA_POWER_BALANCE("Balanced Vashya creates equal partnership. Imbalance may lead to dominance issues but can work if both partners accept their natural roles.", "सन्तुलित वश्यले समान साझेदारी सिर्जना गर्दछ। असन्तुलनले प्रभुत्व समस्या ल्याउन सक्छ तर दुवै साझेदारहरूले आफ्नो प्राकृतिक भूमिका स्वीकार गरे काम गर्न सक्छ।"),

    // Tara Expanded
    TARA_IMPORTANCE("Tara analyzes birth star compatibility to determine destiny harmony. It predicts how well the couple's life paths align and support each other.", "तारा भाग्य सामञ्जस्य निर्धारण गर्न जन्म तारा अनुकूलता विश्लेषण गर्दछ। यसले जोडीको जीवन मार्गहरू कत्तिको राम्रोसँग मिल्छन् र एकअर्कालाई समर्थन गर्छन् भनेर भविष्यवाणी गर्दछ।"),
    TARA_CATEGORIES_EXPLAINED("Nakshatras are grouped in sets of 9 from each person's birth star. Janma, Vipat, and Vadha taras are inauspicious; Sampat, Kshema, Sadhana, and Mitra taras are auspicious.", "नक्षत्रहरू प्रत्येक व्यक्तिको जन्म ताराबाट ९ को सेटमा समूहीकृत गरिन्छ। जन्म, विपत् र वध तारा अशुभ हुन्; सम्पत्, क्षेम, साधना र मित्र तारा शुभ हुन्।"),

    // Yoni Expanded
    YONI_IMPORTANCE("Yoni represents physical, sexual, and biological compatibility. It's based on animal nature of birth nakshatras and indicates instinctual harmony.", "योनिले शारीरिक, यौन र जैविक अनुकूलता प्रतिनिधित्व गर्दछ। यो जन्म नक्षत्रको पशु प्रकृतिमा आधारित छ र सहज सामञ्जस्य संकेत गर्दछ।"),
    YONI_RELATIONSHIPS_EXPLAINED("Same yoni = Perfect (4 points). Friendly yonis = Very Good (3-4 points). Neutral = Moderate (2 points). Enemy yonis = Poor (0-1 points).", "उही योनि = उत्तम (४ अंक)। मित्र योनि = धेरै राम्रो (३-४ अंक)। तटस्थ = मध्यम (२ अंक)। शत्रु योनि = कमजोर (०-१ अंक)।"),

    // Graha Maitri Expanded
    GRAHA_MAITRI_IMPORTANCE("Graha Maitri examines mental compatibility through planetary friendship of Moon sign lords. It indicates intellectual rapport and communication ease.", "ग्रह मैत्रीले चन्द्र राशि स्वामीहरूको ग्रह मित्रताद्वारा मानसिक अनुकूलता जाँच गर्दछ। यसले बौद्धिक सम्बन्ध र सञ्चार सहजता संकेत गर्दछ।"),
    GRAHA_MAITRI_LEVELS("Same lord/Friends = 5 points. One friend + one neutral = 4 points. Both neutral = 3 points. One enemy = 1-2 points. Both enemies = 0.5 points.", "उही स्वामी/मित्र = ५ अंक। एक मित्र + एक तटस्थ = ४ अंक। दुवै तटस्थ = ३ अंक। एक शत्रु = १-२ अंक। दुवै शत्रु = ०.५ अंक।"),

    // Gana Expanded
    GANA_IMPORTANCE("Gana measures behavioral temperament and nature. It indicates how partners will behave in conflicts and daily life situations.", "गणले व्यवहारिक स्वभाव र प्रकृति मापन गर्दछ। यसले साझेदारहरूले द्वन्द्व र दैनिक जीवन परिस्थितिहरूमा कसरी व्यवहार गर्छन् भनेर संकेत गर्दछ।"),
    GANA_COMPATIBILITY_DETAILS("Deva-Deva = 6 points (best). Manushya-Manushya = 6 points. Rakshasa-Rakshasa = 6 points. Deva-Manushya = 5 points. Manushya-Rakshasa = 2 points. Deva-Rakshasa = 0 points (worst).", "देव-देव = ६ अंक (उत्तम)। मनुष्य-मनुष्य = ६ अंक। राक्षस-राक्षस = ६ अंक। देव-मनुष्य = ५ अंक। मनुष्य-राक्षस = २ अंक। देव-राक्षस = ० अंक (सबैभन्दा खराब)।"),

    // Bhakoot Expanded
    BHAKOOT_IMPORTANCE("Bhakoot assesses emotional love, family welfare, and financial prosperity. It's based on sign positions and is crucial for long-term happiness.", "भकुटले भावनात्मक प्रेम, पारिवारिक कल्याण र आर्थिक समृद्धि मूल्याङ्कन गर्दछ। यो राशि स्थितिहरूमा आधारित छ र दीर्घकालीन खुशीको लागि महत्त्वपूर्ण छ।"),
    BHAKOOT_DOSHA_TYPES("No dosha = 7 points. 2-12 position (Dhan-Vyaya dosha) = Financial concerns. 6-8 position (Shadashtak dosha) = Health and separation concerns (most serious).", "कुनै दोष छैन = ७ अंक। २-१२ स्थिति (धन-व्यय दोष) = आर्थिक चिन्ता। ६-८ स्थिति (षडाष्टक दोष) = स्वास्थ्य र विच्छेद चिन्ता (सबैभन्दा गम्भीर)।"),

    // Nadi Expanded
    NADI_IMPORTANCE("Nadi is the MOST IMPORTANT guna (8 points) indicating genetic compatibility, health, and progeny. Same Nadi can cause serious health and conception issues.", "नाडी सबैभन्दा महत्त्वपूर्ण गुण (८ अंक) हो जसले आनुवंशिक अनुकूलता, स्वास्थ्य र सन्तान संकेत गर्दछ। उही नाडीले गम्भीर स्वास्थ्य र गर्भधारण समस्या ल्याउन सक्छ।"),
    NADI_TYPES_EXPLAINED("Adi (Vata): Wind element, nervous system, movement. Madhya (Pitta): Fire element, digestion, metabolism. Antya (Kapha): Water element, structure, lubrication. Same Nadi = physiological incompatibility.", "आदि (वात): वायु तत्व, स्नायु प्रणाली, गति। मध्य (पित्त): अग्नि तत्व, पाचन, चयापचय। अन्त्य (कफ): जल तत्व, संरचना, स्नेहन। उही नाडी = शारीरिक असंगतता।"),
    NADI_ZERO_EXPLANATION("Zero points indicates Same Nadi without cancellation - this is the most serious dosha in matchmaking. Immediate astrological consultation and extensive remedies are mandatory.", "शून्य अंकले रद्द बिना उही नाडी संकेत गर्दछ - यो मिलानमा सबैभन्दा गम्भीर दोष हो। तत्काल ज्योतिषीय परामर्श र व्यापक उपायहरू अनिवार्य छन्।"),

    // ============================================
    // MATCHMAKING - SCORE INTERPRETATIONS & RELATIONSHIP GUIDANCE
    // ============================================
    SCORE_EXCELLENT("Excellent match! Score above 28 indicates highly favorable compatibility across all dimensions.", "उत्कृष्ट मिलान! २८ माथिको अंकले सबै आयामहरूमा अत्यन्त अनुकूल अनुकूलता संकेत गर्दछ।"),
    SCORE_EXCELLENT_GUIDANCE("Your compatibility across spiritual, mental, physical, and temperamental dimensions is outstanding. You share compatible values, communication styles, and life goals. This union has the blessings of celestial alignments. Focus on maintaining open communication and mutual respect to nurture this harmonious connection throughout your married life.", "तपाईंको आध्यात्मिक, मानसिक, शारीरिक र स्वभावगत आयामहरूमा अनुकूलता उत्कृष्ट छ। तपाईं अनुकूल मूल्यहरू, सञ्चार शैली र जीवन लक्ष्यहरू साझा गर्नुहुन्छ। यस संघमा आकाशीय संरेखणको आशीर्वाद छ। आफ्नो वैवाहिक जीवनभर यो सामञ्जस्यपूर्ण सम्बन्धलाई पोषण गर्न खुला सञ्चार र पारस्परिक सम्मान कायम राख्नमा ध्यान दिनुहोस्।"),

    SCORE_GOOD("Good match. Score of 21-27 suggests strong compatibility with minor areas to work on.", "राम्रो मिलान। २१-२७ को अंकले काम गर्नुपर्ने सानातिना क्षेत्रहरूसहित बलियो अनुकूलता सुझाव दिन्छ।"),
    SCORE_GOOD_GUIDANCE("You have strong overall compatibility with good harmony in most areas. While there are some minor differences, these can be easily managed through mutual understanding and effort. The relationship has solid foundations for a successful marriage. Focus on conscious communication, especially in areas where gunas scored lower. Regular discussions about expectations and compromise will strengthen your bond.", "तपाईं धेरै क्षेत्रहरूमा राम्रो सामञ्जस्यसहित बलियो समग्र अनुकूलता राख्नुहुन्छ। यद्यपि केही सानातिना भिन्नताहरू छन्, यी पारस्परिक बुझाइ र प्रयासद्वारा सजिलैसँग व्यवस्थापन गर्न सकिन्छ। सम्बन्धमा सफल विवाहको लागि ठोस आधारहरू छन्। सचेत सञ्चारमा ध्यान दिनुहोस्, विशेष गरी ती क्षेत्रहरूमा जहाँ गुणहरूले कम अंक पाएको छ। अपेक्षाहरू र सम्झौताको बारेमा नियमित छलफलले तपाईंको बन्धनलाई बलियो बनाउनेछ।"),

    SCORE_AVERAGE("Average compatibility. Score of 18-20 requires attention to problem areas and remedies.", "औसत अनुकूलता। १८-२० को अंकले समस्या क्षेत्रहरू र उपायहरूमा ध्यान आवश्यक पर्छ।"),
    SCORE_AVERAGE_GUIDANCE("Your compatibility is moderate with several areas requiring conscious effort and adjustment. While the match is acceptable, success depends on both partners' willingness to work through differences. Identify specific problem areas (especially low-scoring gunas) and actively address them. Consider performing recommended Vedic remedies to strengthen weak areas. Pre-marital counseling is strongly advised to establish communication patterns and conflict resolution strategies. With dedication and mutual respect, this relationship can thrive.", "तपाईंको अनुकूलता सचेत प्रयास र समायोजन आवश्यक पर्ने धेरै क्षेत्रहरूसहित मध्यम छ। मिलान स्वीकार्य भए पनि, सफलता दुवै साझेदारहरूको भिन्नताहरू समाधान गर्ने इच्छामा निर्भर गर्दछ। विशेष समस्या क्षेत्रहरू (विशेष गरी कम अंक पाएका गुणहरू) पहिचान गर्नुहोस् र सक्रिय रूपमा सम्बोधन गर्नुहोस्। कमजोर क्षेत्रहरूलाई बलियो बनाउन सिफारिस गरिएका वैदिक उपायहरू गर्ने विचार गर्नुहोस्। सञ्चार ढाँचा र द्वन्द्व समाधान रणनीतिहरू स्थापना गर्न विवाह-पूर्व परामर्श दृढतापूर्वक सल्लाह दिइन्छ। समर्पण र पारस्परिक सम्मानका साथ, यो सम्बन्ध फस्टाउन सक्छ।"),

    SCORE_BELOW_AVERAGE("Below average compatibility. Score of 14-17 indicates significant challenges requiring serious consideration.", "औसतमुनि अनुकूलता। १४-१७ को अंकले गम्भीर विचार आवश्यक पर्ने महत्त्वपूर्ण चुनौतीहरू संकेत गर्दछ।"),
    SCORE_BELOW_AVERAGE_GUIDANCE("Your compatibility score indicates notable challenges across multiple dimensions. This match faces significant obstacles that require serious consideration before proceeding. Multiple gunas showing low scores suggest fundamental differences in temperament, values, or life approaches. Extensive Vedic remedies are essential - consult a qualified Vedic astrologer for a comprehensive remedy plan. Professional couples counseling is strongly recommended. Both partners must be willing to make substantial adjustments and compromises. Carefully evaluate whether both are prepared for the level of conscious effort required to build a harmonious relationship.", "तपाईंको अनुकूलता अंकले विभिन्न आयामहरूमा उल्लेखनीय चुनौतीहरू संकेत गर्दछ। यो मिलानले अगाडि बढ्नु अघि गम्भीर विचार आवश्यक पर्ने महत्त्वपूर्ण बाधाहरूको सामना गर्दछ। धेरै गुणहरूले कम अंक देखाउनुले स्वभाव, मूल्यहरू वा जीवन दृष्टिकोणमा आधारभूत भिन्नताहरू सुझाव दिन्छ। व्यापक वैदिक उपायहरू आवश्यक छन् - व्यापक उपाय योजनाको लागि योग्य वैदिक ज्योतिषीसँग परामर्श गर्नुहोस्। पेशेवर जोडी परामर्श दृढतापूर्वक सिफारिस गरिएको छ। दुवै साझेदारहरू पर्याप्त समायोजन र सम्झौता गर्न इच्छुक हुनुपर्छ। सामञ्जस्यपूर्ण सम्बन्ध निर्माण गर्न आवश्यक सचेत प्रयासको स्तरको लागि दुवै तयार छन् कि छैनन् भनेर सावधानीपूर्वक मूल्याङ्कन गर्नुहोस्।"),

    SCORE_POOR("Poor compatibility. Score below 14 suggests major challenges. Marriage not recommended without extensive remedies.", "कमजोर अनुकूलता। १४ मुनिको अंकले प्रमुख चुनौतीहरू सुझाव दिन्छ। व्यापक उपायहरू बिना विवाह सिफारिस गरिएको छैन।"),
    SCORE_POOR_GUIDANCE("This match shows poor compatibility with fundamental incompatibilities across critical areas. Traditional Vedic astrology does not recommend proceeding with this marriage without addressing severe issues. The low score suggests conflicts in temperament, values, physical harmony, and mental compatibility. If you choose to proceed despite this assessment, the following are absolutely essential: 1) Consult multiple qualified Vedic astrologers for comprehensive remedy plans 2) Perform all recommended pujas, homas, and remedial measures diligently 3) Undergo extensive pre-marital and ongoing couples counseling 4) Both partners must have extraordinary commitment to conscious relationship work 5) Prepare for ongoing challenges requiring patience, tolerance, and spiritual growth. Consider whether alternative matches might offer better foundations for marital happiness.", "यो मिलानले महत्त्वपूर्ण क्षेत्रहरूमा आधारभूत असंगतताहरूसहित कमजोर अनुकूलता देखाउँछ। परम्परागत वैदिक ज्योतिष गम्भीर समस्याहरू सम्बोधन नगरी यस विवाहसँग अगाडि बढ्न सिफारिस गर्दैन। कम अंकले स्वभाव, मूल्यहरू, शारीरिक सामञ्जस्य र मानसिक अनुकूलतामा द्वन्द्व सुझाव दिन्छ। यदि तपाईं यस मूल्याङ्कनको बावजुद अगाडि बढ्न रोज्नुहुन्छ भने, निम्न कुराहरू एकदमै आवश्यक छन्: १) व्यापक उपाय योजनाको लागि धेरै योग्य वैदिक ज्योतिषीहरूसँग परामर्श गर्नुहोस् २) सबै सिफारिस गरिएका पूजा, होम र उपचारात्मक उपायहरू लगनशीलताका साथ गर्नुहोस् ३) व्यापक विवाह-पूर्व र निरन्तर जोडी परामर्श लिनुहोस् ४) दुवै साझेदारहरूसँग सचेत सम्बन्ध कार्यको लागि असाधारण प्रतिबद्धता हुनुपर्छ ५) धैर्य, सहनशीलता र आध्यात्मिक विकास आवश्यक पर्ने निरन्तर चुनौतीहरूको लागि तयार रहनुहोस्। वैकल्पिक मिलानहरूले वैवाहिक खुशीको लागि राम्रो आधारहरू प्रदान गर्न सक्छन् कि भनेर विचार गर्नुहोस्।"),

    // ============================================
    // MATCHMAKING - DOSHA CANCELLATION EXPLANATIONS
    // ============================================
    DOSHA_CANCEL_EXPLAINED("Dosha Cancellation Factors Explained", "दोष रद्द कारकहरू व्याख्या"),
    NADI_CANCEL_EXPLANATION("Nadi Dosha can be cancelled in several specific scenarios according to classical Vedic texts. These cancellations are based on deeper astrological principles that override the superficial Nadi match.", "नाडी दोष शास्त्रीय वैदिक ग्रन्थहरू अनुसार धेरै विशिष्ट परिस्थितिहरूमा रद्द गर्न सकिन्छ। यी रद्दहरू गहन ज्योतिषीय सिद्धान्तहरूमा आधारित छन् जसले सतही नाडी मिलानलाई ओभरराइड गर्दछ।"),
    BHAKOOT_CANCEL_EXPLANATION("Bhakoot Dosha cancellation occurs when deeper planetary relationships indicate harmony. Even though Moon signs are in challenging positions, the underlying planetary friendships or same lords can neutralize the dosha effects.", "भकुट दोष रद्द गहन ग्रह सम्बन्धहरूले सामञ्जस्य संकेत गर्दा हुन्छ। यद्यपि चन्द्र राशिहरू चुनौतीपूर्ण स्थानमा छन्, अन्तर्निहित ग्रह मित्रता वा उही स्वामीहरूले दोष प्रभावहरू निष्क्रिय पार्न सक्छन्।"),
    MANGLIK_CANCEL_EXPLANATION("Manglik Dosha can be cancelled through various factors: both partners being Manglik (mutual cancellation), Mars in certain houses (1st, 4th, 7th, 8th, 12th from specific references), Mars being in own sign or exalted, age over 28 years (Mars matures), or presence of benefic aspects. These factors indicate that Mars energy is channeled constructively.", "मांगलिक दोष विभिन्न कारकहरूद्वारा रद्द गर्न सकिन्छ: दुवै साझेदार मांगलिक हुनु (पारस्परिक रद्द), मंगल निश्चित भावहरूमा (विशेष सन्दर्भबाट १, ४, ७, ८, १२), मंगल आफ्नै राशि वा उच्चमा हुनु, २८ वर्ष भन्दा माथिको उमेर (मंगल परिपक्व हुन्छ), वा शुभ दृष्टिहरूको उपस्थिति। यी कारकहरूले संकेत गर्दछन् कि मंगल ऊर्जा रचनात्मक रूपमा च्यानल गरिएको छ।"),

    // ============================================
    // MATCHMAKING - COMPATIBILITY RECOMMENDATIONS
    // ============================================
    COMPAT_RECOMMEND_MARRIAGE_TIMING("Recommended Marriage Timing", "सिफारिस गरिएको विवाह समय"),
    COMPAT_TIMING_IMMEDIATE("Marriage can proceed without delay. Choose an auspicious muhurta during favorable planetary transits for both charts.", "विवाह ढिलाइ बिना अगाडि बढ्न सक्छ। दुवै कुण्डलीको लागि अनुकूल ग्रह गोचरको समयमा शुभ मुहूर्त छान्नुहोस्।"),
    COMPAT_TIMING_AFTER_REMEDIES("Proceed after completing essential remedies (minimum 40-90 days). This allows remedial measures to strengthen weak areas before marriage.", "आवश्यक उपायहरू पूरा गरेपछि अगाडि बढ्नुहोस् (न्यूनतम ४०-९० दिन)। यसले विवाह अघि कमजोर क्षेत्रहरूलाई बलियो बनाउन उपचारात्मक उपायहरूलाई अनुमति दिन्छ।"),
    COMPAT_TIMING_EXTENDED_REMEDIES("Delay marriage for 6-12 months while performing comprehensive remedies. Use this time for counseling and conscious preparation.", "व्यापक उपायहरू गर्दै ६-१२ महिना विवाहमा ढिलाइ गर्नुहोस्। परामर्श र सचेत तयारीको लागि यो समय प्रयोग गर्नुहोस्।"),
    COMPAT_TIMING_RECONSIDER("Seriously reconsider this match. If proceeding, delay minimum 1 year with extensive remedies, counseling, and spiritual preparation.", "यस मिलानलाई गम्भीरतापूर्वक पुनर्विचार गर्नुहोस्। अगाडि बढ्दै हुनुहुन्छ भने, व्यापक उपाय, परामर्श र आध्यात्मिक तयारीसहित न्यूनतम १ वर्ष ढिलाइ गर्नुहोस्।"),

    COMPAT_RECOMMEND_RELATIONSHIP_FOCUS("Relationship Focus Areas", "सम्बन्ध फोकस क्षेत्रहरू"),
    COMPAT_FOCUS_SPIRITUAL("Spiritual Growth Together: Practice joint meditation, visit temples together, study spiritual texts, maintain shared spiritual practices.", "सँगै आध्यात्मिक विकास: संयुक्त ध्यान अभ्यास गर्नुहोस्, सँगै मन्दिरहरू भ्रमण गर्नुहोस्, आध्यात्मिक ग्रन्थहरू अध्ययन गर्नुहोस्, साझा आध्यात्मिक अभ्यासहरू कायम राख्नुहोस्।"),
    COMPAT_FOCUS_COMMUNICATION("Mental & Communication: Schedule regular check-ins, practice active listening, express appreciation daily, address conflicts calmly.", "मानसिक र सञ्चार: नियमित जाँच-पडताल तालिका बनाउनुहोस्, सक्रिय सुनाइ अभ्यास गर्नुहोस्, दैनिक प्रशंसा व्यक्त गर्नुहोस्, द्वन्द्वहरूलाई शान्तपूर्वक सम्बोधन गर्नुहोस्।"),
    COMPAT_FOCUS_PHYSICAL("Physical Harmony: Prioritize physical affection, maintain health together, respect boundaries, create intimate connection rituals.", "शारीरिक सामञ्जस्य: शारीरिक स्नेहलाई प्राथमिकता दिनुहोस्, सँगै स्वास्थ्य कायम राख्नुहोस्, सीमाहरूको सम्मान गर्नुहोस्, अन्तरंग सम्बन्ध अनुष्ठानहरू सिर्जना गर्नुहोस्।"),
    COMPAT_FOCUS_TEMPERAMENT("Temperamental Balance: Understand personality differences, practice patience, compromise consciously, celebrate individual strengths.", "स्वभावगत सन्तुलन: व्यक्तित्व भिन्नताहरू बुझ्नुहोस्, धैर्य अभ्यास गर्नुहोस्, सचेत रूपमा सम्झौता गर्नुहोस्, व्यक्तिगत शक्तिहरू मनाउनुहोस्।"),
    COMPAT_FOCUS_FINANCIAL("Financial Harmony: Create joint financial plans, discuss money values openly, set shared goals, respect spending differences.", "आर्थिक सामञ्जस्य: संयुक्त वित्तीय योजनाहरू बनाउनुहोस्, पैसा मूल्यहरू खुलेर छलफल गर्नुहोस्, साझा लक्ष्यहरू सेट गर्नुहोस्, खर्च भिन्नताहरूलाई सम्मान गर्नुहोस्।"),
    COMPAT_FOCUS_HEALTH("Health & Progeny: Maintain healthy lifestyles, support each other's wellness, prepare mindfully for parenthood.", "स्वास्थ्य र सन्तान: स्वस्थ जीवनशैली कायम राख्नुहोस्, एकअर्काको कल्याणलाई समर्थन गर्नुहोस्, मातृत्व/पितृत्वको लागि सावधानीपूर्वक तयारी गर्नुहोस्।"),

    // Manglik Quick Status
    MANGLIK_QUICK_NONE("No Dosha", "दोष छैन"),
    MANGLIK_QUICK_BOTH("Both Manglik", "दुवै मांगलिक"),
    MANGLIK_QUICK_BRIDE("Bride Only", "दुलही मात्र"),
    MANGLIK_QUICK_GROOM("Groom Only", "दुलाहा मात्र"),

    // ============================================
    // YOGA CALCULATOR - CATEGORIES & STRENGTH
    // ============================================
    YOGA_CAT_RAJA("Raja Yoga", "राज योग"),
    YOGA_CAT_RAJA_DESC("Power, authority, and leadership combinations", "शक्ति, अधिकार र नेतृत्व संयोजनहरू"),
    YOGA_CAT_DHANA("Dhana Yoga", "धन योग"),
    YOGA_CAT_DHANA_DESC("Wealth and prosperity combinations", "धन र समृद्धि संयोजनहरू"),
    YOGA_CAT_PANCHA_MAHAPURUSHA("Pancha Mahapurusha Yoga", "पञ्च महापुरुष योग"),
    YOGA_CAT_PANCHA_MAHAPURUSHA_DESC("Five great person combinations", "पाँच महान व्यक्ति संयोजनहरू"),
    YOGA_CAT_NABHASA("Nabhasa Yoga", "नाभस योग"),
    YOGA_CAT_NABHASA_DESC("Pattern-based planetary combinations", "ढाँचामा आधारित ग्रह संयोजनहरू"),
    YOGA_CAT_CHANDRA("Chandra Yoga", "चन्द्र योग"),
    YOGA_CAT_CHANDRA_DESC("Moon-based combinations", "चन्द्रमामा आधारित संयोजनहरू"),
    YOGA_CAT_SOLAR("Solar Yoga", "सूर्य योग"),
    YOGA_CAT_SOLAR_DESC("Sun-based combinations", "सूर्यमा आधारित संयोजनहरू"),
    YOGA_CAT_NEGATIVE("Negative Yoga", "नकारात्मक योग"),
    YOGA_CAT_NEGATIVE_DESC("Challenging combinations", "चुनौतीपूर्ण संयोजनहरू"),
    YOGA_CAT_SPECIAL("Special Yoga", "विशेष योग"),
    YOGA_CAT_SPECIAL_DESC("Other significant combinations", "अन्य महत्त्वपूर्ण संयोजनहरू"),

    YOGA_STRENGTH_EXTREMELY_STRONG("Extremely Strong", "अत्यन्त बलियो"),
    YOGA_STRENGTH_STRONG("Strong", "बलियो"),
    YOGA_STRENGTH_MODERATE("Moderate", "मध्यम"),
    YOGA_STRENGTH_WEAK("Weak", "कमजोर"),
    YOGA_STRENGTH_VERY_WEAK("Very Weak", "धेरै कमजोर"),

    // ============================================
    // REMEDIES CALCULATOR - CATEGORIES & STRENGTH
    // ============================================
    REMEDY_CAT_GEMSTONE("Gemstone", "रत्न"),
    REMEDY_CAT_MANTRA("Mantra", "मन्त्र"),
    REMEDY_CAT_YANTRA("Yantra", "यन्त्र"),
    REMEDY_CAT_CHARITY("Charity", "दान"),
    REMEDY_CAT_FASTING("Fasting", "उपवास"),
    REMEDY_CAT_COLOR("Color Therapy", "रंग चिकित्सा"),
    REMEDY_CAT_METAL("Metal", "धातु"),
    REMEDY_CAT_RUDRAKSHA("Rudraksha", "रुद्राक्ष"),
    REMEDY_CAT_DEITY("Deity Worship", "देवता पूजा"),
    REMEDY_CAT_LIFESTYLE("Lifestyle", "जीवनशैली"),

    REMEDY_PRIORITY_ESSENTIAL("Essential", "आवश्यक"),
    REMEDY_PRIORITY_HIGHLY_RECOMMENDED("Highly Recommended", "अत्यधिक सिफारिस"),
    REMEDY_PRIORITY_RECOMMENDED("Recommended", "सिफारिस गरिएको"),
    REMEDY_PRIORITY_OPTIONAL("Optional", "वैकल्पिक"),

    PLANETARY_STRENGTH_VERY_STRONG("Very Strong", "धेरै बलियो"),
    PLANETARY_STRENGTH_STRONG("Strong", "बलियो"),
    PLANETARY_STRENGTH_MODERATE("Moderate", "मध्यम"),
    PLANETARY_STRENGTH_WEAK("Weak", "कमजोर"),
    PLANETARY_STRENGTH_VERY_WEAK("Very Weak", "धेरै कमजोर"),
    PLANETARY_STRENGTH_AFFLICTED("Afflicted", "पीडित"),

    // Shadbala StrengthRating
    SHADBALA_EXTREMELY_WEAK("Extremely Weak", "अत्यन्त कमजोर"),
    SHADBALA_WEAK("Weak", "कमजोर"),
    SHADBALA_BELOW_AVERAGE("Below Average", "औसतभन्दा तल"),
    SHADBALA_AVERAGE("Average", "औसत"),
    SHADBALA_ABOVE_AVERAGE("Above Average", "औसतभन्दा माथि"),
    SHADBALA_STRONG("Strong", "बलियो"),
    SHADBALA_VERY_STRONG("Very Strong", "धेरै बलियो"),
    SHADBALA_EXTREMELY_STRONG("Extremely Strong", "अत्यन्त बलियो"),

    // ============================================
    // HOUSE SIGNIFICATIONS (Localized)
    // ============================================
    HOUSE_1_SIGNIFICATION("self-effort and personality", "आत्म-प्रयास र व्यक्तित्व"),
    HOUSE_2_SIGNIFICATION("family wealth and speech", "पारिवारिक धन र वाणी"),
    HOUSE_3_SIGNIFICATION("courage and communication", "साहस र सञ्चार"),
    HOUSE_4_SIGNIFICATION("property and domestic comfort", "सम्पत्ति र घरेलु सुविधा"),
    HOUSE_5_SIGNIFICATION("speculation and creative ventures", "अनुमान र सिर्जनात्मक उद्यमहरू"),
    HOUSE_6_SIGNIFICATION("service and defeating competition", "सेवा र प्रतिस्पर्धा जित्नु"),
    HOUSE_7_SIGNIFICATION("partnership and business", "साझेदारी र व्यापार"),
    HOUSE_8_SIGNIFICATION("inheritance and unexpected gains", "विरासत र अप्रत्याशित लाभ"),
    HOUSE_9_SIGNIFICATION("fortune and higher pursuits", "भाग्य र उच्च खोजीहरू"),
    HOUSE_10_SIGNIFICATION("career and public recognition", "क्यारियर र सार्वजनिक मान्यता"),
    HOUSE_11_SIGNIFICATION("gains and social networks", "लाभ र सामाजिक सञ्जालहरू"),
    HOUSE_12_SIGNIFICATION("foreign connections and spiritual pursuits", "विदेशी सम्बन्ध र आध्यात्मिक खोजीहरू"),
    VARIOUS_ACTIVITIES("various activities", "विभिन्न गतिविधिहरू"),

    // ============================================
    // REPORT HEADERS & SECTIONS
    // ============================================
    REPORT_YOGA_ANALYSIS("YOGA ANALYSIS REPORT", "योग विश्लेषण रिपोर्ट"),
    REPORT_TOTAL_YOGAS("Total Yogas Found", "कुल योगहरू फेला परेको"),
    REPORT_OVERALL_STRENGTH("Overall Yoga Strength", "समग्र योग बल"),
    REPORT_DOMINANT_CATEGORY("Dominant Category", "प्रमुख वर्ग"),
    REPORT_PLANETS("Planets", "ग्रहहरू"),
    REPORT_HOUSES("Houses", "भावहरू"),
    REPORT_EFFECTS("Effects", "प्रभावहरू"),
    REPORT_ACTIVATION("Activation", "सक्रियता"),
    REPORT_PATTERN("Pattern", "ढाँचा"),
    REPORT_CANCELLATION_FACTORS("Cancellation Factors", "रद्द गर्ने कारकहरू"),
    REPORT_AUSPICIOUS("Auspicious", "शुभ"),
    REPORT_INAUSPICIOUS("Inauspicious", "अशुभ"),

    REPORT_REMEDIES("VEDIC ASTROLOGY REMEDIES REPORT", "वैदिक ज्योतिष उपाय रिपोर्ट"),
    REPORT_PLANETARY_STRENGTH_ANALYSIS("PLANETARY STRENGTH ANALYSIS", "ग्रह बल विश्लेषण"),
    REPORT_PLANETS_REQUIRING_ATTENTION("PLANETS REQUIRING ATTENTION", "ध्यान आवश्यक ग्रहहरू"),
    REPORT_RECOMMENDED_REMEDIES("RECOMMENDED REMEDIES", "सिफारिस गरिएका उपायहरू"),
    REPORT_GENERAL_RECOMMENDATIONS("GENERAL RECOMMENDATIONS", "सामान्य सिफारिसहरू"),
    REPORT_SUMMARY("SUMMARY", "सारांश"),
    REPORT_GENERATED_BY("Generated by AstroStorm - Ultra-Precision Vedic Astrology", "AstroStorm द्वारा उत्पन्न - अति-सटीक वैदिक ज्योतिष"),
    REPORT_CATEGORY("Category", "वर्ग"),
    REPORT_PLANET("Planet", "ग्रह"),
    REPORT_METHOD("Method", "विधि"),
    REPORT_TIMING("Timing", "समय"),

    REPORT_MATCHMAKING("KUNDLI MILAN (MATCHMAKING) REPORT", "कुण्डली मिलान रिपोर्ट"),
    REPORT_MATCHMAKING_TITLE("KUNDLI MILAN REPORT", "कुण्डली मिलान रिपोर्ट"),
    REPORT_ASTROSTORM_ANALYSIS("AstroStorm Analysis", "AstroStorm विश्लेषण"),
    REPORT_PROFILES("PROFILES", "प्रोफाइलहरू"),
    REPORT_BRIDE("BRIDE", "वधू"),
    REPORT_BRIDE_LABEL("Bride:", "वधू:"),
    REPORT_GROOM("GROOM", "वर"),
    REPORT_GROOM_LABEL("Groom:", "वर:"),
    REPORT_MOON_SIGN_LABEL("Moon Sign:", "चन्द्र राशि:"),
    REPORT_NAKSHATRA_LABEL("Nakshatra:", "नक्षत्र:"),
    REPORT_COMPATIBILITY_SCORE("COMPATIBILITY SCORE", "अनुकूलता अंक"),
    REPORT_TOTAL_POINTS("Total Points:", "कुल अंक:"),
    REPORT_PERCENTAGE("Percentage:", "प्रतिशत:"),
    REPORT_RATING_LABEL("Rating:", "मूल्याङ्कन:"),
    REPORT_ASHTAKOOTA("ASHTAKOOTA ANALYSIS", "अष्टकूट विश्लेषण"),
    REPORT_ASHTAKOOTA_8_GUNA("ASHTAKOOTA (8 GUNA) ANALYSIS", "अष्टकूट (८ गुण) विश्लेषण"),
    REPORT_SCORE_LABEL("Score:", "अंक:"),
    REPORT_GUNA("GUNA", "गुण"),
    REPORT_MAX("MAX", "अधिकतम"),
    REPORT_OBTAINED("OBTAINED", "प्राप्त"),
    REPORT_STATUS("STATUS", "स्थिति"),
    REPORT_TOTAL("TOTAL", "कुल"),
    REPORT_OVERALL_RATING("OVERALL RATING", "समग्र मूल्याङ्कन"),
    REPORT_ADDITIONAL_FACTORS("ADDITIONAL FACTORS", "थप कारकहरू"),
    REPORT_MANGLIK_ANALYSIS("MANGLIK ANALYSIS", "मांगलिक विश्लेषण"),
    REPORT_MANGLIK_DOSHA_ANALYSIS("MANGLIK DOSHA ANALYSIS", "मांगलिक दोष विश्लेषण"),
    REPORT_MARS_IN_HOUSE("Mars in House %d", "भाव %d मा मंगल"),
    REPORT_CANCELLATION("(Cancellation)", "(रद्द)"),
    REPORT_SPECIAL_CONSIDERATIONS("SPECIAL CONSIDERATIONS", "विशेष विचारहरू"),
    REPORT_SUGGESTED_REMEDIES("SUGGESTED REMEDIES", "सुझाव गरिएका उपायहरू"),
    REPORT_COMPATIBILITY("Compatibility", "अनुकूलता"),
    REPORT_COMPATIBILITY_LABEL("Compatibility:", "अनुकूलता:"),
    REPORT_NOT_PRESENT("Not Present", "उपस्थित छैन"),
    REPORT_COMPATIBLE("Compatible", "अनुकूल"),
    REPORT_SATISFIED("Satisfied", "सन्तुष्ट"),
    REPORT_NOT_SATISFIED("Not satisfied", "सन्तुष्ट छैन"),
    REPORT_NOT_APPLICABLE("Not applicable", "लागू हुँदैन"),
    REPORT_NA("N/A", "उपलब्ध छैन"),
    REPORT_ASTROSTORM_VEDIC("Vedic Astrology • Ultra-Precision", "वैदिक ज्योतिष • अति-सटीक"),
    REPORT_KUNDLI_MILAN_SUMMARY("KUNDLI MILAN SUMMARY", "कुण्डली मिलान सारांश"),
    REPORT_MANGLIK_LABEL("Manglik:", "मांगलिक:"),
    REPORT_ASHTAKOOTA_GUNA_SCORES("ASHTAKOOTA GUNA SCORES", "अष्टकूट गुण अंकहरू"),

    // ============================================
    // SPECIFIC YOGA NAMES (For display)
    // ============================================
    YOGA_KENDRA_TRIKONA("Kendra-Trikona Raja Yoga", "केन्द्र-त्रिकोण राज योग"),
    YOGA_PARIVARTANA("Parivartana Raja Yoga", "परिवर्तन राज योग"),
    YOGA_VIPARITA("Viparita Raja Yoga", "विपरीत राज योग"),
    YOGA_NEECHA_BHANGA("Neecha Bhanga Raja Yoga", "नीच भंग राज योग"),
    YOGA_MAHA_RAJA("Maha Raja Yoga", "महा राज योग"),
    YOGA_LAKSHMI("Lakshmi Yoga", "लक्ष्मी योग"),
    YOGA_KUBERA("Kubera Yoga", "कुबेर योग"),
    YOGA_CHANDRA_MANGALA("Chandra-Mangala Yoga", "चन्द्र-मंगल योग"),
    YOGA_LABHA("Labha Yoga", "लाभ योग"),
    YOGA_RUCHAKA("Ruchaka Yoga", "रुचक योग"),
    YOGA_BHADRA("Bhadra Yoga", "भद्र योग"),
    YOGA_HAMSA("Hamsa Yoga", "हंस योग"),
    YOGA_MALAVYA("Malavya Yoga", "मालव्य योग"),
    YOGA_SASA("Sasa Yoga", "शश योग"),
    YOGA_SUNAFA("Sunafa Yoga", "सुनफा योग"),
    YOGA_ANAFA("Anafa Yoga", "अनफा योग"),
    YOGA_DURUDHARA("Durudhara Yoga", "दुरुधरा योग"),
    YOGA_GAJA_KESARI("Gaja-Kesari Yoga", "गज-केसरी योग"),
    YOGA_ADHI("Adhi Yoga", "अधि योग"),
    YOGA_VESI("Vesi Yoga", "वेशी योग"),
    YOGA_VOSI("Vosi Yoga", "वोशी योग"),
    YOGA_UBHAYACHARI("Ubhayachari Yoga", "उभयचारी योग"),
    YOGA_KEMADRUMA("Kemadruma Yoga", "केमद्रुम योग"),
    YOGA_DARIDRA("Daridra Yoga", "दरिद्र योग"),
    YOGA_SHAKATA("Shakata Yoga", "शकट योग"),
    YOGA_GURU_CHANDAL("Guru-Chandal Yoga", "गुरु-चांडाल योग"),
    YOGA_BUDHA_ADITYA("Budha-Aditya Yoga", "बुध-आदित्य योग"),
    YOGA_AMALA("Amala Yoga", "अमला योग"),
    YOGA_SARASWATI("Saraswati Yoga", "सरस्वती योग"),
    YOGA_PARVATA("Parvata Yoga", "पर्वत योग"),
    YOGA_KAHALA("Kahala Yoga", "कहल योग"),
    YOGA_YAVA("Yava Yoga", "यव योग"),
    YOGA_SHRINGATAKA("Shringataka Yoga", "शृंगाटक योग"),
    YOGA_GADA("Gada Yoga", "गदा योग"),
    YOGA_RAJJU("Rajju Yoga", "रज्जु योग"),
    YOGA_MUSALA("Musala Yoga", "मुसल योग"),
    YOGA_NALA("Nala Yoga", "नल योग"),
    YOGA_KEDARA("Kedara Yoga", "केदार योग"),
    YOGA_SHOOLA("Shoola Yoga", "शूल योग"),
    YOGA_YUGA("Yuga Yoga", "युग योग"),
    YOGA_GOLA("Gola Yoga", "गोल योग"),
    YOGA_VEENA("Veena Yoga", "वीणा योग"),
    YOGA_DASA_MULA("Dasa-Mula Yoga", "दश-मूल योग"),
    YOGA_VARGOTTAMA_STRENGTH("Vargottama Strength", "वर्गोत्तम बल"),

    // New Yogas - Grahan and Nodal Combinations
    YOGA_SURYA_GRAHAN("Surya Grahan Yoga", "सूर्य ग्रहण योग"),
    YOGA_SURYA_KETU_GRAHAN("Surya-Ketu Grahan Yoga", "सूर्य-केतु ग्रहण योग"),
    YOGA_CHANDRA_GRAHAN("Chandra Grahan Yoga", "चन्द्र ग्रहण योग"),
    YOGA_CHANDRA_KETU("Chandra-Ketu Yoga", "चन्द्र-केतु योग"),
    YOGA_ANGARAK("Angarak Yoga", "अङ्गारक योग"),
    YOGA_SHRAPIT("Shrapit Yoga", "शापित योग"),
    YOGA_KALA_SARPA("Kala Sarpa Yoga", "कालसर्प योग"),
    YOGA_PAPAKARTARI("Papakartari Yoga", "पापकर्तरी योग"),
    YOGA_SHUBHAKARTARI("Shubhakartari Yoga", "शुभकर्तरी योग"),
    YOGA_SANYASA("Sanyasa Yoga", "सन्यास योग"),
    YOGA_CHAMARA("Chamara Yoga", "चामर योग"),
    YOGA_DHARMA_KARMADHIPATI("Dharma-Karmadhipati Yoga", "धर्म-कर्माधिपति योग"),

    // New Yoga Effects
    YOGA_EFFECT_SURYA_GRAHAN("Father-related troubles, ego issues, government problems, health issues with head/eyes", "पिता सम्बन्धी समस्याहरू, अहंकार समस्याहरू, सरकारी समस्याहरू, टाउको/आँखामा स्वास्थ्य समस्याहरू"),
    YOGA_EFFECT_SURYA_KETU_GRAHAN("Spiritual detachment, low self-esteem, father troubles, past-life karmic issues", "आध्यात्मिक विरक्ति, कम आत्मसम्मान, पिता समस्याहरू, पूर्वजन्म कर्म समस्याहरू"),
    YOGA_EFFECT_CHANDRA_GRAHAN("Mental restlessness, mother troubles, emotional instability, obsessive tendencies", "मानसिक अशान्ति, आमा समस्याहरू, भावनात्मक अस्थिरता, जुनूनी प्रवृत्तिहरू"),
    YOGA_EFFECT_CHANDRA_KETU("Detachment from emotions, past-life memories, psychic sensitivity, mother karma", "भावनाहरूबाट विरक्ति, पूर्वजन्म स्मृतिहरू, मनोवैज्ञानिक संवेदनशीलता, आमा कर्म"),
    YOGA_EFFECT_ANGARAK("Accidents, surgery, aggression, sibling troubles, litigation, sudden events", "दुर्घटनाहरू, शल्यक्रिया, आक्रामकता, भाइबहिनी समस्याहरू, मुद्दा, अचानक घटनाहरू"),
    YOGA_EFFECT_SHRAPIT("Past-life karma manifesting as chronic obstacles, delays, fear, ancestral issues", "पूर्वजन्म कर्म दीर्घकालीन बाधाहरू, ढिलाइ, डर, पुर्खौली समस्याहरूको रूपमा प्रकट"),
    YOGA_EFFECT_KALA_SARPA("Karmic life patterns, sudden ups and downs, spiritual transformation potential", "कर्मजन्य जीवन ढाँचाहरू, अचानक उतार-चढाव, आध्यात्मिक रूपान्तरण सम्भावना"),
    YOGA_EFFECT_PAPAKARTARI("Obstacles in self-expression, health challenges, restricted opportunities", "आत्म-अभिव्यक्तिमा बाधाहरू, स्वास्थ्य चुनौतीहरू, सीमित अवसरहरू"),
    YOGA_EFFECT_SHUBHAKARTARI("Protected life, good health, success in endeavors, helpful people around", "सुरक्षित जीवन, राम्रो स्वास्थ्य, प्रयासहरूमा सफलता, वरपर मद्दतगर्ने मानिसहरू"),
    YOGA_EFFECT_SANYASA("Renunciation tendencies, spiritual inclinations, detachment from worldly matters", "त्याग प्रवृत्तिहरू, आध्यात्मिक झुकाव, सांसारिक कुराहरूबाट विरक्ति"),
    YOGA_EFFECT_CHAMARA("Royal honors, fame, eloquence, learned, respected by rulers", "राजकीय सम्मान, प्रसिद्धि, वाक्पटुता, पढेलेखेको, शासकहरूद्वारा सम्मानित"),
    YOGA_EFFECT_DHARMA_KARMADHIPATI("Highly successful career, fortune through profession, fame, authority positions", "अत्यन्त सफल करियर, पेशाबाट भाग्य, प्रसिद्धि, अधिकार पदहरू"),

    // Yoga Effects Translations
    YOGA_EFFECT_RUCHAKA("Commander, army chief, valorous, muscular body, red complexion, successful in conflicts, skilled in warfare, leader of thieves or soldiers, wealth through martial arts or defense", "सेनापति, सेना प्रमुख, वीर, बलियो शरीर, रातो रंग, द्वन्द्वमा सफल, युद्ध कलामा दक्ष, चोर वा सैनिकहरूको नेता, युद्ध कला वा रक्षाबाट धन"),
    YOGA_EFFECT_BHADRA("Intelligent, eloquent speaker, skilled in arts and sciences, long-lived, wealthy through intellect, respected in assemblies, lion-like face, broad chest", "बुद्धिमान, वाक्पटु वक्ता, कला र विज्ञानमा दक्ष, दीर्घायु, बुद्धिबाट धनी, सभामा सम्मानित, सिंह जस्तो मुख, फराकिलो छाती"),
    YOGA_EFFECT_HAMSA("Righteous king, fair complexion, elevated nose, beautiful face, devoted to gods and brahmins, fond of water sports, walks like a swan, respected by rulers, spiritual inclinations", "धार्मिक राजा, गोरो रंग, उठेको नाक, सुन्दर मुख, देवता र ब्राह्मणहरूप्रति भक्त, जल क्रीडाको शौकीन, हंस जस्तो हिँड्ने, शासकहरूद्वारा सम्मानित, आध्यात्मिक झुकाव"),
    YOGA_EFFECT_MALAVYA("Wealthy, enjoys all comforts, beautiful spouse, strong limbs, attractive face, blessed with vehicles and servants, learned in scriptures, lives up to 77 years", "धनी, सबै सुविधा भोग्ने, सुन्दर पति/पत्नी, बलियो अंगहरू, आकर्षक मुख, वाहन र सेवकहरूले आशीर्वादित, शास्त्रमा पढेको, ७७ वर्षसम्म बाँच्ने"),
    YOGA_EFFECT_SASA("Head of village/town/city, wicked disposition but good servants, intriguing nature, knows others' weaknesses, commands over masses, wealth through iron or labor", "गाउँ/शहर/नगरको प्रमुख, दुष्ट स्वभाव तर राम्रा सेवकहरू, षड्यन्त्रकारी स्वभाव, अरूको कमजोरी जान्ने, जनतामाथि आदेश, फलाम वा श्रमबाट धन"),
    YOGA_EFFECT_GAJA_KESARI("Destroyer of enemies like lion, eloquent speaker, virtuous, long-lived, famous", "सिंह जस्तो शत्रु विनाशक, वाक्पटु वक्ता, सद्गुणी, दीर्घायु, प्रसिद्ध"),
    YOGA_EFFECT_SUNAFA("Self-made wealth, intelligent, good status, praised by kings", "स्व-निर्मित धन, बुद्धिमान, राम्रो स्थिति, राजाहरूद्वारा प्रशंसित"),
    YOGA_EFFECT_ANAFA("Good reputation, health, happiness, self-respect", "राम्रो प्रतिष्ठा, स्वास्थ्य, खुशी, आत्म-सम्मान"),
    YOGA_EFFECT_DURUDHARA("Highly fortunate, wealthy, vehicles, servants, charitable, enjoys life", "अत्यधिक भाग्यशाली, धनी, वाहनहरू, सेवकहरू, दानशील, जीवनको आनन्द लिने"),
    YOGA_EFFECT_ADHI("Commander, minister, or king; polite, trustworthy, healthy, wealthy, defeats enemies", "सेनापति, मन्त्री, वा राजा; विनम्र, विश्वसनीय, स्वस्थ, धनी, शत्रुहरूलाई हराउने"),
    YOGA_EFFECT_BUDHA_ADITYA("Intelligence, skilled in many arts, famous, sweet speech, scholarly", "बुद्धि, धेरै कलाहरूमा दक्ष, प्रसिद्ध, मीठो बोली, विद्वान"),
    YOGA_EFFECT_SARASWATI("Highly learned, poet, prose writer, famous speaker, skilled in all arts", "अत्यधिक पढेलेखेको, कवि, गद्य लेखक, प्रसिद्ध वक्ता, सबै कलाहरूमा दक्ष"),
    YOGA_EFFECT_PARVATA("King or minister, famous, generous, wealthy, charitable, mountain-like stability", "राजा वा मन्त्री, प्रसिद्ध, उदार, धनी, दानशील, पहाड जस्तो स्थिरता"),
    YOGA_EFFECT_LAKSHMI("Blessed by Goddess Lakshmi, abundant wealth, luxury, beauty, artistic success", "देवी लक्ष्मीको आशीर्वाद, प्रचुर धन, विलासिता, सौन्दर्य, कलात्मक सफलता"),
    YOGA_EFFECT_MAHA_RAJA("Exceptional fortune, royal status, widespread fame, great wealth and power", "असाधारण भाग्य, राजकीय स्थिति, व्यापक प्रसिद्धि, ठूलो धन र शक्ति"),
    YOGA_EFFECT_KENDRA_TRIKONA("Rise to power and authority, leadership position, recognition from government", "शक्ति र अधिकारमा उदय, नेतृत्व पद, सरकारबाट मान्यता"),
    YOGA_EFFECT_PARIVARTANA("Strong Raja Yoga through mutual exchange, stable rise to power, lasting authority", "आपसी आदानप्रदानबाट बलियो राज योग, शक्तिमा स्थिर उदय, दिगो अधिकार"),
    YOGA_EFFECT_VIPARITA("Rise through fall of enemies, sudden fortune from unexpected sources, gains through others' losses", "शत्रुहरूको पतनबाट उदय, अप्रत्याशित स्रोतहरूबाट अचानक भाग्य, अरूको हानिबाट लाभ"),
    YOGA_EFFECT_NEECHA_BHANGA("Rise from humble beginnings, success after initial struggles, respected leader", "साधारण सुरुवातबाट उदय, प्रारम्भिक संघर्षपछि सफलता, सम्मानित नेता"),
    YOGA_EFFECT_KEMADRUMA("Poverty, suffering, struggles, lack of support, lonely, menial work", "गरिबी, दुख, संघर्ष, समर्थनको अभाव, एक्लो, तल्लो काम"),
    YOGA_EFFECT_KEMADRUMA_CANCELLED("Kemadruma effects significantly reduced due to cancellation factors", "रद्द कारकहरूको कारण केमद्रुम प्रभाव उल्लेखनीय रूपमा कम भयो"),
    YOGA_EFFECT_DARIDRA("Obstacles to gains, financial struggles, unfulfilled desires", "लाभमा बाधाहरू, आर्थिक संघर्ष, अपूर्ण इच्छाहरू"),
    YOGA_EFFECT_SHAKATA("Fluctuating fortune, periods of poverty alternating with wealth", "उतार-चढाव भाग्य, गरिबीको अवधि धनसँग पालैपालो"),
    YOGA_EFFECT_GURU_CHANDAL("Unorthodox beliefs, breaks from tradition, possible disgrace through teachers/religion", "अपरम्परागत विश्वासहरू, परम्परा तोड्ने, गुरु/धर्मबाट सम्भावित अपमान"),
    YOGA_EFFECT_VESI("Wealth through hard work, truthful, balanced life, comfortable old age", "मेहनतबाट धन, सत्यवादी, सन्तुलित जीवन, आरामदायक बुढेसकाल"),
    YOGA_EFFECT_VOSI("Famous, generous, skilled in service, gains through associations", "प्रसिद्ध, उदार, सेवामा दक्ष, संगतबाट लाभ"),
    YOGA_EFFECT_UBHAYACHARI("Eloquent speaker, wealthy, influential, respected by rulers", "वाक्पटु वक्ता, धनी, प्रभावशाली, शासकहरूद्वारा सम्मानित"),
    YOGA_EFFECT_LABHA("Gains from multiple sources, profitable ventures, wealth accumulation", "विभिन्न स्रोतबाट लाभ, लाभदायक उद्यमहरू, धन संचय"),
    YOGA_EFFECT_KUBERA("Immense wealth like lord of wealth, treasure finder, banking success", "धनको देवता जस्तो अपार धन, खजाना फेला पार्ने, बैंकिङमा सफलता"),
    YOGA_EFFECT_CHANDRA_MANGALA("Wealth through business, enterprise, real estate, aggressive financial pursuits", "व्यापारबाट धन, उद्यम, घर जग्गा, आक्रामक आर्थिक प्रयासहरू"),
    YOGA_EFFECT_DASA_MULA("Obstacles in undertakings, needs remedial measures, struggle with finances", "कार्यहरूमा बाधाहरू, उपचारात्मक उपायहरू आवश्यक, आर्थिक संघर्ष"),
    YOGA_EFFECT_KAHALA("Brave but stubborn, military success, leadership through conflict", "बहादुर तर हठी, सैन्य सफलता, द्वन्द्वबाट नेतृत्व"),

    // Yoga Descriptions
    YOGA_DESC_KENDRA_LORD("Kendra lord", "केन्द्र अधिपति"),
    YOGA_DESC_TRIKONA_LORD("Trikona lord", "त्रिकोण अधिपति"),
    YOGA_DESC_IN_CONJUNCTION("in conjunction", "संयोग मा"),
    YOGA_DESC_IN_ASPECT("in aspect", "दृष्टि मा"),
    YOGA_DESC_OWN_SIGN("own sign", "आफ्नो राशि"),
    YOGA_DESC_EXALTED("exalted sign", "उच्च राशि"),
    YOGA_DESC_IN_KENDRA("in Kendra", "केन्द्रमा"),
    YOGA_DESC_DUSTHANA("Dusthana", "दुस्थान"),
    YOGA_DESC_DEBILITATED("debilitated", "नीच"),
    YOGA_DESC_COMBUST("combust", "अस्त"),

    // Nabhasa Yoga Effects
    YOGA_EFFECT_YAVA("Medium wealth initially, prosperity in middle age, decline in old age", "सुरुमा मध्यम धन, मध्य उमेरमा समृद्धि, बुढेसकालमा पतन"),
    YOGA_EFFECT_SHRINGATAKA("Fond of quarrels initially, happiness in middle age, wandering in old age", "सुरुमा झगडा मनपर्ने, मध्य उमेरमा खुशी, बुढेसकालमा भौंतारिने"),
    YOGA_EFFECT_GADA("Wealthy through ceremonies, always engaged in auspicious activities", "संस्कारहरूबाट धनी, सधैं शुभ कार्यहरूमा संलग्न"),
    YOGA_EFFECT_SHAKATA_NABHASA("Fluctuating fortune, poverty followed by wealth in cycles", "उतार-चढाव भाग्य, गरिबीपछि धन चक्रमा"),
    YOGA_EFFECT_RAJJU("Fond of travel, living in foreign lands, restless nature", "यात्राको शौकीन, विदेशी भूमिमा बस्ने, अशान्त स्वभाव"),
    YOGA_EFFECT_MUSALA("Proud, wealthy, learned, famous, many children", "गर्विलो, धनी, पढेलेखेको, प्रसिद्ध, धेरै सन्तान"),
    YOGA_EFFECT_NALA("Handsome, skilled in arts, wealthy through multiple sources", "सुन्दर, कलामा दक्ष, विभिन्न स्रोतबाट धनी"),
    YOGA_EFFECT_KEDARA("Agricultural wealth, helpful to others, truthful", "कृषि धन, अरूलाई मद्दतगर्ने, सत्यवादी"),
    YOGA_EFFECT_SHOOLA("Sharp intellect, quarrelsome, cruel, poor", "तीक्ष्ण बुद्धि, झगडालु, निर्दयी, गरिब"),
    YOGA_EFFECT_YUGA("Heretic, poor, rejected by family", "विधर्मी, गरिब, परिवारद्वारा त्यागिएको"),
    YOGA_EFFECT_GOLA("Poor, dirty, ignorant, idle", "गरिब, फोहोरी, अज्ञानी, अल्छी"),
    YOGA_EFFECT_VEENA("Fond of music, dance, leader, wealthy, happy", "संगीत र नृत्यको शौकीन, नेता, धनी, खुशी"),


    // ============================================
    // ADDITIONAL UI STRINGS
    // ============================================
    UI_CONJUNCTION("conjunction", "युति"),
    UI_ASPECT("aspect", "दृष्टि"),
    UI_EXCHANGE("exchange", "परिवर्तन"),
    UI_THROUGHOUT_LIFE("Throughout life", "जीवनभर"),
    UI_NONE("None", "कुनै पनि छैन"),
    UI_PRESENT("Present", "उपस्थित"),
    UI_ASCENDING("Ascending", "आरोही"),
    UI_DESCENDING("Descending", "अवरोही"),
    UI_NAKSHATRAS("nakshatras", "नक्षत्रहरू"),

    // ============================================
    // SCREEN TAB NAMES
    // ============================================
    TAB_OVERVIEW("Overview", "सिंहावलोकन"),
    TAB_REMEDIES("Remedies", "उपायहरू"),
    TAB_PLANETS("Planets", "ग्रहहरू"),
    TAB_TODAY("Today", "आज"),
    TAB_FIND_MUHURTA("Find Muhurta", "मुहूर्त खोज्नुहोस्"),
    TAB_TAJIKA("Tajika", "ताजिक"),
    TAB_SAHAMS("Sahams", "सहमहरू"),
    TAB_DASHA("Dasha", "दशा"),
    TAB_HOUSES("Houses", "भावहरू"),
    TAB_ANALYSIS("Analysis", "विश्लेषण"),
    TAB_DETAILS("Details", "विवरणहरू"),
    TAB_TRANSITS("Transits", "गोचर"),
    TAB_ASPECTS("Aspects", "दृष्टि"),
    TAB_STRENGTH("Strength", "बल"),
    TAB_DIGNITIES("Dignities", "मर्यादाहरू"),

    // ============================================
    // REMEDIES SCREEN SPECIFIC
    // ============================================
    REMEDY_TITLE("Remedies", "उपायहरू"),
    REMEDY_SEARCH("Search remedies", "उपायहरू खोज्नुहोस्"),
    REMEDY_CALCULATION_FAILED("Failed to calculate remedies: %s", "उपायहरू गणना गर्न असफल: %s"),
    REMEDY_COPY("Copy", "कपी गर्नुहोस्"),
    REMEDY_SHARE("Share", "साझा गर्नुहोस्"),
    REMEDY_PLANETARY_ANALYSIS("Planetary Analysis", "ग्रह विश्लेषण"),
    REMEDY_ESSENTIAL_COUNT("%d Essential Remedies", "%d आवश्यक उपायहरू"),
    REMEDY_ALL_STRONG("All planets are in good condition", "सबै ग्रह राम्रो अवस्थामा छन्"),

    // ============================================
    // MUHURTA SCREEN SPECIFIC
    // ============================================
    MUHURTA_CHOGHADIYA("Choghadiya", "चौघडिया"),
    MUHURTA_RAHU_KAAL("Rahu Kaal", "राहुकाल"),
    MUHURTA_YAMA_GHANTAKA("Yama Ghantaka", "यम घण्टक"),
    MUHURTA_GULIKA_KAAL("Gulika Kaal", "गुलिका काल"),
    MUHURTA_ABHIJIT("Abhijit Muhurta", "अभिजित मुहूर्त"),
    MUHURTA_BRAHMA("Brahma Muhurta", "ब्रह्म मुहूर्त"),
    MUHURTA_SELECT_DATE("Select Date", "मिति चयन गर्नुहोस्"),
    MUHURTA_SEARCH_RESULTS("Search Results", "खोज परिणामहरू"),

    // ============================================
    // VARSHAPHALA SCREEN SPECIFIC
    // ============================================
    VARSHAPHALA_TITLE("Varshaphala", "वर्षफल"),
    VARSHAPHALA_ANNUAL_CHART("Annual Chart", "वार्षिक चार्ट"),
    VARSHAPHALA_SAHAMS("Sahams", "सहमहरू"),
    VARSHAPHALA_TAJIKA("Tajika Aspects", "ताजिक दृष्टि"),
    VARSHAPHALA_YOGAS("Tajika Yogas", "ताजिक योग"),
    VARSHAPHALA_PREDICTIONS("Year Predictions", "वर्ष भविष्यवाणी"),
    VARSHAPHALA_SELECT_YEAR("Select Year", "वर्ष चयन गर्नुहोस्"),
    VARSHAPHALA_YEAR_OF_LIFE("Year %d of life", "जीवनको वर्ष %d"),

    // ============================================
    // PANCHANGA DETAILS
    // ============================================
    PANCHANGA_TITHI_SHUKLA("Shukla", "शुक्ल"),
    PANCHANGA_TITHI_KRISHNA("Krishna", "कृष्ण"),
    PANCHANGA_PAKSHA("Paksha", "पक्ष"),
    PANCHANGA_MASA("Masa", "मास"),
    PANCHANGA_LUNAR_PHASE("Lunar Phase", "चन्द्र कला"),
    PANCHANGA_NEW_MOON("New Moon", "अमावस्या"),
    PANCHANGA_FULL_MOON("Full Moon", "पूर्णिमा"),
    PANCHANGA_WAXING("Waxing", "बढ्दो"),
    PANCHANGA_WANING("Waning", "घट्दो"),
    PANCHANGA_FAVORABLE("Favorable", "अनुकूल"),
    PANCHANGA_UNFAVORABLE("Unfavorable", "प्रतिकूल"),
    PANCHANGA_ACTIVITIES("Favorable Activities", "अनुकूल गतिविधिहरू"),

    // ============================================
    // TITHI NAMES
    // ============================================
    TITHI_PRATIPADA("Pratipada", "प्रतिपदा"),
    TITHI_DWITIYA("Dwitiya", "द्वितीया"),
    TITHI_TRITIYA("Tritiya", "तृतीया"),
    TITHI_CHATURTHI("Chaturthi", "चतुर्थी"),
    TITHI_PANCHAMI("Panchami", "पञ्चमी"),
    TITHI_SHASHTHI("Shashthi", "षष्ठी"),
    TITHI_SAPTAMI("Saptami", "सप्तमी"),
    TITHI_ASHTAMI("Ashtami", "अष्टमी"),
    TITHI_NAVAMI("Navami", "नवमी"),
    TITHI_DASHAMI("Dashami", "दशमी"),
    TITHI_EKADASHI("Ekadashi", "एकादशी"),
    TITHI_DWADASHI("Dwadashi", "द्वादशी"),
    TITHI_TRAYODASHI("Trayodashi", "त्रयोदशी"),
    TITHI_CHATURDASHI("Chaturdashi", "चतुर्दशी"),
    TITHI_PURNIMA("Purnima", "पूर्णिमा"),
    TITHI_AMAVASYA("Amavasya", "अमावस्या"),

    // ============================================
    // KARANA NAMES
    // ============================================
    KARANA_BAVA("Bava", "बव"),
    KARANA_BALAVA("Balava", "बालव"),
    KARANA_KAULAVA("Kaulava", "कौलव"),
    KARANA_TAITILA("Taitila", "तैतिल"),
    KARANA_GARA("Gara", "गर"),
    KARANA_VANIJA("Vanija", "वणिज"),
    KARANA_VISHTI("Vishti (Bhadra)", "विष्टि (भद्रा)"),
    KARANA_SHAKUNI("Shakuni", "शकुनि"),
    KARANA_CHATUSHPADA("Chatushpada", "चतुष्पद"),
    KARANA_NAGA("Naga", "नाग"),
    KARANA_KIMSTUGHNA("Kimstughna", "किंस्तुघ्न"),

    // ============================================
    // DAILY YOGA NAMES (PANCHANGA)
    // ============================================
    DAILY_YOGA_VISHKUMBHA("Vishkumbha", "विष्कम्भ"),
    DAILY_YOGA_PRITI("Priti", "प्रीति"),
    DAILY_YOGA_AYUSHMAN("Ayushman", "आयुष्मान"),
    DAILY_YOGA_SAUBHAGYA("Saubhagya", "सौभाग्य"),
    DAILY_YOGA_SHOBHANA("Shobhana", "शोभन"),
    DAILY_YOGA_ATIGANDA("Atiganda", "अतिगण्ड"),
    DAILY_YOGA_SUKARMA("Sukarma", "सुकर्म"),
    DAILY_YOGA_DHRITI("Dhriti", "धृति"),
    DAILY_YOGA_SHOOLA("Shoola", "शूल"),
    DAILY_YOGA_GANDA("Ganda", "गण्ड"),
    DAILY_YOGA_VRIDDHI("Vriddhi", "वृद्धि"),
    DAILY_YOGA_DHRUVA("Dhruva", "ध्रुव"),
    DAILY_YOGA_VYAGHATA("Vyaghata", "व्याघात"),
    DAILY_YOGA_HARSHANA("Harshana", "हर्षण"),
    DAILY_YOGA_VAJRA("Vajra", "वज्र"),
    DAILY_YOGA_SIDDHI("Siddhi", "सिद्धि"),
    DAILY_YOGA_VYATIPATA("Vyatipata", "व्यतीपात"),
    DAILY_YOGA_VARIYANA("Variyana", "वरीयान"),
    DAILY_YOGA_PARIGHA("Parigha", "परिघ"),
    DAILY_YOGA_SHIVA("Shiva", "शिव"),
    DAILY_YOGA_SIDDHA("Siddha", "सिद्ध"),
    DAILY_YOGA_SADHYA("Sadhya", "साध्य"),
    DAILY_YOGA_SHUBHA("Shubha", "शुभ"),
    DAILY_YOGA_SHUKLA("Shukla", "शुक्ल"),
    DAILY_YOGA_BRAHMA("Brahma", "ब्रह्म"),
    DAILY_YOGA_INDRA("Indra", "इन्द्र"),
    DAILY_YOGA_VAIDHRITI("Vaidhriti", "वैधृति"),

    // ============================================
    // VARA (WEEKDAY) DESCRIPTIONS
    // ============================================
    VARA_SUNDAY_DESC("Ruled by Sun - Good for government work, authority, spiritual practices", "सूर्य द्वारा शासित - सरकारी काम, अधिकार, आध्यात्मिक अभ्यासको लागि राम्रो"),
    VARA_MONDAY_DESC("Ruled by Moon - Good for travel, public dealings, emotional matters", "चन्द्रमा द्वारा शासित - यात्रा, सार्वजनिक व्यवहार, भावनात्मक मामिलाहरूको लागि राम्रो"),
    VARA_TUESDAY_DESC("Ruled by Mars - Good for property, surgery, competitive activities", "मंगल द्वारा शासित - सम्पत्ति, शल्यक्रिया, प्रतिस्पर्धात्मक गतिविधिहरूको लागि राम्रो"),
    VARA_WEDNESDAY_DESC("Ruled by Mercury - Good for education, communication, business", "बुध द्वारा शासित - शिक्षा, सञ्चार, व्यापारको लागि राम्रो"),
    VARA_THURSDAY_DESC("Ruled by Jupiter - Good for religious ceremonies, marriage, education", "बृहस्पति द्वारा शासित - धार्मिक समारोह, विवाह, शिक्षाको लागि राम्रो"),
    VARA_FRIDAY_DESC("Ruled by Venus - Good for romance, marriage, arts, luxury", "शुक्र द्वारा शासित - प्रेम, विवाह, कला, विलासिताको लागि राम्रो"),
    VARA_SATURDAY_DESC("Ruled by Saturn - Good for property, agriculture, spiritual discipline", "शनि द्वारा शासित - सम्पत्ति, कृषि, आध्यात्मिक अनुशासनको लागि राम्रो"),

    // ============================================
    // COMMON ACTION LABELS
    // ============================================
    ACTION_NEW_BEGINNINGS("New beginnings", "नयाँ सुरुवातहरू"),
    ACTION_STARTING_VENTURES("Starting ventures", "उद्यमहरू सुरु गर्दै"),
    ACTION_TRAVEL("Travel", "यात्रा"),
    ACTION_MARRIAGE("Marriage", "विवाह"),
    ACTION_EDUCATION("Education", "शिक्षा"),
    ACTION_BUSINESS("Business", "व्यापार"),
    ACTION_SPIRITUAL_PRACTICES("Spiritual practices", "आध्यात्मिक अभ्यास"),
    ACTION_WORSHIP("Worship", "पूजा"),
    ACTION_CHARITY("Charity", "दान"),
    ACTION_FASTING("Fasting", "उपवास"),
    ACTION_MEDITATION("Meditation", "ध्यान"),
    ACTION_SURGERY("Surgery", "शल्यक्रिया"),
    ACTION_CREATIVE_WORK("Creative work", "सिर्जनात्मक काम"),
    ACTION_GOVERNMENT_WORK("Government work", "सरकारी काम"),
    ACTION_PROPERTY_MATTERS("Property matters", "सम्पत्ति मामिलाहरू"),
    ACTION_FINANCIAL_MATTERS("Financial matters", "वित्तीय मामिलाहरू"),
    ACTION_LEGAL_MATTERS("Legal matters", "कानुनी मामिलाहरू"),

    // ============================================
    // NAVIGATION & COMMON UI ACTIONS
    // ============================================
    NAV_BACK("Back", "पछाडि"),
    NAV_NAVIGATE_BACK("Navigate back", "पछाडि जानुहोस्"),
    NAV_PREVIOUS("Previous", "अघिल्लो"),
    NAV_NEXT("Next", "अर्को"),
    NAV_PREVIOUS_YEAR("Previous year", "अघिल्लो वर्ष"),
    NAV_NEXT_YEAR("Next year", "अर्को वर्ष"),
    NAV_PREVIOUS_DAY("Previous day", "अघिल्लो दिन"),
    NAV_NEXT_DAY("Next day", "अर्को दिन"),

    ACTION_EXPORT("Export", "निर्यात गर्नुहोस्"),
    ACTION_CLEAR("Clear", "खाली गर्नुहोस्"),
    ACTION_CLEAR_SEARCH("Clear search", "खोज खाली गर्नुहोस्"),
    ACTION_COPY("Copy", "कपी गर्नुहोस्"),
    ACTION_COPY_MANTRA("Copy mantra", "मन्त्र कपी गर्नुहोस्"),
    ACTION_VIEW_DETAILS("View details", "विवरणहरू हेर्नुहोस्"),
    ACTION_VIEW_FULLSCREEN("View fullscreen", "पूर्ण स्क्रिनमा हेर्नुहोस्"),
    ACTION_NEW_QUESTION("New question", "नयाँ प्रश्न"),
    ACTION_SEARCH("Search", "खोज्नुहोस्"),

    // ============================================
    // MISC - ADDITIONAL STRINGS FOR DASHA SCREENS
    // ============================================
    MISC_DAYS_LEFT("days left", "दिन बाँकी"),
    MISC_MONTHS("months", "महिना"),
    MISC_CURRENT("Current", "वर्तमान"),
    MISC_YEARS("years", "वर्ष"),
    DASHA_NO_CURRENT_PERIOD("No current period active", "कुनै वर्तमान अवधि सक्रिय छैन"),
    DASHA_LEVEL_ANTARDASHAS("Antardashas", "अन्तर्दशाहरू"),

    // ============================================
    // INTERPRETATION - CHARA DASHA
    // ============================================
    INTERPRETATION_TITLE("Interpretation", "व्याख्या"),
    INTERPRETATION_GENERAL("General Effects", "सामान्य प्रभावहरू"),
    INTERPRETATION_LORD_EFFECTS("Lord Effects", "स्वामी प्रभावहरू"),
    INTERPRETATION_FAVORABLE("Favorable Areas", "अनुकूल क्षेत्रहरू"),
    INTERPRETATION_CHALLENGES("Challenges", "चुनौतीहरू"),
    INTERPRETATION_RECOMMENDATIONS("Recommendations", "सिफारिसहरू"),

    // ============================================
    // DASHA RECOMMENDATIONS
    // ============================================
    DASHA_RECOMMENDATIONS("Recommendations", "सिफारिसहरू"),

    // ============================================
    // BATCH 2 SCREEN KEYS
    // ============================================

    // Generic Actions & Accessibility
    ACC_VIEW_DETAILS("View details", "विवरण हेर्नुहोस्"),
    ACC_COLLAPSE("Collapse", "लुकाउनुहोस्"),
    ACC_EXPAND("Expand", "देखाउनुहोस्"),
    ACC_FULLSCREEN("View fullscreen", "पूरा स्क्रीन हेर्नुहोस्"),
    ACC_CHART_ANALYSIS("Chart analysis", "कुण्डली विश्लेषण"),
    ACC_COMING_SOON("Coming soon", "चाँडै आउँदैछ"),
    ACC_PROFILE_AVATAR("Profile avatar", "प्रोफाइल अवतार"),

    // Chart & Planets
    CHART_LAGNA("Lagna", "लग्न"),
    CHART_MOON("Moon", "चन्द्रमा"),
    CHART_SUN("Sun", "सूर्य"),
    CHART_NAKSHATRA_LABEL("Nakshatra", "नक्षत्र"),
    CHART_ASCENDANT_LABEL("Ascendant", "लग्न"),
    LABEL_NA("N/A", "-"),
    LABEL_DASH("-", "-"),

    // Settings Theme
    THEME_LIGHT("Light", "उज्यालो"),
    THEME_DARK("Dark", "गाढा"),
    THEME_SYSTEM("System", "सिस्टम"),
    THEME_DESC_LIGHT("Always use light theme", "सधैं उज्यालो थीम प्रयोग गर्नुहोस्"),
    THEME_DESC_DARK("Always use dark theme", "सधैं गाढा थीम प्रयोग गर्नुहोस्"),
    THEME_DESC_SYSTEM("Follow device settings", "डिभाइस सेटिङ अनुसरण गर्नुहोस्"),
    SETTINGS_THEME_TITLE("Theme", "थीम"),

    // Dignity Status
    DIGNITY_EXALTED_STATUS("Exalted", "उच्च"),
    DIGNITY_DEBILITATED_STATUS("Debilitated", "नीच"),
    DIGNITY_OWN_SIGN_STATUS("Own Sign", "स्वराशि"),
    DIGNITY_NEUTRAL_STATUS("Neutral", "सम"),

    // Errors & Dialogs
    ERROR_HOROSCOPE_UNAVAILABLE_FMT("Horoscope for {0} is currently unavailable.", "{0}को राशिफल हाल उपलब्ध छैन।"),
    ERROR_GENERIC_RETRY("Please try again later.", "कृपया पछि फेरि प्रयास गर्नुहोस्।"),

    // Navigation & Tabs
    NAV_HOME("Home", "गृह"),
    NAV_INSIGHTS("Insights", "अन्तर्दृष्टि"),
    NAV_CHAT("Chat", "कुराकानी"),
    NAV_SETTINGS("Settings", "सेटिङहरू"),

    // Home Screen
    HOME_CHART_ANALYSIS("Your Chart Analysis", "तपाईंको कुण्डली विश्लेषण"),
    HOME_COMING_SOON("Coming Soon", "चाँडै आउँदैछ"),
    HOME_SOON_BADGE("Soon", "चाँडै"),
    NO_PROFILE_SELECTED("No Profile Selected", "कुनै प्रोफाइल छानिएको छैन"),
    NO_PROFILE_MESSAGE("Create or select a profile to view personalized insights and charts.", "व्यक्तिगत अन्तर्दृष्टि र कुण्डलीहरू हेर्न प्रोफाइल बनाउनुहोस् वा छान्नुहोस्।"),
    BTN_CREATE_CHART("Create New Chart", "नयाँ कुण्डली बनाउनुहोस्"),
    BTN_VIEW_DETAILS("View Details", "विवरण हेर्नुहोस्"),

    // Insights Error States
    ERROR_PARTIAL("Partial Data Loaded", "आंशिक डाटा लोड भयो"),
    ERROR_CALCULATIONS_FAILED("{0} calculations failed", "{0} गणनाहरू असफल भए"),
    ERROR_UNABLE_TO_LOAD("Unable to Load", "लोड गर्न सकिएन"),
    BTN_RETRY("Retry", "फेरि प्रयास गर्नुहोस्"),
    BTN_TRY_AGAIN("Try Again", "फेरि प्रयास गर्नुहोस्"),
    BTN_PREVIEW("Preview", "पूर्वावलोकन"),

    // Insights - Transits
    TRANSITS_MOON_IN("Moon in {0}", "{0}मा चन्द्रमा"),

    SETTINGS_PROFILE("Profile", "प्रोफाइल"),
    SETTINGS_EDIT_PROFILE("Edit Profile", "प्रोफाइल सम्पादन गर्नुहोस्"),
    SETTINGS_EDIT_PROFILE_DESC("Modify current charts details", "हालको कुण्डली विवरण परिमार्जन गर्नुहोस्"),
    SETTINGS_MANAGE_PROFILES("Manage Profiles", "प्रोफाइलहरू व्यवस्थापन गर्नुहोस्"),
    SETTINGS_NO_PROFILE("No Profile", "कुनै प्रोफाइल छैन"),
    SETTINGS_TAP_TO_SELECT("Tap to create or select a profile", "प्रोफाइल बनाउन वा छान्न ट्याप गर्नुहोस्"),
    SETTINGS_EXPORT("Export", "निर्यात"),
    SETTINGS_EXPORT_PDF("Export as PDF", "PDF को रूपमा निर्यात गर्नुहोस्"),
    SETTINGS_EXPORT_PDF_DESC("Save chart details as PDF file", "कुण्डली विवरण PDF फाइलको रूपमा बचत गर्नुहोस्"),
    SETTINGS_AI_CHAT("AI & Chat", "AI र कुराकानी"),
    SETTINGS_AI_MODELS("AI Models", "AI मोडेलहरू"),
    SETTINGS_AI_MODELS_DESC("Configure AI model settings", "AI मोडेल सेटिङहरू कन्फिगर गर्नुहोस्"),
    SETTINGS_PREFERENCES("Preferences", "प्राथमिकताहरू"),
    SETTINGS_LANGUAGE("Language", "भाषा"),
    SETTINGS_HOUSE_SYSTEM("House System", "भाव प्रणाली"),
    SETTINGS_ABOUT("About", "बारेमा"),
    SETTINGS_ABOUT_APP("AstroStorm", "एस्ट्रोस्टर्म"),
    SETTINGS_VERSION("Version {0}", "संस्करण {0}"),
    SETTINGS_CALC_ENGINE("Calculation Engine", "गणना इन्जिन"),
    SETTINGS_CALC_ENGINE_DESC("Swiss Ephemeris & Vedic Algorithms", "स्विस एफिमेरिस र वैदिक एल्गोरिदमहरू"),
    DIALOG_DELETE_PROFILE("Delete Profile", "प्रोफाइल मेटाउनुहोस्"),
    DIALOG_DELETE_CONFIRM("Are you sure you want to delete profile ''{0}''? This action cannot be undone.", "के तपाईं साँच्चिकै ''{0}'' प्रोफाइल मेटाउन चाहनुहुन्छ? यो कार्य पूर्ववत गर्न सकिँदैन।"),
    BTN_DELETE("Delete", "मेटाउनुहोस्"),
    BTN_CANCEL("Cancel", "रद्ध गर्नुहोस्");
}
