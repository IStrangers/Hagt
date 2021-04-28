package hagtMvc.controller;

import com.hagt.core.annotation.Controller;
import com.hagt.core.annotation.MappingFunction;
import com.hagt.core.annotation.RequestBody;
import com.hagt.core.annotation.RequestParam;
import hagtMvc.entity.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller(baseUrl = "test")
public class TestController {

    @MappingFunction(url = "/MVC")
    public Map<String,String> test
    (
        @RequestParam("HagtMvc") String v,
        @RequestParam("dddd") String v2,
        @RequestParam("file") String[] file,
        @RequestBody("data.user") User user
    )
        throws IOException
    {
        OutputStream outputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\" + file[1]);
        outputStream.write(file[0].getBytes());
        outputStream.flush();
        outputStream.close();
        Map<String,String> result = new HashMap<>();
        result.put("hagt","MVC666");
        return result;
    }

}
