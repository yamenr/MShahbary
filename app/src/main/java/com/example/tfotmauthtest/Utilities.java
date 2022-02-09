package com.example.tfotmauthtest;

public class Utilities {
    private static Utilities instance;

    public Utilities()
    {

    }

    public static Utilities getInstance()      // checks if utilities exists if not it creates
    {
        if (instance==null)
            instance =new Utilities();
        return instance;


    }
    public boolean correctuser(String user)
    {


        if(user.charAt(0)>='A'&&user.charAt(0)<='Z'||user.charAt(0)>='a'&&user.charAt(0)<='z')
            for(int i=1; i<user.length();i++)
            {



                if(user.charAt(i)<='A'||user.charAt(i)>='Z')
                    if(user.charAt(i)!='.'&&user.charAt(i)!='_')
                        return false;

            }
        for(int i=0; i<user.length();i++) {
            if (user.charAt(i) == ' ')
                return false;
        }
        return true;
    }


    public boolean correctpass(String password)
    {
        int count=0;
        if(!(password.length()>=8&&password.length()<=30))
            return false;
        for(int i=0; i< password.length();i++)
        {

            if(!(password.charAt(i)>='A'&&password.charAt(i)<='Z'))
                if(!(password.charAt(i)>='a'&&password.charAt(i)<='z'))
                    if(!(password.charAt(i)>='1'&&password.charAt(i)<='9'))
                        return false;

        }

        return true;


    }
}
