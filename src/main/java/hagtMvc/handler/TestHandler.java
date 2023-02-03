package hagtMvc.handler;

import com.hagt.core.Handing;
import com.hagt.uitl.JudgeUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestHandler extends Handing {

    @Override
    public void handing(String requestPath, HttpServletRequest req, HttpServletResponse res, FilterChain chain)
    {
        if (JudgeUtil.isNull(requestPath))
        {
            return;
        }
        nextHanding.handing(requestPath,req,res,chain);
    }

}
