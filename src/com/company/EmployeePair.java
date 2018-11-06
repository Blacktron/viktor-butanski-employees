package com.company;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EmployeePair {
    private File file;
    private ArrayList<Employee> employees;
    private ArrayList<String> checkedPairs;

    public EmployeePair(String filePath) {
        this.file = new File(filePath);
        this.employees = new ArrayList<>();
        this.checkedPairs = new ArrayList<>();
    }

    /**
     * Method which reads a file and creates Employee objects
     * based on the data read from the file.
     * @throws IOException
     */
    private void createEmployees() throws IOException {
        BufferedReader reader = null;
        String line;

        try {
            reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(", ");
                String empID = details[0];
                String projectID = details[1];
                Date dateFrom = convertStringToDate(details[2]);
                Date dateTo = convertStringToDate(details[3]);

                Employee employee = new Employee(empID, projectID, dateFrom, dateTo);
                employees.add(employee);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * Method which creates Date from String.
     * @param dateStr   The date in string.
     * @return          The Date object.
     */
    private Date convertStringToDate(String dateStr) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        if (dateStr.equalsIgnoreCase("null")) {
            date = new Date();
        } else {
            try {
                date = myFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (date == null) {
            date = new Date();
        }

        return date;
    }

    /**
     * Method which finds the pair employees worked together on a project
     * for the longest period of time.
     */
    public void findPair() {
        ArrayList<Pair> pairs = createPairs();

        int maxDaysWorkedTogether;
        int index;

        if (pairs.size() != 1) {
            maxDaysWorkedTogether = pairs.get(0).getDaysWorkedTogether();
            index = 0;

            for (int i = 1; i < pairs.size(); i++) {
                Pair temp = pairs.get(i);

                if (maxDaysWorkedTogether < temp.getDaysWorkedTogether()) {
                    maxDaysWorkedTogether = temp.getDaysWorkedTogether();
                    index = i;
                }
            }

            Pair pair = pairs.get(index);
            System.out.println("Employee ID #1 Employee ID #2 Project ID Days worked");
            System.out.println(pair.getEmployeeID1() + "            " + pair.getEmployeeID2() + "            " + pair.getProjectID() + "         " + pair.getDaysWorkedTogether());
        } else {
            System.out.println("No pairs found");
        }
    }

    /**
     * Method which creates pair of employees (Pair) worked on the same project.
     * @return  A collection of employee Pairs.
     */
    private ArrayList<Pair> createPairs() {
        ArrayList<Pair> pairs = new ArrayList<>();

        try {
            createEmployees();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < employees.size(); i++) {
            Employee emp1 = employees.get(i);

            for (Employee emp2 : employees) {
                if (!emp1.getId().equals(emp2.getId()) && emp1.getProjectID().equals(emp2.getProjectID())) {
                    // Validate that the pair is not already checked
                    String pairToValidate = emp1.getId() + "-" + emp2.getId();
                    boolean isPairToCheck = validatePairNotChecked(pairToValidate);

                    if (isPairToCheck) {
                        boolean isOverlap = checkIsOverlapped(emp1.getDateFrom(), emp1.getDateTo(), emp2.getDateFrom(), emp2.getDateTo());

                        if (isOverlap) {
                            long diff = minDate(emp1.getDateTo(), emp2.getDateTo()).getTime() - maxDate(emp1.getDateFrom(), emp2.getDateFrom()).getTime();
                            int days = (int) (diff / (1000 * 60 * 60 * 24));
                            Pair pair = new Pair(emp1.getId(), emp2.getId(), emp1.getProjectID(), days);
                            pairs.add(pair);
                        }
                    }
                }
            }
        }

        return pairs;
    }

    /**
     * Method which checks if two data ranges overlap.
     * @param startDate1    Start date of first employee.
     * @param endDate1      End date of first employee.
     * @param startDate2    Start date of second employee.
     * @param endDate2      End date of second employee.
     * @return              true if the date ranges overlap, false otherwise.
     * @throws NullPointerException
     */
    private boolean checkIsOverlapped(Date startDate1, Date endDate1, Date startDate2, Date endDate2) throws NullPointerException {
        return (startDate1.before(startDate2) && endDate1.after(startDate2)) ||
                (startDate1.before(endDate2) && endDate1.after(endDate2)) ||
                (startDate1.before(startDate2) && endDate1.after(endDate2));
    }

    /**
     * Compares two date to check which is the sooner (smaller) date.
     * @param endDate1      The end date of the first employee.
     * @param endDate2      The end date of the second employee.
     * @return              The sooner date.
     */
    private Date minDate(Date endDate1, Date endDate2) {
        return (endDate1.compareTo(endDate2) < 0) ? endDate1 : endDate2;
    }

    /**
     * Compares two dates to check which is the later (bigger) date.
     * @param startDate1    The start date of the first employee.
     * @param startDate2    The start date of the second employee.
     * @return              The later date.
     */
    private Date maxDate(Date startDate1, Date startDate2) {
        return (startDate1.compareTo(startDate2) > 0) ? startDate1 : startDate2;
    }

    /**
     * Method which validates if a pair was already checked.
     * @param pairToValidate    The pair which has to be validated if already checked.
     * @return                  true if the pair is not yet checked, false otherwise.
     */
    private boolean validatePairNotChecked(String pairToValidate) {
        boolean result = true;
        String[] pairDetails = pairToValidate.split("-");
        String invertedPair = pairDetails[1] + "-" + pairDetails[0];

        if (checkedPairs.contains(pairToValidate) || checkedPairs.contains(invertedPair)) {
            result = false;
        } else {
            checkedPairs.add(pairToValidate);
            checkedPairs.add(invertedPair);
        }

        return result;
    }
}