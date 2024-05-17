package net.nevowolfman.mipo.UI.SignUp;

import android.content.Context;

import net.nevowolfman.mipo.Repository.Repository;
import net.nevowolfman.mipo.Repository.UserModel;

public class ModuleSignUp {

    private Context context;

    public ModuleSignUp(Context context) {
        this.context = context;
    }

    public enum Error{
        SUCCESS,

        EMAIL_EMPTY,
        EMAIL_At,
        EMAIL_LONG,
        EMAIL_SHORT,
        EMAIL_DOT,
        EMAIL_INVALID,

        PASSWORD_EMPTY,
        PASSWORD_LONG,
        PASSWORD_SHORT,

        EMAIL_ALREADY,
        DB_FAILED
    }
    public Error userSignUp(String email, String password)
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




        // email validity checkups
        if(email.indexOf("@") <= 1)
        {
            return Error.EMAIL_At;
        }
        if(email.indexOf("@") != email.lastIndexOf("@"))
        {
            return Error.EMAIL_At;
        }
        if(email.indexOf(".") - email.indexOf("@") <= 3)
        {
            return Error.EMAIL_DOT;
        }
        if(email.indexOf(".") != email.lastIndexOf("."))
        {
            return Error.EMAIL_DOT;
        }

//        String[] parts = email.split("@");
//        if(parts.length != 2)
//        {
//            return Error.EMAIL_At;
//        }
//
//        String local_part = parts[0];
//        int local_part_len = local_part.length();
//        String domain = parts[1];
//        int domain_len = domain.length();
//
//        if(local_part_len > 64 || domain_len > 255)
//            return Error.EMAIL_LONG;
//        if(local_part_len < 2 || domain_len<2)
//            return Error.EMAIL_SHORT;
//
//        for (int i = 0; i <email_len; i++) {
//            char c = email.charAt(i);
//
//            if(c == '.')
//            {
//                if(i == 0 || i == (email_len -1) || email.charAt(i+1) == '.' || email.charAt(i-1) == '.')
//                    return Error.EMAIL_DOT;
//            }
//
////            else if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c<= '9') ))
////            {
////                return Error.EMAIL_INVALID;
////            }
//        }


        Repository repository = new Repository(context);
        UserModel userModel = repository.getUserByEmail(email);
        if(userModel != null)
        {
            return Error.EMAIL_ALREADY;
        }

        long id = repository.addUser(new UserModel(email, password));
        if(id == -1)
        {
            return Error.DB_FAILED;
        }

        return Error.SUCCESS;
    }
}
