
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {

    static class Task implements Comparable<Task> {
        int id;
        int process;


        Task(int id, int process){
            this.id = id;
            this.process = process;
        }

        public int compareTo(Task other) {
            return Integer.compare(this.process, other.process);

        }


    }
    static class PriorityTask extends Task {
        int priority;

        PriorityTask(int id, int processTime, int priority) {
            super(id, processTime);
            this.priority = priority;
        }

        @Override
        public int compareTo(Task other) {
            if (other instanceof PriorityTask) {
                PriorityTask otherTask = (PriorityTask) other;
                if (this.priority != otherTask.priority) {
                    return Integer.compare(this.priority, otherTask.priority);
                }
            }
            return Integer.compare(this.process, other.process);
        }
    }
    static class ArrivalTask extends Task {
        int arrival;

        ArrivalTask(int id, int process, int arrival) {
            super(id, process);
            this.arrival = arrival;
        }


        @Override
        public int compareTo(Task other) {
            if (other instanceof ArrivalTask) {
                ArrivalTask otherArrivalTask = (ArrivalTask) other;
                if (this.arrival != otherArrivalTask.arrival) {
                    return Integer.compare(this.arrival, otherArrivalTask.arrival);
                }
                return Integer.compare(this.process, otherArrivalTask.process);
            }
            return Integer.compare(this.process, other.process);
        }
    }



    public static void main(String[] args) throws FileNotFoundException {
        priorityqueue<Task> pq = new priorityqueue<>(100);
        priorityqueue<Task> pq2 = new priorityqueue<>(100);
        priorityqueue<Task> pq3 = new priorityqueue<>(100);


        File input = new File("task1-input.txt");

        Scanner reader = new Scanner(input);
        while (reader.hasNextLine()) {
            String task = reader.nextLine();
            String[] parts = task.split(" ");

            int jobId = Integer.parseInt(parts[0]);
            int processtime = Integer.parseInt(parts[1]);

            pq.insert(new Task(jobId, processtime));

        }

        input = new File("task2-input.txt");
        Scanner reader2 = new Scanner(input);
        while (reader2.hasNextLine()) {
            String task = reader2.nextLine();
            String[] parts = task.split(" ");

            int jobId = Integer.parseInt(parts[0]);
            int processtime = Integer.parseInt(parts[1]);
            int priority = Integer.parseInt(parts[2]);

            pq2.insert(new PriorityTask(jobId, processtime, priority));

        }

        input = new File("task3-input.txt");
        Scanner reader3 = new Scanner(input);

        while (reader3.hasNextLine()) {
            String task = reader3.nextLine();
            String[] parts = task.split(" ");

            int jobId = Integer.parseInt(parts[0]);
            int processtime = Integer.parseInt(parts[1]);
            int arrival = Integer.parseInt(parts[2]);

            pq3.insert(new ArrivalTask(jobId, processtime, arrival));


        }

        System.out.println("Task 1 output");
        scheduleJobs(pq);
        System.out.println(" ");
        System.out.println("Task 2 output");
        scheduleJobs(pq2);
        System.out.println(" ");
        System.out.println("Task 3 output");
        DynamicSchedule(pq3);
    }

    public static void scheduleJobs(priorityqueue<Task> pq) {
        int time = 0;
        int totalCompletionTime = 0;
        int[] executionOrder = new int[pq.size()];
        int index = 0;

        while (!pq.isEmpty()) {
            Task currentJob = pq.delMin();
            executionOrder[index++] = currentJob.id;
            time += currentJob.process;
            totalCompletionTime += time;
        }
        double averageCompletionTime = (double) totalCompletionTime / executionOrder.length;

        // Output the results
        System.out.print("Execution order: [");
        for (int i = 0; i < executionOrder.length; i++) {
            System.out.print(executionOrder[i]);
            if (i < executionOrder.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.println("Average completion time: " + averageCompletionTime);


    }
    public static void DynamicSchedule(priorityqueue<Task> arrivalQueue) {
        priorityqueue<Task> readyQueue = new priorityqueue<>(arrivalQueue.size());
        int time = 0;
        int totalCompletionTime = 0;
        int tasksCompleted = 0;
        int[] executionOrder = new int[arrivalQueue.size()];
        int index = 0;

        while (!arrivalQueue.isEmpty() || !readyQueue.isEmpty()) {
            while (!arrivalQueue.isEmpty() && ((ArrivalTask) arrivalQueue.peek()).arrival <= time) {
                Task task = arrivalQueue.delMin();
                readyQueue.insert(task);
            }

            if (readyQueue.isEmpty()) {
                if (!arrivalQueue.isEmpty()) {
                    time = ((ArrivalTask) arrivalQueue.peek()).arrival;
                }
            } else {
                Task currentTask = readyQueue.delMin();
                time += currentTask.process;
                totalCompletionTime += time;

                executionOrder[index++] = currentTask.id;
                tasksCompleted++;
            }
        }


        System.out.print("Execution order: [");
        for (int i = 0; i < index; i++) {
            System.out.print(executionOrder[i]);
            if (i < index - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        double averageCompletionTime = (double) totalCompletionTime / tasksCompleted;
        System.out.println("Average completion time: " + averageCompletionTime);
    }



}

