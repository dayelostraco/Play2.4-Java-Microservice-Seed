package features.sms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dayel Ostraco
 * 9/28/15.
 */
@SuppressWarnings("unused")
public class PhoneNumberValidator {

    public static boolean isValidPhoneNumber(String phoneNumber, PhoneNumberRegion region) {
        return region.isValidPhoneNumber(phoneNumber);
    }

    public static String convertToMinimizedPhoneNumber(String phoneNumber, PhoneNumberRegion region)
        throws PhoneNumberException {
        return region.convertPhoneNumberToMinimizedFormat(phoneNumber);
    }

    public enum PhoneNumberRegion {

        NORTH_AMERICA("North America", "\\D*([2-9]\\d{2})(\\D*)([2-9]\\d{2})(\\D*)(\\d{4})\\D*", "+1$1$3$5");

        private String regionName;
        private String phoneNumberRegex;
        private String minimizedPhoneNumberRegexReplace;

        PhoneNumberRegion(String regionName, String phoneNumberRegex, String minimizedPhoneNumberRegexReplace) {
            this.regionName = regionName;
            this.phoneNumberRegex = phoneNumberRegex;
            this.minimizedPhoneNumberRegexReplace = minimizedPhoneNumberRegexReplace;
        }

        public String getRegionName() {
            return regionName;
        }

        public String getPhoneNumberRegex() {
            return phoneNumberRegex;
        }

        public String getMinimizedPhoneNumberRegexReplace() {
            return minimizedPhoneNumberRegexReplace;
        }

        public boolean isValidPhoneNumber(String phoneNumber) {

            Pattern naPattern = Pattern.compile(this.getPhoneNumberRegex());
            Matcher naMatcher = naPattern.matcher(phoneNumber);

            return naMatcher.matches();
        }

        public String convertPhoneNumberToMinimizedFormat(String phoneNumber)
                throws PhoneNumberException {

            Pattern naPattern = Pattern.compile(this.getPhoneNumberRegex());
            Matcher naMatcher = naPattern.matcher(phoneNumber);

            if(naMatcher.matches()) {
                return naMatcher.replaceAll(this.getMinimizedPhoneNumberRegexReplace());
            } else {
                throw new PhoneNumberException("The provided phone number " + phoneNumber
                        + " is not a valid North American phone number.");
            }
        }
    }
}
