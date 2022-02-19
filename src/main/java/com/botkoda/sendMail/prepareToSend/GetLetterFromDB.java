package com.botkoda.sendMail.prepareToSend;

import com.botkoda.sendMail.interfaces.Connectuble;
import com.botkoda.sendMail.interfaces.FromDbGetuble;
import com.botkoda.sendMail.interfacesClass.KeyForMsg;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GetLetterFromDB implements FromDbGetuble {
    Letter letter;
    Map<Integer, Letter> letterMap = new HashMap<>();
    Statement statement = null;

    //Connectuble connectuble = new Connecter();
    @Override
    public Map<Integer, Letter> getFromDB(String sql, String sqlProc, Connectuble connecter, KeyForMsg keyForMsg) {
        try (Connection connection = connecter.connect()) {
            if (connection != null) {
                System.out.println("Connected to the database! " +keyForMsg);
            } else {
                System.out.println("Failed to make connection!");
            }

            executeSqlProc(connection, sqlProc);
      
            ResultSet rs = statement.executeQuery(sql);
        if(keyForMsg==KeyForMsg.TM){
            while (rs.next()) {
                letter = new Letter();
                int well_id = rs.getInt("well_id");
                if (letterMap.containsKey(well_id)) {
                    letter.setTo(letterMap.get(well_id).getTo() + ',' + rs.getString("EMAIL"));
                } else {
                    letter.setTo(rs.getString("EMAIL"));
                }
                letter.setSubject(rs.getString("theme"));

                String property_date = rs.getString("property_date");
                String well_name = rs.getString("well_name");
                String field_name = rs.getString("field_name");
                String ceh_name = rs.getString("ceh_name");
                String commnet1 = rs.getString("commnet1");
                String commnet2 = rs.getString("commnet2");

                letter.setMsg(preaperMsg(keyForMsg, property_date, commnet1, commnet2, ceh_name, field_name, well_name));
                letterMap.put(well_id, letter);


            }
            
        }
        if(keyForMsg==KeyForMsg.KNS){
            while (rs.next()) {
                letter = new Letter();
                int id = rs.getInt("idserv");
                if (letterMap.containsKey(id)) {
                    letter.setTo(letterMap.get(id).getTo() + ',' + rs.getString("EMAIL"));
                } else {
                    letter.setTo(rs.getString("EMAIL"));
                }
                letter.setSubject(rs.getString("theme"));

                String property_date = rs.getString("dt");
                String well_name = rs.getString("kns");
                String field_name = rs.getString("field_name");
                String ceh_name = rs.getString("ceh_name");
                String commnet1 = rs.getString("commnet1");
                String commnet2 = rs.getString("discr");

                letter.setMsg(preaperMsg(keyForMsg, property_date, commnet1, commnet2, ceh_name, field_name, well_name));
                letterMap.put(id, letter);


            }
        }
            rs.close();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return letterMap;
    }

    public void executeSqlProc(Connection connection, String sqlProc) {

       // CallableStatement cs;
        try {
            CallableStatement cs = connection.prepareCall(sqlProc);
            cs.execute();
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String preaperMsg(KeyForMsg keyForMsg, String property_date, String commnet1, String commnet2, String ceh_name, String field_name, String well_name) {
        String text = "";
        switch (keyForMsg) {
            case TM:
                text = "Добрый день." +
                        "<br><br>" +
                        commnet1 + ":<br>" +
                        "Дата: <b>" + property_date + "</b><br>" +
                        "Цех: <b>" + ceh_name + "</b><br>" +
                        "Месторождение: <b>" + field_name + "</b><br>" +
                        "Скважина: <b>" + well_name + "</b><br>" +
                        commnet2 + "<br><br>" +
                        "Просьба не отвечать, письмо сформировано автоматически.";
                break;
            case KNS:
                text = "Добрый день."+
                        "<br><br>"+
                        commnet1 +":<br>"+
                        "Дата: <b>"+property_date+"</b><br>"+
                        "Объект: <b>"+ well_name +"</b><br>"+
                        "Месторождение: <b>"+field_name+"</b><br>"+
                        "Фактор отклонения: <b>"+commnet2+"</b><br>"+
                        "<br><br>"+
                        "Просьба не отвечать, письмо сформировано автоматически.";
                
                break;
        }
        return text;

    }


}
