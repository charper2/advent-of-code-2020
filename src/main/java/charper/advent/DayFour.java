package charper.advent;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DayFour {
    
    public void run() {
        int numValid = 0;
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(Pattern.compile("^\\s*$", Pattern.MULTILINE));;
        Pattern byr = Pattern.compile("(?:^|\\s)byr:([^\\s]+)(?:\\s|$)");
        Pattern iyr = Pattern.compile("(?:^|\\s)iyr:([^\\s]+)(?:\\s|$)");
        Pattern eyr = Pattern.compile("(?:^|\\s)eyr:([^\\s]+)(?:\\s|$)");
        Pattern hgt = Pattern.compile("(?:^|\\s)hgt:([^\\s]+)(?:\\s|$)");
        Pattern hcl = Pattern.compile("(?:^|\\s)hcl:([^\\s]+)(?:\\s|$)");
        Pattern ecl = Pattern.compile("(?:^|\\s)ecl:([^\\s]+)(?:\\s|$)");
        Pattern pid = Pattern.compile("(?:^|\\s)pid:([^\\s]+)(?:\\s|$)");
        // Pattern cid = Pattern.compile("(?:^|\\s)cid:([^\\s]+)(?:\\s|$)");

        // TODO refactor into functions
        while(scanner.hasNext()) {
            String str = scanner.next();
            Matcher mByr = byr.matcher(str);
            if (mByr.find()) {
                int birthYear = Integer.parseInt(mByr.group(1));
                if (birthYear < 1920 || birthYear > 2002) {
                    continue;
                }
            } else {
                continue;
            }

            Matcher mIyr = iyr.matcher(str);
            if (mIyr.find()) {
                int match = Integer.parseInt(mIyr.group(1));
                if (match < 2010 || match > 2020) {
                    continue;
                }
            } else {
                continue;
            }

            Matcher mEyr = eyr.matcher(str);
            if (mEyr.find()) {
                int match = Integer.parseInt(mEyr.group(1));
                if (match < 2020 || match > 2030) {
                    continue;
                }
            } else {
                continue;
            }

            Matcher mHgt = hgt.matcher(str);
            if (mHgt.find()) {
                String heightStr = mHgt.group(1);
                Pattern heightValidation = Pattern.compile("(.*)(cm|in)");
                Matcher mHgtValid = heightValidation.matcher(heightStr);
                if (mHgtValid.find()) {
                    int height = Integer.parseInt(mHgtValid.group(1));
                    String type = mHgtValid.group(2);
                    if (type.equals("cm") && (height < 150 || height > 193)) {
                        continue;
                    } else if (type.equals("in") && (height < 59 || height > 76)) {
                        continue;
                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }

            Matcher mHcl = hcl.matcher(str);
            if (mHcl.find()) {
                String hclMatch = mHcl.group(1);
                if (hclMatch.length() != 7 || hclMatch.charAt(0) != '#') {
                    continue;
                }
                Pattern hclValidation = Pattern.compile("(#+[0-9a-f]{6})");
                Matcher mHclValid = hclValidation.matcher(hclMatch);
                if (!mHclValid.matches()) {
                    continue;
                }
            } else {
                continue;
            }

            Matcher mEcl = ecl.matcher(str);
            if (mEcl.find()) {
                String eclMatch = mEcl.group(1);
                Pattern eclValidation = Pattern.compile("^(?:amb|blu|brn|gry|grn|hzl|oth)$");
                Matcher mEclValid = eclValidation.matcher(eclMatch);
                if (!mEclValid.matches()) {
                    continue;
                }
            } else {
                continue;
            }

            Matcher mPid = pid.matcher(str);
            if (mPid.find()) {
                String pidMatch = mPid.group(1);
                Pattern pidValidation = Pattern.compile("^\\d{9}");
                Matcher mPidValid = pidValidation.matcher(pidMatch);
                if (!mPidValid.matches()) {
                    continue;
                }
            } else {
                continue;
            }

            numValid++;
        }
        scanner.close();
        System.out.println(numValid);
    }
}
