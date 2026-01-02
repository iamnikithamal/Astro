package com.astro.storm.data.localization

enum class StringKeyTransit(override val en: String, override val ne: String) : StringKeyInterface {
    
    // ============================================
    // TRANSIT ANALYSIS REPORT HEADER
    // ============================================
    REPORT_TITLE_LINE1("TRANSIT ANALYSIS REPORT", "गोचर विश्लेषण रिपोर्ट"),
    REPORT_DATE_LABEL("Transit Date/Time: %s", "गोचर मिति/समय: %s"),
    REPORT_SECTION_POSITIONS("CURRENT PLANETARY POSITIONS", "हालको ग्रह स्थितिहरू"),
    REPORT_SECTION_GOCHARA("GOCHARA ANALYSIS (Transit from Moon)", "गोचर विश्लेषण (चन्द्रमाबाट गोचर)"),
    REPORT_SECTION_ASPECTS("TRANSIT ASPECTS TO NATAL PLANETS", "जन्म ग्रहहरूमा गोचर दृष्टि"),
    REPORT_SECTION_ASHTAKAVARGA("ASHTAKAVARGA TRANSIT SCORES", "अष्टकवर्ग गोचर स्कोरहरू"),
    REPORT_SECTION_ASSESSMENT("OVERALL ASSESSMENT", "समग्र मूल्यांकन"),
    
    REPORT_NO_ASPECTS("No significant aspects currently active.", "हाल कुनै महत्त्वपूर्ण दृष्टि सक्रिय छैन।"),
    REPORT_VEDHA_FROM("             └─ Vedha from %s", "             └─ %s बाट वेध"),
    REPORT_ASPECT_LINE("Transit %1\$s %2\$s Natal %3\$s", "गोचर %1\$s %2\$s जन्म %3\$s"),
    REPORT_ASPECT_DETAILS("  Orb: %1\$s° (%2\$s) | Strength: %3\$s%%", "  अंश: %1\$s° (%2\$s) | बल: %3\$s%%"),
    REPORT_BAV_SAV("%1\$s: BAV=%2\$d, SAV=%3\$d - %4\$s", "%1\$s: भिन्ना=%2\$d, सर्वा=%3\$d - %4\$s"),
    REPORT_PERIOD_QUALITY("Period Quality: %s", "अवधि गुणस्तर: %s"),
    REPORT_SCORE("Score: %s/100", "स्कोर: %s/100"),
    REPORT_SUMMARY("Summary: %s", "सारांश: %s"),
    REPORT_FOCUS_AREAS("Key Areas of Focus:", "ध्यान केन्द्रित गर्ने मुख्य क्षेत्रहरू:"),
    
    // ============================================
    // GOCHARA INTERPRETATIONS
    // ============================================
    GOCHARA_MATTERS_1("self, health, personality", "स्वयं, स्वास्थ्य, व्यक्तित्व"),
    GOCHARA_MATTERS_2("wealth, family, speech", "धन, परिवार, वाणी"),
    GOCHARA_MATTERS_3("courage, siblings, short journeys", "साहस, भाइबहिनी, छोटो यात्रा"),
    GOCHARA_MATTERS_4("home, mother, mental peace", "घर, आमा, मानसिक शान्ति"),
    GOCHARA_MATTERS_5("children, creativity, romance", "सन्तान, सिर्जनशीलता, रोमान्स"),
    GOCHARA_MATTERS_6("enemies, health issues, debts", "शत्रु, स्वास्थ्य समस्या, ऋण"),
    GOCHARA_MATTERS_7("marriage, partnerships, business", "विवाह, साझेदारी, व्यापार"),
    GOCHARA_MATTERS_8("obstacles, longevity, occult", "बाधाहरू, दीर्घायु, तन्त्रमन्त्र"),
    GOCHARA_MATTERS_9("fortune, father, religion", "भाग्य, पिता, धर्म"),
    GOCHARA_MATTERS_10("career, status, government", "करियर, प्रतिष्ठा, सरकार"),
    GOCHARA_MATTERS_11("gains, friends, elder siblings", "लाभ, साथीहरू, ठूला भाइबहिनी"),
    GOCHARA_MATTERS_12("expenses, spirituality, foreign", "खर्च, आध्यात्मिकता, विदेश"),
    GOCHARA_MATTERS_GENERAL("general matters", "सामान्य मामिलाहरू"),
    
    GOCHARA_VEDHA_NOTE(" Effects may be diminished due to Vedha.", " वेधको कारण प्रभावहरू कम हुन सक्छ।"),
    
    GOCHARA_EFFECT_EXCELLENT("%1\$s transit in %2\$dth house brings excellent results for %3\$s.%4\$s", "%2\$dऔं भावमा %1\$s गोचरले %3\$s को लागि उत्कृष्ट परिणाम ल्याउँछ।%4\$s"),
    GOCHARA_EFFECT_GOOD("%1\$s transit in %2\$dth house supports %3\$s.%4\$s", "%2\$dऔं भावमा %1\$s गोचरले %3\$s लाई सहयोग गर्छ।%4\$s"),
    GOCHARA_EFFECT_NEUTRAL("%1\$s transit in %2\$dth house has neutral effects on %3\$s.%4\$s", "%2\$dऔं भावमा %1\$s गोचरले %3\$s मा तटस्थ प्रभाव पार्छ।%4\$s"),
    GOCHARA_EFFECT_CHALLENGING("%1\$s transit in %2\$dth house may challenge %3\$s.%4\$s", "%2\$dऔं भावमा %1\$s गोचरले %3\$s लाई चुनौती दिन सक्छ।%4\$s"),
    GOCHARA_EFFECT_DIFFICULT("%1\$s transit in %2\$dth house requires caution in %3\$s.%4\$s", "%2\$dऔं भावमा %1\$s गोचरले %3\$s मा सावधानी आवश्यक छ।%4\$s"),
    
    // ============================================
    // ASPECT INTERPRETATIONS
    // ============================================
    ASPECT_APPLYING("becoming exact", "पूर्ण हुँदै"),
    ASPECT_SEPARATING("separating", "छुट्टिदै"),
    
    ASPECT_INTERP_BENEFIC_HARMONIC("Favorable: Transit %1\$s %2\$s natal %3\$s (%4\$s) - beneficial influence", "शुभ: गोचर %1\$s %2\$s जन्म %3\$s (%4\$s) - लाभदायक प्रभाव"),
    ASPECT_INTERP_BENEFIC("Transit %1\$s %2\$s natal %3\$s (%4\$s) - mixed but generally supportive", "गोचर %1\$s %2\$s जन्म %3\$s (%4\$s) - मिश्रित तर सामान्यतया सहयोगी"),
    ASPECT_INTERP_HARMONIC("Transit %1\$s %2\$s natal %3\$s (%4\$s) - harmonious connection", "गोचर %1\$s %2\$s जन्म %3\$s (%4\$s) - सामंजस्यपूर्ण सम्बन्ध"),
    ASPECT_INTERP_CHALLENGING("Transit %1\$s %2\$s natal %3\$s (%4\$s) - requires attention", "गोचर %1\$s %2\$s जन्म %3\$s (%4\$s) - ध्यान दिन आवश्यक"),
    
    // Aspect Names (if not elsewhere)
    ASPECT_CONJUNCTION("Conjunction", "युति"),
    ASPECT_SEXTILE("Sextile", "षडाष्टक"), // Note: Vedic uses different terms but Western names are used in code. Sextile ~ 3/11 or 60 deg.
    ASPECT_SQUARE("Square", "केन्द्र"),
    ASPECT_TRINE("Trine", "त्रिकोण"),
    ASPECT_OPPOSITION("Opposition", "समसप्तक"),

    // ============================================
    // SUMMARY TEMPLATES
    // ============================================
    SUMMARY_EXCELLENT("This is an excellent transit period. %s are well-placed from Moon, supporting growth and positive developments.", "यो उत्कृष्ट गोचर अवधि हो। %s चन्द्रमाबाट राम्रो स्थानमा छन्, जसले वृद्धि र सकारात्मक विकासलाई सहयोग गर्छ।"),
    SUMMARY_GOOD("Overall favorable transit period. %s provide support. Good time for important initiatives.", "समग्रमा शुभ गोचर अवधि। %s ले सहयोग प्रदान गर्छन्। महत्त्वपूर्ण पहलहरूको लागि राम्रो समय।"),
    SUMMARY_MIXED("Mixed transit influences present. Balance %s positives against %s challenges.", "मिश्रित गोचर प्रभावहरू उपस्थित छन्। %s सकारात्मक पक्षहरूलाई %s चुनौतीहरूसँग सन्तुलन गर्नुहोस्।"),
    SUMMARY_CHALLENGING("Challenging period requiring patience. %s may create obstacles. Focus on steady progress.", "धैर्य चाहिने चुनौतीपूर्ण अवधि। %s ले बाधाहरू सिर्जना गर्न सक्छन्। स्थिर प्रगतिमा ध्यान दिनुहोस्।"),
    SUMMARY_DIFFICULT("Difficult transit period. %s create significant challenges. Exercise caution and avoid major decisions.", "कठिन गोचर अवधि। %s ले महत्त्वपूर्ण चुनौतीहरू सिर्जना गर्छन्। सावधानी अपनाउनुहोस् र ठूला निर्णयहरूबाट बच्नुहोस्।"),
    
    // ============================================
    // FOCUS AREAS
    // ============================================
    FOCUS_SADE_SATI("Sade Sati period - focus on patience, health, and spiritual growth", "साढे साती अवधि - धैर्य, स्वास्थ्य र आध्यात्मिक वृद्धिमा ध्यान दिनुहोस्"),
    FOCUS_ASHTAMA_SHANI("Ashtama Shani - be cautious about health, unexpected challenges", "अष्टम शनि - स्वास्थ्य र अप्रत्याशित चुनौतीहरूबारे सावधान रहनुहोस्"),
    FOCUS_SATURN_4("Saturn transiting 4th - attention to home, mother, mental peace", "चौथो भावमा शनि - घर, आमा र मानसिक शान्तिमा ध्यान दिनुहोस्"),
    FOCUS_SATURN_10("Saturn transiting 10th - career responsibilities, hard work pays off", "दशौं भावमा शनि - करियर जिम्मेवारीहरू, कडा परिश्रमले फल दिन्छ"),
    
    FOCUS_JUPITER_TRINE("Jupiter in trine houses - excellent for expansion, learning, spirituality", "त्रिकोण भावमा बृहस्पति - विस्तार, सिकाइ र आध्यात्मिकताको लागि उत्कृष्ट"),
    FOCUS_JUPITER_2("Jupiter transiting 2nd - favorable for wealth accumulation", "दोस्रो भावमा बृहस्पति - धन सञ्चयको लागि अनुकूल"),
    FOCUS_JUPITER_11("Jupiter transiting 11th - gains through networking, fulfillment of desires", "एघारौं भावमा बृहस्पति - नेटवर्किङ मार्फत लाभ, इच्छाहरूको पूर्ति"),
    
    FOCUS_STRONG_ASPECT("Strong %1\$s from transit %2\$s to natal %3\$s", "गोचर %2\$s बाट जन्म %3\$s मा बलियो %1\$s"),
    
    // ============================================
    // PERIOD DESCRIPTIONS
    // ============================================
    PERIOD_SADE_SATI_BEGIN("Sade Sati beginning phase (Saturn in 12th from Moon)", "साढे साती सुरु हुने चरण (चन्द्रमाबाट १२औंमा शनि)"),
    PERIOD_SADE_SATI_PEAK("Sade Sati peak phase (Saturn over natal Moon)", "साढे साती शिखर चरण (जन्म चन्द्रमामाथि शनि)"),
    PERIOD_SADE_SATI_END("Sade Sati ending phase (Saturn in 2nd from Moon)", "साढे साती अन्त्य हुने चरण (चन्द्रमाबाट दोस्रोमा शनि)"),
    PERIOD_ASHTAMA_SHANI("Ashtama Shani (Saturn in 8th from Moon)", "अष्टम शनि (चन्द्रमाबाट आठौंमा शनि)"),
    PERIOD_KANTAK_SHANI("Kantak Shani (Saturn in 4th from Moon)", "कन्टक शनि (चन्द्रमाबाट चौथोमा शनि)"),
    PERIOD_SATURN_7("Saturn in 7th from Moon - relationship focus", "चन्द्रमाबाट सातौंमा शनि - सम्बन्धमा ध्यान"),
    PERIOD_SATURN_10("Saturn in 10th from Moon - career challenges and growth", "चन्द्रमाबाट दशौंमा शनि - करियर चुनौतीहरू र वृद्धि"),
    PERIOD_SATURN_GENERIC("Saturn transit in %dth from Moon", "चन्द्रमाबाट %dऔंमा शनि गोचर"),
    
    PERIOD_JUPITER_1("Jupiter over natal Moon - expansion and growth", "जन्म चन्द्रमामाथि बृहस्पति - विस्तार र वृद्धि"),
    PERIOD_JUPITER_5("Jupiter in 5th from Moon - creativity and children", "चन्द्रमाबाट पाँचौंमा बृहस्पति - सिर्जनशीलता र सन्तान"),
    PERIOD_JUPITER_9("Jupiter in 9th from Moon - fortune and dharma", "चन्द्रमाबाट नवौंमा बृहस्पति - भाग्य र धर्म"),
    PERIOD_JUPITER_GENERIC("Jupiter transit in %dth from Moon", "चन्द्रमाबाट %dऔंमा बृहस्पति गोचर"),
    
    PERIOD_RAHU_GENERIC("Rahu transit in %dth from Moon - worldly desires amplified", "चन्द्रमाबाट %dऔंमा राहु गोचर - सांसारिक इच्छाहरू बढ्छ"),
    PERIOD_KETU_GENERIC("Ketu transit in %dth from Moon - spiritual detachment", "चन्द्रमाबाट %dऔंमा केतु गोचर - आध्यात्मिक वैराग्य"),
    PERIOD_GENERIC("%1\$s transit in %2\$dth from Moon", "चन्द्रमाबाट %2\$dऔंमा %1\$s गोचर"),
    
    // ============================================
    // TRANSIT QUALITIES & EFFECTS
    // ============================================
    QUALITY_EXCELLENT("Excellent Period", "उत्कृष्ट अवधि"),
    QUALITY_GOOD("Good Period", "राम्रो अवधि"),
    QUALITY_MIXED("Mixed Period", "मिश्रित अवधि"),
    QUALITY_CHALLENGING("Challenging Period", "चुनौतीपूर्ण अवधि"),
    QUALITY_DIFFICULT("Difficult Period", "कठिन अवधि"),
    
    EFFECT_EXCELLENT("Excellent", "उत्कृष्ट"),
    EFFECT_GOOD("Good", "राम्रो"),
    EFFECT_NEUTRAL("Neutral", "तटस्थ"),
    EFFECT_CHALLENGING("Challenging", "चुनौतीपूर्ण"),
    EFFECT_DIFFICULT("Difficult", "कठिन");
}
