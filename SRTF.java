import java.util.*;

public class SRTF {

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

    Comparator<Process> comparator = new Comparator<>(){
        public int compare(Process p1, Process p2){
            if(p1.BurstTime != p2.BurstTime){
                return Integer.compare(p1.BurstTime, p2.BurstTime);
            }
            else{
                return Integer.compare(p1.ArrivalTime, p2.ArrivalTime);
            }
        }
    };

    public void srtfSchedulingAlgorithm(Process[] processes){
        PriorityQueue<Process>queue=new PriorityQueue<>(comparator);

        Arrays.sort(processes, Comparator.comparingDouble(p -> p.ArrivalTime));

        List<Process>ans=new ArrayList<>();
        List<String>ganttChart = new ArrayList<>();

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

                while(queue.isEmpty() && curr.BurstTime>0){
                    curr.BurstTime--;
                    startTime++;
                    if(i<processes.length && processes[i].ArrivalTime==startTime){
                        queue.add(processes[i]);
                        i++;
                    }
                }

                while(!queue.isEmpty() && curr.BurstTime <= queue.peek().BurstTime && curr.BurstTime>0 ){

                    curr.BurstTime--;
                    startTime++;

                    if(i<processes.length && processes[i].ArrivalTime==startTime){
                        queue.add(processes[i]);
                        i++;
                    }

                }

                if(curr.BurstTime==0){
                    ganttChart.add("P"+String.valueOf(curr.ProcessNo));
                    curr.ComplitionTime= startTime;
                    curr.TurnAroundTime = curr.ComplitionTime - curr.ArrivalTime;
                    curr.WaitingTime = curr.TurnAroundTime - curr.BT;
                    // curr.ResponseTime = startTime - curr.ArrivalTime;
                    ganttChart.add("P"+String.valueOf(curr.ProcessNo));
                    ans.add(curr);
                }
                else{
                    ganttChart.add("P"+String.valueOf(curr.ProcessNo));
                    queue.add(curr);
                }   
                
            }
            else{
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

        System.out.println("\nGantt Chart of SRTF is as below");
        System.out.println(ganttChart);
    
    }
    public static void main(String[] a){
        Scanner sc=new Scanner(System.in);
        SRTF srtf=new SRTF();

        System.out.println("Enter the total no. of process to schedule");
        int noOfProcess=sc.nextInt();

        Process[] processes=new Process[noOfProcess];

        System.out.println("Enter the arrival time of each process along with its brust time");
        for(int i=0;i<noOfProcess;i++){
            processes[i]=srtf.new Process(i, sc.nextInt(), sc.nextInt());
        }

        srtf.srtfSchedulingAlgorithm(processes);


    }
}
