package pgtest.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mvel2.MVEL;
import org.mvel2.templates.TemplateRuntime;
import org.springframework.security.crypto.codec.Hex;


import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.text.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.io.FileUtils.writeStringToFile;

public class Utilities {



    public static final char ARRAY_DELIMITER_CHAR = ',';
    public static final char FILE_DELIMITER_CHAR = ',';
    public static final String ARRAY_DELIMITER = ",";
    public static final TimeZone UTC_TIMEZONE = TimeZone.getTimeZone("UTC");
    private static final Logger log = Logger.getLogger(Utilities.class);
    private static final ThreadLocal<DateFormat> INTERNAL_CONVERSION_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"); // Used only for converting dates from UTC to Client and vice versa. Do not change.
        }
    };
    private static final ThreadLocal<DateFormat> INTERNAL_CONVERSION_DATE_FORMAT_UTC = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"); // Used only for converting dates from UTC to Client and vice versa. Do not change.
            df.setTimeZone(UTC_TIMEZONE);
            return df;
        }
    };

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final String CURRENCY_FORMAT = "0.00";

    public static boolean equal(Object o1, Object o2) {
        if (o1 != null) {
            return o1.equals(o2);
        }

        if (o2 != null) {
            return o2.equals(o1);
        }

        return true;
    }

    public static String md5Hash(String s) {
        try {
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            byte[] digest = md5Digest.digest(s.getBytes("UTF-8"));
            String hashValue = new String((Hex.encode(digest)));
            return hashValue;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateRandomPassword(int noOfChars) {
        char[] pw = new char[noOfChars];
        int c = 'A';
        int r1 = 0;
        for (int i = 0; i < noOfChars; i++) {
            r1 = (int) (Math.random() * 3);
            switch (r1) {
                case 0:
                    c = '0' + (int) (Math.random() * 10);
                    break;
                case 1:
                    c = 'a' + (int) (Math.random() * 26);
                    break;
                case 2:
                    c = 'A' + (int) (Math.random() * 26);
                    break;
            }
            pw[i] = (char) c;
        }
        return new String(pw);
    }

    public static boolean toLongText(String text, int length) {
        return text.length() > length;
    }

    public static boolean isNumber(String value, int digits) {
        return value != null && Pattern.compile("^[0-9]{" + digits + "}").matcher(value).find();
    }

    public static boolean isLetter(String text) {
        return text != null && Pattern.compile("[a-zA-Z]+").matcher(text).matches();
    }

    public static boolean isNumber(String value) {
        return value != null && Pattern.compile("[0-9]+").matcher(value).matches();
    }

    public static boolean isNegativeNumber(Integer number) {
        return number < 0;
    }

    public static boolean isPositiveNumber(Integer number) {
        return number > 0;
    }

    public static boolean matchNumber(String value) {
        return Pattern.compile("[0-9]+").matcher(value).matches();
    }

    public static boolean hasCharactersOnly(String characters) {
        return characters != null && Pattern.compile("[a-zA-Z]+").matcher(characters).matches();
    }

    public static boolean isAlphanumeric(String input) {
        return input != null && Pattern.compile("[a-zA-Z0-9]+").matcher(input).matches();
    }

    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof IsEmpty) {
            return ((IsEmpty) value).isEmpty();
        } else if (value instanceof String) {
            return (((String) value).trim().length() == 0);
        } else if (value instanceof Map) {
            return ((Map) value).isEmpty();
        } else if (value instanceof Collection) {
            return ((Collection) value).isEmpty();
        } else if (value instanceof Map) {
            return ((Map) value).isEmpty();
        } else return value.getClass().isArray() && (Array.getLength(value) == 0);
    }

    public static boolean isListEmpty(List<? extends IsEmpty> list) {
        if (list == null) {
            return true;
        }
        if (list.isEmpty()) {
            return true;
        }
        for (IsEmpty el : list) {
            if (!el.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static int size(List<?> list) {
        return list == null ? 0 : list.size();
    }

    public static boolean IsArrayFull(Object[] array) {
        if (array == null) return false;
        for (Object o : array) {
            if (o == null) return false;
        }
        return true;
    }

    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }

    public static String formatDate(Date value) {
        if (value == null) {
            return null;
        }
        return WebUtils.getDefaultDateFormat().format(value);
    }

    public static String formatDateHourMin(Date value) {
        if (value == null) {
            return null;
        }
        return WebUtils.getDefaultDateTimeFormat().format(value);
    }



    public static String formatDateToIsoUTC(Date value) {
        if (value == null) {
            return null;
        }
        return WebUtils.getIsoDateFormat().format(value);
    }

    public static String formatDateWithPattern(Date value, String pattern) {
        if (value == null) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(value);
    }

    public static String formatDateWithPatternToUTC(Date value, String pattern) {
        if (value == null) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setTimeZone(Utilities.UTC_TIMEZONE);
        return formatter.format(value);
    }

    /**
     * Converts a date that is actually in UTC but has the client timezone instead of the UTC timezone into the correct date in the client timezone.
     * This is used because Oracle's STRUCT ignores timezones.
     *
     * @param d
     * @return
     */
    public static Date convertDateFromUtcWithWrongTimezoneToClient(Date d) {
        try {
            String s = INTERNAL_CONVERSION_DATE_FORMAT.get().format(d);
            return INTERNAL_CONVERSION_DATE_FORMAT_UTC.get().parse(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Date convertDateFromClientToUtcWithWrongTimezone(Date d) {
        try {
            String s = INTERNAL_CONVERSION_DATE_FORMAT_UTC.get().format(d);
            return INTERNAL_CONVERSION_DATE_FORMAT.get().parse(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Date parseDate(String dateStr) throws ParseException {
        return WebUtils.getDefaultDateFormat().parse(dateStr);
    }

    public static Date parseDateHourMin(String dateStr) throws ParseException {
        return WebUtils.getDefaultDateTimeFormat().parse(dateStr);
    }

    public static Date parseDateIso(String dateStr) throws ParseException {
        return WebUtils.getIsoDateFormat().parse(dateStr);
    }

    public static final String getFontFilePath() {
        return "/WEB-INF/fonts/arial.ttf";
    }

    public static Date getBeginningOfDay(Date date) {
        if (date == null) {
            return null;
        }
        return new Date(new DateTime(date).toDateMidnight().getMillis());
    }

    public static Date getEndOfDay(Date date) {
        if (date == null) {
            return null;
        }
        return new Date(new DateTime(date).toDateMidnight().plusDays(1).getMillis() - 1);
    }

    public static Integer[] toIntArray(String inStr, char splitChar) {
        if (isEmpty(inStr))
            return new Integer[0];
        else {
            String[] strArray = StringUtils.split(inStr, splitChar);
            Integer[] intArray = new Integer[strArray.length];
            for (int i = 0; i < strArray.length; i++) {
                if("null".equals(strArray[i]))
                    intArray[i] = null;
                else
                    intArray[i] = Integer.parseInt(strArray[i]);
            }
            return intArray;
        }
    }

    public static Integer[] toIntArray(String inStr) {
        return toIntArray(inStr, ARRAY_DELIMITER_CHAR);
    }

    public static Long[] toLongArray(String inStr, char splitChar) {
        if (isEmpty(inStr))
            return null;
        else {
            String[] strArray = StringUtils.split(inStr, splitChar);
            Long[] longArray = new Long[strArray.length];
            for (int i = 0; i < strArray.length; i++) {
                if("null".equals(strArray[i]))
                    longArray[i] = null;
                else
                    longArray[i] = Long.parseLong(strArray[i]);
            }
            return longArray;
        }
    }

    public static Long[] toLongArray(String inStr) {
        return toLongArray(inStr, ARRAY_DELIMITER_CHAR);
    }

    public static List<String> toList(String inStr, char splitChar) {
        if (isEmpty(inStr))
            return new ArrayList<String>();
        else {
            String[] strArray = StringUtils.split(inStr, splitChar);
            List<String> intList = new ArrayList<String>();
            int i = 0;
            while (i < strArray.length) {
                if("null".equals(strArray[i]))
                    intList.add(null);
                else
                    intList.add(strArray[i]);

                i++;
            }
            return intList;
        }
    }

    public static List<Long> toLongList(String inStr, char splitChar) {
        if (isEmpty(inStr))
            return new ArrayList<Long>();
        else {
            String[] strArray = StringUtils.split(inStr, splitChar);
            List<Long> longList = new ArrayList<Long>();
            int i = 0;
            while (i < strArray.length) {
                if("null".equals(strArray[i]))
                    longList.add(null);
                else
                    longList.add(Long.valueOf(strArray[i]));

                i++;
            }
            return longList;
        }
    }

    public static List<Boolean> toBooleanList(String inStr, char splitChar) {
        if (isEmpty(inStr))
            return new ArrayList<Boolean>();
        else {
            String[] strArray = StringUtils.split(inStr, splitChar);
            List<Boolean> boolList = new ArrayList<Boolean>();
            int i = 0;
            while (i < strArray.length) {
                if("null".equals(strArray[i]))
                    boolList.add(null);
                else
                    boolList.add(Boolean.parseBoolean(strArray[i]));

                i++;
            }
            return boolList;
        }
    }

    public static List<Date> toDateList(String inStr, char splitChar) throws ParseException {
        if (isEmpty(inStr))
            return new ArrayList<Date>();
        else {
            String[] strArray = StringUtils.split(inStr, splitChar);
            List<Date> dateList = new ArrayList<Date>();
            int i = 0;
            while (i < strArray.length) {
                if("null".equals(strArray[i]))
                    dateList.add(null);
                else
                    dateList.add(parseDateIso(strArray[i]));

                i++;
            }
            return dateList;
        }
    }

    public static boolean containsIgnoreCase(List<String> l, String s) {
        Iterator<String> it = l.iterator();
        while (it.hasNext()) {
            if (it.next().equalsIgnoreCase(s))
                return true;
        }

        return false;
    }

    public static boolean stringContainsIgnoreCase(String source, String s) {
        if (isEmpty(source)) return false;

        Iterator<String> it = toList(source, ARRAY_DELIMITER_CHAR).iterator();
        while (it.hasNext()) {
            if (it.next().equalsIgnoreCase(s))
                return true;
        }

        return false;
    }


    public static boolean isValidEmailAddress(String emailAddress) {
        String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    public static String printArray(Object[] values) {
        return printArray(values, ARRAY_DELIMITER_CHAR);
    }

    public static String printArray(Object[] values, char separator) {
        if (values == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (isNotEmpty(values[i])) {
                if (i > 0)
                    sb.append(separator);
                sb.append(values[i]);
            }
        }
        return sb.toString();
    }

    public static String printList(List values) {
        return printList(values, false);
    }

    public static String printList(List values, boolean withSpace) {
        if (values == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (isNotEmpty(values.get(i))) {
                if (i > 0) {
                    sb.append(ARRAY_DELIMITER_CHAR);
                    if (withSpace) {
                        sb.append(" ");
                    }
                }
                sb.append(values.get(i));
            }
        }
        return sb.toString();
    }

    public static String[] toStringArray(String value) {
        if (isEmpty(value)) return new String[0];
        return value.trim().split(ARRAY_DELIMITER);
    }

    public static boolean isDateBetween(Date startDate, Date endDate) {
        Date today = new Date();
        if (startDate != null && endDate != null)
            return today.after(startDate) && today.before(endDate);
        else
            return false;
    }

    public static String shorten(String val, int i) {
        if (val == null) {
            return null;
        }
        if (val.length() < i - 1) {
            return val;
        }
        return val.substring(0, i - 1);
    }

    public static boolean isDateBetween(Date input, Date from, Date to) {
        if (input == null) {
            throw new IllegalArgumentException("The given 'input' date was null");
        }
        if (from == null) {
            throw new IllegalArgumentException("The given 'from' date was null");
        }
        if (to == null) {
            throw new IllegalArgumentException("The given 'to' date was null");
        }
        if (from.after(to)) {
            throw new IllegalArgumentException("The given 'from' date is after the 'to' date");
        }
        return (input.getTime() == from.getTime() || input.after(from)) && (input.getTime() == to.getTime() || input.before(to));
    }

    public static boolean doPeriodsOverlap(Date p1Start, Date p1End, Date p2Start, Date p2End) {
        return isDateBetween(p1Start, p2Start, p2End) || isDateBetween(p1End, p2Start, p2End)
                || (p1Start.before(p2Start) && p1End.after(p2End)) || (p1Start.after(p2Start) && p1End.before(p2End));
    }

    /**
     * Utility Method to validate any String against a Regular Expression
     *
     * @param regex the regular expression to match
     * @param value the String to validate against the regular expression
     * @return true if value matches regex.
     */
    public static boolean validate(String regex, String value) {
        Pattern mask = Pattern.compile(regex);
        return mask.matcher(value).matches();
    }

    /**
     * Utility Method to validate integer numbers.
     *
     * @param number the String to validate
     * @return true if number is valid Integer
     */
    public static boolean isValidInteger(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Date getDaysFromNow(int days, Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        if (isNotEmpty(date))
            calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    public static Date getMonthsFromNow(int months, Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        if (isNotEmpty(date))
            calendar.setTime(date);

        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    public static Date getYearsFromNow(int years, Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        if (isNotEmpty(date))
            calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    public static Date getWeeksFromNow(int weeks, Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        if (isNotEmpty(date))
            calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, weeks);
        return calendar.getTime();
    }

    public static Date getDaysFromNow(int days, int hour, int minute) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date getMinutesFromNow(int minutes, Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        if (isNotEmpty(date))
            calendar.setTime(date);

        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date getHoursFromNow(int hours, Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        if (isNotEmpty(date))
            calendar.setTime(date);

        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static Integer extractMinutes(Date d) {
        if (d == null) {
            return null;
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        return calendar.get(Calendar.MINUTE);
    }

    public static Integer extractHours(Date d) {
        if (d == null) {
            return null;
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String replaceVariables(String s, Map<String, Object> replacementVariables) {
        return String.valueOf(TemplateRuntime.eval(s, replacementVariables));
    }

    public static JSONArray toJsonMap(String key, List objects) throws Exception {
        return toJsonMap(key, objects, "toJSON");
    }

    public static JSONArray toJsonMap(String key, List objects, String method) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (Object o : objects) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", o.getClass().getMethod("get" + StringUtils.capitalize(key)).invoke(o));
            jsonObject.put("value", o.getClass().getMethod(method).invoke(o));
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    public static JSONArray toJsonList(List objects) throws Exception {
        return toJsonList(objects, "toJSON");
    }

    public static JSONArray toJsonList(List objects, String method) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (Object o : objects) {
            jsonArray.put(o.getClass().getMethod(method).invoke(o));
        }
        return jsonArray;
    }

    private static String objToJsonString(Object obj, boolean indentation, boolean includeNulls, DateFormat dateFormat) {
        try {
            if (obj != null) {
                ObjectMapper om = new ObjectMapper();
                if (!includeNulls) {
                    om.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                }
                if (indentation) {
                    om.configure(SerializationFeature.INDENT_OUTPUT, true);
                }
                om.setDateFormat(dateFormat);
                return om.writeValueAsString(obj);
            } else
                return null;
        } catch (Exception e) {
            log.error("Error in objToJsonString", e);
            return null;
        }
    }

    public static String objToJsonString(Object obj) {
        return objToJsonString(obj, false, false, WebUtils.getIsoDateFormat());
    }

    public static String objToJsonStringWithClientDateFormat(Object obj) {
        return objToJsonString(obj, false, false, WebUtils.getDefaultDateFormat());
    }

    public static String objToJsonStringWithIndentation(Object obj) {
        return objToJsonString(obj, true, false, WebUtils.getIsoDateFormat());
    }

    public static String objToJsonStringWithClientDateFormatAndIndentation(Object obj) {
        return objToJsonString(obj, true, false, WebUtils.getDefaultDateFormat());
    }

    public static <T extends Object> T jsonStringToObj(String json, Class<T> clazz, boolean failOnUnknownProperties) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknownProperties);
        objectMapper.setDateFormat(WebUtils.getIsoDateFormat());
        return objectMapper.readValue(json, clazz);
    }

    public static <T extends Object> T jsonStringToObj(String json, TypeReference<T> typeReference) throws Exception {
        return jsonStringToObj(json, typeReference, true);
    }

    public static <T extends Object> T jsonStringToObj(String json, TypeReference<T> typeReference, boolean failOnUnknownProperties) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknownProperties);
        objectMapper.setDateFormat(WebUtils.getIsoDateFormat());
        return objectMapper.readValue(json, typeReference);
    }

    public static <T extends Object> T jsonStringToObj(String json, Class<T> clazz) throws Exception {
        return jsonStringToObj(json, clazz, true);
    }

    public static Object getPropertyFromJsonClassString(String jsonObj, String fqClassName, String property) throws Exception {
        Object obj = Utilities.jsonStringToObj(jsonObj, Class.forName(fqClassName), false);
        return obj.getClass().getMethod("get" + StringUtils.capitalize(property)).invoke(obj);
    }

    public static Date trunc(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        } else
            return null;
    }

    public static boolean isToday(Date endDate) {
        return trunc(new Date()).equals(trunc(endDate));
    }

    public static Object deepCopy(Object o) throws Exception {
        //serialize object
        ByteArrayOutputStream baos = new ByteArrayOutputStream(100);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        byte buf[] = baos.toByteArray();
        oos.close();
        // deserialize object
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object cloneObject = ois.readObject();
        ois.close();
        return cloneObject;
    }

    public static int getDateDifferenceInDays(Date from, Date to) {
        return Days.daysBetween(new DateTime(from), new DateTime(to)).getDays();
    }

    public static int getDateDifferenceInHours(Date from, Date to) {
        return Hours.hoursBetween(new DateTime(from), new DateTime(to)).getHours();
    }

    public static Map<String, String> transformToMap(List<MapEntry> entries) {
        Map<String, String> map = new HashMap<String, String>();
        for (MapEntry entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static Map transformToMap(List entries, String key, String value) throws Exception {
        Map map = new HashMap();
        for (Object obj : entries) {
            map.put(obj.getClass().getMethod("get" + StringUtils.capitalize(key)).invoke(obj), obj.getClass().getMethod("get" + StringUtils.capitalize(value)).invoke(obj));
        }
        return map;
    }

    public static Map<String, List> transformToMapCategories(Collection entries, String key) throws Exception {
        Map<String, List> map = new HashMap<String, List>();
        if (entries != null) {
            for (Object obj : entries) {
                String category = MVEL.evalToString(key, obj);
                if (!map.containsKey(category)) {
                    map.put(category, new ArrayList());
                }

                map.get(category).add(obj);
            }
        }
        return map;
    }

    public static String printIdsFromList(List list) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            sb.append(o.getClass().getMethod("getId").invoke(o));
            if (i - 1 < list.size()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static String printPropertyFromList(List list, String property) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            sb.append(o.getClass().getMethod("get" + StringUtils.capitalize(property)).invoke(o));
            if (i + 1 < list.size()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static int valueOfPropertyExistsInObjectList(List list, final String property, final Object value) throws Exception {
        Predicate propertyHasValuePredicate = new Predicate() {
            public boolean evaluate(Object object) {
                Object objValue;
                try {
                    objValue = object.getClass().getMethod("get" + StringUtils.capitalize(property)).invoke(object);
                } catch (Exception e) {
                    return false;
                }
                return objValue.equals(value);
            }
        };

        return CollectionUtils.countMatches(list, propertyHasValuePredicate);
    }


    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.<T>copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static <T extends Enum<T>> String joinEnum(List<T> l, String separator) {
        StringBuilder sb = new StringBuilder();
        if (l != null) {
            for (int i = 0; i < l.size() - 1; i++) {
                sb.append(l.get(i).name()).append(separator);
            }
            sb.append(l.get(l.size() - 1));
        }
        return sb.toString();
    }

    public static String join(String[] array, String separator) {
        StringBuilder sb = new StringBuilder();
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length - 1; i++) {
                sb.append(array[i]).append(separator);
            }
            sb.append(array[(array.length - 1)]);
        }
        return sb.toString();
    }

    public static String join(Collection collection, String separator) {
        StringBuilder sb = new StringBuilder();
        if (collection != null && collection.size() > 0) {
            for (Object o : collection) {
                sb.append(String.valueOf(o)).append(separator);
            }
            sb.delete(sb.lastIndexOf(separator), sb.length());
        }
        return sb.toString();
    }

    public static <T extends Enum<T>> List<T> fromCSVString(Class<T> clazz, String sList) {
        List<T> l = new ArrayList<T>();
        if (sList != null) {
            String[] tokens = sList.split("\\s*,\\s*");
            if (tokens != null) {
                for (String s : tokens) {
                    l.add(Enum.valueOf(clazz, s.trim()));
                }
            }
        }
        return l;
    }

    public static <T> List<T> arrayToList(T[] elements) {
        List<T> list = new ArrayList<T>();
        Collections.addAll(list, elements);
        return list;
    }

    public static List<SelectOption> asSelectOptions(Collection collection, boolean addEmpty) {
        List<SelectOption> selectOptions = new ArrayList<SelectOption>();
        if(addEmpty)
            selectOptions.add(new SelectOption("", "", null));
        for (Object s : collection) {
            selectOptions.add(new SelectOption(String.valueOf(s), String.valueOf(s), null));
        }
        return selectOptions;
    }

    public static List<SelectOption> asSelectOptions(Collection collection, String labelProperty, String valueProperty) {
        List<SelectOption> selectOptions = new ArrayList<SelectOption>();
        for (Object o : collection) {
            selectOptions.add(new SelectOption(MVEL.evalToString(labelProperty, o), MVEL.evalToString(valueProperty, o), null));
        }
        return selectOptions;
    }

    public static List<Long> getDatesInListOfLong(List<Date> dates) {
        List<Long> datesInLong = new ArrayList<Long>();
        if (dates != null) {
            for (Date date : dates) {
                datesInLong.add(date.getTime());
            }
        }
        return datesInLong;
    }

    public static int indexOfInArray(Object obj, Object[] array) {
        for (int i = 0, arrayLength = array.length; i < arrayLength; i++) {
            Object o = array[i];
            if (o.equals(obj)) return i;
        }
        return -1;
    }

    public static File getCsvFileFromString(String fileName, String content) {
        File temp = null;
        try {
            temp = File.createTempFile(fileName, ".csv");
            writeStringToFile(temp, content);
        } catch (IOException e) {
        }
        return temp;
    }

    public static boolean checkTextForEAN13(String text) {
        int check = 0;
        for (int i = 0; i < 13; i += 2) {
            String temp = text.substring(i, 1 + i);
            check += Integer.parseInt(temp);
        }
        for (int i = 1; i < 12; i += 2) {
            String temp = text.substring(i, 1 + i);
            check += 3 * Integer.parseInt(temp);
        }
        return check % 10 == 0;
    }

    public static boolean isAlphabeticalWithCapitalNumbersAndSpecialCharacters(String input) {
        return input != null && Pattern.compile("[A-Z0-9\\-\\+\\.\\$\\/\\%\\s]+").matcher(input).matches();
    }

    public static boolean isCODBARBarcode(String input) {
        return input != null && Pattern.compile("[AaBbCcDd][0-9\\-\\:\\.\\$\\/\\+]*[Tt Nn *Ee]").matcher(input).matches();
    }

    public static <T> boolean checkAllTrue(Map<T, Boolean> steps) {
        boolean isComplete = true;
        if (isNotEmpty(steps)) {
            for (T step : steps.keySet()) {
                if (!steps.get(step)) {
                    isComplete = false;
                    break;
                }
            }
        } else {
            isComplete = false;
        }
        return isComplete;
    }

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static boolean isTrue(Boolean b) {
        return b != null && b.booleanValue() == true;
    }

    public static boolean isFalse(Boolean b) {
        return b == null || b.booleanValue() == false;
    }




    public static List<Long> extractLongPropertyList(List list, String property) {
        List<Long> ret = new ArrayList<Long>();
        if (list != null) {
            for (Object o : list) {
                ret.add(Long.valueOf(MVEL.evalToString(property, o)));
            }
        }
        return ret;
    }

    public static Object extractPropertyForIdInList(List list, String keyProperty, Object keyValue, String extractedProperty) throws Exception {
        List<Long> ret = new ArrayList<Long>();
        if (list != null) {
            for (Object o : list) {
                Object key = o.getClass().getMethod("get" + StringUtils.capitalize(keyProperty)).invoke(o);
                if (key.equals(keyValue))
                    return o.getClass().getMethod("get" + StringUtils.capitalize(extractedProperty)).invoke(o);
            }
        }
        return ret;
    }

    /**
     * Group a list by an items' attribute value. The map produced will have as a key the value of the property
     * and as a value the items that have the key's value as attribute
     * @param list the list containing the items
     * @param keyPropertyName the path to the property e.x. "address.street"
     * @param <S> The maps key type
     * @param <T> The List's items type
     * @return the map
     */
    @SuppressWarnings("unchecked")
    public static <S, T> Map<S, List<T>> groupListBy(List<T> list, String keyPropertyName) {
        if (isEmpty(list)) return null;
        Map<S, List<T>> map = new HashMap<S, List<T>>();
        try {
            for (T i : list) {
                S key = (S) MVEL.eval(keyPropertyName, i);

                if (map.get(key) == null) map.put(key, new ArrayList<T>());
                map.get(key).add(i);
            }
            return map;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }
    /**
     * Group properties of list by an items' attribute value. The map produced will have as a key the value of the property
     * and as a value the properties of the items that have the key's value as attribute
     * @param list the list containing the items
     * @param keyPropertyName the path to the key property e.x. "address.street"
     * @param propertyName the path to the value property
     * @param propertyClass the class of the value property
     * @param <S> The maps key type
     * @param <T> The properies type
     * @return the map
     */
    @SuppressWarnings("unchecked")
    public static <S, T> Map<S, List<T>> groupPropertyOfListBy(List list, String keyPropertyName, String propertyName, Class<T> propertyClass) {
        if (isEmpty(list)) return null;
        Map<S, List<T>> map = new HashMap<S, List<T>>();
        try {
            for (Object i : list) {
                S key = (S) MVEL.eval(keyPropertyName, i);
                if (map.get(key) == null) map.put(key, new ArrayList<T>());
                T prop = (T) MVEL.eval(propertyName, i);
                map.get(key).add(prop);
            }
            return map;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    public static Long getMostFrequent(Map<Long,Long> frequencyMap) {
        if (frequencyMap == null) {
            return null;
        }
        Long maxVal = 0L;
        Long mostFrequent = null;
        SortedSet<Long> highFrequencies = new TreeSet<Long>();
        for (Long key : frequencyMap.keySet()) {
            if (frequencyMap.get(key) > maxVal) {
                maxVal = frequencyMap.get(key);
                mostFrequent = key;
                highFrequencies.clear();
                highFrequencies.add(mostFrequent);
            } else if (frequencyMap.get(key) == maxVal) {
                highFrequencies.add(key);
            }
        }
        if (highFrequencies.size() == 1) {
            return mostFrequent;
        } else {
            Double total = 0D;
            Long divisor = 0L;
            for (Long amount : frequencyMap.keySet()) {
                Long frequency = frequencyMap.get(amount);
                total += amount * frequency;
                divisor += frequency;
            }
            Double average = total / divisor;
            Double minDiff = 0D;
            Long closestAmount = null;
            int counter = 0;
            for (Long amount : highFrequencies) {
                Double diff = amount - average;
                if (diff < 0)
                    diff = diff * (-1);
                if (diff <= minDiff || counter == 0) {
                    minDiff = diff;
                    closestAmount = amount;
                }
                counter ++;
            }
            return closestAmount;
        }
    }

    public static String formatCurrency(Integer value) {
        Double currencyValue = Double.valueOf(value)/100;
        DecimalFormat format = new DecimalFormat(CURRENCY_FORMAT);
        String currencyFormattedValue = format.format(currencyValue);
        currencyFormattedValue = removeCurrencyZeroDecimals(currencyFormattedValue);

        return currencyFormattedValue;
    }

    public static Long getValueFromMap(Map<Long,Long> map, Long key, Long defaultValue) {
        if (map == null) {
            return defaultValue;
        }
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return defaultValue;
    }

    public static Map<String , Object> findAnnotatedFieldValues(Object inst, Class annotationClass) {
        Map<String, Object> m = new HashMap<String, Object>();
        for(Field field : inst.getClass().getDeclaredFields()){
            String name = field.getName();
            java.lang.annotation.Annotation[] annotations = field.getDeclaredAnnotations();
            for (int i = 0; i < annotations.length; i++) {
                if (annotations[i].annotationType().equals(annotationClass)) {
                    Object val = MVEL.eval(name,inst);
                    m.put(name, val);
                    break;
                }
            }
        }
        return m;
    }

    public static String removeCurrencyZeroDecimals(String currencyValue) {
        currencyValue = StringUtils.removeEnd(String.valueOf(currencyValue), ",00");
        currencyValue = StringUtils.removeEnd(String.valueOf(currencyValue), ".00");
        return currencyValue;
    }




    public static String appendPathSeparator(String path) {
        if (!path.endsWith("/")) {
            path += "/";
        }
        return path;
    }
}

