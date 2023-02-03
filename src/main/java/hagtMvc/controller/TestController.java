package hagtMvc.controller;

import com.hagt.core.annotation.*;
import hagtMvc.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller(baseUrl = "test")
public class TestController {

    @MappingFunction(url = "/getData")
    public Map<String,String> test
    (
        @RequestParam("param") String v1,
        @RequestParam("param") String[] v2,
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
        result.put("data","test");
        return result;
    }

}
