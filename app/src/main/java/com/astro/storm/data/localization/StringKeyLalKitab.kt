package com.astro.storm.data.localization

/**
 * Lal Kitab specific string keys
 */
enum class StringKeyLalKitab(override val en: String, override val ne: String) : StringKeyInterface {
    SYSTEM_NOTE("Lal Kitab is a distinct astrological system from classical Vedic astrology. These remedies are based on Lal Kitab principles and traditions.", "लाल किताब शास्त्रीय वैदिक ज्योतिषभन्दा फरक एक विशिष्ट ज्योतिष प्रणाली हो। यी उपायहरू लाल किताबका सिद्धान्त र परम्परामा आधारित छन्।"),
    
    // Affliction Types
    AFFL_PITRU("Ancestral karma affecting current life", "वर्तमान जीवनलाई प्रभावित गर्ने पुर्ख्यौली कर्म"),
    AFFL_PITRU_PROGENY("Obstacles in progeny matters", "सन्तान सम्बन्धी मामिलामा अवरोध"),
    AFFL_PITRU_HEALTH("Health issues related to father's lineage", "पिताको वंशसँग सम्बन्धित स्वास्थ्य समस्या"),
    
    AFFL_MATRU("Debt towards mother figure", "माता समान व्यक्तिप्रतिको ऋण"),
    AFFL_MATRU_PROP("Property and comfort matters affected", "सम्पत्ति र सुख-सुविधाका मामिलाहरू प्रभावित"),
    AFFL_MATRU_PEACE("Mental peace disturbances", "मानसिक शान्तिमा अवरोध"),
    
    AFFL_STRI("Debt towards women/wife", "महिला/पत्नीप्रतिको ऋण"),
    AFFL_STRI_MARR("Marriage and relationship challenges", "विवाह र सम्बन्धमा चुनौतीहरू"),
    AFFL_STRI_FIN("Financial fluctuations", "आर्थिक उतार-चढाव"),
    
    AFFL_KANYA("Debt towards unmarried girls/daughters", "अविवाहित केटी/छोरीहरूप्रतिको ऋण"),
    AFFL_KANYA_CHILD("Female child related matters", "छोरी सम्बन्धी मामिलाहरू"),
    AFFL_KANYA_EDU("Education disruptions", "शिक्षामा अवरोध"),
    
    AFFL_GRAHAN("Eclipse-like effect on planet's significations", "ग्रहको कारकत्वमा ग्रहण जस्तो प्रभाव"),
    AFFL_GRAHAN_SUDDEN("Sudden obstacles and confusions", "अचानक अवरोध र भ्रम"),
    AFFL_GRAHAN_HIDDEN("Hidden matters surfacing", "लुकेका कुराहरू बाहिर आउने"),
    
    AFFL_SHANI("Saturn's restrictive influence", "शनिको प्रतिबन्धात्मक प्रभाव"),
    AFFL_SHANI_DELAY("Delays and obstacles", "ढिलाइ र अवरोधहरू"),
    AFFL_SHANI_INTENSE("Karmic lessons intensified", "कार्मिक पाठहरू तीव्र पारिएको"),

    // Planet House Effects
    EFFECT_SUN_AUTH("Authority challenges", "अधिकारमा चुनौतीहरू"),
    EFFECT_SUN_GOVT("Government-related obstacles", "सरकारसँग सम्बन्धित अवरोधहरू"),
    EFFECT_SUN_FATHER("Father's health concerns", "बुबाको स्वास्थ्यमा चिन्ता"),
    EFFECT_SUN_GEN("Leadership matters affected by house placement", "घरको स्थितिका कारण नेतृत्वका मामिलाहरू प्रभावित"),
    
    EFFECT_MOON_PEACE("Mental peace disturbances", "मानसिक शान्तिमा अवरोध"),
    EFFECT_MOON_MOTHER("Mother's health concerns", "आमाको स्वास्थ्यमा चिन्ता"),
    EFFECT_MOON_EMO("Emotional fluctuations", "भावनात्मक उतार-चढाव"),
    EFFECT_MOON_GEN("Emotional matters influenced by house placement", "घरको स्थितिका कारण भावनात्मक मामिलाहरू प्रभावित"),
    
    EFFECT_MARS_PROP("Property disputes possible", "सम्पत्ति विवादको सम्भावना"),
    EFFECT_MARS_REL("Relationship conflicts", "सम्बन्धमा द्वन्द्व"),
    EFFECT_MARS_ACC("Accident-prone periods", "दुर्घटनाको जोखिम हुने अवधि"),
    EFFECT_MARS_GEN("Energy and courage matters affected", "ऊर्जा र साहसका मामिलाहरू प्रभावित"),
    
    EFFECT_MERC_COMM("Communication difficulties", "सञ्चारमा कठिनाइहरू"),
    EFFECT_MERC_BIZ("Business challenges", "व्यापारमा चुनौतीहरू"),
    EFFECT_MERC_EDU("Education obstacles", "शिक्षामा अवरोधहरू"),
    EFFECT_MERC_GEN("Intellectual matters influenced", "बौद्धिक मामिलाहरू प्रभावित"),
    
    EFFECT_JUP_WISDOM("Wisdom blocked", "ज्ञानमा अवरोध"),
    EFFECT_JUP_CHILD("Children-related challenges", "सन्तान सम्बन्धी चुनौतीहरू"),
    EFFECT_JUP_SPIRIT("Spiritual obstacles", "आध्यात्मिक अवरोधहरू"),
    EFFECT_JUP_GEN("Fortune matters affected", "भाग्यका मामिलाहरू प्रभावित"),
    
    EFFECT_VENUS_MARR("Marriage delays or conflicts", "विवाहमा ढिलाइ वा द्वन्द्व"),
    EFFECT_VENUS_REL("Relationship troubles", "सम्बन्धमा समस्या"),
    EFFECT_VENUS_LUXURY("Luxury denied", "सुख-सुविधाबाट वञ्चित"),
    EFFECT_VENUS_GEN("Relationship and comfort matters affected", "सम्बन्ध र सुविधाका मामिलाहरू प्रभावित"),
    
    EFFECT_SAT_HEALTH("Health and longevity concerns", "स्वास्थ्य र दीर्घायुमा चिन्ता"),
    EFFECT_SAT_HOME("Home comfort issues", "घरेलु सुविधामा समस्या"),
    EFFECT_SAT_PARTNER("Partnership delays", "साझेदारीमा ढिलाइ"),
    EFFECT_SAT_GEN("Karmic matters activated", "कार्मिक मामिलाहरू सक्रिय"),
    
    EFFECT_RAHU_ID("Identity confusion", "पहिचानमा भ्रम"),
    EFFECT_RAHU_SUDDEN("Sudden relationship issues", "अचानक सम्बन्धमा समस्या"),
    EFFECT_RAHU_FOREIGN("Foreign-related complications", "विदेशसँग सम्बन्धित जटिलताहरू"),
    EFFECT_RAHU_GEN("Unconventional matters affected", "अपरम्परागत मामिलाहरू प्रभावित"),
    
    EFFECT_KETU_WEALTH("Family wealth affected", "पारिवारिक धन प्रभावित"),
    EFFECT_KETU_CHILD("Children-related concerns", "सन्तान सम्बन्धी चिन्ता"),
    EFFECT_KETU_KARMA("Past karma surfacing", "विगतको कर्म बाहिर आउने"),
    EFFECT_KETU_GEN("Detachment themes activated", "विरक्तिका विषयहरू सक्रिय"),

    // Debt Descriptions
    DEBT_PITRU_DESC("Debt towards father and ancestors", "पिता र पूर्वजहरूप्रतिको ऋण"),
    DEBT_MATRU_DESC("Debt towards mother and maternal lineage", "आमा र मातृवंशप्रतिको ऋण"),
    DEBT_STRI_DESC("Debt towards wife and women", "पत्नी र महिलाहरूप्रतिको ऋण"),
    DEBT_KANYA_DESC("Debt towards unmarried girls/daughters", "अविवाहित केटी/छोरीहरूप्रतिको ऋण"),

    // Debt Indicators
    INDIC_SUN_HOUSE("Sun in %s house", "सूर्य %s भावमा"),
    INDIC_JUP_8("Jupiter in 8th house", "बृहस्पति ८औं भावमा"),
    INDIC_MOON_HOUSE("Moon in %s house", "चन्द्रमा %s भावमा"),
    INDIC_VENUS_HOUSE("Venus in %s house", "शुक्र %s भावमा"),
    INDIC_MERC_HOUSE("Mercury in %s house", "बुध %s भावमा"),

    // Debt Effects
    DEBT_PITRU_EFF_1("Obstacles in career and authority", "करियर र अधिकारमा अवरोध"),
    DEBT_PITRU_EFF_2("Father's health or relationship issues", "बुबाको स्वास्थ्य वा सम्बन्धमा समस्या"),
    DEBT_PITRU_EFF_3("Children face delays or challenges", "सन्तानले ढिलाइ वा चुनौतीको सामना गर्ने"),
    
    DEBT_MATRU_EFF_1("Mental peace disturbed", "मानसिक शान्ति बिग्रिएको"),
    DEBT_MATRU_EFF_2("Mother's health concerns", "आमाको स्वास्थ्यमा चिन्ता"),
    DEBT_MATRU_EFF_3("Property and comfort issues", "सम्पत्ति र सुविधामा समस्या"),
    
    DEBT_STRI_EFF_1("Marriage delays or conflicts", "विवाहमा ढिलाइ वा द्वन्द्व"),
    DEBT_STRI_EFF_2("Relationship troubles", "सम्बन्धमा समस्याहरू"),
    DEBT_STRI_EFF_3("Financial instability", "आर्थिक अस्थिरता"),
    
    DEBT_KANYA_EFF_1("Daughter's welfare concerns", "छोरीको कल्याणमा चिन्ता"),
    DEBT_KANYA_EFF_2("Education obstacles", "शिक्षामा अवरोध"),
    DEBT_KANYA_EFF_3("Business communication issues", "व्यापारिक सञ्चारमा समस्या"),

    // Debt Remedies
    DEBT_PITRU_REM_1("Offer water to Sun at sunrise daily", "दैनिक सूर्योदयमा सूर्यलाई जल अर्पण गर्नुहोस्"),
    DEBT_PITRU_REM_2("Feed crows with sweet bread on Saturdays", "शनिबार कागलाई गुलियो रोटी खुवाउनुहोस्"),
    DEBT_PITRU_REM_3("Perform Shraddha rituals for ancestors", "पूर्वजहरूको लागि श्राद्ध अनुष्ठान गर्नुहोस्"),
    DEBT_PITRU_REM_4("Help elderly people, especially fathers", "वृद्धहरूलाई, विशेष गरी बुबाहरूलाई मद्दत गर्नुहोस्"),
    
    DEBT_MATRU_REM_1("Serve milk or rice to mother daily", "दैनिक आमालाई दूध वा चामल खुवाउनुहोस्"),
    DEBT_MATRU_REM_2("Donate white items on Mondays", "सोमबार सेतो वस्तुहरू दान गर्नुहोस्"),
    DEBT_MATRU_REM_3("Keep silver coin given by mother", "आमाले दिएको चाँदीको सिक्का राख्नुहोस्"),
    DEBT_MATRU_REM_4("Respect and serve elderly women", "वृद्ध महिलाहरूको सम्मान र सेवा गर्नुहोस्"),
    
    DEBT_STRI_REM_1("Donate white clothes to women on Fridays", "शुक्रबार महिलाहरूलाई सेतो कपडा दान गर्नुहोस्"),
    DEBT_STRI_REM_2("Respect wife and all women", "पत्नी र सबै महिलाहरूको सम्मान गर्नुहोस्"),
    DEBT_STRI_REM_3("Offer rice and camphor at Goddess temple", "देवीको मन्दिरमा चामल र कपूर चढाउनुहोस्"),
    DEBT_STRI_REM_4("Avoid exploiting women in any way", "कुनै पनि किसिमले महिलाहरूको शोषण नगर्नुहोस्"),
    
    DEBT_KANYA_REM_1("Donate green items to unmarried girls", "अविवाहित केटीहरूलाई हरियो वस्तुहरू दान गर्नुहोस्"),
    DEBT_KANYA_REM_2("Support girls' education", "केटीहरूको शिक्षामा सहयोग गर्नुहोस्"),
    DEBT_KANYA_REM_3("Bury green glass bottle filled with honey", "महले भरिएको हरियो काँचको बोतल गाड्नुहोस्"),
    DEBT_KANYA_REM_4("Feed green vegetables to goats on Wednesdays", "बुधबार बाख्रालाई हरियो तरकारी खुवाउनुहोस्"),

    // House Significance
    SIG_1("Self, personality, health - the native's foundation", "स्वयं, व्यक्तित्व, स्वास्थ्य - जातकको आधार"),
    SIG_2("Wealth, family, speech - material foundation", "धन, परिवार, वाणी - भौतिक आधार"),
    SIG_3("Siblings, courage, communication - personal effort", "भाइबहिनी, साहस, सञ्चार - व्यक्तिगत प्रयास"),
    SIG_4("Mother, property, happiness - emotional foundation", "आमा, सम्पत्ति, सुख - भावनात्मक आधार"),
    SIG_5("Children, intelligence, past merit - creative expression", "सन्तान, बुद्धि, पूर्व पुण्य - सिर्जनात्मक अभिव्यक्ति"),
    SIG_6("Enemies, disease, service - challenges to overcome", "शत्रु, रोग, सेवा - पार गर्नुपर्ने चुनौतीहरू"),
    SIG_7("Marriage, partnership, public - relationships", "विवाह, साझेदारी, सार्वजनिक - सम्बन्ध"),
    SIG_8("Longevity, inheritance, occult - transformation", "दीर्घायु, पैतृक सम्पत्ति, तन्त्र - रूपान्तरण"),
    SIG_9("Fortune, father, dharma - blessings and guidance", "भाग्य, बुबा, धर्म - आशीर्वाद र मार्गदर्शन"),
    SIG_10("Career, status, karma - worldly achievement", "करियर, प्रतिष्ठा, कर्म - सांसारिक उपलब्धि"),
    SIG_11("Gains, income, aspirations - fulfillment", "लाभ, आय, आकांक्षाहरू - प्राप्ति"),
    SIG_12("Losses, moksha, foreign - spiritual liberation", "हानि, मोक्ष, विदेश - आध्यात्मिक मुक्ति"),
    SIG_GEN("General house matters", "सामान्य भावका मामिलाहरू"),

    // House Recommendations
    HOUSE_REC_STRENGTHEN("Strengthen %1$s for %2$s house matters", "%2$s भावका मामिलाका लागि %1$s लाई बलियो बनाउनुहोस्"),

    // Remedy Frequency
    FREQ_SUN("Daily at sunrise", "दैनिक सूर्योदयमा"),
    FREQ_MOON("Mondays", "सोमबार"),
    FREQ_MARS("Tuesdays", "मंगलबार"),
    FREQ_MERC("Wednesdays", "बुधबार"),
    FREQ_JUP("Thursdays", "बिहीबार"),
    FREQ_VENUS("Fridays", "शुक्रबार"),
    FREQ_SAT("Saturdays", "शनिबार"),
    FREQ_RAHU("Saturdays or specific days", "शनिबार वा विशेष दिनहरू"),
    FREQ_KETU("Tuesdays or Saturdays", "मंगलबार वा शनिबार"),
    FREQ_GEN("As recommended", "सिफारिस गरिए अनुसार"),

    // Color Applications
    COLOR_APP_SUN("Wear these colors on Sundays, especially in upper garments", "आइतबार यी रंगहरू लगाउनुहोस्, विशेष गरी माथिल्लो शरीरमा"),
    COLOR_APP_MOON("Prefer white clothes on Mondays, keep white items at home", "सोमबार सेतो कपडा लगाउनुहोस्, घरमा सेता वस्तुहरू राख्नुहोस्"),
    COLOR_APP_MARS("Wear red on Tuesdays, keep red items in south direction", "मंगलबार रातो लगाउनुहोस्, दक्षिण दिशामा राता वस्तुहरू राख्नुहोस्"),
    COLOR_APP_MERC("Wear green on Wednesdays, keep green plants at home", "बुधबार हरियो लगाउनुहोस्, घरमा हरियो बोटबिरुवा राख्नुहोस्"),
    COLOR_APP_JUP("Apply saffron tilak, wear yellow on Thursdays", "केशरी तिलक लगाउनुहोस्, बिहीबार पहेंलो लगाउनुहोस्"),
    COLOR_APP_VENUS("Wear white or pink on Fridays, keep white items in bedroom", "शुक्रबार सेतो वा गुलाबी लगाउनुहोस्, सुत्ने कोठामा सेता वस्तुहरू राख्नुहोस्"),
    COLOR_APP_SAT("Wear blue/black on Saturdays, donate black items", "शनिबार नीलो/कालो लगाउनुहोस्, कालो वस्तुहरू दान गर्नुहोस्"),
    COLOR_APP_RAHU("Prefer subdued colors, avoid flashy dressing", "हल्का रंगहरू रोज्नुहोस्, चम्किलो लुगाबाट बच्नुहोस्"),
    COLOR_APP_KETU("Two-toned clothes work well, avoid bright single colors", "दुई रंगका लुगाहरू राम्रो हुन्छन्, चम्किलो एकनाश रंगबाट बच्नुहोस्"),

    // Direction Applications
    DIR_APP_SUN("Face east during Sun remedies, keep important items in east", "सूर्यको उपाय गर्दा पूर्व फर्किनुहोस्, महत्त्वपूर्ण कुरा पूर्वमा राख्नुहोस्"),
    DIR_APP_MOON("Sleep with head towards northwest for mental peace", "मानसिक शान्तिको लागि उत्तर-पश्चिम तर्फ शिर राखेर सुत्नुहोस्"),
    DIR_APP_MARS("Keep red items in south, face south for Mars mantras", "दक्षिणमा राता वस्तुहरू राख्नुहोस्, मंगल मन्त्रका लागि दक्षिण फर्किनुहोस्"),
    DIR_APP_MERC("Study/work area in north direction is beneficial", "उत्तर दिशामा अध्ययन/कार्य क्षेत्र हुनु लाभदायक हुन्छ"),
    DIR_APP_JUP("Prayer area in northeast, face northeast for Jupiter remedies", "उत्तर-पूर्वमा पूजा क्षेत्र, बृहस्पतिको उपायका लागि उत्तर-पूर्व फर्किनुहोस्"),
    DIR_APP_VENUS("Keep bedroom in southeast if possible", "सम्भव भएमा सुत्ने कोठा दक्षिण-पूर्वमा राख्नुहोस्"),
    DIR_APP_SAT("West direction work benefits, donate facing west on Saturdays", "पश्चिम दिशाको कामले फाइदा दिन्छ, शनिबार पश्चिम फर्केर दान गर्नुहोस्"),
    DIR_APP_RAHU("Perform Rahu remedies facing southwest", "दक्षिण-पश्चिम फर्केर राहुका उपायहरू गर्नुहोस्"),
    DIR_APP_KETU("Northwest direction for spiritual practices", "आध्यात्मिक अभ्यासका लागि उत्तर-पश्चिम दिशा"),

    // General Recommendations
    GEN_REC_1("Lal Kitab emphasizes practical, daily remedies over expensive rituals", "लाल किताबले महँगो अनुष्ठान भन्दा व्यावहारिक, दैनिक उपायहरूमा जोड दिन्छ"),
    GEN_REC_2("Consistency in remedies is more important than occasional grand gestures", "उपायहरूमा निरन्तरता कहिलेकाहींको ठूलो अनुष्ठान भन्दा बढी महत्त्वपूर्ण छ"),
    GEN_REC_3("Charity (daan) is considered highly effective in Lal Kitab", "लाल किताबमा दानलाई अत्यन्त प्रभावकारी मानिन्छ"),
    GEN_REC_4("Respecting elders and serving the needy brings general planetary blessings", "ठूलाको सम्मान र दुखीको सेवाले सामान्य ग्रह आशीर्वाद ल्याउँछ"),
    GEN_REC_5("Keep your ancestral items with respect for Pitru dosh relief", "पितृ दोषको शान्तिका लागि आफ्ना पुर्ख्यौली वस्तुहरू सम्मानका साथ राख्नुहोस्"),
    GEN_REC_6("Avoid hoarding money - keep it flowing through charity", "पैसा थुपारेर नराख्नुहोस् - दान मार्फत यसलाई प्रवाहित राख्नुहोस्"),
    GEN_REC_7("Maintain clean kitchen and bathroom for overall planetary harmony", "समग्र ग्रह अनुकूलताका लागि भान्छा र शौचालय सफा राख्नुहोस्"),

    // Remedy Details
    REM_DURATION_43("Continue for at least 43 days", "कम्तिमा ४३ दिनसम्म जारी राख्नुहोस्"),
    REM_DURATION_LONG("Long-term practice recommended", "दीर्घकालीन अभ्यास सिफारिस गरिएको"),
    REM_FREQ_WEEKLY("Weekly or as specified", "साप्ताहिक वा तोकिए अनुसार"),
    REM_EFF_HIGH("High impact remedy", "उच्च प्रभाव पार्ने उपाय"),
    REM_EFF_MOD("Moderate impact remedy", "मध्यम प्रभाव पार्ने उपाय"),
    REM_EFF_ROOT("Addresses root karmic cause", "मूल कार्मिक कारणलाई सम्बोधन गर्दछ"),

    // Remedy Methods
    METHOD_FEEDING("Feeding ritual", "भोजन गराउने विधि"),
    METHOD_CHARITY("Charity/Donation", "दान/दक्षिणा"),
    METHOD_OFFERING("Offering ritual", "अर्पण विधि"),
    METHOD_PROTECTIVE("Protective item", "रक्षात्मक वस्तु"),
    METHOD_MANTRA("Mantra/Prayer", "मन्त्र/प्रार्थना"),
    METHOD_WATER("Water ritual", "जल विधि"),
    METHOD_EARTH("Earth ritual", "भूमि विधि"),
    METHOD_GENERAL("General remedy", "सामान्य उपाय"),

    // Planetary Remedies (Specific text strings)
    REM_SUN_WATER("Offer water to Sun at sunrise with copper vessel", "सूर्योदयमा तामाको भाँडोबाट सूर्यलाई जल अर्पण गर्नुहोस्"),
    REM_SUN_FEED("Feed wheat and jaggery to cows on Sundays", "आइतबार गाईलाई गहुँ र सख्खर खुवाउनुहोस्"),
    REM_SUN_SILVER("Keep a solid silver square piece", "चाँदीको ठोस वर्गाकार टुक्रा राख्नुहोस्"),
    REM_SUN_RUBY("Wear Ruby ring on ring finger (if suitable)", "उपयुक्त भएमा अनामिकामा माणिकको औंठी लगाउनुहोस्"),
    
    REM_MOON_SHIVA("Offer milk to Shiva Lingam on Mondays", "सोमबार शिवलिङ्गमा दूध चढाउनुहोस्"),
    REM_MOON_SILVER("Keep silver items and wear pearl", "चाँदीका वस्तुहरू राख्नुहोस् र मोती लगाउनुहोस्"),
    REM_MOON_MOTHER("Serve your mother and elderly women", "आफ्नो आमा र वृद्ध महिलाहरूको सेवा गर्नुहोस्"),
    REM_MOON_GLASS("Keep drinking water in silver glass", "चाँदीको गिलासमा खानेपानी राख्नुहोस्"),
    
    REM_MARS_HANUMAN("Recite Hanuman Chalisa daily", "दैनिक हनुमान चालीसा पाठ गर्नुहोस्"),
    REM_MARS_FEED("Feed jaggery/wheat to monkeys on Tuesdays", "मंगलबार बाँदरहरूलाई सख्खर/गहुँ खुवाउनुहोस्"),
    REM_MARS_DEER("Keep deer skin at home", "घरमा मृगको छाला राख्नुहोस्"),
    REM_MARS_DONATE("Donate red items on Tuesdays", "मंगलबार राता वस्तुहरू दान गर्नुहोस्"),
    
    REM_MERC_FEED("Feed green grass to cows", "गाईलाई हरियो घाँस खुवाउनुहोस्"),
    REM_MERC_BURY("Bury green bottle with honey in deserted place", "निर्जन ठाउँमा महसहितको हरियो बोतल गाड्नुहोस्"),
    REM_MERC_DONATE("Donate green vegetables on Wednesdays", "बुधबार हरिया तरकारीहरू दान गर्नुहोस्"),
    REM_MERC_PARROT("Keep a parrot or help parrots", "सुगा पाल्नुहोस् वा सुगाहरूलाई मद्दत गर्नुहोस्"),
    
    REM_JUP_TILAK("Apply saffron tilak on forehead", "निधारमा केशरी तिलक लगाउनुहोस्"),
    REM_JUP_PEEPAL("Offer water at Peepal tree roots", "पीपलको फेदमा जल चढाउनुहोस्"),
    REM_JUP_DONATE("Donate yellow items on Thursdays", "बिहीबार पहेंला वस्तुहरू दान गर्नुहोस्"),
    REM_JUP_BRAHMIN("Feed Brahmins and seek their blessings", "ब्राह्मणहरूलाई भोजन गराउनुहोस् र आशीर्वाद लिनुहोस्"),
    
    REM_VENUS_DONATE("Donate white items on Fridays", "शुक्रबार सेता वस्तुहरू दान गर्नुहोस्"),
    REM_VENUS_FEED("Feed white cows with wheat and sugar", "सेता गाईलाई गहुँ र चिनी खुवाउनुहोस्"),
    REM_VENUS_SILVER("Keep silver in your pocket", "आफ्नो खल्तीमा चाँदी राख्नुहोस्"),
    REM_VENUS_WOMEN("Respect and help women", "महिलाहरूको सम्मान र मद्दत गर्नुहोस्"),
    
    REM_SAT_DONATE("Donate black items on Saturdays", "शनिबार काला वस्तुहरू दान गर्नुहोस्"),
    REM_SAT_FEED("Feed crows with sweet bread", "कागलाई गुलियो रोटी खुवाउनुहोस्"),
    REM_SAT_SERVE("Serve elderly and disabled persons", "वृद्ध र अपाङ्गहरूको सेवा गर्नुहोस्"),
    REM_SAT_OIL("Offer mustard oil to Shani idol", "शनिदेवलाई तोरीको तेल चढाउनुहोस्"),
    
    REM_RAHU_DONATE("Donate radish and blue/black items", "मुला र नीलो/कालो वस्तुहरू दान गर्नुहोस्"),
    REM_RAHU_BARLEY("Keep barley under your pillow and feed fish with it next morning", "सिरानीमुनि जौ राख्नुहोस् र अर्को बिहान माछालाई खुवाउनुहोस्"),
    REM_RAHU_COCONUT("Float coconut in running water", "बगिरहेको पानीमा नरिवल बगाउनुहोस्"),
    REM_RAHU_DOGS("Feed dogs regularly", "कुकुरहरूलाई नियमित खुवाउनुहोस्"),
    
    REM_KETU_DONATE("Donate blanket to poor on Tuesdays", "मंगलबार गरिबलाई कम्बल दान गर्नुहोस्"),
    REM_KETU_DOGS("Keep a dog as pet or feed stray dogs", "कुकुर पाल्नुहोस् वा सडकका कुकुरहरूलाई खुवाउनुहोस्"),
    REM_KETU_CLOTHES("Wear two-toned (black and white) clothes", "दुई रङका (कालो र सेतो) लुगा लगाउनुहोस्"),
    REM_KETU_SESAME("Offer sesame seeds at temple", "मन्दिरमा तिल चढाउनुहोस्"),
    
    REM_HOUSE_CHARITY("Perform charity for %s house remedy", "%s भावको उपायका लागि दान गर्नुहोस्"),
    REM_HOUSE_PEACE("Avoid conflicts and maintain peace", "द्वन्द्वबाट बच्नुहोस् र शान्ति कायम राख्नुहोस्")
}
