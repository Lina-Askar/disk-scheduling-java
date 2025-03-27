package diskscheduling;
import java.util.*;
public class DiskScheduling {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the total number of cylinders: ");
        int cylinders = scanner.nextInt();
        System.out.print("Enter the current head position: ");
        int head = scanner.nextInt();
        System.out.print("Enter the head direction (1 for up, -1 for down): ");
        int direction = scanner.nextInt();
        System.out.print("Enter the queue of 15 pending requests: ");
        int[] requests = new int[15];
        for (int i = 0; i < 15; i++) {
            requests[i] = scanner.nextInt();}
        System.out.println("\nFirst-Come-First-Served (FCFS): ");
        int fcfsDistance = FCFS(requests, head);
        System.out.println("\nShortest Seek Time First (SSTF):");
        int sstfDistance = SSTF(requests, head);
        System.out.println("\nSCAN:");
        int scanDistance = SCAN(requests, head, cylinders, direction);
        System.out.println("\nPerformance Analysis:");
        analyzePerformance(fcfsDistance, sstfDistance, scanDistance);
    }

    // First-Come-First-Served (FCFS)
    private static int FCFS(int[] requests, int head) {
        int totalSeekD = 0;
        int startHead = head;
        List<Integer> headSequence = new ArrayList<>();

        for (int request : requests) {
            headSequence.add(request);
            totalSeekD += Math.abs(request - head);
            head = request;
        }

        System.out.println("Head movement sequence: " + startHead + ", " + headSequence);
        System.out.println("Total seek distance: " + totalSeekD);
        return totalSeekD;}
    // Shortest Seek Time First (SSTF)
    private static int SSTF(int[] requests, int head) {
        List<Integer> requestL = new ArrayList<>();
        for (int request : requests) {
            requestL.add(request);}
        int startHead = head;
        int totalSeekDistance = 0;
        List<Integer> headSequence = new ArrayList<>();
        while (!requestL.isEmpty()) {
            int closestRequest = ClosestRequest(requestL, head);
            headSequence.add(closestRequest);
            totalSeekDistance += Math.abs(closestRequest - head);
            head = closestRequest;
            requestL.remove((Integer) closestRequest);}
        System.out.println("Head movement sequence: " + startHead + ", " + headSequence);
        System.out.println("Total seek distance: " + totalSeekDistance);
        return totalSeekDistance;}
    
    private static int ClosestRequest(List<Integer> requests, int head) {
        int closest = requests.get(0);
        int minDistance = Math.abs(closest - head);
        for (int request : requests) {
            int distance = Math.abs(request - head);
            if (distance < minDistance) {
                closest = request;
                minDistance = distance;}}
        return closest;}
    // SCAN
    private static int SCAN(int[] requests, int head, int cylinders, int direction) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> headSequence = new ArrayList<>();
        int startHead = head;
        for (int request : requests) {
            if (request < head) {
                left.add(request);
            } else {
                right.add(request);}}
        // Add boundary limits
        if (direction == -1) {
            left.add(0);
        } else {
            right.add(cylinders - 1);}
        // Sort lists
        Collections.sort(left, Collections.reverseOrder());
        Collections.sort(right);
        int totalSeekDistance = 0;
        if (direction == -1) {// Scan left first
            for (int request : left) {
                headSequence.add(request);
                totalSeekDistance += Math.abs(request - head);
                head = request;}
            for (int request : right) {
                headSequence.add(request);
                totalSeekDistance += Math.abs(request - head);
                head = request;}
        } else {
        // Scan right first
            for (int request : right) {
                headSequence.add(request);
                totalSeekDistance += Math.abs(request - head);
                head = request;}
            for (int request : left) {
                headSequence.add(request);
                totalSeekDistance += Math.abs(request - head);
                head = request;}}
        System.out.println("Head movement sequence: " + startHead + ", " + headSequence);
        System.out.println("Total seek distance: " + totalSeekDistance);
        return totalSeekDistance;}
    // Performance Analysis
    private static void analyzePerformance(int fcfsSeekDistance, int sstfSeekDistance, int scanSeekDistance){
        System.out.println("\nTotal Seek Distances:");
        System.out.println("First-Come-First-Served (FCFS): " + fcfsSeekDistance);
        System.out.println("Shortest Seek Time First (SSTF): " + sstfSeekDistance);
        System.out.println("SCAN: " + scanSeekDistance);

        int minSeekDistance = Math.min(fcfsSeekDistance, 
                Math.min(sstfSeekDistance, scanSeekDistance));
        if (minSeekDistance == fcfsSeekDistance) {
            System.out.println("Best Algorithm: First-Come-First-Served (FCFS)");
        } else if (minSeekDistance == sstfSeekDistance) {
            System.out.println("Best Algorithm: Shortest Seek Time First (SSTF)");
        } else {
            System.out.println("Best Algorithm: SCAN");}}}
        

    


