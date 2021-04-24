package hagtMvc.controller;

import com.hagt.core.annotation.Controller;
import com.hagt.core.annotation.MappingFunction;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    @MappingFunction
    public Map<String,String> test(){
        Map<String,String> result = new HashMap<>();
        result.put("hagt","MVC666");
        return result;
    }

}