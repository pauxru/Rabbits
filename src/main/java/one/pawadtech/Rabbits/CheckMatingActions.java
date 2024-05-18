package one.pawadtech.Rabbits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckMatingActions {
    @Autowired
    private  MatingService matingService = null;
    private final DailyQueueService dailyQueueService;
    private RabbitService rabbitService = null;

    private static TasksService tasksService;
    @Autowired
    private  DateService dateService=null;


    @Autowired
    public CheckMatingActions(
            MatingService matingService,
            DailyQueueService dailyQueueService,
            RabbitService rabbitService,
            TasksService tasksService,
            DateService dateService) {
        this.matingService = matingService;
        this.dailyQueueService = dailyQueueService;
        this.rabbitService = rabbitService;
        this.tasksService = tasksService;
        this.dateService = dateService;
    }
    public void createTodo4HealthBeforeMating(){
        List<Mating> nullHealthmates = matingService.getMatingObjectsWithNullHealthCheckedDate();


        for(Mating mate: nullHealthmates){
            System.out.println("Mating ID "+mate.getMatingNum()+" is yet for health checkup");
            String queueID = dateService.getTodayDateString();
            Optional<Rabbit> f_mate = rabbitService.findRabbitBySerialNum(mate.getFemale());
            Optional<Rabbit> m_mate = rabbitService.findRabbitBySerialNum(mate.getMale());
            String f_cage = "";
            String m_cage = "";
            if(f_mate.isPresent()){
                Rabbit fe_mate = f_mate.get();
                f_cage = fe_mate.getCage();
            }
            if(m_mate.isPresent()){
                Rabbit ma_mate = m_mate.get();
                m_cage = ma_mate.getCage();
            }
            String TaskTextf = "Check HEALTH of female "+ mate.getFemale() +" in Cage Number " + f_cage;
            System.out.println(TaskTextf);
            dailyQueueService.updateTaskList(queueID, TaskTextf);
            String TaskTextm = "Check HEALTH of male"+mate.getMale() + " in Cage Number "+ m_cage;
            System.out.println(TaskTextm);
            dailyQueueService.updateTaskList(queueID, TaskTextm);
        }
    }

    private Map<String, String> answerDataMap(String... required){
        Map<String, String> AnswerDataMap = new HashMap<>();
        for(String req: required){
            AnswerDataMap.put(req,"~ ");
        }
        return AnswerDataMap;
    }

    public void createTask(String refId, String taskType, String msg, String answerData){
        System.out.println("Here to create a new tasks");
        String introDate = dateService.getTodayDateString();
        List<Tasks> todayTasks = tasksService.getTasksByIntroDate(introDate);

        int sec;
        if(!todayTasks.isEmpty()){
            sec = todayTasks.size() + 1;
        }else {
            sec = 1;
        }
        String taskRef = dateService.concatTodayDateString() + sec;

        System.out.println("TASK ID ::: "+taskRef);
        System.out.println("TASK MESSAGE ::: "+msg);
        Tasks newTask = new Tasks(taskRef, taskType, refId, introDate, false, null, msg, answerData);
        tasksService.createTask(newTask);
    }

    public String manageMatings(){

        List<Mating> matings = matingService.findAllMating();
        System.out.println("Here to manageMatings");
        for(Mating mating : matings){
            String matingID = mating.getMatingNum();
            Optional<Rabbit> femal = rabbitService.findRabbitBySerialNum(mating.getFemale());
            Optional<Rabbit> mal = rabbitService.findRabbitBySerialNum(mating.getMale());
            if(!femal.isPresent()){
                System.out.println("Female selected does not exist :: "+mating.getFemale());
                continue;
            }else {
                System.out.println("Female selected :: "+mating.getFemale());
            }
            if(!mal.isPresent()){
                System.out.println("Male selected does not exist ::"+mating.getMale());
                continue;
            }else {
                System.out.println("Male selected ::"+mating.getMale());
            }
            Rabbit female = femal.get();
            Rabbit male = mal.get();

            //************** Check healthchecked date *************//
            Date hcd = mating.getHealth_checked_date();
            if(hcd != null){
                System.out.println("Health Checked on ::: "+mating.getHealth_checked_date());
            }else{
                createTask(matingID, "CheckHealth", "Check the health of Female "+ mating.getFemale() + " in cage "+female.getCage(),"~ ");
                createTask(matingID, "CheckHealth","Check the health of Male "+ mating.getMale() + " in cage "+male.getCage(),"~ ");
                continue;
            }

            //************** Check mate from date; empty when not mated *************//
            Date mateFrm = mating.getMate_from();
            if(mateFrm != null){
                System.out.println("Started mating on ::: "+mating.getMate_from());
            }else{
                String msg = "Move female "+ mating.getMale() + " in cage "+female.getCage() +" to cage ";
                msg += male.getCage() + " to mate with male " + mating.getMale();
                createTask(matingID,"MateFrom", msg,"~ ");
                continue;
            }

            //************** Check mate to date; empty when not done mating *************//
            Date mateTo = mating.getMate_to();
            if(mateTo != null){
                System.out.println("Endend mating on ::: "+mating.getMate_from());
            }else{
                if(dateService.durationFrom(mateFrm).toHours() > 72){
                    createTask(matingID,"MateTo", "Remove Female "+ mating.getFemale() + " from cage "+ mating.getCage() +" to its original cage "+ female.getCage(), "~ ");
                    continue;
                }
            }

            //************** Check if pregnanant *************//
            if(mating.getPregnancy_confirmed().equalsIgnoreCase("") || mating.getPregnancy_confirmed().equalsIgnoreCase("0")){
                if(dateService.durationFrom(mateTo).toDays() > 13) {
                    createTask(matingID,"CheckPregnancy", "Check if Female " + mating.getFemale() + " in cage " + female.getCage() + " is pregnant", "~ ");
                    continue;
                }
            }

            //************** Put kindling basket *************//
            if(mating.getPregnancy_confirmed().equalsIgnoreCase("1")){
                if(dateService.durationFrom(mateFrm).toDays() > 25) {
                    createTask(matingID,"PutNesting", "Put kindling basket for Female " + mating.getFemale() + " in cage " + female.getCage(), "~ ");
                    continue;
                }
            }

            //************** Has it given birth yet *************//
            if(mating.getPregnancy_confirmed().equalsIgnoreCase("1")){
                if(dateService.durationFrom(mateFrm).toDays() > 26) {
                    createTask(matingID,"ActualBirthday", "Has Female " + mating.getFemale() + " in cage " + female.getCage() + " given birth?", "~ ");
                    continue;
                }
            }
        }
        return "OK";
    }
}
