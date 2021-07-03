

import java.util.ArrayList;
import java.util.Arrays;

public class Scheduler {

    private Assignment[] assignmentArray;
    private Integer[] C;
    private Double[] max;
    private ArrayList<Assignment> solutionDynamic;
    private ArrayList<Assignment> solutionGreedy;

    /**
     * @throws IllegalArgumentException when the given array is empty
     */
    public Scheduler(Assignment[] assignmentArray) throws IllegalArgumentException {
        this.assignmentArray = assignmentArray;
        if ( assignmentArray.length == 0){
            throw new IllegalArgumentException();
        }
        this.C = new Integer[assignmentArray.length];
        this.max = new Double[assignmentArray.length];
        solutionDynamic = new ArrayList<>();
        solutionGreedy = new ArrayList<>();
        // Should be instantiated with an Assignment array
        // All the properties of this class should be initialized here
    }

    /**
     * @param index of the {@link Assignment}
     * @return Returns the index of the last compatible {@link Assignment},
     * returns -1 if there are no compatible assignments
     */
    private int binarySearch(int index) {
        int first = 0;
        int end = index;

        while(end >= first){
            int midElement = (first+end)/2;  // define middle element of array
            // find compatible element
            if ( (Integer.parseInt(assignmentArray[index].getStartTime().substring(0,2) +
                    assignmentArray[index].getStartTime().substring(3)) )
                    >= (Integer.parseInt(assignmentArray[midElement].getFinishTime().substring(0,2) +
                    assignmentArray[midElement].getFinishTime().substring(3)) ) ){
                // look at next one element
                if( (Integer.parseInt(assignmentArray[index].getStartTime().substring(0,2) +
                        assignmentArray[index].getStartTime().substring(3)) ) >=
                        ( Integer.parseInt(assignmentArray[midElement+1].getFinishTime().substring(0,2) +
                                assignmentArray[midElement+1].getFinishTime().substring(3)) )){
                    first = midElement+1;  // new first pivot
                }else{
                    return midElement;   // We found suitable element
                }
            }else{
                end = midElement-1;   // new end pivot
            }
        }
        return -1;   // there is no solution
    }

    /**
     * {@link #C} must be filled after calling this method
     */
    private void calculateC() {

        for ( int i = 0; i < C.length; i++){
            C[i] = binarySearch(i);    // store C array according to compatible
        }
    }

    /**
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleDynamic() {
        calculateC();   // first one store C array
        calculateMax(max.length-1);   // calculate compatible element according to their weight
        findSolutionDynamic(max.length-1);   // store solution dynamic array
        return solutionDynamic;
    }

    /**
     * {@link #solutionDynamic} must be filled after calling this method
     */
    private void findSolutionDynamic(int i) {
        //System.out.println("findSolutionDynamic("+i+")");
        if ( i < 1){
            if ( i == 0){
                System.out.println("findSolutionDynamic("+i+")");
                System.out.println("Adding " + assignmentArray[i].toString() + " to the dynamic schedule");
                solutionDynamic.add(assignmentArray[i]);  // add to solution dynamic array
            }
        }else if (C[i] == -1){   // C[i] is not in the solution
            System.out.println("findSolutionDynamic("+i+")");
            if ( assignmentArray[i].getWeight() -1 > max[i-1]) {   // compare each recursive call
                System.out.println("Adding " + assignmentArray[i].toString() + " to the dynamic schedule");
                solutionDynamic.add(assignmentArray[i]);  // add to solution dynamic array
                findSolutionDynamic(C[i]);   // other recursive call
            }else{
                findSolutionDynamic(i-1);
            }
        }else if( assignmentArray[i].getWeight() + max[C[i]] > max[i-1] ){  // C[i] is in the solution and compare last element
            System.out.println("findSolutionDynamic("+i+")");
            System.out.println("Adding " + assignmentArray[i].toString() + " to the dynamic schedule");
            solutionDynamic.add(assignmentArray[i]);
            findSolutionDynamic(C[i]);
        }else{
            System.out.println("findSolutionDynamic("+i+")");
            findSolutionDynamic(i-1);  // we didn't find element adding to array so other recursive call
        }
    }

    /**
     * {@link #max} must be filled after calling this method
     */
    private Double calculateMax(int i) {
        if ( i < 1){
            if ( i == 0){   // base condition
                max[i] = assignmentArray[i].getWeight();   // first element initialize
                System.out.println("calculateMax(0): Zero");
                return max[i];
            }
            return 0.0;
        }else{
            System.out.print("calculateMax("+i+"): ");
            if ( max[i] != null){
                System.out.println("Present");
            }else{
                System.out.println("Prepare");  // compare two conditions and select bigger than other
                max[i] = Math.max(assignmentArray[i].getWeight() +calculateMax(C[i]), calculateMax(i-1) );
            }
            return max[i];   // return true store element
        }
    }

    /**
     * {@link #solutionGreedy} must be filled after calling this method
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleGreedy() {
        System.out.println("Adding " + assignmentArray[0].toString() + " to the greedy schedule");
        solutionGreedy.add(assignmentArray[0]);   // initialize first element
        int index = 0;
        for ( int i = 1; i < assignmentArray.length; i++){   // compare with last item from solution greedy array
            if ( (Integer.parseInt(assignmentArray[i].getStartTime().substring(0,2) +
                    assignmentArray[i].getStartTime().substring(3)) )
                    >= (Integer.parseInt(solutionGreedy.get(index).getFinishTime().substring(0,2) +
                    solutionGreedy.get(index).getFinishTime().substring(3)) ) ){
                System.out.println("Adding " + assignmentArray[i].toString() + " to the greedy schedule");
                solutionGreedy.add(assignmentArray[i]);   // this elements' finish time is bigger or equal than last item
                index++;  // last element of solution greed array
            }
        }

        return solutionGreedy;
    }
}
