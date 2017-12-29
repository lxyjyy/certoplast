package com.lab.certoplast.parser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public abstract class BaseParser<T> {

	public abstract T parse(String paramString) throws IOException, XmlPullParserException;
}
