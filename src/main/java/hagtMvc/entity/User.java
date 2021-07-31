package hagtMvc.entity;

import com.hagt.uitl.SortUtil;

import java.math.BigDecimal;

public class User implements SortUtil.Bucket.BucketSortITF
{

    private String userName;
    private int age;

    public User(String userName,int age)
    {
        this.userName = userName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public BigDecimal SortV() {
        return new BigDecimal(this.age);
    }
}
