package com.hagt.uitl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RandomUtil {

    private static final Map<String,Object> RANDOM_NUMBER_MAP = new HashMap<>();

    private static volatile long preTime;

    public static volatile AtomicLong i = new AtomicLong();

    private static char[] RANDOM_CODE_TABLE = new char[]{
            '1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','J','H','I',
            'J','K','L','M','N','O','P','Q','R',
            'S','T','U','V','W','X','Y','Z','a',
            'b','c','d','e','f','j','h','i','j',
            'k','l','m','n','o','p','q','r','s',
            't','u','v','w','x','y','z','0'
    };

    public String getRandomNumber()
    {
        long currentTime = System.currentTimeMillis();
        String result = generateRandomNumber(currentTime,0);
        if (preTime == currentTime){
            while (RANDOM_NUMBER_MAP.containsKey(result)) {
                result = generateRandomNumber(currentTime,i.incrementAndGet());
            }
        }else{
            i.set(0);
            RANDOM_NUMBER_MAP.clear();
        }
        RANDOM_NUMBER_MAP.put(result,0);
        preTime = currentTime;
        return result;
    }

    private String generateRandomNumber(long currentTime,long count){
        StringBuilder randomNumber = new StringBuilder();
        String currentTimeString = String.valueOf(currentTime);
        String countString = String.valueOf(count);
        randomNumber.append(currentTimeString);
        Random random = new Random();
        int randomCount = 32 - currentTimeString.length() - countString.length();
        StringBuilder randomCodeNumber = new StringBuilder();
        for (int i = 0;i < randomCount; i++){
            int randomCodeIndex = random.nextInt(RANDOM_CODE_TABLE.length);
            int numericValue = Character.getNumericValue(RANDOM_CODE_TABLE[randomCodeIndex]);
            randomCodeNumber.append(numericValue);
        }
        randomNumber.append(randomCodeNumber.subSequence(0,randomCount));
        randomNumber.append(count);
        return randomNumber.toString();
    }

    public static void main(String[] args) {
        RandomUtil randomUtil = new RandomUtil();
        System.out.println(System.currentTimeMillis());
        int i = 1;
        while (i < 100000000){
            String randomNumber = randomUtil.getRandomNumber();
            i++;
        }
        System.out.println(System.currentTimeMillis());
        System.out.println(randomUtil.i);
    }
}
