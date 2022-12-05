package com.example.sa_g7_tw2_spring.Event;

import com.example.sa_g7_tw2_spring.DataAccessObject.DataAccessObject;
import com.example.sa_g7_tw2_spring.DataAccessObject.ResultProcessDAO;
import com.example.sa_g7_tw2_spring.DataAccessObject.ResultQueryDAO;
import com.example.sa_g7_tw2_spring.ValueObject.FindRequestVO;
import com.example.sa_g7_tw2_spring.ValueObject.ResultVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;


public class SpringRetrunByDate extends SpringEvent{


    public SpringRetrunByDate(FindRequestVO vo, JdbcTemplate jdbcTemplate){
        super(vo,jdbcTemplate);

    }
    @Override
    public Collection<ResultVO> excute() throws ParseException {
        return ((ResultQueryDAO)dao).returnBYDate((FindRequestVO) vo);
    }
}
