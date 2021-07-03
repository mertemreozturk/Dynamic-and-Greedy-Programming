

public class Assignment implements Comparable {
    private String name;
    private String start;
    private int duration;
    private int importance;
    private boolean maellard;

    /*
        Getter methods
     */
    public String getName() {
        return name;
    }

    public String getStartTime() {
        return start;
    }

    public int getDuration() {
        return duration;
    }

    public int getImportance() {
        return importance;
    }

    public boolean isMaellard() {
        return maellard;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        int temp = Integer.parseInt(start.substring(0,2));
        temp += duration;
        if ( temp < 10){
            return "0"+temp +start.substring(2);
        }
        return temp +start.substring(2);
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        return (double)(importance*(isMaellard()?1001:1))/duration;
    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
        return getFinishTime().compareTo(((Assignment)o).getFinishTime());
    }

    /**
     * @return Should return a string in the following form:
     * Assignment{name='Refill vending machines', start='12:00', duration=1, importance=45, maellard=false, finish='13:00', weight=45.0}
     */
    @Override
    public String toString() {
        return "Assignment{name='"+getName()+"', "+"start='"+getStartTime()+"', "+"duration="+getDuration()+", "+
                "importance="+getImportance()+", "+"maellard="+isMaellard()+", "+"finish='"+getFinishTime()+"', "+
                "weight="+getWeight()+"}";
    }
}
