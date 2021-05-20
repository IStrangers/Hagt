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
        System.out.println(l);
        int [] BubbleArray = new int[]{2,156,132,5,1,156,844,64,614,6541,6146,464651,64,64641,0,5616,31,31,65};
        int[] BubbleSort = SortUtil.Bubble.sort(BubbleArray);
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(JSON.toJSONString(BubbleSort));

        l = System.currentTimeMillis();
        System.out.println(l);
        int [] SelectionArray = new int[]{2,156,132,5,1,156,844,64,614,6541,6146,464651,64,64641,0,5616,31,31,65};
        int[] SelectionSort = SortUtil.Selection.sort(SelectionArray);
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(JSON.toJSONString(SelectionSort));

        l = System.currentTimeMillis();
        System.out.println(l);
        int [] InsertionArray = new int[]{2,156,132,5,1,156,844,64,614,6541,6146,464651,64,64641,0,5616,31,31,65};
        int[] InsertionSort = SortUtil.Insertion.sort(InsertionArray);
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(JSON.toJSONString(InsertionSort));

        l = System.currentTimeMillis();
        System.out.println(l);
        int [] ShellArray = new int[]{2,156,132,5,1,156,844,64,614,6541,6146,464651,64,64641,0,5616,31,31,65};
        int[] ShellSort = SortUtil.Shell.sort(ShellArray);
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(JSON.toJSONString(ShellSort));

    }

}
