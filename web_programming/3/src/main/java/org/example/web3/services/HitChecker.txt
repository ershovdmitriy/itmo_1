package org.example.web3.services;

public class HitChecker {
    public boolean checkHit(Integer x, Double y, Double r) {
        if(x <= 0 && y <= 0) {
            return x >= -r / 2 && y >= -r;
        }
        else if(x >= 0 && y <= 0) {
            return x - y <= r;
        }
        else if(x >= 0 && y >= 0) {
            return x * x + y * y <= (r / 2) * (r / 2);
        }
        else{
            return false;
        }
    }
}
