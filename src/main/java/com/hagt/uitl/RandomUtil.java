package com.hagt.uitl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomUtil {

    private final Map<String,Object> RANDOM_NUMBER_MAP = new HashMap<>();

    private volatile long preTime;

    public volatile long i;

    private static char[] RANDOM_CODE_TABLE = new char[]{
       '1','2','3','4','5','6','7','8','9',
       'A','B','C','D','E','F','J','H','I',
       'J','K','L','M','N','O','P','Q','R',
       'S','T','U','V','W','X','Y','Z','a',
       'b','c','d','e','f','j','h','i','j',
       'k','l','m','n','o','p','q','r','s',
       't','u','v','w','x','y','z','0'
    };

    public String getRandomNumber(String workName)
    {
        long currentTime = System.currentTimeMillis();
        String result = generateRandomNumber(currentTime,workName);
        if (preTime == currentTime){
            if (RANDOM_NUMBER_MAP.containsKey(result)){
                i++;
                result = getRandomNumber(workName);
            }
        }else{
            RANDOM_NUMBER_MAP.clear();
        }
        RANDOM_NUMBER_MAP.put(result,0);
        preTime = currentTime;
        return result;
    }

    private String generateRandomNumber(long currentTime,String workName){
        StringBuilder workNameNumber = new StringBuilder();
        workNameNumber.append(currentTime);
        char [] workNameChars = String.valueOf(workName).toCharArray();
        for (char workNameChar : workNameChars) {
            int workNameCodeIndex = Character.getNumericValue(workNameChar);
            char workNameCode = RANDOM_CODE_TABLE[workNameCodeIndex];
            workNameNumber.append(Character.getNumericValue(workNameCode));
        }
        return workNameNumber.toString() + new Random().nextInt();
    }

    public static void main(String[] args) {
        RandomUtil randomUtil = new RandomUtil();
        System.out.println(System.currentTimeMillis());
        int i = 1;
        while (i < 10000000){
            String randomNumber = randomUtil.getRandomNumber("OCQ");
            i++;
        }
        System.out.println(System.currentTimeMillis());
        System.out.println(randomUtil.i);
    }
}
