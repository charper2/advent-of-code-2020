package charper.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DayFour {

    public DayFour() {
        run();
    }
    
    public void run() {
        int numValid = 0;
        try {
            File input = new File("C:\\Users\\Caity\\code\\advent-of-code-2020\\src\\main\\java\\charper\\advent\\DayFourInput.txt");
            Scanner scanner = new Scanner(input);
            scanner.useDelimiter(Pattern.compile("^\\s*$", Pattern.MULTILINE));
            while(scanner.hasNext()) {
                String str = scanner.next();
                if (!isBirthYearValid(str)) {
                    continue;
                }

                if (!isIssueYearValid(str)) {
                    continue;
                }

                if (!isExpirationYearValid(str)) {
                    continue;
                }
                
                if (!isHeightValid(str)) {
                    continue;
                }

                if (!isHairColourValid(str)) {
                    continue;
                }

                if (!isEyeColourValid(str)) {
                    continue;
                }

                if (!isPassportIDValid(str)) {
                    continue;
                }

                numValid++;
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops, no file here...");
        }
        System.out.println(numValid);
    }

    private boolean isBirthYearValid(String str) {
        Pattern byr = Pattern.compile("(?:^|\\s)byr:([^\\s]+)(?:\\s|$)");
        Matcher mByr = byr.matcher(str);
        if (mByr.find()) {
            int birthYear = Integer.parseInt(mByr.group(1));
            if (birthYear >= 1920 || birthYear <= 2002) {
                return true;
            }
        }
        return false;
    }

    private boolean isIssueYearValid(String str) {
        Pattern iyr = Pattern.compile("(?:^|\\s)iyr:([^\\s]+)(?:\\s|$)");
        Matcher mIyr = iyr.matcher(str);
        if (mIyr.find()) {
            int match = Integer.parseInt(mIyr.group(1));
            if (match >= 2010 || match <= 2020) {
                return true;
            }
        } 
        return false;
    }

    private boolean isExpirationYearValid(String str) {
        Pattern eyr = Pattern.compile("(?:^|\\s)eyr:([^\\s]+)(?:\\s|$)");
        Matcher mEyr = eyr.matcher(str);
        if (mEyr.find()) {
            int match = Integer.parseInt(mEyr.group(1));
            if (match >= 2020 || match <= 2030) {
                return true;
            }
        } 
        return false;
    }

    private boolean isHeightValid(String str) {
        Pattern hgt = Pattern.compile("(?:^|\\s)hgt:([^\\s]+)(?:\\s|$)");
        Matcher mHgt = hgt.matcher(str);
        if (mHgt.find()) {
            String heightStr = mHgt.group(1);
            Pattern heightValidation = Pattern.compile("(.*)(cm|in)");
            Matcher mHgtValid = heightValidation.matcher(heightStr);
            if (mHgtValid.find()) {
                int height = Integer.parseInt(mHgtValid.group(1));
                String type = mHgtValid.group(2);
                if (type.equals("cm") && height >= 150 && height <= 193) {
                    return true;
                } else if (type.equals("in") && height >= 59 && height <= 76) {
                    return true;
                }
            } 
        } 
        return false;
    }

    private boolean isHairColourValid(String str) {
        Pattern hcl = Pattern.compile("(?:^|\\s)hcl:([^\\s]+)(?:\\s|$)");
        Matcher mHcl = hcl.matcher(str);
        if (mHcl.find()) {
            String hclMatch = mHcl.group(1);
            if (hclMatch.length() != 7 || hclMatch.charAt(0) != '#') {
                return false;
            }
            Pattern hclValidation = Pattern.compile("(#+[0-9a-f]{6})");
            Matcher mHclValid = hclValidation.matcher(hclMatch);
            if (mHclValid.matches()) {
                return true;
            }
        } 
        return false;
    }

    private boolean isEyeColourValid(String str) {
        Pattern ecl = Pattern.compile("(?:^|\\s)ecl:([^\\s]+)(?:\\s|$)");
        Matcher mEcl = ecl.matcher(str);
        if (mEcl.find()) {
            String eclMatch = mEcl.group(1);
            Pattern eclValidation = Pattern.compile("^(?:amb|blu|brn|gry|grn|hzl|oth)$");
            Matcher mEclValid = eclValidation.matcher(eclMatch);
            if (mEclValid.matches()) {
                return true;
            }
        } 
        return false;
    }

    private boolean isPassportIDValid(String str) {
        Pattern pid = Pattern.compile("(?:^|\\s)pid:([^\\s]+)(?:\\s|$)");
        Matcher mPid = pid.matcher(str);
        if (mPid.find()) {
            String pidMatch = mPid.group(1);
            Pattern pidValidation = Pattern.compile("^\\d{9}");
            Matcher mPidValid = pidValidation.matcher(pidMatch);
            if (mPidValid.matches()) {
                return true;
            }
        } 
        return false;
    }
}
