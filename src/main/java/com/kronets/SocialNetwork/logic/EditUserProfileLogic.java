package com.kronets.SocialNetwork.logic;

import com.kronets.SocialNetwork.dao.InterestDao;
import com.kronets.SocialNetwork.dao.UserDao;
import com.kronets.SocialNetwork.dao.impl.InterestDaoImpl;
import com.kronets.SocialNetwork.dao.impl.UserDaoImpl;
import com.kronets.SocialNetwork.models.Interest;
import com.kronets.SocialNetwork.models.User;


import java.sql.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Volodymyr Grynda
 */
public class EditUserProfileLogic {


    public boolean edit(long userId, String name, String surname,
                     String position, String interests, String day,
                     String month, String year) {
        String names = new String(name);
        String surnames = new String(surname);
        String positions = new String(position);
        String interest = new String(interests);
        String days = new String(day);
        String months = new String(month);
        String years = new String(year);
        try {
            if(logic(userId, name, surname, position, interests, day, month, year) && match(name, surname, position, interests,
                    day, month, year) && names.length() > 2 && names.length() < 20 && surnames.length() > 2 && surnames.length() < 20 &&
                    positions.length() > 2 && positions.length() < 20 && interest.length() > 2 && interest.length() < 51 &&
                    days.length() == 2 && months.length() == 2 && years.length() == 4){
                return true;
            }else{
                return false;
            }

        }
        catch (Exception e) {
            return false;
        }


    }

    private boolean logic(long userId, String name, String surname,
                       String position, String interests, String day,
                       String month, String year) throws Exception {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.selectById(userId);

        if (!name.equals("")&& !surname.equals("") && !position.equals("")) {
            user.setName(name);
            user.setSurname(surname);
            user.setPosition(position);
        }else{
            return false;
        }

        Date date = null;
        date = parseBirthday(day, month, year);

        if (date != null) {
            user.setBirthday(date);
        }else{
            return false;
        }

        userDao.update(user);

        editInterests(userId, interests);

        return true;
    }


    private void editInterests(long userId, String interest) throws Exception {

        //parsing income line to request array
        String[] requestArray = interest.split(",");
        int length = requestArray.length;
        for (int i = 0; i < length; i++) {
            requestArray[i] = requestArray[i].trim();
        }

        //convert request array to request list
        List<String> requestList = new ArrayList<String>(Arrays.asList(requestArray));

        UserDao userDao = new UserDaoImpl();

        //getting current interests from DB
        List<Interest> interestsFromDb = userDao.selectAllInterests(userId);

        //convert interest lists from DB to 2 String lists (for deleting and adding users interests)
        List<String> interestForDelete = new ArrayList<String>(interestsFromDb.size());
        List<String> interestsForSubtraction = new ArrayList<String>(interestsFromDb.size());
        for (Interest current : interestsFromDb) {
            interestForDelete.add(current.getInterest());
            interestsForSubtraction.add(current.getInterest());

        }

        //getting list of interests what we must to delete
        interestForDelete.removeAll(requestList);

        //getting current user from DB
        User user = userDao.selectById(userId);

        //deleting old interests from DB
        InterestDao interestDao = new InterestDaoImpl();
        for (String current : interestForDelete) {
            Interest delInterest = interestDao.selectByInterest(current);
            userDao.deleteInterests(delInterest, user);
        }

        //searching interests for insert into DB
        if (requestList.size() != 0) {
            requestList.removeAll(interestsForSubtraction);
        }

        //inserting new interest to DB
        if (requestList.size() != 0) {
            Interest inter = null;
            for (String current : requestList) {
                inter = null;
                inter = interestDao.selectByInterest(current);
                if (inter == null) {
                    inter = new Interest();
                    inter.setInterest(current);
                    Set<User> set = new HashSet<User>();
                    set.add(user);
                    inter.setUsers(set);
                    interestDao.insert(inter);
                } else {
                    Set<User> set = inter.getUsers();
                    set.add(user);
                    inter.setUsers(set);
                    interestDao.update(inter);

                }
            }
        }
    }

    public java.sql.Date parseBirthday(String day, String month, String year) {
        int intDay;
        int intMonth;
        int intYear;
        try {
            intDay = Integer.parseInt(day);
            intMonth = Integer.parseInt(month);
            intYear = Integer.parseInt(year);
        } catch (Exception e) {
            return null;
        }


        if (intDay < 32 && intDay > 0 &&
                intMonth <= 12 && intMonth > 0 &&
                intYear > 1920 && intYear <2020) {
            //month -- because int sqlDate first month is 0
            intMonth--;
            Calendar calendar = new GregorianCalendar(intYear, intMonth, intDay);
            Date date = new Date(calendar.getTimeInMillis());
            return date;
        } else {
            return null;
        }
    }

    private boolean match(String name, String surname, String position, String interests, String day,
                          String month, String year) {

        Pattern namePattern = Pattern.compile("^[a-zA-Z0-9_-]{2,20}$");
        Pattern surnamePattern = Pattern.compile("^[a-zA-Z0-9_-]{2,20}$");
        Pattern positionpattern = Pattern.compile("^[a-zA-Z0-9_-]{2,20}$");
        Pattern interestsPattern = Pattern.compile("^[a-zA-Z0-9_-]{2,100}$");
        Pattern dayPattern =  Pattern.compile("^[a-zA-Z0-9_-]{1,3}$");
        Pattern monthPattern = Pattern.compile("^[a-zA-Z0-9_-]{1,3}$");
        Pattern yearPattern = Pattern.compile("^[a-zA-Z0-9_-]{1,5}$");

        Matcher nameMatcher = namePattern.matcher(name);
        Matcher surnameMatcher = surnamePattern.matcher(surname);
        Matcher positionMatcher = positionpattern.matcher(position);
        Matcher interestsMatcher = interestsPattern.matcher(interests);
        Matcher dayMatcher = dayPattern.matcher(day);
        Matcher monthMatcher = monthPattern.matcher(month);
        Matcher yearMatcher = yearPattern.matcher(year);

        if (surnameMatcher.matches() && nameMatcher.matches() && positionMatcher.matches() && interestsMatcher.matches()
                && dayMatcher.matches() && monthMatcher.matches() && yearMatcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
