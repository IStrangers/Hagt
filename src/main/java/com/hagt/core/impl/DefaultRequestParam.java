package com.hagt.core.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hagt.core.enums.RequestContentType;
import com.hagt.core.enums.RequestType;
import com.hagt.core.iface.GetRequestParam;
import com.hagt.uitl.JudgeUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class DefaultRequestParam implements GetRequestParam {

    private HttpServletRequest request;
    private String header;
    private String rowData;
    JSONObject rowDataJSON;
    private Map<String, String[]> parameterMap;

    public DefaultRequestParam(HttpServletRequest request){
        this.header = request.getHeader("content-type");
        this.request = request;
        init();
    }

    private void init()
    {
        this.parameterMap = this.request.getParameterMap();
        if (RequestType.POST == RequestType.valueOf(request.getMethod()))
        {
            this.rowData = getPostData();
        }
        if (RequestContentType.MULTIPART_FORM_DATA.getTypeValue().equals(this.header))
        {
            String data = this.rowData;
        }
        if (RequestContentType.APPLICATION_JSON.getTypeValue().equals(this.header))
        {
            this.rowDataJSON = JSON.parseObject(this.rowData);
        }
    }

    private String getPostData()
    {
        BufferedReader br = null;
        try
        {
            StringBuilder ret;
            br = this.request.getReader();

            String line = br.readLine();
            if (line != null)
            {
                ret = new StringBuilder();
                ret.append(line);
            }
            else
            {
                return "";
            }

            while ((line = br.readLine()) != null)
            {
                ret.append('\n').append(line);
            }
            return ret.toString();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getRawData(String name)
    {
        if (JudgeUtil.isNull(name))
        {
            return this.rowData;
        }
        else
        {
            String[] indexPaths = name.split("\\.");
            JSONObject result = null;
            for (String indexPath : indexPaths)
            {
                if (JudgeUtil.isNull(result))
                {
                    result = this.rowDataJSON.getJSONObject(indexPath);
                }
                else
                {
                    result = result.getJSONObject(indexPath);
                }
            }
            return JudgeUtil.isNull(result) ? null : result.toJSONString();
        }
    }

    @Override
    public String getParam(String name)
    {
        String[] v = this.parameterMap.get(name);
        if (JudgeUtil.isEmptyArray(v))
        {
            return null;
        }
        else{
            return v[0];
        }
    }

    @Override
    public String[] getParams(String name) {
        String[] v = this.parameterMap.get(name);
        if (JudgeUtil.isEmptyArray(v))
        {
            return null;
        }
        else{
            return v;
        }
    }

    @Override
    public String getHeader() {
        return this.header;
    }

}
