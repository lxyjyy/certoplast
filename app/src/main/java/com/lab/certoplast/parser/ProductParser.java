package com.lab.certoplast.parser;

import com.google.gson.JsonParseException;
import com.lab.certoplast.bean.Product;

/**
 * Created by lxyjyy on 17/11/12.
 * 成品入库
 */

public class ProductParser extends BaseParser<Product> {

    @Override
    public Product parse(String paramString) throws JsonParseException{

//        if (paramString != null)
//        {
//            String result = StringUtils.xmlParser(paramString);
//            Type founderListType = new TypeToken<ArrayList<Product>>(){}.getType();
//
//            List<Product> list = new Gson().fromJson(result, founderListType);
//
//            if (list.size() > 0){
//                return list.get(0);
//            }
//        }

        return null;

    }
}
