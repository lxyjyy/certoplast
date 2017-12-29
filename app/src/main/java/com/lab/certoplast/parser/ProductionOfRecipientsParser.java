package com.lab.certoplast.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lab.certoplast.bean.ProductionOfRecipients;
import com.lab.certoplast.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxyjyy on 17/11/12.
 * 生产领用转换对象
 */

public class ProductionOfRecipientsParser extends BaseParser<List<ProductionOfRecipients>> {

    @Override
    public List<ProductionOfRecipients> parse(String paramString) throws IOException, XmlPullParserException {

        if (paramString != null)
        {
            String result = StringUtils.xmlParser(paramString);
            Type founderListType = new TypeToken<ArrayList<ProductionOfRecipients>>(){}.getType();

            List<ProductionOfRecipients> list = new Gson().fromJson(result, founderListType);

            if (list.size() > 0){
                return list;
            }
        }

        return null;

    }
}
