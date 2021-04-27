package hagtMvc.controller;

import com.hagt.core.annotation.Controller;
import com.hagt.core.annotation.MappingFunction;
import com.hagt.core.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller(baseUrl = "test")
public class TestController {

    @MappingFunction(url = "/MVC")
    public Map<String,String> test(@RequestParam("HagtMvc") String v, Map<String,String[]> params){
        System.out.println(v);
        Map<String,String> result = new HashMap<>();
        result.put("hagt","MVC666");
        System.out.println(result);
        return result;
    }

}
