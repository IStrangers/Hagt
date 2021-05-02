package com.hagt.core.parse.model;

import com.hagt.uitl.JudgeUtil;

public class MultipartFormData
{

    public MultipartFormData(String name,String fileName,String type,byte[] data)
    {
        this.name = name;
        this.fileName = fileName;
        this.type = type;
        this.data = data;
    }

    private String name;
    private String fileName;
    private String type;
    private byte[] data;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }

    public boolean isFile()
    {
        return JudgeUtil.isNotNull(fileName);
    }
}