package com.hagt.core.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hagt.core.enums.RequestContentType;
import com.hagt.core.enums.RequestType;
import com.hagt.core.iface.GetRequestParam;
import com.hagt.uitl.JudgeUtil;
import com.hagt.uitl.MapUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class DefaultRequestParam implements GetRequestParam {

    private HttpServletRequest request;
    private String requestContentType;
    private String rowData;
    JSONObject rowDataJSON;
    private Map<String, String[]> parameterMap;

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
        if (RequestType.POST == RequestType.valueOf(request.getMethod()))
        {
            this.rowData = getPostData();
        }
        if (this.requestContentType.startsWith(RequestContentType.MULTIPART_FORM_DATA.getTypeValue()))
        {
            String boundary = "--" + this.requestContentType.split("=")[1];
            String[] bodyArray = this.rowData.split("\n");
            String currentLine = null;
            String name = null;
            StringBuilder content = new StringBuilder();
            String fileName = null;
            String type = null;
            for (String body : bodyArray)
            {
                if (JudgeUtil.isNull(body)) continue;
                currentLine = body;
                if (currentLine.startsWith(boundary))
                {
                    if (JudgeUtil.isNull(name)) continue;
                    parameterMap.put(name,new String[]{content.toString(),fileName,type});
                    content.setLength(0);
                    continue;
                }
                else
                {
                    if (currentLine.startsWith("Content-Disposition: form-data;"))
                    {
                        String[] contentDisposition = currentLine.split(";");

                        for (String attr : contentDisposition)
                        {
                            String[] splitAttr = attr.replaceAll("\"", "").trim().split("=");
                            if (JudgeUtil.isEmptyArray(splitAttr)) continue;
                            if (splitAttr[0].equals("name"))
                            {
                                name = splitAttr[1];
                            }
                            if (splitAttr[0].equals("filename"))
                            {
                                fileName = splitAttr[1];
                            }
                        }
                    }
                    else if (currentLine.startsWith("Content-Type:"))
                    {
                        type = currentLine.split(":")[1].trim();
                    }
                    else
                    {
                        content.append(currentLine);
                    }
                }
            }
        }
        if (RequestContentType.APPLICATION_JSON.getTypeValue().equals(this.requestContentType))
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
    public String getRequestContentType() {
        return this.requestContentType;
    }

}
