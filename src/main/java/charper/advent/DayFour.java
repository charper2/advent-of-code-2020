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
            Pattern byr = Pattern.compile("(?:^|\\s)byr:([^\\s]+)(?:\\s|$)");
            Pattern iyr = Pattern.compile("(?:^|\\s)iyr:([^\\s]+)(?:\\s|$)");
            Pattern eyr = Pattern.compile("(?:^|\\s)eyr:([^\\s]+)(?:\\s|$)");
            Pattern hgt = Pattern.compile("(?:^|\\s)hgt:([^\\s]+)(?:\\s|$)");
            Pattern hcl = Pattern.compile("(?:^|\\s)hcl:([^\\s]+)(?:\\s|$)");
            Pattern ecl = Pattern.compile("(?:^|\\s)ecl:([^\\s]+)(?:\\s|$)");
            Pattern pid = Pattern.compile("(?:^|\\s)pid:([^\\s]+)(?:\\s|$)");
            // Pattern cid = Pattern.compile("(?:^|\\s)cid:([^\\s]+)(?:\\s|$)");

            while(scanner.hasNext()) {
                String str = scanner.next();
                if (!isBirthYearValid(byr, str)) {
                    continue;
                }

                if (!isIssueYearValid(iyr, str)) {
                    continue;
                }

                if (!isExpirationYearValid(eyr, str)) {
                    continue;
                }
                
                if (!isHeightValid(hgt, str)) {
                    continue;
                }

                if (!isHairColourValid(hcl, str)) {
                    continue;
                }

                if (!isEyeColourValid(ecl, str)) {
                    continue;
                }

                if (!isPassportIDValid(pid, str)) {
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

    private boolean isBirthYearValid(Pattern pattern, String str) {
        Matcher mByr = pattern.matcher(str);
        if (mByr.find()) {
            int birthYear = Integer.parseInt(mByr.group(1));
            if (birthYear >= 1920 || birthYear <= 2002) {
                return true;
            }
        }
        return false;
    }

    private boolean isIssueYearValid(Pattern pattern, String str) {
        Matcher mIyr = pattern.matcher(str);
        if (mIyr.find()) {
            int match = Integer.parseInt(mIyr.group(1));
            if (match >= 2010 || match <= 2020) {
                return true;
            }
        } 
        return false;
    }

    private boolean isExpirationYearValid(Pattern pattern, String str) {
        Matcher mEyr = pattern.matcher(str);
        if (mEyr.find()) {
            int match = Integer.parseInt(mEyr.group(1));
            if (match >= 2020 || match <= 2030) {
                return true;
            }
        } 
        return false;
    }

    private boolean isHeightValid(Pattern pattern, String str) {
        Matcher mHgt = pattern.matcher(str);
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

    private boolean isHairColourValid(Pattern pattern, String str) {
        Matcher mHcl = pattern.matcher(str);
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

    private boolean isEyeColourValid(Pattern pattern, String str) {
        Matcher mEcl = pattern.matcher(str);
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

    private boolean isPassportIDValid(Pattern pattern, String str) {
        Matcher mPid = pattern.matcher(str);
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
