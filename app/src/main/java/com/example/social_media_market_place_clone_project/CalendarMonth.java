package com.example.social_media_market_place_clone_project;

public class CalendarMonth {

    public String getMonth(int i){
        String m;

        switch(i){
            case 1:
                m = "January";
                break;

            case 2:
                m = "February";
                break;

            case 3:
                m = "March";
                break;

            case 4:
                m = "April";
                break;

            case 5:
                m = "May";
                break;

            case 6:
                m = "June";
                break;

            case 7:
                m = "July";
                break;

            case 8:
                m = "August";
                break;

            case 9:
                m = "September";
                break;

            case 10:
                m = "October";
                break;

            case 11:
                m = "November";
                break;

            case 12:
                m = "December";
                break;

            default:
                m = "Month";
        }

        return m;
    }
    public String getDateFormatURL(int day, int month, int year){
        String dateURLformat = " ";

        if(month<10){
            if(day<10){
                dateURLformat = "0"+day+"-"+"0"+month+"-"+year;
            }else{
                dateURLformat = day+"-"+"0"+month+"-"+year;
            }
        }else if(day<10){
            dateURLformat = "0"+day+"-"+month+"-"+year;
        }else{
            dateURLformat = day+"-"+month+"-"+year;
        }
        return dateURLformat;
    }

}

