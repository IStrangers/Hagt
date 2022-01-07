package p;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RandomNumberUtil {
	
	private static final RandomNumberUtil ME = new RandomNumberUtil();

    private static final Map<String,Object> RANDOM_NUMBER_MAP = new HashMap<>();
    
    private static final Object SIGN = new Object();

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
    
    public static String GetRandomNumber(int length) throws Exception {
    	return ME.getRandomNumber(length);
    }

	public String getRandomNumber(int length) throws Exception {
		if (length < 18) {
			throw new Exception("Length Min 18");
		}
		String result = generateRandomNumber(length,preTime, i.incrementAndGet());
		while (RANDOM_NUMBER_MAP.containsKey(result)) {
			clear();
			result = generateRandomNumber(length,preTime, i.incrementAndGet());
		}
		RANDOM_NUMBER_MAP.put(result,SIGN);
		return result;
	}
	
    private void clear() {
		long currentTime = System.currentTimeMillis();
    	if (preTime != currentTime) {
			preTime = currentTime;
			i.set(0L);
			RANDOM_NUMBER_MAP.clear();
		}
	}

	private String generateRandomNumber(int length,long currentTime,long count){
        StringBuilder randomNumber = new StringBuilder();
        String currentTimeString = String.valueOf(currentTime);
        String countString = String.valueOf(count);
        randomNumber.append(currentTimeString);
        Random random = new Random();
        int randomCount = length - currentTimeString.length() - countString.length();
        StringBuilder randomCodeNumber = new StringBuilder();
        for (int i = 0;i < randomCount; i++){
            int randomCodeIndex = random.nextInt(RANDOM_CODE_TABLE.length);
            int numericValue = Character.getNumericValue(RANDOM_CODE_TABLE[randomCodeIndex]);
            randomCodeNumber.append(numericValue);
        }
        randomNumber.append(randomCodeNumber);
        randomNumber.append(count);
        return randomNumber.substring(0,length);
    }

    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException {
//    	ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<>();
//    	AtomicInteger count = new AtomicInteger();
//    	for (int i = 0; i < 100; i++) {
//			new Thread(()->{
//				for (int j = 0; j < 100000; j++) {
//					String getRandomNumber = null;
//					try {
//						getRandomNumber = RandomNumberUtil.GetRandomNumber(32);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					if (map.contains(getRandomNumber)) {
//						System.out.println("--------------------------------------");
//						System.out.println(getRandomNumber);
//						System.out.println("--------------------------------------");
//						count.incrementAndGet();
//					}else {
//						map.put(getRandomNumber, 1);
//						System.out.println("======================================");
//						System.out.println(map.size());
//						System.out.println(count);
//						
//					}
//				}
//			}).start();
//		}
    	List<String> synchronizedList = Collections.synchronizedList(new ArrayList<String>(100000000));
    	List<Thread> threads = new ArrayList<Thread>();
    	for (int i = 0; i < 1000; i++) {
			Thread thread = new Thread(()->{
				for (int j = 0; j < 1000; j++) {
					String getRandomNumber = null;
					try {
						getRandomNumber = RandomNumberUtil.GetRandomNumber(18);
					} catch (Exception e) {
						e.printStackTrace();
					}
					synchronizedList.add(getRandomNumber);
					//System.out.println(synchronizedList.size());
				}
			});
			threads.add(thread);
		}
    	threads.forEach(t -> t.start());

    	while (synchronizedList.size() < 1000000) {
    		Thread.sleep(500);
            continue;			
		}
    	BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter("C:\\Users\\xwl\\Desktop\\18.txt"));
    	synchronizedList.forEach(v -> {
    		try {
				bufferedWriter.append(v).append("\r\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	bufferedWriter.flush();
    	bufferedWriter.close();
	}
}
