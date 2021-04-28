package hagtMvc.controller;

import com.hagt.core.annotation.Controller;
import com.hagt.core.annotation.MappingFunction;
import com.hagt.core.annotation.RequestBody;
import com.hagt.core.annotation.RequestParam;
import hagtMvc.entity.User;

import java.util.HashMap;
import java.util.Map;

@Controller(baseUrl = "test")
public class TestController {

    @MappingFunction(url = "/MVC")
    public Map<String,String> test
    (
        @RequestParam("HagtMvc") String v,
        @RequestParam("HagtMvc") String v2,
        @RequestBody("data.user") User user
    )
    {
        System.out.println(v);
        System.out.println(v2);
        System.out.println(user);
        Map<String,String> result = new HashMap<>();
        result.put("hagt","MVC666");
        return result;
    }

}
