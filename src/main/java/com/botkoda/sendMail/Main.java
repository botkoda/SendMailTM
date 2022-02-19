package com.botkoda.sendMail;

import com.botkoda.sendMail.interfacesClass.Connecter;
import com.botkoda.sendMail.interfacesClass.ConnecterKNS;

import com.botkoda.sendMail.interfacesClass.KeyForMsg;
import com.botkoda.sendMail.prepareToSend.ReadyToSend;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
//   static  int ij=0;
    public static void main(String[] args) throws Exception{
        String sqlProcTM1 = "BEGIN EMAIL_TM_20; END;";
        String sqlProcTM0 = "BEGIN EMAIL_TM_01; END;";
        String sqlProcKNS = "BEGIN UPDATE_KNS_ALL; END;";


        String sqlTM1 = "select well_id,property_date,well_name,field_name,EMAIL, t.ceh_name, c.theme,c.commnet1,c.commnet2 \n" +
                "              from GREM_TM_1 t\n" +
                "              join (select * from EMAIL_USERS ) s  on s.ceh_name=t.ceh_name  and instr(s.vid,t.vid)>0 \n" +
                "              join COMMENT_TM_0 c on t.type_zamer=c.id\n" +
                "              where new_row=1";
        String sqlTM0 = "select well_id,property_date,well_name,field_name,EMAIL, t.ceh_name, c.theme,c.commnet1,c.commnet2 \n" +
                "              from GREM_TM_0 t\n" +
                "              join (select * from EMAIL_USERS ) s  on s.ceh_name=t.ceh_name and  instr(s.vid,t.type_zamer)>0 /*поле vid отвечает за выборку рассылки*/\n" +
                "              join COMMENT_TM_0 c on t.type_zamer=c.id\n" +
                "              where new_row=1";

        String sqlKNS = "select idserv, dt,field_name,EMAIL,t.ceh_name, t.kns, t.vid, c.theme,c.commnet1,c.commnet2,t.discr,new_row \n"+
                  "from EMAIl_KNS t\n"+
                  "join (select * from EMAIL_USERS ) s  on instr(s.kns,t.kns)>0 and  instr(s.ceh_name,t.ceh_name)>0 and  instr(s.vid,t.vid)>0 \n"+
                  "join EMAIL_COMMENTS c on t.vid=c.id \n" +
                  "join (select id as idserv, kns as kns_from_server from EMAIL_SERVERS) s on t.kns=s.kns_from_server \n"+
                  "where new_row=1" ;

    ScheduledExecutorService service1= Executors.newScheduledThreadPool(3);
        //асинхронное выполнение кода с промежутком 10 секунда между выполнениями:
        service1.scheduleWithFixedDelay(() -> new ReadyToSend(new Connecter(), KeyForMsg.TM).start(sqlTM0,sqlProcTM0),0,10, TimeUnit.SECONDS);
        service1.scheduleWithFixedDelay(() -> new ReadyToSend(new Connecter(),KeyForMsg.TM).start(sqlTM1,sqlProcTM1),0,10, TimeUnit.SECONDS);
        service1.scheduleWithFixedDelay(() -> new ReadyToSend(new ConnecterKNS(),KeyForMsg.KNS).start(sqlKNS,sqlProcKNS),0,600, TimeUnit.SECONDS);
        
        
}

    // public static String GetsqlProcKNS(int i){

        
    //     return "BEGIN \n"+
    //             "update_kns2("+ i +"); \n"+
    //             "update_kns(22); \n"+            
    //             "END;";
    // }
    

}
