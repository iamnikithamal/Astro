package com.astro.storm.data.localization

/**
 * Divisional Chart Analysis string keys (Hora, Drekkana, Navamsa, Dashamsa, Dwadasamsa)
 * Used by DivisionalChartAnalyzer to provide localized interpretations.
 */
enum class StringKeyDivisional(override val en: String, override val ne: String) : StringKeyInterface {

    // ============================================
    // D-2 HORA (Wealth)
    // ============================================
    HORA_POTENTIAL_EXCEPTIONAL("Exceptional health and prosperity indicated", "असाधारण स्वास्थ्य र समृद्धिको संकेत"),
    HORA_POTENTIAL_MODERATE("Moderate indications", "मध्यम संकेतहरू"),
    HORA_POTENTIAL_CHALLENGES("Some challenges indicated - remedies beneficial", "केही चुनौतीहरूको संकेत - उपायहरू लाभदायक"),

    // Sun Hora Sources
    HORA_SUN_SOURCE_GOVT("Government positions", "सरकारी पदहरू"),
    HORA_SUN_SOURCE_GOLD("Gold investments", "सुन लगानी"),
    HORA_SUN_SOURCE_FATHER("Father's legacy", "पिताको विरासत"),
    HORA_SUN_SOURCE_AUTHORITY("Authority roles", "अधिकार भूमिकाहरू"),
    HORA_SUN_SOURCE_REAL_ESTATE("Real estate", "घरजग्गा"),
    HORA_SUN_SOURCE_ENGINEERING("Engineering", "इन्जिनियरिङ"),
    HORA_SUN_SOURCE_MILITARY("Military/Police", "सेना/प्रहरी"),
    HORA_SUN_SOURCE_SPORTS("Sports", "खेलकुद"),
    HORA_SUN_SOURCE_TEACHING("Teaching", "शिक्षण"),
    HORA_SUN_SOURCE_CONSULTANCY("Consultancy", "परामर्श"),
    HORA_SUN_SOURCE_BANKING("Banking", "बैंकिङ"),
    HORA_SUN_SOURCE_RELIGIOUS("Religious institutions", "धार्मिक संस्थाहरू"),
    HORA_SUN_SOURCE_MINING("Mining", "खनन"),
    HORA_SUN_SOURCE_OIL("Oil/Petroleum", "तेल/पेट्रोलियम"),
    HORA_SUN_SOURCE_LABOR("Labor management", "श्रम व्यवस्थापन"),
    HORA_SUN_SOURCE_LAND("Land", "जमिन"),
    HORA_SUN_SOURCE_BUSINESS("Business", "व्यापार"),
    HORA_SUN_SOURCE_COMMUNICATION("Communication", "सञ्चार"),
    HORA_SUN_SOURCE_TECHNOLOGY("Technology", "प्रविधि"),
    HORA_SUN_SOURCE_TRADE("Trade", "व्यापार"),
    HORA_SUN_SOURCE_ARTS("Arts", "कला"),
    HORA_SUN_SOURCE_ENTERTAINMENT("Entertainment", "मनोरञ्जन"),
    HORA_SUN_SOURCE_LUXURY("Luxury goods", "विलासी वस्तुहरू"),
    HORA_SUN_SOURCE_BEAUTY("Beauty industry", "सौन्दर्य उद्योग"),
    HORA_SUN_SOURCE_PUBLIC("Public dealings", "सार्वजनिक व्यवहार"),
    HORA_SUN_SOURCE_HOSPITALITY("Hospitality", "आतिथ्य"),
    HORA_SUN_SOURCE_DAIRY("Dairy", "दुग्धजन्य"),
    HORA_SUN_SOURCE_LIQUIDS("Liquids", "तरल पदार्थ"),
    HORA_SUN_SOURCE_FOREIGN("Foreign sources", "विदेशी स्रोतहरू"),
    HORA_SUN_SOURCE_UNCONVENTIONAL("Unconventional means", "अपरम्परागत माध्यम"),
    HORA_SUN_SOURCE_SPECULATION("Speculation", "अनुमान/सट्टा"),
    HORA_SUN_SOURCE_SPIRITUAL("Spiritual pursuits", "आध्यात्मिक कार्य"),
    HORA_SUN_SOURCE_OCCULT("Occult", "तन्त्रमन्त्र"),
    HORA_SUN_SOURCE_DETACHMENT("Detachment-based gains", "विरक्तिमा आधारित लाभ"),
    HORA_SUN_SOURCE_RESEARCH("Research", "अनुसन्धान"),

    // Moon Hora Sources
    HORA_MOON_SOURCE_INHERITANCE("Inheritance", "पैतृक सम्पत्ति"),
    HORA_MOON_SOURCE_MOTHER("Mother's side", "माताको पक्ष"),
    HORA_MOON_SOURCE_PEARLS("Pearls", "मोती"),
    HORA_MOON_SOURCE_SILVER("Silver", "चाँदी"),
    HORA_MOON_SOURCE_SPOUSE("Spouse's wealth", "जीवनसाथीको धन"),
    HORA_MOON_SOURCE_EDUCATIONAL("Educational trusts", "शैक्षिक गुठी"),
    HORA_MOON_SOURCE_FAMILY_BIZ("Family business", "पारिवारिक व्यवसाय"),
    HORA_MOON_SOURCE_ANCESTRAL_TRADE("Ancestral trade", "पुर्खौली व्यापार"),
    HORA_MOON_SOURCE_INTELLECTUAL("Intellectual property", "बौद्धिक सम्पत्ति"),
    HORA_MOON_SOURCE_GOVT_BENEFITS("Government benefits", "सरकारी सुविधा"),
    HORA_MOON_SOURCE_ROYAL("Royal grants", "राजकीय अनुदान"),
    HORA_MOON_SOURCE_MILITARY_PENSION("Military pension", "सैनिक पेन्सन"),
    HORA_MOON_SOURCE_OLD_WEALTH("Old wealth", "पुरानो सम्पत्ति"),
    HORA_MOON_SOURCE_DELAYED("Delayed inheritance", "ढिलो गरी प्राप्त पैतृक सम्पत्ति"),
    HORA_MOON_SOURCE_FAMILY_LAND("Land from family", "परिवारबाट प्राप्त जमिन"),

    // Hora Recommendations
    HORA_REC_FOCUS_SOURCES("Focus on indicated wealth sources for maximum prosperity", "अधिकतम समृद्धिको लागि संकेत गरिएका धन स्रोतहरूमा ध्यान दिनुहोस्"),
    HORA_REC_INVEST_FAVORABLE("Invest during favorable Dasha periods of strong Hora planets", "बलियो होरा ग्रहहरूको अनुकूल दशा अवधिमा लगानी गर्नुहोस्"),
    HORA_REC_CONSISTENT_EFFORT("Consistent effort in indicated areas will yield gradual wealth", "संकेत गरिएका क्षेत्रमा निरन्तर प्रयासले क्रमिक धन दिनेछ"),
    HORA_REC_STRENGTHEN_WEAK("Strengthen weak planets through appropriate remedies", "उपयुक्त उपायहरू मार्फत कमजोर ग्रहहरूलाई बलियो बनाउनुहोस्"),
    HORA_REC_WORSHIP_LAKSHMI("Worship Goddess Lakshmi for wealth blessings", "धनको आशीर्वादको लागि देवी लक्ष्मीको पूजा गर्नुहोस्"),
    HORA_REC_STRENGTHEN_JUP_VEN("Strengthen Jupiter and Venus through gemstones or mantras", "बृहस्पति र शुक्रलाई रत्न वा मन्त्र मार्फत बलियो बनाउनुहोस्"),
    HORA_REC_AVOID_SPECULATION("Avoid speculation; focus on steady income sources", "सट्टा/जुवाबाट बच्नुहोस्; स्थिर आय स्रोतहरूमा ध्यान दिनुहोस्"),
    HORA_REC_CAPITALIZE_STRENGTH("Capitalize on %1\$s's strength in %2\$s", "%2\$s मा %1\$s को शक्तिको सदुपयोग गर्नुहोस्"),

    // ============================================
    // D-3 DREKKANA (Siblings & Courage)
    // ============================================
    DREKKANA_SIBLING_JUPITER("Jupiter's blessing on younger siblings - prosperity for them", "साना भाइबहिनीमा बृहस्पतिको आशीर्वाद - उनीहरूको लागि समृद्धि"),
    DREKKANA_SIBLING_SATURN("Saturn indicates hard-working siblings with delayed success", "शनिले कडा परिश्रम गर्ने भाइबहिनी संकेत गर्दछ जसको सफलतामा ढिलाइ हुन्छ"),
    DREKKANA_SIBLING_MARS("Mars shows courageous and competitive siblings", "मंगलले साहसी र प्रतिस्पर्धी भाइबहिनी देखाउँछ"),

    DREKKANA_INITIATIVE_STRONG("Strong initiative ability - natural leader and pioneer", "बलियो पहल क्षमता - प्राकृतिक नेता र अग्रगामी"),
    DREKKANA_INITIATIVE_MODERATE("Moderate initiative - benefits from preparation and planning", "मध्यम पहल - तयारी र योजनाबाट फाइदा हुन्छ"),

    DREKKANA_PHYSICAL_DEPENDS("Physical courage depends on other factors", "शारीरिक साहस अन्य कारकहरूमा निर्भर गर्दछ"),
    DREKKANA_PHYSICAL_EXCEPTIONAL("Exceptional physical courage and athletic ability", "असाधारण शारीरिक साहस र एथलेटिक क्षमता"),
    DREKKANA_PHYSICAL_STRONG("Strong physical constitution and bravery", "बलियो शारीरिक बनावट र वीरता"),
    DREKKANA_PHYSICAL_DEVELOPMENT("Physical courage may need development", "शारीरिक साहसको विकास आवश्यक हुन सक्छ"),
    DREKKANA_PHYSICAL_ADEQUATE("Adequate physical courage for normal situations", "सामान्य परिस्थितिहरूको लागि पर्याप्त शारीरिक साहस"),

    DREKKANA_MENTAL_WISDOM("Exceptional wisdom-backed mental courage", "बुद्धिमा आधारित असाधारण मानसिक साहस"),
    DREKKANA_MENTAL_INTELLECTUAL("Sharp intellectual courage and quick decision-making", "तीक्ष्ण बौद्धिक साहस र छिटो निर्णय लिने क्षमता"),
    DREKKANA_MENTAL_STABLE("Stable mental fortitude", "स्थिर मानसिक दृढता"),
    DREKKANA_MENTAL_EXPERIENCE("Mental courage develops through life experiences", "जीवनका अनुभवहरू मार्फत मानसिक साहस विकसित हुन्छ"),

    DREKKANA_COMM_EXCEPTIONAL("Exceptional", "असाधारण"),
    DREKKANA_COMM_VERY_GOOD("Very Good", "धेरै राम्रो"),
    DREKKANA_COMM_GOOD("Good", "राम्रो"),
    DREKKANA_COMM_AVERAGE("Average", "औसत"),

    DREKKANA_WRITING_EXCEPTIONAL("Exceptional writing talent - potential author or journalist", "असाधारण लेखन प्रतिभा - सम्भावित लेखक वा पत्रकार"),
    DREKKANA_WRITING_DEPTH("Strong writing ability with wisdom and depth", "ज्ञान र गहिराई सहित बलियो लेखन क्षमता"),
    DREKKANA_WRITING_GOOD("Good written communication skills", "राम्रो लिखित सञ्चार सीप"),
    DREKKANA_WRITING_AVERAGE("Average writing ability", "औसत लेखन क्षमता"),

    DREKKANA_SPEAKING_ELOQUENT("Eloquent speaker with wisdom", "ज्ञान सहितको वाक्पटु वक्ता"),
    DREKKANA_SPEAKING_AUTHORITATIVE("Authoritative and commanding speech", "अधिकारपूर्ण र प्रभावशाली बोली"),
    DREKKANA_SPEAKING_QUICK("Quick and articulate speech", "छिटो र स्पष्ट बोली"),
    DREKKANA_SPEAKING_NORMAL("Normal speaking abilities", "सामान्य बोल्ने क्षमता"),

    DREKKANA_ART_VISUAL("Visual arts and aesthetics", "दृश्य कला र सौन्दर्य शास्त्र"),
    DREKKANA_ART_MUSIC("Music and rhythm", "संगीत र लय"),

    DREKKANA_JOURNEY_FREQUENT("Frequent short travels likely", "बारम्बार छोटो यात्राहरूको सम्भावना"),
    DREKKANA_JOURNEY_BUSINESS("Travel for communication or business purposes", "सञ्चार वा व्यापारिक उद्देश्यका लागि यात्रा"),
    DREKKANA_JOURNEY_UNUSUAL("Unusual or sudden short journeys", "असामान्य वा अचानक छोटो यात्राहरू"),

    DREKKANA_REC_CHANNEL_COURAGE("Channel courage into leadership and pioneering ventures", "साहसलाई नेतृत्व र अग्रगामी कार्यहरूमा लगाउनुहोस्"),
    DREKKANA_REC_MARS_ACTIVITIES("Practice Mars-strengthening activities like sports or martial arts", "खेलकुद वा मार्सल आर्ट जस्ता मंगल-बलियो बनाउने गतिविधिहरू अभ्यास गर्नुहोस्"),
    DREKKANA_REC_HANUMAN("Worship Lord Hanuman for courage", "साहसको लागि भगवान हनुमानको पूजा गर्नुहोस्"),
    DREKKANA_REC_BALANCE_CAUTION("Maintain balance between caution and boldness", "सावधानी र साहस बीच सन्तुलन कायम राख्नुहोस्"),
    DREKKANA_REC_SIBLING_PATIENCE("Practice patience and understanding with siblings", "भाइबहिनीसँग धैर्य र समझदारी अभ्यास गर्नुहोस्"),
    DREKKANA_REC_COUNSELING("Consider family counseling if needed", "आवश्यक भएमा पारिवारिक परामर्श विचार गर्नुहोस्"),

    // ============================================
    // D-9 NAVAMSA (Marriage)
    // ============================================
    NAVAMSA_TRANSIT_JUPITER("Jupiter transit over 7th house from Moon or Navamsa Lagna", "चन्द्रमा वा नवांश लग्नबाट ७औं भावमा बृहस्पतिको गोचर"),

    NAVAMSA_NATURE_SUN("Dignified, authoritative, proud, loyal", "मर्यादित, अधिकारपूर्ण, गर्विलो, वफादार"),
    NAVAMSA_NATURE_MOON("Emotional, nurturing, sensitive, caring", "भावनात्मक, पालनपोषण गर्ने, संवेदनशील, दयालु"),
    NAVAMSA_NATURE_MARS("Energetic, assertive, passionate, athletic", "ऊर्जावान, दृढ, जोसिलो, एथलेटिक"),
    NAVAMSA_NATURE_MERCURY("Intelligent, communicative, youthful, adaptable", "बुद्धिमान, मिलनसार, युवावस्थाको, अनुकूलनशील"),
    NAVAMSA_NATURE_JUPITER("Wise, religious, generous, optimistic", "ज्ञानी, धार्मिक, उदार, आशावादी"),
    NAVAMSA_NATURE_VENUS("Beautiful, artistic, romantic, pleasure-loving", "सुन्दर, कलात्मक, रोमान्टिक, विलासी"),
    NAVAMSA_NATURE_SATURN("Serious, hardworking, mature, responsible", "गम्भीर, मेहनती, परिपक्व, जिम्मेवार"),
    NAVAMSA_NATURE_RAHU("Unconventional, foreign influence, ambitious", "अपरम्परागत, विदेशी प्रभाव, महत्त्वाकांक्षी"),
    NAVAMSA_NATURE_KETU("Spiritual, detached, intuitive, mysterious", "आध्यात्मिक, विरक्त, अन्तर्ज्ञानी, रहस्यमय"),
    NAVAMSA_NATURE_MIXED("Mixed qualities", "मिश्रित गुणहरू"),

    NAVAMSA_APPEAR_SUN("Medium height, well-built, commanding presence", "मध्यम उचाई, बलियो शरीर, प्रभावशाली उपस्थिति"),
    NAVAMSA_APPEAR_MOON("Fair complexion, round face, attractive", "गोरो वर्ण, गोलो अनुहार, आकर्षक"),
    NAVAMSA_APPEAR_MARS("Athletic build, sharp features, reddish complexion", "एथलेटिक शरीर, तीक्ष्ण अनुहार, रातो वर्ण"),
    NAVAMSA_APPEAR_MERCURY("Youthful appearance, quick movements", "युवा जस्तो रूप, फुर्तिलो चाल"),
    NAVAMSA_APPEAR_JUPITER("Well-proportioned, dignified appearance", "मिलेको शरीर, मर्यादित रूप"),
    NAVAMSA_APPEAR_VENUS("Very attractive, charming, pleasant features", "अत्यन्त आकर्षक, मोहक, रमाइलो अनुहार"),
    NAVAMSA_APPEAR_SATURN("Tall, thin build, mature appearance", "अग्लो, पातलो शरीर, परिपक्व रूप"),
    NAVAMSA_APPEAR_VARIES("Appearance varies", "रूप परिवर्तनशील हुन सक्छ"),

    NAVAMSA_PROF_GOVT("Government", "सरकारी"),
    NAVAMSA_PROF_ADMIN("Administration", "प्रशासन"),
    NAVAMSA_PROF_MEDICINE("Medicine", "चिकित्सा"),
    NAVAMSA_PROF_HEALTHCARE("Healthcare", "स्वास्थ्य सेवा"),
    NAVAMSA_PROF_HOSPITALITY("Hospitality", "आतिथ्य"),
    NAVAMSA_PROF_PR("Public relations", "जनसम्पर्क"),
    NAVAMSA_PROF_ENGINEERING("Engineering", "इन्जिनियरिङ"),
    NAVAMSA_PROF_MILITARY("Military", "सेना"),
    NAVAMSA_PROF_SPORTS("Sports", "खेलकुद"),
    NAVAMSA_PROF_BUSINESS("Business", "व्यापार"),
    NAVAMSA_PROF_WRITING("Writing", "लेखन"),
    NAVAMSA_PROF_TECH("Technology", "प्रविधि"),
    NAVAMSA_PROF_TEACHING("Teaching", "शिक्षण"),
    NAVAMSA_PROF_LAW("Law", "कानुन"),
    NAVAMSA_PROF_FINANCE("Finance", "वित्त"),
    NAVAMSA_PROF_ARTS("Arts", "कला"),
    NAVAMSA_PROF_FASHION("Fashion", "फेसन"),
    NAVAMSA_PROF_ENTERTAINMENT("Entertainment", "मनोरञ्जन"),
    NAVAMSA_PROF_LABOR("Labor", "श्रम"),
    NAVAMSA_PROF_CONSTRUCTION("Construction", "निर्माण"),
    NAVAMSA_PROF_AGRICULTURE("Agriculture", "कृषि"),
    NAVAMSA_PROF_VARIOUS("Various professions possible", "विभिन्न पेशाहरू सम्भव"),
    NAVAMSA_PROF_FIELDS("Various fields", "विभिन्न क्षेत्रहरू"),

    NAVAMSA_FAM_JUPITER("Respectable, possibly from educated or religious family", "प्रतिष्ठित, सम्भवतः शिक्षित वा धार्मिक परिवार"),
    NAVAMSA_FAM_VENUS("Cultured family with appreciation for arts", "कलाको कदर गर्ने संस्कारी परिवार"),
    NAVAMSA_FAM_SUN("Family with government or administrative background", "सरकारी वा प्रशासनिक पृष्ठभूमि भएको परिवार"),
    NAVAMSA_FAM_MOON("Family-oriented, possibly business background", "पारिवारिक झुकाव भएको, सम्भवतः व्यापारिक पृष्ठभूमि"),
    NAVAMSA_FAM_SATURN("Hardworking family, possibly modest beginnings", "मेहनती परिवार, सम्भवतः साधारण सुरुवात"),
    NAVAMSA_FAM_VARIES("Family background varies", "पारिवारिक पृष्ठभूमि फरक हुन सक्छ"),

    NAVAMSA_DIR_EAST("East direction", "पूर्व दिशा"),
    NAVAMSA_DIR_SOUTH("South direction", "दक्षिण दिशा"),
    NAVAMSA_DIR_WEST("West direction", "पश्चिम दिशा"),
    NAVAMSA_DIR_NORTH("North direction", "उत्तर दिशा"),
    NAVAMSA_DIR_UNKNOWN("Direction not clearly indicated", "दिशा स्पष्ट रूपमा संकेत गरिएको छैन"),

    NAVAMSA_RISK_MULTIPLE_PLANETS("Multiple planets in 7th house", "७औं भावमा धेरै ग्रहहरू"),
    NAVAMSA_RISK_RETRO_VENUS("Retrograde Venus", "वक्री शुक्र"),
    NAVAMSA_RISK_7TH_LORD_DUSTHANA("7th lord in dusthana", "७औं स्वामी दुःस्थानमा"),
    NAVAMSA_MITIGATE_NONE("No strong multiple marriage indicators", "बहु विवाहको कुनै बलियो संकेत छैन"),

    NAVAMSA_MUHURTA_JUPITER("Best muhurta when Jupiter transits %1\$s or its trikona", "जब बृहस्पति %1\$s वा यसको त्रिकोणमा गोचर गर्छ, त्यो उत्तम मुहूर्त हो"),

    NAVAMSA_REC_VENUS_STRONG("Venus is strong - romantic approach to marriage is favorable", "शुक्र बलियो छ - विवाहप्रति रोमान्टिक दृष्टिकोण अनुकूल छ"),
    NAVAMSA_REC_VENUS_WEAK("Strengthen Venus through white colors, Fridays worship, and diamond/white sapphire", "सेतो रंग, शुक्रबारको पूजा, र हीरा/सेतो नीलम मार्फत शुक्रलाई बलियो बनाउनुहोस्"),
    NAVAMSA_REC_TIMING("Best marriage timing: During %1\$s Dasha/Antardasha", "उत्तम विवाह समय: %1\$s दशा/अन्तर्दशाको समयमा"),

    // ============================================
    // D-10 DASHAMSA (Career)
    // ============================================
    DASHAMSA_TYPE_ADMIN("Administrative/Government", "प्रशासनिक/सरकारी"),
    DASHAMSA_TYPE_PUBLIC("Public Service/Nurturing", "सार्वजनिक सेवा/हेरचाह"),
    DASHAMSA_TYPE_TECH("Technical/Competitive", "प्राविधिक/प्रतिस्पर्धात्मक"),
    DASHAMSA_TYPE_COMM("Communication/Business", "सञ्चार/व्यापार"),
    DASHAMSA_TYPE_ADVISORY("Advisory/Educational", "सल्लाहकार/शैक्षिक"),
    DASHAMSA_TYPE_CREATIVE("Creative/Luxury", "सिर्जनात्मक/विलासी"),
    DASHAMSA_TYPE_LABOR("Labor/Resource", "श्रम/स्रोत"),
    DASHAMSA_TYPE_UNCONVENTIONAL("Unconventional/Foreign", "अपरम्परागत/विदेशी"),
    DASHAMSA_TYPE_SPIRITUAL("Spiritual/Research", "आध्यात्मिक/अनुसन्धान"),
    DASHAMSA_TYPE_GENERAL("General", "सामान्य"),

    DASHAMSA_SUIT_ADMIN("Leadership positions, authority roles", "नेतृत्व पदहरू, अधिकार भूमिकाहरू"),
    DASHAMSA_SUIT_PUBLIC("People-facing roles, caring professions", "जनतासँग जोडिने भूमिकाहरू, हेरचाह गर्ने पेशाहरू"),
    DASHAMSA_SUIT_TECH("Action-oriented careers requiring courage", "साहस चाहिने कार्य-उन्मुख करियरहरू"),
    DASHAMSA_SUIT_COMM("Intellectual and analytical roles", "बौद्धिक र विश्लेषणात्मक भूमिकाहरू"),
    DASHAMSA_SUIT_ADVISORY("Positions requiring wisdom and guidance", "ज्ञान र मार्गदर्शन चाहिने पदहरू"),
    DASHAMSA_SUIT_CREATIVE("Aesthetic and pleasure-related industries", "सौन्दर्य र आनन्द सम्बन्धित उद्योगहरू"),
    DASHAMSA_SUIT_LABOR("Careers requiring persistence and hard work", "दृढता र कडा परिश्रम चाहिने करियरहरू"),
    DASHAMSA_SUIT_UNCONVENTIONAL("Innovative and cutting-edge fields", "अभिनव र अत्याधुनिक क्षेत्रहरू"),
    DASHAMSA_SUIT_SPIRITUAL("Fields requiring deep investigation", "गहन अनुसन्धान चाहिने क्षेत्रहरू"),
    DASHAMSA_SUIT_VARIOUS("Various career possibilities", "विभिन्न करियर सम्भावनाहरू"),

    DASHAMSA_GOVT_VERY_HIGH("Very High", "अत्यन्त उच्च"),
    DASHAMSA_GOVT_HIGH("High", "उच्च"),
    DASHAMSA_GOVT_MODERATE("Moderate", "मध्यम"),
    DASHAMSA_GOVT_LOW("Low", "न्यून"),

    DASHAMSA_FACTOR_SUN_10("Sun in 10th house of D10", "D10 को १०औं भावमा सूर्य"),
    DASHAMSA_FACTOR_EXALTED_SUN("Exalted Sun", "उच्चको सूर्य"),

    DASHAMSA_BIZ_APTITUDE("Strong aptitude for business/entrepreneurship", "व्यापार/उद्यमशीलताको लागि बलियो योग्यता"),
    DASHAMSA_SERVICE_APTITUDE("Better suited for service/employment", "सेवा/जागिरको लागि बढी उपयुक्त"),
    DASHAMSA_BOTH_APTITUDE("Can excel in both business and service", "व्यापार र सेवा दुवैमा फस्टाउन सक्छ"),

    DASHAMSA_PEAK_10TH_LORD("10th lord Dasha - Primary career growth period", "१०औं स्वामीको दशा - प्राथमिक करियर विकास अवधि"),
    DASHAMSA_PEAK_10TH_SIG("Major career advancements and recognition", "प्रमुख करियर प्रगति र मान्यता"),
    DASHAMSA_PEAK_PLANET_FMT("%1\$s Dasha/Antardasha", "%1\$s दशा/अन्तर्दशा"),
    DASHAMSA_PEAK_PLANET_SIG("Career opportunities in %1\$s", "%1\$s मा करियरको अवसर"),

    DASHAMSA_MULTIPLE_10TH("Multiple planets in 10th house indicate potential for multiple careers", "१०औं भावमा धेरै ग्रहहरूले बहु करियरको सम्भावना संकेत गर्दछ"),
    DASHAMSA_MERC_VERSATILE("Strong Mercury suggests versatility in career", "बलियो बुधले करियरमा बहुमुखी प्रतिभा सुझाव दिन्छ"),
    DASHAMSA_RAHU_UNCONVENTIONAL("Rahu indicates unconventional career path or career changes", "राहुले अपरम्परागत करियर मार्ग वा करियर परिवर्तन संकेत गर्दछ"),

    DASHAMSA_STRENGTH_IDENTITY("Strong professional identity and presence", "बलियो व्यावसायिक पहिचान र उपस्थिति"),
    DASHAMSA_STRENGTH_EXCEPTIONAL("Exceptional abilities in chosen field", "रोजिएको क्षेत्रमा असाधारण क्षमता"),
    DASHAMSA_STRENGTH_LEADERSHIP("Leadership and authority", "नेतृत्व र अधिकार"),
    DASHAMSA_STRENGTH_PUBLIC("Public appeal and emotional intelligence", "जनताको आकर्षण र भावनात्मक बुद्धि"),
    DASHAMSA_STRENGTH_DRIVE("Drive and competitive spirit", "जोस र प्रतिस्पर्धी भावना"),
    DASHAMSA_STRENGTH_COMM("Communication and analytical skills", "सञ्चार र विश्लेषणात्मक सीप"),
    DASHAMSA_STRENGTH_WISDOM("Wisdom and advisory capabilities", "ज्ञान र सल्लाहकार क्षमता"),
    DASHAMSA_STRENGTH_CREATIVITY("Creativity and relationship skills", "सिर्जनशीलता र सम्बन्ध सीप"),
    DASHAMSA_STRENGTH_PERSISTENCE("Persistence and organizational ability", "दृढता र संगठनात्मक क्षमता"),

    DASHAMSA_REC_PRIMARY_FOCUS("Primary career focus: %1\$s", "प्राथमिक करियर फोकस: %1\$s"),
    DASHAMSA_REC_TOP_INDUSTRIES("Top industries: %1\$s", "प्रमुख उद्योगहरू: %1\$s"),
    DASHAMSA_REC_PERIOD_FAVORS("%1\$s period favors: %2\$s", "%1\$s को अवधि अनुकूल छ: %2\$s"),

    // ============================================
    // D-12 DWADASAMSA (Parents)
    // ============================================
    DWADASAMSA_FATHER_WELLBEING_GOOD("Good health and prosperity indicated", "राम्रो स्वास्थ्य र समृद्धिको संकेत"),
    DWADASAMSA_MOTHER_WELLBEING_GOOD("Good health and emotional wellbeing indicated", "राम्रो स्वास्थ्य र भावनात्मक कल्याणको संकेत"),

    DWADASAMSA_FATHER_CHAR_AUTHORITATIVE("Authoritative, successful, government/administrative role", "अधिकारपूर्ण, सफल, सरकारी/प्रशासनिक भूमिका"),
    DWADASAMSA_FATHER_CHAR_AMBITIOUS("Career-focused, ambitious, prominent position", "करियर-केन्द्रित, महत्त्वाकांक्षी, प्रतिष्ठित पद"),
    DWADASAMSA_FATHER_CHAR_RELIGIOUS("Religious, wise, generous", "धार्मिक, ज्ञानी, उदार"),
    DWADASAMSA_FATHER_CHAR_VARIES("Characteristics vary based on overall chart", "समग्र कुण्डलीको आधारमा विशेषताहरू फरक हुन सक्छन्"),

    DWADASAMSA_MOTHER_CHAR_NURTURING("Nurturing, emotionally supportive, prosperous", "हेरचाह गर्ने, भावनात्मक रूपमा सहयोगी, समृद्ध"),
    DWADASAMSA_MOTHER_CHAR_HOME("Home-oriented, caring, family-focused", "घर-उन्मुख, दयालु, परिवार-केन्द्रित"),
    DWADASAMSA_MOTHER_CHAR_ARTISTIC("Artistic, beautiful, comfort-loving", "कलात्मक, सुन्दर, आराम-प्रेमी"),
    DWADASAMSA_MOTHER_CHAR_VARIES("Characteristics vary based on overall chart", "समग्र कुण्डलीको आधारमा विशेषताहरू फरक हुन सक्छन्"),

    DWADASAMSA_REL_DEPENDS("Relationship quality depends on other factors", "सम्बन्धको गुणस्तर अन्य कारकहरूमा निर्भर गर्दछ"),
    DWADASAMSA_REL_STRONG("Strong, supportive relationship", "बलियो, सहयोगी सम्बन्ध"),
    DWADASAMSA_REL_HARMONIOUS("Harmonious, beneficial relationship", "सामञ्जस्यपूर्ण, लाभदायक सम्बन्ध"),
    DWADASAMSA_REL_CHALLENGES("Some challenges in relationship", "सम्बन्धमा केही चुनौतीहरू"),
    DWADASAMSA_REL_MODERATE("Moderate relationship quality", "मध्यम सम्बन्ध गुणस्तर"),

    DWADASAMSA_INH_GOOD("Good inheritance potential", "राम्रो पैतृक सम्पत्ति सम्भावना"),
    DWADASAMSA_INH_MODERATE("Moderate inheritance", "मध्यम पैतृक सम्पत्ति"),
    DWADASAMSA_INH_LIMITED("Limited inheritance indicated", "सीमित पैतृक सम्पत्तिको संकेत"),

    DWADASAMSA_SOURCE_WEALTH("Family wealth", "पारिवारिक धन"),
    DWADASAMSA_SOURCE_PROPERTY("Property/Land", "सम्पत्ति/जमिन"),
    DWADASAMSA_SOURCE_UNEXPECTED("Unexpected gains", "अप्रत्याशित लाभ"),

    DWADASAMSA_INH_TIMING("During Dasha of benefic planets in 2nd, 4th, or 8th house of D-12", "D-12 को २औं, ४औं वा ८औं भावमा रहेका शुभ ग्रहहरूको दशाको समयमा"),

    DWADASAMSA_PROP_OLD("Old ancestral property indicated", "पुरानो पुर्ख्यौली सम्पत्तिको संकेत"),
    DWADASAMSA_PROP_LAND("Land or real estate from ancestors", "पुर्खाहरूबाट प्राप्त जमिन वा घरजग्गा"),
    DWADASAMSA_PROP_RELIGIOUS("Religious or educational property", "धार्मिक वा शैक्षिक सम्पत्ति"),

    DWADASAMSA_FAM_NOBLE("Noble or respected family lineage", "कुलीन वा सम्मानित पारिवारिक वंश"),
    DWADASAMSA_FAM_DHARMA("Family known for dharma or profession", "धर्म वा पेशाको लागि परिचित परिवार"),
    DWADASAMSA_FAM_SUCCESS("Family with creative or financial success", "सिर्जनात्मक वा आर्थिक सफलता भएको परिवार"),
    DWADASAMSA_FAM_DEPENDS("Family lineage characteristics depend on other factors", "पारिवारिक वंशका विशेषताहरू अन्य कारकहरूमा निर्भर गर्दछ"),

    DWADASAMSA_LONG_INDICATED("Long life indicated", "लामो आयुको संकेत"),
    DWADASAMSA_LONG_ATTENTION("Health attention needed", "स्वास्थ्यमा ध्यान आवश्यक"),
    DWADASAMSA_LONG_MODERATE("Moderate longevity", "मध्यम आयु"),

    DWADASAMSA_CONCERN_FATHER("Father: Regular health checkups advised", "पिता: नियमित स्वास्थ्य जाँचको सल्लाह"),
    DWADASAMSA_CONCERN_MOTHER("Mother: Emotional wellbeing attention", "माता: भावनात्मक कल्याणमा ध्यान"),

    DWADASAMSA_REC_FATHER_SUN("Offer water to Sun on Sundays for father's wellbeing", "पिताको कल्याणको लागि आइतबार सूर्यलाई जल अर्पण गर्नुहोस्"),
    DWADASAMSA_REC_FATHER_RESPECT("Respect and serve father figures", "पिता समान व्यक्तित्वहरूको सम्मान र सेवा गर्नुहोस्"),
    DWADASAMSA_REC_MOTHER_MOON("Worship Moon on Mondays for mother's health", "माताको स्वास्थ्यको लागि सोमबार चन्द्रमाको पूजा गर्नुहोस्"),
    DWADASAMSA_REC_MOTHER_CHARITY("Offer milk/white items in charity", "दूध/सेतो वस्तुहरू दान गर्नुहोस्"),
    DWADASAMSA_REC_PITRU_TARPAN("Perform Pitru Tarpan during Pitru Paksha for ancestral blessings", "पुर्खाहरूको आशीर्वादको लागि पितृ पक्षमा पितृ तर्पण गर्नुहोस्"),

    // ============================================
    // ENUMS & LEVELS (Divisional)
    // ============================================
    REL_QUAL_EXCELLENT("Excellent", "उत्कृष्ट"),
    REL_QUAL_GOOD("Good", "राम्रो"),
    REL_QUAL_NEUTRAL("Neutral", "तटस्थ"),
    REL_QUAL_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    REL_QUAL_DIFFICULT("Difficult", "कठिन")
}
