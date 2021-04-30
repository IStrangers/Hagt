package com.hagt.core.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hagt.core.enums.RequestContentType;
import com.hagt.core.enums.RequestType;
import com.hagt.core.iface.GetRequestParam;
import com.hagt.uitl.JudgeUtil;
import com.hagt.uitl.MapUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;
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
        if (this.requestContentType.startsWith(RequestContentType.MULTIPART_FORM_DATA.getTypeValue()))
        {
            ServletInputStream inputStream = null;
            try
            {
                inputStream = this.request.getInputStream();
                String boundary = "--" + this.requestContentType.split("=")[1];
                int contentLength = this.request.getContentLength();
                byte [] listByte = new byte[contentLength];
                int length = 0;
                int readLength = 0;
                int readCount = 0;
                byte b = -1;
                while ((b = (byte) inputStream.read()) != -1)
                {
                    String sssss = new String(listByte);
                    if (b == '\r')
                    {
                        String content = new String(listByte,readLength,length - readLength);
                        if (content.contains(boundary + "--"))
                        {
                            break;
                        }
                        if (content.startsWith(boundary))
                        {
                            readCount = 1;
                            readLength = length + 1 + readCount;
                        }
                        else if (content.contains("Content-Disposition:"))
                        {
                            String[] splitContent = content.split(";");
                            for (String attr : splitContent)
                            {
                                String[] splitAttr = attr.replaceAll("\"", "").trim().split("=");
                                if (JudgeUtil.isEmptyArray(splitAttr)) continue;
                                if (splitAttr[0].equals("name"))
                                {
                                    String name = splitAttr[1];
                                }
                                if (splitAttr[0].equals("filename"))
                                {
                                    String fileName = splitAttr[1];
                                }
                            }
                            readCount = 1;
                            readLength = length + 1 +readCount;
                        }
                        else if (content.contains("Content-Type:"))
                        {
                            String type = content.split(":")[1].trim();
                            readCount = 1;
                            readLength = length + readCount;
                        }
                        else
                        {
                            readCount = 2;
                            readLength = length + readCount;
                            if (JudgeUtil.isNotNull(content))
                            {

                            }
                        }
                    }
                    listByte[length] = b;
                    length++;
                    while (readCount > 0)
                    {
                        b = (byte) inputStream.read();
                        listByte[length] = b;
                        length++;
                        readCount--;
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (JudgeUtil.isNotNull(inputStream))
                {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
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
    public String getRequestContentType() {
        return this.requestContentType;
    }

}
