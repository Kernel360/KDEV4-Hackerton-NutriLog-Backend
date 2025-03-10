package com.nutrilog.nutrilog_backend.common.util;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class NicknameGenerator {

    private static final List<String> ADJECTIVES = List.of(
            "귀여운", "빠른", "똑똑한", "강한", "용감한", "행복한", "슬기로운", "재미있는"
    );
    private static final List<String> NOUNS = List.of(
            "호랑이", "고양이", "펭귄", "여우", "늑대", "독수리", "토끼", "사자"
    );

    private static final Random random = new SecureRandom();

    /**
     * 랜덤 닉네임 생성 메서드
     * @return 생성된 닉네임 (예: 귀여운호랑이123)
     */
    public static String generateRandomNickname() {
        String adjective = ADJECTIVES.get(random.nextInt(ADJECTIVES.size()));
        String noun = NOUNS.get(random.nextInt(NOUNS.size()));
        int number = 100 + random.nextInt(900); // 100 ~ 999 사이의 숫자

        return adjective + noun + number;
    }
}

