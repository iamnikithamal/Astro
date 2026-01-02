package com.astro.storm.data.localization

enum class StringKeyPrediction(override val en: String, override val ne: String) : StringKeyInterface {
    // Ascendant Paths
    PRED_ASC_ARIES("courage, independence, and pioneering spirit", "साहस, स्वतन्त्रता र अग्रगामी भावना"),
    PRED_ASC_TAURUS("stability, material security, and perseverance", "स्थिरता, भौतिक सुरक्षा र दृढता"),
    PRED_ASC_GEMINI("communication, versatility, and intellectual pursuits", "सञ्चार, बहुमुखी प्रतिभा र बौद्धिक कार्यहरू"),
    PRED_ASC_CANCER("nurturing, emotional depth, and family connections", "हेरचाह, भावनात्मक गहिराई र पारिवारिक सम्बन्धहरू"),
    PRED_ASC_LEO("leadership, creativity, and self-expression", "नेतृत्व, रचनात्मकता र आत्म-अभिव्यक्ति"),
    PRED_ASC_VIRGO("service, analysis, and practical perfection", "सेवा, विश्लेषण र व्यावहारिक पूर्णता"),
    PRED_ASC_LIBRA("harmony, relationships, and balanced judgment", "सद्भाव, सम्बन्ध र सन्तुलित निर्णय"),
    PRED_ASC_SCORPIO("transformation, intensity, and deep research", "रूपान्तरण, गहनता र गहिरो अनुसन्धान"),
    PRED_ASC_SAGITTARIUS("wisdom, exploration, and philosophical understanding", "ज्ञान, अन्वेषण र दार्शनिक समझ"),
    PRED_ASC_CAPRICORN("discipline, ambition, and long-term achievement", "अनुशासन, महत्त्वाकांक्षा र दीर्घकालीन उपलब्धि"),
    PRED_ASC_AQUARIUS("innovation, humanitarian ideals, and progressive thinking", "नवप्रवर्तन, मानवीय आदर्श र प्रगतिशील सोच"),
    PRED_ASC_PISCES("spirituality, compassion, and transcendent wisdom", "आध्यात्मिकता, करुणा र उत्कृष्ट ज्ञान"),

    // Moon Paths
    PRED_MOON_ARIES("excitement and action", "उत्साह र कार्य"),
    PRED_MOON_TAURUS("comfort and stability", "आराम र स्थिरता"),
    PRED_MOON_GEMINI("variety and mental stimulation", "विविधता र मानसिक उत्तेजना"),
    PRED_MOON_CANCER("security and emotional connection", "सुरक्षा र भावनात्मक सम्बन्ध"),
    PRED_MOON_LEO("recognition and warmth", "मान्यता र न्यानोपन"),
    PRED_MOON_VIRGO("order and practical service", "व्यवस्था र व्यावहारिक सेवा"),
    PRED_MOON_LIBRA("partnership and aesthetic beauty", "साझेदारी र सौन्दर्य"),
    PRED_MOON_SCORPIO("depth and emotional transformation", "गहिराई र भावनात्मक रूपान्तरण"),
    PRED_MOON_SAGITTARIUS("meaning and philosophical truth", "अर्थ र दार्शनिक सत्य"),
    PRED_MOON_CAPRICORN("achievement and respect", "उपलब्धि र सम्मान"),
    PRED_MOON_AQUARIUS("freedom and humanitarian connection", "स्वतन्त्रता र मानवीय सम्बन्ध"),
    PRED_MOON_PISCES("unity and spiritual transcendence", "एकता र आध्यात्मिक उत्कृष्टता"),
    PRED_MOON_DEFAULT("emotional fulfillment", "भावनात्मक पूर्ति"),

    // Life Themes
    PRED_THEME_ARIES("Pioneer & Warrior", "अग्रगामी र योद्धा"),
    PRED_THEME_TAURUS("Builder & Preserver", "निर्माता र रक्षक"),
    PRED_THEME_GEMINI("Communicator & Learner", "सञ्चारकर्मी र शिक्षार्थी"),
    PRED_THEME_CANCER("Nurturer & Protector", "पालनकर्ता र संरक्षक"),
    PRED_THEME_LEO("Leader & Creator", "नेता र सिर्जनाकर्ता"),
    PRED_THEME_VIRGO("Server & Healer", "सेवक र उपचारक"),
    PRED_THEME_LIBRA("Diplomat & Artist", "कुटनीतिज्ञ र कलाकार"),
    PRED_THEME_SCORPIO("Transformer & Investigator", "रूपान्तरक र अन्वेषक"),
    PRED_THEME_SAGITTARIUS("Philosopher & Explorer", "दार्शनिक र अन्वेषक"),
    PRED_THEME_CAPRICORN("Achiever & Master", "सफल र निपुण"),
    PRED_THEME_AQUARIUS("Innovator & Humanitarian", "नवप्रवर्तक र मानवतावादी"),
    PRED_THEME_PISCES("Mystic & Compassionate Soul", "रहस्यवादी र दयालु आत्मा"),

    // Spiritual Paths
    PRED_SPIRIT_ARIES("self-realization through action", "कार्य मार्फत आत्म-साक्षात्कार"),
    PRED_SPIRIT_TAURUS("grounding and material transcendence", "आधार र भौतिक उत्कृष्टता"),
    PRED_SPIRIT_GEMINI("integration of duality", "द्वैतको एकीकरण"),
    PRED_SPIRIT_CANCER("emotional mastery and nurturing wisdom", "भावनात्मक निपुणता र हेरचाह ज्ञान"),
    PRED_SPIRIT_LEO("heart-centered consciousness", "हृदय-केन्द्रित चेतना"),
    PRED_SPIRIT_VIRGO("perfection through service", "सेवा मार्फत पूर्णता"),
    PRED_SPIRIT_LIBRA("balance and unity consciousness", "सन्तुलन र एकता चेतना"),
    PRED_SPIRIT_SCORPIO("death and rebirth cycles", "मृत्यु र पुनर्जन्म चक्र"),
    PRED_SPIRIT_SAGITTARIUS("truth-seeking and higher knowledge", "सत्यको खोज र उच्च ज्ञान"),
    PRED_SPIRIT_CAPRICORN("mastery and enlightened leadership", "निपुणता र प्रबुद्ध नेतृत्व"),
    PRED_SPIRIT_AQUARIUS("universal love and detachment", "विश्वव्यापी प्रेम र वैराग्य"),
    PRED_SPIRIT_PISCES("dissolution into divine consciousness", "दिव्य चेतनामि विलय"),
    PRED_SPIRIT_DEFAULT("spiritual awakening", "आध्यात्मिक जागरण"),

    // Dasha Effects (Simpler Generic ones)
    PRED_DASHA_SUN("self-confidence, authority, and father-related matters", "आत्मविश्वास, अधिकार र पिता सम्बन्धी मामिलाहरू"),
    PRED_DASHA_MOON("emotions, mind, and mother-related matters", "भावना, मन र आमा सम्बन्धी मामिलाहरू"),
    PRED_DASHA_MARS("energy, courage, and taking action", "ऊर्जा, साहस र कार्य गर्ने क्षमता"),
    PRED_DASHA_MERCURY("communication, intellect, and business", "सञ्चार, बुद्धि र व्यापार"),
    PRED_DASHA_JUPITER("wisdom, expansion, and spiritual growth", "ज्ञान, विस्तार र आध्यात्मिक वृद्धि"),
    PRED_DASHA_VENUS("relationships, luxury, and artistic pursuits", "सम्बन्ध, विलासिता र कलात्मक कार्यहरू"),
    PRED_DASHA_SATURN("discipline, karmic lessons, and hard work", "अनुशासन, कर्मिक पाठ र कडा परिश्रम"),
    PRED_DASHA_RAHU("worldly desires, unconventional paths, and material success", "सांसारिक इच्छाहरू, अपरम्परागत मार्ग र भौतिक सफलता"),
    PRED_DASHA_KETU("spirituality, detachment, and moksha", "आध्यात्मिकता, वैराग्य र मोक्ष"),
    PRED_DASHA_DEFAULT("various life experiences", "विभिन्न जीवन अनुभवहरू"),

    // Antardasha Effects
    PRED_AD_SUN("emphasis on leadership and recognition", "नेतृत्व र मान्यता मा जोड"),
    PRED_AD_MOON("heightened emotional sensitivity", "बढेको भावनात्मक संवेदनशीलता"),
    PRED_AD_MARS("increased drive and assertiveness", "बढेको उत्साह र दृढता"),
    PRED_AD_MERCURY("enhanced mental clarity", "बढेको मानसिक स्पष्टता"),
    PRED_AD_JUPITER("blessings and fortunate opportunities", " आशीर्वाद र भाग्यशाली अवसरहरू"),
    PRED_AD_VENUS("pleasure and harmonious experiences", "आनन्द र सामंजस्यपूर्ण अनुभवहरू"),
    PRED_AD_SATURN("patience and perseverance requirements", "धैर्य र लगनशीलताको आवश्यकता"),
    PRED_AD_RAHU("sudden changes and ambition", "अचानक परिवर्तन र महत्त्वाकांक्षा"),
    PRED_AD_KETU("spiritual insights and letting go", "आध्यात्मिक अन्तरदृष्टि र त्याग"),
    PRED_AD_DEFAULT("additional influences", "थप प्रभावहरू"),

    // Prediction Templates
    PRED_PATH_TEMPLATE("Your life path is shaped by your %s ascendant, indicating a personality focused on %s. With your Moon in %s, your emotional nature seeks %s.", "तपाईंको जीवन मार्ग तपाईंको %s लग्नद्वारा निर्देशित छ, जसले %s मा केन्द्रित व्यक्तित्वलाई संकेत गर्दछ। %s मा तपाईंको चन्द्रमाको साथ, तपाईंको भावनात्मक प्रकृतिले %s खोज्छ।"),
    PRED_PATH_TEMPLATE_UNKNOWN_MOON("Your life path is shaped by your %s ascendant, indicating a personality focused on %s.", "तपाईंको जीवन मार्ग तपाईंको %s लग्नद्वारा निर्देशित छ, जसले %s मा केन्द्रित व्यक्तित्वलाई संकेत गर्दछ।"),
    PRED_SPIRIT_TEMPLATE("Your spiritual journey emphasizes %s through %s influences.", "तपाईंको आध्यात्मिक यात्राले %s प्रभावहरू मार्फत %s मा जोड दिन्छ।"),
    PRED_DASHA_INFO_TEMPLATE("%s Mahadasha - %s Antardasha", "%s महादशा - %s अन्तर्दशा"),
    PRED_DASHA_PERIOD_DEFAULT("Dasha Period", "दशा अवधि"),
    PRED_DASHA_EFFECT_TEMPLATE("The %s period brings focus on %s. %s", "%s अवधिले %s मा ध्यान केन्द्रित गर्दछ। %s"),
    PRED_AD_EFFECT_SUB_TEMPLATE("The %s sub-period adds %s.", "%s उप-अवधिले %s थप्छ।"),
    PRED_CALC_PROGRESS("Current period influences are being calculated.", "वर्तमान अवधिको प्रभावहरू गणना गरिँदैछ।"),
    PRED_YEARS_REMAINING("%.1f years remaining", "%.1f वर्ष बाँकी"),
    PRED_CURRENT("Current", "वर्तमान"),
    
    // Life Area - Career
    PRED_CAREER_SHORT("Focus on current projects and networking. New opportunities may emerge through professional contacts.", "हालका परियोजनाहरू र नेटवर्किङमा ध्यान दिनुहोस्। व्यावसायिक सम्पर्कहरू मार्फत नयाँ अवसरहरू आउन सक्छन्।"),
    PRED_CAREER_MED("Significant professional advancement possible. Leadership opportunities may present themselves.", "महत्त्वपूर्ण व्यावसायिक प्रगति सम्भव छ। नेतृत्वका अवसरहरू आउन सक्छन्।"),
    PRED_CAREER_LONG("Establishment as authority in your field. Long-term career goals manifest.", "आफ्नो क्षेत्रमा अधिकारको रूपमा स्थापना। दीर्घकालीन करियर लक्ष्यहरू पूरा हुन्छन्।"),
    PRED_CAREER_KEY_1("Professional networking", "व्यावसायिक नेटवर्किङ"),
    PRED_CAREER_KEY_2("Skill enhancement", "सीप वृद्धि"),
    PRED_CAREER_KEY_3("Leadership development", "नेतृत्व विकास"),
    PRED_CAREER_ADVICE("Take calculated risks and invest in professional development. Network actively and stay open to new opportunities.", "जोखिम मोल्नुहोस् र व्यावसायिक विकासमा लगानी गर्नुहोस्। सक्रिय रूपमा नेटवर्क बनाउनुहोस् र नयाँ अवसरहरूका लागि खुला रहनुहोस्।"),
    
    // Life Area - Finance
    PRED_FINANCE_SHORT("Steady income flow with potential for incremental growth. Good time to review budget and savings.", "क्रमिक वृद्धिको सम्भावना सहित स्थिर आय प्रवाह। बजेट र बचत समीक्षा गर्ने राम्रो समय।"),
    PRED_FINANCE_MED("Potential for larger gains through investments or promotions. Financial security strengthens.", "लगानी वा पदोन्नति मार्फत ठूलो लाभको सम्भावना। आर्थिक सुरक्षा बलियो हुन्छ।"),
    PRED_FINANCE_LONG("Wealth accumulation and financial independence. Legacy building period.", "धन सञ्चय र आर्थिक स्वतन्त्रता। विरासत निर्माण अवधि।"),
    PRED_FINANCE_KEY_1("Strategic investments", "रणनीतिक लगानी"),
    PRED_FINANCE_KEY_2("Multiple income streams", "बहु आय स्रोतहरू"),
    PRED_FINANCE_KEY_3("Financial discipline", "आर्थिक अनुशासन"),
    PRED_FINANCE_ADVICE("Create diversified income sources and maintain financial discipline. Seek expert advice for major investments.", "विविध आय स्रोतहरू सिर्जना गर्नुहोस् र आर्थिक अनुशासन कायम राख्नुहोस्। ठूला लगानीका लागि विशेषज्ञको सल्लाह लिनुहोस्।"),

    // Life Area - Relationships
    PRED_REL_SHORT("Existing relationships deepen. Communication is key for resolving minor conflicts.", "अवस्थित सम्बन्धहरू गहिरो हुन्छन्। सानातिना विवादहरू समाधान गर्न सञ्चार मुख्य हो।"),
    PRED_REL_MED("Important relationship milestones. Marriage or commitment possibilities for singles.", "महत्त्वपूर्ण सम्बन्ध कोसेढुङ्गाहरू। अविवाहितहरूका लागि विवाह वा प्रतिबद्धताको सम्भावना।"),
    PRED_REL_LONG("Mature, stable partnerships. Family life flourishes with mutual support.", "परिपक्व, स्थिर साझेदारीहरू। आपसी सहयोगमा पारिवारिक जीवन फस्टाउँछ।"),
    PRED_REL_KEY_1("Communication quality", "सञ्चार गुणस्तर"),
    PRED_REL_KEY_2("Emotional maturity", "भावनात्मक परिपक्वता"),
    PRED_REL_KEY_3("Shared values", "साझा मानहरू"),
    PRED_REL_ADVICE("Practice active listening and express appreciation regularly. Invest quality time in nurturing bonds.", "सक्रिय सुन्ने अभ्यास गर्नुहोस् र नियमित रूपमा प्रशंसा व्यक्त गर्नुहोस्। सम्बन्धहरू पोषण गर्न गुणस्तरीय समय लगानी गर्नुहोस्।"),

    // Life Area - Health
    PRED_HEALTH_SHORT("Maintain regular exercise and balanced diet. Energy levels are stable.", "नियमित व्यायाम र सन्तुलित आहार कायम राख्नुहोस्। ऊर्जा स्तर स्थिर छ।"),
    PRED_HEALTH_MED("Vitality improves with consistent practices. Consider preventive health measures.", "लगातार अभ्यासले जाँगर सुधार हुन्छ। रोकथाम स्वास्थ्य उपायहरू विचार गर्नुहोस्।"),
    PRED_HEALTH_LONG("Strong vitality and longevity indicators. Healthy lifestyle becomes natural.", "बलियो जाँगर र दीर्घायु सूचकहरू। स्वस्थ जीवनशैली स्वाभाविक बन्छ।"),
    PRED_HEALTH_KEY_1("Regular exercise", "नियमित व्यायाम"),
    PRED_HEALTH_KEY_2("Balanced nutrition", "सन्तुलित पोषण"),
    PRED_HEALTH_KEY_3("Stress management", "तनाव व्यवस्थापन"),
    PRED_HEALTH_ADVICE("Establish sustainable wellness routines. Prevention is better than cure - regular check-ups recommended.", "दिगो कल्याण दिनचर्याहरू स्थापना गर्नुहोस्। उपचार भन्दा रोकथाम राम्रो हो - नियमित चेक-अप सिफारिस गरिन्छ।"),
    
    // Life Area - Education
    PRED_EDU_SHORT("Good period for learning and skill development. Short courses bring benefits.", "सिकाइ र सीप विकासका लागि राम्रो अवधि। छोटो पाठ्यक्रमहरूले फाइदा ल्याउँछन्।"),
    PRED_EDU_MED("Major educational achievements or certifications. Academic success likely.", "प्रमुख शैक्षिक उपलब्धिहरू वा प्रमाणपत्रहरू। शैक्षिक सफलताको सम्भावना।"),
    PRED_EDU_LONG("Mastery in chosen field. Potential to become teacher or expert.", "रोजिएको क्षेत्रमा निपुणता। शिक्षक वा विशेषज्ञ बन्ने सम्भावना।"),
    PRED_EDU_KEY_1("Consistent study", "निरन्तर अध्ययन"),
    PRED_EDU_KEY_2("Practical application", "व्यावहारिक प्रयोग"),
    PRED_EDU_KEY_3("Mentor guidance", "मेन्टर मार्गदर्शन"),
    PRED_EDU_ADVICE("Apply theoretical knowledge practically. Seek mentors and engage in continuous learning.", "सैद्धान्तिक ज्ञानलाई व्यावहारिक रूपमा लागू गर्नुहोस्। मेन्टरहरू खोज्नुहोस् र निरन्तर सिकाइमा संलग्न हुनुहोस्।"),
    
    // Life Area - Family
    PRED_FAMILY_SHORT("Harmonious domestic atmosphere. Small celebrations or gatherings bring joy.", "सामंजस्यपूर्ण घरेलु वातावरण। साना उत्सव वा जमघटले आनन्द ल्याउँछ।"),
    PRED_FAMILY_MED("Family expansions or property matters. Supportive period for family bonds.", "पारिवारिक विस्तार वा सम्पत्ति मामिलाहरू। पारिवारिक बन्धनका लागि सहयोगी अवधि।"),
    PRED_FAMILY_LONG("Strong family foundation. Ancestral blessings and property matters resolved.", "बलियो पारिवारिक आधार। पुर्ख्यौली आशीर्वाद र सम्पत्ति मामिलाहरू समाधान।"),
    PRED_FAMILY_KEY_1("Quality time", "गुणस्तरीय समय"),
    PRED_FAMILY_KEY_2("Mutual respect", "आपसी सम्मान"),
    PRED_FAMILY_KEY_3("Emotional support", "भावनात्मक सहयोग"),
    PRED_FAMILY_ADVICE("Balance personal and family time. Address conflicts with patience and understanding.", "व्यक्तिगत र पारिवारिक समय सन्तुलन गर्नुहोस्। धैर्य र समझदारीका साथ द्वन्द्वहरू सम्बोधन गर्नुहोस्।"),

    // Life Area - Spiritual
    PRED_SPIRIT_SHORT("Daily practices bring mental peace. Insights come through meditation.", "दैनिक अभ्यासले मानसिक शान्ति ल्याउँछ। ध्यान मार्फत अन्तरदृष्टि आउँछ।"),
    PRED_SPIRIT_MED("Deepening spiritual understanding. Connection with teachers or spiritual communities.", "आध्यात्मिक समझ गहिरो हुँदै। शिक्षक वा आध्यात्मिक समुदायहरूसँग सम्बन्ध।"),
    PRED_SPIRIT_LONG("Significant spiritual evolution. Inner peace and wisdom established.", "महत्त्वपूर्ण आध्यात्मिक विकास। आन्तरिक शान्ति र ज्ञान स्थापित।"),
    PRED_SPIRIT_KEY_1("Daily practice", "दैनिक अभ्यास"),
    PRED_SPIRIT_KEY_2("Self-reflection", "आत्म-चिन्तन"),
    PRED_SPIRIT_KEY_3("Service to others", "अरूको सेवा"),
    PRED_SPIRIT_ADVICE("Maintain daily spiritual practices. Connect with like-minded seekers and serve others.", "दैनिक आध्यात्मिक अभ्यासहरू कायम राख्नुहोस्। समान विचारधारा भएका साधकहरूसँग जोड्नुहोस् र अरूको सेवा गर्नुहोस्।"),
    
    // Transits
    PRED_TRANSIT_SUN("Illuminating life purpose", "जीवन उद्देश्य प्रष्ट पार्दै"),
    PRED_TRANSIT_MOON("Emotional fluctuations", "भावनात्मक उतार-चढाव"),
    PRED_TRANSIT_MARS("Increased energy and drive", "बढेको ऊर्जा र उत्साह"),
    PRED_TRANSIT_MERCURY("Mental activity and communication", "मानसिक गतिविधि र सञ्चार"),
    PRED_TRANSIT_JUPITER("Growth and opportunities", "वृद्धि र अवसरहरू"),
    PRED_TRANSIT_VENUS("Harmony and relationships", "सद्भाव र सम्बन्धहरू"),
    PRED_TRANSIT_SATURN("Discipline and restrictions", "अनुशासन र प्रतिबन्धहरू"),
    PRED_TRANSIT_RAHU("Unexpected developments", "अप्रत्याशित घटनाक्रम"),
    PRED_TRANSIT_KETU("Spiritual awakening", "आध्यात्मिक जागरण"),
    PRED_TRANSIT_DEFAULT("Planetary influence active", "ग्रह प्रभाव सक्रिय"),
    
    // Remedial
    PRED_REMEDY_MANTRA("Perform %s mantra recitation during morning hours", "बिहानको समयमा %s मन्त्र जप गर्नुहोस्"),
    PRED_REMEDY_DONATE("Donate items associated with %s on appropriate days", "उपयुक्त दिनहरूमा %s सँग सम्बन्धित वस्तुहरू दान गर्नुहोस्"),
    PRED_REMEDY_GEM("Wear gemstone related to %s after proper consultation", "उचित परामर्श पछि %s सँग सम्बन्धित रत्न लगाउनुहोस्"),
    PRED_REMEDY_YOGA("Practice meditation and yoga to balance planetary energies", "ग्रह उर्जाहरू सन्तुलन गर्न ध्यान र योग अभ्यास गर्नुहोस्"),
    PRED_REMEDY_WORSHIP("Maintain regular worship and spiritual practices", "नियमित पूजा र आध्यात्मिक अभ्यासहरू कायम राख्नुहोस्"),

    // Active Yogas
    PRED_YOGA_DHANA_NAME("Dhana Yoga", "धन योग"),
    PRED_YOGA_DHANA_DESC("Wealth-creating planetary combination", "धन-सिर्जना गर्ने ग्रह संयोजन"),
    PRED_YOGA_DHANA_EFFECT("Favorable for financial growth and material prosperity", "आर्थिक वृद्धि र भौतिक समृद्धिको लागि अनुकूल"),
    PRED_YOGA_RAJA_NAME("Raja Yoga", "राज योग"),
    PRED_YOGA_RAJA_DESC("Royal combination indicating success", "सफलता संकेत गर्ने शाही संयोजन"),
    PRED_YOGA_RAJA_EFFECT("Support for leadership and achievement", "नेतृत्व र उपलब्धिका लागि सहयोग"),

    // Challenges
    PRED_CHALLENGE_CAREER_TRANS_NAME("Career Transitions", "करियर संक्रमण"),
    PRED_CHALLENGE_CAREER_TRANS_DESC("Period of professional reassessment and potential changes", "व्यावसायिक पुनर्मूल्यांकन र सम्भावित परिवर्तनहरूको अवधि"),
    PRED_CHALLENGE_CAREER_TRANS_MIT("Focus on skill development and networking", "सीप विकास र नेटवर्किङमा ध्यान दिनुहोस्"),

    // Opportunities
    PRED_OPP_FINANCE_NAME("Financial Growth", "आर्थिक वृद्धि"),
    PRED_OPP_FINANCE_DESC("Favorable period for investments and wealth accumulation", "लगानी र धन सञ्चयका लागि शुभ अवधि"),
    PRED_OPP_TIMING_6M("Next 6 months", "आगामी ६ महिना"),
    PRED_OPP_FINANCE_LEV("Explore new income sources and strategic investments", "नयाँ आय स्रोतहरू र रणनीतिक लगानीहरू अन्वेषण गर्नुहोस्"),
    
    PRED_OPP_PERSONAL_NAME("Personal Development", "व्यक्तिगत विकास"),
    PRED_OPP_PERSONAL_DESC("Excellent time for learning and self-improvement", "सिकाइ र आत्म-सुधारका लागि उत्कृष्ट समय"),
    PRED_OPP_PERSONAL_LEV("Enroll in courses, read extensively, seek mentorship", "पाठ्यक्रमहरूमा भर्ना हुनुहोस्, व्यापक रूपमा पढ्नुहोस्, मार्गदर्शन लिनुहोस्"),

    // Timing
    PRED_TIMING_JUPITER_REASON("Jupiter transit supports growth", "बृहस्पति गोचरले वृद्धिलाई सहयोग गर्छ"),
    PRED_TIMING_JUPITER_BEST_1("New ventures", "नयाँ उद्यमहरू"),
    PRED_TIMING_JUPITER_BEST_2("Important decisions", "महत्त्वपूर्ण निर्णयहरू"),
    PRED_TIMING_JUPITER_BEST_3("Relationships", "सम्बन्धहरू"),
    
    PRED_TIMING_SATURN_REASON("Saturn transit requires caution", "शनि गोचरले सावधानीको माग गर्छ"),
    PRED_TIMING_SATURN_AVOID_1("Major investments", "ठूला लगानीहरू"),
    PRED_TIMING_SATURN_AVOID_2("Hasty decisions", "हतारका निर्णयहरू"),
    PRED_TIMING_SATURN_AVOID_3("Conflicts", "द्वन्द्वहरू"),
    
    PRED_TIMING_JUPITER_ASPECT_EVENT("Favorable Jupiter Aspect", "शुभ बृहस्पति दृष्टि"),
    PRED_TIMING_JUPITER_ASPECT_SIG("Excellent for new beginnings", "नयाँ सुरुवातका लागि उत्कृष्ट"),

    // UI Labels
    PRED_LABEL_KEY_FACTORS("Key Factors", "मुख्य कारकहरू"),
    PRED_LABEL_ADVICE("Advice", "सल्लाह"),
    PRED_REMEDY_FOR_PERIOD("For your current %s period:", "तपाईंको वर्तमान %s अवधिको लागि:"),

    // ============================================
    // HOUSE DETAILS (For ChartDialogs)
    // ============================================
    HOUSE_DETAIL_NAME_1("Lagna Bhava (Ascendant)", "लग्न भाव (उदय)"),
    HOUSE_DETAIL_TYPE_1("Kendra (Angular) & Trikona (Trine)", "केन्द्र (कोणीय) र त्रिकोण"),
    HOUSE_DETAIL_SIG_1("Physical body, Personality, Self-identity, Head and brain, General health, Beginning of life, Appearance", "भौतिक शरीर, व्यक्तित्व, आत्म-पहिचान, टाउको र मस्तिष्क, सामान्य स्वास्थ्य, जीवनको सुरुवात, रूप"),
    HOUSE_DETAIL_INTERP_1("The First House is the most important house, representing you as a whole. It shows your physical constitution, personality traits, and how you present yourself to the world. A strong 1st house gives good health, confidence, and success in self-started ventures.", "पहिलो भाव सबैभन्दा महत्त्वपूर्ण भाव हो, जसले समग्र रूपमा तपाईंको प्रतिनिधित्व गर्दछ। यसले तपाईंको शारीरिक संरचना, व्यक्तित्वका लक्षणहरू, र तपाईंले संसारमा आफूलाई कसरी प्रस्तुत गर्नुहुन्छ भन्ने देखाउँछ। बलियो पहिलो भावले राम्रो स्वास्थ्य, आत्मविश्वास र स्व-सुरु गरिएका उद्यमहरूमा सफलता दिन्छ।"),

    HOUSE_DETAIL_NAME_2("Dhana Bhava (Wealth)", "धन भाव (सम्पत्ति)"),
    HOUSE_DETAIL_TYPE_2("Maraka (Death-inflicting) & Panapara", "मारक (मृत्युकारक) र पणफर"),
    HOUSE_DETAIL_SIG_2("Wealth & Possessions, Family, Speech, Right eye, Face, Food intake, Early childhood", "धन र सम्पत्ति, परिवार, वाणी, दाहिने आँखा, अनुहार, भोजन, प्रारम्भिक बाल्यकाल"),
    HOUSE_DETAIL_INTERP_2("The Second House governs accumulated wealth, family values, and speech. It shows how you earn and save money, your relationship with family, and your communication style. A strong 2nd house indicates financial stability and sweet speech.", "दोस्रो भावले सञ्चित धन, पारिवारिक मूल्य मान्यता र वाणीलाई नियन्त्रण गर्दछ। यसले तपाईंले कसरी पैसा कमाउनुहुन्छ र बचत गर्नुहुन्छ, परिवारसँगको तपाईंको सम्बन्ध, र तपाईंको सञ्चार शैली देखाउँछ। बलियो दोस्रो भावले आर्थिक स्थिरता र मधुर वाणी संकेत गर्दछ।"),

    HOUSE_DETAIL_NAME_3("Sahaja Bhava (Siblings)", "सहज भाव (भाइबहिनी)"),
    HOUSE_DETAIL_TYPE_3("Upachaya (Growth) & Apoklima", "उपचय (वृद्धि) र आपोक्लिम"),
    HOUSE_DETAIL_SIG_3("Siblings, Courage, Short journeys, Communication, Arms and shoulders, Neighbors, Hobbies", "भाइबहिनी, साहस, छोटो यात्रा, सञ्चार, हात र काँध, छिमेकी, शौक"),
    HOUSE_DETAIL_INTERP_3("The Third House represents courage, initiative, and self-effort. It governs siblings (especially younger), short travels, and all forms of communication. A strong 3rd house gives courage, good relationships with siblings, and success through personal effort.", "तेस्रो भावले साहस, पहल र आत्म-प्रयासको प्रतिनिधित्व गर्दछ। यसले भाइबहिनी (विशेष गरी साना), छोटो यात्रा, र सञ्चारका सबै रूपहरूलाई नियन्त्रण गर्दछ। बलियो तेस्रो भावले साहस, भाइबहिनीसँग राम्रो सम्बन्ध, र व्यक्तिगत प्रयास मार्फत सफलता दिन्छ।"),

    HOUSE_DETAIL_NAME_4("Sukha Bhava (Happiness)", "सुख भाव (खुसी)"),
    HOUSE_DETAIL_TYPE_4("Kendra (Angular)", "केन्द्र (कोणीय)"),
    HOUSE_DETAIL_SIG_4("Mother, Home & Property, Vehicles, Education, Chest & Heart, Inner peace, Emotional foundation", "आमा, घर र सम्पत्ति, सवारी साधन, शिक्षा, छाती र हृदय, आन्तरिक शान्ति, भावनात्मक आधार"),
    HOUSE_DETAIL_INTERP_4("The Fourth House is the foundation of your life. It represents your mother, home environment, and emotional security. It also governs formal education and landed property. A strong 4th house gives domestic happiness, property ownership, and mental peace.", "चौथो भाव तपाईंको जीवनको आधार हो। यसले तपाईंको आमा, घरको वातावरण, र भावनात्मक सुरक्षाको प्रतिनिधित्व गर्दछ। यसले औपचारिक शिक्षा र जग्गा जमिनलाई पनि नियन्त्रण गर्दछ। बलियो चौथो भावले पारिवारिक खुसी, सम्पत्ति स्वामित्व, र मानसिक शान्ति दिन्छ।"),

    HOUSE_DETAIL_NAME_5("Putra Bhava (Children)", "पुत्र भाव (सन्तान)"),
    HOUSE_DETAIL_TYPE_5("Trikona (Trine) & Panapara", "त्रिकोण र पणफर"),
    HOUSE_DETAIL_SIG_5("Children, Intelligence, Creativity, Romance, Past life merit, Speculation, Higher education", "सन्तान, बुद्धि, सिर्जनशीलता, रोमान्स, पूर्वजन्मको पुण्य, अनुमान, उच्च शिक्षा"),
    HOUSE_DETAIL_INTERP_5("The Fifth House is the house of creativity and Purva Punya (past life merits). It governs children, intelligence, romance, and speculative gains. A strong 5th house gives intelligent children, creative talents, and success in speculation.", "पाँचौं भाव सिर्जनशीलता र पूर्व पुण्य (पूर्वजन्मको गुणहरू) को भाव हो। यसले सन्तान, बुद्धि, रोमान्स, र अनुमानित लाभहरूलाई नियन्त्रण गर्दछ। बलियो पाँचौं भावले बुद्धिमान सन्तान, सिर्जनात्मक प्रतिभा, र अनुमानमा सफलता दिन्छ।"),

    HOUSE_DETAIL_NAME_6("Ripu Bhava (Enemies)", "रिपु भाव (शत्रु)"),
    HOUSE_DETAIL_TYPE_6("Dusthana (Malefic) & Upachaya", "दुःस्थान (पापी) र उपचय"),
    HOUSE_DETAIL_SIG_6("Enemies, Diseases, Debts, Service, Competition, Daily work, Maternal uncle", "शत्रु, रोग, ऋण, सेवा, प्रतिस्पर्धा, दैनिक काम, मामा"),
    HOUSE_DETAIL_INTERP_6("The Sixth House governs obstacles, health issues, and service. While considered malefic, it also shows the ability to overcome challenges. A well-placed 6th house gives victory over enemies, good health practices, and success in competitive fields.", "छैटौं भावले अवरोध, स्वास्थ्य समस्या, र सेवालाई नियन्त्रण गर्दछ। यसलाई पापी मानिए पनि, यसले चुनौतीहरू पार गर्ने क्षमता पनि देखाउँछ। राम्रोसँग स्थित छैटौं भावले शत्रुहरूमाथि विजय, राम्रो स्वास्थ्य अभ्यास, र प्रतिस्पर्धी क्षेत्रहरूमा सफलता दिन्छ।"),

    HOUSE_DETAIL_NAME_7("Kalatra Bhava (Spouse)", "कलत्र भाव (जीवनसाथी)"),
    HOUSE_DETAIL_TYPE_7("Kendra (Angular) & Maraka", "केन्द्र (कोणीय) र मारक"),
    HOUSE_DETAIL_SIG_7("Marriage, Spouse, Business partnerships, Foreign travel, Public dealing, Lower abdomen, Sexual organs", "विवाह, जीवनसाथी, व्यापार साझेदारी, विदेश यात्रा, सार्वजनिक व्यवहार, तल्लो पेट, यौन अंग"),
    HOUSE_DETAIL_INTERP_7("The Seventh House is the house of partnerships and marriage. It shows your spouse's nature and quality of marriage. It also governs business partnerships and public dealings. A strong 7th house gives a good spouse and success in partnerships.", "सातौं भाव साझेदारी र विवाहको भाव हो। यसले तपाईंको जीवनसाथीको स्वभाव र विवाहको गुणस्तर देखाउँछ। यसले व्यापार साझेदारी र सार्वजनिक व्यवहारलाई पनि नियन्त्रण गर्दछ। बलियो सातौं भावले राम्रो जीवनसाथी र साझेदारीमा सफलता दिन्छ।"),

    HOUSE_DETAIL_NAME_8("Ayur Bhava (Longevity)", "आयु भाव (दीर्घायु)"),
    HOUSE_DETAIL_TYPE_8("Dusthana (Malefic) & Panapara", "दुःस्थान (पापी) र पणफर"),
    HOUSE_DETAIL_SIG_8("Longevity, Transformation, Occult, Inheritance, Hidden matters, Chronic diseases, In-laws' wealth", "दीर्घायु, रूपान्तरण, तन्त्रमन्त्र, पैतृक सम्पत्ति, लुकेका कुरा, दीर्घकालीन रोग, ससुरालीको धन"),
    HOUSE_DETAIL_INTERP_8("The Eighth House governs transformation, death, and rebirth (metaphorical). It shows hidden matters, inheritance, and occult interests. While considered difficult, a well-placed 8th house gives longevity, research abilities, and unexpected gains.", "आठौं भावले रूपान्तरण, मृत्यु, र पुनर्जन्म (लाक्षणिक) लाई नियन्त्रण गर्दछ। यसले लुकेका कुरा, पैतृक सम्पत्ति, र तान्त्रिक रुचिहरू देखाउँछ। यसलाई कठिन मानिए पनि, राम्रोसँग स्थित आठौं भावले दीर्घायु, अनुसन्धान क्षमता, र अप्रत्याशित लाभ दिन्छ।"),

    HOUSE_DETAIL_NAME_9("Dharma Bhava (Fortune)", "धर्म भाव (भाग्य)"),
    HOUSE_DETAIL_TYPE_9("Trikona (Trine) & Apoklima", "त्रिकोण र आपोक्लिम"),
    HOUSE_DETAIL_SIG_9("Fortune & Luck, Father, Higher learning, Long journeys, Religion & Philosophy, Guru/Teacher, Righteousness", "भाग्य र किस्मत, पिता, उच्च शिक्षा, लामो यात्रा, धर्म र दर्शन, गुरु/शिक्षक, धार्मिकता"),
    HOUSE_DETAIL_INTERP_9("The Ninth House is the most auspicious house of fortune and dharma. It represents your father, teachers, and higher wisdom. A strong 9th house gives good fortune, philosophical inclinations, and blessings from elders and teachers.", "नवौं भाव भाग्य र धर्मको सबैभन्दा शुभ भाव हो। यसले तपाईंको पिता, शिक्षकहरू, र उच्च ज्ञानको प्रतिनिधित्व गर्दछ। बलियो नवौं भावले राम्रो भाग्य, दार्शनिक झुकाव, र ठूलाहरू तथा शिक्षकहरूबाट आशीर्वाद दिन्छ।"),

    HOUSE_DETAIL_NAME_10("Karma Bhava (Career)", "कर्म भाव (करियर)"),
    HOUSE_DETAIL_TYPE_10("Kendra (Angular) & Upachaya", "केन्द्र (कोणीय) र उपचय"),
    HOUSE_DETAIL_SIG_10("Career, Profession, Status & Fame, Authority, Government, Father, Knees", "करियर, पेशा, पद र प्रतिष्ठा, अधिकार, सरकार, पिता, घुँडा"),
    HOUSE_DETAIL_INTERP_10("The Tenth House is the house of career and public image. It shows your profession, status in society, and relationship with authority. A strong 10th house gives career success, fame, and high position in society.", "दशौं भाव करियर र सार्वजनिक छविको भाव हो। यसले तपाईंको पेशा, समाजमा स्थिति, र अधिकारसँगको सम्बन्ध देखाउँछ। बलियो दशौं भावले करियर सफलता, प्रसिद्धि, र समाजमा उच्च पद दिन्छ।"),

    HOUSE_DETAIL_NAME_11("Labha Bhava (Gains)", "लाभ भाव (नाफा)"),
    HOUSE_DETAIL_TYPE_11("Upachaya (Growth) & Panapara", "उपचय (वृद्धि) र पणफर"),
    HOUSE_DETAIL_SIG_11("Income & Gains, Elder siblings, Friends, Hopes & Wishes, Social network, Left ear, Ankles", "आय र नाफा, ठूला भाइबहिनी, साथीहरू, आशा र इच्छाहरू, सामाजिक सञ्जाल, बायाँ कान, कुर्कुच्चा"),
    HOUSE_DETAIL_INTERP_11("The Eleventh House is the house of gains and fulfillment of desires. It governs income, elder siblings, and friendships. A strong 11th house gives multiple sources of income, supportive friends, and fulfillment of hopes.", "एघारौं भाव नाफा र इच्छाहरूकोपूर्तिको भाव हो। यसले आय, ठूला भाइबहिनी, र मित्रतालाई नियन्त्रण गर्दछ। बलियो एघारौं भावले आयका धेरै स्रोतहरू, सहयोगी साथीहरू, र आशाहरूको पूर्ति दिन्छ।"),

    HOUSE_DETAIL_NAME_12("Vyaya Bhava (Loss)", "व्यय भाव (हानि)"),
    HOUSE_DETAIL_TYPE_12("Dusthana (Malefic) & Apoklima", "दुःस्थान (पापी) र आपोक्लिम"),
    HOUSE_DETAIL_SIG_12("Losses & Expenses, Liberation (Moksha), Foreign lands, Isolation, Feet, Sleep, Subconscious", "हानि र खर्च, मुक्ति (मोक्ष), विदेशी भूमि, एकान्त, खुट्टा, निद्रा, अवचेतन"),
    HOUSE_DETAIL_INTERP_12("The Twelfth House governs losses, expenses, and liberation. While it shows material losses, it also represents spiritual gains and final liberation. A strong 12th house gives spiritual inclinations, success abroad, and peaceful sleep.", "बाह्रौं भावले हानि, खर्च, र मुक्तिलाई नियन्त्रण गर्दछ। यसले भौतिक हानि देखाए पनि, यसले आध्यात्मिक लाभ र अन्तिम मुक्तिलाई पनि प्रतिनिधित्व गर्दछ। बलियो बाह्रौं भावले आध्यात्मिक झुकाव, विदेशमा सफलता, र शान्त निद्रा दिन्छ।"),

    // ============================================
    // HOUSE PLACEMENT INTERPRETATIONS (For ChartDialogs)
    // ============================================
    PLACEMENT_INTERP_SUN_1("Strong personality with natural leadership abilities. You have a prominent presence and strong willpower. May indicate good health and vitality.", "प्राकृतिक नेतृत्व क्षमताको साथ बलियो व्यक्तित्व। तपाईंसँग प्रमुख उपस्थिति र बलियो इच्छाशक्ति छ। राम्रो स्वास्थ्य र जीवनशक्तिको संकेत गर्न सक्छ।"),
    PLACEMENT_INTERP_SUN_10("Excellent position for career success and recognition. Natural authority in professional life. Government positions or leadership roles favored.", "करियर सफलता र मान्यताको लागि उत्कृष्ट स्थिति। व्यावसायिक जीवनमा प्राकृतिक अधिकार। सरकारी पद वा नेतृत्व भूमिकाहरू अनुकूल छन्।"),
    PLACEMENT_INTERP_MOON_4("Strong emotional foundation and attachment to home. Good relationship with mother. Domestic happiness and property gains likely.", "बलियो भावनात्मक आधार र घरप्रतिको लगाव। आमासँग राम्रो सम्बन्ध। पारिवारिक खुसी र सम्पत्ति लाभको सम्भावना छ।"),
    PLACEMENT_INTERP_MOON_1("Emotional and intuitive personality. Strong connection to feelings. Popular with the public and adaptable nature.", "भावनात्मक र अन्तर्ज्ञानी व्यक्तित्व। भावनाहरूसँग बलियो सम्बन्ध। जनतामा लोकप्रिय र अनुकूलनशील स्वभाव।"),
    PLACEMENT_INTERP_MARS_10("Dynamic career with technical or engineering success. Leadership in competitive fields. Achievement through bold actions.", "प्राविधिक वा इन्जिनियरिङ सफलताको साथ गतिशील करियर। प्रतिस्पर्धी क्षेत्रहरूमा नेतृत्व। साहसी कार्यहरू मार्फत उपलब्धि।"),
    PLACEMENT_INTERP_MARS_1("Energetic and courageous personality. Athletic abilities. Can be aggressive or impulsive. Strong drive for success.", "ऊर्जावान र साहसी व्यक्तित्व। एथलेटिक क्षमताहरू। आक्रामक वा आवेगपूर्ण हुन सक्छ। सफलताको लागि बलियो प्रेरणा।"),
    PLACEMENT_INTERP_MERCURY_1("Intelligent and communicative personality. Good business sense. Quick thinking and versatile nature.", "बुद्धिमान र मिलनसार व्यक्तित्व। राम्रो व्यापारिक ज्ञान। छिटो सोच्ने र बहुमुखी स्वभाव।"),
    PLACEMENT_INTERP_MERCURY_5("Creative intelligence and good with children. Success in speculation and education. Artistic communication skills.", "सिर्जनात्मक बुद्धि र बालबालिकाहरूसँग राम्रो। अनुमान र शिक्षामा सफलता। कलात्मक सञ्चार सीपहरू।"),
    PLACEMENT_INTERP_JUPITER_1("Wise and optimistic personality. Natural teacher or advisor. Good fortune and ethical nature. Respected by others.", "बुद्धिमान र आशावादी व्यक्तित्व। प्राकृतिक शिक्षक वा सल्लाहकार। राम्रो भाग्य र नैतिक स्वभाव। अरूद्वारा सम्मानित।"),
    PLACEMENT_INTERP_JUPITER_9("Excellent position for spiritual growth and higher learning. Good fortune with father and long journeys. Success in teaching or law.", "आध्यात्मिक वृद्धि र उच्च शिक्षाको लागि उत्कृष्ट स्थिति। बुबा र लामो यात्रासँग राम्रो भाग्य। शिक्षण वा कानूनमा सफलता।"),
    PLACEMENT_INTERP_VENUS_7("Beautiful spouse and harmonious marriage. Success in partnerships and business. Diplomatic abilities.", "सुन्दर जीवनसाथी र सामंजस्यपूर्ण विवाह। साझेदारी र व्यापारमा सफलता। कूटनीतिक क्षमताहरू।"),
    PLACEMENT_INTERP_VENUS_4("Luxurious home and vehicles. Good relationship with mother. Domestic happiness and artistic home environment.", "विलासी घर र सवारी साधनहरू। आमासँग राम्रो सम्बन्ध। पारिवारिक खुसी र कलात्मक घरको वातावरण।"),
    PLACEMENT_INTERP_SATURN_10("Slow but steady rise in career. Success through hard work and persistence. Authority gained through discipline.", "करियरमा ढिलो तर स्थिर वृद्धि। कडा परिश्रम र दृढता मार्फत सफलता। अनुशासन मार्फत प्राप्त अधिकार।"),
    PLACEMENT_INTERP_SATURN_7("Delayed marriage but stable. Serious approach to partnerships. May marry someone older or more mature.", "विवाहमा ढिलाइ तर स्थिर। साझेदारीप्रति गम्भीर दृष्टिकोण। आफूभन्दा जेठो वा बढी परिपक्व व्यक्तिसँग विवाह गर्न सक्छ।"),
    PLACEMENT_INTERP_RAHU_10("Unconventional career path. Success in foreign lands or technology. Ambitious and worldly.", "अपरम्परागत करियर मार्ग। विदेशी भूमि वा प्रविधिमा सफलता। महत्त्वाकांक्षी र सांसारिक।"),
    PLACEMENT_INTERP_KETU_12("Strong spiritual inclinations. Interest in meditation and liberation. May spend time in foreign lands or ashrams.", "बलियो आध्यात्मिक झुकाव। ध्यान र मुक्तिमा रुचि। विदेशी भूमि वा आश्रमहरूमा समय बिताउन सक्छ।"),
    PLACEMENT_INTERP_DEFAULT("The %s in the %dth house influences the areas of %s. Results depend on the sign placement, aspects, and overall chart strength.", "%dऔं भावमा रहेको %s ले %s का क्षेत्रहरूलाई प्रभाव पार्छ। परिणामहरू राशि स्थिति, दृष्टि, र समग्र कुण्डलीको बलमा निर्भर हुन्छन्।"),

    // ============================================
    // NAKSHATRA DETAILS (For ChartDialogs)
    // ============================================
    NAK_ASHWINI_SYMBOL("Horse's Head", "घोडाको टाउको"),
    NAK_ASHWINI_CHARS("Ashwini natives are quick, energetic, and pioneering. They have natural healing abilities and are often the first to try new things. Speed and initiative are their hallmarks.", "अश्विनी जातकहरू छिटो, ऊर्जावान र अग्रगामी हुन्छन्। तिनीहरूसँग प्राकृतिक उपचार क्षमताहरू हुन्छन् र प्रायः नयाँ कुराहरू प्रयास गर्ने पहिलो हुन्छन्। गति र पहल तिनीहरूको चिनारी हो।"),
    NAK_ASHWINI_CAREERS("Medical field, Emergency services, Sports, Transportation, Veterinary science", "चिकित्सा क्षेत्र, आपतकालीन सेवा, खेलकुद, यातायात, पशु चिकित्सा विज्ञान"),

    NAK_BHARANI_SYMBOL("Yoni (Female reproductive organ)", "योनि (स्त्री प्रजनन अंग)"),
    NAK_BHARANI_CHARS("Bharani natives are creative, responsible, and can bear heavy burdens. They understand life's transformative nature and often work with matters of birth, death, and transformation.", "भरणी जातकहरू सिर्जनशील, जिम्मेवार हुन्छन् र ठूलो बोझ उठाउन सक्छन्। तिनीहरू जीवनको परिवर्तनकारी प्रकृति बुझ्छन् र प्रायः जन्म, मृत्यु, र रूपान्तरणका विषयहरूसँग काम गर्छन्।"),
    NAK_BHARANI_CAREERS("Midwifery, Funeral services, Entertainment, Creative arts, Psychology", "प्रसूति सेवा, अन्त्येष्टि सेवा, मनोरञ्जन, सिर्जनात्मक कला, मनोविज्ञान"),

    NAK_ROHINI_SYMBOL("Ox Cart / Chariot", "गोरु गाडा / रथ"),
    NAK_ROHINI_CHARS("Rohini natives are attractive, artistic, and materialistic in a positive way. They appreciate beauty and luxury. Strong creative and productive abilities.", "रोहिणी जातकहरू आकर्षक, कलात्मक र सकारात्मक रूपमा भौतिकवादी हुन्छन्। तिनीहरू सौन्दर्य र विलासिताको कदर गर्छन्। बलियो सिर्जनात्मक र उत्पादक क्षमताहरू।"),
    NAK_ROHINI_CAREERS("Fashion, Beauty industry, Agriculture, Music, Hospitality", "फेसन, सौन्दर्य उद्योग, कृषि, संगीत, आतिथ्य"),

    NAK_DEFAULT_CHARS("%s is ruled by %s. Natives are influenced by the deity %s.", "%s %s द्वारा शासित छ। जातकहरू देवता %s द्वारा प्रभावित हुन्छन्।"),
    NAK_DEFAULT_CAREERS("Various fields depending on overall chart analysis", "समग्र कुण्डली विश्लेषणमा निर्भर गर्दै विभिन्न क्षेत्रहरू"),

    PADA_DESC_TEMPLATE("Pada %d falls in %s Navamsa, ruled by %s. This pada emphasizes the %s element qualities combined with the main nakshatra characteristics.", "पद %d %s नवांशमा पर्छ, जुन %s द्वारा शासित छ। यो पदले मुख्य नक्षत्र विशेषताहरूसँग संयुक्त %s तत्व गुणहरूलाई जोड दिन्छ।"),

    // ============================================
    // PREDICTIONS SCREEN (Additional)
    // ============================================
    PRED_STRENGTH_DETERMINATION("Strong determination and resilience in your character", "तपाईंको चरित्रमा बलियो दृढ संकल्प र लचिलोपन"),
    PRED_STRENGTH_SPIRITUAL("Natural ability to connect with spiritual wisdom", "आध्यात्मिक ज्ञानसँग जोडिने प्राकृतिक क्षमता"),
    PRED_STRENGTH_EMOTIONAL("Capacity for deep emotional understanding", "गहिरो भावनात्मक समझको क्षमता"),
    PRED_STRENGTH_PRACTICAL("Practical approach to life's challenges", "जीवनका चुनौतीहरूप्रति व्यावहारिक दृष्टिकोण"),
    
    PRED_LIFE_THEME_TEMPLATE("Path of %s", "%s को मार्ग"),
    PRED_MAHADASHA_LABEL("%s Mahadasha", "%s महादशा")
}
