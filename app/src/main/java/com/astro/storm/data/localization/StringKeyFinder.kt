package com.astro.storm.data.localization

/**
 * String keys for specific astrological findings (Mrityu Bhaga, Pushkara, etc.)
 */
enum class StringKeyFinder(override val en: String, override val ne: String) : StringKeyInterface {
    // Mrityu Bhaga Effects
    MB_SUN_EFF_1("Challenges with authority figures or father", "अधिकारवाला व्यक्ति वा बुबासँग चुनौतीहरू"),
    MB_SUN_EFF_2("Potential health issues related to heart or vitality", "मुटु वा जीवनशक्तिसँग सम्बन्धित सम्भावित स्वास्थ्य समस्याहरू"),
    MB_SUN_EFF_3("Career obstacles during certain periods", "निश्चित अवधिमा करियरमा अवरोधहरू"),
    MB_SUN_EFF_4("Need for ego transformation", "अहंकार रूपान्तरणको आवश्यकता"),
    
    MB_MOON_EFF_1("Emotional sensitivity and mental stress", "भावनात्मक संवेदनशीलता र मानसिक तनाव"),
    MB_MOON_EFF_2("Challenges with mother or maternal relationships", "आमा वा मातृ सम्बन्धमा चुनौतीहरू"),
    MB_MOON_EFF_3("Fluctuating public image or reputation", "सार्वजनिक छवि वा प्रतिष्ठामा उतार-चढाव"),
    MB_MOON_EFF_4("Need for emotional healing and stability", "भावनात्मक उपचार र स्थिरताको आवश्यकता"),
    
    MB_MARS_EFF_1("Accident-prone tendencies during Mars periods", "मंगलको अवधिमा दुर्घटनाको जोखिम हुने प्रवृत्ति"),
    MB_MARS_EFF_2("Blood-related or surgical issues possible", "रगत सम्बन्धी वा शल्यक्रिया सम्बन्धी समस्याहरूको सम्भावना"),
    MB_MARS_EFF_3("Conflicts with siblings or competitors", "भाइबहिनी वा प्रतिस्पर्धीहरूसँग द्वन्द्व"),
    MB_MARS_EFF_4("Need to channel energy constructively", "ऊर्जालाई रचनात्मक रूपमा प्रयोग गर्नुपर्ने आवश्यकता"),
    
    MB_MERC_EFF_1("Communication difficulties or misunderstandings", "सञ्चारमा कठिनाइ वा गलतफहमी"),
    MB_MERC_EFF_2("Nervous system sensitivity", "स्नायु प्रणालीको संवेदनशीलता"),
    MB_MERC_EFF_3("Educational or business challenges", "शैक्षिक वा व्यापारिक चुनौतीहरू"),
    MB_MERC_EFF_4("Need for mental clarity practices", "मानसिक स्पष्टताका अभ्यासहरूको आवश्यकता"),
    
    MB_JUP_EFF_1("Challenges in education or spiritual growth", "शिक्षा वा आध्यात्मिक वृद्धिमा चुनौतीहरू"),
    MB_JUP_EFF_2("Issues with teachers or mentors", "शिक्षक वा गुरुहरूसँग समस्या"),
    MB_JUP_EFF_3("Financial ups and downs", "आर्थिक उतार-चढाव"),
    MB_JUP_EFF_4("Need for wisdom and philosophical grounding", "ज्ञान र दार्शनिक आधारको आवश्यकता"),
    
    MB_VENUS_EFF_1("Relationship challenges or disappointments", "सम्बन्धमा चुनौती वा निराशा"),
    MB_VENUS_EFF_2("Reproductive health considerations", "प्रजनन स्वास्थ्य सम्बन्धी विचारणीय कुराहरू"),
    MB_VENUS_EFF_3("Artistic blocks or creative challenges", "कलात्मक अवरोध वा सिर्जनात्मक चुनौतीहरू"),
    MB_VENUS_EFF_4("Need for self-love and relationship healing", "आत्म-प्रेम र सम्बन्ध उपचारको आवश्यकता"),
    
    MB_SAT_EFF_1("Career delays or professional setbacks", "करियरमा ढिलाइ वा व्यावसायिक अवरोधहरू"),
    MB_SAT_EFF_2("Joint or bone-related health issues", "जोर्नी वा हड्डीसँग सम्बन्धित स्वास्थ्य समस्याहरू"),
    MB_SAT_EFF_3("Karmic lessons intensified", "कार्मिक पाठहरू तीव्र पारिएको"),
    MB_SAT_EFF_4("Need for patience and perseverance", "धैर्य र लगनशीलताको आवश्यकता"),
    
    MB_RAHU_EFF_1("Sudden unexpected events", "अचानक अप्रत्याशित घटनाहरू"),
    MB_RAHU_EFF_2("Foreign-related complications", "विदेशसँग सम्बन्धित जटिलताहरू"),
    MB_RAHU_EFF_3("Obsessive tendencies intensified", "जुनूनी प्रवृत्तिहरू तीव्र पारिएको"),
    MB_RAHU_EFF_4("Need for grounding and reality checks", "यथार्थमा रहनुपर्ने आवश्यकता"),
    
    MB_KETU_EFF_1("Spiritual crises or confusion", "आध्यात्मिक संकट वा भ्रम"),
    MB_KETU_EFF_2("Detachment-related challenges", "वैराग्य सम्बन्धी चुनौतीहरू"),
    MB_KETU_EFF_3("Past-life karma surfacing", "विगतको जीवनको कर्म बाहिर आउने"),
    MB_KETU_EFF_4("Need for spiritual practices", "आध्यात्मिक अभ्यासहरूको आवश्यकता"),

    // Vulnerability Areas
    AREA_HEART("Heart", "मुटु"),
    AREA_EYES("Eyes", "आँखा"),
    AREA_SPINE("Spine", "मेरुदण्ड"),
    AREA_VITALITY("Vitality", "जीवनशक्ति"),
    AREA_MIND("Mind", "मन"),
    AREA_EMOTIONS("Emotions", "भावना"),
    AREA_STOMACH("Stomach", "पेट"),
    AREA_BREASTS("Breasts", "स्तन"),
    AREA_FLUIDS("Fluids", "तरल पदार्थ"),
    AREA_BLOOD("Blood", "रगत"),
    AREA_MUSCLES("Muscles", "मांसपेशी"),
    AREA_HEAD("Head", "टाउको"),
    AREA_ACCIDENTS("Accidents", "दुर्घटना"),
    AREA_BURNS("Burns", "पोलेको"),
    AREA_NERVOUS("Nervous System", "स्नायु प्रणाली"),
    AREA_SKIN("Skin", "छाला"),
    AREA_LUNGS("Lungs", "फोक्सो"),
    AREA_SPEECH("Speech", "वाणी"),
    AREA_LIVER("Liver", "कलेजो"),
    AREA_FAT("Fat Tissues", "बोसो तन्तु"),
    AREA_EARS("Ears", "कान"),
    AREA_THIGHS("Thighs", "तिघ्रा"),
    AREA_REPRO("Reproductive System", "प्रजनन प्रणाली"),
    AREA_KIDNEYS("Kidneys", "मृगौला"),
    AREA_THROAT("Throat", "घाँटी"),
    AREA_FACE("Face", "अनुहार"),
    AREA_BONES("Bones", "हड्डी"),
    AREA_JOINTS("Joints", "जोर्नी"),
    AREA_TEETH("Teeth", "दाँत"),
    AREA_CHRONIC("Chronic Conditions", "पुरानो रोग"),
    AREA_POISONS("Poisons", "विष"),
    AREA_UNKNOWN_DIS("Unknown Diseases", "अज्ञात रोग"),
    AREA_WOUNDS("Wounds", "चोट"),
    AREA_INFECTIONS("Infections", "संक्रमण"),
    AREA_SPIRIT_CRISIS("Spiritual Crises", "आध्यात्मिक संकट"),

    // Mrityu Bhaga Remedies
    MB_SUN_REM_1("Offer water to Sun at sunrise", "सूर्योदयमा सूर्यलाई जल अर्पण गर्नुहोस्"),
    MB_SUN_REM_2("Recite Aditya Hridayam regularly", "नियमित रूपमा आदित्य हृदयम् पाठ गर्नुहोस्"),
    MB_SUN_REM_3("Wear Ruby with proper muhurta (if suitable)", "उपयुक्त भएमा शुभ मुहूर्तमा माणिक लगाउनुहोस्"),
    MB_SUN_REM_4("Donate wheat and jaggery on Sundays", "आइतबार गहुँ र सख्खर दान गर्नुहोस्"),
    
    MB_MOON_REM_1("Worship Divine Mother on Mondays", "सोमबार देवी भगवतीको पूजा गर्नुहोस्"),
    MB_MOON_REM_2("Wear Pearl or Moonstone (if suitable)", "उपयुक्त भएमा मोती वा चन्द्रकान्त मणि लगाउनुहोस्"),
    MB_MOON_REM_3("Offer milk to Shiva Lingam", "शिवलिङ्गमा दूध चढाउनुहोस्"),
    MB_MOON_REM_4("Practice meditation for mental peace", "मानसिक शान्तिको लागि ध्यान अभ्यास गर्नुहोस्"),
    
    MB_MARS_REM_1("Recite Hanuman Chalisa daily", "दैनिक हनुमान चालीसा पाठ गर्नुहोस्"),
    MB_MARS_REM_2("Wear Red Coral (if suitable)", "उपयुक्त भएमा मुगा लगाउनुहोस्"),
    MB_MARS_REM_3("Donate red lentils on Tuesdays", "मंगलबार मुसुरोको दाल दान गर्नुहोस्"),
    MB_MARS_REM_4("Practice physical exercise regularly", "नियमित रूपमा शारीरिक व्यायाम गर्नुहोस्"),
    
    MB_MERC_REM_1("Worship Lord Vishnu on Wednesdays", "बुधबार भगवान विष्णुको पूजा गर्नुहोस्"),
    MB_MERC_REM_2("Wear Emerald (if suitable)", "उपयुक्त भएमा पन्ना लगाउनुहोस्"),
    MB_MERC_REM_3("Donate green vegetables", "हरियो तरकारी दान गर्नुहोस्"),
    MB_MERC_REM_4("Chant Om Budhaya Namaha", "ॐ बुधाय नमः जप गर्नुहोस्"),
    
    MB_JUP_REM_1("Worship Lord Brihaspati on Thursdays", "बिहीबार भगवान बृहस्पतिको पूजा गर्नुहोस्"),
    MB_JUP_REM_2("Wear Yellow Sapphire (if suitable)", "उपयुक्त भएमा पुखराज लगाउनुहोस्"),
    MB_JUP_REM_3("Donate yellow items and turmeric", "पहेंलो वस्तु र बेसार दान गर्नुहोस्"),
    MB_JUP_REM_4("Respect teachers and elders", "शिक्षक र ठूलाबाडालाई आदर गर्नुहोस्"),
    
    MB_VENUS_REM_1("Worship Goddess Lakshmi on Fridays", "शुक्रबार देवी लक्ष्मीको पूजा गर्नुहोस्"),
    MB_VENUS_REM_2("Wear Diamond or White Sapphire (if suitable)", "उपयुक्त भएमा हीरा वा सेतो पुखराज लगाउनुहोस्"),
    MB_VENUS_REM_3("Donate white items and rice", "सेतो वस्तु र चामल दान गर्नुहोस्"),
    MB_VENUS_REM_4("Practice self-care and appreciation", "आत्म-हेरचाह र प्रशंसा अभ्यास गर्नुहोस्"),
    
    MB_SAT_REM_1("Worship Lord Shani on Saturdays", "शनिबार भगवान शनिको पूजा गर्नुहोस्"),
    MB_SAT_REM_2("Wear Blue Sapphire only after thorough analysis", "गहिरो विश्लेषण पछि मात्र नीलम लगाउनुहोस्"),
    MB_SAT_REM_3("Donate black sesame and iron items", "कालो तिल र फलामका वस्तुहरू दान गर्नुहोस्"),
    MB_SAT_REM_4("Serve the elderly and underprivileged", "वृद्ध र असहायहरूको सेवा गर्नुहोस्"),
    
    MB_RAHU_REM_1("Worship Goddess Durga", "देवी दुर्गाको पूजा गर्नुहोस्"),
    MB_RAHU_REM_2("Wear Hessonite (Gomed) if suitable", "उपयुक्त भएमा गोमेद लगाउनुहोस्"),
    MB_RAHU_REM_3("Donate blue clothes and blankets", "नीलो कपडा र कम्बल दान गर्नुहोस्"),
    MB_RAHU_REM_4("Avoid intoxicants and maintain ethics", "नशालु पदार्थबाट बच्नुहोस् र नैतिकता कायम राख्नुहोस्"),
    
    MB_KETU_REM_1("Worship Lord Ganesha", "भगवान गणेशको पूजा गर्नुहोस्"),
    MB_KETU_REM_2("Wear Cat's Eye (if suitable)", "उपयुक्त भएमा वैदूर्य (लहसुनिया) लगाउनुहोस्"),
    MB_KETU_REM_3("Donate multi-colored blankets", "बहुरंगी कम्बल दान गर्नुहोस्"),
    MB_KETU_REM_4("Practice meditation and spiritual disciplines", "ध्यान र आध्यात्मिक अनुशासन अभ्यास गर्नुहोस्"),

    // Gandanta Effects & Remedies
    GAND_BRAHMA_EFF_1("Initial life challenges (first 3 years critical)", "जीवनको सुरुवाती चुनौतीहरू (पहिलो ३ वर्ष महत्त्वपूर्ण)"),
    GAND_BRAHMA_EFF_2("Mother's health during pregnancy/delivery", "गर्भावस्था/प्रसवको समयमा आमाको स्वास्थ्य"),
    GAND_BRAHMA_EFF_3("Creative blocks requiring resolution", "समाधान आवश्यक पर्ने सिर्जनात्मक अवरोधहरू"),
    
    GAND_VISHNU_EFF_1("Transformation through crisis", "संकट मार्फत रूपान्तरण"),
    GAND_VISHNU_EFF_2("Hidden enemies or obstacles", "लुकेका शत्रु वा अवरोधहरू"),
    GAND_VISHNU_EFF_3("Research and occult abilities", "अनुसन्धान र तान्त्रिक क्षमताहरू"),
    
    GAND_SHIVA_EFF_1("Spiritual awakening through dissolution", "विघटन मार्फत आध्यात्मिक जागृति"),
    GAND_SHIVA_EFF_2("Endings leading to new beginnings", "नयाँ सुरुवात तर्फ लैजाने अन्त्यहरू"),
    GAND_SHIVA_EFF_3("Liberation from material attachments", "भौतिक आसक्तिबाट मुक्ति"),
    
    GAND_MOON_EFF_1("Emotional turbulence at junction points", "सन्धि बिन्दुहरूमा भावनात्मक उथलपुथल"),
    GAND_MOON_EFF_2("Mental transformation needed", "मानसिक रूपान्तरणको आवश्यकता"),
    
    GAND_SUN_EFF_1("Identity crisis leading to self-realization", "आत्म-बोध तर्फ लैजाने पहिचानको संकट"),
    GAND_SUN_EFF_2("Authority challenges", "अधिकारमा चुनौतीहरू"),
    
    GAND_MARS_EFF_1("Physical challenges or accidents possible", "शारीरिक चुनौती वा दुर्घटनाको सम्भावना"),
    GAND_MARS_EFF_2("Courage tested", "साहसको परीक्षा"),

    GAND_BRAHMA_REM_1("Perform Gandanta Shanti puja", "गण्डान्त शान्ति पूजा गर्नुहोस्"),
    GAND_BRAHMA_REM_2("Donate grains and gold", "अन्न र सुन दान गर्नुहोस्"),
    GAND_BRAHMA_REM_3("Worship Lord Brahma", "भगवान ब्रह्माको पूजा गर्नुहोस्"),
    
    GAND_VISHNU_REM_1("Perform Vishnu Sahasranama recitation", "विष्णु सहस्रनाम पाठ गर्नुहोस्"),
    GAND_VISHNU_REM_2("Donate black sesame and oil", "कालो तिल र तेल दान गर्नुहोस्"),
    GAND_VISHNU_REM_3("Worship Lord Vishnu regularly", "नियमित रूपमा भगवान विष्णुको पूजा गर्नुहोस्"),
    
    GAND_SHIVA_REM_1("Perform Rudra Abhishekam", "रुद्राभिषेक गर्नुहोस्"),
    GAND_SHIVA_REM_2("Donate silver and white items", "चाँदी र सेतो वस्तुहरू दान गर्नुहोस्"),
    GAND_SHIVA_REM_3("Worship Lord Shiva with devotion", "भक्तिका साथ भगवान शिवको पूजा गर्नुहोस्"),

    // Pushkara Benefits
    PUSH_BASE_1("Highly auspicious placement bringing good fortune", "उच्च शुभ स्थिति जसले राम्रो भाग्य ल्याउँछ"),
    PUSH_BASE_2("Planet's significations strengthened", "ग्रहको कारकत्व बलियो पारिएको"),
    PUSH_BASE_3("Protection during difficult periods", "कठिन अवधिमा सुरक्षा"),
    
    PUSH_MOON_1("Emotional stability and contentment", "भावनात्मक स्थिरता र सन्तुष्टि"),
    PUSH_MOON_2("Good mental health", "राम्रो मानसिक स्वास्थ्य"),
    
    PUSH_JUP_1("Wisdom and spiritual growth enhanced", "ज्ञान र आध्यात्मिक वृद्धि बढाइएको"),
    PUSH_JUP_2("Educational success", "शैक्षिक सफलता"),
    
    PUSH_VENUS_1("Relationship harmony", "सम्बन्धमा सद्भाव"),
    PUSH_VENUS_2("Artistic success and beauty", "कलात्मक सफलता र सौन्दर्य"),
    
    PUSH_MERC_1("Intellectual brilliance", "बौद्धिक प्रखरता"),
    PUSH_MERC_2("Communication excellence", "उत्कृष्ट सञ्चार"),
    
    PUSH_GEN_PROT("General protection and auspiciousness", "सामान्य सुरक्षा र शुभता"),

    PUSH_BHAGA_NOURISH("Planet at its most nourishing degree", "ग्रह आफ्नो सबैभन्दा पोषक डिग्रीमा"),
    PUSH_BHAGA_SUPPORT("Significations of %s receive special support", "%s को कारकत्वहरूले विशेष समर्थन प्राप्त गर्दछ"),
    PUSH_BHAGA_ACT("Auspicious for %s-related activities", "%s सँग सम्बन्धित गतिविधिहरूको लागि शुभ"),
    PUSH_BHAGA_PROT("Natural protection during %s periods", "%s को अवधिमा प्राकृतिक सुरक्षा"),

    // Transit Recommendations
    TRANSIT_CAUTION("Be extra cautious during this transit period", "यस गोचर अवधिमा विशेष सावधान रहनुहोस्"),
    TRANSIT_MAJOR_DEC("Avoid major decisions related to %s's significations", "%s को कारकत्वसँग सम्बन्धित ठूला निर्णयहरू नगर्नुहोस्"),
    TRANSIT_INTENSE_REM("Practice %s's remedies more intensely", "%s को उपायहरू थप तीव्रताका साथ गर्नुहोस्"),
    TRANSIT_AWARENESS("Maintain awareness but avoid excessive worry", "सजग रहनुहोस् तर धेरै चिन्ता नगर्नुहोस्"),

    // Assessment Summaries
    ASSESS_NEEDS_ATTN("Multiple sensitive placements require attention and regular remedies", "धेरै संवेदनशील स्थितिहरूलाई ध्यान र नियमित उपायहरूको आवश्यकता छ"),
    ASSESS_MODERATE("Some sensitive placements present; recommended remedies will help", "केही संवेदनशील स्थितिहरू छन्; सिफारिस गरिएका उपायहरूले मद्दत गर्नेछन्"),
    ASSESS_BALANCED("Balance of challenging and supportive placements", "चुनौतीपूर्ण र सहयोगी स्थितिहरूको सन्तुलन"),
    ASSESS_HIGHLY_AUSP("Multiple auspicious Pushkara placements provide natural protection", "धेरै शुभ पुष्कर स्थितिहरूले प्राकृतिक सुरक्षा प्रदान गर्दछ"),
    ASSESS_GEN_POS("Generally favorable placement pattern", "सामान्यतया अनुकूल स्थिति ढाँचा"),

    // Recommendations
    REC_MB_SPECIFIC("Perform specific planetary remedies for planets in exact Mrityu Bhaga", "सही मृत्यु भागमा रहेका ग्रहहरूको लागि विशिष्ट ग्रह उपचार गर्नुहोस्"),
    REC_MB_CAREFUL("Be especially careful during Dasha/Antardasha of affected planets", "प्रभावित ग्रहहरूको दशा/अन्तर्दशामा विशेष सावधान रहनुहोस्"),
    REC_GAND_MOON_ATTN("Moon in Gandanta requires special attention - Gandanta Shanti recommended", "गण्डान्तमा रहेको चन्द्रमालाई विशेष ध्यान चाहिन्छ - गण्डान्त शान्ति सिफारिस गरिएको"),
    REC_GAND_MOON_GROUND("Practice meditation and emotional grounding regularly", "नियमित रूपमा ध्यान र भावनात्मक स्थिरताको अभ्यास गर्नुहोस्"),
    REC_GAND_KNOTS("Gandanta placements indicate karmic knots requiring spiritual work", "गण्डान्त स्थितिहरूले आध्यात्मिक कार्य आवश्यक पर्ने कार्मिक गाँठोहरू संकेत गर्दछ"),
    REC_NO_CRITICAL("No critical sensitive degree placements - general spiritual practices sufficient", "कुनै गम्भीर संवेदनशील डिग्री स्थिति छैन - सामान्य आध्यात्मिक अभ्यासहरू पर्याप्त छन्"),
    REC_CONT_WORSHIP("Continue regular worship and ethical living", "नियमित पूजा र नैतिक जीवन जारी राख्नुहोस्"),

    // UI Labels
    LABEL_MB("Mrityu Bhaga", "मृत्यु भाग"),
    LABEL_PUSH_NAV("Pushkara Navamsa", "पुष्कर नवरांश"),
    LABEL_PUSH_BHAGA("Pushkara Bhaga", "पुष्कर भाग"),
    LABEL_GANDANTA("Gandanta", "गण्डान्त"),
    LABEL_MB_DEGREE("MB: %s°", "मृ.भा.: %s°"),
    LABEL_ACTUAL_DEGREE("Actual: %s°", "वास्तविक: %s°"),
    LABEL_NOURISHING_DEGREE("Nourishing degree: %1\$s° (Orb: %2\$s°)", "पोषक डिग्री: %1\$s° (अर्ब: %2\$s°)"),
    LABEL_MB_IN("in Mrityu Bhaga", "मृत्यु भागमा"),
    LABEL_PUSH_NAV_IN("in Pushkara Navamsa", "पुष्कर नवरांशमा"),
    LABEL_PUSH_BHAGA_IN("in Pushkara Bhaga", "पुष्कर भागमा"),
    LABEL_GAND_IN("in Gandanta", "गण्डान्तमा"),
    LABEL_PLANET_REM("%s Remedies", "%s उपायहरू"),
    LABEL_GAND_REM("Gandanta Remedies", "गण्डान्त उपायहरू"),
    LABEL_PLANET_SPEC_REM("Planet-Specific Remedies", "ग्रह-विशिष्ट उपायहरू"),
    
    PREC_DASHA("Be extra cautious during Dasha/Antardasha periods of planets in Mrityu Bhaga", "मृत्यु भागमा रहेका ग्रहहरूको दशा/अन्तर्दशा अवधिमा विशेष सावधान रहनुहोस्"),
    PREC_TRANSIT("Avoid major decisions during critical transit periods", "महत्त्वपूर्ण गोचर अवधिहरूमा ठूला निर्णयहरू नगर्नुहोस्"),
    PREC_SPIRITUAL("Regular spiritual practices provide natural protection", "नियमित आध्यात्मिक अभ्यासले प्राकृतिक सुरक्षा प्रदान गर्दछ"),
    PREC_AWARENESS("Maintain awareness without excessive worry - these are tendencies, not certainties", "धेरै चिन्ता नगरी सजगता अपनाउनुहोस् - यी प्रवृत्ति हुन्, निश्चितता होइनन्")
}