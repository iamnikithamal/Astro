package com.astro.storm.data.localization

/**
 * Horoscope and Daily/Weekly prediction string keys
 * Part 5 of split enums
 */
enum class StringKeyHoroscope(override val en: String, override val ne: String) : StringKeyInterface {
    // ============================================
    // ENERGY DESCRIPTIONS
    // ============================================
    ENERGY_EXCEPTIONAL("Exceptional cosmic alignment - seize every opportunity!", "असाधारण ब्रह्माण्डीय संरेखण - हरेक अवसर समात्नुहोस्!"),
    ENERGY_EXCELLENT("Excellent day ahead - favorable for important decisions", "उत्कृष्ट दिन अगाडि - महत्त्वपूर्ण निर्णयहरूको लागि अनुकूल"),
    ENERGY_STRONG("Strong positive energy - good for new initiatives", "बलियो सकारात्मक ऊर्जा - नयाँ पहलहरूको लागि राम्रो"),
    ENERGY_FAVORABLE("Favorable energy - maintain steady progress", "अनुकूल ऊर्जा - स्थिर प्रगति कायम राख्नुहोस्"),
    ENERGY_BALANCED("Balanced energy - stay centered and focused", "सन्तुलित ऊर्जा - केन्द्रित र ध्यान केन्द्रित रहनुहोस्"),
    ENERGY_MODERATE("Moderate energy - pace yourself wisely", "मध्यम ऊर्जा - बुद्धिमानीपूर्वक आफ्नो गति मिलाउनुहोस्"),
    ENERGY_LOWER("Lower energy day - prioritize rest and reflection", "कम ऊर्जा दिन - आराम र चिन्तनलाई प्राथमिकता दिनुहोस्"),
    ENERGY_CHALLENGING("Challenging day - practice patience and self-care", "चुनौतीपूर्ण दिन - धैर्य र आत्म-हेरचाह अभ्यास गर्नुहोस्"),
    ENERGY_REST("Rest and recharge recommended - avoid major decisions", "आराम र रिचार्ज सिफारिस - प्रमुख निर्णयहरूबाट बच्नुहोस्"),

    // ============================================
    // HOROSCOPE THEMES
    // ============================================
    THEME_DYNAMIC_ACTION("Dynamic Action", "गतिशील कार्य"),
    THEME_PRACTICAL_PROGRESS("Practical Progress", "व्यावहारिक प्रगति"),
    THEME_SOCIAL_CONNECTIONS("Social Connections", "सामाजिक सम्बन्धहरू"),
    THEME_EMOTIONAL_INSIGHT("Emotional Insight", "भावनात्मक अन्तर्दृष्टि"),
    THEME_EXPANSION_WISDOM("Expansion & Wisdom", "विस्तार र ज्ञान"),
    THEME_HARMONY_BEAUTY("Harmony & Beauty", "सामञ्जस्य र सौन्दर्य"),
    THEME_DISCIPLINE_GROWTH("Discipline & Growth", "अनुशासन र वृद्धि"),
    THEME_COMMUNICATION_LEARNING("Communication & Learning", "सञ्चार र सिकाइ"),
    THEME_ENERGY_INITIATIVE("Energy & Initiative", "ऊर्जा र पहल"),
    THEME_SELF_EXPRESSION("Self-Expression", "आत्म-अभिव्यक्ति"),
    THEME_INTUITION_NURTURING("Intuition & Nurturing", "अन्तर्ज्ञान र पालन-पोषण"),
    THEME_TRANSFORMATION("Transformation", "रूपान्तरण"),
    THEME_SPIRITUAL_LIBERATION("Spiritual Liberation", "आध्यात्मिक मुक्ति"),
    THEME_BALANCE_EQUILIBRIUM("Balance & Equilibrium", "सन्तुलन र समानता"),

    // Theme Descriptions
    THEME_DESC_DYNAMIC_ACTION(
        "Your energy is high and aligned with fire elements. This is an excellent day for taking initiative, starting new projects, and asserting yourself confidently. Channel this vibrant energy into productive pursuits.",
        "तपाईंको ऊर्जा उच्च छ र अग्नि तत्वहरूसँग मिलेको छ। यो पहल लिन, नयाँ परियोजनाहरू सुरु गर्न, र आत्मविश्वासका साथ आफूलाई प्रस्तुत गर्न उत्कृष्ट दिन हो। यो जीवन्त ऊर्जालाई उत्पादक कार्यहरूमा प्रयोग गर्नुहोस्।"
    ),
    THEME_DESC_PRACTICAL_PROGRESS(
        "Grounded earth energy supports methodical progress today. Focus on practical tasks, financial planning, and building stable foundations. Your efforts will yield tangible results.",
        "भूमिगत पृथ्वी ऊर्जाले आज व्यवस्थित प्रगतिलाई समर्थन गर्छ। व्यावहारिक कार्यहरू, वित्तीय योजना, र स्थिर आधारहरू निर्माण गर्नमा ध्यान दिनुहोस्। तपाईंको प्रयासले ठोस परिणामहरू दिनेछ।"
    ),
    THEME_DESC_SOCIAL_CONNECTIONS(
        "Air element energy enhances communication and social interactions. Networking, negotiations, and intellectual pursuits are favored. Express your ideas and connect with like-minded people.",
        "वायु तत्व ऊर्जाले सञ्चार र सामाजिक अन्तरक्रियाहरूलाई बढाउँछ। नेटवर्किङ, वार्ता, र बौद्धिक प्रयासहरू अनुकूल छन्। आफ्ना विचारहरू व्यक्त गर्नुहोस् र समान विचार भएका मानिसहरूसँग जोडिनुहोस्।"
    ),
    THEME_DESC_EMOTIONAL_INSIGHT(
        "Water element energy deepens your intuition and emotional awareness. Trust your feelings and pay attention to subtle cues. This is a powerful day for healing and self-reflection.",
        "जल तत्व ऊर्जाले तपाईंको अन्तर्ज्ञान र भावनात्मक जागरूकता गहिरो बनाउँछ। आफ्ना भावनाहरूमाथि विश्वास गर्नुहोस् र सूक्ष्म संकेतहरूमा ध्यान दिनुहोस्। यो उपचार र आत्म-चिन्तनको लागि शक्तिशाली दिन हो।"
    ),
    THEME_DESC_EXPANSION_WISDOM(
        "Jupiter's benevolent influence brings opportunities for growth, learning, and good fortune. Be open to new possibilities and share your wisdom generously.",
        "बृहस्पतिको उदार प्रभावले वृद्धि, सिकाइ र सौभाग्यका अवसरहरू ल्याउँछ। नयाँ सम्भावनाहरूको लागि खुला हुनुहोस् र आफ्नो ज्ञान उदारतापूर्वक साझा गर्नुहोस्।"
    ),
    THEME_DESC_HARMONY_BEAUTY(
        "Venus graces you with appreciation for beauty, art, and relationships. Indulge in pleasurable activities and nurture your connections with loved ones.",
        "शुक्रले तपाईंलाई सौन्दर्य, कला र सम्बन्धहरूको प्रशंसाको वरदान दिन्छ। रमाइलो गतिविधिहरूमा संलग्न हुनुहोस् र आफ्ना प्रियजनहरूसँगको सम्बन्ध पोषण गर्नुहोस्।"
    ),
    THEME_DESC_DISCIPLINE_GROWTH(
        "Saturn's influence calls for patience, hard work, and responsibility. Embrace challenges as opportunities for growth and stay committed to your long-term goals.",
        "शनिको प्रभावले धैर्य, कडा परिश्रम र जिम्मेवारीको आह्वान गर्छ। चुनौतीहरूलाई वृद्धिका अवसरहरूको रूपमा स्वीकार गर्नुहोस् र आफ्नो दीर्घकालीन लक्ष्यहरूप्रति प्रतिबद्ध रहनुहोस्।"
    ),
    THEME_DESC_COMMUNICATION_LEARNING(
        "Mercury enhances your mental agility and communication skills. This is ideal for learning, teaching, writing, and all forms of information exchange.",
        "बुधले तपाईंको मानसिक चुस्ती र सञ्चार कौशल बढाउँछ। यो सिक्न, सिकाउन, लेख्न र सूचना आदानप्रदानको सबै रूपहरूको लागि आदर्श छ।"
    ),
    THEME_DESC_ENERGY_INITIATIVE(
        "Mars provides courage and drive. Take bold action, compete with integrity, and channel aggressive energy into constructive activities.",
        "मंगलले साहस र प्रेरणा प्रदान गर्छ। साहसी कदम चाल्नुहोस्, इमानदारीताका साथ प्रतिस्पर्धा गर्नुहोस्, र आक्रामक ऊर्जालाई रचनात्मक गतिविधिहरूमा प्रयोग गर्नुहोस्।"
    ),
    THEME_DESC_SELF_EXPRESSION(
        "The Sun illuminates your path to self-expression and leadership. Shine your light confidently and pursue activities that bring you recognition.",
        "सूर्यले तपाईंको आत्म-अभिव्यक्ति र नेतृत्वको बाटो उज्यालो बनाउँछ। आत्मविश्वासका साथ आफ्नो प्रकाश फैलाउनुहोस् र पहिचान ल्याउने गतिविधिहरू अनुसरण गर्नुहोस्।"
    ),
    THEME_DESC_INTUITION_NURTURING(
        "The Moon heightens your sensitivity and caring nature. Nurture yourself and others, and trust your instincts in important decisions.",
        "चन्द्रमाले तपाईंको संवेदनशीलता र हेरचाहको स्वभाव बढाउँछ। आफू र अरूलाई पोषण गर्नुहोस्, र महत्त्वपूर्ण निर्णयहरूमा आफ्नो सहजज्ञानमाथि विश्वास गर्नुहोस्।"
    ),
    THEME_DESC_TRANSFORMATION(
        "Rahu's influence brings unconventional opportunities and desires for change. Embrace innovation but stay grounded in your values.",
        "राहुको प्रभावले अपरम्परागत अवसरहरू र परिवर्तनको इच्छा ल्याउँछ। नवीनतालाई अँगाल्नुहोस् तर आफ्ना मूल्यहरूमा जमिनमा रहनुहोस्।"
    ),
    THEME_DESC_SPIRITUAL_LIBERATION(
        "Ketu's energy supports detachment and spiritual insight. Let go of what no longer serves you and focus on inner growth.",
        "केतुको ऊर्जाले वैराग्य र आध्यात्मिक अन्तर्दृष्टिलाई समर्थन गर्छ। जुन कुरा अब तपाईंको सेवा गर्दैन त्यसलाई छोडिदिनुहोस् र आन्तरिक वृद्धिमा ध्यान केन्द्रित गर्नुहोस्।"
    ),
    THEME_DESC_BALANCE_EQUILIBRIUM(
        "A day of balance where all energies are in equilibrium. Maintain steadiness and make measured progress in all areas of life.",
        "सन्तुलनको दिन जहाँ सबै ऊर्जाहरू समानमा छन्। स्थिरता कायम राख्नुहोस् र जीवनका सबै क्षेत्रहरूमा मापित प्रगति गर्नुहोस्।"
    ),

    // ============================================
    // LUCKY ELEMENTS
    // ============================================
    LUCKY_COLOR_FIRE("Red, Orange, or Gold", "रातो, सुन्तला, वा सुनौलो"),
    LUCKY_COLOR_EARTH("Green, Brown, or White", "हरियो, खैरो, वा सेतो"),
    LUCKY_COLOR_AIR("Blue, Light Blue, or Silver", "निलो, हल्का निलो, वा चाँदी"),
    LUCKY_COLOR_WATER("White, Cream, or Sea Green", "सेतो, क्रीम, वा समुद्री हरियो"),

    LUCKY_DIR_EAST("East", "पूर्व"),
    LUCKY_DIR_WEST("West", "पश्चिम"),
    LUCKY_DIR_NORTH("North", "उत्तर"),
    LUCKY_DIR_SOUTH("South", "दक्षिण"),
    LUCKY_DIR_NORTHEAST("North-East", "उत्तर-पूर्व"),
    LUCKY_DIR_NORTHWEST("North-West", "उत्तर-पश्चिम"),
    LUCKY_DIR_SOUTHEAST("South-East", "दक्षिण-पूर्व"),
    LUCKY_DIR_SOUTHWEST("South-West", "दक्षिण-पश्चिम"),

    LUCKY_TIME_MORNING("Morning hours", "बिहानी समय"),
    LUCKY_TIME_DAY("Daylight hours", "दिउँसोको समय"),
    LUCKY_TIME_EVENING("Evening hours", "साँझको समय"),
    LUCKY_TIME_NIGHT("Night hours", "रातको समय"),
    
    HORA_SUN("6:00 AM - 7:00 AM (Sun Hora)", "बिहान ६:०० - ७:०० (सूर्य होरा)"),
    HORA_MOON("7:00 AM - 8:00 AM (Moon Hora)", "बिहान ७:०० - ८:०० (चन्द्र होरा)"),
    HORA_MARS("8:00 AM - 9:00 AM (Mars Hora)", "बिहान ८:०० - ९:०० (मंगल होरा)"),
    HORA_MERCURY("9:00 AM - 10:00 AM (Mercury Hora)", "बिहान ९:०० - १०:०० (बुध होरा)"),
    HORA_JUPITER("10:00 AM - 11:00 AM (Jupiter Hora)", "बिहान १०:०० - ११:०० (बृहस्पति होरा)"),
    HORA_VENUS("11:00 AM - 12:00 PM (Venus Hora)", "बिहान ११:०० - १२:०० (शुक्र होरा)"),
    HORA_SATURN("5:00 PM - 6:00 PM (Saturn Hora)", "बेलुका ५:०० - ६:०० (शनि होरा)"),

    // ============================================
    // RECOMMENDATIONS & CAUTIONS
    // ============================================
    REC_SUN("Engage in activities that build confidence and leadership skills.", "आत्मविश्वास र नेतृत्व कौशल विकास गर्ने गतिविधिहरूमा संलग्न हुनुहोस्।"),
    REC_MOON("Prioritize emotional well-being and nurturing relationships.", "भावनात्मक कल्याण र पोषणपूर्ण सम्बन्धहरूलाई प्राथमिकता दिनुहोस्।"),
    REC_MARS("Channel your energy into physical activities and competitive pursuits.", "आफ्नो ऊर्जालाई शारीरिक गतिविधि र प्रतिस्पर्धात्मक प्रयासहरूमा प्रयोग गर्नुहोस्।"),
    REC_MERCURY("Focus on learning, communication, and intellectual growth.", "सिकाइ, सञ्चार र बौद्धिक वृद्धिमा ध्यान दिनुहोस्।"),
    REC_JUPITER("Expand your horizons through education, travel, or spiritual practices.", "शिक्षा, यात्रा वा आध्यात्मिक अभ्यासहरूको माध्यमबाट आफ्नो क्षितिज विस्तार गर्नुहोस्।"),
    REC_VENUS("Cultivate beauty, art, and harmonious relationships.", "सौन्दर्य, कला र सामञ्जस्यपूर्ण सम्बन्धहरू विकास गर्नुहोस्।"),
    REC_SATURN("Embrace discipline, hard work, and long-term planning.", "अनुशासन, कडा परिश्रम र दीर्घकालीन योजनालाई अँगाल्नुहोस्।"),
    REC_RAHU("Explore unconventional paths while staying grounded.", "जमिनमा रहँदै अपरम्परागत मार्गहरू अन्वेषण गर्नुहोस्।"),
    REC_KETU("Practice detachment and focus on spiritual development.", "वैराग्यको अभ्यास गर्नुहोस् र आध्यात्मिक विकासमा ध्यान दिनुहोस्।"),

    CAUTION_SATURN("Avoid rushing into decisions. Patience is key.", "निर्णयहरूमा हतार नगर्नुहोस्। धैर्य महत्त्वपूर्ण छ।"),
    CAUTION_MARS("Control impulsive reactions and avoid conflicts.", "आवेगपूर्ण प्रतिक्रियाहरू नियन्त्रण गर्नुहोस् र विवादहरूबाट बच्नुहोस्।"),
    CAUTION_RAHU("Be wary of deception and unrealistic expectations.", "छलकपट र अवास्तविक अपेक्षाहरूबाट सावधान रहनुहोस्।"),
    CAUTION_KETU("Don't neglect practical responsibilities for escapism.", "पलायनवादको लागि व्यावहारिक जिम्मेवारीहरूलाई बेवास्ता नगर्नुहोस्।"),
    CAUTION_RETROGRADE("%s is retrograde - review and reconsider rather than initiate.", "%s वक्री छ - नयाँ सुरुवात गर्नु भन्दा समीक्षा र पुनर्विचार गर्नुहोस्।"),

    AFF_SUN("I shine my light confidently and inspire those around me.", "म आत्मविश्वासका साथ आफ्नो प्रकाश फैलाउँछु र वरिपरिका मानिसहरूलाई प्रेरित गर्छु।"),
    AFF_MOON("I trust my intuition and nurture myself with compassion.", "म आफ्नो अन्तर्ज्ञानमाथि विश्वास गर्छु र करुणाका साथ आफूलाई पोषण गर्छु।"),
    AFF_MARS("I channel my energy constructively and act with courage.", "म आफ्नो ऊर्जा रचनात्मक रूपमा प्रयोग गर्छु र साहसका साथ काम गर्छु।"),
    AFF_MERCURY("I communicate clearly and embrace continuous learning.", "म स्पष्ट रूपमा सञ्चार गर्छु र निरन्तर सिकाइलाई अँगाल्छु।"),
    AFF_JUPITER("I am open to abundance and share my wisdom generously.", "म प्रचुरताको लागि खुला छु र आफ्नो ज्ञान उदारतापूर्वक साझा गर्छु।"),
    AFF_VENUS("I attract beauty and harmony into my life.", "म आफ्नो जीवनमा सौन्दर्य र सामञ्जस्य आकर्षित गर्छु।"),
    AFF_SATURN("I embrace discipline and trust in the timing of my journey.", "म अनुशासनलाई अँगाल्छु र आफ्नो यात्राको समयमाथि विश्वास गर्छु।"),
    AFF_RAHU("I embrace change and transform challenges into opportunities.", "म परिवर्तनलाई अँगाल्छु र चुनौतीहरूलाई अवसरहरूमा रूपान्तरण गर्छु।"),
    AFF_KETU("I release what no longer serves me and embrace spiritual growth.", "म जुन कुराले अब मेरो सेवा गर्दैन त्यसलाई छाड्छु र आध्यात्मिक वृद्धिलाई अँगाल्छु।"),
    AFF_DEFAULT("I am aligned with cosmic energies and flow with life's rhythm.", "म ब्रह्माण्डीय ऊर्जाहरूसँग संरेखित छु र जीवनको लयसँगै बग्छु।"),

    // ============================================
    // LIFE AREA PREDICTIONS
    // ============================================
    // Career
    PRED_CAREER_5("Excellent day for professional advancement. Your %s period brings recognition and success in work matters.", "व्यावसायिक उन्नतिका लागि उत्कृष्ट दिन। तपाईंको %s अवधिले कामको मामिलामा मान्यता र सफलता ल्याउँछ।"),
    PRED_CAREER_4("Good energy for career activities. Focus on important projects and networking.", "करियर गतिविधिहरूको लागि राम्रो ऊर्जा। महत्त्वपूर्ण परियोजनाहरू र नेटवर्किङमा ध्यान दिनुहोस्।"),
    PRED_CAREER_3("Steady progress in professional matters. Maintain consistency in your efforts.", "व्यावसायिक मामिलाहरूमा स्थिर प्रगति। आफ्नो प्रयासमा निरन्तरता कायम राख्नुहोस्।"),
    PRED_CAREER_2("Some workplace challenges may arise. Stay patient and diplomatic.", "कार्यस्थलमा केही चुनौतीहरू आउन सक्छन्। धैर्यवान र कूटनीतिक रहनुहोस्।"),
    PRED_CAREER_1("Career matters require extra attention. Avoid major decisions today.", "करियरका मामिलाहरूमा विशेष ध्यान आवश्यक छ। आज ठूला निर्णयहरू नगर्नुहोस्।"),

    // Love
    PRED_LOVE_5("Romantic energy is at its peak. Deep connections and meaningful conversations await.", "रोमान्टिक ऊर्जा चरम सीमामा छ। गहिरो सम्बन्ध र अर्थपूर्ण कुराकानीहरू प्रतीक्षामा छन्।"),
    PRED_LOVE_4("Favorable time for relationships. Express your feelings openly.", "सम्बन्धका लागि अनुकूल समय। आफ्ना भावनाहरू खुलेर व्यक्त गर्नुहोस्।"),
    PRED_LOVE_3("Balanced energy in partnerships. Focus on understanding and compromise.", "साझेदारीमा सन्तुलित ऊर्जा। समझदारी र सम्झौतामा ध्यान दिनुहोस्।"),
    PRED_LOVE_2("Minor misunderstandings possible. Practice patience with loved ones.", "सानोतिनो गलतफहमी हुन सक्छ। प्रियजनहरूसँग धैर्यताको अभ्यास गर्नुहोस्।"),
    PRED_LOVE_1("Relationships need nurturing. Avoid conflicts and be extra considerate.", "सम्बन्धहरूलाई पोषण चाहिन्छ। द्वन्द्वबाट बच्नुहोस् र थप विचारशील हुनुहोस्।"),

    // Health
    PRED_HEALTH_5("Vitality is strong. Great day for physical activities and wellness routines.", "जीवनशक्ति बलियो छ। शारीरिक गतिविधि र स्वास्थ्य दिनचर्याका लागि उत्कृष्ट दिन।"),
    PRED_HEALTH_4("Good health energy. Maintain your wellness practices.", "राम्रो स्वास्थ्य ऊर्जा। आफ्नो स्वास्थ्य अभ्यासहरू कायम राख्नुहोस्।"),
    PRED_HEALTH_3("Steady health. Focus on rest and balanced nutrition.", "स्थिर स्वास्थ्य। आराम र सन्तुलित पोषणमा ध्यान दिनुहोस्।"),
    PRED_HEALTH_2("Energy may fluctuate. Prioritize adequate rest.", "ऊर्जा उतार-चढाव हुन सक्छ। पर्याप्त आरामलाई प्राथमिकता दिनुहोस्।"),
    PRED_HEALTH_1("Health needs attention. Take it easy and avoid overexertion.", "स्वास्थ्यमा ध्यान दिनु आवश्यक छ। आराम गर्नुहोस् र बढी परिश्रम नगर्नुहोस्।"),

    // Finance
    PRED_FINANCE_5("Excellent day for financial matters. Opportunities for gains are strong.", "वित्तीय मामिलाहरूको लागि उत्कृष्ट दिन। लाभको अवसरहरू बलियो छन्।"),
    PRED_FINANCE_4("Positive financial energy. Good for planned investments.", "सकारात्मक वित्तीय ऊर्जा। योजनाबद्ध लगानीका लागि राम्रो।"),
    PRED_FINANCE_3("Stable financial period. Stick to your budget.", "स्थिर वित्तीय अवधि। आफ्नो बजेटमा अडिग रहनुहोस्।"),
    PRED_FINANCE_2("Be cautious with expenditures. Avoid impulsive purchases.", "खर्चमा सावधानी अपनाउनुहोस्। आवेगपूर्ण खरिदहरूबाट बच्नुहोस्।"),
    PRED_FINANCE_1("Financial caution advised. Postpone major financial decisions.", "वित्तीय सावधानीको सल्लाह दिइन्छ। ठूला वित्तीय निर्णयहरू स्थगित गर्नुहोस्।"),

    // Family
    PRED_FAMILY_5("Harmonious family energy. Celebrations and joyful gatherings are favored.", "सामञ्जस्यपूर्ण पारिवारिक ऊर्जा। उत्सव र रमाइलो जमघटहरू अनुकूल छन्।"),
    PRED_FAMILY_4("Good time for family bonding. Support flows both ways.", "पारिवारिक बन्धनका लागि राम्रो समय। दुवै तर्फबाट सहयोग प्राप्त हुनेछ।"),
    PRED_FAMILY_3("Steady domestic atmosphere. Focus on routine family matters.", "स्थिर घरेलु वातावरण। नियमित पारिवारिक मामिलाहरूमा ध्यान दिनुहोस्।"),
    PRED_FAMILY_2("Minor family tensions possible. Practice understanding.", "सानोतिनो पारिवारिक तनाव हुन सक्छ। समझदारीको अभ्यास गर्नुहोस्।"),
    PRED_FAMILY_1("Family dynamics need attention. Prioritize peace and harmony.", "पारिवारिक गतिशीलतामा ध्यान दिन आवश्यक छ। शान्ति र सद्भावलाई प्राथमिकता दिनुहोस्।"),

    // Spirituality
    PRED_SPIRIT_5("Profound spiritual insights available. Meditation and reflection are highly beneficial.", "गहिरो आध्यात्मिक अन्तर्दृष्टि उपलब्ध छ। ध्यान र चिन्तन अत्यन्तै लाभदायक छ।"),
    PRED_SPIRIT_4("Good day for spiritual practices. Inner guidance is strong.", "आध्यात्मिक अभ्यासका लागि राम्रो दिन। आन्तरिक मार्गदर्शन बलियो छ।"),
    PRED_SPIRIT_3("Steady spiritual energy. Maintain your regular practices.", "स्थिर आध्यात्मिक ऊर्जा। आफ्नो नियमित अभ्यासहरू कायम राख्नुहोस्।"),
    PRED_SPIRIT_2("Spiritual connection may feel distant. Keep faith.", "आध्यात्मिक सम्बन्ध टाढा महसुस हुन सक्छ। विश्वास राख्नुहोस्।"),
    PRED_SPIRIT_1("Inner turbulence possible. Ground yourself through simple practices.", "आन्तरिक अशान्ति हुन सक्छ। सरल अभ्यासहरू मार्फत आफूलाई स्थिर राख्नुहोस्।"),

    // ============================================
    // WEEKLY HOROSCOPE
    // ============================================
    WEEKLY_THEME_OPPORTUNITY("Week of Opportunities", "अवसरहरूको हप्ता"),
    WEEKLY_THEME_PROGRESS("Steady Progress", "स्थिर प्रगति"),
    WEEKLY_THEME_NAVIGATION("Mindful Navigation", "सावधानीपूर्वक यात्रा"),

    WEEKLY_OVERVIEW_PREFIX("This week under your %s Mahadasha brings ", "तपाईंको %s महादशा अन्तर्गत यो हप्ताले "),
    WEEKLY_OVERVIEW_HIGH("excellent opportunities for growth and success. ", "वृद्धि र सफलताका लागि उत्कृष्ट अवसरहरू ल्याउँछ। "),
    WEEKLY_OVERVIEW_MED("steady progress and balanced energy. ", "स्थिर प्रगति र सन्तुलित ऊर्जा ल्याउँछ। "),
    WEEKLY_OVERVIEW_LOW("challenges that, when navigated wisely, lead to growth. ", "चुनौतीहरू ल्याउँछ जसले, बुद्धिमानीपूर्वक सामना गर्दा वृद्धितर्फ लैजान्छ। "),
    WEEKLY_OVERVIEW_FAVORABLE("%s appears most favorable for important activities. ", "%s महत्त्वपूर्ण गतिविधिहरूको लागि सबैभन्दा अनुकूल देखिन्छ। "),
    WEEKLY_OVERVIEW_CAUTION("%s may require extra patience and care. ", "%s मा थप धैर्य र सावधानी आवश्यक हुन सक्छ। "),
    WEEKLY_OVERVIEW_SUFFIX("Trust in your cosmic guidance and make the most of each day's unique energy.", "आफ्नो ब्रह्माण्डीय मार्गदर्शनमा विश्वास गर्नुहोस् र प्रत्येक दिनको अद्वितीय ऊर्जाको अधिकतम सदुपयोग गर्नुहोस्।"),

    WEEKLY_ADVICE_PREFIX("During this %s period, ", "%s अवधिको समयमा, "),
    WEEKLY_ADVICE_SUN("let your light shine. Leadership roles and self-expression bring recognition.", "आफ्नो चमक फैलाउनुहोस्। नेतृत्व भूमिका र आत्म-अभिव्यक्तिले पहिचान ल्याउँछ।"),
    WEEKLY_ADVICE_MOON("honor your emotions and intuition. Nurturing activities bring fulfillment.", "आफ्नो भावना र अन्तर्ज्ञानको सम्मान गर्नुहोस्। पोषण गर्ने गतिविधिहरूले सन्तुष्टि ल्याउँछन्।"),
    WEEKLY_ADVICE_MARS("channel your energy into productive activities. Exercise and competition are favored.", "आफ्नो ऊर्जालाई उत्पादक गतिविधिहरूमा लगाउनुहोस्। व्यायाम र प्रतिस्पर्धा अनुकूल छन्।"),
    WEEKLY_ADVICE_MERCURY("prioritize communication, learning, and intellectual activities. Your mind is sharp.", "सञ्चार, सिकाइ र बौद्धिक गतिविधिहरूलाई प्राथमिकता दिनुहोस्। तपाईंको दिमाग तीक्ष्ण छ।"),
    WEEKLY_ADVICE_JUPITER("embrace opportunities for learning and expansion. Your wisdom and optimism attract positive outcomes.", "सिकाइ र विस्तारका अवसरहरूलाई अँगाल्नुहोस्। तपाईंको ज्ञान र आशावादले सकारात्मक परिणामहरू आकर्षित गर्दछ।"),
    WEEKLY_ADVICE_VENUS("focus on cultivating beauty, harmony, and meaningful relationships. Artistic pursuits are favored.", "सौन्दर्य, सद्भाव र अर्थपूर्ण सम्बन्धहरू विकास गर्नमा ध्यान दिनुहोस्। कलात्मक कार्यहरू अनुकूल छन्।"),
    WEEKLY_ADVICE_SATURN("embrace discipline and patience. Hard work now builds lasting foundations for the future.", "अनुशासन र धैर्यतालाई अँगाल्नुहोस्। अहिलेको कडा परिश्रमले भविष्यका लागि स्थायी आधार निर्माण गर्दछ।"),
    WEEKLY_ADVICE_RAHU("embrace transformation while staying grounded. Unconventional approaches may succeed.", "जमिनमा रहँदै रूपान्तरणलाई अँगाल्नुहोस्। अपरम्परागत दृष्टिकोणहरू सफल हुन सक्छन्।"),
    WEEKLY_ADVICE_KETU("focus on spiritual growth and letting go. Detachment brings peace.", "आध्यात्मिक वृद्धि र त्यागमा ध्यान दिनुहोस्। वैराग्यले शान्ति ल्याउँछ।"),
    WEEKLY_ADVICE_DATE(" Mark %s for important initiatives.", " महत्त्वपूर्ण पहलहरूको लागि %s मार्क गर्नुहोस्।"),

    // Weekly area predictions
    WEEKLY_CAREER_EXCELLENT("An exceptional week for career advancement. Your %s period supports professional recognition. Key meetings and projects will progress smoothly.", "करियर उन्नतिका लागि एक असाधारण हप्ता। तपाईंको %s अवधिले व्यावसायिक मान्यतालाई समर्थन गर्दछ। मुख्य बैठकहरू र परियोजनाहरू सुचारु रूपमा अगाडि बढ्नेछन्।"),
    WEEKLY_CAREER_STEADY("Steady professional progress this week. Focus on completing pending tasks and building relationships with colleagues.", "यस हप्ता स्थिर व्यावसायिक प्रगति। बाँकी रहेका कार्यहरू पूरा गर्न र सहकर्मीहरूसँग सम्बन्ध निर्माणमा ध्यान दिनुहोस्।"),
    WEEKLY_CAREER_CHALLENGING("Career matters may require extra effort this week. Stay patient and avoid major changes.", "यस हप्ता करियरका मामिलाहरूमा अतिरिक्त प्रयास आवश्यक हुन सक्छ। धैर्यवान रहनुहोस् र ठूला परिवर्तनहरूबाट बच्नुहोस्।"),

    WEEKLY_LOVE_EXCELLENT("Romantic energy flows abundantly this week. Single or committed, relationships deepen. Express your feelings openly.", "यस हप्ता रोमान्टिक ऊर्जा प्रचुर मात्रामा प्रवाहित हुन्छ। अविवाहित होस् वा प्रतिबद्ध सम्बन्धमा, सम्बन्धहरू गहिरो हुनेछन्। आफ्ना भावनाहरू खुलेर व्यक्त गर्नुहोस्।"),
    WEEKLY_LOVE_STEADY("Balanced relationship energy. Good for maintaining harmony and working through minor issues together.", "सन्तुलित सम्बन्ध ऊर्जा। सद्भाव कायम राख्न र सानातिना समस्याहरू मिलेर समाधान गर्न राम्रो।"),
    WEEKLY_LOVE_CHALLENGING("Relationships need nurturing this week. Practice patience and understanding with your partner.", "यस हप्ता सम्बन्धहरूलाई पोषण आवश्यक छ। आफ्नो पार्टनरसँग धैर्यता र समझदारीको अभ्यास गर्नुहोस्।"),

    WEEKLY_HEALTH_EXCELLENT("Excellent vitality this week! Great time to start new fitness routines or health practices. Energy levels are high.", "यस हप्ता उत्कृष्ट जीवनशक्ति! नयाँ फिटनेस दिनचर्या वा स्वास्थ्य अभ्यासहरू सुरु गर्नको लागि उत्कृष्ट समय। ऊर्जा स्तर उच्च छ।"),
    WEEKLY_HEALTH_STEADY("Stable health week. Maintain your regular wellness routines and stay consistent with rest.", "स्थिर स्वास्थ्यको हप्ता। आफ्नो नियमित स्वास्थ्य दिनचर्या कायम राख्नुहोस् र आराममा निरन्तरता दिनुहोस्।"),
    WEEKLY_HEALTH_CHALLENGING("Health vigilance needed this week. Prioritize rest, nutrition, and stress management.", "यस हप्ता स्वास्थ्य सचेतना आवश्यक छ। आराम, पोषण र तनाव व्यवस्थापनलाई प्राथमिकता दिनुहोस्।"),

    WEEKLY_FINANCE_EXCELLENT("Prosperous week for finances. Opportunities for gains through investments or new income sources. Review financial plans.", "आर्थिक रूपमा समृद्ध हप्ता। लगानी वा नयाँ आय स्रोतहरू मार्फत लाभको अवसरहरू। वित्तीय योजनाहरूको समीक्षा गर्नुहोस्।"),
    WEEKLY_FINANCE_STEADY("Stable financial week. Good for routine money management and planned purchases.", "स्थिर वित्तीय हप्ता। नियमित पैसा व्यवस्थापन र योजनाबद्ध खरिदका लागि राम्रो।"),
    WEEKLY_FINANCE_CHALLENGING("Financial caution advised this week. Avoid impulsive spending and postpone major investments.", "यस हप्ता वित्तीय सावधानीको सल्लाह दिइन्छ। आवेगपूर्ण खर्चबाट बच्नुहोस् र ठूला लगानीहरू स्थगित गर्नुहोस्।"),

    WEEKLY_FAMILY_EXCELLENT("Harmonious family week ahead. Celebrations, gatherings, and quality time strengthen bonds. Support flows both ways.", "सामञ्जस्यपूर्ण पारिवारिक हप्ता। उत्सव, जमघट र गुणस्तरीय समयले बन्धनलाई बलियो बनाउँछ। दुवै तर्फबाट सहयोग मिल्नेछ।"),
    WEEKLY_FAMILY_STEADY("Good week for family matters. Focus on communication and shared activities.", "पारिवारिक मामिलाका लागि राम्रो हप्ता। सञ्चार र साझा गतिविधिहरूमा ध्यान दिनुहोस्।"),
    WEEKLY_FAMILY_CHALLENGING("Family dynamics may be challenging this week. Choose understanding over confrontation.", "यस हप्ता पारिवारिक गतिशीलता चुनौतीपूर्ण हुन सक्छ। टकराव भन्दा समझदारी रोज्नुहोस्।"),

    WEEKLY_SPIRITUALITY_EXCELLENT("Profound spiritual week. Meditation, reflection, and inner guidance are heightened. Seek meaningful experiences.", "गहिरो आध्यात्मिक हप्ता। ध्यान, चिन्तन र आन्तरिक मार्गदर्शन बढेको छ। अर्थपूर्ण अनुभवहरू खोज्नुहोस्।"),
    WEEKLY_SPIRITUALITY_STEADY("Steady spiritual energy. Maintain your practices and stay connected to your inner self.", "स्थिर आध्यात्मिक ऊर्जा। आफ्नो अभ्यासहरू कायम राख्नुहोस् र आफ्नो आन्तरिक स्वयंसँग जोडिनुहोस्।"),
    WEEKLY_SPIRITUALITY_CHALLENGING("Spiritual connection may feel elusive. Simple practices and patience will help restore balance.", "आध्यात्मिक सम्बन्ध टाढा महसुस हुन सक्छ। सरल अभ्यास र धैर्यताले सन्तुलन पुनर्स्थापित गर्न मद्दत गर्नेछ।"),

    // ============================================
    // GOCHARA EFFECTS (Planetary Influences)
    // ============================================
    // Sun
    GOCHARA_SUN_3("Courage and valor increase. Victory over rivals.", "साहस र पराक्रम बढ्छ। प्रतिद्वन्द्वीहरूमाथि विजय।"),
    GOCHARA_SUN_6("Destruction of enemies. Health improves. Debts decrease.", "शत्रुहरूको विनाश। स्वास्थ्यमा सुधार। ऋण घट्छ।"),
    GOCHARA_SUN_10("Professional success and recognition. Authority increases.", "व्यावसायिक सफलता र मान्यता। अधिकार बढ्छ।"),
    GOCHARA_SUN_11("Gains of wealth. Fulfillment of desires. Success in endeavors.", "धन लाभ। इच्छाहरूको पूर्ति। प्रयासहरूमा सफलता।"),
    GOCHARA_SUN_1("Health issues. Ego challenges. Conflicts with authority.", "स्वास्थ्य समस्याहरू। अहंकारका चुनौतीहरू। अधिकारसँग द्वन्द्व।"),
    GOCHARA_SUN_2("Financial difficulties. Family disputes. Speech issues.", "वित्तीय कठिनाइहरू। पारिवारिक विवाद। वाणीमा समस्या।"),
    GOCHARA_SUN_4("Domestic unrest. Mental worry. Vehicle problems.", "घरेलु अशान्ति। मानसिक चिन्ता। सवारी साधनमा समस्या।"),
    GOCHARA_SUN_5("Obstacles to children. Poor decisions. Speculation loss.", "सन्तानमा अवरोध। गलत निर्णयहरू। सट्टाबाजीमा घाटा।"),
    GOCHARA_SUN_7("Relationship strain. Partnership challenges.", "सम्बन्धमा तनाव। साझेदारीमा चुनौतीहरू।"),
    GOCHARA_SUN_8("Health concerns. Unexpected problems. Hidden enemies.", "स्वास्थ्य चिन्ताहरू। अप्रत्याशित समस्याहरू। लुकेका शत्रुहरू।"),
    GOCHARA_SUN_9("Obstacles in luck. Difficulties with father/teacher.", "भाग्यमा बाधा। पिता/गुरुसँग कठिनाइहरू।"),
    GOCHARA_SUN_12("Expenses increase. Sleep disturbances. Hidden losses.", "खर्च बढ्छ। निद्रामा गडबडी। लुकेका घाटा।"),

    // Moon
    GOCHARA_MOON_1("Mental peace and satisfaction. Good health and comforts.", "मानसिक शान्ति र सन्तुष्टि। राम्रो स्वास्थ्य र सुविधाहरू।"),
    GOCHARA_MOON_3("Courage increases. Success in short journeys. Good relations with siblings.", "साहस बढ्छ। छोटो यात्रामा सफलता। भाइबहिनीसँग राम्रो सम्बन्ध।"),
    GOCHARA_MOON_6("Victory over enemies. Relief from debts and diseases.", "शत्रुमाथि विजय। ऋण र रोगबाट राहत।"),
    GOCHARA_MOON_7("Pleasure through spouse. Partnership gains. Social happiness.", "जीवनसाथीबाट सुख। साझेदारीमा लाभ। सामाजिक खुसी।"),
    GOCHARA_MOON_10("Success in profession. Recognition from superiors.", "पेशामा सफलता। उच्च अधिकारीहरूबाट मान्यता।"),
    GOCHARA_MOON_11("Financial gains. Fulfillment of desires. Social success.", "वित्तीय लाभ। इच्छाहरूको पूर्ति। सामाजिक सफलता।"),
    GOCHARA_MOON_2("Financial fluctuations. Emotional eating issues.", "वित्तीय उतार-चढाव। भावनात्मक खानपानको समस्या।"),
    GOCHARA_MOON_4("Mental restlessness. Domestic worries.", "मानसिक अशान्ति। घरेलु चिन्ताहरू।"),
    GOCHARA_MOON_5("Emotional challenges with children. Poor speculation.", "सन्तानसँग भावनात्मक चुनौतीहरू। गलत सट्टाबाजी।"),
    GOCHARA_MOON_8("Emotional turmoil. Hidden anxieties. Health vulnerabilities.", "भावनात्मक हलचल। लुकेका चिन्ताहरू। स्वास्थ्य कमजोरी।"),
    GOCHARA_MOON_9("Spiritual doubts. Emotional distance from teachers.", "आध्यात्मिक शङ्काहरू। गुरुहरूबाट भावनात्मक दूरी।"),
    GOCHARA_MOON_12("Sleep issues. Expenses. Emotional withdrawal.", "निद्राको समस्या। खर्च। भावनात्मक विरक्ति।"),

    // Mars
    GOCHARA_MARS_3("Courage and determination. Victory in competitions. Energy for initiatives.", "साहस र दृढ संकल्प। प्रतियोगिताहरूमा विजय। पहलका लागि ऊर्जा।"),
    GOCHARA_MARS_6("Defeat of enemies. Success through effort. Good for legal matters.", "शत्रुहरूको पराजय। प्रयासबाट सफलता। कानूनी मामिलाका लागि राम्रो।"),
    GOCHARA_MARS_11("Financial gains through effort. Achievement of goals. Success in ventures.", "प्रयासबाट वित्तीय लाभ। लक्ष्य प्राप्ति। उद्यमहरूमा सफलता।"),
    GOCHARA_MARS_1("Impulsive actions. Accidents. Health issues. Anger.", "आवेगपूर्ण कार्यहरू। दुर्घटना। स्वास्थ्य समस्या। क्रोध।"),
    GOCHARA_MARS_2("Financial losses through haste. Family arguments.", "हतारका कारण वित्तीय घाटा। पारिवारिक बहस।"),
    GOCHARA_MARS_4("Domestic conflicts. Property disputes. Mother's health.", "घरेलु द्वन्द्व। सम्पत्ति विवाद। आमाको स्वास्थ्य।"),
    GOCHARA_MARS_5("Children's issues. Poor decisions. Speculation loss.", "सन्तानका समस्याहरू। गलत निर्णय। सट्टाबाजीमा घाटा।"),
    GOCHARA_MARS_7("Relationship conflicts. Partnership disputes.", "सम्बन्धमा द्वन्द्व। साझेदारीमा विवाद।"),
    GOCHARA_MARS_8("Accidents. Surgeries. Hidden enemies active.", "दुर्घटना। शल्यक्रिया। लुकेका शत्रुहरू सक्रिय।"),
    GOCHARA_MARS_9("Conflicts with teachers. Father's health.", "गुरुहरूसँग द्वन्द्व। पिताको स्वास्थ्य।"),
    GOCHARA_MARS_10("Professional conflicts. Authority issues.", "व्यावसायिक द्वन्द्व। अधिकारका समस्याहरू।"),
    GOCHARA_MARS_12("Hidden enemies. Expenses. Hospitalization risk.", "लुकेका शत्रुहरू। खर्च। अस्पताल भर्ना हुने जोखिम।"),

    // Mercury
    GOCHARA_MERCURY_2("Gains through speech and intellect. Family harmony. Financial gains.", "वाणी र बुद्धिबाट लाभ। पारिवारिक सद्भाव। वित्तीय लाभ।"),
    GOCHARA_MERCURY_4("Domestic happiness. Property matters favorable. Mental peace.", "घरेलु खुसी। सम्पत्ति मामिलाहरू अनुकूल। मानसिक शान्ति।"),
    GOCHARA_MERCURY_6("Victory over competitors. Success in studies. Sharp intellect.", "प्रतिस्पर्धीहरूमाथि विजय। अध्ययनमा सफलता। तीक्ष्ण बुद्धि।"),
    GOCHARA_MERCURY_8("Gains through research. Understanding occult matters.", "अनुसन्धानबाट लाभ। तन्त्रमन्त्रका कुराहरू बुझ्ने।"),
    GOCHARA_MERCURY_10("Professional success. Recognition for intelligence. Business growth.", "व्यावसायिक सफलता। बुद्धिका लागि मान्यता। व्यापार वृद्धि।"),
    GOCHARA_MERCURY_11("Financial gains through communication. Network expansion.", "सञ्चारबाट वित्तीय लाभ। नेटवर्क विस्तार।"),
    GOCHARA_MERCURY_1("Nervous tension. Skin issues. Restless mind.", "स्नायु तनाव। छालाको समस्या। अशान्त मन।"),
    GOCHARA_MERCURY_3("Communication problems. Sibling issues. Short trips troubled.", "सञ्चार समस्या। भाइबहिनीका समस्या। छोटो यात्राहरूमा समस्या।"),
    GOCHARA_MERCURY_5("Poor decisions. Learning difficulties.", "गलत निर्णय। सिकाइमा कठिनाइ।"),
    GOCHARA_MERCURY_7("Partnership misunderstandings. Contract issues.", "साझेदारीमा गलतफहमी। सम्झौता समस्याहरू।"),
    GOCHARA_MERCURY_9("Educational obstacles. Communication with father strained.", "शैक्षिक अवरोध। पितासँगको सञ्चारमा तनाव।"),
    GOCHARA_MERCURY_12("Mental anxieties. Hidden worries. Poor sleep.", "मानसिक चिन्ता। लुकेका पीरहरू। कम निद्रा।"),

    // Jupiter
    GOCHARA_JUPITER_2("Wealth increases. Family harmony. Sweet speech. Good food.", "धन बढ्छ। पारिवारिक सद्भाव। मधुर वाणी। राम्रो भोजन।"),
    GOCHARA_JUPITER_5("Intelligence flourishes. Good for children. Romance. Creativity.", "बुद्धि फस्टाउँछ। सन्तानका लागि राम्रो। रोमान्स। सिर्जनशीलता।"),
    GOCHARA_JUPITER_7("Partnership success. Marriage prospects. Business partnerships.", "साझेदारीमा सफलता। विवाहको सम्भावना। व्यापारिक साझेदारी।"),
    GOCHARA_JUPITER_9("Spiritual growth. Luck and fortune. Father's blessings. Pilgrimage.", "आध्यात्मिक वृद्धि। भाग्य र किस्मत। पिताको आशीर्वाद। तीर्थयात्रा।"),
    GOCHARA_JUPITER_11("Major gains. Fulfillment of desires. Eldest sibling's success.", "प्रमुख लाभ। इच्छाहरूको पूर्ति। जेठो भाइबहिनीको सफलता।"),
    GOCHARA_JUPITER_1("Weight gain. Overconfidence. Health issues.", "तौल बढ्ने। अत्यधिक आत्मविश्वास। स्वास्थ्य समस्या।"),
    GOCHARA_JUPITER_3("Reduced courage. Sibling issues.", "साहस घट्ने। भाइबहिनीका समस्या।"),
    GOCHARA_JUPITER_4("Domestic expansion issues. Property disputes.", "घरेलु विस्तारमा समस्या। सम्पत्ति विवाद।"),
    GOCHARA_JUPITER_6("Debts may increase. Enemy problems.", "ऋण बढ्न सक्छ। शत्रुको समस्या।"),
    GOCHARA_JUPITER_8("Unexpected expenses. Health vulnerabilities.", "अप्रत्याशित खर्च। स्वास्थ्य कमजोरी।"),
    GOCHARA_JUPITER_10("Professional setbacks. Reputation challenges.", "व्यावसायिक अवरोध। प्रतिष्ठामा चुनौती।"),
    GOCHARA_JUPITER_12("Expenses. Foreign troubles. Spiritual doubts.", "खर्च। विदेशी समस्या। आध्यात्मिक शङ्का।"),

    // Venus
    GOCHARA_VENUS_1("Personal charm increases. Attraction and luxury. Good health.", "व्यक्तिगत आकर्षण बढ्छ। आकर्षण र विलासिता। राम्रो स्वास्थ्य।"),
    GOCHARA_VENUS_2("Wealth and family happiness. Good food and comforts.", "धन र पारिवारिक खुसी। राम्रो भोजन र सुविधाहरू।"),
    GOCHARA_VENUS_3("Artistic talents shine. Harmonious relations with siblings.", "कलात्मक प्रतिभा चम्किन्छ। भाइबहिनीसँग सामञ्जस्यपूर्ण सम्बन्ध।"),
    GOCHARA_VENUS_4("Domestic bliss. Vehicle and property gains. Mother's blessings.", "घरेलु आनन्द। सवारी साधन र सम्पत्ति लाभ। आमाको आशीर्वाद।"),
    GOCHARA_VENUS_5("Romance and creativity. Pleasure through children. Entertainment.", "रोमान्स र सिर्जनशीलता। सन्तानबाट सुख। मनोरञ्जन।"),
    GOCHARA_VENUS_8("Unexpected gains. Inheritance matters favorable.", "अप्रत्याशित लाभ। पैतृक सम्पत्ति मामिलाहरू अनुकूल।"),
    GOCHARA_VENUS_9("Fortune through relationships. Spiritual partnerships.", "सम्बन्धबाट भाग्य। आध्यात्मिक साझेदारी।"),
    GOCHARA_VENUS_11("Major gains through arts/finance. Social success.", "कला/वित्तबाट प्रमुख लाभ। सामाजिक सफलता।"),
    GOCHARA_VENUS_12("Pleasures of bed. Foreign connections favorable.", "शय्या सुख। विदेशी सम्बन्धहरू अनुकूल।"),

    // Saturn
    GOCHARA_SATURN_3("Perseverance pays off. Courage through discipline. Victory through patience.", "दृढताले फल दिन्छ। अनुशासन मार्फत साहस। धैर्य मार्फत विजय।"),
    GOCHARA_SATURN_6("Victory over enemies through persistence. Health through discipline.", "दृढता मार्फत शत्रुहरूमाथि विजय। अनुशासन मार्फत स्वास्थ्य।"),
    GOCHARA_SATURN_11("Long-term gains materialize. Slow but steady prosperity.", "दीर्घकालीन लाभहरू प्राप्त हुन्छन्। ढिलो तर स्थिर समृद्धि।"),
    GOCHARA_SATURN_1("Health issues. Depression. Physical weakness.", "स्वास्थ्य समस्या। डिप्रेसन। शारीरिक कमजोरी।"),
    GOCHARA_SATURN_2("Financial constraints. Family separation. Speech issues.", "वित्तीय अवरोध। परिवारबाट अलगाव। वाणीमा समस्या।"),
    GOCHARA_SATURN_4("Domestic stress. Mother's health. Property issues.", "घरेलु तनाव। आमाको स्वास्थ्य। सम्पत्ति समस्या।"),
    GOCHARA_SATURN_5("Children's problems. Poor decisions. Mental worry.", "सन्तानका समस्या। गलत निर्णय। मानसिक चिन्ता।"),
    GOCHARA_SATURN_7("Relationship strain. Partnership challenges. Delays in marriage.", "सम्बन्धमा तनाव। साझेदारीमा चुनौती। विवाहमा ढिलाइ।"),
    GOCHARA_SATURN_8("Chronic health issues. Hidden problems. Accidents.", "दीर्घकालीन स्वास्थ्य समस्या। लुकेका समस्या। दुर्घटना।"),
    GOCHARA_SATURN_9("Father's troubles. Spiritual obstacles. Bad luck phase.", "पिताका समस्याहरू। आध्यात्मिक अवरोध। दुर्भाग्यपूर्ण चरण।"),
    GOCHARA_SATURN_10("Career setbacks. Authority conflicts. Reputation damage.", "करियरमा अवरोध। अधिकारसँग द्वन्द्व। प्रतिष्ठामा क्षति।"),
    GOCHARA_SATURN_12("Isolation. Expenses. Sleep issues. Foreign troubles.", "एक्लोपन। खर्च। निद्राको समस्या। विदेशी समस्या।"),

    // Rahu/Ketu
    GOCHARA_RAHU_3("Courage for unconventional paths. Success through innovation.", "अपरम्परागत मार्गका लागि साहस। नवप्रवर्तन मार्फत सफलता।"),
    GOCHARA_RAHU_6("Victory over hidden enemies. Overcoming obstacles.", "लुकेका शत्रुहरूमाथि विजय। बाधाहरू पार गर्ने।"),
    GOCHARA_RAHU_10("Sudden rise in career. Foreign opportunities.", "करियरमा अचानक उदय। विदेशी अवसरहरू।"),
    GOCHARA_RAHU_11("Unexpected gains. Fulfillment of unusual desires.", "अप्रत्याशित लाभ। असामान्य इच्छाहरूको पूर्ति।"),
    GOCHARA_RAHU_1("Confusion. Wrong decisions. Health anxieties.", "भ्रम। गलत निर्णय। स्वास्थ्य चिन्ता।"),
    GOCHARA_RAHU_2("Financial deception. Family disharmony.", "वित्तीय छलकपट। पारिवारिक मनमुटाव।"),
    GOCHARA_RAHU_4("Mental confusion. Domestic issues.", "मानसिक भ्रम। घरेलु समस्या।"),
    GOCHARA_RAHU_5("Children's concerns. Poor speculation.", "सन्तानको चिन्ता। गलत सट्टाबाजी।"),
    GOCHARA_RAHU_7("Relationship deceptions. Partnership frauds.", "सम्बन्धमा छलकपट। साझेदारीमा धोखाधडी।"),
    GOCHARA_RAHU_8("Sudden problems. Hidden enemies. Health scares.", "अचानक समस्याहरू। लुकेका शत्रुहरू। स्वास्थ्य डर।"),
    GOCHARA_RAHU_9("Spiritual confusion. Issues with teachers.", "आध्यात्मिक भ्रम। गुरुहरूसँगको समस्या।"),
    GOCHARA_RAHU_12("Hidden enemies. Expenses. Foreign troubles.", "लुकेका शत्रुहरू। खर्च। विदेशी समस्या।"),

    GOCHARA_KETU_3("Spiritual courage. Success in research.", "आध्यात्मिक साहस। अनुसन्धानमा सफलता।"),
    GOCHARA_KETU_6("Healing abilities increase. Victory through spiritual means.", "उपचार क्षमता बढ्छ। आध्यात्मिक माध्यमबाट विजय।"),
    GOCHARA_KETU_9("Spiritual insights. Pilgrimage. Blessings from teachers.", "आध्यात्मिक अन्तर्दृष्टि। तीर्थयात्रा। गुरुहरूबाट आशीर्वाद।"),
    GOCHARA_KETU_11("Gains through spiritual pursuits. Liberation from desires.", "आध्यात्मिक खोजीबाट लाभ। इच्छाहरूबाट मुक्ति।"),
    GOCHARA_KETU_1("Health vulnerabilities. Lack of direction.", "स्वास्थ्य कमजोरी। दिशाहीनता।"),
    GOCHARA_KETU_2("Financial losses. Family separation.", "वित्तीय घाटा। परिवारबाट अलगाव।"),
    GOCHARA_KETU_4("Domestic detachment. Property losses.", "घरेलु विरक्ति। सम्पत्ति घाटा।"),
    GOCHARA_KETU_5("Children's issues. Poor decisions.", "सन्तानका समस्या। गलत निर्णय।"),
    GOCHARA_KETU_7("Relationship detachment. Partnership dissolution.", "सम्बन्धमा विरक्ति। साझेदारी विच्छेद।"),
    GOCHARA_KETU_8("Sudden health issues. Accidents. Hidden problems.", "अचानक स्वास्थ्य समस्या। दुर्घटना। लुकेका समस्या।"),
    GOCHARA_KETU_10("Career confusion. Direction loss.", "करियरमा भ्रम। लक्ष्य हराउनु।"),
    GOCHARA_KETU_12("Expenses. Spiritual confusion. Isolation.", "खर्च। आध्यात्मिक भ्रम। एक्लोपन।"),

    // ============================================
    // MISC LABELS
    // ============================================
    VEDHA_OBSTRUCTION(" However, %s creates Vedha obstruction, reducing benefits.", " तर, %s ले वेध अवरोध सिर्जना गर्छ, जसले लाभ कम गर्छ।"),
    ASHTAKAVARGA_STRONG(" Ashtakavarga (%d/8) strengthens results.", " अष्टकवर्ग (%d/8) ले परिणाम बलियो बनाउँछ।"),
    ASHTAKAVARGA_MODERATE(" Ashtakavarga (%d/8) moderates results.", " अष्टकवर्ग (%d/8) ले परिणाम मध्यम बनाउँछ।"),
    ASHTAKAVARGA_WEAK(" Low Ashtakavarga (%d/8) weakens results.", " कम अष्टकवर्ग (%d/8) ले परिणाम कमजोर बनाउँछ।"),
    RETROGRADE_DELAY(" %s's retrograde motion delays manifestation.", " %s को वक्री गतिले नतिजा ढिलो गराउँछ।"),
    RETROGRADE_RELIEF(" %s's retrograde provides some relief from challenges.", " %s को वक्रीले चुनौतीहरूबाट केही राहत दिन्छ।"),
    OWN_SIGN(" Strong in own sign.", " आफ्नो राशिमा बलियो।"),
    EXALTED(" Exalted - excellent results.", " उच्च - उत्कृष्ट परिणाम।"),
    DEBILITATED(" Debilitated - results weakened.", " नीच - परिणाम कमजोर।"),
    
    LUNAR_FIRST_QUARTER("First Quarter Moon", "प्रथम चतुर्थांश चन्द्रमा"),
    LUNAR_FULL_MOON("Full Moon", "पूर्णिमा"),
    LUNAR_ACTION("Good for taking action", "कार्य सुरु गर्न राम्रो"),
    LUNAR_COMPLETION("Emotional peak - completion energy", "भावनात्मक शिखर - पूर्णताको ऊर्जा"),
    
    DAY_JUPITER("Jupiter's day - auspicious for growth", "बृहस्पतिको दिन - वृद्धिको लागि शुभ"),
    DAY_VENUS("Venus's day - favorable for relationships", "शुक्रको दिन - सम्बन्धका लागि अनुकूल"),
    
    REC_AREA_CAREER("Capitalize on favorable career energy today.", "आज अनुकूल करियर ऊर्जाको फाइदा उठाउनुहोस्।"),
    REC_AREA_LOVE("Nurture your relationships with extra attention.", "आफ्नो सम्बन्धलाई अतिरिक्त ध्यान दिएर पोषण गर्नुहोस्।"),
    REC_AREA_HEALTH("Make the most of your vibrant health energy.", "आफ्नो जीवन्त स्वास्थ्य ऊर्जाको अधिकतम सदुपयोग गर्नुहोस्।"),
    REC_AREA_FINANCE("Take advantage of positive financial influences.", "सकारात्मक वित्तीय प्रभावहरूको फाइदा उठाउनुहोस्।"),
    REC_AREA_FAMILY("Spend quality time with family members.", "परिवारका सदस्यहरूसँग गुणस्तरीय समय बिताउनुहोस्।"),
    REC_AREA_SPIRITUALITY("Deepen your spiritual practices.", "आफ्नो आध्यात्मिक अभ्यासहरूलाई गहिरो बनाउनुहोस्।"),
    
    REC_ELEMENT_FIRE("Take bold action and express yourself confidently.", "साहसी कदम चाल्नुहोस् र आत्मविश्वासका साथ आफूलाई व्यक्त गर्नुहोस्।"),
    REC_ELEMENT_EARTH("Focus on practical matters and material progress.", "व्यावहारिक मामिला र भौतिक प्रगतिमा ध्यान दिनुहोस्।"),
    REC_ELEMENT_AIR("Engage in social activities and intellectual pursuits.", "सामाजिक गतिविधि र बौद्धिक कार्यहरूमा संलग्न हुनुहोस्।"),
    REC_ELEMENT_WATER("Trust your intuition and honor your emotions.", "आफ्नो अन्तर्ज्ञानमा विश्वास गर्नुहोस् र आफ्ना भावनाहरूको सम्मान गर्नुहोस्।")
}
