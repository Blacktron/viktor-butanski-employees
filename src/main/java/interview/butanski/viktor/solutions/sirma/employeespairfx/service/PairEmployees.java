package interview.butanski.viktor.solutions.sirma.employeespairfx.service;

import interview.butanski.viktor.solutions.sirma.employeespairfx.entity.Employee;
import interview.butanski.viktor.solutions.sirma.employeespairfx.entity.Pair;
import interview.butanski.viktor.solutions.sirma.employeespairfx.utils.DateUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class PairEmployees {
    private static final String FILE_DELIMITER = ", ";
    private static final String DASH_STR = "-";

    private File file;
    private ArrayList<Employee> employees;
    private ArrayList<String> checkedPairs;

    public PairEmployees(File file) {
        this.file = file;
        this.employees = new ArrayList<>();
        this.checkedPairs = new ArrayList<>();
    }

    /**
     * Method which finds the pair employees worked together on a project
     * for the longest period of time.
     */
    public Pair findPair() {
        Pair pair = null;
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

            pair = pairs.get(index);
            System.out.println("Employee ID #1 Employee ID #2 Project ID Days worked");
            System.out.println(pair.getFirstEmployeeId() + "            " + pair.getSecondEmployeeId() + "            "
                    + pair.getProjectId() + "         " + pair.getDaysWorkedTogether());
        } else {
            System.out.println("No pairs found");
        }

        return pair;
    }

    /**
     * Method which creates a pair of employees (Pair) worked on the same project.
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
                if (!emp1.getId().equals(emp2.getId()) && emp1.getProjectId().equals(emp2.getProjectId())) {
                    // Validate that the pair is not already checked.
                    String pairToValidate = emp1.getId() + DASH_STR + emp2.getId();
                    boolean isPairToCheck = validatePairNotChecked(pairToValidate);

                    if (isPairToCheck) {
                        boolean areEmployeesOverlapping = checkIfEmployeesOverlap(emp1.getDateFrom(), emp1.getDateTo(), emp2.getDateFrom(), emp2.getDateTo());

                        if (areEmployeesOverlapping) {
                            long lesserDateMillis = getLesserDate(emp1.getDateTo(), emp2.getDateTo()).getTime();
                            long laterDateMillis = getLaterDate(emp1.getDateFrom(), emp2.getDateFrom()).getTime();

                            int days = (int) ((lesserDateMillis - laterDateMillis) / (1000 * 60 * 60 * 24));

                            Pair pair = new Pair(emp1.getId(), emp2.getId(), emp1.getProjectId(), days);
                            pairs.add(pair);
                        }
                    }
                }
            }
        }

        return pairs;
    }

    /**
     * Method which reads a file and creates Employee objects
     * based on the data read from the file.
     * @throws IOException
     */
    private void createEmployees() throws IOException {
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(FILE_DELIMITER);

                String empId = details[0];
                String projectId = details[1];
                Date dateFrom = convertStringToDate(details[2]);
                Date dateTo = convertStringToDate(details[3]);

                Employee employee = new Employee(empId, projectId, dateFrom, dateTo);
                employees.add(employee);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method which creates LocalDateTime from String.
     * @param dateStr   The date in string.
     * @return          The Date object.
     */
    private Date convertStringToDate(String dateStr) {
        return DateUtil.convertStringToDate(dateStr);
    }

    /**
     * Method which validates if a pair was already checked.
     * @param pairToValidate    The pair which has to be validated if already checked.
     * @return                  true if the pair is not yet checked, false otherwise.
     */
    private boolean validatePairNotChecked(String pairToValidate) {
        boolean result = true;
        String[] pairDetails = pairToValidate.split(DASH_STR);
        String invertedPair = pairDetails[1] + DASH_STR + pairDetails[0];

        if (checkedPairs.contains(pairToValidate) || checkedPairs.contains(invertedPair)) {
            result = false;
        } else {
            addPairToCheckedList(pairToValidate);
            addPairToCheckedList(invertedPair);
        }

        return result;
    }

    /**
     * Method which adds a pair to the checked pairs list.
     * @param pairToAdd         The pair which should be added to the list.
     */
    private void addPairToCheckedList(String pairToAdd) {
        checkedPairs.add(pairToAdd);
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
    private boolean checkIfEmployeesOverlap(Date startDate1, Date endDate1, Date startDate2,
                                            Date endDate2) throws NullPointerException {
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
    private Date getLesserDate(Date endDate1, Date endDate2) {
        return (endDate1.compareTo(endDate2) < 0) ? endDate1 : endDate2;
    }

    /**
     * Compares two dates to check which is the later (bigger) date.
     * @param startDate1    The start date of the first employee.
     * @param startDate2    The start date of the second employee.
     * @return              The later date.
     */
    private Date getLaterDate(Date startDate1, Date startDate2) {
        return (startDate1.compareTo(startDate2) > 0) ? startDate1 : startDate2;
    }
}
