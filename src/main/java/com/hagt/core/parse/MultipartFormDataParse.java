package com.hagt.core.parse;

import com.hagt.core.parse.model.MultipartFormData;
import com.hagt.uitl.JudgeUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

public class MultipartFormDataParse
{

    public static Map<String, MultipartFormData> parse(HttpServletRequest request)
    {
        Map<String, MultipartFormData> result = new HashMap<>();
        ServletInputStream inputStream = null;
        String requestContentType = request.getHeader("content-type");
        if (JudgeUtil.isNull(requestContentType))
        {
            requestContentType = request.getContentType();
        }
        try
        {
            inputStream = request.getInputStream();
            String boundary = "--" + requestContentType.split("=")[1];
            String endBoundary = boundary + "--";
            int contentLength = request.getContentLength();
            byte [] listByte = new byte[contentLength];
            byte b = -1;
            int length = 0;
            int readLength = 0;
            int readCount = 0;
            int startDataLength = 0;
            int endDataLength = 0;
            String name = null;
            String fileName = null;
            String type = null;
            List<Byte> data = new ArrayList<>();
            while (length <= contentLength)
            {
                b = (byte) inputStream.read();
                if (b == '\r')
                {
                    String content = new String(listByte,readLength,length - readLength);
                    if (content.contains(boundary))
                    {
                        if (JudgeUtil.isNotNull(name))
                        {
                            if (content.contains(endBoundary))
                            {
                                endDataLength = length - endBoundary.length() - 2;
                            }
                            else
                            {
                                endDataLength = length - boundary.length() - 2;
                            }
                            byte[] copyOfRangeData = Arrays.copyOfRange(listByte, startDataLength, endDataLength);
                            MultipartFormData multipartFormData = new MultipartFormData(name, fileName, type, copyOfRangeData);
                            result.put(multipartFormData.getName(),multipartFormData);
                            startDataLength = 0;
                            endDataLength = 0;
                            name = null;
                            fileName = null;
                            type = null;
                            data = new ArrayList<>();
                        }
                        if (content.contains(endBoundary))
                        {
                            break;
                        }
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
                                name = splitAttr[1];
                            }
                            if (splitAttr[0].equals("filename"))
                            {
                                fileName = splitAttr[1];
                            }
                        }
                        readCount = 1;
                        readLength = length + 1 +readCount;
                    }
                    else if (content.contains("Content-Type:"))
                    {
                        type = content.split(":")[1].trim();
                        readCount = 1;
                        readLength = length + 1 +readCount;
                    }
                    else
                    {
                        readCount = 2;
                        readLength = length + readCount;
                        if (JudgeUtil.isNull(content) && startDataLength == 0)
                        {
                            startDataLength = readLength;
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
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return result;
        }
    }
}
