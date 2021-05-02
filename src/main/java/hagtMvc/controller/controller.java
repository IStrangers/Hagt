package hagtMvc.controller;

import com.hagt.core.annotation.Controller;
import com.hagt.core.annotation.MappingFunction;
import com.hagt.core.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller(baseUrl = "hello")
public class controller {

    @MappingFunction(url = "hagt")
    public Object helloHagt
    (
        @RequestParam("MVC666") String mvc666,
        HttpServletRequest request,
        HttpServletResponse response
    )
    {
        System.out.println(mvc666);
        System.out.println(request);
        System.out.println(response);
        return "Hello Hagt";
    }
}
