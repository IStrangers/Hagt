package hagtMvc.controller;

import com.alibaba.fastjson.JSON;
import com.hagt.core.annotation.*;
import com.hagt.uitl.RandomUtil;
import com.hagt.uitl.SortUtil;
import hagtMvc.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Controller(baseUrl = "test")
public class TestController {

    @MappingFunction(url = "/MVC")
    public Map<String,String> test
    (
        @RequestParam("HagtMvc") String v1,
        @RequestParam("HagtMvc") String[] v2,
        @RequestFile("file") byte[] file,
        @RequestBody("data.user") User user,
        HttpServletRequest request,
        HttpServletResponse response
    )
        throws IOException
    {
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\666.pdf");
        fileOutputStream.write(file);
        fileOutputStream.flush();
        fileOutputStream.close();
        Map<String,String> result = new HashMap<>();
        result.put("hagt","MVC666");
        return result;
    }

    @MappingFunction(url = "getRandomCode")
    public String getRandomCode(){
        String s = RandomUtil.GetRandomNumber();
        System.out.println(s);
        return s;
    }

    public static void main(String[] args) throws InterruptedException {
        long l = System.currentTimeMillis();
        int [] BubbleArray = new int[]{2,156,132,5,1,156,844,64,614,6541,6146,464651,64,64641,0,5616,31,31,65};
        int[] BubbleSort = SortUtil.Bubble.sort(BubbleArray);
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(JSON.toJSONString(BubbleSort));

        l = System.currentTimeMillis();
        int [] SelectionArray = new int[]{2,156,132,5,1,156,844,64,614,6541,6146,464651,64,64641,0,5616,31,31,65};
        int[] SelectionSort = SortUtil.Selection.sort(SelectionArray);
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(JSON.toJSONString(SelectionSort));

        l = System.currentTimeMillis();
        int [] InsertionArray = new int[]{2,156,132,5,1,156,844,64,614,6541,6146,464651,64,64641,0,5616,31,31,65};
        int[] InsertionSort = SortUtil.Insertion.sort(InsertionArray);
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(JSON.toJSONString(InsertionSort));

        l = System.currentTimeMillis();
        int [] ShellArray = new int[]{2,156,132,5,1,156,844,64,614,6541,6146,464651,64,64641,0,5616,31,31,65};
        int[] ShellSort = SortUtil.Shell.sort(ShellArray);
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(JSON.toJSONString(ShellSort));

        l = System.currentTimeMillis();
        int [] MergeArray = new int[]{2,156,132,5,1,156,844,64,614,6541,6146,464651,64,64641,0,5616,31,31,65};
        int[] MergeSort = SortUtil.Merge.sort(MergeArray);
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(JSON.toJSONString(MergeSort));

        l = System.currentTimeMillis();
        User[] users = SortUtil.Bucket.sort
        (
            new User[]
            {
                new User("忽悠我",888),
                new User("八万多",5),
                new User("大法官",55),
                new User("李亚军",45345),
                new User("十一届",666),
            },
            new BiFunction<User, User, Boolean>() {
                @Override
                public Boolean apply(User user, User user2) {
                    return SortUtil.fromSmallToLarge(user.getAge(),user2.getAge());
                }
            }
        );
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(JSON.toJSONString(users));
    }

}
