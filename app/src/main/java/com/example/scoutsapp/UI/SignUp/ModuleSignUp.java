package com.example.scoutsapp.UI.SignUp;

public abstract class ModuleSignUp {
    public enum Error{
        SUCCESS,

        EMAIL_EMPTY,
        EMAIL_At,
        EMAIL_LONG,
        EMAIL_SHORT,
        EMAIL_DOT,

        PASSWORD_EMPTY,
        PASSWORD_LONG,
        PASSWORD_SHORT,
    }
    public static Error userSignUp(String email, String password)
    {
        int email_len = email.length();
        int password_len = password.length();

        if(email_len == 0)
            return Error.EMAIL_EMPTY;
        if(password_len == 0)
            return Error.PASSWORD_EMPTY;

        if(password_len > 16)
            return Error.PASSWORD_LONG;
        if (password_len < 8)
            return Error.PASSWORD_SHORT;

        String[] parts = email.split("@");
        if(parts.length != 2)
        {
            return Error.EMAIL_At;
        }

        String local_part = parts[0];
        int local_part_len = local_part.length();
        String domain = parts[1];
        int domain_len = domain.length();

        if(local_part_len > 64 || domain_len > 255)
            return Error.EMAIL_LONG;
        if(local_part_len < 2 || domain_len<2)
            return Error.EMAIL_SHORT;

        for (int i = 0; i <email_len; i++) {
            char c = email.charAt(i);

            if(c == '.')
            {
                if(i == 0 || i == (email_len -1) || email.charAt(i+1) == '.' || email.charAt(i-1) == '.')
                    return Error.EMAIL_DOT;
            }

            else if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c<= '9') ))
            {

            }
        }

        return Error.SUCCESS;
    }
}
