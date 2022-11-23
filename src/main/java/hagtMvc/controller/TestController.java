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
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\test.pdf");
        fileOutputStream.write(file);
        fileOutputStream.flush();
        fileOutputStream.close();
        Map<String,String> result = new HashMap<>();
        result.put("hagt","test");
        return result;
    }

}
