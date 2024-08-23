import java.util.*;

public class FCFS{
    class Process{
        int ProcessNo;
        int ArrivalTime;
        int BurstTime;
        int ComplitionTime;
        int TurnAroundTime;
        int WaitingTime;
        int ResponseTime;

        Process(int ProcessNo, int ArrivalTime, int BurstTime){
            this.ProcessNo=ProcessNo;
            this.ArrivalTime=ArrivalTime;
            this.BurstTime=BurstTime;
        }

    }

    Comparator<Process> comparator = new Comparator<>(){
        public int compare(Process p1, Process p2){
            return Integer.compare(p1.ArrivalTime, p2.ArrivalTime);
        }
    };

    public void fcfsSchedulingAlgorithm(Process[] processes){
        PriorityQueue<Process>queue=new PriorityQueue<>(comparator);

        List<Process> ans=new ArrayList<>();
        List<String> ganttChart = new ArrayList<>();


        int startTime=0;
        int i=0;
    
        while(i<processes.length || !queue.isEmpty()){

            while(i<processes.length && processes[i].ArrivalTime<=startTime){
                queue.add(processes[i]);
                i++;
            }

            if(!queue.isEmpty()){
                Process curr=queue.poll();
                ganttChart.add("P" + String.valueOf(curr.ProcessNo));
                curr.ComplitionTime=startTime + curr.BurstTime;
                curr.TurnAroundTime=curr.ComplitionTime - curr.ArrivalTime;
                curr.WaitingTime= curr.TurnAroundTime - curr.BurstTime;
                curr.ResponseTime= startTime - curr.ArrivalTime;
                ans.add(curr);
                startTime+=curr.BurstTime;
            }
            else{
                ganttChart.add("CPU Ideal");
                startTime++;
            }

        }
        System.out.println("Process No.  |AT   |BT    |CT    |TAT   |WT   |RT");
        for(i=0;i<ans.size();i++){
            Process p=ans.get(i);
            System.out.println("P"+p.ProcessNo + "           |" 
            + p.ArrivalTime + "    |" 
            + p.BurstTime + "     |" 
            + p.ComplitionTime + "     |" 
            + p.TurnAroundTime + "    |" 
            + p.WaitingTime + "    |" 
            + p.ResponseTime);
        }

        System.out.println("\nGantt Chart of FCFS is as below");
        System.out.println(ganttChart);

    }

    public static void main(String[] a){
        Scanner sc=new Scanner(System.in);
        FCFS fcfs=new FCFS();

        System.out.println("Enter the total no. of process to schedule");
        int noOfProcess=sc.nextInt();

        Process[] processes=new Process[noOfProcess];

        System.out.println("Enter the arrival time of each pocess along with its brust time");
        for(int i=0;i<noOfProcess;i++){
            processes[i]=fcfs.new Process(i, sc.nextInt(), sc.nextInt());
        }

        fcfs.fcfsSchedulingAlgorithm(processes);


    }
}