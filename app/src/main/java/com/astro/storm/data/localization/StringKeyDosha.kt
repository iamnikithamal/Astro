package com.astro.storm.data.localization



/**
 * Divisional charts, Shadbala, and Dosha (Sade Sati, Manglik, Pitra) string keys
 * Part 4 of 4 split enums to avoid JVM method size limit
 */
enum class StringKeyDosha(override val en: String, override val ne: String) : StringKeyInterface {
    GUNA_SATTVA("Sattva", "à¤¸à¤¤à¥à¤¤à¥à¤µ"),

    // ============================================
    // DIVISIONAL CHART TITLES (For DivisionalChartCalculator)
    // ============================================
    VARGA_D1_TITLE("Rashi (D1)", "à¤°à¤¾à¤¶à¤¿ (D1)"),
    VARGA_D1_DESC("Physical Body, General Life", "à¤­à¥Œà¤¤à¤¿à¤• à¤¶à¤°à¥€à¤°, à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤œà¥€à¤µà¤¨"),
    VARGA_D2_TITLE("Hora (D2)", "à¤¹à¥‹à¤°à¤¾ (D2)"),
    VARGA_D2_DESC("Wealth, Prosperity", "à¤§à¤¨, à¤¸à¤®à¥ƒà¤¦à¥à¤§à¤¿"),
    VARGA_D3_TITLE("Drekkana (D3)", "à¤¦à¥à¤°à¥‡à¤•à¥à¤•à¤¾à¤£ (D3)"),
    VARGA_D3_DESC("Siblings, Courage", "à¤­à¤¾à¤‡à¤¬à¤¹à¤¿à¤¨à¥€, à¤¸à¤¾à¤¹à¤¸"),
    VARGA_D4_TITLE("Chaturthamsa (D4)", "à¤šà¤¤à¥à¤°à¥à¤¥à¤¾à¤‚à¤¶ (D4)"),
    VARGA_D4_DESC("Fortune, Property", "à¤­à¤¾à¤—à¥à¤¯, à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿"),
    VARGA_D7_TITLE("Saptamsa (D7)", "à¤¸à¤ªà¥à¤¤à¤®à¤¾à¤‚à¤¶ (D7)"),
    VARGA_D7_DESC("Children, Progeny", "à¤¸à¤¨à¥à¤¤à¤¾à¤¨"),
    VARGA_D9_TITLE("Navamsa (D9)", "à¤¨à¤µà¤®à¤¾à¤‚à¤¶ (D9)"),
    VARGA_D9_DESC("Marriage, Dharma", "à¤µà¤¿à¤µà¤¾à¤¹, à¤§à¤°à¥à¤®"),
    VARGA_D10_TITLE("Dasamsa (D10)", "à¤¦à¤¶à¤®à¤¾à¤‚à¤¶ (D10)"),
    VARGA_D10_DESC("Career, Profession", "à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°, à¤ªà¥‡à¤¶à¤¾"),
    VARGA_D12_TITLE("Dwadasamsa (D12)", "à¤¦à¥à¤µà¤¾à¤¦à¤¶à¤¾à¤‚à¤¶ (D12)"),
    VARGA_D12_DESC("Parents, Ancestry", "à¤†à¤®à¤¾à¤¬à¥à¤µà¤¾, à¤ªà¥à¤°à¥à¤–à¤¾"),
    VARGA_D16_TITLE("Shodasamsa (D16)", "à¤·à¥‹à¤¡à¤¶à¤¾à¤‚à¤¶ (D16)"),
    VARGA_D16_DESC("Vehicles, Pleasures", "à¤¸à¤µà¤¾à¤°à¥€, à¤†à¤¨à¤¨à¥à¤¦"),
    VARGA_D20_TITLE("Vimsamsa (D20)", "à¤µà¤¿à¤‚à¤¶à¤¾à¤‚à¤¶ (D20)"),
    VARGA_D20_DESC("Spiritual Life", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤œà¥€à¤µà¤¨"),
    VARGA_D24_TITLE("Siddhamsa (D24)", "à¤¸à¤¿à¤¦à¥à¤§à¤¾à¤‚à¤¶ (D24)"),
    VARGA_D24_DESC("Education, Learning", "à¤¶à¤¿à¤•à¥à¤·à¤¾, à¤¸à¤¿à¤•à¤¾à¤‡"),
    VARGA_D27_TITLE("Bhamsa (D27)", "à¤­à¤¾à¤‚à¤¶ (D27)"),
    VARGA_D27_DESC("Strength, Weakness", "à¤¬à¤², à¤•à¤®à¤œà¥‹à¤°à¥€"),
    VARGA_D30_TITLE("Trimsamsa (D30)", "à¤¤à¥à¤°à¤¿à¤‚à¤¶à¤¾à¤‚à¤¶ (D30)"),
    VARGA_D30_DESC("Evils, Misfortunes", "à¤…à¤¶à¥à¤­, à¤¦à¥à¤°à¥à¤­à¤¾à¤—à¥à¤¯"),
    VARGA_D40_TITLE("Khavedamsa (D40)", "à¤–à¤µà¥‡à¤¦à¤¾à¤‚à¤¶ (D40)"),
    VARGA_D40_DESC("Auspicious/Inauspicious Effects", "à¤¶à¥à¤­à¤¾à¤¶à¥à¤­ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    VARGA_D45_TITLE("Akshavedamsa (D45)", "à¤…à¤•à¥à¤·à¤µà¥‡à¤¦à¤¾à¤‚à¤¶ (D45)"),
    VARGA_D45_DESC("General Indications", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤¸à¤™à¥à¤•à¥‡à¤¤à¤¹à¤°à¥‚"),
    VARGA_D60_TITLE("Shashtiamsa (D60)", "à¤·à¤·à¥à¤Ÿà¥à¤¯à¤‚à¤¶ (D60)"),
    VARGA_D60_DESC("Past Life Karma", "à¤ªà¥‚à¤°à¥à¤µà¤œà¤¨à¥à¤®à¤•à¥‹ à¤•à¤°à¥à¤®"),
    VARGA_PLANETARY_POSITIONS("PLANETARY POSITIONS", "à¤—à¥à¤°à¤¹ à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤¹à¤°à¥‚"),

    // ============================================
    // HOROSCOPE CALCULATOR (Life Areas & Themes)
    // ============================================
    LIFE_AREA_SPIRITUAL("Spiritual Growth", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤µà¥ƒà¤¦à¥à¤§à¤¿"),

    // Weekly Themes
    THEME_BALANCE("Balance", "à¤¸à¤¨à¥à¤¤à¥à¤²à¤¨"),
    THEME_DYNAMIC_ACTION("Dynamic Action", "à¤—à¤¤à¤¿à¤¶à¥€à¤² à¤•à¤¾à¤°à¥à¤¯"),
    THEME_PRACTICAL_PROGRESS("Practical Progress", "à¤µà¥à¤¯à¤¾à¤µà¤¹à¤¾à¤°à¤¿à¤• à¤ªà¥à¤°à¤—à¤¤à¤¿"),
    THEME_SOCIAL_CONNECTIONS("Social Connections", "à¤¸à¤¾à¤®à¤¾à¤œà¤¿à¤• à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¹à¤°à¥‚"),
    THEME_EMOTIONAL_INSIGHT("Emotional Insight", "à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤…à¤¨à¥à¤¤à¤°à¥à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿"),
    THEME_SELF_EXPRESSION("Self-Expression", "à¤†à¤¤à¥à¤®-à¤…à¤­à¤¿à¤µà¥à¤¯à¤•à¥à¤¤à¤¿"),
    THEME_TRANSFORMATION("Transformation", "à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£"),
    THEME_SPIRITUAL_LIBERATION("Spiritual Liberation", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤®à¥à¤•à¥à¤¤à¤¿"),

    // Element-based advice
    ADVICE_FIRE_ELEMENT("Take bold action and express yourself confidently.", "à¤¸à¤¾à¤¹à¤¸à¤¿à¤• à¤•à¤¦à¤® à¤šà¤¾à¤²à¥à¤¨à¥à¤¹à¥‹à¤¸à¥ à¤° à¤†à¤¤à¥à¤®à¤µà¤¿à¤¶à¥à¤µà¤¾à¤¸à¤•à¤¾ à¤¸à¤¾à¤¥ à¤†à¤«à¥‚à¤²à¤¾à¤ˆ à¤…à¤­à¤¿à¤µà¥à¤¯à¤•à¥à¤¤ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    ADVICE_EARTH_ELEMENT("Focus on practical matters and material progress.", "à¤µà¥à¤¯à¤¾à¤µà¤¹à¤¾à¤°à¤¿à¤• à¤®à¤¾à¤®à¤¿à¤²à¤¾à¤¹à¤°à¥‚ à¤° à¤­à¥Œà¤¤à¤¿à¤• à¤ªà¥à¤°à¤—à¤¤à¤¿à¤®à¤¾ à¤§à¥à¤¯à¤¾à¤¨ à¤¦à¤¿à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    ADVICE_AIR_ELEMENT("Engage in social activities and intellectual pursuits.", "à¤¸à¤¾à¤®à¤¾à¤œà¤¿à¤• à¤•à¥à¤°à¤¿à¤¯à¤¾à¤•à¤²à¤¾à¤ªà¤¹à¤°à¥‚ à¤° à¤¬à¥Œà¤¦à¥à¤§à¤¿à¤• à¤–à¥‹à¤œà¤®à¤¾ à¤¸à¤‚à¤²à¤—à¥à¤¨ à¤¹à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    ADVICE_WATER_ELEMENT("Trust your intuition and honor your emotions.", "à¤†à¤«à¥à¤¨à¥‹ à¤…à¤¨à¥à¤¤à¤°à¥à¤œà¥à¤žà¤¾à¤¨à¤®à¤¾ à¤µà¤¿à¤¶à¥à¤µà¤¾à¤¸ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥ à¤° à¤­à¤¾à¤µà¤¨à¤¾à¤¹à¤°à¥‚à¤²à¤¾à¤ˆ à¤¸à¤®à¥à¤®à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),

    // Week types
    WEEK_OPPORTUNITIES("Week of Opportunities", "à¤…à¤µà¤¸à¤°à¤¹à¤°à¥‚à¤•à¥‹ à¤¹à¤ªà¥à¤¤à¤¾"),
    WEEK_STEADY_PROGRESS("Steady Progress", "à¤¸à¥à¤¥à¤¿à¤° à¤ªà¥à¤°à¤—à¤¤à¤¿"),
    WEEK_MINDFUL_NAVIGATION("Mindful Navigation", "à¤¸à¤šà¥‡à¤¤ à¤¨à¥‡à¤­à¤¿à¤—à¥‡à¤¸à¤¨"),

    // Time periods
    TIME_MORNING("Morning hours", "à¤¬à¤¿à¤¹à¤¾à¤¨à¤•à¥‹ à¤¸à¤®à¤¯"),
    TIME_AFTERNOON("Afternoon hours", "à¤¦à¤¿à¤‰à¤à¤¸à¥‹à¤•à¥‹ à¤¸à¤®à¤¯"),
    TIME_EVENING("Evening hours", "à¤¸à¤¾à¤à¤à¤•à¥‹ à¤¸à¤®à¤¯"),

    // ============================================
    // MATCHMAKING CALCULATOR (Additional Vashya types)
    // ============================================
    VASHYA_QUADRUPED("Quadruped", "à¤šà¤¤à¥à¤·à¥à¤ªà¤¾à¤¦"),
    VASHYA_HUMAN("Human", "à¤®à¤¨à¥à¤·à¥à¤¯"),
    VASHYA_AQUATIC("Aquatic", "à¤œà¤²à¤šà¤°"),
    VASHYA_WILD("Wild", "à¤µà¤¨à¥à¤¯"),
    VASHYA_INSECT("Insect", "à¤•à¥€à¤Ÿ"),

    // Compatibility ratings (additional)
    COMPAT_BELOW_AVG("Below Average", "à¤”à¤¸à¤¤à¤­à¤¨à¥à¤¦à¤¾ à¤•à¤®"),
    COMPAT_BELOW_AVG_DESC("Caution advised. Several compatibility issues that need addressing through remedies and counseling.", "à¤¸à¤¾à¤µà¤§à¤¾à¤¨à¥€ à¤¸à¤²à¥à¤²à¤¾à¤¹ à¤¦à¤¿à¤‡à¤à¤•à¥‹à¥¤ à¤‰à¤ªà¤¾à¤¯ à¤° à¤ªà¤°à¤¾à¤®à¤°à¥à¤¶à¤¬à¤¾à¤Ÿ à¤¸à¤®à¥à¤¬à¥‹à¤§à¤¨ à¤—à¤°à¥à¤¨à¥à¤ªà¤°à¥à¤¨à¥‡ à¤§à¥‡à¤°à¥ˆ à¤…à¤¨à¥à¤•à¥‚à¤²à¤¤à¤¾ à¤¸à¤®à¤¸à¥à¤¯à¤¾à¤¹à¤°à¥‚à¥¤"),

    // Yogini planet associations (additional)
    YOGINI_CHANDRA("Chandra (Moon)", "à¤šà¤¨à¥à¤¦à¥à¤°"),
    YOGINI_SURYA("Surya (Sun)", "à¤¸à¥‚à¤°à¥à¤¯"),
    YOGINI_GURU("Guru (Jupiter)", "à¤—à¥à¤°à¥"),
    YOGINI_MANGAL("Mangal (Mars)", "à¤®à¤‚à¤—à¤²"),
    YOGINI_BUDHA("Budha (Mercury)", "à¤¬à¥à¤§"),
    YOGINI_SHANI("Shani (Saturn)", "à¤¶à¤¨à¤¿"),
    YOGINI_SHUKRA("Shukra (Venus)", "à¤¶à¥à¤•à¥à¤°"),
    YOGINI_RAHU("Rahu", "à¤°à¤¾à¤¹à¥"),

    // ============================================
    // REPORT HEADERS
    // ============================================
    REPORT_VEDIC_REMEDIES("VEDIC ASTROLOGY REMEDIES REPORT", "à¤µà¥ˆà¤¦à¤¿à¤• à¤œà¥à¤¯à¥‹à¤¤à¤¿à¤· à¤‰à¤ªà¤¾à¤¯ à¤ªà¥à¤°à¤¤à¤¿à¤µà¥‡à¤¦à¤¨"),
    REPORT_PLANETS_NEEDING_ATTENTION("PLANETS REQUIRING ATTENTION:", "à¤§à¥à¤¯à¤¾à¤¨ à¤†à¤µà¤¶à¥à¤¯à¤• à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚:"),

    // ============================================
    // ERROR MESSAGES (Additional for GeocodingService)
    // ============================================
    ERROR_QUERY_MIN_CHARS("Query must be at least 2 characters", "à¤–à¥‹à¤œà¥€ à¤•à¤®à¥à¤¤à¥€à¤®à¤¾ à¥¨ à¤µà¤°à¥à¤£à¤•à¥‹ à¤¹à¥à¤¨à¥à¤ªà¤°à¥à¤›"),
    ERROR_CONNECTION_TIMEOUT("Connection timeout. Please check your internet.", "à¤œà¤¡à¤¾à¤¨ à¤¸à¤®à¤¯ à¤¸à¤®à¤¾à¤ªà¥à¤¤à¥¤ à¤•à¥ƒà¤ªà¤¯à¤¾ à¤†à¤«à¥à¤¨à¥‹ à¤‡à¤¨à¥à¤Ÿà¤°à¤¨à¥‡à¤Ÿ à¤œà¤¾à¤à¤š à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    ERROR_NO_INTERNET("No internet connection.", "à¤‡à¤¨à¥à¤Ÿà¤°à¤¨à¥‡à¤Ÿ à¤œà¤¡à¤¾à¤¨ à¤›à¥ˆà¤¨à¥¤"),
    ERROR_UNKNOWN("Unknown error", "à¤…à¤œà¥à¤žà¤¾à¤¤ à¤¤à¥à¤°à¥à¤Ÿà¤¿"),

    // ============================================
    // GENERAL UI LABELS
    // ============================================
    LABEL_UNKNOWN("Unknown", "à¤…à¤œà¥à¤žà¤¾à¤¤"),
    LABEL_REQUIRED("Required:", "à¤†à¤µà¤¶à¥à¤¯à¤•:"),
    LABEL_RUPAS("Rupas", "à¤°à¥‚à¤ªà¤¾"),
    LABEL_PERCENT_REQUIRED("% of required", "à¤†à¤µà¤¶à¥à¤¯à¤•à¤•à¥‹ %"),
    LABEL_TODAY("Today", "à¤†à¤œ"),
    LABEL_PREVIOUS_MONTH("Previous month", "à¤…à¤˜à¤¿à¤²à¥à¤²à¥‹ à¤®à¤¹à¤¿à¤¨à¤¾"),
    LABEL_NEXT_MONTH("Next month", "à¤…à¤°à¥à¤•à¥‹ à¤®à¤¹à¤¿à¤¨à¤¾"),
    LABEL_AD("AD", "à¤ˆ.à¤¸à¤‚."),
    LABEL_BS("BS", "à¤¬à¤¿.à¤¸à¤‚."),

    // Weekday abbreviations
    WEEKDAY_SU("Su", "à¤†"),
    WEEKDAY_MO("Mo", "à¤¸à¥‹"),
    WEEKDAY_TU("Tu", "à¤®à¤‚"),
    WEEKDAY_WE("We", "à¤¬à¥"),
    WEEKDAY_TH("Th", "à¤¬à¤¿"),
    WEEKDAY_FR("Fr", "à¤¶à¥"),
    WEEKDAY_SA("Sa", "à¤¶"),

    // ============================================
    // COLORS (For Horoscope advice)
    // ============================================
    COLOR_RED_ORANGE_GOLD("Red, Orange, or Gold", "à¤°à¤¾à¤¤à¥‹, à¤¸à¥à¤¨à¥à¤¤à¤²à¤¾, à¤µà¤¾ à¤¸à¥à¤¨à¥Œà¤²à¥‹"),
    COLOR_GREEN_BROWN_WHITE("Green, Brown, or White", "à¤¹à¤°à¤¿à¤¯à¥‹, à¤–à¥ˆà¤°à¥‹, à¤µà¤¾ à¤¸à¥‡à¤¤à¥‹"),
    COLOR_BLUE_LIGHT_SILVER("Blue, Light Blue, or Silver", "à¤¨à¤¿à¤²à¥‹, à¤¹à¤²à¥à¤•à¤¾ à¤¨à¤¿à¤²à¥‹, à¤µà¤¾ à¤šà¤¾à¤à¤¦à¥€"),
    COLOR_WHITE_CREAM_SEA("White, Cream, or Sea Green", "à¤¸à¥‡à¤¤à¥‹, à¤•à¥à¤°à¤¿à¤®, à¤µà¤¾ à¤¸à¤®à¥à¤¦à¥à¤°à¥€ à¤¹à¤°à¤¿à¤¯à¥‹"),

    // ============================================
    // GEMSTONES
    // ============================================
    GEMSTONE_RUBY("Ruby", "à¤®à¤¾à¤£à¤¿à¤•"),
    GEMSTONE_PEARL("Pearl", "à¤®à¥‹à¤¤à¥€"),
    GEMSTONE_RED_CORAL("Red Coral", "à¤®à¥‚à¤‚à¤—à¤¾"),
    GEMSTONE_EMERALD("Emerald", "à¤ªà¤¨à¥à¤¨à¤¾"),
    GEMSTONE_YELLOW_SAPPHIRE("Yellow Sapphire", "à¤ªà¥à¤–à¤°à¤¾à¤œ"),
    GEMSTONE_DIAMOND("Diamond/White Sapphire", "à¤¹à¥€à¤°à¤¾/à¤¸à¥‡à¤¤à¥‹ à¤¨à¥€à¤²à¤®"),
    GEMSTONE_BLUE_SAPPHIRE("Blue Sapphire", "à¤¨à¥€à¤²à¤®"),
    GEMSTONE_HESSONITE("Hessonite", "à¤—à¥‹à¤®à¥‡à¤¦"),
    GEMSTONE_CATS_EYE("Cat's Eye", "à¤²à¤¹à¤¸à¥à¤¨à¤¿à¤¯à¤¾"),

    // ============================================
    // MOON PHASES
    // ============================================
    MOON_FIRST_QUARTER("First Quarter Moon", "à¤ªà¤¹à¤¿à¤²à¥‹ à¤šà¥Œà¤¥à¤¾à¤ˆ à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾"),
    MOON_FIRST_QUARTER_DESC("Good for taking action", "à¤•à¤¾à¤°à¥à¤¯ à¤—à¤°à¥à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤°à¤¾à¤®à¥à¤°à¥‹"),
    MOON_FULL("Full Moon", "à¤ªà¥‚à¤°à¥à¤£à¤¿à¤®à¤¾"),
    MOON_FULL_DESC("Emotional peak - completion energy", "à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤¶à¤¿à¤–à¤° - à¤ªà¥‚à¤°à¥à¤£à¤¤à¤¾ à¤Šà¤°à¥à¤œà¤¾"),

    // ============================================
    // PRASHNA HOUSE SIGNIFICATIONS
    // ============================================
    PRASHNA_HOUSE_1_NAME("Lagna/Querent", "à¤²à¤—à¥à¤¨/à¤ªà¥à¤°à¤¶à¥à¤¨à¤•à¤°à¥à¤¤à¤¾"),
    PRASHNA_HOUSE_2_NAME("Dhana", "à¤§à¤¨"),
    PRASHNA_HOUSE_3_NAME("Sahaja", "à¤¸à¤¹à¤œ"),
    PRASHNA_HOUSE_4_NAME("Sukha", "à¤¸à¥à¤–"),
    PRASHNA_HOUSE_5_NAME("Putra", "à¤ªà¥à¤¤à¥à¤°"),
    PRASHNA_HOUSE_6_NAME("Ripu", "à¤°à¤¿à¤ªà¥"),
    PRASHNA_HOUSE_7_NAME("Kalatra", "à¤•à¤²à¤¤à¥à¤°"),
    PRASHNA_HOUSE_8_NAME("Ayu", "à¤†à¤¯à¥"),
    PRASHNA_HOUSE_9_NAME("Dharma", "à¤§à¤°à¥à¤®"),
    PRASHNA_HOUSE_10_NAME("Karma", "à¤•à¤°à¥à¤®"),
    PRASHNA_HOUSE_11_NAME("Labha", "à¤²à¤¾à¤­"),
    PRASHNA_HOUSE_12_NAME("Vyaya", "à¤µà¥à¤¯à¤¯"),

    // Body parts for Prashna
    BODY_PART_HEAD("Head", "à¤Ÿà¤¾à¤‰à¤•à¥‹"),
    BODY_PART_FACE("Face/Mouth", "à¤…à¤¨à¥à¤¹à¤¾à¤°/à¤®à¥à¤–"),
    BODY_PART_ARMS("Arms/Shoulders", "à¤ªà¤¾à¤–à¥à¤°à¤¾/à¤•à¤¾à¤à¤§"),
    BODY_PART_CHEST("Chest", "à¤›à¤¾à¤¤à¥€"),
    BODY_PART_UPPER_ABDOMEN("Upper Abdomen", "à¤®à¤¾à¤¥à¤¿à¤²à¥à¤²à¥‹ à¤ªà¥‡à¤Ÿ"),
    BODY_PART_LOWER_ABDOMEN("Lower Abdomen", "à¤¤à¤²à¥à¤²à¥‹ à¤ªà¥‡à¤Ÿ"),
    BODY_PART_BELOW_NAVEL("Below Navel", "à¤¨à¤¾à¤­à¤¿à¤®à¥à¤¨à¤¿"),
    BODY_PART_REPRODUCTIVE("Reproductive organs", "à¤ªà¥à¤°à¤œà¤¨à¤¨ à¤…à¤‚à¤—à¤¹à¤°à¥‚"),
    BODY_PART_THIGHS("Thighs", "à¤œà¤¾à¤à¤˜"),
    BODY_PART_KNEES("Knees", "à¤˜à¥à¤à¤¡à¤¾"),
    BODY_PART_CALVES("Calves/Shins", "à¤ªà¤¿à¤à¤¡à¥Œà¤²à¤¾"),
    BODY_PART_FEET("Feet", "à¤–à¥à¤Ÿà¥à¤Ÿà¤¾"),

    // Prashna terms
    PRASHNA_MOOK("Mook (Dumb) Prashna", "à¤®à¥‚à¤• à¤ªà¥à¤°à¤¶à¥à¤¨"),
    PRASHNA_ARUDHA("Arudha Lagna", "à¤†à¤°à¥‚à¤¢ à¤²à¤—à¥à¤¨"),

    // ============================================
    // REMEDIES REPORT LABELS
    // ============================================
    REPORT_NAME_LABEL("Name:", "à¤¨à¤¾à¤®:"),
    REPORT_ASCENDANT_LABEL("Ascendant:", "à¤²à¤—à¥à¤¨:"),
    REPORT_MANTRA_LABEL("Mantra:", "à¤®à¤¨à¥à¤¤à¥à¤°:"),

    // ============================================
    // PLANET LIFE AREAS (For remedies)
    // ============================================
    PLANET_LIFE_AREA_SUN("authority, career, government favor, father's health, self-confidence", "à¤…à¤§à¤¿à¤•à¤¾à¤°, à¤•à¤°à¤¿à¤¯à¤°, à¤¸à¤°à¤•à¤¾à¤°à¥€ à¤•à¥ƒà¤ªà¤¾, à¤¬à¥à¤¬à¤¾à¤•à¥‹ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯, à¤†à¤¤à¥à¤®à¤µà¤¿à¤¶à¥à¤µà¤¾à¤¸"),
    PLANET_LIFE_AREA_MOON("mental peace, mother's health, emotional stability, public relations", "à¤®à¤¾à¤¨à¤¸à¤¿à¤• à¤¶à¤¾à¤¨à¥à¤¤à¤¿, à¤†à¤®à¤¾à¤•à¥‹ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯, à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤¸à¥à¤¥à¤¿à¤°à¤¤à¤¾, à¤œà¤¨à¤¸à¤®à¥à¤ªà¤°à¥à¤•"),
    PLANET_LIFE_AREA_MARS("courage, siblings, property matters, physical strength, competition", "à¤¸à¤¾à¤¹à¤¸, à¤­à¤¾à¤‡à¤¬à¤¹à¤¿à¤¨à¥€, à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿ à¤®à¤¾à¤®à¤¿à¤²à¤¾, à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤¶à¤•à¥à¤¤à¤¿, à¤ªà¥à¤°à¤¤à¤¿à¤¸à¥à¤ªà¤°à¥à¤§à¤¾"),
    PLANET_LIFE_AREA_MERCURY("intellect, communication, business, education, nervous system", "à¤¬à¥à¤¦à¥à¤§à¤¿, à¤¸à¤žà¥à¤šà¤¾à¤°, à¤µà¥à¤¯à¤¾à¤ªà¤¾à¤°, à¤¶à¤¿à¤•à¥à¤·à¤¾, à¤¸à¥à¤¨à¤¾à¤¯à¥ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€"),
    PLANET_LIFE_AREA_JUPITER("wisdom, children, wealth, spirituality, teachers, dharma", "à¤œà¥à¤žà¤¾à¤¨, à¤¸à¤¨à¥à¤¤à¤¾à¤¨, à¤§à¤¨, à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤•à¤¤à¤¾, à¤—à¥à¤°à¥, à¤§à¤°à¥à¤®"),
    PLANET_LIFE_AREA_VENUS("marriage, love, luxury, art, vehicles, pleasure", "à¤µà¤¿à¤µà¤¾à¤¹, à¤ªà¥à¤°à¥‡à¤®, à¤µà¤¿à¤²à¤¾à¤¸à¤¿à¤¤à¤¾, à¤•à¤²à¤¾, à¤¸à¤µà¤¾à¤°à¥€, à¤†à¤¨à¤¨à¥à¤¦"),
    PLANET_LIFE_AREA_SATURN("longevity, service, discipline, karma, delays, chronic issues", "à¤¦à¥€à¤°à¥à¤˜à¤¾à¤¯à¥, à¤¸à¥‡à¤µà¤¾, à¤…à¤¨à¥à¤¶à¤¾à¤¸à¤¨, à¤•à¤°à¥à¤®, à¤¢à¤¿à¤²à¤¾à¤‡, à¤¦à¥€à¤°à¥à¤˜à¤•à¤¾à¤²à¥€à¤¨ à¤¸à¤®à¤¸à¥à¤¯à¤¾"),
    PLANET_LIFE_AREA_RAHU("foreign connections, unconventional success, material desires", "à¤µà¤¿à¤¦à¥‡à¤¶à¥€ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§, à¤…à¤ªà¤°à¤®à¥à¤ªà¤°à¤¾à¤—à¤¤ à¤¸à¤«à¤²à¤¤à¤¾, à¤­à¥Œà¤¤à¤¿à¤• à¤‡à¤šà¥à¤›à¤¾"),
    PLANET_LIFE_AREA_KETU("spirituality, liberation, past karma, psychic abilities", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤•à¤¤à¤¾, à¤®à¥‹à¤•à¥à¤·, à¤ªà¥‚à¤°à¥à¤µ à¤•à¤°à¥à¤®, à¤®à¤¾à¤¨à¤¸à¤¿à¤• à¤•à¥à¤·à¤®à¤¤à¤¾"),

    // ============================================
    // PLANETARY WEEKDAYS
    // ============================================
    PLANET_DAY_SUN("Sunday", "à¤†à¤‡à¤¤à¤¬à¤¾à¤°"),
    PLANET_DAY_MOON("Monday", "à¤¸à¥‹à¤®à¤¬à¤¾à¤°"),
    PLANET_DAY_MARS("Tuesday", "à¤®à¤‚à¤—à¤²à¤¬à¤¾à¤°"),
    PLANET_DAY_MERCURY("Wednesday", "à¤¬à¥à¤§à¤¬à¤¾à¤°"),
    PLANET_DAY_JUPITER("Thursday", "à¤¬à¤¿à¤¹à¤¿à¤¬à¤¾à¤°"),
    PLANET_DAY_VENUS("Friday", "à¤¶à¥à¤•à¥à¤°à¤¬à¤¾à¤°"),
    PLANET_DAY_SATURN("Saturday", "à¤¶à¤¨à¤¿à¤¬à¤¾à¤°"),
    PLANET_DAY_RAHU("Saturday", "à¤¶à¤¨à¤¿à¤¬à¤¾à¤°"),
    PLANET_DAY_KETU("Tuesday", "à¤®à¤‚à¤—à¤²à¤¬à¤¾à¤°"),

    // ============================================
    // SYNASTRY / CHART COMPARISON
    // ============================================
    SYNASTRY_TITLE("Chart Comparison", "à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤¤à¥à¤²à¤¨à¤¾"),
    SYNASTRY_SUBTITLE("Synastry Analysis", "à¤¸à¤¿à¤¨à¥‡à¤¸à¥à¤Ÿà¥à¤°à¥€ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SYNASTRY_SELECT_CHARTS("Select Charts to Compare", "à¤¤à¥à¤²à¤¨à¤¾ à¤—à¤°à¥à¤¨ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤¹à¤°à¥‚ à¤›à¤¾à¤¨à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    SYNASTRY_CHART_1("Chart 1", "à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¥§"),
    SYNASTRY_CHART_2("Chart 2", "à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¥¨"),
    SYNASTRY_OVERVIEW("Overview", "à¤…à¤µà¤²à¥‹à¤•à¤¨"),
    SYNASTRY_ASPECTS("Inter-Aspects", "à¤…à¤¨à¥à¤¤à¤°-à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚"),
    SYNASTRY_HOUSES("House Overlays", "à¤­à¤¾à¤µ à¤“à¤­à¤°à¤²à¥‡"),
    SYNASTRY_COMPATIBILITY("Compatibility", "à¤…à¤¨à¥à¤•à¥‚à¤²à¤¤à¤¾"),
    SYNASTRY_NO_ASPECTS("No significant aspects found", "à¤•à¥à¤¨à¥ˆ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿ à¤«à¥‡à¤²à¤¾ à¤ªà¤°à¥‡à¤¨"),
    SYNASTRY_OVERALL_SCORE("Overall Compatibility Score", "à¤¸à¤®à¤—à¥à¤° à¤…à¤¨à¥à¤•à¥‚à¤²à¤¤à¤¾ à¤¸à¥à¤•à¥‹à¤°"),
    SYNASTRY_HARMONIOUS("Harmonious Aspects", "à¤¸à¤¾à¤®à¤žà¥à¤œà¤¸à¥à¤¯à¤ªà¥‚à¤°à¥à¤£ à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚"),
    SYNASTRY_CHALLENGING("Challenging Aspects", "à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£ à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚"),
    SYNASTRY_PLANET_CONNECTIONS("Planetary Connections", "à¤—à¥à¤°à¤¹ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¹à¤°à¥‚"),
    SYNASTRY_HOUSE_INFLUENCE("House Influences", "à¤­à¤¾à¤µ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    SYNASTRY_PLANET_IN_HOUSE("%s in %s's House %d", "%s à¤•à¥‹ à¤­à¤¾à¤µ %d à¤®à¤¾ %s"),
    SYNASTRY_ASPECT_ORB("Orb: %.1fÂ°", "à¤“à¤°à¥à¤¬: %.1fÂ°"),
    SYNASTRY_APPLYING("Applying", "à¤¨à¤¿à¤•à¤Ÿ à¤†à¤‰à¤à¤¦à¥ˆ"),
    SYNASTRY_SEPARATING("Separating", "à¤Ÿà¤¾à¤¢à¤¾ à¤œà¤¾à¤à¤¦à¥ˆ"),
    SYNASTRY_CONJUNCTION("Conjunction", "à¤¯à¥à¤¤à¤¿"),
    SYNASTRY_OPPOSITION("Opposition", "à¤ªà¥à¤°à¤¤à¤¿à¤ªà¤•à¥à¤·"),
    SYNASTRY_TRINE("Trine", "à¤¤à¥à¤°à¤¿à¤•à¥‹à¤£"),
    SYNASTRY_SQUARE("Square", "à¤šà¤¤à¥à¤°à¥à¤¥à¤¾à¤‚à¤¶"),
    SYNASTRY_SEXTILE("Sextile", "à¤·à¤¡à¥à¤­à¤¾à¤—"),
    SYNASTRY_STRONG("Strong", "à¤¬à¤²à¤¿à¤¯à¥‹"),
    SYNASTRY_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    SYNASTRY_WEAK("Weak", "à¤•à¤®à¤œà¥‹à¤°"),
    SYNASTRY_KEY_ASPECTS("Key Synastry Aspects", "à¤®à¥à¤–à¥à¤¯ à¤¸à¤¿à¤¨à¥‡à¤¸à¥à¤Ÿà¥à¤°à¥€ à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚"),
    SYNASTRY_EMOTIONAL_BOND("Emotional Bond", "à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤¬à¤¨à¥à¤§à¤¨"),
    SYNASTRY_COMMUNICATION("Communication", "à¤¸à¤žà¥à¤šà¤¾à¤°"),
    SYNASTRY_ROMANCE("Romance & Attraction", "à¤°à¥‹à¤®à¤¾à¤¨à¥à¤¸ à¤° à¤†à¤•à¤°à¥à¤·à¤£"),
    SYNASTRY_STABILITY("Long-term Stability", "à¤¦à¥€à¤°à¥à¤˜à¤•à¤¾à¤²à¥€à¤¨ à¤¸à¥à¤¥à¤¿à¤°à¤¤à¤¾"),
    SYNASTRY_GROWTH("Growth & Evolution", "à¤µà¥ƒà¤¦à¥à¤§à¤¿ à¤° à¤µà¤¿à¤•à¤¾à¤¸"),
    SYNASTRY_INFO_TITLE("About Synastry", "à¤¸à¤¿à¤¨à¥‡à¤¸à¥à¤Ÿà¥à¤°à¥€à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    SYNASTRY_INFO_DESC("Synastry compares two birth charts to analyze relationship dynamics, compatibility, and areas of harmony or challenge between individuals.", "à¤¸à¤¿à¤¨à¥‡à¤¸à¥à¤Ÿà¥à¤°à¥€à¤²à¥‡ à¤¦à¥à¤ˆ à¤œà¤¨à¥à¤® à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤¹à¤°à¥‚ à¤¤à¥à¤²à¤¨à¤¾ à¤—à¤°à¥‡à¤° à¤µà¥à¤¯à¤•à¥à¤¤à¤¿à¤¹à¤°à¥‚ à¤¬à¥€à¤šà¤•à¥‹ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤—à¤¤à¤¿à¤¶à¥€à¤²à¤¤à¤¾, à¤…à¤¨à¥à¤•à¥‚à¤²à¤¤à¤¾, à¤° à¤¸à¤¾à¤®à¤žà¥à¤œà¤¸à¥à¤¯ à¤µà¤¾ à¤šà¥à¤¨à¥Œà¤¤à¥€à¤•à¤¾ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    SYNASTRY_SWAP("Swap Charts", "à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤¹à¤°à¥‚ à¤¸à¥à¤µà¤¾à¤ª à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    SYNASTRY_CLEAR("Clear Selection", "à¤›à¤¨à¥Œà¤Ÿ à¤–à¤¾à¤²à¥€ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    SYNASTRY_CALCULATE("Analyze Synastry", "à¤¸à¤¿à¤¨à¥‡à¤¸à¥à¤Ÿà¥à¤°à¥€ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    SYNASTRY_ANALYZING("Analyzing synastry...", "à¤¸à¤¿à¤¨à¥‡à¤¸à¥à¤Ÿà¥à¤°à¥€ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤°à¥à¤¦à¥ˆ..."),
    SYNASTRY_SUN_MOON("Sun-Moon Aspects", "à¤¸à¥‚à¤°à¥à¤¯-à¤šà¤¨à¥à¤¦à¥à¤° à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚"),
    SYNASTRY_VENUS_MARS("Venus-Mars Aspects", "à¤¶à¥à¤•à¥à¤°-à¤®à¤‚à¤—à¤² à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚"),
    SYNASTRY_ASCENDANT("Ascendant Connections", "à¤²à¤—à¥à¤¨ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¹à¤°à¥‚"),
    SYNASTRY_MUTUAL_ASPECTS("Mutual Aspects", "à¤ªà¤¾à¤°à¤¸à¥à¤ªà¤°à¤¿à¤• à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚"),
    SYNASTRY_SELECT_BOTH("Please select both charts to compare", "à¤•à¥ƒà¤ªà¤¯à¤¾ à¤¤à¥à¤²à¤¨à¤¾ à¤—à¤°à¥à¤¨ à¤¦à¥à¤µà¥ˆ à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤›à¤¾à¤¨à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    SYNASTRY_QUINCUNX("Quincunx", "à¤•à¥à¤µà¤¿à¤¨à¤•à¤¨à¤•à¥à¤¸"),
    SYNASTRY_SEMI_SEXTILE("Semi-Sextile", "à¤…à¤°à¥à¤§-à¤·à¤¡à¥à¤­à¤¾à¤—"),
    SYNASTRY_INTERPRET_HARMONIOUS("%1$s and %2$s work together harmoniously, creating mutual understanding and support.", "%1$s à¤° %2$s à¤¸à¤¾à¤®à¤žà¥à¤œà¤¸à¥à¤¯à¤ªà¥‚à¤°à¥à¤£ à¤°à¥‚à¤ªà¤®à¤¾ à¤¸à¤ à¤—à¥ˆ à¤•à¤¾à¤® à¤—à¤°à¥à¤›à¤¨à¥, à¤œà¤¸à¤²à¥‡ à¤†à¤ªà¤¸à¥€ à¤¸à¤®à¤ à¤¦à¤¾à¤°à¥€ à¤° à¤¸à¤®à¤°à¥à¤¥à¤¨ à¤¸à¤¿à¤°à¥à¤œà¤¨à¤¾ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    SYNASTRY_INTERPRET_CHALLENGING("%1$s and %2$s create tension that requires conscious effort to integrate.", "%1$s à¤° %2$s à¤²à¥‡ à¤¤à¤¨à¤¾à¤µ à¤¸à¤¿à¤°à¥à¤œà¤¨à¤¾ à¤—à¤°à¥à¤¦à¤›à¤¨à¥ à¤œà¤¸à¤²à¤¾à¤ˆ à¤ à¤•¥€à¤•à¥ƒà¤¤ à¤—à¤°à¥à¤¨ à¤¸à¤šà¥‡à¤¤ à¤ªà¥à¤°à¤¯à¤¾à¤¸à¤•à¥‹ à¤†à¤µà¤¶à¥à¤¯à¤• à¤ªà¤°à¥à¤¦à¤›à¥¤"),
    SYNASTRY_INTERPRET_MAJOR("%1$s and %2$s are closely connected, amplifying each other's energies.", "%1$s à¤° %2$s à¤¨à¤œà¤¿à¤•à¤¬à¤¾à¤Ÿ à¤œà¥‹à¤¡à¤¿à¤ à¤•à¤¾ à¤›à¤¨à¥, à¤ à¤• à¤…à¤°à¥à¤•à¤¾à¤•à¥‹ à¤Šà¤°à¥à¤œà¤¾à¤²à¤¾à¤ˆ à¤¬à¤¢à¤¾à¤‰à¤à¤¦à¥ˆà¥¤"),
    SYNASTRY_INTERPRET_MINOR("%1$s and %2$s have a subtle connection that adds nuance to the relationship.", "%1$s à¤° %2$s à¤¬à¥€à¤š à¤ à¤• à¤¸à¥‚à¤•à¥à¤·à¥à¤® à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤› à¤œà¤¸à¤²à¥‡ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤®à¤¾ à¤¨à¤¯à¤¾à¤ à¤ªà¤•à¥à¤· à¤¥à¤ªà¥à¤¦à¤›à¥¤"),
    SYNASTRY_INTERPRET_ASCENDANT("%1$s conjunct Person %2$d's Ascendant creates a strong personal connection.", "%1$s à¤µà¥à¤¯à¤•à¥à¤¤à¤¿ %2$d à¤•à¥‹ à¤²à¤—à¥à¤¨à¤®à¤¾ à¤¹à¥à¤¨à¥à¤²à¥‡ à¤ à¤• à¤¬à¤²à¤¿à¤¯à¥‹ à¤µà¥à¤¯à¤•à¥à¤¤à¤¿à¤—à¤¤ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤¸à¤¿à¤°à¥à¤œà¤¨à¤¾ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    SYNASTRY_INTERPRET_OVERLAY("Person %1$d's %2$s falls in the %3$dth house, influencing %4$s.", "à¤µà¥à¤¯à¤•à¥à¤¤à¤¿ %1$d à¤•à¥‹ %2$s %3$d à¤­à¤¾à¤µà¤®à¤¾ à¤ªà¤°à¥à¤¦à¤›, à¤œà¤¸à¤²à¥‡ %4$s à¤²à¤¾à¤ˆ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¿à¤¤ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    SYNASTRY_DESC_EMOTIONAL("Emotional understanding and nurturing", "à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤¸à¤®à¤ à¤¦à¤¾à¤°à¥€ à¤° à¤ªà¥‹à¤·à¤£"),
    SYNASTRY_DESC_ROMANCE("Physical attraction and passion", "à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤†à¤•à¤°à¥à¤·à¤£ à¤° à¤œà¥à¤¨à¥‚à¤¨"),
    SYNASTRY_DESC_COMMUNICATION("Mental connection and dialogue", "à¤®à¤¾à¤¨à¤¸à¤¿à¤• à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤° à¤¸à¤‚à¤µà¤¾à¤¦"),
    SYNASTRY_DESC_STABILITY("Commitment and endurance", "à¤ªà¥à¤°à¤¤à¤¿à¤¬à¤¦à¥à¤§à¤¤à¤¾ à¤° à¤§à¥ˆà¤°à¥à¤¯à¤¤à¤¾"),
    SYNASTRY_DESC_GROWTH("Mutual expansion and learning", "à¤†à¤ªà¤¸à¥€ à¤µà¤¿à¤¸à¥à¤¤à¤¾à¤° à¤° à¤¸à¤¿à¤•à¤¾à¤‡"),
    SYNASTRY_FINDING_ASPECT("Strong %1$s between %2$s and %3$s", "%2$s à¤° %3$s à¤¬à¥€à¤š à¤¬à¤²à¤¿à¤¯à¥‹ %1$s"),
    SYNASTRY_FINDING_HOUSE("%1$s activates the %2$dth house of %3$s", "%1$s à¤²à¥‡ %3$s à¤•à¥‹ %2$d à¤­à¤¾à¤µà¤²à¤¾à¤ˆ à¤¸à¤•à¥ à¤°à¤¿à¤¯ à¤—à¤°à¥à¤¦à¤›"),
    SYNASTRY_CALC_FAILED("Calculation failed", "à¤—à¤£à¤¨à¤¾ à¤…à¤¸à¤«à¤² à¤­à¤¯à¥‹"),
    SYNASTRY_AVAILABLE_CHARTS("%d charts available", "%d à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤¹à¤°à¥‚ à¤‰à¤ªà¤²à¤¬à¥à¤§ à¤›à¤¨à¥"),
    SYNASTRY_LIFE_AREA_GENERAL("General", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯"),

    // ============================================
    // NAKSHATRA ANALYSIS
    // ============================================
    NAKSHATRA_TITLE("Nakshatra Analysis", "à¤¨à¤•à¥à¤·à¤¤à¥à¤° à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    NAKSHATRA_SUBTITLE("Lunar Mansion Analysis", "à¤šà¤¨à¥à¤¦à¥à¤° à¤­à¤µà¤¨ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    NAKSHATRA_OVERVIEW("Overview", "à¤…à¤µà¤²à¥‹à¤•à¤¨"),
    NAKSHATRA_DETAILS("Details", "à¤µà¤¿à¤µà¤°à¤£à¤¹à¤°à¥‚"),
    NAKSHATRA_COMPATIBILITY("Compatibility", "à¤…à¤¨à¥à¤•à¥‚à¤²à¤¤à¤¾"),
    NAKSHATRA_REMEDIES("Remedies", "à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    NAKSHATRA_BIRTH_STAR("Birth Nakshatra", "à¤œà¤¨à¥à¤® à¤¨à¤•à¥à¤·à¤¤à¥à¤°"),
    NAKSHATRA_MOON_POSITION("Moon Nakshatra", "à¤šà¤¨à¥à¤¦à¥à¤° à¤¨à¤•à¥à¤·à¤¤à¥à¤°"),
    NAKSHATRA_RULER("Ruling Planet", "à¤¸à¥à¤µà¤¾à¤®à¥€ à¤—à¥à¤°à¤¹"),
    NAKSHATRA_ELEMENT("Element", "à¤¤à¤¤à¥à¤µ"),
    NAKSHATRA_QUALITY("Quality", "à¤—à¥à¤£"),
    NAKSHATRA_CASTE("Caste", "à¤µà¤°à¥à¤£"),
    NAKSHATRA_DIRECTION("Direction", "à¤¦à¤¿à¤¶à¤¾"),
    NAKSHATRA_BODY_PART("Body Part", "à¤¶à¤°à¥€à¤°à¤•à¥‹ à¤…à¤‚à¤—"),
    NAKSHATRA_DOSHA("Dosha", "à¤¦à¥‹à¤·"),
    NAKSHATRA_FAVORABLE_DAYS("Favorable Days", "à¤…à¤¨à¥à¤•à¥‚à¤² à¤¦à¤¿à¤¨à¤¹à¤°à¥‚"),
    NAKSHATRA_LUCKY_NUMBERS("Lucky Numbers", "à¤­à¤¾à¤—à¥à¤¯à¤¶à¤¾à¤²à¥€ à¤…à¤‚à¤•à¤¹à¤°à¥‚"),
    NAKSHATRA_LUCKY_COLORS("Lucky Colors", "à¤­à¤¾à¤—à¥à¤¯à¤¶à¤¾à¤²à¥€ à¤°à¤‚à¤—à¤¹à¤°à¥‚"),
    NAKSHATRA_LUCKY_STONES("Lucky Gemstones", "à¤­à¤¾à¤—à¥à¤¯à¤¶à¤¾à¤²à¥€ à¤°à¤¤à¥à¤¨à¤¹à¤°à¥‚"),
    NAKSHATRA_CHARACTERISTICS("Characteristics", "à¤µà¤¿à¤¶à¥‡à¤·à¤¤à¤¾à¤¹à¤°à¥‚"),
    NAKSHATRA_STRENGTHS("Strengths", "à¤¶à¤•à¥à¤¤à¤¿à¤¹à¤°à¥‚"),
    NAKSHATRA_WEAKNESSES("Weaknesses", "à¤•à¤®à¤œà¥‹à¤°à¥€à¤¹à¤°à¥‚"),
    NAKSHATRA_CAREER("Career Aptitude", "à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤° à¤¯à¥‹à¤—à¥à¤¯à¤¤à¤¾"),
    NAKSHATRA_HEALTH("Health Tendencies", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤ªà¥à¤°à¤µà¥ƒà¤¤à¥à¤¤à¤¿à¤¹à¤°à¥‚"),
    NAKSHATRA_RELATIONSHIP("Relationship Style", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤¶à¥ˆà¤²à¥€"),
    NAKSHATRA_SPIRITUAL("Spiritual Path", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤®à¤¾à¤°à¥à¤—"),
    NAKSHATRA_MANTRA("Nakshatra Mantra", "à¤¨à¤•à¥à¤·à¤¤à¥à¤° à¤®à¤¨à¥à¤¤à¥à¤°"),
    NAKSHATRA_INFO_TITLE("About Nakshatras", "à¤¨à¤•à¥à¤·à¤¤à¥à¤°à¤¹à¤°à¥‚à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    NAKSHATRA_INFO_DESC("Nakshatras are the 27 lunar mansions in Vedic astrology, each spanning 13Â°20' of the zodiac. They reveal deeper psychological patterns and spiritual tendencies.", "à¤¨à¤•à¥à¤·à¤¤à¥à¤°à¤¹à¤°à¥‚ à¤µà¥ˆà¤¦à¤¿à¤• à¤œà¥à¤¯à¥‹à¤¤à¤¿à¤·à¤®à¤¾ à¥¨à¥­ à¤šà¤¨à¥à¤¦à¥à¤° à¤­à¤µà¤¨à¤¹à¤°à¥‚ à¤¹à¥à¤¨à¥, à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤°à¤¾à¤¶à¤¿à¤šà¤•à¥à¤°à¤•à¥‹ à¥§à¥©Â°à¥¨à¥¦' à¤«à¥ˆà¤²à¤¿à¤à¤•à¥‹à¥¤ à¤¤à¤¿à¤¨à¥€à¤¹à¤°à¥‚à¤²à¥‡ à¤—à¤¹à¤¿à¤°à¥‹ à¤®à¤¨à¥‹à¤µà¥ˆà¤œà¥à¤žà¤¾à¤¨à¤¿à¤• à¤¢à¤¾à¤à¤šà¤¾ à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤ªà¥à¤°à¤µà¥ƒà¤¤à¥à¤¤à¤¿à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤•à¤Ÿ à¤—à¤°à¥à¤›à¤¨à¥à¥¤"),
    NAKSHATRA_ALL_PLANETS("Planetary Nakshatras", "à¤—à¥à¤°à¤¹ à¤¨à¤•à¥à¤·à¤¤à¥à¤°à¤¹à¤°à¥‚"),
    NAKSHATRA_DASHA_LORD("Dasha Lord", "à¤¦à¤¶à¤¾ à¤¸à¥à¤µà¤¾à¤®à¥€"),
    NAKSHATRA_SPAN("Nakshatra Span", "à¤¨à¤•à¥à¤·à¤¤à¥à¤° à¤µà¤¿à¤¸à¥à¤¤à¤¾à¤°"),
    NAKSHATRA_DEGREE_IN("Degree in Nakshatra", "à¤¨à¤•à¥à¤·à¤¤à¥à¤°à¤®à¤¾ à¤…à¤‚à¤¶"),
    NAKSHATRA_TARABALA("Tarabala Analysis", "à¤¤à¤¾à¤°à¤¾à¤¬à¤² à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    NAKSHATRA_CHANDRABALA("Chandrabala", "à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤²"),
    NAKSHATRA_COMPATIBLE_WITH("Compatible with", "à¤…à¤¨à¥à¤•à¥‚à¤²"),
    NAKSHATRA_INCOMPATIBLE_WITH("Incompatible with", "à¤…à¤¨à¥à¤•à¥‚à¤² à¤›à¥ˆà¤¨"),
    NAKSHATRA_VEDHA_PAIRS("Vedha Pairs", "à¤µà¥‡à¤§ à¤œà¥‹à¤¡à¥€à¤¹à¤°à¥‚"),
    NAKSHATRA_RAJJU_TYPE("Rajju Type", "à¤°à¤œà¥à¤œà¥ à¤ªà¥à¤°à¤•à¤¾à¤°"),

    // ============================================
    // SHADBALA ANALYSIS
    // ============================================
    SHADBALA_TITLE("Shadbala", "à¤·à¤¡à¥à¤¬à¤²"),
    SHADBALA_SUBTITLE("Six-fold Planetary Strength", "à¤›à¤µà¤Ÿà¤¾ à¤—à¥à¤°à¤¹ à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_OVERVIEW("Overview", "à¤…à¤µà¤²à¥‹à¤•à¤¨"),
    SHADBALA_DETAILS("Detailed Analysis", "à¤µà¤¿à¤¸à¥à¤¤à¥ƒà¤¤ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SHADBALA_COMPARISON("Comparison", "à¤¤à¥à¤²à¤¨à¤¾"),
    SHADBALA_TOTAL_STRENGTH("Total Strength", "à¤•à¥à¤² à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_RUPAS("Rupas", "à¤°à¥‚à¤ªà¤¾"),
    SHADBALA_VIRUPAS("Virupas", "à¤µà¤¿à¤°à¥‚à¤ªà¤¾"),
    SHADBALA_REQUIRED("Required", "à¤†à¤µà¤¶à¥à¤¯à¤•"),
    SHADBALA_PERCENTAGE("Percentage", "à¤ªà¥à¤°à¤¤à¤¿à¤¶à¤¤"),
    SHADBALA_RATING("Rating", "à¤®à¥‚à¤²à¥à¤¯à¤¾à¤‚à¤•à¤¨"),
    SHADBALA_STRONGEST_PLANET("Strongest Planet", "à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤¬à¤²à¤¿à¤¯à¥‹ à¤—à¥à¤°à¤¹"),
    SHADBALA_WEAKEST_PLANET("Weakest Planet", "à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤•à¤®à¤œà¥‹à¤° à¤—à¥à¤°à¤¹"),
    SHADBALA_OVERALL_STRENGTH("Overall Chart Strength", "à¤¸à¤®à¤—à¥à¤° à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_STRONG_COUNT("%d planets above required", "%d à¤—à¥à¤°à¤¹ à¤†à¤µà¤¶à¥à¤¯à¤•à¤¤à¤¾à¤­à¤¨à¥à¤¦à¤¾ à¤®à¤¾à¤¥à¤¿"),
    SHADBALA_WEAK_COUNT("%d planets below required", "%d à¤—à¥à¤°à¤¹ à¤†à¤µà¤¶à¥à¤¯à¤•à¤¤à¤¾à¤­à¤¨à¥à¤¦à¤¾ à¤¤à¤²"),
    SHADBALA_STHANA_BALA("Sthana Bala", "à¤¸à¥à¤¥à¤¾à¤¨ à¤¬à¤²"),
    SHADBALA_STHANA_BALA_DESC("Positional Strength", "à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤—à¤¤ à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_DIG_BALA("Dig Bala", "à¤¦à¤¿à¤—à¥ à¤¬à¤²"),
    SHADBALA_DIG_BALA_DESC("Directional Strength", "à¤¦à¤¿à¤¶à¤¾à¤—à¤¤ à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_KALA_BALA("Kala Bala", "à¤•à¤¾à¤² à¤¬à¤²"),
    SHADBALA_KALA_BALA_DESC("Temporal Strength", "à¤¸à¤®à¤¯à¤—à¤¤ à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_CHESTA_BALA("Chesta Bala", "à¤šà¥‡à¤·à¥à¤Ÿà¤¾ à¤¬à¤²"),
    SHADBALA_CHESTA_BALA_DESC("Motional Strength", "à¤—à¤¤à¤¿à¤¶à¥€à¤² à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_NAISARGIKA_BALA("Naisargika Bala", "à¤¨à¥ˆà¤¸à¤°à¥à¤—à¤¿à¤• à¤¬à¤²"),
    SHADBALA_NAISARGIKA_BALA_DESC("Natural Strength", "à¤ªà¥à¤°à¤¾à¤•à¥ƒà¤¤à¤¿à¤• à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_DRIK_BALA("Drik Bala", "à¤¦à¥ƒà¤•à¥ à¤¬à¤²"),
    SHADBALA_DRIK_BALA_DESC("Aspectual Strength", "à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤—à¤¤ à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_UCCHA_BALA("Uccha Bala", "à¤‰à¤šà¥à¤š à¤¬à¤²"),
    SHADBALA_SAPTAVARGAJA_BALA("Saptavargaja Bala", "à¤¸à¤ªà¥à¤¤à¤µà¤°à¥à¤—à¤œ à¤¬à¤²"),
    SHADBALA_OJHAYUGMA_BALA("Ojhayugma Bala", "à¤“à¤à¤¾à¤¯à¥à¤—à¥à¤® à¤¬à¤²"),
    SHADBALA_KENDRADI_BALA("Kendradi Bala", "à¤•à¥‡à¤¨à¥à¤¦à¥à¤°à¤¾à¤¦à¤¿ à¤¬à¤²"),
    SHADBALA_DREKKANA_BALA("Drekkana Bala", "à¤¦à¥à¤°à¥‡à¤•à¥à¤•à¤¾à¤£ à¤¬à¤²"),
    SHADBALA_NATHONNATHA_BALA("Nathonnatha Bala", "à¤¨à¤¥à¥‹à¤¨à¥à¤¨à¤¥ à¤¬à¤²"),
    SHADBALA_PAKSHA_BALA("Paksha Bala", "à¤ªà¤•à¥à¤· à¤¬à¤²"),
    SHADBALA_TRIBHAGA_BALA("Tribhaga Bala", "à¤¤à¥à¤°à¤¿à¤­à¤¾à¤— à¤¬à¤²"),
    SHADBALA_HORA_BALA("Hora/Dina/Masa/Varsha", "à¤¹à¥‹à¤°à¤¾/à¤¦à¤¿à¤¨/à¤®à¤¾à¤¸/à¤µà¤°à¥à¤·"),
    SHADBALA_AYANA_BALA("Ayana Bala", "à¤…à¤¯à¤¨ à¤¬à¤²"),
    SHADBALA_YUDDHA_BALA("Yuddha Bala", "à¤¯à¥à¤¦à¥à¤§ à¤¬à¤²"),
    SHADBALA_INFO_TITLE("About Shadbala", "à¤·à¤¡à¥à¤¬à¤²à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    SHADBALA_INFO_DESC("Shadbala is the six-fold strength calculation system in Vedic astrology that determines a planet's capacity to deliver its significations. Each planet needs to meet a minimum threshold to be considered functionally strong.", "à¤·à¤¡à¥à¤¬à¤² à¤µà¥ˆà¤¦à¤¿à¤• à¤œà¥à¤¯à¥‹à¤¤à¤¿à¤·à¤®à¤¾ à¤›à¤µà¤Ÿà¤¾ à¤¶à¤•à¥à¤¤à¤¿ à¤—à¤£à¤¨à¤¾ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤¹à¥‹ à¤œà¤¸à¤²à¥‡ à¤—à¥à¤°à¤¹à¤•à¥‹ à¤†à¤«à¥à¤¨à¥‹ à¤¸à¤‚à¤•à¥‡à¤¤à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥‡ à¤•à¥à¤·à¤®à¤¤à¤¾ à¤¨à¤¿à¤°à¥à¤§à¤¾à¤°à¤£ à¤—à¤°à¥à¤¦à¤›à¥¤ à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤—à¥à¤°à¤¹à¤²à¤¾à¤ˆ à¤•à¤¾à¤°à¥à¤¯à¤¾à¤¤à¥à¤®à¤• à¤°à¥‚à¤ªà¤®à¤¾ à¤¬à¤²à¤¿à¤¯à¥‹ à¤®à¤¾à¤¨à¤¿à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¨à¥à¤¯à¥‚à¤¨à¤¤à¤® à¤¸à¥€à¤®à¤¾ à¤ªà¥‚à¤°à¤¾ à¤—à¤°à¥à¤¨à¥à¤ªà¤°à¥à¤›à¥¤"),
    SHADBALA_INFO_INTRO("The six sources of strength:", "à¤¶à¤•à¥à¤¤à¤¿à¤•à¤¾ à¤›à¤µà¤Ÿà¤¾ à¤¸à¥à¤°à¥‹à¤¤à¤¹à¤°à¥‚:"),
    SHADBALA_INFO_ITEM_1("1. Sthana Bala - Positional strength based on exaltation, own sign, etc.", "à¥§. à¤¸à¥à¤¥à¤¾à¤¨ à¤¬à¤² - à¤‰à¤šà¥à¤šà¤¤à¤¾, à¤¸à¥à¤µà¤°à¤¾à¤¶à¤¿, à¤†à¤¦à¤¿à¤®à¤¾ à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤—à¤¤ à¤¶à¤•à¥à¤¤à¤¿à¥¤"),
    SHADBALA_INFO_ITEM_2("2. Dig Bala - Directional strength based on house placement", "à¥¨. à¤¦à¤¿à¤—à¥ à¤¬à¤² - à¤­à¤¾à¤µ à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤®à¤¾ à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤¦à¤¿à¤¶à¤¾à¤—à¤¤ à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_INFO_ITEM_3("3. Kala Bala - Temporal strength from time of birth", "à¥©. à¤•à¤¾à¤² à¤¬à¤² - à¤œà¤¨à¥à¤® à¤¸à¤®à¤¯à¤¬à¤¾à¤Ÿ à¤¸à¤®à¤¯à¤—à¤¤ à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_INFO_ITEM_4("4. Chesta Bala - Motional strength from planetary motion", "à¥ª. à¤šà¥‡à¤·à¥à¤Ÿà¤¾ à¤¬à¤² - à¤—à¥à¤°à¤¹ à¤—à¤¤à¤¿à¤¬à¤¾à¤Ÿ à¤—à¤¤à¤¿à¤¶à¥€à¤² à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_INFO_ITEM_5("5. Naisargika Bala - Natural inherent strength", "à¥«. à¤¨à¥ˆà¤¸à¤°à¥à¤—à¤¿à¤• à¤¬à¤² - à¤ªà¥à¤°à¤¾à¤•à¥ƒà¤¤à¤¿à¤• à¤…à¤¨à¥à¤¤à¤°à¥à¤¨à¤¿à¤¹à¤¿à¤¤ à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_INFO_ITEM_6("6. Drik Bala - Strength from aspects received", "à¥¬. à¤¦à¥ƒà¤•à¥ à¤¬à¤² - à¤ªà¥à¤°à¤¾à¤ªà¥à¤¤ à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚à¤¬à¤¾à¤Ÿ à¤¶à¤•à¥à¤¤à¤¿"),
    SHADBALA_INTERPRETATION("Interpretation", "à¤µà¥à¤¯à¤¾à¤–à¥à¤¯à¤¾"),
    SHADBALA_PLANET_ANALYSIS("%s Analysis", "%s à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SHADBALA_MEETS_REQUIREMENT("Meets required strength", "à¤†à¤µà¤¶à¥à¤¯à¤• à¤¶à¤•à¥à¤¤à¤¿ à¤ªà¥‚à¤°à¤¾ à¤—à¤°à¥à¤¦à¤›"),
    SHADBALA_BELOW_REQUIREMENT("Below required strength", "à¤†à¤µà¤¶à¥à¤¯à¤• à¤¶à¤•à¥à¤¤à¤¿à¤­à¤¨à¥à¤¦à¤¾ à¤¤à¤²"),
    SHADBALA_BREAKDOWN("Strength Breakdown", "à¤¶à¤•à¥à¤¤à¤¿ à¤µà¤¿à¤µà¤°à¤£"),
    SHADBALA_CALCULATING("Calculating Shadbala...", "à¤·à¤¡à¥à¤¬à¤² à¤—à¤£à¤¨à¤¾ à¤—à¤°à¥à¤¦à¥ˆ..."),
    SHADBALA_CHART_ANALYSIS("Chart Strength Analysis", "à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤¶à¤•à¥à¤¤à¤¿ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SHADBALA_CALCULATION_ERROR("Failed to calculate Shadbala", "à¤·à¤¡à¤¬à¤² à¤—à¤£à¤¨à¤¾ à¤—à¤°à¥à¤¨ à¤…à¤¸à¤«à¤² à¤­à¤¯à¥‹"),

    // ============================================
    // SADE SATI ANALYSIS
    // ============================================
    SADE_SATI_TITLE("Sade Sati Analysis", "à¤¸à¤¾à¤¢à¥‡à¤¸à¤¾à¤¤à¥€ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SADE_SATI_SUBTITLE("Saturn's 7.5 Year Transit", "à¤¶à¤¨à¤¿à¤•à¥‹ à¥­.à¥« à¤µà¤°à¥à¤·à¥‡ à¤—à¥‹à¤šà¤°"),
    SADE_SATI_ACTIVE("Sade Sati Active", "à¤¸à¤¾à¤¢à¥‡à¤¸à¤¾à¤¤à¥€ à¤¸à¤•à¥à¤°à¤¿à¤¯"),
    SADE_SATI_NOT_ACTIVE("Sade Sati is not currently active", "à¤¸à¤¾à¤¢à¥‡à¤¸à¤¾à¤¤à¥€ à¤¹à¤¾à¤² à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤›à¥ˆà¤¨"),
    SADE_SATI_PHASE_RISING("Rising Phase", "à¤‰à¤¦à¤¯ à¤šà¤°à¤£"),
    SADE_SATI_PHASE_PEAK("Peak Phase", "à¤¶à¤¿à¤–à¤° à¤šà¤°à¤£"),
    SADE_SATI_PHASE_SETTING("Setting Phase", "à¤…à¤¸à¥à¤¤ à¤šà¤°à¤£"),
    SADE_SATI_RISING_DESC("Saturn transiting 12th from Moon - Beginning of Sade Sati", "à¤¶à¤¨à¤¿ à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤¾à¤Ÿ à¥§à¥¨à¤”à¤‚ à¤°à¤¾à¤¶à¤¿à¤®à¤¾ à¤—à¥‹à¤šà¤° - à¤¸à¤¾à¤¢à¥‡à¤¸à¤¾à¤¤à¥€à¤•à¥‹ à¤¶à¥à¤°à¥à¤†à¤¤"),
    SADE_SATI_PEAK_DESC("Saturn transiting over natal Moon - Most intense phase", "à¤¶à¤¨à¤¿ à¤œà¤¨à¥à¤® à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤¥à¤¿ à¤—à¥‹à¤šà¤° - à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤¤à¥€à¤µà¥à¤° à¤šà¤°à¤£"),
    SADE_SATI_SETTING_DESC("Saturn transiting 2nd from Moon - Final phase of Sade Sati", "à¤¶à¤¨à¤¿ à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤¾à¤Ÿ à¥¨à¤”à¤‚ à¤°à¤¾à¤¶à¤¿à¤®à¤¾ à¤—à¥‹à¤šà¤° - à¤¸à¤¾à¤¢à¥‡à¤¸à¤¾à¤¤à¥€à¤•à¥‹ à¤…à¤¨à¥à¤¤à¤¿à¤® à¤šà¤°à¤£"),
    SADE_SATI_ACTIVE_SUMMARY("{phase} phase active with {severity} intensity", "{phase} à¤šà¤°à¤£ {severity} à¤¤à¥€à¤µà¥à¤°à¤¤à¤¾à¤•à¤¾ à¤¸à¤¾à¤¥ à¤¸à¤•à¥à¤°à¤¿à¤¯"),
    SMALL_PANOTI_FOURTH("Kantak Shani (4th from Moon)", "à¤•à¤£à¥à¤Ÿà¤• à¤¶à¤¨à¤¿ (à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤¾à¤Ÿ à¥ªà¤”à¤‚)"),
    SMALL_PANOTI_EIGHTH("Ashtama Shani (8th from Moon)", "à¤…à¤·à¥à¤Ÿà¤® à¤¶à¤¨à¤¿ (à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤¾à¤Ÿ à¥®à¤”à¤‚)"),
    SMALL_PANOTI_ACTIVE_SUMMARY("{type} is active", "{type} à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤›"),
    SEVERITY_MILD("Mild", "à¤¹à¤²à¥à¤•à¤¾"),
    SEVERITY_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    SEVERITY_SIGNIFICANT("Significant", "à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£"),
    SEVERITY_INTENSE("Intense", "à¤¤à¥€à¤µà¥à¤°"),
    SADE_SATI_DAYS_REMAINING("Days Remaining", "à¤¬à¤¾à¤à¤•à¥€ à¤¦à¤¿à¤¨à¤¹à¤°à¥‚"),
    SADE_SATI_PROGRESS("Progress in Phase", "à¤šà¤°à¤£à¤®à¤¾ à¤ªà¥à¤°à¤—à¤¤à¤¿"),
    SADE_SATI_MOON_SIGN("Natal Moon Sign", "à¤œà¤¨à¥à¤® à¤šà¤¨à¥à¤¦à¥à¤° à¤°à¤¾à¤¶à¤¿"),
    SADE_SATI_SATURN_SIGN("Transit Saturn Sign", "à¤—à¥‹à¤šà¤° à¤¶à¤¨à¤¿ à¤°à¤¾à¤¶à¤¿"),

    // Sade Sati Remedies
    REMEDY_SHANI_MANTRA_TITLE("Shani Mantra", "à¤¶à¤¨à¤¿ à¤®à¤¨à¥à¤¤à¥à¤°"),
    REMEDY_SHANI_MANTRA_DESC("Recite 'Om Sham Shanaishcharaya Namah' 108 times daily", "'à¥ à¤¶à¤‚ à¤¶à¤¨à¥ˆà¤¶à¥à¤šà¤°à¤¾à¤¯ à¤¨à¤®à¤ƒ' à¤¦à¥ˆà¤¨à¤¿à¤• à¥§à¥¦à¥® à¤ªà¤Ÿà¤• à¤œà¤ª à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    REMEDY_SATURDAY_CHARITY_TITLE("Saturday Charity", "à¤¶à¤¨à¤¿à¤¬à¤¾à¤° à¤¦à¤¾à¤¨"),
    REMEDY_SATURDAY_CHARITY_DESC("Donate black sesame, mustard oil, or iron items to the needy", "à¤•à¤¾à¤²à¥‹ à¤¤à¤¿à¤², à¤¸à¤°à¥à¤¸à¥à¤¯à¥‚à¤•à¥‹ à¤¤à¥‡à¤², à¤µà¤¾ à¤«à¤²à¤¾à¤®à¤•à¤¾ à¤¸à¤¾à¤®à¤¾à¤¨à¤¹à¤°à¥‚ à¤—à¤°à¤¿à¤¬à¤²à¤¾à¤ˆ à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    REMEDY_SATURDAY_FAST_TITLE("Saturday Fasting", "à¤¶à¤¨à¤¿à¤¬à¤¾à¤° à¤µà¥à¤°à¤¤"),
    REMEDY_SATURDAY_FAST_DESC("Observe fast on Saturdays and eat only after sunset", "à¤¶à¤¨à¤¿à¤¬à¤¾à¤° à¤µà¥à¤°à¤¤ à¤°à¤¾à¤–à¥à¤¨à¥à¤¹à¥‹à¤¸à¥ à¤° à¤¸à¥‚à¤°à¥à¤¯à¤¾à¤¸à¥à¤¤ à¤ªà¤›à¤¿ à¤®à¤¾à¤¤à¥à¤° à¤–à¤¾à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    REMEDY_HANUMAN_WORSHIP_TITLE("Hanuman Worship", "à¤¹à¤¨à¥à¤®à¤¾à¤¨ à¤ªà¥‚à¤œà¤¾"),
    REMEDY_HANUMAN_WORSHIP_DESC("Recite Hanuman Chalisa daily, especially on Tuesdays and Saturdays", "à¤¹à¤¨à¥à¤®à¤¾à¤¨ à¤šà¤¾à¤²à¥€à¤¸à¤¾ à¤¦à¥ˆà¤¨à¤¿à¤• à¤ªà¤¾à¤  à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥, à¤µà¤¿à¤¶à¥‡à¤· à¤—à¤°à¥€ à¤®à¤‚à¤—à¤²à¤¬à¤¾à¤° à¤° à¤¶à¤¨à¤¿à¤¬à¤¾à¤°"),
    REMEDY_BLUE_SAPPHIRE_TITLE("Blue Sapphire (Neelam)", "à¤¨à¥€à¤²à¤® à¤°à¤¤à¥à¤¨"),
    REMEDY_BLUE_SAPPHIRE_DESC("Wear after proper testing and astrologer consultation", "à¤‰à¤šà¤¿à¤¤ à¤ªà¤°à¥€à¤•à¥à¤·à¤£ à¤° à¤œà¥à¤¯à¥‹à¤¤à¤¿à¤·à¥€ à¤ªà¤°à¤¾à¤®à¤°à¥à¤¶ à¤ªà¤›à¤¿ à¤²à¤—à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),

    // ============================================
    // MANGLIK DOSHA ANALYSIS
    // ============================================
    MANGLIK_TITLE("Manglik Dosha Analysis", "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    MANGLIK_SUBTITLE("Mars Placement Analysis for Marriage", "à¤µà¤¿à¤µà¤¾à¤¹à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤®à¤‚à¤—à¤² à¤¸à¥à¤¥à¤¿à¤¤à¤¿ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    MANGLIK_NONE_LEVEL("No Manglik Dosha", "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤›à¥ˆà¤¨"),
    MANGLIK_MILD("Mild Manglik", "à¤¹à¤²à¥à¤•à¤¾ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤•"),
    MANGLIK_PARTIAL_LEVEL("Partial Manglik", "à¤†à¤‚à¤¶à¤¿à¤• à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤•"),
    MANGLIK_FULL_LEVEL("Full Manglik", "à¤ªà¥‚à¤°à¥à¤£ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤•"),
    MANGLIK_SEVERE("Severe Manglik", "à¤—à¤®à¥à¤­à¥€à¤° à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤•"),
    MANGLIK_SUMMARY_PRESENT("{level} present with {intensity}% intensity", "{level} {intensity}% à¤¤à¥€à¤µà¥à¤°à¤¤à¤¾à¤•à¤¾ à¤¸à¤¾à¤¥ à¤‰à¤ªà¤¸à¥à¤¥à¤¿à¤¤"),
    MANGLIK_SUMMARY_ABSENT("No Manglik Dosha in this chart", "à¤¯à¤¸ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤›à¥ˆà¤¨"),
    MANGLIK_FROM_LAGNA("From Lagna", "à¤²à¤—à¥à¤¨à¤¬à¤¾à¤Ÿ"),
    MANGLIK_FROM_MOON("From Moon", "à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤¾à¤Ÿ"),
    MANGLIK_FROM_VENUS("From Venus", "à¤¶à¥à¤•à¥à¤°à¤¬à¤¾à¤Ÿ"),
    MANGLIK_CANCELLATIONS("Cancellation Factors", "à¤°à¤¦à¥à¤¦ à¤—à¤°à¥à¤¨à¥‡ à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚"),
    MANGLIK_EFFECTIVE_LEVEL("Effective Level", "à¤ªà¥à¤°à¤­à¤¾à¤µà¥€ à¤¸à¥à¤¤à¤°"),

    // Manglik Cancellation Factors
    MANGLIK_CANCEL_OWN_SIGN_TITLE("Mars in Own Sign", "à¤®à¤‚à¤—à¤² à¤¸à¥à¤µà¤°à¤¾à¤¶à¤¿à¤®à¤¾"),
    MANGLIK_CANCEL_OWN_SIGN_DESC("Mars in Aries or Scorpio reduces Manglik effects", "à¤®à¤‚à¤—à¤² à¤®à¥‡à¤· à¤µà¤¾ à¤µà¥ƒà¤¶à¥à¤šà¤¿à¤•à¤®à¤¾ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤ªà¥à¤°à¤­à¤¾à¤µ à¤•à¤® à¤—à¤°à¥à¤›"),
    MANGLIK_CANCEL_EXALTED_TITLE("Mars Exalted", "à¤®à¤‚à¤—à¤² à¤‰à¤šà¥à¤š"),
    MANGLIK_CANCEL_EXALTED_DESC("Mars in Capricorn cancels Manglik Dosha completely", "à¤®à¤‚à¤—à¤² à¤®à¤•à¤°à¤®à¤¾ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤ªà¥‚à¤°à¥à¤£ à¤°à¥‚à¤ªà¤®à¤¾ à¤°à¤¦à¥à¤¦ à¤—à¤°à¥à¤›"),
    MANGLIK_CANCEL_JUPITER_CONJUNCT_TITLE("Jupiter Conjunction", "à¤—à¥à¤°à¥ à¤¯à¥à¤¤à¤¿"),
    MANGLIK_CANCEL_JUPITER_CONJUNCT_DESC("Jupiter conjunct Mars cancels Manglik effects", "à¤—à¥à¤°à¥à¤²à¥‡ à¤®à¤‚à¤—à¤²à¤¸à¤à¤— à¤¯à¥à¤¤à¤¿à¤²à¥‡ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤ªà¥à¤°à¤­à¤¾à¤µ à¤°à¤¦à¥à¤¦ à¤—à¤°à¥à¤›"),
    MANGLIK_CANCEL_VENUS_CONJUNCT_TITLE("Venus Conjunction", "à¤¶à¥à¤•à¥à¤° à¤¯à¥à¤¤à¤¿"),
    MANGLIK_CANCEL_VENUS_CONJUNCT_DESC("Venus conjunct Mars significantly reduces effects", "à¤¶à¥à¤•à¥à¤°à¤²à¥‡ à¤®à¤‚à¤—à¤²à¤¸à¤à¤— à¤¯à¥à¤¤à¤¿à¤²à¥‡ à¤ªà¥à¤°à¤­à¤¾à¤µ à¤‰à¤²à¥à¤²à¥‡à¤–à¤¨à¥€à¤¯ à¤°à¥‚à¤ªà¤®à¤¾ à¤•à¤® à¤—à¤°à¥à¤›"),
    MANGLIK_CANCEL_JUPITER_ASPECT_TITLE("Jupiter's Aspect", "à¤—à¥à¤°à¥à¤•à¥‹ à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿"),
    MANGLIK_CANCEL_JUPITER_ASPECT_DESC("Jupiter aspecting Mars reduces Manglik effects", "à¤—à¥à¤°à¥à¤•à¥‹ à¤®à¤‚à¤—à¤²à¤®à¤¾ à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤²à¥‡ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤ªà¥à¤°à¤­à¤¾à¤µ à¤•à¤® à¤—à¤°à¥à¤›"),
    MANGLIK_CANCEL_SECOND_MERCURY_TITLE("Mars in 2nd in Mercury Sign", "à¤¬à¥à¤§ à¤°à¤¾à¤¶à¤¿à¤®à¤¾ à¥¨à¤”à¤‚ à¤®à¤‚à¤—à¤²"),
    MANGLIK_CANCEL_SECOND_MERCURY_DESC("Mars in 2nd house in Gemini/Virgo cancels dosha", "à¤®à¤¿à¤¥à¥à¤¨/à¤•à¤¨à¥à¤¯à¤¾à¤®à¤¾ à¥¨à¤”à¤‚ à¤­à¤¾à¤µà¤®à¤¾ à¤®à¤‚à¤—à¤²à¤²à¥‡ à¤¦à¥‹à¤· à¤°à¤¦à¥à¤¦ à¤—à¤°à¥à¤›"),
    MANGLIK_CANCEL_FOURTH_OWN_TITLE("Mars in 4th in Own Sign", "à¤¸à¥à¤µà¤°à¤¾à¤¶à¤¿à¤®à¤¾ à¥ªà¤”à¤‚ à¤®à¤‚à¤—à¤²"),
    MANGLIK_CANCEL_FOURTH_OWN_DESC("Mars in 4th house in Aries/Scorpio cancels dosha", "à¤®à¥‡à¤·/à¤µà¥ƒà¤¶à¥à¤šà¤¿à¤•à¤®à¤¾ à¥ªà¤”à¤‚ à¤­à¤¾à¤µà¤®à¤¾ à¤®à¤‚à¤—à¤²à¤²à¥‡ à¤¦à¥‹à¤· à¤°à¤¦à¥à¤¦ à¤—à¤°à¥à¤›"),
    MANGLIK_CANCEL_SEVENTH_SPECIAL_TITLE("Mars in 7th Special", "à¥­à¤”à¤‚ à¤®à¤‚à¤—à¤² à¤µà¤¿à¤¶à¥‡à¤·"),
    MANGLIK_CANCEL_SEVENTH_SPECIAL_DESC("Mars in 7th in Cancer/Capricorn reduces effects", "à¤•à¤°à¥à¤•à¤Ÿ/à¤®à¤•à¤°à¤®à¤¾ à¥­à¤”à¤‚ à¤­à¤¾à¤µà¤®à¤¾ à¤®à¤‚à¤—à¤²à¤²à¥‡ à¤ªà¥à¤°à¤­à¤¾à¤µ à¤•à¤® à¤—à¤°à¥à¤›"),
    MANGLIK_CANCEL_EIGHTH_JUPITER_TITLE("Mars in 8th Jupiter Sign", "à¤—à¥à¤°à¥ à¤°à¤¾à¤¶à¤¿à¤®à¤¾ à¥®à¤”à¤‚ à¤®à¤‚à¤—à¤²"),
    MANGLIK_CANCEL_EIGHTH_JUPITER_DESC("Mars in 8th in Sagittarius/Pisces reduces effects", "à¤§à¤¨à¥/à¤®à¥€à¤¨à¤®à¤¾ à¥®à¤”à¤‚ à¤­à¤¾à¤µà¤®à¤¾ à¤®à¤‚à¤—à¤²à¤²à¥‡ à¤ªà¥à¤°à¤­à¤¾à¤µ à¤•à¤® à¤—à¤°à¥à¤›"),
    MANGLIK_CANCEL_TWELFTH_VENUS_TITLE("Mars in 12th Venus Sign", "à¤¶à¥à¤•à¥à¤° à¤°à¤¾à¤¶à¤¿à¤®à¤¾ à¥§à¥¨à¤”à¤‚ à¤®à¤‚à¤—à¤²"),
    MANGLIK_CANCEL_TWELFTH_VENUS_DESC("Mars in 12th in Taurus/Libra cancels dosha", "à¤µà¥ƒà¤·à¤­/à¤¤à¥à¤²à¤¾à¤®à¤¾ à¥§à¥¨à¤”à¤‚ à¤­à¤¾à¤µà¤®à¤¾ à¤®à¤‚à¤—à¤²à¤²à¥‡ à¤¦à¥‹à¤· à¤°à¤¦à¥à¤¦ à¤—à¤°à¥à¤›"),
    MANGLIK_CANCEL_BENEFIC_ASC_TITLE("Benefic Ascendant", "à¤¶à¥à¤­ à¤²à¤—à¥à¤¨"),
    MANGLIK_CANCEL_BENEFIC_ASC_DESC("For Aries/Cancer/Leo/Scorpio ascendants, Mars is benefic", "à¤®à¥‡à¤·/à¤•à¤°à¥à¤•à¤Ÿ/à¤¸à¤¿à¤‚à¤¹/à¤µà¥ƒà¤¶à¥à¤šà¤¿à¤• à¤²à¤—à¥à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤®à¤‚à¤—à¤² à¤¶à¥à¤­ à¤¹à¥‹"),

    // Manglik Remedies
    REMEDY_KUMBH_VIVAH_TITLE("Kumbh Vivah", "à¤•à¥à¤®à¥à¤­ à¤µà¤¿à¤µà¤¾à¤¹"),
    REMEDY_KUMBH_VIVAH_DESC("Ceremonial marriage to a clay pot or Peepal tree before actual marriage", "à¤µà¤¾à¤¸à¥à¤¤à¤µà¤¿à¤• à¤µà¤¿à¤µà¤¾à¤¹ à¤…à¤˜à¤¿ à¤®à¤¾à¤Ÿà¥‹à¤•à¥‹ à¤˜à¤¡à¤¾ à¤µà¤¾ à¤ªà¥€à¤ªà¤²à¤•à¥‹ à¤°à¥‚à¤–à¤¸à¤à¤— à¤µà¤¿à¤µà¤¾à¤¹ à¤¸à¤®à¤¾à¤°à¥‹à¤¹"),
    REMEDY_MANGAL_SHANTI_TITLE("Mangal Shanti Puja", "à¤®à¤‚à¤—à¤² à¤¶à¤¾à¤¨à¥à¤¤à¤¿ à¤ªà¥‚à¤œà¤¾"),
    REMEDY_MANGAL_SHANTI_DESC("Perform Mars pacification ritual at a temple or home", "à¤®à¤¨à¥à¤¦à¤¿à¤° à¤µà¤¾ à¤˜à¤°à¤®à¤¾ à¤®à¤‚à¤—à¤² à¤¶à¤¾à¤¨à¥à¤¤à¤¿ à¤µà¤¿à¤§à¤¿ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    REMEDY_MARS_MANTRA_TITLE("Mars Mantra", "à¤®à¤‚à¤—à¤² à¤®à¤¨à¥à¤¤à¥à¤°"),
    REMEDY_MARS_MANTRA_DESC("Recite 'Om Kram Kreem Kroum Sah Bhaumaya Namah' 108 times on Tuesdays", "à¤®à¤‚à¤—à¤²à¤¬à¤¾à¤° 'à¥ à¤•à¥à¤°à¤¾à¤‚ à¤•à¥à¤°à¥€à¤‚ à¤•à¥à¤°à¥Œà¤‚ à¤¸à¤ƒ à¤­à¥Œà¤®à¤¾à¤¯ à¤¨à¤®à¤ƒ' à¥§à¥¦à¥® à¤ªà¤Ÿà¤• à¤œà¤ª à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    REMEDY_CORAL_TITLE("Red Coral (Moonga)", "à¤®à¥‚à¤‚à¤—à¤¾ à¤°à¤¤à¥à¤¨"),
    REMEDY_CORAL_DESC("Wear red coral in gold or copper on right hand ring finger", "à¤¦à¤¾à¤¹à¤¿à¤¨à¥‡ à¤¹à¤¾à¤¤à¤•à¥‹ à¤…à¤¨à¤¾à¤®à¤¿à¤•à¤¾à¤®à¤¾ à¤¸à¥à¤¨ à¤µà¤¾ à¤¤à¤¾à¤®à¤¾à¤®à¤¾ à¤®à¥‚à¤‚à¤—à¤¾ à¤²à¤—à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    REMEDY_TUESDAY_CHARITY_TITLE("Tuesday Charity", "à¤®à¤‚à¤—à¤²à¤¬à¤¾à¤° à¤¦à¤¾à¤¨"),
    REMEDY_TUESDAY_CHARITY_DESC("Donate red lentils, red cloth, or copper items on Tuesdays", "à¤®à¤‚à¤—à¤²à¤¬à¤¾à¤° à¤°à¤¾à¤¤à¥‹ à¤¦à¤¾à¤², à¤°à¤¾à¤¤à¥‹ à¤•à¤ªà¤¡à¤¾, à¤µà¤¾ à¤¤à¤¾à¤®à¤¾à¤•à¤¾ à¤¸à¤¾à¤®à¤¾à¤¨ à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),

    // ============================================
    // PITRA DOSHA ANALYSIS
    // ============================================
    PITRA_DOSHA_TITLE("Pitra Dosha Analysis", "à¤ªà¤¿à¤¤à¥ƒ à¤¦à¥‹à¤· à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    PITRA_DOSHA_SUBTITLE("Ancestral Karma Assessment", "à¤ªà¥à¤°à¥à¤–à¤¾à¤•à¥‹ à¤•à¤°à¥à¤® à¤®à¥‚à¤²à¥à¤¯à¤¾à¤‚à¤•à¤¨"),
    PITRA_DOSHA_NONE("No Pitra Dosha", "à¤ªà¤¿à¤¤à¥ƒ à¤¦à¥‹à¤· à¤›à¥ˆà¤¨"),
    PITRA_DOSHA_MINOR("Minor Pitra Dosha", "à¤¹à¤²à¥à¤•à¤¾ à¤ªà¤¿à¤¤à¥ƒ à¤¦à¥‹à¤·"),
    PITRA_DOSHA_MODERATE("Moderate Pitra Dosha", "à¤®à¤§à¥à¤¯à¤® à¤ªà¤¿à¤¤à¥ƒ à¤¦à¥‹à¤·"),
    PITRA_DOSHA_SIGNIFICANT("Significant Pitra Dosha", "à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤ªà¤¿à¤¤à¥ƒ à¤¦à¥‹à¤·"),
    PITRA_DOSHA_SEVERE("Severe Pitra Dosha", "à¤—à¤®à¥à¤­à¥€à¤° à¤ªà¤¿à¤¤à¥ƒ à¤¦à¥‹à¤·"),
    PITRA_DOSHA_PRESENT_SUMMARY("{level} detected in chart", "à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾ {level} à¤ªà¤¤à¥à¤¤à¤¾ à¤²à¤¾à¤—à¥à¤¯à¥‹"),
    PITRA_DOSHA_ABSENT_SUMMARY("No significant Pitra Dosha indicators found", "à¤•à¥à¤¨à¥ˆ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤ªà¤¿à¤¤à¥ƒ à¤¦à¥‹à¤· à¤¸à¤‚à¤•à¥‡à¤¤à¤•à¤¹à¤°à¥‚ à¤«à¥‡à¤²à¤¾ à¤ªà¤°à¥‡à¤¨à¤¨à¥"),

    // Pitra Dosha Types
    PITRA_TYPE_SURYA_RAHU("Sun-Rahu Conjunction", "à¤¸à¥‚à¤°à¥à¤¯-à¤°à¤¾à¤¹à¥ à¤¯à¥à¤¤à¤¿"),
    PITRA_TYPE_SURYA_KETU("Sun-Ketu Conjunction", "à¤¸à¥‚à¤°à¥à¤¯-à¤•à¥‡à¤¤à¥ à¤¯à¥à¤¤à¤¿"),
    PITRA_TYPE_SURYA_SHANI("Sun-Saturn Affliction", "à¤¸à¥‚à¤°à¥à¤¯-à¤¶à¤¨à¤¿ à¤ªà¥€à¤¡à¤¾"),
    PITRA_TYPE_NINTH_HOUSE("9th House Affliction", "à¥¯à¤”à¤‚ à¤­à¤¾à¤µ à¤ªà¥€à¤¡à¤¾"),
    PITRA_TYPE_NINTH_LORD("9th Lord Affliction", "à¥¯à¤”à¤‚ à¤­à¤¾à¤µà¥‡à¤¶ à¤ªà¥€à¤¡à¤¾"),
    PITRA_TYPE_RAHU_NINTH("Rahu in 9th House", "à¥¯à¤”à¤‚ à¤­à¤¾à¤µà¤®à¤¾ à¤°à¤¾à¤¹à¥"),
    PITRA_TYPE_COMBINED("Combined Affliction", "à¤¸à¤‚à¤¯à¥à¤•à¥à¤¤ à¤ªà¥€à¤¡à¤¾"),

    // Pitra Dosha Descriptions
    PITRA_DESC_SURYA_RAHU("Primary indicator - eclipsed Sun indicates blocked ancestral blessings", "à¤ªà¥à¤°à¤¾à¤¥à¤®à¤¿à¤• à¤¸à¤‚à¤•à¥‡à¤¤à¤• - à¤—à¥à¤°à¤¹à¤£ à¤²à¤¾à¤—à¥‡à¤•à¥‹ à¤¸à¥‚à¤°à¥à¤¯à¤²à¥‡ à¤…à¤µà¤°à¥à¤¦à¥à¤§ à¤ªà¥à¤°à¥à¤–à¤¾à¤•à¥‹ à¤†à¤¶à¥€à¤°à¥à¤µà¤¾à¤¦ à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤›"),
    PITRA_DESC_SURYA_KETU("Past-life karmic debts from paternal lineage", "à¤ªà¤¿à¤¤à¥ƒ à¤µà¤‚à¤¶à¤¬à¤¾à¤Ÿ à¤ªà¥‚à¤°à¥à¤µ à¤œà¤¨à¥à¤®à¤•à¥‹ à¤•à¤°à¥à¤® à¤‹à¤£"),
    PITRA_DESC_SURYA_SHANI("Father-related karmic lessons and delays", "à¤ªà¤¿à¤¤à¤¾-à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¿à¤¤ à¤•à¤°à¥à¤® à¤ªà¤¾à¤  à¤° à¤¢à¤¿à¤²à¤¾à¤‡"),
    PITRA_DESC_NINTH_HOUSE("House of ancestors afflicted by malefics", "à¤ªà¥à¤°à¥à¤–à¤¾à¤•à¥‹ à¤­à¤¾à¤µ à¤…à¤¶à¥à¤­ à¤—à¥à¤°à¤¹à¤¬à¤¾à¤Ÿ à¤ªà¥€à¤¡à¤¿à¤¤"),
    PITRA_DESC_NINTH_LORD("Lord of father and fortune is weakened", "à¤ªà¤¿à¤¤à¤¾ à¤° à¤­à¤¾à¤—à¥à¤¯à¤•à¥‹ à¤¸à¥à¤µà¤¾à¤®à¥€ à¤•à¤®à¤œà¥‹à¤° à¤›"),
    PITRA_DESC_RAHU_NINTH("Strong indication of ancestral debts", "à¤ªà¥à¤°à¥à¤–à¤¾à¤•à¥‹ à¤‹à¤£à¤•à¥‹ à¤¬à¤²à¤¿à¤¯à¥‹ à¤¸à¤‚à¤•à¥‡à¤¤"),
    PITRA_DESC_COMBINED("Multiple factors indicate significant ancestral karma", "à¤¬à¤¹à¥ à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚à¤²à¥‡ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤ªà¥à¤°à¥à¤–à¤¾à¤•à¥‹ à¤•à¤°à¥à¤® à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤›à¤¨à¥"),

    // Pitra Dosha Remedies
    REMEDY_PITRA_TARPAN_TITLE("Pitra Tarpan", "à¤ªà¤¿à¤¤à¥ƒ à¤¤à¤°à¥à¤ªà¤£"),
    REMEDY_PITRA_TARPAN_DESC("Offer water with sesame seeds to ancestors during Amavasya", "à¤…à¤®à¤¾à¤µà¤¸à¥à¤¯à¤¾à¤®à¤¾ à¤ªà¥à¤°à¥à¤–à¤¾à¤²à¤¾à¤ˆ à¤¤à¤¿à¤² à¤ªà¤¾à¤¨à¥€ à¤…à¤°à¥à¤ªà¤£ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    REMEDY_SHRADDHA_TITLE("Shraddha Ceremony", "à¤¶à¥à¤°à¤¾à¤¦à¥à¤§ à¤µà¤¿à¤§à¤¿"),
    REMEDY_SHRADDHA_DESC("Perform annual death anniversary rituals for departed ancestors", "à¤¦à¤¿à¤µà¤‚à¤—à¤¤ à¤ªà¥à¤°à¥à¤–à¤¾à¤•à¥‹ à¤µà¤¾à¤°à¥à¤·à¤¿à¤• à¤®à¥ƒà¤¤à¥à¤¯à¥ à¤µà¤°à¥à¤·à¤—à¤¾à¤à¤  à¤µà¤¿à¤§à¤¿ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    REMEDY_CROW_FEEDING_TITLE("Crow Feeding", "à¤•à¤¾à¤— à¤­à¥‹à¤œà¤¨"),
    REMEDY_CROW_FEEDING_DESC("Feed crows daily as they are considered messengers of ancestors", "à¤•à¤¾à¤—à¤²à¤¾à¤ˆ à¤¦à¥ˆà¤¨à¤¿à¤• à¤–à¥à¤µà¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥ à¤•à¤¿à¤¨à¤­à¤¨à¥‡ à¤¤à¤¿à¤¨à¥€à¤¹à¤°à¥‚à¤²à¤¾à¤ˆ à¤ªà¥à¤°à¥à¤–à¤¾à¤•à¥‹ à¤¦à¥‚à¤¤ à¤®à¤¾à¤¨à¤¿à¤¨à¥à¤›"),
    REMEDY_NARAYAN_BALI_TITLE("Narayan Bali", "à¤¨à¤¾à¤°à¤¾à¤¯à¤£ à¤¬à¤²à¤¿"),
    REMEDY_NARAYAN_BALI_DESC("Special ritual for departed souls performed at Trimbakeshwar", "à¤¤à¥à¤°à¥à¤¯à¤®à¥à¤¬à¤•à¥‡à¤¶à¥à¤µà¤°à¤®à¤¾ à¤¦à¤¿à¤µà¤‚à¤—à¤¤ à¤†à¤¤à¥à¤®à¤¾à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤µà¤¿à¤¶à¥‡à¤· à¤µà¤¿à¤§à¤¿"),
    REMEDY_PIND_DAAN_TITLE("Pind Daan", "à¤ªà¤¿à¤£à¥à¤¡ à¤¦à¤¾à¤¨"),
    REMEDY_PIND_DAAN_DESC("Offer rice balls to ancestors at Gaya or other sacred places", "à¤—à¤¯à¤¾ à¤µà¤¾ à¤…à¤¨à¥à¤¯ à¤ªà¤µà¤¿à¤¤à¥à¤° à¤¸à¥à¤¥à¤¾à¤¨à¤®à¤¾ à¤ªà¥à¤°à¥à¤–à¤¾à¤²à¤¾à¤ˆ à¤­à¤¾à¤¤à¤•à¥‹ à¤ªà¤¿à¤£à¥à¤¡ à¤…à¤°à¥à¤ªà¤£ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    REMEDY_PITRA_GAYATRI_TITLE("Pitra Gayatri Mantra", "à¤ªà¤¿à¤¤à¥ƒ à¤—à¤¾à¤¯à¤¤à¥à¤°à¥€ à¤®à¤¨à¥à¤¤à¥à¤°"),
    REMEDY_PITRA_GAYATRI_DESC("Recite Pitra Gayatri daily during Brahma Muhurta for ancestral peace", "à¤ªà¥à¤°à¥à¤–à¤¾à¤•à¥‹ à¤¶à¤¾à¤¨à¥à¤¤à¤¿à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¬à¥à¤°à¤¹à¥à¤® à¤®à¥à¤¹à¥‚à¤°à¥à¤¤à¤®à¤¾ à¤ªà¤¿à¤¤à¥ƒ à¤—à¤¾à¤¯à¤¤à¥à¤°à¥€ à¤¦à¥ˆà¤¨à¤¿à¤• à¤œà¤ª à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),

    // ============================================
    // COMMON DOSHA ANALYSIS STRINGS
    // ============================================
    DOSHA_ANALYSIS("Dosha Analysis", "à¤¦à¥‹à¤· à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    DOSHA_INDICATORS("Indicators Found", "à¤¸à¤‚à¤•à¥‡à¤¤à¤•à¤¹à¤°à¥‚ à¤«à¥‡à¤²à¤¾ à¤ªà¤°à¥‡"),
    DOSHA_AFFECTED_AREAS("Affected Life Areas", "à¤ªà¥à¤°à¤­à¤¾à¤µà¤¿à¤¤ à¤œà¥€à¤µà¤¨ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚"),
    DOSHA_REMEDIES_SECTION("Recommended Remedies", "à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸ à¤—à¤°à¤¿à¤à¤•à¤¾ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    DOSHA_INTERPRETATION("Interpretation", "à¤µà¥à¤¯à¤¾à¤–à¥à¤¯à¤¾"),
    DOSHA_SEVERITY_SCORE("Severity Score", "à¤—à¤®à¥à¤­à¥€à¤°à¤¤à¤¾ à¤…à¤‚à¤•"),
    DOSHA_AUSPICIOUS_TIMES("Auspicious Times for Remedies", "à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¶à¥à¤­ à¤¸à¤®à¤¯"),

    // ============================================
    // INTERPRETATION SECTION HEADERS
    // ============================================
    INTERP_ANALYSIS_HEADER("ANALYSIS", "à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    INTERP_INDICATORS_FOUND("INDICATORS FOUND:", "à¤ªà¤¾à¤‡à¤à¤•à¤¾ à¤¸à¤‚à¤•à¥‡à¤¤à¤•à¤¹à¤°à¥‚:"),
    INTERP_INTERPRETATION("INTERPRETATION:", "à¤µà¥à¤¯à¤¾à¤–à¥à¤¯à¤¾:"),
    INTERP_SEVERITY("SEVERITY:", "à¤—à¤®à¥à¤­à¥€à¤°à¤¤à¤¾:"),
    INTERP_LEVEL("Level:", "à¤¸à¥à¤¤à¤°:"),

    // ============================================
    // MANGLIK DOSHA INTERPRETATION
    // ============================================
    MANGLIK_INTERP_NO_DOSHA("NO MANGLIK DOSHA", "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤›à¥ˆà¤¨"),
    MANGLIK_INTERP_MARS_NOT_PLACED(
        "Mars is not placed in houses 1, 2, 4, 7, 8, or 12 from your Lagna, Moon, or Venus.",
        "à¤®à¤‚à¤—à¤² à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤²à¤—à¥à¤¨, à¤šà¤¨à¥à¤¦à¥à¤° à¤µà¤¾ à¤¶à¥à¤•à¥à¤°à¤¬à¤¾à¤Ÿ à¥§, à¥¨, à¥ª, à¥­, à¥® à¤µà¤¾ à¥§à¥¨ à¤­à¤¾à¤µà¤®à¤¾ à¤›à¥ˆà¤¨à¥¤"
    ),
    MANGLIK_INTERP_HEADER("MANGLIK DOSHA ANALYSIS", "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    MANGLIK_INTERP_FROM_REFERENCE("ANALYSIS FROM THREE REFERENCE POINTS:", "à¤¤à¥€à¤¨ à¤¸à¤¨à¥à¤¦à¤°à¥à¤­ à¤¬à¤¿à¤¨à¥à¤¦à¥à¤¬à¤¾à¤Ÿ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£:"),
    MANGLIK_INTERP_FROM_LAGNA("From Lagna", "à¤²à¤—à¥à¤¨à¤¬à¤¾à¤Ÿ"),
    MANGLIK_INTERP_FROM_MOON("From Moon", "à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤¾à¤Ÿ"),
    MANGLIK_INTERP_FROM_VENUS("From Venus", "à¤¶à¥à¤•à¥à¤°à¤¬à¤¾à¤Ÿ"),
    MANGLIK_INTERP_MARS_IN_HOUSE("Mars in house", "à¤®à¤‚à¤—à¤² à¤­à¤¾à¤µà¤®à¤¾"),
    MANGLIK_INTERP_MANGLIK_YES("YES", "à¤›"),
    MANGLIK_INTERP_MANGLIK_NO("NO", "à¤›à¥ˆà¤¨"),
    MANGLIK_INTERP_CANCELLATION_PRESENT("CANCELLATION FACTORS PRESENT:", "à¤¨à¤¿à¤°à¤¸à¤¨ à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚ à¤‰à¤ªà¤¸à¥à¤¥à¤¿à¤¤:"),
    MANGLIK_INTERP_EFFECTIVE_LEVEL("Effective Level After Cancellations:", "à¤¨à¤¿à¤°à¤¸à¤¨ à¤ªà¤›à¤¿à¤•à¥‹ à¤ªà¥à¤°à¤­à¤¾à¤µà¤•à¤¾à¤°à¥€ à¤¸à¥à¤¤à¤°:"),
    MANGLIK_INTERP_HOUSE_SUFFIX_ST("st", "à¤”à¤‚"),
    MANGLIK_INTERP_HOUSE_SUFFIX_ND("nd", "à¤”à¤‚"),
    MANGLIK_INTERP_HOUSE_SUFFIX_RD("rd", "à¤”à¤‚"),
    MANGLIK_INTERP_HOUSE_SUFFIX_TH("th", "à¤”à¤‚"),

    // ============================================
    // MANGLIK MARRIAGE CONSIDERATIONS
    // ============================================
    MANGLIK_MARRIAGE_HEADER("MARRIAGE CONSIDERATIONS", "à¤µà¤¿à¤µà¤¾à¤¹ à¤µà¤¿à¤šà¤¾à¤°"),
    MANGLIK_MARRIAGE_NONE_NO_RESTRICTION(
        "No restrictions based on Manglik Dosha",
        "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤·à¤•à¥‹ à¤†à¤§à¤¾à¤°à¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤ªà¥à¤°à¤¤à¤¿à¤¬à¤¨à¥à¤§ à¤›à¥ˆà¤¨"
    ),
    MANGLIK_MARRIAGE_NONE_COMPATIBLE(
        "Compatible with both Manglik and non-Manglik partners",
        "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤° à¤—à¥ˆà¤°-à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥à¤µà¥ˆ à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¤¸à¤à¤— à¤®à¤¿à¤²à¥à¤¦à¥‹"
    ),
    MANGLIK_MARRIAGE_MILD_EFFECTS(
        "Mild Manglik effects - marriage with non-Manglik is possible",
        "à¤¹à¤²à¥à¤•à¤¾ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤ªà¥à¤°à¤­à¤¾à¤µ - à¤—à¥ˆà¤°-à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤•à¤¸à¤à¤— à¤µà¤¿à¤µà¤¾à¤¹ à¤¸à¤®à¥à¤­à¤µ à¤›"
    ),
    MANGLIK_MARRIAGE_MILD_REMEDIES(
        "Simple remedies recommended before marriage",
        "à¤µà¤¿à¤µà¤¾à¤¹ à¤…à¤˜à¤¿ à¤¸à¤°à¤² à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸ à¤—à¤°à¤¿à¤¨à¥à¤›"
    ),
    MANGLIK_MARRIAGE_MILD_MATCHING(
        "Matching with another Manglik is beneficial but not essential",
        "à¤…à¤°à¥à¤•à¥‹ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤•à¤¸à¤à¤— à¤®à¤¿à¤²à¤¾à¤¨ à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤• à¤¤à¤° à¤…à¤¨à¤¿à¤µà¤¾à¤°à¥à¤¯ à¤¹à¥‹à¤‡à¤¨"
    ),
    MANGLIK_MARRIAGE_PARTIAL_REMEDIES(
        "Partial Manglik - remedies strongly recommended",
        "à¤†à¤‚à¤¶à¤¿à¤• à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• - à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤œà¥‹à¤¡à¤¦à¤¾à¤° à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸"
    ),
    MANGLIK_MARRIAGE_PARTIAL_PREFERABLE(
        "Marriage with Manglik partner is preferable",
        "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¤¸à¤à¤— à¤µà¤¿à¤µà¤¾à¤¹ à¤‰à¤ªà¤¯à¥à¤•à¥à¤¤"
    ),
    MANGLIK_MARRIAGE_PARTIAL_KUMBH(
        "If marrying non-Manglik, perform Kumbh Vivah",
        "à¤—à¥ˆà¤°-à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤•à¤¸à¤à¤— à¤µà¤¿à¤µà¤¾à¤¹ à¤—à¤°à¥à¤¦à¤¾ à¤•à¥à¤®à¥à¤­ à¤µà¤¿à¤µà¤¾à¤¹ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"
    ),
    MANGLIK_MARRIAGE_FULL_PRESENT(
        "Full Manglik Dosha present",
        "à¤ªà¥‚à¤°à¥à¤£ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤‰à¤ªà¤¸à¥à¤¥à¤¿à¤¤"
    ),
    MANGLIK_MARRIAGE_FULL_RECOMMENDED(
        "Marriage with Manglik partner highly recommended",
        "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¤¸à¤à¤— à¤µà¤¿à¤µà¤¾à¤¹ à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸"
    ),
    MANGLIK_MARRIAGE_FULL_KUMBH_ESSENTIAL(
        "Kumbh Vivah or equivalent ritual essential before marriage",
        "à¤µà¤¿à¤µà¤¾à¤¹ à¤…à¤˜à¤¿ à¤•à¥à¤®à¥à¤­ à¤µà¤¿à¤µà¤¾à¤¹ à¤µà¤¾ à¤¸à¤®à¤¾à¤¨ à¤…à¤¨à¥à¤·à¥à¤ à¤¾à¤¨ à¤†à¤µà¤¶à¥à¤¯à¤•"
    ),
    MANGLIK_MARRIAGE_FULL_PROPITIATION(
        "Regular Mars propitiation recommended",
        "à¤¨à¤¿à¤¯à¤®à¤¿à¤¤ à¤®à¤‚à¤—à¤² à¤¶à¤¾à¤¨à¥à¤¤à¤¿ à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸ à¤—à¤°à¤¿à¤¨à¥à¤›"
    ),
    MANGLIK_MARRIAGE_SEVERE_CONSIDERATION(
        "Severe Manglik Dosha - careful consideration required",
        "à¤—à¤®à¥à¤­à¥€à¤° à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· - à¤¸à¤¾à¤µà¤§à¤¾à¤¨à¥€à¤ªà¥‚à¤°à¥à¤£ à¤µà¤¿à¤šà¤¾à¤° à¤†à¤µà¤¶à¥à¤¯à¤•"
    ),
    MANGLIK_MARRIAGE_SEVERE_ONLY_MANGLIK(
        "Only marry Manglik partner with similar intensity",
        "à¤¸à¤®à¤¾à¤¨ à¤¤à¥€à¤µà¥à¤°à¤¤à¤¾ à¤­à¤à¤•à¥‹ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¤¸à¤à¤— à¤®à¤¾à¤¤à¥à¤° à¤µà¤¿à¤µà¤¾à¤¹ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"
    ),
    MANGLIK_MARRIAGE_SEVERE_MULTIPLE_REMEDIES(
        "Multiple remedies required before and after marriage",
        "à¤µà¤¿à¤µà¤¾à¤¹ à¤…à¤˜à¤¿ à¤° à¤ªà¤›à¤¿ à¤§à¥‡à¤°à¥ˆ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤†à¤µà¤¶à¥à¤¯à¤•"
    ),
    MANGLIK_MARRIAGE_SEVERE_DELAY(
        "Consider delaying marriage until after age 28 (Mars maturity)",
        "à¤®à¤‚à¤—à¤² à¤ªà¤°à¤¿à¤ªà¤•à¥à¤µà¤¤à¤¾ (à¥¨à¥® à¤µà¤°à¥à¤·) à¤¸à¤®à¥à¤® à¤µà¤¿à¤µà¤¾à¤¹ à¤¢à¤¿à¤²à¤¾ à¤—à¤°à¥à¤¨à¥‡ à¤µà¤¿à¤šà¤¾à¤° à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"
    ),
    MANGLIK_MARRIAGE_FULL_CANCELLATION(
        "NOTE: Full cancellation present - Manglik Dosha effectively nullified",
        "à¤¨à¥‹à¤Ÿ: à¤ªà¥‚à¤°à¥à¤£ à¤¨à¤¿à¤°à¤¸à¤¨ à¤‰à¤ªà¤¸à¥à¤¥à¤¿à¤¤ - à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤ªà¥à¤°à¤­à¤¾à¤µà¤•à¤¾à¤°à¥€ à¤°à¥‚à¤ªà¤®à¤¾ à¤¶à¥‚à¤¨à¥à¤¯"
    ),

    // ============================================
    // MANGLIK COMPATIBILITY
    // ============================================
    MANGLIK_COMPAT_EXCELLENT(
        "Excellent Manglik compatibility - no concerns",
        "à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤®à¤¿à¤²à¤¾à¤¨ - à¤•à¥à¤¨à¥ˆ à¤šà¤¿à¤¨à¥à¤¤à¤¾ à¤›à¥ˆà¤¨"
    ),
    MANGLIK_COMPAT_GOOD(
        "Good compatibility - minor remedies may help",
        "à¤°à¤¾à¤®à¥à¤°à¥‹ à¤®à¤¿à¤²à¤¾à¤¨ - à¤¸à¤¾à¤¨à¤¾ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤¸à¤¹à¤¾à¤¯à¤• à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›"
    ),
    MANGLIK_COMPAT_AVERAGE(
        "Average compatibility - remedies recommended",
        "à¤”à¤¸à¤¤ à¤®à¤¿à¤²à¤¾à¤¨ - à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸ à¤—à¤°à¤¿à¤¨à¥à¤›"
    ),
    MANGLIK_COMPAT_BELOW_AVERAGE(
        "Below average - significant remedies required",
        "à¤”à¤¸à¤¤à¤­à¤¨à¥à¤¦à¤¾ à¤•à¤® - à¤®à¤¹à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤†à¤µà¤¶à¥à¤¯à¤•"
    ),
    MANGLIK_COMPAT_POOR(
        "Challenging combination - expert consultation advised",
        "à¤•à¤ à¤¿à¤¨ à¤¸à¤‚à¤¯à¥‹à¤œà¤¨ - à¤µà¤¿à¤¶à¥‡à¤·à¤œà¥à¤ž à¤ªà¤°à¤¾à¤®à¤°à¥à¤¶ à¤¸à¤²à¥à¤²à¤¾à¤¹ à¤¦à¤¿à¤‡à¤¨à¥à¤›"
    ),

    // ============================================
    // MANGLIK REMEDY EFFECTIVENESS
    // ============================================
    REMEDY_EFFECTIVENESS_TRADITIONAL(
        "Traditional remedy - highly effective",
        "à¤ªà¤°à¤®à¥à¤ªà¤°à¤¾à¤—à¤¤ à¤‰à¤ªà¤¾à¤¯ - à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤ªà¥à¤°à¤­à¤¾à¤µà¤•à¤¾à¤°à¥€"
    ),
    REMEDY_EFFECTIVENESS_ALL_LEVELS(
        "Recommended for all Manglik levels",
        "à¤¸à¤¬à¥ˆ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¸à¥à¤¤à¤°à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸"
    ),
    REMEDY_EFFECTIVENESS_TUESDAY(
        "Daily recitation on Tuesdays",
        "à¤®à¤‚à¤—à¤²à¤¬à¤¾à¤° à¤¦à¥ˆà¤¨à¤¿à¤• à¤ªà¤¾à¤ "
    ),
    REMEDY_EFFECTIVENESS_CONSULT(
        "Consult astrologer before wearing",
        "à¤²à¤—à¤¾à¤‰à¤¨à¥ à¤…à¤˜à¤¿ à¤œà¥à¤¯à¥‹à¤¤à¤¿à¤·à¥€à¤¸à¤à¤— à¤ªà¤°à¤¾à¤®à¤°à¥à¤¶ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"
    ),
    REMEDY_EFFECTIVENESS_EVERY_TUESDAY(
        "Every Tuesday",
        "à¤¹à¤°à¥‡à¤• à¤®à¤‚à¤—à¤²à¤¬à¤¾à¤°"
    ),

    // ============================================
    // MANGLIK REFERENCE POINTS
    // ============================================
    MANGLIK_REF_LAGNA("Lagna", "à¤²à¤—à¥à¤¨"),
    MANGLIK_REF_MOON("Moon", "à¤šà¤¨à¥à¤¦à¥à¤°"),
    MANGLIK_REF_VENUS("Venus", "à¤¶à¥à¤•à¥à¤°"),
    YES("Yes", "à¤›"),
    NO("No", "à¤›à¥ˆà¤¨"),

    // ============================================
    // MANGLIK INTERPRETATION KEYS
    // ============================================
    MANGLIK_INTERP_NO_DOSHA_TITLE("NO MANGLIK DOSHA", "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤›à¥ˆà¤¨"),
    MANGLIK_INTERP_NO_DOSHA_DESC(
        "Mars is not placed in houses 1, 2, 4, 7, 8, or 12 from your Lagna, Moon, or Venus. There is no Manglik Dosha in your chart.",
        "à¤®à¤‚à¤—à¤² à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤²à¤—à¥à¤¨, à¤šà¤¨à¥à¤¦à¥à¤° à¤µà¤¾ à¤¶à¥à¤•à¥à¤°à¤¬à¤¾à¤Ÿ à¥§, à¥¨, à¥ª, à¥­, à¥® à¤µà¤¾ à¥§à¥¨à¤”à¤‚ à¤­à¤¾à¤µà¤®à¤¾ à¤›à¥ˆà¤¨à¥¤ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤›à¥ˆà¤¨à¥¤"
    ),
    MANGLIK_INTERP_TITLE("MANGLIK DOSHA ANALYSIS", "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    MANGLIK_INTERP_MARS_POSITION("Mars Position: {sign} in House {house}", "à¤®à¤‚à¤—à¤² à¤¸à¥à¤¥à¤¿à¤¤à¤¿: {sign} à¤­à¤¾à¤µ {house} à¤®à¤¾"),
    MANGLIK_INTERP_MARS_RETROGRADE("Note: Mars is retrograde which can intensify or modify its effects.", "à¤¨à¥‹à¤Ÿ: à¤®à¤‚à¤—à¤² à¤µà¤•à¥à¤°à¥€ à¤› à¤œà¤¸à¤²à¥‡ à¤¯à¤¸à¤•à¥‹ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚ à¤¤à¥€à¤µà¥à¤° à¤µà¤¾ à¤ªà¤°à¤¿à¤®à¤¾à¤°à¥à¤œà¤¨ à¤—à¤°à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤"),
    MANGLIK_INTERP_THREE_REF("ANALYSIS FROM THREE REFERENCE POINTS:", "à¤¤à¥€à¤¨ à¤¸à¤¨à¥à¤¦à¤°à¥à¤­ à¤¬à¤¿à¤¨à¥à¤¦à¥à¤¬à¤¾à¤Ÿ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£:"),
    MANGLIK_INTERP_MARS_HOUSE("Mars in House {house}", "à¤®à¤‚à¤—à¤² à¤­à¤¾à¤µ {house} à¤®à¤¾"),
    MANGLIK_INTERP_IS_MANGLIK("Manglik", "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤•"),
    MANGLIK_INTERP_INITIAL_LEVEL("Initial Level: {level}", "à¤ªà¥à¤°à¤¾à¤°à¤®à¥à¤­à¤¿à¤• à¤¸à¥à¤¤à¤°: {level}"),

    // ============================================
    // MANGLIK MARRIAGE CONSIDERATIONS
    // ============================================
    MANGLIK_MARRIAGE_TITLE("MARRIAGE CONSIDERATIONS", "à¤µà¤¿à¤µà¤¾à¤¹ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¥€ à¤µà¤¿à¤šà¤¾à¤°à¤¹à¤°à¥‚"),
    MANGLIK_MARRIAGE_NONE_1("No restrictions based on Manglik Dosha.", "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤·à¤®à¤¾ à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤•à¥à¤¨à¥ˆ à¤ªà¥à¤°à¤¤à¤¿à¤¬à¤¨à¥à¤§ à¤›à¥ˆà¤¨à¥¤"),
    MANGLIK_MARRIAGE_NONE_2("Compatible with both Manglik and non-Manglik partners.", "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤° à¤—à¥ˆà¤°-à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥à¤µà¥ˆ à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¤¸à¤à¤— à¤®à¤¿à¤²à¥à¤¦à¥‹à¥¤"),
    MANGLIK_MARRIAGE_MILD_1("Mild Manglik effects - marriage with non-Manglik is possible.", "à¤¹à¤²à¥à¤•à¤¾ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤ªà¥à¤°à¤­à¤¾à¤µ - à¤—à¥ˆà¤°-à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤•à¤¸à¤à¤— à¤µà¤¿à¤µà¤¾à¤¹ à¤¸à¤®à¥à¤­à¤µà¥¤"),
    MANGLIK_MARRIAGE_MILD_2("Simple remedies recommended before marriage.", "à¤µà¤¿à¤µà¤¾à¤¹ à¤…à¤˜à¤¿ à¤¸à¤°à¤² à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸à¥¤"),
    MANGLIK_MARRIAGE_MILD_3("Matching with another Manglik is beneficial but not essential.", "à¤…à¤°à¥à¤•à¥‹ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤•à¤¸à¤à¤— à¤®à¤¿à¤²à¤¾à¤¨ à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤• à¤¤à¤° à¤…à¤¨à¤¿à¤µà¤¾à¤°à¥à¤¯ à¤¹à¥‹à¤‡à¤¨à¥¤"),
    MANGLIK_MARRIAGE_PARTIAL_1("Partial Manglik - remedies strongly recommended.", "à¤†à¤‚à¤¶à¤¿à¤• à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• - à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤œà¥‹à¤¡à¤¦à¤¾à¤° à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸à¥¤"),
    MANGLIK_MARRIAGE_PARTIAL_2("Marriage with Manglik partner is preferable.", "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¤¸à¤à¤— à¤µà¤¿à¤µà¤¾à¤¹ à¤‰à¤ªà¤¯à¥à¤•à¥à¤¤à¥¤"),
    MANGLIK_MARRIAGE_PARTIAL_3("If marrying non-Manglik, perform Kumbh Vivah.", "à¤—à¥ˆà¤°-à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤•à¤¸à¤à¤— à¤µà¤¿à¤µà¤¾à¤¹ à¤—à¤°à¥à¤¦à¤¾ à¤•à¥à¤®à¥à¤­ à¤µà¤¿à¤µà¤¾à¤¹ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    MANGLIK_MARRIAGE_FULL_1("Full Manglik Dosha present.", "à¤ªà¥‚à¤°à¥à¤£ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤‰à¤ªà¤¸à¥à¤¥à¤¿à¤¤à¥¤"),
    MANGLIK_MARRIAGE_FULL_2("Marriage with Manglik partner highly recommended.", "à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¤¸à¤à¤— à¤µà¤¿à¤µà¤¾à¤¹ à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸à¥¤"),
    MANGLIK_MARRIAGE_FULL_3("Kumbh Vivah or equivalent ritual essential before marriage.", "à¤µà¤¿à¤µà¤¾à¤¹ à¤…à¤˜à¤¿ à¤•à¥à¤®à¥à¤­ à¤µà¤¿à¤µà¤¾à¤¹ à¤µà¤¾ à¤¸à¤®à¤¤à¥à¤²à¥à¤¯ à¤…à¤¨à¥à¤·à¥à¤ à¤¾à¤¨ à¤…à¤¨à¤¿à¤µà¤¾à¤°à¥à¤¯à¥¤"),
    MANGLIK_MARRIAGE_FULL_4("Regular Mars propitiation recommended.", "à¤¨à¤¿à¤¯à¤®à¤¿à¤¤ à¤®à¤‚à¤—à¤² à¤¶à¤¾à¤¨à¥à¤¤à¤¿ à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸à¥¤"),
    MANGLIK_MARRIAGE_SEVERE_1("Severe Manglik Dosha - careful consideration required.", "à¤—à¤®à¥à¤­à¥€à¤° à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· - à¤¸à¤¾à¤µà¤§à¤¾à¤¨à¥€à¤ªà¥‚à¤°à¥à¤£ à¤µà¤¿à¤šà¤¾à¤° à¤†à¤µà¤¶à¥à¤¯à¤•à¥¤"),
    MANGLIK_MARRIAGE_SEVERE_2("Only marry Manglik partner with similar intensity.", "à¤¸à¤®à¤¾à¤¨ à¤¤à¥€à¤µà¥à¤°à¤¤à¤¾ à¤­à¤à¤•à¥‹ à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¤¸à¤à¤— à¤®à¤¾à¤¤à¥à¤° à¤µà¤¿à¤µà¤¾à¤¹à¥¤"),
    MANGLIK_MARRIAGE_SEVERE_3("Multiple remedies required before and after marriage.", "à¤µà¤¿à¤µà¤¾à¤¹ à¤…à¤˜à¤¿ à¤° à¤ªà¤›à¤¿ à¤¬à¤¹à¥ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤†à¤µà¤¶à¥à¤¯à¤•à¥¤"),
    MANGLIK_MARRIAGE_SEVERE_4("Consider delaying marriage until after age 28 (Mars maturity).", "à¤®à¤‚à¤—à¤² à¤ªà¤°à¤¿à¤ªà¤•à¥à¤µà¤¤à¤¾ (à¥¨à¥® à¤µà¤°à¥à¤·) à¤¸à¤®à¥à¤® à¤µà¤¿à¤µà¤¾à¤¹ à¤¢à¤¿à¤²à¤¾ à¤—à¤°à¥à¤¨à¥‡ à¤µà¤¿à¤šà¤¾à¤° à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    MANGLIK_MARRIAGE_FULL_CANCEL_NOTE("NOTE: Full cancellation present - Manglik Dosha effectively nullified.", "à¤¨à¥‹à¤Ÿ: à¤ªà¥‚à¤°à¥à¤£ à¤¨à¤¿à¤°à¤¸à¤¨ à¤‰à¤ªà¤¸à¥à¤¥à¤¿à¤¤ - à¤®à¤¾à¤‚à¤—à¤²à¤¿à¤• à¤¦à¥‹à¤· à¤ªà¥à¤°à¤­à¤¾à¤µà¤•à¤¾à¤°à¥€ à¤°à¥‚à¤ªà¤®à¤¾ à¤¶à¥‚à¤¨à¥à¤¯à¥¤"),

    // ============================================
    // PITRA DOSHA INTERPRETATION
    // ============================================
    PITRA_INTERP_NO_DOSHA("NO SIGNIFICANT PITRA DOSHA", "à¤•à¥à¤¨à¥ˆ à¤®à¤¹à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤ªà¤¿à¤¤à¥à¤° à¤¦à¥‹à¤· à¤›à¥ˆà¤¨"),
    PITRA_INTERP_NO_DOSHA_DESC(
        "Your chart does not show significant indicators of Pitra Dosha.",
        "à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾ à¤ªà¤¿à¤¤à¥à¤° à¤¦à¥‹à¤·à¤•à¥‹ à¤®à¤¹à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤¸à¤‚à¤•à¥‡à¤¤à¤•à¤¹à¤°à¥‚ à¤¦à¥‡à¤–à¤¿à¤à¤¦à¥ˆà¤¨à¤¨à¥à¥¤"
    ),
    PITRA_INTERP_SUPPORTIVE(
        "The ancestral lineage appears supportive of your life journey.",
        "à¤ªà¥ˆà¤¤à¥ƒà¤• à¤µà¤‚à¤¶ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤œà¥€à¤µà¤¨ à¤¯à¤¾à¤¤à¥à¤°à¤¾à¤®à¤¾ à¤¸à¤¹à¤¾à¤¯à¤• à¤¦à¥‡à¤–à¤¿à¤¨à¥à¤›à¥¤"
    ),
    PITRA_INTERP_BENEFICIAL(
        "However, performing regular ancestral offerings (Shraddha) is always beneficial for maintaining positive ancestral blessings.",
        "à¤¤à¤¥à¤¾à¤ªà¤¿, à¤¸à¤•à¤¾à¤°à¤¾à¤¤à¥à¤®à¤• à¤ªà¥ˆà¤¤à¥ƒà¤• à¤†à¤¶à¥€à¤°à¥à¤µà¤¾à¤¦ à¤•à¤¾à¤¯à¤® à¤°à¤¾à¤–à¥à¤¨ à¤¨à¤¿à¤¯à¤®à¤¿à¤¤ à¤¶à¥à¤°à¤¾à¤¦à¥à¤§ à¤—à¤°à¥à¤¨à¥ à¤¸à¤§à¥ˆà¤‚ à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤• à¤¹à¥à¤¨à¥à¤›à¥¤"
    ),
    PITRA_INTERP_HEADER("PITRA DOSHA ANALYSIS", "à¤ªà¤¿à¤¤à¥à¤° à¤¦à¥‹à¤· à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    PITRA_INTERP_NINTH_HOUSE("9TH HOUSE ANALYSIS (House of Ancestors):", "à¥¯à¤”à¤‚ à¤­à¤¾à¤µ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ (à¤ªà¥‚à¤°à¥à¤µà¤œà¤•à¥‹ à¤­à¤¾à¤µ):"),
    PITRA_INTERP_NINTH_LORD("9th Lord:", "à¥¯à¤”à¤‚ à¤¸à¥à¤µà¤¾à¤®à¥€:"),
    PITRA_INTERP_NINTH_LORD_POSITION("9th Lord Position:", "à¥¯à¤”à¤‚ à¤¸à¥à¤µà¤¾à¤®à¥€à¤•à¥‹ à¤¸à¥à¤¥à¤¿à¤¤à¤¿:"),
    PITRA_INTERP_LORD_AFFLICTED("9th Lord Afflicted:", "à¥¯à¤”à¤‚ à¤¸à¥à¤µà¤¾à¤®à¥€ à¤ªà¥€à¤¡à¤¿à¤¤:"),
    PITRA_INTERP_HOUSE_AFFLICTED("9th House Afflicted:", "à¥¯à¤”à¤‚ à¤­à¤¾à¤µ à¤ªà¥€à¤¡à¤¿à¤¤:"),
    PITRA_INTERP_BENEFIC_INFLUENCE("Benefic Influence:", "à¤¶à¥à¤­ à¤ªà¥à¤°à¤­à¤¾à¤µ:"),
    PITRA_INTERP_YES_MITIGATING("Yes - Mitigating", "à¤› - à¤¨à¥à¤¯à¥‚à¤¨à¥€à¤•à¤°à¤£ à¤—à¤°à¥à¤¦à¥ˆ"),

    // Pitra Dosha indicator descriptions
    PITRA_DESC_SUN_RAHU_HOUSE(
        "Sun conjunct Rahu in House %d - Primary Pitra Dosha indicator",
        "à¤­à¤¾à¤µ %d à¤®à¤¾ à¤¸à¥‚à¤°à¥à¤¯-à¤°à¤¾à¤¹à¥ à¤¯à¥à¤¤à¤¿ - à¤ªà¥à¤°à¤®à¥à¤– à¤ªà¤¿à¤¤à¥à¤° à¤¦à¥‹à¤· à¤¸à¤‚à¤•à¥‡à¤¤à¤•"
    ),
    PITRA_DESC_SUN_KETU_HOUSE(
        "Sun conjunct Ketu in House %d - Indicates past-life ancestral karma",
        "à¤­à¤¾à¤µ %d à¤®à¤¾ à¤¸à¥‚à¤°à¥à¤¯-à¤•à¥‡à¤¤à¥ à¤¯à¥à¤¤à¤¿ - à¤ªà¥‚à¤°à¥à¤µà¤œà¤¨à¥à¤®à¤•à¥‹ à¤ªà¥ˆà¤¤à¥ƒà¤• à¤•à¤°à¥à¤® à¤¸à¤‚à¤•à¥‡à¤¤"
    ),
    PITRA_DESC_SUN_SATURN_CONJUNCT(
        "Sun conjunct Saturn in House %d - Father-related karmic issues",
        "à¤­à¤¾à¤µ %d à¤®à¤¾ à¤¸à¥‚à¤°à¥à¤¯-à¤¶à¤¨à¤¿ à¤¯à¥à¤¤à¤¿ - à¤ªà¤¿à¤¤à¥ƒ-à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¥€ à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤®à¥à¤¦à¥à¤¦à¤¾à¤¹à¤°à¥‚"
    ),
    PITRA_DESC_SATURN_ASPECT(
        "Saturn aspects Sun from House %d - Delayed results due to ancestral karma",
        "à¤¶à¤¨à¤¿à¤²à¥‡ à¤­à¤¾à¤µ %d à¤¬à¤¾à¤Ÿ à¤¸à¥‚à¤°à¥à¤¯à¤²à¤¾à¤ˆ à¤¹à¥‡à¤°à¥à¤› - à¤ªà¥ˆà¤¤à¥ƒà¤• à¤•à¤°à¥à¤®à¤²à¥‡ à¤—à¤°à¥à¤¦à¤¾ à¤¢à¤¿à¤²à¥‹ à¤ªà¤°à¤¿à¤£à¤¾à¤®"
    ),
    PITRA_DESC_MALEFICS_NINTH(
        "Malefics in 9th house - Ancestral blessings blocked",
        "à¥¯à¤”à¤‚ à¤­à¤¾à¤µà¤®à¤¾ à¤ªà¤¾à¤ª à¤—à¥à¤°à¤¹ - à¤ªà¥ˆà¤¤à¥ƒà¤• à¤†à¤¶à¥€à¤°à¥à¤µà¤¾à¤¦ à¤…à¤µà¤°à¥à¤¦à¥à¤§"
    ),
    PITRA_DESC_NINTH_LORD_AFFLICTED(
        "9th lord %s is afflicted - Ancestral lineage karma",
        "à¥¯à¤”à¤‚ à¤¸à¥à¤µà¤¾à¤®à¥€ %s à¤ªà¥€à¤¡à¤¿à¤¤ à¤› - à¤ªà¥ˆà¤¤à¥ƒà¤• à¤µà¤‚à¤¶ à¤•à¤°à¥à¤®"
    ),

    // Pitra interpretation levels
    PITRA_LEVEL_MINOR_DESC(
        "Minor ancestral karma is indicated. This may manifest as occasional obstacles or delays that seem unexplained. Regular ancestral prayers and offerings during Pitru Paksha should be sufficient.",
        "à¤¸à¤¾à¤¨à¤¾ à¤ªà¥ˆà¤¤à¥ƒà¤• à¤•à¤°à¥à¤® à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¤¿à¤à¤•à¥‹ à¤›à¥¤ à¤¯à¤¸à¤²à¥‡ à¤…à¤µà¥à¤¯à¤¾à¤–à¥à¤¯à¥‡à¤¯ à¤¦à¥‡à¤–à¤¿à¤¨à¥‡ à¤•à¤¹à¤¿à¤²à¥‡à¤•à¤¾à¤¹à¥€à¤‚ à¤…à¤µà¤°à¥‹à¤§à¤¹à¤°à¥‚ à¤µà¤¾ à¤¢à¤¿à¤²à¤¾à¤‡à¤•à¥‹ à¤°à¥‚à¤ªà¤®à¤¾ à¤ªà¥à¤°à¤•à¤Ÿ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤ à¤ªà¤¿à¤¤à¥ƒ à¤ªà¤•à¥à¤·à¤®à¤¾ à¤¨à¤¿à¤¯à¤®à¤¿à¤¤ à¤ªà¥ˆà¤¤à¥ƒà¤• à¤ªà¥à¤°à¤¾à¤°à¥à¤¥à¤¨à¤¾ à¤° à¤…à¤°à¥à¤ªà¤£ à¤ªà¤°à¥à¤¯à¤¾à¤ªà¥à¤¤ à¤¹à¥à¤¨à¥à¤ªà¤°à¥à¤›à¥¤"
    ),
    PITRA_LEVEL_MODERATE_DESC(
        "Moderate Pitra Dosha suggests unresolved ancestral obligations. You may experience recurring challenges in life that feel karmic. Regular Tarpan and Shraddha ceremonies are recommended.",
        "à¤®à¤§à¥à¤¯à¤® à¤ªà¤¿à¤¤à¥à¤° à¤¦à¥‹à¤·à¤²à¥‡ à¤…à¤ªà¥‚à¤°à¥à¤£ à¤ªà¥ˆà¤¤à¥ƒà¤• à¤¦à¤¾à¤¯à¤¿à¤¤à¥à¤µà¤¹à¤°à¥‚ à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤¦à¤›à¥¤ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤²à¥‡ à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤œà¤¸à¥à¤¤à¥‹ à¤²à¤¾à¤—à¥à¤¨à¥‡ à¤œà¥€à¤µà¤¨à¤®à¤¾ à¤†à¤µà¤°à¥à¤¤à¥€ à¤šà¥à¤¨à¥Œà¤¤à¥€à¤¹à¤°à¥‚ à¤…à¤¨à¥à¤­à¤µ à¤—à¤°à¥à¤¨ à¤¸à¤•à¥à¤¨à¥à¤¹à¥à¤¨à¥à¤›à¥¤ à¤¨à¤¿à¤¯à¤®à¤¿à¤¤ à¤¤à¤°à¥à¤ªà¤£ à¤° à¤¶à¥à¤°à¤¾à¤¦à¥à¤§ à¤¸à¤®à¤¾à¤°à¥‹à¤¹à¤¹à¤°à¥‚ à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸ à¤—à¤°à¤¿à¤¨à¥à¤›à¥¤"
    ),
    PITRA_LEVEL_SIGNIFICANT_DESC(
        "Significant ancestral karma is present. This may manifest as: delayed marriage or relationship issues, difficulties with children or progeny, career obstacles despite qualifications, and family disharmony. Comprehensive remedies including Narayan Bali may be beneficial.",
        "à¤®à¤¹à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤ªà¥ˆà¤¤à¥ƒà¤• à¤•à¤°à¥à¤® à¤‰à¤ªà¤¸à¥à¤¥à¤¿à¤¤ à¤›à¥¤ à¤¯à¤¸à¤²à¥‡ à¤µà¤¿à¤µà¤¾à¤¹à¤®à¤¾ à¤¢à¤¿à¤²à¤¾à¤‡ à¤µà¤¾ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤¸à¤®à¤¸à¥à¤¯à¤¾, à¤¸à¤¨à¥à¤¤à¤¾à¤¨ à¤µà¤¾ à¤¸à¤¨à¥à¤¤à¤¤à¤¿à¤®à¤¾ à¤•à¤ à¤¿à¤¨à¤¾à¤‡à¤¹à¤°à¥‚, à¤¯à¥‹à¤—à¥à¤¯à¤¤à¤¾ à¤­à¤à¤¤à¤¾ à¤ªà¤¨à¤¿ à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°à¤®à¤¾ à¤…à¤µà¤°à¥‹à¤§à¤¹à¤°à¥‚, à¤° à¤ªà¤¾à¤°à¤¿à¤µà¤¾à¤°à¤¿à¤• à¤µà¤¿à¤¸à¤‚à¤—à¤¤à¤¿à¤•à¥‹ à¤°à¥‚à¤ªà¤®à¤¾ à¤ªà¥à¤°à¤•à¤Ÿ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤ à¤¨à¤¾à¤°à¤¾à¤¯à¤£ à¤¬à¤²à¤¿ à¤¸à¤¹à¤¿à¤¤ à¤µà¥à¤¯à¤¾à¤ªà¤• à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤• à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤"
    ),
    PITRA_LEVEL_SEVERE_DESC(
        "Severe Pitra Dosha indicates deep ancestral karma that requires serious attention and remedial measures. This level of dosha often indicates: ancestors who departed with unfulfilled wishes, interrupted or improper last rites in the lineage, and significant karmic debts carried forward. Consult a qualified priest for Narayan Bali/Nagbali and Pind Daan at sacred places like Gaya.",
        "à¤—à¤®à¥à¤­à¥€à¤° à¤ªà¤¿à¤¤à¥à¤° à¤¦à¥‹à¤·à¤²à¥‡ à¤—à¤¹à¤¿à¤°à¥‹ à¤ªà¥ˆà¤¤à¥ƒà¤• à¤•à¤°à¥à¤® à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤¦à¤› à¤œà¤¸à¤²à¤¾à¤ˆ à¤—à¤®à¥à¤­à¥€à¤° à¤§à¥à¤¯à¤¾à¤¨ à¤° à¤‰à¤ªà¤šà¤¾à¤°à¤¾à¤¤à¥à¤®à¤• à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤†à¤µà¤¶à¥à¤¯à¤• à¤›à¥¤ à¤¯à¤¸ à¤¸à¥à¤¤à¤°à¤•à¥‹ à¤¦à¥‹à¤·à¤²à¥‡ à¤ªà¥à¤°à¤¾à¤¯à¤ƒ à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤¦à¤›: à¤…à¤ªà¥‚à¤°à¥à¤£ à¤‡à¤šà¥à¤›à¤¾à¤¹à¤°à¥‚à¤¸à¤à¤— à¤—à¤à¤•à¤¾ à¤ªà¥‚à¤°à¥à¤µà¤œà¤¹à¤°à¥‚, à¤µà¤‚à¤¶à¤®à¤¾ à¤…à¤µà¤°à¥à¤¦à¥à¤§ à¤µà¤¾ à¤…à¤¨à¥à¤šà¤¿à¤¤ à¤…à¤¨à¥à¤¤à¤¿à¤® à¤¸à¤‚à¤¸à¥à¤•à¤¾à¤°, à¤° à¤…à¤—à¤¾à¤¡à¤¿ à¤²à¤—à¤¿à¤à¤•à¥‹ à¤®à¤¹à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤‹à¤£à¤¹à¤°à¥‚à¥¤ à¤—à¤¯à¤¾à¤®à¤¾ à¤¨à¤¾à¤°à¤¾à¤¯à¤£ à¤¬à¤²à¤¿/à¤¨à¤¾à¤—à¤¬à¤²à¤¿ à¤° à¤ªà¤¿à¤£à¥à¤¡à¤¦à¤¾à¤¨ à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¯à¥‹à¤—à¥à¤¯ à¤ªà¥à¤œà¤¾à¤°à¥€à¤¸à¤à¤— à¤ªà¤°à¤¾à¤®à¤°à¥à¤¶ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"
    ),

    // Pitra life areas
    PITRA_AREA_FATHER("Father and paternal lineage", "à¤ªà¤¿à¤¤à¤¾ à¤° à¤ªà¥ˆà¤¤à¥ƒà¤• à¤µà¤‚à¤¶"),
    PITRA_AREA_SPIRITUAL("Spiritual progress and dharma", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤ªà¥à¤°à¤—à¤¤à¤¿ à¤° à¤§à¤°à¥à¤®"),
    PITRA_AREA_SELF("Self, health, and overall life direction", "à¤†à¤¤à¥à¤®, à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯, à¤° à¤¸à¤®à¤—à¥à¤° à¤œà¥€à¤µà¤¨ à¤¦à¤¿à¤¶à¤¾"),
    PITRA_AREA_FAMILY_WEALTH("Family wealth and accumulated assets", "à¤ªà¤¾à¤°à¤¿à¤µà¤¾à¤°à¤¿à¤• à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿ à¤° à¤¸à¤‚à¤šà¤¿à¤¤ à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿"),
    PITRA_AREA_SIBLINGS("Siblings and communication", "à¤­à¤¾à¤‡à¤¬à¤¹à¤¿à¤¨à¥€ à¤° à¤¸à¤‚à¤šà¤¾à¤°"),
    PITRA_AREA_MOTHER("Mother, property, and domestic peace", "à¤†à¤®à¤¾, à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿, à¤° à¤˜à¤°à¥‡à¤²à¥ à¤¶à¤¾à¤¨à¥à¤¤à¤¿"),
    PITRA_AREA_CHILDREN("Children, education, and creativity", "à¤¬à¤šà¥à¤šà¤¾à¤¹à¤°à¥‚, à¤¶à¤¿à¤•à¥à¤·à¤¾, à¤° à¤¸à¥ƒà¤œà¤¨à¤¾à¤¤à¥à¤®à¤•à¤¤à¤¾"),
    PITRA_AREA_HEALTH("Health, debts, and service", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯, à¤‹à¤£, à¤° à¤¸à¥‡à¤µà¤¾"),
    PITRA_AREA_MARRIAGE("Marriage and partnerships", "à¤µà¤¿à¤µà¤¾à¤¹ à¤° à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¥€"),
    PITRA_AREA_LONGEVITY("Longevity and inherited wealth", "à¤¦à¥€à¤°à¥à¤˜à¤¾à¤¯à¥ à¤° à¤µà¤¿à¤°à¤¾à¤¸à¤¤ à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿"),
    PITRA_AREA_FORTUNE("Fortune, higher learning, and spirituality", "à¤­à¤¾à¤—à¥à¤¯, à¤‰à¤šà¥à¤š à¤¶à¤¿à¤•à¥à¤·à¤¾, à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤•à¤¤à¤¾"),
    PITRA_AREA_CAREER("Career and public reputation", "à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤° à¤° à¤¸à¤¾à¤°à¥à¤µà¤œà¤¨à¤¿à¤• à¤ªà¥à¤°à¤¤à¤¿à¤·à¥à¤ à¤¾"),
    PITRA_AREA_GAINS("Gains and social network", "à¤²à¤¾à¤­ à¤° à¤¸à¤¾à¤®à¤¾à¤œà¤¿à¤• à¤¨à¥‡à¤Ÿà¤µà¤°à¥à¤•"),
    PITRA_AREA_LIBERATION("Spiritual liberation and foreign lands", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤®à¥à¤•à¥à¤¤à¤¿ à¤° à¤µà¤¿à¤¦à¥‡à¤¶"),

    // Pitra remedy timings
    PITRA_TIMING_AMAVASYA("Amavasya (New Moon) or Pitru Paksha", "à¤”à¤‚à¤¸à¥€ à¤µà¤¾ à¤ªà¤¿à¤¤à¥ƒ à¤ªà¤•à¥à¤·"),
    PITRA_TIMING_DEATH_ANNIVERSARY("Father's death anniversary or Pitru Paksha", "à¤ªà¤¿à¤¤à¤¾à¤•à¥‹ à¤ªà¥à¤£à¥à¤¯à¤¤à¤¿à¤¥à¤¿ à¤µà¤¾ à¤ªà¤¿à¤¤à¥ƒ à¤ªà¤•à¥à¤·"),
    PITRA_TIMING_DAILY_PITRU("Daily, especially during Pitru Paksha", "à¤¦à¥ˆà¤¨à¤¿à¤•, à¤µà¤¿à¤¶à¥‡à¤· à¤—à¤°à¥€ à¤ªà¤¿à¤¤à¥ƒ à¤ªà¤•à¥à¤·à¤®à¤¾"),
    PITRA_TIMING_NARAYAN_BALI("Once in lifetime at Trimbakeshwar or Gaya", "à¤œà¥€à¤µà¤¨à¤•à¤¾à¤²à¤®à¤¾ à¤à¤•à¤ªà¤Ÿà¤• à¤¤à¥à¤°à¥à¤¯à¤®à¥à¤¬à¤•à¥‡à¤¶à¥à¤µà¤° à¤µà¤¾ à¤—à¤¯à¤¾à¤®à¤¾"),
    PITRA_TIMING_PIND_DAAN("Pitru Paksha at Gaya", "à¤—à¤¯à¤¾à¤®à¤¾ à¤ªà¤¿à¤¤à¥ƒ à¤ªà¤•à¥à¤·"),
    PITRA_TIMING_BRAHMA_MUHURTA("Daily during Brahma Muhurta", "à¤¬à¥à¤°à¤¹à¥à¤® à¤®à¥à¤¹à¥‚à¤°à¥à¤¤à¤®à¤¾ à¤¦à¥ˆà¤¨à¤¿à¤•"),

    // Pitra auspicious periods
    PITRA_PERIOD_PITRU_PAKSHA("Pitru Paksha (15-day period in Bhadrapada month)", "à¤ªà¤¿à¤¤à¥ƒ à¤ªà¤•à¥à¤· (à¤­à¤¾à¤¦à¥à¤° à¤®à¤¹à¤¿à¤¨à¤¾à¤®à¤¾ à¥§à¥« à¤¦à¤¿à¤¨à¤•à¥‹ à¤…à¤µà¤§à¤¿)"),
    PITRA_PERIOD_AMAVASYA("Amavasya (New Moon days)", "à¤”à¤‚à¤¸à¥€ (à¤…à¤®à¤¾à¤µà¤¸à¥à¤¯à¤¾ à¤¦à¤¿à¤¨à¤¹à¤°à¥‚)"),
    PITRA_PERIOD_ECLIPSE("Solar/Lunar eclipses", "à¤¸à¥‚à¤°à¥à¤¯/à¤šà¤¨à¥à¤¦à¥à¤° à¤—à¥à¤°à¤¹à¤£"),
    PITRA_PERIOD_DEATH_ANNIV("Father's death anniversary", "à¤ªà¤¿à¤¤à¤¾à¤•à¥‹ à¤ªà¥à¤£à¥à¤¯à¤¤à¤¿à¤¥à¤¿"),
    PITRA_PERIOD_MAHALAYA("Mahalaya Amavasya", "à¤®à¤¹à¤¾à¤²à¤¯ à¤…à¤®à¤¾à¤µà¤¸à¥à¤¯à¤¾"),
    PITRA_PERIOD_AKSHAYA("Akshaya Tritiya", "à¤…à¤•à¥à¤·à¤¯ à¤¤à¥ƒà¤¤à¥€à¤¯à¤¾"),
    PITRA_PERIOD_GAYA("Gaya Shraddha periods", "à¤—à¤¯à¤¾ à¤¶à¥à¤°à¤¾à¤¦à¥à¤§ à¤…à¤µà¤§à¤¿à¤¹à¤°à¥‚"),

    // ============================================
    // SADE SATI INTERPRETATION
    // ============================================
    SADE_SATI_ACTIVE_HEADER("SADE SATI ACTIVE - %s PHASE", "à¤¸à¤¾à¤¢à¤¼à¥‡ à¤¸à¤¾à¤¤à¥€ à¤¸à¤•à¥à¤°à¤¿à¤¯ - %s à¤šà¤°à¤£"),
    SADE_SATI_TRANSIT_DESC(
        "Saturn is currently transiting %s, which is the %s from your natal Moon in %s.",
        "à¤¶à¤¨à¤¿ à¤¹à¤¾à¤² %s à¤®à¤¾ à¤—à¥‹à¤šà¤° à¤—à¤°à¥à¤¦à¥ˆà¤›, à¤œà¥à¤¨ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤œà¤¨à¥à¤® à¤šà¤¨à¥à¤¦à¥à¤° %s à¤¬à¤¾à¤Ÿ %s à¤¹à¥‹à¥¤"
    ),
    SADE_SATI_12TH_HOUSE("12th house", "à¥§à¥¨à¤”à¤‚ à¤­à¤¾à¤µ"),
    SADE_SATI_SAME_SIGN("same sign", "à¤‰à¤¹à¥€ à¤°à¤¾à¤¶à¤¿"),
    SADE_SATI_2ND_HOUSE("2nd house", "à¥¨à¤”à¤‚ à¤­à¤¾à¤µ"),
    SADE_SATI_RISING_HEADER("RISING PHASE CHARACTERISTICS:", "à¤‰à¤¦à¤¯ à¤šà¤°à¤£ à¤µà¤¿à¤¶à¥‡à¤·à¤¤à¤¾à¤¹à¤°à¥‚:"),
    SADE_SATI_RISING_BEGIN("Beginning of Sade Sati period", "à¤¸à¤¾à¤¢à¤¼à¥‡ à¤¸à¤¾à¤¤à¥€ à¤…à¤µà¤§à¤¿à¤•à¥‹ à¤¸à¥à¤°à¥à¤µà¤¾à¤¤"),
    SADE_SATI_RISING_EXPENSES("Focus on expenses and losses (12th house)", "à¤–à¤°à¥à¤š à¤° à¤¹à¤¾à¤¨à¤¿à¤®à¤¾ à¤•à¥‡à¤¨à¥à¤¦à¥à¤°à¤¿à¤¤ (à¥§à¥¨à¤”à¤‚ à¤­à¤¾à¤µ)"),
    SADE_SATI_RISING_SLEEP("Sleep disturbances possible", "à¤¨à¤¿à¤¦à¥à¤°à¤¾à¤®à¤¾ à¤—à¤¡à¤¬à¤¡à¥€ à¤¸à¤®à¥à¤­à¤µ"),
    SADE_SATI_RISING_ENEMIES("Hidden enemies may become active", "à¤²à¥à¤•à¥‡à¤•à¤¾ à¤¶à¤¤à¥à¤°à¥à¤¹à¤°à¥‚ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›à¤¨à¥"),
    SADE_SATI_RISING_SPIRITUAL("Spiritual growth opportunities", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤µà¥ƒà¤¦à¥à¤§à¤¿à¤•à¤¾ à¤…à¤µà¤¸à¤°à¤¹à¤°à¥‚"),
    SADE_SATI_PEAK_HEADER("PEAK PHASE CHARACTERISTICS:", "à¤¶à¤¿à¤–à¤° à¤šà¤°à¤£ à¤µà¤¿à¤¶à¥‡à¤·à¤¤à¤¾à¤¹à¤°à¥‚:"),
    SADE_SATI_PEAK_INTENSE("Most intense phase of Sade Sati", "à¤¸à¤¾à¤¢à¤¼à¥‡ à¤¸à¤¾à¤¤à¥€ à¤•à¥‹ à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤¤à¥€à¤µà¥à¤° à¤šà¤°à¤£"),
    SADE_SATI_PEAK_MIND("Direct impact on mind and emotions", "à¤®à¤¨ à¤° à¤­à¤¾à¤µà¤¨à¤¾à¤¹à¤°à¥‚à¤®à¤¾ à¤ªà¥à¤°à¤¤à¥à¤¯à¤•à¥à¤· à¤ªà¥à¤°à¤­à¤¾à¤µ"),
    SADE_SATI_PEAK_HEALTH("Health may need attention", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯à¤®à¤¾ à¤§à¥à¤¯à¤¾à¤¨ à¤†à¤µà¤¶à¥à¤¯à¤• à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›"),
    SADE_SATI_PEAK_SELF("Self-image transformation", "à¤†à¤¤à¥à¤®-à¤›à¤µà¤¿ à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£"),
    SADE_SATI_PEAK_RESTRUCTURE("Major life restructuring possible", "à¤ªà¥à¤°à¤®à¥à¤– à¤œà¥€à¤µà¤¨ à¤ªà¥à¤¨à¤°à¥à¤¸à¤‚à¤°à¤šà¤¨à¤¾ à¤¸à¤®à¥à¤­à¤µ"),
    SADE_SATI_SETTING_HEADER("SETTING PHASE CHARACTERISTICS:", "à¤…à¤¸à¥à¤¤ à¤šà¤°à¤£ à¤µà¤¿à¤¶à¥‡à¤·à¤¤à¤¾à¤¹à¤°à¥‚:"),
    SADE_SATI_SETTING_FINAL("Final phase of Sade Sati", "à¤¸à¤¾à¤¢à¤¼à¥‡ à¤¸à¤¾à¤¤à¥€ à¤•à¥‹ à¤…à¤¨à¥à¤¤à¤¿à¤® à¤šà¤°à¤£"),
    SADE_SATI_SETTING_FINANCES("Focus on finances and family (2nd house)", "à¤µà¤¿à¤¤à¥à¤¤ à¤° à¤ªà¤°à¤¿à¤µà¤¾à¤°à¤®à¤¾ à¤•à¥‡à¤¨à¥à¤¦à¥à¤°à¤¿à¤¤ (à¥¨à¤”à¤‚ à¤­à¤¾à¤µ)"),
    SADE_SATI_SETTING_SPEECH("Speech and communication impacted", "à¤µà¤¾à¤£à¥€ à¤° à¤¸à¤‚à¤šà¤¾à¤° à¤ªà¥à¤°à¤­à¤¾à¤µà¤¿à¤¤"),
    SADE_SATI_SETTING_WEALTH("Accumulated wealth may fluctuate", "à¤¸à¤‚à¤šà¤¿à¤¤ à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿à¤®à¤¾ à¤‰à¤¤à¤¾à¤°à¤šà¤¢à¤¾à¤µ"),
    SADE_SATI_SETTING_LESSONS("Integration of lessons learned", "à¤¸à¤¿à¤•à¥‡à¤•à¤¾ à¤ªà¤¾à¤ à¤¹à¤°à¥‚à¤•à¥‹ à¤à¤•à¥€à¤•à¤°à¤£"),
    SADE_SATI_NOT_ACTIVE_HEADER("SADE SATI NOT ACTIVE", "à¤¸à¤¾à¤¢à¤¼à¥‡ à¤¸à¤¾à¤¤à¥€ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤›à¥ˆà¤¨"),
    SADE_SATI_NOT_ACTIVE_DESC(
        "Saturn is currently transiting %s, which does not form Sade Sati or Small Panoti with your natal Moon in %s.",
        "à¤¶à¤¨à¤¿ à¤¹à¤¾à¤² %s à¤®à¤¾ à¤—à¥‹à¤šà¤° à¤—à¤°à¥à¤¦à¥ˆà¤›, à¤œà¤¸à¤²à¥‡ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤œà¤¨à¥à¤® à¤šà¤¨à¥à¤¦à¥à¤° %s à¤¸à¤à¤— à¤¸à¤¾à¤¢à¤¼à¥‡ à¤¸à¤¾à¤¤à¥€ à¤µà¤¾ à¤¸à¤¾à¤¨à¥‹ à¤ªà¤¨à¥‹à¤¤à¥€ à¤¬à¤¨à¤¾à¤‰à¤à¤¦à¥ˆà¤¨à¥¤"
    ),
    SADE_SATI_FAVORABLE_PERIOD(
        "This is generally a favorable period regarding Saturn's influence on emotional and mental well-being.",
        "à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤° à¤®à¤¾à¤¨à¤¸à¤¿à¤• à¤•à¤²à¥à¤¯à¤¾à¤£à¤®à¤¾ à¤¶à¤¨à¤¿à¤•à¥‹ à¤ªà¥à¤°à¤­à¤¾à¤µ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤®à¤¾ à¤¯à¥‹ à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¯à¤¾ à¤…à¤¨à¥à¤•à¥‚à¤² à¤…à¤µà¤§à¤¿ à¤¹à¥‹à¥¤"
    ),
    SADE_SATI_SMALL_PANOTI_HEADER("SMALL PANOTI (DHAIYA) ACTIVE", "à¤¸à¤¾à¤¨à¥‹ à¤ªà¤¨à¥‹à¤¤à¥€ (à¤¢à¥ˆà¤¯à¤¾) à¤¸à¤•à¥à¤°à¤¿à¤¯"),
    SADE_SATI_FOURTH_TRANSIT("Saturn is transiting the 4th house from your Moon.", "à¤¶à¤¨à¤¿ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤¾à¤Ÿ à¥ªà¤”à¤‚ à¤­à¤¾à¤µà¤®à¤¾ à¤—à¥‹à¤šà¤° à¤—à¤°à¥à¤¦à¥ˆà¤›à¥¤"),
    SADE_SATI_FOURTH_DOMESTIC("Domestic peace may be disturbed", "à¤˜à¤°à¥‡à¤²à¥ à¤¶à¤¾à¤¨à¥à¤¤à¤¿ à¤…à¤¶à¤¾à¤¨à¥à¤¤ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›"),
    SADE_SATI_FOURTH_MOTHER("Mother's health may need attention", "à¤†à¤®à¤¾à¤•à¥‹ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯à¤®à¤¾ à¤§à¥à¤¯à¤¾à¤¨ à¤†à¤µà¤¶à¥à¤¯à¤•"),
    SADE_SATI_FOURTH_PROPERTY("Property matters require caution", "à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿ à¤®à¤¾à¤®à¤¿à¤²à¤¾à¤¹à¤°à¥‚à¤®à¤¾ à¤¸à¤¾à¤µà¤§à¤¾à¤¨à¥€ à¤†à¤µà¤¶à¥à¤¯à¤•"),
    SADE_SATI_FOURTH_MENTAL("Mental peace may fluctuate", "à¤®à¤¾à¤¨à¤¸à¤¿à¤• à¤¶à¤¾à¤¨à¥à¤¤à¤¿à¤®à¤¾ à¤‰à¤¤à¤¾à¤°à¤šà¤¢à¤¾à¤µ"),
    SADE_SATI_ASHTAMA_HEADER("ASHTAMA SHANI - Saturn in 8th from Moon", "à¤…à¤·à¥à¤Ÿà¤® à¤¶à¤¨à¤¿ - à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤¾à¤Ÿ à¥®à¤”à¤‚ à¤®à¤¾ à¤¶à¤¨à¤¿"),
    SADE_SATI_ASHTAMA_CHALLENGING(
        "This is considered one of the most challenging Saturn transits.",
        "à¤¯à¥‹ à¤¶à¤¨à¤¿à¤•à¥‹ à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£ à¤—à¥‹à¤šà¤° à¤®à¤§à¥à¤¯à¥‡ à¤à¤• à¤®à¤¾à¤¨à¤¿à¤¨à¥à¤›à¥¤"
    ),
    SADE_SATI_ASHTAMA_CHANGES("Sudden changes and transformations", "à¤…à¤šà¤¾à¤¨à¤• à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨ à¤° à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£à¤¹à¤°à¥‚"),
    SADE_SATI_ASHTAMA_HEALTH("Health requires vigilance", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯à¤®à¤¾ à¤¸à¤¤à¤°à¥à¤•à¤¤à¤¾ à¤†à¤µà¤¶à¥à¤¯à¤•"),
    SADE_SATI_ASHTAMA_OBSTACLES("Obstacles in ventures", "à¤‰à¤¦à¥à¤¯à¤®à¤¹à¤°à¥‚à¤®à¤¾ à¤…à¤µà¤°à¥‹à¤§à¤¹à¤°à¥‚"),
    SADE_SATI_ASHTAMA_PSYCHOLOGICAL("Deep psychological transformation", "à¤—à¤¹à¤¿à¤°à¥‹ à¤®à¤¨à¥‹à¤µà¥ˆà¤œà¥à¤žà¤¾à¤¨à¤¿à¤• à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£"),

    // Sade Sati favorable/challenging factors
    SADE_SATI_FACTOR_EXALTED("Saturn is exalted in transit - effects significantly reduced", "à¤¶à¤¨à¤¿ à¤—à¥‹à¤šà¤°à¤®à¤¾ à¤‰à¤šà¥à¤š - à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚ à¤‰à¤²à¥à¤²à¥‡à¤–à¤¨à¥€à¤¯ à¤•à¤®"),
    SADE_SATI_FACTOR_OWN_SIGN("Saturn is in own sign - effects well-managed", "à¤¶à¤¨à¤¿ à¤¸à¥à¤µà¤°à¤¾à¤¶à¤¿à¤®à¤¾ - à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚ à¤°à¤¾à¤®à¥à¤°à¥‹à¤¸à¤à¤— à¤µà¥à¤¯à¤µà¤¸à¥à¤¥à¤¿à¤¤"),
    SADE_SATI_FACTOR_YOGAKARAKA("Saturn is Yogakaraka for your ascendant - may bring positive results", "à¤¶à¤¨à¤¿ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤²à¤—à¥à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¯à¥‹à¤—à¤•à¤¾à¤°à¤• - à¤¸à¤•à¤¾à¤°à¤¾à¤¤à¥à¤®à¤• à¤ªà¤°à¤¿à¤£à¤¾à¤® à¤²à¥à¤¯à¤¾à¤‰à¤¨ à¤¸à¤•à¥à¤›"),
    SADE_SATI_FACTOR_NATAL_STRONG("Natal Saturn is strong - better equipped to handle transit", "à¤œà¤¨à¥à¤® à¤¶à¤¨à¤¿ à¤¬à¤²à¤¿à¤¯à¥‹ - à¤—à¥‹à¤šà¤° à¤¸à¤®à¥à¤¹à¤¾à¤²à¥à¤¨ à¤°à¤¾à¤®à¥à¤°à¥‹ à¤¸à¥à¤¸à¤œà¥à¤œà¤¿à¤¤"),
    SADE_SATI_FACTOR_DEBILITATED("Saturn is debilitated in transit - effects may be more challenging", "à¤¶à¤¨à¤¿ à¤—à¥‹à¤šà¤°à¤®à¤¾ à¤¨à¥€à¤š - à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚ à¤¥à¤ª à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›"),
    SADE_SATI_FACTOR_WEAK_MOON("Natal Moon is weak - emotional resilience may be tested", "à¤œà¤¨à¥à¤® à¤šà¤¨à¥à¤¦à¥à¤° à¤•à¤®à¤œà¥‹à¤° - à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤²à¤šà¤¿à¤²à¥‹à¤ªà¤¨ à¤ªà¤°à¥€à¤•à¥à¤·à¤£ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›"),
    SADE_SATI_FACTOR_NATAL_WEAK("Natal Saturn is weak - transit effects may be more pronounced", "à¤œà¤¨à¥à¤® à¤¶à¤¨à¤¿ à¤•à¤®à¤œà¥‹à¤° - à¤—à¥‹à¤šà¤° à¤ªà¥à¤°à¤­à¤¾à¤µ à¤¥à¤ª à¤‰à¤šà¥à¤šà¤¾à¤°à¤¿à¤¤ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›"),
    SADE_SATI_FACTOR_RETROGRADE("Natal Saturn is retrograde - internal processing of karmic lessons", "à¤œà¤¨à¥à¤® à¤¶à¤¨à¤¿ à¤µà¤•à¥à¤°à¥€ - à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤ªà¤¾à¤ à¤¹à¤°à¥‚à¤•à¥‹ à¤†à¤¨à¥à¤¤à¤°à¤¿à¤• à¤ªà¥à¤°à¤¶à¥‹à¤§à¤¨"),

    // Sade Sati remedy timings
    SADE_SATI_TIMING_SATURN_HORA("Saturday during Saturn Hora", "à¤¶à¤¨à¤¿ à¤¹à¥‹à¤°à¤¾à¤®à¤¾ à¤¶à¤¨à¤¿à¤¬à¤¾à¤°"),
    SADE_SATI_TIMING_EVERY_SATURDAY("Every Saturday", "à¤¹à¤°à¥‡à¤• à¤¶à¤¨à¤¿à¤¬à¤¾à¤°"),
    SADE_SATI_TIMING_TUE_SAT("Tuesday and Saturday", "à¤®à¤‚à¤—à¤²à¤¬à¤¾à¤° à¤° à¤¶à¤¨à¤¿à¤¬à¤¾à¤°"),

    // Error messages
    ERROR_MOON_NOT_FOUND("Unable to calculate - Moon position not found", "à¤—à¤£à¤¨à¤¾ à¤—à¤°à¥à¤¨ à¤…à¤¸à¤®à¤°à¥à¤¥ - à¤šà¤¨à¥à¤¦à¥à¤° à¤¸à¥à¤¥à¤¿à¤¤à¤¿ à¤«à¥‡à¤²à¤¾ à¤ªà¤°à¥‡à¤¨"),
    ERROR_SADE_SATI_CALC("Unable to calculate Sade Sati - Moon position not available in chart.", "à¤¸à¤¾à¤¢à¤¼à¥‡ à¤¸à¤¾à¤¤à¥€ à¤—à¤£à¤¨à¤¾ à¤—à¤°à¥à¤¨ à¤…à¤¸à¤®à¤°à¥à¤¥ - à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾ à¤šà¤¨à¥à¤¦à¥à¤° à¤¸à¥à¤¥à¤¿à¤¤à¤¿ à¤‰à¤ªà¤²à¤¬à¥à¤§ à¤›à¥ˆà¤¨à¥¤"),
    ERROR_BAV_NOT_FOUND("Bhinnashtakavarga not found for %s", "%s à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤­à¤¿à¤¨à¥à¤¨à¤¾à¤·à¥à¤Ÿà¤•à¤µà¤°à¥à¤— à¤«à¥‡à¤²à¤¾ à¤ªà¤°à¥‡à¤¨"),
    ERROR_ASHTAKAVARGA_NOT_APPLICABLE("Ashtakavarga not applicable for %s", "%s à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤…à¤·à¥à¤Ÿà¤•à¤µà¤°à¥à¤— à¤²à¤¾à¤—à¥‚ à¤¹à¥à¤à¤¦à¥ˆà¤¨"),

    // ============================================
    // SYNASTRY SCREEN STRINGS
    // ============================================
    SYNASTRY_LAGNA("Lagna", "à¤²à¤—à¥à¤¨"),
    SYNASTRY_MOON("Moon", "à¤šà¤¨à¥à¤¦à¥à¤°"),
    SYNASTRY_VENUS("Venus", "à¤¶à¥à¤•à¥à¤°"),
    SYNASTRY_HOUSE_IN("in House", "à¤­à¤¾à¤µà¤®à¤¾"),

    // ============================================
    // BHRIGU BINDU ANALYSIS
    // ============================================
    BHRIGU_BINDU_TITLE("Bhrigu Bindu Analysis", "à¤­à¥ƒà¤—à¥ à¤¬à¤¿à¤¨à¥à¤¦à¥ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    BHRIGU_BINDU_SUBTITLE("Karmic Destiny Point", "à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤­à¤¾à¤—à¥à¤¯ à¤¬à¤¿à¤¨à¥à¤¦à¥"),
    BHRIGU_BINDU_LONGITUDE("Bhrigu Bindu Longitude", "à¤­à¥ƒà¤—à¥ à¤¬à¤¿à¤¨à¥à¤¦à¥ à¤¦à¥‡à¤¶à¤¾à¤¨à¥à¤¤à¤°"),
    BHRIGU_BINDU_SIGN("Sign", "à¤°à¤¾à¤¶à¤¿"),
    BHRIGU_BINDU_NAKSHATRA("Nakshatra", "à¤¨à¤•à¥à¤·à¤¤à¥à¤°"),
    BHRIGU_BINDU_PADA("Pada", "à¤ªà¤¦"),
    BHRIGU_BINDU_HOUSE("House", "à¤­à¤¾à¤µ"),
    BHRIGU_BINDU_LORD("Sign Lord", "à¤°à¤¾à¤¶à¤¿ à¤¸à¥à¤µà¤¾à¤®à¥€"),
    BHRIGU_BINDU_NAKSHATRA_LORD("Nakshatra Lord", "à¤¨à¤•à¥à¤·à¤¤à¥à¤° à¤¸à¥à¤µà¤¾à¤®à¥€"),
    BHRIGU_BINDU_STRENGTH("Strength Assessment", "à¤¶à¤•à¥à¤¤à¤¿ à¤®à¥‚à¤²à¥à¤¯à¤¾à¤‚à¤•à¤¨"),
    BHRIGU_BINDU_EXCELLENT("Excellent", "à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ"),
    BHRIGU_BINDU_GOOD("Good", "à¤°à¤¾à¤®à¥à¤°à¥‹"),
    BHRIGU_BINDU_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    BHRIGU_BINDU_CHALLENGING("Challenging", "à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£"),
    BHRIGU_BINDU_DIFFICULT("Difficult", "à¤•à¤ à¤¿à¤¨"),
    BHRIGU_BINDU_ASPECTS("Aspecting Planets", "à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿ à¤—à¤°à¥à¤¨à¥‡ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    BHRIGU_BINDU_CONJUNCTIONS("Conjunct Planets", "à¤¯à¥à¤¤à¤¿ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    BHRIGU_BINDU_TRANSITS("Transit Analysis", "à¤—à¥‹à¤šà¤° à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    BHRIGU_BINDU_KARMIC_SIGNIFICANCE("Karmic Significance", "à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤®à¤¹à¤¤à¥à¤¤à¥à¤µ"),
    BHRIGU_BINDU_LIFE_AREAS("Life Area Influences", "à¤œà¥€à¤µà¤¨ à¤•à¥à¤·à¥‡à¤¤à¥à¤° à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    BHRIGU_BINDU_RECOMMENDATIONS("Recommendations", "à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸à¤¹à¤°à¥‚"),
    BHRIGU_BINDU_AUSPICIOUS_DAYS("Auspicious Days", "à¤¶à¥à¤­ à¤¦à¤¿à¤¨à¤¹à¤°à¥‚"),
    BHRIGU_BINDU_REMEDIES("Remedial Measures", "à¤‰à¤ªà¤šà¤¾à¤°à¤¾à¤¤à¥à¤®à¤• à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    BHRIGU_BINDU_ABOUT("About Bhrigu Bindu", "à¤­à¥ƒà¤—à¥ à¤¬à¤¿à¤¨à¥à¤¦à¥à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    BHRIGU_BINDU_ABOUT_DESC("Bhrigu Bindu is a sensitive point calculated from the midpoint of Rahu and Moon. It indicates where significant karmic events manifest in life and is used for precise event timing.", "à¤­à¥ƒà¤—à¥ à¤¬à¤¿à¤¨à¥à¤¦à¥ à¤°à¤¾à¤¹à¥ à¤° à¤šà¤¨à¥à¤¦à¥à¤°à¤•à¥‹ à¤®à¤§à¥à¤¯à¤¬à¤¿à¤¨à¥à¤¦à¥à¤¬à¤¾à¤Ÿ à¤—à¤£à¤¨à¤¾ à¤—à¤°à¤¿à¤à¤•à¥‹ à¤¸à¤‚à¤µà¥‡à¤¦à¤¨à¤¶à¥€à¤² à¤¬à¤¿à¤¨à¥à¤¦à¥ à¤¹à¥‹à¥¤ à¤¯à¤¸à¤²à¥‡ à¤œà¥€à¤µà¤¨à¤®à¤¾ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤˜à¤Ÿà¤¨à¤¾à¤¹à¤°à¥‚ à¤•à¤¹à¤¾à¤ à¤ªà¥à¤°à¤•à¤Ÿ à¤¹à¥à¤¨à¥à¤›à¤¨à¥ à¤­à¤¨à¥‡à¤° à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤› à¤° à¤¸à¤Ÿà¥€à¤• à¤˜à¤Ÿà¤¨à¤¾ à¤¸à¤®à¤¯à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¤¿à¤¨à¥à¤›à¥¤"),
    BHRIGU_BINDU_CALCULATION("Calculation: (Rahu + Moon) / 2", "à¤—à¤£à¤¨à¤¾: (à¤°à¤¾à¤¹à¥ + à¤šà¤¨à¥à¤¦à¥à¤°) / à¥¨"),
    BHRIGU_BINDU_RAHU_POSITION("Rahu Position", "à¤°à¤¾à¤¹à¥ à¤¸à¥à¤¥à¤¿à¤¤à¤¿"),
    BHRIGU_BINDU_MOON_POSITION("Moon Position", "à¤šà¤¨à¥à¤¦à¥à¤° à¤¸à¥à¤¥à¤¿à¤¤à¤¿"),

    // ============================================
    // YOGINI DASHA SYSTEM
    // ============================================
    YOGINI_DASHA_TITLE("Yogini Dasha", "à¤¯à¥‹à¤—à¤¿à¤¨à¥€ à¤¦à¤¶à¤¾"),
    YOGINI_DASHA_SUBTITLE("36-Year Cycle Dasha System", "à¥©à¥¬-à¤µà¤°à¥à¤·à¥‡ à¤šà¤•à¥à¤° à¤¦à¤¶à¤¾ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€"),
    YOGINI_DASHA_ABOUT("About Yogini Dasha", "à¤¯à¥‹à¤—à¤¿à¤¨à¥€ à¤¦à¤¶à¤¾à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    YOGINI_DASHA_ABOUT_DESC("Yogini Dasha is a nakshatra-based timing system with a 36-year cycle. It is particularly effective for female horoscopes and relationship timing.", "à¤¯à¥‹à¤—à¤¿à¤¨à¥€ à¤¦à¤¶à¤¾ à¥©à¥¬-à¤µà¤°à¥à¤·à¥‡ à¤šà¤•à¥à¤°à¤•à¥‹ à¤¸à¤¾à¤¥ à¤¨à¤•à¥à¤·à¤¤à¥à¤°à¤®à¤¾ à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤¸à¤®à¤¯ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤¹à¥‹à¥¤ à¤¯à¥‹ à¤®à¤¹à¤¿à¤²à¤¾ à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤° à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤¸à¤®à¤¯à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤µà¤¿à¤¶à¥‡à¤· à¤ªà¥à¤°à¤­à¤¾à¤µà¤•à¤¾à¤°à¥€ à¤›à¥¤"),
    YOGINI_DASHA_CURRENT("Current Yogini", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤¯à¥‹à¤—à¤¿à¤¨à¥€"),
    YOGINI_DASHA_ANTARDASHA("Current Antardasha", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤…à¤¨à¥à¤¤à¤°à¥à¤¦à¤¶à¤¾"),
    YOGINI_DASHA_BALANCE("Balance at Birth", "à¤œà¤¨à¥à¤®à¤®à¤¾ à¤¸à¤¨à¥à¤¤à¥à¤²à¤¨"),
    YOGINI_DASHA_SEQUENCE("Yogini Sequence", "à¤¯à¥‹à¤—à¤¿à¤¨à¥€ à¤•à¥à¤°à¤®"),
    YOGINI_DASHA_APPLICABILITY("Applicability", "à¤ªà¥à¤°à¤¯à¥‹à¤œà¥à¤¯à¤¤à¤¾"),
    YOGINI_DASHA_RECOMMENDED("Recommended for this chart", "à¤¯à¥‹ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸"),
    YOGINI_DASHA_TIMELINE("Dasha Timeline", "à¤¦à¤¶à¤¾ à¤¸à¤®à¤¯à¤°à¥‡à¤–à¤¾"),

    // Yogini Names
    YOGINI_MANGALA("Mangala", "à¤®à¤‚à¤—à¤²à¤¾"),
    YOGINI_MANGALA_DESC("Auspicious beginnings, prosperity, and happiness. Moon's nurturing energy.", "à¤¶à¥à¤­ à¤¶à¥à¤°à¥à¤†à¤¤, à¤¸à¤®à¥ƒà¤¦à¥à¤§à¤¿, à¤° à¤–à¥à¤¶à¥€à¥¤ à¤šà¤¨à¥à¤¦à¥à¤°à¤•à¥‹ à¤ªà¥‹à¤·à¤£à¤•à¤¾à¤°à¥€ à¤Šà¤°à¥à¤œà¤¾à¥¤"),
    YOGINI_PINGALA("Pingala", "à¤ªà¤¿à¤‚à¤—à¤²à¤¾"),
    YOGINI_PINGALA_DESC("Authority, father-related matters, recognition. Sun's illuminating energy.", "à¤…à¤§à¤¿à¤•à¤¾à¤°, à¤ªà¤¿à¤¤à¤¾-à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¿à¤¤ à¤®à¤¾à¤®à¤¿à¤²à¤¾à¤¹à¤°à¥‚, à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¾à¥¤ à¤¸à¥‚à¤°à¥à¤¯à¤•à¥‹ à¤ªà¥à¤°à¤•à¤¾à¤¶à¤¿à¤¤ à¤Šà¤°à¥à¤œà¤¾à¥¤"),
    YOGINI_DHANYA("Dhanya", "à¤§à¤¾à¤¨à¥à¤¯à¤¾"),
    YOGINI_DHANYA_DESC("Wealth, wisdom, children, spiritual growth. Jupiter's expansive grace.", "à¤§à¤¨, à¤œà¥à¤žà¤¾à¤¨, à¤¸à¤¨à¥à¤¤à¤¾à¤¨, à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤µà¥ƒà¤¦à¥à¤§à¤¿à¥¤ à¤—à¥à¤°à¥à¤•à¥‹ à¤µà¤¿à¤¸à¥à¤¤à¤¾à¤°à¤¿à¤¤ à¤•à¥ƒà¤ªà¤¾à¥¤"),
    YOGINI_BHRAMARI("Bhramari", "à¤­à¥à¤°à¤¾à¤®à¤°à¥€"),
    YOGINI_BHRAMARI_DESC("Energy, conflicts, property matters. Mars brings action and courage.", "à¤Šà¤°à¥à¤œà¤¾, à¤¦à¥à¤µà¤¨à¥à¤¦à¥à¤µ, à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿ à¤®à¤¾à¤®à¤¿à¤²à¤¾à¤¹à¤°à¥‚à¥¤ à¤®à¤‚à¤—à¤²à¤²à¥‡ à¤•à¤¾à¤°à¥à¤¯ à¤° à¤¸à¤¾à¤¹à¤¸ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),
    YOGINI_BHADRIKA("Bhadrika", "à¤­à¤¦à¥à¤°à¤¿à¤•à¤¾"),
    YOGINI_BHADRIKA_DESC("Intelligence, communication, business success. Mercury's intellectual wit.", "à¤¬à¥à¤¦à¥à¤§à¤¿, à¤¸à¤žà¥à¤šà¤¾à¤°, à¤µà¥à¤¯à¤¾à¤ªà¤¾à¤° à¤¸à¤«à¤²à¤¤à¤¾à¥¤ à¤¬à¥à¤§à¤•à¥‹ à¤¬à¥Œà¤¦à¥à¤§à¤¿à¤• à¤•à¥à¤¶à¤²à¤¤à¤¾à¥¤"),
    YOGINI_ULKA("Ulka", "à¤‰à¤²à¥à¤•à¤¾"),
    YOGINI_ULKA_DESC("Hardship, discipline, eventual success. Saturn teaches patience.", "à¤•à¤ à¤¿à¤¨à¤¾à¤ˆ, à¤…à¤¨à¥à¤¶à¤¾à¤¸à¤¨, à¤…à¤¨à¥à¤¤à¤¤à¤ƒ à¤¸à¤«à¤²à¤¤à¤¾à¥¤ à¤¶à¤¨à¤¿à¤²à¥‡ à¤§à¥ˆà¤°à¥à¤¯ à¤¸à¤¿à¤•à¤¾à¤‰à¤à¤›à¥¤"),
    YOGINI_SIDDHA("Siddha", "à¤¸à¤¿à¤¦à¥à¤§à¤¾"),
    YOGINI_SIDDHA_DESC("Success, luxury, marriage, artistic achievements. Venus brings love.", "à¤¸à¤«à¤²à¤¤à¤¾, à¤µà¤¿à¤²à¤¾à¤¸à¤¿à¤¤à¤¾, à¤µà¤¿à¤µà¤¾à¤¹, à¤•à¤²à¤¾à¤¤à¥à¤®à¤• à¤‰à¤ªà¤²à¤¬à¥à¤§à¤¿à¤¹à¤°à¥‚à¥¤ à¤¶à¥à¤•à¥à¤°à¤²à¥‡ à¤ªà¥à¤°à¥‡à¤® à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),
    YOGINI_SANKATA("Sankata", "à¤¸à¤‚à¤•à¤Ÿà¤¾"),
    YOGINI_SANKATA_DESC("Obstacles, foreign influences, sudden changes. Rahu brings transformation.", "à¤…à¤µà¤°à¥‹à¤§à¤¹à¤°à¥‚, à¤µà¤¿à¤¦à¥‡à¤¶à¥€ à¤ªà¥à¤°à¤­à¤¾à¤µ, à¤…à¤šà¤¾à¤¨à¤• à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨à¥¤ à¤°à¤¾à¤¹à¥à¤²à¥‡ à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),

    // Yogini period durations
    YOGINI_YEARS_1("1 Year", "à¥§ à¤µà¤°à¥à¤·"),
    YOGINI_YEARS_2("2 Years", "à¥¨ à¤µà¤°à¥à¤·"),
    YOGINI_YEARS_3("3 Years", "à¥© à¤µà¤°à¥à¤·"),
    YOGINI_YEARS_4("4 Years", "à¥ª à¤µà¤°à¥à¤·"),
    YOGINI_YEARS_5("5 Years", "à¥« à¤µà¤°à¥à¤·"),
    YOGINI_YEARS_6("6 Years", "à¥¬ à¤µà¤°à¥à¤·"),
    YOGINI_YEARS_7("7 Years", "à¥­ à¤µà¤°à¥à¤·"),
    YOGINI_YEARS_8("8 Years", "à¥® à¤µà¤°à¥à¤·"),

    // Yogini nature
    YOGINI_NATURE_HIGHLY_AUSPICIOUS("Highly Auspicious", "à¤…à¤¤à¥à¤¯à¤¨à¥à¤¤ à¤¶à¥à¤­"),
    YOGINI_NATURE_AUSPICIOUS("Auspicious", "à¤¶à¥à¤­"),
    YOGINI_NATURE_MIXED("Mixed", "à¤®à¤¿à¤¶à¥à¤°à¤¿à¤¤"),
    YOGINI_NATURE_CHALLENGING("Challenging", "à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£"),
    YOGINI_NATURE_DIFFICULT("Difficult", "à¤•à¤ à¤¿à¤¨"),

    // Yogini effects labels
    YOGINI_GENERAL_EFFECTS("General Effects", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    YOGINI_CAREER_EFFECTS("Career Effects", "à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤° à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    YOGINI_RELATIONSHIP_EFFECTS("Relationship Effects", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    YOGINI_HEALTH_EFFECTS("Health Effects", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    YOGINI_SPIRITUAL_EFFECTS("Spiritual Effects", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    YOGINI_CAUTION_AREAS("Caution Areas", "à¤¸à¤¾à¤µà¤§à¤¾à¤¨à¥€ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚"),

    // Mangala (Moon)
    YOGINI_MANGALA_CAREER("Career brings emotional satisfaction. Good for nurturing professions, public relations, and hospitality industry.", "à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°à¤²à¥‡ à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤¸à¤¨à¥à¤¤à¥à¤·à¥à¤Ÿà¤¿ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤ à¤¹à¥‡à¤°à¤šà¤¾à¤¹ à¤—à¤°à¥à¤¨à¥‡ à¤ªà¥‡à¤¶à¤¾à¤¹à¤°à¥‚, à¤œà¤¨à¤¸à¤®à¥à¤ªà¤°à¥à¤•, à¤° à¤†à¤¤à¤¿à¤¥à¥à¤¯ à¤‰à¤¦à¥à¤¯à¥‹à¤—à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤°à¤¾à¤®à¥à¤°à¥‹à¥¤"),
    YOGINI_MANGALA_RELATIONSHIP("Emotional connections deepen. Good for starting relationships. Mother's influence prominent. Nurturing partnerships.", "à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤—à¤¹à¤¿à¤°à¥‹ à¤¹à¥à¤¨à¥à¤›à¥¤ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤¸à¥à¤°à¥ à¤—à¤°à¥à¤¨ à¤°à¤¾à¤®à¥à¤°à¥‹à¥¤ à¤†à¤®à¤¾à¤•à¥‹ à¤ªà¥à¤°à¤­à¤¾à¤µ à¤ªà¥à¤°à¤®à¥à¤–à¥¤ à¤ªà¤¾à¤²à¤¨à¤ªà¥‹à¤·à¤£ à¤—à¤°à¥à¤¨à¥‡ à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¥€à¥¤"),
    YOGINI_MANGALA_HEALTH("Generally good health. Focus on emotional and mental well-being. Water-related activities beneficial.", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¯à¤¾ à¤°à¤¾à¤®à¥à¤°à¥‹ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯à¥¤ à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤° à¤®à¤¾à¤¨à¤¸à¤¿à¤• à¤•à¤²à¥à¤¯à¤¾à¤£à¤®à¤¾ à¤§à¥à¤¯à¤¾à¤¨ à¤¦à¤¿à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤ à¤ªà¤¾à¤¨à¥€ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¿à¤¤ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚ à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤•à¥¤"),
    YOGINI_MANGALA_SPIRITUAL("Devotional practices strengthen. Mother goddess worship beneficial. Emotional cleansing through meditation.", "à¤­à¤•à¥à¤¤à¤¿ à¤…à¤­à¥à¤¯à¤¾à¤¸ à¤¬à¤²à¤¿à¤¯à¥‹ à¤¹à¥à¤¨à¥à¤›à¥¤ à¤®à¤¾à¤¤à¥ƒ à¤¦à¥‡à¤µà¥€à¤•à¥‹ à¤ªà¥‚à¤œà¤¾ à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤•à¥¤ à¤§à¥à¤¯à¤¾à¤¨ à¤®à¤¾à¤°à¥à¤«à¤¤ à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤¸à¤«à¤¾à¤ˆà¥¤"),
    YOGINI_MANGALA_REC_1("Wear pearl or moonstone", "à¤®à¥‹à¤¤à¥€ à¤µà¤¾ à¤šà¤¨à¥à¤¦à¥à¤°à¤•à¤¾à¤¨à¥à¤¤ à¤®à¤£à¤¿ à¤²à¤—à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_MANGALA_REC_2("Honor your mother and maternal figures", "à¤†à¤®à¤¾ à¤° à¤®à¤¾à¤¤à¥ƒ à¤µà¥à¤¯à¤•à¥à¤¤à¤¿à¤¤à¥à¤µà¤¹à¤°à¥‚à¤•à¥‹ à¤¸à¤®à¥à¤®à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_MANGALA_REC_3("Practice moon salutations", "à¤šà¤¨à¥à¤¦à¥à¤° à¤¨à¤®à¤¸à¥à¤•à¤¾à¤° à¤…à¤­à¥à¤¯à¤¾à¤¸ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_MANGALA_REC_4("Donate white items on Mondays", "à¤¸à¥‹à¤®à¤¬à¤¾à¤° à¤¸à¥‡à¤¤à¥‹ à¤µà¤¸à¥à¤¤à¥à¤¹à¤°à¥‚ à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_MANGALA_CAUTION_1("Emotional volatility", "à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤…à¤¸à¥à¤¥à¤¿à¤°à¤¤à¤¾"),
    YOGINI_MANGALA_CAUTION_2("Over-attachment", "à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤²à¤—à¤¾à¤µ"),
    YOGINI_MANGALA_CAUTION_3("Water-related issues", "à¤ªà¤¾à¤¨à¥€ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¿à¤¤ à¤¸à¤®à¤¸à¥à¤¯à¤¾à¤¹à¤°à¥‚"),

    // Pingala (Sun)
    YOGINI_PINGALA_CAREER("Leadership opportunities arise. Government jobs, authority positions, and recognition in career. Father may influence career.", "à¤¨à¥‡à¤¤à¥ƒà¤¤à¥à¤µà¤•à¥‹ à¤…à¤µà¤¸à¤° à¤†à¤‰à¤à¤›à¥¤ à¤¸à¤°à¤•à¤¾à¤°à¥€ à¤œà¤¾à¤—à¤¿à¤°, à¤…à¤§à¤¿à¤•à¤¾à¤°à¤¿à¤• à¤ªà¤¦, à¤° à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°à¤®à¤¾ à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¾à¥¤ à¤ªà¤¿à¤¤à¤¾à¤²à¥‡ à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°à¤²à¤¾à¤ˆ à¤ªà¥à¤°à¤­à¤¾à¤µ à¤ªà¤¾à¤°à¥à¤¨ à¤¸à¤•à¥à¤›à¤¨à¥à¥¤"),
    YOGINI_PINGALA_RELATIONSHIP("Relationships with authority figures. Father-related matters in marriage. May face ego conflicts with partners.", "à¤…à¤§à¤¿à¤•à¤¾à¤°à¥€ à¤µà¤°à¥à¤—à¤¸à¤à¤— à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¥¤ à¤µà¤¿à¤µà¤¾à¤¹à¤®à¤¾ à¤ªà¤¿à¤¤à¤¾ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¿à¤¤ à¤µà¤¿à¤·à¤¯à¤¹à¤°à¥‚à¥¤ à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¤¹à¤°à¥‚à¤¸à¤à¤— à¤…à¤¹à¤‚à¤•à¤¾à¤° à¤¦à¥à¤µà¤¨à¥à¤¦à¥à¤µ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤"),
    YOGINI_PINGALA_HEALTH("Watch heart, eyes, and overall vitality. Morning sun exposure beneficial. Maintain healthy ego.", "à¤®à¥à¤Ÿà¥, à¤†à¤à¤–à¤¾, à¤° à¤¸à¤®à¤—à¥à¤° à¤œà¥€à¤µà¤¨à¤¶à¤•à¥à¤¤à¤¿à¤®à¤¾ à¤§à¥à¤¯à¤¾à¤¨ à¤¦à¤¿à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤ à¤¬à¤¿à¤¹à¤¾à¤¨à¥€ à¤˜à¤¾à¤® à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤•à¥¤ à¤¸à¥à¤µà¤¸à¥à¤¥ à¤…à¤¹à¤‚à¤•à¤¾à¤° à¤•à¤¾à¤¯à¤® à¤°à¤¾à¤–à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    YOGINI_PINGALA_SPIRITUAL("Solar meditation and surya namaskar beneficial. Connection with divine father principle. Self-realization focus.", "à¤¸à¥‚à¤°à¥à¤¯ à¤§à¥à¤¯à¤¾à¤¨ à¤° à¤¸à¥‚à¤°à¥à¤¯ à¤¨à¤®à¤¸à¥à¤•à¤¾à¤° à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤•à¥¤ à¤¦à¤¿à¤µà¥à¤¯ à¤ªà¤¿à¤¤à¤¾ à¤¤à¤¤à¥à¤¤à¥à¤µà¤¸à¤à¤— à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¥¤ à¤†à¤¤à¥à¤®-à¤¸à¤¾à¤•à¥à¤·à¤¾à¤¤à¥à¤•à¤¾à¤°à¤®à¤¾ à¤§à¥à¤¯à¤¾à¤¨à¥¤"),
    YOGINI_PINGALA_REC_1("Wear ruby if suitable", "à¤‰à¤ªà¤¯à¥à¤•à¥à¤¤ à¤­à¤à¤®à¤¾ à¤®à¤¾à¤£à¤¿à¤• à¤²à¤—à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_PINGALA_REC_2("Practice Surya Namaskar at sunrise", "à¤¸à¥‚à¤°à¥à¤¯à¥‹à¤¦à¤¯à¤®à¤¾ à¤¸à¥‚à¤°à¥à¤¯ à¤¨à¤®à¤¸à¥à¤•à¤¾à¤° à¤…à¤­à¥à¤¯à¤¾à¤¸ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_PINGALA_REC_3("Honor your father", "à¤ªà¤¿à¤¤à¤¾à¤•à¥‹ à¤¸à¤®à¥à¤®à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_PINGALA_REC_4("Donate wheat or jaggery on Sundays", "à¤†à¤‡à¤¤à¤¬à¤¾à¤° à¤—à¤¹à¥à¤ à¤µà¤¾ à¤¸à¤–à¥à¤–à¤° à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_PINGALA_CAUTION_1("Ego conflicts", "à¤…à¤¹à¤‚à¤•à¤¾à¤° à¤¦à¥à¤µà¤¨à¥à¤¦à¥à¤µ"),
    YOGINI_PINGALA_CAUTION_2("Eye problems", "à¤†à¤à¤–à¤¾ à¤¸à¤®à¤¸à¥à¤¯à¤¾à¤¹à¤°à¥‚"),
    YOGINI_PINGALA_CAUTION_3("Father's health", "à¤ªà¤¿à¤¤à¤¾à¤•à¥‹ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯"),

    // Dhanya (Jupiter)
    YOGINI_DHANYA_CAREER("Expansion in career, promotions, and higher learning. Excellent for teaching, consulting, and advisory roles.", "à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°à¤®à¤¾ à¤µà¤¿à¤¸à¥à¤¤à¤¾à¤°, à¤ªà¤¦à¥‹à¤¨à¥à¤¨à¤¤à¤¿, à¤° à¤‰à¤šà¥à¤š à¤¶à¤¿à¤•à¥à¤·à¤¾à¥¤ à¤¶à¤¿à¤•à¥à¤·à¤£, à¤ªà¤°à¤¾à¤®à¤°à¥à¤¶, à¤° à¤¸à¤²à¥à¤²à¤¾à¤¹à¤•à¤¾à¤° à¤­à¥‚à¤®à¤¿à¤•à¤¾à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿà¥¤"),
    YOGINI_DHANYA_RELATIONSHIP("Excellent for marriage and childbirth. Spiritual connections in relationships. Teachers and mentors become important.", "à¤µà¤¿à¤µà¤¾à¤¹ à¤° à¤¸à¤¨à¥à¤¤à¤¾à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿà¥¤ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤®à¤¾ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¥¤ à¤¶à¤¿à¤•à¥à¤·à¤• à¤° à¤—à¥à¤°à¥à¤¹à¤°à¥‚ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤¹à¥à¤¨à¥à¤›à¤¨à¥à¥¤"),
    YOGINI_DHANYA_HEALTH("Good health generally. Watch liver and weight. Spiritual practices enhance well-being.", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¯à¤¾ à¤°à¤¾à¤®à¥à¤°à¥‹ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯à¥¤ à¤•à¤²à¥‡à¤œà¥‹ à¤° à¤¤à¥Œà¤²à¤®à¤¾ à¤§à¥à¤¯à¤¾à¤¨ à¤¦à¤¿à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤…à¤­à¥à¤¯à¤¾à¤¸à¤²à¥‡ à¤•à¤²à¥à¤¯à¤¾à¤£ à¤¬à¤¢à¤¾à¤‰à¤à¤›à¥¤"),
    YOGINI_DHANYA_SPIRITUAL("Excellent for spiritual growth and higher learning. Guru connection strengthens. Sacred knowledge flows.", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤µà¥ƒà¤¦à¥à¤§à¤¿ à¤° à¤‰à¤šà¥à¤š à¤¶à¤¿à¤•à¥à¤·à¤¾à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿà¥¤ à¤—à¥à¤°à¥ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤¬à¤²à¤¿à¤¯à¥‹ à¤¹à¥à¤¨à¥à¤›à¥¤ à¤ªà¤µà¤¿à¤¤à¥à¤° à¤œà¥à¤žà¤¾à¤¨ à¤ªà¥à¤°à¤µà¤¾à¤¹à¤¿à¤¤ à¤¹à¥à¤¨à¥à¤›à¥¤"),
    YOGINI_DHANYA_REC_1("Wear yellow sapphire if suitable", "à¤‰à¤ªà¤¯à¥à¤•à¥à¤¤ à¤­à¤à¤®à¤¾ à¤ªà¥à¤·à¥à¤ªà¤°à¤¾à¤œ à¤²à¤—à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_DHANYA_REC_2("Seek blessings from teachers", "à¤¶à¤¿à¤•à¥à¤·à¤•à¤¹à¤°à¥‚à¤¬à¤¾à¤Ÿ à¤†à¤¶à¥€à¤°à¥à¤µà¤¾à¤¦ à¤²à¤¿à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_DHANYA_REC_3("Study sacred texts", "à¤ªà¤µà¤¿à¤¤à¥à¤° à¤—à¥à¤°à¤¨à¥à¤¥à¤¹à¤°à¥‚à¤•à¥‹ à¤…à¤§à¥à¤¯à¤¯à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_DHANYA_REC_4("Donate to educational causes on Thursdays", "à¤¬à¤¿à¤¹à¥€à¤¬à¤¾à¤° à¤¶à¥ˆà¤•à¥à¤·à¤¿à¤• à¤•à¤¾à¤°à¥à¤¯à¤®à¤¾ à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_DHANYA_CAUTION_1("Over-expansion", "à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤µà¤¿à¤¸à¥à¤¤à¤¾à¤°"),
    YOGINI_DHANYA_CAUTION_2("Weight issues", "à¤¤à¥Œà¤² à¤¸à¤®à¤¸à¥à¤¯à¤¾à¤¹à¤°à¥‚"),
    YOGINI_DHANYA_CAUTION_3("Over-optimism", "à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤†à¤¶à¤¾à¤µà¤¾à¤¦"),

    // Bhramari (Mars)
    YOGINI_BHRAMARI_CAREER("Active period in career with competition. Good for technical, engineering, and defense-related fields. Property dealings.", "à¤ªà¥à¤°à¤¤à¤¿à¤¸à¥à¤ªà¤°à¥à¤§à¤¾ à¤¸à¤¹à¤¿à¤¤ à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°à¤®à¤¾ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤…à¤µà¤§à¤¿à¥¤ à¤ªà¥à¤°à¤¾à¤µà¤¿à¤§à¤¿à¤•, à¤‡à¤¨à¥à¤œà¤¿à¤¨à¤¿à¤¯à¤°à¤¿à¤™, à¤° à¤°à¤•à¥à¤·à¤¾ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¿à¤¤ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤°à¤¾à¤®à¥à¤°à¥‹à¥¤ à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿ à¤•à¤¾à¤°à¥‹à¤¬à¤¾à¤°à¥¤"),
    YOGINI_BHRAMARI_RELATIONSHIP("Passionate but potentially conflicting relationships. Brothers/sisters prominent. Physical attraction strong.", "à¤œà¥‹à¤¸à¤¿à¤²à¥‹ à¤¤à¤° à¤¸à¤®à¥à¤­à¤¾à¤µà¤¿à¤¤ à¤¦à¥à¤µà¤¨à¥à¤¦à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¥¤ à¤¦à¤¾à¤œà¥à¤­à¤¾à¤‡/à¤¦à¤¿à¤¦à¥€à¤¬à¤¹à¤¿à¤¨à¥€ à¤ªà¥à¤°à¤®à¥à¤–à¥¤ à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤†à¤•à¤°à¥à¤·à¤£ à¤¬à¤²à¤¿à¤¯à¥‹à¥¤"),
    YOGINI_BHRAMARI_HEALTH("Watch for injuries, inflammations, and accidents. Physical exercise important but avoid overexertion.", "à¤šà¥‹à¤Ÿà¤ªà¤Ÿà¤•, à¤¸à¥à¤œà¤¨, à¤° à¤¦à¥à¤°à¥à¤˜à¤Ÿà¤¨à¤¾à¤¹à¤°à¥‚à¤¬à¤¾à¤Ÿ à¤¬à¤šà¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤ à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤µà¥à¤¯à¤¾à¤¯à¤¾à¤® à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤¤à¤° à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤ªà¤°à¤¿à¤¶à¥à¤°à¤® à¤¨à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    YOGINI_BHRAMARI_SPIRITUAL("Active spiritual practices like yoga and pranayama. Kundalini may activate. Mars-related deity worship helps.", "à¤¯à¥‹à¤— à¤° à¤ªà¥à¤°à¤¾à¤£à¤¾à¤¯à¤¾à¤® à¤œà¤¸à¥à¤¤à¤¾ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤…à¤­à¥à¤¯à¤¾à¤¸à¤¹à¤°à¥‚à¥¤ à¤•à¥à¤£à¥à¤¡à¤²à¤¿à¤¨à¥€ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤ à¤®à¤‚à¤—à¤² à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¿à¤¤ à¤¦à¥‡à¤µà¤¤à¤¾ à¤ªà¥‚à¤œà¤¾à¤²à¥‡ à¤®à¤¦à¥à¤¦à¤¤ à¤—à¤°à¥à¤›à¥¤"),
    YOGINI_BHRAMARI_REC_1("Wear red coral if suitable", "à¤‰à¤ªà¤¯à¥à¤•à¥à¤¤ à¤­à¤à¤®à¤¾ à¤®à¥à¤—à¤¾ à¤²à¤—à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_BHRAMARI_REC_2("Practice physical exercise regularly", "à¤¨à¤¿à¤¯à¤®à¤¿à¤¤ à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤µà¥à¤¯à¤¾à¤¯à¤¾à¤® à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_BHRAMARI_REC_3("Channel energy constructively", "à¤Šà¤°à¥à¤œà¤¾à¤²à¤¾à¤ˆ à¤°à¤šà¤¨à¤¾à¤¤à¥à¤®à¤• à¤°à¥‚à¤ªà¤®à¤¾ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_BHRAMARI_REC_4("Donate red items on Tuesdays", "à¤®à¤‚à¤—à¤²à¤¬à¤¾à¤° à¤°à¤¾à¤¤à¥‹ à¤µà¤¸à¥à¤¤à¥à¤¹à¤°à¥‚ à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_BHRAMARI_CAUTION_1("Anger management", "à¤•à¥à¤°à¥‹à¤§ à¤µà¥à¤¯à¤µà¤¸à¥à¤¥à¤¾à¤ªà¤¨"),
    YOGINI_BHRAMARI_CAUTION_2("Accidents", "à¤¦à¥à¤°à¥à¤˜à¤Ÿà¤¨à¤¾à¤¹à¤°à¥‚"),
    YOGINI_BHRAMARI_CAUTION_3("Property disputes", "à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿ à¤µà¤¿à¤µà¤¾à¤¦"),

    // Bhadrika (Mercury)
    YOGINI_BHADRIKA_CAREER("Business acumen peaks. Excellent for trade, communication, writing, and intellectual pursuits. Good for learning new skills.", "à¤µà¥à¤¯à¤¾à¤ªà¤¾à¤°à¤¿à¤• à¤•à¥Œà¤¶à¤² à¤¶à¤¿à¤–à¤°à¤®à¤¾à¥¤ à¤µà¥à¤¯à¤¾à¤ªà¤¾à¤°, à¤¸à¤žà¥à¤šà¤¾à¤°, à¤²à¥‡à¤–à¤¨, à¤° à¤¬à¥Œà¤¦à¥à¤§à¤¿à¤• à¤•à¤¾à¤°à¥à¤¯à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿà¥¤ à¤¨à¤¯à¤¾à¤ à¤¸à¥€à¤ªà¤¹à¤°à¥‚ à¤¸à¤¿à¤•à¥à¤¨ à¤°à¤¾à¤®à¥à¤°à¥‹à¥¤"),
    YOGINI_BHADRIKA_RELATIONSHIP("Communication improves relationships. Intellectual compatibility matters. Good for understanding partners better.", "à¤¸à¤žà¥à¤šà¤¾à¤°à¤²à¥‡ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤¸à¥à¤§à¤¾à¤° à¤—à¤°à¥à¤›à¥¤ à¤¬à¥Œà¤¦à¥à¤§à¤¿à¤• à¤…à¤¨à¥à¤•à¥‚à¤²à¤¤à¤¾ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤›à¥¤ à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¤¹à¤°à¥‚à¤²à¤¾à¤ˆ à¤°à¤¾à¤®à¥à¤°à¥‹à¤¸à¤à¤— à¤¬à¥à¤à¥à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤°à¤¾à¤®à¥à¤°à¥‹à¥¤"),
    YOGINI_BHADRIKA_HEALTH("Nervous system needs attention. Skin issues possible. Mental relaxation techniques helpful.", "à¤¸à¥à¤¨à¤¾à¤¯à¥ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€à¤®à¤¾ à¤§à¥à¤¯à¤¾à¤¨ à¤†à¤µà¤¶à¥à¤¯à¤• à¤›à¥¤ à¤›à¤¾à¤²à¤¾ à¤¸à¤®à¤¸à¥à¤¯à¤¾à¤¹à¤°à¥‚ à¤¸à¤®à¥à¤­à¤µ à¤›à¥¤ à¤®à¤¾à¤¨à¤¸à¤¿à¤• à¤µà¤¿à¤¶à¥à¤°à¤¾à¤® à¤ªà¥à¤°à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚ à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤•à¥¤"),
    YOGINI_BHADRIKA_SPIRITUAL("Intellectual approach to spirituality. Study of scriptures beneficial. Mantra practice effective.", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤•à¤¤à¤¾à¤®à¤¾ à¤¬à¥Œà¤¦à¥à¤§à¤¿à¤• à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤•à¥‹à¤£à¥¤ à¤¶à¤¾à¤¸à¥à¤¤à¥à¤°à¤¹à¤°à¥‚à¤•à¥‹ à¤…à¤§à¥à¤¯à¤¯à¤¨ à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤•à¥¤ à¤®à¤¨à¥à¤¤à¥à¤° à¤…à¤­à¥à¤¯à¤¾à¤¸ à¤ªà¥à¤°à¤­à¤¾à¤µà¤•à¤¾à¤°à¥€à¥¤"),
    YOGINI_BHADRIKA_REC_1("Wear emerald if suitable", "à¤‰à¤ªà¤¯à¥à¤•à¥à¤¤ à¤­à¤à¤®à¤¾ à¤ªà¤¨à¥à¤¨à¤¾ à¤²à¤—à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_BHADRIKA_REC_2("Engage in learning and communication", "à¤¸à¤¿à¤•à¥à¤¨à¥‡ à¤° à¤¸à¤žà¥à¤šà¤¾à¤°à¤®à¤¾ à¤¸à¤‚à¤²à¤—à¥à¤¨ à¤¹à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_BHADRIKA_REC_3("Write and express yourself", "à¤²à¥‡à¤–à¥à¤¨à¥à¤¹à¥‹à¤¸à¥ à¤° à¤†à¤«à¥‚à¤²à¤¾à¤ˆ à¤µà¥à¤¯à¤•à¥à¤¤ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_BHADRIKA_REC_4("Donate green items on Wednesdays", "à¤¬à¥à¤§à¤¬à¤¾à¤° à¤¹à¤°à¤¿à¤¯à¥‹ à¤µà¤¸à¥à¤¤à¥à¤¹à¤°à¥‚ à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_BHADRIKA_CAUTION_1("Nervousness", "à¤˜à¤¬à¤°à¤¾à¤¹à¤Ÿ"),
    YOGINI_BHADRIKA_CAUTION_2("Overthinking", "à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤¸à¥‹à¤š"),
    YOGINI_BHADRIKA_CAUTION_3("Skin issues", "à¤›à¤¾à¤²à¤¾ à¤¸à¤®à¤¸à¥à¤¯à¤¾à¤¹à¤°à¥‚"),

    // Ulka (Saturn)
    YOGINI_ULKA_CAREER("Slow but steady career progress. Hard work brings delayed rewards. Good for perseverance in long-term projects.", "à¤¸à¥à¤¸à¥à¤¤ à¤¤à¤° à¤¸à¥à¤¥à¤¿à¤° à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤° à¤ªà¥à¤°à¤—à¤¤à¤¿à¥¤ à¤•à¤¡à¤¾ à¤ªà¤°à¤¿à¤¶à¥à¤°à¤®à¤²à¥‡ à¤¢à¤¿à¤²à¥‹ à¤ªà¥à¤°à¤¸à¥à¤•à¤¾à¤° à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤ à¤¦à¥€à¤°à¥à¤˜à¤•à¤¾à¤²à¥€à¤¨ à¤ªà¤°à¤¿à¤¯à¥‹à¤œà¤¨à¤¾à¤¹à¤°à¥‚à¤®à¤¾ à¤§à¥ˆà¤°à¥à¤¯à¤¤à¤¾à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤°à¤¾à¤®à¥à¤°à¥‹à¥¤"),
    YOGINI_ULKA_RELATIONSHIP("Delays or challenges in relationships. Karmic partners appear. Long-distance relationships possible.", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤®à¤¾ à¤¢à¤¿à¤²à¤¾à¤‡ à¤µà¤¾ à¤šà¥à¤¨à¥Œà¤¤à¥€à¤¹à¤°à¥‚à¥¤ à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¤¹à¤°à¥‚ à¤¦à¥‡à¤–à¤¾ à¤ªà¤°à¥à¤›à¤¨à¥à¥¤ à¤²à¤¾à¤®à¥‹-à¤¦à¥‚à¤°à¥€à¤•à¥‹ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤¸à¤®à¥à¤­à¤µ à¤›à¥¤"),
    YOGINI_ULKA_HEALTH("Chronic issues may arise. Bones, joints, and teeth need care. Patience in recovery.", "à¤¦à¥€à¤°à¥à¤˜à¤•à¤¾à¤²à¥€à¤¨ à¤¸à¤®à¤¸à¥à¤¯à¤¾à¤¹à¤°à¥‚ à¤‰à¤¤à¥à¤ªà¤¨à¥à¤¨ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤ à¤¹à¤¡à¥à¤¡à¥€, à¤œà¥‹à¤°à¥à¤¨à¥€, à¤° à¤¦à¤¾à¤à¤¤à¤•à¥‹ à¤¹à¥‡à¤°à¤šà¤¾à¤¹ à¤†à¤µà¤¶à¥à¤¯à¤•à¥¤ à¤¨à¤¿à¤•à¥‹ à¤¹à¥à¤¨ à¤§à¥ˆà¤°à¥à¤¯à¤¤à¤¾à¥¤"),
    YOGINI_ULKA_SPIRITUAL("Deep karmic cleansing period. Meditation on impermanence. Service-oriented spirituality.", "à¤—à¤¹à¤¿à¤°à¥‹ à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤¸à¤«à¤¾à¤ˆ à¤…à¤µà¤§à¤¿à¥¤ à¤…à¤¨à¤¿à¤¤à¥à¤¯à¤¤à¤¾à¤®à¤¾ à¤§à¥à¤¯à¤¾à¤¨à¥¤ à¤¸à¥‡à¤µà¤¾-à¤‰à¤¨à¥à¤®à¥à¤– à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤•à¤¤à¤¾à¥¤"),
    YOGINI_ULKA_REC_1("Wear blue sapphire with caution", "à¤¸à¤¾à¤µà¤§à¤¾à¤¨à¥€à¤ªà¥‚à¤°à¥à¤µà¤• à¤¨à¥€à¤²à¤® à¤²à¤—à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_ULKA_REC_2("Practice patience and discipline", "à¤§à¥ˆà¤°à¥à¤¯ à¤° à¤…à¤¨à¥à¤¶à¤¾à¤¸à¤¨ à¤…à¤­à¥à¤¯à¤¾à¤¸ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_ULKA_REC_3("Serve the elderly and disadvantaged", "à¤µà¥ƒà¤¦à¥à¤§ à¤° à¤µà¤¿à¤ªà¤¨à¥à¤¨à¤¹à¤°à¥‚à¤•à¥‹ à¤¸à¥‡à¤µà¤¾ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_ULKA_REC_4("Donate oil and black items on Saturdays", "à¤¶à¤¨à¤¿à¤¬à¤¾à¤° à¤¤à¥‡à¤² à¤° à¤•à¤¾à¤²à¥‹ à¤µà¤¸à¥à¤¤à¥à¤¹à¤°à¥‚ à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_ULKA_CAUTION_1("Depression", "à¤…à¤µà¤¸à¤¾à¤¦"),
    YOGINI_ULKA_CAUTION_2("Delays", "à¤¢à¤¿à¤²à¤¾à¤‡"),
    YOGINI_ULKA_CAUTION_3("Chronic health issues", "à¤¦à¥€à¤°à¥à¤˜à¤•à¤¾à¤²à¥€à¤¨ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤¸à¤®à¤¸à¥à¤¯à¤¾à¤¹à¤°à¥‚"),

    // Siddha (Venus)
    YOGINI_SIDDHA_CAREER("Career success through creativity and charm. Excellent for arts, entertainment, luxury goods, and beauty industries.", "à¤¸à¥ƒà¤œà¤¨à¤¶à¥€à¤²à¤¤à¤¾ à¤° à¤†à¤•à¤°à¥à¤·à¤£ à¤®à¤¾à¤°à¥à¤«à¤¤ à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤° à¤¸à¤«à¤²à¤¤à¤¾à¥¤ à¤•à¤²à¤¾, à¤®à¤¨à¥‹à¤°à¤žà¥à¤œà¤¨, à¤µà¤¿à¤²à¤¾à¤¸à¤¿à¤¤à¤¾à¤•à¤¾ à¤¸à¤¾à¤®à¤¾à¤¨, à¤° à¤¸à¥Œà¤¨à¥à¤¦à¤°à¥à¤¯ à¤‰à¤¦à¥à¤¯à¥‹à¤—à¤•à¤¾ à¤²à¤¾à¤—à¤¿ à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿà¥¤"),
    YOGINI_SIDDHA_RELATIONSHIP("Excellent for romance, marriage, and love. Harmonious relationships. Beauty and pleasure in partnerships.", "à¤°à¥‹à¤®à¤¾à¤¨à¥à¤¸, à¤µà¤¿à¤µà¤¾à¤¹, à¤° à¤ªà¥à¤°à¥‡à¤®à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿà¥¤ à¤¸à¤¾à¤®à¤‚à¤œà¤¸à¥à¤¯à¤ªà¥‚à¤°à¥à¤£ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¥¤ à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¥€à¤®à¤¾ à¤¸à¥Œà¤¨à¥à¤¦à¤°à¥à¤¯ à¤° à¤†à¤¨à¤¨à¥à¤¦à¥¤"),
    YOGINI_SIDDHA_HEALTH("Generally good health. Watch reproductive system. Beauty treatments beneficial.", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¯à¤¾ à¤°à¤¾à¤®à¥à¤°à¥‹ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯à¥¤ à¤ªà¥à¤°à¤œà¤¨à¤¨ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€à¤®à¤¾ à¤§à¥à¤¯à¤¾à¤¨ à¤¦à¤¿à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤ à¤¸à¥Œà¤¨à¥à¤¦à¤°à¥à¤¯ à¤‰à¤ªà¤šà¤¾à¤° à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤•à¥¤"),
    YOGINI_SIDDHA_SPIRITUAL("Tantric practices may attract. Beauty in spirituality. Heart-centered practices beneficial.", "à¤¤à¤¾à¤¨à¥à¤¤à¥à¤°à¤¿à¤• à¤…à¤­à¥à¤¯à¤¾à¤¸à¤¹à¤°à¥‚à¤²à¥‡ à¤†à¤•à¤°à¥à¤·à¤¿à¤¤ à¤—à¤°à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤•à¤¤à¤¾à¤®à¤¾ à¤¸à¥Œà¤¨à¥à¤¦à¤°à¥à¤¯à¥¤ à¤¹à¥ƒà¤¦à¤¯-à¤•à¥‡à¤¨à¥à¤¦à¥à¤°à¤¿à¤¤ à¤…à¤­à¥à¤¯à¤¾à¤¸à¤¹à¤°à¥‚ à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤•à¥¤"),
    YOGINI_SIDDHA_REC_1("Wear diamond or white sapphire if suitable", "à¤‰à¤ªà¤¯à¥à¤•à¥à¤¤ à¤­à¤à¤®à¤¾ à¤¹à¥€à¤°à¤¾ à¤µà¤¾ à¤¸à¥‡à¤¤à¥‹ à¤ªà¥à¤–à¤°à¤¾à¤œ à¤²à¤—à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_SIDDHA_REC_2("Engage in arts and creativity", "à¤•à¤²à¤¾ à¤° à¤¸à¥ƒà¤œà¤¨à¤¶à¥€à¤²à¤¤à¤¾à¤®à¤¾ à¤¸à¤‚à¤²à¤—à¥à¤¨ à¤¹à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_SIDDHA_REC_3("Cultivate harmonious relationships", "à¤¸à¤¾à¤®à¤‚à¤œà¤¸à¥à¤¯à¤ªà¥‚à¤°à¥à¤£ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤µà¤¿à¤•à¤¾à¤¸ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_SIDDHA_REC_4("Donate white items on Fridays", "à¤¶à¥à¤•à¥à¤°à¤¬à¤¾à¤° à¤¸à¥‡à¤¤à¥‹ à¤µà¤¸à¥à¤¤à¥à¤¹à¤°à¥‚ à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_SIDDHA_CAUTION_1("Indulgence", "à¤­à¥‹à¤—à¤µà¤¿à¤²à¤¾"),
    YOGINI_SIDDHA_CAUTION_2("Relationship complications", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤œà¤Ÿà¤¿à¤²à¤¤à¤¾à¤¹à¤°à¥‚"),
    YOGINI_SIDDHA_CAUTION_3("Luxury overspending", "à¤µà¤¿à¤²à¤¾à¤¸à¤¿à¤¤à¤¾à¤®à¤¾ à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤–à¤°à¥à¤š"),

    // Sankata (Rahu)
    YOGINI_SANKATA_CAREER("Unconventional career paths, foreign opportunities. May bring sudden changes in profession. Research and technology favored.", "à¤…à¤ªà¤°à¤®à¥à¤ªà¤°à¤¾à¤—à¤¤ à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤° à¤ªà¤¥à¤¹à¤°à¥‚, à¤µà¤¿à¤¦à¥‡à¤¶à¥€ à¤…à¤µà¤¸à¤°à¤¹à¤°à¥‚à¥¤ à¤ªà¥‡à¤¶à¤¾à¤®à¤¾ à¤…à¤šà¤¾à¤¨à¤• à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨ à¤²à¥à¤¯à¤¾à¤‰à¤¨ à¤¸à¤•à¥à¤›à¥¤ à¤…à¤¨à¥à¤¸à¤¨à¥à¤§à¤¾à¤¨ à¤° à¤ªà¥à¤°à¤µà¤¿à¤§à¤¿ à¤…à¤¨à¥à¤•à¥‚à¤²à¥¤"),
    YOGINI_SANKATA_RELATIONSHIP("Unusual or foreign partners. Sudden attractions or separations. Need to address karmic relationship patterns.", "à¤…à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤µà¤¾ à¤µà¤¿à¤¦à¥‡à¤¶à¥€ à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¤¹à¤°à¥‚à¥¤ à¤…à¤šà¤¾à¤¨à¤• à¤†à¤•à¤°à¥à¤·à¤£ à¤µà¤¾ à¤…à¤²à¤—à¤¾à¤µà¥¤ à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤¢à¤¾à¤à¤šà¤¾à¤¹à¤°à¥‚ à¤¸à¤®à¥à¤¬à¥‹à¤§à¤¨ à¤—à¤°à¥à¤¨ à¤†à¤µà¤¶à¥à¤¯à¤•à¥¤"),
    YOGINI_SANKATA_HEALTH("Unusual or hard-to-diagnose health issues. Mental health important. Alternative therapies may help.", "à¤…à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤µà¤¾ à¤¨à¤¿à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨ à¤—à¤¾à¤¹à¥à¤°à¥‹ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤¸à¤®à¤¸à¥à¤¯à¤¾à¤¹à¤°à¥‚à¥¤ à¤®à¤¾à¤¨à¤¸à¤¿à¤• à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£à¥¤ à¤µà¥ˆà¤•à¤²à¥à¤ªà¤¿à¤• à¤‰à¤ªà¤šà¤¾à¤°à¤²à¥‡ à¤®à¤¦à¥à¤¦à¤¤ à¤—à¤°à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤"),
    YOGINI_SANKATA_SPIRITUAL("Deep transformation possible. Occult interests may arise. Breaking free from illusions.", "à¤—à¤¹à¤¿à¤°à¥‹ à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£ à¤¸à¤®à¥à¤­à¤µ à¤›à¥¤ à¤¤à¤¨à¥à¤¤à¥à¤°à¤®à¤¨à¥à¤¤à¥à¤°à¤®à¤¾ à¤°à¥à¤šà¤¿ à¤œà¤¾à¤—à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤ à¤­à¥à¤°à¤®à¤¬à¤¾à¤Ÿ à¤®à¥à¤•à¥à¤¤à¤¿à¥¤"),
    YOGINI_SANKATA_REC_1("Wear hessonite after careful analysis", "à¤¸à¤¾à¤µà¤§à¤¾à¤¨à¥€à¤ªà¥‚à¤°à¥à¤µà¤• à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤ªà¤›à¤¿ à¤—à¥‹à¤®à¥‡à¤¦ à¤²à¤—à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_SANKATA_REC_2("Stay grounded during sudden changes", "à¤…à¤šà¤¾à¤¨à¤• à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨à¤•à¥‹ à¤¸à¤®à¤¯à¤®à¤¾ à¤¸à¥à¤¥à¤¿à¤° à¤°à¤¹à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_SANKATA_REC_3("Address past-life patterns through meditation", "à¤§à¥à¤¯à¤¾à¤¨ à¤®à¤¾à¤°à¥à¤«à¤¤ à¤ªà¥‚à¤°à¥à¤µ-à¤œà¤¨à¥à¤®à¤•à¤¾ à¤¢à¤¾à¤à¤šà¤¾à¤¹à¤°à¥‚ à¤¸à¤®à¥à¤¬à¥‹à¤§à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_SANKATA_REC_4("Donate blue/black items on Saturdays", "à¤¶à¤¨à¤¿à¤¬à¤¾à¤° à¤¨à¤¿à¤²à¥‹/à¤•à¤¾à¤²à¥‹ à¤µà¤¸à¥à¤¤à¥à¤¹à¤°à¥‚ à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    YOGINI_SANKATA_CAUTION_1("Sudden obstacles", "à¤…à¤šà¤¾à¤¨à¤• à¤…à¤µà¤°à¥‹à¤§à¤¹à¤°à¥‚"),
    YOGINI_SANKATA_CAUTION_2("Confusion", "à¤­à¥à¤°à¤®"),
    YOGINI_SANKATA_CAUTION_3("Unusual diseases", "à¤…à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤°à¥‹à¤—à¤¹à¤°à¥‚"),

    // Antardasha Relationships
    YOGINI_ANTAR_FRIENDLY(
        "%s-%s: Harmonious sub-period with complementary energies. %s's significations blend well with %s's ongoing influence. Good for collaborative efforts and relationship building.",
        "%s-%s: à¤ªà¥‚à¤°à¤• à¤Šà¤°à¥à¤œà¤¾à¤•à¥‹ à¤¸à¤¾à¤¥ à¤¸à¤¾à¤®à¤‚à¤œà¤¸à¥à¤¯à¤ªà¥‚à¤°à¥à¤£ à¤‰à¤ª-à¤…à¤µà¤§à¤¿à¥¤ %s à¤•à¥‹ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚ %s à¤•à¥‹ à¤šà¤²à¤¿à¤°à¤¹à¥‡à¤•à¥‹ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¸à¤à¤— à¤°à¤¾à¤®à¥à¤°à¥‹à¤¸à¤à¤— à¤®à¤¿à¤²à¥à¤›à¤¨à¥à¥¤ à¤¸à¤¹à¤¯à¥‹à¤—à¤¾à¤¤à¥à¤®à¤• à¤ªà¥à¤°à¤¯à¤¾à¤¸ à¤° à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤¨à¤¿à¤°à¥à¤®à¤¾à¤£à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤°à¤¾à¤®à¥à¤°à¥‹à¥¤"
    ),
    YOGINI_ANTAR_HOSTILE(
        "%s-%s: Potentially challenging sub-period with conflicting energies. %s may create tension with %s's ongoing themes. Requires patience, remedies, and conscious effort for harmony.",
        "%s-%s: à¤¦à¥à¤µà¤¨à¥à¤¦à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤Šà¤°à¥à¤œà¤¾à¤•à¥‹ à¤¸à¤¾à¤¥ à¤¸à¤®à¥à¤­à¤¾à¤µà¤¿à¤¤ à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£ à¤‰à¤ª-à¤…à¤µà¤§à¤¿à¥¤ %s à¤²à¥‡ %s à¤•à¥‹ à¤šà¤²à¤¿à¤°à¤¹à¥‡à¤•à¥‹ à¤µà¤¿à¤·à¤¯à¤¹à¤°à¥‚à¤¸à¤à¤— à¤¤à¤¨à¤¾à¤µ à¤¸à¤¿à¤°à¥à¤œà¤¨à¤¾ à¤—à¤°à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤ à¤§à¥ˆà¤°à¥à¤¯, à¤‰à¤ªà¤¾à¤¯, à¤° à¤¸à¤¦à¥à¤­à¤¾à¤µà¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¸à¤šà¥‡à¤¤ à¤ªà¥à¤°à¤¯à¤¾à¤¸ à¤†à¤µà¤¶à¥à¤¯à¤•à¥¤"
    ),
    YOGINI_ANTAR_NEUTRAL(
        "%s-%s: Balanced sub-period requiring conscious integration. %s's themes activate within %s's framework. Results depend on individual chart placements.",
        "%s-%s: à¤¸à¤šà¥‡à¤¤ à¤à¤•à¥€à¤•à¤°à¤£ à¤†à¤µà¤¶à¥à¤¯à¤• à¤ªà¤°à¥à¤¨à¥‡ à¤¸à¤¨à¥à¤¤à¥à¤²à¤¿à¤¤ à¤‰à¤ª-à¤…à¤µà¤§à¤¿à¥¤ %s à¤•à¥‹ à¤µà¤¿à¤·à¤¯à¤¹à¤°à¥‚ %s à¤•à¥‹ à¤¢à¤¾à¤à¤šà¤¾ à¤­à¤¿à¤¤à¥à¤° à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤¹à¥à¤¨à¥à¤›à¤¨à¥à¥¤ à¤ªà¤°à¤¿à¤£à¤¾à¤®à¤¹à¤°à¥‚ à¤µà¥à¤¯à¤•à¥à¤¤à¤¿à¤—à¤¤ à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤®à¤¾ à¤¨à¤¿à¤°à¥à¤­à¤° à¤¹à¥à¤¨à¥à¤›à¤¨à¥à¥¤"
    ),

    // Applicability Reasons
    YOGINI_APP_UNIVERSAL("Yogini Dasha is universally applicable for timing specific events", "à¤µà¤¿à¤¶à¤¿à¤·à¥à¤Ÿ à¤˜à¤Ÿà¤¨à¤¾à¤¹à¤°à¥‚à¤•à¥‹ à¤¸à¤®à¤¯ à¤¨à¤¿à¤°à¥à¤§à¤¾à¤°à¤£à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¯à¥‹à¤—à¤¿à¤¨à¥€ à¤¦à¤¶à¤¾ à¤¸à¤°à¥à¤µà¤µà¥à¤¯à¤¾à¤ªà¥€ à¤°à¥‚à¤ªà¤®à¤¾ à¤²à¤¾à¤—à¥‚ à¤¹à¥à¤¨à¥à¤›"),
    YOGINI_APP_FEMALE("Yogini Dasha is traditionally considered more accurate for female horoscopes", "à¤¯à¥‹à¤—à¤¿à¤¨à¥€ à¤¦à¤¶à¤¾ à¤ªà¤°à¤®à¥à¤ªà¤°à¤¾à¤—à¤¤ à¤°à¥‚à¤ªà¤®à¤¾ à¤®à¤¹à¤¿à¤²à¤¾ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¬à¤¢à¥€ à¤¸à¤¹à¥€ à¤®à¤¾à¤¨à¤¿à¤¨à¥à¤›"),
    YOGINI_APP_STRONG_MOON("Strong Moon in %s enhances Yogini Dasha applicability", "%s à¤®à¤¾ à¤¬à¤²à¤¿à¤¯à¥‹ à¤šà¤¨à¥à¤¦à¥à¤°à¤²à¥‡ à¤¯à¥‹à¤—à¤¿à¤¨à¥€ à¤¦à¤¶à¤¾à¤•à¥‹ à¤ªà¥à¤°à¤¯à¥‹à¤œà¥à¤¯à¤¤à¤¾ à¤¬à¤¢à¤¾à¤‰à¤à¤›"),
    YOGINI_APP_NIGHT_BIRTH("Night birth traditionally favors Yogini Dasha", "à¤°à¤¾à¤¤à¥à¤°à¤¿ à¤œà¤¨à¥à¤®à¤²à¥‡ à¤ªà¤°à¤®à¥à¤ªà¤°à¤¾à¤—à¤¤ à¤°à¥‚à¤ªà¤®à¤¾ à¤¯à¥‹à¤—à¤¿à¤¨à¥€ à¤¦à¤¶à¤¾à¤²à¤¾à¤ˆ à¤ªà¤•à¥à¤· à¤¦à¤¿à¤¨à¥à¤›"),
    YOGINI_APP_RELATIONSHIP("Yogini Dasha excels at timing relationship and marriage events", "à¤¯à¥‹à¤—à¤¿à¤¨à¥€ à¤¦à¤¶à¤¾ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤° à¤µà¤¿à¤µà¤¾à¤¹ à¤˜à¤Ÿà¤¨à¤¾à¤¹à¤°à¥‚à¤•à¥‹ à¤¸à¤®à¤¯ à¤¨à¤¿à¤°à¥à¤§à¤¾à¤°à¤£à¤®à¤¾ à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ à¤›"),
    YOGINI_APP_VALIDATION("Can be used alongside Vimsottari Dasha for validation", "à¤ªà¥à¤·à¥à¤Ÿà¤¿à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤µà¤¿à¤‚à¤¶à¥‹à¤¤à¥à¤¤à¤°à¥€ à¤¦à¤¶à¤¾à¤¸à¤à¤—à¥ˆ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¥à¤¨ à¤¸à¤•à¤¿à¤¨à¥à¤›"),

    // ============================================
    // ARGALA (JAIMINI) ANALYSIS
    // ============================================
    ARGALA_TITLE("Argala Analysis", "à¤…à¤°à¥à¤—à¤²à¤¾ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    ARGALA_SUBTITLE("Jaimini Intervention System", "à¤œà¥ˆà¤®à¤¿à¤¨à¥€ à¤¹à¤¸à¥à¤¤à¤•à¥à¤·à¥‡à¤ª à¤ªà¥à¤°à¤£à¤¾à¤²à¥€"),
    ARGALA_ABOUT("About Argala", "à¤…à¤°à¥à¤—à¤²à¤¾à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    ARGALA_ABOUT_DESC("Argala is a Jaimini system showing how planets in certain houses create interventions (Argala) that influence the results of other houses. Understanding Argala helps predict how life events unfold.", "à¤…à¤°à¥à¤—à¤²à¤¾ à¤à¤• à¤œà¥ˆà¤®à¤¿à¤¨à¥€ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤¹à¥‹ à¤œà¤¸à¤²à¥‡ à¤¨à¤¿à¤¶à¥à¤šà¤¿à¤¤ à¤­à¤¾à¤µà¤¹à¤°à¥‚à¤®à¤¾ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤²à¥‡ à¤•à¤¸à¤°à¥€ à¤¹à¤¸à¥à¤¤à¤•à¥à¤·à¥‡à¤ª (à¤…à¤°à¥à¤—à¤²à¤¾) à¤¸à¤¿à¤°à¥à¤œà¤¨à¤¾ à¤—à¤°à¥à¤›à¤¨à¥ à¤œà¤¸à¤²à¥‡ à¤…à¤¨à¥à¤¯ à¤­à¤¾à¤µà¤¹à¤°à¥‚à¤•à¥‹ à¤ªà¤°à¤¿à¤£à¤¾à¤®à¤²à¤¾à¤ˆ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¿à¤¤ à¤—à¤°à¥à¤› à¤­à¤¨à¥‡à¤° à¤¦à¥‡à¤–à¤¾à¤‰à¤à¤›à¥¤ à¤…à¤°à¥à¤—à¤²à¤¾ à¤¬à¥à¤à¥à¤¦à¤¾ à¤œà¥€à¤µà¤¨à¤•à¤¾ à¤˜à¤Ÿà¤¨à¤¾à¤¹à¤°à¥‚ à¤•à¤¸à¤°à¥€ à¤ªà¥à¤°à¤•à¤Ÿ à¤¹à¥à¤¨à¥à¤›à¤¨à¥ à¤­à¤¨à¥‡à¤° à¤­à¤µà¤¿à¤·à¥à¤¯à¤µà¤¾à¤£à¥€ à¤—à¤°à¥à¤¨ à¤®à¤¦à¥à¤¦à¤¤ à¤—à¤°à¥à¤›à¥¤"),
    ARGALA_PRIMARY("Primary Argala", "à¤ªà¥à¤°à¤¾à¤¥à¤®à¤¿à¤• à¤…à¤°à¥à¤—à¤²à¤¾"),
    ARGALA_SECONDARY("Secondary Argala", "à¤®à¤¾à¤§à¥à¤¯à¤®à¤¿à¤• à¤…à¤°à¥à¤—à¤²à¤¾"),
    ARGALA_VIRODHA("Virodha Argala (Obstruction)", "à¤µà¤¿à¤°à¥‹à¤§ à¤…à¤°à¥à¤—à¤²à¤¾ (à¤¬à¤¾à¤§à¤¾)"),
    ARGALA_SHUBHA("Shubha Argala (Benefic)", "à¤¶à¥à¤­ à¤…à¤°à¥à¤—à¤²à¤¾ (à¤²à¤¾à¤­à¤•à¤¾à¤°à¥€)"),
    ARGALA_ASHUBHA("Ashubha Argala (Malefic)", "à¤…à¤¶à¥à¤­ à¤…à¤°à¥à¤—à¤²à¤¾ (à¤¹à¤¾à¤¨à¤¿à¤•à¤¾à¤°à¤•)"),
    ARGALA_UNOBSTRUCTED("Unobstructed Argala", "à¤…à¤¬à¤¾à¤§à¤¿à¤¤ à¤…à¤°à¥à¤—à¤²à¤¾"),
    ARGALA_OBSTRUCTED("Obstructed Argala", "à¤¬à¤¾à¤§à¤¿à¤¤ à¤…à¤°à¥à¤—à¤²à¤¾"),
    ARGALA_NET_EFFECT("Net Effect", "à¤¶à¥à¤¦à¥à¤§ à¤ªà¥à¤°à¤­à¤¾à¤µ"),
    ARGALA_POSITIVE("Positive", "à¤¸à¤•à¤¾à¤°à¤¾à¤¤à¥à¤®à¤•"),
    ARGALA_NEGATIVE("Negative", "à¤¨à¤•à¤¾à¤°à¤¾à¤¤à¥à¤®à¤•"),
    ARGALA_MIXED("Mixed", "à¤®à¤¿à¤¶à¥à¤°à¤¿à¤¤"),
    ARGALA_FROM_HOUSE("From House %d", "à¤­à¤¾à¤µ %d à¤¬à¤¾à¤Ÿ"),
    ARGALA_TO_HOUSE("To House %d", "à¤­à¤¾à¤µ %d à¤®à¤¾"),
    ARGALA_SECOND_HOUSE("2nd House Argala", "à¤¦à¥à¤µà¤¿à¤¤à¥€à¤¯ à¤­à¤¾à¤µ à¤…à¤°à¥à¤—à¤²à¤¾"),
    ARGALA_FOURTH_HOUSE("4th House Argala", "à¤šà¤¤à¥à¤°à¥à¤¥ à¤­à¤¾à¤µ à¤…à¤°à¥à¤—à¤²à¤¾"),
    ARGALA_ELEVENTH_HOUSE("11th House Argala", "à¤à¤•à¤¾à¤¦à¤¶ à¤­à¤¾à¤µ à¤…à¤°à¥à¤—à¤²à¤¾"),
    ARGALA_FIFTH_HOUSE("5th House Argala", "à¤ªà¤žà¥à¤šà¤® à¤­à¤¾à¤µ à¤…à¤°à¥à¤—à¤²à¤¾"),
    ARGALA_KARMA_DHARMA("Dharma Argala", "à¤§à¤°à¥à¤® à¤…à¤°à¥à¤—à¤²à¤¾"),
    ARGALA_KARMA_ARTHA("Artha Argala", "à¤…à¤°à¥à¤¥ à¤…à¤°à¥à¤—à¤²à¤¾"),
    ARGALA_KARMA_KAMA("Kama Argala", "à¤•à¤¾à¤® à¤…à¤°à¥à¤—à¤²à¤¾"),
    ARGALA_KARMA_MOKSHA("Moksha Argala", "à¤®à¥‹à¤•à¥à¤· à¤…à¤°à¥à¤—à¤²à¤¾"),
    ARGALA_HOUSE_STRENGTH("House Argala Strength", "à¤­à¤¾à¤µ à¤…à¤°à¥à¤—à¤²à¤¾ à¤¬à¤²"),
    ARGALA_PLANET_CAUSES("Planet Argala Effects", "à¤—à¥à¤°à¤¹ à¤…à¤°à¥à¤—à¤²à¤¾ à¤ªà¥à¤°à¤­à¤¾à¤µ"),

    // ============================================
    // CHARA DASHA (JAIMINI) SYSTEM
    // ============================================
    CHARA_DASHA_TITLE("Chara Dasha", "à¤šà¤° à¤¦à¤¶à¤¾"),
    CHARA_DASHA_SUBTITLE("Jaimini Sign-Based Dasha", "à¤œà¥ˆà¤®à¤¿à¤¨à¥€ à¤°à¤¾à¤¶à¤¿-à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤¦à¤¶à¤¾"),
    CHARA_DASHA_ABOUT("About Chara Dasha", "à¤šà¤° à¤¦à¤¶à¤¾à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    CHARA_DASHA_ABOUT_DESC("Chara Dasha is Jaimini's primary sign-based timing system. Unlike Vimsottari which uses planets, Chara Dasha uses zodiac signs as periods, providing unique insights into life events.", "à¤šà¤° à¤¦à¤¶à¤¾ à¤œà¥ˆà¤®à¤¿à¤¨à¥€à¤•à¥‹ à¤ªà¥à¤°à¤¾à¤¥à¤®à¤¿à¤• à¤°à¤¾à¤¶à¤¿-à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤¸à¤®à¤¯ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤¹à¥‹à¥¤ à¤µà¤¿à¤®à¥à¤¸à¥‹à¤¤à¥à¤¤à¤°à¥€ à¤œà¤¸à¤²à¥‡ à¤—à¥à¤°à¤¹ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¥à¤› à¤¤à¥à¤¯à¤¸à¤•à¥‹ à¤µà¤¿à¤ªà¤°à¥€à¤¤, à¤šà¤° à¤¦à¤¶à¤¾à¤²à¥‡ à¤…à¤µà¤§à¤¿à¤•à¥‹ à¤°à¥‚à¤ªà¤®à¤¾ à¤°à¤¾à¤¶à¤¿à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¥à¤›, à¤œà¥€à¤µà¤¨à¤•à¤¾ à¤˜à¤Ÿà¤¨à¤¾à¤¹à¤°à¥‚à¤®à¤¾ à¤…à¤¦à¥à¤µà¤¿à¤¤à¥€à¤¯ à¤…à¤¨à¥à¤¤à¤°à¥à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿ à¤ªà¥à¤°à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤›à¥¤"),
    CHARA_DASHA_CURRENT("Current Sign Dasha", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤°à¤¾à¤¶à¤¿ à¤¦à¤¶à¤¾"),
    CHARA_DASHA_ANTARDASHA("Current Antardasha", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤…à¤¨à¥à¤¤à¤°à¥à¤¦à¤¶à¤¾"),
    CHARA_DASHA_SEQUENCE("Dasha Sequence", "à¤¦à¤¶à¤¾ à¤•à¥à¤°à¤®"),
    CHARA_DASHA_DIRECTION("Dasha Direction", "à¤¦à¤¶à¤¾ à¤¦à¤¿à¤¶à¤¾"),
    CHARA_DASHA_DIRECT("Direct (Zodiacal)", "à¤¸à¥€à¤§à¥‹ (à¤°à¤¾à¤¶à¤¿à¤•à¥à¤°à¤®)"),
    CHARA_DASHA_REVERSE("Reverse (Anti-zodiacal)", "à¤‰à¤²à¥à¤Ÿà¥‹ (à¤µà¤¿à¤ªà¤°à¥€à¤¤-à¤°à¤¾à¤¶à¤¿à¤•à¥à¤°à¤®)"),
    CHARA_DASHA_ODD_LAGNA("Odd Sign Lagna", "à¤µà¤¿à¤·à¤® à¤°à¤¾à¤¶à¤¿ à¤²à¤—à¥à¤¨"),
    CHARA_DASHA_EVEN_LAGNA("Even Sign Lagna", "à¤¸à¤® à¤°à¤¾à¤¶à¤¿ à¤²à¤—à¥à¤¨"),
    CHARA_DASHA_YEARS("Years: %d", "à¤µà¤°à¥à¤·: %d"),
    CHARA_DASHA_TIMELINE("Chara Dasha Timeline", "à¤šà¤° à¤¦à¤¶à¤¾ à¤¸à¤®à¤¯à¤°à¥‡à¤–à¤¾"),

    // Chara Karakas
    KARAKA_TITLE("Chara Karakas", "à¤šà¤° à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚"),
    KARAKA_SUBTITLE("Jaimini Variable Significators", "à¤œà¥ˆà¤®à¤¿à¤¨à¥€ à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨à¤¶à¥€à¤² à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚"),
    KARAKA_ABOUT("About Chara Karakas", "à¤šà¤° à¤•à¤¾à¤°à¤•à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    KARAKA_ABOUT_DESC("Chara Karakas are Jaimini's system of variable significators based on planetary degrees. Unlike fixed karakas, these change based on each chart.", "à¤šà¤° à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚ à¤—à¥à¤°à¤¹à¤•à¥‹ à¤…à¤‚à¤¶à¤®à¤¾ à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤œà¥ˆà¤®à¤¿à¤¨à¥€à¤•à¥‹ à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨à¤¶à¥€à¤² à¤•à¤¾à¤°à¤• à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤¹à¥‹à¥¤ à¤¸à¥à¤¥à¤¿à¤° à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚à¤•à¥‹ à¤µà¤¿à¤ªà¤°à¥€à¤¤, à¤¯à¥€ à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤…à¤¨à¥à¤¸à¤¾à¤° à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨ à¤¹à¥à¤¨à¥à¤›à¤¨à¥à¥¤"),
    KARAKA_AK("Atmakaraka (AK)", "à¤†à¤¤à¥à¤®à¤•à¤¾à¤°à¤• (AK)"),
    KARAKA_AK_DESC("Soul significator - Highest degree planet, represents the self and spiritual evolution", "à¤†à¤¤à¥à¤®à¤¾ à¤•à¤¾à¤°à¤• - à¤‰à¤šà¥à¤šà¤¤à¤® à¤…à¤‚à¤¶ à¤—à¥à¤°à¤¹, à¤†à¤¤à¥à¤® à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤µà¤¿à¤•à¤¾à¤¸à¤•à¥‹ à¤ªà¥à¤°à¤¤à¤¿à¤¨à¤¿à¤§à¤¿à¤¤à¥à¤µ"),
    KARAKA_AMK("Amatyakaraka (AmK)", "à¤…à¤®à¤¾à¤¤à¥à¤¯à¤•à¤¾à¤°à¤• (AmK)"),
    KARAKA_AMK_DESC("Minister significator - Career, profession, and life purpose", "à¤®à¤¨à¥à¤¤à¥à¤°à¥€ à¤•à¤¾à¤°à¤• - à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°, à¤ªà¥‡à¤¶à¤¾, à¤° à¤œà¥€à¤µà¤¨ à¤‰à¤¦à¥à¤¦à¥‡à¤¶à¥à¤¯"),
    KARAKA_BK("Bhratrikaraka (BK)", "à¤­à¥à¤°à¤¾à¤¤à¥ƒà¤•à¤¾à¤°à¤• (BK)"),
    KARAKA_BK_DESC("Sibling significator - Brothers, sisters, and courage", "à¤­à¤¾à¤‡à¤¬à¤¹à¤¿à¤¨à¥€ à¤•à¤¾à¤°à¤• - à¤­à¤¾à¤‡, à¤¬à¤¹à¤¿à¤¨à¥€, à¤° à¤¸à¤¾à¤¹à¤¸"),
    KARAKA_MK("Matrikaraka (MK)", "à¤®à¤¾à¤¤à¥ƒà¤•à¤¾à¤°à¤• (MK)"),
    KARAKA_MK_DESC("Mother significator - Mother, nurturing, emotional foundation", "à¤†à¤®à¤¾ à¤•à¤¾à¤°à¤• - à¤†à¤®à¤¾, à¤ªà¤¾à¤²à¤¨à¤ªà¥‹à¤·à¤£, à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤†à¤§à¤¾à¤°"),
    KARAKA_PIK("Pitrikaraka (PiK)", "à¤ªà¤¿à¤¤à¥ƒà¤•à¤¾à¤°à¤• (PiK)"),
    KARAKA_PIK_DESC("Father significator - Father, authority, guidance", "à¤¬à¥à¤¬à¤¾ à¤•à¤¾à¤°à¤• - à¤¬à¥à¤¬à¤¾, à¤…à¤§à¤¿à¤•à¤¾à¤°, à¤®à¤¾à¤°à¥à¤—à¤¦à¤°à¥à¤¶à¤¨"),
    KARAKA_PUK("Putrakaraka (PuK)", "à¤ªà¥à¤¤à¥à¤°à¤•à¤¾à¤°à¤• (PuK)"),
    KARAKA_PUK_DESC("Children significator - Children, creativity, intelligence", "à¤¸à¤¨à¥à¤¤à¤¾à¤¨ à¤•à¤¾à¤°à¤• - à¤¸à¤¨à¥à¤¤à¤¾à¤¨, à¤¸à¥ƒà¤œà¤¨à¤¶à¥€à¤²à¤¤à¤¾, à¤¬à¥à¤¦à¥à¤§à¤¿"),
    KARAKA_GK("Gnatikaraka (GK)", "à¤œà¥à¤žà¤¾à¤¤à¤¿à¤•à¤¾à¤°à¤• (GK)"),
    KARAKA_GK_DESC("Relative significator - Relatives, obstacles, competition", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¥€ à¤•à¤¾à¤°à¤• - à¤†à¤«à¤¨à¥à¤¤, à¤¬à¤¾à¤§à¤¾, à¤ªà¥à¤°à¤¤à¤¿à¤¸à¥à¤ªà¤°à¥à¤§à¤¾"),
    KARAKA_DK("Darakaraka (DK)", "à¤¦à¤¾à¤°à¤•à¤¾à¤°à¤• (DK)"),
    KARAKA_DK_DESC("Spouse significator - Marriage partner, business partners", "à¤œà¥€à¤µà¤¨à¤¸à¤¾à¤¥à¥€ à¤•à¤¾à¤°à¤• - à¤µà¤¿à¤µà¤¾à¤¹ à¤¸à¤¾à¤¥à¥€, à¤µà¥à¤¯à¤¾à¤ªà¤¾à¤° à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°"),
    KARAKAMSHA_TITLE("Karakamsha", "à¤•à¤¾à¤°à¤•à¤¾à¤‚à¤¶"),
    KARAKAMSHA_DESC("Navamsa sign of Atmakaraka - Key for spiritual and material destiny", "à¤†à¤¤à¥à¤®à¤•à¤¾à¤°à¤•à¤•à¥‹ à¤¨à¤µà¤¾à¤‚à¤¶ à¤°à¤¾à¤¶à¤¿ - à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤° à¤­à¥Œà¤¤à¤¿à¤• à¤­à¤¾à¤—à¥à¤¯à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£"),

    // ============================================
    // SHODASHVARGA (16 DIVISIONAL CHARTS) STRENGTH
    // ============================================
    SHODASHVARGA_TITLE("Shodashvarga Strength", "à¤·à¥‹à¤¡à¤¶à¤µà¤°à¥à¤— à¤¬à¤²"),
    SHODASHVARGA_SUBTITLE("16-Divisional Chart Analysis", "à¥§à¥¬-à¤µà¤¿à¤­à¤¾à¤œà¤¨ à¤šà¤¾à¤°à¥à¤Ÿ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SHODASHVARGA_ABOUT("About Shodashvarga", "à¤·à¥‹à¤¡à¤¶à¤µà¤°à¥à¤—à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    SHODASHVARGA_ABOUT_DESC("Shodashvarga Bala evaluates planetary strength across all 16 divisional charts. This comprehensive analysis reveals how effectively each planet can deliver its results.", "à¤·à¥‹à¤¡à¤¶à¤µà¤°à¥à¤— à¤¬à¤²à¤²à¥‡ à¤¸à¤¬à¥ˆ à¥§à¥¬ à¤µà¤¿à¤­à¤¾à¤œà¤¨ à¤šà¤¾à¤°à¥à¤Ÿà¤¹à¤°à¥‚à¤®à¤¾ à¤—à¥à¤°à¤¹ à¤¬à¤²à¤•à¥‹ à¤®à¥‚à¤²à¥à¤¯à¤¾à¤‚à¤•à¤¨ à¤—à¤°à¥à¤¦à¤›à¥¤ à¤¯à¥‹ à¤µà¥à¤¯à¤¾à¤ªà¤• à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£à¤²à¥‡ à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤—à¥à¤°à¤¹à¤²à¥‡ à¤†à¤«à¥à¤¨à¥‹ à¤ªà¤°à¤¿à¤£à¤¾à¤® à¤•à¤¤à¤¿ à¤ªà¥à¤°à¤­à¤¾à¤µà¤•à¤¾à¤°à¥€ à¤°à¥‚à¤ªà¤®à¤¾ à¤ªà¥à¤°à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨ à¤¸à¤•à¥à¤› à¤­à¤¨à¥‡à¤° à¤ªà¥à¤°à¤•à¤Ÿ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    SHODASHVARGA_BALA("Shodashvarga Bala", "à¤·à¥‹à¤¡à¤¶à¤µà¤°à¥à¤— à¤¬à¤²"),
    SHADVARGA_BALA("Shadvarga Bala (6-Varga)", "à¤·à¤¡à¥à¤µà¤°à¥à¤— à¤¬à¤² (à¥¬-à¤µà¤°à¥à¤—)"),
    SAPTAVARGA_BALA("Saptavarga Bala (7-Varga)", "à¤¸à¤ªà¥à¤¤à¤µà¤°à¥à¤— à¤¬à¤² (à¥­-à¤µà¤°à¥à¤—)"),
    DASHAVARGA_BALA("Dashavarga Bala (10-Varga)", "à¤¦à¤¶à¤µà¤°à¥à¤— à¤¬à¤² (à¥§à¥¦-à¤µà¤°à¥à¤—)"),
    VIMSOPAKA_BALA("Vimsopaka Bala", "à¤µà¤¿à¤®à¥à¤¶à¥‹à¤ªà¤• à¤¬à¤²"),
    VIMSOPAKA_POORVA("Poorva Scheme", "à¤ªà¥‚à¤°à¥à¤µ à¤¯à¥‹à¤œà¤¨à¤¾"),
    VIMSOPAKA_MADHYA("Madhya Scheme", "à¤®à¤§à¥à¤¯ à¤¯à¥‹à¤œà¤¨à¤¾"),
    VIMSOPAKA_PARA("Para Scheme", "à¤ªà¤° à¤¯à¥‹à¤œà¤¨à¤¾"),
    VARGOTTAMA_TITLE("Vargottama Planets", "à¤µà¤°à¥à¤—à¥‹à¤¤à¥à¤¤à¤® à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    VARGOTTAMA_DESC("Planet in same sign in D1 and divisional chart", "D1 à¤° à¤µà¤¿à¤­à¤¾à¤œà¤¨ à¤šà¤¾à¤°à¥à¤Ÿà¤®à¤¾ à¤¸à¤®à¤¾à¤¨ à¤°à¤¾à¤¶à¤¿à¤®à¤¾ à¤—à¥à¤°à¤¹"),
    VARGOTTAMA_NAVAMSA("Navamsa Vargottama", "à¤¨à¤µà¤¾à¤‚à¤¶ à¤µà¤°à¥à¤—à¥‹à¤¤à¥à¤¤à¤®"),
    VARGOTTAMA_DASHAMSA("Dashamsa Vargottama", "à¤¦à¤¶à¤¾à¤‚à¤¶ à¤µà¤°à¥à¤—à¥‹à¤¤à¥à¤¤à¤®"),
    DIGNITY_EXALTED("Exalted", "à¤‰à¤šà¥à¤š"),
    DIGNITY_MOOLATRIKONA("Moolatrikona", "à¤®à¥‚à¤²à¤¤à¥à¤°à¤¿à¤•à¥‹à¤£"),
    DIGNITY_OWN_SIGN("Own Sign", "à¤¸à¥à¤µà¤°à¤¾à¤¶à¤¿"),
    DIGNITY_GREAT_FRIEND("Great Friend", "à¤…à¤§à¤¿à¤®à¤¿à¤¤à¥à¤°"),
    DIGNITY_FRIEND("Friend", "à¤®à¤¿à¤¤à¥à¤°"),
    DIGNITY_NEUTRAL("Neutral", "à¤¸à¤®"),
    DIGNITY_ENEMY("Enemy", "à¤¶à¤¤à¥à¤°à¥"),
    DIGNITY_GREAT_ENEMY("Great Enemy", "à¤…à¤§à¤¿à¤¶à¤¤à¥à¤°à¥"),
    DIGNITY_DEBILITATED("Debilitated", "à¤¨à¥€à¤š"),
    STRENGTH_EXCELLENT("Excellent", "à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ"),
    STRENGTH_GOOD("Good", "à¤°à¤¾à¤®à¥à¤°à¥‹"),
    STRENGTH_AVERAGE("Average", "à¤”à¤¸à¤¤"),
    STRENGTH_WEAK("Weak", "à¤•à¤®à¤œà¥‹à¤°"),
    STRENGTH_VERY_WEAK("Very Weak", "à¤…à¤¤à¤¿ à¤•à¤®à¤œà¥‹à¤°"),
    STRONGEST_PLANET("Strongest Planet", "à¤¬à¤²à¤µà¤¾à¤¨ à¤—à¥à¤°à¤¹"),
    WEAKEST_PLANET("Weakest Planet", "à¤¦à¥à¤°à¥à¤¬à¤² à¤—à¥à¤°à¤¹"),
    AVERAGE_STRENGTH("Average Strength", "à¤”à¤¸à¤¤ à¤¬à¤²"),
    KEY_INSIGHTS("Key Insights", "à¤®à¥à¤–à¥à¤¯ à¤…à¤¨à¥à¤¤à¤°à¥à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿"),
    VARGA_POSITIONS("Varga Positions", "à¤µà¤°à¥à¤— à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤¹à¤°à¥‚"),
    SHODASHVARGA_CALCULATION_ERROR("Failed to calculate Shodashvarga analysis", "à¤·à¥‹à¤¡à¤¶à¤µà¤°à¥à¤— à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤£à¤¨à¤¾ à¤—à¤°à¥à¤¨ à¤…à¤¸à¤«à¤²"),
    SHODASHVARGA_VARGOTTAMA_COUNT_FMT("Vargottama in %d chart(s)", "à¤µà¤°à¥à¤—à¥‹à¤¤à¥à¤¤à¤® %d à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾"),
    SHODASHVARGA_NO_VARGOTTAMA("No Vargottama planets found", "à¤•à¥à¤¨à¥ˆ à¤µà¤°à¥à¤—à¥‹à¤¤à¥à¤¤à¤® à¤—à¥à¤°à¤¹ à¤«à¥‡à¤²à¤¾ à¤ªà¤°à¥‡à¤¨"),
    SHODASHVARGA_16_DIAGRAMS_HEADER("The 16 Divisional Charts:", "à¥§à¥¬ à¤·à¥‹à¤¡à¤¶à¤µà¤°à¥à¤— à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤¹à¤°à¥‚:"),

    // ============================================
    // MRITYU BHAGA (SENSITIVE DEGREES) ANALYSIS
    // ============================================
    MRITYU_BHAGA_TITLE("Mrityu Bhaga Analysis", "à¤®à¥ƒà¤¤à¥à¤¯à¥ à¤­à¤¾à¤— à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    MRITYU_BHAGA_SUBTITLE("Sensitive Degrees Analysis", "à¤¸à¤‚à¤µà¥‡à¤¦à¤¨à¤¶à¥€à¤² à¤…à¤‚à¤¶ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    MRITYU_BHAGA_ABOUT("About Mrityu Bhaga", "à¤®à¥ƒà¤¤à¥à¤¯à¥ à¤­à¤¾à¤—à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    MRITYU_BHAGA_ABOUT_DESC("Mrityu Bhaga indicates critical degrees where planets become vulnerable. Analysis includes Gandanta junctions and auspicious Pushkara placements.", "à¤®à¥ƒà¤¤à¥à¤¯à¥ à¤­à¤¾à¤—à¤²à¥‡ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚ à¤•à¤®à¤œà¥‹à¤° à¤¹à¥à¤¨à¥‡ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤…à¤‚à¤¶à¤¹à¤°à¥‚ à¤¦à¥‡à¤–à¤¾à¤‰à¤à¤›à¥¤ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£à¤®à¤¾ à¤—à¤£à¥à¤¡à¤¾à¤¨à¥à¤¤ à¤¸à¤¨à¥à¤§à¤¿à¤¹à¤°à¥‚ à¤° à¤¶à¥à¤­ à¤ªà¥à¤·à¥à¤•à¤° à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤¹à¤°à¥‚ à¤¸à¤®à¤¾à¤µà¥‡à¤¶ à¤›à¤¨à¥à¥¤"),
    MRITYU_BHAGA_EXACT("Exact", "à¤¯à¤¥à¤¾à¤°à¥à¤¥"),
    MRITYU_BHAGA_VERY_CLOSE("Very Close", "à¤…à¤¤à¤¿ à¤¨à¤œà¤¿à¤•"),
    MRITYU_BHAGA_WITHIN_ORB("Within Orb", "à¤ªà¤°à¤¿à¤§à¤¿ à¤­à¤¿à¤¤à¥à¤°"),
    MRITYU_BHAGA_APPROACHING("Approaching", "à¤¨à¤œà¤¿à¤• à¤†à¤‰à¤à¤¦à¥ˆ"),
    MRITYU_BHAGA_SAFE("Safe", "à¤¸à¥à¤°à¤•à¥à¤·à¤¿à¤¤"),
    MRITYU_BHAGA_DEGREE("Mrityu Bhaga Degree", "à¤®à¥ƒà¤¤à¥à¤¯à¥ à¤­à¤¾à¤— à¤…à¤‚à¤¶"),
    MRITYU_BHAGA_DISTANCE("Distance from Critical Degree", "à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤…à¤‚à¤¶à¤¬à¤¾à¤Ÿ à¤¦à¥‚à¤°à¥€"),
    MRITYU_BHAGA_EFFECTS("Effects", "à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    MRITYU_BHAGA_VULNERABILITIES("Vulnerability Areas", "à¤•à¤®à¤œà¥‹à¤° à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚"),
    GANDANTA_TITLE("Gandanta Analysis", "à¤—à¤£à¥à¤¡à¤¾à¤¨à¥à¤¤ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    GANDANTA_SUBTITLE("Water-Fire Sign Junction Points", "à¤œà¤²-à¤…à¤—à¥à¤¨à¤¿ à¤°à¤¾à¤¶à¤¿ à¤¸à¤¨à¥à¤§à¤¿ à¤¬à¤¿à¤¨à¥à¤¦à¥à¤¹à¤°à¥‚"),
    GANDANTA_EXACT_JUNCTION("Exact Junction", "à¤¯à¤¥à¤¾à¤°à¥à¤¥ à¤¸à¤¨à¥à¤§à¤¿"),
    GANDANTA_CRITICAL("Critical", "à¤—à¤®à¥à¤­à¥€à¤°"),
    GANDANTA_SEVERE("Severe", "à¤¤à¥€à¤µà¥à¤°"),
    GANDANTA_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    GANDANTA_MILD("Mild", "à¤¹à¤²à¥à¤•à¤¾"),
    GANDANTA_BRAHMA("Brahma Gandanta (Cancer-Leo)", "à¤¬à¥à¤°à¤¹à¥à¤® à¤—à¤£à¥à¤¡à¤¾à¤¨à¥à¤¤ (à¤•à¤°à¥à¤•à¤Ÿ-à¤¸à¤¿à¤‚à¤¹)"),
    GANDANTA_VISHNU("Vishnu Gandanta (Scorpio-Sagittarius)", "à¤µà¤¿à¤·à¥à¤£à¥ à¤—à¤£à¥à¤¡à¤¾à¤¨à¥à¤¤ (à¤µà¥ƒà¤¶à¥à¤šà¤¿à¤•-à¤§à¤¨à¥)"),
    GANDANTA_SHIVA("Shiva Gandanta (Pisces-Aries)", "à¤¶à¤¿à¤µ à¤—à¤£à¥à¤¡à¤¾à¤¨à¥à¤¤ (à¤®à¥€à¤¨-à¤®à¥‡à¤·)"),
    PUSHKARA_NAVAMSA_TITLE("Pushkara Navamsa", "à¤ªà¥à¤·à¥à¤•à¤° à¤¨à¤µà¤¾à¤‚à¤¶"),
    PUSHKARA_NAVAMSA_DESC("Highly auspicious navamsa placements providing protection", "à¤¸à¥à¤°à¤•à¥à¤·à¤¾ à¤ªà¥à¤°à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥‡ à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤¶à¥à¤­ à¤¨à¤µà¤¾à¤‚à¤¶ à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤¹à¤°à¥‚"),
    PUSHKARA_BHAGA_TITLE("Pushkara Bhaga", "à¤ªà¥à¤·à¥à¤•à¤° à¤­à¤¾à¤—"),
    PUSHKARA_BHAGA_DESC("Single auspicious degrees providing nourishment", "à¤ªà¥‹à¤·à¤£ à¤ªà¥à¤°à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥‡ à¤à¤•à¤² à¤¶à¥à¤­ à¤…à¤‚à¤¶à¤¹à¤°à¥‚"),
    SENSITIVE_DEGREES_CRITICAL("Critical Planets", "à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    SENSITIVE_DEGREES_AUSPICIOUS("Auspicious Planets", "à¤¶à¥à¤­ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    SENSITIVE_DEGREES_NEEDS_ATTENTION("Needs Attention", "à¤§à¥à¤¯à¤¾à¤¨ à¤†à¤µà¤¶à¥à¤¯à¤•"),
    SENSITIVE_DEGREES_MODERATE_CONCERN("Moderate Concern", "à¤®à¤§à¥à¤¯à¤® à¤šà¤¿à¤¨à¥à¤¤à¤¾"),
    SENSITIVE_DEGREES_BALANCED("Balanced", "à¤¸à¤¨à¥à¤¤à¥à¤²à¤¿à¤¤"),
    SENSITIVE_DEGREES_POSITIVE("Generally Positive", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¯à¤¾ à¤¸à¤•à¤¾à¤°à¤¾à¤¤à¥à¤®à¤•"),
    SENSITIVE_DEGREES_HIGHLY_AUSPICIOUS("Highly Auspicious", "à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤¶à¥à¤­"),

    // ============================================
    // ASHTOTTARI DASHA SYSTEM
    // ============================================
    ASHTOTTARI_DASHA_TITLE("Ashtottari Dasha", "à¤…à¤·à¥à¤Ÿà¥‹à¤¤à¥à¤¤à¤°à¥€ à¤¦à¤¶à¤¾"),
    ASHTOTTARI_DASHA_SUBTITLE("108-Year Dasha Cycle", "à¥§à¥¦à¥®-à¤µà¤°à¥à¤·à¥‡ à¤¦à¤¶à¤¾ à¤šà¤•à¥à¤°"),
    ASHTOTTARI_DASHA_ABOUT("About Ashtottari Dasha", "à¤…à¤·à¥à¤Ÿà¥‹à¤¤à¥à¤¤à¤°à¥€ à¤¦à¤¶à¤¾à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    ASHTOTTARI_DASHA_ABOUT_DESC("Ashtottari Dasha is a 108-year cycle using 8 planets (excluding Ketu). It is applicable when Rahu is in Kendra or Trikona from Lagna lord.", "à¤…à¤·à¥à¤Ÿà¥‹à¤¤à¥à¤¤à¤°à¥€ à¤¦à¤¶à¤¾ à¥® à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚ (à¤•à¥‡à¤¤à¥ à¤¬à¤¾à¤¹à¥‡à¤•) à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¥à¤¨à¥‡ à¥§à¥¦à¥®-à¤µà¤°à¥à¤·à¥‡ à¤šà¤•à¥à¤° à¤¹à¥‹à¥¤ à¤¯à¥‹ à¤²à¤—à¥à¤¨à¥‡à¤¶à¤¬à¤¾à¤Ÿ à¤°à¤¾à¤¹à¥ à¤•à¥‡à¤¨à¥à¤¦à¥à¤° à¤µà¤¾ à¤¤à¥à¤°à¤¿à¤•à¥‹à¤£à¤®à¤¾ à¤¹à¥à¤à¤¦à¤¾ à¤²à¤¾à¤—à¥‚ à¤¹à¥à¤¨à¥à¤›à¥¤"),
    ASHTOTTARI_APPLICABLE("Applicable", "à¤²à¤¾à¤—à¥‚"),
    ASHTOTTARI_NOT_APPLICABLE("Not Applicable", "à¤²à¤¾à¤—à¥‚ à¤¹à¥à¤à¤¦à¥ˆà¤¨"),
    ASHTOTTARI_REASON("Applicability Reason", "à¤ªà¥à¤°à¤¯à¥‹à¤œà¥à¤¯à¤¤à¤¾ à¤•à¤¾à¤°à¤£"),
    ASHTOTTARI_RAHU_FROM_LAGNA_LORD("Rahu from Lagna Lord", "à¤²à¤—à¥à¤¨à¥‡à¤¶à¤¬à¤¾à¤Ÿ à¤°à¤¾à¤¹à¥"),
    ASHTOTTARI_CURRENT_MD("Current Mahadasha", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤®à¤¹à¤¾à¤¦à¤¶à¤¾"),
    ASHTOTTARI_CURRENT_AD("Current Antardasha", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤…à¤¨à¥à¤¤à¤°à¥à¤¦à¤¶à¤¾"),
    ASHTOTTARI_BALANCE_AT_BIRTH("Balance at Birth", "à¤œà¤¨à¥à¤®à¤®à¤¾ à¤¸à¤¨à¥à¤¤à¥à¤²à¤¨"),
    ASHTOTTARI_STARTING_LORD("Starting Dasha Lord", "à¤¸à¥à¤°à¥à¤µà¤¾à¤¤ à¤¦à¤¶à¤¾ à¤¸à¥à¤µà¤¾à¤®à¥€"),
    ASHTOTTARI_PERIOD_YEARS("Period (Years)", "à¤…à¤µà¤§à¤¿ (à¤µà¤°à¥à¤·)"),
    ASHTOTTARI_COMPARISON("Vimsottari vs Ashtottari", "à¤µà¤¿à¤‚à¤¶à¥‹à¤¤à¥à¤¤à¤°à¥€ à¤¬à¤¨à¤¾à¤® à¤…à¤·à¥à¤Ÿà¥‹à¤¤à¥à¤¤à¤°à¥€"),
    ASHTOTTARI_IN_AGREEMENT("Both systems agree", "à¤¦à¥à¤µà¥ˆ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤¸à¤¹à¤®à¤¤"),
    ASHTOTTARI_DIFFERENT("Systems show different planets", "à¤ªà¥à¤°à¤£à¤¾à¤²à¥€à¤¹à¤°à¥‚à¤²à¥‡ à¤«à¤°à¤• à¤—à¥à¤°à¤¹ à¤¦à¥‡à¤–à¤¾à¤‰à¤à¤›à¤¨à¥"),
    ASHTOTTARI_FRIEND("Friendly Period", "à¤®à¥ˆà¤¤à¥à¤°à¥€à¤ªà¥‚à¤°à¥à¤£ à¤…à¤µà¤§à¤¿"),
    ASHTOTTARI_ENEMY("Challenging Period", "à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£ à¤…à¤µà¤§à¤¿"),
    ASHTOTTARI_NEUTRAL("Neutral Period", "à¤¤à¤Ÿà¤¸à¥à¤¥ à¤…à¤µà¤§à¤¿"),
    ASHTOTTARI_SAME("Intensified Period", "à¤¤à¥€à¤µà¥à¤° à¤…à¤µà¤§à¤¿"),

    // ============================================
    // SUDARSHANA CHAKRA DASHA
    // ============================================
    SUDARSHANA_CHAKRA_TITLE("Sudarshana Chakra", "à¤¸à¥à¤¦à¤°à¥à¤¶à¤¨ à¤šà¤•à¥à¤°"),
    SUDARSHANA_CHAKRA_SUBTITLE("Triple Reference Annual Analysis", "à¤¤à¥à¤°à¤¿à¤—à¥à¤£ à¤¸à¤¨à¥à¤¦à¤°à¥à¤­ à¤µà¤¾à¤°à¥à¤·à¤¿à¤• à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SUDARSHANA_CHAKRA_ABOUT("About Sudarshana Chakra", "à¤¸à¥à¤¦à¤°à¥à¤¶à¤¨ à¤šà¤•à¥à¤°à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    SUDARSHANA_CHAKRA_ABOUT_DESC("Sudarshana Chakra analyzes yearly results from three references: Lagna, Moon, and Sun. Each house progresses one year at a time in a 12-year cycle.", "à¤¸à¥à¤¦à¤°à¥à¤¶à¤¨ à¤šà¤•à¥à¤°à¤²à¥‡ à¤¤à¥€à¤¨ à¤¸à¤¨à¥à¤¦à¤°à¥à¤­à¤¬à¤¾à¤Ÿ à¤µà¤¾à¤°à¥à¤·à¤¿à¤• à¤ªà¤°à¤¿à¤£à¤¾à¤® à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤°à¥à¤›: à¤²à¤—à¥à¤¨, à¤šà¤¨à¥à¤¦à¥à¤°, à¤° à¤¸à¥‚à¤°à¥à¤¯à¥¤ à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤­à¤¾à¤µ à¥§à¥¨-à¤µà¤°à¥à¤·à¥‡ à¤šà¤•à¥à¤°à¤®à¤¾ à¤à¤• à¤ªà¤Ÿà¤•à¤®à¤¾ à¤à¤• à¤µà¤°à¥à¤· à¤…à¤—à¤¾à¤¡à¤¿ à¤¬à¤¢à¥à¤›à¥¤"),
    SUDARSHANA_CURRENT_AGE("Current Age", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤‰à¤®à¥‡à¤°"),
    SUDARSHANA_LAGNA_REFERENCE("Lagna Reference", "à¤²à¤—à¥à¤¨ à¤¸à¤¨à¥à¤¦à¤°à¥à¤­"),
    SUDARSHANA_CHANDRA_REFERENCE("Moon Reference", "à¤šà¤¨à¥à¤¦à¥à¤° à¤¸à¤¨à¥à¤¦à¤°à¥à¤­"),
    SUDARSHANA_SURYA_REFERENCE("Sun Reference", "à¤¸à¥‚à¤°à¥à¤¯ à¤¸à¤¨à¥à¤¦à¤°à¥à¤­"),
    SUDARSHANA_ACTIVE_HOUSE("Active House", "à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤­à¤¾à¤µ"),
    SUDARSHANA_ACTIVE_SIGN("Active Sign", "à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤°à¤¾à¤¶à¤¿"),
    SUDARSHANA_SIGN_LORD("Sign Lord", "à¤°à¤¾à¤¶à¤¿ à¤¸à¥à¤µà¤¾à¤®à¥€"),
    SUDARSHANA_CYCLE_NUMBER("Cycle Number", "à¤šà¤•à¥à¤° à¤¸à¤‚à¤–à¥à¤¯à¤¾"),
    SUDARSHANA_HOUSE_THEMES("House Themes", "à¤­à¤¾à¤µ à¤µà¤¿à¤·à¤¯à¤¹à¤°à¥‚"),
    SUDARSHANA_SYNTHESIS("Synthesis", "à¤¸à¤‚à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SUDARSHANA_COMBINED_STRENGTH("Combined Strength", "à¤¸à¤‚à¤¯à¥à¤•à¥à¤¤ à¤¬à¤²"),
    SUDARSHANA_COMMON_THEMES("Common Themes", "à¤¸à¤¾à¤à¤¾ à¤µà¤¿à¤·à¤¯à¤¹à¤°à¥‚"),
    SUDARSHANA_CONFLICTING_THEMES("Conflicting Themes", "à¤µà¤¿à¤°à¥‹à¤§à¤¾à¤­à¤¾à¤¸à¥€ à¤µà¤¿à¤·à¤¯à¤¹à¤°à¥‚"),
    SUDARSHANA_PRIMARY_FOCUS("Primary Focus", "à¤ªà¥à¤°à¤¾à¤¥à¤®à¤¿à¤• à¤•à¥‡à¤¨à¥à¤¦à¥à¤°"),
    SUDARSHANA_SECONDARY_FOCUS("Secondary Focus", "à¤®à¤¾à¤§à¥à¤¯à¤®à¤¿à¤• à¤•à¥‡à¤¨à¥à¤¦à¥à¤°"),
    SUDARSHANA_YEARLY_PROGRESSION("Yearly Progression", "à¤µà¤¾à¤°à¥à¤·à¤¿à¤• à¤ªà¥à¤°à¤—à¤¤à¤¿"),
    SUDARSHANA_PREVIOUS_YEAR("Previous Year", "à¤—à¤¤ à¤µà¤°à¥à¤·"),
    SUDARSHANA_CURRENT_YEAR("Current Year", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤µà¤°à¥à¤·"),
    SUDARSHANA_NEXT_YEAR("Next Year", "à¤†à¤—à¤¾à¤®à¥€ à¤µà¤°à¥à¤·"),
    SUDARSHANA_TREND("Trend", "à¤ªà¥à¤°à¤µà¥ƒà¤¤à¥à¤¤à¤¿"),
    SUDARSHANA_EXCELLENT("Excellent", "à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ"),
    SUDARSHANA_GOOD("Good", "à¤°à¤¾à¤®à¥à¤°à¥‹"),
    SUDARSHANA_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    SUDARSHANA_WEAK("Weak", "à¤•à¤®à¤œà¥‹à¤°"),
    SUDARSHANA_VERY_WEAK("Very Weak", "à¤…à¤¤à¤¿ à¤•à¤®à¤œà¥‹à¤°"),

    // ============================================
    // UPACHAYA TRANSIT TRACKER
    // ============================================
    UPACHAYA_TRANSIT_TITLE("Upachaya Transits", "à¤‰à¤ªà¤šà¤¯ à¤—à¥‹à¤šà¤°"),
    UPACHAYA_TRANSIT_SUBTITLE("Growth House Transit Analysis", "à¤µà¥ƒà¤¦à¥à¤§à¤¿ à¤­à¤¾à¤µ à¤—à¥‹à¤šà¤° à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    UPACHAYA_TRANSIT_ABOUT("About Upachaya Houses", "à¤‰à¤ªà¤šà¤¯ à¤­à¤¾à¤µà¤¹à¤°à¥‚à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    UPACHAYA_TRANSIT_ABOUT_DESC("Upachaya houses (3, 6, 10, 11) are growth houses where planets, especially natural malefics, give increasingly positive results over time.", "à¤‰à¤ªà¤šà¤¯ à¤­à¤¾à¤µà¤¹à¤°à¥‚ (à¥©, à¥¬, à¥§à¥¦, à¥§à¥§) à¤µà¥ƒà¤¦à¥à¤§à¤¿ à¤­à¤¾à¤µà¤¹à¤°à¥‚ à¤¹à¥à¤¨à¥ à¤œà¤¹à¤¾à¤ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚, à¤µà¤¿à¤¶à¥‡à¤· à¤—à¤°à¥€ à¤ªà¥à¤°à¤¾à¤•à¥ƒà¤¤à¤¿à¤• à¤ªà¤¾à¤ªà¤—à¥à¤°à¤¹, à¤¸à¤®à¤¯à¤¸à¤à¤—à¥ˆ à¤¬à¤¢à¥à¤¦à¥‹ à¤¸à¤•à¤¾à¤°à¤¾à¤¤à¥à¤®à¤• à¤ªà¤°à¤¿à¤£à¤¾à¤®à¤¹à¤°à¥‚ à¤¦à¤¿à¤¨à¥à¤›à¤¨à¥à¥¤"),
    UPACHAYA_HOUSE_3("3rd House (Courage)", "à¥© à¤­à¤¾à¤µ (à¤¸à¤¾à¤¹à¤¸)"),
    UPACHAYA_HOUSE_6("6th House (Enemies)", "à¥¬ à¤­à¤¾à¤µ (à¤¶à¤¤à¥à¤°à¥)"),
    UPACHAYA_HOUSE_10("10th House (Career)", "à¥§à¥¦ à¤­à¤¾à¤µ (à¤•à¤°à¤¿à¤¯à¤°)"),
    UPACHAYA_HOUSE_11("11th House (Gains)", "à¥§à¥§ à¤­à¤¾à¤µ (à¤²à¤¾à¤­)"),
    UPACHAYA_FROM_MOON("From Moon", "à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤¾à¤Ÿ"),
    UPACHAYA_FROM_LAGNA("From Lagna", "à¤²à¤—à¥à¤¨à¤¬à¤¾à¤Ÿ"),
    UPACHAYA_TRANSIT_QUALITY("Transit Quality", "à¤—à¥‹à¤šà¤° à¤—à¥à¤£à¤¸à¥à¤¤à¤°"),
    UPACHAYA_QUALITY_EXCELLENT("Excellent", "à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ"),
    UPACHAYA_QUALITY_GOOD("Good", "à¤°à¤¾à¤®à¥à¤°à¥‹"),
    UPACHAYA_QUALITY_FAVORABLE("Favorable", "à¤…à¤¨à¥à¤•à¥‚à¤²"),
    UPACHAYA_QUALITY_NEUTRAL("Neutral", "à¤¤à¤Ÿà¤¸à¥à¤¥"),
    UPACHAYA_SIGNIFICANCE("Significance", "à¤®à¤¹à¤¤à¥à¤¤à¥à¤µ"),
    UPACHAYA_DURATION("Duration", "à¤…à¤µà¤§à¤¿"),
    UPACHAYA_ACTIVE_TRANSITS("Active Upachaya Transits", "à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤‰à¤ªà¤šà¤¯ à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚"),
    UPACHAYA_MOST_SIGNIFICANT("Most Significant Transits", "à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚"),
    UPACHAYA_HOUSE_WISE("House-wise Analysis", "à¤­à¤¾à¤µ-à¤…à¤¨à¥à¤¸à¤¾à¤° à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    UPACHAYA_ASSESSMENT("Overall Assessment", "à¤¸à¤®à¤—à¥à¤° à¤®à¥‚à¤²à¥à¤¯à¤¾à¤‚à¤•à¤¨"),
    UPACHAYA_LEVEL_EXCEPTIONAL("Exceptional Period", "à¤…à¤¸à¤¾à¤§à¤¾à¤°à¤£ à¤…à¤µà¤§à¤¿"),
    UPACHAYA_LEVEL_HIGH("High Activity", "à¤‰à¤šà¥à¤š à¤¸à¤•à¥à¤°à¤¿à¤¯à¤¤à¤¾"),
    UPACHAYA_LEVEL_MODERATE("Moderate Activity", "à¤®à¤§à¥à¤¯à¤® à¤¸à¤•à¥à¤°à¤¿à¤¯à¤¤à¤¾"),
    UPACHAYA_LEVEL_LOW("Low Activity", "à¤•à¤® à¤¸à¤•à¥à¤°à¤¿à¤¯à¤¤à¤¾"),
    UPACHAYA_ALERTS("Transit Alerts", "à¤—à¥‹à¤šà¤° à¤¸à¤šà¥‡à¤¤à¤¨à¤¾à¤¹à¤°à¥‚"),
    UPACHAYA_ALERT_OPPORTUNITY("Opportunity", "à¤…à¤µà¤¸à¤°"),
    UPACHAYA_ALERT_MAJOR_TRANSIT("Major Transit", "à¤ªà¥à¤°à¤®à¥à¤– à¤—à¥‹à¤šà¤°"),
    UPACHAYA_ALERT_FORTUNE("Fortune", "à¤­à¤¾à¤—à¥à¤¯"),
    UPACHAYA_UPCOMING("Upcoming Transits", "à¤†à¤—à¤¾à¤®à¥€ à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚"),
    UPACHAYA_HOUSE_INACTIVE("Inactive", "à¤¨à¤¿à¤·à¥à¤•à¥à¤°à¤¿à¤¯"),
    UPACHAYA_HOUSE_MILD("Mild", "à¤¹à¤²à¥à¤•à¤¾"),
    UPACHAYA_HOUSE_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    UPACHAYA_HOUSE_STRONG("Strong", "à¤¬à¤²à¤¿à¤¯à¥‹"),
    UPACHAYA_HOUSE_VERY_STRONG("Very Strong", "à¤…à¤¤à¤¿ à¤¬à¤²à¤¿à¤¯à¥‹"),

    // ============================================
    // LAL KITAB REMEDIES
    // ============================================
    LAL_KITAB_TITLE("Lal Kitab Analysis", "à¤²à¤¾à¤² à¤•à¤¿à¤¤à¤¾à¤¬ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    LAL_KITAB_SUBTITLE("Traditional Remedial System", "à¤ªà¤°à¤®à¥à¤ªà¤°à¤¾à¤—à¤¤ à¤‰à¤ªà¤šà¤¾à¤° à¤ªà¥à¤°à¤£à¤¾à¤²à¥€"),
    LAL_KITAB_ABOUT("About Lal Kitab", "à¤²à¤¾à¤² à¤•à¤¿à¤¤à¤¾à¤¬à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    LAL_KITAB_ABOUT_DESC("Lal Kitab is a distinct astrological system with practical, cost-effective remedies. It differs from classical Vedic astrology in house analysis and remedy approach.", "à¤²à¤¾à¤² à¤•à¤¿à¤¤à¤¾à¤¬ à¤µà¥à¤¯à¤¾à¤µà¤¹à¤¾à¤°à¤¿à¤•, à¤²à¤¾à¤—à¤¤-à¤ªà¥à¤°à¤­à¤¾à¤µà¥€ à¤‰à¤ªà¤šà¤¾à¤°à¤¹à¤°à¥‚ à¤­à¤à¤•à¥‹ à¤à¤• à¤µà¤¿à¤¶à¤¿à¤·à¥à¤Ÿ à¤œà¥à¤¯à¥‹à¤¤à¤¿à¤· à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤¹à¥‹à¥¤ à¤¯à¥‹ à¤­à¤¾à¤µ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤° à¤‰à¤ªà¤šà¤¾à¤° à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤•à¥‹à¤£à¤®à¤¾ à¤¶à¤¾à¤¸à¥à¤¤à¥à¤°à¥€à¤¯ à¤µà¥ˆà¤¦à¤¿à¤• à¤œà¥à¤¯à¥‹à¤¤à¤¿à¤·à¤­à¤¨à¥à¤¦à¤¾ à¤«à¤°à¤• à¤›à¥¤"),
    LAL_KITAB_SYSTEM_NOTE("System Note", "à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤¨à¥‹à¤Ÿ"),
    LAL_KITAB_PLANETARY_AFFLICTIONS("Planetary Afflictions", "à¤—à¥à¤°à¤¹ à¤ªà¥€à¤¡à¤¾à¤¹à¤°à¥‚"),
    LAL_KITAB_KARMIC_DEBTS("Karmic Debts (Rin)", "à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤‹à¤£"),
    LAL_KITAB_HOUSE_ANALYSIS("House Analysis", "à¤­à¤¾à¤µ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    LAL_KITAB_REMEDIES("Remedies", "à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    LAL_KITAB_COLOR_REMEDIES("Color Remedies", "à¤°à¤‚à¤— à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    LAL_KITAB_DIRECTION_REMEDIES("Direction Remedies", "à¤¦à¤¿à¤¶à¤¾ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    LAL_KITAB_ANNUAL_CALENDAR("Weekly Remedy Calendar", "à¤¸à¤¾à¤ªà¥à¤¤à¤¾à¤¹à¤¿à¤• à¤‰à¤ªà¤¾à¤¯ à¤ªà¤¾à¤¤à¥à¤°à¥‹"),
    LAL_KITAB_GENERAL_RECOMMENDATIONS("General Recommendations", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸à¤¹à¤°à¥‚"),
    LAL_KITAB_AFFLICTION_TYPE("Affliction Type", "à¤ªà¥€à¤¡à¤¾ à¤ªà¥à¤°à¤•à¤¾à¤°"),
    LAL_KITAB_AFFLICTION_SEVERITY("Severity", "à¤—à¤®à¥à¤­à¥€à¤°à¤¤à¤¾"),
    LAL_KITAB_PITRU_DOSH("Pitru Dosh", "à¤ªà¤¿à¤¤à¥ƒ à¤¦à¥‹à¤·"),
    LAL_KITAB_MATRU_RIN("Matru Rin", "à¤®à¤¾à¤¤à¥ƒ à¤‹à¤£"),
    LAL_KITAB_STRI_RIN("Stri Rin", "à¤¸à¥à¤¤à¥à¤°à¥€ à¤‹à¤£"),
    LAL_KITAB_KANYA_RIN("Kanya Rin", "à¤•à¤¨à¥à¤¯à¤¾ à¤‹à¤£"),
    LAL_KITAB_GRAHAN_DOSH("Grahan Dosh", "à¤—à¥à¤°à¤¹à¤£ à¤¦à¥‹à¤·"),
    LAL_KITAB_SHANI_PEEDA("Shani Peeda", "à¤¶à¤¨à¤¿ à¤ªà¥€à¤¡à¤¾"),
    LAL_KITAB_SEVERITY_MINIMAL("Minimal", "à¤¨à¥à¤¯à¥‚à¤¨à¤¤à¤®"),
    LAL_KITAB_SEVERITY_MILD("Mild", "à¤¹à¤²à¥à¤•à¤¾"),
    LAL_KITAB_SEVERITY_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    LAL_KITAB_SEVERITY_SEVERE("Severe", "à¤—à¤®à¥à¤­à¥€à¤°"),
    LAL_KITAB_DEBT_TYPE("Debt Type", "à¤‹à¤£ à¤ªà¥à¤°à¤•à¤¾à¤°"),
    LAL_KITAB_DEBT_INDICATORS("Indicators", "à¤¸à¥‚à¤šà¤•à¤¹à¤°à¥‚"),
    LAL_KITAB_DEBT_EFFECTS("Effects", "à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    LAL_KITAB_HOUSE_STATUS("House Status", "à¤­à¤¾à¤µ à¤¸à¥à¤¥à¤¿à¤¤à¤¿"),
    LAL_KITAB_HOUSE_EMPTY("Empty", "à¤–à¤¾à¤²à¥€"),
    LAL_KITAB_HOUSE_OCCUPIED("Occupied", "à¤­à¤°à¤¿à¤à¤•à¥‹"),
    LAL_KITAB_HOUSE_BENEFIC("Benefic Influence", "à¤¶à¥à¤­ à¤ªà¥à¤°à¤­à¤¾à¤µ"),
    LAL_KITAB_HOUSE_AFFLICTED("Afflicted", "à¤ªà¥€à¤¡à¤¿à¤¤"),
    LAL_KITAB_REMEDY_CATEGORY("Remedy Category", "à¤‰à¤ªà¤¾à¤¯ à¤¶à¥à¤°à¥‡à¤£à¥€"),
    LAL_KITAB_CATEGORY_PLANETARY("Planetary Remedy", "à¤—à¥à¤°à¤¹ à¤‰à¤ªà¤¾à¤¯"),
    LAL_KITAB_CATEGORY_KARMIC("Karmic Debt Remedy", "à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤‹à¤£ à¤‰à¤ªà¤¾à¤¯"),
    LAL_KITAB_CATEGORY_HOUSE("House-based Remedy", "à¤­à¤¾à¤µ-à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤‰à¤ªà¤¾à¤¯"),
    LAL_KITAB_CATEGORY_GENERAL("General Remedy", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤‰à¤ªà¤¾à¤¯"),
    LAL_KITAB_REMEDY_METHOD("Method", "à¤µà¤¿à¤§à¤¿"),
    LAL_KITAB_REMEDY_FREQUENCY("Frequency", "à¤†à¤µà¥ƒà¤¤à¥à¤¤à¤¿"),
    LAL_KITAB_REMEDY_DURATION("Duration", "à¤…à¤µà¤§à¤¿"),
    LAL_KITAB_REMEDY_EFFECTIVENESS("Effectiveness", "à¤ªà¥à¤°à¤­à¤¾à¤µà¤•à¤¾à¤°à¤¿à¤¤à¤¾"),
    LAL_KITAB_FAVORABLE_COLORS("Favorable Colors", "à¤¶à¥à¤­ à¤°à¤‚à¤—à¤¹à¤°à¥‚"),
    LAL_KITAB_AVOID_COLORS("Colors to Avoid", "à¤ªà¤°à¤¿à¤¹à¤¾à¤° à¤—à¤°à¥à¤¨à¥‡ à¤°à¤‚à¤—à¤¹à¤°à¥‚"),
    LAL_KITAB_COLOR_APPLICATION("Application", "à¤ªà¥à¤°à¤¯à¥‹à¤—"),
    LAL_KITAB_FAVORABLE_DIRECTION("Favorable Direction", "à¤¶à¥à¤­ à¤¦à¤¿à¤¶à¤¾"),
    LAL_KITAB_AVOID_DIRECTION("Direction to Avoid", "à¤ªà¤°à¤¿à¤¹à¤¾à¤° à¤—à¤°à¥à¤¨à¥‡ à¤¦à¤¿à¤¶à¤¾"),
    LAL_KITAB_DIRECTION_APPLICATION("Direction Application", "à¤¦à¤¿à¤¶à¤¾ à¤ªà¥à¤°à¤¯à¥‹à¤—"),
    LAL_KITAB_SUNDAY("Sunday - Sun", "à¤†à¤‡à¤¤à¤¬à¤¾à¤° - à¤¸à¥‚à¤°à¥à¤¯"),
    LAL_KITAB_MONDAY("Monday - Moon", "à¤¸à¥‹à¤®à¤¬à¤¾à¤° - à¤šà¤¨à¥à¤¦à¥à¤°"),
    LAL_KITAB_TUESDAY("Tuesday - Mars", "à¤®à¤‚à¤—à¤²à¤¬à¤¾à¤° - à¤®à¤‚à¤—à¤²"),
    LAL_KITAB_WEDNESDAY("Wednesday - Mercury", "à¤¬à¥à¤§à¤¬à¤¾à¤° - à¤¬à¥à¤§"),
    LAL_KITAB_THURSDAY("Thursday - Jupiter", "à¤¬à¤¿à¤¹à¥€à¤¬à¤¾à¤° - à¤¬à¥ƒà¤¹à¤¸à¥à¤ªà¤¤à¤¿"),
    LAL_KITAB_FRIDAY("Friday - Venus", "à¤¶à¥à¤•à¥à¤°à¤¬à¤¾à¤° - à¤¶à¥à¤•à¥à¤°"),
    LAL_KITAB_SATURDAY("Saturday - Saturn", "à¤¶à¤¨à¤¿à¤¬à¤¾à¤° - à¤¶à¤¨à¤¿"),

    // Bhrigu Bindu
    BHRIGU_BINDU_REMEDIES("Remedial Measures", "à¤‰à¤ªà¤šà¤¾à¤°à¤¾à¤¤à¥à¤®à¤• à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    BHRIGU_BINDU_ABOUT_DESC("The Bhrigu Bindu is a sensitive mathematical point between the Moon and Rahu that reveals karmic destiny and life purpose.", "à¤­à¥ƒà¤—à¥ à¤¬à¤¿à¤¨à¥à¤¦à¥ à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤° à¤°à¤¾à¤¹à¥ à¤¬à¥€à¤šà¤•à¥‹ à¤à¤• à¤¸à¤‚à¤µà¥‡à¤¦à¤¨à¤¶à¥€à¤² à¤—à¤£à¤¿à¤¤à¥€à¤¯ à¤¬à¤¿à¤¨à¥à¤¦à¥ à¤¹à¥‹ à¤œà¤¸à¤²à¥‡ à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤­à¤¾à¤—à¥à¤¯ à¤° à¤œà¥€à¤µà¤¨ à¤‰à¤¦à¥à¤¦à¥‡à¤¶à¥à¤¯ à¤ªà¥à¤°à¤•à¤Ÿ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    BHRIGU_BINDU_CALC_ERROR("Unable to calculate Bhrigu Bindu. Please check chart data.", "à¤­à¥ƒà¤—à¥ à¤¬à¤¿à¤¨à¥à¤¦à¥ à¤—à¤£à¤¨à¤¾ à¤—à¤°à¥à¤¨ à¤…à¤¸à¤®à¤°à¥à¤¥à¥¤ à¤•à¥ƒà¤ªà¤¯à¤¾ à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤¡à¤¾à¤Ÿà¤¾ à¤œà¤¾à¤à¤š à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    POSITION_INTERPRETATION("Position Interpretation", "à¤¸à¥à¤¥à¤¿à¤¤à¤¿ à¤µà¥à¤¯à¤¾à¤–à¥à¤¯à¤¾"),
    STRENGTH_FACTORS("Strength Factors", "à¤¬à¤² à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚"),
    TRANSIT_DATA_UNAVAILABLE("Transit data not available", "à¤—à¥‹à¤šà¤° à¤¡à¤¾à¤Ÿà¤¾ à¤‰à¤ªà¤²à¤¬à¥à¤§ à¤›à¥ˆà¤¨"),
    CURRENT_PLANETARY_POSITIONS("Current Planetary Positions", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤—à¥à¤°à¤¹ à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤¹à¤°à¥‚"),
    UPCOMING_TRANSITS("Upcoming Significant Transits", "à¤†à¤—à¤¾à¤®à¥€ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚"),
    TRANSIT_TIMING("Transit Timing", "à¤—à¥‹à¤šà¤° à¤¸à¤®à¤¯"),
    TRANSIT_TIMING_DESC("When slow-moving planets (Saturn, Jupiter, Rahu, Ketu) transit over your Bhrigu Bindu, significant karmic events tend to manifest.", "à¤œà¤¬ à¤¢à¤¿à¤²à¥‹ à¤¹à¤¿à¤¡à¥à¤¨à¥‡ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚ (à¤¶à¤¨à¤¿, à¤¬à¥ƒà¤¹à¤¸à¥à¤ªà¤¤à¤¿, à¤°à¤¾à¤¹à¥, à¤•à¥‡à¤¤à¥) à¤¤à¤ªà¤¾à¤‡à¤à¤•à¥‹ à¤­à¥ƒà¤—à¥ à¤¬à¤¿à¤¨à¥à¤¦à¥ à¤®à¤¾à¤¥à¤¿ à¤—à¥‹à¤šà¤° à¤—à¤°à¥à¤›à¤¨à¥, à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤˜à¤Ÿà¤¨à¤¾à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤•à¤Ÿ à¤¹à¥à¤¨à¥à¤›à¤¨à¥à¥¤"),
    CONJUNCT("Conjunct", "à¤¯à¥à¤¤à¤¿"),
    APPLYING("Applying", "à¤²à¤¾à¤—à¥‚ à¤¹à¥à¤à¤¦à¥ˆ"),

    // ============================================
    // DIVISIONAL CHART ANALYZER
    // ============================================
    HORA_ANALYSIS_TITLE("Hora (D-2) Analysis", "à¤¹à¥‹à¤°à¤¾ (D-2) à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    HORA_ANALYSIS_SUBTITLE("Wealth and Financial Potential", "à¤§à¤¨ à¤° à¤†à¤°à¥à¤¥à¤¿à¤• à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾"),
    HORA_ANALYSIS_ABOUT("About Hora Chart", "à¤¹à¥‹à¤°à¤¾ à¤šà¤¾à¤°à¥à¤Ÿà¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    HORA_ANALYSIS_ABOUT_DESC("Hora chart divides each sign into Sun and Moon halves, revealing wealth accumulation patterns and financial potential.", "à¤¹à¥‹à¤°à¤¾ à¤šà¤¾à¤°à¥à¤Ÿà¤²à¥‡ à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤°à¤¾à¤¶à¤¿à¤²à¤¾à¤ˆ à¤¸à¥‚à¤°à¥à¤¯ à¤° à¤šà¤¨à¥à¤¦à¥à¤° à¤†à¤§à¤¾à¤®à¤¾ à¤µà¤¿à¤­à¤¾à¤œà¤¨ à¤—à¤°à¥à¤›, à¤§à¤¨ à¤¸à¤‚à¤šà¤¯ à¤¢à¤¾à¤à¤šà¤¾ à¤° à¤†à¤°à¥à¤¥à¤¿à¤• à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾ à¤ªà¥à¤°à¤•à¤Ÿ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    HORA_SUN_PLANETS("Planets in Sun Hora", "à¤¸à¥‚à¤°à¥à¤¯ à¤¹à¥‹à¤°à¤¾à¤®à¤¾ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    HORA_MOON_PLANETS("Planets in Moon Hora", "à¤šà¤¨à¥à¤¦à¥à¤° à¤¹à¥‹à¤°à¤¾à¤®à¤¾ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    HORA_DOMINANT("Dominant Hora", "à¤ªà¥à¤°à¤­à¤¾à¤µà¤¶à¤¾à¤²à¥€ à¤¹à¥‹à¤°à¤¾"),
    HORA_BALANCE("Hora Balance", "à¤¹à¥‹à¤°à¤¾ à¤¸à¤¨à¥à¤¤à¥à¤²à¤¨"),
    HORA_WEALTH_INDICATORS("Wealth Indicators", "à¤§à¤¨ à¤¸à¥‚à¤šà¤•à¤¹à¤°à¥‚"),
    HORA_INCOME_POTENTIAL("Income Potential", "à¤†à¤¯ à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾"),
    DREKKANA_ANALYSIS_TITLE("Drekkana (D-3) Analysis", "à¤¦à¥à¤°à¥‡à¤·à¥à¤•à¤¾à¤£ (D-3) à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    DREKKANA_ANALYSIS_SUBTITLE("Siblings and Courage", "à¤­à¤¾à¤‡à¤¬à¤¹à¤¿à¤¨à¥€ à¤° à¤¸à¤¾à¤¹à¤¸"),
    DREKKANA_ANALYSIS_ABOUT("About Drekkana Chart", "à¤¦à¥à¤°à¥‡à¤·à¥à¤•à¤¾à¤£ à¤šà¤¾à¤°à¥à¤Ÿà¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    DREKKANA_ANALYSIS_ABOUT_DESC("Drekkana chart reveals sibling relationships, courage, and personal initiative. Each sign is divided into three 10-degree portions.", "à¤¦à¥à¤°à¥‡à¤·à¥à¤•à¤¾à¤£ à¤šà¤¾à¤°à¥à¤Ÿà¤²à¥‡ à¤­à¤¾à¤‡à¤¬à¤¹à¤¿à¤¨à¥€ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§, à¤¸à¤¾à¤¹à¤¸, à¤° à¤µà¥à¤¯à¤•à¥à¤¤à¤¿à¤—à¤¤ à¤ªà¤¹à¤²à¤•à¤¦à¤®à¥€ à¤ªà¥à¤°à¤•à¤Ÿ à¤—à¤°à¥à¤¦à¤›à¥¤ à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤°à¤¾à¤¶à¤¿ à¤¤à¥€à¤¨ à¥§à¥¦-à¤…à¤‚à¤¶ à¤­à¤¾à¤—à¤®à¤¾ à¤µà¤¿à¤­à¤¾à¤œà¤¿à¤¤ à¤›à¥¤"),
    DREKKANA_TYPE("Drekkana Type", "à¤¦à¥à¤°à¥‡à¤·à¥à¤•à¤¾à¤£ à¤ªà¥à¤°à¤•à¤¾à¤°"),
    DREKKANA_FIRST("First Drekkana (0-10Â°)", "à¤ªà¤¹à¤¿à¤²à¥‹ à¤¦à¥à¤°à¥‡à¤·à¥à¤•à¤¾à¤£ (à¥¦-à¥§à¥¦Â°)"),
    DREKKANA_SECOND("Second Drekkana (10-20Â°)", "à¤¦à¥‹à¤¸à¥à¤°à¥‹ à¤¦à¥à¤°à¥‡à¤·à¥à¤•à¤¾à¤£ (à¥§à¥¦-à¥¨à¥¦Â°)"),
    DREKKANA_THIRD("Third Drekkana (20-30Â°)", "à¤¤à¥‡à¤¸à¥à¤°à¥‹ à¤¦à¥à¤°à¥‡à¤·à¥à¤•à¤¾à¤£ (à¥¨à¥¦-à¥©à¥¦Â°)"),
    DREKKANA_SIBLING_KARMA("Sibling Karma", "à¤­à¤¾à¤‡à¤¬à¤¹à¤¿à¤¨à¥€ à¤•à¤°à¥à¤®"),
    DREKKANA_COURAGE_LEVEL("Courage Level", "à¤¸à¤¾à¤¹à¤¸ à¤¸à¥à¤¤à¤°"),
    NAVAMSA_MARRIAGE_TITLE("Navamsa Marriage Analysis", "à¤¨à¤µà¤¾à¤‚à¤¶ à¤µà¤¿à¤µà¤¾à¤¹ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    NAVAMSA_MARRIAGE_SUBTITLE("D-9 Partnership Timing", "D-9 à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¥€ à¤¸à¤®à¤¯"),
    NAVAMSA_MARRIAGE_ABOUT("About Navamsa for Marriage", "à¤µà¤¿à¤µà¤¾à¤¹à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¨à¤µà¤¾à¤‚à¤¶à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    NAVAMSA_MARRIAGE_ABOUT_DESC("Navamsa is the most important divisional chart for marriage timing, spouse characteristics, and relationship quality.", "à¤¨à¤µà¤¾à¤‚à¤¶ à¤µà¤¿à¤µà¤¾à¤¹ à¤¸à¤®à¤¯, à¤œà¥€à¤µà¤¨à¤¸à¤¾à¤¥à¥€ à¤µà¤¿à¤¶à¥‡à¤·à¤¤à¤¾à¤¹à¤°à¥‚, à¤° à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤—à¥à¤£à¤¸à¥à¤¤à¤°à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤µà¤¿à¤­à¤¾à¤œà¤¨ à¤šà¤¾à¤°à¥à¤Ÿ à¤¹à¥‹à¥¤"),
    NAVAMSA_VENUS_POSITION("Venus in Navamsa", "à¤¨à¤µà¤¾à¤‚à¤¶à¤®à¤¾ à¤¶à¥à¤•à¥à¤°"),
    NAVAMSA_7TH_LORD("7th Lord in Navamsa", "à¤¨à¤µà¤¾à¤‚à¤¶à¤®à¤¾ à¥­à¤® à¤¸à¥à¤µà¤¾à¤®à¥€"),
    NAVAMSA_SPOUSE_INDICATORS("Spouse Indicators", "à¤œà¥€à¤µà¤¨à¤¸à¤¾à¤¥à¥€ à¤¸à¥‚à¤šà¤•à¤¹à¤°à¥‚"),
    NAVAMSA_MARRIAGE_TIMING("Marriage Timing", "à¤µà¤¿à¤µà¤¾à¤¹ à¤¸à¤®à¤¯"),
    NAVAMSA_RELATIONSHIP_QUALITY("Relationship Quality", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤—à¥à¤£à¤¸à¥à¤¤à¤°"),
    DASHAMSA_CAREER_TITLE("Dashamsa (D-10) Career Analysis", "à¤¦à¤¶à¤¾à¤‚à¤¶ (D-10) à¤•à¤°à¤¿à¤¯à¤° à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    DASHAMSA_CAREER_SUBTITLE("Professional Life and Status", "à¤µà¥à¤¯à¤¾à¤µà¤¸à¤¾à¤¯à¤¿à¤• à¤œà¥€à¤µà¤¨ à¤° à¤¸à¥à¤¥à¤¿à¤¤à¤¿"),
    DASHAMSA_CAREER_ABOUT("About Dashamsa Chart", "à¤¦à¤¶à¤¾à¤‚à¤¶ à¤šà¤¾à¤°à¥à¤Ÿà¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    DASHAMSA_CAREER_ABOUT_DESC("Dashamsa reveals career potential, professional achievements, and worldly status through 10th harmonic analysis.", "à¤¦à¤¶à¤¾à¤‚à¤¶à¤²à¥‡ à¥§à¥¦à¤”à¤‚ à¤¹à¤¾à¤°à¥à¤®à¥‹à¤¨à¤¿à¤• à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤®à¤¾à¤°à¥à¤«à¤¤ à¤•à¤°à¤¿à¤¯à¤° à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾, à¤µà¥à¤¯à¤¾à¤µà¤¸à¤¾à¤¯à¤¿à¤• à¤‰à¤ªà¤²à¤¬à¥à¤§à¤¿, à¤° à¤¸à¤¾à¤‚à¤¸à¤¾à¤°à¤¿à¤• à¤¸à¥à¤¥à¤¿à¤¤à¤¿ à¤ªà¥à¤°à¤•à¤Ÿ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    DASHAMSA_10TH_LORD("10th Lord in D-10", "D-10 à¤®à¤¾ à¥§à¥¦à¤”à¤‚ à¤¸à¥à¤µà¤¾à¤®à¥€"),
    DASHAMSA_LAGNA_LORD("D-10 Lagna Lord", "D-10 à¤²à¤—à¥à¤¨ à¤¸à¥à¤µà¤¾à¤®à¥€"),
    DASHAMSA_CAREER_HOUSES("Career Houses Analysis", "à¤•à¤°à¤¿à¤¯à¤° à¤­à¤¾à¤µ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    DASHAMSA_PROFESSION_TYPE("Profession Indicators", "à¤ªà¥‡à¤¶à¤¾ à¤¸à¥‚à¤šà¤•à¤¹à¤°à¥‚"),
    DASHAMSA_STATUS_POTENTIAL("Status Potential", "à¤¸à¥à¤¥à¤¿à¤¤à¤¿ à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾"),
    DWADASAMSA_TITLE("Dwadasamsa (D-12) Analysis", "à¤¦à¥à¤µà¤¾à¤¦à¤¶à¤¾à¤‚à¤¶ (D-12) à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    DWADASAMSA_SUBTITLE("Parents and Ancestral Karma", "à¤…à¤­à¤¿à¤­à¤¾à¤µà¤• à¤° à¤ªà¥‚à¤°à¥à¤µà¤œ à¤•à¤°à¥à¤®"),
    DWADASAMSA_ABOUT("About Dwadasamsa Chart", "à¤¦à¥à¤µà¤¾à¤¦à¤¶à¤¾à¤‚à¤¶ à¤šà¤¾à¤°à¥à¤Ÿà¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    DWADASAMSA_ABOUT_DESC("Dwadasamsa reveals parental relationships, ancestral inheritance, and genetic predispositions through 12th harmonic analysis.", "à¤¦à¥à¤µà¤¾à¤¦à¤¶à¤¾à¤‚à¤¶à¤²à¥‡ à¥§à¥¨à¤”à¤‚ à¤¹à¤¾à¤°à¥à¤®à¥‹à¤¨à¤¿à¤• à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤®à¤¾à¤°à¥à¤«à¤¤ à¤…à¤­à¤¿à¤­à¤¾à¤µà¤• à¤¸à¤®à¥à¤¬à¤¨à¥à¤§, à¤ªà¥ˆà¤¤à¥ƒà¤• à¤µà¤¿à¤°à¤¾à¤¸à¤¤, à¤° à¤†à¤¨à¥à¤µà¤‚à¤¶à¤¿à¤• à¤ªà¥à¤°à¤µà¥ƒà¤¤à¥à¤¤à¤¿à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤•à¤Ÿ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    DWADASAMSA_SUN_POSITION("Sun in D-12 (Father)", "D-12 à¤®à¤¾ à¤¸à¥‚à¤°à¥à¤¯ (à¤¬à¥à¤¬à¤¾)"),
    DWADASAMSA_MOON_POSITION("Moon in D-12 (Mother)", "D-12 à¤®à¤¾ à¤šà¤¨à¥à¤¦à¥à¤° (à¤†à¤®à¤¾)"),
    DWADASAMSA_FATHER_ANALYSIS("Father Analysis", "à¤¬à¥à¤¬à¤¾ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    DWADASAMSA_MOTHER_ANALYSIS("Mother Analysis", "à¤†à¤®à¤¾ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    DWADASAMSA_ANCESTRAL_KARMA("Ancestral Karma", "à¤ªà¥ˆà¤¤à¥ƒà¤• à¤•à¤°à¥à¤®"),
    DWADASAMSA_INHERITANCE("Inheritance Potential", "à¤µà¤¿à¤°à¤¾à¤¸à¤¤ à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾"),

    // ============================================
    // ADDITIONAL SCREEN-SPECIFIC STRING KEYS
    // ============================================
    // Ashtottari Screen specific
    ASHTOTTARI_TITLE("Ashtottari Dasha", "à¤…à¤·à¥à¤Ÿà¥‹à¤¤à¥à¤¤à¤°à¥€ à¤¦à¤¶à¤¾"),
    ASHTOTTARI_STARTING_LORD("Starting Lord", "à¤¸à¥à¤°à¥à¤µà¤¾à¤¤ à¤¸à¥à¤µà¤¾à¤®à¥€"),
    ASHTOTTARI_BALANCE("Balance at Birth", "à¤œà¤¨à¥à¤®à¤®à¤¾ à¤¶à¥‡à¤·"),
    ASHTOTTARI_SUBTITLE("108-Year Conditional Dasha System", "à¥§à¥¦à¥® à¤µà¤°à¥à¤·à¤•à¥‹ à¤¸à¤¶à¤°à¥à¤¤ à¤¦à¤¶à¤¾ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€"),
    ASHTOTTARI_ABOUT("About Ashtottari Dasha", "à¤…à¤·à¥à¤Ÿà¥‹à¤¤à¥à¤¤à¤°à¥€ à¤¦à¤¶à¤¾à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    ASHTOTTARI_ABOUT_DESC("Ashtottari Dasha is a conditional planetary period system spanning 108 years. It uses 8 planets (excluding Ketu). The system is particularly effective for daytime births with Moon in Krishna Paksha or nighttime births with Moon in Shukla Paksha.", "à¤…à¤·à¥à¤Ÿà¥‹à¤¤à¥à¤¤à¤°à¥€ à¤¦à¤¶à¤¾ à¥§à¥¦à¥® à¤µà¤°à¥à¤·à¤•à¥‹ à¤¸à¤¶à¤°à¥à¤¤ à¤—à¥à¤°à¤¹ à¤…à¤µà¤§à¤¿ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤¹à¥‹à¥¤ à¤¯à¤¸à¤®à¤¾ à¥® à¤—à¥à¤°à¤¹ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¤¿à¤¨à¥à¤› (à¤•à¥‡à¤¤à¥ à¤¬à¤¾à¤¹à¥‡à¤•)à¥¤ à¤¯à¥‹ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤•à¥ƒà¤·à¥à¤£ à¤ªà¤•à¥à¤·à¤®à¤¾ à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤¸à¤¹à¤¿à¤¤ à¤¦à¤¿à¤¨à¤•à¥‹ à¤œà¤¨à¥à¤® à¤µà¤¾ à¤¶à¥à¤•à¥à¤² à¤ªà¤•à¥à¤·à¤®à¤¾ à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤¸à¤¹à¤¿à¤¤ à¤°à¤¾à¤¤à¤•à¥‹ à¤œà¤¨à¥à¤®à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤ªà¥à¤°à¤­à¤¾à¤µà¤•à¤¾à¤°à¥€ à¤›à¥¤"),
    ASHTOTTARI_APPLICABILITY("Applicability Check", "à¤²à¤¾à¤—à¥‚ à¤¹à¥à¤¨à¥‡ à¤œà¤¾à¤à¤š"),
    ASHTOTTARI_IDEAL_CONDITION("Ideal Condition: Day birth with Krishna Paksha Moon or Night birth with Shukla Paksha Moon", "à¤†à¤¦à¤°à¥à¤¶ à¤…à¤µà¤¸à¥à¤¥à¤¾: à¤•à¥ƒà¤·à¥à¤£ à¤ªà¤•à¥à¤·à¤•à¥‹ à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤¸à¤¹à¤¿à¤¤ à¤¦à¤¿à¤¨à¤•à¥‹ à¤œà¤¨à¥à¤® à¤µà¤¾ à¤¶à¥à¤•à¥à¤² à¤ªà¤•à¥à¤·à¤•à¥‹ à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤¸à¤¹à¤¿à¤¤ à¤°à¤¾à¤¤à¤•à¥‹ à¤œà¤¨à¥à¤®"),
    ASHTOTTARI_TOTAL_YEARS("Total Cycle: 108 Years", "à¤•à¥à¤² à¤šà¤•à¥à¤°: à¥§à¥¦à¥® à¤µà¤°à¥à¤·"),
    ASHTOTTARI_PLANETS_USED("8 Planets Used (No Sun, No Ketu)", "à¥® à¤—à¥à¤°à¤¹ à¤ªà¥à¤°à¤¯à¥‹à¤— (à¤¸à¥‚à¤°à¥à¤¯ à¤° à¤•à¥‡à¤¤à¥ à¤¬à¤¿à¤¨à¤¾)"),
    ASHTOTTARI_CURRENT_PERIOD("Current Period", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤…à¤µà¤§à¤¿"),
    ASHTOTTARI_TIMELINE("Dasha Timeline", "à¤¦à¤¶à¤¾ à¤¸à¤®à¤¯à¤°à¥‡à¤–à¤¾"),
    ASHTOTTARI_SUN_DURATION("Sun: 6 years", "à¤¸à¥‚à¤°à¥à¤¯: à¥¬ à¤µà¤°à¥à¤·"),
    ASHTOTTARI_MOON_DURATION("Moon: 15 years", "à¤šà¤¨à¥à¤¦à¥à¤°: à¥§à¥« à¤µà¤°à¥à¤·"),
    ASHTOTTARI_MARS_DURATION("Mars: 8 years", "à¤®à¤‚à¤—à¤²: à¥® à¤µà¤°à¥à¤·"),
    ASHTOTTARI_MERCURY_DURATION("Mercury: 17 years", "à¤¬à¥à¤§: à¥§à¥­ à¤µà¤°à¥à¤·"),
    ASHTOTTARI_SATURN_DURATION("Saturn: 10 years", "à¤¶à¤¨à¤¿: à¥§à¥¦ à¤µà¤°à¥à¤·"),
    ASHTOTTARI_JUPITER_DURATION("Jupiter: 19 years", "à¤¬à¥ƒà¤¹à¤¸à¥à¤ªà¤¤à¤¿: à¥§à¥¯ à¤µà¤°à¥à¤·"),
    ASHTOTTARI_RAHU_DURATION("Rahu: 12 years", "à¤°à¤¾à¤¹à¥: à¥§à¥¨ à¤µà¤°à¥à¤·"),

    // Chara Dasha
    CHARA_DASHA_TYPE("Type", "à¤ªà¥à¤°à¤•à¤¾à¤°"),

    // Divisional Charts Additional
    DIVISIONAL_HORA_TAB("Hora (D-2)", "à¤¹à¥‹à¤°à¤¾ (D-2)"),
    DIVISIONAL_DREKKANA_TAB("Drekkana (D-3)", "à¤¦à¥à¤°à¥‡à¤·à¥à¤•à¤¾à¤£ (D-3)"),
    DIVISIONAL_NAVAMSA_TAB("Navamsa (D-9)", "à¤¨à¤µà¤¾à¤‚à¤¶ (D-9)"),
    DIVISIONAL_DASHAMSA_TAB("Dashamsa (D-10)", "à¤¦à¤¶à¤¾à¤‚à¤¶ (D-10)"),
    DIVISIONAL_DWADASAMSA_TAB("Dwadasamsa (D-12)", "à¤¦à¥à¤µà¤¾à¤¦à¤¶à¤¾à¤‚à¤¶ (D-12)"),
    
    // Note: Other Divisional keys already existed in DIVISIONAL_CHART_ANALYZER block above
    HORA_SELF_EARNED("Self Earned", "à¤†à¤°à¥à¤œà¤¿à¤¤"),
    HORA_INHERITED("Inherited", "à¤ªà¥ˆà¤¤à¥ƒà¤•"),
    HORA_POTENTIAL("Wealth Potential", "à¤§à¤¨ à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾"),
    HORA_POTENTIAL_EXCEPTIONAL("Exceptional Wealth", "à¤…à¤¸à¤¾à¤§à¤¾à¤°à¤£ à¤§à¤¨"),
    HORA_POTENTIAL_HIGH("High Wealth", "à¤‰à¤šà¥à¤š à¤§à¤¨"),
    HORA_POTENTIAL_MODERATE("Moderate Wealth", "à¤®à¤§à¥à¤¯à¤® à¤§à¤¨"),
    HORA_POTENTIAL_AVERAGE("Average Wealth", "à¤”à¤¸à¤¤ à¤§à¤¨"),
    HORA_POTENTIAL_NEEDS_EFFORT("Needs Effort", "à¤ªà¥à¤°à¤¯à¤¾à¤¸ à¤†à¤µà¤¶à¥à¤¯à¤•"),
    
    COURAGE_EXCEPTIONAL("Exceptional Courage", "à¤…à¤¸à¤¾à¤§à¤¾à¤°à¤£ à¤¸à¤¾à¤¹à¤¸"),
    COURAGE_HIGH("High Courage", "à¤‰à¤šà¥à¤š à¤¸à¤¾à¤¹à¤¸"),
    COURAGE_MODERATE("Moderate Courage", "à¤®à¤§à¥à¤¯à¤® à¤¸à¤¾à¤¹à¤¸"),
    COURAGE_DEVELOPING("Developing Courage", "à¤µà¤¿à¤•à¤¾à¤¸à¤¶à¥€à¤² à¤¸à¤¾à¤¹à¤¸"),
    COURAGE_NEEDS_WORK("Needs Work", "à¤•à¤¾à¤® à¤†à¤µà¤¶à¥à¤¯à¤•"),
    
    DREKKANA_COURAGE_TITLE("Courage Analysis", "à¤¸à¤¾à¤¹à¤¸ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    DREKKANA_SHORT_JOURNEYS("Short Journeys", "à¤›à¥‹à¤Ÿà¥‹ à¤¯à¤¾à¤¤à¥à¤°à¤¾"),
    DREKKANA_YOUNGER("Younger Siblings", "à¤¸à¤¾à¤¨à¤¾ à¤­à¤¾à¤‡à¤¬à¤¹à¤¿à¤¨à¥€"),
    DREKKANA_ELDER("Elder Siblings", "à¤ à¥‚à¤²à¤¾ à¤­à¤¾à¤‡à¤¬à¤¹à¤¿à¤¨à¥€"),
    DREKKANA_RELATIONSHIP("Relationship", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§"),
    DREKKANA_COMMUNICATION_TITLE("Communication", "à¤¸à¤žà¥à¤šà¤¾à¤°"),
    DREKKANA_OVERALL("Overall", "à¤¸à¤®à¤—à¥à¤°"),
    DREKKANA_WRITING("Writing", "à¤²à¥‡à¤–à¤¨"),
    DREKKANA_SPEAKING("Speaking", "à¤¬à¥‹à¤²à¥à¤¨à¥‡"),
    DREKKANA_ARTISTIC_TALENTS("Artistic Talents", "à¤•à¤²à¤¾à¤¤à¥à¤®à¤• à¤ªà¥à¤°à¤¤à¤¿à¤­à¤¾"),
    
    NAVAMSA_SPOUSE_TITLE("Spouse Characteristics", "à¤œà¥€à¤µà¤¨à¤¸à¤¾à¤¥à¥€ à¤µà¤¿à¤¶à¥‡à¤·à¤¤à¤¾à¤¹à¤°à¥‚"),
    NAVAMSA_TIMING_TITLE("Marriage Timing", "à¤µà¤¿à¤µà¤¾à¤¹ à¤¸à¤®à¤¯"),
    NAVAMSA_KEY_PLANETS_TITLE("Key Planets", "à¤®à¥à¤–à¥à¤¯ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    NAVAMSA_NATURE("General Nature", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤ªà¥à¤°à¤•à¥ƒà¤¤à¤¿"),
    NAVAMSA_PHYSICAL_TRAITS("Physical Traits", "à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤²à¤•à¥à¤·à¤£"),
    NAVAMSA_FAMILY_BACKGROUND("Family Background", "à¤ªà¤¾à¤°à¤¿à¤µà¤¾à¤°à¤¿à¤• à¤ªà¥ƒà¤·à¥à¤ à¤­à¥‚à¤®à¤¿"),
    NAVAMSA_DIRECTION("Direction", "à¤¦à¤¿à¤¶à¤¾"),
    NAVAMSA_PROBABLE_PROFESSIONS("Probable Professions", "à¤¸à¤®à¥à¤­à¤¾à¤µà¤¿à¤¤ à¤ªà¥‡à¤¶à¤¾à¤¹à¤°à¥‚"),
    NAVAMSA_VENUS("Venus", "à¤¶à¥à¤•à¥à¤°"),
    NAVAMSA_JUPITER("Jupiter", "à¤¬à¥ƒà¤¹à¤¸à¥à¤ªà¤¤à¤¿"),
    NAVAMSA_7TH_LORD_LABEL("7th Lord", "à¥­à¤® à¤¸à¥à¤µà¤¾à¤®à¥€"),
    NAVAMSA_DARAKARAKA("Darakaraka", "à¤¦à¤¾à¤°à¤¾à¤•à¤¾à¤°à¤•"),
    NAVAMSA_FAVORABLE_DASHA("Favorable Dasha", "à¤¶à¥à¤­ à¤¦à¤¶à¤¾"),
    NAVAMSA_UPAPADA("Upapada Lagna", "à¤‰à¤ªà¤ªà¤¦ à¤²à¤—à¥à¤¨"),
    NAVAMSA_RELATIONSHIP_STABILITY("Relationship Stability", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤¸à¥à¤¥à¤¿à¤°à¤¤à¤¾"),
    NAVAMSA_AREAS_ATTENTION("Areas for Attention", "à¤§à¥à¤¯à¤¾à¤¨ à¤¦à¤¿à¤¨à¥à¤ªà¤°à¥à¤¨à¥‡ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚"),
    NAVAMSA_PROTECTIVE_FACTORS("Protective Factors", "à¤°à¤•à¥à¤·à¤¾à¤¤à¥à¤®à¤• à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚"),
    
    DASHAMSA_BUSINESS_VS_SERVICE("Business vs Service", "à¤µà¥à¤¯à¤¾à¤ªà¤¾à¤° à¤¬à¤¨à¤¾à¤® à¤¸à¥‡à¤µà¤¾"),
    DASHAMSA_BUSINESS("Business Aptitude", "à¤µà¥à¤¯à¤¾à¤ªà¤¾à¤° à¤¯à¥‹à¤—à¥à¤¯à¤¤à¤¾"),
    DASHAMSA_SERVICE("Service Aptitude", "à¤¸à¥‡à¤µà¤¾ à¤¯à¥‹à¤—à¥à¤¯à¤¤à¤¾"),
    DASHAMSA_GOVT_SERVICE_TITLE("Government Service", "à¤¸à¤°à¤•à¤¾à¤°à¥€ à¤¸à¥‡à¤µà¤¾"),
    DASHAMSA_POTENTIAL("Potential", "à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾"),
    DASHAMSA_RECOMMENDED_AREAS("Recommended Areas", "à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸ à¤—à¤°à¤¿à¤à¤•à¤¾ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚"),
    DASHAMSA_PROFESSIONAL_STRENGTHS("Professional Strengths", "à¤µà¥à¤¯à¤¾à¤µà¤¸à¤¾à¤¯à¤¿à¤• à¤¶à¤•à¥à¤¤à¤¿à¤¹à¤°à¥‚"),
    
    DWADASAMSA_FATHER("Father", "à¤¬à¥à¤¬à¤¾"),
    DWADASAMSA_MOTHER("Mother", "à¤†à¤®à¤¾"),
    DWADASAMSA_INHERITANCE_TITLE("Inheritance Analysis", "à¤µà¤¿à¤°à¤¾à¤¸à¤¤ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    DWADASAMSA_ANCESTRAL_PROPERTY("Ancestral Property", "à¤ªà¥ˆà¤¤à¥ƒà¤• à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿"),
    DWADASAMSA_LONGEVITY_TITLE("Parental Longevity", "à¤…à¤­à¤¿à¤­à¤¾à¤µà¤• à¤¦à¥€à¤°à¥à¤˜à¤¾à¤¯à¥"),
    DWADASAMSA_POTENTIAL("Potential", "à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾"),
    DWADASAMSA_TIMING("Timing", "à¤¸à¤®à¤¯"),
    DWADASAMSA_SOURCES("Sources", "à¤¸à¥à¤°à¥‹à¤¤à¤¹à¤°à¥‚"),
    DWADASAMSA_SIGNIFICATOR("Significator", "à¤•à¤¾à¤°à¤•"),
    DWADASAMSA_HOUSE_LORD("House Lord", "à¤­à¤¾à¤µ à¤¸à¥à¤µà¤¾à¤®à¥€"),
    DWADASAMSA_CHARACTERISTICS("Characteristics", "à¤µà¤¿à¤¶à¥‡à¤·à¤¤à¤¾à¤¹à¤°à¥‚"),
    DWADASAMSA_RELATIONSHIP("Relationship", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§"),
    ASHTOTTARI_VENUS_DURATION("Venus: 21 years", "à¤¶à¥à¤•à¥à¤°: à¥¨à¥§ à¤µà¤°à¥à¤·"),

    // Sudarshana Screen specific
    SUDARSHANA_TITLE("Sudarshana Chakra Dasha", "à¤¸à¥à¤¦à¤°à¥à¤¶à¤¨ à¤šà¤•à¥à¤° à¤¦à¤¶à¤¾"),
    SUDARSHANA_SUBTITLE("Triple-Reference Timing System", "à¤¤à¥à¤°à¤¿-à¤¸à¤¨à¥à¤¦à¤°à¥à¤­ à¤¸à¤®à¤¯ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€"),
    SUDARSHANA_ABOUT("About Sudarshana Chakra", "à¤¸à¥à¤¦à¤°à¥à¤¶à¤¨ à¤šà¤•à¥à¤°à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    SUDARSHANA_ABOUT_DESC("Sudarshana Chakra Dasha simultaneously considers the Lagna, Chandra, and Surya charts. Each year progresses through signs from these three reference points, giving a holistic view of life events.", "à¤¸à¥à¤¦à¤°à¥à¤¶à¤¨ à¤šà¤•à¥à¤° à¤¦à¤¶à¤¾à¤²à¥‡ à¤²à¤—à¥à¤¨, à¤šà¤¨à¥à¤¦à¥à¤° à¤° à¤¸à¥‚à¤°à¥à¤¯ à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤à¤•à¥ˆà¤¸à¤¾à¤¥ à¤µà¤¿à¤šà¤¾à¤° à¤—à¤°à¥à¤¦à¤›à¥¤ à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤µà¤°à¥à¤· à¤¯à¥€ à¤¤à¥€à¤¨ à¤¸à¤¨à¥à¤¦à¤°à¥à¤­ à¤¬à¤¿à¤¨à¥à¤¦à¥à¤¹à¤°à¥‚à¤¬à¤¾à¤Ÿ à¤°à¤¾à¤¶à¤¿à¤¹à¤°à¥‚ à¤®à¤¾à¤°à¥à¤«à¤¤ à¤…à¤—à¤¾à¤¡à¤¿ à¤¬à¤¢à¥à¤›à¥¤"),
    SUDARSHANA_LAGNA_CHAKRA("Lagna Chakra", "à¤²à¤—à¥à¤¨ à¤šà¤•à¥à¤°"),
    SUDARSHANA_CHANDRA_CHAKRA("Chandra Chakra", "à¤šà¤¨à¥à¤¦à¥à¤° à¤šà¤•à¥à¤°"),
    SUDARSHANA_SURYA_CHAKRA("Surya Chakra", "à¤¸à¥‚à¤°à¥à¤¯ à¤šà¤•à¥à¤°"),
    SUDARSHANA_AGE("Age", "à¤‰à¤®à¥‡à¤°"),
    SUDARSHANA_COMBINED_ANALYSIS("Combined Period Analysis", "à¤¸à¤‚à¤¯à¥à¤•à¥à¤¤ à¤…à¤µà¤§à¤¿ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SUDARSHANA_CONVERGENCE("Convergence Analysis", "à¤…à¤­à¤¿à¤¸à¤°à¤£ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SUDARSHANA_TRIPLE_VIEW("Triple View", "à¤¤à¥à¤°à¤¿à¤—à¥à¤£ à¤¦à¥ƒà¤¶à¥à¤¯"),
    SUDARSHANA_YEAR_ANALYSIS("Year Analysis", "à¤µà¤¾à¤°à¥à¤·à¤¿à¤• à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SUDARSHANA_LAGNA_INFLUENCE("Lagna Influence", "à¤²à¤—à¥à¤¨ à¤ªà¥à¤°à¤­à¤¾à¤µ"),
    SUDARSHANA_CHANDRA_INFLUENCE("Chandra Influence", "à¤šà¤¨à¥à¤¦à¥à¤° à¤ªà¥à¤°à¤­à¤¾à¤µ"),
    SUDARSHANA_SURYA_INFLUENCE("Surya Influence", "à¤¸à¥‚à¤°à¥à¤¯ à¤ªà¥à¤°à¤­à¤¾à¤µ"),
    SUDARSHANA_CURRENT_SIGNS("Current Signs", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤°à¤¾à¤¶à¤¿à¤¹à¤°à¥‚"),
    SUDARSHANA_HOUSE_SIGNIFICATIONS("House Significations", "à¤­à¤¾à¤µ à¤…à¤°à¥à¤¥à¤¹à¤°à¥‚"),
    SUDARSHANA_PLANETS_IN_SIGN("Planets in Sign", "à¤°à¤¾à¤¶à¤¿à¤®à¤¾ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    SUDARSHANA_ASPECTS_RECEIVED("Aspects Received", "à¤ªà¥à¤°à¤¾à¤ªà¥à¤¤ à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚"),
    SUDARSHANA_STRONG_CONVERGENCE("Strong Convergence", "à¤¬à¤²à¤¿à¤¯à¥‹ à¤…à¤­à¤¿à¤¸à¤°à¤£"),
    SUDARSHANA_WEAK_CONVERGENCE("Weak Convergence", "à¤•à¤®à¤œà¥‹à¤° à¤…à¤­à¤¿à¤¸à¤°à¤£"),

    // Mrityu Bhaga Screen specific
    MRITYU_BHAGA_SCREEN_TITLE("Mrityu Bhaga Analysis", "à¤®à¥ƒà¤¤à¥à¤¯à¥ à¤­à¤¾à¤— à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    MRITYU_BHAGA_SCREEN_SUBTITLE("Sensitive Degrees in Chart", "à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾ à¤¸à¤‚à¤µà¥‡à¤¦à¤¨à¤¶à¥€à¤² à¤…à¤‚à¤¶à¤¹à¤°à¥‚"),
    MRITYU_BHAGA_SCREEN_ABOUT("About Mrityu Bhaga", "à¤®à¥ƒà¤¤à¥à¤¯à¥ à¤­à¤¾à¤—à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    MRITYU_BHAGA_SCREEN_ABOUT_DESC("Mrityu Bhaga refers to specific sensitive degrees in each zodiac sign. When planets occupy these degrees, they indicate areas of life requiring extra attention. These are sensitive points that may indicate health vulnerabilities or significant life challenges.", "à¤®à¥ƒà¤¤à¥à¤¯à¥ à¤­à¤¾à¤—à¤²à¥‡ à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤°à¤¾à¤¶à¤¿à¤®à¤¾ à¤µà¤¿à¤¶à¥‡à¤· à¤¸à¤‚à¤µà¥‡à¤¦à¤¨à¤¶à¥€à¤² à¤…à¤‚à¤¶à¤¹à¤°à¥‚à¤²à¤¾à¤ˆ à¤œà¤¨à¤¾à¤‰à¤à¤›à¥¤ à¤œà¤¬ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚ à¤¯à¥€ à¤…à¤‚à¤¶à¤¹à¤°à¥‚à¤®à¤¾ à¤¹à¥à¤¨à¥à¤›à¤¨à¥, à¤¤à¤¿à¤¨à¥€à¤¹à¤°à¥‚à¤²à¥‡ à¤¥à¤ª à¤§à¥à¤¯à¤¾à¤¨ à¤šà¤¾à¤¹à¤¿à¤¨à¥‡ à¤œà¥€à¤µà¤¨à¤•à¤¾ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚ à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤¦à¤›à¤¨à¥à¥¤"),
    MRITYU_BHAGA_PLANETS_AFFECTED("Planets in Mrityu Bhaga", "à¤®à¥ƒà¤¤à¥à¤¯à¥ à¤­à¤¾à¤—à¤®à¤¾ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    MRITYU_BHAGA_NO_PLANETS("No planets in Mrityu Bhaga", "à¤®à¥ƒà¤¤à¥à¤¯à¥ à¤­à¤¾à¤—à¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤—à¥à¤°à¤¹ à¤›à¥ˆà¤¨"),
    MRITYU_BHAGA_SENSITIVE_DEGREE("Sensitive Degree", "à¤¸à¤‚à¤µà¥‡à¤¦à¤¨à¤¶à¥€à¤² à¤…à¤‚à¤¶"),
    MRITYU_BHAGA_ORB("Orb", "à¤•à¥‹à¤£à¤¾à¤¨à¥à¤¤à¤°"),
    MRITYU_BHAGA_SEVERITY("Severity Level", "à¤—à¤®à¥à¤­à¥€à¤°à¤¤à¤¾ à¤¸à¥à¤¤à¤°"),
    MRITYU_BHAGA_LIFE_AREAS("Affected Life Areas", "à¤ªà¥à¤°à¤­à¤¾à¤µà¤¿à¤¤ à¤œà¥€à¤µà¤¨ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚"),
    MRITYU_BHAGA_REMEDIES_SECTION("Suggested Remedies", "à¤¸à¥à¤à¤¾à¤µ à¤—à¤°à¤¿à¤à¤•à¤¾ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    MRITYU_BHAGA_TRANSIT_WARNING("Transit Alerts", "à¤—à¥‹à¤šà¤° à¤šà¥‡à¤¤à¤¾à¤µà¤¨à¥€à¤¹à¤°à¥‚"),
    MRITYU_BHAGA_SIGN_DEGREES("Mrityu Bhaga Degrees by Sign", "à¤°à¤¾à¤¶à¤¿ à¤…à¤¨à¥à¤¸à¤¾à¤° à¤®à¥ƒà¤¤à¥à¤¯à¥ à¤­à¤¾à¤— à¤…à¤‚à¤¶à¤¹à¤°à¥‚"),
    MRITYU_BHAGA_ALL_SIGNS("All Signs Reference", "à¤¸à¤¬à¥ˆ à¤°à¤¾à¤¶à¤¿ à¤¸à¤¨à¥à¤¦à¤°à¥à¤­"),
    MRITYU_BHAGA_PRECAUTIONS("Precautionary Measures", "à¤¸à¤¾à¤µà¤§à¤¾à¤¨à¥€à¤•à¤¾ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),

    // Mrityu Bhaga Additional
    MRITYU_BHAGA_GANDANTA_PLACEMENTS("Gandanta Placements", "à¤—à¤£à¥à¤¡à¤¾à¤¨à¥à¤¤ à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤¹à¤°à¥‚"),
    MRITYU_BHAGA_AUSPICIOUS_PLACEMENTS("Auspicious Placements", "à¤¶à¥à¤­ à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤¹à¤°à¥‚"),
    MRITYU_BHAGA_NO_CRITICAL("Your chart is free from critical Mrityu Bhaga placements.", "à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤—à¤®à¥à¤­à¥€à¤° à¤®à¥ƒà¤¤à¥à¤¯à¥ à¤­à¤¾à¤— à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤¹à¤°à¥‚à¤¬à¤¾à¤Ÿ à¤®à¥à¤•à¥à¤¤ à¤›à¥¤"),
    
    // Assessment Levels
    MRITYU_BHAGA_LEVEL_NEEDS_ATTENTION("Needs Attention", "à¤§à¥à¤¯à¤¾à¤¨ à¤¦à¤¿à¤¨ à¤†à¤µà¤¶à¥à¤¯à¤•"),
    MRITYU_BHAGA_LEVEL_MODERATE_CONCERN("Moderate Concern", "à¤®à¤§à¥à¤¯à¤® à¤šà¤¿à¤¨à¥à¤¤à¤¾"),
    MRITYU_BHAGA_LEVEL_BALANCED("Balanced", "à¤¸à¤¨à¥à¤¤à¥à¤²à¤¿à¤¤"),
    MRITYU_BHAGA_LEVEL_GENERALLY_POSITIVE("Generally Positive", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¯à¤¾ à¤¸à¤•à¤¾à¤°à¤¾à¤¤à¥à¤®à¤•"),
    MRITYU_BHAGA_LEVEL_HIGHLY_AUSPICIOUS("Highly Auspicious", "à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤¶à¥à¤­"),

    MRITYU_BHAGA_CRITICAL_COUNT("Critical", "à¤—à¤®à¥à¤­à¥€à¤°"),
    MRITYU_BHAGA_AUSPICIOUS_COUNT("Auspicious", "à¤¶à¥à¤­"),
    
    // Gandanta Types
    MRITYU_BHAGA_GANDANTA_BRAHMA("Brahma Gandanta", "à¤¬à¥à¤°à¤¹à¥à¤® à¤—à¤£à¥à¤¡à¤¾à¤¨à¥à¤¤"),
    MRITYU_BHAGA_GANDANTA_VISHNU("Vishnu Gandanta", "à¤µà¤¿à¤·à¥à¤£à¥ à¤—à¤£à¥à¤¡à¤¾à¤¨à¥à¤¤"),
    MRITYU_BHAGA_GANDANTA_SHIVA("Shiva Gandanta", "à¤¶à¤¿à¤µ à¤—à¤£à¥à¤¡à¤¾à¤¨à¥à¤¤"),
    
    MRITYU_BHAGA_JUNCTION_DESC("Junction between %1$s and %2$s", "%1$s à¤° %2$s à¤¬à¥€à¤šà¤•à¥‹ à¤¸à¤¨à¥à¤§à¤¿"),
    MRITYU_BHAGA_DISTANCE_JUNCTION("Distance from Junction: %1$s", "à¤¸à¤¨à¥à¤§à¤¿à¤¬à¤¾à¤Ÿ à¤¦à¥‚à¤°à¥€: %1$s"),
    
    // Pushkara
    MRITYU_BHAGA_PUSHKARA_NAVAMSA_TITLE("Pushkara Navamsa", "à¤ªà¥à¤·à¥à¤•à¤° à¤¨à¤µà¤®à¤¾à¤‚à¤¶"),
    MRITYU_BHAGA_PUSHKARA_BHAGA_TITLE("Pushkara Bhaga", "à¤ªà¥à¤·à¥à¤•à¤° à¤­à¤¾à¤—"),
    MRITYU_BHAGA_BENEFITS("Benefits", "à¤²à¤¾à¤­à¤¹à¤°à¥‚"),
    
    MRITYU_BHAGA_OVERALL_ASSESSMENT("Overall Assessment", "à¤¸à¤®à¤—à¥à¤° à¤®à¥‚à¤²à¥à¤¯à¤¾à¤‚à¤•à¤¨"),

    // Mrityu Bhaga Severity
    MB_SEV_EXACT("Exact", "à¤ªà¥‚à¤°à¥à¤£"),
    MB_SEV_VERY_CLOSE("Very Close", "à¤§à¥‡à¤°à¥ˆ à¤¨à¤œà¤¿à¤•"),
    MB_SEV_WITHIN_ORB("Within Orb", "à¤ªà¥à¤°à¤­à¤¾à¤µ à¤•à¥à¤·à¥‡à¤¤à¥à¤° à¤­à¤¿à¤¤à¥à¤°"),
    MB_SEV_APPROACHING("Approaching", "à¤†à¤‰à¤à¤¦à¥ˆ à¤—à¤°à¥‡à¤•à¥‹"),
    MB_SEV_SAFE("Safe", "à¤¸à¥à¤°à¤•à¥à¤·à¤¿à¤¤"),

    // Gandanta Severity
    GANDANTA_SEV_EXACT("Exact Junction", "à¤ªà¥‚à¤°à¥à¤£ à¤¸à¤¨à¥à¤§à¤¿"),
    GANDANTA_SEV_CRITICAL("Critical", "à¤—à¤®à¥à¤­à¥€à¤°"),
    GANDANTA_SEV_SEVERE("Severe", "à¤•à¤¡à¤¾"),
    GANDANTA_SEV_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    GANDANTA_SEV_MILD("Mild", "à¤¹à¤²à¥à¤•à¤¾"),

    // Lal Kitab Screen specific
    LAL_KITAB_SCREEN_TITLE("Lal Kitab Remedies", "à¤²à¤¾à¤² à¤•à¤¿à¤¤à¤¾à¤¬ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    
    // Additional Lal Kitab Keys
    LAL_KITAB_INDICATORS("Indicators", "à¤¸à¤‚à¤•à¥‡à¤¤à¤¹à¤°à¥‚"),
    LAL_KITAB_REMEDIES_LABEL("Remedies", "à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    LAL_KITAB_TYPES_TITLE("Types of Karmic Debts", "à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤‹à¤£à¤•à¤¾ à¤ªà¥à¤°à¤•à¤¾à¤°à¤¹à¤°à¥‚"),
    LAL_KITAB_DESC_PITRU("Debt towards father and ancestors", "à¤ªà¤¿à¤¤à¤¾ à¤° à¤ªà¥‚à¤°à¥à¤µà¤œà¤¹à¤°à¥‚à¤ªà¥à¤°à¤¤à¤¿ à¤‹à¤£"),
    LAL_KITAB_DESC_MATRU("Debt towards mother and maternal lineage", "à¤†à¤®à¤¾ à¤° à¤®à¤¾à¤¤à¥ƒà¤ªà¤•à¥à¤·à¤ªà¥à¤°à¤¤à¤¿ à¤‹à¤£"),
    LAL_KITAB_DESC_STRI("Debt towards wife/women", "à¤ªà¤¤à¥à¤¨à¥€/à¤¸à¥à¤¤à¥à¤°à¥€à¤¹à¤°à¥‚à¤ªà¥à¤°à¤¤à¤¿ à¤‹à¤£"),
    LAL_KITAB_DESC_SELF("Debt towards self and karma", "à¤¸à¥à¤µà¤¯à¤‚ à¤° à¤•à¤°à¥à¤®à¤ªà¥à¤°à¤¤à¤¿ à¤‹à¤£"),
    LAL_KITAB_DAILY_REMEDIES_DESC("Daily remedies for each planetary day as per Lal Kitab tradition", "à¤²à¤¾à¤² à¤•à¤¿à¤¤à¤¾à¤¬ à¤ªà¤°à¤®à¥à¤ªà¤°à¤¾ à¤…à¤¨à¥à¤¸à¤¾à¤° à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤—à¥à¤°à¤¹ à¤¬à¤¾à¤°à¤•à¤¾ à¤¦à¥ˆà¤¨à¤¿à¤• à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    LAL_KITAB_FAVORABLE("Favorable", "à¤…à¤¨à¥à¤•à¥‚à¤²"),
    LAL_KITAB_AVOID("Avoid", "à¤¤à¥à¤¯à¤¾à¤— à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    LAL_KITAB_DEBT_KANYA("Girl Child Debt", "à¤•à¤¨à¥à¤¯à¤¾ à¤‹à¤£"),
    LAL_KITAB_SCREEN_SUBTITLE("Practical Karmic Remedies", "à¤µà¥à¤¯à¤¾à¤µà¤¹à¤¾à¤°à¤¿à¤• à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    LAL_KITAB_SCREEN_ABOUT("About Lal Kitab", "à¤²à¤¾à¤² à¤•à¤¿à¤¤à¤¾à¤¬à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    LAL_KITAB_SCREEN_ABOUT_DESC("Lal Kitab offers simple, practical remedies using everyday items like turmeric, milk, honey, and specific actions. These remedies work on karmic debts and planetary afflictions without expensive rituals.", "à¤²à¤¾à¤² à¤•à¤¿à¤¤à¤¾à¤¬à¤²à¥‡ à¤¬à¥‡à¤¸à¤¾à¤°, à¤¦à¥‚à¤§, à¤®à¤¹ à¤œà¤¸à¥à¤¤à¤¾ à¤¦à¥ˆà¤¨à¤¿à¤• à¤µà¤¸à¥à¤¤à¥à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¥€ à¤¸à¤°à¤², à¤µà¥à¤¯à¤¾à¤µà¤¹à¤¾à¤°à¤¿à¤• à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¦à¤›à¥¤ à¤¯à¥€ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚à¤²à¥‡ à¤®à¤¹à¤à¤—à¥‹ à¤µà¤¿à¤§à¤¿ à¤¬à¤¿à¤¨à¤¾ à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤‹à¤£ à¤° à¤—à¥à¤°à¤¹ à¤ªà¥€à¤¡à¤¾à¤®à¤¾ à¤•à¤¾à¤® à¤—à¤°à¥à¤¦à¤›à¤¨à¥à¥¤"),

    // Kalachakra Dasha Keys
    KALACHAKRA_GROUP_SAVYA("Savya (Direct)", "à¤¸à¤µà¥à¤¯ (à¤¸à¥€à¤§à¥‹)"),
    KALACHAKRA_GROUP_APSAVYA("Apsavya (Retrograde)", "à¤…à¤ªà¤¸à¤µà¥à¤¯ (à¤‰à¤²à¥à¤Ÿà¥‹)"),
    KALACHAKRA_DESC_SAVYA("Clockwise progression through signs - generally smoother life flow", "à¤°à¤¾à¤¶à¤¿à¤¹à¤°à¥‚à¤•à¥‹ à¤˜à¤¡à¥€à¤•à¥‹ à¤¦à¤¿à¤¶à¤¾à¤®à¤¾ à¤ªà¥à¤°à¤—à¤¤à¤¿ - à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¯à¤¾ à¤¸à¤¹à¤œ à¤œà¥€à¤µà¤¨ à¤ªà¥à¤°à¤µà¤¾à¤¹"),
    KALACHAKRA_DESC_APSAVYA("Anti-clockwise progression - more karmic intensity and transformation", "à¤°à¤¾à¤¶à¤¿à¤¹à¤°à¥‚à¤•à¥‹ à¤˜à¤¡à¥€à¤•à¥‹ à¤µà¤¿à¤ªà¤°à¥€à¤¤ à¤¦à¤¿à¤¶à¤¾à¤®à¤¾ à¤ªà¥à¤°à¤—à¤¤à¤¿ - à¤…à¤§à¤¿à¤• à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤¤à¥€à¤µà¥à¤°à¤¤à¤¾ à¤° à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨"),
    
    KALACHAKRA_HEALTH_EXCELLENT("Excellent", "à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ"),
    KALACHAKRA_HEALTH_GOOD("Good", "à¤°à¤¾à¤®à¥à¤°à¥‹"),
    KALACHAKRA_HEALTH_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    KALACHAKRA_HEALTH_CHALLENGING("Challenging", "à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£"),
    KALACHAKRA_HEALTH_CRITICAL("Critical", "à¤—à¤®à¥à¤­à¥€à¤°"),
    
    KALACHAKRA_HEALTH_DESC_EXCELLENT("Very favorable for health and vitality", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤° à¤œà¥‹à¤¸à¤•à¤¾ à¤²à¤¾à¤—à¤¿ à¤§à¥‡à¤°à¥ˆ à¤…à¤¨à¥à¤•à¥‚à¤²"),
    KALACHAKRA_HEALTH_DESC_GOOD("Generally supportive of health", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¯à¤¾ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¸à¤¹à¤¯à¥‹à¤—à¥€"),
    KALACHAKRA_HEALTH_DESC_MODERATE("Mixed health indications", "à¤®à¤¿à¤¶à¥à¤°à¤¿à¤¤ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤¸à¤‚à¤•à¥‡à¤¤à¤¹à¤°à¥‚"),
    KALACHAKRA_HEALTH_DESC_CHALLENGING("Need to take care of health", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯à¤•à¥‹ à¤–à¥à¤¯à¤¾à¤² à¤°à¤¾à¤–à¥à¤¨à¥ à¤†à¤µà¤¶à¥à¤¯à¤•"),
    KALACHAKRA_HEALTH_DESC_CRITICAL("Extra caution needed - follow remedies", "à¤…à¤¤à¤¿à¤°à¤¿à¤•à¥à¤¤ à¤¸à¤¾à¤µà¤§à¤¾à¤¨à¥€ à¤†à¤µà¤¶à¥à¤¯à¤• - à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤…à¤ªà¤¨à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),

    KALACHAKRA_REL_HARMONIOUS("Harmonious", "à¤¸à¤¾à¤®à¤žà¥à¤œà¤¸à¥à¤¯à¤ªà¥‚à¤°à¥à¤£"),
    KALACHAKRA_REL_SUPPORTIVE("Supportive", "à¤¸à¤¹à¤¯à¥‹à¤—à¥€"),
    KALACHAKRA_REL_NEUTRAL("Neutral", "à¤¤à¤Ÿà¤¸à¥à¤¥"),
    KALACHAKRA_REL_CHALLENGING("Challenging", "à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£"),
    KALACHAKRA_REL_TRANSFORMATIVE("Transformative", "à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨à¤•à¤¾à¤°à¥€"),

    KALACHAKRA_REL_DESC_HARMONIOUS("Body and soul are aligned - good health and spiritual progress", "à¤¶à¤°à¤¿à¤° à¤° à¤†à¤¤à¥à¤®à¤¾ à¤à¤•à¤°à¥à¤ª à¤›à¤¨à¥ - à¤°à¤¾à¤®à¥à¤°à¥‹ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤ªà¥à¤°à¤—à¤¤à¤¿"),
    KALACHAKRA_REL_DESC_SUPPORTIVE("Jeeva supports Deha - spiritual practices benefit health", "à¤œà¥€à¤µà¤²à¥‡ à¤¦à¥‡à¤¹à¤²à¤¾à¤ˆ à¤¸à¤¹à¤¯à¥‹à¤— à¤—à¤°à¥à¤› - à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤…à¤­à¥à¤¯à¤¾à¤¸à¤²à¥‡ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯à¤²à¤¾à¤ˆ à¤«à¤¾à¤‡à¤¦à¤¾ à¤ªà¥à¤°à¥â€à¤¯à¤¾à¤‰à¤à¤›"),
    KALACHAKRA_REL_DESC_NEUTRAL("Independent functioning of body and spirit", "à¤¶à¤°à¤¿à¤° à¤° à¤†à¤¤à¥à¤®à¤¾à¤•à¥‹ à¤¸à¥à¤µà¤¤à¤¨à¥à¤¤à¥à¤° à¤•à¤¾à¤°à¥à¤¯"),
    KALACHAKRA_REL_DESC_CHALLENGING("Some friction between material and spiritual needs", "à¤­à¥Œà¤¤à¤¿à¤• à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤†à¤µà¤¶à¥à¤¯à¤•à¤¤à¤¾à¤¹à¤°à¥‚ à¤¬à¥€à¤š à¤•à¥‡à¤¹à¥€ à¤˜à¤°à¥à¤·à¤£"),
    KALACHAKRA_REL_DESC_TRANSFORMATIVE("Deep karmic work needed to align body and soul", "à¤¶à¤°à¤¿à¤° à¤° à¤†à¤¤à¥à¤®à¤¾ à¤®à¤¿à¤²à¤¾à¤‰à¤¨ à¤—à¤¹à¤¿à¤°à¥‹ à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤•à¤¾à¤°à¥à¤¯ à¤†à¤µà¤¶à¥à¤¯à¤•"),

    KALACHAKRA_OVERVIEW_TEXT("Kalachakra Dasha is a sophisticated timing system particularly useful for health predictions and spiritual transformation timing. It operates on the principle that body (Deha) and soul (Jeeva) follow different but related cycles.", "à¤•à¤¾à¤²à¤šà¤•à¥à¤° à¤¦à¤¶à¤¾ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤­à¤µà¤¿à¤·à¥à¤¯à¤µà¤¾à¤£à¥€ à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£ à¤¸à¤®à¤¯à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤µà¤¿à¤¶à¥‡à¤· à¤—à¤°à¥€ à¤‰à¤ªà¤¯à¥‹à¤—à¥€ à¤à¤• à¤ªà¤°à¤¿à¤·à¥à¤•à¥ƒà¤¤ à¤¸à¤®à¤¯ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤¹à¥‹à¥¤ à¤¯à¥‹ à¤¶à¤°à¥€à¤° (à¤¦à¥‡à¤¹) à¤° à¤†à¤¤à¥à¤®à¤¾ (à¤œà¥€à¤µ) à¤²à¥‡ à¤«à¤°à¤• à¤¤à¤° à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¿à¤¤ à¤šà¤•à¥à¤°à¤¹à¤°à¥‚ à¤ªà¤›à¥à¤¯à¤¾à¤‰à¤à¤›à¤¨à¥ à¤­à¤¨à¥à¤¨à¥‡ à¤¸à¤¿à¤¦à¥à¤§à¤¾à¤¨à¥à¤¤à¤®à¤¾ à¤•à¤¾à¤® à¤—à¤°à¥à¤¦à¤›à¥¤"),
    KALACHAKRA_GUIDANCE_1("Monitor transits over Deha Rashi for physical health events", "à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤˜à¤Ÿà¤¨à¤¾à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¦à¥‡à¤¹ à¤°à¤¾à¤¶à¤¿ à¤®à¤¾à¤¥à¤¿à¤•à¥‹ à¤—à¥‹à¤šà¤° à¤¨à¤¿à¤—à¤°à¤¾à¤¨à¥€ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    KALACHAKRA_GUIDANCE_2("Observe transits over Jeeva Rashi for spiritual opportunities", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤…à¤µà¤¸à¤°à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤œà¥€à¤µ à¤°à¤¾à¤¶à¤¿ à¤®à¤¾à¤¥à¤¿à¤•à¥‹ à¤—à¥‹à¤šà¤° à¤…à¤µà¤²à¥‹à¤•à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    KALACHAKRA_GUIDANCE_3("When Deha and Jeeva sign lords are strong in transit, both health and spiritual progress are favored", "à¤œà¤¬ à¤¦à¥‡à¤¹ à¤° à¤œà¥€à¤µ à¤°à¤¾à¤¶à¤¿à¤•à¤¾ à¤¸à¥à¤µà¤¾à¤®à¥€à¤¹à¤°à¥‚ à¤—à¥‹à¤šà¤°à¤®à¤¾ à¤¬à¤²à¤¿à¤¯à¥‹ à¤¹à¥à¤¨à¥à¤›à¤¨à¥, à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤ªà¥à¤°à¤—à¤¤à¤¿ à¤¦à¥à¤¬à¥ˆ à¤…à¤¨à¥à¤•à¥‚à¤² à¤¹à¥à¤¨à¥à¤›à¤¨à¥"),
    KALACHAKRA_GUIDANCE_4("Use Kalachakra Dasha alongside Vimsottari for comprehensive analysis", "à¤µà¤¿à¤¸à¥à¤¤à¥ƒà¤¤ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤µà¤¿à¤‚à¤¶à¥‹à¤¤à¥à¤¤à¤°à¥€ à¤¸à¤à¤—à¥ˆ à¤•à¤¾à¤²à¤šà¤•à¥à¤° à¤¦à¤¶à¤¾ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    KALACHAKRA_GUIDANCE_5("Pay special attention when malefics transit your Deha Rashi", "à¤œà¤¬ à¤ªà¤¾à¤ªà¥€ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤²à¥‡ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤¦à¥‡à¤¹ à¤°à¤¾à¤¶à¤¿à¤®à¤¾ à¤—à¥‹à¤šà¤° à¤—à¤°à¥à¤›à¤¨à¥ à¤µà¤¿à¤¶à¥‡à¤· à¤§à¥à¤¯à¤¾à¤¨ à¤¦à¤¿à¤¨à¥à¤¹à¥‹à¤¸à¥"),

    // Kalachakra Sign Effects
    KALACHAKRA_EFFECT_ARIES("Period of initiative, new beginnings, and physical vitality. Mars energy brings action and courage.", "à¤ªà¤¹à¤², à¤¨à¤¯à¤¾à¤ à¤¶à¥à¤°à¥à¤†à¤¤ à¤° à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤œà¥‹à¤¸à¤•à¥‹ à¤…à¤µà¤§à¤¿à¥¤ à¤®à¤‚à¤—à¤²à¤•à¥‹ à¤Šà¤°à¥à¤œà¤¾à¤²à¥‡ à¤•à¤°à¥à¤® à¤° à¤¸à¤¾à¤¹à¤¸ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),
    KALACHAKRA_EFFECT_TAURUS("Focus on stability, wealth accumulation, and sensory pleasures. Venus brings comfort and beauty.", "à¤¸à¥à¤¥à¤¿à¤°à¤¤à¤¾, à¤§à¤¨ à¤¸à¤žà¥à¤šà¤¯ à¤° à¤‡à¤¨à¥à¤¦à¥à¤°à¤¿à¤¯ à¤¸à¥à¤–à¤®à¤¾ à¤§à¥à¤¯à¤¾à¤¨à¥¤ à¤¶à¥à¤•à¥à¤°à¤²à¥‡ à¤†à¤°à¤¾à¤® à¤° à¤¸à¥Œà¤¨à¥à¤¦à¤°à¥à¤¯ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),
    KALACHAKRA_EFFECT_GEMINI("Communication, learning, and intellectual pursuits are highlighted. Mercury brings versatility.", "à¤¸à¤žà¥à¤šà¤¾à¤°, à¤¸à¤¿à¤•à¤¾à¤‡ à¤° à¤¬à¥Œà¤¦à¥à¤§à¤¿à¤• à¤ªà¥à¤°à¤¯à¤¾à¤¸à¤¹à¤°à¥‚à¤²à¤¾à¤ˆ à¤œà¥‹à¤¡ à¤¦à¤¿à¤‡à¤¨à¥à¤›à¥¤ à¤¬à¥à¤§à¤²à¥‡ à¤¬à¤¹à¥à¤®à¥à¤–à¥€ à¤ªà¥à¤°à¤¤à¤¿à¤­à¤¾ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),
    KALACHAKRA_EFFECT_CANCER("Emotional growth, home, and family matters take center stage. Moon brings nurturing energy.", "à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤µà¥ƒà¤¦à¥à¤§à¤¿, à¤˜à¤° à¤° à¤ªà¤¾à¤°à¤¿à¤µà¤¾à¤°à¤¿à¤• à¤®à¤¾à¤®à¤¿à¤²à¤¾à¤¹à¤°à¥‚ à¤®à¥à¤–à¥à¤¯ à¤¹à¥à¤¨à¥à¤›à¤¨à¥à¥¤ à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤²à¥‡ à¤ªà¥‹à¤·à¤£ à¤—à¤°à¥à¤¨à¥‡ à¤Šà¤°à¥à¤œà¤¾ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),
    KALACHAKRA_EFFECT_LEO("Recognition, authority, and creative self-expression. Sun brings confidence and vitality.", "à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¾, à¤…à¤§à¤¿à¤•à¤¾à¤° à¤° à¤°à¤šà¤¨à¤¾à¤¤à¥à¤®à¤• à¤†à¤¤à¥à¤®-à¤…à¤­à¤¿à¤µà¥à¤¯à¤•à¥à¤¤à¤¿à¥¤ à¤¸à¥‚à¤°à¥à¤¯à¤²à¥‡ à¤†à¤¤à¥à¤®à¤µà¤¿à¤¶à¥à¤µà¤¾à¤¸ à¤° à¤œà¥‹à¤¸ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),
    KALACHAKRA_EFFECT_VIRGO("Analysis, health consciousness, and service. Mercury brings discrimination and healing potential.", "à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£, à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤šà¥‡à¤¤à¤¨à¤¾ à¤° à¤¸à¥‡à¤µà¤¾à¥¤ à¤¬à¥à¤§à¤²à¥‡ à¤µà¤¿à¤µà¥‡à¤• à¤° à¤¨à¤¿à¤•à¥‹ à¤ªà¤¾à¤°à¥à¤¨à¥‡ à¤•à¥à¤·à¤®à¤¤à¤¾ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),
    KALACHAKRA_EFFECT_LIBRA("Partnerships, balance, and aesthetic pursuits. Venus brings harmony and relationships.", "à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¥€, à¤¸à¤¨à¥à¤¤à¥à¤²à¤¨ à¤° à¤¸à¥Œà¤¨à¥à¤¦à¤°à¥à¤¯ à¤ªà¥à¤°à¤¯à¤¾à¤¸à¤¹à¤°à¥‚à¥¤ à¤¶à¥à¤•à¥à¤°à¤²à¥‡ à¤¸à¤¦à¥à¤­à¤¾à¤µ à¤° à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¹à¤°à¥‚ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),
    KALACHAKRA_EFFECT_SCORPIO("Deep transformation, hidden matters, and research. Mars/Ketu bring intensity and rebirth.", "à¤—à¤¹à¤¿à¤°à¥‹ à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£, à¤—à¥à¤ªà¥à¤¤ à¤®à¤¾à¤®à¤¿à¤²à¤¾ à¤° à¤…à¤¨à¥à¤¸à¤¨à¥à¤§à¤¾à¤¨à¥¤ à¤®à¤‚à¤—à¤²/à¤•à¥‡à¤¤à¥à¤²à¥‡ à¤¤à¥€à¤µà¥à¤°à¤¤à¤¾ à¤° à¤ªà¥à¤¨à¤°à¥à¤œà¤¨à¥à¤® à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¤¨à¥à¥¤"),
    KALACHAKRA_EFFECT_SAGITTARIUS("Higher learning, philosophy, and expansion. Jupiter brings wisdom and fortune.", "à¤‰à¤šà¥à¤š à¤¸à¤¿à¤•à¤¾à¤‡, à¤¦à¤°à¥à¤¶à¤¨ à¤° à¤µà¤¿à¤¸à¥à¤¤à¤¾à¤°à¥¤ à¤¬à¥ƒà¤¹à¤¸à¥à¤ªà¤¤à¤¿à¤²à¥‡ à¤œà¥à¤žà¤¾à¤¨ à¤° à¤­à¤¾à¤—à¥à¤¯ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),
    KALACHAKRA_EFFECT_CAPRICORN("Career, discipline, and long-term achievements. Saturn brings structure and maturity.", "à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°, à¤…à¤¨à¥à¤¶à¤¾à¤¸à¤¨ à¤° à¤¦à¥€à¤°à¥à¤˜à¤•à¤¾à¤²à¥€à¤¨ à¤‰à¤ªà¤²à¤¬à¥à¤§à¤¿à¤¹à¤°à¥‚à¥¤ à¤¶à¤¨à¤¿à¤²à¥‡ à¤¸à¤‚à¤°à¤šà¤¨à¤¾ à¤° à¤ªà¤°à¤¿à¤ªà¤•à¥à¤µà¤¤à¤¾ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),
    KALACHAKRA_EFFECT_AQUARIUS("Innovation, humanitarian concerns, and group activities. Saturn/Rahu bring progressive change.", "à¤¨à¤µà¤ªà¥à¤°à¤µà¤°à¥à¤¤à¤¨, à¤®à¤¾à¤¨à¤µà¥€à¤¯ à¤šà¤¾à¤¸à¥‹ à¤° à¤¸à¤®à¥‚à¤¹ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚à¥¤ à¤¶à¤¨à¤¿/à¤°à¤¾à¤¹à¥à¤²à¥‡ à¤ªà¥à¤°à¤—à¤¤à¤¿à¤¶à¥€à¤² à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¤¨à¥à¥¤"),
    KALACHAKRA_EFFECT_PISCES("Spirituality, imagination, and transcendence. Jupiter brings divine connection and liberation.", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤•à¤¤à¤¾, à¤•à¤²à¥à¤ªà¤¨à¤¾ à¤° à¤ªà¤¾à¤°à¤²à¥Œà¤•à¤¿à¤•à¤¤à¤¾à¥¤ à¤¬à¥ƒà¤¹à¤¸à¥à¤ªà¤¤à¤¿à¤²à¥‡ à¤ˆà¤¶à¥à¤µà¤°à¥€à¤¯ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤° à¤®à¥à¤•à¥à¤¤à¤¿ à¤²à¥à¤¯à¤¾à¤‰à¤à¤›à¥¤"),

    KALACHAKRA_BRIEF_ARIES("Energy, initiative, new starts.", "à¤Šà¤°à¥à¤œà¤¾, à¤ªà¤¹à¤², à¤¨à¤¯à¤¾à¤ à¤¶à¥à¤°à¥à¤†à¤¤à¥¤"),
    KALACHAKRA_BRIEF_TAURUS("Stability, wealth, comfort.", "à¤¸à¥à¤¥à¤¿à¤°à¤¤à¤¾, à¤§à¤¨, à¤†à¤°à¤¾à¤®à¥¤"),
    KALACHAKRA_BRIEF_GEMINI("Communication, learning, versatility.", "à¤¸à¤žà¥à¤šà¤¾à¤°, à¤¸à¤¿à¤•à¤¾à¤‡, à¤¬à¤¹à¥à¤®à¥à¤–à¥€ à¤ªà¥à¤°à¤¤à¤¿à¤­à¤¾à¥¤"),
    KALACHAKRA_BRIEF_CANCER("Emotions, home, nurturing.", "à¤­à¤¾à¤µà¤¨à¤¾à¤¹à¤°à¥‚, à¤˜à¤°, à¤ªà¤¾à¤²à¤¨à¤ªà¥‹à¤·à¤£à¥¤"),
    KALACHAKRA_BRIEF_LEO("Recognition, creativity, authority.", "à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¾, à¤°à¤šà¤¨à¤¾à¤¤à¥à¤®à¤•à¤¤à¤¾, à¤…à¤§à¤¿à¤•à¤¾à¤°à¥¤"),
    KALACHAKRA_BRIEF_VIRGO("Analysis, health, service.", "à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£, à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯, à¤¸à¥‡à¤µà¤¾à¥¤"),
    KALACHAKRA_BRIEF_LIBRA("Balance, relationships, harmony.", "à¤¸à¤¨à¥à¤¤à¥à¤²à¤¨, à¤¸à¤®à¥à¤¬à¤¨à¥à¤§, à¤¸à¤¦à¥à¤­à¤¾à¤µà¥¤"),
    KALACHAKRA_BRIEF_SCORPIO("Transformation, depth, intensity.", "à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£, à¤—à¤¹à¤¿à¤°à¤¾à¤ˆ, à¤¤à¥€à¤µà¥à¤°à¤¤à¤¾à¥¤"),
    KALACHAKRA_BRIEF_SAGITTARIUS("Expansion, wisdom, fortune.", "à¤µà¤¿à¤¸à¥à¤¤à¤¾à¤°, à¤œà¥à¤žà¤¾à¤¨, à¤­à¤¾à¤—à¥à¤¯à¥¤"),
    KALACHAKRA_BRIEF_CAPRICORN("Career, discipline, achievement.", "à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°, à¤…à¤¨à¥à¤¶à¤¾à¤¸à¤¨, à¤‰à¤ªà¤²à¤¬à¥à¤§à¤¿à¥¤"),
    KALACHAKRA_BRIEF_AQUARIUS("Innovation, humanity, progress.", "à¤¨à¤µà¤ªà¥à¤°à¤µà¤°à¥à¤¤à¤¨, à¤®à¤¾à¤¨à¤µà¤¤à¤¾, à¤ªà¥à¤°à¤—à¤¤à¤¿à¥¤"),
    KALACHAKRA_BRIEF_PISCES("Spirituality, imagination, liberation.", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤•à¤¤à¤¾, à¤•à¤²à¥à¤ªà¤¨à¤¾, à¤®à¥à¤•à¥à¤¤à¤¿à¥¤"),
    
    // Planet Strength
    PLANET_STRENGTH_EXALTED("Exalted - Very Strong", "à¤‰à¤šà¥à¤š - à¤§à¥‡à¤°à¥ˆ à¤¬à¤²à¤¿à¤¯à¥‹"),
    PLANET_STRENGTH_OWN_SIGN("Own Sign - Strong", "à¤¸à¥à¤µà¤°à¤¾à¤¶à¤¿ - à¤¬à¤²à¤¿à¤¯à¥‹"),
    PLANET_STRENGTH_DEBILITATED("Debilitated - Weak", "à¤¨à¥€à¤š - à¤•à¤®à¤œà¥‹à¤°"),
    PLANET_STRENGTH_RETROGRADE("Retrograde - Introspective", "à¤¬à¤•à¥à¤°à¥€ - à¤†à¤¤à¥à¤®à¤¨à¤¿à¤°à¥€à¤•à¥à¤·à¤•"),
    PLANET_STRENGTH_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    PLANET_STRENGTH_UNKNOWN("Unknown", "à¤…à¤œà¥à¤žà¤¾à¤¤"),

    // Kalachakra Predictions
    KALACHAKRA_PRED_INTRO_DEHA("Based on Deha lord's %1$s status and %2$s Deha-Jeeva relationship: ", "à¤¦à¥‡à¤¹ à¤¸à¥à¤µà¤¾à¤®à¥€à¤•à¥‹ %1$s à¤…à¤µà¤¸à¥à¤¥à¤¾ à¤° %2$s à¤¦à¥‡à¤¹-à¤œà¥€à¤µ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤•à¥‹ à¤†à¤§à¤¾à¤°à¤®à¤¾: "),
    KALACHAKRA_PRED_INTRO_JEEVA("Jeeva lord's %1$s condition indicates ", "à¤œà¥€à¤µ à¤¸à¥à¤µà¤¾à¤®à¥€à¤•à¥‹ %1$s à¤…à¤µà¤¸à¥à¤¥à¤¾à¤²à¥‡ à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤¦à¤› "),

    KALACHAKRA_HEALTH_PRED_HARMONIOUS("Physical health is well-supported by spiritual practices. Body responds well to holistic healing.", "à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯à¤²à¤¾à¤ˆ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤…à¤­à¥à¤¯à¤¾à¤¸à¤¹à¤°à¥‚à¤²à¥‡ à¤°à¤¾à¤®à¥à¤°à¥‹ à¤¸à¤®à¤°à¥à¤¥à¤¨ à¤—à¤°à¥à¤¦à¤›à¥¤ à¤¶à¤°à¥€à¤°à¤²à¥‡ à¤¸à¤®à¤—à¥à¤° à¤‰à¤ªà¤šà¤¾à¤°à¤²à¤¾à¤ˆ à¤°à¤¾à¤®à¥à¤°à¥‹ à¤ªà¥à¤°à¤¤à¤¿à¤•à¥à¤°à¤¿à¤¯à¤¾ à¤¦à¤¿à¤¨à¥à¤›à¥¤"),
    KALACHAKRA_HEALTH_PRED_SUPPORTIVE("Good baseline health with spiritual practices enhancing physical wellbeing.", "à¤°à¤¾à¤®à¥à¤°à¥‹ à¤†à¤§à¤¾à¤°à¤­à¥‚à¤¤ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯à¤•à¥‹ à¤¸à¤¾à¤¥ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤…à¤­à¥à¤¯à¤¾à¤¸à¤¹à¤°à¥‚à¤²à¥‡ à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤•à¤²à¥à¤¯à¤¾à¤£ à¤¬à¤¢à¤¾à¤‰à¤à¤›à¥¤"),
    KALACHAKRA_HEALTH_PRED_NEUTRAL("Health matters follow their natural course. Maintain regular routines.", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤®à¤¾à¤®à¤¿à¤²à¤¾à¤¹à¤°à¥‚à¤²à¥‡ à¤†à¤«à¥à¤¨à¥‹ à¤ªà¥à¤°à¤¾à¤•à¥ƒà¤¤à¤¿à¤• à¤®à¤¾à¤°à¥à¤— à¤ªà¤›à¥à¤¯à¤¾à¤‰à¤à¤›à¤¨à¥à¥¤ à¤¨à¤¿à¤¯à¤®à¤¿à¤¤ à¤¦à¤¿à¤¨à¤šà¤°à¥à¤¯à¤¾ à¤•à¤¾à¤¯à¤® à¤°à¤¾à¤–à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    KALACHAKRA_HEALTH_PRED_CHALLENGING("May experience tension between physical demands and spiritual aspirations. Balance is key.", "à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤®à¤¾à¤— à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤†à¤•à¤¾à¤‚à¤•à¥à¤·à¤¾à¤¹à¤°à¥‚ à¤¬à¥€à¤š à¤¤à¤¨à¤¾à¤µ à¤…à¤¨à¥à¤­à¤µ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤ à¤¸à¤¨à¥à¤¤à¥à¤²à¤¨ à¤•à¥à¤žà¥à¤œà¥€ à¤¹à¥‹à¥¤"),
    KALACHAKRA_HEALTH_PRED_TRANSFORMATIVE("Health challenges may serve as catalysts for spiritual growth. Deep healing possible.", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤šà¥à¤¨à¥Œà¤¤à¥€à¤¹à¤°à¥‚à¤²à¥‡ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤µà¥ƒà¤¦à¥à¤§à¤¿à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤‰à¤¤à¥à¤ªà¥à¤°à¥‡à¤°à¤•à¤•à¥‹ à¤°à¥‚à¤ªà¤®à¤¾ à¤•à¤¾à¤® à¤—à¤°à¥à¤¨ à¤¸à¤•à¥à¤›à¤¨à¥à¥¤ à¤—à¤¹à¤¿à¤°à¥‹ à¤‰à¤ªà¤šà¤¾à¤° à¤¸à¤®à¥à¤­à¤µ à¤›à¥¤"),

    KALACHAKRA_SPIRITUAL_PRED_HARMONIOUS("natural spiritual progress. Meditation and dharmic practices flow easily.", "à¤ªà¥à¤°à¤¾à¤•à¥ƒà¤¤à¤¿à¤• à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤ªà¥à¤°à¤—à¤¤à¤¿à¥¤ à¤§à¥à¤¯à¤¾à¤¨ à¤° à¤§à¤¾à¤°à¥à¤®à¤¿à¤• à¤…à¤­à¥à¤¯à¤¾à¤¸à¤¹à¤°à¥‚ à¤¸à¤œà¤¿à¤²à¥ˆ à¤ªà¥à¤°à¤µà¤¾à¤¹à¤¿à¤¤ à¤¹à¥à¤¨à¥à¤›à¤¨à¥à¥¤"),
    KALACHAKRA_SPIRITUAL_PRED_SUPPORTIVE("spiritual growth through practical application of wisdom.", "à¤œà¥à¤žà¤¾à¤¨à¤•à¥‹ à¤µà¥à¤¯à¤¾à¤µà¤¹à¤¾à¤°à¤¿à¤• à¤ªà¥à¤°à¤¯à¥‹à¤— à¤®à¤¾à¤°à¥à¤«à¤¤ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤µà¥ƒà¤¦à¥à¤§à¤¿à¥¤"),
    KALACHAKRA_SPIRITUAL_PRED_NEUTRAL("steady spiritual development through consistent practice.", "à¤¨à¤¿à¤°à¤¨à¥à¤¤à¤° à¤…à¤­à¥à¤¯à¤¾à¤¸ à¤®à¤¾à¤°à¥à¤«à¤¤ à¤¸à¥à¤¥à¤¿à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤µà¤¿à¤•à¤¾à¤¸à¥¤"),
    KALACHAKRA_SPIRITUAL_PRED_CHALLENGING("spiritual growth through overcoming material attachments.", "à¤­à¥Œà¤¤à¤¿à¤• à¤†à¤¸à¤•à¥à¤¤à¤¿à¤¹à¤°à¥‚ à¤œà¤¿à¤¤à¥‡à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤µà¥ƒà¤¦à¥à¤§à¤¿à¥¤"),
    KALACHAKRA_SPIRITUAL_PRED_TRANSFORMATIVE("profound spiritual transformation through life's challenges.", "à¤œà¥€à¤µà¤¨à¤•à¤¾ à¤šà¥à¤¨à¥Œà¤¤à¥€à¤¹à¤°à¥‚ à¤®à¤¾à¤°à¥à¤«à¤¤ à¤—à¤¹à¤¿à¤°à¥‹ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£à¥¤"),

    // Recommendations
    KALACHAKRA_REC_DEHA_LORD("Strengthen Deha lord (%1$s) through appropriate mantras and gemstones", "à¤‰à¤ªà¤¯à¥à¤•à¥à¤¤ à¤®à¤¨à¥à¤¤à¥à¤° à¤° à¤°à¤¤à¥à¤¨à¤¹à¤°à¥‚ à¤®à¤¾à¤°à¥à¤«à¤¤ à¤¦à¥‡à¤¹ à¤¸à¥à¤µà¤¾à¤®à¥€ (%1$s) à¤²à¤¾à¤ˆ à¤¬à¤²à¤¿à¤¯à¥‹ à¤¬à¤¨à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    KALACHAKRA_REC_JEEVA_LORD("Honor Jeeva lord (%1$s) through spiritual practices", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤…à¤­à¥à¤¯à¤¾à¤¸à¤¹à¤°à¥‚ à¤®à¤¾à¤°à¥à¤«à¤¤ à¤œà¥€à¤µ à¤¸à¥à¤µà¤¾à¤®à¥€ (%1$s) à¤²à¤¾à¤ˆ à¤¸à¤®à¥à¤®à¤¾à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    
    KALACHAKRA_REC_HARMONIOUS_1("Continue current spiritual and health practices - they are aligned", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤° à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤…à¤­à¥à¤¯à¤¾à¤¸à¤¹à¤°à¥‚ à¤œà¤¾à¤°à¥€ à¤°à¤¾à¤–à¥à¤¨à¥à¤¹à¥‹à¤¸à¥ - à¤¤à¤¿à¤¨à¥€à¤¹à¤°à¥‚ à¤ªà¤™à¥à¤•à¥à¤¤à¤¿à¤¬à¤¦à¥à¤§ à¤›à¤¨à¥"),
    KALACHAKRA_REC_HARMONIOUS_2("Use this favorable period for deepening meditation", "à¤¯à¥‹ à¤…à¤¨à¥à¤•à¥‚à¤² à¤…à¤µà¤§à¤¿à¤²à¤¾à¤ˆ à¤§à¥à¤¯à¤¾à¤¨ à¤—à¤¹à¤¿à¤°à¥‹ à¤¬à¤¨à¤¾à¤‰à¤¨ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    KALACHAKRA_REC_SUPPORTIVE_1("Integrate physical yoga with spiritual practices", "à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤¯à¥‹à¤—à¤²à¤¾à¤ˆ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤…à¤­à¥à¤¯à¤¾à¤¸à¤¹à¤°à¥‚à¤¸à¤à¤— à¤à¤•à¥€à¤•à¥ƒà¤¤ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    KALACHAKRA_REC_SUPPORTIVE_2("Serve others as part of spiritual path", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤®à¤¾à¤°à¥à¤—à¤•à¥‹ à¤­à¤¾à¤—à¤•à¥‹ à¤°à¥‚à¤ªà¤®à¤¾ à¤…à¤°à¥‚à¤•à¥‹ à¤¸à¥‡à¤µà¤¾ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    KALACHAKRA_REC_NEUTRAL_1("Establish regular routines for both physical and spiritual health", "à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤¦à¥à¤µà¥ˆà¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¨à¤¿à¤¯à¤®à¤¿à¤¤ à¤¦à¤¿à¤¨à¤šà¤°à¥à¤¯à¤¾ à¤¸à¥à¤¥à¤¾à¤ªà¤¨à¤¾ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    KALACHAKRA_REC_NEUTRAL_2("Create balance between material and spiritual pursuits", "à¤­à¥Œà¤¤à¤¿à¤• à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤ªà¥à¤°à¤¯à¤¾à¤¸à¤¹à¤°à¥‚ à¤¬à¥€à¤š à¤¸à¤¨à¥à¤¤à¥à¤²à¤¨ à¤¸à¤¿à¤°à¥à¤œà¤¨à¤¾ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    KALACHAKRA_REC_CHALLENGING_1("Practice patience and acceptance with physical limitations", "à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤¸à¥€à¤®à¤¾à¤¹à¤°à¥‚à¤¸à¤à¤— à¤§à¥ˆà¤°à¥à¤¯ à¤° à¤¸à¥à¤µà¥€à¤•à¥ƒà¤¤à¤¿ à¤…à¤­à¥à¤¯à¤¾à¤¸ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    KALACHAKRA_REC_CHALLENGING_2("Transform challenges into spiritual growth opportunities", "à¤šà¥à¤¨à¥Œà¤¤à¥€à¤¹à¤°à¥‚à¤²à¤¾à¤ˆ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤µà¥ƒà¤¦à¥à¤§à¤¿à¤•à¥‹ à¤…à¤µà¤¸à¤°à¤®à¤¾ à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    KALACHAKRA_REC_TRANSFORMATIVE_1("Embrace transformation as part of soul's journey", "à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£à¤²à¤¾à¤ˆ à¤†à¤¤à¥à¤®à¤¾à¤•à¥‹ à¤¯à¤¾à¤¤à¥à¤°à¤¾à¤•à¥‹ à¤­à¤¾à¤—à¤•à¥‹ à¤°à¥‚à¤ªà¤®à¤¾ à¤…à¤à¤—à¤¾à¤²à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    KALACHAKRA_REC_TRANSFORMATIVE_2("Seek guidance from spiritual teachers during difficult periods", "à¤•à¤ à¤¿à¤¨ à¤¸à¤®à¤¯à¤®à¤¾ à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤—à¥à¤°à¥à¤¹à¤°à¥‚à¤¬à¤¾à¤Ÿ à¤®à¤¾à¤°à¥à¤—à¤¦à¤°à¥à¤¶à¤¨ à¤²à¤¿à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    LAL_KITAB_SCREEN_SUBTITLE("Practical Karmic Remedies", "à¤µà¥à¤¯à¤¾à¤µà¤¹à¤¾à¤°à¤¿à¤• à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    LAL_KITAB_SCREEN_ABOUT("About Lal Kitab", "à¤²à¤¾à¤² à¤•à¤¿à¤¤à¤¾à¤¬à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    LAL_KITAB_SCREEN_ABOUT_DESC("Lal Kitab offers simple, practical remedies using everyday items like turmeric, milk, honey, and specific actions. These remedies work on karmic debts and planetary afflictions without expensive rituals.", "à¤²à¤¾à¤² à¤•à¤¿à¤¤à¤¾à¤¬à¤²à¥‡ à¤¬à¥‡à¤¸à¤¾à¤°, à¤¦à¥‚à¤§, à¤®à¤¹ à¤œà¤¸à¥à¤¤à¤¾ à¤¦à¥ˆà¤¨à¤¿à¤• à¤µà¤¸à¥à¤¤à¥à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¥€ à¤¸à¤°à¤², à¤µà¥à¤¯à¤¾à¤µà¤¹à¤¾à¤°à¤¿à¤• à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¦à¤›à¥¤ à¤¯à¥€ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚à¤²à¥‡ à¤®à¤¹à¤à¤—à¥‹ à¤µà¤¿à¤§à¤¿ à¤¬à¤¿à¤¨à¤¾ à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤‹à¤£ à¤° à¤—à¥à¤°à¤¹ à¤ªà¥€à¤¡à¤¾à¤®à¤¾ à¤•à¤¾à¤® à¤—à¤°à¥à¤¦à¤›à¤¨à¥à¥¤"),
    LAL_KITAB_SCREEN_KARMIC_DEBTS("Karmic Debts (Rin)", "à¤•à¤¾à¤°à¥à¤®à¤¿à¤• à¤‹à¤£à¤¹à¤°à¥‚"),
    LAL_KITAB_SCREEN_PLANETARY_AFFLICTIONS("Planetary Afflictions", "à¤—à¥à¤°à¤¹ à¤ªà¥€à¤¡à¤¾à¤¹à¤°à¥‚"),
    LAL_KITAB_HOUSE_REMEDIES("House-Based Remedies", "à¤­à¤¾à¤µ-à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    LAL_KITAB_DAILY_REMEDIES("Daily Remedies", "à¤¦à¥ˆà¤¨à¤¿à¤• à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    LAL_KITAB_ITEM_BASED("Item-Based Remedies", "à¤µà¤¸à¥à¤¤à¥-à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    LAL_KITAB_COLOR_THERAPY("Color Therapy", "à¤°à¤‚à¤— à¤šà¤¿à¤•à¤¿à¤¤à¥à¤¸à¤¾"),
    LAL_KITAB_DIRECTION_GUIDANCE("Direction Guidance", "à¤¦à¤¿à¤¶à¤¾ à¤®à¤¾à¤°à¥à¤—à¤¦à¤°à¥à¤¶à¤¨"),
    LAL_KITAB_WEEKLY_SCHEDULE("Weekly Remedy Schedule", "à¤¸à¤¾à¤ªà¥à¤¤à¤¾à¤¹à¤¿à¤• à¤‰à¤ªà¤¾à¤¯ à¤¤à¤¾à¤²à¤¿à¤•à¤¾"),
    LAL_KITAB_REMEDY_ITEM("Remedy Item", "à¤‰à¤ªà¤¾à¤¯ à¤µà¤¸à¥à¤¤à¥"),
    LAL_KITAB_REMEDY_ACTION("Action Required", "à¤†à¤µà¤¶à¥à¤¯à¤• à¤•à¤¾à¤°à¥à¤¯"),
    LAL_KITAB_REMEDY_TIMING("Best Timing", "à¤‰à¤¤à¥à¤¤à¤® à¤¸à¤®à¤¯"),
    LAL_KITAB_EFFECTIVENESS_HIGH("Highly Effective", "à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤ªà¥à¤°à¤­à¤¾à¤µà¤•à¤¾à¤°à¥€"),
    LAL_KITAB_EFFECTIVENESS_MEDIUM("Moderately Effective", "à¤®à¤§à¥à¤¯à¤® à¤ªà¥à¤°à¤­à¤¾à¤µà¤•à¤¾à¤°à¥€"),
    LAL_KITAB_DEBT_PITRU("Pitru Rin (Ancestral)", "à¤ªà¤¿à¤¤à¥ƒ à¤‹à¤£ (à¤ªà¥‚à¤°à¥à¤µà¤œ)"),
    LAL_KITAB_DEBT_MATRU("Matru Rin (Maternal)", "à¤®à¤¾à¤¤à¥ƒ à¤‹à¤£ (à¤®à¤¾à¤¤à¥ƒà¤ªà¤•à¥à¤·)"),
    LAL_KITAB_DEBT_STRI("Stri Rin (Feminine)", "à¤¸à¥à¤¤à¥à¤°à¥€ à¤‹à¤£ (à¤¸à¥à¤¤à¥à¤°à¥€)"),
    LAL_KITAB_DEBT_SELF("Swayam Rin (Self)", "à¤¸à¥à¤µà¤¯à¤‚ à¤‹à¤£ (à¤¸à¥à¤µà¤¯à¤‚)"),

    // Divisional Charts Screen specific
    DIVISIONAL_CHARTS_TITLE("Divisional Charts Analysis", "à¤µà¤¿à¤­à¤¾à¤—à¥€à¤¯ à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    DIVISIONAL_CHARTS_SUBTITLE("Detailed Varga Analysis", "à¤µà¤¿à¤¸à¥à¤¤à¥ƒà¤¤ à¤µà¤°à¥à¤— à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    DIVISIONAL_CHARTS_ABOUT("About Divisional Charts", "à¤µà¤¿à¤­à¤¾à¤—à¥€à¤¯ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    DIVISIONAL_CHARTS_ABOUT_DESC("Divisional charts (Vargas) provide deeper insights into specific life areas. D-2 for wealth, D-3 for siblings, D-9 for marriage, D-10 for career, D-12 for parents.", "à¤µà¤¿à¤­à¤¾à¤—à¥€à¤¯ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤¹à¤°à¥‚à¤²à¥‡ à¤µà¤¿à¤¶à¥‡à¤· à¤œà¥€à¤µà¤¨ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚à¤®à¤¾ à¤—à¤¹à¤¿à¤°à¥‹ à¤…à¤¨à¥à¤¤à¤°à¥à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿ à¤ªà¥à¤°à¤¦à¤¾à¤¨ à¤—à¤°à¥à¤¦à¤›à¤¨à¥à¥¤ D-2 à¤§à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿, D-3 à¤­à¤¾à¤‡à¤¬à¤¹à¤¿à¤¨à¥€à¤•à¥‹ à¤²à¤¾à¤—à¤¿, D-9 à¤µà¤¿à¤µà¤¾à¤¹à¤•à¥‹ à¤²à¤¾à¤—à¤¿, D-10 à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°à¤•à¥‹ à¤²à¤¾à¤—à¤¿, D-12 à¤…à¤­à¤¿à¤­à¤¾à¤µà¤•à¤•à¥‹ à¤²à¤¾à¤—à¤¿à¥¤"),
    DIVISIONAL_HORA_TAB("D-2 Hora", "D-2 à¤¹à¥‹à¤°à¤¾"),
    DIVISIONAL_DREKKANA_TAB("D-3 Drekkana", "D-3 à¤¦à¥à¤°à¥‡à¤•à¥à¤•à¤¾à¤£"),
    DIVISIONAL_NAVAMSA_TAB("D-9 Navamsa", "D-9 à¤¨à¤µà¤¾à¤‚à¤¶"),
    DIVISIONAL_DASHAMSA_TAB("D-10 Dashamsa", "D-10 à¤¦à¤¶à¤¾à¤‚à¤¶"),
    DIVISIONAL_DWADASAMSA_TAB("D-12 Dwadasamsa", "D-12 à¤¦à¥à¤µà¤¾à¤¦à¤¶à¤¾à¤‚à¤¶"),
    DIVISIONAL_WEALTH_ANALYSIS("Wealth & Finance", "à¤§à¤¨ à¤° à¤µà¤¿à¤¤à¥à¤¤"),
    DIVISIONAL_SIBLING_ANALYSIS("Siblings & Courage", "à¤­à¤¾à¤‡à¤¬à¤¹à¤¿à¤¨à¥€ à¤° à¤¸à¤¾à¤¹à¤¸"),
    DIVISIONAL_MARRIAGE_ANALYSIS("Marriage & Dharma", "à¤µà¤¿à¤µà¤¾à¤¹ à¤° à¤§à¤°à¥à¤®"),
    DIVISIONAL_CAREER_ANALYSIS("Career & Status", "à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤° à¤° à¤¸à¥à¤¥à¤¿à¤¤à¤¿"),
    DIVISIONAL_PARENTS_ANALYSIS("Parents & Ancestry", "à¤…à¤­à¤¿à¤­à¤¾à¤µà¤• à¤° à¤µà¤‚à¤¶"),
    DIVISIONAL_KEY_PLANETS("Key Planets", "à¤®à¥à¤–à¥à¤¯ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    DIVISIONAL_STRENGTH_ASSESSMENT("Strength Assessment", "à¤¬à¤² à¤®à¥‚à¤²à¥à¤¯à¤¾à¤™à¥à¤•à¤¨"),
    DIVISIONAL_DETAILED_RESULTS("Detailed Results", "à¤µà¤¿à¤¸à¥à¤¤à¥ƒà¤¤ à¤ªà¤°à¤¿à¤£à¤¾à¤®à¤¹à¤°à¥‚"),

    // Upachaya Transit Screen specific
    UPACHAYA_SCREEN_TITLE("Upachaya Transits", "à¤‰à¤ªà¤šà¤¯ à¤—à¥‹à¤šà¤°"),
    UPACHAYA_SCREEN_SUBTITLE("Growth House Transit Tracker", "à¤µà¥ƒà¤¦à¥à¤§à¤¿ à¤­à¤¾à¤µ à¤—à¥‹à¤šà¤° à¤Ÿà¥à¤°à¥à¤¯à¤¾à¤•à¤°"),
    UPACHAYA_SCREEN_ABOUT("About Upachaya Houses", "à¤‰à¤ªà¤šà¤¯ à¤­à¤¾à¤µà¤¹à¤°à¥‚à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    UPACHAYA_SCREEN_ABOUT_DESC("Upachaya houses (3, 6, 10, 11) are growth houses where malefic planets give good results over time. Natural malefics improve with age in these houses.", "à¤‰à¤ªà¤šà¤¯ à¤­à¤¾à¤µà¤¹à¤°à¥‚ (à¥©, à¥¬, à¥§à¥¦, à¥§à¥§) à¤µà¥ƒà¤¦à¥à¤§à¤¿ à¤­à¤¾à¤µà¤¹à¤°à¥‚ à¤¹à¥à¤¨à¥ à¤œà¤¹à¤¾à¤ à¤ªà¤¾à¤ªà¤—à¥à¤°à¤¹à¤²à¥‡ à¤¸à¤®à¤¯à¤¸à¤à¤—à¥ˆ à¤°à¤¾à¤®à¥à¤°à¥‹ à¤ªà¤°à¤¿à¤£à¤¾à¤® à¤¦à¤¿à¤¨à¥à¤›à¤¨à¥à¥¤"),
    UPACHAYA_HOUSES("Upachaya Houses (3, 6, 10, 11)", "à¤‰à¤ªà¤šà¤¯ à¤­à¤¾à¤µà¤¹à¤°à¥‚ (à¥©, à¥¬, à¥§à¥¦, à¥§à¥§)"),
    UPACHAYA_SCREEN_FROM_MOON("Transit from Moon Sign", "à¤šà¤¨à¥à¤¦à¥à¤° à¤°à¤¾à¤¶à¤¿à¤¬à¤¾à¤Ÿ à¤—à¥‹à¤šà¤°"),
    UPACHAYA_CURRENT_TRANSITS("Current Beneficial Transits", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤²à¤¾à¤­à¤¦à¤¾à¤¯à¤• à¤—à¥‹à¤šà¤°"),
    UPACHAYA_SCREEN_UPCOMING("Upcoming Favorable Periods", "à¤†à¤—à¤¾à¤®à¥€ à¤…à¤¨à¥à¤•à¥‚à¤² à¤…à¤µà¤§à¤¿à¤¹à¤°à¥‚"),
    UPACHAYA_SATURN_TRANSIT("Saturn in Upachaya", "à¤‰à¤ªà¤šà¤¯à¤®à¤¾ à¤¶à¤¨à¤¿"),
    UPACHAYA_MARS_TRANSIT("Mars in Upachaya", "à¤‰à¤ªà¤šà¤¯à¤®à¤¾ à¤®à¤‚à¤—à¤²"),
    UPACHAYA_RAHU_TRANSIT("Rahu in Upachaya", "à¤‰à¤ªà¤šà¤¯à¤®à¤¾ à¤°à¤¾à¤¹à¥"),
    UPACHAYA_JUPITER_TRANSIT("Jupiter in Upachaya", "à¤‰à¤ªà¤šà¤¯à¤®à¤¾ à¤¬à¥ƒà¤¹à¤¸à¥à¤ªà¤¤à¤¿"),
    UPACHAYA_SCREEN_HOUSE_3("3rd House - Courage, Siblings, Communication", "à¤¤à¥‡à¤¸à¥à¤°à¥‹ à¤­à¤¾à¤µ - à¤¸à¤¾à¤¹à¤¸, à¤­à¤¾à¤‡à¤¬à¤¹à¤¿à¤¨à¥€, à¤¸à¤žà¥à¤šà¤¾à¤°"),
    UPACHAYA_SCREEN_HOUSE_6("6th House - Enemies, Service, Health", "à¤›à¥ˆà¤Ÿà¥Œà¤‚ à¤­à¤¾à¤µ - à¤¶à¤¤à¥à¤°à¥, à¤¸à¥‡à¤µà¤¾, à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯"),
    UPACHAYA_SCREEN_HOUSE_10("10th House - Career, Status, Authority", "à¤¦à¤¸à¥Œà¤‚ à¤­à¤¾à¤µ - à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°, à¤¸à¥à¤¥à¤¿à¤¤à¤¿, à¤…à¤§à¤¿à¤•à¤¾à¤°"),
    UPACHAYA_SCREEN_HOUSE_11("11th House - Gains, Desires, Friends", "à¤à¤˜à¤¾à¤°à¥Œà¤‚ à¤­à¤¾à¤µ - à¤²à¤¾à¤­, à¤‡à¤šà¥à¤›à¤¾, à¤®à¤¿à¤¤à¥à¤°"),
    UPACHAYA_GROWTH_INDICATOR("Growth Indicator", "à¤µà¥ƒà¤¦à¥à¤§à¤¿ à¤¸à¥‚à¤šà¤•"),
    UPACHAYA_ACTIVE_NOW("Active Now", "à¤…à¤¹à¤¿à¤²à¥‡ à¤¸à¤•à¥à¤°à¤¿à¤¯"),
    UPACHAYA_COMING_SOON("Coming Soon", "à¤›à¤¿à¤Ÿà¥à¤Ÿà¥ˆ à¤†à¤‰à¤à¤¦à¥ˆà¤›"),
    UPACHAYA_SCREEN_DURATION("Transit Duration", "à¤—à¥‹à¤šà¤° à¤…à¤µà¤§à¤¿"),
    UPACHAYA_SCREEN_EFFECTS("Expected Effects", "à¤…à¤ªà¥‡à¤•à¥à¤·à¤¿à¤¤ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    UPACHAYA_IMPROVEMENT_AREAS("Areas of Improvement", "à¤¸à¥à¤§à¤¾à¤°à¤•à¤¾ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚"),
    UPACHAYA_HOUSE_ANALYSIS("House Analysis", "à¤­à¤¾à¤µ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    UPACHAYA_TRANSIT_DETAILS("Transit Details", "à¤—à¥‹à¤šà¤° à¤µà¤¿à¤µà¤°à¤£"),
    UPACHAYA_UPCOMING_TRANSITS("Upcoming Transits", "à¤†à¤—à¤¾à¤®à¥€ à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚"),
    UPACHAYA_ACTIVE_ALERTS("Active Alerts", "à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤¸à¥‚à¤šà¤¨à¤¾à¤¹à¤°à¥‚"),
    UPACHAYA_SIGNIFICANT_TRANSITS("Significant Transits", "à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚"),
    UPACHAYA_TRANSIT_ASSESSMENT("Transit Assessment", "à¤—à¥‹à¤šà¤° à¤®à¥‚à¤²à¥à¤¯à¤¾à¤™à¥à¤•à¤¨"),
    UPACHAYA_REFERENCE_POINTS("Reference Points", "à¤¸à¤¨à¥à¤¦à¤°à¥à¤­ à¤¬à¤¿à¤¨à¥à¤¦à¥à¤¹à¤°à¥‚"),
    UPACHAYA_ABOUT("About Upachaya", "à¤‰à¤ªà¤šà¤¯à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),

    // General UI labels needed by screens
    BIRTH_NAKSHATRA("Birth Nakshatra", "à¤œà¤¨à¥à¤® à¤¨à¤•à¥à¤·à¤¤à¥à¤°"),
    BALANCE_LABEL("Balance at Birth", "à¤œà¤¨à¥à¤®à¤®à¤¾ à¤¸à¤¨à¥à¤¤à¥à¤²à¤¨"),
    KEY_THEMES("Key Themes", "à¤®à¥à¤–à¥à¤¯ à¤µà¤¿à¤·à¤¯à¤¹à¤°à¥‚"),
    EFFECTS_LABEL("Effects", "à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    CURRENT_LABEL("Current", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨"),
    PREVIOUS_LABEL("Previous", "à¤…à¤˜à¤¿à¤²à¥à¤²à¥‹"),
    NEXT_LABEL("Next", "à¤…à¤°à¥à¤•à¥‹"),
    PRIMARY_LABEL("Primary", "à¤ªà¥à¤°à¤¾à¤¥à¤®à¤¿à¤•"),
    SECONDARY_LABEL("Secondary", "à¤®à¤¾à¤§à¥à¤¯à¤®à¤¿à¤•"),
    LAGNA_LABEL("Lagna", "à¤²à¤—à¥à¤¨"),
    MOON_LABEL("Moon", "à¤šà¤¨à¥à¤¦à¥à¤°"),
    SUN_LABEL("Sun", "à¤¸à¥‚à¤°à¥à¤¯"),
    SIGN_LABEL("Sign", "à¤°à¤¾à¤¶à¤¿"),
    HOUSE_LABEL("House", "à¤­à¤¾à¤µ"),
    STRENGTH_LABEL("Strength", "à¤¬à¤²"),
    RELATIONSHIP_FRIEND("Friendly", "à¤®à¥ˆà¤¤à¥à¤°à¥€à¤ªà¥‚à¤°à¥à¤£"),
    RELATIONSHIP_ENEMY("Inimical", "à¤¶à¤¤à¥à¤°à¥à¤¤à¤¾à¤ªà¥‚à¤°à¥à¤£"),
    RELATIONSHIP_NEUTRAL("Neutral", "à¤¤à¤Ÿà¤¸à¥à¤¥"),
    RELATIONSHIP_SAME("Intensified", "à¤¤à¥€à¤µà¥à¤°"),
    PANCHA_EXCELLENT("Excellent (5+)", "à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ (à¥«+)"),
    PANCHA_GOOD("Good (4)", "à¤°à¤¾à¤®à¥à¤°à¥‹ (à¥ª)"),
    PANCHA_AVERAGE("Average (3)", "à¤”à¤¸à¤¤ (à¥©)"),
    PANCHA_BELOW_AVERAGE("Below Average (2)", "à¤”à¤¸à¤¤ à¤­à¤¨à¥à¤¦à¤¾ à¤•à¤® (à¥¨)"),
    PANCHA_WEAK("Weak (0-1)", "à¤•à¤®à¤œà¥‹à¤° (à¥¦-à¥§)"),

    // ============================================
    // DASHA SYSTEMS
    // ============================================
    DASHA_VIMSOTTARI("Vimsottari", "à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹"), // Correct translation for Vimsottari? "Vimsottari" is usually kept or transliterated. "à¤µà¤¿à¤‚à¤¶à¥‹à¤¤à¥à¤¤à¤°à¥€".
    // Wait, "à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹" means "Yours". That's a mistake.
    // I will use proper transliteration.
    DASHA_VIMSOTTARI_NAME("Vimsottari", "à¤µà¤¿à¤‚à¤¶à¥‹à¤¤à¥à¤¤à¤°à¥€"),
    DASHA_VIMSOTTARI_DESC("Most widely used Nakshatra-based planetary period system", "à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤¬à¤¢à¥€ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤¹à¥à¤¨à¥‡ à¤¨à¤•à¥à¤·à¤¤à¥à¤°-à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤—à¥à¤°à¤¹ à¤…à¤µà¤§à¤¿ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€"),
    DASHA_VIMSOTTARI_DURATION("120 years", "à¥§à¥¨à¥¦ à¤µà¤°à¥à¤·"),

    DASHA_YOGINI("Yogini", "à¤¯à¥‹à¤—à¤¿à¤¨à¥€"),
    DASHA_YOGINI_DESC("Feminine energy-based system, especially for female charts", "à¤¸à¥à¤¤à¥à¤°à¥€ à¤Šà¤°à¥à¤œà¤¾-à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€, à¤µà¤¿à¤¶à¥‡à¤· à¤—à¤°à¥€ à¤®à¤¹à¤¿à¤²à¤¾ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤•à¥‹ à¤²à¤¾à¤—à¤¿"),
    DASHA_YOGINI_DURATION("36 years", "à¥©à¥¬ à¤µà¤°à¥à¤·"),

    DASHA_ASHTOTTARI("Ashtottari", "à¤…à¤·à¥à¤Ÿà¥‹à¤¤à¥à¤¤à¤°à¥€"),
    DASHA_ASHTOTTARI_DESC("Traditional for night births, uses 8 planets", "à¤°à¤¾à¤¤à¥à¤°à¥€ à¤œà¤¨à¥à¤®à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤ªà¤°à¤®à¥à¤ªà¤°à¤¾à¤—à¤¤, à¥® à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¥à¤¦à¤›"),
    DASHA_ASHTOTTARI_DURATION("108 years", "à¥§à¥¦à¥® à¤µà¤°à¥à¤·"),

    DASHA_SUDARSHANA("Sudarshana", "à¤¸à¥à¤¦à¤°à¥à¤¶à¤¨"),
    DASHA_SUDARSHANA_DESC("Chakra Dasha from Lagna, Moon, and Sun simultaneously", "à¤²à¤—à¥à¤¨, à¤šà¤¨à¥à¤¦à¥à¤° à¤° à¤¸à¥‚à¤°à¥à¤¯à¤¬à¤¾à¤Ÿ à¤à¤• à¤¸à¤¾à¤¥ à¤šà¤•à¥à¤° à¤¦à¤¶à¤¾"),
    DASHA_SUDARSHANA_DURATION("Triple view", "à¤¤à¥à¤°à¤¿à¤ªà¤•à¥à¤·à¥€à¤¯ à¤¦à¥ƒà¤¶à¥à¤¯"),
    
    DASHA_CHARA("Chara", "à¤•à¤¾à¤°à¤¾"),
    DASHA_CHARA_DESC("Jaimini sign-based system with Karakamsha analysis", "à¤œà¥ˆà¤®à¤¿à¤¨à¥€ à¤°à¤¾à¤¶à¤¿-à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€, à¤•à¤¾à¤°à¤•à¤¾à¤‚à¤¶ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤¸à¤¹à¤¿à¤¤"),
    DASHA_CHARA_DURATION("Variable", "à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨à¤¶à¥€à¤²"),
    
    DASHA_TITLE_YOGINI("Yogini Dasha", "à¤¯à¥‹à¤—à¤¿à¤¨à¥€ à¤¦à¤¶à¤¾"),
    DASHA_TITLE_CHARA("Chara Dasha", "à¤•à¤¾à¤°à¤¾ à¤¦à¤¶à¤¾"),
    DASHA_VIEW_SYSTEM("View %s", "%s à¤¹à¥‡à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    DASHA_CYCLE_DURATION("Cycle Duration: %s", "à¤šà¤•à¥à¤° à¤…à¤µà¤§à¤¿: %s"),
    
    DASHA_CALC_LOADING("Calculating %s Dasha timeline...", "%s à¤¦à¤¶à¤¾ à¤¸à¤®à¤¯à¤°à¥‡à¤–à¤¾ à¤—à¤£à¤¨à¤¾ à¤—à¤°à¥à¤¦à¥ˆ..."),
    DASHA_NO_CHART("No chart selected. Please select a birth chart to view dasha periods.", "à¤•à¥à¤¨à¥ˆ à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤šà¤¯à¤¨ à¤—à¤°à¤¿à¤à¤•à¥‹ à¤›à¥ˆà¤¨à¥¤ à¤¦à¤¶à¤¾ à¤…à¤µà¤§à¤¿à¤¹à¤°à¥‚ à¤¹à¥‡à¤°à¥à¤¨ à¤•à¥ƒà¤ªà¤¯à¤¾ à¤œà¤¨à¥à¤® à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤šà¤¯à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    DASHA_NOT_AVAILABLE("%s calculation not available for this chart.", "%s à¤—à¤£à¤¨à¤¾ à¤¯à¥‹ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤‰à¤ªà¤²à¤¬à¥à¤§ à¤›à¥ˆà¤¨à¥¤"),

    // ============================================
    // KALACHAKRA DASHA SYSTEM
    // ============================================
    KALACHAKRA_DASHA_TITLE("Kalachakra Dasha", "à¤•à¤¾à¤²à¤šà¤•à¥à¤° à¤¦à¤¶à¤¾"),
    KALACHAKRA_DASHA_SUBTITLE("Body-Soul Timing System", "à¤¦à¥‡à¤¹-à¤†à¤¤à¥à¤®à¤¾ à¤¸à¤®à¤¯ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€"),
    KALACHAKRA_DASHA_ABOUT("About Kalachakra Dasha", "à¤•à¤¾à¤²à¤šà¤•à¥à¤° à¤¦à¤¶à¤¾à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    KALACHAKRA_DASHA_ABOUT_DESC("Kalachakra Dasha is a highly respected Nakshatra-based timing system from BPHS, particularly useful for health predictions and spiritual transformation. It operates on the principle that body (Deha) and soul (Jeeva) follow different but related cycles. Savya (clockwise) and Apsavya (anti-clockwise) groups determine life's flow direction.", "à¤•à¤¾à¤²à¤šà¤•à¥à¤° à¤¦à¤¶à¤¾ BPHS à¤¬à¤¾à¤Ÿ à¤…à¤¤à¥à¤¯à¤¨à¥à¤¤ à¤¸à¤®à¥à¤®à¤¾à¤¨à¤¿à¤¤ à¤¨à¤•à¥à¤·à¤¤à¥à¤°-à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤¸à¤®à¤¯ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤¹à¥‹, à¤µà¤¿à¤¶à¥‡à¤· à¤—à¤°à¥€ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤­à¤µà¤¿à¤·à¥à¤¯à¤µà¤¾à¤£à¥€ à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤‰à¤ªà¤¯à¥‹à¤—à¥€à¥¤ à¤¯à¥‹ à¤¦à¥‡à¤¹ à¤° à¤†à¤¤à¥à¤®à¤¾à¤²à¥‡ à¤«à¤°à¤• à¤¤à¤° à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¿à¤¤ à¤šà¤•à¥à¤°à¤¹à¤°à¥‚ à¤ªà¤›à¥à¤¯à¤¾à¤‰à¤à¤›à¤¨à¥ à¤­à¤¨à¥à¤¨à¥‡ à¤¸à¤¿à¤¦à¥à¤§à¤¾à¤¨à¥à¤¤à¤®à¤¾ à¤•à¤¾à¤® à¤—à¤°à¥à¤›à¥¤ à¤¸à¤µà¥à¤¯ (à¤˜à¤¡à¥€à¤•à¥‹ à¤¦à¤¿à¤¶à¤¾à¤®à¤¾) à¤° à¤…à¤ªà¤¸à¤µà¥à¤¯ (à¤˜à¤¡à¥€à¤•à¥‹ à¤µà¤¿à¤ªà¤°à¥€à¤¤ à¤¦à¤¿à¤¶à¤¾à¤®à¤¾) à¤¸à¤®à¥‚à¤¹à¤¹à¤°à¥‚à¤²à¥‡ à¤œà¥€à¤µà¤¨à¤•à¥‹ à¤ªà¥à¤°à¤µà¤¾à¤¹ à¤¦à¤¿à¤¶à¤¾ à¤¨à¤¿à¤°à¥à¤§à¤¾à¤°à¤£ à¤—à¤°à¥à¤¦à¤›à¤¨à¥à¥¤"),
    KALACHAKRA_CURRENT("Current Period", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤…à¤µà¤§à¤¿"),
    KALACHAKRA_TIMELINE("Timeline", "à¤¸à¤®à¤¯à¤°à¥‡à¤–à¤¾"),
    KALACHAKRA_DEHA_JEEVA("Deha-Jeeva", "à¤¦à¥‡à¤¹-à¤œà¥€à¤µ"),
    KALACHAKRA_DEHA_JEEVA_TITLE("Deha-Jeeva Analysis", "à¤¦à¥‡à¤¹-à¤œà¥€à¤µ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    KALACHAKRA_DEHA_JEEVA_SUBTITLE("Body and Soul Relationship", "à¤¶à¤°à¥€à¤° à¤° à¤†à¤¤à¥à¤®à¤¾à¤•à¥‹ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§"),
    KALACHAKRA_DEHA("Deha", "à¤¦à¥‡à¤¹"),
    KALACHAKRA_JEEVA("Jeeva", "à¤œà¥€à¤µ"),
    KALACHAKRA_BODY("Body", "à¤¶à¤°à¥€à¤°"),
    KALACHAKRA_SOUL("Soul", "à¤†à¤¤à¥à¤®à¤¾"),
    KALACHAKRA_EFFECTS("Current Effects", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    KALACHAKRA_NAKSHATRA_GROUP("Nakshatra Group", "à¤¨à¤•à¥à¤·à¤¤à¥à¤° à¤¸à¤®à¥‚à¤¹"),
    KALACHAKRA_BIRTH_NAKSHATRA("Birth Nakshatra", "à¤œà¤¨à¥à¤® à¤¨à¤•à¥à¤·à¤¤à¥à¤°"),
    KALACHAKRA_PADA("Pada", "à¤ªà¤¾à¤¦"),
    KALACHAKRA_APPLICABILITY("System Applicability", "à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤ªà¥à¤°à¤¯à¥‹à¤œà¥à¤¯à¤¤à¤¾"),
    KALACHAKRA_HEALTH_INDICATOR("Health Outlook", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤•à¥‹à¤£"),
    KALACHAKRA_PARAMA_AYUSH("Parama Ayush Sign - Special longevity indicator", "à¤ªà¤°à¤® à¤†à¤¯à¥à¤· à¤°à¤¾à¤¶à¤¿ - à¤µà¤¿à¤¶à¥‡à¤· à¤¦à¥€à¤°à¥à¤˜à¤¾à¤¯à¥ à¤¸à¥‚à¤šà¤•"),
    KALACHAKRA_INTERPRETATION("Interpretation & Guidance", "à¤µà¥à¤¯à¤¾à¤–à¥à¤¯à¤¾ à¤° à¤®à¤¾à¤°à¥à¤—à¤¦à¤°à¥à¤¶à¤¨"),
    KALACHAKRA_GUIDANCE("Guidance", "à¤®à¤¾à¤°à¥à¤—à¤¦à¤°à¥à¤¶à¤¨"),
    KALACHAKRA_DEHA_ANALYSIS("Deha (Body) Analysis", "à¤¦à¥‡à¤¹ (à¤¶à¤°à¥€à¤°) à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    KALACHAKRA_JEEVA_ANALYSIS("Jeeva (Soul) Analysis", "à¤œà¥€à¤µ (à¤†à¤¤à¥à¤®à¤¾) à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    KALACHAKRA_DEHA_LORD("Deha Lord", "à¤¦à¥‡à¤¹ à¤¸à¥à¤µà¤¾à¤®à¥€"),
    KALACHAKRA_JEEVA_LORD("Jeeva Lord", "à¤œà¥€à¤µ à¤¸à¥à¤µà¤¾à¤®à¥€"),
    KALACHAKRA_STRENGTH("Strength", "à¤¬à¤²"),
    KALACHAKRA_RELATIONSHIP("Deha-Jeeva Relationship", "à¤¦à¥‡à¤¹-à¤œà¥€à¤µ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§"),
    KALACHAKRA_SAVYA("Savya (Direct)", "à¤¸à¤µà¥à¤¯ (à¤¸à¥€à¤§à¥‹)"),
    KALACHAKRA_APSAVYA("Apsavya (Retrograde)", "à¤…à¤ªà¤¸à¤µà¥à¤¯ (à¤‰à¤²à¥à¤Ÿà¥‹)"),
    KALACHAKRA_HEALTH_EXCELLENT("Excellent", "à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ"),
    KALACHAKRA_HEALTH_GOOD("Good", "à¤°à¤¾à¤®à¥à¤°à¥‹"),
    KALACHAKRA_HEALTH_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    KALACHAKRA_HEALTH_CHALLENGING("Challenging", "à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£"),
    KALACHAKRA_HEALTH_CRITICAL("Critical", "à¤—à¤®à¥à¤­à¥€à¤°"),
    KALACHAKRA_RELATIONSHIP_HARMONIOUS("Harmonious", "à¤¸à¤¾à¤®à¤žà¥à¤œà¤¸à¥à¤¯à¤ªà¥‚à¤°à¥à¤£"),
    KALACHAKRA_RELATIONSHIP_SUPPORTIVE("Supportive", "à¤¸à¤¹à¤¯à¥‹à¤—à¥€"),
    KALACHAKRA_RELATIONSHIP_NEUTRAL("Neutral", "à¤¤à¤Ÿà¤¸à¥à¤¥"),
    KALACHAKRA_RELATIONSHIP_CHALLENGING("Challenging", "à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£"),
    KALACHAKRA_RELATIONSHIP_TRANSFORMATIVE("Transformative", "à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨à¤•à¤¾à¤°à¥€"),

    // ============================================
    // COMMON SCREEN STRINGS
    // ============================================
    SCREEN_CALCULATING("Calculating analysis...", "à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤£à¤¨à¤¾ à¤—à¤°à¥à¤¦à¥ˆ..."),
    SCREEN_NO_DATA("No data available", "à¤•à¥à¤¨à¥ˆ à¤¡à¤¾à¤Ÿà¤¾ à¤‰à¤ªà¤²à¤¬à¥à¤§ à¤›à¥ˆà¤¨"),
    SCREEN_ERROR_CALCULATION("Error calculating results", "à¤ªà¤°à¤¿à¤£à¤¾à¤® à¤—à¤£à¤¨à¤¾à¤®à¤¾ à¤¤à¥à¤°à¥à¤Ÿà¤¿"),
    SCREEN_OVERVIEW("Overview", "à¤¸à¤¿à¤‚à¤¹à¤¾à¤µà¤²à¥‹à¤•à¤¨"),
    SCREEN_DETAILS("Details", "à¤µà¤¿à¤µà¤°à¤£à¤¹à¤°à¥‚"),
    SCREEN_TIMELINE("Timeline", "à¤¸à¤®à¤¯à¤°à¥‡à¤–à¤¾"),
    SCREEN_INTERPRETATION("Interpretation", "à¤µà¥à¤¯à¤¾à¤–à¥à¤¯à¤¾"),
    SCREEN_RECOMMENDATIONS("Recommendations", "à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸à¤¹à¤°à¥‚"),

    // ============================================
    // TARABALA AND CHANDRABALA
    // ============================================
    TARABALA_TITLE("Tarabala & Chandrabala", "à¤¤à¤¾à¤°à¤¾à¤¬à¤² à¤° à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤²"),
    TARABALA_SUBTITLE("Daily Strength Analysis", "à¤¦à¥ˆà¤¨à¤¿à¤• à¤¬à¤² à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    TARABALA_TODAY("Today's Tarabala", "à¤†à¤œà¤•à¥‹ à¤¤à¤¾à¤°à¤¾à¤¬à¤²"),
    TARABALA_WEEKLY("Weekly Forecast", "à¤¸à¤¾à¤ªà¥à¤¤à¤¾à¤¹à¤¿à¤• à¤ªà¥‚à¤°à¥à¤µà¤¾à¤¨à¥à¤®à¤¾à¤¨"),
    TARABALA_ALL_TARAS("All 27 Nakshatras", "à¤¸à¤¬à¥ˆ à¥¨à¥­ à¤¨à¤•à¥à¤·à¤¤à¥à¤°à¤¹à¤°à¥‚"),
    TARABALA_BIRTH_NAKSHATRA("Birth Nakshatra", "à¤œà¤¨à¥à¤® à¤¨à¤•à¥à¤·à¤¤à¥à¤°"),
    TARABALA_TRANSIT_NAKSHATRA("Transit Nakshatra", "à¤—à¥‹à¤šà¤° à¤¨à¤•à¥à¤·à¤¤à¥à¤°"),
    TARABALA_CYCLE("Cycle", "à¤šà¤•à¥à¤°"),
    TARABALA_FAVORABLE("Favorable", "à¤…à¤¨à¥à¤•à¥‚à¤²"),
    TARABALA_UNFAVORABLE("Unfavorable", "à¤ªà¥à¤°à¤¤à¤¿à¤•à¥‚à¤²"),
    TARABALA_DAILY_SCORE("Daily Strength Score", "à¤¦à¥ˆà¤¨à¤¿à¤• à¤¬à¤² à¤¸à¥à¤•à¥‹à¤°"),
    TARABALA_BEST_DAY("Best Day", "à¤‰à¤¤à¥à¤¤à¤® à¤¦à¤¿à¤¨"),
    TARABALA_WORST_DAY("Challenging Day", "à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£ à¤¦à¤¿à¤¨"),
    TARABALA_FAVORABLE_ACTIVITIES("Favorable Activities", "à¤…à¤¨à¥à¤•à¥‚à¤² à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚"),
    TARABALA_AVOID_ACTIVITIES("Activities to Avoid", "à¤Ÿà¤¾à¤¢à¤¾ à¤°à¤¹à¤¨à¥‡ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚"),
    TARABALA_GENERAL_ADVICE("General Advice", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤¸à¤²à¥à¤²à¤¾à¤¹"),
    TARABALA_WEEKLY_ADVICE("Weekly Guidance", "à¤¸à¤¾à¤ªà¥à¤¤à¤¾à¤¹à¤¿à¤• à¤®à¤¾à¤°à¥à¤—à¤¦à¤°à¥à¤¶à¤¨"),

    // Nine Taras
    TARA_JANMA("Janma", "à¤œà¤¨à¥à¤®"),
    TARA_SAMPAT("Sampat", "à¤¸à¤®à¥à¤ªà¤¤à¥"),
    TARA_VIPAT("Vipat", "à¤µà¤¿à¤ªà¤¤à¥"),
    TARA_KSHEMA("Kshema", "à¤•à¥à¤·à¥‡à¤®"),
    TARA_PRATYARI("Pratyari", "à¤ªà¥à¤°à¤¤à¥à¤¯à¤¾à¤°à¤¿"),
    TARA_SADHAKA("Sadhaka", "à¤¸à¤¾à¤§à¤•"),
    TARA_VADHA("Vadha", "à¤µà¤§"),
    TARA_MITRA("Mitra", "à¤®à¤¿à¤¤à¥à¤°"),
    TARA_PARAMA_MITRA("Parama Mitra", "à¤ªà¤°à¤® à¤®à¤¿à¤¤à¥à¤°"),

    // Tara Descriptions
    TARA_JANMA_DESC("Birth star day - rest and introspection recommended", "à¤œà¤¨à¥à¤® à¤¨à¤•à¥à¤·à¤¤à¥à¤° à¤¦à¤¿à¤¨ - à¤†à¤°à¤¾à¤® à¤° à¤†à¤¤à¥à¤®à¤šà¤¿à¤¨à¥à¤¤à¤¨ à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸"),
    TARA_SAMPAT_DESC("Wealth star - excellent for financial matters", "à¤§à¤¨ à¤¨à¤•à¥à¤·à¤¤à¥à¤° - à¤†à¤°à¥à¤¥à¤¿à¤• à¤®à¤¾à¤®à¤¿à¤²à¤¾à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ"),
    TARA_VIPAT_DESC("Danger star - avoid important activities", "à¤µà¤¿à¤ªà¤¤à¥ à¤¨à¤•à¥à¤·à¤¤à¥à¤° - à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚ à¤Ÿà¤¾à¤³à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    TARA_KSHEMA_DESC("Well-being star - favorable for health matters", "à¤•à¥à¤·à¥‡à¤® à¤¨à¤•à¥à¤·à¤¤à¥à¤° - à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤®à¤¾à¤®à¤¿à¤²à¤¾à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤…à¤¨à¥à¤•à¥‚à¤²"),
    TARA_PRATYARI_DESC("Obstacle star - expect minor hindrances", "à¤ªà¥à¤°à¤¤à¥à¤¯à¤¾à¤°à¤¿ à¤¨à¤•à¥à¤·à¤¤à¥à¤° - à¤¸à¤¾à¤¨à¤¾à¤¤à¤¿à¤¨à¤¾ à¤¬à¤¾à¤§à¤¾à¤¹à¤°à¥‚ à¤…à¤ªà¥‡à¤•à¥à¤·à¤¾ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    TARA_SADHAKA_DESC("Achievement star - good for completing goals", "à¤¸à¤¾à¤§à¤• à¤¨à¤•à¥à¤·à¤¤à¥à¤° - à¤²à¤•à¥à¤·à¥à¤¯ à¤ªà¥‚à¤°à¤¾ à¤—à¤°à¥à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤°à¤¾à¤®à¥à¤°à¥‹"),
    TARA_VADHA_DESC("Death star - highly inauspicious, maximum caution", "à¤µà¤§ à¤¨à¤•à¥à¤·à¤¤à¥à¤° - à¤…à¤¤à¥à¤¯à¤¨à¥à¤¤ à¤…à¤¶à¥à¤­, à¤…à¤§à¤¿à¤•à¤¤à¤® à¤¸à¤¾à¤µà¤§à¤¾à¤¨à¥€"),
    TARA_MITRA_DESC("Friend star - favorable for relationships", "à¤®à¤¿à¤¤à¥à¤° à¤¨à¤•à¥à¤·à¤¤à¥à¤° - à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤…à¤¨à¥à¤•à¥‚à¤²"),
    TARA_PARAMA_MITRA_DESC("Great friend star - most auspicious for everything", "à¤ªà¤°à¤® à¤®à¤¿à¤¤à¥à¤° à¤¨à¤•à¥à¤·à¤¤à¥à¤° - à¤¸à¤¬à¥ˆà¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤¶à¥à¤­"),

    // Chandrabala
    CHANDRABALA_TITLE("Chandrabala Analysis", "à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤² à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    CHANDRABALA_TRANSIT_MOON("Transit Moon Sign", "à¤—à¥‹à¤šà¤° à¤šà¤¨à¥à¤¦à¥à¤° à¤°à¤¾à¤¶à¤¿"),
    CHANDRABALA_NATAL_MOON("Natal Moon Sign", "à¤œà¤¨à¥à¤® à¤šà¤¨à¥à¤¦à¥à¤° à¤°à¤¾à¤¶à¤¿"),
    CHANDRABALA_HOUSE_FROM_MOON("House from Moon", "à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤¾à¤Ÿ à¤­à¤¾à¤µ"),
    CHANDRABALA_EXCELLENT("Excellent", "à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ"),
    CHANDRABALA_GOOD("Good", "à¤°à¤¾à¤®à¥à¤°à¥‹"),
    CHANDRABALA_NEUTRAL("Neutral", "à¤¤à¤Ÿà¤¸à¥à¤¥"),
    CHANDRABALA_WEAK("Weak", "à¤•à¤®à¤œà¥‹à¤°"),
    CHANDRABALA_UNFAVORABLE("Unfavorable", "à¤ªà¥à¤°à¤¤à¤¿à¤•à¥‚à¤²"),
    CHANDRABALA_SIGNIFICATIONS("House Significations", "à¤­à¤¾à¤µ à¤¸à¤‚à¤•à¥‡à¤¤à¤¹à¤°à¥‚"),

    // Combined Strength
    COMBINED_HIGHLY_FAVORABLE("Highly Favorable", "à¤…à¤¤à¥à¤¯à¤¨à¥à¤¤ à¤…à¤¨à¥à¤•à¥‚à¤²"),
    COMBINED_FAVORABLE("Favorable", "à¤…à¤¨à¥à¤•à¥‚à¤²"),
    COMBINED_MIXED("Mixed Results", "à¤®à¤¿à¤¶à¥à¤°à¤¿à¤¤ à¤ªà¤°à¤¿à¤£à¤¾à¤®"),
    COMBINED_CHALLENGING("Challenging", "à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£"),
    COMBINED_UNFAVORABLE("Unfavorable", "à¤ªà¥à¤°à¤¤à¤¿à¤•à¥‚à¤²"),

    // ============================================
    // DASHA SANDHI ANALYSIS
    // ============================================
    SANDHI_TITLE("Dasha Sandhi Analysis", "à¤¦à¤¶à¤¾ à¤¸à¤¨à¥à¤§à¤¿ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SANDHI_SUBTITLE("Period Junction Analysis", "à¤…à¤µà¤§à¤¿ à¤œà¥‹à¤¡ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    SANDHI_CURRENT("Current Sandhi", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤¸à¤¨à¥à¤§à¤¿"),
    SANDHI_UPCOMING("Upcoming Sandhis", "à¤†à¤—à¤¾à¤®à¥€ à¤¸à¤¨à¥à¤§à¤¿à¤¹à¤°à¥‚"),
    SANDHI_RECENT("Recent Sandhis", "à¤¹à¤¾à¤²à¥ˆà¤•à¤¾ à¤¸à¤¨à¥à¤§à¤¿à¤¹à¤°à¥‚"),
    SANDHI_CALENDAR("Sandhi Calendar", "à¤¸à¤¨à¥à¤§à¤¿ à¤•à¥à¤¯à¤¾à¤²à¥‡à¤¨à¥à¤¡à¤°"),
    SANDHI_VOLATILITY("Volatility Score", "à¤…à¤¸à¥à¤¥à¤¿à¤°à¤¤à¤¾ à¤¸à¥à¤•à¥‹à¤°"),
    SANDHI_TRANSITION("Transition", "à¤¸à¤‚à¤•à¥à¤°à¤®à¤£"),
    SANDHI_FROM_PLANET("From", "à¤¬à¤¾à¤Ÿ"),
    SANDHI_TO_PLANET("To", "à¤®à¤¾"),
    SANDHI_DAYS_UNTIL("Days Until Transition", "à¤¸à¤‚à¤•à¥à¤°à¤®à¤£à¤¸à¤®à¥à¤® à¤¦à¤¿à¤¨"),
    SANDHI_PROGRESS("Sandhi Progress", "à¤¸à¤¨à¥à¤§à¤¿ à¤ªà¥à¤°à¤—à¤¤à¤¿"),
    SANDHI_KEY_DATES("Key Dates", "à¤®à¥à¤–à¥à¤¯ à¤®à¤¿à¤¤à¤¿à¤¹à¤°à¥‚"),
    SANDHI_PREDICTIONS("Predictions", "à¤­à¤µà¤¿à¤·à¥à¤¯à¤µà¤¾à¤£à¥€à¤¹à¤°à¥‚"),
    SANDHI_CAREER_EFFECTS("Career Effects", "à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤° à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    SANDHI_HEALTH_CONCERNS("Health Considerations", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤µà¤¿à¤šà¤¾à¤°à¤£à¤¾à¤¹à¤°à¥‚"),
    SANDHI_RELATIONSHIP_IMPACT("Relationship Impact", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤ªà¥à¤°à¤­à¤¾à¤µ"),
    SANDHI_FINANCIAL_TREND("Financial Trend", "à¤†à¤°à¥à¤¥à¤¿à¤• à¤ªà¥à¤°à¤µà¥ƒà¤¤à¥à¤¤à¤¿"),
    SANDHI_SPIRITUAL("Spiritual Opportunities", "à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤…à¤µà¤¸à¤°à¤¹à¤°à¥‚"),
    SANDHI_AFFECTED_AREAS("Affected Life Areas", "à¤ªà¥à¤°à¤­à¤¾à¤µà¤¿à¤¤ à¤œà¥€à¤µà¤¨ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚"),
    SANDHI_GENERAL_GUIDANCE("General Guidance", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤®à¤¾à¤°à¥à¤—à¤¦à¤°à¥à¤¶à¤¨"),

    // Sandhi Intensity
    SANDHI_INTENSITY_CRITICAL("Critical", "à¤—à¤®à¥à¤­à¥€à¤°"),
    SANDHI_INTENSITY_HIGH("High", "à¤‰à¤šà¥à¤š"),
    SANDHI_INTENSITY_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    SANDHI_INTENSITY_MILD("Mild", "à¤¹à¤²à¥à¤•à¤¾"),
    SANDHI_INTENSITY_MINIMAL("Minimal", "à¤¨à¥à¤¯à¥‚à¤¨à¤¤à¤®"),

    // Transition Types
    TRANSITION_FRIEND_FRIEND("Friend to Friend", "à¤®à¤¿à¤¤à¥à¤°à¤¬à¤¾à¤Ÿ à¤®à¤¿à¤¤à¥à¤°à¤®à¤¾"),
    TRANSITION_FRIEND_NEUTRAL("Friend to Neutral", "à¤®à¤¿à¤¤à¥à¤°à¤¬à¤¾à¤Ÿ à¤¤à¤Ÿà¤¸à¥à¤¥à¤®à¤¾"),
    TRANSITION_FRIEND_ENEMY("Friend to Enemy", "à¤®à¤¿à¤¤à¥à¤°à¤¬à¤¾à¤Ÿ à¤¶à¤¤à¥à¤°à¥à¤®à¤¾"),
    TRANSITION_NEUTRAL_FRIEND("Neutral to Friend", "à¤¤à¤Ÿà¤¸à¥à¤¥à¤¬à¤¾à¤Ÿ à¤®à¤¿à¤¤à¥à¤°à¤®à¤¾"),
    TRANSITION_NEUTRAL_NEUTRAL("Neutral to Neutral", "à¤¤à¤Ÿà¤¸à¥à¤¥à¤¬à¤¾à¤Ÿ à¤¤à¤Ÿà¤¸à¥à¤¥à¤®à¤¾"),
    TRANSITION_NEUTRAL_ENEMY("Neutral to Enemy", "à¤¤à¤Ÿà¤¸à¥à¤¥à¤¬à¤¾à¤Ÿ à¤¶à¤¤à¥à¤°à¥à¤®à¤¾"),
    TRANSITION_ENEMY_FRIEND("Enemy to Friend", "à¤¶à¤¤à¥à¤°à¥à¤¬à¤¾à¤Ÿ à¤®à¤¿à¤¤à¥à¤°à¤®à¤¾"),
    TRANSITION_ENEMY_NEUTRAL("Enemy to Neutral", "à¤¶à¤¤à¥à¤°à¥à¤¬à¤¾à¤Ÿ à¤¤à¤Ÿà¤¸à¥à¤¥à¤®à¤¾"),
    TRANSITION_ENEMY_ENEMY("Enemy to Enemy", "à¤¶à¤¤à¥à¤°à¥à¤¬à¤¾à¤Ÿ à¤¶à¤¤à¥à¤°à¥à¤®à¤¾"),

    // ============================================
    // GOCHARA VEDHA ANALYSIS
    // ============================================
    VEDHA_TITLE("Gochara Vedha Analysis", "à¤—à¥‹à¤šà¤° à¤µà¥‡à¤§ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    VEDHA_SUBTITLE("Transit Obstruction Analysis", "à¤—à¥‹à¤šà¤° à¤…à¤µà¤°à¥‹à¤§ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    VEDHA_OVERVIEW("Transit Overview", "à¤—à¥‹à¤šà¤° à¤¸à¤¿à¤‚à¤¹à¤¾à¤µà¤²à¥‹à¤•à¤¨"),
    VEDHA_ACTIVE("Active Vedhas", "à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤µà¥‡à¤§à¤¹à¤°à¥‚"),
    VEDHA_PLANET_TRANSITS("Planetary Transits", "à¤—à¥à¤°à¤¹ à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚"),
    VEDHA_OVERALL_SCORE("Overall Transit Score", "à¤¸à¤®à¤—à¥à¤° à¤—à¥‹à¤šà¤° à¤¸à¥à¤•à¥‹à¤°"),
    VEDHA_FAVORABLE_COUNT("Favorable Transits", "à¤…à¤¨à¥à¤•à¥‚à¤² à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚"),
    VEDHA_OBSTRUCTED_COUNT("Obstructed Transits", "à¤…à¤µà¤°à¥à¤¦à¥à¤§ à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚"),
    VEDHA_KEY_INSIGHTS("Key Insights", "à¤®à¥à¤–à¥à¤¯ à¤…à¤¨à¥à¤¤à¤°à¥à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚"),
    VEDHA_OBSTRUCTED_BY("Obstructed by", "à¤¦à¥à¤µà¤¾à¤°à¤¾ à¤…à¤µà¤°à¥à¤¦à¥à¤§"),
    VEDHA_LOST_BENEFITS("Lost Benefits", "à¤—à¥à¤®à¤¾à¤à¤•à¥‹ à¤«à¤¾à¤‡à¤¦à¤¾à¤¹à¤°à¥‚"),

    // Vedha Severity
    VEDHA_COMPLETE("Complete Obstruction", "à¤ªà¥‚à¤°à¥à¤£ à¤…à¤µà¤°à¥‹à¤§"),
    VEDHA_STRONG("Strong Obstruction", "à¤¬à¤²à¤¿à¤¯à¥‹ à¤…à¤µà¤°à¥‹à¤§"),
    VEDHA_MODERATE("Moderate Obstruction", "à¤®à¤§à¥à¤¯à¤® à¤…à¤µà¤°à¥‹à¤§"),
    VEDHA_PARTIAL("Partial Obstruction", "à¤†à¤‚à¤¶à¤¿à¤• à¤…à¤µà¤°à¥‹à¤§"),
    VEDHA_NONE("No Obstruction", "à¤•à¥à¤¨à¥ˆ à¤…à¤µà¤°à¥‹à¤§ à¤›à¥ˆà¤¨"),

    // Transit Effectiveness
    TRANSIT_EXCELLENT("Excellent", "à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ"),
    TRANSIT_GOOD("Good", "à¤°à¤¾à¤®à¥à¤°à¥‹"),
    TRANSIT_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    TRANSIT_WEAK("Weak", "à¤•à¤®à¤œà¥‹à¤°"),
    TRANSIT_NULLIFIED("Nullified by Vedha", "à¤µà¥‡à¤§à¤¦à¥à¤µà¤¾à¤°à¤¾ à¤°à¤¦à¥à¤¦"),
    TRANSIT_UNFAVORABLE("Unfavorable Transit", "à¤ªà¥à¤°à¤¤à¤¿à¤•à¥‚à¤² à¤—à¥‹à¤šà¤°"),

    // ============================================
    // KEMADRUMA YOGA ANALYSIS
    // ============================================
    KEMADRUMA_TITLE("Kemadruma Yoga Analysis", "à¤•à¥‡à¤®à¤¦à¥à¤°à¥à¤® à¤¯à¥‹à¤— à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    KEMADRUMA_SUBTITLE("Moon Support Analysis", "à¤šà¤¨à¥à¤¦à¥à¤° à¤¸à¤®à¤°à¥à¤¥à¤¨ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    KEMADRUMA_MOON_ANALYSIS("Moon Analysis", "à¤šà¤¨à¥à¤¦à¥à¤° à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    KEMADRUMA_FORMATION("Formation Details", "à¤—à¤ à¤¨ à¤µà¤¿à¤µà¤°à¤£"),
    KEMADRUMA_CANCELLATIONS("Cancellation Factors", "à¤°à¤¦à¥à¤¦à¥€à¤•à¤°à¤£ à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚"),
    KEMADRUMA_EMOTIONAL("Emotional Impact", "à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤ªà¥à¤°à¤­à¤¾à¤µ"),
    KEMADRUMA_FINANCIAL("Financial Impact", "à¤†à¤°à¥à¤¥à¤¿à¤• à¤ªà¥à¤°à¤­à¤¾à¤µ"),
    KEMADRUMA_SOCIAL("Social Impact", "à¤¸à¤¾à¤®à¤¾à¤œà¤¿à¤• à¤ªà¥à¤°à¤­à¤¾à¤µ"),
    KEMADRUMA_ACTIVATION("Activation Periods", "à¤¸à¤•à¥à¤°à¤¿à¤¯à¤¤à¤¾ à¤…à¤µà¤§à¤¿à¤¹à¤°à¥‚"),
    KEMADRUMA_MENTAL_PEACE("Mental Peace", "à¤®à¤¾à¤¨à¤¸à¤¿à¤• à¤¶à¤¾à¤¨à¥à¤¤à¤¿"),
    KEMADRUMA_STABILITY("Emotional Stability", "à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤¸à¥à¤¥à¤¿à¤°à¤¤à¤¾"),
    KEMADRUMA_CONFIDENCE("Confidence Level", "à¤†à¤¤à¥à¤®à¤µà¤¿à¤¶à¥à¤µà¤¾à¤¸ à¤¸à¥à¤¤à¤°"),
    KEMADRUMA_ANXIETY("Anxiety Tendency", "à¤šà¤¿à¤¨à¥à¤¤à¤¾ à¤ªà¥à¤°à¤µà¥ƒà¤¤à¥à¤¤à¤¿"),
    KEMADRUMA_DEPRESSION_RISK("Depression Risk", "à¤…à¤µà¤¸à¤¾à¤¦ à¤œà¥‹à¤–à¤¿à¤®"),
    KEMADRUMA_WEALTH_RETENTION("Wealth Retention", "à¤§à¤¨ à¤¸à¤‚à¤°à¤•à¥à¤·à¤£"),
    KEMADRUMA_FAMILY_SUPPORT("Family Support", "à¤ªà¤¾à¤°à¤¿à¤µà¤¾à¤°à¤¿à¤• à¤¸à¤®à¤°à¥à¤¥à¤¨"),
    KEMADRUMA_FRIENDSHIP("Friendship Quality", "à¤®à¤¿à¤¤à¥à¤°à¤¤à¤¾ à¤—à¥à¤£à¤¸à¥à¤¤à¤°"),
    KEMADRUMA_ISOLATION("Isolation Tendency", "à¤à¤•à¤¾à¤¨à¥à¤¤ à¤ªà¥à¤°à¤µà¥ƒà¤¤à¥à¤¤à¤¿"),

    // Kemadruma Status
    KEMADRUMA_NOT_PRESENT("Not Present", "à¤‰à¤ªà¤¸à¥à¤¥à¤¿à¤¤ à¤›à¥ˆà¤¨"),
    KEMADRUMA_FULLY_CANCELLED("Fully Cancelled", "à¤ªà¥‚à¤°à¥à¤£ à¤°à¥‚à¤ªà¤®à¤¾ à¤°à¤¦à¥à¤¦"),
    KEMADRUMA_MOSTLY_CANCELLED("Mostly Cancelled", "à¤ªà¥à¤°à¤¾à¤¯à¤ƒ à¤°à¤¦à¥à¤¦"),
    KEMADRUMA_PARTIALLY_CANCELLED("Partially Cancelled", "à¤†à¤‚à¤¶à¤¿à¤• à¤°à¥‚à¤ªà¤®à¤¾ à¤°à¤¦à¥à¤¦"),
    KEMADRUMA_WEAKLY_CANCELLED("Weakly Cancelled", "à¤•à¤®à¤œà¥‹à¤° à¤°à¥‚à¤ªà¤®à¤¾ à¤°à¤¦à¥à¤¦"),
    KEMADRUMA_ACTIVE_MODERATE("Active (Moderate)", "à¤¸à¤•à¥à¤°à¤¿à¤¯ (à¤®à¤§à¥à¤¯à¤®)"),
    KEMADRUMA_ACTIVE_SEVERE("Active (Severe)", "à¤¸à¤•à¥à¤°à¤¿à¤¯ (à¤—à¤®à¥à¤­à¥€à¤°)"),

    // Bhanga (Cancellation) Types
    BHANGA_KENDRA_MOON("Planets in Kendra from Moon", "à¤šà¤¨à¥à¤¦à¥à¤°à¤¬à¤¾à¤Ÿ à¤•à¥‡à¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    BHANGA_KENDRA_LAGNA("Planets in Kendra from Lagna", "à¤²à¤—à¥à¤¨à¤¬à¤¾à¤Ÿ à¤•à¥‡à¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚"),
    BHANGA_MOON_KENDRA("Moon in Kendra from Lagna", "à¤²à¤—à¥à¤¨à¤¬à¤¾à¤Ÿ à¤•à¥‡à¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤šà¤¨à¥à¤¦à¥à¤°"),
    BHANGA_BENEFIC_ASPECT("Benefic planet aspects Moon", "à¤¶à¥à¤­ à¤—à¥à¤°à¤¹à¤²à¥‡ à¤šà¤¨à¥à¤¦à¥à¤°à¤²à¤¾à¤ˆ à¤¦à¥‡à¤–à¥à¤›"),
    BHANGA_BENEFIC_CONJUNCTION("Benefic planet conjuncts Moon", "à¤¶à¥à¤­ à¤—à¥à¤°à¤¹ à¤šà¤¨à¥à¤¦à¥à¤°à¤¸à¤à¤— à¤¯à¥à¤¤à¤¿"),
    BHANGA_MOON_EXALTED("Moon in exaltation", "à¤šà¤¨à¥à¤¦à¥à¤° à¤‰à¤šà¥à¤šà¤®à¤¾"),
    BHANGA_MOON_OWN("Moon in own sign", "à¤šà¤¨à¥à¤¦à¥à¤° à¤¸à¥à¤µà¤°à¤¾à¤¶à¤¿à¤®à¤¾"),
    BHANGA_MOON_FRIEND("Moon in friendly sign", "à¤šà¤¨à¥à¤¦à¥à¤° à¤®à¤¿à¤¤à¥à¤° à¤°à¤¾à¤¶à¤¿à¤®à¤¾"),
    BHANGA_FULL_MOON("Full or bright Moon", "à¤ªà¥‚à¤°à¥à¤£ à¤µà¤¾ à¤‰à¤œà¥à¤¯à¤¾à¤²à¥‹ à¤šà¤¨à¥à¤¦à¥à¤°"),
    BHANGA_ANGULAR_MOON("Moon in angular house", "à¤•à¥‹à¤£à¥€à¤¯ à¤­à¤¾à¤µà¤®à¤¾ à¤šà¤¨à¥à¤¦à¥à¤°"),
    BHANGA_STRONG_DISPOSITOR("Strong dispositor", "à¤¬à¤²à¤¿à¤¯à¥‹ à¤°à¤¾à¤¶à¥€à¤¶"),
    BHANGA_JUPITER_ASPECT("Jupiter aspects Moon", "à¤¬à¥ƒà¤¹à¤¸à¥à¤ªà¤¤à¤¿à¤²à¥‡ à¤šà¤¨à¥à¤¦à¥à¤°à¤²à¤¾à¤ˆ à¤¦à¥‡à¤–à¥à¤›"),
    BHANGA_VENUS_ASPECT("Venus aspects Moon", "à¤¶à¥à¤•à¥à¤°à¤²à¥‡ à¤šà¤¨à¥à¤¦à¥à¤°à¤²à¤¾à¤ˆ à¤¦à¥‡à¤–à¥à¤›"),

    // Moon Brightness
    MOON_BRIGHTNESS_FULL("Full Moon", "à¤ªà¥‚à¤°à¥à¤£à¤¿à¤®à¤¾"),
    MOON_BRIGHTNESS_BRIGHT("Bright Moon", "à¤‰à¤œà¥à¤¯à¤¾à¤²à¥‹ à¤šà¤¨à¥à¤¦à¥à¤°"),
    MOON_BRIGHTNESS_AVERAGE("Average Moon", "à¤”à¤¸à¤¤ à¤šà¤¨à¥à¤¦à¥à¤°"),
    MOON_BRIGHTNESS_DIM("Dim Moon", "à¤§à¤®à¤¿à¤²à¥‹ à¤šà¤¨à¥à¤¦à¥à¤°"),
    MOON_BRIGHTNESS_NEW("New Moon", "à¤…à¤®à¤¾à¤µà¤¸à¥à¤¯à¤¾"),

    // Lunar Paksha
    PAKSHA_SHUKLA("Shukla Paksha", "à¤¶à¥à¤•à¥à¤² à¤ªà¤•à¥à¤·"),
    PAKSHA_KRISHNA("Krishna Paksha", "à¤•à¥ƒà¤·à¥à¤£ à¤ªà¤•à¥à¤·"),

    // Impact Levels
    IMPACT_SEVERE("Severe", "à¤—à¤®à¥à¤­à¥€à¤°"),
    IMPACT_HIGH("High", "à¤‰à¤šà¥à¤š"),
    IMPACT_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    IMPACT_MILD("Mild", "à¤¹à¤²à¥à¤•à¤¾"),
    IMPACT_MINIMAL("Minimal", "à¤¨à¥à¤¯à¥‚à¤¨à¤¤à¤®"),
    IMPACT_POSITIVE("Positive", "à¤¸à¤•à¤¾à¤°à¤¾à¤¤à¥à¤®à¤•"),

    // ============================================
    // CHAT & STORMY AI STRINGS
    // ============================================
    CHAT_NEW("New Chat", "à¤¨à¤¯à¤¾à¤ à¤šà¥à¤¯à¤¾à¤Ÿ"),
    CHAT_DELETE("Delete Chat", "à¤šà¥à¤¯à¤¾à¤Ÿ à¤®à¥‡à¤Ÿà¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    CHAT_DELETE_CONFIRM("Are you sure you want to delete \"%s\"? This cannot be undone.", "à¤•à¥‡ à¤¤à¤ªà¤¾à¤ˆà¤‚ \"%s\" à¤®à¥‡à¤Ÿà¤¾à¤‰à¤¨ à¤¨à¤¿à¤¶à¥à¤šà¤¿à¤¤ à¤¹à¥à¤¨à¥à¤¹à¥à¤¨à¥à¤›? à¤¯à¥‹ à¤ªà¥‚à¤°à¥à¤µà¤µà¤¤ à¤—à¤°à¥à¤¨ à¤¸à¤•à¤¿à¤à¤¦à¥ˆà¤¨à¥¤"),
    CHAT_DELETE_BTN("Delete", "à¤®à¥‡à¤Ÿà¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    CHAT_CANCEL_BTN("Cancel", "à¤°à¤¦à¥à¤¦ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    CHAT_ARCHIVE("Archive", "à¤¸à¤‚à¤—à¥à¤°à¤¹"),
    CHAT_MORE_OPTIONS("More options", "à¤¥à¤ª à¤µà¤¿à¤•à¤²à¥à¤ªà¤¹à¤°à¥‚"),
    CHAT_MESSAGES_COUNT("%d messages", "%d à¤¸à¤¨à¥à¤¦à¥‡à¤¶à¤¹à¤°à¥‚"),
    CHAT_JUST_NOW("Just now", "à¤…à¤¹à¤¿à¤²à¥‡ à¤­à¤°à¥à¤–à¤°à¥ˆ"),
    CHAT_MINUTES_AGO("%dm ago", "%dà¤®à¤¿. à¤ªà¤¹à¤¿à¤²à¥‡"),
    CHAT_HOURS_AGO("%dh ago", "%d à¤˜à¤£à¥à¤Ÿà¤¾ à¤ªà¤¹à¤¿à¤²à¥‡"),
    CHAT_DAYS_AGO("%dd ago", "%d à¤¦à¤¿à¤¨ à¤ªà¤¹à¤¿à¤²à¥‡"),

    // Stormy AI Welcome
    STORMY_TITLE("Stormy", "à¤¸à¥à¤Ÿà¤°à¥à¤®à¥€"),
    STORMY_MEET("Meet Stormy", "à¤¸à¥à¤Ÿà¤°à¥à¤®à¥€à¤²à¤¾à¤ˆ à¤­à¥‡à¤Ÿà¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    STORMY_SUBTITLE("Your Vedic astrology AI assistant", "à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤µà¥ˆà¤¦à¤¿à¤• à¤œà¥à¤¯à¥‹à¤¤à¤¿à¤· à¤à¤†à¤ˆ à¤¸à¤¹à¤¾à¤¯à¤•"),
    STORMY_INTRO("Ask about your birth chart, planetary periods, transits, compatibility, remedies, and more.", "à¤†à¤«à¥à¤¨à¥‹ à¤œà¤¨à¥à¤® à¤•à¥à¤£à¥à¤¡à¤²à¥€, à¤—à¥à¤°à¤¹ à¤…à¤µà¤§à¤¿, à¤—à¥‹à¤šà¤°, à¤…à¤¨à¥à¤•à¥‚à¤²à¤¤à¤¾, à¤‰à¤ªà¤¾à¤¯ à¤° à¤¥à¤ª à¤¬à¤¾à¤°à¥‡à¤®à¤¾ à¤¸à¥‹à¤§à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    STORMY_HELLO("Hello! I'm Stormy", "à¤¨à¤®à¤¸à¥à¤¤à¥‡! à¤® à¤¸à¥à¤Ÿà¤°à¥à¤®à¥€ à¤¹à¥à¤"),
    STORMY_HELLO_DESC("Your Vedic astrology assistant. Ask me anything about your birth chart, planetary periods, transits, compatibility, or remedies.", "à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤µà¥ˆà¤¦à¤¿à¤• à¤œà¥à¤¯à¥‹à¤¤à¤¿à¤· à¤¸à¤¹à¤¾à¤¯à¤•à¥¤ à¤†à¤«à¥à¤¨à¥‹ à¤œà¤¨à¥à¤® à¤•à¥à¤£à¥à¤¡à¤²à¥€, à¤—à¥à¤°à¤¹ à¤…à¤µà¤§à¤¿, à¤—à¥‹à¤šà¤°, à¤…à¤¨à¥à¤•à¥‚à¤²à¤¤à¤¾, à¤µà¤¾ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾ à¤®à¤²à¤¾à¤ˆ à¤•à¥‡à¤¹à¥€ à¤ªà¤¨à¤¿ à¤¸à¥‹à¤§à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    STORMY_START_CHAT("Start New Chat", "à¤¨à¤¯à¤¾à¤ à¤šà¥à¤¯à¤¾à¤Ÿ à¤¸à¥à¤°à¥ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    STORMY_CONFIGURE_MODELS("Configure AI Models", "à¤à¤†à¤ˆ à¤®à¥‹à¤¡à¥‡à¤²à¤¹à¤°à¥‚ à¤•à¤¨à¥à¤«à¤¿à¤—à¤° à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    STORMY_ENABLE_MODELS("Enable AI models to start chatting", "à¤šà¥à¤¯à¤¾à¤Ÿ à¤¸à¥à¤°à¥ à¤—à¤°à¥à¤¨ à¤à¤†à¤ˆ à¤®à¥‹à¤¡à¥‡à¤²à¤¹à¤°à¥‚ à¤¸à¤•à¥à¤·à¤® à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    STORMY_ASK_PLACEHOLDER("Ask Stormy...", "à¤¸à¥à¤Ÿà¤°à¥à¤®à¥€à¤²à¤¾à¤ˆ à¤¸à¥‹à¤§à¥à¤¨à¥à¤¹à¥‹à¤¸à¥..."),

    // Stormy Status Messages
    STORMY_THINKING("Stormy is thinking...", "à¤¸à¥à¤Ÿà¤°à¥à¤®à¥€à¤²à¥‡ à¤¸à¥‹à¤šà¥à¤¦à¥ˆà¤›..."),
    STORMY_REASONING("Stormy is reasoning...", "à¤¸à¥à¤Ÿà¤°à¥à¤®à¥€à¤²à¥‡ à¤¤à¤°à¥à¤• à¤—à¤°à¥à¤¦à¥ˆà¤›..."),
    STORMY_TYPING("Stormy is typing...", "à¤¸à¥à¤Ÿà¤°à¥à¤®à¥€à¤²à¥‡ à¤Ÿà¤¾à¤‡à¤ª à¤—à¤°à¥à¤¦à¥ˆà¤›..."),
    STORMY_CALLING_TOOL("Calling %s...", "%s à¤•à¤² à¤—à¤°à¥à¤¦à¥ˆà¤›..."),
    STORMY_USING_TOOLS("Using tools: %s", "à¤‰à¤ªà¤•à¤°à¤£à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¥à¤¦à¥ˆà¤›: %s"),

    // Chat Suggestions
    CHAT_SUGGESTION_DASHA("What's my current dasha period?", "à¤®à¥‡à¤°à¥‹ à¤¹à¤¾à¤²à¤•à¥‹ à¤¦à¤¶à¤¾ à¤…à¤µà¤§à¤¿ à¤•à¥‡ à¤¹à¥‹?"),
    CHAT_SUGGESTION_CHART("Analyze my birth chart", "à¤®à¥‡à¤°à¥‹ à¤œà¤¨à¥à¤® à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    CHAT_SUGGESTION_YOGAS("What yogas are present in my chart?", "à¤®à¥‡à¤°à¥‹ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾ à¤•à¥à¤¨ à¤¯à¥‹à¤—à¤¹à¤°à¥‚ à¤›à¤¨à¥?"),

    // Chat Actions
    CHAT_CLEAR("Clear Chat", "à¤šà¥à¤¯à¤¾à¤Ÿ à¤–à¤¾à¤²à¥€ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    CHAT_CLEAR_CONFIRM("Are you sure you want to clear all messages in this conversation?", "à¤•à¥‡ à¤¤à¤ªà¤¾à¤ˆà¤‚ à¤¯à¤¸ à¤•à¥à¤°à¤¾à¤•à¤¾à¤¨à¥€à¤•à¤¾ à¤¸à¤¬à¥ˆ à¤¸à¤¨à¥à¤¦à¥‡à¤¶à¤¹à¤°à¥‚ à¤¹à¤Ÿà¤¾à¤‰à¤¨ à¤¨à¤¿à¤¶à¥à¤šà¤¿à¤¤ à¤¹à¥à¤¨à¥à¤¹à¥à¤¨à¥à¤›?"),
    CHAT_CLEAR_BTN("Clear", "à¤–à¤¾à¤²à¥€ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    CHAT_STOP("Stop", "à¤°à¥‹à¤•à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    CHAT_SEND("Send", "à¤ªà¤ à¤¾à¤‰à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    CHAT_BACK("Back", "à¤ªà¤›à¤¾à¤¡à¤¿"),
    CHAT_CHANGE_MODEL("Change model", "à¤®à¥‹à¤¡à¥‡à¤² à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    CHAT_MODEL_OPTIONS("Model options", "à¤®à¥‹à¤¡à¥‡à¤² à¤µà¤¿à¤•à¤²à¥à¤ªà¤¹à¤°à¥‚"),

    // Model Options
    MODEL_OPTIONS_TITLE("Model Options", "à¤®à¥‹à¤¡à¥‡à¤² à¤µà¤¿à¤•à¤²à¥à¤ªà¤¹à¤°à¥‚"),
    MODEL_THINKING_MODE("Thinking Mode", "à¤¸à¥‹à¤šà¥à¤¨à¥‡ à¤®à¥‹à¤¡"),
    MODEL_THINKING_DESC("Extended reasoning before answering", "à¤œà¤µà¤¾à¤« à¤¦à¤¿à¤¨à¥ à¤…à¤˜à¤¿ à¤µà¤¿à¤¸à¥à¤¤à¤¾à¤°à¤¿à¤¤ à¤¤à¤°à¥à¤•"),
    MODEL_WEB_SEARCH("Web Search", "à¤µà¥‡à¤¬ à¤–à¥‹à¤œ"),
    MODEL_WEB_SEARCH_DESC("Search the web for current information", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤œà¤¾à¤¨à¤•à¤¾à¤°à¥€à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤µà¥‡à¤¬ à¤–à¥‹à¤œà¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    MODEL_DONE("Done", "à¤¸à¤®à¥à¤ªà¤¨à¥à¤¨"),

    // Model Selector
    MODEL_SELECT_TITLE("Select AI Model", "à¤à¤†à¤ˆ à¤®à¥‹à¤¡à¥‡à¤² à¤›à¤¾à¤¨à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    MODEL_AI_LABEL("AI Model", "à¤à¤†à¤ˆ à¤®à¥‹à¤¡à¥‡à¤²"),
    MODEL_SELECT_PROMPT("Select a model", "à¤®à¥‹à¤¡à¥‡à¤² à¤›à¤¾à¤¨à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    MODEL_NONE_AVAILABLE("No models available", "à¤•à¥à¤¨à¥ˆ à¤®à¥‹à¤¡à¥‡à¤² à¤‰à¤ªà¤²à¤¬à¥à¤§ à¤›à¥ˆà¤¨"),
    MODEL_CONFIGURE("Configure Models", "à¤®à¥‹à¤¡à¥‡à¤²à¤¹à¤°à¥‚ à¤•à¤¨à¥à¤«à¤¿à¤—à¤° à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    MODEL_MANAGE("Manage AI Models", "à¤à¤†à¤ˆ à¤®à¥‹à¤¡à¥‡à¤²à¤¹à¤°à¥‚ à¤µà¥à¤¯à¤µà¤¸à¥à¤¥à¤¾à¤ªà¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),

    // ============================================
    // DIVISIONAL CHARTS - HORA (D-2) WEALTH
    // ============================================
    HORA_SUN_TITLE("Sun Hora - Self-Earned Wealth", "à¤¸à¥‚à¤°à¥à¤¯ à¤¹à¥‹à¤°à¤¾ - à¤¸à¥à¤µ-à¤…à¤°à¥à¤œà¤¿à¤¤ à¤§à¤¨"),
    HORA_SUN_DESC("These planets indicate potential for wealth through your own efforts", "à¤¯à¥€ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤²à¥‡ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤†à¤«à¥à¤¨à¥ˆ à¤ªà¥à¤°à¤¯à¤¾à¤¸à¤¬à¤¾à¤Ÿ à¤§à¤¨à¤•à¥‹ à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾ à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤›à¤¨à¥"),
    HORA_MOON_TITLE("Moon Hora - Inherited/Liquid Wealth", "à¤šà¤¨à¥à¤¦à¥à¤° à¤¹à¥‹à¤°à¤¾ - à¤µà¤¿à¤°à¤¾à¤¸à¤¤/à¤¤à¤°à¤² à¤§à¤¨"),
    HORA_MOON_DESC("These planets indicate potential for inherited or liquid assets", "à¤¯à¥€ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤²à¥‡ à¤µà¤¿à¤°à¤¾à¤¸à¤¤ à¤µà¤¾ à¤¤à¤°à¤² à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿à¤•à¥‹ à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾ à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤›à¤¨à¥"),
    HORA_WEALTH_SOURCES("Wealth Sources", "à¤§à¤¨à¤•à¤¾ à¤¸à¥à¤°à¥‹à¤¤à¤¹à¤°à¥‚"),
    HORA_POTENTIAL("Potential", "à¤¸à¤®à¥à¤­à¤¾à¤µà¥à¤¯à¤¤à¤¾"),
    HORA_POTENTIAL_EXCEPTIONAL("Exceptional", "à¤…à¤¸à¤¾à¤§à¤¾à¤°à¤£"),
    HORA_POTENTIAL_HIGH("High", "à¤‰à¤šà¥à¤š"),
    HORA_POTENTIAL_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    HORA_POTENTIAL_AVERAGE("Average", "à¤”à¤¸à¤¤"),
    HORA_POTENTIAL_NEEDS_EFFORT("Needs Effort", "à¤ªà¥à¤°à¤¯à¤¾à¤¸ à¤†à¤µà¤¶à¥à¤¯à¤•"),
    HORA_SELF_EARNED("Self-Earned", "à¤¸à¥à¤µ-à¤…à¤°à¥à¤œà¤¿à¤¤"),
    HORA_INHERITED("Inherited/Liquid", "à¤µà¤¿à¤°à¤¾à¤¸à¤¤/à¤¤à¤°à¤²"),

    // ============================================
    // DIVISIONAL CHARTS - DREKKANA (D-3) SIBLINGS
    // ============================================
    DREKKANA_COURAGE_TITLE("Courage & Initiative", "à¤¸à¤¾à¤¹à¤¸ à¤° à¤ªà¤¹à¤²"),
    DREKKANA_PHYSICAL("Physical", "à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤•"),
    DREKKANA_MENTAL("Mental", "à¤®à¤¾à¤¨à¤¸à¤¿à¤•"),
    DREKKANA_INITIATIVE("Initiative", "à¤ªà¤¹à¤²"),
    DREKKANA_YOUNGER("Younger", "à¤¸à¤¾à¤¨à¥‹"),
    DREKKANA_ELDER("Elder", "à¤ à¥‚à¤²à¥‹"),
    DREKKANA_RELATIONSHIP("Relationship", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§"),
    DREKKANA_SHORT_JOURNEYS("Short Journeys", "à¤›à¥‹à¤Ÿà¤¾ à¤¯à¤¾à¤¤à¥à¤°à¤¾à¤¹à¤°à¥‚"),
    DREKKANA_COMMUNICATION_TITLE("Communication Skills", "à¤¸à¤žà¥à¤šà¤¾à¤° à¤¸à¥€à¤ªà¤¹à¤°à¥‚"),
    DREKKANA_OVERALL("Overall", "à¤¸à¤®à¤—à¥à¤°"),
    DREKKANA_WRITING("Writing", "à¤²à¥‡à¤–à¤¨"),
    DREKKANA_SPEAKING("Speaking", "à¤¬à¥‹à¤²à¥à¤¨à¥‡"),
    DREKKANA_ARTISTIC("Artistic Talents", "à¤•à¤²à¤¾à¤¤à¥à¤®à¤• à¤ªà¥à¤°à¤¤à¤¿à¤­à¤¾à¤¹à¤°à¥‚"),
    DREKKANA_ARTISTIC_TALENTS("Artistic Talents", "à¤•à¤²à¤¾à¤¤à¥à¤®à¤• à¤ªà¥à¤°à¤¤à¤¿à¤­à¤¾à¤¹à¤°à¥‚"),

    // Courage Levels
    COURAGE_LEVEL("Level", "à¤¸à¥à¤¤à¤°"),
    COURAGE_PHYSICAL("Physical Courage", "à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤¸à¤¾à¤¹à¤¸"),
    COURAGE_MENTAL("Mental Courage", "à¤®à¤¾à¤¨à¤¸à¤¿à¤• à¤¸à¤¾à¤¹à¤¸"),
    COURAGE_INITIATIVE("Initiative", "à¤ªà¤¹à¤²"),
    COURAGE_EXCEPTIONAL("Exceptional", "à¤…à¤¸à¤¾à¤§à¤¾à¤°à¤£"),
    COURAGE_HIGH("High", "à¤‰à¤šà¥à¤š"),
    COURAGE_MODERATE("Moderate", "à¤®à¤§à¥à¤¯à¤®"),
    COURAGE_DEVELOPING("Developing", "à¤µà¤¿à¤•à¤¾à¤¸à¤¶à¥€à¤²"),
    COURAGE_NEEDS_WORK("Needs Work", "à¤•à¤¾à¤® à¤†à¤µà¤¶à¥à¤¯à¤•"),

    // ============================================
    // DIVISIONAL CHARTS - NAVAMSA (D-9) MARRIAGE
    // ============================================
    NAVAMSA_SPOUSE_TITLE("Spouse Characteristics", "à¤œà¥€à¤µà¤¨à¤¸à¤¾à¤¥à¥€ à¤µà¤¿à¤¶à¥‡à¤·à¤¤à¤¾à¤¹à¤°à¥‚"),
    NAVAMSA_NATURE("Nature", "à¤¸à¥à¤µà¤­à¤¾à¤µ"),
    NAVAMSA_PHYSICAL_TRAITS("Physical Traits", "à¤¶à¤¾à¤°à¥€à¤°à¤¿à¤• à¤—à¥à¤£à¤¹à¤°à¥‚"),
    NAVAMSA_FAMILY_BACKGROUND("Family Background", "à¤ªà¤¾à¤°à¤¿à¤µà¤¾à¤°à¤¿à¤• à¤ªà¥ƒà¤·à¥à¤ à¤­à¥‚à¤®à¤¿"),
    NAVAMSA_DIRECTION("Direction", "à¤¦à¤¿à¤¶à¤¾"),
    NAVAMSA_PROBABLE_PROFESSIONS("Probable Professions", "à¤¸à¤®à¥à¤­à¤¾à¤µà¤¿à¤¤ à¤ªà¥‡à¤¶à¤¾à¤¹à¤°à¥‚"),
    NAVAMSA_TIMING_TITLE("Marriage Timing", "à¤µà¤¿à¤µà¤¾à¤¹ à¤¸à¤®à¤¯"),
    NAVAMSA_VENUS("Venus", "à¤¶à¥à¤•à¥à¤°"),
    NAVAMSA_JUPITER("Jupiter", "à¤¬à¥ƒà¤¹à¤¸à¥à¤ªà¤¤à¤¿"),
    NAVAMSA_DARAKARAKA("Darakaraka", "à¤¦à¤¾à¤°à¤•à¤¾à¤°à¤•"),
    NAVAMSA_FAVORABLE_DASHA("Favorable Dasha Periods", "à¤…à¤¨à¥à¤•à¥‚à¤² à¤¦à¤¶à¤¾ à¤…à¤µà¤§à¤¿à¤¹à¤°à¥‚"),
    NAVAMSA_KEY_PLANETS_TITLE("Key Planet Positions (D-9)", "à¤®à¥à¤–à¥à¤¯ à¤—à¥à¤°à¤¹ à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤¹à¤°à¥‚ (D-9)"),
    NAVAMSA_UPAPADA("Upapada", "à¤‰à¤ªà¤ªà¤¦"),
    NAVAMSA_RELATIONSHIP_STABILITY("Relationship Stability", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤¸à¥à¤¥à¤¿à¤°à¤¤à¤¾"),
    NAVAMSA_AREAS_ATTENTION("Areas of Attention", "à¤§à¥à¤¯à¤¾à¤¨ à¤¦à¤¿à¤¨à¥à¤ªà¤°à¥à¤¨à¥‡ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚"),
    NAVAMSA_PROTECTIVE_FACTORS("Protective Factors", "à¤¸à¥à¤°à¤•à¥à¤·à¤¾à¤¤à¥à¤®à¤• à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚"),

    // ============================================
    // DIVISIONAL CHARTS - DASHAMSA (D-10) CAREER
    // ============================================
    DASHAMSA_BUSINESS_VS_SERVICE("Business vs Service Aptitude", "à¤µà¥à¤¯à¤¾à¤ªà¤¾à¤° à¤¬à¤¨à¤¾à¤® à¤¸à¥‡à¤µà¤¾ à¤¯à¥‹à¤—à¥à¤¯à¤¤à¤¾"),
    DASHAMSA_BUSINESS("Business", "à¤µà¥à¤¯à¤¾à¤ªà¤¾à¤°"),
    DASHAMSA_SERVICE("Service", "à¤¸à¥‡à¤µà¤¾"),
    DASHAMSA_GOVT_SERVICE_TITLE("Government Service Potential", "à¤¸à¤°à¤•à¤¾à¤°à¥€ à¤¸à¥‡à¤µà¤¾ à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾"),
    DASHAMSA_POTENTIAL("Potential", "à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾"),
    DASHAMSA_RECOMMENDED_AREAS("Recommended Areas", "à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸ à¤—à¤°à¤¿à¤à¤•à¤¾ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚"),
    DASHAMSA_PROFESSIONAL_STRENGTHS("Professional Strengths", "à¤µà¥à¤¯à¤¾à¤µà¤¸à¤¾à¤¯à¤¿à¤• à¤¬à¤²à¤¹à¤°à¥‚"),

    // ============================================
    // DIVISIONAL CHARTS - DWADASAMSA (D-12) PARENTS
    // ============================================
    DWADASAMSA_FATHER("Father", "à¤¬à¥à¤¬à¤¾"),
    DWADASAMSA_MOTHER("Mother", "à¤†à¤®à¤¾"),
    DWADASAMSA_SIGNIFICATOR("Significator", "à¤•à¤¾à¤°à¤•"),
    DWADASAMSA_HOUSE_LORD("House Lord", "à¤­à¤¾à¤µ à¤¸à¥à¤µà¤¾à¤®à¥€"),
    DWADASAMSA_CHARACTERISTICS("Characteristics", "à¤µà¤¿à¤¶à¥‡à¤·à¤¤à¤¾à¤¹à¤°à¥‚"),
    DWADASAMSA_RELATIONSHIP("Relationship", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§"),
    DWADASAMSA_INHERITANCE_TITLE("Inheritance", "à¤µà¤¿à¤°à¤¾à¤¸à¤¤"),
    DWADASAMSA_POTENTIAL("Potential", "à¤¸à¤®à¥à¤­à¤¾à¤µà¤¨à¤¾"),
    DWADASAMSA_TIMING("Timing", "à¤¸à¤®à¤¯"),
    DWADASAMSA_SOURCES("Sources", "à¤¸à¥à¤°à¥‹à¤¤à¤¹à¤°à¥‚"),
    DWADASAMSA_ANCESTRAL_PROPERTY("Ancestral Property", "à¤ªà¥ˆà¤¤à¥ƒà¤• à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿"),
    DWADASAMSA_LONGEVITY_TITLE("Longevity Indicators", "à¤¦à¥€à¤°à¥à¤˜à¤¾à¤¯à¥ à¤¸à¥‚à¤šà¤•à¤¹à¤°à¥‚"),
    DWADASAMSA_HEALTH_CONCERNS("Health Concerns", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤šà¤¿à¤¨à¥à¤¤à¤¾à¤¹à¤°à¥‚"),

    // ============================================
    // AI MODELS SCREEN
    // ============================================
    AI_MODELS_TITLE("AI Models", "AI à¤®à¥‹à¤¡à¥‡à¤²à¤¹à¤°à¥‚"),
    AI_MODELS_ENABLED_COUNT("%d models enabled", "%d à¤®à¥‹à¤¡à¥‡à¤²à¤¹à¤°à¥‚ à¤¸à¤•à¥à¤·à¤®"),
    AI_MODELS_BACK("Back", "à¤ªà¤›à¤¾à¤¡à¤¿"),
    AI_MODELS_REFRESH("Refresh models", "à¤®à¥‹à¤¡à¥‡à¤²à¤¹à¤°à¥‚ à¤°à¤¿à¤«à¥à¤°à¥‡à¤¸ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    AI_MODELS_FREE_TITLE("Free AI Models", "à¤¨à¤¿à¤ƒà¤¶à¥à¤²à¥à¤• AI à¤®à¥‹à¤¡à¥‡à¤²à¤¹à¤°à¥‚"),
    AI_MODELS_FREE_DESC("These models are provided by free API providers and don't require any API keys. Model availability may vary.", "à¤¯à¥€ à¤®à¥‹à¤¡à¥‡à¤²à¤¹à¤°à¥‚ à¤¨à¤¿à¤ƒà¤¶à¥à¤²à¥à¤• API à¤ªà¥à¤°à¤¦à¤¾à¤¯à¤•à¤¹à¤°à¥‚à¤¬à¤¾à¤Ÿ à¤ªà¥à¤°à¤¦à¤¾à¤¨ à¤—à¤°à¤¿à¤à¤•à¤¾ à¤¹à¥à¤¨à¥ à¤° à¤•à¥à¤¨à¥ˆ API à¤•à¥à¤žà¥à¤œà¥€ à¤†à¤µà¤¶à¥à¤¯à¤• à¤ªà¤°à¥à¤¦à¥ˆà¤¨à¥¤ à¤®à¥‹à¤¡à¥‡à¤² à¤‰à¤ªà¤²à¤¬à¥à¤§à¤¤à¤¾ à¤«à¤°à¤• à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›à¥¤"),
    AI_MODELS_DEFAULT("Default Model", "à¤ªà¥‚à¤°à¥à¤µà¤¨à¤¿à¤°à¥à¤§à¤¾à¤°à¤¿à¤¤ à¤®à¥‹à¤¡à¥‡à¤²"),
    AI_MODELS_NOT_SET("Not set", "à¤¸à¥‡à¤Ÿ à¤—à¤°à¤¿à¤à¤•à¥‹ à¤›à¥ˆà¤¨"),
    AI_MODELS_SELECT_DEFAULT("Select Default Model", "à¤ªà¥‚à¤°à¥à¤µà¤¨à¤¿à¤°à¥à¤§à¤¾à¤°à¤¿à¤¤ à¤®à¥‹à¤¡à¥‡à¤² à¤›à¤¾à¤¨à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    AI_MODELS_CANCEL("Cancel", "à¤°à¤¦à¥à¤¦ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    AI_MODELS_MODELS_ENABLED("%d/%d models enabled", "%d/%d à¤®à¥‹à¤¡à¥‡à¤²à¤¹à¤°à¥‚ à¤¸à¤•à¥à¤·à¤®"),
    AI_MODELS_ENABLE_ALL("Enable All", "à¤¸à¤¬à¥ˆ à¤¸à¤•à¥à¤·à¤® à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    AI_MODELS_DISABLE_ALL("Disable All", "à¤¸à¤¬à¥ˆ à¤…à¤¸à¤•à¥à¤·à¤® à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    AI_MODELS_TOOLS("Tools", "à¤‰à¤ªà¤•à¤°à¤£à¤¹à¤°à¥‚"),
    AI_MODELS_REASONING("Reasoning", "à¤¤à¤°à¥à¤•"),
    AI_MODELS_VISION("Vision", "à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿"),
    AI_MODELS_NONE("No Models Available", "à¤•à¥à¤¨à¥ˆ à¤®à¥‹à¤¡à¥‡à¤² à¤‰à¤ªà¤²à¤¬à¥à¤§ à¤›à¥ˆà¤¨"),
    AI_MODELS_NONE_DESC("Unable to fetch AI models. Check your internet connection and try again.", "AI à¤®à¥‹à¤¡à¥‡à¤²à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤¾à¤ªà¥à¤¤ à¤—à¤°à¥à¤¨ à¤…à¤¸à¤®à¤°à¥à¤¥à¥¤ à¤†à¤«à¥à¤¨à¥‹ à¤‡à¤¨à¥à¤Ÿà¤°à¤¨à¥‡à¤Ÿ à¤œà¤¡à¤¾à¤¨ à¤œà¤¾à¤à¤š à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥ à¤° à¤ªà¥à¤¨: à¤ªà¥à¤°à¤¯à¤¾à¤¸ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    AI_MODELS_RETRY("Retry", "à¤ªà¥à¤¨: à¤ªà¥à¤°à¤¯à¤¾à¤¸ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    
    // AI Providers
    AI_PROVIDER_DEEPINFRA("DeepInfra", "à¤¡à¥€à¤ªà¤‡à¤¨à¥à¤«à¥à¤°à¤¾"),
    AI_PROVIDER_QWEN("Qwen", "à¤•à¥à¤µà¥‡à¤¨"),
    AI_PROVIDER_BLACKBOX("Blackbox AI", "à¤¬à¥à¤²à¥à¤¯à¤¾à¤•à¤¬à¤•à¥à¤¸ AI"),
    AI_PROVIDER_DDG("DuckDuckGo AI", "à¤¡à¤•à¤¡à¤•à¤—à¥‹ AI"),

    // ============================================
    // COMMON UI STRINGS
    // ============================================
    BTN_GOT_IT("Got it", "à¤¬à¥à¤à¥‡à¤‚"),

    // ============================================
    // SHADBALA SCREEN
    // ============================================
    SHADBALA_STHANA("Sthana", "à¤¸à¥à¤¥à¤¾à¤¨"),
    SHADBALA_DIG("Dig", "à¤¦à¤¿à¤•à¥"),
    SHADBALA_KALA("Kala", "à¤•à¤¾à¤²"),
    SHADBALA_CHESTA("Chesta", "à¤šà¥‡à¤·à¥à¤Ÿà¤¾"),
    SHADBALA_TOTAL("Total", "à¤•à¥à¤²"),

    // ============================================
    // UPACHAYA TRANSIT SCREEN
    // ============================================
    UPACHAYA_MOON_SIGN("Moon Sign", "à¤šà¤¨à¥à¤¦à¥à¤° à¤°à¤¾à¤¶à¤¿"),
    UPACHAYA_LAGNA("Lagna", "à¤²à¤—à¥à¤¨"),
    UPACHAYA_ACTIVE("Active", "à¤¸à¤•à¥à¤°à¤¿à¤¯"),

    // ============================================
    // UI SCREENS & EMPTY STATES
    // ============================================
    UI_NO_CHART_DATA("No Chart Data", "à¤•à¥à¤¨à¥à¤¡à¤²à¥€ à¤¡à¤¾à¤Ÿà¤¾ à¤‰à¤ªà¤²à¤¬à¥à¤§ à¤›à¥ˆà¤¨"),
    UI_NO_BIRTH_CHART("No Birth Chart", "à¤•à¥à¤¨à¥ˆ à¤œà¤¨à¥à¤® à¤•à¥à¤¨à¥à¤¡à¤²à¥€ à¤›à¥ˆà¤¨"),
    UI_SELECT_CHART("Please select a birth chart", "à¤•à¥ƒà¤ªà¤¯à¤¾ à¤œà¤¨à¥à¤® à¤•à¥à¤¨à¥à¤¡à¤²à¥€ à¤šà¤¯à¤¨ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    UI_ERROR_LOADING("Error loading chart", "à¤•à¥à¤¨à¥à¤¡à¤²à¥€ à¤²à¥‹à¤¡ à¤—à¤°à¥à¤¦à¤¾ à¤¤à¥à¤°à¥à¤Ÿà¤¿"),
    
    // Ashtakavarga
    UI_NO_ASHTAKAVARGA_DATA("No Ashtakavarga Data", "à¤…à¤·à¥à¤Ÿà¤•à¤µà¤°à¥à¤— à¤¡à¤¾à¤Ÿà¤¾ à¤›à¥ˆà¤¨"),
    
    // Arudha
    UI_NO_ARUDHA_YOGAS("No significant Arudha Yogas found", "à¤•à¥à¤¨à¥ˆ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤†à¤°à¥à¤¢ à¤¯à¥‹à¤— à¤«à¥‡à¤²à¤¾ à¤ªà¤°à¥‡à¤¨"),
    
    // Dasha Sandhi
    UI_NO_ACTIVE_SANDHI("No Active Sandhi", "à¤•à¥à¤¨à¥ˆ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤¸à¤¨à¥à¤§à¤¿ à¤›à¥ˆà¤¨"),
    UI_NO_UPCOMING_SANDHI("No upcoming Sandhi periods in the analysis window.", "à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤…à¤µà¤§à¤¿à¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤†à¤—à¤¾à¤®à¥€ à¤¸à¤¨à¥à¤§à¤¿ à¤…à¤µà¤§à¤¿ à¤›à¥ˆà¤¨à¥¤"),
    UI_NO_CALENDAR_ENTRIES("No calendar entries available.", "à¤•à¥à¤¨à¥ˆ à¤•à¥à¤¯à¤¾à¤²à¥‡à¤¨à¥à¤¡à¤° à¤ªà¥à¤°à¤µà¤¿à¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚ à¤‰à¤ªà¤²à¤¬à¥à¤§ à¤›à¥ˆà¤¨à¤¨à¥à¥¤"),
    
    // Gochara Vedha
    UI_NO_ACTIVE_VEDHAS("No Active Vedhas", "à¤•à¥à¤¨à¥ˆ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤µà¥‡à¤§ à¤›à¥ˆà¤¨"),
    
    // Graha Yuddha
    UI_NO_PLANET_WARS("No Active Planetary Wars", "à¤•à¥à¤¨à¥ˆ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤—à¥à¤°à¤¹ à¤¯à¥à¤¦à¥à¤§ à¤›à¥ˆà¤¨"),
    UI_NO_WAR_DASHA("No War-Related Dasha Effects", "à¤•à¥à¤¨à¥ˆ à¤¯à¥à¤¦à¥à¤§-à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¿à¤¤ à¤¦à¤¶à¤¾ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚ à¤›à¥ˆà¤¨"),
    UI_NO_WAR_REMEDIES("No Specific Remedies Needed", "à¤•à¥à¤¨à¥ˆ à¤µà¤¿à¤¶à¤¿à¤·à¥à¤Ÿ à¤‰à¤ªà¤¾à¤¯ à¤†à¤µà¤¶à¥à¤¯à¤• à¤›à¥ˆà¤¨"),
    UI_NO_ACTIVE_WAR_REMEDIES("No active wars to remediate", "à¤‰à¤ªà¤šà¤¾à¤° à¤—à¤°à¥à¤¨ à¤•à¥à¤¨à¥ˆ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤¯à¥à¤¦à¥à¤§ à¤›à¥ˆà¤¨"),
    
    // Kemadruma
    UI_KEMADRUMA_CONDITION_MET("No planets in houses adjacent to Moon - Kemadruma condition met", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤•à¥‹ à¤›à¥‡à¤‰à¤•à¤¾ à¤­à¤¾à¤µà¤¹à¤°à¥‚à¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤—à¥à¤°à¤¹ à¤›à¥ˆà¤¨ - à¤•à¥‡à¤®à¤¦à¥à¤°à¥à¤® à¤…à¤µà¤¸à¥à¤¥à¤¾ à¤ªà¥‚à¤°à¤¾ à¤­à¤¯à¥‹"),
    UI_NO_REMEDIES_NEEDED("No Remedies Needed", "à¤•à¥à¤¨à¥ˆ à¤‰à¤ªà¤¾à¤¯ à¤†à¤µà¤¶à¥à¤¯à¤• à¤›à¥ˆà¤¨"),
    
    // Panch Mahapurusha / Yogas
    UI_NO_YOGAS_FORMED("No Yogas Formed", "à¤•à¥à¤¨à¥ˆ à¤¯à¥‹à¤— à¤¬à¤¨à¥‡à¤•à¥‹ à¤›à¥ˆà¤¨"),
    UI_NO_YOGAS_DISPLAY("No Yogas to Display", "à¤¦à¥‡à¤–à¤¾à¤‰à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤•à¥à¤¨à¥ˆ à¤¯à¥‹à¤— à¤›à¥ˆà¤¨"),
    
    // ============================================
    // ARUDHA PADA SCREEN
    // ============================================
    ARUDHA_SCREEN_TITLE("Arudha Pada", "à¤†à¤°à¥à¤¢ à¤ªà¤¦"),
    ARUDHA_ANALYSIS_TITLE("Arudha Pada Analysis", "à¤†à¤°à¥à¤¢ à¤ªà¤¦ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    ARUDHA_KEY_POSITIONS("Key Arudha Positions", "à¤®à¥à¤–à¥à¤¯ à¤†à¤°à¥à¤¢ à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤¹à¤°à¥‚"),
    ARUDHA_MANIFESTATION("Manifestation Strength", "à¤ªà¥à¤°à¤•à¤Ÿà¥€à¤•à¤°à¤£ à¤¶à¤•à¥à¤¤à¤¿"),
    ARUDHA_PUBLIC_IMAGE("Public Image", "à¤¸à¤¾à¤°à¥à¤µà¤œà¤¨à¤¿à¤• à¤›à¤µà¤¿"),
    ARUDHA_CAREER("Career", "à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°"),
    ARUDHA_GAINS("Gains", "à¤²à¤¾à¤­"),
    ARUDHA_RELATIONSHIPS("Relationships", "à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¹à¤°à¥‚"),
    ARUDHA_KEY_THEMES("Key Themes", "à¤®à¥à¤–à¥à¤¯ à¤µà¤¿à¤·à¤¯à¤µà¤¸à¥à¤¤à¥à¤¹à¤°à¥‚"),
    ARUDHA_YOGAS_TITLE("Arudha Yogas", "à¤†à¤°à¥à¤¢ à¤¯à¥‹à¤—à¤¹à¤°à¥‚"),
    ARUDHA_RECOMMENDATIONS("Recommendations", "à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸à¤¹à¤°à¥‚"),
    ARUDHA_CALCULATING("Calculating Arudha Padas...", "à¤†à¤°à¥à¤¢ à¤ªà¤¦ à¤—à¤£à¤¨à¤¾ à¤—à¤°à¥à¤¦à¥ˆ..."),
    ARUDHA_ABOUT_BTN("About Arudha Pada", "à¤†à¤°à¥à¤¢ à¤ªà¤¦à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    
    // ============================================
    // ASHTAKAVARGA SCREEN
    // ============================================
    ASHTAKAVARGA_KEY_INSIGHTS("Key Insights", "à¤®à¥à¤–à¥à¤¯ à¤…à¤¨à¥à¤¤à¤°à¥à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿"),
    ASHTAKAVARGA_STRONGEST("STRONGEST HOUSES", "à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤¬à¤²à¤¿à¤¯à¥‹ à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    ASHTAKAVARGA_WEAKEST("WEAKEST HOUSES", "à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤•à¤®à¤œà¥‹à¤° à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    ASHTAKAVARGA_PLANET_TOTALS("Planet Bindu Totals", "à¤—à¥à¤°à¤¹ à¤¬à¤¿à¤¨à¥à¤¦à¥ à¤¯à¥‹à¤—"),
    ASHTAKAVARGA_SARVA_DESC("Sarvashtakavarga is the combined bindu strength from all seven planets for each house/sign.", "à¤¸à¤°à¥à¤µà¤¾à¤·à¥à¤Ÿà¤•à¤µà¤°à¥à¤— à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤­à¤¾à¤µ/à¤°à¤¾à¤¶à¤¿à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¸à¤¾à¤¤à¥ˆ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤•à¥‹ à¤¸à¤‚à¤¯à¥à¤•à¥à¤¤ à¤¬à¤¿à¤¨à¥à¤¦à¥ à¤¶à¤•à¥à¤¤à¤¿ à¤¹à¥‹à¥¤"),
    ASHTAKAVARGA_ABOUT_TITLE("About Ashtakavarga", "à¤…à¤·à¥à¤Ÿà¤•à¤µà¤°à¥à¤—à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    ASHTAKAVARGA_ABOUT_DESC("Ashtakavarga is a unique Vedic system that calculates the strength of each house by analyzing beneficial points (bindus) contributed by seven planets.", "à¤…à¤·à¥à¤Ÿà¤•à¤µà¤°à¥à¤— à¤à¤• à¤…à¤¦à¥à¤µà¤¿à¤¤à¥€à¤¯ à¤µà¥ˆà¤¦à¤¿à¤• à¤ªà¥à¤°à¤£à¤¾à¤²à¥€ à¤¹à¥‹ à¤œà¤¸à¤²à¥‡ à¤¸à¤¾à¤¤ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤¦à¥à¤µà¤¾à¤°à¤¾ à¤ªà¥à¤°à¤¦à¤¾à¤¨ à¤—à¤°à¤¿à¤à¤•à¥‹ à¤¶à¥à¤­ à¤¬à¤¿à¤¨à¥à¤¦à¥à¤¹à¤°à¥‚à¤•à¥‹ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤°à¥‡à¤° à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤­à¤¾à¤µà¤•à¥‹ à¤¶à¤•à¥à¤¤à¤¿ à¤—à¤£à¤¨à¤¾ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    ASHTAKAVARGA_INTERPRETATION("Interpretation Guide:", "à¤µà¥à¤¯à¤¾à¤–à¥à¤¯à¤¾ à¤®à¤¾à¤°à¥à¤—à¤¦à¤°à¥à¤¶à¤¨:"),
    ASHTAKAVARGA_GUIDE_TEXT("â€¢ 30+ bindus: Very strong, excellent results\nâ€¢ 25-30 bindus: Good strength, favorable\nâ€¢ 20-25 bindus: Average, mixed results\nâ€¢ Below 20: Weak, challenges expected", "â€¢ à¥©à¥¦+ à¤¬à¤¿à¤¨à¥à¤¦à¥: à¤§à¥‡à¤°à¥ˆ à¤¬à¤²à¤¿à¤¯à¥‹, à¤‰à¤¤à¥à¤•à¥ƒà¤·à¥à¤Ÿ à¤ªà¤°à¤¿à¤£à¤¾à¤®\nâ€¢ à¥¨à¥«-à¥©à¥¦ à¤¬à¤¿à¤¨à¥à¤¦à¥: à¤°à¤¾à¤®à¥à¤°à¥‹ à¤¶à¤•à¥à¤¤à¤¿, à¤…à¤¨à¥à¤•à¥‚à¤²\nâ€¢ à¥¨à¥¦-à¥¨à¥« à¤¬à¤¿à¤¨à¥à¤¦à¥: à¤”à¤¸à¤¤, à¤®à¤¿à¤¶à¥à¤°à¤¿à¤¤ à¤ªà¤°à¤¿à¤£à¤¾à¤®\nâ€¢ à¥¨à¥¦ à¤­à¤¨à¥à¤¦à¤¾ à¤•à¤®: à¤•à¤®à¤œà¥‹à¤°, à¤šà¥à¤¨à¥Œà¤¤à¥€à¤¹à¤°à¥‚ à¤…à¤ªà¥‡à¤•à¥à¤·à¤¿à¤¤"),
    ASHTAKAVARGA_BINDUS_SUFFIX("bindus", "à¤¬à¤¿à¤¨à¥à¤¦à¥à¤¹à¤°à¥‚"),
    
    // House Significations (Short)
    HOUSE_SIG_1("Self, Body, Personality", "à¤¸à¥à¤µà¤¯à¤‚, à¤¶à¤°à¥€à¤°, à¤µà¥à¤¯à¤•à¥à¤¤à¤¿à¤¤à¥à¤µ"),
    HOUSE_SIG_2("Wealth, Speech, Family", "à¤§à¤¨, à¤µà¤¾à¤£à¥€, à¤ªà¤°à¤¿à¤µà¤¾à¤°"),
    HOUSE_SIG_3("Siblings, Courage", "à¤¦à¤¾à¤œà¥à¤­à¤¾à¤‡, à¤¸à¤¾à¤¹à¤¸"),
    HOUSE_SIG_4("Home, Mother, Property", "à¤˜à¤°, à¤†à¤®à¤¾, à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿"),
    HOUSE_SIG_5("Children, Education", "à¤¸à¤¨à¥à¤¤à¤¾à¤¨, à¤¶à¤¿à¤•à¥à¤·à¤¾"),
    HOUSE_SIG_6("Health, Enemies", "à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯, à¤¶à¤¤à¥à¤°à¥"),
    HOUSE_SIG_7("Marriage, Partnership", "à¤µà¤¿à¤µà¤¾à¤¹, à¤¸à¤¾à¤à¥‡à¤¦à¤¾à¤°à¥€"),
    HOUSE_SIG_8("Transformation", "à¤°à¥‚à¤ªà¤¾à¤¨à¥à¤¤à¤°à¤£"),
    HOUSE_SIG_9("Fortune, Father", "à¤­à¤¾à¤—à¥à¤¯, à¤ªà¤¿à¤¤à¤¾"),
    HOUSE_SIG_10("Career, Fame", "à¤•à¥à¤¯à¤¾à¤°à¤¿à¤¯à¤°, à¤ªà¥à¤°à¤¸à¤¿à¤¦à¥à¤§à¤¿"),
    HOUSE_SIG_11("Gains, Wishes", "à¤²à¤¾à¤­, à¤‡à¤šà¥à¤›à¤¾"),
    HOUSE_SIG_12("Losses, Liberation", "à¤¹à¤¾à¤¨à¥€, à¤®à¥‹à¤•à¥à¤·"),
    
    // ============================================
    // AVASTHA SCREEN
    // ============================================
    AVASTHA_SCREEN_TITLE("Planetary Avasthas", "à¤—à¥à¤°à¤¹ à¤…à¤µà¤¸à¥à¤¥à¤¾à¤¹à¤°à¥‚"),
    AVASTHA_SUBTITLE("Planetary States & Conditions", "à¤—à¥à¤°à¤¹ à¤¸à¥à¤¥à¤¿à¤¤à¤¿ à¤° à¤…à¤µà¤¸à¥à¤¥à¤¾à¤¹à¤°à¥‚"),
    AVASTHA_NO_CHART_DESC("Create or select a birth chart to analyze planetary Avasthas.", "à¤—à¥à¤°à¤¹ à¤…à¤µà¤¸à¥à¤¥à¤¾à¤¹à¤°à¥‚ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤°à¥à¤¨ à¤œà¤¨à¥à¤® à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤¸à¤¿à¤°à¥à¤œà¤¨à¤¾ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥ à¤µà¤¾ à¤›à¤¾à¤¨à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    AVASTHA_ABOUT_TITLE("About Planetary Avasthas", "à¤—à¥à¤°à¤¹ à¤…à¤µà¤¸à¥à¤¥à¤¾à¤¹à¤°à¥‚à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    AVASTHA_ABOUT_DESC("Avasthas are planetary states that indicate how effectively a planet can deliver its results.\n\nFour Types of Avasthas:\n\n1. Baladi (Age-based):\nBala (Infant), Kumara (Youth), Yuva (Adult), Vriddha (Old), Mrita (Dead)\n\n2. Jagradadi (Alertness):\nJagrat (Awake), Swapna (Dreaming), Sushupti (Deep Sleep)\n\n3. Deeptadi (Dignity):\nDeepta, Swastha, Mudita, Shanta, Dina, Vikala, Khala, Kopa, Bhita\n\n4. Lajjitadi (Emotional):\nLajjita, Garvita, Kshudita, Trushita, Mudita, Kshobhita\n\nA planet in good avasthas gives its full results, while one in poor avasthas struggles to manifest its significations.", "à¤…à¤µà¤¸à¥à¤¥à¤¾à¤¹à¤°à¥‚ à¤—à¥à¤°à¤¹à¤•à¤¾ à¤¸à¥à¤¥à¤¿à¤¤à¤¿à¤¹à¤°à¥‚ à¤¹à¥à¤¨à¥ à¤œà¤¸à¤²à¥‡ à¤—à¥à¤°à¤¹à¤²à¥‡ à¤†à¤«à¥à¤¨à¤¾ à¤¨à¤¤à¤¿à¤œà¤¾à¤¹à¤°à¥‚ à¤•à¤¤à¥à¤¤à¤¿à¤•à¥‹ à¤ªà¥à¤°à¤­à¤¾à¤µà¤•à¤¾à¤°à¥€ à¤°à¥‚à¤ªà¤®à¤¾ à¤¦à¤¿à¤¨ à¤¸à¤•à¥à¤› à¤­à¤¨à¥‡à¤° à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤¦à¤›à¥¤\n\nà¤šà¤¾à¤° à¤ªà¥à¤°à¤•à¤¾à¤°à¤•à¤¾ à¤…à¤µà¤¸à¥à¤¥à¤¾à¤¹à¤°à¥‚:\n\nà¥§. à¤¬à¤¾à¤²à¤¾à¤¦à¤¿ (à¤‰à¤®à¥‡à¤°à¤®à¤¾ à¤†à¤§à¤¾à¤°à¤¿à¤¤):\nà¤¬à¤¾à¤², à¤•à¥à¤®à¤¾à¤°, à¤¯à¥à¤µà¤¾, à¤µà¥ƒà¤¦à¥à¤§, à¤®à¥ƒà¤¤\n\nà¥¨. à¤œà¤¾à¤—à¥à¤°à¤¦à¤¾à¤¦à¤¿ (à¤œà¤¾à¤—à¤°à¥‚à¤•à¤¤à¤¾):\nà¤œà¤¾à¤—à¥à¤°à¤¤, à¤¸à¥à¤µà¤ªà¥à¤¨, à¤¸à¥à¤·à¥à¤ªà¥à¤¤à¤¿\n\nà¥©. à¤¦à¥€à¤ªà¥à¤¤à¤¾à¤¦à¤¿ (à¤®à¤°à¥à¤¯à¤¾à¤¦à¤¾):\nà¤¦à¥€à¤ªà¥à¤¤, à¤¸à¥à¤µà¤¸à¥à¤¥, à¤®à¥à¤¦à¤¿à¤¤, à¤¶à¤¾à¤¨à¥à¤¤, à¤¦à¥€à¤¨, à¤µà¤¿à¤•à¤², à¤–à¤², à¤•à¥‹à¤ª, à¤­à¥€à¤¤\n\nà¥ª. à¤²à¤œà¥à¤œà¤¿à¤¤à¤¾à¤¦à¤¿ (à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤•):\nà¤²à¤œà¥à¤œà¤¿à¤¤, à¤—à¤°à¥à¤µà¤¿à¤¤, à¤•à¥à¤·à¥à¤§à¤¿à¤¤, à¤¤à¥ƒà¤·à¤¿à¤¤, à¤®à¥à¤¦à¤¿à¤¤, à¤•à¥à¤·à¥‹à¤­à¤¿à¤¤\n\nà¤°à¤¾à¤®à¥à¤°à¥‹ à¤…à¤µà¤¸à¥à¤¥à¤¾à¤®à¤¾ à¤°à¤¹à¥‡à¤•à¥‹ à¤—à¥à¤°à¤¹à¤²à¥‡ à¤ªà¥‚à¤°à¥à¤£ à¤«à¤² à¤¦à¤¿à¤¨à¥à¤›, à¤œà¤¬à¤•à¤¿ à¤•à¤®à¤œà¥‹à¤° à¤…à¤µà¤¸à¥à¤¥à¤¾à¤®à¤¾ à¤°à¤¹à¥‡à¤•à¥‹ à¤—à¥à¤°à¤¹à¤²à¥‡ à¤†à¤«à¥à¤¨à¤¾ à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚ à¤µà¥à¤¯à¤•à¥à¤¤ à¤—à¤°à¥à¤¨ à¤¸à¤‚à¤˜à¤°à¥à¤· à¤—à¤°à¥à¤¦à¤›à¥¤"),
    AVASTHA_OVERALL_STRENGTH("Overall Planetary Strength", "à¤¸à¤®à¤—à¥à¤° à¤—à¥à¤°à¤¹ à¤¬à¤²"),
    AVASTHA_STRONGEST("Strongest", "à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤¬à¤²à¤¿à¤¯à¥‹"),
    AVASTHA_NEEDS_ATTENTION("Needs Attention", "à¤§à¥à¤¯à¤¾à¤¨ à¤¦à¤¿à¤¨à¥à¤ªà¤°à¥à¤¨à¥‡"),
    
    // ============================================
    // NITYA YOGA SCREEN
    // ============================================
    NITYA_SCREEN_TITLE("Nitya Yoga", "à¤¨à¤¿à¤¤à¥à¤¯ à¤¯à¥‹à¤—"),
    NITYA_SUBTITLE("27 Daily Yogas", "à¥¨à¥­ à¤¦à¥ˆà¤¨à¤¿à¤• à¤¯à¥‹à¤—à¤¹à¤°à¥‚"),
    NITYA_NO_DATA_DESC("Create or select a birth chart to view Nitya Yoga analysis.", "à¤¨à¤¿à¤¤à¥à¤¯ à¤¯à¥‹à¤— à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤¹à¥‡à¤°à¥à¤¨ à¤œà¤¨à¥à¤® à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤¸à¤¿à¤°à¥à¤œà¤¨à¤¾ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥ à¤µà¤¾ à¤›à¤¾à¤¨à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    NITYA_ABOUT_TITLE("About Nitya Yoga", "à¤¨à¤¿à¤¤à¥à¤¯ à¤¯à¥‹à¤—à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    NITYA_ABOUT_DESC("Nitya Yoga (Daily Yoga) is one of the five elements of the Panchanga (Hindu almanac).\n\nThere are 27 Nitya Yogas, each spanning 13Â°20' of the combined longitude of Sun and Moon.\n\nCalculation:\nNitya Yoga = (Sun longitude + Moon longitude) Ã· 13Â°20'\n\nThese yogas indicate the general auspiciousness of a moment and are used in Muhurta (electional astrology) to select favorable times for important activities.\n\nEach yoga is ruled by a planet and has specific characteristics affecting health, wealth, relationships, and spiritual matters.", "à¤¨à¤¿à¤¤à¥à¤¯ à¤¯à¥‹à¤— à¤ªà¤žà¥à¤šà¤¾à¤™à¥à¤—à¤•à¤¾ à¤ªà¤¾à¤à¤š à¤…à¤‚à¤—à¤¹à¤°à¥‚ à¤®à¤§à¥à¤¯à¥‡ à¤à¤• à¤¹à¥‹à¥¤\n\nà¤¤à¥à¤¯à¤¹à¤¾à¤ à¥¨à¥­ à¤¨à¤¿à¤¤à¥à¤¯ à¤¯à¥‹à¤—à¤¹à¤°à¥‚ à¤›à¤¨à¥, à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤•à¤²à¥‡ à¤¸à¥‚à¤°à¥à¤¯ à¤° à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤•à¥‹ à¤¸à¤‚à¤¯à¥à¤•à¥à¤¤ à¤¦à¥‡à¤¶à¤¾à¤¨à¥à¤¤à¤°à¤•à¥‹ à¥§à¥©Â°à¥¨à¥¦' à¤¸à¤®à¥‡à¤Ÿà¥à¤›à¥¤\n\nà¤—à¤£à¤¨à¤¾:\nà¤¨à¤¿à¤¤à¥à¤¯ à¤¯à¥‹à¤— = (à¤¸à¥‚à¤°à¥à¤¯ à¤¸à¥à¤ªà¤·à¥à¤Ÿ + à¤šà¤¨à¥à¤¦à¥à¤° à¤¸à¥à¤ªà¤·à¥à¤Ÿ) Ã· à¥§à¥©Â°à¥¨à¥¦'\n\nà¤¯à¥€ à¤¯à¥‹à¤—à¤¹à¤°à¥‚à¤²à¥‡ à¤¸à¤®à¤¯à¤•à¥‹ à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤¶à¥à¤­à¤¤à¤¾à¤²à¤¾à¤ˆ à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤¦à¤› à¤° à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤¶à¥à¤­ à¤¸à¤®à¤¯ à¤›à¤¨à¥Œà¤Ÿ à¤—à¤°à¥à¤¨ à¤®à¥à¤¹à¥‚à¤°à¥à¤¤à¤®à¤¾ à¤ªà¥à¤°à¤¯à¥‹à¤— à¤—à¤°à¤¿à¤¨à¥à¤›à¥¤\n\nà¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤¯à¥‹à¤— à¤à¤• à¤—à¥à¤°à¤¹à¤¦à¥à¤µà¤¾à¤°à¤¾ à¤¶à¤¾à¤¸à¤¿à¤¤ à¤¹à¥à¤¨à¥à¤› à¤° à¤¯à¤¸à¤²à¥‡ à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯, à¤§à¤¨, à¤¸à¤®à¥à¤¬à¤¨à¥à¤§ à¤° à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤• à¤®à¤¾à¤®à¤¿à¤²à¤¾à¤¹à¤°à¥‚à¤²à¤¾à¤ˆ à¤…à¤¸à¤° à¤—à¤°à¥à¤¨à¥‡ à¤µà¤¿à¤¶à¤¿à¤·à¥à¤Ÿ à¤µà¤¿à¤¶à¥‡à¤·à¤¤à¤¾à¤¹à¤°à¥‚ à¤°à¤¾à¤–à¥à¤›à¥¤"),
    
    NITYA_PROGRESS("Progress", "à¤ªà¥à¤°à¤—à¤¤à¤¿"),
    NITYA_POSITION("Position", "à¤¸à¥à¤¥à¤¿à¤¤à¤¿"),
    NITYA_NATURE("Nature", "à¤ªà¥à¤°à¤•à¥ƒà¤¤à¤¿"),
    NITYA_CURRENT_PROGRESS("Progress in Current Yoga", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤¯à¥‹à¤—à¤®à¤¾ à¤ªà¥à¤°à¤—à¤¤à¤¿"),
    NITYA_SUITABLE("Suitable Activities", "à¤‰à¤ªà¤¯à¥à¤•à¥à¤¤ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚"),
    NITYA_AVOID("Activities to Avoid", "à¤¬à¤šà¥à¤¨à¥à¤ªà¤°à¥à¤¨à¥‡ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚"),
    NITYA_NEXT("Next:", "à¤…à¤°à¥à¤•à¥‹:"),
    NITYA_REMAINING("remaining", "à¤¬à¤¾à¤à¤•à¥€"),
    
    // Common
    UI_INTERPRETATION("Interpretation", "à¤µà¥à¤¯à¤¾à¤–à¥à¤¯à¤¾"),
    UI_RECOMMENDATIONS("Recommendations", "à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸à¤¹à¤°à¥‚"),
    UI_OVERVIEW("Overview", "à¤…à¤µà¤²à¥‹à¤•à¤¨"),
    UI_YOGAS("Yogas", "à¤¯à¥‹à¤—à¤¹à¤°à¥‚"),
    UI_EFFECTS("Effects", "à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    UI_TIMING("Timing", "à¤¸à¤®à¤¯"),
    UI_REMEDIES("Remedies", "à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    UI_TRANSITS("Transits", "à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚"),
    UI_CURRENT("Current", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨"),
    UI_UPCOMING("Upcoming", "à¤†à¤—à¤¾à¤®à¥€"),
    UI_CALENDAR("Calendar", "à¤ªà¤¾à¤¤à¥à¤°à¥‹"),
    UI_KEY_INSIGHTS("Key Insights", "à¤®à¥à¤–à¥à¤¯ à¤…à¤¨à¥à¤¤à¤°à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚"),
    UI_START("Start:", "à¤¸à¥à¤°à¥:"),
    UI_END("End:", "à¤…à¤¨à¥à¤¤à¥à¤¯:"),

    // ============================================
    // PANCH MAHAPURUSHA SCREEN
    // ============================================
    PANCHA_SCREEN_TITLE("Panch Mahapurusha Yoga", "à¤ªà¤žà¥à¤š à¤®à¤¹à¤¾à¤ªà¥à¤°à¥à¤· à¤¯à¥‹à¤—"),
    PANCHA_SUBTITLE("Five Great Person Yogas", "à¤ªà¤¾à¤à¤š à¤®à¤¹à¤¾à¤ªà¥à¤°à¥à¤· à¤¯à¥‹à¤—à¤¹à¤°à¥‚"),
    PANCHA_ABOUT_TITLE("About Panch Mahapurusha Yoga", "à¤ªà¤žà¥à¤š à¤®à¤¹à¤¾à¤ªà¥à¤°à¥à¤· à¤¯à¥‹à¤—à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    PANCHA_ABOUT_DESC("Panch Mahapurusha Yoga (Five Great Person Yogas) are special planetary combinations that indicate exceptional qualities and achievements.\n\nThe five yogas are:\nâ€¢ Ruchaka (Mars) - Courage, leadership, military prowess\nâ€¢ Bhadra (Mercury) - Intelligence, communication, commerce\nâ€¢ Hamsa (Jupiter) - Wisdom, spirituality, fortune\nâ€¢ Malavya (Venus) - Beauty, luxury, artistic talents\nâ€¢ Sasha (Saturn) - Discipline, authority, longevity\n\nFormation requirements:\nThe planet must be in a Kendra house (1st, 4th, 7th, or 10th) AND in its own sign or exaltation sign.\n\nHaving one or more of these yogas in a chart indicates the native will possess the exceptional qualities of that planet and achieve success in related areas.", "à¤ªà¤žà¥à¤š à¤®à¤¹à¤¾à¤ªà¥à¤°à¥à¤· à¤¯à¥‹à¤—à¤¹à¤°à¥‚ à¤µà¤¿à¤¶à¥‡à¤· à¤—à¥à¤°à¤¹ à¤¸à¤‚à¤¯à¥‹à¤œà¤¨à¤¹à¤°à¥‚ à¤¹à¥à¤¨à¥ à¤œà¤¸à¤²à¥‡ à¤…à¤¸à¤¾à¤§à¤¾à¤°à¤£ à¤—à¥à¤£à¤¹à¤°à¥‚ à¤° à¤‰à¤ªà¤²à¤¬à¥à¤§à¤¿à¤¹à¤°à¥‚à¤²à¤¾à¤ˆ à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤¦à¤›à¥¤\n\nà¤ªà¤¾à¤à¤š à¤¯à¥‹à¤—à¤¹à¤°à¥‚ à¤¹à¥à¤¨à¥:\nâ€¢ à¤°à¥à¤šà¤• (à¤®à¤‚à¤—à¤²) - à¤¸à¤¾à¤¹à¤¸, à¤¨à¥‡à¤¤à¥ƒà¤¤à¥à¤µ, à¤¸à¥ˆà¤¨à¥à¤¯ à¤•à¥Œà¤¶à¤²\nâ€¢ à¤­à¤¦à¥à¤° (à¤¬à¥à¤§) - à¤¬à¥à¤¦à¥à¤§à¤¿, à¤¸à¤žà¥à¤šà¤¾à¤°, à¤µà¤¾à¤£à¤¿à¤œà¥à¤¯\nâ€¢ à¤¹à¤‚à¤¸ (à¤¬à¥ƒà¤¹à¤¸à¥à¤ªà¤¤à¤¿) - à¤œà¥à¤žà¤¾à¤¨, à¤†à¤§à¥à¤¯à¤¾à¤¤à¥à¤®à¤¿à¤•à¤¤à¤¾, à¤­à¤¾à¤—à¥à¤¯\nâ€¢ à¤®à¤¾à¤²à¤µà¥à¤¯ (à¤¶à¥à¤•à¥à¤°) - à¤¸à¥Œà¤¨à¥à¤¦à¤°à¥à¤¯, à¤µà¤¿à¤²à¤¾à¤¸à¤¿à¤¤à¤¾, à¤•à¤²à¤¾à¤¤à¥à¤®à¤• à¤ªà¥à¤°à¤¤à¤¿à¤­à¤¾\nâ€¢ à¤¶à¤¶ (à¤¶à¤¨à¤¿) - à¤…à¤¨à¥à¤¶à¤¾à¤¸à¤¨, à¤…à¤§à¤¿à¤•à¤¾à¤°, à¤¦à¥€à¤°à¥à¤˜à¤¾à¤¯à¥\n\nà¤¬à¤¨à¥à¤¨à¥‡ à¤¶à¤°à¥à¤¤à¤¹à¤°à¥‚:\nà¤—à¥à¤°à¤¹ à¤•à¥‡à¤¨à¥à¤¦à¥à¤° à¤­à¤¾à¤µ (à¥§, à¥ª, à¥­, à¤µà¤¾ à¥§à¥¦) à¤®à¤¾ à¤¹à¥à¤¨à¥à¤ªà¤°à¥à¤› à¤° à¤†à¤«à¥à¤¨à¥ˆ à¤°à¤¾à¤¶à¤¿ à¤µà¤¾ à¤‰à¤šà¥à¤š à¤°à¤¾à¤¶à¤¿à¤®à¤¾ à¤¹à¥à¤¨à¥à¤ªà¤°à¥à¤›à¥¤\n\nà¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾ à¤¯à¥€ à¤®à¤§à¥à¤¯à¥‡ à¤à¤• à¤µà¤¾ à¤¬à¤¢à¥€ à¤¯à¥‹à¤—à¤¹à¤°à¥‚ à¤¹à¥à¤¨à¥à¤²à¥‡ à¤œà¤¾à¤¤à¤•à¤®à¤¾ à¤¤à¥à¤¯à¤¸ à¤—à¥à¤°à¤¹à¤•à¤¾ à¤…à¤¸à¤¾à¤§à¤¾à¤°à¤£ à¤—à¥à¤£à¤¹à¤°à¥‚ à¤¹à¥à¤¨à¥‡à¤›à¤¨à¥ à¤° à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¿à¤¤ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤®à¤¾ à¤¸à¤«à¤²à¤¤à¤¾ à¤ªà¥à¤°à¤¾à¤ªà¥à¤¤ à¤—à¤°à¥à¤¨à¥‡à¤›à¤¨à¥ à¤­à¤¨à¥à¤¨à¥‡ à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    PANCHA_NO_YOGAS_DISPLAY("No Yogas to Display", "à¤¦à¥‡à¤–à¤¾à¤‰à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤•à¥à¤¨à¥ˆ à¤¯à¥‹à¤—à¤¹à¤°à¥‚ à¤›à¥ˆà¤¨à¤¨à¥"),
    PANCHA_NO_YOGAS_DESC("For a Mahapurusha Yoga to form, Mars, Mercury, Jupiter, Venus, or Saturn must be in Kendra (1,4,7,10) in its own or exaltation sign.", "à¤®à¤¹à¤¾à¤ªà¥à¤°à¥à¤· à¤¯à¥‹à¤— à¤¬à¤¨à¥à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿, à¤®à¤‚à¤—à¤², à¤¬à¥à¤§, à¤¬à¥ƒà¤¹à¤¸à¥à¤ªà¤¤à¤¿, à¤¶à¥à¤•à¥à¤° à¤µà¤¾ à¤¶à¤¨à¤¿ à¤•à¥‡à¤¨à¥à¤¦à¥à¤° (à¥§, à¥ª, à¥­, à¥§à¥¦) à¤®à¤¾ à¤†à¤«à¥à¤¨à¥ˆ à¤µà¤¾ à¤‰à¤šà¥à¤š à¤°à¤¾à¤¶à¤¿à¤®à¤¾ à¤¹à¥à¤¨à¥à¤ªà¤°à¥à¤›à¥¤"),
    PANCHA_STATUS_FOUND("Yoga(s) Found!", "à¤¯à¥‹à¤—(à¤¹à¤°à¥‚) à¤«à¥‡à¤²à¤¾ à¤ªà¤°à¥à¤¯à¥‹!"),
    PANCHA_STATUS_FOUND_DESC("You have %1\$d Panch Mahapurusha Yoga(s) in your chart", "à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾ %1\$d à¤ªà¤žà¥à¤š à¤®à¤¹à¤¾à¤ªà¥à¤°à¥à¤· à¤¯à¥‹à¤—(à¤¹à¤°à¥‚) à¤›à¤¨à¥"),
    PANCHA_STATUS_NONE_DESC("None of the five Mahapurusha Yogas are formed", "à¤ªà¤¾à¤à¤š à¤®à¤¹à¤¾à¤ªà¥à¤°à¥à¤· à¤¯à¥‹à¤—à¤¹à¤°à¥‚ à¤®à¤§à¥à¤¯à¥‡ à¤•à¥à¤¨à¥ˆ à¤ªà¤¨à¤¿ à¤¬à¤¨à¥‡à¤•à¥‹ à¤›à¥ˆà¤¨"),
    PANCHA_COMBINED("Combined Effects", "à¤¸à¤‚à¤¯à¥à¤•à¥à¤¤ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    PANCHA_SYNERGIES("Synergies:", "à¤¸à¤®à¤¨à¥à¤µà¤¯à¤¹à¤°à¥‚:"),
    PANCHA_NO_CHART_DESC("Create or select a birth chart to analyze Panch Mahapurusha Yogas.", "à¤ªà¤žà¥à¤š à¤®à¤¹à¤¾à¤ªà¥à¤°à¥à¤· à¤¯à¥‹à¤—à¤¹à¤°à¥‚ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤°à¥à¤¨ à¤œà¤¨à¥à¤® à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤¸à¤¿à¤°à¥à¤œà¤¨à¤¾ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥ à¤µà¤¾ à¤›à¤¾à¤¨à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),

    // ============================================
    // NAKSHATRA SCREEN
    // ============================================
    NAKSHATRA_NATURE_FIXED("Fixed (Dhruva)", "à¤¸à¥à¤¥à¤¿à¤° (à¤§à¥à¤°à¥à¤µ)"),
    NAKSHATRA_NATURE_MOVEABLE("Moveable (Chara)", "à¤šà¤°"),
    NAKSHATRA_NATURE_SHARP("Sharp (Tikshna)", "à¤¤à¥€à¤•à¥à¤·à¥à¤£"),
    NAKSHATRA_NATURE_SOFT("Soft (Mridu)", "à¤®à¥ƒà¤¦à¥"),
    NAKSHATRA_NATURE_MIXED("Mixed (Mishra)", "à¤®à¤¿à¤¶à¥à¤°"),
    NAKSHATRA_NATURE_LIGHT("Light (Laghu)", "à¤²à¤˜à¥"),
    NAKSHATRA_NATURE_FIERCE("Fierce (Ugra)", "à¤‰à¤—à¥à¤°"),

    NAKSHATRA_GANA_DEVA("Deva (Divine)", "à¤¦à¥‡à¤µ"),
    NAKSHATRA_GANA_MANUSHYA("Manushya (Human)", "à¤®à¤¨à¥à¤·à¥à¤¯"),
    NAKSHATRA_GANA_RAKSHASA("Rakshasa (Demon)", "à¤°à¤¾à¤•à¥à¤·à¤¸"),

    NAKSHATRA_ELEMENT_FIRE("Fire (Agni)", "à¤…à¤—à¥à¤¨à¤¿"),
    NAKSHATRA_ELEMENT_EARTH("Earth (Prithvi)", "à¤ªà¥ƒà¤¥à¥à¤µà¥€"),
    NAKSHATRA_ELEMENT_AIR("Air (Vayu)", "à¤µà¤¾à¤¯à¥"),
    NAKSHATRA_ELEMENT_WATER("Water (Jala)", "à¤œà¤²"),
    NAKSHATRA_ELEMENT_ETHER("Ether (Akasha)", "à¤†à¤•à¤¾à¤¶"),

    NAKSHATRA_CASTE_BRAHMIN("Brahmin", "à¤¬à¥à¤°à¤¾à¤¹à¥à¤®à¤£"),
    NAKSHATRA_CASTE_KSHATRIYA("Kshatriya", "à¤•à¥à¤·à¤¤à¥à¤°à¤¿à¤¯"),
    NAKSHATRA_CASTE_VAISHYA("Vaishya", "à¤µà¥ˆà¤¶à¥à¤¯"),
    NAKSHATRA_CASTE_SHUDRA("Shudra", "à¤¶à¥‚à¤¦à¥à¤°"),

    NAKSHATRA_GENDER_MALE("Male", "à¤ªà¥à¤°à¥à¤·"),
    NAKSHATRA_GENDER_FEMALE("Female", "à¤¸à¥à¤¤à¥à¤°à¥€"),
    NAKSHATRA_GENDER_NEUTRAL("Neutral", "à¤¨à¤ªà¥à¤‚à¤¸à¤•"),

    NAKSHATRA_DOSHA_VATA("Vata", "à¤µà¤¾à¤¤"),
    NAKSHATRA_DOSHA_PITTA("Pitta", "à¤ªà¤¿à¤¤à¥à¤¤"),
    NAKSHATRA_DOSHA_KAPHA("Kapha", "à¤•à¤«"),

    NAKSHATRA_RAJJU_PAADA("Paada", "à¤ªà¤¾à¤¦"),
    NAKSHATRA_RAJJU_KATI("Kati", "à¤•à¤Ÿà¤¿"),
    NAKSHATRA_RAJJU_NABHI("Nabhi", "à¤¨à¤¾à¤­à¤¿"),
    NAKSHATRA_RAJJU_KANTHA("Kantha", "à¤•à¤£à¥à¤ "),
    NAKSHATRA_RAJJU_SHIRO("Shiro", "à¤¶à¤¿à¤°à¥‹"),
    
    // Tarabala
    TARABALA_JANMA("Janma", "à¤œà¤¨à¥à¤®"),
    TARABALA_SAMPAT("Sampat", "à¤¸à¤®à¥à¤ªà¤¤"),
    TARABALA_VIPAT("Vipat", "à¤µà¤¿à¤«à¤¤"),
    TARABALA_KSHEMA("Kshema", "à¤•à¥à¤·à¥‡à¤®"),
    TARABALA_PRATYARI("Pratyari", "à¤ªà¥à¤°à¤¤à¥à¤¯à¤°à¤¿"),
    TARABALA_SADHAKA("Sadhaka", "à¤¸à¤¾à¤§à¤•"),
    TARABALA_VADHA("Vadha", "à¤µà¤§"),
    TARABALA_MITRA("Mitra", "à¤®à¤¿à¤¤à¥à¤°"),
    TARABALA_PARAMA_MITRA("Parama Mitra", "à¤ªà¤°à¤® à¤®à¤¿à¤¤à¥à¤°"),
    
    // Tarabala Descriptions
    TARABALA_DESC_JANMA("Birth star - challenging for new beginnings", "à¤œà¤¨à¥à¤® à¤¨à¤•à¥à¤·à¤¤à¥à¤° - à¤¨à¤¯à¤¾à¤ à¤¸à¥à¤°à¥à¤µà¤¾à¤¤à¥€à¤•à¤¾ à¤²à¤¾à¤—à¤¿ à¤šà¥à¤¨à¥Œà¤¤à¥€à¤ªà¥‚à¤°à¥à¤£"),
    TARABALA_DESC_SAMPAT("Wealth - favorable for financial matters", "à¤¸à¤®à¥à¤ªà¤¤à¥à¤¤à¤¿ - à¤†à¤°à¥à¤¥à¤¿à¤• à¤®à¤¾à¤®à¤¿à¤²à¤¾à¤•à¤¾ à¤²à¤¾à¤—à¤¿ à¤¶à¥à¤­"),
    TARABALA_DESC_VIPAT("Danger - avoid important activities", "à¤–à¤¤à¤°à¤¾ - à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤•à¤¾à¤°à¥à¤¯à¤¹à¤°à¥‚ à¤¨à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    TARABALA_DESC_KSHEMA("Well-being - favorable for health matters", "à¤•à¤²à¥à¤¯à¤¾à¤£ - à¤¸à¥à¤µà¤¾à¤¸à¥à¤¥à¥à¤¯ à¤®à¤¾à¤®à¤¿à¤²à¤¾à¤•à¤¾ à¤²à¤¾à¤—à¤¿ à¤¶à¥à¤­"),
    TARABALA_DESC_PRATYARI("Obstacle - creates hindrances", "à¤¬à¤¾à¤§à¤¾ - à¤…à¤µà¤°à¥‹à¤§à¤¹à¤°à¥‚ à¤¸à¤¿à¤°à¥à¤œà¤¨à¤¾ à¤—à¤°à¥à¤¦à¤›"),
    TARABALA_DESC_SADHAKA("Achievement - good for goals", "à¤‰à¤ªà¤²à¤¬à¥à¤§à¤¿ - à¤²à¤•à¥à¤·à¥à¤¯à¤•à¤¾ à¤²à¤¾à¤—à¤¿ à¤°à¤¾à¤®à¥à¤°à¥‹"),
    TARABALA_DESC_VADHA("Death - highly inauspicious", "à¤µà¤§ - à¤…à¤¤à¥à¤¯à¤¨à¥à¤¤ à¤…à¤¶à¥à¤­"),
    TARABALA_DESC_MITRA("Friend - favorable for relationships", "à¤®à¤¿à¤¤à¥à¤° - à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤•à¤¾ à¤²à¤¾à¤—à¤¿ à¤¶à¥à¤­"),
    TARABALA_DESC_PARAMA_MITRA("Great Friend - highly auspicious", "à¤ªà¤°à¤® à¤®à¤¿à¤¤à¥à¤° - à¤…à¤¤à¥à¤¯à¤¨à¥à¤¤ à¤¶à¥à¤­"),

    // Misc Nakshatra
    NAKSHATRA_REMEDY_TITLE("Remedies for %s", "%s à¤•à¤¾ à¤²à¤¾à¤—à¤¿ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    NAKSHATRA_REMEDY_DESC("Personalized remedies based on your birth nakshatra to enhance positive qualities and mitigate challenges.", "à¤¤à¤ªà¤¾à¤‡à¤à¤•à¥‹ à¤œà¤¨à¥à¤® à¤¨à¤•à¥à¤·à¤¤à¥à¤°à¤®à¤¾ à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤µà¥à¤¯à¤•à¥à¤¤à¤¿à¤—à¤¤ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤¸à¤•à¤¾à¤°à¤¾à¤¤à¥à¤®à¤• à¤—à¥à¤£à¤¹à¤°à¥‚ à¤¬à¤¢à¤¾à¤‰à¤¨ à¤° à¤šà¥à¤¨à¥Œà¤¤à¥€à¤¹à¤°à¥‚ à¤•à¤® à¤—à¤°à¥à¤¨à¥¤"),
    NAKSHATRA_REMEDY_TIMING("Best performed during %s hora or on %s", "%s à¤¹à¥‹à¤°à¤¾ à¤µà¤¾ %s à¤®à¤¾ à¤‰à¤¤à¥à¤¤à¤®"),
    NAKSHATRA_BODY_PART_FEET("Feet", "à¤ªà¤¾à¤‰"),
    NAKSHATRA_BODY_PART_WAIST("Waist", "à¤•à¤®à¥à¤®à¤°"),
    NAKSHATRA_BODY_PART_NAVEL("Navel", "à¤¨à¤¾à¤­à¤¿"),
    NAKSHATRA_BODY_PART_NECK("Neck", "à¤˜à¤¾à¤à¤Ÿà¥€"),
    NAKSHATRA_BODY_PART_HEAD("Head", "à¤Ÿà¤¾à¤‰à¤•à¥‹"),
    
    // Gemstones
    GEM_RUBY("Ruby", "à¤®à¤¾à¤£à¤¿à¤•"),
    GEM_PEARL("Pearl", "à¤®à¥‹à¤¤à¥€"),
    GEM_RED_CORAL("Red Coral", "à¤®à¥à¤—à¤¾"),
    GEM_EMERALD("Emerald", "à¤ªà¤¨à¥à¤¨à¤¾"),
    GEM_YELLOW_SAPPHIRE("Yellow Sapphire", "à¤ªà¥à¤·à¥à¤ªà¤°à¤¾à¤œ"),
    GEM_DIAMOND("Diamond", "à¤¹à¥€à¤°à¤¾"),
    GEM_BLUE_SAPPHIRE("Blue Sapphire", "à¤¨à¥€à¤²à¤®"),
    GEM_HESSONITE("Hessonite", "à¤—à¥‹à¤®à¥‡à¤¦"),
    GEM_CATS_EYE("Cat's Eye", "à¤²à¤¹à¤¸à¥à¤¨à¤¿à¤¯à¤¾"),

    // Colors
    COLOR_ORANGE("Orange", "à¤¸à¥à¤¨à¥à¤¤à¤²à¤¾"),
    COLOR_GOLD("Gold", "à¤¸à¥à¤¨à¥Œà¤²à¥‹"),
    COLOR_RED("Red", "à¤°à¤¾à¤¤à¥‹"),
    COLOR_WHITE("White", "à¤¸à¥‡à¤¤à¥‹"),
    COLOR_SILVER("Silver", "à¤šà¤¾à¤à¤¦à¥€"),
    COLOR_CORAL("Coral", "à¤®à¥à¤—à¤¾ à¤°à¤‚à¤—"),
    COLOR_SCARLET("Scarlet", "à¤¸à¤¿à¤¨à¥à¤¦à¥‚à¤°à¥‡"),
    COLOR_GREEN("Green", "à¤¹à¤°à¤¿à¤¯à¥‹"),
    COLOR_EMERALD("Emerald", "à¤ªà¤¨à¥à¤¨à¤¾"),
    COLOR_TURQUOISE("Turquoise", "à¤«à¤¿à¤°à¥‹à¤œà¤¾"),
    COLOR_YELLOW("Yellow", "à¤¹à¥‡à¤²à¥‹à¤‚"),
    COLOR_PINK("Pink", "à¤—à¥à¤²à¤¾à¤¬à¥€"),
    COLOR_PASTEL("Pastel", "à¤ªà¤¾à¤¸à¥à¤Ÿà¥‡à¤²"),
    COLOR_BLUE("Blue", "à¤¨à¥€à¤²à¥‹"),
    COLOR_BLACK("Black", "à¤•à¤¾à¤²à¥‹"),
    COLOR_DARK("Dark colors", "à¤—à¤¾à¤¢à¤¾ à¤°à¤‚à¤—à¤¹à¤°à¥‚"),
    COLOR_SMOKY("Smoky", "à¤§à¥à¤µà¤¾à¤"),
    COLOR_GREY("Grey", "à¤–à¤°à¤¾à¤¨à¥€"),
    COLOR_MIXED("Mixed colors", "à¤®à¤¿à¤¶à¥à¤°à¤¿à¤¤ à¤°à¤‚à¤—à¤¹à¤°à¥‚"),
    COLOR_BROWN("Brown", "à¤–à¥ˆà¤°à¥‹"),
    COLOR_MULTI("Multi-colored", "à¤¬à¤¹à¥à¤°à¤‚à¤—à¥€"),
    
    // Days
    DAY_SUNDAY("Sunday", "à¤†à¤‡à¤¤à¤¬à¤¾à¤°"),
    DAY_MONDAY("Monday", "à¤¸à¥‹à¤®à¤¬à¤¾à¤°"),
    DAY_TUESDAY("Tuesday", "à¤®à¤™à¥à¤—à¤²à¤¬à¤¾à¤°"),
    DAY_WEDNESDAY("Wednesday", "à¤¬à¥à¤§à¤¬à¤¾à¤°"),
    DAY_THURSDAY("Thursday", "à¤¬à¤¿à¤¹à¥€à¤¬à¤¾à¤°"),
    DAY_FRIDAY("Friday", "à¤¶à¥à¤•à¥à¤°à¤¬à¤¾à¤°"),
    DAY_SATURDAY("Saturday", "à¤¶à¤¨à¤¿à¤¬à¤¾à¤°"),
    DAY_ANY("Any day", "à¤•à¥à¤¨à¥ˆ à¤ªà¤¨à¤¿ à¤¦à¤¿à¤¨"),
    
    // ============================================
    // GOCHARA VEDHA SCREEN
    // ============================================
    GOCHARA_SCREEN_TITLE("Gochara Vedha", "à¤—à¥‹à¤šà¤° à¤µà¥‡à¤§"),
    GOCHARA_SUBTITLE("Transit Obstructions", "à¤—à¥‹à¤šà¤° à¤…à¤µà¤°à¥‹à¤§à¤¹à¤°à¥‚"),
    GOCHARA_ABOUT_TITLE("About Gochara Vedha", "à¤—à¥‹à¤šà¤° à¤µà¥‡à¤§à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    GOCHARA_ABOUT_DESC("Gochara refers to planetary transits, while Vedha means \"obstruction\" in Sanskrit.\n\nWhen a planet transits through a favorable house from your natal Moon, its positive effects can be blocked (Vedha) by another planet positioned in specific houses.\n\nKey concepts:\nâ€¢ Each house has specific Vedha points\nâ€¢ Benefic transits can be nullified by malefic planets\nâ€¢ The severity of Vedha varies based on planetary combinations\nâ€¢ Understanding Vedha helps predict when good transits may not deliver expected results\n\nThis analysis helps you identify periods when planetary energies are blocked and plan accordingly.", "à¤—à¥‹à¤šà¤° à¤­à¤¨à¥à¤¨à¤¾à¤²à¥‡ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤•à¥‹ à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤šà¤¾à¤²à¤²à¤¾à¤ˆ à¤œà¤¨à¤¾à¤‰à¤à¤›, à¤œà¤¬à¤•à¤¿ à¤µà¥‡à¤§à¤•à¥‹ à¤…à¤°à¥à¤¥ à¤¸à¤‚à¤¸à¥à¤•à¥ƒà¤¤à¤®à¤¾ \"à¤…à¤µà¤°à¥‹à¤§\" à¤¹à¥‹à¥¤\n\nà¤œà¤¬ à¤•à¥à¤¨à¥ˆ à¤—à¥à¤°à¤¹ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤œà¤¨à¥à¤® à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤¬à¤¾à¤Ÿ à¤¶à¥à¤­ à¤­à¤¾à¤µà¤®à¤¾ à¤—à¥‹à¤šà¤° à¤—à¤°à¥à¤›, à¤¯à¤¸à¤•à¥‹ à¤¸à¤•à¤¾à¤°à¤¾à¤¤à¥à¤®à¤• à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚ à¤µà¤¿à¤¶à¤¿à¤·à¥à¤Ÿ à¤­à¤¾à¤µà¤¹à¤°à¥‚à¤®à¤¾ à¤°à¤¹à¥‡à¤•à¤¾ à¤…à¤¨à¥à¤¯ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤¦à¥à¤µà¤¾à¤°à¤¾ à¤…à¤µà¤°à¥‹à¤§à¤¿à¤¤ (à¤µà¥‡à¤§) à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›à¤¨à¥à¥¤\n\nà¤®à¥à¤–à¥à¤¯ à¤…à¤µà¤§à¤¾à¤°à¤£à¤¾à¤¹à¤°à¥‚:\nâ€¢ à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤­à¤¾à¤µà¤•à¤¾ à¤µà¤¿à¤¶à¤¿à¤·à¥à¤Ÿ à¤µà¥‡à¤§ à¤¬à¤¿à¤¨à¥à¤¦à¥à¤¹à¤°à¥‚ à¤¹à¥à¤¨à¥à¤›à¤¨à¥\nâ€¢ à¤¶à¥à¤­ à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚ à¤ªà¤¾à¤ª à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤¦à¥à¤µà¤¾à¤°à¤¾ à¤¨à¤¿à¤·à¥à¤«à¤² à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›à¤¨à¥\nâ€¢ à¤µà¥‡à¤§à¤•à¥‹ à¤—à¤®à¥à¤­à¥€à¤°à¤¤à¤¾ à¤—à¥à¤°à¤¹ à¤¸à¤‚à¤¯à¥‹à¤œà¤¨à¤¹à¤°à¥‚à¤®à¤¾ à¤†à¤§à¤¾à¤°à¤¿à¤¤ à¤¹à¥à¤¨à¥à¤›\nâ€¢ à¤µà¥‡à¤§ à¤¬à¥à¤à¥à¤¨à¤¾à¤²à¥‡ à¤°à¤¾à¤®à¥à¤°à¥‹ à¤—à¥‹à¤šà¤°à¤²à¥‡ à¤•à¤¹à¤¿à¤²à¥‡ à¤…à¤ªà¥‡à¤•à¥à¤·à¤¿à¤¤ à¤¨à¤¤à¤¿à¤œà¤¾ à¤¨à¤¦à¤¿à¤¨ à¤¸à¤•à¥à¤› à¤­à¤¨à¥€ à¤…à¤¨à¥à¤®à¤¾à¤¨ à¤—à¤°à¥à¤¨ à¤®à¤¦à¥à¤¦à¤¤ à¤—à¤°à¥à¤›\n\nà¤¯à¥‹ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£à¤²à¥‡ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤²à¤¾à¤ˆ à¤—à¥à¤°à¤¹ à¤‰à¤°à¥à¤œà¤¾à¤¹à¤°à¥‚ à¤•à¤¹à¤¿à¤²à¥‡ à¤…à¤µà¤°à¥‹à¤§à¤¿à¤¤ à¤¹à¥à¤¨à¥à¤›à¤¨à¥ à¤­à¤¨à¥€ à¤ªà¤¹à¤¿à¤šà¤¾à¤¨ à¤—à¤°à¥à¤¨ à¤° à¤¸à¥‹à¤¹à¥€ à¤…à¤¨à¥à¤¸à¤¾à¤° à¤¯à¥‹à¤œà¤¨à¤¾ à¤¬à¤¨à¤¾à¤‰à¤¨ à¤®à¤¦à¥à¤¦à¤¤ à¤—à¤°à¥à¤›à¥¤"),
    GOCHARA_SCORE("Overall Transit Score", "à¤¸à¤®à¤—à¥à¤° à¤—à¥‹à¤šà¤° à¤…à¤™à¥à¤•"),
    GOCHARA_FAVORABLE("Favorable", "à¤¶à¥à¤­"),
    GOCHARA_OBSTRUCTED("Obstructed", "à¤…à¤µà¤°à¥‹à¤§à¤¿à¤¤"),
    GOCHARA_MOON_SIGN("Natal Moon Sign", "à¤œà¤¨à¥à¤® à¤°à¤¾à¤¶à¥€"),
    GOCHARA_ACTIVE_VEDHAS("Active Vedhas", "à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤µà¥‡à¤§"),
    GOCHARA_TRANSITS_TRACKED("Transits Tracked", "à¤Ÿà¥à¤°à¥à¤¯à¤¾à¤• à¤—à¤°à¤¿à¤à¤•à¤¾ à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚"),
    GOCHARA_CURRENT_TRANSITS("Current Planetary Transits", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤—à¥à¤°à¤¹ à¤—à¥‹à¤šà¤°à¤¹à¤°à¥‚"),
    GOCHARA_NATURALLY_FAVORABLE("Naturally Favorable", "à¤¸à¥à¤µà¤¾à¤­à¤¾à¤µà¤¿à¤• à¤°à¥‚à¤ªà¤®à¤¾ à¤¶à¥à¤­"),
    GOCHARA_NATURALLY_UNFAVORABLE("Naturally Unfavorable", "à¤¸à¥à¤µà¤¾à¤­à¤¾à¤µà¤¿à¤• à¤°à¥‚à¤ªà¤®à¤¾ à¤…à¤¶à¥à¤­"),
    GOCHARA_VEDHA_LABEL("VEDHA", "à¤µà¥‡à¤§"),
    GOCHARA_TRANSIT_EFFECTS("Transit Effects", "à¤—à¥‹à¤šà¤° à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    GOCHARA_VEDHA_OBSTRUCTION("Vedha Obstruction", "à¤µà¥‡à¤§ à¤…à¤µà¤°à¥‹à¤§"),
    GOCHARA_FROM("From:", "à¤¬à¤¾à¤Ÿ:"),
    GOCHARA_SEVERITY("Severity:", "à¤—à¤®à¥à¤­à¥€à¤°à¤¤à¤¾:"),
    GOCHARA_NO_CHART_DESC("Create or select a birth chart to analyze Gochara Vedha effects.", "à¤—à¥‹à¤šà¤° à¤µà¥‡à¤§ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤°à¥à¤¨ à¤œà¤¨à¥à¤® à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤¸à¤¿à¤°à¥à¤œà¤¨à¤¾ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥ à¤µà¤¾ à¤›à¤¾à¤¨à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    GOCHARA_VEDHAS("Vedhas", "à¤µà¥‡à¤§à¤¹à¤°à¥‚"),
    GOCHARA_FORECAST("Forecast", "à¤ªà¥‚à¤°à¥à¤µà¤¾à¤¨à¥à¤®à¤¾à¤¨"),

    // ============================================
    // DASHA SANDHI SCREEN
    // ============================================
    SANDHI_SCREEN_TITLE("Dasha Sandhi", "à¤¦à¤¶à¤¾ à¤¸à¤¨à¥à¤§à¤¿"),
    SANDHI_SUBTITLE("Period Transitions", "à¤¦à¤¶à¤¾ à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨à¤¹à¤°à¥‚"),
    SANDHI_ABOUT_TITLE("About Dasha Sandhi", "à¤¦à¤¶à¤¾ à¤¸à¤¨à¥à¤§à¤¿à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    SANDHI_ABOUT_DESC("Dasha Sandhi refers to the junction or transition point between two planetary periods (Dashas) in Vedic astrology.\n\nThese transition periods are considered sensitive times when:\nâ€¢ The energy shifts from one planetary influence to another\nâ€¢ Both planets' effects may be felt simultaneously\nâ€¢ Major life changes often occur\nâ€¢ Careful planning is advised\n\nThe intensity of a Sandhi depends on:\nâ€¢ The nature of the transitioning planets\nâ€¢ Their relationship in your chart\nâ€¢ Current transits and aspects\n\nUnderstanding your Sandhi periods helps you prepare for and navigate these significant life transitions.", "à¤¦à¤¶à¤¾ à¤¸à¤¨à¥à¤§à¤¿ à¤­à¤¨à¥à¤¨à¤¾à¤²à¥‡ à¤µà¥ˆà¤¦à¤¿à¤• à¤œà¥à¤¯à¥‹à¤¤à¤¿à¤·à¤®à¤¾ à¤¦à¥à¤ˆ à¤—à¥à¤°à¤¹ à¤¦à¤¶à¤¾ (à¤¸à¤®à¤¯ à¤…à¤µà¤§à¤¿) à¤¬à¥€à¤šà¤•à¥‹ à¤®à¤¿à¤²à¤¨ à¤µà¤¾ à¤¸à¤‚à¤•à¥à¤°à¤®à¤£ à¤¬à¤¿à¤¨à¥à¤¦à¥à¤²à¤¾à¤ˆ à¤œà¤¨à¤¾à¤‰à¤à¤›à¥¤\n\nà¤¯à¥€ à¤¸à¤‚à¤•à¥à¤°à¤®à¤£ à¤…à¤µà¤§à¤¿à¤¹à¤°à¥‚à¤²à¤¾à¤ˆ à¤¸à¤‚à¤µà¥‡à¤¦à¤¨à¤¶à¥€à¤² à¤¸à¤®à¤¯ à¤®à¤¾à¤¨à¤¿à¤¨à¥à¤› à¤œà¤¬:\nâ€¢ à¤Šà¤°à¥à¤œà¤¾ à¤à¤• à¤—à¥à¤°à¤¹à¤•à¥‹ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¬à¤¾à¤Ÿ à¤…à¤°à¥à¤•à¥‹à¤®à¤¾ à¤¸à¤°à¥à¤›\nâ€¢ à¤¦à¥à¤¬à¥ˆ à¤—à¥à¤°à¤¹à¤•à¤¾ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚ à¤à¤• à¤¸à¤¾à¤¥ à¤®à¤¹à¤¸à¥à¤¸ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›\nâ€¢ à¤…à¤•à¥à¤¸à¤° à¤ à¥‚à¤²à¤¾ à¤œà¥€à¤µà¤¨ à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨à¤¹à¤°à¥‚ à¤¹à¥à¤¨à¥à¤›à¤¨à¥\nâ€¢ à¤¸à¤¾à¤µà¤§à¤¾à¤¨à¥€à¤ªà¥‚à¤°à¥à¤µà¤• à¤¯à¥‹à¤œà¤¨à¤¾ à¤¬à¤¨à¤¾à¤‰à¤¨ à¤¸à¤²à¥à¤²à¤¾à¤¹ à¤¦à¤¿à¤‡à¤¨à¥à¤›\n\nà¤¸à¤¨à¥à¤§à¤¿à¤•à¥‹ à¤¤à¥€à¤µà¥à¤°à¤¤à¤¾ à¤¯à¤¸à¤®à¤¾ à¤¨à¤¿à¤°à¥à¤­à¤° à¤—à¤°à¥à¤¦à¤›:\nâ€¢ à¤¸à¤‚à¤•à¥à¤°à¤®à¤£ à¤­à¤‡à¤°à¤¹à¥‡à¤•à¤¾ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤•à¥‹ à¤ªà¥à¤°à¤•à¥ƒà¤¤à¤¿\nâ€¢ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾ à¤¤à¤¿à¤¨à¥€à¤¹à¤°à¥‚à¤•à¥‹ à¤¸à¤®à¥à¤¬à¤¨à¥à¤§\nâ€¢ à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤—à¥‹à¤šà¤° à¤° à¤¦à¥ƒà¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚\n\nà¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤¸à¤¨à¥à¤§à¤¿ à¤…à¤µà¤§à¤¿à¤¹à¤°à¥‚ à¤¬à¥à¤à¥à¤¨à¤¾à¤²à¥‡ à¤¤à¤ªà¤¾à¤ˆà¤‚à¤²à¤¾à¤ˆ à¤¯à¥€ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤œà¥€à¤µà¤¨ à¤¸à¤‚à¤•à¥à¤°à¤®à¤£à¤¹à¤°à¥‚à¤•à¥‹ à¤¤à¤¯à¤¾à¤°à¥€ à¤—à¤°à¥à¤¨ à¤° à¤¨à¥‡à¤­à¤¿à¤—à¥‡à¤Ÿ à¤—à¤°à¥à¤¨ à¤®à¤¦à¥à¤¦à¤¤ à¤—à¤°à¥à¤›à¥¤"),
    SANDHI_VOLATILITY("Overall Volatility", "à¤¸à¤®à¤—à¥à¤° à¤…à¤¸à¥à¤¥à¤¿à¤°à¤¤à¤¾"),
    SANDHI_VOLATILITY_DESC("Based on current and upcoming period transitions", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨ à¤° à¤†à¤—à¤¾à¤®à¥€ à¤¦à¤¶à¤¾ à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨à¤¹à¤°à¥‚à¤®à¤¾ à¤†à¤§à¤¾à¤°à¤¿à¤¤"),
    SANDHI_ACTIVE("Active", "à¤¸à¤•à¥à¤°à¤¿à¤¯"),
    SANDHI_UPCOMING("Upcoming", "à¤†à¤—à¤¾à¤®à¥€"),
    SANDHI_RECENT("Recent", "à¤¹à¤¾à¤²à¤¸à¤¾à¤²à¥ˆà¤•à¥‹"),
    SANDHI_GUIDANCE("General Guidance", "à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤®à¤¾à¤°à¥à¤—à¤¦à¤°à¥à¤¶à¤¨"),
    SANDHI_CURRENT_ACTIVE("Currently in Sandhi Period", "à¤µà¤°à¥à¤¤à¤®à¤¾à¤¨à¤®à¤¾ à¤¸à¤¨à¥à¤§à¤¿ à¤…à¤µà¤§à¤¿à¤®à¤¾"),
    SANDHI_INTENSITY("Intensity:", "à¤¤à¥€à¤µà¥à¤°à¤¤à¤¾:"),
    SANDHI_ENDS("Ends:", "à¤…à¤¨à¥à¤¤à¥à¤¯:"),
    SANDHI_NO_ACTIVE("No Active Sandhi", "à¤•à¥à¤¨à¥ˆ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤¸à¤¨à¥à¤§à¤¿ à¤›à¥ˆà¤¨"),
    SANDHI_NO_ACTIVE_DESC("You are not currently in a Dasha Sandhi period. Check the Upcoming tab for future transitions.", "à¤¤à¤ªà¤¾à¤ˆà¤‚ à¤¹à¤¾à¤² à¤¦à¤¶à¤¾ à¤¸à¤¨à¥à¤§à¤¿ à¤…à¤µà¤§à¤¿à¤®à¤¾ à¤¹à¥à¤¨à¥à¤¹à¥à¤¨à¥à¤¨à¥¤ à¤­à¤µà¤¿à¤·à¥à¤¯à¤•à¤¾ à¤ªà¤°à¤¿à¤µà¤°à¥à¤¤à¤¨à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤†à¤—à¤¾à¤®à¥€ à¤Ÿà¥à¤¯à¤¾à¤¬ à¤œà¤¾à¤à¤š à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    SANDHI_PREDICTIONS("Predictions", "à¤­à¤µà¤¿à¤·à¥à¤¯à¤µà¤¾à¤£à¥€à¤¹à¤°à¥‚"),
    SANDHI_IMPACTS("Life Area Impacts", "à¤œà¥€à¤µà¤¨ à¤•à¥à¤·à¥‡à¤¤à¥à¤° à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    SANDHI_REMEDIES("Recommended Remedies", "à¤¸à¤¿à¤«à¤¾à¤°à¤¿à¤¸ à¤—à¤°à¤¿à¤à¤•à¤¾ à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚"),
    SANDHI_NO_CHART_DESC("Create or select a birth chart to analyze Dasha Sandhi periods.", "à¤¦à¤¶à¤¾ à¤¸à¤¨à¥à¤§à¤¿ à¤…à¤µà¤§à¤¿à¤¹à¤°à¥‚ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤°à¥à¤¨ à¤œà¤¨à¥à¤® à¤•à¥à¤£à¥à¤¡à¤²à¥€ à¤¸à¤¿à¤°à¥à¤œà¤¨à¤¾ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥ à¤µà¤¾ à¤›à¤¾à¤¨à¥à¤¨à¥à¤¹à¥‹à¤¸à¥à¥¤"),
    SANDHI_NO_UPCOMING("No upcoming Sandhi periods in the analysis window.", "à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤…à¤µà¤§à¤¿à¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤†à¤—à¤¾à¤®à¥€ à¤¸à¤¨à¥à¤§à¤¿ à¤…à¤µà¤§à¤¿à¤¹à¤°à¥‚ à¤›à¥ˆà¤¨à¤¨à¥à¥¤"),
    SANDHI_NO_CALENDAR("No calendar entries available.", "à¤•à¥à¤¨à¥ˆ à¤ªà¤¾à¤¤à¥à¤°à¥‹ à¤ªà¥à¤°à¤µà¤¿à¤·à¥à¤Ÿà¤¿à¤¹à¤°à¥‚ à¤‰à¤ªà¤²à¤¬à¥à¤§ à¤›à¥ˆà¤¨à¤¨à¥à¥¤"),

    // Graha Yuddha
    GRAHA_SCREEN_TITLE("Graha Yuddha", "à¤—à¥à¤°à¤¹ à¤¯à¥à¤¦à¥à¤§"),
    GRAHA_SUBTITLE("Planetary War Analysis", "à¤—à¥à¤°à¤¹ à¤¯à¥à¤¦à¥à¤§ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    GRAHA_ACTIVE_WARS("Active Wars", "à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤¯à¥à¤¦à¥à¤§à¤¹à¤°à¥‚"),
    GRAHA_DASHA_EFFECTS("Dasha Effects", "à¤¦à¤¶à¤¾ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    GRAHA_ANALYZING("Analyzing Planetary Wars...", "à¤—à¥à¤°à¤¹ à¤¯à¥à¤¦à¥à¤§ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤—à¤°à¥à¤¦à¥ˆ..."),
    GRAHA_WAR_STATUS("War Status", "à¤¯à¥à¤¦à¥à¤§ à¤¸à¥à¤¥à¤¿à¤¤à¤¿"),
    GRAHA_ANALYSIS_SUMMARY("Analysis Summary", "à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£ à¤¸à¤¾à¤°à¤¾à¤‚à¤¶"),
    GRAHA_OVERALL_IMPACT("Overall War Impact", "à¤¸à¤®à¤—à¥à¤° à¤¯à¥à¤¦à¥à¤§ à¤ªà¥à¤°à¤­à¤¾à¤µ"),
    GRAHA_VICTORY("Victorious", "à¤µà¤¿à¤œà¤¯à¥€"),
    GRAHA_DEFEATED("Defeated", "à¤ªà¤°à¤¾à¤œà¤¿à¤¤"),
    GRAHA_AFFECTED_AREAS("Affected Life Areas", "à¤ªà¥à¤°à¤­à¤¾à¤µà¤¿à¤¤ à¤œà¥€à¤µà¤¨ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚"),
    GRAHA_NO_WARS("No Active Planetary Wars", "à¤•à¥à¤¨à¥ˆ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤—à¥à¤°à¤¹ à¤¯à¥à¤¦à¥à¤§ à¤›à¥ˆà¤¨"),
    GRAHA_NO_WARS_DESC("All planets are operating peacefully", "à¤¸à¤¬à¥ˆ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚ à¤¶à¤¾à¤¨à¥à¤¤à¤¿à¤ªà¥‚à¤°à¥à¤£ à¤°à¥‚à¤ªà¤®à¤¾ à¤•à¤¾à¤® à¤—à¤°à¤¿à¤°à¤¹à¥‡à¤•à¤¾ à¤›à¤¨à¥"),
    GRAHA_VS("vs", "à¤µà¤¿à¤°à¥à¤¦à¥à¤§"),
    GRAHA_SEPARATION("Separation", "à¤…à¤¨à¥à¤¤à¤°"),
    GRAHA_ADVANTAGE("Advantage", "à¤«à¤¾à¤‡à¤¦à¤¾"),
    GRAHA_NO_DASHA_EFFECTS("No War-Related Dasha Effects", "à¤•à¥à¤¨à¥ˆ à¤¯à¥à¤¦à¥à¤§-à¤¸à¤®à¥à¤¬à¤¨à¥à¤§à¤¿à¤¤ à¤¦à¤¶à¤¾ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚ à¤›à¥ˆà¤¨à¤¨à¥"),
    GRAHA_NO_REMEDIES("No Specific Remedies Needed", "à¤•à¥à¤¨à¥ˆ à¤µà¤¿à¤¶à¥‡à¤· à¤‰à¤ªà¤¾à¤¯ à¤†à¤µà¤¶à¥à¤¯à¤• à¤›à¥ˆà¤¨"),
    GRAHA_NO_DASHA_DESC("Planets in War not currently activated in Dasha/Antardasha.", "à¤¯à¥à¤¦à¥à¤§à¤®à¤¾ à¤°à¤¹à¥‡à¤•à¤¾ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚ à¤¹à¤¾à¤² à¤¦à¤¶à¤¾/à¤…à¤¨à¥à¤¤à¤°à¥à¤¦à¤¶à¤¾à¤®à¤¾ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤›à¥ˆà¤¨à¤¨à¥à¥¤"),
    GRAHA_NO_REMEDIES_DESC("Since there are no active planetary wars, remedial measures are not required.", "à¤•à¥à¤¨à¥ˆ à¤¸à¤•à¥à¤°à¤¿à¤¯ à¤—à¥à¤°à¤¹ à¤¯à¥à¤¦à¥à¤§ à¤¨à¤­à¤à¤•à¥‹à¤²à¥‡, à¤‰à¤ªà¤¾à¤¯à¤¹à¤°à¥‚ à¤†à¤µà¤¶à¥à¤¯à¤• à¤›à¥ˆà¤¨à¤¨à¥à¥¤"),
    GRAHA_ABOUT_TITLE("About Graha Yuddha", "à¤—à¥à¤°à¤¹ à¤¯à¥à¤¦à¥à¤§à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    GRAHA_ABOUT_DESC("Graha Yuddha (Planetary War) occurs when two planets (excluding Sun and Moon) are within 1 degree of each other.\n\nThe planet with the lower longitude is usually considered the winner, while the one with higher longitude is the loser.\n\nImpacts:\nâ€¢ The losing planet's significations may suffer\nâ€¢ If the losing planet rules important houses, those areas may be affected\nâ€¢ The war is most intense during close conjunctions", "à¤—à¥à¤°à¤¹ à¤¯à¥à¤¦à¥à¤§ à¤¤à¤¬ à¤¹à¥à¤¨à¥à¤› à¤œà¤¬ à¤¦à¥à¤ˆ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚ (à¤¸à¥‚à¤°à¥à¤¯ à¤° à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤¬à¤¾à¤¹à¥‡à¤•) à¤à¤• à¤…à¤°à¥à¤•à¤¾à¤•à¥‹ à¥§ à¤¡à¤¿à¤—à¥à¤°à¥€ à¤­à¤¿à¤¤à¥à¤° à¤¹à¥à¤¨à¥à¤›à¤¨à¥à¥¤\n\nà¤•à¤® à¤¦à¥‡à¤¶à¤¾à¤¨à¥à¤¤à¤° à¤­à¤à¤•à¥‹ à¤—à¥à¤°à¤¹à¤²à¤¾à¤ˆ à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯à¤¤à¤¯à¤¾ à¤µà¤¿à¤œà¥‡à¤¤à¤¾ à¤®à¤¾à¤¨à¤¿à¤¨à¥à¤›, à¤œà¤¬à¤•à¤¿ à¤‰à¤šà¥à¤š à¤¦à¥‡à¤¶à¤¾à¤¨à¥à¤¤à¤° à¤­à¤à¤•à¥‹ à¤—à¥à¤°à¤¹à¤²à¤¾à¤ˆ à¤ªà¤°à¤¾à¤œà¤¿à¤¤ à¤®à¤¾à¤¨à¤¿à¤¨à¥à¤›à¥¤\n\nà¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚:\nâ€¢ à¤ªà¤°à¤¾à¤œà¤¿à¤¤ à¤—à¥à¤°à¤¹à¤•à¤¾ à¤•à¤¾à¤°à¤•à¤¤à¥à¤µà¤¹à¤°à¥‚à¤®à¤¾ à¤…à¤¸à¤° à¤ªà¤°à¥à¤¨ à¤¸à¤•à¥à¤›\nâ€¢ à¤¯à¤¦à¤¿ à¤ªà¤°à¤¾à¤œà¤¿à¤¤ à¤—à¥à¤°à¤¹à¤²à¥‡ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤­à¤¾à¤µà¤¹à¤°à¥‚à¤•à¥‹ à¤¸à¥à¤µà¤¾à¤®à¥€ à¤¹à¥‹ à¤­à¤¨à¥‡, à¤¤à¥€ à¤•à¥à¤·à¥‡à¤¤à¥à¤°à¤¹à¤°à¥‚ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¿à¤¤ à¤¹à¥à¤¨ à¤¸à¤•à¥à¤›à¤¨à¥\nâ€¢ à¤¨à¤œà¤¿à¤•à¤•à¥‹ à¤¯à¥à¤¤à¤¿à¤®à¤¾ à¤¯à¥à¤¦à¥à¤§ à¤¸à¤¬à¥ˆà¤­à¤¨à¥à¤¦à¤¾ à¤¤à¥€à¤µà¥à¤° à¤¹à¥à¤¨à¥à¤›"),
    GRAHA_DEFEATS_MSG("%1$s defeats %2$s", "%1$s à¤²à¥‡ %2$s à¤²à¤¾à¤ˆ à¤œà¤¿à¤¤à¥à¤›"),
    GRAHA_IN_HOUSE_MSG("in %1$s (House %2$d)", "%1$s (à¤­à¤¾à¤µ %2$d) à¤®à¤¾"),

    // Kemadruma Yoga
    KEMA_SCREEN_TITLE("Kemadruma Yoga", "à¤•à¥‡à¤®à¤¦à¥à¤°à¥à¤® à¤¯à¥‹à¤—"),
    KEMA_SUBTITLE("Moon Isolation Analysis", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤à¤•à¥à¤²à¥‹à¤ªà¤¨ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    KEMA_MOON("Moon", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾"),
    KEMA_CANCELLATIONS("Cancellations", "à¤­à¤‚à¤—"),
    KEMA_IMPACTS("Impacts", "à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚"),
    KEMA_FORMED("Kemadruma Yoga is formed in this chart", "à¤¯à¥‹ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾ à¤•à¥‡à¤®à¤¦à¥à¤°à¥à¤® à¤¯à¥‹à¤— à¤¬à¤¨à¥‡à¤•à¥‹ à¤›"),
    KEMA_NOT_FORMED("Kemadruma Yoga is not formed", "à¤•à¥‡à¤®à¤¦à¥à¤°à¥à¤® à¤¯à¥‹à¤— à¤¬à¤¨à¥‡à¤•à¥‹ à¤›à¥ˆà¤¨"),
    KEMA_FORMATION_DETAILS("Formation Details", "à¤¯à¥‹à¤— à¤¨à¤¿à¤°à¥à¤®à¤¾à¤£ à¤µà¤¿à¤µà¤°à¤£"),
    KEMA_MOON_POSITION("Moon Position", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤•à¥‹ à¤¸à¥à¤¥à¤¿à¤¤à¤¿"),
    KEMA_SURROUNDING_HOUSES("Surrounding Houses Analysis", "à¤µà¤°à¤ªà¤°à¤•à¤¾ à¤­à¤¾à¤µà¤¹à¤°à¥‚à¤•à¥‹ à¤µà¤¿à¤¶à¥à¤²à¥‡à¤·à¤£"),
    KEMA_BEFORE_MOON("Before Moon", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤…à¤˜à¤¿"),
    KEMA_AFTER_MOON("After Moon", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤ªà¤›à¤¿"),
    KEMA_MOON_HOUSE("Moon's House", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤•à¥‹ à¤­à¤¾à¤µ"),
    KEMA_CONDITION_MET("No planets in houses adjacent to Moon - Kemadruma condition met", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤•à¥‹ à¤¨à¤œà¤¿à¤•à¤•à¤¾ à¤­à¤¾à¤µà¤¹à¤°à¥‚à¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤—à¥à¤°à¤¹ à¤›à¥ˆà¤¨ - à¤•à¥‡à¤®à¤¦à¥à¤°à¥à¤® à¤¶à¤°à¥à¤¤ à¤ªà¥‚à¤°à¤¾ à¤­à¤¯à¥‹"),
    KEMA_NO_CANCELLATIONS("No Cancellations Found", "à¤•à¥à¤¨à¥ˆ à¤­à¤‚à¤— à¤«à¥‡à¤²à¤¾ à¤ªà¤°à¥‡à¤¨"),
    KEMA_NA("Not Applicable", "à¤²à¤¾à¤—à¥‚ à¤¹à¥à¤à¤¦à¥ˆà¤¨"),
    KEMA_NO_CANCELLATIONS_DESC("No Kemadruma Bhanga (cancellation) factors were found in your chart.", "à¤¤à¤ªà¤¾à¤ˆà¤‚à¤•à¥‹ à¤•à¥à¤£à¥à¤¡à¤²à¥€à¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤•à¥‡à¤®à¤¦à¥à¤°à¥à¤® à¤­à¤‚à¤— à¤•à¤¾à¤°à¤•à¤¹à¤°à¥‚ à¤«à¥‡à¤²à¤¾ à¤ªà¤°à¥‡à¤¨à¤¨à¥à¥¤"),
    KEMA_NO_IMPACTS("No Negative Impacts", "à¤•à¥à¤¨à¥ˆ à¤¨à¤•à¤¾à¤°à¤¾à¤¤à¥à¤®à¤• à¤ªà¥à¤°à¤­à¤¾à¤µ à¤›à¥ˆà¤¨"),
    KEMA_NO_IMPACTS_DESC("Moon is well-supported, ensuring emotional stability and mental peace.", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤°à¤¾à¤®à¥à¤°à¥‹à¤¸à¤à¤— à¤¸à¤®à¤°à¥à¤¥à¤¿à¤¤ à¤›, à¤œà¤¸à¤²à¥‡ à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤¸à¥à¤¥à¤¿à¤°à¤¤à¤¾ à¤° à¤®à¤¾à¤¨à¤¸à¤¿à¤• à¤¶à¤¾à¤¨à¥à¤¤à¤¿ à¤¸à¥à¤¨à¤¿à¤¶à¥à¤šà¤¿à¤¤ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    KEMA_NO_REMEDIES("No Remedies Needed", "à¤•à¥à¤¨à¥ˆ à¤‰à¤ªà¤¾à¤¯ à¤†à¤µà¤¶à¥à¤¯à¤• à¤›à¥ˆà¤¨"),
    KEMA_NO_REMEDIES_DESC("Since Kemadruma Yoga is not formed or is fully cancelled, no remedies are required.", "à¤•à¥‡à¤®à¤¦à¥à¤°à¥à¤® à¤¯à¥‹à¤— à¤¨à¤¬à¤¨à¥‡à¤•à¥‹ à¤µà¤¾ à¤ªà¥‚à¤°à¥à¤£ à¤°à¥‚à¤ªà¤®à¤¾ à¤­à¤‚à¤— à¤­à¤à¤•à¥‹à¤²à¥‡, à¤•à¥à¤¨à¥ˆ à¤‰à¤ªà¤¾à¤¯ à¤†à¤µà¤¶à¥à¤¯à¤• à¤›à¥ˆà¤¨à¥¤"),
    KEMA_ABOUT_TITLE("About Kemadruma Yoga", "à¤•à¥‡à¤®à¤¦à¥à¤°à¥à¤® à¤¯à¥‹à¤—à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    KEMA_ABOUT_DESC("Kemadruma Yoga forms when the Moon has no planets (except Sun, Rahu, Ketu) in the 2nd and 12th houses from it, and no planets are in Kendra from the Moon or Lagna.\n\nIt is considered an inauspicious yoga indicating loneliness, mental unrest, and financial instability. However, it is often cancelled (Bhanga) by the presence of planets in Kendra houses or if the Moon interacts with other planets.", "à¤•à¥‡à¤®à¤¦à¥à¤°à¥à¤® à¤¯à¥‹à¤— à¤¤à¤¬ à¤¬à¤¨à¥à¤› à¤œà¤¬ à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤•à¥‹ à¤¦à¥‹à¤¸à¥à¤°à¥‹ à¤° à¤¬à¤¾à¤¹à¥à¤°à¥Œà¤‚ à¤­à¤¾à¤µà¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚ (à¤¸à¥‚à¤°à¥à¤¯, à¤°à¤¾à¤¹à¥, à¤•à¥‡à¤¤à¥ à¤¬à¤¾à¤¹à¥‡à¤•) à¤¹à¥à¤à¤¦à¥ˆà¤¨à¤¨à¥, à¤° à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤µà¤¾ à¤²à¤—à¥à¤¨à¤¬à¤¾à¤Ÿ à¤•à¥‡à¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚ à¤¹à¥à¤à¤¦à¥ˆà¤¨à¤¨à¥à¥¤\n\nà¤¯à¤¸à¤²à¤¾à¤ˆ à¤…à¤¶à¥à¤­ à¤¯à¥‹à¤— à¤®à¤¾à¤¨à¤¿à¤¨à¥à¤› à¤œà¤¸à¤²à¥‡ à¤à¤•à¥à¤²à¥‹à¤ªà¤¨, à¤®à¤¾à¤¨à¤¸à¤¿à¤• à¤…à¤¶à¤¾à¤¨à¥à¤¤à¤¿ à¤° à¤†à¤°à¥à¤¥à¤¿à¤• à¤…à¤¸à¥à¤¥à¤¿à¤°à¤¤à¤¾à¤²à¤¾à¤ˆ à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤¦à¤›à¥¤ à¤¯à¤¦à¥à¤¯à¤ªà¤¿, à¤•à¥‡à¤¨à¥à¤¦à¥à¤° à¤­à¤¾à¤µà¤¹à¤°à¥‚à¤®à¤¾ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤•à¥‹ à¤‰à¤ªà¤¸à¥à¤¥à¤¿à¤¤à¤¿ à¤µà¤¾ à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤²à¥‡ à¤…à¤¨à¥à¤¯ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤¸à¤à¤— à¤…à¤¨à¥à¤¤à¤°à¤•à¥à¤°à¤¿à¤¯à¤¾ à¤—à¤°à¥‡à¤®à¤¾ à¤¯à¥‹ à¤…à¤•à¥à¤¸à¤° à¤­à¤‚à¤— à¤¹à¥à¤¨à¥à¤›à¥¤"),
    KEMA_CANCELLATIONS_FOUND("%1$d Cancellation(s) Found", "%1$d à¤­à¤‚à¤—(à¤¹à¤°à¥‚) à¤«à¥‡à¤²à¤¾ à¤ªà¤°à¥à¤¯à¥‹"),
    KEMA_FORMATION_MAIN("Moon lacks planetary support in adjacent houses.", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤²à¤¾à¤ˆ à¤¨à¤œà¤¿à¤•à¤•à¤¾ à¤­à¤¾à¤µà¤¹à¤°à¥‚à¤®à¤¾ à¤—à¥à¤°à¤¹à¤¹à¤°à¥‚à¤•à¥‹ à¤¸à¤®à¤°à¥à¤¥à¤¨ à¤›à¥ˆà¤¨à¥¤"),
    KEMA_FORMATION_2ND_EMPTY("2nd house from Moon is empty.", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤¬à¤¾à¤Ÿ à¤¦à¥‹à¤¸à¥à¤°à¥‹ à¤­à¤¾à¤µ à¤–à¤¾à¤²à¥€ à¤›à¥¤"),
    KEMA_FORMATION_12TH_EMPTY("12th house from Moon is empty.", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤¬à¤¾à¤Ÿ à¤¬à¤¾à¤¹à¥à¤°à¥Œà¤‚ à¤­à¤¾à¤µ à¤–à¤¾à¤²à¥€ à¤›à¥¤"),
    KEMA_FORMATION_UNASPECTED("Moon has no planetary conjunction.", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤•à¥‹ à¤•à¥à¤¨à¥ˆ à¤—à¥à¤°à¤¹à¤¸à¤à¤— à¤¯à¥à¤¤à¤¿ à¤›à¥ˆà¤¨à¥¤"),
    KEMA_REASON_2ND("No planets in 2nd house from Moon", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤¬à¤¾à¤Ÿ à¤¦à¥‹à¤¸à¥à¤°à¥‹ à¤­à¤¾à¤µà¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤—à¥à¤°à¤¹ à¤›à¥ˆà¤¨"),
    KEMA_REASON_12TH("No planets in 12th house from Moon", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤¬à¤¾à¤Ÿ à¤¬à¤¾à¤¹à¥à¤°à¥Œà¤‚ à¤­à¤¾à¤µà¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤—à¥à¤°à¤¹ à¤›à¥ˆà¤¨"),
    KEMA_REASON_CONJUNCT("Moon is not conjunct with any planet", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤—à¥à¤°à¤¹à¤¸à¤à¤— à¤¯à¥à¤¤à¤¿à¤®à¤¾ à¤›à¥ˆà¤¨"),
    KEMA_FORMATION_STRENGTH("Formation strength: %1$d%%", "à¤¯à¥‹à¤— à¤¬à¤²: %1$d%%"),
    KEMA_NAKSHATRA("Nakshatra", "à¤¨à¤•à¥à¤·à¤¤à¥à¤°"),
    KEMA_PAKSHA("Paksha", "à¤ªà¤•à¥à¤·"),
    KEMA_BRIGHTNESS("Brightness", "à¤šà¤®à¤•"),
    KEMA_MOON_STRENGTH("Moon Strength:", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤¬à¤²:"),

    // Panchanga Redesigned
    PANCHANGA_LIMBS_TITLE("Five Limbs of Hindu Calendar", "à¤¹à¤¿à¤¨à¥à¤¦à¥ à¤ªà¤¾à¤¤à¥à¤°à¥‹à¤•à¤¾ à¤ªà¤¾à¤à¤š à¤…à¤‚à¤—à¤¹à¤°à¥‚"),
    PANCHANGA_FIVE_LIMBS("Five Limbs (Pancha Anga)", "à¤ªà¤¾à¤à¤š à¤…à¤‚à¤—à¤¹à¤°à¥‚ (à¤ªà¤žà¥à¤šà¤¾à¤™à¥à¤—)"),
    PANCHANGA_TIMING_AUSPICIOUSNESS("Timing & Auspiciousness", "à¤¸à¤®à¤¯ à¤° à¤¶à¥à¤­à¤¤à¤¾"),
    PANCHANGA_SUN_MOON("Sun & Moon", "à¤¸à¥‚à¤°à¥à¤¯ à¤° à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾"),
    PANCHANGA_RISE("Rise", "à¤‰à¤¦à¤¯"),
    PANCHANGA_SET("Set", "à¤…à¤¸à¥à¤¤"),
    PANCHANGA_LUNAR_DAY_FMT("Lunar day (%1$s)", "à¤šà¤¨à¥à¤¦à¥à¤° à¤¦à¤¿à¤¨ (%1$s)"),
    PANCHANGA_VARA_DESC("Weekday, ruled by specific planet", "à¤¬à¤¾à¤°, à¤µà¤¿à¤¶à¤¿à¤·à¥à¤Ÿ à¤—à¥à¤°à¤¹à¤¦à¥à¤µà¤¾à¤°à¤¾ à¤¶à¤¾à¤¸à¤¿à¤¤"),
    PANCHANGA_NAKSHATRA_DESC_FMT("Lunar mansion (Pada %1$d)", "à¤¨à¤•à¥à¤·à¤¤à¥à¤° (à¤ªà¤¦ %1$d)"),
    PANCHANGA_YOGA_DESC("Auspicious combination", "à¤¶à¥à¤­ à¤¸à¤‚à¤¯à¥‹à¤—"),
    PANCHANGA_KARANA_DESC("Half of a tithi", "à¤¤à¤¿à¤¥à¤¿à¤•à¥‹ à¤†à¤§à¤¾ à¤­à¤¾à¤—"),
    PANCHANGA_AVOID_ACTIVITIES("Avoid important activities", "à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤•à¤¾à¤°à¥à¤¯à¤¹à¤°à¥‚ à¤¨à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    PANCHANGA_AUSPICIOUS_TIMING("Auspicious timing", "à¤¶à¥à¤­ à¤¸à¤®à¤¯"),
    PANCHANGA_CALCULATE_DAY("Calculate based on day", "à¤¦à¤¿à¤¨à¤•à¥‹ à¤†à¤§à¤¾à¤°à¤®à¤¾ à¤—à¤£à¤¨à¤¾ à¤—à¤°à¥à¤¨à¥à¤¹à¥‹à¤¸à¥"),
    PANCHANGA_MIDDAY_HOUR("Midday hour", "à¤®à¤§à¥à¤¯à¤¾à¤¨à¥à¤¹ à¤¸à¤®à¤¯"),
    PANCHANGA_SUN("Sun", "à¤¸à¥‚à¤°à¥à¤¯"),
    PANCHANGA_MOON("Moon", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾"),
    PANCHANGA_TITHI_LABEL("Tithi", "à¤¤à¤¿à¤¥à¤¿"),
    PANCHANGA_VARA_LABEL("Vara", "à¤µà¤¾à¤°"),
    PANCHANGA_NAKSHATRA_LABEL("Nakshatra", "à¤¨à¤•à¥à¤·à¤¤à¥à¤°"),
    PANCHANGA_YOGA_LABEL("Yoga", "à¤¯à¥‹à¤—"),
    PANCHANGA_KARANA_LABEL("Karana", "à¤•à¤°à¤£"),
    PANCHANGA_RAHU("Rahu Kaal", "à¤°à¤¾à¤¹à¥ à¤•à¤¾à¤²"),
    PANCHANGA_YAMAGANDAM("Yamagandam", "à¤¯à¤®à¤—à¤£à¥à¤¡à¤®"),
    PANCHANGA_GULIKA("Gulika Kaal", "à¤—à¥à¤²à¤¿à¤• à¤•à¤¾à¤²"),
    PANCHANGA_ABHIJIT("Abhijit Muhurta", "à¤…à¤­à¤¿à¤œà¤¿à¤¤ à¤®à¥à¤¹à¥‚à¤°à¥à¤¤"),
    PANCHANGA_INTRO("Panchanga comprises five essential elements (Pancha Anga) that determine the quality and auspiciousness of any moment in the Hindu calendar system.", "à¤ªà¤žà¥à¤šà¤¾à¤™à¥à¤—à¤®à¤¾ à¤ªà¤¾à¤à¤š à¤†à¤µà¤¶à¥à¤¯à¤• à¤¤à¤¤à¥à¤µà¤¹à¤°à¥‚ (à¤ªà¤žà¥à¤š à¤…à¤‚à¤—) à¤¹à¥à¤¨à¥à¤›à¤¨à¥ à¤œà¤¸à¤²à¥‡ à¤¹à¤¿à¤¨à¥à¤¦à¥ à¤ªà¤¾à¤¤à¥à¤°à¥‹ à¤ªà¥à¤°à¤£à¤¾à¤²à¥€à¤®à¤¾ à¤•à¥à¤¨à¥ˆ à¤ªà¤¨à¤¿ à¤•à¥à¤·à¤£à¤•à¥‹ à¤—à¥à¤£à¤¸à¥à¤¤à¤° à¤° à¤¶à¥à¤­à¤¤à¤¾ à¤¨à¤¿à¤°à¥à¤§à¤¾à¤°à¤£ à¤—à¤°à¥à¤¦à¤›à¥¤"),
    PANCHANGA_TITHI_DESC_LONG("The lunar day, calculated based on the angular distance between the Sun and Moon. Each tithi represents 12 degrees of lunar motion. There are 30 tithis in a lunar month, divided into Shukla (waxing) and Krishna (waning) Paksha.", "à¤šà¤¨à¥à¤¦à¥à¤° à¤¦à¤¿à¤¨, à¤œà¥à¤¨ à¤¸à¥‚à¤°à¥à¤¯ à¤° à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤¬à¥€à¤šà¤•à¥‹ à¤•à¥‹à¤£à¥€à¤¯ à¤¦à¥‚à¤°à¥€à¤•à¥‹ à¤†à¤§à¤¾à¤°à¤®à¤¾ à¤—à¤£à¤¨à¤¾ à¤—à¤°à¤¿à¤¨à¥à¤›à¥¤ à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤¤à¤¿à¤¥à¤¿à¤²à¥‡ à¥§à¥¨ à¤¡à¤¿à¤—à¥à¤°à¥€à¤•à¥‹ à¤šà¤¨à¥à¤¦à¥à¤° à¤—à¤¤à¤¿ à¤ªà¥à¤°à¤¤à¤¿à¤¨à¤¿à¤§à¤¿à¤¤à¥à¤µ à¤—à¤°à¥à¤¦à¤›à¥¤ à¤à¤• à¤šà¤¨à¥à¤¦à¥à¤° à¤®à¤¾à¤¸à¤®à¤¾ à¥©à¥¦ à¤¤à¤¿à¤¥à¤¿à¤¹à¤°à¥‚ à¤¹à¥à¤¨à¥à¤›à¤¨à¥, à¤œà¥à¤¨ à¤¶à¥à¤•à¥à¤² (à¤¬à¤¢à¥à¤¦à¥‹) à¤° à¤•à¥ƒà¤·à¥à¤£ (à¤˜à¤Ÿà¥à¤¦à¥‹) à¤ªà¤•à¥à¤·à¤®à¤¾ à¤µà¤¿à¤­à¤¾à¤œà¤¿à¤¤ à¤¹à¥à¤¨à¥à¤›à¤¨à¥à¥¤"),
    PANCHANGA_TITHI_SIG("Determines the nature of activities suitable for the day. Some tithis like Purnima (full moon) and Amavasya (new moon) have special significance.", "à¤¦à¤¿à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤‰à¤ªà¤¯à¥à¤•à¥à¤¤ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚à¤•à¥‹ à¤ªà¥à¤°à¤•à¥ƒà¤¤à¤¿ à¤¨à¤¿à¤°à¥à¤§à¤¾à¤°à¤£ à¤—à¤°à¥à¤¦à¤›à¥¤ à¤ªà¥‚à¤°à¥à¤£à¤¿à¤®à¤¾ à¤° à¤”à¤‚à¤¶à¥€ à¤œà¤¸à¥à¤¤à¤¾ à¤•à¥‡à¤¹à¥€ à¤¤à¤¿à¤¥à¤¿à¤¹à¤°à¥‚à¤•à¥‹ à¤µà¤¿à¤¶à¥‡à¤· à¤®à¤¹à¤¤à¥à¤¤à¥à¤µ à¤¹à¥à¤¨à¥à¤›à¥¤"),
    PANCHANGA_VARA_DESC_LONG("The weekday, each ruled by a specific planet. The planetary ruler influences the nature and appropriate activities for that day.", "à¤µà¤¾à¤°, à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤à¤• à¤µà¤¿à¤¶à¤¿à¤·à¥à¤Ÿ à¤—à¥à¤°à¤¹à¤¦à¥à¤µà¤¾à¤°à¤¾ à¤¶à¤¾à¤¸à¤¿à¤¤ à¤¹à¥à¤¨à¥à¤›à¥¤ à¤—à¥à¤°à¤¹ à¤¶à¤¾à¤¸à¤•à¤²à¥‡ à¤¤à¥à¤¯à¤¸ à¤¦à¤¿à¤¨à¤•à¥‹ à¤ªà¥à¤°à¤•à¥ƒà¤¤à¤¿ à¤° à¤‰à¤ªà¤¯à¥à¤•à¥à¤¤ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚à¤²à¤¾à¤ˆ à¤ªà¥à¤°à¤­à¤¾à¤µ à¤ªà¤¾à¤°à¥à¤›à¥¤"),
    PANCHANGA_VARA_SIG("Each vara has specific activities that are considered favorable. For example, Sunday is good for authority matters, Monday for emotional work.", "à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤µà¤¾à¤°à¤®à¤¾ à¤µà¤¿à¤¶à¤¿à¤·à¥à¤Ÿ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚ à¤¹à¥à¤¨à¥à¤›à¤¨à¥ à¤œà¥à¤¨ à¤…à¤¨à¥à¤•à¥‚à¤² à¤®à¤¾à¤¨à¤¿à¤¨à¥à¤›à¤¨à¥à¥¤ à¤‰à¤¦à¤¾à¤¹à¤°à¤£à¤•à¤¾ à¤²à¤¾à¤—à¤¿, à¤†à¤‡à¤¤à¤¬à¤¾à¤° à¤…à¤§à¤¿à¤•à¤¾à¤° à¤®à¤¾à¤®à¤¿à¤²à¤¾à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤°à¤¾à¤®à¥à¤°à¥‹ à¤¹à¥à¤¨à¥à¤›, à¤¸à¥‹à¤®à¤¬à¤¾à¤° à¤­à¤¾à¤µà¤¨à¤¾à¤¤à¥à¤®à¤• à¤•à¤¾à¤°à¥à¤¯à¤•à¥‹ à¤²à¤¾à¤—à¤¿à¥¤"),
    PANCHANGA_NAKSHATRA_DESC_LONG("The lunar mansion where the Moon resides. There are 27 nakshatras, each spanning 13Â°20' of the zodiac. Each nakshatra has a ruling deity and planet.", "à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾ à¤¬à¤¸à¥à¤¨à¥‡ à¤¨à¤•à¥à¤·à¤¤à¥à¤°à¥¤ à¤¤à¥à¤¯à¤¹à¤¾à¤ à¥¨à¥­ à¤¨à¤•à¥à¤·à¤¤à¥à¤°à¤¹à¤°à¥‚ à¤›à¤¨à¥, à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤•à¤²à¥‡ à¤°à¤¾à¤¶à¤¿à¤•à¥‹ à¥§à¥©Â°à¥¨à¥¦' à¤“à¤—à¤Ÿà¥‡à¤•à¤¾ à¤›à¤¨à¥à¥¤ à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤¨à¤•à¥à¤·à¤¤à¥à¤°à¤•à¥‹ à¤à¤• à¤¶à¤¾à¤¸à¤• à¤¦à¥‡à¤µà¤¤à¤¾ à¤° à¤—à¥à¤°à¤¹ à¤¹à¥à¤¨à¥à¤›à¥¤"),
    PANCHANGA_NAKSHATRA_SIG("Birth nakshatra determines personality traits and compatibility. Moon's nakshatra is crucial for muhurta selection.", "à¤œà¤¨à¥à¤® à¤¨à¤•à¥à¤·à¤¤à¥à¤°à¤²à¥‡ à¤µà¥à¤¯à¤•à¥à¤¤à¤¿à¤¤à¥à¤µ à¤—à¥à¤£à¤¹à¤°à¥‚ à¤° à¤…à¤¨à¥à¤•à¥‚à¤²à¤¤à¤¾ à¤¨à¤¿à¤°à¥à¤§à¤¾à¤°à¤£ à¤—à¤°à¥à¤¦à¤›à¥¤ à¤®à¥à¤¹à¥‚à¤°à¥à¤¤ à¤šà¤¯à¤¨à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤•à¥‹ à¤¨à¤•à¥à¤·à¤¤à¥à¤° à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤›à¥¤"),
    PANCHANGA_YOGA_DESC_LONG("Calculated from the combined longitudes of Sun and Moon. There are 27 yogas, each with specific qualities ranging from highly auspicious to inauspicious.", "à¤¸à¥‚à¤°à¥à¤¯ à¤° à¤šà¤¨à¥à¤¦à¥à¤°à¤®à¤¾à¤•à¥‹ à¤¸à¤‚à¤¯à¥à¤•à¥à¤¤ à¤¦à¥‡à¤¶à¤¾à¤¨à¥à¤¤à¤°à¤¬à¤¾à¤Ÿ à¤—à¤£à¤¨à¤¾ à¤—à¤°à¤¿à¤à¤•à¥‹à¥¤ à¤¤à¥à¤¯à¤¹à¤¾à¤ à¥¨à¥­ à¤¯à¥‹à¤—à¤¹à¤°à¥‚ à¤›à¤¨à¥, à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤•à¤¸à¤à¤— à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤¶à¥à¤­ à¤¦à¥‡à¤–à¤¿ à¤…à¤¶à¥à¤­ à¤¸à¤®à¥à¤®à¤•à¤¾ à¤µà¤¿à¤¶à¤¿à¤·à¥à¤Ÿ à¤—à¥à¤£à¤¹à¤°à¥‚ à¤›à¤¨à¥à¥¤"),
    PANCHANGA_YOGA_SIG("Yogas indicate the general nature of results from activities undertaken. Siddha, Amrita, and Shubha yogas are highly favorable.", "à¤¯à¥‹à¤—à¤¹à¤°à¥‚à¤²à¥‡ à¤—à¤°à¤¿à¤à¤•à¤¾ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚à¤•à¥‹ à¤ªà¤°à¤¿à¤£à¤¾à¤®à¤•à¥‹ à¤¸à¤¾à¤®à¤¾à¤¨à¥à¤¯ à¤ªà¥à¤°à¤•à¥ƒà¤¤à¤¿ à¤¸à¤‚à¤•à¥‡à¤¤ à¤—à¤°à¥à¤¦à¤›à¥¤ à¤¸à¤¿à¤¦à¥à¤§, à¤…à¤®à¥ƒà¤¤ à¤° à¤¶à¥à¤­ à¤¯à¥‹à¤—à¤¹à¤°à¥‚ à¤…à¤¤à¥à¤¯à¤§à¤¿à¤• à¤…à¤¨à¥à¤•à¥‚à¤² à¤¹à¥à¤¨à¥à¤›à¤¨à¥à¥¤"),
    PANCHANGA_KARANA_DESC_LONG("Half of a tithi, there are 11 karanas total. Seven are movable and four are fixed. Each karana has its own nature and suitable activities.", "à¤¤à¤¿à¤¥à¤¿à¤•à¥‹ à¤†à¤§à¤¾ à¤­à¤¾à¤—, à¤•à¥à¤² à¥§à¥§ à¤•à¤°à¤£à¤¹à¤°à¥‚ à¤›à¤¨à¥à¥¤ à¤¸à¤¾à¤¤ à¤šà¤° à¤° à¤šà¤¾à¤° à¤¸à¥à¤¥à¤¿à¤° à¤›à¤¨à¥à¥¤ à¤ªà¥à¤°à¤¤à¥à¤¯à¥‡à¤• à¤•à¤°à¤£à¤•à¥‹ à¤†à¤«à¥à¤¨à¥ˆ à¤ªà¥à¤°à¤•à¥ƒà¤¤à¤¿ à¤° à¤‰à¤ªà¤¯à¥à¤•à¥à¤¤ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚ à¤›à¤¨à¥à¥¤"),
    PANCHANGA_KARANA_SIG("Karanas fine-tune the effects of tithis. Movable karanas recur throughout the month while fixed ones appear only once.", "à¤•à¤°à¤£à¤¹à¤°à¥‚à¤²à¥‡ à¤¤à¤¿à¤¥à¤¿à¤•à¤¾ à¤ªà¥à¤°à¤­à¤¾à¤µà¤¹à¤°à¥‚à¤²à¤¾à¤ˆ à¤¸à¥à¤·à¥à¤® à¤¬à¤¨à¤¾à¤‰à¤à¤›à¤¨à¥à¥¤ à¤šà¤° à¤•à¤°à¤£à¤¹à¤°à¥‚ à¤®à¤¹à¤¿à¤¨à¤¾à¤­à¤° à¤¦à¥‹à¤¹à¥‹à¤°à¤¿à¤¨à¥à¤›à¤¨à¥ à¤­à¤¨à¥‡ à¤¸à¥à¤¥à¤¿à¤° à¤•à¤°à¤£à¤¹à¤°à¥‚ à¤à¤• à¤ªà¤Ÿà¤• à¤®à¤¾à¤¤à¥ à¤° à¤†à¤‰à¤à¤›à¤¨à¥à¥¤"),
    PANCHANGA_ABOUT_TITLE("About Panchanga", "à¤ªà¤žà¥à¤šà¤¾à¤™à¥à¤—à¤•à¥‹ à¤¬à¤¾à¤°à¥‡à¤®à¤¾"),
    PANCHANGA_ABOUT_DESC_1("Panchanga (Sanskrit: à¤ªà¤žà¥à¤šà¤¾à¤™à¥à¤—) means 'five limbs' and refers to the traditional Hindu almanac that tracks five elements of time: Tithi, Vara, Nakshatra, Yoga, and Karana.", "à¤ªà¤žà¥à¤šà¤¾à¤™à¥à¤— (à¤¸à¤‚à¤¸à¥à¤•à¥ƒà¤¤: à¤ªà¤žà¥à¤šà¤¾à¤™à¥à¤—) à¤•à¥‹ à¤…à¤°à¥à¤¥ 'à¤ªà¤¾à¤à¤š à¤…à¤‚à¤—' à¤¹à¥‹ à¤° à¤¯à¤¸à¤²à¥‡ à¤¸à¤®à¤¯à¤•à¤¾ à¤ªà¤¾à¤à¤š à¤¤à¤¤à¥à¤µà¤¹à¤°à¥‚: à¤¤à¤¿à¤¥à¤¿, à¤µà¤¾à¤°, à¤¨à¤•à¥à¤·à¤¤à¥à¤°, à¤¯à¥‹à¤— à¤° à¤•à¤°à¤£ à¤Ÿà¥à¤°à¥à¤¯à¤¾à¤• à¤—à¤°à¥à¤¨à¥‡ à¤ªà¤°à¤®à¥à¤ªà¤°à¤¾à¤—à¤¤ à¤¹à¤¿à¤¨à¥à¤¦à¥ à¤ªà¤¾à¤¤à¥à¤°à¥‹à¤²à¤¾à¤ˆ à¤œà¤¨à¤¾à¤‰à¤à¤›à¥¤"),
    PANCHANGA_ABOUT_DESC_2("These five elements together determine the auspiciousness of any moment and are crucial for selecting muhurtas (auspicious timings) for important activities.", "à¤¯à¥€ à¤ªà¤¾à¤à¤š à¤¤à¤¤à¥à¤µà¤¹à¤°à¥‚à¤²à¥‡ à¤®à¤¿à¤²à¥‡à¤° à¤•à¥à¤¨à¥ˆ à¤ªà¤¨à¤¿ à¤•à¥à¤·à¤£à¤•à¥‹ à¤¶à¥à¤­à¤¤à¤¾ à¤¨à¤¿à¤°à¥à¤§à¤¾à¤°à¤£ à¤—à¤°à¥à¤¦à¤› à¤° à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤—à¤¤à¤¿à¤µà¤¿à¤§à¤¿à¤¹à¤°à¥‚à¤•à¥‹ à¤²à¤¾à¤—à¤¿ à¤®à¥à¤¹à¥‚à¤°à¥à¤¤ (à¤¶à¥à¤­ à¤¸à¤®à¤¯) à¤šà¤¯à¤¨ à¤—à¤°à¥à¤¨ à¤®à¤¹à¤¤à¥à¤¤à¥à¤µà¤ªà¥‚à¤°à¥à¤£ à¤›à¤¨à¥à¥¤"),
    PANCHANGA_SIGNIFICANCE("Significance", "à¤®à¤¹à¤¤à¥à¤¤à¥à¤µ"),

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
    SUDARSHANA_THEME_H12_2("Expenses", "खर्च");
}
