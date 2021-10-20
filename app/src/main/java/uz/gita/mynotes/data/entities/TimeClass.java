package uz.gita.mynotes.data.entities;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeClass {

    public String getTime(String givenDateString){
        if (givenDateString.equalsIgnoreCase("")) {
            return "";
        }

        long timeInMilliseconds=0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        try {

            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String result = "now";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        String todayDate = formatter.format(new Date());
        Calendar calendar = Calendar.getInstance();

        long dayagolong =  timeInMilliseconds;
        calendar.setTimeInMillis(dayagolong);
        String agoformater = formatter.format(calendar.getTime());

        Date CurrentDate = null;
        Date CreateDate = null;

        try {
            CurrentDate = formatter.parse(todayDate);
            CreateDate = formatter.parse(agoformater);

            long different = Math.abs(CurrentDate.getTime() - CreateDate.getTime());

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            different = different % secondsInMilli;
            if (elapsedDays == 0) {
                if (elapsedHours == 0) {
                    if (elapsedMinutes == 0) {
                        if (elapsedSeconds < 0) {
                            return "0" + " s";
                        } else {
                            if (elapsedDays > 0 && elapsedSeconds < 59) {
                                return "now";
                            }
                        }
                    } else {
                        return elapsedMinutes + "mins ago";
                    }
                } else {
                    return elapsedHours + "hr ago";
                }

            } else {
                if (elapsedDays <= 29) {
                    return elapsedDays + "d ago";

                }
                else if (elapsedDays <= 58) {
                    return "1Mth ago";
                }
                if (elapsedDays <= 87) {
                    return "2Mth ago";
                }
                if (elapsedDays <= 116) {
                    return "3Mth ago";
                }
                if (elapsedDays <= 145) {
                    return "4Mth ago";
                }
                if (elapsedDays <= 174) {
                    return "5Mth ago";
                }
                if (elapsedDays <= 203) {
                    return "6Mth ago";
                }
                if (elapsedDays <= 232) {
                    return "7Mth ago";
                }
                if (elapsedDays <= 261) {
                    return "8Mth ago";
                }
                if (elapsedDays <= 290) {
                    return "9Mth ago";
                }
                if (elapsedDays <= 319) {
                    return "10Mth ago";
                }
                if (elapsedDays <= 348) {
                    return "11Mth ago";
                }
                if (elapsedDays <= 360) {
                    return "12Mth ago";
                }

                if (elapsedDays <= 720) {
                    return "1 year ago";
                }
            }

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return result;
    }


}

