package ecoo.convert;

import ecoo.data.ChamberUserListRow;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public class ObjectArrayToChamberUserListRowConverter implements Converter<Object[], ChamberUserListRow> {

    @Override
    public ChamberUserListRow convert(Object[] objects) {
        return new ChamberUserListRow(Integer.parseInt(objects[0].toString())
                , Integer.parseInt(objects[1].toString())
                , strip(objects[2])
                , strip(objects[3])
                , strip(objects[4])
                , strip(objects[5])
                , strip(objects[6])
                , strip(objects[7])
                , strip(objects[8])
                , Boolean.parseBoolean(objects[9].toString())
                , (Date) objects[10]
                , (Date) objects[11]
                , new Date().before((Date) objects[11])
        );
    }

    private String strip(Object word) {
        if (word == null) return null;
        return StringUtils.stripToNull(word.toString());
    }
}
