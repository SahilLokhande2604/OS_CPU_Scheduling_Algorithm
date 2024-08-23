import java.util.*;

public class RoundRobin {

    class Process{
        int ProcessNo;
        int ArrivalTime;
        int BurstTime;
        int ComplitionTime;
        int TurnAroundTime;
        int WaitingTime;
        int ResponseTime;
        int BT;
        boolean getCPU;

        Process(int ProcessNo, int ArrivalTime, int BurstTime){
            this.ProcessNo=ProcessNo;
            this.ArrivalTime=ArrivalTime;
            this.BurstTime=BurstTime;
            this.BT=BurstTime;
            this.getCPU=false;
        }

    }

    public void RoundRobinSchedulingAlgorithm(Process[] processes, int timeQuantum){
        Arrays.sort(processes, Comparator.comparingInt( p -> p.ArrivalTime));
        Queue<Process>queue=new LinkedList<>();
        List<Process>ans=new ArrayList<>();
        List<String> ganttChart=new ArrayList<>();


        int startTime=0;
        int i=0;
        
        while(i<processes.length || !queue.isEmpty()){
            while(i<processes.length && processes[i].ArrivalTime<=startTime){
                queue.add(processes[i]);
                i++;
            }

            if(!queue.isEmpty()){

                Process curr=queue.poll();

                ganttChart.add("P"+String.valueOf(curr.ProcessNo));

                if(curr.getCPU==false){
                    curr.getCPU=true;
                    curr.ResponseTime=startTime - curr.ArrivalTime;
                }

                int cnt=0;
                while(cnt<timeQuantum){
                    curr.BurstTime--;
                    
                    startTime++;
                    cnt++;

                    if(curr.BurstTime==0){
                        break;
                    }

                    while(i<processes.length && processes[i].ArrivalTime<=startTime){
                        queue.add(processes[i]);
                        i++;
                    }
                }

                if(curr.BurstTime>0){
                    queue.add(curr);
                }
                else{
                    curr.ComplitionTime=startTime;
                    curr.TurnAroundTime= curr.ComplitionTime - curr.ArrivalTime;
                    curr.WaitingTime = curr.TurnAroundTime - curr.BT;
                    ans.add(curr);
                }
            }
            else{
                startTime++;
                ganttChart.add("CPU Idle");
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

        System.out.println("\nGantt Chart of RoundRobin is as below");
        System.out.println(ganttChart);
    }
    public static void main(String[] a){
        Scanner sc=new Scanner(System.in);
        RoundRobin rr=new RoundRobin();

        System.out.println("Enter the total no. of process to schedule");
        int noOfProcess=sc.nextInt();

        System.out.println("Enter the Time Quantum");
        int timeQuantum=sc.nextInt();

        Process[] processes=new Process[noOfProcess];

        System.out.println("Enter the arrival time of each pocess along with its brust time");
        for(int i=0;i<noOfProcess;i++){
            processes[i]=rr.new Process(i, sc.nextInt(), sc.nextInt());
        }

        rr.RoundRobinSchedulingAlgorithm(processes, timeQuantum);


    }
}


// 4
// 2
// 0 5
// 1 4
// 2 2
// 4 1