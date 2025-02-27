package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.DataAccessObject.UserDAO;
import com.example.sa_g7_tw2_spring.Domain.Observer.MultiThreadHandler;
import com.example.sa_g7_tw2_spring.Domain.Prototype.ValueObjectCache;
import com.example.sa_g7_tw2_spring.ValueObject.AnalyzedVO;
import com.example.sa_g7_tw2_spring.ValueObject.UploadVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.text.ParseException;

public class SpringFileUploadAnalyze extends SpringEvent{
    ResultProcessDAO resultProcessDAO ;
    UserDAO userDAO;
    MultiThreadHandler multiThreadHandler;
    public SpringFileUploadAnalyze(ValueObject vo, MultiThreadHandler mth){
        super(vo);
        multiThreadHandler = mth;
    }
    @Override
    public ResponseEntity execute() throws ParseException, IOException {
        UploadVO uploadVO =(UploadVO)vo;
        resultProcessDAO=ResultProcessDAO.getInstance();
        resultProcessDAO.setJdbcTemplate(jdbcTemplate);
        userDAO=UserDAO.getInstance();
        userDAO.setJdbcTemplate(jdbcTemplate);
        String anID = resultProcessDAO.GenerateAnID();
        AnalyzedVO analyzedVO;
        try{
            resultProcessDAO.SoundFileToDB(uploadVO.getMultipartFile(), uploadVO.getWristbandName(),anID);
            String token =userDAO.returnTokenByWristbandName(uploadVO.getWristbandName());
            int id =userDAO.returnIDByWristbandName(uploadVO.getWristbandName());
            analyzedVO = (AnalyzedVO) ValueObjectCache.getValueObject("AnalyzedVO");
            analyzedVO.setMultipartFile(uploadVO.getMultipartFile());
            analyzedVO.setToken(token);
            analyzedVO.setWristbandName(uploadVO.getWristbandName());
            analyzedVO.setUserID(id);
            analyzedVO.setAn_ID(anID);

        }catch (Exception e){
            return new ResponseEntity("uploaded Error!"+e, HttpStatus.BAD_REQUEST);

        }
        multiThreadHandler.executeAnalyze(analyzedVO);
        return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);
    }
}
