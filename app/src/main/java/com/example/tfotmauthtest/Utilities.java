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

        boolean check = true;
//        if(user.charAt(0)>='A'&&user.charAt(0)<='Z'||user.charAt(0)>='a'&&user.charAt(0)<='z')
//            for(int i=1; i<user.length();i++)
//            {
//
//
//
//                if(user.charAt(i)<='A'||user.charAt(i)>='Z'){
//                    check = false;
//                }
//                if(user.charAt(i)<='a'||user.charAt(i)>='z'){
//                    check = false;
//                }
//
//            }
        for(int i=0; i<user.length();i++) {
            if (user.charAt(i) == ' ')
                check = false;
        }
        return check;
    }


    public boolean correctpass(String password)
    {
        boolean check = true;
        int count=0;
        if(!(password.length()>=8&&password.length()<=30)){
            check = false;
        }
//        for(int i=0; i< password.length();i++)
//        {
//
//            if(!(password.charAt(i)>='A'&&password.charAt(i)<='Z')){
//                check = false;
//
//            }
//            if(!(password.charAt(i)>='a'&&password.charAt(i)<='z')){
//                check = false;
//
//            }
//            if(!(password.charAt(i)>='1'&&password.charAt(i)<='9'))
//            {
//                check = false;
//
//            }
//        }

        return check;


    }
}
