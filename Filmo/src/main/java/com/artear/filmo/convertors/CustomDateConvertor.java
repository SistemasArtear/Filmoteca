/**
 * 
 */
package com.artear.filmo.convertors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

/**
 * @author sisloc
 *
 */
public class CustomDateConvertor extends StrutsTypeConverter {

    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return (Date) sdf.parse(values[0]);
        } catch (ParseException e) {
            return values[0];
        }
    }

    @Override
    public String convertToString(Map context, Object o) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(o);
    }

}
