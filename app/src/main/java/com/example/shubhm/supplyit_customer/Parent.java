package com.example.shubhm.supplyit_customer;

import java.util.ArrayList;

/**
 * Created by Shubhm on 27-11-2016.
 */
public class Parent
{
    private String name;
    private String text1;
    private String text2;
    private String checkedtype;
    private boolean checked;

    private String text3;
    private String text4;

    // ArrayList to store Child objects
    private ArrayList<Child> Children;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public String getText1()
    {
        return text1;
    }

    public void setText1(String text1)
    {
        this.text1 = text1;
    }

    public String getText2()
    {
        return text2;
    }

    public void setText2(String text2)
    {
        this.text2 = text2;
    }
    public String getCheckedType()
    {
        return checkedtype;
    }

    public void setCheckedType(String checkedtype)
    {
        this.checkedtype = checkedtype;
    }


    public String getText3()
    {
        return text3;
    }

    public String getText4()
    {
        return text4;
    }

    public boolean isChecked()
    {
        return checked;
    }
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    // ArrayList to store Child objects
    public ArrayList<Child> getChildren()
    {
        return Children;
    }

    public void setChildren(ArrayList<Child> Children)
    {
        this.Children = Children;
    }
}