package com.hagt.core.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hagt.core.enums.RequestContentType;
import com.hagt.core.enums.RequestType;
import com.hagt.core.iface.GetRequestParam;
import com.hagt.core.parse.MultipartFormDataParse;
import com.hagt.core.parse.model.MultipartFormData;
import com.hagt.uitl.JudgeUtil;
import com.hagt.uitl.MapUtil;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DefaultRequestParam implements GetRequestParam {

    private HttpServletRequest request;
    private String requestContentType;
    private String rowData;
    JSONObject rowDataJSON;
    private Map<String, String[]> parameterMap;
    private Map<String, byte[]> binaryParameterMap = new HashMap<>();

    public DefaultRequestParam(HttpServletRequest request){
        this.requestContentType = request.getHeader("content-type");
        if (JudgeUtil.isNull(this.requestContentType))
        {
            this.requestContentType = request.getContentType();
        }
        this.request = request;
        init();
    }

    private void init()
    {
        this.parameterMap = MapUtil.copyMap(this.request.getParameterMap());
        if (JudgeUtil.isNotNull(this.requestContentType) && this.requestContentType.startsWith(RequestContentType.MULTIPART_FORM_DATA.getTypeValue()))
        {
            initMultipartFormData();
        }
        else if (RequestType.POST == RequestType.valueOf(request.getMethod()))
        {
            this.rowData = getPostData();
        }
        if (RequestContentType.APPLICATION_JSON.getTypeValue().equals(this.requestContentType))
        {
            this.rowDataJSON = JSON.parseObject(this.rowData);
        }
    }

    private void initMultipartFormData() {
        Map<String, MultipartFormData> multipartFormData = MultipartFormDataParse.parse(this.request);
        for (MultipartFormData m : multipartFormData.values())
        {
            if (m.isFile())
            {
                binaryParameterMap.put(m.getName(),m.getData());
            }
            else {
                parameterMap.put(m.getName(),new String[]{new String(m.getData())});
            }
        }
    }

    private String getPostData()
    {
        BufferedReader br = null;
        try
        {
            StringBuilder ret;
            br = new BufferedReader(new InputStreamReader(this.request.getInputStream(),"UTF-8"));

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
            if (JudgeUtil.isNull(this.rowDataJSON)) return null;
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
    public byte[] getBinaryParams(String name) {
        byte[] v = this.binaryParameterMap.get(name);
        if (JudgeUtil.isEmptyArray(v))
        {
            return null;
        }
        else{
            return v;
        }
    }

    @Override
    public String getRequestContentType() {
        return this.requestContentType;
    }

}
