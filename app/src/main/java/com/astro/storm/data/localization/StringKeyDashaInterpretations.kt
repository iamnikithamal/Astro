package com.astro.storm.data.localization

enum class StringKeyDashaInterpretations(override val en: String, override val ne: String) : StringKeyInterface {

    // ============================================
    // MAHADASHA INTERPRETATIONS
    // ============================================
    MAHADASHA_SUN_INTERP(
        "A period of heightened self-expression, authority, and recognition. Focus turns to career advancement, leadership roles, government dealings, and matters related to father. Soul purpose becomes clearer. Health of heart and vitality gains prominence. Good for developing confidence and establishing one's identity in the world.",
        "आत्म-अभिव्यक्ति, अधिकार र मान्यता बढ्ने अवधि। ध्यान करियर उन्नति, नेतृत्व भूमिका, सरकारी कारोबार र पितासँग सम्बन्धित मामिलाहरूमा जान्छ। आत्माको उद्देश्य स्पष्ट हुन्छ। हृदय र जीवनशक्तिको स्वास्थ्य प्रमुखता पाउँछ। आत्मविश्वास विकास गर्न र संसारमा आफ्नो पहिचान स्थापित गर्न राम्रो छ।"
    ),
    MAHADASHA_MOON_INTERP(
        "An emotionally rich and intuitive period emphasizing mental peace, nurturing, and receptivity. Focus on mother, home life, public image, travel across water, and emotional well-being. Creativity and imagination flourish. Memory and connection to the past strengthen. Relationships with women and the public become significant.",
        "मानसिक शान्ति, पोषण र ग्रहणशीलतामा जोड दिने भावनात्मक रूपमा समृद्ध र अन्तर्ज्ञानी अवधि। ध्यान आमा, गृहस्थ जीवन, सार्वजनिक छवि, जल यात्रा र भावनात्मक कल्याणमा केन्द्रित हुन्छ। सिर्जनशीलता र कल्पना फस्टाउँछ। स्मृति र विगतसँगको सम्बन्ध बलियो हुन्छ। महिला र जनतासँगको सम्बन्ध महत्त्वपूर्ण हुन्छ।"
    ),
    MAHADASHA_MARS_INTERP(
        "A period of heightened energy, courage, initiative, and competitive drive. Focus on property matters, real estate, siblings, technical and engineering pursuits, sports, and surgery. Decisive action is favored. Physical vitality increases. Good for tackling challenges requiring strength and determination.",
        "बढेको ऊर्जा, साहस, पहल र प्रतिस्पर्धात्मक उत्साहको अवधि। ध्यान सम्पत्ति मामिलाहरू, घरजग्गा, भाइबहिनी, प्राविधिक र इन्जिनियरिङ कार्यहरू, खेलकुद र शल्यक्रियामा केन्द्रित हुन्छ। निर्णायक कारबाही अनुकूल छ। शारीरिक जीवनशक्ति बढ्छ। शक्ति र दृढता चाहिने चुनौतीहरू सामना गर्न राम्रो छ।"
    ),
    MAHADASHA_MERCURY_INTERP(
        "A period of enhanced learning, communication, analytical thinking, and commerce. Focus on education, writing, publishing, accounting, trade, and intellectual pursuits. Social connections expand through skillful communication. Good for developing skills, starting businesses, and mastering information.",
        "बढेको सिकाइ, सञ्चार, विश्लेषणात्मक सोच र वाणिज्यको अवधि। ध्यान शिक्षा, लेखन, प्रकाशन, लेखा, व्यापार र बौद्धिक कार्यहरूमा केन्द्रित हुन्छ। कुशल सञ्चार मार्फत सामाजिक सम्बन्धहरू विस्तार हुन्छन्। सीप विकास गर्न, व्यवसाय सुरु गर्न र जानकारीमा निपुणता हासिल गर्न राम्रो छ।"
    ),
    MAHADASHA_JUPITER_INTERP(
        "A period of wisdom, expansion, prosperity, and divine grace (Guru's blessings). Focus on spirituality, higher learning, teaching, children, law, and philosophical pursuits. Fortune favors righteous endeavors. Faith and optimism increase. Excellent for marriage, progeny, and spiritual advancement.",
        "ज्ञान, विस्तार, समृद्धि र दैवीय कृपा (गुरुको आशीर्वाद) को अवधि। ध्यान आध्यात्मिकता, उच्च शिक्षा, शिक्षण, सन्तान, कानून र दार्शनिक कार्यहरूमा केन्द्रित हुन्छ। भाग्यले धार्मिक प्रयासहरूलाई साथ दिन्छ। विश्वास र आशावाद बढ्छ। विवाह, सन्तान र आध्यात्मिक उन्नतिको लागि उत्कृष्ट।"
    ),
    MAHADASHA_VENUS_INTERP(
        "A period of luxury, beauty, relationships, artistic expression, and material comforts. Focus on marriage, partnerships, arts, music, dance, vehicles, jewelry, and sensory pleasures. Creativity and romance blossom. Refinement in all areas of life. Good for enhancing beauty, wealth, and experiencing life's pleasures.",
        "विलासिता, सौन्दर्य, सम्बन्ध, कलात्मक अभिव्यक्ति र भौतिक सुखसुविधाहरूको अवधि। ध्यान विवाह, साझेदारी, कला, संगीत, नृत्य, सवारी साधन, गहना र इन्द्रिय सुखहरूमा केन्द्रित हुन्छ। सिर्जनशीलता र रोमान्स फस्टाउँछ। जीवनका सबै क्षेत्रहरूमा परिष्कार। सौन्दर्य, धन बढाउन र जीवनका सुखहरू अनुभव गर्न राम्रो छ।"
    ),
    MAHADASHA_SATURN_INTERP(
        "A period of discipline, karmic lessons, perseverance, and structural growth. Focus on service, responsibility, hard work, long-term projects, and lessons through patience. Delays and obstacles ultimately lead to lasting success and maturity. Time to build solid foundations and pay karmic debts.",
        "अनुशासन, कर्मिक पाठ, लगनशीलता र संरचनात्मक वृद्धिको अवधि। ध्यान सेवा, जिम्मेवारी, कडा परिश्रम, दीर्घकालीन परियोजनाहरू र धैर्य मार्फत पाठहरूमा केन्द्रित हुन्छ। ढिलाइ र बाधाहरूले अन्ततः स्थायी सफलता र परिपक्वतातिर लैजान्छ। ठोस जग निर्माण गर्ने र कर्मिक ऋण तिर्ने समय।"
    ),
    MAHADASHA_RAHU_INTERP(
        "A period of intense worldly ambition, unconventional paths, and material desires. Focus on foreign connections, technology, innovation, and breaking traditional boundaries. Sudden opportunities and unexpected changes arise. Material gains through unusual or non-traditional means. Beware of illusions.",
        "तीव्र सांसारिक महत्त्वाकांक्षा, अपरम्परागत मार्ग र भौतिक इच्छाहरूको अवधि। ध्यान विदेशी सम्बन्ध, प्रविधि, नवप्रवर्तन र परम्परागत सीमाहरू तोड्नमा केन्द्रित हुन्छ। अचानक अवसरहरू र अप्रत्याशित परिवर्तनहरू उत्पन्न हुन्छन्। असामान्य वा गैर-परम्परागत माध्यमबाट भौतिक लाभ। भ्रमबाट सावधान रहनुहोस्।"
    ),
    MAHADASHA_KETU_INTERP(
        "A period of spirituality, detachment, and profound inner transformation. Focus on liberation (moksha), occult research, healing practices, and resolving past-life karma. Deep introspection yields spiritual insights. Material attachments may dissolve. Excellent for meditation, research, and spiritual practices.",
        "आध्यात्मिकता, वैराग्य र गहिरो आन्तरिक रूपान्तरणको अवधि। ध्यान मुक्ति (मोक्ष), तन्त्रमन्त्र अनुसन्धान, उपचार अभ्यास र पूर्वजन्मको कर्म समाधानमा केन्द्रित हुन्छ। गहिरो आत्म-चिन्तनले आध्यात्मिक अन्तरदृष्टि दिन्छ। भौतिक मोह भंग हुन सक्छ। ध्यान, अनुसन्धान र आध्यात्मिक अभ्यासहरूको लागि उत्कृष्ट।"
    ),
    MAHADASHA_DEFAULT_INTERP(
        "A period of transformation and karmic unfolding according to planetary influences.",
        "ग्रह प्रभावहरू अनुसार रूपान्तरण र कर्मिक प्रकटीकरणको अवधि।"
    ),

    // ============================================
    // ANTARDASHA INTERPRETATIONS
    // ============================================
    ANTARDASHA_SUN_INTERP(
        "Current sub-period (Bhukti) activates themes of authority, self-confidence, recognition, and dealings with father figures or government. Leadership opportunities may arise.",
        "हालको उप-अवधि (भुक्ति) ले अधिकार, आत्मविश्वास, मान्यता, र पिता समान व्यक्तित्व वा सरकारसँगको व्यवहारका विषयहरू सक्रिय गर्दछ। नेतृत्वका अवसरहरू आउन सक्छन्।"
    ),
    ANTARDASHA_MOON_INTERP(
        "Current sub-period emphasizes emotional matters, mental peace, mother, public image, domestic affairs, and connection with women. Intuition heightens.",
        "हालको उप-अवधिले भावनात्मक मामिलाहरू, मानसिक शान्ति, आमा, सार्वजनिक छवि, घरायसी मामिलाहरू र महिलाहरूसँगको सम्बन्धलाई जोड दिन्छ। अन्तर्ज्ञान बढ्छ।"
    ),
    ANTARDASHA_MARS_INTERP(
        "Current sub-period brings increased energy, drive for action, courage, and matters involving property, siblings, competition, or technical endeavors.",
        "हालको उप-अवधिले बढेको ऊर्जा, कार्यप्रतिको उत्साह, साहस, र सम्पत्ति, भाइबहिनी, प्रतिस्पर्धा वा प्राविधिक प्रयासहरू समावेश गर्ने मामिलाहरू ल्याउँछ।"
    ),
    ANTARDASHA_MERCURY_INTERP(
        "Current sub-period emphasizes communication, learning, business transactions, intellectual activities, and connections with younger people or merchants.",
        "हालको उप-अवधिले सञ्चार, सिकाइ, व्यापारिक लेनदेन, बौद्धिक गतिविधिहरू र युवा मानिसहरू वा व्यापारीहरूसँगको सम्बन्धलाई जोड दिन्छ।"
    ),
    ANTARDASHA_JUPITER_INTERP(
        "Current sub-period brings wisdom, expansion, good fortune, and focus on spirituality, teachers, children, higher education, or legal matters.",
        "हालको उप-अवधिले ज्ञान, विस्तार, सौभाग्य, र आध्यात्मिकता, शिक्षकहरू, सन्तान, उच्च शिक्षा वा कानूनी मामिलाहरूमा ध्यान ल्याउँछ।"
    ),
    ANTARDASHA_VENUS_INTERP(
        "Current sub-period emphasizes relationships, romance, creativity, luxury, artistic pursuits, material comforts, and partnership matters.",
        "हालको उप-अवधिले सम्बन्ध, रोमान्स, सिर्जनशीलता, विलासिता, कलात्मक कार्यहरू, भौतिक सुखसुविधाहरू र साझेदारी मामिलाहरूलाई जोड दिन्छ।"
    ),
    ANTARDASHA_SATURN_INTERP(
        "Current sub-period brings discipline, responsibility, hard work, delays, and lessons requiring patience. Focus on service and long-term efforts.",
        "हालको उप-अवधिले अनुशासन, जिम्मेवारी, कडा परिश्रम, ढिलाइ र धैर्य चाहिने पाठहरू ल्याउँछ। सेवा र दीर्घकालीन प्रयासहरूमा ध्यान केन्द्रित गर्नुहोस्।"
    ),
    ANTARDASHA_RAHU_INTERP(
        "Current sub-period emphasizes worldly ambitions, unconventional approaches, foreign matters, technology, and sudden changes or opportunities.",
        "हालको उप-अवधिले सांसारिक महत्त्वाकांक्षा, अपरम्परागत दृष्टिकोण, विदेशी मामिलाहरू, प्रविधि र अचानक परिवर्तन वा अवसरहरूलाई जोड दिन्छ।"
    ),
    ANTARDASHA_KETU_INTERP(
        "Current sub-period brings spiritual insights, detachment, introspection, research, and resolution of past karmic patterns. Material concerns recede.",
        "हालको उप-अवधिले आध्यात्मिक अन्तरदृष्टि, वैराग्य, आत्म-चिन्तन, अनुसन्धान र विगतका कर्मिक ढाँचाहरूको समाधान ल्याउँछ। भौतिक चिन्ताहरू कम हुन्छन्।"
    ),
    ANTARDASHA_DEFAULT_INTERP(
        "Current sub-period brings mixed planetary influences requiring careful navigation.",
        "हालको उप-अवधिले मिश्रित ग्रह प्रभावहरू ल्याउँछ जसलाई सावधानीपूर्वक नेभिगेट गर्न आवश्यक छ।"
    );
}
