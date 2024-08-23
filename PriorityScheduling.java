import java.util.*;

public class PriorityScheduling {

    class Process{
        int ProcessNo;
        int ArrivalTime;
        int BurstTime;
        int PriorityNo;

        int ComplitionTime;
        int TurnAroundTime;
        int WaitingTime;
        int ResponseTime;
        int BT;
        boolean getCPU;
        

        Process(int ProcessNo, int ArrivalTime, int BurstTime, int PriorityNo){
            this.ProcessNo=ProcessNo;
            this.ArrivalTime=ArrivalTime;
            this.BurstTime=BurstTime;
            this.PriorityNo=PriorityNo;
            this.BT=BurstTime;
            this.getCPU=false;
        }

    }

    Comparator<Process>comparator=new Comparator<>(){
        public int compare(Process p1, Process p2){
            return Integer.compare(p2.PriorityNo, p1.PriorityNo);
        }
    };

    public void PrioritySchedulingAlgorithm(Process[] processes){
        Arrays.sort(processes, Comparator.comparingInt(p->p.ArrivalTime));
        PriorityQueue<Process>queue=new PriorityQueue<>(comparator);

        List<Process>ans=new ArrayList<>();
        List<String>ganttChart=new ArrayList<>();

        int startTime=0;
        int i=0;

        while(i<processes.length || !queue.isEmpty()){

            while(i<processes.length && processes[i].ArrivalTime<=startTime){
                queue.add(processes[i]);
                i++;
            }

            if(!queue.isEmpty()){
                Process curr=queue.poll();

                if(curr.getCPU==false){
                    curr.getCPU=true;
                    curr.ResponseTime = startTime - curr.ArrivalTime;
                }

                ganttChart.add("P"+String.valueOf(curr.ProcessNo));
                
                curr.BurstTime--;
                startTime++;

                while(i<processes.length && processes[i].ArrivalTime<=startTime){
                    queue.add(processes[i]);
                    i++;
                }

                if(curr.BurstTime>0){
                    queue.add(curr);
                }
                else{
                    curr.ComplitionTime=startTime;
                    curr.TurnAroundTime= curr.ComplitionTime - curr.ArrivalTime;
                    curr.WaitingTime =  curr.TurnAroundTime - curr.BT;

                    ans.add(curr);
                }
            }
            else{
                ganttChart.add("CPU Idle");
                startTime++;
            }
        }




        System.out.println("Process No.  |AT   |BT    |CT    |TAT   |WT   |RT");
        for(i=0;i<ans.size();i++){
            Process p=ans.get(i);
            System.out.println("P"+p.ProcessNo + "           |" 
            + p.ArrivalTime + "    |" 
            + p.BT + "     |" 
            + p.ComplitionTime + "     |" 
            + p.TurnAroundTime + "    |" 
            + p.WaitingTime + "    |" 
            + p.ResponseTime);
        }

        System.out.println("\nGantt Chart of Priority Scheduling is as below");
        System.out.println(ganttChart);
    }

    public static void main(String[] a){
        Scanner sc=new Scanner(System.in);
        PriorityScheduling ps=new PriorityScheduling();

        System.out.println("Enter the total no. of process to schedule");
        int noOfProcess=sc.nextInt();

        Process[] processes=new Process[noOfProcess];

        System.out.println("Enter the arrival time, brust time of each process along with Priority(higher number higher priority) ");
        for(int i=0;i<noOfProcess;i++){
            processes[i]=ps.new Process(i, sc.nextInt(), sc.nextInt(), sc.nextInt());
        }

        ps.PrioritySchedulingAlgorithm(processes);


    }
}


// 4
// 0 5 10
// 1 4 20
// 2 2 30
// 4 1 40