package ecoo.data.upload;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public abstract class RequiredField<M> {

	private interface FieldParser {
		  String getAsStringForDatabase(String fieldValue) throws ParseException;

		  String getAsStringForView(String fieldValue);
	}

	// A basic implementation of field parser, it will return value as is.
	private static class BasicFieldParser implements FieldParser {

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.co.aforbes.fpc.db.model.bulkimport.RequiredField.FieldParser#getAsStringForDatabase (java.lang.String)
		 */
		@Override
		public final String getAsStringForDatabase(String fieldValue) throws ParseException {
			return fieldValue;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.co.aforbes.fpc.db.model.bulkimport.RequiredField.FieldParser#getAsStringForView(java .lang.String)
		 */
		@Override
		public final String getAsStringForView(String fieldValue) {
			return fieldValue;
		}
	}

	// The DateFieldParser will take a date in one format and convert it to a different format.
	private static class DateFieldParser implements FieldParser {

		/** The formatter used to determine if the date is in the correct format. */
		private DateFormat fileFormat;

		/** The formatter used to convert the date from file into the database date format. */
		private DateFormat uploadFormat;

		private DateFieldParser(String fileFormat, String uploadFormat) {
			this.fileFormat = new SimpleDateFormat(fileFormat);
			this.uploadFormat = new SimpleDateFormat(uploadFormat);
		}

		private DateFormat getViewFormat() {
			return fileFormat;
		}

		private DateFormat getDatabaseFormat() {
			return uploadFormat;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.co.aforbes.fpc.db.model.bulkimport.RequiredField.FieldParser#getAsStringForDatabase (java.lang.String)
		 */
		@Override
		public final String getAsStringForDatabase(String fieldValue) throws ParseException {
			if(StringUtils.isEmpty(fieldValue)) {
				throw new ParseException("Parsing failed", 0);
			}

			Date dateValue ;
			try {
				fieldValue = fieldValue.replace("-", "/");
				fieldValue = fieldValue.replace("\"", "");
				fieldValue = fieldValue.replace(" ", "");

				dateValue = getViewFormat().parse(fieldValue);
				// Because of some quirk in the formater a date in format dd/MM/yyyy can be parsed
				// to yyyy-MM-dd, but incorrecly, so this test will ensure that doesn't happen.
				if(!getViewFormat().format(dateValue).equals(fieldValue)) {
					throw new ParseException("Parsing failed", 0);
				}

			} catch(ParseException pe) {
				dateValue = getDatabaseFormat().parse(fieldValue);
			}
			return getDatabaseFormat().format(dateValue);
		}

		@Override
		public final String getAsStringForView(String fieldValue) {
			if(StringUtils.isEmpty(fieldValue)) {
				return null;
			}

			Date dateValue ;
			try {
				dateValue = getDatabaseFormat().parse(fieldValue);

			} catch(ParseException pe) {
				return fieldValue;
			}
			return getViewFormat().format(dateValue);
		}
	}

	// Parser used to format field with numeric value.
	private static class NumberParser implements FieldParser {

		private NumberFormat numberFormat = null;

		 NumberParser() {
			numberFormat = NumberFormat.getNumberInstance();
			numberFormat.setParseIntegerOnly(false);
			numberFormat.setMaximumFractionDigits(5);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.co.aforbes.fpc.db.model.bulkimport.RequiredField.FieldParser#getAsStringForDatabase (java.lang.String)
		 */
		@Override
		public final String getAsStringForDatabase(String fieldValue) throws ParseException {
			if(StringUtils.isEmpty(fieldValue)) {
				throw new ParseException("Parsing failed", 0);
			}

			// Clean the value.
			final StringBuilder buffer = new StringBuilder();
			int index = 0;
			for(final char c : fieldValue.toCharArray()) {
				if(Character.isWhitespace(c)) {
					continue;
				}
				if(index == 0 && c == '-') {
					index++;
					buffer.append(c);
				} else {
					if(c == '.') {
						buffer.append(c);

					} else if(c == '-') {
						buffer.append(".");

					} else if(Character.isDigit(c)) {
						buffer.append(c);
					}
				}
			}

			Number number ;
			try {
				number = numberFormat.parse(buffer.toString());

			} catch(NumberFormatException nfe) {
				throw new ParseException("Parsing failed", 0);
			}
			return number.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.co.aforbes.fpc.db.model.bulkimport.RequiredField.FieldParser#getAsStringForView(java .lang.String)
		 */
		@Override
		public final String getAsStringForView(String fieldValue) {
			return fieldValue;
		}
	}

	@SuppressWarnings("unused")
	public enum FieldType {

		Basic(new BasicFieldParser()), DefaultDate(new DateFieldParser("dd/MM/yyyy", "yyyy-MM-dd")), Numeric(
				new NumberParser());

		private FieldParser fieldParser;

		 FieldType(FieldParser fieldParser) {
			this.fieldParser = fieldParser;
		}

		// Return field parser used
		private FieldParser getFieldParser() {
			return fieldParser;
		}


		private String parse(String value) throws ParseException {
			return getFieldParser().getAsStringForDatabase(value);
		}

		private String getValue(String value) {
			return getFieldParser().getAsStringForView(value);
		}
	}

	// The name of field
	private String name;

	// The order that this field must be eported.
	private int exportOrder;

	// The field type of this required field.
	private FieldType fieldType;

	// Private default constructor.
	protected RequiredField() {}

	public RequiredField(String name, int exportOrder) {
		this(name, exportOrder, FieldType.Basic);
	}

	  RequiredField(String name, int exportOrder, FieldType fieldType) {
		this.name = name;
		this.exportOrder = exportOrder;
		this.fieldType = fieldType;
	}

	public final String getValueAsString(M data) {
		return fieldType.getValue(getFieldValueIn(data));
	}

	public final String parse(String value) throws ParseException {
		return fieldType.parse(value);
	}

	protected abstract String getFieldValueIn(M data);

	public abstract void setFieldValueFor(M data, String newValue);

	public final String getName() {
		return name;
	}

	public final int getExportOrder() {
		return exportOrder;
	}
}
