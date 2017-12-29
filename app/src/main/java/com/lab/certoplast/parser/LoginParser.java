package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.User;
import com.lab.certoplast.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/7.
 */

public class LoginParser extends BaseParser<User> {
    @Override
    public User parse(String paramString) throws IOException, XmlPullParserException {

        if (paramString != null)
        {
            String param = StringUtils.xmlParser(paramString);

            Type founderListType = new TypeToken<ArrayList<User>>(){}.getType();

            List<User> userList = new Gson().fromJson(param, founderListType);
            if (userList.size() > 0){
                return userList.get(0);
            }

        }

        return null;
    }
}
