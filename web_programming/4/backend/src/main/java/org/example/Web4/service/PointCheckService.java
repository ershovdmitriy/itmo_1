package org.example.Web4.service;
import org.springframework.stereotype.Service;

@Service
public class PointCheckService {

    public boolean checkHit(double x, double y, double r){
        if (x <= 0 && y >= 0){
            return x > -r/2 && y < r;
        }
        else if (x <= 0 && y <= 0){
            return 2*x + y + r >= 0;
        }
        else if (x >= 0 && y <= 0){
            return x*x + y*y <= r*r/4;
        }
        else{
            return false;
        }
    }
}
